// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.diff;

import java.util.*;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

/**********************************************************************************
* This class takes 2 EntityLists (VEs) and will diff() them.  It first flattens each VE into
* a Vector with string representations, (a DiffEntity) for each entity/relator.
* The GNU Diff algo is applied to these Vectors and they are merged into one Vector with
* inserts and deletes marked based on Diff results
*
*/
// $Log: DiffVE.java,v $
// Revision 1.7  2015/02/04 18:47:08  stimpsow
// IN5947420 - prevent marking item as New if the only update was an added path to the item
//
// Revision 1.6  2011/03/21 20:22:44  wendy
// Allow 4 items at the same level
//
// Revision 1.5  2010/01/08 12:45:05  wendy
// make nls checks non static, allow override of attr checked
//
// Revision 1.4  2008/04/15 20:51:01  wendy
// Added check for changes in any language
//
// Revision 1.3  2008/03/20 20:28:10  wendy
// Keep changes made for abr performance test - more work needed if used
//
// Revision 1.2  2006/11/15 16:39:16  wendy
// Modify to support VE change for MN29794770
//
// Revision 1.1  2006/07/24 20:50:11  wendy
// Replacement for XML in change reports
//
//
public class DiffVE {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006, 2008  All Rights Reserved.";

	/** cvs revision number */
	public static final String VERSION = "$Revision: 1.7 $";
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

	private EIComparator eicomp = new EIComparator();
	private Vector priorVct;  // has DiffEntity representing entities and relators from prior time (time 1)
	private Vector currentVct;// has DiffEntity representing entities and relators from current time (time 2)
	private Hashtable veStepTbl; // has steps used in VE
	private StringBuffer debugSb = new StringBuffer();
	private boolean debug = false;
	private boolean checkAllNLS = false;  // original default
	private Set ignoreTypeAttrSet = null;

	/**
	 * Support suppressing checks on this attribute code for this entitytype
	 * @param entitytype
	 * @param attrCode
	 */
	public void setIngoredTypeAttr(String entitytype, String attrCode){
		if (ignoreTypeAttrSet==null){
			ignoreTypeAttrSet = new HashSet();
		}
		ignoreTypeAttrSet.add(entitytype+":"+attrCode);
	}
	/**
	 * @param entitytype
	 * @param attrCode
	 * @return
	 */
	public boolean isIgnored(String entitytype, String attrCode){
		boolean ignored = false;
		if (ignoreTypeAttrSet!=null){
			ignored = ignoreTypeAttrSet.contains(entitytype+":"+attrCode);
		}
		return ignored;
	}
	
	/** stop flag for 'fix' to ve mapping */
	public static final String STOP_CONDITION = "Stop";

    /***************************************
    * Constructor used for structure only
    *@param list EntityList
    *@param hshMap Hashtable with VE steps
    */
	public DiffVE(EntityList list, Hashtable hshMap)
	{
		this(list,hshMap,false);
	}
	/***************************************
	 * Constructor used for structure only
	 * @param list EntityList
	 * @param hshMap Hashtable with VE steps
	 * @param useDebug
	 */
	public DiffVE(EntityList list, Hashtable hshMap, boolean useDebug)
	{
		Hashtable histTbl = new Hashtable();
		// flatten the list
		EntityItem pei = list.getParentEntityGroup().getEntityItem(0);
		veStepTbl = hshMap;
		debug=useDebug;
		priorVct = pullSorted(pei, 0, pei, histTbl, 0,"Root","","",-1,-1);
		histTbl.clear();
		// use same vector, won't be used for differences
		currentVct = priorVct;
	}

    /***************************************
    * Constructor used for changes
    *@param list1 EntityList from prior time
    *@param list2 EntityList from current time
    *@param hshMap Hashtable with VE steps
    */
	public DiffVE(EntityList list1,EntityList list2, Hashtable hshMap)
	{
		this(list1,list2,hshMap,false);
	}

	/***************************************
	 * Constructor used for changes
	 * @param list1 EntityList from prior time
	 * @param list2 EntityList from current time
	 * @param hshMap Hashtable with VE steps
	 * @param useDebug
	 */
	public DiffVE(EntityList list1,EntityList list2, Hashtable hshMap, boolean useDebug)
	{
		veStepTbl = hshMap;
		debug=useDebug;
		flattenLists(list1,list2);
	}
    /***************************************
    * release memory
    */
	public void dereference() {
		veStepTbl = null;
		// deref here, not in jsp, merged vct won't have unchanged from both times
		for (int x=0; x<priorVct.size(); x++){
			DiffEntity de = (DiffEntity)priorVct.elementAt(x);
			de.dereference();
		}
		priorVct.clear();
		priorVct = null;
		for (int x=0; x<currentVct.size(); x++){
			DiffEntity de = (DiffEntity)currentVct.elementAt(x);
			de.dereference();
		}
		currentVct.clear();
		currentVct = null;
		debugSb = null;
		eicomp = null;
		
		if (ignoreTypeAttrSet!=null){
			ignoreTypeAttrSet.clear();
			ignoreTypeAttrSet = null;
		}
	}

