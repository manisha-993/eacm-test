//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2001, 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

//$Log: EntityItem.java,v $
//Revision 1.587  2013/05/08 18:12:39  wendy
//refresh restrictions after save
//
//Revision 1.586  2013/05/07 18:12:16  wendy
//another perf update for large amt of data
//
//Revision 1.585  2013/05/01 18:34:08  wendy
//perf updates for large amt of data
//
//Revision 1.584  2012/04/16 16:33:28  wendy
//fillcopy/fillpaste perf updates
//
//Revision 1.583  2011/10/05 00:11:25  wendy
//add check for specific attribute change
//
//Revision 1.582  2011/03/20 01:44:54  wendy
//add key to debug msgs and make reassign public

//Revision 1.581  2011/02/05 03:07:34  wendy
//add method to check stack for attr chgs only

//Revision 1.580  2011/01/19 18:48:14  wendy
//prevent stack overflow if editrelator is specified for relator-relator-entity

//Revision 1.579  2010/10/18 11:53:14  wendy
//make dereference public

//Revision 1.578  2010/07/12 18:13:26  wendy
//add to debug msg

//Revision 1.577  2009/08/26 17:56:17  wendy
//SR5 updates

//Revision 1.576  2009/07/31 12:43:33  wendy
//Replace attr instead of nulling uilist

//Revision 1.575  2009/07/25 18:38:13  wendy
//Make sure a 'new' attribute object is referenced

//Revision 1.574  2009/05/28 14:05:47  wendy
//Refresh rules when lock changes

//Revision 1.573  2009/05/11 15:31:59  wendy
//Support dereference for memory release

//Revision 1.572  2009/05/07 16:05:07  wendy
//prevent null pointer if entitylist is null in getrowlist()

//Revision 1.571  2009/04/15 20:15:39  wendy
//Maintain a list for lookup to prevent returning the wrong EANObject

//Revision 1.570  2009/03/05 22:12:42  wendy
//MN38666284 - CQ00022911:Creating elements from search not linking back to Workgroup

//Revision 1.569  2009/02/18 17:25:34  wendy
//MN38316169 : was picking wrong entity

//Revision 1.568  2008/10/09 17:12:41  wendy
//CQ16205 for MN36915802 and MN36915859 convert the mismatched attribute

//Revision 1.567  2008/10/06 14:56:42  wendy
//CQ16205 for MN36915802 and MN36915859 Class cast exception

//Revision 1.566  2008/04/29 19:27:55  wendy
//MN35270066 VEEdit rewrite

//Revision 1.565  2008/02/21 18:52:24  wendy
//Added method to get history without going to child

//Revision 1.564  2008/02/07 22:14:22  wendy
//Fix null ptr

//Revision 1.563  2008/01/29 22:18:20  wendy
//Corrected put() when finding entity where type was same on both ends of relator

//Revision 1.562  2007/11/07 14:42:41  wendy
//MN33607291 error when parent and child are same entitytypes MODEL:MODELREL:MODEL

//Revision 1.561  2007/08/21 00:16:53  wendy
//Add chk if rs != null before close()

//Revision 1.560  2007/08/08 17:34:51  wendy
//Correct domain info msg

//Revision 1.559  2007/08/03 11:25:44  wendy
//RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs

//Revision 1.558  2007/04/27 14:46:21  wendy
//Prevent null ptr getting attribute in getEANObject()

//Revision 1.557  2007/04/24 18:16:26  wendy
//Added support for VEEdit delete

//Revision 1.556  2007/04/11 19:21:55  wendy
//Added some logging for JUI

//Revision 1.555  2007/04/11 16:53:51  wendy
//TIR6YZSJJ more changes needed for VEEdit

//Revision 1.554  2007/04/10 18:05:57  wendy
//TIR6YZSJJ default values not showing up in VEEDIT

//Revision 1.553  2007/04/09 19:24:30  wendy
//TIR6Z8M6Q getEANObject() was picking the wrong object when a heritage key was used

//Revision 1.552  2007/04/06 11:55:55  wendy
//TIR6YZSXM veedit was deactivating some attributes for classified entity types

//Revision 1.551  2007/03/30 02:40:15  tony
//*** empty log message ***

//Revision 1.550  2007/02/27 03:57:29  tony
//Phase2 of fix

//Revision 1.549  2007/02/19 22:10:35  tony
//Fixed VEEdit Stuff Maybe

//Revision 1.548  2006/12/07 19:28:14  tony
//29829301

//Revision 1.547  2006/06/16 06:41:08  dave
//more test

//Revision 1.545  2006/06/16 06:04:49  dave
//more isolation

//Revision 1.544  2006/06/16 05:51:15  dave
//more isolation on commit

//Revision 1.543  2006/06/16 05:37:52  dave
//more isolation for rmileak

//Revision 1.542  2006/06/16 05:06:31  dave
//testing rmi memory

//Revision 1.541  2006/06/15 22:12:17  dave
//backing out trace

//Revision 1.540  2006/06/15 21:22:01  dave
//mre trace

//Revision 1.539  2006/06/15 20:59:57  dave
//tracing

//Revision 1.538  2006/05/10 15:37:02  tony
//cr 0428066810

//Revision 1.537  2006/05/05 22:55:01  joan
//change to public

//Revision 1.536  2006/04/28 14:24:01  tony
//fixed null pointer

//Revision 1.535  2006/04/26 21:43:44  tony
//CR093005678
//CR0930055856
//Added VALIDATIONRULE

//Revision 1.534  2006/04/10 22:32:17  tony
//added testing statements

//Revision 1.533  2006/03/21 18:50:57  tony
//6MV4U7 wrapup

//Revision 1.532  2006/03/12 03:32:35  dave
//working unicode trim

//Revision 1.531  2006/03/12 03:20:25  dave
//tactical trim

//Revision 1.530  2006/03/12 03:15:51  dave
//<No Comment Entered>

//Revision 1.529  2006/03/01 07:35:23  dave
//formating and fixing array exception

//Revision 1.528  2006/02/20 21:39:46  joan
//clean up System.out.println

//Revision 1.527  2006/02/20 19:23:50  tony
//6LY42Z

//Revision 1.526  2006/02/17 17:48:21  tony
//6LY42Z removed output statements

//Revision 1.525  2006/02/17 17:46:37  tony
//6LY42Z

//Revision 1.524  2006/01/12 23:14:19  joan
//work on CR0817052746

//Revision 1.523  2006/01/05 21:30:30  tony
//VEEdit BUI functionality enhancement

//Revision 1.522  2005/12/16 21:10:36  gregg
//setting valfrom on attributes

//Revision 1.521  2005/12/15 15:32:32  tony
//MN26341495

//Revision 1.520  2005/11/15 16:53:04  tony
//fixed duplicate of VEEdit

//Revision 1.519  2005/11/15 15:50:23  tony
//logic repaired

//Revision 1.518  2005/11/14 22:07:36  tony
//fixed logic

//Revision 1.517  2005/11/14 15:38:36  tony
//cleaned code

//Revision 1.516  2005/11/14 15:36:47  tony
//cleaned-up code

//Revision 1.515  2005/11/14 15:16:45  tony
//VEEdit_Iteration3
//PRODSTRUCT Functionality.

//Revision 1.514  2005/11/10 21:25:16  tony
//VEEdit_Iteration3

//Revision 1.513  2005/11/08 15:42:02  tony
//removed test statement

//Revision 1.512  2005/11/07 21:40:12  tony
//improved logic for VEEdit Iteration2

//Revision 1.511  2005/11/07 19:06:25  tony
//fixed logic

//Revision 1.510  2005/11/04 23:02:17  tony
//VEEdit_Iteration2 tuning

//Revision 1.509  2005/11/04 16:50:12  tony
//VEEdit_Iteration2

//Revision 1.508  2005/11/04 14:52:10  tony
//VEEdit_Iteration2
//updated VEEdit logic to meet new requirements.

//Revision 1.507  2005/10/07 17:33:59  tony
//fixed null pointer

//Revision 1.506  2005/10/05 20:24:30  tony
//fixed updating with the stolpe fix

//Revision 1.505  2005/10/03 17:50:11  tony
//fix null pointer

//Revision 1.504  2005/09/29 17:49:35  tony
//USRO-R-TMAY-6GLNMS

//Revision 1.503  2005/09/28 19:11:14  tony
//TIR USRO-R-TMAY-6GLQLW

//Revision 1.502  2005/09/19 18:50:34  tony
//MN25287512

//Revision 1.501  2005/09/14 20:31:16  tony
//fixed null pointer

//Revision 1.500  2005/09/08 21:16:51  tony
//commented out fix

//Revision 1.499  2005/09/08 21:06:23  tony
//MN25287512

//Revision 1.498  2005/09/08 17:57:26  tony
//Updated VEEdit Logic to Improve functionality

//Revision 1.497  2005/09/02 15:32:58  tony
//VEEdit functionality addition for CR 0815056514

//Revision 1.496  2005/08/30 21:00:54  joan
//remove comments made by cvs to compile

//Revision 1.495  2005/08/30 17:39:14  dave
//new cat comments

//Revision 1.493  2005/07/19 18:42:34  tony
//fixed null pointer

//Revision 1.492  2005/07/19 18:26:19  tony
//fixed null pointer

//Revision 1.494  2005/08/11 20:26:19  joan
//fixes

//Revision 1.493  2005/07/19 18:42:34  tony
//fixed null pointer

//Revision 1.492  2005/07/19 18:26:19  tony
//fixed null pointer

//Revision 1.491  2005/06/20 22:34:30  gregg
//fix for rst.put on FLAG case.

//Revision 1.490  2005/05/10 20:15:41  joan
//trim ei for locking

//Revision 1.489  2005/04/01 15:44:58  tony
//removed println statement

//Revision 1.488  2005/03/28 22:58:03  gregg
//getAttributeFromEntityItem for t-bone

//Revision 1.487  2005/03/24 23:38:12  gregg
//*** empty log message ***

//Revision 1.486  2005/03/24 21:59:23  gregg
//backing out changes

//Revision 1.485  2005/03/24 21:42:56  gregg
//trying to cover relator t-bone case in getEANObject

//Revision 1.484  2005/03/24 20:41:02  gregg
//some trace stmts

//Revision 1.483  2005/03/23 23:34:57  gregg
//chaining fix on parentrelaochilderama

//Revision 1.482  2005/03/09 00:27:20  joan
//work on display nav attributes

//Revision 1.481  2005/03/08 22:10:21  dave
//backing out tracing

//Revision 1.480  2005/03/08 18:09:56  dave
//more trace for steve

//Revision 1.479  2005/03/07 23:35:37  dave
//syntax

//Revision 1.478  2005/03/07 23:29:30  dave
//trace for steve

//Revision 1.477  2005/03/03 23:21:51  dave
//fixing small int problem and jtest

//Revision 1.476  2005/03/03 21:25:16  dave
//NEW_LINE on EAN Foundation

//Revision 1.475  2005/03/02 22:53:37  dave
//Jtest for EntityItem

//Revision 1.474  2005/02/23 20:30:16  gregg
//workin on more for CR7137 (UniqueAttribute)

//Revision 1.473  2005/02/18 22:43:20  gregg
//evaluate LocalRuleGroup

//Revision 1.472  2005/02/18 19:30:33  gregg
//mulitple values in one group(CR 3148)

//Revision 1.471  2005/02/17 20:37:02  gregg
//some debugs for tony

//Revision 1.470  2005/02/01 18:42:33  gregg
//fix

//Revision 1.469  2005/02/01 18:38:09  gregg
//lets use Evaluator sooner for UniqueAttributeGroup... before we sned off to database

//Revision 1.468  2005/01/28 23:07:15  gregg
//updates for DependentAttributeValue

//Revision 1.467  2005/01/28 22:06:46  gregg
//DependentAttributeValue

//Revision 1.466  2005/01/18 21:46:49  dave
//more parm debug cleanup

//Revision 1.465  2005/01/17 22:30:08  gregg
//fix for UniqueAttributeGroups...

//Revision 1.464  2005/01/17 19:40:39  gregg
//addDomainEntities()

//Revision 1.463  2005/01/17 19:32:31  gregg
//ok second pass at Rule51

//Revision 1.462  2005/01/17 17:59:35  gregg
//setting props on Rule51

//Revision 1.461  2005/01/17 17:45:59  gregg
//Rule51Group

//Revision 1.460  2005/01/11 21:11:26  dave
//CR 1547 - hide a column with a meta tag (feature key). and change the to string for the entityid...

//Revision 1.459  2005/01/11 19:06:29  gregg
//more UniqueAttributes

//Revision 1.458  2005/01/10 21:47:49  joan
//work on multiple edit

//Revision 1.457  2005/01/07 21:26:32  gregg
//setActive on UniqueAttributeGroup

//Revision 1.456  2005/01/07 20:57:56  gregg
//ensure all attributes exist in a UniqueAttributeGroup if we're gonna perform checks

//Revision 1.455  2005/01/05 22:58:26  gregg
//more UniqueAttributeGroup

//Revision 1.454  2005/01/05 19:24:08  joan
//add new method

//Revision 1.453  2004/12/14 23:18:22  joan
//fix for abr status

//Revision 1.452  2004/12/14 21:40:30  joan
//add for ABR status

//Revision 1.451  2004/12/08 22:34:01  dave
//untracify

//Revision 1.450  2004/12/08 21:57:37  dave
//ok.. lets do a trace

//Revision 1.449  2004/11/17 20:16:21  gregg
//getRowList fix and remove debugs

//Revision 1.448  2004/11/17 19:10:32  gregg
//duplicate get() changes in getEANObject()

//Revision 1.447  2004/11/17 18:19:44  gregg
//Parent/Child logic for Edit Relators in get()

//Revision 1.446  2004/11/16 22:56:41  gregg
//some fixes found while looking at EntityList Edit constructor

//Revision 1.445  2004/11/15 22:35:22  gregg
//work on column orders for ActionItems where parent.entitytype == child.entitytype

//Revision 1.444  2004/11/09 23:58:57  joan
//work on show parent child

//Revision 1.443  2004/11/09 21:00:04  joan
//work on showing both parent and child in navigate

//Revision 1.442  2004/11/05 00:44:20  dave
//code cleanup

//Revision 1.441  2004/11/03 23:12:43  gregg
//MetaColumnOrderGroup for NavActionItems
//MetaLinkAttr switch on ActionItem ColumnOrderControl
//Apply ColumnOrderControl for EntityItems
//Cleanup Debugs

//Revision 1.440  2004/11/03 19:06:22  dave
//undo trace

//Revision 1.439  2004/11/03 18:59:26  dave
//trace for entityitem not found

//Revision 1.438  2004/10/28 18:28:38  joan
//remove System.out

//Revision 1.437  2004/10/28 17:48:19  dave
//some minor fixes

//Revision 1.436  2004/10/25 22:59:45  joan
//fix TIR

//Revision 1.435  2004/10/25 21:13:06  joan
//work on create parent

//Revision 1.434  2004/10/25 20:31:40  joan
//fixes

//Revision 1.433  2004/10/25 17:23:00  joan
//work on create parent

//Revision 1.432  2004/10/20 17:25:44  dave
//syntax

//Revision 1.431  2004/10/20 17:15:24  dave
//trying to save space here

//Revision 1.430  2004/10/15 17:13:53  dave
//o.k. some syntax

//Revision 1.429  2004/10/15 17:06:03  dave
//atttempting some speed up by bypassing instance of checks

//Revision 1.428  2004/10/15 00:24:44  dave
//moving nulling back to top of outer loop

//Revision 1.427  2004/10/11 21:17:32  dave
//syntax

//Revision 1.426  2004/10/11 21:11:20  dave
//change to delete

//Revision 1.425  2004/09/24 18:15:44  joan
//fixes

//Revision 1.424  2004/09/24 17:03:34  joan
//fix for editRelatorOnly

//Revision 1.423  2004/09/22 22:28:25  dave
//ok found it

//Revision 1.422  2004/09/22 22:22:44  dave
//more trace

//Revision 1.421  2004/09/22 22:01:07  dave
//tracking

//Revision 1.420  2004/09/15 16:05:39  joan
//fix long desc.

//Revision 1.419  2004/09/09 20:35:05  gregg
//attempting to store valfrom on BlobAttribute

//Revision 1.418  2004/08/09 15:26:41  dave
//small change to track timing

//Revision 1.417  2004/08/09 15:19:12  dave
//speed test on 7545

//Revision 1.416  2004/07/30 19:48:03  joan
//work on edit

//Revision 1.415  2004/07/27 22:40:21  joan
//fix bug

//Revision 1.414  2004/07/20 20:19:25  dave
//Cutting out System.out trace

//Revision 1.413  2004/06/29 15:50:42  joan
//fix bug

//Revision 1.412  2004/06/18 20:28:38  joan
//work on edit relator

//Revision 1.411  2004/06/18 17:11:17  joan
//work on edit relator

//Revision 1.410  2004/06/16 20:10:17  joan
//work on CopyActionItem

//Revision 1.409  2004/06/08 17:51:31  joan
//throw exception

//Revision 1.408  2004/06/08 17:37:12  joan
//add method

//Revision 1.407  2004/06/02 23:08:11  dave
//removing trace

//Revision 1.406  2004/06/02 22:15:02  dave
//more trace in entity item

//Revision 1.405  2004/05/21 16:15:50  gregg
//check for isDerived on MetaAttribute in getActualRowList()

//Revision 1.404  2004/05/07 19:37:01  gregg
//more derived EID for new Entities

//Revision 1.403  2004/05/07 19:19:35  gregg
//genAttribute for Derived

//Revision 1.402  2004/05/07 18:49:21  gregg
//nll ptr fix in refreshDefaults

//Revision 1.401  2004/04/20 19:52:54  joan
//work on duplicate

//Revision 1.400  2004/04/20 18:26:25  gregg
//reviving some wasModifiedInInterval logic

//Revision 1.399  2004/04/20 18:10:57  gregg
//always include isDerived() attributes in vertical

//Revision 1.398  2004/04/15 18:29:27  joan
//add LastOPWGID in EntityItem

//Revision 1.397  2004/04/09 19:37:19  joan
//add duplicate method

//Revision 1.396  2004/01/13 20:03:37  dave
//syntax

//Revision 1.395  2004/01/13 19:54:41  dave
//space Saver Phase II  m_hsh1 and m_hsh2 created
//as needed instead of always there

//Revision 1.394  2004/01/12 18:04:25  dave
//more tossing weight over board

//Revision 1.393  2003/12/03 22:05:36  dave
//more changes

//Revision 1.392  2003/12/03 21:38:58  dave
//more deferred locking

//Revision 1.391  2003/11/21 22:12:33  dave
//syntax

//Revision 1.390  2003/11/21 21:53:04  dave
//deferred restriction refresh

//Revision 1.389  2003/10/30 01:13:44  dave
//removing boos

//Revision 1.388  2003/10/29 19:02:30  dave
//making blobs go to 50M

//Revision 1.387  2003/10/29 02:08:38  dave
//remove trace

//Revision 1.386  2003/10/29 02:00:09  dave
//more trace

//Revision 1.385  2003/10/29 01:40:01  dave
//more trace

//Revision 1.384  2003/10/29 01:25:48  dave
//more  trace

//Revision 1.383  2003/10/29 00:36:19  dave
//post drop changes for next wave

//Revision 1.382  2003/10/20 21:16:16  joan
//fb52563

//Revision 1.381  2003/10/13 22:23:53  joan
//fix getActualRowList method

//Revision 1.380  2003/10/08 18:22:44  joan
//add getActualColumnListCount and getActualRowListCount methods

//Revision 1.379  2003/09/16 15:57:27  joan
//fb52270, null pointer

//Revision 1.378  2003/09/10 21:43:18  dave
//null profile, partant knundrum

//Revision 1.377  2003/09/09 22:07:29  dave
//removing profile links in attribute creation

//Revision 1.376  2003/09/03 20:07:18  dave
//Trace removal

//Revision 1.375  2003/08/28 16:28:03  joan
//adjust link method to have link option

//Revision 1.374  2003/08/27 23:09:59  dave
//fixing null pointer in EntityItem constructor

//Revision 1.373  2003/08/27 22:41:10  dave
//minor cleanup

//Revision 1.372  2003/08/21 20:52:43  dave
//new import statement for Date

//Revision 1.371  2003/08/21 20:43:08  dave
//Timing the clone process on entityitem

//Revision 1.370  2003/08/18 21:29:20  dave
//syntax

//Revision 1.369  2003/08/18 21:18:52  dave
//isLocked Modifier

//Revision 1.368  2003/08/18 21:05:08  dave
//Adding  the sequencing chain to the islocked to not
//induced each cell for being locked in the islocked of
//entityItem (kludge)

//Revision 1.367  2003/08/15 20:09:32  dave
//changing islocked to check for a new lock if
//exclusive lock does not match the requestor

//Revision 1.366  2003/06/25 18:43:59  joan
//move changes from v111

//Revision 1.363.2.7  2003/06/25 00:15:27  joan
//fix compile

//Revision 1.363.2.6  2003/06/24 23:37:26  joan
//fix WhereUsedActionItem constructor

//Revision 1.363.2.5  2003/06/10 17:20:36  dave
//in some updates.. we were not catching required fields given
//had change generate..

//Revision 1.363.2.4  2003/06/05 23:11:10  joan
//fix refreshDefault

//Revision 1.363.2.3  2003/06/05 18:24:32  joan
//refresh default for attributes in classification list.

//Revision 1.363.2.2  2003/06/05 15:26:46  joan
//add System.out for debugging

//Revision 1.363.2.1  2003/05/30 22:03:04  joan
//display key in toString method

//Revision 1.363  2003/05/19 15:13:21  dave
//toString fix

//Revision 1.362  2003/05/16 21:58:13  dave
//change to entityitem toSTring

//Revision 1.361  2003/05/15 19:36:07  dave
//syntax fixes

//Revision 1.360  2003/05/15 18:13:39  bala
//Adding methods to set and retrieve status of EntityItem...used for comparison purposes

//Revision 1.359  2003/05/14 20:05:27  dave
//do not auto add the entityitem to the parent group

//Revision 1.358  2003/05/09 22:06:28  gregg
//compile fix

//Revision 1.357  2003/05/09 21:58:26  gregg
//remove emf parent param from ChangeHistoryGroup constructor

//Revision 1.356  2003/05/08 17:59:22  dave
//tracking setDefauls for classificaitons...

//Revision 1.355  2003/05/02 15:49:39  dave
//cleaning up commit and stand alone Create

//Revision 1.354  2003/04/30 21:54:36  dave
//making sure refreshClassifications is fired when the rows
//are pulled for the rowselectable table

//Revision 1.353  2003/04/30 20:13:40  dave
//only turn off  > on classification check

//Revision 1.352  2003/04/30 03:45:38  dave
//trying to make evaluator run faster

//Revision 1.351  2003/04/30 02:31:30  dave
//fixing null pointer

//Revision 1.350  2003/04/30 01:52:30  dave
//fixing null pointer

//Revision 1.349  2003/04/29 21:47:14  dave
//adding refreshRestrictions. to key spots

//Revision 1.348  2003/04/28 22:49:36  dave
//adding toClassificationString on entityitem

//Revision 1.347  2003/04/28 18:39:12  joan
//fix null pointer in refreshClassification

//Revision 1.346  2003/04/25 22:21:35  bala
//Change refreshClassifications, isClassified methods to public

//Revision 1.345  2003/04/24 21:14:09  dave
//syntax fix

//Revision 1.344  2003/04/24 21:04:52  dave
//trying to fix where used and equivelence?

//Revision 1.343  2003/04/24 18:32:17  dave
//getting rid of traces and system out printlns

//Revision 1.342  2003/04/24 16:48:02  dave
//trying to pin point refreshClassifications to trigger when
//a lock was aquired successfully the first time

//Revision 1.341  2003/04/24 00:14:54  dave
//update fixes

//Revision 1.340  2003/04/23 22:46:38  dave
//fix

//Revision 1.339  2003/04/23 22:28:45  dave
//cleanup on getClassification

//Revision 1.338  2003/04/23 22:12:58  dave
//clean up and invokation of setClassifications..

//Revision 1.337  2003/04/23 21:57:55  gregg
//System.out for isLocked-> _ll is null (Tony requested)

//Revision 1.336  2003/04/23 21:21:10  dave
//adding all fields back to grid

//Revision 1.335  2003/04/21 21:55:25  gregg
//fix for getEntityChangeHistoryGroup to include downlinks on associations

//Revision 1.334  2003/04/01 01:49:02  dave
//closing in on Tagging

//Revision 1.333  2003/04/01 01:32:05  dave
//some minor mods

//Revision 1.332  2003/04/01 01:16:40  dave
//final compile for this effort

//Revision 1.331  2003/04/01 01:04:58  dave
//more fixes

//Revision 1.330  2003/04/01 01:03:02  dave
//more syntax

//Revision 1.329  2003/04/01 00:47:07  dave
//better import statements

//Revision 1.328  2003/04/01 00:36:23  dave
//Stand Alone Entity Item Constructor

//Revision 1.327  2003/03/27 23:40:23  gregg
//applyColumnOrders when classifications are refreshed

//Revision 1.326  2003/03/25 20:31:42  gregg
//more applyColumnOrders for relator case

//Revision 1.325  2003/03/24 22:18:28  gregg
//apply MetaColOrder logic to Vertical+Horizontal

//Revision 1.324  2003/03/20 20:43:38  gregg
//getChangeHistoryGroup method

