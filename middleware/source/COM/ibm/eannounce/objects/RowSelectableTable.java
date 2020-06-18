////
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: RowSelectableTable.java,v $
// Revision 1.280  2013/10/24 17:34:33  wendy
// add access to tablewrapper
//
// Revision 1.279  2012/04/16 16:33:28  wendy
// fillcopy/fillpaste perf updates
//
// Revision 1.278  2010/11/04 01:19:48  wendy
// use lastindex when stripping relatorChar in applycolumnorders
//
// Revision 1.277  2010/11/03 19:58:00  wendy
// ColumnOrder needs to check for relatortags too
//
// Revision 1.276  2009/05/28 14:35:51  wendy
// Performance updates
//
// Revision 1.275  2008/08/02 01:59:56  wendy
// CQ00006067-WI : LA CTO - Added support for QueryAction
//
// Revision 1.274  2008/07/31 18:59:05  wendy
// CQ00006067-WI : LA CTO - Added support for QueryAction
//
// Revision 1.273  2008/04/29 19:27:56  wendy
// MN35270066 VEEdit rewrite
//
// Revision 1.272  2008/02/01 22:10:06  wendy
// Cleanup RSA warnings
//
// Revision 1.271  2007/08/09 13:46:30  wendy
// RQ0713072645 whereused create check domain
//
// Revision 1.270  2007/08/03 11:43:28  wendy
// Remove debug msgs
//
// Revision 1.269  2007/08/03 11:25:45  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.268  2006/01/05 21:30:30  tony
// VEEdit BUI functionality enhancement
//
// Revision 1.267  2005/11/07 21:40:12  tony
// improved logic for VEEdit Iteration2
//
// Revision 1.266  2005/11/07 17:31:10  tony
// removed comment
//
// Revision 1.265  2005/11/04 23:02:17  tony
// VEEdit_Iteration2 tuning
//
// Revision 1.264  2005/11/04 16:50:13  tony
// VEEdit_Iteration2
//
// Revision 1.263  2005/11/04 14:52:11  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.262  2005/10/28 19:19:05  tony
// added print statements
//
// Revision 1.261  2005/07/25 18:29:12  tony
// WK_001
// Filter was not properly working for multi-flag.
// updated the logic to use the correct get call and
// now the filter works properly.
//
// Revision 1.260  2005/06/02 16:29:28  tony
// JSTT-6CYHKV
//
// Revision 1.259  2005/03/24 18:38:53  joan
// work on flag maintenance
//
// Revision 1.258  2005/03/11 22:21:40  dave
// removing those nasty auto method generations
//
// Revision 1.257  2005/03/10 22:12:06  dave
// Jtest results
//
// Revision 1.256  2005/03/07 19:58:13  joan
// work on flag maintenance
//
// Revision 1.255  2005/03/04 19:18:53  joan
// work on maintenance flag
//
// Revision 1.254  2005/03/03 21:30:18  joan
// fixes
//
// Revision 1.253  2005/03/03 20:20:15  joan
// fixes
//
// Revision 1.252  2005/02/25 17:53:04  gregg
// fixing MetacolumnOrderGroup build, remove debugs!
//
// Revision 1.251  2005/02/02 17:37:39  gregg
// fix
//
// Revision 1.250  2005/02/02 17:37:14  gregg
// fix
//
// Revision 1.249  2005/02/01 19:34:27  gregg
// fix boolean logic
//
// Revision 1.248  2005/02/01 19:20:06  gregg
// settin up for column fitlering
//
// Revision 1.247  2005/01/25 18:45:36  joan
// fix null pointer
//
// Revision 1.246  2005/01/18 18:47:25  tony
// added functionality to improve capability for
// sorting based on column headers
//
// Revision 1.245  2005/01/10 22:04:07  joan
// fix compile
//
// Revision 1.244  2005/01/10 21:59:49  joan
// fix compile
//
// Revision 1.243  2005/01/10 21:47:49  joan
// work on multiple edit
//
// Revision 1.242  2005/01/05 19:32:30  joan
// fix compile
//
// Revision 1.241  2005/01/05 19:24:09  joan
// add new method
//
// Revision 1.240  2004/11/23 21:09:16  gregg
// more ColOrder on SearchActions
//
// Revision 1.239  2004/11/17 20:18:06  gregg
// remove debugs
//
// Revision 1.238  2004/11/16 20:22:35  tony
// added hideRows functionality.
//
// Revision 1.237  2004/11/16 19:28:38  tony
// added synchronize rows functionality
//
// Revision 1.236  2004/11/16 17:37:38  bala
// adding sort capability
//
// Revision 1.235  2004/11/16 01:13:46  joan
// fixes
//
// Revision 1.234  2004/11/15 22:41:18  gregg
// more MetaColumnOrder
//
// Revision 1.233  2004/11/15 22:35:22  gregg
// work on column orders for ActionItems where parent.entitytype == child.entitytype
//
// Revision 1.232  2004/11/12 19:12:17  tony
// added keyed matrix value get.
//
// Revision 1.231  2004/11/11 21:55:14  tony
// removed printltn
//
// Revision 1.230  2004/11/11 21:34:10  tony
// get by key and int adjustment.
//
// Revision 1.229  2004/11/11 21:18:56  tony
// get by keys
//
// Revision 1.228  2004/11/11 21:02:49  tony
// fixed getkey logic
//
// Revision 1.227  2004/11/11 20:37:37  tony
// added a get(key,key) to the rowSelectableTable
//
// Revision 1.226  2004/11/09 23:58:57  joan
// work on show parent child
//
// Revision 1.225  2004/11/05 18:57:02  joan
// fixes
//
// Revision 1.224  2004/10/25 20:31:40  joan
// fixes
//
// Revision 1.223  2004/10/25 17:23:01  joan
// work on create parent
//
// Revision 1.222  2004/10/22 20:08:09  dave
// change for better trace
//
// Revision 1.221  2004/10/22 19:39:55  dave
// Calling a double shot of string
//
// Revision 1.220  2004/10/21 23:46:25  dave
// rowselectable table contructor debug
//
// Revision 1.219  2004/10/21 22:45:56  dave
// attempting to speed up rendering by removing the need to
// create a new String buffer
//
// Revision 1.218  2004/10/01 23:23:26  joan
// fixes
//
// Revision 1.217  2004/07/27 22:19:11  joan
// work on lock
//
// Revision 1.216  2004/07/26 22:16:22  joan
// fix compile
//
// Revision 1.215  2004/07/26 22:08:53  joan
// add unlockEntityItems
//
// Revision 1.214  2004/07/26 17:39:20  joan
// fix compile
//
// Revision 1.213  2004/07/26 17:06:02  joan
// add lockEntityItems
//
// Revision 1.212  2004/06/30 16:32:00  joan
// debug
//
// Revision 1.211  2004/06/23 16:23:59  joan
// fix null pointer
//
// Revision 1.210  2004/06/18 17:34:27  joan
// fix compile
//
// Revision 1.209  2004/06/18 17:24:07  joan
// work on edit relator
//
// Revision 1.208  2004/06/18 17:11:18  joan
// work on edit relator
//
// Revision 1.207  2004/06/08 17:58:22  joan
// throw exception
//
// Revision 1.206  2004/06/08 17:51:31  joan
// throw exception
//
// Revision 1.205  2004/06/08 17:05:22  joan
// add methods
//
// Revision 1.204  2004/04/09 19:56:23  joan
// fix compile
//
// Revision 1.203  2004/04/09 19:45:23  joan
// add duplicate method
//
// Revision 1.202  2004/04/09 19:37:21  joan
// add duplicate method
//
// Revision 1.201  2003/10/28 18:51:22  joan
// remove System.out
//
// Revision 1.200  2003/10/20 21:16:16  joan
// fb52563
//
// Revision 1.199  2003/09/17 21:27:16  gregg
// applyFilter(): skip new enttiyID's (< 0) -- per FB 52297
//
// Revision 1.198  2003/08/28 16:28:08  joan
// adjust link method to have link option
//
// Revision 1.197  2003/08/21 23:49:59  joan
// work on general search
//
// Revision 1.196  2003/08/18 21:18:52  dave
// isLocked Modifier
//
// Revision 1.195  2003/08/18 21:05:09  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.194  2003/06/25 18:44:02  joan
// move changes from v111
//
// Revision 1.193.2.3  2003/06/25 00:52:42  joan
// fix compile
//
// Revision 1.193.2.2  2003/06/25 00:36:51  joan
// fix compile
//
// Revision 1.193.2.1  2003/06/24 23:37:29  joan
// fix WhereUsedActionItem constructor
//
// Revision 1.193  2003/05/14 21:04:48  dave
// non implicating t he implicator
//
// Revision 1.192  2003/05/13 22:23:26  roger
// Fix compile error
//
// Revision 1.191  2003/05/13 21:53:46  dave
// if the row is an implicator.. the return the parent
//
// Revision 1.190  2003/05/09 21:38:28  gregg
// comment
//
// Revision 1.189  2003/05/09 21:36:04  gregg
// applyFilter(): filter on getMatrixValue() for MatrixLists
//
// Revision 1.188  2003/05/08 22:45:01  dave
// remove stack track
//
// Revision 1.187  2003/05/08 21:17:44  dave
// tracking so we can find some code stacks
//
// Revision 1.186  2003/05/05 21:38:47  joan
// fix null pointer
//
// Revision 1.185  2003/04/24 18:32:18  dave
// getting rid of traces and system out printlns
//
// Revision 1.184  2003/03/27 00:12:56  gregg
// remove System.out's
//
// Revision 1.183  2003/03/26 00:00:57  gregg
// tying off apply MetaColumnOrder to RST
//
// Revision 1.182  2003/03/25 20:31:42  gregg
// more applyColumnOrders for relator case
//
// Revision 1.181  2003/03/24 22:37:05  gregg
// in applyColumnOrders -> add any unspecified rows to end of list
//
// Revision 1.180  2003/03/24 22:18:29  gregg
// apply MetaColOrder logic to Vertical+Horizontal
//
// Revision 1.179  2003/03/21 00:15:38  gregg
// some prearation for apply MetaColumnOrder Logic, but not enforcing it yet due to code drop...
//
// Revision 1.178  2003/03/19 18:46:40  joan
// adjust codes
//
// Revision 1.177  2003/02/25 19:00:08  gregg
// setUseFilter, useFilter methods
//
// Revision 1.175  2003/02/24 23:13:07  gregg
// more FilterGroup
//
// Revision 1.174  2003/02/22 00:46:08  gregg
// calls to refresh moved from within setFilterGroup/removeFilterGroup methods
//
// Revision 1.173  2003/02/20 19:22:22  gregg
// just some method description changes
//
// Revision 1.172  2003/02/20 19:20:14  gregg
// protected method getColumnList
//
// Revision 1.171  2003/02/18 21:55:16  gregg
// remove System.out's
//
// Revision 1.170  2003/02/18 21:37:03  gregg
// more refresh()
//
// Revision 1.169  2003/02/18 21:11:02  gregg
// some more trace statements in refresh method
//
// Revision 1.168  2003/02/18 19:11:24  gregg
// in applyFilter-> use getColumnKey(i) : (not getColumn(i).getKey())
//
// Revision 1.167  2003/02/18 18:29:12  gregg
// hasFilterGroup went public
//
// Revision 1.166  2003/02/18 17:36:25  gregg
// call refresh() in removeFilterGroup method
//
// Revision 1.165  2003/02/18 17:34:40  gregg
// hasFilteredRows method
//
// Revision 1.164  2003/02/18 00:13:45  gregg
// System.out in applyFilter
//
// Revision 1.163  2003/02/18 00:05:55  gregg
// first shot at applyFilter logic for RowSelectableTable
//
// Revision 1.162  2003/02/17 17:31:45  gregg
// getFilterGroup/setFilterGroup methods
//
// Revision 1.161  2003/02/14 23:21:16  gregg
// Removed FilterList -> comment out getFilterGroup logic until this class is solidified
//
// Revision 1.160  2003/02/13 20:43:02  gregg
// getFilterList, setFilterList methods
//
// Revision 1.159  2003/01/21 00:20:36  joan
// adjust link method to test VE lock
//
// Revision 1.158  2003/01/14 23:46:54  joan
// adjust exception
//
// Revision 1.157  2003/01/14 22:05:06  joan
// adjust removeLink method
//
// Revision 1.156  2003/01/08 21:44:06  joan
// add getWhereUsedList
//
// Revision 1.155  2002/12/06 18:42:23  dave
// trying to comment out is editable
//
// Revision 1.154  2002/11/19 23:26:56  joan
// fix hasLock method
//
// Revision 1.153  2002/11/19 18:27:43  joan
// adjust lock, unlock
//
// Revision 1.152  2002/11/19 00:22:28  joan
// fix compile
//
// Revision 1.151  2002/11/19 00:06:27  joan
// adjust isLocked method
//
// Revision 1.150  2002/11/12 17:18:28  dave
// System.out.println clean up
//
// Revision 1.149  2002/11/12 02:20:10  dave
// fixing rollback
//
// Revision 1.148  2002/11/07 18:10:09  dave
// cleaning up displays on rst
//
// Revision 1.147  2002/10/30 23:50:31  dave
// more syntax
//
// Revision 1.146  2002/10/30 23:18:48  dave
// syntax and more throwing
//
// Revision 1.145  2002/10/29 00:02:56  dave
// backing out row commit for 1.1
//
// Revision 1.144  2002/10/28 23:49:15  dave
// attempting the first commit with a row index
//
// Revision 1.143  2002/10/28 21:09:27  dave
// string bounds exception error fixed
//
// Revision 1.142  2002/10/28 20:48:16  dave
// misc syntax
//
// Revision 1.141  2002/10/28 20:39:43  dave
// Feedback 22529.  Changed column title of searchBinder
// and tried to remove the asterek from the get when
// we are in DynaTable mode
//
// Revision 1.140  2002/10/23 14:54:12  dave
// removal of display statements
//
// Revision 1.139  2002/10/22 22:17:19  dave
// more trace for classifications
//
// Revision 1.138  2002/10/22 21:50:03  dave
// more trace
//
// Revision 1.137  2002/10/18 20:39:44  joan
// fix compile
//
// Revision 1.136  2002/10/18 20:18:54  joan
// add isMatrixEditable method
//
// Revision 1.135  2002/10/09 21:32:57  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.134  2002/10/07 17:49:03  joan
// add getLockGroup
//
// Revision 1.133  2002/09/27 17:11:00  dave
// made addRow a boolean
//
// Revision 1.132  2002/08/15 17:38:47  joan
// fix null pointer
//
// Revision 1.131  2002/08/08 20:51:49  joan
// fix setParentEntityItem
//
// Revision 1.130  2002/08/08 20:16:31  joan
// fix setParentEntityItem
//
// Revision 1.129  2002/08/08 20:07:26  joan
// fix setParentEntityItem
//
// Revision 1.128  2002/07/16 15:38:20  joan
// working on method to return the array of actionitems
//
// Revision 1.127  2002/07/08 21:51:32  joan
// remove System.out
//
// Revision 1.126  2002/07/08 17:53:43  joan
// fix link method
//
// Revision 1.125  2002/07/08 16:05:30  joan
// fix link method
//
// Revision 1.124  2002/06/27 15:52:44  joan
// fixing bugs
//
// Revision 1.123  2002/06/26 23:54:01  joan
// fix bugs
//
// Revision 1.122  2002/06/26 22:27:04  joan
// fixing bugs
//
// Revision 1.121  2002/06/25 20:36:09  joan
// add create method for whereused
//
// Revision 1.120  2002/06/25 17:49:37  joan
// add link and removeLink methods
//
// Revision 1.119  2002/06/21 21:40:40  joan
// null pointer
//
// Revision 1.118  2002/06/19 18:01:15  joan
// addColumn for an array
//
// Revision 1.117  2002/06/19 15:52:20  joan
// work on add column in matrix
//
// Revision 1.116  2002/06/17 23:53:47  joan
// add addColumn method
//
// Revision 1.115  2002/06/07 22:29:42  joan
// working on getRowHeader for matrix
//
// Revision 1.114  2002/06/07 16:44:06  joan
// working on column headers
//
// Revision 1.113  2002/06/05 22:31:36  joan
// fix error
//
// Revision 1.112  2002/06/05 22:18:20  joan
// work on put and rollback
//
// Revision 1.111  2002/06/05 16:28:49  joan
// add getMatrixValue method
//
// Revision 1.110  2002/05/30 22:49:53  joan
// throw MiddlewareBusinessRuleException when committing
//
// Revision 1.109  2002/05/29 22:24:15  joan
// adjust System.out.println
//
// Revision 1.108  2002/05/20 21:31:11  joan
// add setParentEntityItem
//
// Revision 1.107  2002/05/16 21:23:43  joan
// remove removeRow part in rollback
//
// Revision 1.106  2002/05/16 15:28:38  joan
// fix null pointer
//
// Revision 1.105  2002/05/15 23:19:02  joan
// fix null pointer
//
// Revision 1.104  2002/05/15 22:21:13  joan
// fix classcast
//
// Revision 1.103  2002/05/15 21:56:04  joan
// fix null pointer
//
// Revision 1.102  2002/05/15 21:42:22  joan
// working on rollback
//
// Revision 1.101  2002/05/15 21:15:11  joan
// add removeRow in rollback
//
// Revision 1.100  2002/05/15 18:30:47  joan
// debug rollback
//
// Revision 1.99  2002/05/15 17:55:06  joan
// debug rollback
//
// Revision 1.98  2002/05/15 17:28:43  joan
// fix removeRow
//
// Revision 1.97  2002/05/15 15:58:05  joan
// call removeRow in rollback method
//
// Revision 1.96  2002/05/14 17:47:06  joan
// working on LockActionItem
//
// Revision 1.95  2002/05/13 20:40:32  joan
// add resetLockGroup method
//
// Revision 1.94  2002/05/13 16:42:08  joan
// fixing unlock method
//
// Revision 1.93  2002/05/10 22:26:12  joan
// fix error
//
// Revision 1.92  2002/05/10 22:04:56  joan
// add hasLock method
//
// Revision 1.91  2002/05/10 21:06:20  joan
// compiling errors
//
// Revision 1.90  2002/05/10 20:45:55  joan
// fixing lock
//
// Revision 1.89  2002/05/08 19:56:41  dave
// attempting to throw the BusinessRuleException on Commit
//
// Revision 1.88  2002/04/25 00:00:52  joan
// working on getRowHeader
//
// Revision 1.87  2002/04/24 22:57:18  joan
// debug getRowHeader
//
// Revision 1.86  2002/04/24 21:09:29  joan
// removeRow
//
// Revision 1.85  2002/04/24 20:28:47  joan
// working on removeRow
//
// Revision 1.84  2002/04/24 18:04:38  joan
// add removeRow method
//
// Revision 1.83  2002/04/23 17:05:59  joan
// working on lock method
//
// Revision 1.82  2002/04/23 15:22:25  joan
// debug unlock
//
// Revision 1.81  2002/04/22 22:22:40  dave
// adding toString to RowSelectableTable
//
// Revision 1.80  2002/04/22 22:18:24  joan
// working on unlock
//
// Revision 1.79  2002/04/22 18:20:12  joan
// fixing compile errors
//
// Revision 1.78  2002/04/22 18:08:38  joan
// add unlock method
//
// Revision 1.77  2002/04/19 23:09:49  joan
// import profile
//
// Revision 1.76  2002/04/19 22:34:06  joan
// change isLocked interface to include profile as parameter
//
// Revision 1.75  2002/04/19 17:17:03  joan
// change isLocked  interface
//
// Revision 1.74  2002/04/18 23:48:34  joan
// working on isLocked
//
// Revision 1.73  2002/04/18 23:21:21  dave
// basic sketch for lock in RowSelectableTable
//
// Revision 1.72  2002/04/15 16:31:14  joan
// fixing exception
//
// Revision 1.71  2002/04/12 16:53:54  dave
// missed an update
//
// Revision 1.69  2002/04/12 16:42:05  dave
// added isLocked to the tableDef
//
// Revision 1.68  2002/04/11 19:18:25  dave
// more null pointerfixes
//
// Revision 1.67  2002/04/11 18:24:28  dave
// syntax
//
// Revision 1.66  2002/04/11 18:15:09  dave
// Trace statement adjustment and null pointer fix
//
// Revision 1.65  2002/04/09 18:30:07  joan
// syntax
//
// Revision 1.64  2002/04/09 18:22:04  joan
// working on business rule exception
//
// Revision 1.63  2002/04/09 17:05:52  joan
// working on put method
//
// Revision 1.62  2002/04/02 22:03:37  joan
// add a get method with boolean as parameter
//
// Revision 1.61  2002/04/02 21:25:50  dave
// added hasChanges()
//
// Revision 1.60  2002/04/02 17:48:33  dave
// added method isLongDescriptionEnabled
//
// Revision 1.59  2002/03/27 22:34:21  dave
// Row Selectable Table row Add logic. First attempt
//
// Revision 1.58  2002/03/23 01:42:54  dave
// syntax on commit
//
// Revision 1.57  2002/03/23 01:27:39  dave
// more fixes
//
// Revision 1.56  2002/03/22 22:14:30  dave
// more fixes
//
// Revision 1.55  2002/03/22 21:21:13  dave
// syntax
//
// Revision 1.54  2002/03/22 21:10:35  dave
// trace for addToStack
//
// Revision 1.53  2002/03/21 22:43:40  dave
// removing some displays
//
// Revision 1.52  2002/03/21 22:19:01  dave
// fix to isEditable
//
// Revision 1.51  2002/03/21 22:03:21  dave
// more trace statements
//
// Revision 1.50  2002/03/21 18:50:55  dave
// syntax fixes
//
// Revision 1.49  2002/03/21 18:38:57  dave
// added getHelp to the EANTableModel
//
// Revision 1.48  2002/03/21 01:42:37  dave
// syntax
//
// Revision 1.47  2002/03/21 01:30:32  dave
// more EANTable Model interface stuff
//
// Revision 1.46  2002/03/21 01:23:54  dave
// implementing the rowHeaderIndex
//
// Revision 1.45  2002/03/21 00:37:48  dave
// more syntax fixes
//
// Revision 1.44  2002/03/21 00:30:21  dave
// fixes to syntax on rollback function
//
// Revision 1.43  2002/03/21 00:22:57  dave
// adding rollback logic to the rowSelectable table
//
// Revision 1.42  2002/03/20 22:41:02  dave
// Syntax work
//
// Revision 1.41  2002/03/20 22:31:31  dave
// fix to put on RowSelectableTable.  Needed to bypass implicator
//
// Revision 1.40  2002/03/20 18:33:56  dave
// expanding the get for the EANAddressable to
// include a verbose boolean to dictate what gets sent back
//
// Revision 1.39  2002/03/20 18:04:46  dave
// removal of system.out.printlns
//
// Revision 1.38  2002/03/19 23:45:17  dave
// more work on vertical table
//
// Revision 1.37  2002/03/19 21:27:59  dave
// clarity on rowsasarray
//
// Revision 1.36  2002/03/19 20:50:04  dave
// syntax
//
// Revision 1.35  2002/03/19 20:42:36  dave
// adding trace to trace null pointer
//
// Revision 1.34  2002/03/19 19:05:35  dave
// syntax error fixes
//
// Revision 1.33  2002/03/19 18:57:04  dave
// hiding implicators
//
// Revision 1.32  2002/03/18 19:32:44  dave
// remove EANAddressable from external interface
//
// Revision 1.31  2002/03/14 17:15:19  dave
// fix
//
// Revision 1.30  2002/03/12 19:31:07  dave
// adding stuff for proper column header display
//
// Revision 1.29  2002/03/12 18:33:08  dave
// clean up on EANAddressable - removed the int indexes
// because they make no sense.
// Added standard put /get methods to the EANAttibute
//
// Revision 1.28  2002/03/12 01:09:05  dave
// ensuring implicators do not get out!
//
// Revision 1.27  2002/03/11 22:35:46  dave
// more syntax errors
//
// Revision 1.26  2002/03/11 20:56:16  dave
// mass changes for beginnings of edit
//
// Revision 1.25  2002/02/18 20:38:55  dave
// fix on method var name
//
// Revision 1.24  2002/02/18 19:43:28  dave
// added a refresh method to the rowSelectable Table
//
// Revision 1.23  2002/02/18 17:39:45  dave
// more fixes
//
// Revision 1.22  2002/02/18 17:25:23  dave
// more function add for 1.1
//
// Revision 1.21  2002/02/15 22:48:49  dave
// more fixes
//
// Revision 1.20  2002/02/15 22:41:17  dave
// fix for syntax
//
// Revision 1.19  2002/02/15 22:34:24  dave
// syntax
//
// Revision 1.18  2002/02/15 22:24:35  dave
// added row methods
//
// Revision 1.17  2002/02/15 18:18:32  dave
// more fixes for EAN  table structrure
//
// Revision 1.16  2002/02/15 18:06:52  dave
// expanded EAN Table structures
//
// Revision 1.15  2002/02/14 23:14:10  dave
// fixed missing paren
//
// Revision 1.14  2002/02/14 22:46:15  dave
// minor sytax
//
// Revision 1.13  2002/02/14 22:45:19  dave
// added getActiveRow.. setActiveRow for rowselectabletable
//
// Revision 1.12  2002/02/14 01:44:25  dave
// more syntax fixes
//
// Revision 1.11  2002/02/14 01:21:17  dave
// created a binder for action groups so relator/entity can be shown
//
// Revision 1.10  2002/02/13 22:56:13  dave
// uncommented out table fix
//
// Revision 1.9  2002/02/13 22:48:00  dave
// more rearranging in the abstract layer
//
// Revision 1.8  2002/02/13 21:58:21  dave
// more table stuff
//
// Revision 1.7  2001/08/09 23:21:35  dave
// syntax fix for EANAddressable change
//
// Revision 1.6  2001/08/09 23:16:08  dave
// changed EANAddressable interface to use String keys
// as opposed to integers
//
// Revision 1.5  2001/08/09 18:59:13  dave
// modifications regarding getColumnClass
//
// Revision 1.4  2001/08/09 18:40:04  dave
// compile challenged
//
// Revision 1.3  2001/08/09 18:36:19  dave
// clean up for compile
//
// Revision 1.2  2001/08/09 18:30:04  dave
// syntax
//
// Revision 1.1  2001/08/09 18:05:32  dave
// new classes to support table interface
//
//