    /***************************************
    * only used for debug
    *@return Vector of DiffEntity for time1
    */
	public Vector getPriorDiffVE()
	{
		return priorVct;
	}
    /***************************************
    * only used for debug
    *@return Vector of DiffEntity for time2
    */
	public Vector getCurrentDiffVE()
	{
		return currentVct;
	}
    /***************************************
    * only used for debug
    *@return String trace
    */
	public String getDebug() { return debugSb.toString();}
	
	/**
	 * add debug information
	 * @param msg
	 */
	protected void addDebug(String msg){
		debugSb.append(msg+NEWLINE);
	}
    /***************************************
    * turn on extra debug msg output
    *@param b boolean
    */
	public void setDebug(boolean b) { debug=b;}

    /***************************************
    * turn on checks for changes in any NLS
    *@param b boolean
    */
	public void setCheckAllNLS(boolean b) { checkAllNLS=b;}
	public boolean getCheckAllNLS() { return checkAllNLS;}

    /***************************************
    * run diff against vectors of entitys from flattened VEs for time1 and time2
    *@return Vector of DiffEntity with all changes from time1 and time2
    */
	public Vector diffVE()
	{
		Vector veChgVct = new Vector(priorVct.size());
		int lastpriorVctout=0;
		int lastcurrentVctout=0;
		int	last0 = 0;

    	Diff diff = new Diff(priorVct,currentVct);
    	// find changes
    	DiffChgs next = diff.diff(); // if no changes are found, next==null
    	DiffChgs chglist = next; // hold onto it for later deref

		while (next != null)
		{
			int first0 = next.getFirstDeletedRowNumber();
			int first1 = next.getFirstInsertedRowNumber();
			int last1 = next.getFirstInsertedRowNumber() + next.getInsertedCount();
			last0 = next.getFirstDeletedRowNumber() + next.getDeletedCount();

			// mark any entity that is unchanged before the first change
			if(lastpriorVctout<=first0){
				for (int i = lastpriorVctout; i <first0; i++){
					Diffable diffobj = (Diffable)priorVct.elementAt(i);
					diffobj.setNoChange((Diffable)currentVct.elementAt(lastcurrentVctout++));
					veChgVct.add(diffobj);
				}

				lastpriorVctout=last0;
			}

			// mark any entity that is deleted
			if (next.getDeletedCount() != 0){
				for (int i = first0; i <last0; i++){
					Diffable diffobj = (Diffable)priorVct.elementAt(i);
					diffobj.setDeleted();
					veChgVct.add(diffobj);
				}
			}
			// mark any entity that is inserted
			if (next.getInsertedCount() != 0){
				for (int i = first1; i < last1; i++){
					Diffable diffobj = (Diffable)currentVct.elementAt(i);
					diffobj.setAdded();
					veChgVct.add(diffobj);
				}
				lastcurrentVctout = last1;
			}

			next = next.getLink();
		}
		// mark any entity that is unchanged after the first change
		if(lastpriorVctout<=last0){
			for (int i = last0; i <priorVct.size(); i++){
				Diffable diffobj = (Diffable)priorVct.elementAt(i);
				diffobj.setNoChange((Diffable)currentVct.elementAt(lastcurrentVctout++));
				veChgVct.add(diffobj);
			}
		}

		if (chglist!=null) {
			chglist.dereference();
		}
		
		// look through both vectors for any mismarked items - if an entity is linked thru an additional
		// path it may be flagged as new because the path to it was new IN5947420
		veChgVct = cleanupMismarked(veChgVct);
		
		return veChgVct;
	}

