// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.diff;

import java.util.*;

/**********************************************************************************
* A class to compare Vectors of objects.  The result of comparison
* is a list of DiffChgs objects which form an edit script.
* The objects compared are DiffEntity's with String representations of an EntityItem
* and its position in the VE extract.
* The DiffChgs list will indicate the deletions from the first Vector and
* the additions to the second Vector needed to convert the first Vector into the second
* Vector.
*
*   The basic algorithm is described in:
*   "An O(ND) Difference Algorithm and its Variations", Eugene Myers,
*   Algorithmica Vol. 1 No. 2, 1986, p 251.
*   http://www.xmailserver.org/diff2.pdf
*
* Code is a modified version of a freeware Java implementation by
* Stuart D. Gathman, ported from GNU diff 1.15  from http://www.bmsi.com/java/#diff
* This port is based on GNU Diff, which is GPL
* from http://www.gnu.org/licenses/gpl-faq.html
* 	Does the GPL require that source code of modified versions be posted to the public?
* 		The GPL does not require you to release your modified version. You are free to make
* 	modifications and use them privately, without ever releasing them. This applies to
* 	organizations (including companies), too; an organization can make a modified version
* 	and use it internally without ever releasing it outside the organization.
* 	But if you release the modified version to the public in some way, the GPL requires you to make
* 	the modified source code available to the program's users, under the GPL.
*/
// $Log: Diff.java,v $
// Revision 1.2  2006/10/27 21:04:50  wendy
// parm name change for jtest
//
// Revision 1.1  2006/07/24 20:50:11  wendy
// Replacement for XML in change reports
//
//
public class Diff {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";

	/** cvs revision number */
	public static final String VERSION = "$Revision: 1.2 $";

	private static final int BIG_SNAKE = 20;
	private static final int I_200 = 200;
	private static final int I_21 = 21;
	private static final int I_12 = 12;
	private static final int I_64 = 64;

	// 1 more than the maximum equivalence value used for this or its sibling file.
	private int equivMax = 1;

	// When set to true, the comparison uses a heuristic to speed it up.
	// With this heuristic, for files with a constant small density
	// of changes, the algorithm is linear in the file size.
	private boolean heuristic = false;
	// When set to true, the algorithm returns a guaranteed minimal
	//  set of changes.  This makes things slower, sometimes much slower.
	private boolean noDiscards = false;
	private int[] xvec, yvec; // Vectors being compared.
	private int[] fdiag;      /* Vector, indexed by diagonal, containing
				   the X coordinate of the point furthest
				   along the given diagonal in the forward
				   search of the edit matrix. */
	private int[] bdiag;      /* Vector, indexed by diagonal, containing
				   the X coordinate of the point furthest
				   along the given diagonal in the backward
				   search of the edit matrix. */
	private int fdiagoff, bdiagoff;
	private final DiffData diffData0;
	private final DiffData diffData1;
	private int cost;

  	private final ScriptBuilder theScript; // Standard ScriptBuilder.

	/********************************************************************************
	* Constructor
	* Prepare to find differences between two vectors.  Each element of
    * the vector is translated to an "equivalence number" based on
    * the result of <code>equals</code>.  The original Object vectors
    * are no longer needed for computing the differences.  They will
    * be needed again later to print the results of the comparison as
    * an edit script, if desired.
    *
	*@param aVct Vector of Objects to compare
	*@param bVct Vector of Objects to compare
	*/
  	public Diff(Vector aVct,Vector bVct) {
    	Hashtable equivalenceCodeTbl = new Hashtable(aVct.size() + bVct.size());
    	diffData0 = new DiffData(aVct,equivalenceCodeTbl);
    	diffData1 = new DiffData(bVct,equivalenceCodeTbl);
    	theScript = new ScriptBuilder();
    	equivalenceCodeTbl.clear();
  	}