//Revision 1.323  2003/03/19 23:09:47  joan
//remove System.out

//Revision 1.322  2003/03/18 23:36:39  joan
//debug

//Revision 1.321  2003/03/07 21:00:50  dave
//making interval used for GBL8116, GBL8114 to
//only pull an interval's worth of data from the Queue
//table to the Navigate table

//Revision 1.320  2003/02/06 22:14:38  gregg
//setModifiedInInterval() method

//Revision 1.319  2003/02/06 21:00:29  gregg
//remove declares throws from javadoc description

//Revision 1.318  2003/02/06 19:36:38  gregg
//removed Exception throwing for wasModifiedInInterval(); return false by default if no interval is defined

//Revision 1.317  2003/02/06 02:13:20  gregg
//slight adjustments to wasModifiedInInterval logic to incorporate 'one-way-valve' on boolean
//(once EntityItem is detected as modified w/in an interval dont change this!!)

//Revision 1.316  2003/02/04 21:13:52  gregg
//calculateEntityItemsForInterval logic

//Revision 1.315  2003/02/04 19:24:05  gregg
//date modified/interval logic

//Revision 1.314  2003/01/21 00:20:34  joan
//adjust link method to test VE lock

//Revision 1.313  2003/01/20 18:21:42  joan
//debug save WG default

//Revision 1.312  2003/01/14 22:05:05  joan
//adjust removeLink method

//Revision 1.311  2003/01/09 21:37:41  dave
//syntax fixes

//Revision 1.310  2003/01/09 21:28:11  dave
//syntax error

//Revision 1.309  2003/01/09 21:20:31  dave
//added getInUseNLSIDs()

//Revision 1.308  2003/01/08 21:44:04  joan
//add getWhereUsedList

//Revision 1.307  2003/01/07 20:55:27  joan
//fix null pointer

//Revision 1.306  2003/01/03 22:24:39  bala
//setting the setEntityId method to public

//Revision 1.305  2002/12/24 16:47:48  joan
//add comments

//Revision 1.304  2002/12/24 00:17:16  joan
//call refreshDefault after resetting

//Revision 1.303  2002/12/23 23:46:26  joan
//add removeDefaultEntityItem

//Revision 1.302  2002/12/23 22:17:50  joan
//add resetWGDefault method

//Revision 1.301  2002/12/20 21:05:04  joan
//debug

//Revision 1.300  2002/12/13 21:30:52  joan
//fix bugs

//Revision 1.299  2002/12/13 20:41:00  joan
//fix for addition column in Softlock table

//Revision 1.298  2002/12/10 22:51:46  dave
//cleaner deactivate on the classificaiton save when we have to
//null out values

//Revision 1.297  2002/11/21 15:01:28  dave
//publicing

//Revision 1.296  2002/11/20 23:38:21  joan
//fix unlock

//Revision 1.295  2002/11/20 01:09:44  joan
//fix bugs

//Revision 1.294  2002/11/19 23:26:56  joan
//fix hasLock method

//Revision 1.293  2002/11/19 23:24:35  dave
//needed a return on a typed method

//Revision 1.292  2002/11/19 23:17:22  dave
//syntax

//Revision 1.291  2002/11/19 23:07:18  dave
//more level setting methods

//Revision 1.290  2002/11/19 22:56:13  dave
//level tracking in the EntityItem

//Revision 1.289  2002/11/19 18:27:42  joan
//adjust lock, unlock

//Revision 1.288  2002/11/19 00:06:26  joan
//adjust isLocked method

//Revision 1.287  2002/11/16 19:58:14  dave
//formatting

//Revision 1.286  2002/11/14 19:09:52  joan
//add getNavAttrDescription method

//Revision 1.285  2002/11/12 17:18:28  dave
//System.out.println clean up

//Revision 1.284  2002/11/12 02:20:09  dave
//fixing rollback

//Revision 1.283  2002/11/12 01:17:42  dave
//trace

//Revision 1.282  2002/11/11 23:15:26  joan
//remove System.out

//Revision 1.281  2002/11/11 22:14:13  dave
//classification clean up

//Revision 1.280  2002/11/11 21:49:54  joan
//debug refreshDefault

//Revision 1.279  2002/11/11 16:50:53  joan
//use getShortDescription

//Revision 1.278  2002/11/06 20:02:24  dave
//more trace for resest/restrictions/required

//Revision 1.277  2002/11/06 00:39:33  dave
//working w/ refreshrestrictions in more places

//Revision 1.276  2002/11/05 00:33:43  dave
//refreash Defaults fix for classifications

//Revision 1.275  2002/11/05 00:17:35  dave
//more trace for lock/unlock

//Revision 1.274  2002/11/05 00:01:01  dave
//has changes problem

//Revision 1.273  2002/11/04 18:00:19  dave
//fixes to put method

//Revision 1.272  2002/11/04 17:48:09  dave
//fixing 22756 (removing required and default value checks
//if you are isUsedInSearch is true)

//Revision 1.271  2002/10/30 22:39:13  dave
//more cleanup

//Revision 1.270  2002/10/30 22:36:19  dave
//clean up

//Revision 1.269  2002/10/30 22:02:32  dave
//added exception throwing to commit

//Revision 1.268  2002/10/29 19:06:33  dave
//fixing is SuperEditable and looking at it in the EntityItem
//isRestrcited check.

//Revision 1.267  2002/10/29 00:02:55  dave
//backing out row commit for 1.1

//Revision 1.266  2002/10/28 23:49:14  dave
//attempting the first commit with a row index

//Revision 1.265  2002/10/28 20:39:43  dave
//Feedback 22529.  Changed column title of searchBinder
//and tried to remove the asterek from the get when
//we are in DynaTable mode

//Revision 1.264  2002/10/23 14:54:12  dave
//removal of display statements

//Revision 1.263  2002/10/22 22:17:18  dave
//more trace for classifications

//Revision 1.262  2002/10/22 21:42:55  dave
//Yet more trace statements

//Revision 1.261  2002/10/22 21:14:29  dave
//More trace statements for Classification and Preview

//Revision 1.260  2002/10/22 17:59:46  dave
//trace statements enabled

//Revision 1.259  2002/10/18 20:18:52  joan
//add isMatrixEditable method

//Revision 1.258  2002/10/15 22:38:55  dave
//only check required the are part of classification  when
//you are working with a classificed entity

//Revision 1.257  2002/10/14 22:28:59  dave
//Cleaning up and closing out more system.out.println

//Revision 1.256  2002/10/14 18:02:34  dave
//removal of System.out.println in setAttribute

//Revision 1.255  2002/10/09 21:32:56  dave
//added isDynaTable to EANTableWrapper interface

//Revision 1.254  2002/10/09 20:26:37  dave
//making sure we can edit things in an EntityGroup when used
//as part of a SearchActionItem

//Revision 1.253  2002/10/07 21:16:44  dave
//exposing getLockGroup for Ted

//Revision 1.252  2002/10/07 17:41:38  joan
//add getLockGroup method

//Revision 1.251  2002/10/04 19:16:28  joan
//fix null pointer

//Revision 1.250  2002/10/03 17:48:58  dave
//allow for categorization fields to show up during edit

//Revision 1.249  2002/10/02 22:00:19  dave
//tracking on cloneEntityItem

//Revision 1.248  2002/10/02 21:31:06  dave
//ensure all classified attributes do not get deactivated

//Revision 1.247  2002/10/01 21:29:27  dave
//more trace

//Revision 1.246  2002/10/01 20:58:37  dave
//more trace Statements

//Revision 1.245  2002/10/01 20:13:29  dave
//more trace statements

//Revision 1.244  2002/09/27 17:10:59  dave
//made addRow a boolean

//Revision 1.243  2002/09/26 23:39:33  joan
//remove System.out

//Revision 1.242  2002/09/25 21:45:53  dave
//more syntax fix

//Revision 1.241  2002/09/25 21:31:49  dave
//syntax

//Revision 1.240  2002/09/25 21:20:31  dave
//exposing ClassificationTesting to the outside world

//Revision 1.239  2002/09/25 17:11:09  dave
//generalized getRowList on EntityItem

//Revision 1.238  2002/09/25 16:31:41  dave
//small syntax error

//Revision 1.237  2002/09/25 16:23:59  dave
//Deactivate non Classified attributes on update

//Revision 1.236  2002/09/24 23:25:16  dave
//calling refreshClassifications from getRowList

//Revision 1.235  2002/09/24 23:13:51  dave
//only show global classifications and the classification atts on create
//in EntityItem getEntityItemTable

//Revision 1.234  2002/09/24 22:06:58  dave
//SYNTAX

//Revision 1.233  2002/09/24 21:57:15  dave
//more null pointer trapping and Classification on create

//Revision 1.232  2002/09/24 18:58:31  dave
//fix to Classification Wave II

//Revision 1.231  2002/09/24 17:00:49  dave
//more displays and null pointer  trapping

//Revision 1.230  2002/09/20 21:33:56  dave
//bad var name

//Revision 1.229  2002/09/20 21:25:12  dave
//syntax fixing for classification wave II

//Revision 1.228  2002/09/20 21:14:49  dave
//more classifications

//Revision 1.227  2002/09/20 21:04:59  dave
//more Classification Changes

//Revision 1.226  2002/09/20 20:54:02  dave
//Classification Wave II and other

//Revision 1.225  2002/09/20 17:21:32  dave
//syntax fix

//Revision 1.224  2002/09/20 17:12:47  dave
//improved toString on Entity Item

//Revision 1.223  2002/09/20 17:00:00  dave
//need a try catch in ClassificationBinder generator

//Revision 1.222  2002/09/20 16:21:52  dave
//classification first pass for the structure

//Revision 1.221  2002/09/11 23:14:09  joan
//call refreshRequired for entityid < 0

//Revision 1.220  2002/09/11 22:44:47  joan
//debug refreshRequired

//Revision 1.219  2002/09/11 21:51:22  joan
//debug refreshRequired

//Revision 1.218  2002/09/10 19:34:12  dave
//more changes for Description Change List

//Revision 1.217  2002/09/09 20:39:17  dave
//trying to fix syntax

//Revision 1.216  2002/09/09 20:28:29  dave
//simplifying isEditable to common code

//Revision 1.215  2002/09/06 20:02:02  dave
//fixing isEditable for EntityGroup,Item, Attribute

//Revision 1.214  2002/09/06 18:19:27  dave
//fixes

//Revision 1.213  2002/09/06 18:07:21  dave
//syntax

//Revision 1.212  2002/09/06 17:52:52  dave
//tracing on edit update

//Revision 1.211  2002/09/06 17:15:43  dave
//attempts to honor view only in action item and entity capability

//Revision 1.210  2002/09/06 15:32:32  bala
//changing protected method checkBusinessRules to public

//Revision 1.209  2002/08/20 20:57:56  joan
//debug

//Revision 1.208  2002/08/20 16:42:50  joan
//debug

//Revision 1.207  2002/08/20 16:25:45  joan
//debug getEANObject

//Revision 1.206  2002/08/19 19:39:06  dave
//changes lock to send clipped copy of EntityItem so we do not
//end up sending up the whole bunch

//Revision 1.205  2002/08/15 22:17:08  bala
//Add debug statement

//Revision 1.204  2002/08/15 21:24:26  bala
//fix compiler error

//Revision 1.203  2002/08/15 20:36:39  bala
//fix bug

//Revision 1.202  2002/08/15 20:29:12  bala
//typo

//Revision 1.201  2002/08/15 20:26:56  bala
//Changed setEntityID method to update the entityid of the attributes of the Returnentitykey

//Revision 1.200  2002/08/14 21:55:08  bala
//deleting an extra catch clause from updateWGDefault

//Revision 1.199  2002/08/14 21:51:51  bala
//Bypassing business rule checking in generateUpdateEntity

//Revision 1.198  2002/08/14 21:39:25  bala
//sp is not getting the right entity id for wgdefault

//Revision 1.197  2002/08/13 23:35:56  bala
//print error msg when nothing found to update when updateWGDefault called

//Revision 1.196  2002/08/13 23:34:06  bala
//Disable business rule checking for updateWGDefault

//Revision 1.195  2002/08/13 18:33:11  bala
//more fixes

//Revision 1.194  2002/08/13 17:56:41  bala
//more fixes to updateWGDefault

//Revision 1.193  2002/08/13 17:46:14  bala
//Fix updateWGDefault

//Revision 1.192  2002/08/13 17:33:23  bala
//Adding updateWGDefault method

//Revision 1.191  2002/08/08 20:51:48  joan
//fix setParentEntityItem

//Revision 1.190  2002/08/08 20:07:25  joan
//fix setParentEntityItem

//Revision 1.189  2002/08/08 19:20:41  joan
//debug

//Revision 1.188  2002/08/08 16:27:00  joan
//debug setParentEntityItem

//Revision 1.187  2002/08/07 23:10:52  dave
//first pass at new Clone EntityItem array stuff

//Revision 1.186  2002/07/16 15:38:20  joan
//working on method to return the array of actionitems

//Revision 1.185  2002/07/08 17:53:42  joan
//fix link method

//Revision 1.184  2002/07/08 16:05:29  joan
//fix link method

//Revision 1.183  2002/06/25 20:36:08  joan
//add create method for whereused

//Revision 1.182  2002/06/25 17:49:36  joan
//add link and removeLink methods

//Revision 1.181  2002/06/19 23:37:54  joan
//add getLongDescription and getShortDescription methods

//Revision 1.180  2002/06/19 15:52:19  joan
//work on add column in matrix

//Revision 1.179  2002/06/17 23:53:47  joan
//add addColumn method

//Revision 1.178  2002/06/05 22:18:19  joan
//work on put and rollback

//Revision 1.177  2002/06/05 16:28:49  joan
//add getMatrixValue method

//Revision 1.176  2002/05/30 22:49:53  joan
//throw MiddlewareBusinessRuleException when committing

//Revision 1.175  2002/05/21 16:36:02  joan
//remove System.out

//Revision 1.174  2002/05/20 23:55:26  joan
//fix setParentEntityItem

//Revision 1.173  2002/05/20 23:23:26  joan
//debug setParentEntityItem

//Revision 1.172  2002/05/20 22:26:47  joan
//add some debug statements

//Revision 1.171  2002/05/20 21:31:11  joan
//add setParentEntityItem

//Revision 1.170  2002/05/20 18:47:58  joan
//when adding new row and creating, set up the parent entity item with entityid = -1
//when parent entitygroup has more than one entity item,
//and throw exception when commit

//Revision 1.169  2002/05/20 17:40:28  joan
//working on addRow

//Revision 1.168  2002/05/20 16:07:36  joan
//working on addRow

//Revision 1.167  2002/05/15 18:36:17  dave
//attempted fix at required

//Revision 1.166  2002/05/15 16:40:55  joan
//fix removeLockItem

//Revision 1.165  2002/05/14 21:38:00  joan
//debug

//Revision 1.164  2002/05/14 17:47:06  joan
//working on LockActionItem

//Revision 1.163  2002/05/13 22:59:17  joan
//add code for new entityitem in hasLock method

//Revision 1.162  2002/05/13 20:40:32  joan
//add resetLockGroup method

//Revision 1.161  2002/05/13 18:33:14  joan
//debug

//Revision 1.160  2002/05/13 17:48:17  joan
//debug lock

//Revision 1.159  2002/05/13 16:42:08  joan
//fixing unlock method

//Revision 1.158  2002/05/10 22:18:14  joan
//fix error

//Revision 1.157  2002/05/10 22:12:00  joan
//fix null pointer

//Revision 1.156  2002/05/10 22:04:55  joan
//add hasLock method

//Revision 1.155  2002/05/10 20:45:54  joan
//fixing lock

//Revision 1.154  2002/05/08 20:38:51  joan
//fix compiling error

//Revision 1.153  2002/05/08 20:19:32  dave
//syntax error fixes

//Revision 1.152  2002/05/08 19:56:41  dave
//attempting to throw the BusinessRuleException on Commit

//Revision 1.151  2002/04/26 17:08:24  joan
//working on blob attribute

//Revision 1.150  2002/04/25 22:32:00  joan
//blob attribute

//Revision 1.149  2002/04/25 21:51:51  joan
//working on blob attribute

//Revision 1.148  2002/04/25 00:00:52  joan
//working on getRowHeader

//Revision 1.147  2002/04/24 23:34:58  joan
//debug

//Revision 1.146  2002/04/24 23:22:35  joan
//debug

//Revision 1.145  2002/04/24 22:57:18  joan
//debug getRowHeader

//Revision 1.144  2002/04/24 18:04:36  joan
//add removeRow method

//Revision 1.143  2002/04/24 17:51:35  dave
//to string fix on entityItem

//Revision 1.142  2002/04/24 16:48:54  joan
//add code in isLocked method to only check whether lockgroup has exclusive lock

//Revision 1.141  2002/04/23 23:58:25  joan
//working on entityid < 0

//Revision 1.140  2002/04/23 23:43:33  joan
//working on entityid < 0

//Revision 1.139  2002/04/23 22:44:31  joan
//debug

//Revision 1.138  2002/04/23 22:30:17  joan
//debug

//Revision 1.137  2002/04/23 22:15:54  joan
//add code to handle the case when new entityitem is created

//Revision 1.136  2002/04/23 21:16:17  joan
//syntax

//Revision 1.135  2002/04/23 20:48:48  joan
//fixing null pointer

//Revision 1.134  2002/04/23 20:23:19  joan
//null pointer

//Revision 1.133  2002/04/23 17:05:59  joan
//working on lock method

//Revision 1.132  2002/04/23 15:41:25  joan
//null pointer

//Revision 1.131  2002/04/22 22:18:24  joan
//working on unlock

//Revision 1.130  2002/04/22 20:17:59  joan
//working on unlock

//Revision 1.129  2002/04/22 19:38:37  joan
//fixing bugs

//Revision 1.128  2002/04/22 18:08:38  joan
//add unlock method

//Revision 1.127  2002/04/22 17:23:20  joan
//catch exception

//Revision 1.126  2002/04/22 17:16:13  dave
//attempting to fix remotedatabase

//Revision 1.125  2002/04/22 17:12:16  joan
//import exception

//Revision 1.124  2002/04/22 16:54:45  joan
//working on lock

//Revision 1.123  2002/04/22 16:37:52  dave
//stripping 1.0 functionality from the 1.1 arch

//Revision 1.122  2002/04/19 22:34:06  joan
//change isLocked interface to include profile as parameter

//Revision 1.121  2002/04/19 22:06:19  dave
//making sense of swapkey = resetKey

//Revision 1.120  2002/04/19 20:31:28  joan
//fixing errors

//Revision 1.119  2002/04/19 20:13:54  joan
//working on lock

//Revision 1.118  2002/04/19 17:17:02  joan
//change isLocked  interface

//Revision 1.117  2002/04/18 23:48:34  joan
//working on isLocked

//Revision 1.116  2002/04/18 23:21:20  dave
//basic sketch for lock in RowSelectableTable

//Revision 1.115  2002/04/18 19:50:42  dave
//lock = true for set testing in State machine

//Revision 1.114  2002/04/18 17:09:58  joan
//debug

//Revision 1.113  2002/04/18 16:50:09  joan
//debugging

//Revision 1.112  2002/04/18 16:29:54  joan
//initialize m_bLocked = false

//Revision 1.111  2002/04/17 23:32:12  dave
//ordering issue for vertical

//Revision 1.110  2002/04/17 20:17:06  dave
//new XMLAttribute and its MetaPartner

//Revision 1.109  2002/04/17 18:13:34  joan
//fixing createLock method and add lockgroup methods in entityitem

//Revision 1.108  2002/04/12 23:04:21  joan
//syntax

//Revision 1.107  2002/04/12 22:44:18  joan
//throws exception

//Revision 1.106  2002/04/12 16:53:31  dave
//syntax

//Revision 1.105  2002/04/12 16:42:05  dave
//added isLocked to the tableDef

//Revision 1.104  2002/04/11 23:41:13  dave
//toString on entityItem is limited to navigateable attributes only

//Revision 1.103  2002/04/11 18:15:08  dave
//Trace statement adjustment and null pointer fix

//Revision 1.102  2002/04/10 20:06:12  dave
//Syntax error fix

//Revision 1.101  2002/04/10 19:50:19  dave
//Added a cloneStructure routine to the EANMetaAttribute

//Revision 1.100  2002/04/08 20:04:04  dave
//adding file closes

//Revision 1.99  2002/04/08 19:43:28  dave
//tracing create action item problem

//Revision 1.98  2002/04/05 20:11:31  dave
//do not refresh defaults when we create a new Attribute Object

//Revision 1.97  2002/04/04 19:29:56  dave
//fix on put to gen the object if no object exists

//Revision 1.96  2002/04/04 18:49:31  dave
//syntax

//Revision 1.95  2002/04/04 18:32:16  dave
//added more refreshReset stuff

//Revision 1.94  2002/04/04 17:59:57  dave
//Syntax

//Revision 1.93  2002/04/04 17:52:53  dave
//syntax fixes on refresh logic

//Revision 1.92  2002/04/04 17:42:03  dave
//added Reset logic to entity Item (Pass one)
//And added EANList reaturn from refresh methods

//Revision 1.91  2002/04/03 23:15:06  dave
//Null pointer fix

//Revision 1.90  2002/04/03 23:10:24  dave
//system.out.println removal

//Revision 1.89  2002/04/03 22:49:50  dave
//syntax error

//Revision 1.88  2002/04/03 22:38:11  dave
//Remove tracing and added checking for required fields ..
//cannot update w/o them

//Revision 1.87  2002/04/03 00:28:23  dave
//retrun to return

//Revision 1.86  2002/04/03 00:21:09  dave
//syntax

//Revision 1.85  2002/04/03 00:19:38  dave
//seperated hasChanges from pendingChanges

//Revision 1.84  2002/04/03 00:08:25  dave
//syntax

//Revision 1.83  2002/04/02 23:58:29  dave
//trace statements

//Revision 1.82  2002/04/02 19:21:58  dave
//Syntax

//Revision 1.81  2002/04/02 19:12:37  dave
//more isRequiredStuff

//Revision 1.80  2002/04/02 19:00:01  dave
//first pass at required field changes

//Revision 1.79  2002/04/02 18:17:39  dave
//more refreshRestriction

//Revision 1.78  2002/04/02 01:53:41  dave
//more Syntax

//Revision 1.77  2002/04/02 01:41:07  dave
//Syntax

//Revision 1.76  2002/04/02 01:12:08  dave
//first stab at restriction

//Revision 1.75  2002/04/02 00:08:28  dave
//more fixes to syntax

//Revision 1.74  2002/04/02 00:06:19  dave
//syntax fix

//Revision 1.73  2002/04/01 22:27:36  dave
//test

//Revision 1.72  2002/03/28 01:26:59  dave
//syntax

//Revision 1.70  2002/03/27 23:51:57  dave
//syntax for addRow, etc

//Revision 1.69  2002/03/27 22:34:21  dave
//Row Selectable Table row Add logic. First attempt

//Revision 1.68  2002/03/26 22:04:25  dave
//typo on null pointer trap

//Revision 1.67  2002/03/26 20:58:11  dave
//adding commit local logic so we have cornered updates

//Revision 1.66  2002/03/26 19:18:31  dave
//fixes and patches

//Revision 1.65  2002/03/26 19:03:52  dave
//trace and fix for bad parent

//Revision 1.64  2002/03/26 18:43:34  dave
//data integrity check for hierarchical structure management

//Revision 1.63  2002/03/26 17:47:40  dave
//more trace

//Revision 1.62  2002/03/25 21:18:10  dave
//syntax

//Revision 1.61  2002/03/25 21:01:09  dave
//trace statement for stack management

//Revision 1.60  2002/03/25 20:33:37  dave
//More trace statements

//Revision 1.59  2002/03/25 19:50:50  dave
//more update traces

//Revision 1.58  2002/03/23 01:17:59  dave
//syntax and import fixes

//Revision 1.57  2002/03/23 01:08:26  dave
//first attempt at update

//Revision 1.56  2002/03/22 22:39:59  dave
//more trace and ensuring stack is init for Entity Item

//Revision 1.55  2002/03/22 21:21:13  dave
//syntax

//Revision 1.54  2002/03/22 21:10:34  dave
//trace for addToStack

//Revision 1.53  2002/03/21 22:24:45  dave
//isEditble clarification

//Revision 1.52  2002/03/21 21:48:29  dave
//trace statements

//Revision 1.51  2002/03/21 18:38:57  dave
//added getHelp to the EANTableModel

//Revision 1.50  2002/03/21 00:37:47  dave
//more syntax fixes

//Revision 1.49  2002/03/21 00:22:57  dave
//adding rollback logic to the rowSelectable table

//Revision 1.48  2002/03/20 21:21:11  dave
//syntax fixes, and rollback on the attribute

//Revision 1.47  2002/03/20 21:07:38  dave
//starting to place stack information in the EntityItem

//Revision 1.46  2002/03/20 18:33:56  dave
//expanding the get for the EANAddressable to
//include a verbose boolean to dictate what gets sent back

//Revision 1.45  2002/03/20 00:04:16  dave
//syntax

//Revision 1.44  2002/03/19 23:45:17  dave
//more work on vertical table

//Revision 1.43  2002/03/19 19:36:09  dave
//syntax fix

//Revision 1.42  2002/03/19 19:29:02  dave
//fix for entityitem table bug

//Revision 1.41  2002/03/19 17:32:27  dave
//name fix

//Revision 1.40  2002/03/19 05:08:50  dave
//syntax