	/**
	 * look for any mismarked items - if an entity is linked thru an additional
	 * path it may be flagged as new because the path to it was new
	 * IN5947420 
	 * @param veChgVct
	 */
	private Vector cleanupMismarked(Vector veChgVct) {
		Vector veCleanVct = new Vector();

		Vector veNewVct = new Vector();
		Hashtable delTbl = new Hashtable();
		Hashtable noChgTbl = new Hashtable();

		// look at each item for new, deleted or unchanged
		for (int i = 0; i <veChgVct.size(); i++){
			DiffEntity diffent = (DiffEntity)veChgVct.elementAt(i);
			if(diffent.isNew()){
				veNewVct.add(diffent);
			}else if(diffent.isDeleted()){
				delTbl.put(diffent.getKey(), diffent);
			}else{
				veCleanVct.add(diffent);
				noChgTbl.put(diffent.getKey(), diffent);
			}
		}
		
		for (int i = 0; i <veNewVct.size(); i++){
			DiffEntity diffent = (DiffEntity)veNewVct.elementAt(i);
			DiffEntity nodiffent = (DiffEntity)	noChgTbl.get(diffent.getKey());
			if(nodiffent !=null){
				if (debug){
					debugSb.append("cleanupMismarked() removing false new "+diffent+NEWLINE);
				}
				//the entity currently existed but on another path, it is not new
				//4:1:E:LSEOBUNDLE:1265448:LSEOBUNDLEAVAIL:7:U:New chgdAttrCnt:6 path:LSEOBUNDLEAVAIL7[U][R]:AVAIL8[U][E]:ANNAVAILA8[D][A]:ANNOUNCEMENT1265352[Root][E], 
				//4:1:E:LSEOBUNDLE:1265448:LSEOBUNDLEAVAIL:1265610:U:Unchanged chgdAttrCnt:0 path:LSEOBUNDLEAVAIL1265610[U][R]:AVAIL1267018[U][E]:ANNAVAILA1267018[D][A]:ANNOUNCEMENT1265352[Root][E], 
				diffent.dereference();
			}else {
				DiffEntity deldiffent = (DiffEntity) delTbl.get(diffent.getKey());
				if(deldiffent !=null){
					//this item was tagged as new and as deleted
					delTbl.remove(diffent.getKey()); // anything left in this table needs to be added to the return vct
					if (debug){
						debugSb.append("cleanupMismarked() merging deleted "+
								" "+deldiffent+" with new "+diffent+NEWLINE);
					}

					// was it flagged as deleted too? could happen if the path was different 2 steps away
					//6:2:E:PROJ:344:LSEOBUNDLEPROJA:344:D:New     chgdAttrCnt:1 path:LSEOBUNDLEPROJA344[D][A]:LSEOBUNDLE1265448[D][E]:LSEOBUNDLEAVAIL7[U][R]:AVAIL8[U][E]:ANNAVAILA8[D][A]:ANNOUNCEMENT1265352[Root][E], 
					//6:2:E:PROJ:344:LSEOBUNDLEPROJA:344:D:Deleted chgdAttrCnt:1 path:LSEOBUNDLEPROJA344[D][A]:LSEOBUNDLE1265448[D][E]:LSEOBUNDLEAVAIL1265610[U][R]:AVAIL1267018[U][E]:ANNAVAILA1267018[D][A]:ANNOUNCEMENT1265352[Root][E]
					diffent.mergeNoChange(deldiffent);
					veCleanVct.add(diffent);

					deldiffent.dereference();
				}else{
					//this was new and didnt match one tagged for delete
					veCleanVct.add(diffent);
				}
			}
		}
		
		// add any deleted items
		if(delTbl.size()>0){
			Iterator itr = delTbl.values().iterator();
			while(itr.hasNext()){
				DiffEntity diffent = (DiffEntity)itr.next();
				veCleanVct.add(diffent);
			}
		}

		noChgTbl.clear();
		delTbl.clear();
		veNewVct.clear();
		veChgVct.clear();
		
		return veCleanVct;
	}
	
    /***************************************
    * Get differences as XML
    *@param diffVct Vector of DiffEntitys
    *@param _profT1 Profile from prior time
    *@param _profT2 Profile from current time
    */
	public String getXML(Vector diffVct, Profile _profT1, Profile _profT2){
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE vechangepackage [<!ENTITY nbsp \"&#160;\">]>\n");
		sb.append("<vechangepackage startdate=\"" + _profT1.getValOn() + "\" enddate=\"" + _profT2.getValOn() + "\" submiter= \"" + _profT2.getEmailAddress() + "\" role=\"" + _profT2.getRoleCode() + "\">\n");
		int iStk = -1;
		int iEntityS = 0;
		int iEntityE = 0;

		for (int x=0; x<diffVct.size(); x++){
			DiffEntity de = (DiffEntity)diffVct.elementAt(x);
		//System.out.println("de["+x+"] "+de.toString());

			if (de.getDepth() < iStk) {
				for (int ii = iStk; ii >= de.getDepth(); ii--) {
					sb.append("</entity>\n");
					++iEntityE;
				}
			}
			iStk = de.getDepth();

			sb.append(de.getEntityAndAttributes());
			++iEntityS;
			//DiffVE will dereference DiffEntity
		}

		int iEnd = iEntityS - iEntityE;
		for (int ii = iEnd; ii > 0; ii--) {
			sb.append("</entity>\n");
		}

		sb.append("</vechangepackage>\n");
		return sb.toString();
	}
    /***************************************
    * convert lists in to a flattened vector representation
    *@param priorList EntityList from prior time
    *@param currentList EntityList from current time
    */
	private void flattenLists(EntityList priorList,EntityList currentList)
	{
		Hashtable histTbl = new Hashtable();
        EntityItem pei = priorList.getParentEntityGroup().getEntityItem(0);
		priorVct = pullSorted(pei, 0, pei, histTbl, 0,"Root","","",-1,-1);
		histTbl.clear();

		pei = currentList.getParentEntityGroup().getEntityItem(0);
		currentVct = pullSorted(pei, 0, pei, histTbl, 0,"Root","","",-1,-1);
		histTbl.clear();
	}