    /********************************************************************************
  	* Get the results of comparison as an edit script.  The script
    * is described by a list of changes.  The standard ScriptBuilder
    * implementations provide for forward and reverse edit scripts.
    * Alternate implementations could, for instance, list common elements
    * instead of differences.
    * @return DiffChgs the head of a list of changes
   	*/
  	public DiffChgs diff()
  	{
		int diags=0;
    	/* Some lines are obviously insertions or deletions
       	because they don't match anything.  Detect them now,
       	and avoid even thinking about them in the main comparison algorithm.
  	    Discard lines from one file that have no matches in the other file.
   		*/
    	diffData0.discardConfusingLines(diffData1);
    	diffData1.discardConfusingLines(diffData0);

    	/* Now do the main comparison algorithm, considering just the
       	undiscarded lines.  */
    	xvec = diffData0.undiscarded;
    	yvec = diffData1.undiscarded;

    	diags = diffData0.nondiscardedLines + diffData1.nondiscardedLines + 3;
    	fdiag = new int[diags];
    	fdiagoff = diffData1.nondiscardedLines + 1;
    	bdiag = new int[diags];
    	bdiagoff = diffData1.nondiscardedLines + 1;

    	compareSeq(0, diffData0.nondiscardedLines, 0, diffData1.nondiscardedLines);
    	fdiag = null;
    	bdiag = null;

    	/* Modify the results slightly to make them prettier
        in cases where that can validly be done.  Adjust inserts/deletes of blank lines to join changes
     	as much as possible.
   		*/
		diffData0.shiftBoundaries(diffData1);
		diffData1.shiftBoundaries(diffData0);

    	// Get the results of comparison in the form of a chain of DiffChgs's -- an edit script.
    	//return theScript.buildScript(diffData0.changedFlag,  diffData0.bufferedLineCnt,
      	//	diffData1.changedFlag,  diffData1.bufferedLineCnt);
      	return theScript.buildScript(diffData0, diffData1);
  	}

    /********************************************************************************
    * When set to true, the comparison uses a heuristic to speed it up.
	* With this heuristic, for files with a constant small density
	* of changes, the algorithm is linear in the file size.
	*
    * @param b    boolean
    */
	public void setHeuristic(boolean b) {
		heuristic = b;
	}

    /********************************************************************************
    * When set to true, the algorithm returns a guaranteed minimal
	* set of changes.  This makes things slower, sometimes much slower.
	*
    * @param b    boolean
    */
    public void setNoDiscards(boolean b) {
		noDiscards = b;
	}