//Revision 1.39  2002/03/19 00:50:09  dave
//missing try block

//Revision 1.38  2002/03/19 00:39:07  dave
//more syntax fixes

//Revision 1.37  2002/03/19 00:23:15  dave
//syntax fixes

//Revision 1.36  2002/03/19 00:10:49  dave
//first attempt at vertical table stuff

//Revision 1.35  2002/03/18 20:40:15  dave
//backing out EntityItem table concept.  we will need a columnSelectble table model
//if we want to edit a single entity at a time.  I.E. use the single
//table metaphor

//Revision 1.34  2002/03/18 19:32:44  dave
//remove EANAddressable from external interface

//Revision 1.33  2002/03/13 18:47:07  dave
//syntax fixes

//Revision 1.32  2002/03/13 18:28:19  dave
//attempt at creating the putCache information

//Revision 1.31  2002/03/12 18:42:31  dave
//abstract fixes

//Revision 1.30  2002/03/12 18:33:08  dave
//clean up on EANAddressable - removed the int indexes
//because they make no sense.
//Added standard put /get methods to the EANAttibute

//Revision 1.29  2002/03/11 22:14:17  dave
//more syntax

//Revision 1.28  2002/03/11 22:03:57  dave
//more syntax

//Revision 1.27  2002/03/11 21:45:39  dave
//more syntax

//Revision 1.26  2002/03/11 21:35:40  dave
//Switch to switch.

//Revision 1.25  2002/03/11 21:22:39  dave
//syntax for first stage of edit

//Revision 1.24  2002/03/11 21:07:08  dave
//more changes to syntax

//Revision 1.23  2002/03/11 20:56:15  dave
//mass changes for beginnings of edit

//Revision 1.22  2002/03/07 22:26:00  dave
//added a re-assignment function for an entityitem

//Revision 1.21  2002/02/22 17:35:11  dave
//added EANObject as in interface

//Revision 1.20  2002/02/19 20:06:58  dave
//removed the SQLexception throwing in the EntityItem constructor

//Revision 1.19  2002/02/19 18:28:59  dave
//added transfer Attributes function

//Revision 1.18  2002/02/18 18:42:17  dave
//adding cart methods

//Revision 1.17  2002/02/14 01:21:17  dave
//created a binder for action groups so relator/entity can be shown

//Revision 1.16  2002/02/13 21:58:21  dave
//more table stuff

//Revision 1.15  2002/02/13 21:42:55  dave
//fixing more syntax

//Revision 1.14  2002/02/13 21:32:19  dave
//more table generating stuff

//Revision 1.13  2002/02/12 21:29:19  dave
//for syntax

//Revision 1.12  2002/02/12 21:17:11  dave
//syntax

//Revision 1.11  2002/02/12 21:01:58  dave
//added toString methods for diplay help

//Revision 1.10  2002/02/12 17:49:18  dave
//more fixes

//Revision 1.9  2002/02/12 17:41:00  dave
//syntax fixes

//Revision 1.8  2002/02/12 17:28:45  dave
//more dump extra's

//Revision 1.6  2002/02/06 15:23:07  dave
//more fixes to sytax

//Revision 1.5  2002/02/06 00:42:39  dave
//more fixes to base code

//Revision 1.4  2002/02/06 00:02:56  dave
//more syntax fixes

//Revision 1.3  2002/02/05 17:50:13  dave
//adjusted import statements

//Revision 1.2  2002/02/05 16:39:14  dave
//more expansion of abstract model

//Revision 1.1  2002/02/05 00:11:57  dave
//more structure


package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
import COM.ibm.opicmpdh.transactions.OPICMBlobValue;
import COM.ibm.opicmpdh.transactions.OPICMList;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.middleware.Unicode;

import java.sql.ResultSet;

import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import java.sql.SQLException;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.LockException;
import java.util.StringTokenizer;
import java.util.Stack;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.Vector;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSetGroup;

/**
 *  This Object holds all the required information for an Entity
 *
 *@author     davidbig
 *@created    April 23, 2003
 */