    /***************************************
    * check to see if this should recurse or not
    *@param resultVct Vector built up with DiffEntity based on mapping
    *@param stopCondition String value from mapping
    *@param item EntityItem
    *@param level int
    *@param parent EntityItem  parent
    *@param historyTbl Hashtable  prevent loops and duplicate output for the same entity:path
    *@param depth int
    *@param direction String
    *@param path String  	build string of entities to root
    *@param plevel int level used for parent DiffEntity
    */
	private void checkStopCondition(Vector resultVct, String stopCondition,
		EntityItem item, int level,EntityItem parent, Hashtable historyTbl,int depth,
		String direction, String path, String indent, int plevel)
	{
		// ve mapping isn't correct when a relator to relator is used
		// WWSEO-->WWSEOPRODSTRUCT-->PRODSTRUCT<--FEATURE will also get PRODSTRUCT-->MODEL
		// the MODEL mapping will be wrong so add the MODEL but don't go any farther
		// jsp will modify the mapping before calling this
		if (stopCondition.equals(STOP_CONDITION)){
			String stopPath = item.getKey()+"["+direction+"]["+
				(item.getEntityGroup().isRelator()?"R":item.getEntityGroup().isAssoc()?"A":"E")+"]"+path;
			debugSb.append(indent+"   "+item.getKey()+" has STOP condition!"+NEWLINE);
			historyTbl.put(stopPath, "hi");
			resultVct.add(new DiffEntity(this,item, parent,depth, level, direction,path));
		}else{
			resultVct.addAll(pullSorted(item, level, parent, historyTbl,
				depth, direction,path,indent+"  ",level,plevel));
		}
	}

