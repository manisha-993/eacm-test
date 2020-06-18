//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANActionItem.java,v $
// Revision 1.211  2013/05/01 18:34:09  wendy
// perf updates for large amt of data
//
// Revision 1.210  2010/08/24 20:29:25  wendy
// dont deref shared objects
//
// Revision 1.209  2010/05/26 16:08:02  wendy
// expose getRootEntityType()
//
// Revision 1.208  2009/05/11 15:44:15  wendy
// Support dereference for memory release
//
// Revision 1.207  2009/05/11 13:48:36  wendy
// Support turning off domain check for all actions
//
// Revision 1.206  2009/04/01 14:54:27  wendy
// Add query action to mdui
//
// Revision 1.205  2009/03/12 14:59:14  wendy
// Add methods for metaui access
//
// Revision 1.204  2008/08/01 12:28:19  wendy
// Change action type to View
//
// Revision 1.203  2008/07/31 18:59:06  wendy
// CQ00006067-WI : LA CTO - Added support for QueryAction
//
// Revision 1.202  2008/07/24 16:12:30  wendy
// Check for null before rs.close()
//
// Revision 1.201  2007/08/03 11:25:44  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.200  2006/03/30 21:32:44  gregg
// putting back "real" classes after bootstrap for sgcat.jar
//
// Revision 1.198  2006/02/20 21:39:45  joan
// clean up System.out.println
//
// Revision 1.197  2005/09/02 15:32:58  tony
// VEEdit functionality addition for CR 0815056514
//
// Revision 1.196  2005/08/22 19:21:49  tony
// Added getTargetType Logic to improve keying of the
// records
//
// Revision 1.195  2005/08/17 17:56:41  tony
// VEEdit
//
// Revision 1.194  2005/08/16 18:11:13  tony
// updated catalog viewer logic and adjusted pull
// criteria
//
// Revision 1.193  2005/08/16 16:52:58  tony
// CatalogViewer
//
// Revision 1.192  2005/08/11 17:30:13  tony
// fixed compile issues
//
// Revision 1.191  2005/08/11 17:21:27  tony
// added EntityItem array to catalog function
//
// Revision 1.190  2005/08/11 16:21:38  tony
// fixed spelling
//
// Revision 1.189  2005/08/11 15:55:40  tony
// remote execution of addtional processes.
//
// Revision 1.188  2005/08/10 20:45:28  tony
// added testing statements.
//
// Revision 1.187  2005/08/10 18:06:19  tony
// updated logic
//
// Revision 1.186  2005/08/10 17:18:15  tony
// mod
//
// Revision 1.185  2005/08/10 17:07:18  tony
// add int capability.
//
// Revision 1.184  2005/08/10 16:55:01  tony
// updated logic.
//
// Revision 1.183  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.182  2005/08/08 19:02:56  tony
// modified functionality.
//
// Revision 1.181  2005/08/04 15:51:39  tony
// catalog mods
//
// Revision 1.180  2005/08/03 19:51:24  tony
// provide customizable sp support
//
// Revision 1.179  2005/08/03 17:09:44  tony
// added datasource logic for catalog mod
//
// Revision 1.178  2005/03/11 22:42:53  dave
// removing some auto genned stuff
//
// Revision 1.177  2005/02/25 16:39:28  tony
// added javadoc comment
//
// Revision 1.176  2005/02/25 16:35:16  tony
// made method public
//
// Revision 1.175  2005/02/18 00:15:59  gregg
// setAllNLS in copy constructor
//
// Revision 1.174  2005/02/16 21:05:57  gregg
// isAllNLS
//
// Revision 1.173  2005/02/15 00:23:12  dave
// more CR work CR1124045246
//
// Revision 1.172  2005/02/15 00:02:20  dave
// CR1124045246 - making a place for any
// clear Change Group function to be triggered
//
// Revision 1.171  2005/02/14 17:18:34  dave
// more jtest fixing
//
// Revision 1.170  2005/01/18 21:33:10  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.169  2004/11/03 23:35:32  gregg
// setMetaColumnOrderControl(_ai.isMetaColumnOrderControl()); in copy constructor
//
// Revision 1.168  2004/11/03 23:25:00  gregg
// compile fix
//
// Revision 1.167  2004/11/03 23:12:44  gregg
// MetaColumnOrderGroup for NavActionItems
// MetaLinkAttr switch on ActionItem ColumnOrderControl
// Apply ColumnOrderControl for EntityItems
// Cleanup Debugs
//
// Revision 1.166  2004/11/03 22:14:05  gregg
// method public
//
// Revision 1.165  2004/11/03 19:18:54  gregg
// hasMetaColumnOrderGroup check on EANActionItem
//
// Revision 1.164  2004/11/03 19:02:15  gregg
// store away MetaColumnOrderGroup
//
// Revision 1.163  2004/10/21 16:49:52  dave
// trying to share compartor
//
// Revision 1.162  2004/07/30 22:46:46  joan
// work on use Root EI
//
// Revision 1.161  2004/07/30 16:05:39  joan
// work on use Root EI
//
// Revision 1.160  2004/07/15 16:58:18  joan
// add SingleInput to accept only one input
//
// Revision 1.159  2004/06/18 17:24:06  joan
// work on edit relator
//
// Revision 1.158  2004/05/24 19:05:34  joan
// fix bugs
//
// Revision 1.157  2004/05/24 18:00:43  joan
// fix bug
//
// Revision 1.156  2004/05/21 22:37:11  joan
// more adjustment
//
// Revision 1.155  2004/05/21 20:30:23  joan
// more adjustments
//
// Revision 1.154  2004/05/21 17:30:22  joan
// put more work
//
// Revision 1.153  2004/05/21 15:45:07  joan
// more adjustment
//
// Revision 1.152  2004/05/20 22:48:29  joan
// fix compile
//
// Revision 1.151  2004/05/20 22:37:40  joan
// fix compile
//
// Revision 1.150  2004/05/20 22:20:08  joan
// fix compile
//
// Revision 1.149  2004/05/20 22:09:56  joan
// work on chain action
//
// Revision 1.148  2004/01/14 18:41:23  dave
// more squeezing of the short description
//
// Revision 1.147  2004/01/13 22:49:11  gregg
// moving EANActionItem.EANActionItemEntitiesList class into it's own class because it's rarely used and taking up space
//
// Revision 1.146  2004/01/09 22:04:04  gregg
// Adding in ActionItemHistory
//
// Revision 1.145  2004/01/09 18:20:37  gregg
// set/getDisplayName()
//
// Revision 1.144  2004/01/08 22:19:34  gregg
// null ptr fix
//
// Revision 1.143  2004/01/08 21:48:10  gregg
// setActionHistory in copy constructors
//
// Revision 1.142  2004/01/08 21:30:30  gregg
// set/getActionHistory (CR#1210035324)
//
// Revision 1.141  2004/01/08 19:07:52  gregg
// made EANActionItemEntitiesList transient - we should never have to serialize this.
//
// Revision 1.140  2003/11/06 23:27:15  gregg
// in EANActionItemEntitiesList: start out w/ list sorted by LinkValue (i.e. Level)
//
// Revision 1.139  2003/11/06 19:22:01  gregg
// Made inner classes (EANActionItemEntitiesList,EANActionItemEntity) public. were protected.
// - for access from w/in MetaReport Jsp's.
//
// Revision 1.138  2003/10/30 00:43:30  dave
// fixing all the profile references
//
// Revision 1.137  2003/09/15 18:34:06  dave
// misc fixes and usenglish only flag for flag support
//
// Revision 1.136  2003/09/11 20:54:51  gregg
// setFilterGroup() in copy Constructors
//
// Revision 1.135  2003/09/11 18:22:17  gregg
// store FilterGroup in EANActionItem.
//
// Revision 1.134  2003/08/22 19:28:54  dave
// rmi trickery II
//
// Revision 1.133  2003/08/22 18:32:56  dave
// Lets try remote procedure calls in contructors
//
// Revision 1.132  2003/08/18 21:05:07  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.131  2003/05/09 22:33:03  dave
// more cleanup and trace for controlling ui/non ui needs
//
// Revision 1.130  2003/03/27 23:07:20  dave
// adding some timely commits to free up result sets
//
// Revision 1.129  2003/03/10 17:17:59  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.128  2003/02/15 00:18:21  gregg
// removed interface HtmlDisplayable
//
// Revision 1.127  2003/01/14 00:23:03  gregg
// removed entityclass param in gbl7506 -- we want to ensure entitytype is unique across ALL entityclasses
//
// Revision 1.126  2003/01/14 00:09:17  dave
// adding schema filter on ODS init pull.
// Also fixing display statements
//
// Revision 1.125  2003/01/13 22:28:19  gregg
// extra EntityClass param passed into GBL7506
//
// Revision 1.124  2003/01/13 19:32:21  gregg
// InsertPdhMeta method + throw MetaUpdateException on duplicate unique key
//
// Revision 1.123  2002/12/13 18:09:47  gregg
// in updatePdhMeta method -> insert MetaEntity record for Category if its a brand new one.
//
// Revision 1.122  2002/12/13 00:07:35  gregg
// remove all uses of MetaActionItemList.putCache()
//
// Revision 1.121  2002/11/19 23:26:55  joan
// fix hasLock method
//
// Revision 1.120  2002/11/19 18:27:41  joan
// adjust lock, unlock
//
// Revision 1.119  2002/11/19 00:06:25  joan
// adjust isLocked method
//
// Revision 1.118  2002/11/18 21:20:25  gregg
// ignore case logic on performFilter
//
// Revision 1.117  2002/11/18 18:28:10  gregg
// allow use of wildcards on performFilter()
//
// Revision 1.116  2002/10/31 21:43:53  dave
// fix to memory leak
//
// Revision 1.115  2002/10/31 21:35:18  dave
// trapped the memory leak
//
// Revision 1.114  2002/10/17 23:11:09  gregg
// in updatePdhMeta: getCategory().update/expirePdhMetaDescriptions()
//
// Revision 1.113  2002/10/17 22:48:08  gregg
// updatePdhMeta for Action/Category record
//
// Revision 1.112  2002/10/17 20:58:49  gregg
// made setCategory method public now
//
// Revision 1.111  2002/10/17 18:27:41  gregg
// made getCategory protected (was private)
//
// Revision 1.110  2002/10/17 17:24:57  gregg
// setCategory made protected (was private)
//
// Revision 1.109  2002/10/11 00:18:43  dave
// More trace on absent parent
//
// Revision 1.108  2002/10/09 19:36:22  gregg
// moved previous add up into MetaDescription row class
//
// Revision 1.107  2002/10/09 19:35:05  gregg
// in updatePdhMeta: if add new item && nlsid != 1 -> add english descrip also
//
// Revision 1.106  2002/10/09 17:00:53  dave
// Dyna Search II fixes
//
// Revision 1.105  2002/10/07 17:41:37  joan
// add getLockGroup method
//
// Revision 1.104  2002/09/27 18:54:19  gregg
// more of the same (forgot to save all changes last time)
//
// Revision 1.103  2002/09/26 23:39:33  joan
// remove System.out
//
// Revision 1.102  2002/09/23 18:26:00  gregg
// setParent reference to MetaActionItemList in updatePdhMeta for add/updates b4 update cache
//
// Revision 1.101  2002/09/23 18:06:33  gregg
// refresh cache for MetaActionItemList parent on updatePdhMeta
//
// Revision 1.100  2002/09/19 16:24:07  gregg
// removed some debug statements
//
// Revision 1.99  2002/09/18 21:55:32  gregg
// more trace stmts
//
// Revision 1.98  2002/09/18 20:54:48  gregg
// some debugging for EANActionItemEntities.duplicateForNewActionItem.
//
// Revision 1.97  2002/09/18 20:12:25  gregg
// more dumps for inner classes
//
// Revision 1.96  2002/09/18 18:33:17  gregg
// duplicateForNewActionItem, setActionEntitiesList methods
//
// Revision 1.95  2002/09/06 17:38:48  gregg
// SortFilterInfo now uses String sort/filter key constants (were ints)
//
// Revision 1.94  2002/09/05 20:07:57  gregg
// more updatePdhMeta for associatedEntityType
//
// Revision 1.93  2002/09/05 19:35:02  gregg
// updates for AssociatedEntityType
//
// Revision 1.92  2002/09/05 17:51:04  gregg
// setAssociatedEntityType, getAssociatedEntityType methods
//
// Revision 1.91  2002/09/03 23:07:25  gregg
// getNewActionItemEntity method
//
// Revision 1.90  2002/09/03 20:59:40  gregg
// getNewActionEntitiesList
//
// Revision 1.89  2002/09/03 20:34:46  gregg
// EANActionItemEntitiesList, EANActionItemEntity constructors are now public
//
// Revision 1.88  2002/09/03 19:53:35  gregg
// putObject, removeObject methods
//
// Revision 1.87  2002/09/03 16:57:46  gregg
// EANActionItemEntitiesList..
//
// Revision 1.86  2002/08/31 00:19:27  gregg
// typo in EANActionItemEntitiesList
//
// Revision 1.85  2002/08/30 18:20:05  gregg
// more EANActionItemEntity
//
// Revision 1.84  2002/08/30 17:24:09  gregg
// more EANActionItemEntity
//
// Revision 1.83  2002/08/30 00:32:17  gregg
// s'more debug stmts
//
// Revision 1.82  2002/08/29 23:32:34  gregg
// made s'more methods public in inner classes
//
// Revision 1.81  2002/08/29 22:34:25  gregg
// updatePdhMetaCommon...
//
// Revision 1.80  2002/08/29 22:18:31  gregg
// more debugging
//
// Revision 1.79  2002/08/29 21:58:38  gregg
// some debugging
//
// Revision 1.78  2002/08/29 20:35:53  gregg
// made inner classes public
//
// Revision 1.77  2002/08/29 18:26:08  gregg
// made getActionEntitiesList public
//
// Revision 1.76  2002/08/29 17:41:04  gregg
// updatePdhMeta for EANActionItemEntitiesList
//
// Revision 1.75  2002/08/28 23:44:14  gregg
// EANActionItemEntitiesList, EANActionItemEntity inner classes
//
// Revision 1.74  2002/08/28 21:57:11  gregg
// some db.freeStatements in updatePdhMeta
//
// Revision 1.73  2002/08/28 21:21:25  gregg
// some cleanup in updatePdhMeta method
//
// Revision 1.72  2002/08/28 16:07:27  joan
// debug getID
//
// Revision 1.71  2002/08/27 23:59:35  joan
// debug getID
//
// Revision 1.70  2002/08/26 17:51:03  gregg
// first pass at guts of updatePdhMeta(db) method
//
// Revision 1.69  2002/08/24 00:19:37  joan
// add resetID
//
// Revision 1.68  2002/08/23 23:23:58  gregg
// first pass at guts of updatePdhMeta
//
// Revision 1.67  2002/08/23 21:59:54  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.66  2002/08/23 21:29:45  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.65  2002/08/23 20:01:31  gregg
// made setActionClass() method protected
//
// Revision 1.64  2002/08/13 23:34:29  gregg
// updatePdhMeta() method stub
//
// Revision 1.63  2002/08/08 23:37:19  gregg
// changed getFiltered() method to isFiltered()
//
// Revision 1.62  2002/08/08 22:21:14  gregg
// syntax
//
// Revision 1.61  2002/08/08 22:17:04  gregg
// getCompareField(), setCompareField(String) methods required by EANComparable inteface
//
// Revision 1.60  2002/08/08 21:40:34  gregg
// some rearrangin in toCompareString() method
//
// Revision 1.59  2002/08/08 21:38:11  gregg
// defined toCompareString() for EANComparable interface
//
// Revision 1.58  2002/08/08 21:30:06  gregg
// implement EANComparable
//
// Revision 1.57  2002/08/08 20:51:48  joan
// fix setParentEntityItem
//
// Revision 1.56  2002/08/08 20:20:14  gregg
// changed sortTypes to Strings/resolved some merge conflicts
//
// Revision 1.55  2002/08/08 20:07:25  joan
// fix setParentEntityItem
//
// Revision 1.54  2002/08/08 19:19:25  gregg
// setFiltered(), getFiltered() methods
//
// Revision 1.53  2002/08/08 00:16:32  gregg
// setSortType()
//
// Revision 1.52  2002/07/29 23:09:45  joan
// add SBR class
//
// Revision 1.51  2002/05/20 21:31:11  joan
// add setParentEntityItem
//
// Revision 1.50  2002/05/20 17:27:04  gregg
// moved HtmlDispalyable interface up into EANMetaFoundation
//
// Revision 1.49  2002/05/17 18:48:29  gregg
// getDefaultHtml
//
// Revision 1.48  2002/05/17 18:45:35  gregg
// added getHtmlDisplayObject, setHtmlDisplayObject methods
//
// Revision 1.47  2002/05/17 18:26:34  joan
// work on report
//
// Revision 1.46  2002/05/17 17:27:57  joan
// working on ReportActionItem
//
// Revision 1.45  2002/05/17 00:12:09  gregg
// dumpHtml
//
// Revision 1.44  2002/05/17 00:10:42  gregg
// implement HtmlDisplayable
//
// Revision 1.43  2002/05/14 17:47:06  joan
// working on LockActionItem
//
// Revision 1.42  2002/05/13 21:01:48  gregg
// including Watchdog actionClass
//
// Revision 1.41  2002/05/13 20:40:33  joan
// add resetLockGroup method
//
// Revision 1.40  2002/05/10 22:04:55  joan
// add hasLock method
//
// Revision 1.39  2002/05/10 20:45:54  joan
// fixing lock
//
// Revision 1.38  2002/04/25 20:11:34  dave
// fixes for null pointer
//
// Revision 1.37  2002/04/25 19:17:57  dave
// pulling the proper variables in 7003
//
// Revision 1.36  2002/04/25 18:01:21  dave
// fix
//
// Revision 1.35  2002/04/25 17:54:50  dave
// Fixing Syntax
//
// Revision 1.34  2002/04/25 17:40:10  dave
// syntax
//
// Revision 1.33  2002/04/25 17:27:10  dave
// Attempt to adding category to Action items.. (as a tag)
//
// Revision 1.32  2002/04/23 22:35:17  dave
// removed isFormAction on Abract Action Item object
//
// Revision 1.31  2002/04/22 22:18:23  joan
// working on unlock
//
// Revision 1.30  2002/04/22 18:26:00  gregg
// added Extract actionclass
//
// Revision 1.29  2002/04/22 18:08:38  joan
// add unlock method
//
// Revision 1.28  2002/04/19 22:34:05  joan
// change isLocked interface to include profile as parameter
//
// Revision 1.27  2002/04/19 18:19:55  joan
// fixing errors
//
// Revision 1.26  2002/04/19 17:17:02  joan
// change isLocked  interface
//
// Revision 1.25  2002/04/12 22:44:17  joan
// throws exception
//
// Revision 1.24  2002/04/12 16:42:04  dave
// added isLocked to the tableDef
//
// Revision 1.23  2002/03/27 22:34:20  dave
// Row Selectable Table row Add logic. First attempt
//
// Revision 1.22  2002/03/21 18:38:56  dave
// added getHelp to the EANTableModel
//
// Revision 1.21  2002/03/21 00:22:56  dave
// adding rollback logic to the rowSelectable table
//
// Revision 1.20  2002/03/20 18:33:55  dave
// expanding the get for the EANAddressable to
// include a verbose boolean to dictate what gets sent back
//
// Revision 1.19  2002/03/11 21:22:39  dave
// syntax for first stage of edit
//
// Revision 1.18  2002/03/07 19:01:58  dave
// Fix to ActionItem object
//
// Revision 1.17  2002/03/04 20:39:26  dave
// debug cleanup
//
// Revision 1.16  2002/03/01 19:00:52  dave
// merged conflicts
//
// Revision 1.15  2002/02/26 21:43:59  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.14  2002/02/20 18:07:06  dave
// adding new Link Action Item
//
// Revision 1.13  2002/02/18 22:55:04  dave
// fixed null pointer
//
// Revision 1.12  2002/02/18 17:39:44  dave
// more fixes
//
// Revision 1.11  2002/02/18 17:25:22  dave
// more function add for 1.1
//
// Revision 1.10  2002/02/15 18:06:52  dave
// expanded EAN Table structures
//
// Revision 1.9  2002/02/14 01:21:17  dave
// created a binder for action groups so relator/entity can be shown
//
// Revision 1.8  2002/02/12 23:35:36  dave
// added purpose to the NavActionItem
//
// Revision 1.7  2002/02/12 21:29:19  dave
// for syntax
//
// Revision 1.6  2002/02/12 21:01:58  dave
// added toString methods for diplay help
//
// Revision 1.5  2002/02/12 19:33:35  dave
// fixed the class setting when we instantiate from existing action item
//
// Revision 1.4  2002/02/12 19:18:38  dave
// extra debugging to isolate Class problem for action item
//
// Revision 1.3  2002/02/09 21:50:41  dave
// more syntax
//
// Revision 1.2  2002/02/09 21:39:34  dave
// syntax fixes
//
// Revision 1.1  2002/02/09 20:48:25  dave
// re-arrannging the ActionItem to abstract a common base
//
// Revision 1.20  2002/02/06 16:18:28  dave
// more syntax
//
// Revision 1.19  2002/02/06 15:23:06  dave
// more fixes to sytax
//
// Revision 1.18  2002/02/06 00:56:12  dave
// more base changes
//
// Revision 1.17  2002/02/02 21:22:55  dave
// more clean up
//
// Revision 1.16  2002/02/02 21:11:06  dave
// fixing more import statements
//
// Revision 1.15  2002/02/02 20:56:53  dave
// tightening up code
//
// Revision 1.14  2002/01/31 22:57:18  dave
// more Foundation Cleanup
//
// Revision 1.13  2002/01/18 23:33:43  dave
// minor syntax
//
// Revision 1.12  2002/01/18 23:24:28  dave
// fixes to ActionItem
//
// Revision 1.11  2002/01/18 22:57:16  dave
// sp change to swap direction and d,u
//
// Revision 1.10  2002/01/17 18:33:53  dave
// misc adjustments to help cloning and copying of structures
// in e-announce
//
// Revision 1.9  2002/01/17 00:16:18  dave
// DB2 rs read fix
//
// Revision 1.8  2002/01/16 23:50:15  dave
// interject first NavigateObject constructor test
//
// Revision 1.7  2002/01/14 22:07:54  dave
// minor abract fixes
//
// Revision 1.6  2002/01/14 21:36:58  dave
// removed astract for now.. and fixed constructor info
//
// Revision 1.5  2002/01/12 00:47:02  dave
// changed set to put for Descriptions
//
// Revision 1.4  2002/01/12 00:37:28  dave
// syntax fixes
//
// Revision 1.3  2002/01/12 00:06:33  dave
// syntax fixes
//
// Revision 1.2  2002/01/11 23:57:39  dave
// misc syntax error fixes
//
// Revision 1.1  2002/01/11 23:45:00  dave
// generated abstract ActionItem object for workflow start
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSetGroup;