    /********************************************************************************
    * Find the midpoint of the shortest edit script for a specified
    * portion of the two files.
	*
    * We scan from the beginnings of the files, and simultaneously from the ends,
    * doing a breadth-first search through the space of edit-sequence.
    * When the two searches meet, we have found the midpoint of the shortest
    * edit sequence.
	*
    * The value returned is the number of the diagonal on which the midpoint lies.
    * The diagonal number equals the number of inserted lines minus the number
    * of deleted lines (counting only lines before the midpoint).
    * The edit cost is stored into COST; this is the total number of
    * lines inserted or deleted (counting only lines before the midpoint).
	*
    * This function assumes that the first lines of the specified portions
    * of the two files do not match, and likewise that the last lines do not
    * match.  The caller must trim matching lines from the beginning and end
    * of the portions it is going to specify.
	*
    * Note that if we return the "wrong" diagonal value, or if
    * the value of bdiag at that diagonal is "wrong",
    * the worst this can do is cause suboptimal diff output.
    * It cannot cause incorrect diff output.
    */
	private int diag(int xoff, int xlim, int yoff, int ylim)
	{
		final int[] fd = fdiag; // Give the compiler a chance.
		final int[] bd = bdiag; // Additional help for the compiler.
		final int[] xv = xvec;      // Still more help for the compiler.
		final int[] yv = yvec;      // And more and more . . .
		final int dmin = xoff - ylim;   // Minimum valid diagonal.
		final int dmax = xlim - yoff;   // Maximum valid diagonal.
		final int fmid = xoff - yoff;   // Center diagonal of top-down search.
		final int bmid = xlim - ylim;   // Center diagonal of bottom-up search.
		int fmin = fmid, fmax = fmid;   // Limits of top-down search.
		int bmin = bmid, bmax = bmid;   // Limits of bottom-up search.
		// True if southeast corner is on an odd diagonal with respect to the northwest.
		final boolean odd = (fmid - bmid & 1) != 0;
		int retValue = -1;
		int c = 1;  // jtest fix

		fd[fdiagoff + fmid] = xoff;
		bd[bdiagoff + bmid] = xlim;

		outerloop:
		//for (int c = 1;; ++c) { jtest flags this as an error
		while(true){
			int d;          // Active diagonal.
			boolean bigSnake = false;

			// Extend the top-down search by an edit step in each diagonal.
			if (fmin > dmin){
		  		fd[fdiagoff + --fmin - 1] = -1;
			} else {
		  		++fmin;
			}
			if (fmax < dmax){
		  		fd[fdiagoff + ++fmax + 1] = -1;
			} else {
		  		--fmax;
			}
			for (d = fmax; d >= fmin; d -= 2)  {
				int x, y, oldx, tlo = fd[fdiagoff + d - 1], thi = fd[fdiagoff + d + 1];

				if (tlo >= thi){
			  		x = tlo + 1;
				} else{
			  		x = thi;
				}
				oldx = x;
				y = x - d;
				while (x < xlim && y < ylim && xv[x] == yv[y]) {
			  		++x;
			  		++y;
				}
				if (x - oldx > BIG_SNAKE){
			  		bigSnake = true;
				}
				fd[fdiagoff + d] = x;
				if (odd && bmin <= d && d <= bmax && bd[bdiagoff + d] <= fd[fdiagoff + d])  {
					cost = 2 * c - 1;
					//return d;
					retValue = d;
					break outerloop;
			  	}
		  	}

			// Similar extend the bottom-up search.
			if (bmin > dmin) {
		  		bd[bdiagoff + --bmin - 1] = Integer.MAX_VALUE;
			} else{
		  		++bmin;
			}
			if (bmax < dmax){
		  		bd[bdiagoff + ++bmax + 1] = Integer.MAX_VALUE;
			} else {
		  		--bmax;
			}
			for (d = bmax; d >= bmin; d -= 2)  {
				int x, y, oldx, tlo = bd[bdiagoff + d - 1], thi = bd[bdiagoff + d + 1];
				if (tlo < thi) {
			  		x = tlo;
				} else {
			  		x = thi - 1;
				}
				oldx = x;
				y = x - d;
				while (x > xoff && y > yoff && xv[x - 1] == yv[y - 1]) {
			  		--x;
			  		--y;
				}
				if (oldx - x > BIG_SNAKE) {
			  		bigSnake = true;
				}
				bd[bdiagoff + d] = x;
				if (!odd && fmin <= d && d <= fmax && bd[bdiagoff + d] <= fd[fdiagoff + d])  {
					cost = 2 * c;
//					return d;
					retValue = d;
					break outerloop;
			  	}
		  	}

			/* Heuristic: check occasionally for a diagonal that has made
		   lots of progress compared with the edit distance.
		   If we have any such, find the one that has made the most
		   progress and return it as if it had succeeded.

		   With this heuristic, for files with a constant small density
		   of changes, the algorithm is linear in the file size.  */
			if (c > I_200 && bigSnake && heuristic)  {
				int best = 0;
				int bestpos = -1;

				for (d = fmax; d >= fmin; d -= 2)  {
					int dd = d - fmid;
					if ((fd[fdiagoff + d] - xoff)*2 - dd > I_12 * (c + (dd > 0 ? dd : -dd)))
					{
						if (fd[fdiagoff + d] * 2 - dd > best && fd[fdiagoff + d] - xoff > BIG_SNAKE
							&& fd[fdiagoff + d] - d - yoff > BIG_SNAKE)
						{
							int k;
							int x = fd[fdiagoff + d];

							/* We have a good enough best diagonal;
							   now insist that it end with a significant snake.  */
							for (k = 1; k <= BIG_SNAKE; k++) {
								if (xvec[x - k] != yvec[x - d - k]){
									break;
								}
							}

							if (k == I_21) {
								best = fd[fdiagoff + d] * 2 - dd;
								bestpos = d;
				  			}
				  		}
			  		}
			  	}
				if (best > 0)  {
					cost = 2 * c - 1;
//					return bestpos;
					retValue = bestpos;
					break outerloop;
			  	}

				best = 0;
				for (d = bmax; d >= bmin; d -= 2)  {
					int dd = d - bmid;
					if ((xlim - bd[bdiagoff + d])*2 + dd > I_12 * (c + (dd > 0 ? dd : -dd)))
			  		{
						if ((xlim - bd[bdiagoff + d]) * 2 + dd > best && xlim - bd[bdiagoff + d] > BIG_SNAKE
							&& ylim - (bd[bdiagoff + d] - d) > BIG_SNAKE)
				  		{
							/* We have a good enough best diagonal;
						   now insist that it end with a significant snake.  */
							int k;
							int x = bd[bdiagoff + d];

							for (k = 0; k < BIG_SNAKE; k++) {
				  				if (xvec[x + k] != yvec[x - d + k]){
									break;
								}
							}
							if (k == BIG_SNAKE) {
								best = (xlim - bd[bdiagoff + d]) * 2 + dd;
								bestpos = d;
				  			}
				  		}
			  		}
			  	}
				if (best > 0)  {
					cost = 2 * c - 1;
					//return bestpos;
					retValue = bestpos;
					break outerloop;
			  	}
		  	}
		  	++c;  // jtest fix
		}
		return retValue;
	}