    /***************************************
    * convert lists in to a flattened vector representation
    * found problem with LSEO1 root and duplicate entries for
    * PRODSTRUCT35781[U][R]MODEL84638[U][E]:MODELWWSEO1[U][R]:WWSEO1[U][E]:WWSEOLSEO1[U][R]:LSEO1[U][E]
    * because both MODEL and PRODSTRUCT had level 1 and level 2 defined.
    * added path to the history to prevent duplicates
 	*	-- MODEL84638 level+1 uplink finds PRODSTRUCT35781 and creates
 	*		PRODSTRUCT35781[U][R]MODEL84638[U][E]:MODELWWSEO1[U][R]:WWSEO1[U][E]:WWSEOLSEO1[U][R]:LSEO1[U][E]:
	*	-- MODEL84638 current level uplink finds PRODSTRUCT35781 and tries to create the exact same path
	* causing duplicates of the information
	*
	* an entity, the relator and then next entity/relator will have the same level.  this level is the same
	* as the VE definition  LSEO is root
	* RPTLSEOSRD  LSEOPRODSTRUCT          D   0
	* RPTLSEOSRD  WWSEOLSEO               U   0
	* RPTLSEOSRD  MODELWWSEO              U   1
	* RPTLSEOSRD  PRODSTRUCT              U   1
	* RPTLSEOSRD  WWSEOPRODSTRUCT         D   1
	* RPTLSEOSRD  PRODSTRUCT              U   2
    *@param eitem EntityItem
    *@param level int
    *@param eParent EntityItem  parent
    *@param historyTbl Hashtable  prevent loops and duplicate output for the same entity:path
    *@param depth int
    *@param direction String
    *@param path String  	build string of entities to root
    *@param plevel int level used for parent DiffEntity
    *@param gplevel int level used for grandparent DiffEntity
    *@return Vector of DiffEntity
    */
	private Vector pullSorted(EntityItem eitem, int level, EntityItem eParent, Hashtable historyTbl, int depth,
								String direction, String path, String indent,
								int plevel, int gplevel)
	{
		Vector resultVct = new Vector();
		String thisPath = eitem.getKey()+"["+direction+"]["+
			(eitem.getEntityGroup().isRelator()?"R":eitem.getEntityGroup().isAssoc()?"A":"E")+"]"+path;

		if (debug){
			debugSb.append(indent+"pullSorted() entered "+eitem.getKey()+" lvl: "+level+" plvl: "+plevel+" gplvl: "+gplevel+" depth: "+depth+NEWLINE);
		}

		// prevent duplicates of the same entity and same path to it, can happen when an entity and its child
		// have more than one of the same levels
		// method entered for MODEL-1 on a call from MODELWWSEO-1, MODEL then calls PRODSTRUCT-2 and traverses
		// that path and then tries to do the same PRODSTRUCT-1 path
		// entity-level:relator-level...
		// LSEO-0:WWSEOLSEO-0:WWSEO-0,1:MODELWWSEO-1:MODEL-1,2:PRODSTRUCT-1,2
		//                    WWSEO-0,1:WWSEOPRODSTRUCT-1:PRODSTRUCT-1,2
		// this should not happen any more, i added a check for level, parentlevel and grandparent level
		// if all of these are the same, then dont' try to traverse this level any more
		// an entityitem's level is the same as it's uplink AND downlink entityitem's level when constructed
		// in the EntityList, the next item in the hierarchy will not have the same level!
		// unfortunately this is not true in all cases - LSEO as root - if you get to a MODEL thru a PRODSTRUCT and then
		// go from the MODEL to AVAIL, parent and gparent have same level
		//5:2:E:FEATURE:27607:PRODSTRUCT:140637:U, 
		//5:2:E:MODEL:85799:PRODSTRUCT:140637:D, 
		//6:2:R:MODELAVAIL:1269374:MODEL:85799:D, 
		//7:2:E:AVAIL:1267485:MODELAVAIL:1269374:D, see path output below
		if (historyTbl.containsKey(thisPath)){
			debugSb.append(indent+"pullSorted() Already created "+eitem.getKey()+" path: "+thisPath+NEWLINE);
		}
		else{
			historyTbl.put(thisPath, "hi");

			resultVct.add(new DiffEntity(this,eitem, eParent,depth, level, direction,path));
			if (debug){
				int[]lvlArray =  eitem.getLevelsAsIntArray();
				debugSb.append(indent+" "+eitem.getKey()+" has levels:");
				for (int i=0; i<lvlArray.length;i++){
					debugSb.append(" "+lvlArray[i]);
				}
				debugSb.append(NEWLINE);
			}
			if (historyTbl.containsKey(eitem.getKey()+level+eParent.getKey())) {
				// this can happen if an entity is linked more than once, need to add parentkey because same entity
				// may be linked to same parent but thru different relators
				// this allows SWFEATURE12068 to be added to the list when it is linked to WWSEO1 thru WWSEOSWPRODSTRUCT8
				// and WWSEOSWPRODSTRUCT7, without this parentkey, the WWSEOSWPRODSTRUCT were added but not the SWFEATURE
				// Do nothing
				debugSb.append(indent+"pullSorted() Already did links for "+eitem.getKey()+" level: "+
					level+" parentkey: "+eParent.getKey()+" path: "+thisPath+NEWLINE);
			} else {
				Vector eiVct;
				StringBuffer uppath = new StringBuffer(eitem.getKey()+"[");
				StringBuffer dnpath = new StringBuffer(eitem.getKey()+"[");
				historyTbl.put(eitem.getKey() + level+eParent.getKey(), "hi");

				if(direction.equals("Root")){
					uppath.append(direction);
					dnpath.append(direction);
				}else{
					uppath.append("U");
					dnpath.append("D");
				}
				uppath.append("]["+(eitem.getEntityGroup().isRelator()?"R":eitem.getEntityGroup().isAssoc()?"A":"E")+"]");
				dnpath.append("]["+(eitem.getEntityGroup().isRelator()?"R":eitem.getEntityGroup().isAssoc()?"A":"E")+"]");
				if (path.length()>0){
					uppath.append(":"+path);
					dnpath.append(":"+path);
				}

				// sort the uplinks and downlinks so output is in sortorder
				eiVct = eitem.getUpLink();
				Collections.sort(eiVct, eicomp);
				eiVct = eitem.getDownLink();
				Collections.sort(eiVct, eicomp);

				// check for higher level, that is this entity has more up or dn entries in the VE (and links to items)
				if (eitem.getLevel(level + 1)) {
					if (debug){
						debugSb.append(indent+" "+eitem.getKey()+" has level+1"+NEWLINE);
					}

					// look at uplinks for higher level
					eiVct = eitem.getUpLink();
					for (int ii = 0; ii < eiVct.size(); ii++) {
						EntityItem eiUp = (EntityItem) eiVct.elementAt(ii);
						if (debug){
							debugSb.append(indent+" "+eitem.getKey()+" lvl+1 upitem "+eiUp.getKey()+NEWLINE);
						}
						if (eiUp.getLevel(level + 1) && veStepTbl.containsKey( (level + 1) + eiUp.getEntityType() + "U") &&
							!eiUp.getKey().equals(eParent.getKey()))
						{
							String stopCondition = (String)veStepTbl.get((level + 1) + eiUp.getEntityType() + "U");
							checkStopCondition(resultVct, stopCondition, eiUp, level+1,eitem, historyTbl,
								depth+1,"U", uppath.toString(), indent, plevel);

							//resultVct.addAll(pullSorted(eiUp, level + 1, eitem, historyTbl,
							//	depth + 1, "U",uppath.toString(),indent+"  ",level+1,plevel));
						}
					}
					// look at downlinks for higher level
					eiVct = eitem.getDownLink();
					for (int ii = 0; ii < eiVct.size(); ii++) {
						EntityItem eiDown = (EntityItem) eiVct.elementAt(ii);
						if (debug){
							debugSb.append(indent+" "+eitem.getKey()+" lvl+1 dnitem "+eiDown.getKey()+NEWLINE);
						}
						if (eiDown.getLevel(level + 1) && veStepTbl.containsKey( (level + 1) + eiDown.getEntityType() + "D") &&
							!eiDown.getKey().equals(eParent.getKey()))
						{
							String stopCondition = (String)veStepTbl.get( (level + 1) + eiDown.getEntityType() + "D");
							checkStopCondition(resultVct, stopCondition, eiDown, level+1,eitem, historyTbl,
								depth+1,"D", dnpath.toString(), indent, plevel);
							//resultVct.addAll(pullSorted(eiDown, level + 1, eitem, historyTbl,
							//	depth + 1, "D",dnpath.toString(),indent+"  ",level+1,plevel));
						}
					}
				}// end level+1 check

				// look at links at this level, this entity is part of VE def because it was checked before calling pull()
				// look at uplinks and downlinks at this level
				eiVct = eitem.getUpLink();
				for (int ii = 0; ii < eiVct.size(); ii++) {
					EntityItem eiUp = (EntityItem)  eiVct.elementAt(ii);
					if (debug){
						debugSb.append(indent+" "+eitem.getKey()+" lvl:"+level+" upitem "+eiUp.getKey()+NEWLINE);
					}
					if (eiUp.getLevel(level) && veStepTbl.containsKey(level + eiUp.getEntityType() + "U") &&
						!eiUp.getKey().equals(eParent.getKey())) {
						if(level==plevel&& plevel==gplevel){// all 3 match so don't continue
							// stop it to prevent traveling too far - path history should prevent dupes
							debugSb.append(indent+"WARNING: "+eitem.getKey()+" has parent and grandparent at same level, building Ulevel: "+level+NEWLINE);
						}//else
						//some valid data conditions will have all 3 match now
						{
							String stopCondition = (String)veStepTbl.get(level+ eiUp.getEntityType() + "U");
							checkStopCondition(resultVct, stopCondition, eiUp, level,eitem, historyTbl,
								depth+1,"U", uppath.toString(), indent, plevel);

							//resultVct.addAll(pullSorted(eiUp, level, eitem, historyTbl,
							//	depth + 1, "U",uppath.toString(),indent+"  ",level,plevel));
						}
					}
				}

				eiVct = eitem.getDownLink();
				for (int ii = 0; ii < eiVct.size(); ii++) {
					EntityItem eiDown = (EntityItem) eiVct.elementAt(ii);
					if (debug){
						debugSb.append(indent+" "+eitem.getKey()+" lvl:"+level+" dnitem "+eiDown.getKey()+NEWLINE);
					}
					if (eiDown.getLevel(level) && veStepTbl.containsKey(level + eiDown.getEntityType() + "D") &&
						!eiDown.getKey().equals(eParent.getKey())) {
						if(level==plevel&& plevel==gplevel){// all 3 match so don't continue
							// stop it to prevent traveling too far - path history should prevent dupes
							debugSb.append(indent+"WARNING: "+eitem.getKey()+" has parent and grandparent at same level, building Dlevel: "+level+NEWLINE);
						}//else
						// some valid data conditions will have all 3 match now
						{
							String stopCondition = (String)veStepTbl.get(level+ eiDown.getEntityType() + "D");
							checkStopCondition(resultVct, stopCondition, eiDown, level,eitem, historyTbl,
								depth+1,"D", dnpath.toString(), indent, plevel);

							//resultVct.addAll(pullSorted(eiDown, level, eitem, historyTbl,
							//	depth + 1, "D",dnpath.toString(),indent+"  ",level, plevel));
						}
					}
				}
			}

		}
		if (debug){
			debugSb.append(indent+"pullSorted() exit "+eitem.getKey()+NEWLINE);
		}

		return resultVct;
	}
/*
problem data when ADSLSEO and ADSLSEO2 are merged, MODELAVAIL must recurse and was stopping.
pullSorted() entered LSEO330683 lvl: 0 plvl: -1 gplvl: -1 depth: 0
 LSEO330683 has levels: 0
 LSEO330683 lvl:0 upitem WWSEOLSEO356564
  pullSorted() entered WWSEOLSEO356564 lvl: 0 plvl: 0 gplvl: -1 depth: 1
   WWSEOLSEO356564 has levels: 0
   WWSEOLSEO356564 lvl:0 upitem WWSEO45699
    pullSorted() entered WWSEO45699 lvl: 0 plvl: 0 gplvl: 0 depth: 2
     WWSEO45699 has levels: 0 1
     WWSEO45699 has level+1
     WWSEO45699 lvl+1 upitem MODELWWSEO45699
      pullSorted() entered MODELWWSEO45699 lvl: 1 plvl: 1 gplvl: 0 depth: 3
       MODELWWSEO45699 has levels: 1
       MODELWWSEO45699 lvl:1 upitem MODEL85799
       MODELWWSEO45699 lvl:1 dnitem WWSEO45699
      pullSorted() exit MODELWWSEO45699
     WWSEO45699 lvl+1 dnitem WWSEOPRODSTRUCT1269421
      pullSorted() entered WWSEOPRODSTRUCT1269421 lvl: 1 plvl: 1 gplvl: 0 depth: 3
       WWSEOPRODSTRUCT1269421 has levels: 1
       WWSEOPRODSTRUCT1269421 lvl:1 upitem WWSEO45699
       WWSEOPRODSTRUCT1269421 lvl:1 dnitem PRODSTRUCT140637
        pullSorted() entered PRODSTRUCT140637 lvl: 1 plvl: 1 gplvl: 1 depth: 4
         PRODSTRUCT140637 has levels: 1 2
         PRODSTRUCT140637 has level+1
         PRODSTRUCT140637 lvl+1 upitem WWSEOPRODSTRUCT1269421
         PRODSTRUCT140637 lvl+1 upitem FEATURE27607
          pullSorted() entered FEATURE27607 lvl: 2 plvl: 2 gplvl: 1 depth: 5
           FEATURE27607 has levels: 2
           FEATURE27607 lvl:2 dnitem PRODSTRUCT140637
          pullSorted() exit FEATURE27607
         PRODSTRUCT140637 lvl+1 dnitem MODEL85799
          pullSorted() entered MODEL85799 lvl: 2 plvl: 2 gplvl: 1 depth: 5
           MODEL85799 has levels: 2
           MODEL85799 lvl:2 upitem PRODSTRUCT140637
           MODEL85799 lvl:2 dnitem MODELAVAIL1269374
            pullSorted() entered MODELAVAIL1269374 lvl: 2 plvl: 2 gplvl: 2 depth: 6
             MODELAVAIL1269374 has levels: 2
             MODELAVAIL1269374 lvl:2 upitem MODEL85799
             MODELAVAIL1269374 lvl:2 dnitem AVAIL1267485
             MODELAVAIL1269374 has parent and grandparent at same level, STOP building level: 2
              pullSorted() entered AVAIL1267485 lvl: 2 plvl: 2 gplvl: 2 depth: 7
               AVAIL1267485 has levels: 2 3
               AVAIL1267485 has level+1
               AVAIL1267485 lvl+1 upitem MODELAVAIL1269374
               AVAIL1267485 lvl+1 dnitem AVAILANNA1265390
                pullSorted() entered AVAILANNA1265390 lvl: 3 plvl: 3 gplvl: 2 depth: 8
                 AVAILANNA1265390 has levels: 3
                 AVAILANNA1265390 lvl:3 upitem AVAIL1267485
                 AVAILANNA1265390 lvl:3 dnitem ANNOUNCEMENT1265390
                  pullSorted() entered ANNOUNCEMENT1265390 lvl: 3 plvl: 3 gplvl: 3 depth: 9
                   ANNOUNCEMENT1265390 has levels: 3
                   ANNOUNCEMENT1265390 lvl:3 upitem AVAILANNA1265390
                  pullSorted() exit ANNOUNCEMENT1265390
                pullSorted() exit AVAILANNA1265390
               AVAIL1267485 lvl+1 dnitem AVAILSLEORGA2
                pullSorted() entered AVAILSLEORGA2 lvl: 3 plvl: 3 gplvl: 2 depth: 8
                 AVAILSLEORGA2 has levels: 3
                 AVAILSLEORGA2 lvl:3 upitem AVAIL1267485
                 AVAILSLEORGA2 lvl:3 dnitem SLEORGNPLNTCODE2
                  pullSorted() entered SLEORGNPLNTCODE2 lvl: 3 plvl: 3 gplvl: 3 depth: 9
                   SLEORGNPLNTCODE2 has levels: 3
                   SLEORGNPLNTCODE2 lvl:3 upitem AVAILSLEORGA2
                  pullSorted() exit SLEORGNPLNTCODE2
                pullSorted() exit AVAILSLEORGA2
               AVAIL1267485 lvl+1 dnitem AVAILSLEORGA6
                pullSorted() entered AVAILSLEORGA6 lvl: 3 plvl: 3 gplvl: 2 depth: 8
                 AVAILSLEORGA6 has levels: 3
                 AVAILSLEORGA6 lvl:3 upitem AVAIL1267485
                 AVAILSLEORGA6 lvl:3 dnitem SLEORGNPLNTCODE6
                  pullSorted() entered SLEORGNPLNTCODE6 lvl: 3 plvl: 3 gplvl: 3 depth: 9
                   SLEORGNPLNTCODE6 has levels: 3
                   SLEORGNPLNTCODE6 lvl:3 upitem AVAILSLEORGA6
                  pullSorted() exit SLEORGNPLNTCODE6
                pullSorted() exit AVAILSLEORGA6
               AVAIL1267485 lvl+1 dnitem AVAILSLEORGA7
                pullSorted() entered AVAILSLEORGA7 lvl: 3 plvl: 3 gplvl: 2 depth: 8
                 AVAILSLEORGA7 has levels: 3
                 AVAILSLEORGA7 lvl:3 upitem AVAIL1267485
                 AVAILSLEORGA7 lvl:3 dnitem SLEORGNPLNTCODE7
                  pullSorted() entered SLEORGNPLNTCODE7 lvl: 3 plvl: 3 gplvl: 3 depth: 9
                   SLEORGNPLNTCODE7 has levels: 3
                   SLEORGNPLNTCODE7 lvl:3 upitem AVAILSLEORGA7
                  pullSorted() exit SLEORGNPLNTCODE7
                pullSorted() exit AVAILSLEORGA7
               AVAIL1267485 lvl+1 dnitem AVAILSLEORGA8
                pullSorted() entered AVAILSLEORGA8 lvl: 3 plvl: 3 gplvl: 2 depth: 8
                 AVAILSLEORGA8 has levels: 3
                 AVAILSLEORGA8 lvl:3 upitem AVAIL1267485
                 AVAILSLEORGA8 lvl:3 dnitem SLEORGNPLNTCODE8
                  pullSorted() entered SLEORGNPLNTCODE8 lvl: 3 plvl: 3 gplvl: 3 depth: 9
                   SLEORGNPLNTCODE8 has levels: 3
                   SLEORGNPLNTCODE8 lvl:3 upitem AVAILSLEORGA8
                  pullSorted() exit SLEORGNPLNTCODE8
                pullSorted() exit AVAILSLEORGA8
               AVAIL1267485 lvl:2 upitem MODELAVAIL1269374
               AVAIL1267485 lvl:2 dnitem AVAILANNA1265390
               AVAIL1267485 lvl:2 dnitem AVAILSLEORGA2
               AVAIL1267485 lvl:2 dnitem AVAILSLEORGA6
               AVAIL1267485 lvl:2 dnitem AVAILSLEORGA7
               AVAIL1267485 lvl:2 dnitem AVAILSLEORGA8
              pullSorted() exit AVAIL1267485
            pullSorted() exit MODELAVAIL1269374
           MODEL85799 lvl:2 dnitem MODELWWSEO45699
          pullSorted() exit MODEL85799
         PRODSTRUCT140637 lvl:1 upitem WWSEOPRODSTRUCT1269421
         PRODSTRUCT140637 lvl:1 upitem FEATURE27607
         PRODSTRUCT140637 lvl:1 dnitem MODEL85799
        pullSorted() exit PRODSTRUCT140637
      pullSorted() exit WWSEOPRODSTRUCT1269421
     WWSEO45699 lvl+1 dnitem WWSEOLSEO356564

 */
	// used to control display order
	private static class EIComparator implements java.util.Comparator
	{
		public int compare(Object o1, Object o2) {
			String name1 = ((EntityItem) o1).getEntityGroup().getLongDescription();
			String name2 = ((EntityItem) o2).getEntityGroup().getLongDescription();
			return name1.compareTo(name2);
		}
	}
}