package COM.ibm.eannounce.objects;

import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.Serializable;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.Profile;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.LockException;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;


/**
* This is an adapter class that representes a basic table interface
* It will eventually become a general table interface for EAN
*
* @author David Bigelow
* @version @date
*/
public final class RowSelectableTable implements Serializable, EANTableModel {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private EANList m_elc = null;
    private EANList m_elr = null;
    private String m_strTableTitle = null;
    private EANTableWrapper m_tw = null;
    private int m_iActiveRow = 0;
    private boolean m_bLong = false;
    private FilterGroup m_filterGroup = null;
    private boolean m_bHasFilteredRows = false;
    private int m_iRowHeaderIndex = -1;
    private boolean m_bUseFilter = true;

    private FilterGroup m_colFilterGroup = null;
    private boolean m_bHasFilteredCols = false;

    /**
     * release memory
     */
    public void dereference(){
    	m_elc = null;
        m_elr = null;
        m_strTableTitle = null;
        m_tw = null;
        m_filterGroup = null;
        m_colFilterGroup = null;
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /* Constructs a new Matrix Object from a given Navigate Object
    */
    /**
     * RowSelectableTable
     *
     * @param _tw
     * @param _s1
     *  @author David Bigelow
     */
    public RowSelectableTable(EANTableWrapper _tw, String _s1) {
        m_tw = _tw;
        setTableTitle(_s1);
        D.ebug("*** NEW RST HAS BEEN CALLED ON " + getTableTitle() + " *** SOURCE IS: " + (m_tw == null ? " *null* " : m_tw.getClass().getName()));
//        if (m_tw instanceof EANFoundation) {
//	        D.ebug("***  with key of : " + ((EANFoundation)m_tw).getKey() + "  ***");
//		}
        m_elc = _tw.getColumnList();
        m_elr = _tw.getRowList();

    }

    /**
     * provide access to table wrapper
     * @return
     */
    public EANTableWrapper getEANTableWrapper(){
    	return m_tw;
    }
    /**
     * (non-Javadoc)
     * getTableTitle
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getTableTitle()
     */
    public String getTableTitle() {
        return m_strTableTitle;
    }

    /**
     * (non-Javadoc)
     * setTableTitle
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#setTableTitle(java.lang.String)
     */
    public void setTableTitle(String _s) {
        m_strTableTitle = _s;
    }

    /**
     * getColumnHeader
     *
     * @param _mf
     * @return
     *  @author David Bigelow
     */
    public String getColumnHeader(EANFoundation _mf) {
        // Get the Parent here...
        String colHeader = null;

        if (_mf instanceof Implicator) {
            _mf = _mf.getParent();
        }

        if (m_bLong) {
            colHeader = _mf.getLongDescription();
            if (colHeader.equals("TBD")) {
                colHeader = _mf.getKey();
            }
        } else {
            colHeader = _mf.getShortDescription();
            if (colHeader.equals("TBD")) {
                colHeader = _mf.getKey();
            }
        }

        if (colHeader.length() > 0 && colHeader.charAt(0) == '*' && isDynaTable()) {
            return colHeader.substring(1);
        }
        return colHeader;
    }

    /**
     * (non-Javadoc)
     * getColumnHeader
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getColumnHeader(int)
     */
    public String getColumnHeader(int _c) {

        EANFoundation mf = null;

        if (_c > m_elc.size()) {
            return "";
        }

        mf = (EANFoundation) m_elc.getAt(_c);
        if (mf == null) {
            System.out.println("*** getColumnHeader Cannot locate Column in get:" + _c);
            return null;
        }
        return getColumnHeader(mf);
    }

    /**
     * (non-Javadoc)
     * setRowHeaderIndex
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#setRowHeaderIndex(int)
     */
    public void setRowHeaderIndex(int _ic) {
        m_iRowHeaderIndex = _ic;
    }

    /**
     * (non-Javadoc)
     * getColumnCount
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getColumnCount()
     */
    public int getColumnCount() {
        return m_elc.size();
    }

    // Returns Row Stuff
    /**
     * (non-Javadoc)
     * getRowHeader
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getRowHeader(int)
     */
    public String getRowHeader(int _r) {

        EANFoundation mf = null;
        EANFoundation mfRow = null;
        String strAnswer = null;

        if (m_iRowHeaderIndex < 0 || m_iRowHeaderIndex >= getColumnCount()) {
            return "";
        }

        mf = (EANFoundation) m_elc.getAt(m_iRowHeaderIndex);
        mfRow = (EANFoundation) m_elr.getAt(_r);
        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
        }

        strAnswer = (String) ((EANAddressable) mfRow).get(mf.getKey(), m_bLong);
        if (strAnswer.length() > 0 && strAnswer.charAt(0) == '*' && isDynaTable()) {
            return strAnswer.substring(1);
        }
        return strAnswer;

    }