    /***********************************************************************
  	* Compare in detail contiguous subsequences of the two files
    * which are known, as a whole, to match each other.
	*
    * The results are recorded in the vectors filevec[N].changedFlag, by
    * storing a 1 in the element for each line that is an insertion or deletion.
	*
    * The subsequence of file 0 is [XOFF, XLIM) and likewise for file 1.
	*
    * Note that XLIM, YLIM are exclusive bounds.
    * All line numbers are origin-0 and discarded lines are not counted.
    */
	private void compareSeq(int xoff, int xlim, int yoff, int ylim)
	{
    	// Slide down the bottom initial diagonal.
    	while (xoff < xlim && yoff < ylim && xvec[xoff] == yvec[yoff]) {
    		++xoff;
    		++yoff;
    	}
    	// Slide up the top initial diagonal.
    	while (xlim > xoff && ylim > yoff && xvec[xlim - 1] == yvec[ylim - 1]) {
      		--xlim;
      		--ylim;
    	}

    	// Handle simple cases.
    	if (xoff == xlim) {
      		while (yoff < ylim){
    			diffData1.changedFlag[1+diffData1.realindexes[yoff++]] = true;
			}
		} else if (yoff == ylim) {
      		while (xoff < xlim) {
    			diffData0.changedFlag[1+diffData0.realindexes[xoff++]] = true;
			}
		} else
      	{
    		// Find a point of correspondence in the middle of the files.
    		int d = diag(xoff, xlim, yoff, ylim);
//    		int f = fdiag[fdiagoff + d];
    		int b = bdiag[bdiagoff + d];

    		if (cost == 1)  {
        		/* This should be impossible, because it implies that
        		   one of the two subsequences is empty,
        		   and that case was handled above without calling `diag'.
        		   Let's verify that this is true.  */
        		throw new IllegalArgumentException("Empty subsequence");
      		} else {
        		// Use that point to split this problem into two subproblems.
        		compareSeq(xoff, b, yoff, b - d);
        		/* This used to use f instead of b,
        		   but that is incorrect!
        		   It is not necessarily the case that diagonal d
        		   has a snake from b to f.  */
        		compareSeq(b, xlim, b - d, ylim);
      		}
      	}
  	}