import java.rmi.RemoteException;
import COM.ibm.eannounce.catalog.CatalogViewer;
import java.util.StringTokenizer;
// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate
/**
 * EANActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class EANActionItem extends EANMetaFoundation implements EANAddressable, EANComparable {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * FIELD
     */
    protected static String[] c_saSortTypes = { "Long Description", "ShortDescription", "Action Item Key", "Action Item Class" };

    /**
     * FIELD
     */
    public static final String SORT_BY_LONG_DESCRIPTION = c_saSortTypes[0];
    /**
     * FIELD
     */
    public static final String SORT_BY_SHORT_DESCRIPTION = c_saSortTypes[1];
    /**
     * FIELD
     */
    public static final String SORT_BY_ACTION_NAME = c_saSortTypes[2];
    /**
     * FIELD
     */
    public static final String SORT_BY_ACTION_CLASS = c_saSortTypes[3];
    /**
     * FIELD
     */
    public static String DESCRIPTION = "0";
    private String m_strActionClass = null;
    private boolean m_bNavigate = false;
    private boolean m_bEdit = false;
    private boolean m_bMatrix = false;
    private boolean m_bWhereUsed = false;
    private boolean m_bQuery = false;
    private boolean m_bCreate = false;
    private boolean m_bCart = false;
    private boolean m_bSearch = false;
    private boolean m_bLink = false;
    private boolean m_bExtract = false;
    private boolean m_bWatchdog = false;
    private boolean m_bLock = false;
    private boolean m_bDelete = false;
    private boolean m_bSBR = false;
    private boolean m_bReport = false;
    private boolean m_bWorkflow = false;
    private MetaTag m_mt = null;
    private String m_strSortType = SORT_BY_LONG_DESCRIPTION;
    private boolean m_bFiltered = false;
    /**
     * FIELD
     */
    protected transient EANActionItemEntitiesList m_actionEntitiesList = null;
    private String m_strAssociatedEntityType = null;
    private String m_strTargetType = null;
    private FilterGroup m_filterGroup = null;
    private String m_strDisplayName = null;
    private ActionItemHistory m_actionHistory = null;
    private MetaColumnOrderGroup m_mcog = null;
    private boolean m_bMetaColumnOrderControl = false;

    private boolean m_bClearTargetChangeGroup = false;

    // Various Counters
    private int m_iID = -1;

    // metahelp
    private MetaHelpValue m_mHelpValue = null;

    //chain action item
    /**
     * FIELD
     */
    protected String m_strChainActionType = null;
    /**
     * FIELD
     */
    protected String m_strChainActionClass = null;
    /**
     * FIELD
     */
    protected String m_strRequireInput = null;
    private boolean m_bChainNavigate = false;
    private boolean m_bChainEdit = false;
    private boolean m_bChainMatrix = false;
    private boolean m_bChainWhereUsed = false;
    private boolean m_bChainCreate = false;
    private boolean m_bChainSearch = false;
    private boolean m_bChainLink = false;
    private boolean m_bChainExtract = false;
    private boolean m_bChainWatchdog = false;
    private boolean m_bChainLock = false;
    private boolean m_bChainDelete = false;
    private boolean m_bChainReport = false;
    private boolean m_bChainWorkflow = false;
    private boolean m_bVEEdit = false;				//VEEdit

    /**
     * FIELD
     */
    protected boolean m_bSingleInput = false;
    /**
     * FIELD
     */
    protected boolean m_bRootEI = false;
    protected boolean m_bDomainCheck = false; // default to not check users domain before executing action
    /** RQ0713072645
     * setDomainCheck- to turn it on use the following meta:
     * SG	Action/Attribute	LINKACT 	TYPE	DomainCheck	Y
     * setDomainCheck- to turn it off use the following meta:
     * SG	Action/Attribute	LINKACT 	TYPE	DomainCheck	N
     *
     * @param _b
     */
    protected void setDomainCheck(boolean _b) {
        m_bDomainCheck = _b;
    }

    /** RQ0713072645
     * mustCheckDomain
     *
     * @return boolean
     */
    public boolean mustCheckDomain() {
        return m_bDomainCheck;
    }
    /**
     * FIELD
     */
    protected String m_strRootEntityType = null;

    protected void dereference(){
    	super.dereference();
    	m_strRootEntityType = null;
        m_strActionClass = null;
        if (m_mt !=null){
        	//m_mt.dereference(); this is shared
        	m_mt = null;
        }
        if (m_mHelpValue!=null){
        	//m_mHelpValue.dereference(); this is shared
        	m_mHelpValue = null;
        }
        if (m_mcog!=null){
        	//m_mcog.dereference(); this is shared
        	m_mcog = null;
        }
        if (m_actionHistory!=null){
        	//m_actionHistory.dereference(); this is shared
        	m_actionHistory = null;
        }
        
        if (m_filterGroup!=null){
        	//m_filterGroup.dereference(); this is shared
        	m_filterGroup = null;
        }

        m_strChainActionType = null;
        m_strChainActionClass = null;
        m_strRequireInput = null;
        m_strDisplayName = null;
        m_strTargetType = null;
        m_strAssociatedEntityType = null;
        m_actionEntitiesList = null;
        m_strSortType = null;
    }
    /**
     * Some ActionItems (e.g. certain Edits/Creates) will need to pull all NLS's at the time of execution. Default single nls;
     */
	/*
	 * default datasource retrieve data from the pdh
	 */
	public static final int PDH_DATASOURCE = 0;
	/*
	 * catalog datasource get data from the catalog
	 */
	public static final int CATALOG_DATASOURCE = 1;
	/*
	 * ods datasource get data from the ods
	 * this is probably over kill but doesn't hurt to have
	 * for future use
	 */
	public static final int ODS_DATASOURCE = 2;

    private boolean m_bAllNLS = false;
	private int m_iDatasource = PDH_DATASOURCE;
	private String m_sSp = null;
	private String[] m_sSpParms = null;
    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * getPurpose
     *
     * @return
     *  @author David Bigelow
     */
    public abstract String getPurpose();

    /**
     * updatePdhMeta
     *
     * @param _db
     * @param _bExpire
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    protected abstract boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException;

    /*
    * Version info
    */
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: EANActionItem.java,v 1.211 2013/05/01 18:34:09 wendy Exp $";
    }

    /*
    * Instantiate a new ActionItem based upon a dereferenced version of the Existing One
    */
    /**
     * EANActionItem
     *
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EANActionItem(EANActionItem _ai) throws MiddlewareRequestException {
        super(null, _ai.getProfile(), _ai.getActionItemKey());
        copyDescription(_ai);
        setActionClass(_ai.getActionClass());
        setCategory(_ai.getCategory());
        if (_ai.hasFilterGroup()) {
            setFilterGroup(_ai.getFilterGroup());
        }
        setActionHistory(_ai.getActionHistory());
        setDisplayName(_ai.getDisplayName());

        setChainActionType(_ai.m_strChainActionType);
        setChainActionClass(_ai.m_strChainActionClass);
        setRequireInput(_ai.getRequireInput());

        setSingleInput(_ai.isSingleInput());
        setRootEI(_ai.useRootEI());
        setDomainCheck(_ai.mustCheckDomain()); // RQ0713072645
        setRootEntityType(_ai.getRootEntityType());

        setMetaColumnOrderGroup(_ai.getMetaColumnOrderGroup());
        setMetaColumnOrderControl(_ai.isMetaColumnOrderControl());

        setAllNLS(_ai.isAllNLS());
		setDataSource(_ai.getDataSourceAsString());							//catalog enhancement
		setAdditionalParms(_ai.getAdditionalParms());						//catalog enhancement
		setVEEdit(_ai.isVEEdit());											//VEEdit
		setTargetType(_ai.getTargetType());									//target
    }
   /**
     * EANActionItem
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EANActionItem(EANMetaFoundation _mf, EANActionItem _ai) throws MiddlewareRequestException {
        super(_mf, (_ai.getProfile() == null && _mf != null ? _mf.getProfile() : _ai.getProfile()), _ai.getActionItemKey());
        copyDescription(_ai);
        setActionClass(_ai.getActionClass());
        setCategory(_ai.getCategory());
        if (_ai.hasFilterGroup()) {
            setFilterGroup(_ai.getFilterGroup());
        }
        setActionHistory(_ai.getActionHistory());
        setDisplayName(_ai.getDisplayName());

        setChainActionType(_ai.m_strChainActionType);
        setChainActionClass(_ai.m_strChainActionClass);
        setRequireInput(_ai.getRequireInput());

        setSingleInput(_ai.isSingleInput());
        setRootEI(_ai.useRootEI());
        setRootEntityType(_ai.getRootEntityType());

        setMetaColumnOrderGroup(_ai.getMetaColumnOrderGroup());
        setMetaColumnOrderControl(_ai.isMetaColumnOrderControl());

        setAllNLS(_ai.isAllNLS());
        setDataSource(_ai.getDataSourceAsString());							//catalog enhancement
		setAdditionalParms(_ai.getAdditionalParms());						//catalog enhancement
		setVEEdit(_ai.isVEEdit());											//VEEdit
		setTargetType(_ai.getTargetType());									//target
		setDomainCheck(_ai.mustCheckDomain());
    }

    /*
    * Instantiate a new ActionItem with no basis
    */
    /**
     * EANActionItem
     *
     * @param _emf
     * @param _prof
     * @param _strActionItemKey
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EANActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws MiddlewareRequestException {
        super(_emf, _prof, _strActionItemKey);
        setActionClass(_strActionItemKey);
    }

    /**
     * This represents an Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public EANActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _prof, _strActionItemKey);

        D.ebug(D.EBUG_DETAIL, "\n*** New EANActionItem from Database for:" + _strActionItemKey + "\n");

        try {
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            Profile prof = getProfile();
            // Retrieve the Action Class and the Action Description.
            try {
                rs = _db.callGBL7003(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getReadLanguage().getNLSID(), prof.getValOn(), prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
                    rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {

                String strActClass = rdrs.getColumn(ii, 0);
                int iActNLSID = rdrs.getColumnInt(ii, 1);
                String strActShortDesc = rdrs.getColumn(ii, 2);
                String strActLongDesc = rdrs.getColumn(ii, 3);
                String strCatClass = rdrs.getColumn(ii, 4);
                int iCatNLSID = rdrs.getColumnInt(ii, 5);
                String strCatShortDesc = rdrs.getColumn(ii, 6);
                String strCatLongDesc = rdrs.getColumn(ii, 7);
                _db.debug(D.EBUG_SPEW, "gbl7003 answer is:" + strActClass + ":" + iActNLSID + ":" + strActShortDesc + ":" + strActLongDesc + ":" + strCatClass + ":" + iCatNLSID + ":" + strCatShortDesc + ":" + strCatLongDesc + ":");
                setActionClass(strActClass);
                //putShortDescription(iActNLSID,strActShortDesc);
                putLongDescription(iActNLSID, strActLongDesc);

                // Set up the category it belongs too..

                if (m_mt == null) {
                    m_mt = new MetaTag(this, null, strCatClass);
                }
                m_mt.putShortDescription(iCatNLSID, strCatShortDesc);
                m_mt.putLongDescription(iCatNLSID, strCatLongDesc);
            }

            // set up the meta help  DWB not needed yet
            // setMetaHelpValue(new MetaHelpValue(this, _db, _prof));

            // set up for chaining action item
            rs = _db.callGBL7008(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
            rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {

                String strChainActionClass = rdrs.getColumn(ii, 0).trim();
                String strChainActionType = rdrs.getColumn(ii, 1).trim();
                String strRequireInput = rdrs.getColumn(ii, 2).trim();

                _db.debug(D.EBUG_SPEW, "gbl7008 answer is:" + strChainActionClass + ":" + strChainActionType + ":" + strRequireInput);
                setChainActionType(strChainActionType);
                setChainActionClass(strChainActionClass);
                setRequireInput(strRequireInput);
            }

        } catch (Exception x) {
            System.out.println("ActionItem exception: " + x);
            x.printStackTrace();
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * This represents an Action Item.  It can only be constructed via a remotedatabase connection, a Profile, and an Action Item Key*
     *
     * @param _emf
     * @param _ro
     * @param _prof
     * @param _strActionItemKey
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public EANActionItem(EANMetaFoundation _emf, RemoteDatabaseInterface _ro, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _prof, _strActionItemKey);
        try {

            ReturnDataResultSet rdrs = null;
            Profile prof = getProfile();

            // Retrieve the Action Class and the Action Description.
            ReturnDataResultSetGroup rdrsg = _ro.remoteGBL7003(prof.getEnterprise(), _strActionItemKey, prof.getReadLanguage().getNLSID(), prof.getValOn(), prof.getEffOn());
            rdrs = rdrsg.getResultSet(0);

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {

                String strActClass = rdrs.getColumn(ii, 0);
                int iActNLSID = rdrs.getColumnInt(ii, 1);
                String strActShortDesc = rdrs.getColumn(ii, 2);
                String strActLongDesc = rdrs.getColumn(ii, 3);
                String strCatClass = rdrs.getColumn(ii, 4);
                int iCatNLSID = rdrs.getColumnInt(ii, 5);
                String strCatShortDesc = rdrs.getColumn(ii, 6);
                String strCatLongDesc = rdrs.getColumn(ii, 7);
                D.ebug(D.EBUG_SPEW, "gbl7003 answer is:" + strActClass + ":" + iActNLSID + ":" + strActShortDesc + ":" + strActLongDesc + ":" + strCatClass + ":" + iCatNLSID + ":" + strCatShortDesc + ":" + strCatLongDesc + ":");
                setActionClass(strActClass);
                //putShortDescription(iActNLSID,strActShortDesc);
                putLongDescription(iActNLSID, strActLongDesc);

                // Set up the category it belongs too..

                if (m_mt == null) {
                    m_mt = new MetaTag(this, null, strCatClass);
                }
                m_mt.putShortDescription(iCatNLSID, strCatShortDesc);
                m_mt.putLongDescription(iCatNLSID, strCatLongDesc);
            }

            // set up the meta help  DWB not needed yet
            // setMetaHelpValue(new MetaHelpValue(this, _db, _prof));

            // set up for chaining action item

            rdrsg = _ro.remoteGBL7008(prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
            rdrs = rdrsg.getResultSet(0);

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strChainActionClass = rdrs.getColumn(ii, 0).trim();
                String strChainActionType = rdrs.getColumn(ii, 1).trim();
                String strRequireInput = rdrs.getColumn(ii, 2).trim();

                D.ebug(D.EBUG_SPEW, "gbl7008 answer is:" + strChainActionClass + ":" + strChainActionType + ":" + strRequireInput);
                setChainActionType(strChainActionType);
                setChainActionClass(strChainActionClass);
                setRequireInput(strRequireInput);
            }

        } catch (Exception x) {
            System.out.println("ActionItem exception: " + x);
            x.printStackTrace();
        } finally {
            // Nothing
        }
    }

    /**
     * keep track of which EntityType this ActionItem should be associated with
     *
     * @param _s
     */
    public void setAssociatedEntityType(String _s) {
        m_strAssociatedEntityType = _s;
    }

    /**
     * getAssociatedEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getAssociatedEntityType() {
        return m_strAssociatedEntityType;
    }

    /**
     * set whether or not this ActionItem will be displayed in an EANSortableList (true means not displayed)
     *
     * @param _b
     */
    public void setFiltered(boolean _b) {
        m_bFiltered = _b;
    }

    /**
     * get whether or not this ActionItem will be displayed in an EANSortableList (true means not displayed)
     *
     * @return boolean
     */
    public boolean isFiltered() {
        return m_bFiltered;
    }

    /**
     * setCategory
     *
     * @param _mt
     *  @author David Bigelow
     */
    public void setCategory(MetaTag _mt) {
        m_mt = _mt;
        if (m_mt != null) {
            m_mt.setParent(this);
        }
    }

    /**
     * getCategory
     *
     * @return
     *  @author David Bigelow
     */
    protected MetaTag getCategory() {
        return m_mt;
    }

    /**
     * getCategoryCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getCategoryCode() {
        if (m_mt != null) {
            return m_mt.getKey();
        } else {
            return "GENERAL";
        }
    }

    /**
     * getCategoryLongDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getCategoryLongDescription() {
        if (m_mt != null) {
            return m_mt.getLongDescription();
        } else {
            return "General";
        }
    }

    /**
     * getCategoryShortDescription
     *
     * @return
     *  @author David Bigelow
     */
    public String getCategoryShortDescription() {
        if (m_mt != null) {
            return m_mt.getShortDescription();
        } else {
            return "General";
        }
    }

    /**
     * getActionItemKey
     *
     * @return
     *  @author David Bigelow
     */
    public String getActionItemKey() {
        return getKey();
    }

    /**
     * setActionClass
     *
     * @param _s
     *  @author David Bigelow
     */
    protected void setActionClass(String _s) {
        m_strActionClass = _s;

        if (_s == null) {
            return;
        }

        // Set the basic booleans
        m_bNavigate = _s.equals("Navigate");
        m_bEdit = _s.equals("Edit");
        m_bMatrix = _s.equals("Matrix");
        m_bWhereUsed = _s.equals("WhereUsed");
        m_bQuery = _s.equals("View");
        m_bCreate = _s.equals("Create");
        m_bCart = _s.equals("Cart");
        m_bLink = _s.equals("Link");
        m_bSearch = _s.equals("Search");
        m_bExtract = _s.equals("Extract");
        m_bWatchdog = _s.equals("Watchdog");
        m_bLock = _s.equals("Lock");
        m_bDelete = _s.equals("Delete");
        m_bSBR = _s.equals("SBR");
        m_bReport = _s.equals("Report");
        m_bWorkflow = _s.equals("Workflow");
    }

    /**
     * getMetaHelpValue
     *
     * @return
     *  @author David Bigelow
     */
    public MetaHelpValue getMetaHelpValue() {
        return m_mHelpValue;
    }

    /**
     * setMetaHelpValue
     *
     * @param _mhv
     *  @author David Bigelow
     */
    public void setMetaHelpValue(MetaHelpValue _mhv) {
        m_mHelpValue = _mhv;
    }

    // Accessors and Mutators for a NavigateAction that further describe it
    // this should be lifted and placed in a NavigateAction Class that
    // extends this class

    /**
     * isNavAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isNavAction() {
        return m_bNavigate;
    }

    /**
     * isSearchAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isSearchAction() {
        return m_bSearch;
    }

    /**
     * isEditAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isEditAction() {
        return m_bEdit;
    }

    /**
     * isWhereUsedAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isWhereUsedAction() {
        return m_bWhereUsed;
    }
    /**
     * isQueryAction
     *
     * @return
     */
    public boolean isQueryAction() {
        return m_bQuery;
    }

    /**
     * isMatrixAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isMatrixAction() {
        return m_bMatrix;
    }

    /**
     * isCreateAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isCreateAction() {
        return m_bCreate;
    }

    /**
     * isCartAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isCartAction() {
        return m_bCart;
    }

    /**
     * isLinkAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isLinkAction() {
        return m_bLink;
    }

    /**
     * isExtractAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isExtractAction() {
        return m_bExtract;
    }

    /**
     * isWatchdogAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isWatchdogAction() {
        return m_bWatchdog;
    }

    /**
     * isLockAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isLockAction() {
        return m_bLock;
    }

    /**
     * isDeleteAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isDeleteAction() {
        return m_bDelete;
    }

    /**
     * isSBRAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isSBRAction() {
        return m_bSBR;
    }

    /**
     * isReportAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isReportAction() {
        return m_bReport;
    }

    /**
     * isWorkflowAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isWorkflowAction() {
        return m_bWorkflow;
    }

    /**
     * getActionClass
     *
     * @return
     *  @author David Bigelow
     */
    public String getActionClass() {
        return m_strActionClass;
    }

    /**
     * setLinkAction
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setLinkAction(boolean _b) {
        m_bLink = _b;
    }

    /*
    * Clips any external references (Parent and Child)
    * then returns itself
    */
    /**
     * clip
     *
     * @return
     *  @author David Bigelow
     */
    public EANActionItem clip() {
        clipParent();
        return this;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();

        if (_bBrief) {
            strbResult.append(getKey());
        } else {
            strbResult.append("EANActionItem:" + getKey());
            strbResult.append(":ShortDesc:" + getShortDescription());
            strbResult.append(":LongDesc:" + getLongDescription());
            strbResult.append(":Class:" + getActionClass());
            strbResult.append(":isNavigateAction:" + isNavAction());
            strbResult.append(":isEditAction:" + isEditAction());
            strbResult.append(":isWhereUsedAction:" + isWhereUsedAction());
            strbResult.append(":isQueryAction:" + isQueryAction());
            strbResult.append(":isMatrixAction:" + isMatrixAction());
            strbResult.append(":isCreateAction:" + isCreateAction());
            strbResult.append(":isLinkAction:" + isLinkAction());
            strbResult.append(":isSearch:" + isSearchAction());
        }

        return strbResult.toString();

    }

    /*
    * Nothing is Editable .. so it is always false
    */
    /**
     * isEditable
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public boolean isEditable(int _i) {
        return _i == 1 && false;
    }

    /*
    * are these objects equal?
    */
    /**
     * (non-Javadoc)
     * equals
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public final boolean equals(Object _o) {
        return ((_o != null) && (_o instanceof EANActionItem) && (getActionItemKey().equals(((EANActionItem) _o).getActionItemKey())));
    }

    //{{{ setCompareField(String) method
    //set the String representing the field to compare by
    /**
     * (non-Javadoc)
     * setCompareField
     *
     * @see COM.ibm.eannounce.objects.EANComparable#setCompareField(java.lang.String)
     */
    public void setCompareField(String _s) {
        m_strSortType = _s;
    }
    //}}}

    //{{{ getCompareFiled() method
    //get the String representing the field to compare by
    /**
     * (non-Javadoc)
     * getCompareField
     *
     * @see COM.ibm.eannounce.objects.EANComparable#getCompareField()
     */
    public String getCompareField() {
        return m_strSortType;
    }
    //}}}

    /**
     * get the sort key required by EANComparable interface
     *
     * @return String
     */
    public String toCompareString() {
        if (getCompareField().equals(SORT_BY_LONG_DESCRIPTION)) {
            return getLongDescription();

        } else if (getCompareField().equals(SORT_BY_SHORT_DESCRIPTION)) {
            return getShortDescription();

        } else if (getCompareField().equals(SORT_BY_ACTION_NAME)) {
            return getActionItemKey();

        } else if (getCompareField().equals(SORT_BY_ACTION_CLASS)) {
            return getActionClass();
        }
        //this shouldn't occur
        return toString();
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getLongDescription();
    }

    /**
     * getID
     *
     * @return
     *  @author David Bigelow
     */
    protected int getID() {
        return m_iID--;
    }

    /**
     * setID
     *
     * @param _i
     *  @author David Bigelow
     */
    protected void setID(int _i) {
        m_iID = _i;
    }

    //
    // This implements EAN Addressable
    //

    /*
    * This returns the object that we are interested in based on the _str key
    * in this case.. the key is the EntityType.AttributeType
    */
    /**
     * (non-Javadoc)
     * get
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#get(java.lang.String, boolean)
     */
    public Object get(String _str, boolean _b) {
        return toString();
    }

    /*
    * Do we have Action Item Help applicable to this guy?
    * The key does not matter here since it is in editable
    */
    /**
     * (non-Javadoc)
     * getHelp
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getHelp(java.lang.String)
     */
    public String getHelp(String _str) {
        //        return getLongDescription();
        if (m_mHelpValue != null) {
            return m_mHelpValue.getHelpValueText();
        }
        return null;
    }

    /*
    * This returns the object that we are interested in based on the _str key
    * in this case.. the key is the EntityType.AttributeType
    */
    /**
     * (non-Javadoc)
     * getEANObject
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getEANObject(java.lang.String)
     */
    public EANFoundation getEANObject(String _str) {
        return this;
    }

    /*
    * Nothing is editable yet . so it is always false
    */
    /**
     * (non-Javadoc)
     * put
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#put(java.lang.String, java.lang.Object)
     */
    public boolean put(String _s, Object _o) throws EANBusinessRuleException {
        return false;
    }

    /*
    * Nothing is Editable .. so it is always false
    */
    /**
     * (non-Javadoc)
     * isEditable
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isEditable(java.lang.String)
     */
    public boolean isEditable(String _s) {
        return false;
    }

    /*
    * Nothing is locked .. so it is always false
    */
    /**
     * (non-Javadoc)
     * isLocked
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isLocked(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int, java.lang.String, boolean)
     */
    public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
        return false;
    }

    /*
    * No LockGroup to return
    */
    /**
     * (non-Javadoc)
     * getLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#getLockGroup(java.lang.String)
     */
    public LockGroup getLockGroup(String _s) {
        return null;
    }

    /**
     * (non-Javadoc)
     * hasLock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#hasLock(java.lang.String, COM.ibm.eannounce.objects.EntityItem, COM.ibm.opicmpdh.middleware.Profile)
     */
    public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
        return false;
    }

    /*
    * This rollback an attribute for the given string index into its structure
    */
    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#rollback(java.lang.String)
     */
    public void rollback(String _str) {
    }

    /*
    * Nothing to unlock
    */
    /**
     * (non-Javadoc)
     * unlock
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#unlock(java.lang.String, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
    }

    /*
    * This does nothing
    */
    /**
     * (non-Javadoc)
     * resetLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#resetLockGroup(java.lang.String, COM.ibm.eannounce.objects.LockList)
     */
    public void resetLockGroup(String _s, LockList _ll) {
    }

    /*
    * This does nothing
    */
    /**
     * (non-Javadoc)
     * setParentEntityItem
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#setParentEntityItem(COM.ibm.eannounce.objects.EntityItem)
     */
    public void setParentEntityItem(EntityItem _ei) {
    }

    /**
     * Placeholder for a special display name when toString() isn't good enough.
     *
     * @param _s
     */
    public void setDisplayName(String _s) {
        m_strDisplayName = _s;
    }

    /**
     * Grab the value set in setDisplayName().
     *
     * @return String
     */
    public String getDisplayName() {
        return m_strDisplayName;
    }

    private void setActionHistory(ActionItemHistory _actionHistory) {
        m_actionHistory = _actionHistory;
    }

    /**
     * Hold some info about this ActionItem's history
     * <!-- functionality Added per CR #1210035324.-->
     *
     * @return ActionItemHistory
     */
    public ActionItemHistory getActionHistory() {
        if (m_actionHistory == null) {
            setActionHistory(new ActionItemHistory());
        }
        return m_actionHistory;
    }

    /**
     * Same as updatePdhMeta(Database _db) method, but check for duplicates and throw appropriate Exceptions.
     *
     * @return true if successful, false if nothing to update or unsuccessful
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.eannounce.objects.MetaUpdateException
     */
    public boolean insertPdhMeta(Database _db) throws MiddlewareException, MetaUpdateException {
        //check for existing records first
        if (isMetaDefinedInPdh(_db)) {
            MetaUpdateException MUExc = new MetaUpdateException();
            MUExc.setDuplicateKey(getKey());
            throw MUExc;
        }
        return this.updatePdhMeta(_db);
    }

    /**
     * updatePdhMeta
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.eannounce.objects.MetaUpdateException
     * @return
     *  @author David Bigelow
     */
    public boolean updatePdhMeta(Database _db) throws MiddlewareException, MetaUpdateException {
        if (!updatePdhMetaCommon(_db, false)) {
            return false;
        }
        return updatePdhMeta(_db, false);
    }

    /**
     * expirePdhMeta
     *
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.eannounce.objects.MetaUpdateException
     * @return
     *  @author David Bigelow
     */
    public boolean expirePdhMeta(Database _db) throws MiddlewareException, MetaUpdateException {
        if (!updatePdhMetaCommon(_db, true)) {
            return false;
        }
        return updatePdhMeta(_db, true);
    }

    /**
     * Is there a current record for this ActionItem in the MetaEntity table?
     *
     * @return boolean
     * @param _db
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected boolean isMetaDefinedInPdh(Database _db) throws MiddlewareException {
        //see if this is in the database (MetaEntity table) or not
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        try {
            String strNow = _db.getDates().getNow();
            try {
                rs = _db.callGBL7506(new ReturnStatus(-1), getProfile().getEnterprise(), getActionItemKey(), strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
                    rs = null;
            	}
            	_db.freeStatement();
                _db.isPending();
            }
            _db.debug(D.EBUG_SPEW, "7506 rdrs.toString():" + rdrs.toString());
            if (rdrs.getRowCount() == 0) {
                return false;
            }
        } catch (SQLException sqlExc) {
            _db.debug(D.EBUG_ERR, "EANActionItem.isMetaDefinedInPdh():" + sqlExc.toString());
            return false;
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        _db.debug(D.EBUG_SPEW, "7506 isMetaDefinedInPdh() returning true");
        return true;
    }

    /**
    * updates common to all action classes
    */
    private boolean updatePdhMetaCommon(Database _db, boolean _bExpire) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strNow = strValFrom;
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        boolean bIsInDatabase = true;
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        String strEnterprise = getProfile().getEnterprise();
        int iCurrNLSID = getProfile().getReadLanguage().getNLSID();
        String strAssociatedEntityType_db = null;
        try {
            bIsInDatabase = isMetaDefinedInPdh(_db);
            //_db.debug(D.EBUG_SPEW,"bIsInDatabase (" + getActionItemKey() + "):" + bIsInDatabase);
            //get Associated Entity Type
            try {
                rs = _db.callGBL7503(new ReturnStatus(-1), strEnterprise, "Action/Attribute", getActionItemKey(), "ENTITYTYPE", "Link", strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
                    rs = null;
            	}
            	_db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                //there should only be one of these...
                strAssociatedEntityType_db = (rdrs.getColumn(row, 0));
                //_db.debug(D.EBUG_SPEW,"gbl7503 answer (associatedEntityType for " + getActionItemKey() + "):" + strAssociatedEntityType_db);
            }
            //expires only
            if (_bExpire && bIsInDatabase) {
                // 1) MetaEntity
                new MetaEntityRow(getProfile(), getActionItemKey(), getActionClass(), strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                // 2) MetaDescription
                //     we must expire ALL nlsId's!
                //_db.debug(D.EBUG_SPEW,"calling gbl7511: " + strEnterprise + "," + getActionItemKey() + "," + getActionClass());
                try {
                    rs = _db.callGBL7511(new ReturnStatus(-1), strEnterprise, getActionItemKey(), getActionClass());
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                	if (rs != null){
                		rs.close();
                        rs = null;
                	}
                	_db.freeStatement();
                    _db.isPending();
                }
                for (int row = 0; row < rdrs.getRowCount(); row++) {
                    int iNLSID = rdrs.getColumnInt(row, 0);
                    String strShortDesc_nls = rdrs.getColumn(row, 1);
                    String strLongDesc_nls = rdrs.getColumn(row, 2);
                    //_db.debug(D.EBUG_SPEW,"gbl7511 answers: " + iNLSID + ":" + strShortDesc_nls + ":" + strLongDesc_nls);
                    new MetaDescriptionRow(getProfile(), getActionItemKey(), getActionClass(), strShortDesc_nls, strLongDesc_nls, iNLSID, strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                }
                // 3) MetaLinkAttr
                //    A) Group/Action
                try {
                    rs = _db.callGBL7507(new ReturnStatus(-1), strEnterprise, "Group/Action", getActionItemKey(), "Link", strNow, strNow);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                	if (rs != null){
                		rs.close();
                        rs = null;
                	}
                    _db.freeStatement();
                    _db.isPending();
                }
                for (int row = 0; row < rdrs.getRowCount(); row++) {
                    String strLinkType1 = rdrs.getColumn(row, 0);
                    String strLinkValue = rdrs.getColumn(row, 1);
                    new MetaLinkAttrRow(getProfile(), "Group/Action", strLinkType1, getActionItemKey(), "Link", strLinkValue, strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                }
                //  B) Role/Action/Entity/Group
                try {
                    rs = _db.callGBL7525(new ReturnStatus(-1), strEnterprise, "Role/Action/Entity/Group", getActionItemKey(), strNow, strNow);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                	if (rs != null){
                		rs.close();
                        rs = null;
                	}
                	_db.freeStatement();
                    _db.isPending();
                }
                for (int row = 0; row < rdrs.getRowCount(); row++) {
                    String strLinkType1 = rdrs.getColumn(row, 0);
                    String strLinkCode = rdrs.getColumn(row, 1);
                    String strLinkValue = rdrs.getColumn(row, 2);
                    new MetaLinkAttrRow(getProfile(), "Role/Action/Entity/Group", strLinkType1, getActionItemKey(), strLinkCode, strLinkValue, strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                }
                //  C) Action/Entity - taken care of through EANActionItemEntitiesList!!

                //  D) Action/Category
                try {
                    rs = _db.callGBL7509(new ReturnStatus(-1), strEnterprise, "Action/Category", getActionItemKey(), strNow, strNow);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                	if (rs != null){
                		rs.close();
                        rs = null;
                	}
                	_db.freeStatement();
                    _db.isPending();
                }
                for (int row = 0; row < rdrs.getRowCount(); row++) {
                    String strLinkType2 = rdrs.getColumn(row, 0);
                    String strLinkCode = rdrs.getColumn(row, 1);
                    String strLinkValue = rdrs.getColumn(row, 2);
                    new MetaLinkAttrRow(getProfile(), "Action/Category", getActionItemKey(), strLinkType2, strLinkCode, strLinkValue, strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                }
                //ExceptionAsosciated EntityType
                if (strAssociatedEntityType_db != null) {
                    new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), "ENTITYTYPE", "Link", strAssociatedEntityType_db, strNow, strNow, strNow, strNow, 2).updatePdh(_db);
                }
                //Category
                if (getCategory() != null) {
                    //dont necessarily need to expire category
                    //getCategory().expirePdhMetaDescriptions(_db,getCategoryCode(),"Category");
                }
                //expire EANActionItemEntitiesList
                getActionEntitiesList(_db).expirePdhMeta(_db);
            } else { // end expires
                try {
                    rs = _db.callGBL7511(new ReturnStatus(-1), strEnterprise, getActionItemKey(), getActionClass());
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                	if (rs != null){
                		rs.close();
                        rs = null;
                	}
                	_db.freeStatement();
                    _db.isPending();
                }
                if (bIsInDatabase) {

                    String strCatCode_db = null;
                    String strLinkValue_db = "Y";

                    for (int row = 0; row < rdrs.getRowCount(); row++) {
                        int iNLSID = rdrs.getColumnInt(row, 0);
                        String strShortDesc_nls = rdrs.getColumn(row, 1);
                        String strLongDesc_nls = rdrs.getColumn(row, 2);
                        if (iNLSID == iCurrNLSID) {
                            if (!strShortDesc_nls.equals(getShortDescription()) || !strLongDesc_nls.equals(getLongDescription())) {
                                //_db.debug(D.EBUG_SPEW,"updating descriptions for " + getActionItemKey());
                                new MetaDescriptionRow(getProfile(), getActionItemKey(), getActionClass(), getShortDescription(), getLongDescription(), iCurrNLSID, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
                            }
                        }
                    }
                    //Associated EntityType
                    if (getAssociatedEntityType() != null) {
                        if (strAssociatedEntityType_db == null || !getAssociatedEntityType().equals(strAssociatedEntityType_db)) {
                            new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), "ENTITYTYPE", "Link", getAssociatedEntityType(), strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
                        }
                    } else { //check for expire
                        if (strAssociatedEntityType_db != null) {
                            new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), "ENTITYTYPE", "Link", strAssociatedEntityType_db, strValFrom, strValFrom, strValFrom, strValFrom, 2).updatePdh(_db);
                        }
                    }
                    //Action/Category
                    try {
                        rs = _db.callGBL7508(new ReturnStatus(-1), getProfile().getEnterprise(), "Action/Category", getActionItemKey(), "Link", strNow, strNow);
                        rdrs = new ReturnDataResultSet(rs);
                    } finally  {
                    	if (rs != null){
                    		rs.close();
                            rs = null;
                    	}
                    	_db.freeStatement();
                        _db.isPending();
                    }

                    //there should only be one of these
                    for (int row = 0; row < rdrs.getRowCount(); row++) {
                        strCatCode_db = rdrs.getColumn(row, 0);
                        strLinkValue_db = rdrs.getColumn(row, 1);
                    }
                    //if used to exist, and doesnt now -> expire db record
                    if (strCatCode_db != null && (getCategoryCode() == null || getCategoryCode().equals(""))) {
                        new MetaLinkAttrRow(getProfile(), "Action/Category", getActionItemKey(), strCatCode_db, "Link", strLinkValue_db, strValFrom, strValFrom, strValFrom, strValFrom, 2).updatePdh(_db);
                    }
                    //if didnt exist, but does now -> insert new record
                    else if (strCatCode_db == null && (getCategoryCode() != null && !getCategoryCode().equals(""))) {
                        new MetaLinkAttrRow(getProfile(), "Action/Category", getActionItemKey(), getCategoryCode(), "Link", "Y", strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
                    }
                    //if used to exist, and exists now, and changed -> update
                    else if (strCatCode_db != null && getCategoryCode() != null && (!strCatCode_db.equals(getCategoryCode()))) {
                        //expire old
                        new MetaLinkAttrRow(getProfile(), "Action/Category", getActionItemKey(), strCatCode_db, "Link", strLinkValue_db, strValFrom, strValFrom, strValFrom, strValFrom, 2).updatePdh(_db);
                        //insert new
                        new MetaLinkAttrRow(getProfile(), "Action/Category", getActionItemKey(), getCategoryCode(), "Link", "Y", strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
                    }
                    //
                } else { //we can assume fresh and clean inserts ...
                    new MetaEntityRow(getProfile(), getActionItemKey(), getActionClass(), strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
                    //if a non-english create -> add descrip in English also
                    //if(iCurrNLSID != 1)
                    //    new MetaDescriptionRow(getProfile(),getActionItemKey(),getActionClass(),getShortDescription(),getLongDescription(),1,strValFrom,strValTo,strValFrom,strValTo,2).updatePdh(_db);
                    new MetaDescriptionRow(getProfile(), getActionItemKey(), getActionClass(), getShortDescription(), getLongDescription(), iCurrNLSID, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
                    new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), "ENTITYTYPE", "Link", getAssociatedEntityType(), strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
                    if (getCategoryCode() != null && !getCategoryCode().equals("")) {
                        new MetaLinkAttrRow(getProfile(), "Action/Category", getActionItemKey(), getCategoryCode(), "Link", "Y", strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
                    }
                }
                //Categories
                if (getCategory() != null) {
                    getCategory().updatePdhMetaDescriptions(_db, getCategoryCode(), "Category");
                    //if new cat, then add it!
                    try {
                        rs = _db.callGBL7506(new ReturnStatus(-1), getProfile().getEnterprise(), getCategoryCode(), strNow, strNow);
                        rdrs = new ReturnDataResultSet(rs);
                    } finally {
                    	if (rs != null){
                    		rs.close();
                            rs = null;
                    	}
                    	_db.freeStatement();
                        _db.isPending();
                    }
                    if (rdrs.getRowCount() == 0) {
                        new MetaEntityRow(getProfile(), getCategoryCode(), "Category", strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
                    }
                }
                //update EANActionItemEntitiesList
                getActionEntitiesList(_db).updatePdhMeta(_db);
            }

        } catch (SQLException sqlExc) { //for rs.close()
            _db.debug(D.EBUG_ERR, "861 " + sqlExc.toString());
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        //update the cache for the Parent MetaActionItem list if applicable
        try {
            MetaActionItemList mail = (MetaActionItemList) getParent();
            Profile origprof = getProfile();
            //this should cover 3 cases:
            // 1) delete
            // 2) add new
            // 3) update existing
            mail.removeActionItem(this); //justo be safe -> remove old reference
            //replace w/ new
            if (!_bExpire) {
                mail.putActionItem(this);
                //we must set the parent reference
                this.setParent(mail);
            }else{
            	// restore the profile if needed
            	if (getProfile()==null){
            		setProfile(origprof);
            	}
            }
            //do we need to expire first???
        } catch (ClassCastException c) { /*Do Nothing*/
            c.printStackTrace();
        }
        return true;
    }

    //////////
    // EANActionItemEntitiesList methods are used in xxxEditHtml classes -->
    //////////
    /**
     * getNewActionItemEntity
     *
     * @param _eList
     * @param _db
     * @param _strEntity
     * @param _strLinkCode
     * @param _strLinkValue
     * @return
     *  @author David Bigelow
     */
    public EANActionItemEntitiesList.EANActionItemEntity getNewActionItemEntity(EANActionItemEntitiesList _eList, Database _db, String _strEntity, String _strLinkCode, String _strLinkValue) {
        if (getPurpose().equalsIgnoreCase("Extract") || getPurpose().equalsIgnoreCase("Navigate")) {
            return getActionEntitiesList(_db).getNewActionItemEntity(_eList, _db, _strEntity, _strLinkCode, _strLinkValue);
        } else {
            return null;
        }
    }

    /**
     * getNewActionEntitiesList
     *
     * @param _db
     * @return
     *  @author David Bigelow
     */
    public EANActionItemEntitiesList getNewActionEntitiesList(Database _db) {
        try {
            m_actionEntitiesList = new EANActionItemEntitiesList(this, _db, getProfile());
        } catch (MiddlewareRequestException mrExc) {
            _db.debug(D.EBUG_ERR, "EANActionItemEntitiesList constructor:" + mrExc.toString());
        }
        return m_actionEntitiesList;
    }

    /**
     * getActionEntitiesList
     *
     * @param _db
     * @return
     *  @author David Bigelow
     */
    public EANActionItemEntitiesList getActionEntitiesList(Database _db) {
        if (m_actionEntitiesList == null) {
            m_actionEntitiesList = getNewActionEntitiesList(_db);
        }
        return m_actionEntitiesList;
    }

    /**
     * setActionEntitiesList
     *
     * @param _theList
     *  @author David Bigelow
     */
    public void setActionEntitiesList(EANActionItemEntitiesList _theList) {
        m_actionEntitiesList = _theList;
    }
    ////////
    // --> EANActionItemEntitiesList methods
    ////////

    /**
     * Save a FilterGroup object in this ActionItem for later replay.
     * Ensure that the parent is clipped.
     *
     * @param _fg
     */
    public void setFilterGroup(FilterGroup _fg) {
        _fg.setParent(null);
        m_filterGroup = _fg;
    }

    /**
     * getFilterGroup
     *
     * @return
     *  @author David Bigelow
     */
    public FilterGroup getFilterGroup() {
        return m_filterGroup;
    }

    /**
     * hasFilterGroup
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasFilterGroup() {
        return (getFilterGroup() != null);
    }

    /**
     * (non-Javadoc)
     * isParentAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isParentAttribute(java.lang.String)
     */
    public boolean isParentAttribute(String _str) {
        return false;
    }
    /**
     * (non-Javadoc)
     * isChildAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isChildAttribute(java.lang.String)
     */
    public boolean isChildAttribute(String _str) {
        return false;
    }

    //These methods are for chain action item

    /**
     * setChainActionType
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setChainActionType(String _str) {
        m_strChainActionType = _str;
    }

    /**
     * getChainActionType
     *
     * @return
     *  @author David Bigelow
     */
    public String getChainActionType() {
        return m_strChainActionType;
    }

    /**
     * setChainActionClass
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setChainActionClass(String _str) {
        m_strChainActionClass = _str;
        if (_str == null) {
            return;
        }

        // Set the basic booleans
        m_bChainNavigate = _str.equals("Navigate");
        m_bChainEdit = _str.equals("Edit");
        m_bChainMatrix = _str.equals("Matrix");
        m_bChainWhereUsed = _str.equals("WhereUsed");
        m_bChainCreate = _str.equals("Create");
        m_bChainLink = _str.equals("Link");
        m_bChainSearch = _str.equals("Search");
        m_bChainExtract = _str.equals("Extract");
        m_bChainWatchdog = _str.equals("Watchdog");
        m_bChainLock = _str.equals("Lock");
        m_bChainDelete = _str.equals("Delete");
        m_bChainReport = _str.equals("Report");
        m_bChainWorkflow = _str.equals("Workflow");
    }

    /**
     * getChainActionClass
     *
     * @return
     *  @author David Bigelow
     */
    public String getChainActionClass() {
        return m_strChainActionClass;
    }

    /**
     * hasChainAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasChainAction() {
        return (m_strChainActionType != null);
    }

    /**
     * setRequireInput
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setRequireInput(String _str) {
        m_strRequireInput = _str;
    }

    /**
     * getRequireInput
     *
     * @return
     *  @author David Bigelow
     */
    public String getRequireInput() {
        return m_strRequireInput;
    }

    /**
     * requireInput
     *
     * @return
     *  @author David Bigelow
     */
    public boolean requireInput() {
        return ((m_strRequireInput != null) && (!m_strRequireInput.equals("N")));
    }

    /**
     * isChainNavAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainNavAction() {
        return m_bChainNavigate;
    }

    /**
     * isChainSearchAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainSearchAction() {
        return m_bChainSearch;
    }

    /**
     * isChainEditAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainEditAction() {
        return m_bChainEdit;
    }

    /**
     * isChainWhereUsedAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainWhereUsedAction() {
        return m_bChainWhereUsed;
    }

    /**
     * isChainMatrixAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainMatrixAction() {
        return m_bChainMatrix;
    }

    /**
     * isChainCreateAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainCreateAction() {
        return m_bChainCreate;
    }

    /**
     * isChainLinkAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainLinkAction() {
        return m_bChainLink;
    }

    /**
     * isChainExtractAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainExtractAction() {
        return m_bChainExtract;
    }

    /**
     * isChainWatchdogAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainWatchdogAction() {
        return m_bChainWatchdog;
    }

    /**
     * isChainLockAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainLockAction() {
        return m_bChainLock;
    }

    /**
     * isChainDeleteAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainDeleteAction() {
        return m_bChainDelete;
    }

    /**
     * isChainReportAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainReportAction() {
        return m_bChainReport;
    }

    /**
     * isChainWorkflowAction
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isChainWorkflowAction() {
        return m_bChainWorkflow;
    }

    /**
     * getChainActionItem
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getChainActionItem(Database _db, RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
        if (_db == null && _rdi == null) {
            return null;
        }

        if (_db != null) {
            if (isChainNavAction()) {
                return _db.getNavActionItem(null, _prof, m_strChainActionType);
            } else if (isChainSearchAction()) {
                return _db.getSearchActionItem(null, _prof, m_strChainActionType);
            } else if (isChainEditAction()) {
                return _db.getEditActionItem(null, _prof, m_strChainActionType);
            } else if (isChainWhereUsedAction()) {
                return _db.getWhereUsedActionItem(null, _prof, m_strChainActionType);
            } else if (isChainMatrixAction()) {
                return _db.getMatrixActionItem(null, _prof, m_strChainActionType);
            } else if (isChainCreateAction()) {
                return _db.getCreateActionItem(null, _prof, m_strChainActionType);
            } else if (isChainLinkAction()) {
                return _db.getLinkActionItem(null, _prof, m_strChainActionType);
            } else if (isChainExtractAction()) {
                return _db.getExtractActionItem(null, _prof, m_strChainActionType);
            } else if (isChainWatchdogAction()) {
                return _db.getWatchdogActionItem(null, _prof, m_strChainActionType);
            } else if (isChainLockAction()) {
                return _db.getLockActionItem(null, _prof, m_strChainActionType);
            } else if (isChainDeleteAction()) {
                return _db.getDeleteActionItem(null, _prof, m_strChainActionType);
            } else if (isChainReportAction()) {
                return _db.getReportActionItem(null, _prof, m_strChainActionType);
            } else if (isChainWorkflowAction()) {
                return _db.getWorkflowActionItem(null, _prof, m_strChainActionType);
            }
        } else {
            if (isChainNavAction()) {
                return _rdi.getNavActionItem(null, _prof, m_strChainActionType);
            } else if (isChainSearchAction()) {
                return _rdi.getSearchActionItem(null, _prof, m_strChainActionType);
            } else if (isChainEditAction()) {
                return _rdi.getEditActionItem(null, _prof, m_strChainActionType);
            } else if (isChainWhereUsedAction()) {
                return _rdi.getWhereUsedActionItem(null, _prof, m_strChainActionType);
            } else if (isChainMatrixAction()) {
                return _rdi.getMatrixActionItem(null, _prof, m_strChainActionType);
            } else if (isChainCreateAction()) {
                return _rdi.getCreateActionItem(null, _prof, m_strChainActionType);
            } else if (isChainLinkAction()) {
                return _rdi.getLinkActionItem(null, _prof, m_strChainActionType);
            } else if (isChainExtractAction()) {
                return _rdi.getExtractActionItem(null, _prof, m_strChainActionType);
            } else if (isChainWatchdogAction()) {
                return _rdi.getWatchdogActionItem(null, _prof, m_strChainActionType);
            } else if (isChainLockAction()) {
                return _rdi.getLockActionItem(null, _prof, m_strChainActionType);
            } else if (isChainDeleteAction()) {
                return _rdi.getDeleteActionItem(null, _prof, m_strChainActionType);
            } else if (isChainReportAction()) {
                return _rdi.getReportActionItem(null, _prof, m_strChainActionType);
            } else if (isChainWorkflowAction()) {
                return _rdi.getWorkflowActionItem(null, _prof, m_strChainActionType);
            }
        }

        return null;
    }

    //End of methods for chain action item
    /**
     * setSingleInput
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setSingleInput(boolean _b) {
        m_bSingleInput = _b;
    }

    /**
     * isSingleInput
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isSingleInput() {
        return m_bSingleInput;
    }

    /**
     * setRootEI
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setRootEI(boolean _b) {
        m_bRootEI = _b;
    }

    /**
     * useRootEI
     *
     * @return
     *  @author David Bigelow
     */
    public boolean useRootEI() {
        return m_bRootEI;
    }

    /**
     * setRootEntityType
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setRootEntityType(String _str) {
        m_strRootEntityType = _str;
    }

    /**
     * getRootEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getRootEntityType() {
        return m_strRootEntityType;
    }

    /**
     * setMetaColumnOrderGroup
     *
     * @param _mcog
     *  @author David Bigelow
     */
    protected void setMetaColumnOrderGroup(MetaColumnOrderGroup _mcog) {
        m_mcog = _mcog;
        if(m_mcog!=null){
            m_mcog.setParent(this);  // break link to entitylist - reduce memory needed
        }
    }

    /**
     * getMetaColumnOrderGroup
     *
     * @return
     *  @author David Bigelow
     */
    protected MetaColumnOrderGroup getMetaColumnOrderGroup() {
        return m_mcog;
    }

    /**
     * hasMetaColumnOrderGroup
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasMetaColumnOrderGroup() {
        return (m_mcog != null);
    }

    /**
     * isMetaColumnOrderControl
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isMetaColumnOrderControl() {
        return m_bMetaColumnOrderControl;
    }

    /**
     * setMetaColumnOrderControl
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setMetaColumnOrderControl(boolean _b) {
        m_bMetaColumnOrderControl = _b;
    }

    /**
      * setClearChangeGroup
      *
      * @param _b
      *  @author David Bigelow
      */
     protected void setClearTargetChangeGroup(boolean _b) {
         m_bClearTargetChangeGroup = _b;
     }

     public boolean isClearTargetChangeGroupEnabled() {
         return m_bClearTargetChangeGroup;
     }

    /**
     * Some ActionItems (e.g. certain Edits/Creates) will need to pull all NLS's at the time of execution. Default single nls;
     * @return
     * @author Gregg Burns
     */
     public final boolean isAllNLS() {
		 return m_bAllNLS;
	 }
     protected final void setAllNLS(boolean _b) {
		 m_bAllNLS = _b;
	 }

	/**
	 * get data source
	 *
	 * catalogEnhancement
	 *
	 * @return datasource indicator
	 * @author tony
	 */
	public final int getDataSource() {
		return m_iDatasource;
	}

	/**
	 * get data source as String
	 *
	 * catalogEnhancement
	 *
	 * @return datasource indicator
	 * @author tony
	 */
	public final String getDataSourceAsString() {
		switch (m_iDatasource) {
		case CATALOG_DATASOURCE:
			return "CATALOG";
		case ODS_DATASOURCE:
			return "ODS";
		default:
			return "PDH";
		}
	}

	/**
	 * set data source
	 *
	 * catalogEnhancement
	 *
	 * @param _strSource type of data source
	 * @author tony
	 */
	protected final void setDataSource(String _strSource) {
		if (_strSource != null) {
			if (_strSource.startsWith("CATALOG")) {
				m_iDatasource = CATALOG_DATASOURCE;
			} else if (_strSource.startsWith("ODS")) {
				m_iDatasource = ODS_DATASOURCE;
			} else {
				m_iDatasource = PDH_DATASOURCE;
			}
			int indx = _strSource.indexOf(":") + 1;
			if (indx > 0 && indx < _strSource.length()) {
				setAdditional(_strSource.substring(indx));
			}
			//look for : if it contains one
			//the stuff after it is the spname
		} else {
			m_iDatasource = PDH_DATASOURCE;
		}
		return;
	}

	/**
	 * is pdh data source
	 *
	 * catalogEnhancement
	 *
	 * @return boolean
	 * @author tony
	 */
	public final boolean isPDHDataSource() {
		return m_iDatasource == PDH_DATASOURCE;
	}

	/**
	 * is catalog data source
	 *
	 * catalogEnhancement
	 *
	 * @return datasource indicator
	 * @author tony
	 */
	public final boolean isCatalogDataSource() {
		return m_iDatasource == CATALOG_DATASOURCE;
	}

	/**
	 * is ods datasource
	 *
	 * catalogEnhancement
	 *
	 * @return datasource indicator
	 * @author tony
	 */
	public final boolean isODSDataSource() {
		return m_iDatasource == ODS_DATASOURCE;
	}

	/**
	 * set the special SP
	 * for the retrieval function
	 *
	 * catalogEnhancement
	 *
	 * @param _s the name of the sp
	 * @author tony
	 */
	protected final void setAdditional(String _s) {
		if (_s != null) {
			m_sSp = new String(_s);
		}
		return;
	}

	/**
	 * get the special SP
	 * for the retrieval function
	 * will return the name of the sp
	 * that then has to be coded for
	 *
	 * catalogEnhancement
	 *
	 * @param _s the name of the sp
	 * @author tony
	 */
	public final String getAdditional() {
		return m_sSp;
	}
	/**
	 * get Additonal parameters as an
	 * array of string
	 * @return string[]
	 * @author tony
	 */
	public final String[] getAdditionalParms() {
		return m_sSpParms;
	}

	/**
	 * set the parms
	 * @param parms
	 * @author tony
	 */
	protected void setAdditionalParms(String _s) {
		if (_s != null) {
			StringTokenizer st = new StringTokenizer(_s,"|");
			m_sSpParms = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreElements()) {
				m_sSpParms[i] = st.nextToken();
				++i;
			}
		}
		return;
	}
	/**
	 * set the parms
	 * @param parms
	 * @author tony
	 */
	protected void setAdditionalParms(String[] _s) {
		m_sSpParms = _s;
		return;
	}

	/**
	 * is the special SP defined
	 * for the retrieval function
	 *
	 * catalogEnhancement
	 *
	 * @param _s the name of the sp
	 * @author tony
	 */
	public final boolean hasAdditional() {
		return m_sSp != null;
	}

	/**
	 * does the additional function
	 * require specific parms
	 * @return boolean
	 * @author tony
	 */
	public final boolean hasAdditionalParms() {
		return m_sSpParms != null && m_sSpParms.length > 0;
	}

	/**
	 * based on the special SP
	 * we need to map the result set
	 * with what is currently being returned.
	 * GBL7006 for Navigate
	 * GBL7520 and GB7007 for edit
	 *
	 * We are running custom code, but returning
	 * information as it is currently being returned
	 * this will insure that the information is in
	 * the proper sequence.  Which should allow the
	 * existing load to function properly.
	 *
	 * catalogEnhancement
	 *
	 * @return ResultSet
	 * @author Tony
	 */
	public Object processAdditional(Database _db, Profile _prof, EntityItem[] _ei, Object[] _parms) {
		String sAdd = getAdditional();
		if (sAdd != null) {
			Object[] parms = null;
			if (hasAdditionalParms()) {
				parms = getParameters(getAdditionalParms(), _parms);
			} else {
			}
			if (isCatalogDataSource()) {
				if (sAdd.equals("View")) {
					if (parms != null) {
						CatalogViewer cv = new CatalogViewer(_prof,(String)parms[1],(String)parms[2],((Integer)parms[3]).intValue());
						if (cv != null) {
							return cv.getEntityList(_prof,this,_ei,(String)parms[4],((Integer)parms[5]).intValue(),((Integer)parms[6]).intValue());
						}
					}
				}
			} else if (isODSDataSource()) {
				System.out.println("ODSDatasource Additional Instruction Set currently NOT supported");
			} else if (isPDHDataSource()) {
				System.out.println("PDHDatasource Additional Instruction Set currently NOT supported");
			}
		}
		return null;
	}

	public Object rexecAdditional(RemoteDatabaseInterface _rdi, Profile _prof, EntityItem[] _ei, Object[] _parms) throws MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {
		Object out = null;
		out = _rdi.execAdditional(_prof, this, _ei,_parms);
		return out;
	}

	public Object execAdditional(Database _db, Profile _prof, EntityItem[] _ei, Object[] _parms) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
		Object out = _db.execAdditional(_prof,this,_ei,_parms);
		return out;
	}

	private final boolean isInternalParameter(String _s) {
		if (_s != null) {
			if (_s.startsWith("{")  && _s.endsWith("}")) {
				return true;
			}
		}
		return false;
	}

	private final Object[] getParameters(String[] _s,Object[] _o) {
		Object[] out = null;
		if (_s != null) {
			int ii = _s.length;
			out = new Object[ii];
			for (int i=0;i<ii;++i) {
				if (isInternalParameter(_s[i])) {
					out[i] = getInternalParameter(_s[i],_o);
				} else {
					out[i] = _s[i];
				}
			}
		}
		return out;
	}

	/**
	 * get the internally passed parameter
	 * this may need to be changed to protected
	 * to improve functionality....
	 * @param parameter
	 * @param objects
	 * @return object
	 * @author tony
	 */
	private Object getInternalParameter(String _s, Object[] _o) {
		if (_s != null) {
			String parm = _s.substring(1,_s.length() -1);
			if (parm.equals("null")) {
				return null;
			} else if (parm.startsWith("$p")) {
				if (_o != null) {
					String tmp = parm.substring(2);
					try {
						int i = Integer.parseInt(tmp);
						if (i >= 0 && _o.length > i) {
							return _o[i];
						}
					} catch (NumberFormatException _nfe) {
						_nfe.printStackTrace();
					}
				}
			} else if (parm.startsWith("$i")) {
				String tmp = parm.substring(2);
				D.ebug(D.EBUG_SPEW,"    detected: " + parm);
				try {
					return new Integer(tmp);
				} catch (NumberFormatException _nfe) {
					return parm;
				}
			}
		}
		return null;
	}

	/**
	 * set this action to a VEEdit
	 * VEEdit
	 * CR0815056514
	 * @param b
	 * @author ton
	 */
	protected void setVEEdit(boolean _b) {
		m_bVEEdit = _b;
	}
	/**
	 * is this a VEEdit
	 * VEEdit
	 * CR0815056514
	 * @return boolean
	 * @author tony
	 */
	public boolean isVEEdit() {
		return m_bVEEdit;
	}

	/**
	 * set the target type for the action
	 * @return string
	 * @author tony
	 */
	protected final void setTargetType(String _s) {
		m_strTargetType = _s;
		return;
	}

	/**
	 * get the target type for the action
	 * @return string
	 * @author tony
	 */
	public String getTargetType() {
		return m_strTargetType;
	}
}