    /**
     * (non-Javadoc)
     * getRowCount
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getRowCount()
     */
    public int getRowCount() {
        return m_elr.size();
    }

    /*
    * Returns the object behind the row
    */
    /**
     * (non-Javadoc)
     * getRow
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getRow(int)
     */
    public EANFoundation getRow(int _i) {
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_i);
        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
        }
        return mfRow;
    }

    /*
    * Returns the object behind the row
    */
    /**
     * (non-Javadoc)
     * getRow
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getRow(java.lang.String)
     */
    public EANFoundation getRow(String _str) {
        EANFoundation mfRow = (EANFoundation) m_elr.get(_str);
        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
        }

        return mfRow;

    }

    /**
     * (non-Javadoc)
     * getColumnClass
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getColumnClass(int)
     */
    public Class getColumnClass(int _c) {
        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        if (mf == null) {
            System.out.println("*** getColumnClass Cannot locate Column in get COL:" + _c);
            return null;
        }

        if (mf instanceof Implicator) {
            mf = mf.getParent();
        }
        return mf.getTargetClass();
    }

    /**
     * (non-Javadoc)
     * getRowClass
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getRowClass(int)
     */
    public Class getRowClass(int _r) {
        EANFoundation mf = (EANFoundation) m_elr.getAt(_r);
        if (mf == null) {
            System.out.println("*** getRowClass Cannot locate row in get:" + _r);
            return null;
        }

        if (mf instanceof Implicator) {
            mf = mf.getParent();
        }
        return mf.getTargetClass();
    }

    /**
     * (non-Javadoc)
     * isEditable
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#isEditable(int, int)
     */
    public boolean isEditable(int _r, int _c) {
    	boolean editable = false;
        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

        if (mf == null) {
            System.out.println("*** isEditable Cannot locate Column in get:" + _r + ":COL:" + _c);
            return false;
        }
        if (mfRow == null) {
            System.out.println("*** isEditable Cannot locate row in get:" + _r + ":COL:" + _c);
            return false;
        }
    	if (m_tw instanceof MatrixList || m_tw instanceof MatrixGroup) {
    		// must be able to lock editable matrix cell
    		Object objValue = getMatrixValue(_r, _c);
    		if (objValue instanceof EANAddressable){
    			editable = ((EANAddressable) objValue).isEditable(mf.getKey());
    		}
    	} else{
	        if (mfRow instanceof Implicator) {
	            mfRow = mfRow.getParent();
	            if (mfRow == null) {
    				System.out.println("*** isEditable Cannot locate Parent of Implicator:" + _r + ":COL:" + _c);
    				return false;
    			}
	        }
	        editable = ((EANAddressable) mfRow).isEditable(mf.getKey());
    	}
       
        return editable;
    }

    /**
     * (non-Javadoc)
     * hasLock
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#hasLock(int, int, COM.ibm.eannounce.objects.EntityItem, COM.ibm.opicmpdh.middleware.Profile)
     */
    public boolean hasLock(int _r, int _c, EntityItem _lockOwnerEI, Profile _prof) {

    	boolean locked =false;
    	EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
    	EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

    	if (mf == null) {
    		System.out.println("*** hasLock Cannot locate Column in get:" + _r + ":COL:" + _c);
    		return false;
    	}

    	if (mfRow == null) {
    		System.out.println("*** hasLock Cannot locate row in get:" + _r + ":COL:" + _c);
    		return false;
    	}
    	if (m_tw instanceof MatrixList || m_tw instanceof MatrixGroup) {
    		// must be able to lock editable matrix cell
    		Object objValue = getMatrixValue(_r, _c);
    		if (objValue instanceof EANAddressable){
    			locked = ((EANAddressable) objValue).hasLock(mf.getKey(), _lockOwnerEI, _prof);
    		}
    	} else{
    		if (mfRow instanceof Implicator) {
    			mfRow = mfRow.getParent();
    			if (mfRow == null) {
    				System.out.println("*** hasLock Cannot locate Parent of Implicator:" + _r + ":COL:" + _c);
    				return false;
    			}
    		}
    		locked = ((EANAddressable) mfRow).hasLock(mf.getKey(), _lockOwnerEI, _prof);
    	}

        return locked;
    }

    /**
     * (non-Javadoc)
     * isLocked
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#isLocked(int, int, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int, java.lang.String, boolean)
     */
    public boolean isLocked(int _r, int _c, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
    	boolean locked =false;
    	EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
    	EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);
		if (mf == null) {
			System.out.println("*** isLocked Cannot locate Column in get:" + _r + ":COL:" + _c);
			return false;
		}

		if (mfRow == null) {
			System.out.println("*** isLocked Cannot locate row in get:" + _r + ":COL:" + _c);
			return false;
		}
		
    	if (m_tw instanceof MatrixList || m_tw instanceof MatrixGroup) {
    		// must be able to lock editable matrix cell
    		Object objValue = getMatrixValue(_r, _c);
    		if (objValue instanceof EANAddressable){
    			locked = ((EANAddressable) objValue).isLocked(mf.getKey(), _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, _strTime, _bCreateLock);
    		}
    	} else{
    		if (mfRow instanceof Implicator) {
    			mfRow = mfRow.getParent();
    			if (mfRow == null) {
    				System.out.println("*** isLocked Cannot locate Parent of Implicator:" + _r + ":COL:" + _c);
    				return false;
    			}
    		}

    		locked = ((EANAddressable) mfRow).isLocked(mf.getKey(), _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, _strTime, _bCreateLock);
    	}

    	return locked;
    }

    /**
     * lockEntityItems
     *
     * @param _ar
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     * @param _bCreateLock
     * @return
     *  @author David Bigelow
     */
    public boolean lockEntityItems(int[] _ar, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, boolean _bCreateLock) {
        EANList eiList = new EANList();
        for (int i = 0; i < _ar.length; i++) {
            int r = _ar[i];
            EANFoundation mfRow = (EANFoundation) m_elr.getAt(r);
            if (mfRow == null) {
                System.out.println("*** isLocked Cannot locate row in get:" + r);
                continue;
            }

            if (mfRow instanceof Implicator) {
                mfRow = mfRow.getParent();
                if (mfRow == null) {
                    System.out.println("*** lockEntityItems Cannot locate Parent of Implicator:" + r);
                    continue;
                }
            }

            if (mfRow instanceof EntityItem) {
                EntityGroup eg = null;
                EntityList entl = null;

                EntityItem ei = (EntityItem) mfRow;
				if (ei instanceof VEEditItem){
					VEEditItem vei = (VEEditItem)ei;
					EntityItem[] items = vei.getEditableItems();
					if (items != null) {
						for (int x=0;x<items.length;++x) {
							if (!items[x].hasLock(_lockOwnerEI, _prof)) {
								eiList.put(items[x]);
							}
						}
					}
				}else {
					if (!ei.hasLock(_lockOwnerEI, _prof)) {
						eiList.put(ei);
					}
					eg = ei.getEntityGroup();
					entl = eg.getEntityList();
					if (eg.isRelator()) {
						EntityItem eic = (EntityItem) ei.getDownLink(0);
						if (entl != null && entl.isCreateParent()) {
							eic = (EntityItem) ei.getUpLink(0);
						}
						if (eic != null && !eic.hasLock(_lockOwnerEI, _prof)) {
							eiList.put(eic);
						}
					}
				}
            }
        }

        if (eiList.size() > 0) {
            LockGroup[] alg = null;
            EntityItem[] aei = new EntityItem[eiList.size()];
            //eiList.copyTo(aei); this sends entire entity and attributes over the network
            for (int j = 0; j < eiList.size(); j++) {
            	EntityItem ei = (EntityItem)eiList.getAt(j);
            	try {
            		// make a smaller copy
					aei[j]=new EntityItem(null, ei.getProfile(), ei.getEntityType(), ei.getEntityID());
				} catch (MiddlewareRequestException e) {
					e.printStackTrace();
					return false;
				}
            }
    
            try {
                if (_rdi != null) {
                    alg = _rdi.getLockGroups(_prof, _lockOwnerEI, aei, _lockOwnerEI.getKey(), _iLockType, _bCreateLock);
                } else {
                    alg = _db.getLockGroups(_prof, _lockOwnerEI, aei, _lockOwnerEI.getKey(), _iLockType, _bCreateLock);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
 
            for (int i = 0; i < alg.length; i++) {
                _ll.putLockGroup(alg[i]);
                // set lock group on original entity items
                EntityItem ei = (EntityItem)eiList.get(alg[i].getKey());
                ei.setLockGroup(alg[i]);
                ei.refreshRestrictions();
                ei.refreshRequired();
                ei.refreshClassifications();
                ei.refreshDefaults();
            }
 
            return true;
        } else {
            System.out.println("*** lockEntityItems there's no entity item to lock");
            return false;
        }
    }

    /**
     * unlockEntityItems
     *
     * @param _ar
     * @param _rdi
     * @param _db
     * @param _ll
     * @param _prof
     * @param _lockOwnerEI
     * @param _iLockType
     *  @author David Bigelow
     */
    public void unlockEntityItems(int[] _ar, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        EANList eiList = new EANList();
        for (int i = 0; i < _ar.length; i++) {
            int r = _ar[i];
            EANFoundation mfRow = (EANFoundation) m_elr.getAt(r);
            if (mfRow == null) {
                System.out.println("*** unlockEntityItems Cannot locate row in get:" + r);
                continue;
            }

            if (mfRow instanceof Implicator) {
                mfRow = mfRow.getParent();
                if (mfRow == null) {
                    System.out.println("*** unlockEntityItems Cannot locate Parent of Implicator:" + r);
                    continue;
                }
            }

            if (mfRow instanceof EntityItem) {
                EntityItem ei = (EntityItem) mfRow;
                EntityGroup eg = ei.getEntityGroup();
				if (ei instanceof VEEditItem){
					VEEditItem vei = (VEEditItem)ei;
					EntityItem[] items = vei.getEditableItems();
					if (items != null) {
						for (int x=0;x<items.length;++x) {
							eiList.put(items[x]);
						}
					}
				}else{
					eiList.put(ei);
					if (eg.isRelator()) {
						EntityList entl = eg.getEntityList();
						EntityItem eic = (EntityItem) ei.getDownLink(0);
						if (entl != null && entl.isCreateParent()) {
							eic = (EntityItem) ei.getUpLink(0);
						}
						if (eic != null){
							eiList.put(eic);
						}
					}
				}
            }
        }

        if (eiList.size() > 0) {
            EntityItem[] aei = new EntityItem[eiList.size()];
            //eiList.copyTo(aei); this sends entire entity and attributes over the network
            for (int j = 0; j < eiList.size(); j++) {
            	EntityItem ei = (EntityItem)eiList.getAt(j);
            	try {
            		// make a smaller copy
					aei[j]=new EntityItem(null, ei.getProfile(), ei.getEntityType(), ei.getEntityID());
				} catch (MiddlewareRequestException e) {
					e.printStackTrace();
				}
            }
            try {
                if (_rdi != null) {
                    _rdi.clearLocks(_prof, aei, _lockOwnerEI.getEntityType(), _lockOwnerEI.getEntityID(), _lockOwnerEI.getKey(), _iLockType);
                } else {
                    _db.clearLocks(_prof, aei, _lockOwnerEI.getEntityType(), _lockOwnerEI.getEntityID(), _lockOwnerEI.getKey(), _iLockType);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i < aei.length; i++) {
              //  EntityItem ei = aei[i];
                // clear lock group on original entity items
                EntityItem ei = (EntityItem)eiList.get(aei[i].getKey());
                _ll.removeLockGroup(ei.getEntityType() + ei.getEntityID());
                ei.setLockGroup(null);
            }
        }
    }

    /**
     * (non-Javadoc)
     * get
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#get(int, int)
     */
    public Object get(int _r, int _c) {

        Object o = null;

        // here .. we do not convert the thing from an implicator
        // because the key of the implicator drives the get function
        // of the EANAddressable Object

        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);
        if (mf == null) {
            System.out.println("*** get #1 Cannot locate Column in get:" + _r + ":COL:" + _c);
            return null;
        }
        if (mfRow == null) {
            System.out.println("*** get #1 Cannot locate row in get:" + _r + ":COL:" + _c);
            return null;
        }

        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
            if (mfRow == null) {
                System.out.println("*** get #1 Cannot locate Parent of Implicator in get:" + _r + ":COL:" + _c);
                return null;
            }
        }

//		System.out.println("RowSelectableTable.get()");
//		System.out.println("    mfrow: " + mfRow.getClass().getName());
//		System.out.println("    mf: " + mf.getClass().getName());
//		System.out.println("    mf.key: " + mf.getKey());
        o = ((EANAddressable) mfRow).get(mf.getKey(), m_bLong);
        if (o instanceof String) {
            String strAnswer = (String) o;
            if (strAnswer.length() > 0 && strAnswer.charAt(0) == '*' && isDynaTable() && _c == 0) {
                return strAnswer.substring(1);
            }
            return strAnswer;
        } else {
            return o;
        }

    }

    /**
     * get
     *
     * @param _r
     * @param _c
     * @param _b
     * @return
     *  @author David Bigelow
     */
    public Object get(int _r, int _c, boolean _b) {

        // here .. we do not convert the thing from an implicator
        // because the key of the implicator drives the get function
        // of the EANAddressable Object

        Object o = null;

        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

        if (mf == null) {
            System.out.println("*** get #2 Cannot locate Column in get:" + _r + ":COL:" + _c);
            return null;
        }

        if (mfRow == null) {
            System.out.println("*** get #2 Cannot find object for get ROW:" + _r + ":COL:" + _c);
            return null;
        }

        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
            if (mfRow == null) {
                System.out.println("*** get #2 Implicator has no parent:" + _r + ":COL:" + _c);
                return null;
            }
        }

        o = ((EANAddressable) mfRow).get(mf.getKey(), _b);
  
        if (o instanceof String) {
            String strAnswer = (String) o;
            // if there is an asterek, and it is a dynatable .. and they are asking
            // about column 0
            if (strAnswer.length() > 0 && strAnswer.charAt(0) == '*' && isDynaTable() && _c == 0) {
                return strAnswer.substring(1);
            }
            return strAnswer;
        } else {
            return o;
        }
    }

    /*
    *  This creates an object if non exists and is used by the client
    *  to gain access to the object for possible editing.
    */
    /**
     * (non-Javadoc)
     * getEANObject
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getEANObject(int, int)
     */
    public EANFoundation getEANObject(int _r, int _c) {

        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

        if (mf == null) {
            System.out.println("*** getEANObject Cannot locate Column in get:" + _r + ":COL:" + _c);
            return null;
        }

        if (mfRow == null) {
            System.out.println("*** getEANObject Cannot locate row in get:" + _r + ":COL:" + _c);
            return null;
        }

        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
        }

        // here .. we do not convert the thing from an implicator
        // because the key of the implicator drives the get function
        // of the EANAddressable Object

        return ((EANAddressable) mfRow).getEANObject(mf.getKey());

    }

    /*
    * Get row via key..
    * get column via int
    */
    /**
     * (non-Javadoc)
     * getRowIndex
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getRowIndex(java.lang.String)
     */
    public int getRowIndex(String _str) {
        return m_elr.indexOf(_str);
    }

    /**
     * (non-Javadoc)
     * getColumnIndex
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getColumnIndex(java.lang.String)
     */
    public int getColumnIndex(String _str) {
        return m_elc.indexOf(_str);
    }

    /**
     * (non-Javadoc)
     * getRowKey
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getRowKey(int)
     */
    public String getRowKey(int _i) {
        EANFoundation ef = (EANFoundation) m_elr.getAt(_i);
        if (ef == null) {
            System.out.println("RowSelectableTable.getRowKey(" + _i + ") is not a valid row");
            return null;
        }

        return ef.getKey();
    }

    /**
     * getColKey
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getColKey(int _i) {
        EANFoundation ef = (EANFoundation) m_elc.getAt(_i);
        if (ef == null) {
            System.out.println("RowSelectableTable.getColKey(" + _i + ") is not a valid row");
            return null;
        }

        return ef.getKey();
    }

    /*
    * Get key of column at the position
    */
    /**
     * (non-Javadoc)
     * getColumnKey
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getColumnKey(int)
     */
    public String getColumnKey(int _i) {
        EANFoundation ef = (EANFoundation) m_elc.getAt(_i);
        if (ef == null) {
            System.out.println("RowSelectableTable.getColumnKey(" + _i + ") is not a valid column");
            return null;
        }
        return ef.getKey();
    }

    /*
    * Make sure you hide any implicators from the
    * Table user
    */
    /**
     * (non-Javadoc)
     * getColumn
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getColumn(int)
     */
    public EANFoundation getColumn(int _i) {
        EANFoundation ef = (EANFoundation) m_elc.getAt(_i);
        if (ef == null) {
            return null;
        }
        if (ef instanceof Implicator) {
            return ef.getParent();
        }
        return ef;
    }

    /*
    * Make sure you hide any implicators from the
    * Table user
    * They will want to deal the object the implicator holds
    * in this case
    */
    /**
     * (non-Javadoc)
     * getColumn
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getColumn(java.lang.String)
     */
    public EANFoundation getColumn(String _str) {
        EANFoundation ef = (EANFoundation) m_elc.get(_str);
        if (ef == null) {
            return null;
        }
        if (ef instanceof Implicator) {
            return ef.getParent();
        }
        return ef;
    }

    /*
    * This ensures that the user never see implicators
    */
    /**
     * (non-Javadoc)
     * getNativeRowsAsArray
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getNativeRowsAsArray()
     */
    public EANFoundation[] getNativeRowsAsArray() {
        EANFoundation[] af = new EANFoundation[m_elr.size()];
        for (int ii = 0; ii < m_elr.size(); ii++) {
            EANFoundation mf = (EANFoundation) m_elr.getAt(ii);
            if (mf instanceof Implicator) {
                mf = mf.getParent();
            }
            af[ii] = mf;
        }
        return af;
    }

    /*
    * This one leaves implicators alone
    */
    /**
     * (non-Javadoc)
     * getTableRowsAsArray
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getTableRowsAsArray()
     */
    public EANFoundation[] getTableRowsAsArray() {
        EANFoundation[] af = new EANFoundation[m_elr.size()];
        for (int ii = 0; ii < m_elr.size(); ii++) {
            EANFoundation mf = (EANFoundation) m_elr.getAt(ii);
            //if (mf instanceof Implicator) {
            //  mf = mf.getParent();
            //}
            af[ii] = mf;
        }
        return af;
    }

    // basic put back to the table
    /**
     * (non-Javadoc)
     * put
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#put(int, int, java.lang.Object)
     */
    public void put(int _r, int _c, Object _o) throws EANBusinessRuleException {
//		if (_o == null) {
//			System.out.println("SCBBCS rst.put(" + _r + ", " + _c + ", null)");
//		} else {
//			System.out.println("SCBBCS rst.put(" + _r + ", " + _c + ", " + _o.toString() + ")");
//		}
        // here .. we do not convert the thing from an implicator
        // because the key of the implicator drives the put function
        // of the EANAddressable Object
        // if (!isEditable(_r,_c)) return;
        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);
        if (mf == null) {
            System.out.println("*** put Cannot locate Column in get:" + _r + ":COL:" + _c);
            return;
        }

        if (mfRow == null) {
            System.out.println("*** put Cannot locate row in get:" + _r + ":COL:" + _c);
            return;
        }
        // Need to bypass the Implicator here..
        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
        }