    /***********************************************************************
  	* Scan the arrays of which rows are inserted and deleted,
    * producing an edit script in forward order.
    *@param diffData00 DiffData with deletes from first vector to build script
    *@param diffData11 DiffData with inserts from second vector to build script
   	*@return a linked list of changes - or null
   	*/
  	private static class ScriptBuilder {
    	DiffChgs buildScript(DiffData diffData00, DiffData diffData11)
    	{
      		DiffChgs script = null;
      		int i0 = diffData00.bufferedLineCnt;  // number of rows in first Vector
      		int i1 = diffData11.bufferedLineCnt;  // number of rows in 2nd Vector
      		while (i0 >= 0 || i1 >= 0)
    		{
      			if (diffData00.changedFlag[i0] || // true for rows in first vector which do not match 2nd
      				diffData11.changedFlag[i1])   // true for rows in 2nd vector which do not match 1st
        		{
          			int line0 = i0, line1 = i1;
          			// Find # lines changed here in each file.
          			while (diffData00.changedFlag[i0]) {
          				--i0;
          			}
          			while (diffData11.changedFlag[i1]) {
          				--i1;
          			}

          			// Record this change.
          			script = new DiffChgs(i0, i1, line0 - i0, line1 - i1, script);
        		}

      			// We have reached lines in the two files that match each other.
      			i0--;
      			i1--;
    		}

      		return script;
    	}
  	}

    /********************************************************************************
  	* Data on one input being compared.
   	*/
  	private class DiffData
  	{
		private int bufferedLineCnt; // Number of elements (lines) in this data.

		/** Array, indexed by line number, containing an equivalence code for
		   each element of the Vector.  It is this array that is actually compared with that
		   of another Vector to generate differences. */
		private final int[]  equivs;

		/** Array, like the previous one except that
		   the elements for discarded lines have been squeezed out.  */
		private int[]    undiscarded;

		/** Array mapping virtual line numbers (not counting discarded lines)
		   to real ones (counting those lines).  Both are origin-0.  */
		private int[]    realindexes;

		private int nondiscardedLines;  // Total number of nondiscarded lines.

		/** Array, indexed by real origin-1 line number,
		   containing true for a line that is an insertion or a deletion.
		   The results of comparison are stored here.  */
		private boolean[]       changedFlag;

		private DiffData(Vector data,Hashtable equivalenceCodeTbl)
		{
			bufferedLineCnt = data.size();
			equivs = new int[bufferedLineCnt];
			undiscarded = new int[bufferedLineCnt];
			realindexes = new int[bufferedLineCnt];

			for (int i = 0; i < data.size(); ++i) {
				String str = data.elementAt(i).toString();
				Integer ir = (Integer)equivalenceCodeTbl.get(str);
				if (ir == null) {
					equivalenceCodeTbl.put(str,new Integer(equivs[i] = equivMax++));
				}
				else {
					equivs[i] = ir.intValue();
				}
			}
		}

    	/********************************************************************************
    	* Allocate changed array for the results of comparison.
    	*/
    	private void clear() {
    	  	/* Allocate a flag for each line of each file, saying whether that line
    	 	is an insertion or deletion.
    	 	Allocate an extra element, always zero, at each end of each vector.
    	   	*/
    	  	changedFlag = new boolean[bufferedLineCnt + 2];
    	}

    	/********************************************************************************
		* Return equivCount[I] as the number of lines in this file
		* that fall in equivalence class I.
		* @return the array of equivalence class counts.
		*/
		private int[] equivCount() {
			int[] equivCount = new int[equivMax];
			for (int i = 0; i < bufferedLineCnt; ++i){
				++equivCount[equivs[i]];
			}
			return equivCount;
		}