public class EntityItem
extends EANEntity implements EANAddressable, EANTableWrapper {

	/**
	 *@serial
	 */
	final static long serialVersionUID = 1L;

	/**
	 * MAX_LEVEL
	 */
	public final static int MAX_LEVEL = 10;

	private Stack m_stk = null;
	private EANList m_elRestrict = null;
	private EANList m_elRequired = null;
	private EANList m_elReset = null;
	private EANList m_elClassify = null;
	// private boolean m_bLocked = true;
	//
	private boolean[] m_abLevel = null;
	// private boolean[] m_abLevel = new boolean[MAX_LEVEL];
	//DWB removed because of weight
	private boolean m_bWasModifiedInInterval = false;

	private LockGroup m_lockgroup = null;
	private transient EANList uiAttrKeyList = null; // hangon to key used for attributes in a row - may be multiple entities

	//Holds the Status of this EntityItem (default is NEW)
	//private String m_strStatus="New";
	private String m_strLockTime = null;
	private int m_iLastOPWGID = -1;

	private boolean m_bcanEdit = true; // RQ0713072645
	private boolean m_beditDomainChked = false; // RQ0713072645 only check once

	public void dereference(){
		if (m_stk!=null){
			m_stk.clear();
			m_stk = null;
		}
		if (uiAttrKeyList!= null){
			uiAttrKeyList.clear();
			uiAttrKeyList = null;
		}
		m_strLockTime = null;
		if (m_elRestrict != null){
			m_elRestrict.clear();
			m_elRestrict = null;
		}
		if (m_elRequired != null){
			m_elRequired.clear();
			m_elRequired = null;
		}
		if (m_elReset != null){
			m_elReset.clear();
			m_elReset = null;
		}

		if (m_elClassify != null){
			m_elClassify.clear();
			m_elClassify = null;
		}

		if (m_lockgroup!= null){
			m_lockgroup.dereference();
			m_lockgroup = null;
		}

		super.dereference();
	}

	/**
	 *  Gets the version attribute of the EntityItem object
	 *
	 *@return    The version value
	 */
	public String getVersion() {
		return "$Id: EntityItem.java,v 1.587 2013/05/08 18:12:39 wendy Exp $";
	}

	/**
	 *  Main method which performs a simple test of this class
	 *
	 *@param  arg  Description of the Parameter
	 */
	public static void main(String arg[]) {
	}

	/*
	 *  Right Now.. there is only one way to create an EntityItem
	 *  and it is Not through a database call yet
	 */
	/**
	 *  Constructor for the EntityItem object
	 *
	 *@param  _f                              Description of the Parameter
	 *@param  _prof                           Description of the Parameter
	 *@param  _strEntityType                  Description of the Parameter
	 *@param  _iEntityID                      Description of the Parameter
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public EntityItem(EANFoundation _f, Profile _prof, String _strEntityType, int _iEntityID) throws MiddlewareRequestException {
		super(_f, _prof, _strEntityType, _iEntityID);
		//initLevels();
	}

	/**
	 *  Constructor for the EntityItem object
	 *
	 *@param  _f                              Description of the Parameter
	 *@param  _prof                           Description of the Parameter
	 *@param  _ei                             Description of the Parameter
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public EntityItem(EANFoundation _f, Profile _prof, EntityItem _ei) throws MiddlewareRequestException {
		super(_f, _prof, _ei.getEntityType(), _ei.getEntityID());
		setAttribute(_ei);
		setLevels(_ei.getLevels());
	}

	/**
	 *  Constructor for the EntityItem object
	 *
	 *@param  _ei                             Description of the Parameter
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public EntityItem(EntityItem _ei) throws MiddlewareRequestException {
		super(null, _ei.getProfile(), _ei.getEntityType(), _ei.getEntityID());
		setAttribute(_ei);
		setLevels(_ei.getLevels());
	}

	/**
	 *  Constructor for the EntityItem object
	 *
	 *@param  _f                              Description of the Parameter
	 *@param  _ei                             Description of the Parameter
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public EntityItem(EANFoundation _f, EntityItem _ei) throws MiddlewareRequestException {
		//  super(_f, _ei.getProfile(), _ei.getEntityType(), _ei.getEntityID());
		super(_f, (_f == null ? _ei.getProfile() : null), _ei.getEntityType(), _ei.getEntityID());
		setAttribute(_ei);
		setLevels(_ei.getLevels());
	}

	/*
	 *  Here is that stand alone contructor that has to have an entityGroup as a parent coming into it
	 */
	/**
	 *  Constructor for the EntityItem object
	 *
	 *@param  _eg                             Description of the Parameter
	 *@param  _prof                           Description of the Parameter
	 *@param  _db                             Description of the Parameter
	 *@param  _strEntityType                  Description of the Parameter
	 *@param  _iEntityID                      Description of the Parameter
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public EntityItem(EntityGroup _eg, Profile _prof, Database _db, String _strEntityType, int _iEntityID) throws SQLException,
	MiddlewareException, MiddlewareRequestException {

		super(_eg, _prof, _strEntityType, _iEntityID);
		initLevels();

		try {
			ReturnStatus returnStatus = new ReturnStatus();
			ResultSet rs = null;

			String strEnterprise = getProfile().getEnterprise();
			String strValOn = getProfile().getValOn();
			String strEffOn = getProfile().getEffOn();

			//Declare who we are
			EntityItem ei = this;
			EntityGroup eg = _eg;

			// Set up some Att objects
			TextAttribute ta = null;
			SingleFlagAttribute sfa = null;
			StatusAttribute sa = null;
			MultiFlagAttribute mfa = null;
			TaskAttribute tska = null;
			LongTextAttribute lta = null;
			XMLAttribute xa = null;
			BlobAttribute ba = null;
			EANMetaAttribute ma = null;
			EANAttribute att = null;

			try {
				rs = _db.callGBL7545(returnStatus, strEnterprise, _strEntityType, _iEntityID, strValOn, strEffOn);
				//
				// Removed the rdrs to see if processing is faster
				//
				while (rs.next()) {
					int i = 1;
					String strAttributeCode = rs.getString(i++);
					int iNLSID = rs.getInt(i++);
					String strAttributeValue = rs.getString(i++);
					String strValFrom = rs.getString(i++);
					String strValTo = rs.getString(i++);
					String strEffFrom = rs.getString(i++);
					String strEffTo = rs.getString(i++);

					_db.debug(D.EBUG_SPEW,
							"gbl7545:answers:" + strAttributeCode + ":" + iNLSID + ":" + strAttributeValue + ":" + strValFrom +
							":" + strValTo + ":" + strEffFrom + ":" + strEffTo);

					ma = eg.getMetaAttribute(strAttributeCode);
					if (ma == null) {
						continue;
					}

					att = ei.getAttribute(strAttributeCode);
					if (att == null) {
						if (ma.isText()) {
							ta = new TextAttribute(ei, null, (MetaTextAttribute) ma);
							ta.setValFrom(strValFrom);
							ei.putAttribute(ta);
						} else if (ma.isSingle()) {
							sfa = new SingleFlagAttribute(ei, null, (MetaSingleFlagAttribute) ma);
							sfa.setValFrom(strValFrom);
							ei.putAttribute(sfa);
						} else if (ma.isMulti()) {
							mfa = new MultiFlagAttribute(ei, null, (MetaMultiFlagAttribute) ma);
							mfa.setValFrom(strValFrom);
							ei.putAttribute(mfa);
						} else if (ma.isStatus()) {
							sa = new StatusAttribute(ei, null, (MetaStatusAttribute) ma);
							sa.setValFrom(strValFrom);
							ei.putAttribute(sa);
						} else if (ma.isABR()) {
							tska = new TaskAttribute(ei, null, (MetaTaskAttribute) ma);
							tska.setValFrom(strValFrom);
							ei.putAttribute(tska);
						} else if (ma.isLongText()) {
							lta = new LongTextAttribute(ei, null, (MetaLongTextAttribute) ma);
							lta.setValFrom(strValFrom);
							ei.putAttribute(lta);
						} else if (ma.isXML()) {
							xa = new XMLAttribute(ei, null, (MetaXMLAttribute) ma);
							xa.setValFrom(strValFrom);
							ei.putAttribute(xa);
						} else if (ma.isBlob()) {
							ba = new BlobAttribute(ei, null, (MetaBlobAttribute) ma);
							ba.setValFrom(strValFrom);
							ei.putAttribute(ba);
						}
					} else {
						att.setValFrom(strValFrom);
						if (ma.isText()) {
							ta = (TextAttribute) att;
						} else if (ma.isSingle()) {
							sfa = (SingleFlagAttribute) att;
						} else if (ma.isMulti()) {
							mfa = (MultiFlagAttribute) att;
						} else if (ma.isStatus()) {
							sa = (StatusAttribute) att;
						} else if (ma.isABR()) {
							tska = (TaskAttribute) att;
						} else if (ma.isLongText()) {
							lta = (LongTextAttribute) att;
						} else if (ma.isXML()) {
							xa = (XMLAttribute) att;
						} else if (ma.isBlob()) {
							ba = (BlobAttribute) att;
						}
					}

					// OK.. drop the value into the structure
					if (ma.isText()) {
						ta.putPDHData(iNLSID, strAttributeValue);
					} else if (ma.isSingle()) {
						sfa.putPDHFlag(strAttributeValue);
					} else if (ma.isMulti()) {
						mfa.putPDHFlag(strAttributeValue);
					} else if (ma.isStatus()) {
						sa.putPDHFlag(strAttributeValue);
					} else if (ma.isABR()) {
						tska.putPDHFlag(strAttributeValue);
					} else if (ma.isLongText()) {
						lta.putPDHData(iNLSID, Unicode.rtrim(strAttributeValue));
					} else if (ma.isXML()) {
						xa.putPDHData(iNLSID, strAttributeValue);
					} else if (ma.isBlob()) {
						ba.putPDHData(iNLSID, new OPICMBlobValue(iNLSID, strAttributeValue, null));
					} else {
						_db.debug(D.EBUG_ERR, "**Unknown Meta Type for" + strAttributeCode + ":");
					}
				}
			}
			finally {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				_db.commit();
				_db.freeStatement();
				_db.isPending();
			}

		}
		finally {

		}

	}

	/**
	 *  Gets the entityGroup attribute of the EntityItem object
	 *
	 *@return    The entityGroup value
	 */
	public EntityGroup getEntityGroup() {
		return (EntityGroup) getParent();
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _bBrief  Description of the Parameter
	 *@return          Description of the Return Value
	 */
	public String dump(boolean _bBrief) {
		StringBuffer strbResult = new StringBuffer();
		strbResult.append(NEW_LINE + "EntityItem:" + getKey() + ":");
		if (!_bBrief) {
			strbResult.append(NEW_LINE + "Attributes:" + NEW_LINE);
			for (int ii = 0; ii < getAttributeCount(); ii++) {
				EANAttribute att = getAttribute(ii);
				strbResult.append(NEW_LINE + "" + ii + ":" + att.dump(_bBrief));
			}
			if (m_abLevel != null) {
				strbResult.append(NEW_LINE + "Levels:" + NEW_LINE);
				for (int ii = 0; ii < MAX_LEVEL; ii++) {
					if (m_abLevel[ii]) {
						strbResult.append(NEW_LINE + "\tLevel:" + ii);
					}
				}
			}

			strbResult.append(NEW_LINE + "UpLinkItems:" + NEW_LINE);
			for (int ii = 0; ii < getUpLinkCount(); ii++) {
				EANEntity ent = getUpLink(ii);
				strbResult.append(NEW_LINE + "" + ii + ":" + ent.getKey());
			}

			strbResult.append(NEW_LINE + "DownLinkItems:" + NEW_LINE);
			for (int ii = 0; ii < getDownLinkCount(); ii++) {
				EANEntity ent = getDownLink(ii);
				strbResult.append(NEW_LINE + "" + ii + ":" + ent.getKey());
			}
		}

		return strbResult.toString();
	}

	/**
	 * (non-Javadoc)
	 * toString
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		EntityItem ieDown = null;

		boolean bFirstPass = true;
		boolean bRelAss = false;
		StringBuffer strbResult = new StringBuffer();
		EntityGroup eg = getEntityGroup();

		// CR 1547 - hide a column with a meta tag (feature key). and change the to string for the entityid...

		if (eg == null) {
			for (int ii = 0; ii < getAttributeCount(); ii++) {
				EANAttribute att = getAttribute(ii);
				EANMetaAttribute ma = att.getMetaAttribute();
				if (ma != null) {
					if (ma.isNavigate()) {
						strbResult.append( (bFirstPass ? "" : ", ") + att.toString());
						bFirstPass = false;
					}
				} else {
					strbResult.append( (bFirstPass ? "" : ", ") + att.toString());
					bFirstPass = false;
				}
			}
		} else {
			if (eg.isRelator() || eg.isAssoc()) {
				bRelAss = true;
			}
			for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
				EANMetaAttribute ma = eg.getMetaAttribute(ii);
				if (ma != null) {
					if (ma.isNavigate()) {
						EANAttribute att = getAttribute(ma.getAttributeCode());
						strbResult.append( (bFirstPass ? "" : ", ") + (att == null ? "" : att.toString()));
						bFirstPass = false;
					}
				} else {
					EANAttribute att = getAttribute(ma.getAttributeCode());
					strbResult.append( (bFirstPass ? "" : ", ") + (att == null ? "" : att.toString()));
					bFirstPass = false;
				}
			}
		}

		// If there is a down link.. lets go get it!
		ieDown = (EntityItem) getDownLink(0);
		if (ieDown != null && bRelAss) {
			strbResult.append( (strbResult.toString().length() == 0 ? "" : ", ") + ieDown.toString());
		}

		return strbResult.toString();
	}

	/**
	 * toClassificationString
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public String toClassificationString() {

		EntityItem ieDown = null;

		boolean bFirstPass = true;
		boolean bRelAss = false;
		StringBuffer strbResult = new StringBuffer();
		EntityGroup eg = getEntityGroup();

		if (eg == null) {
			strbResult.append(getKey() + ": ");

			for (int ii = 0; ii < getAttributeCount(); ii++) {
				EANAttribute att = getAttribute(ii);
				EANMetaAttribute ma = att.getMetaAttribute();
				if (ma.isClassified()) {
					strbResult.append( (bFirstPass ? "" : ", ") + ma.toString() + "(" + att.toString() + ")");
					bFirstPass = false;
				}
			}
		} else {
			strbResult.append(eg.toString() + ": ");
			if (eg.isRelator() || eg.isAssoc()) {
				bRelAss = true;
			}
			for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
				EANMetaAttribute ma = eg.getMetaAttribute(ii);
				if (ma.isClassified()) {
					EANAttribute att = getAttribute(ma.getAttributeCode());
					strbResult.append( (bFirstPass ? "" : ", ") + ma.toString() + "(" + att.toString() + ")");
					bFirstPass = false;
				}
			}
		}

		// If there is a down link.. lets go get it!
		ieDown = (EntityItem) getDownLink(0);
		if (ieDown != null && bRelAss) {
			strbResult.append("/" + ieDown.toClassificationString());
		}

		return strbResult.toString();
	}

	/*
	 *  Re-assign its current parent to the passed _eg.
	 *  Ensure that the metattributes in each attribute are re-directed to the passed EntityGroup's attribute list
	 */
	/**
	 * reassign
	 *
	 * @param _eg
	 *  @author David Bigelow
	 */
	public void reassign(EntityGroup _eg) {
		setParent(_eg);

		for (int ii = 0; ii < getAttributeCount(); ii++) {
			EANAttribute att = getAttribute(ii);
			att.setParent(this);
			if (_eg == null) {
				att.setMetaAttribute(null);
			} else {
				att.setMetaAttribute(_eg.getMetaAttribute(att.getKey()));
			}
		}
	}

	//
	//  EANAddressable interface
	//

	/**
	 *  Gets the help attribute of the EntityItem object
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The help value
	 */
	public String getHelp(String _str) {
		StringTokenizer st = new StringTokenizer(_str, ":");
		String strEntityType = st.nextToken();
		String strAttributeCode = st.nextToken();

		// Ok. if the entitytype = thisguys entitytype then we are ok.. otherwise.. we have to
		// look at the downlink version to get the proper answer.
		if (strEntityType.equals(getEntityType())) {
			EANAttribute att = getAttribute(strAttributeCode);
			if (att == null) {
				return "No Help is Availabe for this Attribute";
			} else {
				return att.getHelp(EANAttribute.VALUE);
			}
		} else {
			EANEntity ei = getDownLink(0);
			if (ei == null) {
				return "No Help is Available for this Attribute";
			} else {
				EANAttribute att = ei.getAttribute(strAttributeCode);
				if (att == null) {
					return "No Help is Available for this Attribute";
				} else {
					return att.getHelp(EANAttribute.VALUE);
				}
			}
		}
	}

	/*
	 *  This returns the object that we are interested in based on the _str key
	 *  in this case.. the key is the EntityType.AttributeType
	 */
	/**
	 *  Description of the Method
	 *
	 *@param  _str  Description of the Parameter
	 *@param  _b    Description of the Parameter
	 *@return       Description of the Return Value
	 */
	public Object get(String _str, boolean _b) {
		KeyPath kPath = new KeyPath();
		kPath.parse(_str);
		String strEntityType = kPath.getEntityType();
		String strAttributeCode = kPath.getAttributeCode();

		if (strAttributeCode==null){
			return "Invalid key "+_str;
		}
		if (!isVEEdit() && uiAttrKeyList == null){ // VEEdit handles its own rowlist
			getRowList();// populate the list and use it to find the specified attribute
		}
		//correct finding wrong thing
		if (uiAttrKeyList != null){
			Object obj = uiAttrKeyList.get(_str);
			if (obj instanceof Implicator){
				EANAttribute attr = (EANAttribute)((Implicator)obj).getParent();
				if (attr !=null){
					return attr.get(EANAttribute.VALUE, _b);
				}
			}
		}

		// Ok. if the entitytype = thisguys entitytype then we are ok.. otherwise.. we have to
		// look at the downlink version to get the proper answer.
		if (strEntityType.equals(getEntityType())) {
			EANAttribute att = getAttribute(strAttributeCode);
			if (att == null) {
				return "";
			} else {
				return att.get(EANAttribute.VALUE, _b);
			}
		} else {
			//added for edit relator
			String strRelTag = null;
			EntityItem ei = null;

			EntityGroup eg = getEntityGroup();
			EntityList entl = eg.getEntityList();

			// GAB 11/17/04
			// We might need an extra indicator telling us which way to look for the EntityItem
			// when we are editing in context of multiple Entities.
			EntityGroup egGet = entl.getEntityGroup(strEntityType);
			if (egGet == null) {
				egGet = entl.getParentEntityGroup();
			}
			strRelTag = kPath.getRelTag();
			if (strRelTag == null) {
				strRelTag = (egGet.isRelator() ? "R" : "C");
			}

			if (entl.isEditRelatorOnly() || entl.showRelParentChild(getEntityType())) {
				if (strEntityType.equals(eg.getEntity1Type()) && strRelTag.equals("P")) {
					for (int i = 0; i < getUpLinkCount(); i++) {
						EntityItem eitmp = (EntityItem) getUpLink(i);
						if (eitmp.getEntityType().equals(eg.getEntity1Type())) {
							ei = eitmp;
							break;
						}
					}
					// Should not be null at this point
				} else {
					for (int i = 0; i < getDownLinkCount(); i++) {
						EntityItem eitmp = (EntityItem) getDownLink(i);
						if (eitmp.getEntityType().equals(eg.getEntity2Type())) {
							ei = eitmp;
							break;
						}
					}
				}
			} else if (entl != null && entl.isCreateParent()) {
				ei = (EntityItem) getUpLink(0);
			} else {
				ei = (EntityItem) getDownLink(0);
			}

			if (ei == null) {
				return "Cannot find entityItem " + strEntityType;
			} else {
				// finds wrong one EANAttribute att = getAttributeFromEntityItem(ei, strAttributeCode);
				EANAttribute att = ei.getAttribute(strAttributeCode);
				if (att == null) {
					return "";
				} else {
					return att.get(EANAttribute.VALUE, _b);
				}
			}
		}
	}

	/**
	 * Fan out and look at everything, because hey ... its what ... we .. .got ... to ... do!!
	 * (Yes this is horribly inefficient)
	 *
	 * LSEOPRODSTRUCT str MODEL:PDHDOMAIN:C
getAttributeFromEntityItem  for PDHDOMAIN returning PRODSTRUCT att * pSeries
it didnt find the MODEL.PDHDOMAIN but returned PRODSTRUCT.PDHDOMAIN
	 * /
    protected final EANAttribute getAttributeFromEntityItem(EntityItem _ei, String _strAttributeCode) {
        EANAttribute att = _ei.getAttribute(_strAttributeCode);
        // 1) uplinks
        if (att == null && _ei.getEntityGroup().isRelator()) {
            for (int i = 0; i < _ei.getUpLinkCount(); i++) {
                EntityItem eitmp = (EntityItem) _ei.getUpLink(i);

                att = eitmp.getAttribute(_strAttributeCode);
                if (att != null) {
                    return att;
                }
            }
        }
        // now downlinks...
        if (att == null && _ei.getEntityGroup().isRelator()) {
            for (int i = 0; i < _ei.getDownLinkCount(); i++) {
                EntityItem eitmp = (EntityItem) _ei.getDownLink(i);

                att = eitmp.getAttribute(_strAttributeCode);
                if (att != null) {
                    return att;
                }
            }
        }
        return att;
    }*/

	/*
	 *  This rollback an attribute for the given string index into its structure
	 */
	/**
	 *  Description of the Method
	 *
	 *@param  _str  Description of the Parameter
	 */
	public void rollback(String _str) {

		StringTokenizer st = new StringTokenizer(_str, ":");
		String strEntityType = st.nextToken();
		String strAttributeCode = st.nextToken();

		// Ok. if the entitytype = thisguys entitytype then we are ok.. otherwise.. we have to
		// look at the downlink version to get the proper answer.
		if (strEntityType.equals(getEntityType())) {
			EANAttribute att = getAttribute(strAttributeCode);
			if (att == null) {
				D.ebug(D.EBUG_SPEW, "EntityItem.rollback.1 *** "+getKey()+" " + strAttributeCode + " cannot be found.");
			} else {
				att.rollback();
			}
		} else {
			EANEntity ei = getDownLink(0);
			if (ei == null) {
			} else {
				EANAttribute att = ei.getAttribute(strAttributeCode);
				if (att == null) {
					ei = getUpLink(0);
					if (ei == null) {
					} else {
						att = ei.getAttribute(strAttributeCode);
						if (att == null) {
							D.ebug(D.EBUG_SPEW, "EntityItem.rollback.2 *** "+getKey()+" "+ei.getKey()+" " + strAttributeCode + " cannot be found.");
						} else {
							att.rollback();
						}
					}
				} else {
					att.rollback();
				}
			}
		}
	}

	/**
	 * Get and/or generate the specified attribute
	 * @param strAttributeCode
	 * @return
	 */
	protected EANFoundation getThisEANObject(String strAttributeCode) {
		EANAttribute att = getAttribute(strAttributeCode);
		if (att == null) {
			try {
				att = (EANAttribute)genAttribute(strAttributeCode);
			}
			catch (Exception x) {
				x.printStackTrace();
			}
		}

		return att;
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.objects.EANEntity#putAttribute(COM.ibm.eannounce.objects.EANAttribute)
	 */
	public void putAttribute(EANAttribute _att) {
		super.putAttribute(_att);

		if (uiAttrKeyList!= null){ // make sure the correct attribute object is referenced
			String reltag = "C";
			if (getEntityGroup()!=null){
				reltag = (getEntityGroup().isRelator() ? "R" : "C");
			}

			String strKey = getEntityType() + ":" +_att.getKey()+":" +reltag;
			Object obj = uiAttrKeyList.get(strKey);
			if (obj == null){
				// try again with just entitytype:attributecode because search sets key that way
				strKey = getEntityType() + ":" + _att.getKey();
				obj = uiAttrKeyList.get(strKey);
				if (obj ==null){
					strKey = _att.getKey();
					//try again with just attributecode
					obj = uiAttrKeyList.get(strKey);
				}
			} 

			if (obj !=null){
				// replace it
				try{
					Implicator imp = new Implicator(_att, null, strKey);
					uiAttrKeyList.put(imp);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	/*
	 *  This returns the object that we are interested in based on the _str key
	 *  in this case.. the key is the EntityType.AttributeType
	 */
	/**
	 *  Gets the eANObject attribute of the EntityItem object
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The eANObject value
	 */
	public EANFoundation getEANObject(String _str) {
		KeyPath kPath = new KeyPath();
		kPath.parse(_str);
		String strEntityType = kPath.getEntityType();
		String strAttributeCode = kPath.getAttributeCode();
		String strRelTag = kPath.getRelTag();

		if (strAttributeCode==null){
			return null;
		}
		if (!isVEEdit() && uiAttrKeyList == null){ // VEEdit handles its own rowlist
			getRowList();// populate the list and use it to find the specified attribute
		}
		// prevent finding wrong thing
		if (uiAttrKeyList != null){
			Object obj = uiAttrKeyList.get(_str);
			if (obj instanceof Implicator){
				EANAttribute attr = (EANAttribute)((Implicator)obj).getParent();
				if (attr !=null){
					return attr;
				}
			}
			// try again with just entitytype:attributecode because search sets key that way
			obj = uiAttrKeyList.get(strEntityType+":"+strAttributeCode);
			if (obj instanceof Implicator){
				EANAttribute attr = (EANAttribute)((Implicator)obj).getParent();
				if (attr !=null){
					return attr;
				}
			}
			// try again adding reltag
			if (strRelTag==null){
				obj = uiAttrKeyList.get(strEntityType+":"+strAttributeCode+":"+
						(getEntityGroup().isRelator() ? "R" : "C"));
				if (obj instanceof Implicator){
					EANAttribute attr = (EANAttribute)((Implicator)obj).getParent();
					if (attr !=null){
						return attr;
					}
				}
			}
		}

		// Ok. if the entitytype = thisguys entitytype then we are ok.. otherwise.. we have to
		// look at the downlink version to get the proper answer.
		if (strEntityType.equals(getEntityType())) {
			return getThisEANObject(strAttributeCode);
		}

		//added for edit relator
		EntityItem ei = null;

		EntityGroup eg = getEntityGroup();
		EntityList entl = eg.getEntityList();

		// GAB 11/17/04
		// We might need an extra indicator telling us which way to look for the EntityItem
		// when we are editing in context of multiple Entities.
//		System.out.println(" getting EG for: " + strEntityType);
//		System.out.println(" myType        : " + getEntityType());
		EntityGroup egGet = entl.getEntityGroup(strEntityType);
		if (egGet == null) {
			egGet = entl.getParentEntityGroup();
		}

		if (strRelTag == null) {
			strRelTag = (egGet.isRelator() ? "R" : "C");
		}
		//
		if (entl.isEditRelatorOnly() || entl.showRelParentChild(getEntityType())) {
			if (strEntityType.equals(eg.getEntity1Type()) && strRelTag.equals("P")) {
				for (int i = 0; i < getUpLinkCount(); i++) {
					EANFoundation found = getUpLink(i);
					EntityItem  eitmp = (EntityItem) found;

					if (eitmp.getEntityType().equals(eg.getEntity1Type())) {
						ei = eitmp;
						break;
					}
				}
				// Should not be null at this point
			} else if (eg.isRelator()) {
				EntityItem eiTmp = (EntityItem)getUpLink(0);											//6MV4U7
				if (eiTmp != null && eiTmp.getEntityType().equals(strEntityType)
						&& strRelTag.equals("P")) {	//MN33607291 look for parent, error when MODEL:MODELREL:MODEL
					ei = eiTmp;																			//6MV4U7
				}																						//6MV4U7
				if (ei == null) {																		//6MV4U7
					eiTmp = (EntityItem)getDownLink(0);													//6MV4U7
					if (eiTmp != null && eiTmp.getEntityType().equals(strEntityType)) {					//6MV4U7
						ei = eiTmp;																		//6MV4U7
					}																					//6MV4U7
				}																						//6MV4U7
			} else {
				for (int i = 0; i < getDownLinkCount(); i++) {
					EntityItem eitmp = null;
//					MN26341495						EANFoundation found = getUpLink(i);
					EANFoundation found = getDownLink(i); //MN26341495
					eitmp = (EntityItem) found;

					/*
                     MN26341495
                     eitmp was null because was using uplink instead of down.
					 */
					if (eitmp.getEntityType().equals(eg.getEntity2Type())) {
						ei = eitmp;
						break;
					}
				}
			}

			// TODO This may be baad
		} else if (entl != null && entl.isCreateParent()) {
			ei = (EntityItem) getUpLink(0);
		} else {
			ei = (EntityItem) getDownLink(0);
			// when entered with str=(MODEL:MACHTYPEATR:C) for WWSEOSWPRODSTRUCT, need to go down 2
			// or (SWFEATURE:COMNAME:P) need to go up after down
			if (ei!=null && !ei.getEntityType().equals(strEntityType)){
				if(strRelTag.equals("P") &&ei.getUpLinkCount()>0){
					for(int u=0; u<ei.getUpLinkCount(); u++){
						EntityItem upitem = (EntityItem) ei.getUpLink(u);
						if (upitem.getEntityType().equals(strEntityType)){
							ei=upitem;
							break;
						}
					}
				}
				else if (ei.getDownLinkCount()>0) {
					for(int u=0; u<ei.getDownLinkCount(); u++){
						EntityItem dnitem = (EntityItem) ei.getDownLink(u);
						if (dnitem.getEntityType().equals(strEntityType)){
							ei=dnitem;
							break;
						}
					}
				}
				if(!ei.getEntityType().equals(strEntityType)){ // couldnt find the entity
					ei=null;
				}
			}
		}
		// TODO END
		if (ei == null) {
			return null;
		} else {
			EANAttribute att = ei.getAttribute(strAttributeCode);
			if (att == null) {
				try {
					return ei.genAttribute(strAttributeCode);
				}
				catch (MiddlewareException mrx) {
					mrx.printStackTrace();
					if (ei.getEntityGroup().isRelator()) {
						String strRelKey = strEntityType + ":" + strAttributeCode + ":R";
						return ei.getEANObject(strRelKey);
					}
				}
				catch (Exception x) {
					x.printStackTrace();
				}
			} else {
				return att;
			}
		}
		// Whoops nothing to return.
		return null;
	}

	/*
	 *  Nothing is editable yet . so it is always false
	 */
	/**
	 *  Description of the Method
	 *
	 *@param  _str                          Description of the Parameter
	 *@param  _o                            Description of the Parameter
	 *@return                               Description of the Return Value
	 *@exception  EANBusinessRuleException  Description of the Exception
	 */
	public boolean put(String _str, Object _o) throws EANBusinessRuleException {
		String ovalue = "null";
		if (_o instanceof MetaFlag[]){
			ovalue="";
			MetaFlag[] mf = (MetaFlag[])_o;
			for (int i=0; i<mf.length; i++){
				MetaFlag flag = mf[i];
				if (flag.isSelected()){
					ovalue+=mf[i].toString()+",";
				}
			}
		}else if (_o != null){
			ovalue = _o.toString();
		}
		D.ebug(D.EBUG_SPEW, "EntityItem.put() called on " + getKey() + " for "+_str+" - \"" +ovalue+ "\"");

//		6LY42Z        StringTokenizer st = new StringTokenizer(_str, ":");
//		6LY42Z        String strEntityType = st.nextToken();
//		6LY42Z        String strAttributeCode = st.nextToken();

		KeyPath kPath = new KeyPath(); //6LY42Z
		kPath.parse(_str); //6LY42Z
		String strEntityType = kPath.getEntityType(); //6LY42Z
		String strAttributeCode = kPath.getAttributeCode(); //6LY42Z
		String strRelTag = kPath.getRelTag();

		EANAttribute att = null;
		EntityItem ei = this;

		if (strEntityType.equals(getEntityType())) {
			att = getAttribute(strAttributeCode);
		} else {
			boolean lookup = true;
			if (strRelTag!=null && strRelTag.equals("C")){
				lookup = false;
			}
			EntityGroup eg = getEntityGroup();
			if (eg!= null){
				EntityList list = eg.getEntityList();
				if (list!= null){
					//may need to look up first, SWFEATURE->SWPRODSTRUCT->MODEL
					//MODEL and SWFEATURE share some attributes MN38316169
					lookup = list.isCreateParent(); // if true, look up first
				}
			}
//			6LY42Z            ei = (EntityItem) getDownLink(0);
//			ei = VEPath.getEntityItem(this, strEntityType, true); this gets wrong one when WG->WGWGCREATE->WG//6LY42Z
			ei = VEPath.getEntityItem(this, strEntityType, lookup);
			if (ei==null){//MN38316169 must look up for creating SWFEATURE from SWPRODSTRUCT
				ei = VEPath.getEntityItem(this, strEntityType, !lookup);
			}
//			6LY42Z            if (ei != null) {												//MN25287512
//			6LY42Z				if (!ei.getEntityType().equals(strEntityType)) {			//MN25287512
//			6LY42Z					if (hasUpLinks()) {										//MN25287512
//			6LY42Z						System.out.println("SCBBCS uplink");
//			6LY42Z						ei = (EntityItem) getUpLink(0);						//MN25287512
//			6LY42Z					}														//MN25287512
//			6LY42Z				}															//MN25287512
//			6LY42Z			}																//MN25287512
		}

		if (ei == null) {
			return false;
		} else {
			att = ei.getAttribute(strAttributeCode);
		}

		if (att == null) {
			// This is the time to make something if nothing is there...
			att = (EANAttribute) getEANObject(_str);
		}

		// This returns an array of Strings.. which will be
		// Repackaged into the proper form
		// which needs to be an array of RolColKeys. that need to be refreshed

		// This is for dependent flags..
		// this is for newly required fields

		D.ebug(D.EBUG_SPEW, "EntityItem.put() for " + getKey() + "." + att.getAttributeCode() + " is \"" +ovalue+ "\"");
		
		att.put(_o);
		return true;
	}

	/**
	 *  Here is where we determine what is editable.. Based upon the string and its
	 *  tokens and the meta it points to ...
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The editable value
	 */
	public boolean isEditable(String _str) {
		if (isVEEdit() && !isEntityGroupEditable(_str)) {
			return false;
		}

		// Lets rely on the attribute itself
		EANAttribute att = (EANAttribute) getEANObject(_str);
		if (att == null) {
			return false;
		}

		return att.isEditable();
	}

	/**
	 *  Here is where we determine what is editable.. Based upon the string and its
	 *  tokens and the meta it points to ... Gets the locked attribute of the
	 *  EntityItem object
	 *
	 *@param  _str          Description of the Parameter
	 *@param  _rdi          Description of the Parameter
	 *@param  _db           Description of the Parameter
	 *@param  _ll           Description of the Parameter
	 *@param  _prof         Description of the Parameter
	 *@param  _lockOwnerEI  Description of the Parameter
	 *@param  _iLockType    Description of the Parameter
	 *@param  _bCreateLock  Description of the Parameter
	 *@return               The locked value
	 * @param _strTime
	 */
	public boolean isLocked(String _str, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof,
			EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
		KeyPath kPath = new KeyPath();
		kPath.parse(_str);

		String strEntityType = kPath.getEntityType();

		if (strEntityType.equals(getEntityType())) {
			return isLocked(_rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, _strTime, _bCreateLock);
		} else {

			EntityItem ei = (EntityItem) getDownLink(0);
			EntityGroup eg = ei.getEntityGroup();
			if (eg != null) {
				EntityList entl = eg.getEntityList();
				if (entl != null && entl.isCreateParent()) {
					ei = (EntityItem) getUpLink(0);
				}
			}

			if (ei == null) {
				return false;
			}
			return ei.isLocked(_rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, _strTime, _bCreateLock);
		}
	}

	/**
	 *  Gets the lockGroup attribute of the EntityItem object
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The lockGroup value
	 */
	public LockGroup getLockGroup(String _str) {
		// Lets get the Meta Attribute based upon the string
		StringTokenizer st = new StringTokenizer(_str, ":");
		String strEntityType = st.nextToken();
		st.nextToken();

		if (strEntityType.equals(getEntityType())) {
			return getLockGroup();
		} else {
			EntityItem ei = (EntityItem) getDownLink(0);
			if (ei == null) {
				return null;
			}
			return ei.getLockGroup();
		}
	}

	/**
	 *  Gets the allLockGroups attribute of the EntityItem object
	 *
	 *@return    The allLockGroups value
	 */
	public LockGroup[] getAllLockGroups() {
		LockGroup lg1[] = new LockGroup[1];
		LockGroup lg2[] = new LockGroup[2];

		EntityGroup eg = getEntityGroup();

		if (eg.isRelator() || eg.isAssoc()) {
			EntityItem ei = (EntityItem) getDownLink(0);
			lg2[0] = getLockGroup();
			if (ei != null) {
				lg2[1] = getLockGroup();
			}
			return lg2;
		}

		lg1[0] = getLockGroup();

		return lg1;
	}

	/**
	 *  Description of the Method
	 *
	 * @return LockGroup
	 * @param _rdi
	 * @param _db
	 * @param _prof
	 * @param _ll
	 * @param _lockOwnerEI
	 * @param _strLockOwner
	 * @param _iLockType
	 * @param _bCreateLock
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	 * @throws java.sql.SQLException
	 * @throws java.rmi.RemoteException
	 */
	public LockGroup refreshLockGroup(RemoteDatabaseInterface _rdi, Database _db, Profile _prof, LockList _ll,
			EntityItem _lockOwnerEI, String _strLockOwner, int _iLockType, boolean _bCreateLock) throws
			MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException, RemoteException {
		EntityItem ei = new EntityItem(null, _prof, getEntityType(), getEntityID());
		if (_rdi != null) {
			m_lockgroup = _rdi.getLockGroup(_prof, _lockOwnerEI, ei, _strLockOwner, _iLockType, _bCreateLock);
		} else if (_db != null) {
			m_lockgroup = _db.getLockGroup(_prof, _lockOwnerEI, ei, _strLockOwner, _iLockType, _bCreateLock);
		}

		if (_ll != null && m_lockgroup != null) {
			_ll.putLockGroup(m_lockgroup);
		}
		return m_lockgroup;
	}

	/*
	 *  If _rdi or _db is not null, this method would lock the entityitem and return if the lockgroup has exclusive lock or not.
	 *  Otherwise, it would only return the result of the lockgroup's exclusivelock.
	 */
	/**
	 *  Gets the locked attribute of the EntityItem object
	 *
	 *@param  _rdi          Description of the Parameter
	 *@param  _db           Description of the Parameter
	 *@param  _ll           Description of the Parameter
	 *@param  _prof         Description of the Parameter
	 *@param  _lockOwnerEI  Description of the Parameter
	 *@param  _iLockType    Description of the Parameter
	 *@param  _bCreateLock  Description of the Parameter
	 *@return               The locked value
	 * @param _strTime
	 */
	public boolean isLocked(RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI,
			int _iLockType, String _strTime, boolean _bCreateLock) {
		String strLockOwner = null;
		boolean binit = m_lockgroup == null;

		//this is for new entityitem
		if (getEntityID() <= 0) {
			return true;
		}

		if (isVEEdit() && !isEntityGroupEditable()) {
			return false;
		}

		// check the locklist first to see if this profile has the lock
		strLockOwner = _lockOwnerEI.getKey();
		if (_ll == null) {
			return false;
		} else {
			LockGroup lg = m_lockgroup;
			if (lg != null && lg.hasExclusiveLock(_lockOwnerEI, strLockOwner, _prof)) {
				return true;
			}
		}

		if (m_strLockTime == null) {
			m_strLockTime = "";
		}

		// O.K.  If we have made it this far.. either no one has locked this guy yet..
		// or the lock is currently thought to be held by someone else
		// so lets refresh

		// Only get get a lock if its the first time.. or m_strLockKey is differentfrom the last request
		if (!m_strLockTime.equals(_strTime) || binit) {
			try {

				if (!binit){ // may need to update required lists
					if (m_lockgroup != null) {
						boolean sameowner = false;
						for (int i=0; i<m_lockgroup.getLockItemCount(); i++){
							LockItem li = m_lockgroup.getLockItem(i);
							if(li.getLockOwner().equals(strLockOwner)){
								sameowner = true;
								break;
							}
						}
						if (!sameowner){
							binit=true;  // force reload
						}
					}
				}
				refreshLockGroup(_rdi, _db, _prof, _ll, _lockOwnerEI, strLockOwner, _iLockType, _bCreateLock);
				m_strLockTime = _strTime;
			}
			catch (Exception x) {
				x.printStackTrace();
			}
		}

		// If I am not null something has this locked
		if (m_lockgroup != null) {
			// If I have this lock do some refreshing.. and return true
			if (m_lockgroup.hasExclusiveLock(_lockOwnerEI, strLockOwner, _prof)) {
				if (binit) {
					// When we first get the lock.. let refresh only those that are immediate
					refreshRestrictions();
					refreshRequired();
					refreshClassifications();
					refreshDefaults();
				}
//				System.out.println("    SCBBCS exclusive lock 2");
				return true;
			}
		}

//		System.out.println("    SCBBCS returning default");
		return false;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _str          Description of the Parameter
	 *@param  _lockOwnerEI  Description of the Parameter
	 *@param  _prof         Description of the Parameter
	 *@return               Description of the Return Value
	 */
	public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
		KeyPath kPath = new KeyPath();
		kPath.parse(_str);

		String strEntityType = kPath.getEntityType();
		if (strEntityType.equals(getEntityType())) {
			return hasLock(_lockOwnerEI, _prof);
		} else {
			EntityItem ei = (EntityItem) getDownLink(0);
			if (ei != null) {
				EntityGroup eg = ei.getEntityGroup();
				if (eg != null) {
					EntityList entl = eg.getEntityList();
					if (entl != null && entl.isCreateParent()) {
						ei = (EntityItem) getUpLink(0);
					}
				}
			} else {
				return false;
			}
			return ei.hasLock(_lockOwnerEI, _prof);
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _lockOwnerEI  Description of the Parameter
	 *@param  _prof         Description of the Parameter
	 *@return               Description of the Return Value
	 */
	public boolean hasLock(EntityItem _lockOwnerEI, Profile _prof) {
		String strLockOwner = _lockOwnerEI.getKey();
		//this is for new entityitem
		if (getEntityID() <= 0) {
			refreshRequired();
			return true;
		}

		if (m_lockgroup != null) {
			boolean locked = m_lockgroup.hasExclusiveLock(_lockOwnerEI, strLockOwner, _prof);
			return locked;
		}

		return false;
	}

	/*
	 *  This can only be set by the LockList Object
	 */
	/**
	 *  Sets the lock attribute of the EntityItem object
	 *
	 *@param  _b  The new lock value
	 * /
    protected void setLock(boolean _b) {
        m_bLocked = _b;
    }*/

	/**
	 *  Gets the new attribute of the EntityItem object
	 *
	 *@return    The new value
	 */
	public boolean isNew() {
		if (getEntityID() < 0){
			if(!isVEEdit()){
				return true;
			}else{
				// this item maybe new but it may not be editable by the VEEdit action
				//EntityList thelist = getEntityGroup().getEntityList();
				//ExtractActionItem ean = (ExtractActionItem)thelist.getParentActionItem();
				//return (ean.isEditable(getEntityType()) || ean.isCreatable(getEntityType()));

				EntityList thelist = getEntityGroup().getEntityList();
				ExtractActionItem ean = (ExtractActionItem)thelist.getParentActionItem();
				StringTokenizer st = new StringTokenizer(ean.getVEPath(),":"); // D:MODELBOM:BOM
				//String pathdir =
				st.nextToken();
				String pathreltype = st.nextToken();
				String pathtype = st.nextToken();

				if (pathreltype.equals(getEntityType())||pathtype.equals(getEntityType())){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * am i new?
	 * @return boolean
	 * @author tony
	 */
	protected boolean amNew() {
		return getEntityID() < 0;
	}

	/**
	 *  Sets the attributeOwner attribute of the EntityItem object
	 */
	protected void setAttributeOwner() {
		for (int ii = 0; ii < getAttributeCount(); ii++) {
			EANAttribute att = getAttribute(ii);
			att.setParent(this);
		}
	}

	/*
	 *  Generates an Attribute
	 *  and places it in the list
	 */
	/**
	 *  Description of the Method
	 *
	 *@param  _strAttributeCode               Description of the Parameter
	 *@return                                 Description of the Return Value
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	protected EANFoundation genAttribute(String _strAttributeCode) throws MiddlewareRequestException {
		TextAttribute ta = null;
		LongTextAttribute lta = null;
		XMLAttribute xa = null;
		SingleFlagAttribute sfa = null;
		MultiFlagAttribute mfa = null;
		StatusAttribute sa = null;
		TaskAttribute tska = null;
		BlobAttribute ba = null;
		TextAttribute taDerived = null;

		EntityGroup eg = getEntityGroup();
		EANMetaAttribute ma = eg.getMetaAttribute(_strAttributeCode);

		T.est(ma != null, getKey()+" MetaAttribute cannot be found to getNewAttribute." + _strAttributeCode);

		switch (ma.getAttributeType().charAt(0)) {

		case 'T':
		case 'I':
			ta = new TextAttribute(this, null, (MetaTextAttribute) ma);
			putAttribute(ta);
			return ta;
		case 'L':
			lta = new LongTextAttribute(this, null, (MetaLongTextAttribute) ma);
			putAttribute(lta);
			return lta;
		case 'X':
			xa = new XMLAttribute(this, null, (MetaXMLAttribute) ma);
			putAttribute(xa);
			return xa;
		case 'U':
			sfa = new SingleFlagAttribute(this, null, (MetaSingleFlagAttribute) ma);
			putAttribute(sfa);
			return sfa;
		case 'F':
			mfa = new MultiFlagAttribute(this, null, (MetaMultiFlagAttribute) ma);
			putAttribute(mfa);
			return mfa;
		case 'S':
			sa = new StatusAttribute(this, null, (MetaStatusAttribute) ma);
			putAttribute(sa);
			return sa;
		case 'A':
			tska = new TaskAttribute(this, null, (MetaTaskAttribute) ma);
			putAttribute(tska);
			return tska;
		case 'B':
			ba = new BlobAttribute(this, null, (MetaBlobAttribute) ma);
			putAttribute(ba);
			return ba;
		case 'D':
			if (ma.getAttributeType().charAt(1) == 'T') {
				taDerived = new TextAttribute(this, null, (MetaTextAttribute) ma);
				putAttribute(taDerived);
				return taDerived;
			}

		default:
			break;
		}
		// could not get anything
		return null;
	}

	/**
	 *  Gets the entityItemTable attribute of the EntityItem object
	 *
	 *@return    The entityItemTable value
	 */
	public RowSelectableTable getEntityItemTable() {

		// First.. do some checking for nulls
		EntityList el = null;

		EntityGroup eg = getEntityGroup();
		EntityGroup egDown = eg;
		if (eg == null) {
			D.ebug(D.EBUG_SPEW, " 1.1 "+getKey()+" EntityGroup is null in getEntityItemTable()");
			return null;
		}

		el = eg.getEntityList();
		if (el == null) {
			D.ebug(D.EBUG_SPEW, " 1.2 "+getKey()+" EntityList is null in getEntityItemTable()");
			return null;
		}

		if (eg.isRelator() || eg.isAssoc()) {
			if (!el.isEditEntityOnly()) {
				if (el.isCreateParent()) {
					egDown = el.getEntityGroup(eg.getEntity1Type());
					if (egDown == null) {
						D.ebug(D.EBUG_SPEW, " 1.3 "+getKey()+" Entity1Type (" + eg.getEntity1Type() + ") cannot be found in the entitylist");
						return null;
					}

				} else {
					egDown = el.getEntityGroup(eg.getEntity2Type());
					if (egDown == null) {
						D.ebug(D.EBUG_SPEW, " 1.3 "+getKey()+" Entity2Type (" + eg.getEntity2Type() + ") cannot be found in the entitylist");
						return null;
					}
				}
			}
		}
		// We are in normal edit mode here
		//     disableClassifyMode();
		return new RowSelectableTable(this, getLongDescription());
	}

	/***
	 * maintain a list for UI access to improve performance and prevent finding the wrong key
	 * when several entities have the same attribute or the structure has many levels like
	 * LSEO->LSEOPRODSTRUCT->PRODSTRUCT-FEATURE and MODEL
	 */
	private void updateUIKeyList(EANList otherList){
		for (int i = 0; i < otherList.size(); i++) {
			uiAttrKeyList.put(otherList.getAt(i));
		}
	}

	/*
	 *  Here we must build an array Attributes.. buried in Implicators
	 *  with a unique Key. (EntityType.AttributeCode)
	 *  and the Entity2Type.
	 *
	 *  Make sure if we were created w a Navigate Purpose in mind.. only include the
	 *  navigate meta attributes as columns.
	 *
	 *  Gets the rowList attribute of the EntityItem object
	 *  These are the visible attributes for the current entity and any specified structure
	 *  in vertical edit mode only
	 *
	 *@return    The rowList value
	 */
	public EANList getRowList() {
		EntityList elParent = null;
		EANActionItem aiParent = null;
		MetaColumnOrderGroup mcog = null;

		EANList el = new EANList();
		uiAttrKeyList = new EANList();
		EntityGroup eg = (EntityGroup) getParent();

		refreshClassifications();

		if (eg == null) {
			D.ebug(D.EBUG_SPEW, "EntityItem.getRowList() *** "+getKey()+" EntityGroup is null");
			return el;
		}

		elParent = (EntityList) eg.getParent();
		if (elParent == null) {
			D.ebug(D.EBUG_SPEW, "EntityItem.getRowList() *** "+getKey()+" EntityList is null");
			return el;
		}
		aiParent = elParent.getParentActionItem();

		// If we are a relator.. lets  get the Child EntityList
		if (!isVEEdit() && (eg.isRelator() || eg.isAssoc())) {
			boolean bCreateParent =  elParent.isCreateParent();

			if (bCreateParent) {
				EntityItem eiUp = (EntityItem) getUpLink(0);
				if (eiUp != null) {
					// get the parent keys for vertical edit
					el = eiUp.getRowList();
					// get all keys for the parent
					updateUIKeyList(eiUp.uiAttrKeyList);
				}
			} else {
				EntityItem eiDown = (EntityItem) getDownLink(0);
				if (eiDown != null) {
					// get the child keys for vertical edit
					el = eiDown.getRowList();
					// get all keys for the child
					updateUIKeyList(eiDown.uiAttrKeyList);
				}
			}

			// for edit relator, add columns for parent entity
			if (elParent.isEditRelatorOnly() || elParent.showRelParentChild(getEntityType())) {
				EntityItem eiUp = null;
				for (int i = 0; i < getUpLinkCount(); i++) {
					EntityItem eitmp = (EntityItem) getUpLink(i);
					if (eitmp.getEntityType().equals(eg.getEntity1Type())) {
						eiUp = eitmp;
						break;
					}
				}
				if (eiUp != null) {
					//if relator to relator and edit second relator, stop 
					//edit PRODSTRUCTWARR from WARR PRODSTRUCT-PRODSTRUCTWARR-WARR causes stack overflow
					EntityGroup upEg = eiUp.getEntityGroup();
					if(eg.isRelator() && upEg!=null && upEg.isRelator()){
						D.ebug(D.EBUG_WARN, "EntityItem.getRowList() *** "+getKey()+
								" stopping editrelator recursion because parent "+eiUp.getKey()+" are both relators");
						eiUp=null;
					}
				}
				if (eiUp != null) {
					EANList rlist = eiUp.getRowList();
					// get keys for the parent but add with 'P'arent key
					for (int i = 0; i < rlist.size(); i++) {
						try {
							Implicator imp = (Implicator) rlist.getAt(i);
							//avoid lookup imp = new Implicator(eiUp.getEANObject(imp.getKey()), null,
							imp = new Implicator(imp.getParent(), null,
									imp.getKey().substring(0, imp.getKey().length() - 2) + ":P");
							el.put(imp);
						}
						catch (Exception excep) {
							excep.printStackTrace();
						}
					}
					// update UI keys but modify implicator key
					for (int i = 0; i < eiUp.uiAttrKeyList.size(); i++) {
						try {
							Implicator imp = (Implicator) eiUp.uiAttrKeyList.getAt(i);
							imp = new Implicator(imp.getParent(), null,
									imp.getKey().substring(0, imp.getKey().length() - 2) + ":P");
							uiAttrKeyList.put(imp);
						}
						catch (Exception excep) {
							excep.printStackTrace();
						}
					}
				}
			}
		}

		for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
			Object o = eg.getMeta(ii);
			if (o instanceof Implicator) {
				Implicator imp = (Implicator) o;
				// new VEEdit
				try {
					EANFoundation ean = getEANObject(imp.getKey());
					if (ean == null) {
						ean = genAttribute(imp.getKey());
					}
					if (ean != null) {
						Implicator imp2 = new Implicator(ean, null, imp.getKey());
						el.put(imp2);
						uiAttrKeyList.put(imp2);
					}
				}
				catch (Exception _ex) {
					_ex.printStackTrace();
				}
			} else {
				EANMetaAttribute ma = (EANMetaAttribute) o;
				String strRelTag = (eg.isRelator() ? "R" : "C");
				String strKey = getEntityType() + ":" + ma.getKey();

				try{
					if ( (eg.isNavigateLike() && ma.isNavigate()) || !eg.isNavigateLike()) {
						if (!eg.isNavigateLike() && eg.isClassified()) {
							eg.getGlobalClassificationGroup();
							// If we are new here.. we need to either show the classification vars in
							// addition to anything that is classified.
							if (isNew()) {
								// Here is where we only show the classified stuff on a new record
								if (ma.isClassified() || isClassified(ma) || ma.isDerived()) {
									EANFoundation eaf = getThisEANObject(ma.getKey());
									//avoid lookup el.put(new Implicator(getEANObject(strKey), null, strKey + ":" + strRelTag));
									Implicator imp = new Implicator(eaf, null, strKey + ":" + strRelTag);
									el.put(imp);
									uiAttrKeyList.put(imp);
								} else {
									// do not return this one
									EANFoundation eaf = getThisEANObject(ma.getKey());
									Implicator imp = new Implicator(eaf, null, strKey + ":" + strRelTag);
									uiAttrKeyList.put(imp);
								}
							} else if (isClassified(ma) || ma.isClassified() || ma.isDerived()) {
								EANFoundation eaf = getThisEANObject(ma.getKey());
								//avoid lookup el.put(new Implicator(getEANObject(strKey), null, strKey + ":" + strRelTag));
								Implicator imp = new Implicator(eaf, null, strKey + ":" + strRelTag);
								el.put(imp);
								uiAttrKeyList.put(imp);
							} else if (eg.isABRStatusLike()) {
								EANFoundation eaf = getThisEANObject(ma.getKey());
								//avoid lookup el.put(new Implicator(getEANObject(strKey), null, strKey + ":" + strRelTag));
								Implicator imp = new Implicator(eaf, null, strKey + ":" + strRelTag);
								el.put(imp);
								uiAttrKeyList.put(imp);
							} else {
								// do not return this one
								EANFoundation eaf = getThisEANObject(ma.getKey());
								Implicator imp = new Implicator(eaf, null, strKey + ":" + strRelTag);
								uiAttrKeyList.put(imp);
							}
						} else {                   	
							EANFoundation eaf = getThisEANObject(ma.getKey());
							//avoid lookup el.put(new Implicator(getEANObject(strKey), null, strKey + ":" + strRelTag));
							Implicator imp = new Implicator(eaf, null, strKey + ":" + strRelTag);
							el.put(imp);
							uiAttrKeyList.put(imp);
						}
					}else{
						// do not return this one
						EANFoundation eaf = getThisEANObject(ma.getKey());
						Implicator imp = new Implicator(eaf, null, strKey + ":" + strRelTag);
						uiAttrKeyList.put(imp);
					}
				}
				catch (Exception x) {
					x.printStackTrace();
				}
			}
		}

		// apply MetaColumnOrderGroup if we have one...
		if (aiParent != null && aiParent.hasMetaColumnOrderGroup()) {
			mcog = aiParent.getMetaColumnOrderGroup();
		}
		if (mcog == null) {
			mcog = eg.getMetaColumnOrderGroup();
		}

		if (mcog != null) {
			el = RowSelectableTable.applyColumnOrders(mcog, el);
		}

		return el;
	}

	/**
	 *  Gets the columnList attribute of the EntityItem object
	 *
	 *@return    The columnList value
	 */
	public EANList getColumnList() {

		EANList el = new EANList();
		MetaLabel ml1 = null;
		MetaLabel ml2 = null;

		try {
			ml1 = new MetaLabel(this, getProfile(), EANAttribute.DESCRIPTION, "Description", String.class);
			ml1.putShortDescription("Description");
			ml2 = new MetaLabel(this, getProfile(), EANAttribute.VALUE, "Value", String.class);
			ml2.putShortDescription("Value");
			el.put(ml1);
			el.put(ml2);
		}
		catch (Exception x) {
			x.printStackTrace();
		}
		return el;
	}

	/**
	 *  adds a EANAttribute to the changeStack it not already on
	 *
	 *@param  _att  The feature to be added to the ToStack attribute
	 */
	protected void addToStack(EANAttribute _att) {
		// Move to the top of the stack if already in the stack
		if (_att == null) {
			return;
		}

		if (m_stk == null) {

			m_stk = new Stack();

		}

		if (m_stk.search(_att) != -1) {
			m_stk.removeElementAt(m_stk.lastIndexOf(_att));
		}

		m_stk.push(_att);

	}

	/**
	 *  Description of the Method
	 *
	 *@param  _att  Description of the Parameter
	 *@return       Description of the Return Value
	 */
	protected boolean removeFromStack(EANAttribute _att) {
		if (m_stk == null) {
			return true;
		}
		if (m_stk.search(_att) != -1) {
			m_stk.removeElementAt(m_stk.lastIndexOf(_att));
			return true;
		}
		return false;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _att  Description of the Parameter
	 *@return       Description of the Return Value
	 */
	public boolean rollback(EANAttribute _att) {

		// It should be on the stack .. so lets check

		removeFromStack(_att);
		_att.rollback();

		if (m_stk == null) {
			return true;
		}
		if (m_stk.size() == 0) {
			return true;
		}
		return false;
	}


	/******************************************************************************
	 * Deactivate these entities  VEEdit_delete MN30841458
	 * @param itemArray EntityItem[] to deactivate
	 */
	private static void veeditDelete(Database _db, RemoteDatabaseInterface _rdi, Profile profile,
			EntityItem[] itemArray, String delaction)  throws
			COM.ibm.opicmpdh.middleware.LockException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.eannounce.objects.EANBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
			java.sql.SQLException,
			java.rmi.RemoteException
			{
		if(_db != null){
			DeleteActionItem deleteActionItem = new DeleteActionItem(null, _db, profile, delaction);
			deleteActionItem.setEntityItems(itemArray);
			deleteActionItem.executeAction(_db, profile);
		}else{
			DeleteActionItem deleteActionItem = _rdi.getDeleteActionItem(null, profile, delaction);
			deleteActionItem.setEntityItems(itemArray);
			_rdi.executeAction(profile, deleteActionItem) ;
		}
			}

	/**
	 * VEEdit_delete MN30841458
	 * check to see if this is a delete for veedit action
	 *@param related EntityItem[] relator and item that need to be deleted or committed
	 *@return boolean true if this is a delete
	 * /
	private boolean isVEEditDelete(EntityItem[] related)
	{
		boolean isdelete = true;
        EntityGroup eg = getEntityGroup();
        EntityList eList = eg.getEntityList();

		// look at all attributes, all must be nulled out to be a delete
		outerloop:for (int i=0; i<related.length; i++){
			for(int a=0; a<related[i].getAttributeCount(); a++){
				EANAttribute att = related[i].getAttribute(a);
				if (att!=null && att.toString().length()>0){
					isdelete = false;
					break outerloop;
				}
			}
		}
		// if is delete get deleteaction from the parentaction
		if (isdelete){
			EANActionItem ean = eList.getParentActionItem();
			if (ean instanceof ExtractActionItem) {
				String delaction = null;
				// find the delete action, it could be defined at the relator or entity.. first one found wins
				for (int i=0; i<related.length; i++){
					delaction = ((ExtractActionItem) ean).getDeleteAction(related[i].getEntityType());
					if (delaction !=null){
						break;
					}
				}
				if (delaction ==null){
					isdelete = false;
                    D.ebug(D.EBUG_ERR,"EntityItem.isVEEditDelete delete action was not in meta for "+ean+", can't delete items related to "+getKey());
				}
			}
		}

		return isdelete;
	}

    /**
	 * VEEdit_delete MN30841458
	 * handle deletes for veedit action
	 *@param eList EntityList
	 *@param vctDeleteParentItems Vector of parentitems that need related items deleted
	 *@param _db Database
	 *@param _rdi RemoteDatabaseInterface
	 */
	protected static void doVEEditDelete(EntityList eList,Vector vctDeleteParentItems,
			Database _db, RemoteDatabaseInterface _rdi) throws
			RemoteException, MiddlewareException,
			MiddlewareRequestException, MiddlewareShutdownInProgressException,
			SQLException, LockException, EANBusinessRuleException
			{
		String delaction = null;
		String deltype = null;
		EntityItem[] itemArray = null;
		Vector vctDeleteItems = new Vector();

		// get the delaction and set of items to delete
		for (int d=0; d<vctDeleteParentItems.size(); d++) {
			VEEditItem parent = (VEEditItem)vctDeleteParentItems.elementAt(d);
			EntityItem[] related = parent.getVEPathItems();//VEPath.getEntityItems(parent, eList, true);
			if (related != null) { // this shouldnt be the case now, it was checked by isveeditdelete
				// get deleteaction from the parentaction
				if (delaction==null){ // only need to get it once, same one will be used
					EANActionItem ean = eList.getParentActionItem();
					if (ean instanceof ExtractActionItem) {
						// find the delete action, it could be defined at the relator or entity.. first one found wins
						for (int i=0; i<related.length; i++){
							delaction = ((ExtractActionItem) ean).getDeleteAction(related[i].getEntityType());
							if (delaction !=null){
								deltype=related[i].getEntityType();
								vctDeleteItems.addElement(related[i]);
								break;
							}
						}
					}
				}else{
					// find related item to delete
					for (int i=0; i<related.length; i++){
						if(related[i].getEntityType().equals(deltype)){
							vctDeleteItems.addElement(related[i]);
							break;
						}
					}
				}
			} // related found
		}  // parent loop

		if (delaction !=null){
			itemArray = new EntityItem[vctDeleteItems.size()];
			vctDeleteItems.copyInto(itemArray);
			// do the actual deletion now
			veeditDelete(_db,_rdi, eList.getProfile(),itemArray, delaction);
			for (int i=0; i<itemArray.length; i++){
				itemArray[i].commitLocal(); // not sure this is needed

				// table refreshes in jui but entitylist doesnt so remove linkages now
				EntityGroup relatedGrp = itemArray[i].getEntityGroup();
				relatedGrp.removeEntityItem(itemArray[i]); // remove it from the group
				if (relatedGrp.isRelator()){
					EntityItem parent = (EntityItem)itemArray[i].getUpLink(0);
					// remove linkages
					parent.removeDownLink(itemArray[i]);
				}else { // must be entity
					EntityItem parentrel = (EntityItem)itemArray[i].getUpLink(0);
					EntityItem parent = (EntityItem)parentrel.getUpLink(0);
					relatedGrp = parentrel.getEntityGroup();
					relatedGrp.removeEntityItem(parentrel); // remove it from the group

					// remove linkages
					parent.removeDownLink(parentrel);
					parentrel.removeDownLink(itemArray[i]);
				}
			}
		}
		else{
			D.ebug(D.EBUG_ERR,"EntityItem.doVEEditDelete delaction was null, can't delete items");
		}
			}

	/**
	 * VEEdit if this is not a delete, the vctReturnEntityKeys is filled in
	 * if it is a delete, the vctDeleteParentItems is filled in
	 * handle veedit action
	 *@param _db Database
	 *@param _rdi RemoteDatabaseInterface
	 *@param eanlst EANList filled in if not a delete request
	 *@param vctReturnEntityKeys Vector filled in if not a delete request
	 *@param vctDeleteParentItems Vector filled in if is a delete request
	 *@return boolean true if is a delete request
	 */
	protected boolean handleVEEdit(Database _db, RemoteDatabaseInterface _rdi, EANList eanlst,
			Vector vctReturnEntityKeys, Vector vctDeleteParentItems) throws
			RemoteException, MiddlewareException,
			MiddlewareRequestException, MiddlewareShutdownInProgressException,
			SQLException, EANBusinessRuleException
			{
		return false;
			}

	/*
	 *  if you want to save one record only
	 */
	/**
	 * (non-Javadoc)
	 * commit
	 *
	 * @see COM.ibm.eannounce.objects.EANTableWrapper#commit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
	 */
	public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException,
	RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {

		// You want to save it.. and its downstream partner iff present
		// Here we loop through each entityItem (and its child)
		// and build a vct Of Transactions...
		EANList eanlst = new EANList();
		EntityList eList = null;
		EntityGroup eg = getEntityGroup();
		OPICMList ol = null;
		Vector vctReturnEntityKeys = new Vector();
		Vector vctDeleteParentItems = new Vector();
		// Should be called in generateUpdateEntity... so we comment out
		// if (eg.isClassified()) {
		//   refreshClassifications();
		// }
		// we do not want to pop the stack because if the update fails the
		// client would like to try again

		EntityItemException valException = validateEntityItem(_db,_rdi);	//CR093005678 CR0930055856
		if (valException != null) {											//CR093005678 CR0930055856
			throw valException;												//CR093005678 CR0930055856
		}																	//CR093005678 CR0930055856

		ReturnEntityKey rek = generateUpdateEntity(true);
		if (rek != null) {
			eanlst.put(this);
			vctReturnEntityKeys.addElement(rek);
		}
		// Do not forget to drag any children with you
		if (eg != null) { //cStolpe
			eList = eg.getEntityList(); //cStolpe
		} //cstolpe
		if (isVEEdit()) {
			// this 'parent' entity should not be editable in veedit, so eanlst will be empty now
			// it will be filled in if this is not a delete request
			//VEEdit_delete MN30841458
			boolean deleteNeeded = handleVEEdit(_db, _rdi, eanlst, vctReturnEntityKeys,vctDeleteParentItems);
			if (deleteNeeded){ // delete is needed
				try{
					doVEEditDelete(eList,vctDeleteParentItems,_db, _rdi);
				}catch(LockException lex){
					lex.printStackTrace();
				}
				vctDeleteParentItems.clear();
				return;
			}
			// how to commit all?
		} else if (eg.isRelator() || eg.isAssoc()) {
			EntityItem eip = null;
			EntityItem eid = null;

			boolean bGoDown = true;
			if (eList != null && eList.isEditRelatorOnly()) {
				bGoDown = false;
				// check if child is a relator LSEOPRODSTRUCT-PRODSTRUCT
				EntityItem eiDown = (EntityItem) getDownLink(0);
				if (eiDown.getEntityGroup().isRelator()){
					bGoDown = true;
				}
			}
			if (bGoDown) {
				EntityItem eiDown = (EntityItem) getDownLink(0);
				if (eList != null && eList.isCreateParent()) {
					eiDown = (EntityItem) getUpLink(0);
				}

				if (eiDown != null) {
					ReturnEntityKey rekDown = eiDown.generateUpdateEntity(true);
					if (rekDown != null) {
						eanlst.put(eiDown);
						vctReturnEntityKeys.addElement(rekDown);
					}
				}
			}

			// Check for Newness and make sure I am a relator.. not an assoc placeholder
			if (getEntityID() < 0 && eg.isRelator()) {
				if (eList.isCreateParent()) {
					eid = (EntityItem) getDownLink(0);
					if (eid.getEntityID() < 0) {
						EntityItemException eie = new EntityItemException();
						eie.add(this,
								"No Child Item is selected.  Please select a child for this entity prior to attempting to save it into the PDH");
						throw eie;
					}
					eip = (EntityItem) getUpLink(0);
					vctReturnEntityKeys.addElement(new ReturnRelatorKey(getEntityType(), getEntityID(), eip.getEntityType(),
							eip.getEntityID(), eid.getEntityType(), eid.getEntityID(), true));
					if (!eanlst.containsKey(getKey())) {
						eanlst.put(this);
					}
				} else {

					eip = (EntityItem) getUpLink(0);
					if (eip.getEntityID() < 0) {
						EntityItemException eie = new EntityItemException();
						eie.add(this,
								"No Parent Item is selected.  Please select a parent for this entity prior to attempting to save it into the PDH");
						throw eie;
					}
					eid = (EntityItem) getDownLink(0);
					vctReturnEntityKeys.addElement(new ReturnRelatorKey(getEntityType(), getEntityID(), eip.getEntityType(),
							eip.getEntityID(), eid.getEntityType(), eid.getEntityID(), true));
					if (!eanlst.containsKey(getKey())) {
						eanlst.put(this);
					}
				}
			}
		}else{
			// handle case where search and edit and then create is done, commit is on the entity not the relator
			//MN 38666284 - CQ00022911
			if (getEntityID() < 0 && eList!=null){
				EANActionItem action = eList.getParentActionItem();
				if (action instanceof EditActionItem){
					EditActionItem eai = (EditActionItem)action;
					if (eai.canCreate()){
						EntityItem eiRelator = (EntityItem) getUpLink(0);
						if (eiRelator != null) {
							EntityItem eiparent = (EntityItem) eiRelator.getUpLink(0);
							if (eiparent!=null){
								vctReturnEntityKeys.addElement(new ReturnRelatorKey(eiRelator.getEntityType(), eiRelator.getEntityID(),
										eiparent.getEntityType(),
										eiparent.getEntityID(), getEntityType(), getEntityID(), true));
								if (!eanlst.containsKey(getKey())) {
									eanlst.put(this);
								}
							}
						}
					}
				}
			}
		}

		// Now.. we attempt the update.. EEK!
		if (_rdi != null) {
//			28708515            ReturnEntityKey rekt = (ReturnEntityKey)vctReturnEntityKeys.elementAt(0);
			// rekt.setRule51Group(null);
//			28708515             rekt.setDependentAttributeValues(null);
			// rekt.setUniqueAttributeGroups(null);
			ol = _rdi.update(getProfile(), vctReturnEntityKeys);
		} else if (_db != null) {

			ol = _db.update(getProfile(), vctReturnEntityKeys);
		}

		// So now if everything went ok.. we commit local on all EntityItems in the list
		if (ol == null) {

			D.ebug(D.EBUG_SPEW, "EntityItem.commit() "+getKey()+" #1# *** returned OPICMList is null***");
		} else {
			for (int ii = 0; ii < eanlst.size(); ii++) {
				EntityItem ei = (EntityItem) eanlst.getAt(ii);
				eg = ei.getEntityGroup();
				if (ol.get(ei.getKey()) instanceof ReturnEntityKey) {
					rek = (ReturnEntityKey) ol.get(ei.getKey());
					if (rek.m_iEntityID < 0) {
						if (rek.isPosted() && rek.isActive()) {
							String strOld = ei.getKey();

							ei.setEntityID(rek.getReturnID());
							// Triggers key change
							eg.getData().resetKey(strOld);
						}
					}
				} else if (ol.get(ei.getKey()) instanceof ReturnRelatorKey) {
					ReturnRelatorKey rrk = (ReturnRelatorKey) ol.get(ei.getKey());
					if (rrk.getEntityID() < 0) {
						if (rrk.isPosted() && rrk.isActive()) {
							String strOld = ei.getKey();

							ei.setEntityID(rrk.getReturnID());
							// Triggers key change
							eg.getData().resetKey(strOld);
						}
					} else {
						D.ebug(D.EBUG_SPEW,
								"EntityItem.commit() #2# *** "+getKey()+" EIOL Entity:" + ei.getKey() + ":Cannot be found in the return list");
					}
				}

				ei.commitLocal();
			}
		}
	}

	/*
	 *  Saving the WG settings for future retrieval
	 */
	/**
	 *  Description of the Method
	 *
	 *@param  _db                                  Description of the Parameter
	 *@param  _rdi                                 Description of the Parameter
	 *@exception  MiddlewareBusinessRuleException  Description of the Exception
	 *@exception  EANBusinessRuleException         Description of the Exception
	 */
	public void updateWGDefault(Database _db, RemoteDatabaseInterface _rdi) throws MiddlewareBusinessRuleException,
	EANBusinessRuleException {

		// You want to save it.. and its downstream partner iff present
		// Here we loop through each entityItem (and its child)
		// and build a vct Of Transactions...

		EANList eanlst = new EANList();
		EntityGroup eg = getEntityGroup();
		//OPICMList ol = null;
		Vector vctReturnEntityKeys = new Vector();

		// we do not want to pop the stack because if the update fails the
		// client would like to try again
		ReturnEntityKey rek = null;
		rek = generateUpdateEntity(false);

		if (rek != null) {

			//Set the entityid of the rek to the profile index
			setEntityId(rek, getProfile().getDefaultIndex());

			eanlst.put(this);
			//Package the update Vector
			vctReturnEntityKeys.addElement(rek);
		}

		// Get the Children...forget bout the parents
		if (eg.isRelator() || eg.isAssoc()) {
			EntityItem eiDown = (EntityItem) getDownLink(0);
			ReturnEntityKey rekDown = null;
			rekDown = eiDown.generateUpdateEntity(false);

			if (rekDown != null) {

				//Set the entityid to the profile index one
				setEntityId(rekDown, getProfile().getDefaultIndex());

				eanlst.put(eiDown);
				vctReturnEntityKeys.addElement(rekDown);
			}
		}

		// Update WG Default here
		try {
			if (_rdi != null) {
				//ol =
				_rdi.updateWGDefault(getProfile(), vctReturnEntityKeys);
			} else if (_db != null) {
				//ol =
				_db.updateWGDefault(getProfile(), vctReturnEntityKeys);
			}
		}
		catch (Exception x) {
			x.printStackTrace();
		}

	}

	/*
	 *  Reset the WG settings
	 */
	/**
	 *  Description of the Method
	 *
	 *@param  _db                                  Description of the Parameter
	 *@param  _rdi                                 Description of the Parameter
	 *@exception  MiddlewareBusinessRuleException  Description of the Exception
	 *@exception  EANBusinessRuleException         Description of the Exception
	 */
	public void resetWGDefault(Database _db, RemoteDatabaseInterface _rdi) throws MiddlewareBusinessRuleException,
	EANBusinessRuleException {

		ReturnEntityKey rekDown = null;
		ReturnEntityKey rek = null;

		Vector vctReturnEntityKeys = new Vector();
		int dIndex = getProfile().getDefaultIndex();

		// remove from entitylist
		EntityGroup eg = (EntityGroup) getParent();
		EntityList elst = (EntityList) eg.getParent();

		rek = new ReturnEntityKey(getEntityType(), dIndex, isActive());

		elst.removeDefaultEntityItem(getEntityType(), dIndex);

		//Package the update Vector
		vctReturnEntityKeys.addElement(rek);

		if (eg.isRelator() || eg.isAssoc()) {
			EntityItem eiDown = (EntityItem) getDownLink(0);

			// remove from entitylist
			eg = (EntityGroup) eiDown.getParent();
			elst = (EntityList) eg.getParent();
			elst.removeDefaultEntityItem(eiDown.getEntityType(), dIndex);

			rekDown = new ReturnEntityKey(eiDown.getEntityType(), dIndex, isActive());
			vctReturnEntityKeys.addElement(rekDown);
		}

		// Reset WG Default from pdh here
		try {
			if (_rdi != null) {
				_rdi.resetWGDefault(getProfile(), vctReturnEntityKeys);
			} else if (_db != null) {
				_db.resetWGDefault(getProfile(), vctReturnEntityKeys);
			}
		}
		catch (Exception x) {
			x.printStackTrace();
		}
	}

	/**
	 *  Sets the entityId attribute of the EntityItem object
	 *
	 *@param  _rek   The new entityId value
	 *@param  _iEID  The new entityId value
	 */
	public void setEntityId(ReturnEntityKey _rek, int _iEID) {

		Vector vRekAttr = _rek.m_vctAttributes;

		//Set the entityid of the entity
		//Now set the entityid of the rek and all its attr to the profile index
		_rek.m_iEntityID = _iEID;

		//Set the entity id of its attributes
		for (int i = 0; i < vRekAttr.size(); i++) {
			//Get the attribute type...and change its eid
			if (vRekAttr.elementAt(i) instanceof COM.ibm.opicmpdh.objects.Text) {
				COM.ibm.opicmpdh.objects.Text txtCurrent = (COM.ibm.opicmpdh.objects.Text) vRekAttr.elementAt(i);
				txtCurrent.m_iEntityID = _iEID;

			} else if (vRekAttr.elementAt(i) instanceof COM.ibm.opicmpdh.objects.SingleFlag) {
				COM.ibm.opicmpdh.objects.SingleFlag sfCurrent = (COM.ibm.opicmpdh.objects.SingleFlag) vRekAttr.elementAt(i);

				sfCurrent.m_iEntityID = _iEID;
			} else if (vRekAttr.elementAt(i) instanceof COM.ibm.opicmpdh.objects.MultipleFlag) {
				COM.ibm.opicmpdh.objects.MultipleFlag mfCurrent = (COM.ibm.opicmpdh.objects.MultipleFlag) vRekAttr.elementAt(i);

				mfCurrent.m_iEntityID = _iEID;

			} else if (vRekAttr.elementAt(i) instanceof COM.ibm.opicmpdh.objects.LongText) {
				COM.ibm.opicmpdh.objects.LongText ltCurrent = (COM.ibm.opicmpdh.objects.LongText) vRekAttr.elementAt(i);

				ltCurrent.m_iEntityID = _iEID;

			} else if (vRekAttr.elementAt(i) instanceof COM.ibm.opicmpdh.objects.Blob) {
				COM.ibm.opicmpdh.objects.Blob blCurrent = (COM.ibm.opicmpdh.objects.Blob) vRekAttr.elementAt(i);
				blCurrent.m_iEntityID = _iEID;

			}
		}
		//end for

	}

	//
	// This section needs to start throwing business rule exceptions
	//

	/**
	 *  Description of the Method
	 *
	 *@return                               Description of the Return Value
	 *@exception  EANBusinessRuleException  Description of the Exception
	 */
	public boolean checkBusinessRules() throws EANBusinessRuleException {

		// Lets loop through each attribute and ensure that if it is required
		// It is present...

		RequiredRuleException rre = new RequiredRuleException();
		LocalRuleException lre = new LocalRuleException();
		EntityGroup eg = getEntityGroup();

		// Required no enforced in search
		if (isUsedInSearch()) {
			return true;
		}

		// end Local Rule Group

		for (int ii = 0; ii < getRequiredListCount(); ii++) {
			EANMetaAttribute ma = getRequiredList(ii);
			if (ma != null) { //USRO-R-TMAY-6GLNMS
				EANAttribute att = (EANAttribute) getEANObject(getEntityType() + ":" + ma.getKey());
				if (att != null) { //USRO-R-TMAY-6GLNMS
					if ( (eg.isClassified() && isClassified(ma)) || !eg.isClassified()) {
						rre.validate(att, att.toString());
					}
				} //USRO-R-TMAY-6GLNMS
			} //USRO-R-TMAY-6GLNMS
		}

		if (rre.getErrorCount() > 0) {
			throw rre;
		}

		// Local Rule Group
		if (eg.hasLocalRuleGroup()) {

			for (int i = 0; i < eg.getLocalRuleGroupCount(); i++) {
				lre.validate(this, eg.getLocalRuleGroup(i));
			}
		}
		if (lre.getErrorCount() > 0) {
			throw lre;
		}

		return true;
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public boolean hasChanges() {
		EntityGroup eg = getEntityGroup();

		if (pendingChanges()) {
			return true;
		}

		if (eg.isRelator() || eg.isAssoc()) {
			EntityItem ei = (EntityItem) getDownLink(0);
			if (ei != null) {
				return ei.pendingChanges();
			}
		}
		return false;
	}

	/**
	 *  Attribute updates pending or this is a new entity
	 *
	 *@return    Description of the Return Value
	 */
	protected boolean pendingChanges() {
		if (m_stk == null) {
			return false;
		}
		return!m_stk.empty() || isNew();
	}

	/**
	 * Only attribute updates pending, used for search reset button control
	 * @return
	 */
	public boolean hasAttributeChanges() {
		return !(m_stk == null || m_stk.empty());
	}

	/**
	 * is this attribute on the stack?
	 * @return
	 */
	public boolean hasAttributeChange(EANAttribute att) {
		if(m_stk !=null){
			return(m_stk.search(att)!=-1);
		}
		return false;
	}
	/**
	 *  Description of the Method
	 *
	 *@param  _bCheckRules                  Description of the Parameter
	 *@return                               Description of the Return Value
	 *@exception  EANBusinessRuleException  Description of the Exception
	 */
	protected ReturnEntityKey generateUpdateEntity(boolean _bCheckRules) throws EANBusinessRuleException {
		// If we are classified.. we need to loop through all the atts and deactivate anything that is set
		// that is not in the current Classification List
		boolean bContinue = false;
		EntityGroup eg = getEntityGroup();
		if (eg.isClassified()) {
			refreshClassifications();
			for (int ii = 0; ii < getAttributeCount(); ii++) {
				EANAttribute att = getAttribute(ii);
//				TIR6YZSXM error here.. attr has meta but entitygroup does not! changes made for veedit put metaattr
//				from other types like AVAIL in MODEL entity group and dont put some metaattr for MODEL in
//				MODEL is classified so these attr with missing metaattr are deactivated
//				EANMetaAttribute ma = att.getMetaAttribute();
				EANMetaAttribute ma = eg.getMetaAttribute(att.getAttributeCode()); //TIR6YZSXM
				if (ma == null) {
					continue;
				}
				// We deactivate it it is currently active
				if (!isClassified(ma) && att.isActive() && !ma.isClassified() && att.toString().length() > 0) {
					D.ebug(D.EBUG_SPEW,
							"WARN:EntityItem.generateUpdateEntity(b).1. Active Attribute will be turned off. It does not apply to the given classification: " +
							getEntityType() + ":" + getEntityID() + ":" + ma.getAttributeCode() + ":" + att.toString());
					att.put(null);
				}
			}
		}

		bContinue = (_bCheckRules ? checkBusinessRules() : true);

		// if there is no change stack..
		if (m_stk == null) {
			return null;
		}

		// If it is an association.. we do not need to do anything
		if (pendingChanges() && bContinue && (!eg.isAssoc())) {
			ReturnEntityKey rek = new ReturnEntityKey(getEntityType(), getEntityID(), isActive());
			Vector vctAtts = new Vector();

			for (int ii = 0; ii < m_stk.size(); ii++) {
				EANAttribute att = (EANAttribute) m_stk.elementAt(ii);
				Vector vctAtt = att.generateUpdateAttribute();
				// If the vctAtt comes  back null .. then it was a false update
				// i.e. a flag value was set from x to y .. then back to x again
				if (vctAtt != null) {
					for (int iy = 0; iy < vctAtt.size(); iy++) {
						vctAtts.addElement(vctAtt.elementAt(iy));
					}
				}
			}
			rek.m_vctAttributes = vctAtts;

			// we only want to run Unique validation if all the relevant attributes exist.
			if (getEntityGroup().hasUniqueAttributeGroup()) {
				Vector vctUag = getEntityGroup().getUniqueAttributeGroupVector();
				UAG_LOOP:for (int i = 0; i < vctUag.size(); i++) {
					UniqueAttributeGroup uag = (UniqueAttributeGroup) vctUag.elementAt(i);

					// this is step one: we are only applying our uniqueness check to Entities which satisfy one of the Uniqueness
					// rules cases. Later, when we pull out other matches, we will need to do a similar check, on the results.
					if (!uag.evaluate(this)) {
						uag.setActive(false);
						D.ebug(D.EBUG_SPEW, "UniqueAttributeGroup: "+getKey()+" EvaluatorII returned false, so bailing out!");
						continue UAG_LOOP;
					}

					if (uag.allUniqueValuesExist(this)) {
						uag.setActive(true);
						uag.populateAttributeVals(this);
					} else {
						uag.setActive(false);
					}
				}
				rek.setUniqueAttributeGroups(vctUag);
			}
			if (isBypassCommitChecks()){ 
				// SR5- must be able to avoid attribute checks/searches on commit, done when created
				D.ebug(D.EBUG_WARN, getKey()+" Action: "+
						getEntityGroup().getEntityList().getParentActionItem().getActionItemKey()
						+" is BypassCommitChecks!");
				return rek;
			}
			// RULE51
			if (getEntityGroup().hasRule51Group()) {
				try {
					Rule51Group r51g = getEntityGroup().getRule51Group().getCopy();
					EANAttribute att51 = getAttribute(r51g.getAttributeCode());
					if (att51 != null) {
						r51g.setAttributeValue(att51.toString());
						r51g.setParentEntityID(getEntityID());
						r51g.addDomainEntities(this);
						rek.setRule51Group(r51g);
					} else {
						System.err.println("error in EntityItem.generateUpdateEntity for Rule51: "+getKey()+" attribute is null!");
					}
				}
				catch (Exception exc) {
					System.err.println("error in EntityItem.generateUpdateEntity for Rule51: "+getKey()+" "+ exc.toString());
					exc.printStackTrace();
				}
			}
			// DependentAttributeValue
			if (getEntityGroup().hasDependentAttributeValue()) {
				Vector vctDav = getEntityGroup().getDependentAttributeValueVector();
				for (int i = 0; i < vctDav.size(); i++) {
					DependentAttributeValue dav = (DependentAttributeValue) vctDav.elementAt(i);
					if (dav.allValuesExist(this)) {
						dav.setActive(true);
						dav.populateAttributeVals(this);
					} else {
						dav.setActive(false);
					}
				}
				rek.setDependentAttributeValues(vctDav);
			}
			//log this in jui so user can see something is happening
			D.ebug(D.EBUG_INFO, "Generated update entity for "+getKey());

			return rek;
		} else {
			// Do nothing now...
		}

		return null;
	}
	/***************************
	 * SR5 must be able to bypass commit checks for dependent attributes
	 * @return
	 */
	private boolean isBypassCommitChecks(){
		boolean isBypass=false;
		if (getEntityGroup()!=null){
			EntityList thelist = getEntityGroup().getEntityList();
			if (thelist!=null){
				EANActionItem ean = thelist.getParentActionItem();
				if (ean instanceof CreateActionItem){
					isBypass = ((CreateActionItem)ean).isBypassCommitChecks();
				}else if (ean instanceof ExtractActionItem){
					isBypass = ((ExtractActionItem)ean).isBypassCommitChecks();
				}
			}
		}
		return isBypass;
	}

	/*
	 *  All children should be pointing to me
	 */
	/**
	 *  Description of the Method
	 */
	protected void checkDataIntegrity() {

		int ihc = hashCode();

		for (int ii = 0; ii < getAttributeCount(); ii++) {
			EANAttribute att = getAttribute(ii);
			EANFoundation eanf = att.getParent();
			if (ihc != eanf.hashCode()) {
				D.ebug(D.EBUG_SPEW,
						"EI PC Integrity problem:" + getKey() + ":" + ihc + " != " + eanf.getKey() + ":" + eanf.hashCode());
				att.setParent(this);
			}
		}
	}

	/**
	 *  Description of the Method
	 */
	protected void commitLocal() {
		if (m_stk == null) {
			return;
		}
		while (!m_stk.empty()) {
			EANAttribute att = (EANAttribute) m_stk.pop();

			att.commitLocal();
		}

		// Should not have to do this any more
		//refreshRestrictions(true);
		refreshRestrictions(); // must use this or wrong restrictions are applied after save

		if (!m_stk.empty()) {
			D.ebug(D.EBUG_SPEW, "EntityItem.commitLocal(). Not empty for key:" + getKey());
		}
	}

	/*
	 *  We need to create objects for everything here..
	 */
	/**
	 * refreshDefaults
	 *
	 *  @author David Bigelow
	 */
	public void refreshDefaults() {
		EntityGroup eg = (EntityGroup) getParent();
		if (eg == null) {
			return;
		}

		// No defaults if used in a search
		if (isUsedInSearch()) {
			return;
		}

		for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
			EANMetaAttribute ma = eg.getMetaAttribute(ii);
			if ((!eg.isClassified()) || (eg.isClassified() && (isClassified(ma) || ma.isClassified()))) {
				String strEntityType = getEntityType(); //VEEdit_lockmulti
				if (isVEEdit()) { //VEEdit_lockmulti
					EANFoundation eanParent = ma.getParent(); //VEEdit_lockmulti
					if (eanParent instanceof EntityGroup) { //VEEdit_lockmulti
						strEntityType = ((EntityGroup)eanParent).getEntityType(); //VEEdit_lockmulti
					} //VEEdit_lockmulti
				} //VEEdit_lockmulti
				String strKey = strEntityType + ":" + ma.getKey(); //VEEdit_lockmulti
				EANAttribute att = (EANAttribute) getEANObject(strKey);
				if (att != null) {
					att.refreshDefaults();
				}
			}
		}

		//TIR6YZSJJ refresh VEEdit
		if (isVEEdit()) {
			// VEEdit is always done on a child, make sure its relator is refreshed too
			if (!eg.isRelator()&& !eg.isAssoc() && getUpLinkCount()>0){
				((EntityItem)getUpLink(0)).refreshDefaults();
			}
		}
	}
	/*
	 *  We need to loop through all the RestrictionGroup objects
	 *  and puts all the metaAttributes in a list that are currently
	 *  deemed restricted for this entity.
	 */
	/**
	 *  Description of the Method
	 *
	 */

	protected void refreshRestrictions() {
		refreshRestrictions(true);
		refreshRestrictions(false);
	}

	/**
	 * refreshRestrictions
	 *
	 * @param _bDeferred
	 *  @author David Bigelow
	 */
	protected void refreshRestrictions(boolean _bDeferred) {

		EntityGroup eg = (EntityGroup) getParent();
		if (eg == null) {
			return; // Nothing to do
		}

		resetRestrictionList();

		// Fill up the list again ..
		for (int ii = 0; ii < eg.getRestrictionGroupCount(); ii++) {
			RestrictionGroup rg = eg.getRestrictionGroup(ii);
			// only reclac deferred or non deferred
			//
			if (_bDeferred == rg.isDeferred()) {
				rg.evaluate(this);
			}

			if (rg.getCurrentEvaluateState()) {
				// We need to add MetaAttributes to this list
				if (rg.isGlobalLock()) {
					for (int iy = 0; iy < eg.getMetaAttributeCount(); iy++) {
						EANMetaAttribute ma = eg.getMetaAttribute(iy);
						if (!ma.isSuperEditable()) {
							putRestrictionList(ma);
						}
					}
				} else {
					for (int iy = 0; iy < rg.getAttributeCodeCount(); iy++) {
						String strAttributeCode = rg.getAttributeCode(iy);
						EANMetaAttribute ma = eg.getMetaAttribute(strAttributeCode);
						if (ma != null) {
							putRestrictionList(ma);
						}
					}
				}
			}
		}
	}

	/**
	 *  We need to loop through all the ClassificationGroup objects and puts all
	 *  the metaAttributes in a list that are now deemed classified
	 *
	 */
	public void refreshClassifications() {
		EntityGroup eg = (EntityGroup) getParent();
		if (eg == null) {
			return; // Nothing to do
		}

		getClassificationList();

		resetClassificationList();

		for (int ii = 0; ii < eg.getClassificationGroupCount(); ii++) {
			ClassificationGroup cg = eg.getClassificationGroup(ii);

			if (cg != null && cg.evaluate(this)) {

				// We need to add MetaAttributes to this list
				for (int iy = 0; iy < cg.getAttributeCodeCount(); iy++) {
					String strAttributeCode = cg.getAttributeCode(iy);
					EANMetaAttribute ma = eg.getMetaAttribute(strAttributeCode);
					if (ma != null) {
						putClassificationList(ma);
					}
				}
			}
		}
	}

	/*
	 *  We need to loop through all the RestrictionGroup objects
	 *  and puts all the metaAttributes in a list that are currently
	 *  deemed restricted for this entity.
	 */
	/**
	 *  Description of the Method
	 *
	 */
	protected void refreshResets() {

		EntityGroup eg = (EntityGroup) getParent();

		getRestrictionList();
		resetResetList();

		// Fill up the list again ..

		for (int ii = 0; ii < eg.getResetGroupCount(); ii++) {
			ResetGroup rg = eg.getResetGroup(ii);
			if (rg.evaluate(this)) {
				for (int iy = 0; iy < rg.getAttributeCodeCount(); iy++) {
					String strAttributeCode = rg.getAttributeCode(iy);
					EANMetaAttribute ma = eg.getMetaAttribute(strAttributeCode);
					if (ma != null) {
						EANAttribute att = getAttribute(strAttributeCode);
						putResetList(ma);
						if (att != null) {
							try {
								att.put(null);
							}
							catch (EANBusinessRuleException bre) {
								bre.printStackTrace();
							}
						}
					}
				}
			}
		}

	}

	/**
	 *  We need to loop through all the RestrictionGroup objects and puts all the
	 *  metaAttributes in a list that are currently deemed restricted for this
	 *  entity.
	 */
	protected void refreshRequired() {
		EntityGroup eg = (EntityGroup) getParent();
		if (eg == null) {
			return; // Nothing to do
		}

		// There is no required fields on a search
		if (isUsedInSearch()) {
			return;
		}

		getRequiredList();
		resetRequiredList();

		// Fill up the list again for the globals
		for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
			EANMetaAttribute ma = eg.getMetaAttribute(ii);
			if (ma.isRequired()) {
				putRequiredList(ma);
			}
		}

		// Now.. go for the variable Required thing
		for (int ii = 0; ii < eg.getRequiredGroupCount(); ii++) {
			RequiredGroup rg = eg.getRequiredGroup(ii);
			if (rg.evaluate(this)) {
				for (int iy = 0; iy < rg.getAttributeCodeCount(); iy++) {
					String strAttributeCode = rg.getAttributeCode(iy);
					EANMetaAttribute ma = eg.getMetaAttribute(strAttributeCode);
					if (ma != null) {
						putRequiredList(ma);
					}
				}
			}
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public boolean canEdit() {
		EntityGroup eg = getEntityGroup();
		if (eg == null) {
			D.ebug(D.EBUG_SPEW, "EntityItem.canEdit(): "+getKey()+" entity Group is null");
			return false;
		}
		if (!eg.canEdit()) {
			return false;
		}

		if(isVEEdit()){
			// this item may not be editable by the VEEdit action
			EntityList thelist = getEntityGroup().getEntityList();
			ExtractActionItem ean = (ExtractActionItem)thelist.getParentActionItem();
			if(!ean.isEditable(getEntityType()) && !ean.isCreatable(getEntityType())){
				return false;
			}
		}

		//RQ0713072645
		if (!m_beditDomainChked){
			m_beditDomainChked = true;
			boolean indomain = true;
			try{ // RQ0713072645
				EntityList list = eg.getEntityList();
				EANActionItem actionItem = null;
				if (list!=null){
					actionItem = list.getParentActionItem();
				}
				// search uses an entityitem to capture parameters
				if (actionItem instanceof EditActionItem){
					EntityList.checkDomain(getProfile(),actionItem,this);
				}else{
					D.ebug(D.EBUG_SPEW, "EntityItem.canEdit() BYPASSING domain check for "+getKey()+
							" because action is not edit action '"+actionItem+"'");
				}
			}catch (DomainException e) { // RQ0713072645
				//errmsg =  e.getMessage();
				indomain = false;
				e.dereference();
			}

			m_bcanEdit = indomain;
		}

		return m_bcanEdit;//RQ0713072645 return true;
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public boolean canCreate() {
		return false;
	}

	/**
	 *  Gets the usedInSearch attribute of the EntityItem object
	 *
	 *@return    The usedInSearch value
	 */
	public boolean isUsedInSearch() {
		EntityGroup eg = getEntityGroup();
		if (eg != null) {
			return eg.isUsedInSearch();
		}
		return false;
	}

	/**
	 *  Gets the dynaTable attribute of the EntityItem object
	 *
	 *@return    The dynaTable value
	 */
	public boolean isDynaTable() {
		return isUsedInSearch();
	}

	/**
	 *  Adds a feature to the Row attribute of the EntityItem object
	 *
	 *@return    Description of the Return Value
	 */
	public boolean addRow() {
		return false;
	}

	/**
	 *  Adds a feature to the Row attribute of the EntityItem object
	 *
	 *@return    Description of the Return Value
	 */
	public boolean addRow(String _strKey) {
		return false;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _strKey  Description of the Parameter
	 */
	public void removeRow(String _strKey) {
	}

	//

	// Restiction List methods
	//

	/**
	 *  Description of the Method
	 */
	protected void resetRestrictionList() {
		m_elRestrict = new EANList();
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _ma  Description of the Parameter
	 */
	protected void putRestrictionList(EANMetaAttribute _ma) {
		if (m_elRestrict == null) {
			m_elRestrict = new EANList();
		}

		m_elRestrict.put(_ma);
	}

	/**
	 *  Gets the restrictionListCount attribute of the EntityItem object
	 *
	 *@return    The restrictionListCount value
	 */
	protected int getRestrictionListCount() {
		if (m_elRestrict == null) {
			return 0;
		}
		return m_elRestrict.size();
	}

	/**
	 *  Gets the restrictionList attribute of the EntityItem object
	 *
	 *@return    The restrictionList value
	 */
	protected EANList getRestrictionList() {
		if (m_elRestrict == null) {
			m_elRestrict = new EANList();
		}
		return m_elRestrict;
	}

	/**
	 *  Gets the restrictionList attribute of the EntityItem object
	 *
	 *@param  _i  Description of the Parameter
	 *@return     The restrictionList value
	 */
	protected EANMetaAttribute getRestrictionList(int _i) {
		if (m_elRestrict == null) {
			return null;
		}
		return (EANMetaAttribute) m_elRestrict.getAt(_i);
	}

	/**
	 *  Gets the restrictionList attribute of the EntityItem object
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The restrictionList value
	 */
	protected EANMetaAttribute getRestrictionList(String _str) {
		if (m_elRestrict == null) {
			return null;
		}
		return (EANMetaAttribute) m_elRestrict.get(_str);
	}

	/**
	 *  Gets the restricted attribute of the EntityItem object
	 *
	 *@param  _ma  Description of the Parameter
	 *@return      The restricted value
	 */
	protected boolean isRestricted(EANMetaAttribute _ma) {
		// If the meta Attribute is super Editable
		// we always return false.  It is immune from
		// any restriction.
		if (_ma.isSuperEditable()) {
			return false;
		}
		if (m_elRestrict == null) {
			return false;
		}
		return (m_elRestrict.containsKey(_ma.getKey()));
	}

	//
	// Classify methods
	/**
	 *  Description of the Method
	 */
	protected void resetClassificationList() {
		m_elClassify = new EANList();
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _ma  Description of the Parameter
	 */
	protected void putClassificationList(EANMetaAttribute _ma) {
		if (m_elClassify == null) {
			m_elClassify = new EANList();
		}
		m_elClassify.put(_ma);
	}

	/**
	 *  Gets the classificationListCount attribute of the EntityItem object
	 *
	 *@return    The classificationListCount value
	 */
	protected int getClassificationListCount() {
		if (m_elClassify == null) {
			return 0;
		}
		return m_elClassify.size();
	}

	/**
	 *  Gets the classificationList attribute of the EntityItem object
	 *
	 *@return    The classificationList value
	 */
	protected EANList getClassificationList() {
		if (m_elClassify == null) {
			m_elClassify = new EANList();
		}
		return m_elClassify;
	}

	/**
	 *  Gets the classificationList attribute of the EntityItem object
	 *
	 *@param  _i  Description of the Parameter
	 *@return     The classificationList value
	 */
	protected EANMetaAttribute getClassificationList(int _i) {
		if (m_elClassify == null) {
			return null;
		}
		return (EANMetaAttribute) m_elClassify.getAt(_i);
	}

	/**
	 *  Gets the classificationList attribute of the EntityItem object
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The classificationList value
	 */
	protected EANMetaAttribute getClassificationList(String _str) {
		if (m_elClassify == null) {
			return null;
		}
		return (EANMetaAttribute) m_elClassify.get(_str);
	}

	/**
	 *  Gets the classified attribute of the EntityItem object
	 *
	 *@param  _ma  Description of the Parameter
	 *@return      The classified value
	 */
	public boolean isClassified(EANMetaAttribute _ma) {
		if (m_elClassify == null) {
			return false;
		}
		return (m_elClassify.containsKey(_ma.getKey()));
	}

	//
	// Required List methods
	//

	/**
	 *  Description of the Method
	 */
	protected void resetRequiredList() {
		m_elRequired = new EANList();
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _ma  Description of the Parameter
	 */
	protected void putRequiredList(EANMetaAttribute _ma) {
		if (m_elRequired == null) {
			m_elRequired = new EANList();
		}
		m_elRequired.put(_ma);
	}

	/**
	 *  Gets the requiredListCount attribute of the EntityItem object
	 *
	 *@return    The requiredListCount value
	 */
	protected int getRequiredListCount() {
		if (m_elRequired == null) {
			return 0;
		}
		return m_elRequired.size();
	}

	/**
	 *  Gets the requiredList attribute of the EntityItem object
	 *
	 *@return    The requiredList value
	 */
	protected EANList getRequiredList() {
		if (m_elRequired == null) {
			m_elRequired = new EANList();
		}
		return m_elRequired;
	}

	/**
	 *  Gets the requiredList attribute of the EntityItem object
	 *
	 *@param  _i  Description of the Parameter
	 *@return     The requiredList value
	 */
	protected EANMetaAttribute getRequiredList(int _i) {
		if (m_elRequired == null) {
			return null;
		}
		return (EANMetaAttribute) m_elRequired.getAt(_i);
	}

	/**
	 *  Gets the requiredList attribute of the EntityItem object
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The requiredList value
	 */
	protected EANMetaAttribute getRequiredList(String _str) {
		if (m_elRequired == null) {
			return null;
		}
		return (EANMetaAttribute) m_elRequired.get(_str);
	}

	/**
	 *  Gets the required attribute of the EntityItem object
	 *
	 *@param  _ma  Description of the Parameter
	 *@return      The required value
	 */
	protected boolean isRequired(EANMetaAttribute _ma) {
		if (m_elRequired == null) {
			return false;
		}
		return (m_elRequired.containsKey(_ma.getKey()));
	}

	/**
	 *  Description of the Method
	 */
	protected void resetResetList() {
		m_elReset = new EANList();
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _ma  Description of the Parameter
	 */
	protected void putResetList(EANMetaAttribute _ma) {
		if (m_elReset == null) {
			m_elReset = new EANList();
		}
		m_elReset.put(_ma);
	}

	/**
	 *  Gets the resetListCount attribute of the EntityItem object
	 *
	 *@return    The resetListCount value
	 */
	protected int getResetListCount() {
		if (m_elReset == null) {
			return 0;
		}
		return m_elReset.size();
	}

	/**
	 *  Gets the resetList attribute of the EntityItem object
	 *
	 *@return    The resetList value
	 */
	protected EANList getResetList() {
		if (m_elReset == null) {
			m_elReset = new EANList();
		}
		return m_elReset;
	}

	/**
	 *  Gets the resetList attribute of the EntityItem object
	 *
	 *@param  _i  Description of the Parameter
	 *@return     The resetList value
	 */
	protected EANMetaAttribute getResetList(int _i) {
		if (m_elReset == null) {
			return null;
		}
		return (EANMetaAttribute) m_elReset.getAt(_i);
	}

	/**
	 *  Gets the resetList attribute of the EntityItem object
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The resetList value
	 */
	protected EANMetaAttribute getResetList(String _str) {
		if (m_elReset == null) {
			return null;
		}
		return (EANMetaAttribute) m_elReset.get(_str);
	}

	/**
	 *  Gets the reset attribute of the EntityItem object
	 *
	 *@param  _ma  Description of the Parameter
	 *@return      The reset value
	 */
	protected boolean isReset(EANMetaAttribute _ma) {
		if (m_elReset == null) {
			return false;
		}
		return (m_elReset.containsKey(_ma.getKey()));
	}

	/**
	 *  This takes the attributes currently in the passed _ent
	 *  clones each att and assigns its parent to this  entity item
	 *  and assigned the metaAttribute of this EntityItem's group
	 *  to it
	 *
	 * @param _ent
	 */
	protected void setAttribute(EANEntity _ent) {

		resetAttribute();
		for (int ii = 0; ii < _ent.getAttributeCount(); ii++) {

			EntityGroup eg = getEntityGroup();
			EANAttribute origatt = _ent.getAttribute(ii);
			EANAttribute att = verifyAttributeClass(eg, origatt);
			//EANAttribute att = _ent.getAttribute(ii).cloneStructure(); //MN36915802
			if (att != null){
				putAttribute(att);
				att.setParent(this);

				// If the parent is already part of an entityGroup.. we assign the meta
				if (eg != null) {
					att.setMetaAttribute(eg.getMetaAttribute(att.getKey()));
				}
			}
		}
	}
	/**********************************
	 * MN36915802/36915859
	 * Some attribute types are overridden by entitytype.
	 * This is used when the current action has an "Editlike" purpose but the attribute was for other purposes
	 * and used the PDH attribute type.  Conflicts occur when the attribute is copied from the non-edit
	 * flavor like Navigate or Search to the Edit flavor.
	 * Remove conflicting attributes to prevent class cast exception
	 * @param eg
	 * @param origatt
	 * @return
	 */
	private EANAttribute verifyAttributeClass(EntityGroup eg, EANAttribute origatt){
		EANAttribute att = null;
		if (eg != null){
			EANMetaAttribute ma = eg.getMetaAttribute(origatt.getAttributeCode());

			if (ma instanceof MetaSingleFlagAttribute && !(origatt instanceof SingleFlagAttribute)) {
				//don't clone attribute
				D.ebug(D.EBUG_WARN, "**EntityItem.verifyAttributeClass and 'U' Meta mismatch for " +getKey()+" "+ma.getAttributeCode());
				if (origatt instanceof MultiFlagAttribute){
					// convert this to a SingleFlagAttribute
					MultiFlagAttribute origmfa = (MultiFlagAttribute)origatt;
					// Original attribute's EntityGroup may be null at this point, so can't use get()
					EANMetaFlagAttribute metafa = (EANMetaFlagAttribute)ma;
					for (int i=0; i<metafa.getMetaFlagCount(); i++){
						MetaFlag mf = metafa.getMetaFlag(i);
						if (origmfa.isSelected(mf)){
							SingleFlagAttribute sfa;
							try {
								sfa = new SingleFlagAttribute(this, null, (MetaSingleFlagAttribute) ma);
								sfa.putPDHFlag(mf.getFlagCode());
								att = sfa;
							} catch (MiddlewareRequestException e) {
								e.printStackTrace();
							}
							break;
						}
					}
				}
				//Thread.dumpStack();
			}else if (ma instanceof MetaMultiFlagAttribute && !(origatt instanceof MultiFlagAttribute)) {
				//don't clone attribute
				D.ebug(D.EBUG_WARN, "**EntityItem.verifyAttributeClass and 'F' Meta mismatch for " +getKey()+" "+ma.getAttributeCode());
				if (origatt instanceof SingleFlagAttribute){
					// convert this to a MultiFlagAttribute
					SingleFlagAttribute origsfa = (SingleFlagAttribute)origatt;
					// Original attribute's EntityGroup may be null at this point, so can't use get()
					EANMetaFlagAttribute metafa = (EANMetaFlagAttribute)ma;
					for (int i=0; i<metafa.getMetaFlagCount(); i++){
						MetaFlag mf = metafa.getMetaFlag(i);
						if (origsfa.isSelected(mf)){
							MultiFlagAttribute mfa;
							try {
								mfa = new MultiFlagAttribute(this, null, (MetaMultiFlagAttribute) ma);
								mfa.putPDHFlag(mf.getFlagCode());
								att = mfa;
							} catch (MiddlewareRequestException e) {
								e.printStackTrace();
							}
							break;
						}
					}
				}
				//Thread.dumpStack();
			}else{
				att = origatt.cloneStructure();
			}
		}else{
			att = origatt.cloneStructure();
		}

		return att;
	}
	/**
	 *  Gets the lockGroup attribute of the EntityItem object
	 *
	 *@return    The lockGroup value
	 */
	public LockGroup getLockGroup() {
		return m_lockgroup;
	}

	/**
	 *  Sets the lockGroup attribute of the EntityItem object
	 *
	 *@param  _lg  The new lockGroup value
	 */
	public void setLockGroup(LockGroup _lg) {
		m_lockgroup = _lg;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _str          Description of the Parameter
	 *@param  _rdi          Description of the Parameter
	 *@param  _db           Description of the Parameter
	 *@param  _ll           Description of the Parameter
	 *@param  _prof         Description of the Parameter
	 *@param  _lockOwnerEI  Description of the Parameter
	 *@param  _iLockType    Description of the Parameter
	 */
	public void unlock(String _str, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof,
			EntityItem _lockOwnerEI, int _iLockType) {
		// Lets get the Meta Attribute based upon the string

		StringTokenizer st = new StringTokenizer(_str, ":");
		String strEntityType = st.nextToken();
		st.nextToken();

		if (strEntityType.equals(getEntityType())) {
			unlock(_rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType);
		} else {
			EntityItem ei = (EntityItem) getDownLink(0);
			if (ei != null) {
				EntityGroup eg = ei.getEntityGroup();
				if (eg != null) {
					EntityList entl = eg.getEntityList();
					if (entl != null && entl.isCreateParent()) {
						ei = (EntityItem) getUpLink(0);
					}
				}
			}

			if (ei == null) {
				return;
			}
			ei.unlock(_rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType);
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _rdi          Description of the Parameter
	 *@param  _db           Description of the Parameter
	 *@param  _ll           Description of the Parameter
	 *@param  _prof         Description of the Parameter
	 *@param  _lockOwnerEI  Description of the Parameter
	 *@param  _iLockType    Description of the Parameter
	 */
	public void unlock(RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI,
			int _iLockType) {
		if (_ll != null && m_lockgroup != null && _prof != null) {

			String strLockOwner = _lockOwnerEI.getKey();
			String key = getEntityType() + getEntityID() + _lockOwnerEI.getEntityType() + _lockOwnerEI.getEntityID() + strLockOwner;

			if (m_lockgroup.getLockItem(key) != null) {
				try {
					// refresh the lock level
					m_lockgroup = refreshLockGroup(_rdi, _db, _prof, _ll, _lockOwnerEI, strLockOwner, _iLockType, false);

					EntityItem ei = new EntityItem(null, _prof, getEntityType(), getEntityID());
					if (_db != null) {
						m_lockgroup = m_lockgroup.removeLockItem(_db, ei, _prof, _lockOwnerEI, strLockOwner, _iLockType);
					} else if (_rdi != null) {
						m_lockgroup = m_lockgroup.reRemoveLockItem(_rdi, ei, _prof, _lockOwnerEI, strLockOwner, _iLockType);
					}
					if (m_lockgroup != null) {
						if (m_lockgroup.getLockItemCount() <= 0) {
							_ll.removeLockGroup(getEntityType() + getEntityID());
							m_lockgroup = null;
						} else {
							_ll.putLockGroup(m_lockgroup);
						}
					}
				}
				catch (Exception x) {
					x.printStackTrace();
				}
			}
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _str  Description of the Parameter
	 *@param  _ll   Description of the Parameter
	 */
	public void resetLockGroup(String _str, LockList _ll) {
		// Lets get the Meta Attribute based upon the string
		StringTokenizer st = new StringTokenizer(_str, ":");
		String strEntityType = st.nextToken();
		st.nextToken();

		if (strEntityType.equals(getEntityType())) {
			resetLockGroup(_ll);
		} else {
			EntityItem ei = (EntityItem) getDownLink(0);
			if (ei == null) {
				return;
			}
			ei.resetLockGroup(_ll);
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _ll  Description of the Parameter
	 */
	public void resetLockGroup(LockList _ll) {
		m_lockgroup = null;
		if (_ll != null) {
			_ll.removeLockGroup(getEntityType() + getEntityID());
		}
	}

	/*
	 *  This method is used for setting the parent for the newly created entity only
	 */
	/**
	 *  Sets the parentEntityItem attribute of the EntityItem object
	 *
	 *@param  _ei  The new parentEntityItem value
	 */
	public void setParentEntityItem(EntityItem _ei) {

		EntityGroup eg = getEntityGroup();
		EntityList entl = eg.getEntityList();

		String strParentEntityType = null;
		if (getEntityID() > 0) {
			D.ebug(D.EBUG_SPEW, "10110.0 setParentEntityItem() "+getKey()+" was called on an Entity/Relator that already exists.");
			return;
		}

		if (_ei == null) {
			D.ebug(D.EBUG_SPEW, "10110.1 setParentEntityItem() "+getKey()+"  _ei is null.");
			return;
		}

		if (eg == null) {
			D.ebug(D.EBUG_SPEW, "10110.2 setParentEntityItem() "+getKey()+" _ei is not attached to any EntityGroup.");
			return;
		}

		if (eg.isRelator() || eg.isAssoc()) {
			// Check for Newness // if we are not new.. we do not do anything..
			if (entl != null && entl.isCreateParent()) {
				EntityItem eip = (EntityItem) getDownLink(0);
				if (eip == null) {
					D.ebug(D.EBUG_SPEW, "10110.3 setParentEntityItem() this guy:" + getKey() + ": Has no dnlink");
					return;
				}
				strParentEntityType = eip.getEntityType();
				if (strParentEntityType.equals(_ei.getEntityType())) {
					resetDownLink();
					putDownLink(_ei);
				}
			} else {

				EntityItem eip = (EntityItem) getUpLink(0);
				if (eip == null) {
					D.ebug(D.EBUG_SPEW, "10110.3 setParentEntityItem() this guy:" + getKey() + ": Has no uplink");
					return;
				}
				strParentEntityType = eip.getEntityType();
				if (strParentEntityType.equals(_ei.getEntityType())) {
					resetUpLink();
					putUpLink(_ei);
				}
			}
		}
	}

	/**
	 *  Gets the matrixValue attribute of the EntityItem object
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The matrixValue value
	 */
	public Object getMatrixValue(String _str) {
		return null;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _str  Description of the Parameter
	 *@param  _o    Description of the Parameter
	 */
	public void putMatrixValue(String _str, Object _o) {
	}

	/**
	 *  Gets the matrixEditable attribute of the EntityItem object
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The matrixEditable value
	 */
	public boolean isMatrixEditable(String _str) {
		return false;
	}

	/**
	 *  Description of the Method
	 */
	public void rollbackMatrix() {
	}

	/**
	 *  Adds a feature to the Column attribute of the EntityItem object
	 *
	 *@param  _ean  The feature to be added to the Column attribute
	 */
	public void addColumn(EANFoundation _ean) {
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _db              Description of the Parameter
	 *@param  _rdi             Description of the Parameter
	 *@param  _prof            Description of the Parameter
	 *@param  _strRelatorType  Description of the Parameter
	 *@return                  Description of the Return Value
	 */
	public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
		return null;
	}

	/**
	 *  Gets the longDescription attribute of the EntityItem object
	 *
	 *@return    The longDescription value
	 */
	public String getLongDescription() {
		EntityItem ieDown = null;

		int iDisplayTotal = getDisplayLimit();
		boolean bDisplayLimit = (iDisplayTotal > 0);
		boolean bFirstPass = true;
		boolean bRelAss = false;
		StringBuffer strbResult = new StringBuffer();
		EntityGroup eg = getEntityGroup();

		if (eg == null) {
			for (int ii = 0; ii < getAttributeCount(); ii++) {
				EANAttribute att = getAttribute(ii);
				EANMetaAttribute ma = att.getMetaAttribute();
				if (ma.isNavigate()) {
					if (bDisplayLimit) {														//cr0428066810
						if (display(--iDisplayTotal)) {											//cr0428066810
							strbResult.append( (bFirstPass ? "" : ", ") + att.toString());		//cr0428066810
						}																		//cr0428066810
					} else {																	//cr0428066810
						strbResult.append( (bFirstPass ? "" : ", ") + att.toString());
					}																			//cr0428066810
					bFirstPass = false;
				}
			}
		} else {
			if (eg.isRelator() || eg.isAssoc()) {
				bRelAss = true;
			}
			for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
				EANMetaAttribute ma = eg.getMetaAttribute(ii);
				if (ma.isNavigate()) {
					EANAttribute att = getAttribute(ma.getAttributeCode());
					if (bDisplayLimit) {																			//cr0428066810
						if (display(--iDisplayTotal)) {																//cr0428066810
							strbResult.append( (bFirstPass ? "" : ", ") + (att == null ? "" : att.toString()));		//cr0428066810
						}																							//cr0428066810
					} else {																						//cr0428066810
						strbResult.append( (bFirstPass ? "" : ", ") + (att == null ? "" : att.toString()));
					}																								//cr0428066810
					bFirstPass = false;
				}
			}
		}

		// If there is a down link.. lets go get it!
		ieDown = (EntityItem) getDownLink(0);
		if (ieDown != null && bRelAss) {
			if (bDisplayLimit) {																				//cr0428066810
				if (display(--iDisplayTotal)) {																	//cr0428066810
					strbResult.append((strbResult.toString().length() == 0 ? "" : ", ") + ieDown.toString());	//cr0428066810
				}																								//cr0428066810
			} else {																							//cr0428066810
				strbResult.append( (strbResult.toString().length() == 0 ? "" : ", ") + ieDown.toString());
			}																									//cr0428066810
		}

		return strbResult.toString();
	}

	/**
	 *  Gets the shortDescription attribute of the EntityItem object
	 *
	 *@return    The shortDescription value
	 */
	public String getShortDescription() {

		EntityItem ieDown = null;

		boolean bFirstPass = true;
		boolean bRelAss = false;
		StringBuffer strbResult = new StringBuffer();
		EntityGroup eg = getEntityGroup();

		if (eg == null) {
			for (int ii = 0; ii < getAttributeCount(); ii++) {
				EANAttribute att = getAttribute(ii);
				EANMetaAttribute ma = att.getMetaAttribute();
				if (ma.isNavigate()) {
					strbResult.append( (bFirstPass ? "" : ", ") + att.get(EANAttribute.VALUE, false));
					bFirstPass = false;
				}
			}
		} else {
			//strbResult.append(eg.toString() + ": ");
			if (eg.isRelator() || eg.isAssoc()) {
				bRelAss = true;
			}
			for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
				EANMetaAttribute ma = eg.getMetaAttribute(ii);
				if (ma.isNavigate()) {
					EANAttribute att = getAttribute(ma.getAttributeCode());
					strbResult.append( (bFirstPass ? "" : ", ") + (att == null ? "" : att.get(EANAttribute.VALUE, false)));
					bFirstPass = false;
				}
			}
		}

		// If there is a down link.. lets go get it!
		ieDown = (EntityItem) getDownLink(0);
		if (ieDown != null && bRelAss) {
			strbResult.append("/" + ieDown.getShortDescription());
		}

		return strbResult.toString();
	}

	/**
	 *  Gets the navAttrDescription attribute of the EntityItem object
	 *
	 *@return    The navAttrDescription value
	 */
	public String getNavAttrDescription() {
		boolean bFirstPass = true;
		StringBuffer strbResult = new StringBuffer();
		EntityGroup eg = getEntityGroup();
		if (eg == null) {
			for (int ii = 0; ii < getAttributeCount(); ii++) {
				EANAttribute att = getAttribute(ii);
				EANMetaAttribute ma = att.getMetaAttribute();
				if (ma != null && ma.isNavigate() && (!ma.isDerived())) {
					strbResult.append( (bFirstPass ? "" : ", ") + att.get(EANAttribute.VALUE, false));
					bFirstPass = false;
				}
			}
		} else {
			for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
				EANMetaAttribute ma = eg.getMetaAttribute(ii);
				if (ma.isNavigate() && (!ma.isDerived())) {
					EANAttribute att = getAttribute(ma.getAttributeCode());
					strbResult.append( (bFirstPass ? "" : ", ") + (att == null ? "" : att.get(EANAttribute.VALUE, false)));
					bFirstPass = false;
				}
			}
		}
		return strbResult.toString();
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _db                           Description of the Parameter
	 *@param  _rdi                          Description of the Parameter
	 *@param  _prof                         Description of the Parameter
	 *@param  _strRowKey                    Description of the Parameter
	 *@return                               Description of the Return Value
	 *@exception  EANBusinessRuleException  Description of the Exception
	 */
	public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws
	EANBusinessRuleException {
		return false;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _db                           Description of the Parameter
	 *@param  _rdi                          Description of the Parameter
	 *@param  _prof                         Description of the Parameter
	 *@param  _strRowKey                    Description of the Parameter
	 *@param  _aeiChild                     Description of the Parameter
	 *@return                               Description of the Return Value
	 *@exception  EANBusinessRuleException  Description of the Exception
	 * @param _strLinkOption
	 */
	public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey,
			EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
		return null;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _db              Description of the Parameter
	 *@param  _rdi             Description of the Parameter
	 *@param  _prof            Description of the Parameter
	 *@param  _strRelatorType  Description of the Parameter
	 *@return                  Description of the Return Value
	 */
	public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
		return null;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _db              Description of the Parameter
	 *@param  _rdi             Description of the Parameter
	 *@param  _prof            Description of the Parameter
	 *@return                  Description of the Return Value
	 * @param _astrKey
	 * @throws java.sql.SQLException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	 * @throws java.rmi.RemoteException
	 */
	public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException,
	MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
		return null;
	}

	/**
	 *  Gets the whereUsedList attribute of the EntityItem object
	 *
	 *@param  _db              Description of the Parameter
	 *@param  _rdi             Description of the Parameter
	 *@param  _prof            Description of the Parameter
	 *@param  _strRelatorType  Description of the Parameter
	 *@return                  The whereUsedList value
	 */
	public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
		return null;
	}

	/**
	 *  Gets the actionItemsAsArray attribute of the EntityItem object
	 *
	 *@param  _strKey  Description of the Parameter
	 *@return          The actionItemsAsArray value
	 * @param _db
	 * @param _rdi
	 * @param _prof
	 */
	public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
		return null;
	}

	/**
	 *  A unit test for JUnit
	 *  returns true if the classification key returns true
	 *  We have to look for both it and its child
	 *
	 *@param  _strEntityType  Description of the Parameter
	 *@param  _strClassKey    Description of the Parameter
	 *@return                 Description of the Return Value
	 */
	public boolean testClassification(String _strEntityType, String _strClassKey) {

		ClassificationGroup cg = null;

		EntityGroup eg = getEntityGroup();

		// Do we have to shift down to find the answer

		if (eg == null) {
			D.ebug(D.EBUG_SPEW, "testClassification: "+getKey()+" EntityGroup is null:" + _strEntityType + "/" + _strClassKey);
			return false;
		}

		if (!eg.getEntityType().equals(_strEntityType)) {
			if ( (eg.isRelator() || eg.isAssoc()) && eg.getEntity2Type().equals(_strEntityType)) {
				EntityItem ei = (EntityItem) getDownLink(0);
				if (ei == null) {
					D.ebug(D.EBUG_SPEW, "testClassification: "+getKey()+" DownLinked EntityItem is null:" + _strEntityType + "/" + _strClassKey);
					return false;
				}
				return ei.testClassification(_strEntityType, _strClassKey);
			}
			D.ebug(D.EBUG_SPEW, "testClassification: "+getKey()+" Passed EntityType cannot be found" + _strEntityType + "/" + _strClassKey);
			return false;
		}

		cg = eg.getClassificationGroup(_strClassKey);
		if (cg != null) {
			return cg.evaluate(this);
		}
		D.ebug(D.EBUG_SPEW, "testClassification: "+getKey()+" Passed Classification Key cannot be found" + _strEntityType + ":" + _strClassKey);
		return false;
	}

	/**
	 *  Description of the Method
	 */
	private void initLevels() {
		if (m_abLevel == null) {
			m_abLevel = new boolean[MAX_LEVEL];
		}
		for (int ii = 0; ii < MAX_LEVEL; ii++) {
			m_abLevel[ii] = false;
		}
	}

	/**
	 *  Gets the levels attribute of the EntityItem object
	 *
	 *@return    The levels value
	 */
	public boolean[] getLevels() {
		if (m_abLevel == null) {
			m_abLevel = new boolean[MAX_LEVEL];
		}
		return m_abLevel;
	}

	/**
	 *  Sets the levels attribute of the EntityItem object
	 *
	 *@param  _ab  The new levels value
	 */
	protected void setLevels(boolean[] _ab) {
		m_abLevel = _ab;
	}

	/**
	 *  Gets the levelsAsIntArray attribute of the EntityItem object
	 *
	 *@return    The levelsAsIntArray value
	 */
	public int[] getLevelsAsIntArray() {

		int ij = 0;
		int ik = 0;
		int[] aiReturn = null;

		if (m_abLevel == null) {
			m_abLevel = new boolean[MAX_LEVEL];
		}
		for (int ii = 0; ii < MAX_LEVEL; ii++) {
			if (m_abLevel[ii]) {
				ij++;
			}
		}

		aiReturn = new int[ij];

		for (int ii = 0; ii < MAX_LEVEL; ii++) {
			if (m_abLevel[ii]) {
				aiReturn[ik++] = ii;
			}
		}

		return aiReturn;
	}

	/**
	 *  Sets the level attribute of the EntityItem object
	 *
	 *@param  _i  The new level value
	 */
	protected void setLevel(int _i) {
		if (m_abLevel == null) {
			m_abLevel = new boolean[MAX_LEVEL];
		}
		if (_i <= MAX_LEVEL) {
			m_abLevel[_i] = true;
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _i  Description of the Parameter
	 */
	protected void clearLevel(int _i) {
		if (m_abLevel == null) {
			m_abLevel = new boolean[MAX_LEVEL];
		}
		if (_i <= MAX_LEVEL) {
			m_abLevel[_i] = false;
		}
	}

	/**
	 *  Gets the level attribute of the EntityItem object
	 *
	 *@param  _i  Description of the Parameter
	 *@return     The level value
	 */
	public boolean getLevel(int _i) {
		if (m_abLevel == null) {
			m_abLevel = new boolean[MAX_LEVEL];
		}
		if (_i <= MAX_LEVEL) {
			return m_abLevel[_i];
		}
		return false;
	}

	/**
	 * getInUseNLSIDs
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public int[] getInUseNLSIDs() {

		int[] ai = null;
		int ii = 0;
		Iterator it = null;

		Hashtable hsh = new Hashtable();

		for (int ij = 0; ij < getAttributeCount(); ij++) {

			EANAttribute att = getAttribute(ij);
			if (att instanceof EANTextAttribute) {
				if (att.m_hsh1 != null) {

					it = att.m_hsh1.keySet().iterator();

					while (it.hasNext()) {
						String strNLS = (String) it.next();
						if (!hsh.containsKey(strNLS)) {
							hsh.put(strNLS, "HI");
						}
					}
				}
			}
		}

		ai = new int[hsh.size()];
		it = hsh.keySet().iterator();
		while (it.hasNext()) {
			String strNLS = (String) it.next();
			ai[ii++] = Integer.valueOf(strNLS).intValue();
		}

		return ai;
	}

	/**
	 *  Reports results from previous call to setModifiedInInterval.
	 *
	 *@return    true if passes, false otherwise.
	 */
	public boolean wasModifiedInInterval() {
		return m_bWasModifiedInInterval;
	}

	/**
	 * setModifiedInInterval
	 *
	 *  @author David Bigelow
	 */
	protected void setModifiedInInterval() {
		m_bWasModifiedInInterval = true;
	}

	/**
	 *  determine whether this is in the given interval
	 *
	 *@param  _s             Description of the Parameter
	 *@param  _intervalItem  Description of the Parameter
	 */
	protected void calcAttributesModified(String _s, IntervalItem _intervalItem) {
		//if its w/in the interval -> this is a CHANGED ENTITYITEM FOREVER AND EVER (it can't be marked 'unchanged')
		if (_intervalItem.containsDate(_s)) {
			setModifiedInInterval();
			//!!!one way-valve
		}
		return;
	}

	/**
	 *  determine whether this is in the given interval
	 *
	 *@param  _s             Description of the Parameter
	 *@param  _intervalItem  Description of the Parameter
	 */
	protected void calcRelatorModified(String _s, IntervalItem _intervalItem) {
		if (!getEntityGroup().isRelator()) {
			return;
		}
		if (_intervalItem.containsDate(_s)) {
			setModifiedInInterval();
			//!!!one way-valve again
		}
		return;
	}

	//// END Interval methods

	/**
	 *  Get the EntityChangeHistoryGroup Object for this EntitryItem. Note that is
	 *  this EntityItem doubles as a Relator, then the Entity2Item will be used.
	 *
	 *@param  _db                             Description of the Parameter
	 *@return                                 The changeHistoryGroup value
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 */
	public EntityChangeHistoryGroup getChangeHistoryGroup(Database _db) throws SQLException, MiddlewareRequestException,
	MiddlewareException {
		return _db.getEntityChangeHistoryGroup(getProfile(), processForChangeHistory());
	}

	/**
	 *  Get the EntityChangeHistoryGroup Object for this EntitryItem. It will not
	 *  attempt to get downlink
	 *
	 *@param  _db                             Description of the Parameter
	 *@return                                 The changeHistoryGroup value
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 */
	public EntityChangeHistoryGroup getThisChangeHistoryGroup(Database _db) throws SQLException, MiddlewareRequestException,
	MiddlewareException {
		return _db.getEntityChangeHistoryGroup(getProfile(),
				new EntityItem(null, getProfile(), getEntityType(), getEntityID()));
	}
	/**
	 *  Get the EntityChangeHistoryGroup Object for this EntitryItem. Note that is
	 *  this EntityItem doubles as a Relator, then the Entity2Item will be used.
	 *
	 *@param  _rdi Description of the Parameter
	 *@return   The changeHistoryGroup value
	 *@exception  RemoteException
	 *@exception  MiddlewareRequestException
	 *@exception  MiddlewareException
	 *@exception  MiddlewareShutdownInProgressException
	 */
	public EntityChangeHistoryGroup getChangeHistoryGroup(RemoteDatabaseInterface _rdi) throws RemoteException,
	MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException {
		return _rdi.getEntityChangeHistoryGroup(getProfile(), processForChangeHistory());
	}

	/**
	 *  Get the EntityChangeHistoryGroup Object for this EntityItem. Will not
	 *  attempt to get downlink
	 *
	 *@param  _rdi Description of the Parameter
	 *@return   The changeHistoryGroup value
	 *@exception  RemoteException
	 *@exception  MiddlewareRequestException
	 *@exception  MiddlewareException
	 *@exception  MiddlewareShutdownInProgressException
	 */
	public EntityChangeHistoryGroup getThisChangeHistoryGroup(RemoteDatabaseInterface _rdi) throws RemoteException,
	MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException {
		return _rdi.getEntityChangeHistoryGroup(getProfile(),
				new EntityItem(null, getProfile(), getEntityType(), getEntityID()));
	}

	/**
	 *  Description of the Method
	 *
	 *@return                                 Description of the Return Value
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	private EntityItem processForChangeHistory() throws MiddlewareRequestException {
		try {
			if (getEntityGroup().isRelator() || getEntityGroup().isAssoc()) {
				if (getDownLinkCount() > 0) {
					EntityItem eiDown = (EntityItem) getDownLink(0);
					return new EntityItem(null, eiDown.getProfile(), eiDown.getEntityType(), eiDown.getEntityID());
				}
			}
		}
		catch (Exception exc) {
			System.out.println("EntityItem.processForChangeHistory 9999 "+getKey()+" " + exc.toString());
			exc.printStackTrace();
		}
		return new EntityItem(null, getProfile(), getEntityType(), getEntityID());
	}

	/**
	 * getActualColumnListCount
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public int getActualColumnListCount() {
		return getColumnList().size();
	}

	/**
	 * getActualRowListCount
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public int getActualRowListCount() {
		return getActualRowList().size();
	}

	private EANList getActualRowList() {

		EANList el = new EANList();
		EntityGroup eg = (EntityGroup) getParent();

		refreshClassifications();

		if (eg == null) {
			D.ebug(D.EBUG_SPEW, "EntityItem.getActualRowList() *** "+getKey()+" EntityGroup is null");
			return el;
		}

		// If we are a relator.. lets  get the Child EntityList
		if (!isVEEdit() && eg.isRelator() || eg.isAssoc()) {
			EntityItem eiDown = (EntityItem) getDownLink(0);
			if (eiDown != null) {
				el = eiDown.getActualRowList();
			}
		}

		for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
//			EANMetaAttribute ma = eg.getMetaAttribute(ii);
			EANMetaAttribute ma = null;
			Object o = eg.getMeta(ii);
			if (o instanceof EANMetaAttribute) { //acl_yyu null pointer
				ma = (EANMetaAttribute) o; //acl_yyu null pointer
			} //acl_yyu null pointer
			if (o instanceof Implicator) {
				Implicator myImp = (Implicator) o;
				// new VEEdit
				EANFoundation ean = getEANObject(myImp.getKey());
				if (ean != null) {
					el.put(ean);
				}
			} else if ( (eg.isNavigateLike() && ma.isNavigate()) || !eg.isNavigateLike()) {
				try {
					String strRel = "C";
					if (eg.isRelator()){
						strRel = "R";
					}
					String strKey = getEntityType() + ":" + ma.getKey()+":"+strRel;
					if (!eg.isNavigateLike() && eg.isClassified()) {

						eg.getGlobalClassificationGroup();
						// If we are new here.. we need to either show the classification vars in
						// addition to anything that is classified.

						if (isNew()) {
							// Here is where we only show the classified stuff on a new record
							if (ma.isClassified() || isClassified(ma)) {
								el.put(new Implicator(getEANObject(strKey), null, strKey));
							}
						} else if (isClassified(ma) || ma.isClassified() || ma.isDerived()) {
							el.put(new Implicator(getEANObject(strKey), null, strKey));
						}
					} else {
						el.put(new Implicator(getEANObject(strKey), null, strKey));
					}
				}
				catch (Exception x) {
					x.printStackTrace();
				}
			}
		}
		return el;
	}

	/**
	 * duplicate
	 *
	 * @param _eiOrg
	 * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
	 * @return
	 *  @author David Bigelow
	 */
	public boolean duplicate(EntityItem _eiOrg) throws EANBusinessRuleException {
		if (_eiOrg == null) {
			return false;
		}

		if (!_eiOrg.getEntityType().equals(getEntityType())) {
			return false;
		}
		for (int j = 0; j < _eiOrg.getAttributeCount(); j++) {
			EANAttribute attrOrg = _eiOrg.getAttribute(j);
			EANMetaAttribute ma = attrOrg.getMetaAttribute();

			if (!ma.isExcludeFromCopy()) {
				try {
					if (ma instanceof MetaTextAttribute) {
						TextAttribute ta = new TextAttribute(this, null, (MetaTextAttribute) ma);
						putAttribute(ta);
						ta.duplicate(attrOrg.get());
					} else if (ma instanceof MetaSingleFlagAttribute) {
						SingleFlagAttribute sfa = new SingleFlagAttribute(this, null, (MetaSingleFlagAttribute) ma);
						putAttribute(sfa);
						sfa.duplicate(attrOrg.get());
					} else if (ma instanceof MetaMultiFlagAttribute) {
						MultiFlagAttribute mfa = new MultiFlagAttribute(this, null, (MetaMultiFlagAttribute) ma);
						putAttribute(mfa);
						mfa.duplicate(attrOrg.get());
					} else if (ma instanceof MetaStatusAttribute) {
						StatusAttribute sa = new StatusAttribute(this, null, (MetaStatusAttribute) ma);
						putAttribute(sa);
						sa.duplicate(attrOrg.get());
					} else if (ma instanceof MetaTaskAttribute) {
						TaskAttribute tska = new TaskAttribute(this, null, (MetaTaskAttribute) ma);
						putAttribute(tska);
						tska.duplicate(attrOrg.get());
					} else if (ma instanceof MetaLongTextAttribute) {
						LongTextAttribute lta = new LongTextAttribute(this, null, (MetaLongTextAttribute) ma);
						putAttribute(lta);
						lta.duplicate(attrOrg.get());
					} else if (ma instanceof MetaXMLAttribute) {
						XMLAttribute xa = new XMLAttribute(this, null, (MetaXMLAttribute) ma);
						putAttribute(xa);
						xa.duplicate(attrOrg.get());
					} else if (ma instanceof MetaBlobAttribute) {
						BlobAttribute ba = new BlobAttribute(this, null, (MetaBlobAttribute) ma);
						putAttribute(ba);
						ba.duplicate(attrOrg.get());
					}
				}
				catch (MiddlewareRequestException mre) {
					mre.printStackTrace();
				}
			}
		}

		refreshClassifications();
		refreshRestrictions();
		refreshDefaults();
		refreshRequired();
		refreshResets();

		return true;
	}

	/**
	 * (non-Javadoc)
	 * duplicate
	 *
	 * @see COM.ibm.eannounce.objects.EANTableWrapper#duplicate(java.lang.String, int)
	 */
	public boolean duplicate(String _strKey, int _iDup) {
		return false;
	}

	/**
	 * used to improve performance, only copy navigate attributes from an edited entity into the
	 * original navigate entity
	 * @param eiEdited
	 * @return
	 */
	public boolean duplicateUpdates(EntityItem eiEdited) {
		if (eiEdited == null) {
			return false;
		}

		if (!eiEdited.getEntityType().equals(getEntityType())) {
			return false;
		}
		EntityGroup eg = this.getEntityGroup();
		for (int j = 0; j < eg.getMetaAttributeCount(); j++) {
			//use meta attr from this entity
			EANMetaAttribute ma = eg.getMetaAttribute(j);
			EANAttribute attrEdited = eiEdited.getAttribute(ma.getAttributeCode());
			EANAttribute attrCurr = getAttribute(ma.getAttributeCode());

			if(attrEdited==null && attrCurr == null){
				continue;
			}
	
			try {
				if (ma instanceof MetaTextAttribute) {
					TextAttribute ta = new TextAttribute(this, null, (MetaTextAttribute) ma);
					putAttribute(ta);
					ta.duplicate(attrEdited.get());
				} else if (ma instanceof MetaSingleFlagAttribute) {
					SingleFlagAttribute sfa = new SingleFlagAttribute(this, null, (MetaSingleFlagAttribute) ma);
					putAttribute(sfa);
					sfa.duplicate(attrEdited.get());
				} else if (ma instanceof MetaMultiFlagAttribute) {
					MultiFlagAttribute mfa = new MultiFlagAttribute(this, null, (MetaMultiFlagAttribute) ma);
					putAttribute(mfa);
					mfa.duplicate(attrEdited.get());
				} else if (ma instanceof MetaStatusAttribute) {
					StatusAttribute sa = new StatusAttribute(this, null, (MetaStatusAttribute) ma);
					putAttribute(sa);
					sa.duplicate(attrEdited.get());
				} else if (ma instanceof MetaTaskAttribute) {
					TaskAttribute tska = new TaskAttribute(this, null, (MetaTaskAttribute) ma);
					putAttribute(tska);
					tska.duplicate(attrEdited.get());
				} else if (ma instanceof MetaLongTextAttribute) {
					LongTextAttribute lta = new LongTextAttribute(this, null, (MetaLongTextAttribute) ma);
					putAttribute(lta);
					lta.duplicate(attrEdited.get());
				} else if (ma instanceof MetaXMLAttribute) {
					XMLAttribute xa = new XMLAttribute(this, null, (MetaXMLAttribute) ma);
					putAttribute(xa);
					xa.duplicate(attrEdited.get());
				} else if (ma instanceof MetaBlobAttribute) {
					BlobAttribute ba = new BlobAttribute(this, null, (MetaBlobAttribute) ma);
					putAttribute(ba);
					ba.duplicate(attrEdited.get());
				}
			}
			catch (MiddlewareRequestException mre) {
				mre.printStackTrace();
			}
		}

		refreshClassifications();
		refreshRestrictions();
		refreshDefaults();
		refreshRequired();
		refreshResets();

		//if uplink has implicators like ANNOUNCEMENT:ANNTYPE:C on WGANNOUNCEMENTA, they must
		//be cleared
		for(int p=0;p<this.getUpLinkCount(); p++){
			EntityItem upitem = (EntityItem)this.getUpLink(p);
			upitem.uiAttrKeyList=null;
		}
		
		return true;
	}
	
	/**
	 * setLastOPWGID
	 *
	 * @param _i
	 *  @author David Bigelow
	 */
	protected void setLastOPWGID(int _i) {
		m_iLastOPWGID = _i;
	}

	/**
	 * getLastOPWGID
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public int getLastOPWGID() {
		return m_iLastOPWGID;
	}

	/**
	 * (non-Javadoc)
	 * linkAndRefresh
	 *
	 * @see COM.ibm.eannounce.objects.EANTableWrapper#linkAndRefresh(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.LinkActionItem)
	 */
	public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws
	SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException,
	WorkflowException, RemoteException {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * isParentAttribute
	 *
	 * @see COM.ibm.eannounce.objects.EANAddressable#isParentAttribute(java.lang.String)
	 */
	public boolean isParentAttribute(String _str) {

		EntityGroup eg = getEntityGroup();
		StringTokenizer st = new StringTokenizer(_str, ":");
		String strEntityType = st.nextToken();

		return eg.isParentAttribute(strEntityType);
	}

	/**
	 * (non-Javadoc)
	 * isChildAttribute
	 *
	 * @see COM.ibm.eannounce.objects.EANAddressable#isChildAttribute(java.lang.String)
	 */
	public boolean isChildAttribute(String _str) {

		EntityGroup eg = getEntityGroup();
		StringTokenizer st = new StringTokenizer(_str, ":");
		String strEntityType = st.nextToken();

		return eg.isChildAttribute(strEntityType);
	}

	/**
	 * is Entity Group Editable
	 * VEEdit_Iteration2
	 *
	 * @param s
	 * @return boolean editable
	 * @author Tony
	 */
	public boolean isEntityGroupEditable(String _s) {
//		think about this
		if (_s == null) {
			return isEntityGroupEditable();
		}
		EntityGroup eg = getEntityGroup();
		if (eg != null) {
			EntityList list = eg.getEntityList();
			if (list != null) {
				EANActionItem ean = list.getParentActionItem();
				if (ean instanceof ExtractActionItem) {
					return ( (ExtractActionItem) ean).isEditable(_s);
				}
			}
		}
		return false;
	}

	/**
	 * is Entity Group Editable
	 * VEEdit_Iteration2
	 *
	 * @return boolean editable
	 * @author Tony
	 */
	public boolean isEntityGroupEditable() {
		EntityGroup eg = getEntityGroup();
		if (eg != null) {
			if (isVEEdit()) { // this must be here because the entity itself may not be a VEEditItem
				EntityList eList = eg.getEntityList();
				EANActionItem ean = eList.getParentActionItem();
				if (ean instanceof ExtractActionItem) {
					ExtractActionItem eai = (ExtractActionItem) ean;
					String sType = eg.getEntityType();
					if (!eai.isDisplayable(sType) && eg.isRelator()) {
						sType = eai.getDisplayableEntity(eg.getEntity1Type(), sType, eg.getEntity2Type()); //VEEdit Bui
					}

					return (eai.isEditable(sType));
				}
			}
		}
		return true;
	}

	/**
	 * is Entity Group Creatable
	 * VEEdit_Iteration2
	 *
	 * @return boolean editable
	 * @author Tony
	 */
	protected boolean isEntityGroupCreatable() {
		EntityGroup eg = getEntityGroup();
		if (eg != null) {
			if (isVEEdit()) {
				EntityList eList = eg.getEntityList();
				EANActionItem ean = eList.getParentActionItem();
				if (ean != null) {
					return ( (ExtractActionItem) ean).isCreatable(eg.getEntityType());
				}
			}
		}
		return true;
	}

	/**
	 * is Entity Group Duplicatable
	 * VEEdit_Iteration2
	 *
	 * @return boolean editable
	 * @author Tony
	 */
	protected boolean isEntityGroupDuplicatable() {
		EntityGroup eg = getEntityGroup();
		if (eg != null) {
			if (isVEEdit()) {
				EntityList eList = eg.getEntityList();
				EANActionItem ean = eList.getParentActionItem();
				if (ean != null) {
					return ( (ExtractActionItem) ean).isDuplicatable(eg.getEntityType());
				}
			}
		}
		return true;
	}

	public boolean isVEEdit() {
		EntityGroup eg = getEntityGroup();
		if (eg != null) {
			EntityList eList = eg.getEntityList();
			if (eList != null) {
				return eList.isVEEdit();
			}
		}
		return false;
	}

	/*
 CR093005678
 CR0930055856
	 */
	 protected EntityItemException validateEntityItem(Database _db, RemoteDatabaseInterface _rdi) {
		 if (m_stk != null && !m_stk.empty()) {
			 EANAttribute[] att = (EANAttribute[]) m_stk.toArray(new EANAttribute[m_stk.size()]);
			 if (att != null) {
				 int ii = att.length;
				 for (int i=0;i<ii;++i) {
					 EANMetaAttribute meta = att[i].getMetaAttribute();
					 if (meta != null && meta.hasValidationRule()) {
						 String sException = meta.checkValid(this);
						 if (sException != null) {
							 EntityItemException eie = new EntityItemException();
							 eie.add(att[i],getErrorMessage(_db,_rdi,getProfile(),sException));
							 return eie;
						 }
					 }
				 }
			 }
		 }
		 return null;
	 }

	 private String getErrorMessage(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
		 // this is default for if we cannot find one in the db.
		 String strMsg = "Missing Required Dependent data (" + _strKey + ")";
		 try {
			 ReturnDataResultSet rdrs = null;
			 if (_rdi!= null) {
				 ReturnDataResultSetGroup rdrsg = _rdi.remoteGBL7567(_prof.getEnterprise(),_strKey,_prof.getReadLanguage().getNLSID());
				 rdrs = rdrsg.getResultSet(0);
			 } else {
				 ResultSet rs = _db.callGBL7567(new ReturnStatus(-1),_prof.getEnterprise(),_strKey,_prof.getReadLanguage().getNLSID());
				 rdrs = new ReturnDataResultSet(rs);
				 if (rs != null) {
					 rs.close();
					 rs = null;
				 }
				 _db.commit();
				 _db.debug(D.EBUG_SPEW,getKey()+" GBL7567 row count:" + rdrs.getRowCount());
			 }
			 if(rdrs != null && rdrs.getRowCount() > 0) {
				 strMsg = rdrs.getColumn(0,0);
				 int iNLSID = rdrs.getColumnInt(0,1);
				 if (_db != null) {
					 _db.debug(D.EBUG_SPEW,getKey()+" GBL7567 answer:" + strMsg + ":" + iNLSID);
				 }
			 }
		 } catch (RemoteException _re) {
			 _re.printStackTrace();
		 } catch (MiddlewareException _me) {
			 _me.printStackTrace();
		 } catch (SQLException _sql) {
			 _sql.printStackTrace();
		 }
		 return strMsg;
	 }
	 /*
 cr0428066810
	  */
	 private int getDisplayLimit() {
		 EntityGroup eg = getEntityGroup();
		 if (eg != null) {
			 EntityList el = eg.getEntityList();
			 if (el != null) {
				 EANActionItem ean = el.getParentActionItem();
				 if (ean instanceof MatrixActionItem) {
					 return ((MatrixActionItem)ean).getDisplayLimit();
				 } else if (ean instanceof NavActionItem) {
					 return ((NavActionItem)ean).getDisplayLimit();
				 }
			 }
		 }
		 return -1;
	 }

	 private boolean display(int _i) {
		 return _i >= 0;
	 }
}