//		System.out.println("SCBBCS put mfRow: " + mfRow.getKey());
//		System.out.println("SCBBCS put mfRow class: " + mfRow.getClass().getName());
//		System.out.println("SCBBCS put ms   : " + mf.getKey());
//		System.out.println("SCBBCS put ms class   : " + mf.getClass().getName());
//		if (_o == null) {
//			System.out.println("SCBBCS object is null");
//		} else {
//			System.out.println("SCBBCS object class   : " + _o.getClass().getName());
//		}

        ((EANAddressable) mfRow).put(mf.getKey(), _o);
    }

    // basic rollback of a cell
    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#rollback(int, int)
     */
    public void rollback(int _r, int _c) {
        // here .. we do not convert the thing from an implicator
        // because the key of the implicator drives the put function
        // of the EANAddressable Object
        // if (!isEditable(_r,_c)) return;
        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

        // Need to bypass the Implicator here..
        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
        }

        // Perform the rollback...

        ((EANAddressable) mfRow).rollback(mf.getKey());

    }

    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#rollback(int)
     */
    public void rollback(int _r) {
        for (int ic = 0; ic < getColumnCount(); ic++) {
            rollback(_r, ic);
        }
    }

    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#rollback()
     */
    public void rollback() {
        for (int ir = 0; ir < getRowCount(); ir++) {
            for (int ic = 0; ic < getColumnCount(); ic++) {
                rollback(ir, ic);
            }
        }
    }

    /*
    * Commit stuff
    */
    /**
     * (non-Javadoc)
     * commit
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#commit(COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void commit(RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        m_tw.commit(null, _rdi);
    }

    /**
     * (non-Javadoc)
     * commit
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#commit(COM.ibm.opicmpdh.middleware.Database)
     */
    public void commit(Database _db) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