    	/********************************************************************************
		* Discard lines that have no matches in another file.
		*
		* A line which is discarded will not be considered by the actual
		* comparison algorithm; it will be as if that line were not in the file.
		* The file's `realindexes' table maps virtual line numbers
		* (which don't count the discarded lines) into real line numbers;
		* this is how the actual comparison algorithm produces results
		* that are comprehensible when the discarded lines are counted.
		*
		* When we discard a line, we also mark it as a deletion or insertion
		* so that it will be printed in the output.
		* @param other the other file
		*/
		private void discardConfusingLines(DiffData other) {
			final byte[] discarded;
			clear();
			// Set up table of which lines are going to be discarded.
			discarded = discardable(other.equivCount());

			/* Don't really discard the provisional lines except when they occur
		    in a run of discardables, with nonprovisionals at the beginning
		    and end.  */
			filterDiscards(discarded);

			// Actually discard the lines.
			discard(discarded);
		}

    	/********************************************************************************
		* Mark to be discarded each line that matches no line of another file.
		* If a line matches many lines, mark it as provisionally discardable.
		* @param otherCounts The count of each equivalence number for the other file.
		* @return 0=nondiscardable, 1=discardable or 2=provisionally discardable
		* for each line
		*/
		private byte[] discardable(final int[] otherCounts) {
			final byte[] discards = new byte[bufferedLineCnt];
			final int[] equivs = this.equivs;
			int many = 5;
			int tem = bufferedLineCnt / I_64;

			/* Multiply MANY by approximate square root of number of lines.
			That is the threshold for provisionally discardable lines.  */
			while ((tem = tem >> 2) > 0) {
				many *= 2;
			}

			for (int i = 0; i < bufferedLineCnt; i++) {
				int nmatch;
				if (equivs[i] == 0){
					continue;
				}
				nmatch = otherCounts[equivs[i]];
				if (nmatch == 0){
					discards[i] = 1;
				} else if (nmatch > many) {
					discards[i] = 2;
				}
			}
			return discards;
		}

    	/********************************************************************************
		* Don't really discard the provisional lines except when they occur
		* in a run of discardables, with nonprovisionals at the beginning
		* and end.
		*/
		private void filterDiscards(final byte[] discards) {
			int i=0;  // jtest fix
			//for (int i = 0; i < bufferedLineCnt; i++) { jtest flags this because i is changed in loop
			while(i < bufferedLineCnt) {  // jtest fix
				// Cancel provisional discards not in middle of run of discards.
				if (discards[i] == 2) {
					discards[i] = 0;
				} else if (discards[i] != 0) {
					// We have found a nonprovisional discard.
					int j;
					int length;
					int provisional = 0;

					/* Find end of this run of discardable lines.
					   Count how many are provisionally discardable.  */
					for (j = i; j < bufferedLineCnt; j++) {
						if (discards[j] == 0) {
							break;
						}
						if (discards[j] == 2) {
							++provisional;
						}
					}

					// Cancel provisional discards at end, and shrink the run.
					while (j > i && discards[j - 1] == 2) {
						discards[--j] = 0;
						--provisional;
					}

					/* Now we have the length of a run of discardable lines
					   whose first and last are not provisional.  */
					length = j - i;

					/* If 1/4 of the lines in the run are provisional,
					   cancel discarding of all provisional lines in the run.  */
					if (provisional * 4 > length) {
						while (j > i){
							if (discards[--j] == 2){
								discards[j] = 0;
							}
						}
					} else {
						int consec;
						int minimum = 1;
						int tem = length / 4;

						/* MINIMUM is approximate square root of LENGTH/4.
						   A subrun of two or more provisionals can stand
						   when LENGTH is at least 16.
						   A subrun of 4 or more can stand when LENGTH >= 64.  */
						while ((tem = tem >> 2) > 0) {
							minimum *= 2;
						}
						minimum++;

						/* Cancel any subrun of MINIMUM or more provisionals
						   within the larger run.  */
						j = 0; // jtest fix
						consec = 0;  // jtest fix
						while(j < length){ // jtest fix
						//for (j = 0, consec = 0; j < length; j++){ jtest flags this because j is modified in the loop
							if (discards[i + j] != 2) {
								consec = 0;
							} else if (minimum == ++consec){
								/* Back up to start of subrun, to cancel it all.  */
								j -= consec;
							} else if (minimum < consec){
								discards[i + j] = 0;
							}
							j++; // jtest fix
						}

						/* Scan from beginning of run
						   until we find 3 or more nonprovisionals in a row
						   or until the first nonprovisional at least 8 lines in.
						   Until that point, cancel any provisionals.  */
						for (j = 0, consec = 0; j < length; j++) {
							if (j >= 8 && discards[i + j] == 1){
								break;
							}
							if (discards[i + j] == 2) {
								consec = 0;
								discards[i + j] = 0;
							} else if (discards[i + j] == 0){
								consec = 0;
							}
							else {
								consec++;
							}
							if (consec == 3){
								break;
							}
						}

						// I advances to the last line of the run.
						i += length - 1;

						// Same thing, from end.
						for (j = 0, consec = 0; j < length; j++) {
							if (j >= 8 && discards[i - j] == 1){
								break;
							}
							if (discards[i - j] == 2) {
								consec = 0;
								discards[i - j] = 0;
							} else if (discards[i - j] == 0){
								consec = 0;
							}
							else {
								consec++;
							}
							if (consec == 3){
								break;
							}
						}
					}
				}
				i++; // jtest fix
			}
		}

    	/********************************************************************************
		* Actually discard the lines.
		* @param discards flags lines to be discarded
		*/
		private void discard(final byte[] discards)
		{
			int j = 0;
			for (int i = 0; i < bufferedLineCnt; ++i) {
				if (noDiscards || discards[i] == 0) {
					undiscarded[j] = equivs[i];
					realindexes[j++] = i;
				} else {
					changedFlag[1+i] = true;
				}
			}
			nondiscardedLines = j;
		}

    	/********************************************************************************
		* Adjust inserts/deletes of blank lines to join changes
		* as much as possible.
		*
		* We do something when a run of changed lines include a blank
		* line at one end and have an excluded blank line at the other.
		* We are free to choose which blank line is included.
		* `compareSeq' always chooses the one at the beginning,
		* but usually it is cleaner to consider the following blank line
		* to be the "Change".  The only exception is if the preceding blank line
		* would join this Change to other changes.
		* @param other the file being compared against
		*/
		private void shiftBoundaries(DiffData other)
		{
			int i = 0;
			int j = 0;
			int preceding = -1;
			int otherPreceding = -1;

			while (true) {
				int start, end, other_start;

				/* Scan forwards to find beginning of another run of changes.
				   Also keep track of the corresponding point in the other file.  */
				while (i < bufferedLineCnt && !changedFlag[1+i]) {
					while (other.changedFlag[1+j++]){
						/* Non-corresponding lines in the other file
						will count as the preceding batch of changes.  */
						otherPreceding = j;
					}
					i++;
				}

				if (i == bufferedLineCnt){
					break;
				}

				start = i;
				other_start = j;

				while (true)  {
					// Now find the end of this run of changes.
					while (i < bufferedLineCnt && changedFlag[1+i]) {
						i++;
					}
					end = i;

					/* If the first changed line matches the following unchanged one,
					and this run does not follow right after a previous run,
					and there are no lines deleted from the other file here,
					then classify the first changed line as unchanged
					and the following line as changed in its place.  */

					/* You might ask, how could this run follow right after another?
					Only because the previous run was shifted here.  */
					if (end != bufferedLineCnt && equivs[start] == equivs[end] && !other.changedFlag[1+j]
						&& end != bufferedLineCnt && !((preceding >= 0 && start == preceding)
						|| (otherPreceding >= 0 && other_start == otherPreceding)))
					{
						changedFlag[1+end++] = true;
						changedFlag[1+start++] = false;
						++i;
						/* Since one line-that-matches is now before this run
						   instead of after, we must advance in the other file
						   to keep in synch.  */
						++j;
					}
					else {
						break;
					}
				}

				preceding = i;
				otherPreceding = j;
			}
		}
  	}  // end class DiffData
}