//		System.out.println("SCBBCS commit on: " + m_tw.getClass().getName());
		m_tw.commit(_db, null);
    }

    /**
     * (non-Javadoc)
     * getActiveRow
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getActiveRow()
     */
    public int getActiveRow() {
        return m_iActiveRow;
    }

    /**
     * (non-Javadoc)
     * setActiveRow
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#setActiveRow(int)
     */
    public void setActiveRow(int _i) {
        m_iActiveRow = _i;
    }

    /**
     * (non-Javadoc)
     * refresh
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#refresh()
     */
    public void refresh() {
//		System.out.println("    refreshing on: " + m_tw.getClass().getName());
//		if (m_tw instanceof EANFoundation) {
//			System.out.println("        key: " + ((EANFoundation)m_tw).getKey());
//		}
        // GAB 1/18/03 -- make an explicit copy of these things b/c filter will modify the rows!! (DONT want to modify pointer to 'real' columns)
        m_elc = copyList(m_tw.getColumnList());
        m_elr = copyList(m_tw.getRowList());

        // filter logic - GAB 1/17/03
        m_bHasFilteredRows = false;
        // we can make the filter transparent -- b/c there are cases where we want to bypass filter w/out destroying it
        if (hasFilterGroup() && useFilter()) {
            applyFilter();
        }
        // end filter logic

        // filter logic - GAB 2/01/05
        m_bHasFilteredCols = false;
        // we can make the filter transparent -- b/c there are cases where we want to bypass filter w/out destroying it
        if (hasColFilterGroup() && useFilter()) {
            applyColumnFilter();
        }
        // end filter logic

        if (m_iActiveRow >= m_elr.size()) {
            m_iActiveRow = m_elr.size() - 1;
        }
    }

    /**
     * setLongDescription
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setLongDescription(boolean _b) {
        m_bLong = _b;
    }

    /**
     * isLongDescriptionEnabled
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isLongDescriptionEnabled() {
        return m_bLong;
    }

    /**
     * (non-Javadoc)
     * getHelp
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getHelp(int, int)
     */
    public String getHelp(int _ir, int _ic) {

        EANFoundation mf = (EANFoundation) m_elc.getAt(_ic);

        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_ir);
        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
        }

        // here .. we do not convert the thing from an implicator
        // because the key of the implicator drives the get function
        // of the EANAddressable Object

        return ((EANAddressable) mfRow).getHelp(mf.getKey());
    }

    /**
     * (non-Javadoc)
     * canEdit
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#canEdit()
     */
    public boolean canEdit() {
        return m_tw.canEdit();
    }

    /**
     * (non-Javadoc)
     * canCreate
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#canCreate()
     */
    public boolean canCreate() {
        return m_tw.canCreate();
    }

    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#addRow()
     */
    public boolean addRow() {
        if (!canCreate()) {
            return false;
        }

        if (m_tw.addRow()) {
            refresh();
            return true;
        }

        return false;

    }

    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#addRow(String)
     */
    public boolean addRow(String _strKey) {
        if (!canCreate()) {
            return false;
        }
        if (m_tw.addRow(_strKey)) {
            refresh();
            return true;
        }
        return false;
    }

    /**
     * (non-Javadoc)
     * removeRow
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#removeRow(int)
     */
    public void removeRow(int r) {
        if (!canCreate()) {
            return;
        }

        // EntityItem-specific remove
        if (getRow(r) instanceof EntityItem) {
            EntityItem ei = (EntityItem) getRow(r);
            if (ei.isNew()) {
                rollback(r);
                m_tw.removeRow(ei.getKey());
                refresh();
			}
        } else {
            m_tw.removeRow(getRow(r).getKey());
        }
    }

    /**
     * (non-Javadoc)
     * hasChanges
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#hasChanges()
     */
    public boolean hasChanges() {
        return m_tw.hasChanges();
    }

    /**
     * (non-Javadoc)
     * lock
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#lock(int, int, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int, java.lang.String)
     */
    public void lock(int _r, int _c, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime) {

        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

        if (mf == null) {
            System.out.println("*** lock Cannot locate Column in get:" + _r + ":COL:" + _c);
            return;
        }

        if (mfRow == null) {
            System.out.println("*** lock Cannot locate row in get:" + _r + ":COL:" + _c);
            return;
        }

        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
            if (mfRow == null) {
                System.out.println("*** lock Cannot locate Parent of Implicator:" + _r + ":COL:" + _c);
                return;
            }
        }
        ((EANAddressable) mfRow).isLocked(mf.getKey(), _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, _strTime, true);
    }

    /**
     * (non-Javadoc)
     * lock
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#lock(int, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void lock(int _r, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strTime = sdf.format(new Date());
        for (int ic = 0; ic < getColumnCount(); ic++) {
            lock(_r, ic, _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, strTime);
        }
    }

    /**
     * (non-Javadoc)
     * lock
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#lock(COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void lock(RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String strTime = sdf.format(new Date());
        for (int ir = 0; ir < getRowCount(); ir++) {
            for (int ic = 0; ic < getColumnCount(); ic++) {
                lock(ir, ic, _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, strTime);
            }
        }
    }

    /**
     * (non-Javadoc)
     * unlock
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#unlock(int, int, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void unlock(int _r, int _c, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

        if (mf == null) {
        	System.out.println("*** unlock Cannot locate Column in unlock:" + _r + ":COL:" + _c);
        	return;
        }

        if (mfRow == null) {
        	System.out.println("*** unlock Cannot locate row in unlock:" + _r + ":COL:" + _c);
        	return;
        }
        if (m_tw instanceof MatrixList || m_tw instanceof MatrixGroup) {
        	// must be able to unlock editable matrix cell
        	Object objValue = getMatrixValue(_r, _c);
        	if (objValue instanceof EANAddressable){
        		((EANAddressable) objValue).unlock(mf.getKey(), _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType);
        	}
        } else{
        	if (mfRow instanceof Implicator) {
        		mfRow = mfRow.getParent();
        		if (mfRow == null) {
        			System.out.println("*** unlock Cannot locate Parent of Implicator:" + _r + ":COL:" + _c);
        			return;
        		}
        	}

        	((EANAddressable) mfRow).unlock(mf.getKey(), _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType);
        }
    }

    /**
     * (non-Javadoc)
     * unlock
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#unlock(int, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void unlock(int _r, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        for (int ic = 0; ic < getColumnCount(); ic++) {
            unlock(_r, ic, _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType);
        }
    }

    /**
     * (non-Javadoc)
     * unlock
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#unlock(COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Database, COM.ibm.eannounce.objects.LockList, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem, int)
     */
    public void unlock(RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
        for (int ir = 0; ir < getRowCount(); ir++) {
            for (int ic = 0; ic < getColumnCount(); ic++) {
                unlock(ir, ic, _rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType);
            }
        }
    }

    /**
     * (non-Javadoc)
     * resetLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#resetLockGroup(int, int, COM.ibm.eannounce.objects.LockList)
     */
    public void resetLockGroup(int _r, int _c, LockList _ll) {

        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

        if (mf == null) {
            System.out.println("*** resetLockGroup Cannot locate Column in get:" + _r + ":COL:" + _c);
            return;
        }

        if (mfRow == null) {
            System.out.println("*** resetLockGroup Cannot locate row in get:" + _r + ":COL:" + _c);
            return;
        }

        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
            if (mfRow == null) {
                System.out.println("*** resetLockGroup Cannot locate Parent of Implicator:" + _r + ":COL:" + _c);
                return;
            }
        }

        ((EANAddressable) mfRow).resetLockGroup(mf.getKey(), _ll);
    }

    /**
     * (non-Javadoc)
     * resetLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#resetLockGroup(int, COM.ibm.eannounce.objects.LockList)
     */
    public void resetLockGroup(int _r, LockList _ll) {
        for (int ic = 0; ic < getColumnCount(); ic++) {
            resetLockGroup(_r, ic, _ll);
        }
    }

    /**
     * (non-Javadoc)
     * resetLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#resetLockGroup(COM.ibm.eannounce.objects.LockList)
     */
    public void resetLockGroup(LockList _ll) {
        for (int ir = 0; ir < getRowCount(); ir++) {
            for (int ic = 0; ic < getColumnCount(); ic++) {
                resetLockGroup(ir, ic, _ll);
            }
        }
    }
    /**
     * (non-Javadoc)
     * setParentEntityItem
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#setParentEntityItem(int, COM.ibm.eannounce.objects.EntityItem)
     */
    public void setParentEntityItem(int _r, EntityItem _ei) {
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);
        if (mfRow == null) {
            System.out.println("*** setParentEntityItem Cannot locate row in get:" + _r);
            return;
        }

        ((EANAddressable) mfRow).setParentEntityItem(_ei);
    }

    /**
     * (non-Javadoc)
     * setParentEntityItem
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#setParentEntityItem(COM.ibm.eannounce.objects.EntityItem)
     */
    public void setParentEntityItem(EntityItem _ei) {
        for (int ir = 0; ir < getRowCount(); ir++) {
            setParentEntityItem(ir, _ei);
        }
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getTableTitle();
    }

    /**
     * (non-Javadoc)
     * getMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getMatrixValue(int, int)
     */
    public Object getMatrixValue(int _r, int _c) {
        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

        if (mf == null) {
            System.out.println("*** get #1 Cannot locate Column in getMatrixValue:" + _r + ":COL:" + _c);
            return null;
        }
        if (mfRow == null) {
            System.out.println("*** get #1 Cannot locate row in getMatrixValue:" + _r + ":COL:" + _c);
            return null;
        }

        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
            if (mfRow == null) {
                System.out.println("*** get #1 Cannot locate Parent of Implicator in getMatrixValue:" + _r + ":COL:" + _c);
                return null;
            }
        }
        return m_tw.getMatrixValue(mfRow.getKey() + ":" + mf.getKey());
    }

    /**
     * (non-Javadoc)
     * putMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#putMatrixValue(int, int, java.lang.Object)
     */
    public void putMatrixValue(int _r, int _c, Object _o) {
        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);
        if (mf == null) {
            System.out.println("*** get #1 Cannot locate Column in putMatrixValue:" + _r + ":COL:" + _c);
            return;
        }
        if (mfRow == null) {
            System.out.println("*** get #1 Cannot locate row in putMatrixValue:" + _r + ":COL:" + _c);
            return;
        }

        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
            if (mfRow == null) {
                System.out.println("*** get #1 Cannot locate Parent of Implicator in putMatrixValue:" + _r + ":COL:" + _c);
                return;
            }
        }
        m_tw.putMatrixValue(mfRow.getKey() + ":" + mf.getKey(), _o);
    }

    /**
     * (non-Javadoc)
     * isMatrixEditable
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#isMatrixEditable(int, int)
     */
    public boolean isMatrixEditable(int _r, int _c) {

        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

        if (mf == null) {
            System.out.println("isMatrixEditable Cannot locate Column in get:" + _r + ":COL:" + _c);
            return false;
        }
        if (mfRow == null) {
            System.out.println("isMatrixEditable Cannot locate row in get:" + _r + ":COL:" + _c);
            return false;
        }

        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
            if (mfRow == null) {
                System.out.println("isMatrixEditable Cannot locate Parent of Implicator:" + _r + ":COL:" + _c);
                return false;
            }
        }
        return m_tw.isMatrixEditable(mfRow.getKey() + ":" + mf.getKey());
    }

    /**
     * (non-Javadoc)
     * rollbackMatrix
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#rollbackMatrix()
     */
    public void rollbackMatrix() {
        m_tw.rollbackMatrix();
    }

    /**
     * (non-Javadoc)
     * getRowHeaderMatrix
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getRowHeaderMatrix(int)
     */
    public String getRowHeaderMatrix(int _r) {

        EANFoundation mfRow = null;
        String rowHeader = null;

        if (_r > m_elr.size()) {
            return "";
        }

        mfRow = (EANFoundation) m_elr.getAt(_r);
        if (mfRow == null) {
            System.out.println("*** getRowHeader Cannot locate row in get:" + _r);
            return null;
        }
        if (mfRow instanceof Implicator) {
            mfRow = mfRow.getParent();
        }
        if (m_bLong) {
            rowHeader = mfRow.getLongDescription();
            if (rowHeader.equals("TBD")) {
                rowHeader = mfRow.getKey();
            }
        } else {
            rowHeader = mfRow.getShortDescription();
            if (rowHeader.equals("TBD")) {
                rowHeader = mfRow.getKey();
            }
        }
        return rowHeader;
    }

    /**
     * (non-Javadoc)
     * addColumn
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#addColumn(COM.ibm.eannounce.objects.EANFoundation[])
     */
    public void addColumn(EANFoundation[] _aean) {
        if (_aean == null) {
            return;
        }
        for (int i = 0; i < _aean.length; i++) {
            addColumn(_aean[i]);
        }
    }

    /**
     * (non-Javadoc)
     * addColumn
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#addColumn(COM.ibm.eannounce.objects.EANFoundation)
     */
    public void addColumn(EANFoundation _ean) {
        if (!canCreate()) {
            return;
        }
        m_tw.addColumn(_ean);
        refresh();
    }

    /**
     * (non-Javadoc)
     * generatePickList
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#generatePickList(int, COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    public EntityList generatePickList(int _i, Database _db, RemoteDatabaseInterface _rdi, Profile _prof, boolean _bIsColumn) {
        //    System.out.println("RowSelectableTable generatePickList _i: " + _i + ", _bIsColumn: " + _bIsColumn);
        if (_rdi == null && _db == null) {
            return null;
        }

        if (_bIsColumn) {
            EANFoundation mf = (EANFoundation) m_elc.getAt(_i);
            if (mf == null) {
                System.out.println("*** get #1 Cannot locate Column in generatePickList:" + _i);
                return null;
            }
            //System.out.println("RowSelectableTable generatePickList mf key: " + mf.getKey());
            return m_tw.generatePickList(_db, _rdi, _prof, mf.getKey());
        } else {
            EANFoundation mfRow = (EANFoundation) m_elr.getAt(_i);
            if (mfRow == null) {
                System.out.println("*** get #1 Cannot locate row in generatePickList:" + _i);
                return null;
            }
            //System.out.println("RowSelectableTable generatePickList mfRow key: " + mfRow.getKey());
            return m_tw.generatePickList(_db, _rdi, _prof, mfRow.getKey());
        }
    }

    /*
    * This is used to remove link in whereused based on an array of rows
    */
    /**
     * (non-Javadoc)
     * removeLink
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#removeLink(int[], COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile)
     */
    public boolean removeLink(int[] _ar, Database _db, RemoteDatabaseInterface _rdi, Profile _prof) throws EANBusinessRuleException {
        boolean bResult = false;
        EntityItemException eie = new EntityItemException();
        
        // attempt to improve perf by combining some db calls, only in whereused
        // avoid changing EANTableWrapper interface, too many things use it
        if (m_tw instanceof WhereUsedList){
        	String keyArray[] = new String[_ar.length];
        	for (int i = 0; i < _ar.length; i++) {
                EANFoundation mfRow = (EANFoundation) m_elr.getAt(_ar[i]);
                if (mfRow == null) {
                    System.out.println("*** #1 Cannot locate Row in removeLink:" + _ar[i]);
                    return false;
                }
                keyArray[i]=mfRow.getKey();
            }
        	try {
        		bResult = ((WhereUsedList)m_tw).removeLinks(_db, _rdi, _prof, keyArray);
            } catch (EANBusinessRuleException _bre) {
                eie.addException(_bre);
            }
        }else{ // this is never used
        	for (int i = 0; i < _ar.length; i++) {
                EANFoundation mfRow = (EANFoundation) m_elr.getAt(_ar[i]);
                if (mfRow == null) {
                    System.out.println("*** #1 Cannot locate Row in removeLink:" + _ar[i]);
                    return false;
                }

                try {
                    boolean b = m_tw.removeLink(_db, _rdi, _prof, mfRow.getKey());
                    if (b) {
                        bResult = b;
                    }
                } catch (EANBusinessRuleException _bre) {
                    eie.addException(_bre);
                }
            } 	
        }
        refresh();
        if (eie.getErrorCount() > 0) {
            throw eie;
        }
        return bResult;
    }

    /*
    * This is used to link in whereused
    */
    /**
     * (non-Javadoc)
     * link
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#link(int[], COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem[], java.lang.String)
     */
    public EANFoundation[] link(int[] _ar, Database _db, RemoteDatabaseInterface _rdi, Profile _prof, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
        Vector eanVector = new Vector();
        EntityItemException eie = null;
        EANFoundation eanArray[] = null;

        // attempt to improve perf by combining some db calls, only in whereused
        // avoid changing EANTableWrapper interface, too many things use it
        if (m_tw instanceof WhereUsedList){
        	String keyArray[] = new String[_ar.length];
        	for (int i = 0; i < _ar.length; i++) {
                EANFoundation mfRow = (EANFoundation) m_elr.getAt(_ar[i]);
                if (mfRow == null) {
                    System.out.println("*** #1 Cannot locate Row in Link:" + _ar[i]);
                    return null;
                }
                keyArray[i]=mfRow.getKey();
            }
        	try {
        		eanArray = ((WhereUsedList)m_tw).linkMultiple(_db, _rdi, _prof, keyArray, _aeiChild, _strLinkOption);
            } catch (EANBusinessRuleException _bre) {
            	eie = new EntityItemException();
                eie.addException(_bre);
            }
        }else{ // this is never used
        	for (int i = 0; i < _ar.length; i++) {
        		EANFoundation mfRow = (EANFoundation) m_elr.getAt(_ar[i]);
        		if (mfRow == null) {
        			System.out.println("*** #1 Cannot locate Row in Link:" + _ar[i]);
        			return null;
        		}
        		try {
        			EANFoundation[] eanA = m_tw.link(_db, _rdi, _prof, mfRow.getKey(), _aeiChild, _strLinkOption);
        			if (eanA != null) {
        				for (int ii = 0; ii < eanA.length; ii++) {
        					eanVector.addElement(eanA[ii]);
        				}
        			}
        		} catch (EANBusinessRuleException _bre) {
        			if (eie==null){
        				eie = new EntityItemException();
        			}
        			eie.addException(_bre);
        		}
        	}
            eanArray = new EANFoundation[eanVector.size()];
            eanVector.copyInto(eanArray);
        }

        refresh();
        if (eie!=null && eie.getErrorCount() > 0) {
            throw eie;
        }

        return eanArray;
    }

    /*
    * This is used to get an EntityList to create in whereused
    */
    /**
     * (non-Javadoc)
     * create
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#create(int, COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile)
     */
    public EntityList create(int _i, Database _db, RemoteDatabaseInterface _rdi, Profile _prof)
    {
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_i);

        if (_rdi == null && _db == null) {
            return null;
        }
        if (mfRow == null) {
            System.out.println("*** get #1 Cannot locate row in create:" + _i);
            return null;
        }

       	return m_tw.create(_db, _rdi, _prof, mfRow.getKey());
    }
	// need to capture create domain msg RQ0713072645
    public EntityList create2(int _i, Database _db, RemoteDatabaseInterface _rdi, Profile _prof)
		throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException
    {
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_i);

        if (_rdi == null && _db == null) {
            return null;
        }
        if (mfRow == null) {
            System.out.println("*** get #1 Cannot locate row in create:" + _i);
            return null;
        }

        if (m_tw instanceof WhereUsedList){
        	return ((WhereUsedList)m_tw).create2(_db, _rdi, _prof, mfRow.getKey());
		}else{
        	return m_tw.create(_db, _rdi, _prof, mfRow.getKey());
		}
    }

    /*
    * This is used to get a WhereUsedList from WhereUsedList table
    */
    /**
     * (non-Javadoc)
     * getWhereUsedList
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getWhereUsedList(int, COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile)
     */
    public WhereUsedList getWhereUsedList(int _i, Database _db, RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_i);

        if (_rdi == null && _db == null) {
            return null;
        }
        if (mfRow == null) {
            System.out.println("*** get #1 Cannot locate row in wused:" + _i);
            return null;
        }

        return m_tw.getWhereUsedList(_db, _rdi, _prof, mfRow.getKey());
    }

    /*
    * This is used to get a Edit EntityList from WhereUsedList table
    */
    /**
     * (non-Javadoc)
     * edit
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#edit(int[], COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile)
     */
    public EntityList edit(int[] _ar, Database _db, RemoteDatabaseInterface _rdi, Profile _prof)
    	throws SQLException, MiddlewareException, MiddlewareRequestException,
    	MiddlewareShutdownInProgressException, RemoteException
    {
		return edit(_ar, _db, _rdi, _prof,null);
	}
	//RQ0713072645 must do check in whereusedlist to access items and action
    public EntityList edit(int[] _ar, Database _db, RemoteDatabaseInterface _rdi, Profile _prof, StringBuffer errSb) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        String[] aKeys = null;
        Vector v = new Vector();
        if (_rdi == null && _db == null) {
            return null;
        }

        for (int i = 0; i < _ar.length; i++) {
            int r = _ar[i];
            EANFoundation mfRow = (EANFoundation) m_elr.getAt(r);
            if (mfRow != null) {
                v.addElement(mfRow.getKey());
            }
        }

        if (v.size() <= 0) {
            return null;
        }

        aKeys = new String[v.size()];
        v.toArray(aKeys);
        if (m_tw instanceof WhereUsedList){
	        return ((WhereUsedList)m_tw).edit(_db, _rdi, _prof, aKeys, errSb);
		}else{
	        return m_tw.edit(_db, _rdi, _prof, aKeys);
		}
    }

    /**
     * (non-Javadoc)
     * getActionItemsAsArray
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getActionItemsAsArray(int, COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile)
     */
    public Object[] getActionItemsAsArray(int _r, Database _db, RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);
        if (mfRow == null) {
            System.out.println("*** get #1 Cannot locate row in getActionItemsAsArray:" + _r);
            return null;
        }

        return m_tw.getActionItemsAsArray(_db, _rdi, _prof, mfRow.getKey());
    }

    /**
     * (non-Javadoc)
     * getLockGroup
     *
     * @see COM.ibm.eannounce.objects.EANTableModel#getLockGroup(int, int)
     */
    public LockGroup getLockGroup(int _r, int _c) {

        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);
        LockGroup lg = null;
        if (mf == null) {
            System.out.println("*** getLockGroup Cannot locate Column in get:" + _r + ":COL:" + _c);
            return null;
        }

        if (mfRow == null) {
            System.out.println("*** getLockGroup Cannot locate row in get:" + _r + ":COL:" + _c);
            return null;
        }
    	if (m_tw instanceof MatrixList || m_tw instanceof MatrixGroup) {
    		// must be able to check lock on editable matrix cell
    		Object objValue = getMatrixValue(_r, _c);
    		if (objValue instanceof EANAddressable){
    			lg = ((EANAddressable) objValue).getLockGroup(mf.getKey());
    		}
    	} else{
    		if (mfRow instanceof Implicator) {
    			mfRow = mfRow.getParent();
    			if (mfRow == null) {
    				System.out.println("*** getLockGroup Cannot locate Parent of Implicator:" + _r + ":COL:" + _c);
    				return null;
    			}
    		}
    		lg = ((EANAddressable) mfRow).getLockGroup(mf.getKey());
    	}
        
        return lg;
    }

    /**
     * isDynaTable
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isDynaTable() {
        return m_tw.isDynaTable();
    }

    /**
     * Return this RowSelectableTable's FilterGroup
     *
     * @return FilterGroup
     */
    public FilterGroup getFilterGroup() {
        return m_filterGroup;
    }

    /**
     * setFilterGroup
     *
     * @param _fg
     *  @author David Bigelow
     */
    public void setFilterGroup(FilterGroup _fg) {
        m_filterGroup = _fg;
        return;
    }

    /**
     * hasFilterGroup
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasFilterGroup() {
        return m_filterGroup != null;
    }

    /**
     * removeFilterGroup
     *
     *  @author David Bigelow
     */
    public void removeFilterGroup() {
        m_filterGroup = null;
    }

    /**
     * Are there currently any rows being filtered out (NOT being displayed)
     *
     * @return boolean
     */
    public boolean hasFilteredRows() {
        return m_bHasFilteredRows;
    }

    /**
     * Return this RowSelectableTable's FilterGroup
     *
     * @return FilterGroup
     */
    public FilterGroup getColFilterGroup() {
        return m_colFilterGroup;
    }

    /**
     * setColFilterGroup
     *
     * @param _fg
     *  @author David Bigelow
     */
    public void setColFilterGroup(FilterGroup _fg) {
        m_colFilterGroup = _fg;
        return;
    }

    /**
     * hasColFilterGroup
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasColFilterGroup() {
        return m_colFilterGroup != null;
    }

    /**
     * removeColFilterGroup
     *
     *  @author David Bigelow
     */
    public void removeColFilterGroup() {
        m_colFilterGroup = null;
    }

    /**
     * Are there currently any rows being filtered out (NOT being displayed)
     *
     * @return boolean
     */
    public boolean hasFilteredCols() {
        return m_bHasFilteredCols;
    }

    /**
    * Apply filter to our table (we really our removing columns here)
    */
    private void applyFilter() {

        Vector vctRemoveRowKeys = new Vector();

        // just to protect against nulls...
        if (!hasFilterGroup()) {
            return;
        }
        // 0) we need to save which rows to remove
        // 1) go through each column and pull out FilterItems matching each column's key
        //    - then, we can filter out any rows for this particular FilterItem
        for (int iCol = 0; iCol < getColumnCount(); iCol++) {
            //String strColKey = getColumn(iCol).getKey();
            String strColKey = getColumnKey(iCol);
          
            // 2) go through rows and evaluate each FilterItem against the current cell's value
            for (int iRow = 0; iRow < getRowCount(); iRow++) {
                Object objValue = null;
                String strValue = null;
                EANFoundation efRow = null;

                // we need to get the matrix value when RSTable is on a MatrixList
                if (m_tw instanceof MatrixList || m_tw instanceof MatrixGroup) {
                    objValue = getMatrixValue(iRow, iCol);
                } else {
//WK_001                    objValue = get(iRow, iCol);
					objValue = get(iRow,iCol,true);				//WK_001
                }

                if (objValue==null){
                	objValue="";
                }
  
                strValue = objValue.toString();

                // GAB 9/17/03 - per FB 52297, don't filter new EntityItems....
                efRow = getRow(iRow);
                if (efRow instanceof EANEntity) {
                	EANEntity ent = (EANEntity) efRow;
                	if (ent.getEntityID() < 0) {
                		continue;
                	}
                } // end 9/17/03

                //if it doesnt pass -> we need to remove this row when we're done!
                if (!getFilterGroup().evaluate(strColKey, strValue)) {
                    vctRemoveRowKeys.addElement(getRowKey(iRow));
                }
            }
        }
        // 3) now remove from our row list....
        for (int i = 0; i < vctRemoveRowKeys.size(); i++) {
            m_elr.remove((String) vctRemoveRowKeys.elementAt(i));
            m_bHasFilteredRows = true;
        }
        return;
    }

    /**
    * Apply filter to our table (we really our removing columns here)
    */
    private void applyColumnFilter() {

        Vector vctRemoveColKeys = new Vector();

        // just to protect against nulls...
        if (!hasColFilterGroup()) {
            return;
        }
        // 0) we need to save which rows to remove
        // 1) go through each column and pull out FilterItems matching each column's key
        //    - then, we can filter out any rows for this particular FilterItem
        for (int iRow = 0; iRow < getRowCount(); iRow++) {
            //String strColKey = getColumn(iCol).getKey();
            String strRowKey = getRowKey(iRow);
            // 2) go through rows and evaluate each FilterItem against the current cell's value
            for (int iCol = 0; iCol < getColumnCount(); iCol++) {
                Object objValue = null;
                EANFoundation efRow = null;
                String strValue = null;

                // we need to get the matrix value when RSTable is on a MatrixList
                if (m_tw instanceof MatrixList || m_tw instanceof MatrixGroup) {
                    objValue = getMatrixValue(iRow, iCol);
                } else {
                    objValue = get(iRow, iCol);
                }

                strValue = objValue.toString();

                // GAB 9/17/03 - per FB 52297, don't filter new EntityItems....
                efRow = getRow(iRow);
                if (efRow != null) {
                    if (efRow instanceof EANEntity) {
                        EANEntity ent = (EANEntity) efRow;
                        if (ent.getEntityID() < 0) {
                            continue;
                        }
                    }
                }
                // end 9/17/03

                //if it doesnt pass -> we need to remove this row when we're done!
                if (!getColFilterGroup().evaluate(strRowKey, strValue)) {
                    vctRemoveColKeys.addElement(getColKey(iCol));
                }
            }
        }
        // 3) now remove from our row list....
        for (int i = 0; i < vctRemoveColKeys.size(); i++) {
            m_elc.remove((String) vctRemoveColKeys.elementAt(i));
            m_bHasFilteredCols = true;
        }
        return;
    }

    /**
    * When we want a 'fresh' copy of a list -- not just to set a pointer
    */
    private EANList copyList(EANList _eList) {
        EANList eListCopy = new EANList();
        for (int i = 0; i < _eList.size(); i++) {
            eListCopy.put(_eList.getAt(i));
        }
        return eListCopy;
    }

    /**
     * allow protected access to TableWrapper's ColumnList
     *
     * @return EANList
     */
    protected EANList getColumnList() {
        return m_elc;
    }

    /**
     * getRowList
     *
     * @return
     *  @author David Bigelow
     */
    protected EANList getRowList() {
        return m_elr;
    }

    private boolean useFilter() {
        return m_bUseFilter;
    }

    /**
     * setUseFilter
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setUseFilter(boolean _b) {
        m_bUseFilter = _b;
    }

    /**
     * Re-arrange ColumnList according to MetaColumnOrder objects
     *
     * @concurrency $none
     * @param _mcog
     * @param _el
     * @return EANList
     */
    protected static final synchronized EANList applyColumnOrders(MetaColumnOrderGroup _mcog, EANList _el) {
        EANList elNew = new EANList();
        if (_mcog != null) {
            _mcog.performSort(MetaColumnOrderGroup.COLORDER_KEY, true);
            // assumption is that key of MetaColumnOrderItem == key of Implicator in columnList
            for (int i = 0; i < _mcog.getMetaColumnOrderItemCount(); i++) {
                MetaColumnOrderItem mcoi = _mcog.getMetaColumnOrderItem(i);
           //System.out.println("TRACKING a mcoi.getKey() [" + i + "]:" + mcoi.getKey());
                if (mcoi.isVisible()) {
                	// mcoi key has strreltag appended
                    EANObject eObj = (EANObject) _el.get(mcoi.getKey());
                    if (eObj==null){
                    	// try key without trailing :C
                    	int colon = mcoi.getKey().lastIndexOf(':');
                    	if(colon!=-1){
                    		String key = mcoi.getKey().substring(0, colon);
                  // System.out.println("TRACKING a key [" + i + "]:" + key);
                    		eObj = (EANObject) _el.get(key);
                    	}
                    }
                    if (eObj != null) {
                    //    System.out.println("TRACKING b mcoi.getKey() [" + i + "]:" + mcoi.getKey());
                        elNew.put(eObj);
                    }
                } else {
                    //System.out.println("TRACKING " + mcoi.getKey() + " is NOT visible()");
                }
            }
            // Add in objects NOT IN MetaColumnOrderGroup -- this WILL occur when RST is on Relator 'coz we pass through
            // this twice -- once for EG, once for EG2.
            // However, if this guy IS in mcog, but just not visible --> keep it hidden!!
            for (int i = 0; i < _el.size(); i++) {
                EANObject ogListObj = _el.getAt(i);
                //System.out.println("TRACKING: 1 " + ogListObj.getKey());
                //System.out.println("TRACKING: 2 _mcog.getMetaColumnOrderItem(ogListObj.getKey()) == null?" + (_mcog.getMetaColumnOrderItem(ogListObj.getKey()) == null));
                //System.out.println("TRACKING: 3 elNew.containsKey(ogListObj.getKey()) " + elNew.containsKey(ogListObj.getKey()));
                if (!elNew.containsKey(ogListObj.getKey())) {
                	String key = ogListObj.getKey();
                	MetaColumnOrderItem mcoi = _mcog.getMetaColumnOrderItem(key);
                	if (mcoi == null) {
                		// check for any strreltag appended to mcoi key
                		mcoi = _mcog.getMetaColumnOrderItem(key+":C");
                    //    System.out.println("TRACKING: 2C _mcog.getMetaColumnOrderItem(key+:C) == null?" +(mcoi == null));
                		if (mcoi == null) {
                			mcoi = _mcog.getMetaColumnOrderItem(key+":R");
                		//    System.out.println("TRACKING: 2R _mcog.getMetaColumnOrderItem(key+:R) == null?" +(mcoi == null));
                		}
                	}
                	if (mcoi == null) {
                		//System.out.println("TRACKING ok putting " + ogListObj.getKey());
                		elNew.put(ogListObj);
                	}
                }
            }

            return elNew;
        }
        return _el;
    }

    /**
     * duplicateRow
     *
     * @param _r
     * @param _iDup
     * @return
     *  @author David Bigelow
     */
    public boolean duplicateRow(int _r, int _iDup) {
        if (!canCreate()) {
            return false;
        }

        if (m_tw.duplicate(getRow(_r).getKey(), _iDup)) {
            refresh();
            return true;
        }

        return false;

    }

    /**
     * link
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _lai
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.eannounce.objects.WorkflowException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public Object link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {

        Object obj = m_tw.linkAndRefresh(_db, _rdi, _prof, _lai);

        refresh();
        return obj;
    }

    /**
     * isParentAttribute
     *
     * @param _r
     * @param _c
     * @return
     *  @author David Bigelow
     */
    public boolean isParentAttribute(int _r, int _c) {
        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);
        String strKey = "";

        if (mf == null) {
            System.out.println("*** isParentAttribute Cannot locate Column in isParentAttribute:" + _r + ":COL:" + _c);
            return false;
        }

        if (mfRow == null) {
            System.out.println("*** isParentAttribute Cannot locate row in isParentAttribute:" + _r + ":COL:" + _c);
            return false;
        }

        if (mf.getKey().indexOf(":") > 0) {
            strKey = mf.getKey();
        } else if (mfRow.getKey().indexOf(":") > 0) {
            strKey = mfRow.getKey();
        }
        if (strKey.length() > 0) {
            StringTokenizer st = new StringTokenizer(strKey, ":");
            String strColType = null;
            int i = st.countTokens();
            if (i == 3) {
                st.nextToken();
                st.nextToken();
                strColType = st.nextToken();
                if (strColType.equals("P")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * isChildAttribute
     *
     * @param _r
     * @param _c
     * @return
     *  @author David Bigelow
     */
    public boolean isChildAttribute(int _r, int _c) {
        String strKey = "";
        EANFoundation mf = (EANFoundation) m_elc.getAt(_c);
        EANFoundation mfRow = (EANFoundation) m_elr.getAt(_r);

        if (mf == null) {
            System.out.println("*** isChildAttribute Cannot locate Column in isChildAttribute:" + _r + ":COL:" + _c);
            return false;
        }

        if (mfRow == null) {
            System.out.println("*** isChildAttribute Cannot locate row in isChildAttribute:" + _r + ":COL:" + _c);
            return false;
        }

        if (mf.getKey().indexOf(":") > 0) {
            strKey = mf.getKey();
        } else if (mfRow.getKey().indexOf(":") > 0) {
            strKey = mfRow.getKey();
        }
        if (strKey.length() > 0) {
            StringTokenizer st = new StringTokenizer(strKey, ":");
            String strColType = null;
            int i = st.countTokens();
            if (i == 3) {
                st.nextToken();
                st.nextToken();
                strColType = st.nextToken();
                if (strColType.equals("C")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * getMatrixValue
     *
     * @param _sRow
     * @param _sCol
     * @return
     *  @author David Bigelow
     */
    public Object getMatrixValue(String _sRow, String _sCol) {
        return m_tw.getMatrixValue(_sRow + ":" + _sCol);
    }

    /**
     * get
     *
     * @param _rowKey
     * @param _col
     * @return
     *  @author David Bigelow
     */
    public Object get(String _rowKey, int _col) {
        EANFoundation rowFoundation = getRow(_rowKey);
        EANFoundation colFoundation = getColumn(_col);
        if (rowFoundation != null && colFoundation != null) {
            String sKey = colFoundation.getKey();
            if (rowFoundation instanceof EntityItem) {
                EntityItem ei = (EntityItem) rowFoundation;
                String sParent = ((EntityGroup) colFoundation.getParent()).getEntityType();
                Object o = null;
                if (ei.hasDownLinks()) {
                    EANEntity eanDown = ei.getDownLink(0);
                    if (eanDown.getEntityType().equals(sParent)) {
                        o = eanDown.getAttribute(sKey);
                    }
                }
                if (o == null && ei.hasUpLinks()) {
                    EANEntity eanUp = ei.getUpLink(0);
                    if (eanUp.getEntityType().equals(sParent)) {
                        o = eanUp.getAttribute(sKey);
                    }
                }
                if (o == null) {
                    o = ei.getAttribute(sKey);
                }
                return o;
            } else if (rowFoundation instanceof WhereUsedItem) {
                WhereUsedItem wui = (WhereUsedItem) rowFoundation;
                return wui.get(sKey, false);
            } else if (rowFoundation instanceof QueryItem) {
            	QueryItem qi = (QueryItem) rowFoundation;
                return qi.get(sKey, false);
            }else if (rowFoundation instanceof MatrixItem) {
                MatrixItem mi = (MatrixItem) rowFoundation;
                EntityItem eiTemp = mi.getChildEntity();
                Object o = null;
                if (eiTemp != null) {
                    o = eiTemp.getAttribute(sKey);
                }
                if (o == null) {
                    eiTemp = mi.getParentEntity();
                    if (eiTemp != null) {
                        o = eiTemp.getAttribute(sKey);
                    }
                }
                return o;
            } else {
                System.out.println("rowFoundation is: " + rowFoundation.getClass().getName());
                System.out.println("colFoundation is: " + colFoundation.getClass().getName());
            }
        }
        return null;
    }

    //This uses the bubble sort used by the java Array object to sort the table
    /**
     * bSort
     *
     * @param _iSortColumn
     * @param _bSortAscending
     * @param _bMatrix
     *  @author David Bigelow
     */
    public void bSort(int[] _iSortColumn, boolean[] _bSortAscending, int _type) {
        Object objArray[] = new Object[m_elr.size()];

        //First create the comaparator
        rsTableComparator rsComp = new rsTableComparator(_iSortColumn, _bSortAscending, _type);
        rsComp.setTable(this);

        //Convert to Eanlist rows to an array
        for (int i = 0; i < m_elr.size(); i++) {
            objArray[i] = m_elr.getAt(i);
        }

        //Sort!
        Arrays.sort(objArray, rsComp);

        //Initialize the EanList and copy the sorted array back to vector
        m_elr.removeAll();
        for (int i = 0; i < objArray.length; i++) {
            m_elr.put((EANObject) objArray[i]);
        }
    }

    /**
     * synchronizeRows
     *
     * @param _eanArray
     *  @author David Bigelow
     */
    public void synchronizeRows(EANFoundation[] _eanArray) {
        if (_eanArray != null) {
            int ii = _eanArray.length;
            EANFoundation[] ean = new EANFoundation[ii];
            for (int i = 0; i < ii; i++) {
                ean[i] = getRow(_eanArray[i].getKey());
            }
            m_elr.removeAll();
            for (int i = 0; i < ii; i++) {
                m_elr.put(ean[i]);
            }
        }
    }

    /**
     * getTableColumnsAsArray
     *
     * @return
     *  @author David Bigelow
     */
    public EANFoundation[] getTableColumnsAsArray() {
        EANFoundation[] af = new EANFoundation[m_elc.size()];
        for (int ii = 0; ii < m_elc.size(); ii++) {
            EANFoundation mf = (EANFoundation) m_elc.getAt(ii);
            af[ii] = mf;
        }
        return af;
    }

    /**
     * synchronizeColumns
     *
     * @param _eanArray
     *  @author David Bigelow
     */
    public void synchronizeColumns(EANFoundation[] _eanArray) {
        if (_eanArray != null) {
            int ii = _eanArray.length;
            EANFoundation[] ean = new EANFoundation[ii];
            for (int i = 0; i < ii; i++) {
                ean[i] = getColumn(_eanArray[i].getKey());
            }
            m_elc.removeAll();
            for (int i = 0; i < ii; i++) {
                m_elc.put(ean[i]);
            }
        }
    }

    /**
     * hideRows
     *
     * @param _keys
     *  @author David Bigelow
     */
    public void hideRows(String[] _keys) {
        if (_keys == null) {
            m_elr = copyList(m_tw.getRowList());
        } else {
            int ii = _keys.length;
            for (int i = 0; i < ii; i++) {
                m_elr.remove(_keys[i]);
            }
        }
    }

    /**
     * retireFlags
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param rows
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    public void retireFlags(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, int[] rows) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        if (m_tw instanceof MetaFlagMaintList) {
            MetaFlagMaintList mfml = (MetaFlagMaintList) m_tw;
            Vector v = new Vector();
            for (int i = 0; i < rows.length; i++) {
                EANFoundation ef = (EANFoundation) m_elr.getAt(rows[i]);
                if (ef == null) {
                    System.out.println("RowSelectableTable.retireFlags(" + rows[i] + ") is not a valid row");
                } else {

                    v.addElement(ef.getKey());
                }
            }

            if (v.size() > 0) {
                String[] aKeys = new String[v.size()];
                v.toArray(aKeys);
                mfml.retireMetaFlagMaintItems(_db, _rdi, _prof, aKeys);

            }
        }
    }

    /**
     * retireFlags
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param rows
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    public void unexpireFlags(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, int[] rows) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        if (m_tw instanceof MetaFlagMaintList) {
            MetaFlagMaintList mfml = (MetaFlagMaintList) m_tw;
            Vector v = new Vector();
            for (int i = 0; i < rows.length; i++) {
                EANFoundation ef = (EANFoundation) m_elr.getAt(rows[i]);
                if (ef == null) {
                    System.out.println("RowSelectableTable.unexpireFlags(" + rows[i] + ") is not a valid row");
                } else {

                    v.addElement(ef.getKey());
                }
            }

            if (v.size() > 0) {
                String[] aKeys = new String[v.size()];
                v.toArray(aKeys);
                mfml.unexpireMetaFlagMaintItems(_db, _rdi, _prof, aKeys);

            }
        }
    }

    /**
     * updateFlagCodes
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _el
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    public void updateFlagCodes(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, EntityList _el) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        if (m_tw instanceof MetaFlagMaintList) {
            MetaFlagMaintList mfml = (MetaFlagMaintList) m_tw;
            mfml.updateFlagCodes(_db, _rdi, _prof, _el);
        }
    }
}
