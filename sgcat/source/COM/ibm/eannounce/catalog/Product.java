/*
 * Created on Mar 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: Product.java,v $
 * Revision 1.50  2015/03/05 16:12:24  ptatinen
 * Add new attributes for Lenovo CQ
 *
 * Revision 1.49  2015/02/03 12:27:04  guobin
 * RCQ00337761-RQ - PRCINDC is Yes and the SEOORDERCODE is MES, set the customizable flag to "No"
 *
 * Revision 1.48  2013/06/07 02:48:56  liuweimi
 * New Requirement for PUBFROM in catnet (set pubfrom using plan avail.effectivedate if no announcement)
 *
 * Revision 1.47  2013/06/03 17:08:30  liuweimi
 * bug fix - Pubfrom date - Avail don't have any link to announcement
 *
 * Revision 1.46  2013/05/22 12:22:17  liuweimi
 * fix catlgor deletion issue
 *
 * Revision 1.45  2013/05/14 13:02:55  liuweimi
 * Fix PUBFROM PUBTO derivation for XCC based on BH - EACM
 *
 * Revision 1.44  2012/02/15 02:03:20  praveen
 * Fix 8983 error
 *
 * Revision 1.43  2012/02/15 01:30:10  praveen
 * Fix Override dates logic
 *
 * Revision 1.42  2012/02/06 21:44:15  praveen
 * Add SPECMODDESGN for DAP/DSW
 *
 * Revision 1.41  2011/06/15 19:44:07  praveen
 * Increase oslevel length to 13600, and remove logic to look
 * prodstructwarr for warranty
 *
 * Revision 1.14  2011/01/19 15:37:49  rick
 * Concatenating WARRPRIODUOM attr to WARRPRIOD.
 * Also - setting default for effectivedate on WARRMODEL and
 * PRODSTUCTWARR to '1980-01-01' per Linda.
 *
 * Revision 1.13  2010/12/14 19:20:07  rick
 * do not want to check status on prodstructwarr, modelwarr
 * and model.
 *
 * Revision 1.12  2010/12/09 20:15:56  lucasrg
 * 1. LSEOBUNDLEs use MODEL's Warranties instead of LSEOBUNDLEWARR
 *
 * Revision 1.11  2010/05/11 17:53:05  rick
 * adding a comment
 *
 * Revision 1.10  2010/04/28 19:00:44  rick
 * RCQ98197WI - change to PROCESSWARRs to only
 * consider WWSEOWARRs and MODELWARRs
 * which are active.
 *
 * Revision 1.9  2009/08/24 17:22:31  rick
 * svcpacbndltype change for schooner project
 *
 * Revision 1.8  2009/03/26 20:47:34  rick
 * Change to only look at AVAILs which are
 * ORDERSYSNAME SAP.
 *
 * Revision 1.7  2008/12/04 22:54:24  rick
 * fix typo in code for LACTO
 *
 * Revision 1.6  2008/11/18 21:16:30  rick
 * LACTO
 *
 * Revision 1.5  2008/04/07 15:59:07  yang
 *  CR072607687 Removing old rule for CONFIGURATORFLAG of skipping records.
 *
 * Revision 1.4  2008/04/07 06:00:20  yang
 * *** empty log message ***
 *
 * Revision 1.3  2008/02/28 19:37:53  rick
 * fix to not process inactive children entities.
 *
 * Revision 1.2  2007/11/20 02:58:02  rick
 * Lets not call set attributes from properties for a
 * catalog override entity. This prevents all the values
 * which have been stowed away from being set
 * back to the defaults just because an override entity exists.
 *
 * Revision 1.1.1.1  2007/06/05 02:09:27  jingb
 * no message
 *
 * Revision 1.38  2007/05/31 16:07:02  rick
 * increase length of oslevel and oslevel_fc
 *
 * Revision 1.37  2006/10/30 18:26:38  gregg
 * projcdnam
 *
 * Revision 1.36  2006/10/12 22:18:23  gregg
 * more configuratorflag pruning boolean logic
 *
 * Revision 1.35  2006/10/12 22:13:26  gregg
 * fix CONFIGURATORFLAG logic
 *
 * Revision 1.34  2006/09/21 19:17:18  gregg
 * Spec update, Re:PRODSTRUCT.CONFIGURATORFLAG
 *
 * Revision 1.33  2006/09/01 17:51:45  gregg
 * some debugs
 *
 * Revision 1.32  2006/08/29 17:34:59  gregg
 * CONFIGURATORFLAG pruning
 *
 * Revision 1.31  2006/08/08 23:58:15  gregg
 * null ptr fix
 *
 * Revision 1.30  2006/08/02 16:55:44  gregg
 * check catactive on matches catlgpub
 *
 * Revision 1.29  2006/07/12 20:44:49  gregg
 * 1) setActive on Products based on thecriteria of having AT LEAST one active smi.
 * 2) ignoring smi's which are not active in our pruning logic.
 *
 * Revision 1.28  2006/07/11 18:25:14  gregg
 * pulling back debugs to spew
 *
 * Revision 1.27  2006/06/21 19:41:14  gregg
 * add "Placeholder" for PRODSTRUCT flow criteria
 *
 * Revision 1.26  2006/06/07 22:21:33  gregg
 * fixing intersectFlagVals logic
 *
 * Revision 1.25  2006/05/30 20:28:59  gregg
 * closing out latest PUBFROM/PUBTO work for ProdStruct
 *
 * Revision 1.24  2006/05/24 21:00:31  gregg
 * removing said debug stmts
 *
 * Revision 1.23  2006/05/24 20:40:20  gregg
 * more traces
 *
 * Revision 1.22  2006/05/24 20:29:24  gregg
 * more debug stmts
 *
 * Revision 1.21  2006/05/24 20:18:55  gregg
 * debugging for OOFAVAIL
 *
 * Revision 1.20  2006/05/23 22:11:50  gregg
 * PRODSTRUCT/SWPRODSTRUCT pruning for MODEL (LCTO)'s
 *
 * Revision 1.19  2006/05/22 23:28:35  gregg
 * remove OSLEVEL filtering for country-level products (LCTOs)
 * per discussion w/ Wayne et all. The logic cirresponding
 * to TMF.OSLEVEL really means in the prodstruct table.
 *
 * Revision 1.18  2006/05/17 20:32:20  gregg
 * more debugging
 *
 * Revision 1.17  2006/05/17 20:15:42  gregg
 * more...
 *
 * Revision 1.16  2006/05/17 20:04:04  gregg
 * more debugging
 *
 * Revision 1.15  2006/05/17 19:52:58  gregg
 * debugging for OOFAVAIL
 *
 * Revision 1.14  2006/05/16 21:53:20  gregg
 * beginning changes for 050506 spec update
 *
 * Revision 1.13  2006/05/16 17:29:00  gregg
 * CATLGPUB filtering for products
 *
 * Revision 1.12  2006/05/04 15:46:55  gregg
 * some debugs for prune SMC
 *
 * Revision 1.11  2006/04/26 20:55:28  gregg
 * back to "new" pubto + gbl4027 fix
 *
 * Revision 1.10  2006/04/26 20:50:33  gregg
 * temporarily putting back in old pubdate logic for sake of today's feed. Zoinks!
 *
 * Revision 1.9  2006/04/26 20:39:09  gregg
 * null fix
 *
 * Revision 1.8  2006/04/26 19:57:42  gregg
 * pubdate/GBL4027 npe,
 * moving oslevel,pubdate,warr logic from get() to therir own respective methods.
 *
 * Revision 1.7  2006/04/25 22:09:53  gregg
 * compile fix
 *
 * Revision 1.6  2006/04/25 22:08:24  gregg
 * c_hashAudMap
 *
 * Revision 1.5  2006/04/25 21:45:17  gregg
 * StringTokenize Audien for gbl4027 (its a multi)
 *
 * Revision 1.4  2006/04/25 20:30:09  gregg
 * new PUBFROM/TO derivation
 *
 * Revision 1.3  2006/04/04 20:34:28  gregg
 * updated code per WAyne's 033106 spec
 *
 * Revision 1.2  2006/03/31 20:50:06  gregg
 * for net type products, prodstructs beneath are now dubbed "full"
 *
 * Revision 1.1.1.1  2006/03/30 17:36:31  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.188  2006/03/26 01:02:56  gregg
 * comment
 *
 * Revision 1.187  2006/03/26 00:26:54  gregg
 * merg != add
 *
 * Revision 1.186  2006/03/26 00:19:36  gregg
 * compile fix
 *
 * Revision 1.185  2006/03/26 00:17:48  gregg
 * simplified FEATURE pruning by countrylist.
 *
 * Revision 1.184  2006/03/25 22:49:57  gregg
 * duh
 *
 * Revision 1.183  2006/03/25 22:31:07  gregg
 * bleh fix
 *
 * Revision 1.182  2006/03/25 22:25:41  gregg
 * some wrenching on getChildrenBranch
 *
 * Revision 1.181  2006/03/25 21:49:37  gregg
 * yeehaw! Jackpot typo fix.
 *
 * Revision 1.180  2006/03/25 19:58:47  gregg
 * debug for pruning
 *
 * Revision 1.179  2006/03/25 01:34:22  gregg
 * npe fix
 *
 * Revision 1.178  2006/03/25 00:57:42  gregg
 * update
 *
 * Revision 1.177  2006/03/25 00:41:50  gregg
 * update
 *
 * Revision 1.176  2006/03/24 00:12:41  gregg
 * fix in isCountryList --> isActive <> isSelected!
 *
 * Revision 1.175  2006/03/23 22:36:32  gregg
 * more 10589 OSLEVEL mayhem
 *
 * Revision 1.174  2006/03/23 21:49:58  gregg
 * More OSLEVEL madness: if OS Independent-> this implies ALL
 *
 * Revision 1.173  2006/03/23 21:13:55  gregg
 * intersectFlagVals algorithm
 *
 * Revision 1.172  2006/02/28 20:54:42  gregg
 * oslevel cleanup old code
 *
 * Revision 1.171  2006/02/28 20:52:16  gregg
 * cleaning up OSLEVEL population for product, wwproduct, prodstruct:
 * ** Net consensus for OSLEVEL is:
 *        1) LCTO's go to GBLI.PRODUCT table.
 *        2) LSEOBUNDLE's go to GBLI.PRODUCT table.
 *        3) MODEL TMF's (WorldWide) go to GBLI.PRODSTRUCT table.
 *
 * Revision 1.170  2006/02/27 17:47:14  gregg
 * update
 *
 * Revision 1.169  2006/02/24 21:34:44  gregg
 * oslevel craziness
 *
 * Revision 1.168  2006/02/24 17:47:45  gregg
 * backing out changes 1.166
 *
 * Revision 1.167  2006/02/23 20:58:49  gregg
 * fix withdrawl date (get from availtype = Last Order per SS)
 *
 * Revision 1.166  2006/02/22 19:12:28  gregg
 * small change in populating OSLEVEL for LSEOBUNDLE
 *
 * Revision 1.165  2006/02/01 01:05:07  gregg
 * misc
 *
 * Revision 1.164  2006/01/31 23:11:23  gregg
 * closing out pubfromdate (lets hope)
 *
 * Revision 1.163  2006/01/31 22:31:46  gregg
 * null handling nonsense
 *
 * Revision 1.162  2006/01/31 19:22:41  gregg
 * more allow null dates
 *
 * Revision 1.161  2006/01/31 19:02:55  gregg
 * more allowing nulls
 *
 * Revision 1.160  2006/01/31 18:49:55  gregg
 * trying once again to allow nulls in date fields
 *
 * Revision 1.159  2006/01/31 00:30:46  gregg
 * more pubfrom goodness
 *
 * Revision 1.158  2006/01/31 00:14:24  gregg
 * yee-hoaw-pubfrom
 *
 * Revision 1.157  2006/01/30 23:48:27  gregg
 * oh the joys of pubfrom
 *
 * Revision 1.156  2006/01/30 23:40:50  gregg
 * yadda
 *
 * Revision 1.155  2006/01/30 23:12:58  gregg
 * some PUBFROM mania
 *
 * Revision 1.154  2006/01/23 16:06:27  dave
 * abit of formatting
 *
 * Revision 1.153  2006/01/16 19:23:51  gregg
 * OSLEVEL: update per 1/15 discussion
 * Net:
 *  - LSEO, LSEO BUNDLE has OSLEVEL move into product table (and prodstruct additionally for kicks, though not specified)
 *  - LCTOs taken from Model and moved into the Product table.
 *       (then additional spec logic is applied to these in prodstruct)
 * - WW TMF's to prodstruct only. MODEL.OSLEVEL for wwproducts moved over as is.
 *
 * Revision 1.152  2006/01/13 23:32:20  gregg
 * OSLEVEL processing per 6/12 spec
 *
 * Revision 1.151  2006/01/10 23:52:49  gregg
 * more null ptrs
 *
 * Revision 1.150  2006/01/10 23:38:04  gregg
 * np exc fix
 *
 * Revision 1.149  2006/01/10 23:05:37  gregg
 * OSLEVEL processing per new spec - 01.09.06
 *
 * Revision 1.148  2006/01/04 18:59:37  gregg
 * add LCTO.oslevel
 *
 * Revision 1.147  2005/12/09 18:09:27  gregg
 * update
 *
 * Revision 1.146  2005/12/08 22:05:01  gregg
 * defaulting some pubto's for sake of compare
 *
 * Revision 1.145  2005/12/08 19:40:11  gregg
 * date formatting for below said change
 *
 * Revision 1.144  2005/12/08 19:28:57  gregg
 * fix
 *
 * Revision 1.143  2005/12/08 19:03:05  gregg
 * debug stmt fix
 *
 * Revision 1.142  2005/12/08 18:59:08  gregg
 * LSEO WARR filtering/processing
 *
 * Revision 1.141  2005/12/06 23:28:42  gregg
 * oslevel_fc fix
 *
 * Revision 1.140  2005/12/01 21:16:29  gregg
 * debug stmt
 *
 * Revision 1.139  2005/11/30 17:54:12  gregg
 * LSEOBUNDLE->OSLEVEL
 *
 * Revision 1.138  2005/11/09 18:40:06  gregg
 * going for LSEOBUNDLES
 *
 * Revision 1.137  2005/11/04 18:03:56  gregg
 * null ptr fix
 *
 * Revision 1.136  2005/11/03 23:05:37  gregg
 * PUBFROM/PUBTO logics
 *
 * Revision 1.135  2005/10/28 17:03:04  gregg
 * columns object allowNull property to return null or ""
 *
 * Revision 1.134  2005/10/18 21:14:39  gregg
 * add LANGUAGES column
 *
 * Revision 1.133  2005/10/07 18:22:08  gregg
 * add WARRTYPE/WARRPRIOD cols
 *
 * Revision 1.132  2005/09/30 20:13:57  gregg
 * some LCTO/MODEl etype mapping
 *
 * Revision 1.131  2005/09/30 20:11:14  gregg
 * *** empty log message ***
 *
 * Revision 1.130  2005/09/30 17:56:55  gregg
 * update props
 *
 * Revision 1.129  2005/09/29 00:57:22  gregg
 * catch npe
 *
 * Revision 1.128  2005/09/23 17:35:03  gregg
 * fix
 *
 * Revision 1.127  2005/09/23 17:26:02  gregg
 * some set/getAttributes work
 *
 * Revision 1.126  2005/09/23 17:08:19  gregg
 * compile fix
 *
 * Revision 1.125  2005/09/23 17:00:11  gregg
 * CatDBTableRecord now extends CatItem
 *
 * Revision 1.124  2005/09/22 23:15:37  gregg
 * going for putStringVal, etc...
 *
 * Revision 1.123  2005/09/22 22:45:22  gregg
 * compile fix
 *
 * Revision 1.122  2005/09/22 22:44:06  gregg
 * justa debug
 *
 * Revision 1.121  2005/09/22 22:18:08  gregg
 * move some common getAttribtue functionality up to CatObject
 *
 * Revision 1.120  2005/09/22 21:49:10  gregg
 * some date formatting
 *
 * Revision 1.119  2005/09/22 21:35:46  gregg
 * null ptr fix
 *
 * Revision 1.118  2005/09/22 20:48:08  gregg
 * some property-izing and such
 *
 * Revision 1.117  2005/09/22 00:15:35  gregg
 * ensure no null dates
 *
 * Revision 1.116  2005/09/21 23:34:15  gregg
 * genericizing XML methods
 *
 * Revision 1.115  2005/09/21 23:26:40  gregg
 * some fixes toward using fields
 *
 * Revision 1.114  2005/09/21 22:37:50  gregg
 * fix
 *
 * Revision 1.113  2005/09/21 21:28:06  gregg
 * fixing some nulls
 *
 * Revision 1.112  2005/09/21 20:55:05  gregg
 * fixx
 *
 * Revision 1.111  2005/09/21 20:27:27  gregg
 * compile fixx
 *
 * Revision 1.110  2005/09/21 20:11:36  gregg
 * using CatDBTableRecord
 *
 * Revision 1.109  2005/09/21 18:09:15  gregg
 * shot at property-izing pdh attribute/catdb columns ...
 *
 * Revision 1.108  2005/09/21 16:20:17  gregg
 * debug
 *
 * Revision 1.107  2005/09/21 16:19:22  gregg
 * more trimming
 *
 * Revision 1.106  2005/09/21 15:50:09  gregg
 * a little trimming
 *
 * Revision 1.105  2005/09/20 22:59:14  gregg
 * catalog override is last
 *
 * Revision 1.104  2005/09/20 22:55:29  gregg
 * more mappings for MODEL Root
 *
 * Revision 1.103  2005/09/20 14:34:24  gregg
 * use NO_DATE_VAL
 *
 * Revision 1.102  2005/09/15 23:22:25  gregg
 * save null ptr
 *
 * Revision 1.101  2005/09/15 23:00:08  gregg
 * some changes
 *
 * Revision 1.100  2005/09/15 21:08:05  gregg
 * getAttributes fix
 *
 * Revision 1.99  2005/09/15 20:38:06  gregg
 * null ptr fix
 *
 * Revision 1.98  2005/09/14 20:19:04  gregg
 * streamlining setAttributes
 *
 * Revision 1.97  2005/09/14 17:42:58  gregg
 * map MODEL<->LCTO
 * general setAttribtue cleanup
 *
 * Revision 1.96  2005/09/13 22:20:29  gregg
 * some traces
 *
 * Revision 1.95  2005/09/13 20:16:39  gregg
 * nother constructor fix
 *
 * Revision 1.94  2005/09/13 20:08:54  gregg
 * add CatItem into ProdStructCollectionId Constructor
 *
 * Revision 1.93  2005/09/13 16:28:03  gregg
 * properly building ProdStructs for Products
 *
 * Revision 1.92  2005/09/12 22:13:58  gregg
 * 'NULL' Date fix
 *
 * Revision 1.91  2005/09/12 20:59:21  gregg
 * no null dates
 *
 * Revision 1.90  2005/09/12 20:37:51  gregg
 * ficx
 *
 * Revision 1.89  2005/09/12 20:29:14  gregg
 * fix
 *
 * Revision 1.88  2005/09/12 20:15:32  gregg
 * fixes
 *
 * Revision 1.87  2005/09/12 20:03:07  gregg
 * second try..
 *
 * Revision 1.86  2005/09/12 18:52:30  gregg
 * update
 *
 * Revision 1.85  2005/09/12 18:49:34  gregg
 * Sync Columns excercise pass one
 *
 * Revision 1.84  2005/08/31 15:42:03  gregg
 * remove merge conflict introduced by 1.83
 *
 * Revision 1.82  2005/08/16 16:52:58  tony
 * CatalogViewer
 *
 * Revision 1.81  2005/08/08 20:47:16  tony
 * Added getAttribute logic
 *
 * Revision 1.80  2005/08/08 18:54:24  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.79  2005/07/11 18:01:05  gregg
 * 4013 fix
 *
 * Revision 1.78  2005/06/24 18:40:41  gregg
 * smal fix in isCountryList
 *
 * Revision 1.77  2005/06/24 18:31:58  gregg
 * gettin there...
 *
 * Revision 1.76  2005/06/24 17:08:18  gregg
 * setting valon on profile
 *
 * Revision 1.75  2005/06/24 00:26:35  gregg
 * getChildrenBranch()
 *
 * Revision 1.74  2005/06/24 00:23:18  gregg
 * pruneSmc
 *
 * Revision 1.73  2005/06/23 21:48:03  gregg
 * some work on pruning..
 *
 * Revision 1.72  2005/06/22 22:55:02  gregg
 * pruneSmc
 *
 * Revision 1.71  2005/06/22 22:49:42  gregg
 * pruneSmc
 *
 * Revision 1.70  2005/06/22 21:31:00  gregg
 * fix for GBL4013
 *
 * Revision 1.69  2005/06/22 21:22:38  gregg
 * compile fix
 *
 * Revision 1.68  2005/06/22 21:17:16  gregg
 * change signature for processSyncMap
 *
 * Revision 1.67  2005/06/22 20:22:09  gregg
 * substring modelname
 *
 * Revision 1.66  2005/06/22 20:08:57  gregg
 * adding in MODEL VE logic
 *
 * Revision 1.65  2005/06/15 23:08:24  gregg
 * replace nulls w/ empty strings on getters
 *
 * Revision 1.64  2005/06/15 21:17:06  gregg
 * fix
 *
 * Revision 1.63  2005/06/15 21:11:17  gregg
 * more in setAttributes
 *
 * Revision 1.62  2005/06/15 19:55:51  gregg
 * MODELNAME == INTERNAL name (fornow?)
 *
 * Revision 1.61  2005/06/15 19:44:00  gregg
 * remove GENAREA
 *
 * Revision 1.60  2005/06/15 18:03:48  gregg
 * shorten placeholder constant "!"
 *
 * Revision 1.59  2005/06/15 16:58:22  gregg
 * debug stmt
 *
 * Revision 1.58  2005/06/15 16:47:53  gregg
 * GBL8983 for put()
 *
 * Revision 1.57  2005/06/14 20:44:45  gregg
 * variable access public
 *
 * Revision 1.56  2005/06/14 20:37:54  gregg
 * filling out data from PDH and such
 *
 * Revision 1.55  2005/06/13 04:35:34  dave
 * ! needs to be not !
 *
 * Revision 1.54  2005/06/13 04:02:06  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.53  2005/06/09 18:25:22  gregg
 * some more pdh pull logic
 *
 * Revision 1.52  2005/06/08 23:01:33  gregg
 * more syncmap
 *
 * Revision 1.51  2005/06/07 04:34:51  dave
 * working on commit control
 *
 * Revision 1.50  2005/06/02 20:34:09  gregg
 * fixing the parent/child collection reference mayhem
 *
 * Revision 1.49  2005/06/02 20:00:30  gregg
 * more compile
 *
 * Revision 1.48  2005/06/02 19:52:37  gregg
 * tracking down a null
 *
 * Revision 1.47  2005/06/02 18:34:54  gregg
 * compile, compile, compile
 *
 * Revision 1.46  2005/06/02 18:25:53  gregg
 * some debugs
 *
 * Revision 1.45  2005/06/02 18:01:24  gregg
 * s'more XML + dumps
 *
 * Revision 1.44  2005/06/02 04:55:11  dave
 * missed on abstract
 *
 * Revision 1.43  2005/06/01 20:36:41  gregg
 * get/setAttribute
 *
 * Revision 1.42  2005/05/27 00:55:17  dave
 * adding the merge method.
 *
 * Revision 1.41  2005/05/26 20:31:07  gregg
 * cleaning up source, type, case CONSTANTS.
 * let's also make sure we check for source in building objects within our collections.
 *
 * Revision 1.40  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.39  2005/05/26 00:06:06  dave
 * adding put to design by contract
 *
 * Revision 1.38  2005/05/25 20:57:39  gregg
 * getFatAndDeep methods to traverse the tree.
 *
 * Revision 1.37  2005/05/25 17:46:24  gregg
 * compile fixes
 *
 * Revision 1.36  2005/05/25 17:36:46  gregg
 * going for ProdStruct objects
 *
 * Revision 1.35  2005/05/25 16:52:41  gregg
 * more storing id references, building prodstruct, etc
 *
 * Revision 1.34  2005/05/24 23:04:45  gregg
 * changed sime variable names to be more consistent
 *
 * Revision 1.33  2005/05/24 21:45:29  gregg
 * advent of ProductCollection/Id
 *
 * Revision 1.32  2005/05/23 18:23:18  gregg
 * imports and such
 *
 * Revision 1.31  2005/05/23 18:16:20  gregg
 * use rdrs
 *
 * Revision 1.30  2005/05/23 18:04:12  gregg
 * column index
 *
 * Revision 1.29  2005/05/23 17:56:34  gregg
 * 4013 rs fix
 *
 * Revision 1.28  2005/05/23 17:05:25  gregg
 * compile
 *
 * Revision 1.27  2005/05/23 16:56:24  gregg
 * compile fix
 *
 * Revision 1.26  2005/05/23 16:48:45  gregg
 * move NLSID, Enterprise to Gami
 *
 * Revision 1.25  2005/05/23 16:38:37  gregg
 * main method
 *
 * Revision 1.24  2005/05/20 19:51:22  gregg
 * gbl4013 etc.
 *
 * Revision 1.23  2005/05/19 22:43:00  gregg
 * basics
 *
 * Revision 1.22  2005/05/17 20:34:21  bala
 * fix
 *
 */
package COM.ibm.eannounce.catalog;

/* import COM.ibm.eannounce.objects.EANFoundation; */
/* import COM.ibm.eannounce.objects.EANList; */
/* import COM.ibm.eannounce.objects.EANMetaFoundation; */
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EANFlagAttribute;
/* import COM.ibm.eannounce.objects.EANTextAttribute; */
/* import COM.ibm.eannounce.objects.EANMetaAttribute; */
import COM.ibm.eannounce.objects.MetaFlag;
/* import COM.ibm.eannounce.objects.ExtractActionItem; */
/* import COM.ibm.eannounce.objects.EntityList; */
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
/* import COM.ibm.opicmpdh.middleware.MiddlewareRequestException; */
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.D;
/* import java.util.Vector; */
import java.util.StringTokenizer;
/* import java.util.Set; */
/* import java.util.HashMap; */
/* import java.util.Enumeration; */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
/* import java.lang.reflect.Field; */
/* import java.lang.Package; */
/* import java.lang.reflect.InvocationTargetException; */
import java.util.Hashtable;

/**
 * This represent a generic product for IBM product internal Catalogues
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Product
    extends CatDBTableRecord {

    /* private static final String NA = "N/A"; */
    /* private static final String DONT_KNOW_YET = "!"; */

    public static final String LSEO_ENTITYTYPE = "LSEO";
    public static final String LSEOBUNDLE_ENTITYTYPE = "LSEOBUNDLE";
    public static final String AVAIL_ENTITYTYPE = "AVAIL";
    public static final String MODEL_ENTITYTYPE = "MODEL";
    public static final String ANNOUNCEMENT_ENTITYTYPE = "ANNOUNCEMENT";
    public static final String WWSEO_ENTITYTYPE = "WWSEO";
    public static final String FEATURE_ENTITYTYPE = "FEATURE";
    public static final String LCTO_ENTITYTYPE = "LCTO";
    public static final String CATLGOR_ENTITYTYPE = "CATLGOR";
    public static final String WARRANTY_ENTITYTYPE = "WARR";
    public static final String PRODSTRUCT_ENTITYTYPE = "PRODSTRUCT";

    public static final String ENTERPRISE = "ENTERPRISE";
    public static final String COUNTRYCODE = "COUNTRYCODE";
    public static final String LANGUAGECODE = "LANGUAGECODE";
    public static final String NLSID = "NLSID";
    public static final String COUNTRYLIST = "COUNTRYLIST";
    public static final String WWENTITYTYPE = "WWENTITYTYPE";
    public static final String WWENTITYID = "WWENTITYID";
    public static final String LOCENTITYTYPE = "LOCENTITYTYPE";
    public static final String LOCENTITYID = "LOCENTITYID";
    public static final String MODELNAME = "MODELNAME";
    public static final String WWPARTNUMBER = "WWPARTNUMBER";
    public static final String PARTNUMBER = "PARTNUMBER";
    public static final String MARKETINGDESC = "MARKETINGDESC";
    public static final String ANNDATE = "ANNDATE";
    public static final String WITHDRAWLDATE = "WITHDRAWLDATE";
    public static final String FOTDATE = "FOTDATE";
    public static final String PLANAVAILDATE = "PLANAVAILDATE";
    public static final String PUBFROMDATE = "PUBFROMDATE";
    public static final String PUBTODATE = "PUBTODATE";
    public static final String FLFILSYSINDC = "FLFILSYSINDC";
    public static final String FLFILSYSINDC_FC = "FLFILSYSINDC_FC";
    public static final String OFFCOUNTRY = "OFFCOUNTRY";
    public static final String OFFCOUNTRY_FC = "OFFCOUNTRY_FC";
    public static final String OSLEVEL = "OSLEVEL";
    public static final String OSLEVEL_FC = "OSLEVEL_FC";
    public static final String MKTGNAME = "MKTGNAME";
    public static final String SHORTDESC = "SHORTDESC";
    public static final String WARRTYPE = "WARRTYPE";
    public static final String WARRTYPE_FC = "WARRTYPE_FC";
    public static final String WARRPRIOD = "WARRPRIOD";
    public static final String WARRPRIOD_FC = "WARRPRIOD_FC";
    public static final String WARRPRIODUOM = "WARRPRIODUOM";
    public static final String WARRPRIODUOM_FC = "WARRPRIODUOM_FC";
    public static final String LANGUAGES = "LANGUAGES";
    public static final String LANGUAGES_FC = "LANGUAGES_FC";
    public static final String PUBLISHFLAG = "PUBLISHFLAG";
    public static final String FOTPUBLISHFLAG = "FOTPUBLISHFLAG";
    public static final String STATUS = "STATUS";
    public static final String STATUS_FC = "STATUS_FC";
    public static final String PROJCDNAM = "PROJCDNAM";
    public static final String PROJCDNAM_FC = "PROJCDNAM_FC";
    public static final String SVCPACBNDLTYPE = "SVCPACBNDLTYPE";
    public static final String SVCPACBNDLTYPE_FC = "SVCPACBNDLTYPE_FC";
    public static final String SPECMODDESGN = "SPECMODDESGN";
    public static final String SPECMODDESGN_FC = "SPECMODDESGN_FC";
    public static final String VALFROM = "VALFROM";
    public static final String VALTO = "VALTO";
    public static final String INVNAME = "INVNAME";
    //private boolean ISACTIVE = false;

    // Some flag mappings for audience...
    private static final Hashtable c_hashAudMap = new Hashtable();
    static {
		c_hashAudMap.put("10046","CBP");
		c_hashAudMap.put("10054","Shop");
		c_hashAudMap.put("10062","LE");
	}
    private String [][] flag_array = {
    		{"10046","CATADDTOCART",null},
    		{"10054","CATADDTOCART",null},
    		{"10062","CATADDTOCART",null},
    		{"10046","CATBUYABLE",null},
    		{"10054","CATBUYABLE",null},
    		{"10062","CATBUYABLE",null},
    		{"10046","CATCUSTIMIZE",null},
    		{"10054","CATCUSTIMIZE",null},
    		{"10062","CATCUSTIMIZE",null},
    		{"10046","CATHIDE",null},
    		{"10054","CATHIDE",null},
    		{"10062","CATHIDE",null},
    		{"10046","CATNEWOFF",null},
    		{"10054","CATNEWOFF",null},
    		{"10062","CATNEWOFF",null}
    };

    private String [] audience_array = {"CBP","LE","Shop"};


    public static final String TABLE_NAME = "PRODUCT";

    static {
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(ENTERPRISE, 8, false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(COUNTRYCODE, 2, false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(LANGUAGECODE, 2, false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeIntColumn(NLSID, false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(COUNTRYLIST, 8, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WWENTITYTYPE, 32, false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeIntColumn(WWENTITYID, false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(LOCENTITYTYPE, 32, false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeIntColumn(LOCENTITYID, false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(MODELNAME, 12, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WWPARTNUMBER, 12, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PARTNUMBER, 7, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(MARKETINGDESC, 150, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(ANNDATE, 10, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WITHDRAWLDATE, 10, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(FOTDATE, 10, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PLANAVAILDATE, 10, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PUBFROMDATE, 10, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PUBTODATE, 10, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(FLFILSYSINDC, 32, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(FLFILSYSINDC_FC, 8, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(OFFCOUNTRY, 25, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(OFFCOUNTRY_FC, 8, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(OSLEVEL, 13600, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(OSLEVEL_FC, 1024, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(MKTGNAME, 254, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SHORTDESC, 254, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WARRTYPE, 128, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WARRTYPE_FC, 8, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WARRPRIOD, 50, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WARRPRIOD_FC, 8, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WARRPRIODUOM, 128, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(WARRPRIODUOM_FC, 8, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(LANGUAGES, 32, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(LANGUAGES_FC, 8, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PUBLISHFLAG, 1, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(FOTPUBLISHFLAG, 1, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(STATUS, 16, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(STATUS_FC, 8, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PROJCDNAM, 50, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PROJCDNAM_FC, 8, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SVCPACBNDLTYPE, 32, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SVCPACBNDLTYPE_FC, 32, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SPECMODDESGN, 48, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SPECMODDESGN_FC, 8, true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(VALFROM, 26, false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(VALTO, 26, false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(INVNAME, 90, true);
    }

    private SyncMapCollection m_smc = null;

    private ProdStructCollection m_psc = null; // children container


    private boolean m_bSoftwareModel = false;
    private boolean m_bHardwareModel = false;
    boolean m_bSystemModel = false;
    private boolean m_bServiceModel = false;
    private boolean m_bBaseModel = false;
    private boolean m_bOperatingSystem = false;
	private EntityItem bundleHardwareModel;
    private String m_strAudien_fc = null; // we're gonna need this later.
    private String m_strPRCINDC_fc = null; // we're gonna need this later.
    private String m_strSPECBID_fc = null; // we're gonna need this later
    private String m_strSEOORDERCODE_fc = null;
    private String m_strBUNDLETYPE_fc = null; // we're gonna need this later
    private String m_PUBFROMDATE_CATLGOR = null; // we're gonna need this later
    private String m_PUBFROMDATE_AVAIL143 = null; // we're gonna need this later
    private String m_PUBFROMDATE_AVAIL146 = null; // we're gonna need this later
    private String m_PUBTODATE_CATLGOR = null; // we're gonna need this later
    private String m_PUBTODATE_AVAIL149 = null; // we're gonna need this later
    private String m_UNPUBDATEMTRGT = null; // We're gonna need this later
    private String m_PUBDATEMTRGT = null; // We're gonna need this later
    private Hashtable m_hashPrunedProdstructs = null;

    /**
     * Product lets get the Light
     * @param _cid
     */
    public Product(ProductId _cid) {
        super(TABLE_NAME, _cid);
        putStringVal(LOCENTITYTYPE, _cid.getEntityType());
        putIntVal(LOCENTITYID, _cid.getEntityId());
        m_hashPrunedProdstructs = new Hashtable();
        // TODO Auto-generated constructor stub
    }

    /**
     * Product - lets get the heavy
     * Version
     * @param _cid
     */
    public Product(ProductId _cid, Catalog _cat) {
        this(_cid);
        get(_cat);
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {

    }

    public void get(Catalog _cat) {
        //
        // When we are getting something out of the CatDB
        // We need to pull enterprise out of the ProductID.

        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();
        Profile prof = _cat.getCatalogProfile();

        ProductId pid = getProductId();
        ProductCollectionId pcid = pid.getProductCollectionId();
        GeneralAreaMapItem gami = pid.getGami();
        CatalogInterval cati = pcid.getInterval();

        String strEnterprise = gami.getEnterprise();
        String strLocEntityType = pid.getEntityType();
        int iLocEntityID = pid.getEntityId();
//        int iNLSID = gami.getNLSID();
        String strCountryList = gami.getCountryList();

        if (pcid.isByInterval() && pcid.isFromPDH()) {

            // Right now .. we are gong to have to rebuild this guy.. and
            // any other thing it may be resonsible for
            // at this point.. we will always need to

            if (this.getSmc() == null) {
                D.ebug(D.EBUG_WARN,"Cannot pull out of the PDH since there is no SycnMap for me.");
                return;
            }

            //
            // Lets set the valon's in the profile for the Catalog to
            // I am not sure i like setting them here.
            //

            prof.setEffOn(cati.getEndDate());
            prof.setValOn(cati.getEndDate());

            try {

                EntityItem eiRoot = Catalog.getEntityItem(_cat, pid.getEntityType(), pid.getEntityId());
                this.setAttributes(_cat,eiRoot);
                db.debug(D.EBUG_SPEW, "get() EntityItem is:" + pid.getEntityType());

                //
                // O.K.  here is where I have to go get other things If I have to find kiddies.
                // If we have a sync map collection.. lets get it and search for other things
                //
                if (this.hasSyncMapCollection()) {

                    db.debug(D.EBUG_SPEW, "SyncMapCollection (count=" + this.getSmc().getCount() + "): " + this.getSmc().toString());

                    //
                    // Okay, now any "children" entities...
                    //
                    for (int i = 0; i < this.getSmc().getCount(); i++) {
                        SyncMapItem smi = getSmc().get(i);

                        // putting in fix to only process active children entities - RQK 02282008
                        if (!smi.getChildTran().equals("ON")) {
                            db.debug(D.EBUG_SPEW, "Ignoring "+this.getClass().getName()
                            		+ "smi.getChildType():" + smi.getChildType());
                        	continue;
                        }

                        db.debug(D.EBUG_SPEW, this.getClass().getName() + "smi.getChildType():" + smi.getChildType());

                        if (smi.getChildType().equals(MODEL_ENTITYTYPE)
                        		|| smi.getChildType().equals(AVAIL_ENTITYTYPE)
                        		|| smi.getChildType().equals(WWSEO_ENTITYTYPE)
                        		|| smi.getChildType().equals(ANNOUNCEMENT_ENTITYTYPE)
                        		|| (smi.getChildType().equals(WARRANTY_ENTITYTYPE)
                        				&& !pid.isLSEO()
                        				&& !pid.isLSEOBUNDLE())
                        		|| smi.getChildType().equals(LSEO_ENTITYTYPE)
                        		|| smi.getChildType().equals(LSEOBUNDLE_ENTITYTYPE)) {
                            db.debug(D.EBUG_SPEW, this.getClass().getName() + ".get(): DP1" + smi.toString());

                            //Different logic for AVAIL_ENTITYTYPE
                            if (smi.getChildType().equals(AVAIL_ENTITYTYPE)) {
                            	EntityItem eiAvailEntity = Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID());
                            	String[] saORDERSYSNAME = getAttributeValue(eiAvailEntity, "ORDERSYSNAME");
                            	if (isCountryList(_cat,gami,smi.getChildType(),smi.getChildID())
                            		&& (saORDERSYSNAME != null
                            				&& saORDERSYSNAME[1] != null
                            				&& saORDERSYSNAME[1].equals("4142"))
                            		&& (smi.getChildPath().equals("MODELAVAIL")
                            				|| smi.getChildPath().equals("LSEOAVAIL")
                            				|| smi.getChildPath().equals("LSEOBUNDLEAVAIL"))) {
                                 this.setAttributes(_cat,eiAvailEntity);
                            	}
                            } else {
                            	this.setAttributes(_cat,Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID()));
                            }




                        } // end if model, avail, wwseo ... etc
                    } // end for loop
                    //
                    // Done w/ our first pass...
                    //

                    // sent to its own method below
                    processWarrs(_cat,db,pid,cati.getEndDate().substring(0, 10));

                    setOsLevels(_cat,db, pid);

                    //
                    // CATALOGOVERRIDE IS LAST, YAY!!!
                    //
                    for (int i = 0; i < this.getSmc().getCount(); i++) {
                        SyncMapItem smi = getSmc().get(i);

                        db.debug(D.EBUG_SPEW, this.getClass().getName() + "smi.getChildType():" + smi.getChildType());

                        if (smi.getChildType().equals(CATLGOR_ENTITYTYPE) && smi.getChildTran().equals("ON")) {
                            db.debug(D.EBUG_SPEW, this.getClass().getName() + ".get(): DP1" + smi.toString());
                            this.setAttributes(_cat,Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID()));
                        }
                    }
                    for (int i=0; i<flag_array.length; i++)
                    	db.debug(D.EBUG_SPEW,flag_array[i][0] + " " + flag_array[i][1] + " " + flag_array[i][2]);
                    //
                    // Derived fields...
                    //
                    //
                    // special date logic spanning different entities...
                    //

                    setPubDates(pid);
                    calcDerivedFields();

                }

            }
            finally {
                //TODO
            }
            //
            // Now we have to put the attributes in the right spot
            //

        }
        else if (getProductCollectionId().isFromCAT()) {
            try {
                D.ebug(D.EBUG_SPEW,"Product: isFromCat()");
                rs = db.callGBL4013(new ReturnStatus( -1), strEnterprise, strLocEntityType, iLocEntityID, strCountryList);

                ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);

                for (int i = 0; i < rdrs.getRowCount(); i++) {

                    putStringVal(COUNTRYCODE, rdrs.getColumn(i, 1));
                    putStringVal(LANGUAGECODE, rdrs.getColumn(i, 2));
                    putStringVal(WWENTITYTYPE, rdrs.getColumn(i, 5));
                    putIntVal(WWENTITYID, rdrs.getColumnInt(i, 6));
                    putStringVal(LOCENTITYTYPE, rdrs.getColumn(i, 7));
                    putIntVal(LOCENTITYID, rdrs.getColumnInt(i, 8));
                    putStringVal(MODELNAME, rdrs.getColumn(i, 9));
                    putStringVal(WWPARTNUMBER, rdrs.getColumn(i, 10));
                    putStringVal(PARTNUMBER, rdrs.getColumn(i, 11));
                    putStringVal(MARKETINGDESC, rdrs.getColumn(i, 12));
                    putStringVal(ANNDATE, rdrs.getColumn(i, 13));
                    putStringVal(WITHDRAWLDATE, rdrs.getColumn(i, 14));
                    putStringVal(FOTDATE, rdrs.getColumn(i, 15));
                    putStringVal(FLFILSYSINDC, rdrs.getColumn(i, 16));
                    putStringVal(FLFILSYSINDC_FC, rdrs.getColumn(i, 17));
                    putStringVal(PUBLISHFLAG, rdrs.getColumn(i, 20));
                    putStringVal(STATUS, rdrs.getColumn(i, 21));
                    putStringVal(STATUS_FC, rdrs.getColumn(i, 22));
                    putStringVal(PROJCDNAM, rdrs.getColumn(i, 21));
                    putStringVal(PROJCDNAM_FC, rdrs.getColumn(i, 22));
                    putStringVal(VALFROM, rdrs.getColumn(i, 23));
                    putStringVal(VALTO, rdrs.getColumn(i, 24));
                    setActive(rdrs.getColumnInt(i, 25) == 1);
                    putStringVal(SVCPACBNDLTYPE, rdrs.getColumn(i, 26));
                    putStringVal(SVCPACBNDLTYPE_FC, rdrs.getColumn(i, 27));
                    putStringVal(INVNAME, rdrs.getColumn(i, 28));
                }

            }
            catch (SQLException _ex) {
                _ex.printStackTrace();

            }
            catch (MiddlewareException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally {
                try {
                    rs.close();
                    rs = null;
                    db.commit();
                    db.freeStatement();
                    db.isPending();
                }
                catch (SQLException _ex) {
                    _ex.printStackTrace();
                }
            }
        }
    }

/**
 * OSLEVEL processing goes here.
 */
    private final void setOsLevels(Catalog _cat, Database _db, ProductId _pid) {
        //
        // Removed LSEO.OSLEVEL from product table per Hsi-Ho's note on 2/28
        //

        if (_pid.isLSEOBUNDLE()) {
            // from spec:
            //
            // For LSEO:
            // If the LSEO grandparent MODEL is classified COFCAT="Software", the OSLEVEL information is taken
            // from the SWFEATURE that is categorized as SWFCCAT = "ValueMetric" feature on the LSEO via
            // LSEOSWPRODSTRUCT and/or WWSEOSWPRODSTRUCT through SWPRODSTRUCT.
            //
            // LSEOBUNDLE:
            // Find the LSEO with a grandparent MODEL classified COFCAT="Software". Then proceed as in the prior
            // description for LSEO.
            //
            if (isSoftwareModel() && isOperatingSystem()) {
                for (int i = 0; i < this.getSmc().getCount(); i++) {
                    SyncMapItem smi = getSmc().get(i);
                    if (smi.getChildType().equals("SWPRODSTRUCT")) {
                        EntityItem eiSWFEATURE = Catalog.getEntityItem(_cat, smi.getEntity1Type(), smi.getEntity1ID());
                        String[] saSWCCAT = getAttributeValue(eiSWFEATURE, "SWFCCAT");
                        if (saSWCCAT[1] != null && saSWCCAT[1].equals("319")) {
                            String[] saOSLEVEL = getAttributeValue(eiSWFEATURE, "OSLEVEL");

                            _db.debug(D.EBUG_SPEW,((_pid.isLSEOBUNDLE()?"LSEOBUNDLE":"LSEO") + " OSLEVEL:" + saOSLEVEL[0]));

                            if(saOSLEVEL[0] != null && saOSLEVEL[0].indexOf("|") > -1) {
                                putStringVal(OSLEVEL, saOSLEVEL[0].substring(0,saOSLEVEL[0].indexOf("|")));
                                putStringVal(OSLEVEL_FC, saOSLEVEL[1].substring(0,saOSLEVEL[1].indexOf("|")));
							} else {
                                putStringVal(OSLEVEL, saOSLEVEL[0]);
                                putStringVal(OSLEVEL_FC, saOSLEVEL[1]);
							}
                        }
                    }
                }
            }
	   }

        //
        // Pulled this all out form prodstruct logic for LCTO. Because we will use all OSLEVEL vals for these.
        //

	}

/**
 * Set PubFromDate/PubToDates
 *  */
    private final void setPubDates(ProductId _pid) {

    if (_pid.isMODEL()) {
    	if (m_PUBFROMDATE_CATLGOR != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_CATLGOR);
//    	else if (m_PUBFROMDATE_AVAIL143 != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_AVAIL143);
    	else if (m_PUBFROMDATE_AVAIL146 != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_AVAIL146);
    	if (m_PUBTODATE_CATLGOR != null) putStringVal("PUBTODATE",m_PUBTODATE_CATLGOR);
    	else if (m_PUBTODATE_AVAIL149 != null) putStringVal("PUBTODATE",m_PUBTODATE_AVAIL149);
      }
    else if (_pid.isLSEO()) {
    	if (m_strSPECBID_fc != null && m_strSPECBID_fc.equals("11458")) {
    		if (m_PUBFROMDATE_CATLGOR != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_CATLGOR);
        	else if (m_PUBDATEMTRGT != null) putStringVal("PUBFROMDATE",m_PUBDATEMTRGT);
    		if (m_PUBTODATE_CATLGOR != null) putStringVal("PUBTODATE",m_PUBTODATE_CATLGOR);
        	else if (m_UNPUBDATEMTRGT != null) putStringVal("PUBTODATE",m_UNPUBDATEMTRGT);
        }
    	else {
    		if (m_PUBFROMDATE_CATLGOR != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_CATLGOR);
//        	else if (m_PUBFROMDATE_AVAIL143 != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_AVAIL143);
        	else if (m_PUBFROMDATE_AVAIL146 != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_AVAIL146);
    		if (m_PUBTODATE_CATLGOR != null) putStringVal("PUBTODATE",m_PUBTODATE_CATLGOR);
    		else if (m_PUBTODATE_AVAIL149 != null) putStringVal("PUBTODATE",m_PUBTODATE_AVAIL149);
		}
    }
    else if (_pid.isLSEOBUNDLE()) {
    	if (m_strSPECBID_fc != null && m_strSPECBID_fc.equals("11458")) {
    		if (m_PUBFROMDATE_CATLGOR != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_CATLGOR);
        	else if (m_PUBDATEMTRGT != null) putStringVal("PUBFROMDATE",m_PUBDATEMTRGT);
    		if (m_PUBTODATE_CATLGOR != null) putStringVal("PUBTODATE",m_PUBTODATE_CATLGOR);
        	else if (m_UNPUBDATEMTRGT != null) putStringVal("PUBTODATE",m_UNPUBDATEMTRGT);
        }
    	else {
    		if (m_PUBFROMDATE_CATLGOR != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_CATLGOR);
//        	else if (m_PUBFROMDATE_AVAIL143 != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_AVAIL143);
        	else if (m_PUBFROMDATE_AVAIL146 != null) putStringVal("PUBFROMDATE",m_PUBFROMDATE_AVAIL146);
    		if (m_PUBTODATE_CATLGOR != null) putStringVal("PUBTODATE",m_PUBTODATE_CATLGOR);
    		else if (m_PUBTODATE_AVAIL149 != null) putStringVal("PUBTODATE",m_PUBTODATE_AVAIL149);
		}
    }
//    private String m_strSPECBID_fc = null; // we're gonna need this later
//    private String m_strBUNDLETYPE_fc = null; // we're gonna need this later
//    private String m_PUBFROMDATE_CATLGOR = null; // we're gonna need this later
//    private String m_PUBFROMDATE_AVAIL143 = null; // we're gonna need this later
//    private String m_PUBFROMDATE_AVAIL146 = null; // we're gonna need this later
//    private String m_PUBTODATE_CATLGOR = null; // we're gonna need this later
//    private String m_PUBTODATE_AVAIL149 = null; // we're gonna need this later
//    private String m_UNPUBDATEMTRGT = null; // We're gonna need this later

    /* ResultSet rs1 = null;
        ReturnDataResultSet rdrs1 = null;
        if(getAudien_fc() != null) {
        	StringTokenizer stAud = new StringTokenizer(getAudien_fc(),"|");
        	while(stAud.hasMoreTokens()) {
				String strAudTok = stAud.nextToken();
				String strAudien_fc_one = (String)c_hashAudMap.get(strAudTok);
				if(strAudien_fc_one != null) {
        			try {
        			    rs1 = _db.callGBL4027(new ReturnStatus(-1),_iNLSID,_strRootEntityType,getStringVal("PARTNUMBER"),strAudien_fc_one,_strValOn);
        			    rdrs1 = new ReturnDataResultSet(rs1);
        			    _db.debug(D.EBUG_SPEW,"GBL4027 record count:" + rdrs1.size());
        			    if(rdrs1.size() > 0) {
							int iCATLGPUBID = rdrs1.getColumnInt(0,0);
							String strPubFrom = rdrs1.getColumn(0,1);
							String strPubTo = rdrs1.getColumn(0,2);
        			    	_db.debug(D.EBUG_SPEW,"GBL4027: We Found A Match for " + _strRootEntityType + ":" + _iRootEntityID + "-->CATLGPUB:" + iCATLGPUBID + ":" + strPubFrom + ":" + strPubTo);
						    putStringVal("PUBFROMDATE",strPubFrom);
						    putStringVal("PUBTODATE",strPubTo);
						} else {
        			    	_db.debug(D.EBUG_WARN,"GBL4027: NO Match for " + _strRootEntityType + ":" + _iRootEntityID);
						}
        			} catch (SQLException _ex) {
        			    _ex.printStackTrace();
        			} catch (MiddlewareException e) {
        			    // TODO Auto-generated catch block
        			e.printStackTrace();
        			} finally {
        				try {
        			    	rs1.close();
        			    	rs1 = null;
        			    	_db.commit();
        			    	_db.freeStatement();
        			    	_db.isPending();
        				} catch (SQLException _ex) {
        			        _ex.printStackTrace();
        			    }
        			}
				} else {
		    		_db.debug(D.EBUG_WARN,"GBL4027: strAudien_fc_one (mapped token) is null for " + _strRootEntityType + ":" + _iRootEntityID + ":" + strAudTok);
				}
			}
		} else {
		    _db.debug(D.EBUG_WARN,"GBL4027: AUDIEN flag is null for " + _strRootEntityType + ":" + _iRootEntityID);
		}
        // */
	}

	/**
	 * WARR processing for LSEO and LSEOBUNDLE.
	 */
    private final void processWarrs(Catalog _cat, Database _db, ProductId _pid, String _strNow) {
    	GeneralAreaMapItem gami = _pid.getGami();
        /*
         * LSEO root WARR processing
         * A) Case 1: Option - gets from WWSEOPRODSTRUCT -> PRODSTRUCTWARR first. If not there then it gets from MODELWARR
         * B) Case 2: Non Option - gets only from MODELWARR
         */
        if (_pid.isLSEO()) {
            boolean bWarrFound = false;
            String lseoID = _pid.getEntityType() + " ("+_pid.getEntityId()+") ";

            _db.debug(D.EBUG_SPEW, this.getClass().getName()
        			+ " processWarrs() "+lseoID+" is OPTION = "+isOption());

            // A) Is Option, try to get WARR from PRODSTRUCTWARR
            if (isOption()) {
            	_db.debug(D.EBUG_SPEW, this.getClass().getName()
            			+ " processWarrs() "+lseoID+" looking for PRODSTRUCTWARR");
            	for (int i = 0; i < this.getSmc().getCount(); i++) {
                    SyncMapItem smi = getSmc().get(i);
                    if (smi.getChildType().equals("PRODSTRUCTWARR")
                    		&& smi.getChildTran().equals("ON")) {
                    	EntityItem eiPRODSTRUCTWARR = Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID());
                    	if ( isValidEffectiveDate(_db, _strNow, eiPRODSTRUCTWARR)
                    			&& isValidCountryList(_db, gami, eiPRODSTRUCTWARR)) {
                    		EntityItem eiPRODSTRUCT = Catalog.getEntityItem(_cat, smi.getEntity1Type(), smi.getEntity1ID());
                    		//if (isValidStatus(_db, _strNow, eiPRODSTRUCT)) {
	                    		EntityItem eiWARR = Catalog.getEntityItem(_cat, smi.getEntity2Type(), smi.getEntity2ID());
	                    		bWarrFound = true;
	                    		_db.debug(D.EBUG_SPEW,
	                                    this.getClass().getName() + " processWarrs() found "+
	                                     eiWARR.getKey()+" in PRODSTRUCTWARR for "+lseoID);
	                            this.setAttributes(_cat,eiWARR);
	                            break;
                    		//}
                    	}
                    }
            	}
            }
            //B) Is non-option or no WARR found in PRODSTRUCTWARR
            if (!bWarrFound) {
            	_db.debug(D.EBUG_SPEW, this.getClass().getName()
            			+ " processWarrs() "+lseoID+" looking for MODELWARR");
                for (int i = 0; i < this.getSmc().getCount(); i++) {
                    SyncMapItem smi = getSmc().get(i);

                    // find the MODELWARR and make sure that it is active
                    if (smi.getChildType().equals("MODELWARR") && smi.getChildTran().equals("ON")) {
                    	_db.debug(D.EBUG_SPEW,
                    			this.getClass().getName() + " processWarrs() found MODELWARR for " +
                    			smi.getEntity1Type()+" "+smi.getEntity1ID());

                    	EntityItem eiMODELWARR = Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID());
                    	if (isValidEffectiveDate(_db, _strNow, eiMODELWARR)
                    			&& isValidCountryList(_db, gami, eiMODELWARR)) {
                    		EntityItem eiMODEL = Catalog.getEntityItem(_cat, smi.getEntity1Type(), smi.getEntity1ID());
                    		//if (isValidStatus(_db, _strNow, eiMODEL)) {
                    			EntityItem eiWARR = Catalog.getEntityItem(_cat, smi.getEntity2Type(), smi.getEntity2ID());
                    			bWarrFound = true;
	                    		_db.debug(D.EBUG_SPEW,
	                                    this.getClass().getName() + " processWarrs() found "+
	                                     eiWARR.getKey()+" in MODELWARR for "+lseoID);
	                            this.setAttributes(_cat,eiWARR);
	                            break;
                    		//}
                    	}
                    }
                }
            }
        }

        /*
         * Process Warranties for LSEOBUNDLE
         * At this point, the we know if the bundle contains a hardware model
         * (see setAttributes() and the entity item for the model).
         * Check if the warranty comes from the bundled hardware otherwise bundle has no warranties
         */

        if (_pid.isLSEOBUNDLE()) {

        	String bundleID = _pid.getEntityType() + " ("+_pid.getEntityId()+") ";

        	//Check if the warranty comes from the bundled hardware
        	if (isHardwareBundle()) {
        		boolean warrantyFound = false;
        		_db.debug(D.EBUG_SPEW, this.getClass().getName()
						+ " processWarrs() "+bundleID+" (isHardwareBundle) looking for MODELWARR");

        		for (int i = 0; i < this.getSmc().getCount(); i++) {
					SyncMapItem smi = getSmc().get(i);

					// find the MODELWARR and make sure that it is active
					if (smi.getChildType().equals("MODELWARR") && smi.getChildTran().equals("ON")) {
						_db.debug(D.EBUG_SPEW, this.getClass().getName()
								+ " processWarrs() "+bundleID+" (MODELWARR_FOUND) Looking for: " +
									bundleHardwareModel.getEntityID()+ " == " + smi.getEntity1ID());

						//Find the relator that matches the bundle's model
						if (bundleHardwareModel.getEntityID() == smi.getEntity1ID()) {
							_db.debug(D.EBUG_SPEW, this.getClass().getName()
									+ " processWarrs() Found MODELWARR for: "+bundleHardwareModel.getEntityID());
							EntityItem eiMODELWARR = Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID());
							//if (isValidStatus(_db, _strNow, bundleHardwareModel) &&
							if (isValidCountryList(_db, gami, eiMODELWARR)
								&& isValidEffectiveDate(_db, _strNow, eiMODELWARR)) {
								//Get the WARR entity related to the model
								EntityItem eiWARR = Catalog.getEntityItem(_cat, smi.getEntity2Type(), smi.getEntity2ID());
								_db.debug(D.EBUG_SPEW, this.getClass().getName()
										+ " processWarrs() Using Warranty from bundled hardware: "+
										bundleID + " - "+ bundleHardwareModel.getKey() + " -> "+eiWARR.getKey());
								setAttributes(_cat,eiWARR);
								warrantyFound = true;
								break; // Found warranty, stop loop
							}
						}
					}

				}
        		if (!warrantyFound) {
        			_db.debug(D.EBUG_SPEW,
    						this.getClass().getName()
    								+ " processWarrs() "+bundleID+" has no WARR");
        		}
        	}
        }

	}

    private boolean isValidCountryList(Database _db, GeneralAreaMapItem gami, EntityItem eiCheck) {
        String strCountryListGami = gami.getCountryList();
        EANFlagAttribute faCOUNTRYLIST = (EANFlagAttribute) eiCheck.getAttribute("COUNTRYLIST");
        _db.debug(D.EBUG_SPEW,getClass().getName() +
                " processWarrs() isValidCountryList: " + eiCheck.getKey() + " COUNTRYLIST=" + faCOUNTRYLIST);
        if (faCOUNTRYLIST == null || faCOUNTRYLIST.toString().equals("")) {
            return false;
        }
        MetaFlag[] amf = (MetaFlag[]) faCOUNTRYLIST.get();
        for (int i = 0; i < amf.length; i++) {
            String strFlagCode = amf[i].getFlagCode();
            if (amf[i].isSelected() && strFlagCode.equals(strCountryListGami)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidStatus(Database _db, String _strNow, EntityItem eiENTITY) {
        String[] saSTATUS = getAttributeValue(eiENTITY, "STATUS");
        _db.debug(D.EBUG_SPEW,getClass().getName() +
                " processWarrs() isValidStatus: " + eiENTITY.getKey() + " STATUS_FC=" + saSTATUS[1]);
        return saSTATUS[1] != null
        		&& (saSTATUS[1].equals("0020") || saSTATUS[1].equals("0040"));
    }

    private boolean isValidEffectiveDate(Database _db, String _strNow, EntityItem eiRELATOR) {
    	String[] saEFFECTIVEDATE = getAttributeValue(eiRELATOR, "EFFECTIVEDATE");
        String[] saENDDATE = getAttributeValue(eiRELATOR, "ENDDATE");
        if (saENDDATE[0] == null) {
            saENDDATE[0] = "9999-12-31";
        }
        if (saEFFECTIVEDATE[0] == null) {
            saEFFECTIVEDATE[0] = "1980-01-01";
        }
        _db.debug(D.EBUG_SPEW, getClass().getName() +
                 " processWarrs() isValidEffectiveDate: " + eiRELATOR.getKey() + " now=" + _strNow +
                 ",EFFECTIVEDATE=" + saEFFECTIVEDATE[0] + ",ENDDATE=" + saENDDATE[0]);
        return (saEFFECTIVEDATE[0] != null && saEFFECTIVEDATE[0].compareTo(_strNow) <= 0)
           		&& (saENDDATE[0] != null && _strNow.compareTo(saENDDATE[0]) <= 0);
    }


    public void getReferences(Catalog _cat, int _iCase) {
        // fill out references
        int iSource = getProductCollectionId().getSource();
        int iType = getProductCollectionId().getType();
        // GAB 033106 - now the reason we want this, is because we are reall grabbing full CURRENT images for EACH prodstruct under this product..
        //              Because we have already deactivated all ps records for this product in the catdb.
        int iPSType = iType;
        if(iPSType == CollectionId.NET_CHANGES) {
			iPSType = CollectionId.FULL_IMAGES;
		}
        //
        CatalogInterval catInt = getProductCollectionId().getInterval();
        ProdStructCollectionId pscid = new ProdStructCollectionId(getProductId(), iSource, iPSType, _iCase, catInt);
        D.ebug(D.EBUG_SPEW, "Product.getReferences(PRODSTRUCT_REFERENCE) - making new PSC from pscid" + pscid);
        setProdStructCollection(new ProdStructCollection(pscid));
        //
        // Lets share the SMC stuff
        //
        if (this.hasSyncMapCollection()) {
            D.ebug(D.EBUG_SPEW, "Product.getReferences(PRODSTRUCT_REFERENCE) - setting wwpc's SMC");
            getProdStructCollection().setSmc(this.getSmc());
        }
        //
        // Lets get a list of Stubs
        //
        D.ebug(D.EBUG_SPEW, "Product.getReferences(PRODSTRUCT_REFERENCE) - lets go stub pout the PSC" + pscid);
        getProdStructCollection().get(_cat);
        setDeep(true);
    }

    /**
     * This is going to fill out ALL Data (fat) and ALL references (deep) _iLvl's deep.
     */
    public void getFatAndDeep(Catalog _cat, int _iLvl) {
        if (_iLvl < 0) {
            return;
        }
        _iLvl--;
        get(_cat);
        getReferences(_cat, ProdStructCollectionId.BY_LOCENTITYTYPE_LOCENTITYID);
        if (isProdStructCollectionLoaded()) { // it should be!
            getProdStructCollection().getFatAndDeep(_cat, _iLvl);
        }
    }

///////////////
///////////////


    public void put(Catalog _cat, boolean _bcommit) {
        if (Catalog.isDryRun()) {
            D.ebug(D.EBUG_SPEW, this.getClass().getName() + " >>> Catalog.isDryRun() == true!!! Skipping put()! <<<");
            return;
        }
        Database db = _cat.getCatalogDatabase();
        ReturnStatus rets = new ReturnStatus( -1);
        ProductId pid = this.getProductId();
        GeneralAreaMapItem gami = pid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        String strCountryList = gami.getCountryList();
        int iNLSID = gami.getNLSID();
        String strLocEntityType = pid.getEntityType();
        /* int iLocEntityID = pid.getEntityId(); */

        if (pid.isMODEL() && !isBaseModel() && !isServiceModel()) {
        	this.setActive(false);
        }
        if (pid.isLSEO() && !(isHardwareModel() && isBaseModel()) &&
        		!isServiceModel() && !(isSoftwareModel() && isBaseModel())) {
        	this.setActive(false);
        }
        if (this.getStringVal(PUBFROMDATE)== null) {
        	this.setActive(false);
        }

        if (!pid.isMODEL() && !isCountryList(_cat,gami,strLocEntityType,this.getIntVal(LOCENTITYID)))
        		this.setActive(false);

        try {
            db.callGBL8983(rets, strEnterprise, strCountryCode, strLanguageCode, iNLSID, strCountryList,
                this.getStringVal(WWENTITYTYPE), this.getIntVal(WWENTITYID), strLocEntityType, this.getIntVal(LOCENTITYID),  //this.getStringVal(LOCENTITYTYPE),
                this.getStringVal(MODELNAME), this.getStringVal(WWPARTNUMBER), this.getStringVal(PARTNUMBER),
                this.getStringVal(MARKETINGDESC),
                (this.getStringVal(ANNDATE) == null ? "NULL" : this.getStringVal(ANNDATE)),
                (this.getStringVal(WITHDRAWLDATE) == null ? "NULL" : this.getStringVal(WITHDRAWLDATE)),
                (this.getStringVal(FOTDATE) == null ? "NULL" : this.getStringVal(FOTDATE)),
                (this.getStringVal(PLANAVAILDATE) == null ? "NULL" : this.getStringVal(PLANAVAILDATE)),
                (this.getStringVal(PUBFROMDATE) == null ? "NULL" : this.getStringVal(PUBFROMDATE)),
                (this.getStringVal(PUBTODATE) == null ? "NULL" : this.getStringVal(PUBTODATE)),
                this.getStringVal(FLFILSYSINDC), this.getStringVal(FLFILSYSINDC_FC),
                this.getStringVal(OFFCOUNTRY), this.getStringVal(OFFCOUNTRY_FC), this.getStringVal(OSLEVEL),
                this.getStringVal(OSLEVEL_FC), this.getStringVal(MKTGNAME), this.getStringVal(SHORTDESC),
                this.getStringVal(WARRTYPE), this.getStringVal(WARRTYPE_FC),
                (this.getStringVal(WARRPRIODUOM) == "" ? this.getStringVal(WARRPRIOD) :
                	this.getStringVal(WARRPRIOD) + " " + this.getStringVal(WARRPRIODUOM) ),
                this.getStringVal(WARRPRIOD_FC), this.getStringVal(LANGUAGES), this.getStringVal(LANGUAGES_FC),
                this.getStringVal(PUBLISHFLAG), this.getStringVal(FOTPUBLISHFLAG), this.getStringVal(STATUS),
                this.getStringVal(STATUS_FC), this.getStringVal(PROJCDNAM),this.getStringVal(PROJCDNAM_FC),(this.isActive() ? 1 : 0),
                this.getStringVal(SVCPACBNDLTYPE),this.getStringVal(SVCPACBNDLTYPE_FC),
                this.getStringVal(SPECMODDESGN),this.getStringVal(SPECMODDESGN_FC),this.getStringVal(INVNAME));

            if (_bcommit) {
                db.commit();
            }
            db.freeStatement();
            db.isPending();

            for (int i=0; i<audience_array.length; i++) {
            	boolean foundaud = false;

            if(getAudien_fc() != null) {
                StringTokenizer stAud1 = new StringTokenizer(getAudien_fc(),"|");
                while(stAud1.hasMoreTokens()) {
    				String strAudTok1 = stAud1.nextToken();
    				String strAudien_fc_one1 = (String)c_hashAudMap.get(strAudTok1);
    				if (strAudien_fc_one1 != null && strAudien_fc_one1.equals(audience_array[i])) {
    					foundaud = true;
    					db.callGBL8990(rets, strEnterprise, strCountryCode, strLanguageCode, iNLSID, strCountryList,
    						strLocEntityType, this.getIntVal(LOCENTITYID),
    					 	getFlagAttr(strAudTok1,"CATADDTOCART",pid),
    					   	getFlagAttr(strAudTok1,"CATBUYABLE",pid),
    					 	getFlagAttr(strAudTok1,"CATCUSTIMIZE",pid),
    					 	getFlagAttr(strAudTok1,"CATHIDE",pid),
    					 	getFlagAttr(strAudTok1,"CATNEWOFF",pid),
    					 	strAudien_fc_one1,
    						this.isActive() ? 1 : 0);
    					D.ebug(D.EBUG_SPEW,strEnterprise + " " +
    							strCountryCode + " " +
    							strLanguageCode + " " +
    							iNLSID + " " +
    							strCountryList + " " +
    	    					strLocEntityType + " " +
    	    					this.getIntVal(LOCENTITYID) + " " +
    	    					getFlagAttr(strAudTok1,"CATADDTOCART",pid) + " " +
    	    					getFlagAttr(strAudTok1,"CATBUYABLE",pid) + " " +
    	    					getFlagAttr(strAudTok1,"CATCUSTIMIZE",pid) + " " +
    	    					getFlagAttr(strAudTok1,"CATHIDE",pid) + " " +
    	    					getFlagAttr(strAudTok1,"CATNEWOFF",pid) + " " +
    	    					strAudien_fc_one1 + " " +
    	    					this.isActive());

    					if (_bcommit) {
    		                db.commit();
    		            }
    		            db.freeStatement();
    		            db.isPending();
    				} 							// end if
    			}								// end while
            }						            // end if
            if (!foundaud) {
            	db.callGBL8990(rets, strEnterprise, strCountryCode, strLanguageCode, iNLSID, strCountryList,
						strLocEntityType, this.getIntVal(LOCENTITYID),
					 	"null",
					   	"null",
					 	"null",
					 	"null",
					 	"null",
					 	audience_array[i],
						0);
            	if (_bcommit) {
	                db.commit();
	            }
	            db.freeStatement();
	            db.isPending();

            }									// end if
            }									// end for

         }
        catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub

    }

////////////////
////////////////
    /**
     * For debugging XML
     */
    public void dumpXML(XMLWriter _xml, boolean _bDeep) {
        generateXMLFragment(_xml);
        ProdStructCollection psc = getProdStructCollection();
        psc.dumpXML(_xml, _bDeep);
        if (_bDeep) {
            getProdStructCollection().dumpXML(_xml, _bDeep); //true
        }
    }

    public boolean isProdStructCollectionLoaded() {
        return (m_psc != null);
    }

    public ProdStructCollection getProdStructCollection() {
        return m_psc;
    }

    public ProductId getProductId() {
        return (ProductId)super.getId();
    }

    /**
     * Because we want to know how we got here..
     */
    public ProductCollectionId getProductCollectionId() {
        ProductId pid = getProductId();
        if (pid != null) {
            return pid.getProductCollectionId();
        }
        return null;
    }

    //public void setProductCollectionId(ProductCollectionId _pcid) {
    //    m_pcid = _pcid;
    //}

    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer("[PRODUCT]:");
        if (_bBrief) {
            sb.append(getStringVal(ENTERPRISE) + ",");
            sb.append(getStringVal(LOCENTITYTYPE) + ",");
            sb.append(getIntVal(LOCENTITYID) + ",");
            sb.append(getIntVal(NLSID) + ",");
        }
        else {
            sb.append(getStringVal(ENTERPRISE) + ",");
            sb.append(getStringVal(COUNTRYCODE) + ",");
            sb.append(getStringVal(LANGUAGECODE) + ",");
            sb.append(getIntVal(NLSID) + ",");
            sb.append(getStringVal(WWENTITYTYPE) + ",");
            sb.append(getIntVal(WWENTITYID) + ",");
            sb.append(getStringVal(LOCENTITYTYPE) + ",");
            sb.append(getIntVal(LOCENTITYID) + ",");
            sb.append(getStringVal(MODELNAME) + ",");
            sb.append(getStringVal(WWPARTNUMBER) + ",");
            sb.append(getStringVal(PARTNUMBER) + ",");
            sb.append(getStringVal(MARKETINGDESC) + ",");
            sb.append(getStringVal(ANNDATE) + ",");
            sb.append(getStringVal(WITHDRAWLDATE) + ",");
            sb.append(getStringVal(FOTDATE) + ",");
            sb.append(getStringVal(FLFILSYSINDC) + ",");
            sb.append(getStringVal(FLFILSYSINDC_FC) + ",");
            sb.append(getStringVal(PUBLISHFLAG) + ",");
            sb.append(getStringVal(STATUS) + ",");
            sb.append(getStringVal(STATUS_FC) + ",");
            sb.append(getStringVal(SPECMODDESGN) + ",");
            sb.append(getStringVal(SPECMODDESGN_FC) + ",");
            sb.append(getStringVal(INVNAME) + ",");
            sb.append(getStringVal(VALFROM) + ",");
            sb.append(getStringVal(VALTO) + ",");
            sb.append(isActive());
            if (isProdStructCollectionLoaded()) {
                sb.append("\n" + getProdStructCollection().dump(_bBrief));
            }
        }
        return sb.toString();
    }

    public String toString() {
        return dump(true);
    }

    /**
     * If I have an Smc Collection.  This means I have work to do to sync up to the CatDb
     *
     * @return
     */
    public SyncMapCollection getSmc() {
        return m_smc;
    }

    /**
     * If I have an Smc Collection.  This means I have work to do to sync up to the CatDb
     * @param collection
     */
    public void setSmc(SyncMapCollection collection) {
        m_smc = collection;
    }

    /**
     * hasSyncMapCollection
     *
     * @return
     */
    public final boolean hasSyncMapCollection() {
        return m_smc != null;
    }

    /**
     * setAttributes
     * sets all the attributes it can given the EntityItem that was passed
     * This handles all EntityTypes that have to run through this object
     * Here is where we flatten things out and apply any concatenation rules
     *
     * @param _ei
     */
    private void setAttributes(Catalog _cat,EntityItem _ei) {
        //
        // Process Attributes in order according to column number, and switch on Root/VE Type.
        //  Note, this is a bit different than WWProduct, which groups first by Root/VE Type.
        //
        D.ebug(D.EBUG_SPEW, this.getClass().getName() + " .setAttributes() for " + _ei.getEntityType() + ":" + _ei.getEntityID());

        ProductId pid = this.getProductId();
        GeneralAreaMapItem gami = pid.getGami();

        /* String strEnterprise = gami.getEnterprise(); */
        String strCountryCode = gami.getCountry();
        String strLanguageCode = gami.getLanguage();
        /* String strGenArea = gami.getGeneralArea(); */

        String strVE = "";
        if (pid.isMODEL()) {
            strVE = "LCTO";
        }
        else if (pid.isLSEO()) {
            strVE = "LSEO";
        }
        else if (pid.isLSEOBUNDLE()) {
            strVE = "LSEOBUNDLE";
        }

        D.ebug(D.EBUG_SPEW, "Using VE:\"" + strVE + "\"");
        // RQK 11192007 Lets not call set attributes from properties for a catalog override entity.
        // This prevents all the values which have been stowed away from being set back to the defaults just
        // because an override entity exists.
        if (!_ei.getEntityType().equals(CATLGOR_ENTITYTYPE)) {
        setAttributesFromProps(_ei, strVE);
        }
        // Really we are doing this once and only once.
        if (isRootEntity(_ei)) {
            // COUNTRYCODE
            putStringVal(COUNTRYCODE, strCountryCode);
            // LANGUAGECODE
            putStringVal(LANGUAGECODE, strLanguageCode);
            // COUNTRYLIST
            putStringVal(COUNTRYLIST, gami.getCountryList());
            // PUBLISHFLAG
            putStringVal(PUBLISHFLAG, "1");
            // FOTPUBLISHFLAG
            putStringVal(FOTPUBLISHFLAG, "1");

            // We need the audience for later pubto determination, this should always be on the root.
            String[] saAud = getAttributeValue(_ei, "AUDIEN");
            if(saAud != null) {
                m_strAudien_fc = saAud[1];
		    } else {
				D.ebug(D.EBUG_WARN, "Audience is null for:" + _ei.getKey());
			}
            if (strVE.equals("LSEO")) {
	            String[] saPRCINDC = getAttributeValue(_ei, "PRCINDC");
	            if(saAud != null) {
	                m_strPRCINDC_fc = saPRCINDC[1];
			    } else {
					D.ebug(D.EBUG_WARN, "PRCINDC is null for:" + _ei.getKey());
				}
	            // Pubto date for LSEO
	            String[] saUNPUBDATEMTRGT = getAttributeValue(_ei, "LSEOUNPUBDATEMTRGT");
	            m_UNPUBDATEMTRGT = saUNPUBDATEMTRGT[0];
	            D.ebug(D.EBUG_SPEW, "LSEOUNPUBDATEMTRGT on LSEO is " + m_UNPUBDATEMTRGT);
	            // Pubfrom date for LSEO
	            String[] saPUBDATEMTRGT = getAttributeValue(_ei, "LSEOPUBDATEMTRGT");
	            m_PUBDATEMTRGT = saPUBDATEMTRGT[0];
	            D.ebug(D.EBUG_SPEW, "LSEOPUBDATEMTRGT on LSEO is " + m_PUBDATEMTRGT);
            }
            if (strVE.equals("LSEOBUNDLE")) {
                String[] saSPECBID = getAttributeValue(_ei, "SPECBID");
                if(saSPECBID != null) {
                    m_strSPECBID_fc = saSPECBID[1];
    		    } else {
    				D.ebug(D.EBUG_WARN, "SPECBID is null for:" + _ei.getKey());
    			}
                String[] saBUNDLETYPE = getAttributeValue(_ei, "BUNDLETYPE");
                if(saBUNDLETYPE != null) {
                    m_strBUNDLETYPE_fc = saBUNDLETYPE[1];
    		    } else {
    				D.ebug(D.EBUG_WARN, "BUNDLETYPE is null for:" + _ei.getKey());
    			}
//       		 Pubto date for LSEOBUNDLE
                String[] saUNPUBDATEMTRGT = getAttributeValue(_ei, "BUNDLUNPUBDATEMTRGT");
                m_UNPUBDATEMTRGT = saUNPUBDATEMTRGT[0];
                D.ebug(D.EBUG_SPEW, "BUNDLUNPUBDATEMTRGT on LSEOBUNDLE is " + m_UNPUBDATEMTRGT);
//      		 Pubfrom date for LSEOBUNDLE
                String[] saPUBDATEMTRGT = getAttributeValue(_ei, "BUNDLPUBDATEMTRGT");
                m_PUBDATEMTRGT = saPUBDATEMTRGT[0];
                D.ebug(D.EBUG_SPEW, "BUNDLPUBDATEMTRGT on LSEOBUNDLE is " + m_PUBDATEMTRGT);

            }
         }

        //
        // LSEOBUNDLE-specific mappings
        //
        if (strVE.equals("LSEOBUNDLE")) {

            D.ebug(D.EBUG_SPEW, "(LSEOBUNDLE) looking at:" + _ei.getKey());

            if (_ei.getEntityType().equals(MODEL_ENTITYTYPE)) {

                String[] saCOFCAT = getAttributeValue(_ei, "COFCAT");
                D.ebug(D.EBUG_SPEW, "sa[1a] for COFCAT:" + saCOFCAT[1]);
                String[] saCOFSUBCAT = getAttributeValue(_ei, "COFSUBCAT");
                D.ebug(D.EBUG_SPEW, "sa[1a] for COFSUBCAT:" + saCOFSUBCAT[1]);
                String[] saCOFGRP = getAttributeValue(_ei, "COFGRP");
                D.ebug(D.EBUG_SPEW, "sa[1a] for COFGRP:" + saCOFGRP[1]);
                if (saCOFCAT[1] != null && saCOFCAT[1].equals("101")) { // for Software Model only!
                    setSoftwareModel(true);
                    String[] saAPPLICATIONTYPE = getAttributeValue(_ei, "APPLICATIONTYPE");
                    D.ebug(D.EBUG_SPEW, "sa[1] for APPLICATIONTYPE:" + saAPPLICATIONTYPE[1]);
                    if (saAPPLICATIONTYPE[1] != null && saAPPLICATIONTYPE[1].equals("33")) { // now for OperatingSystem only!
                        setOperatingSystem(true);
                    }
                }
                if (saCOFCAT[1] != null && saCOFCAT[1].equals("100")) { // Hardware
                    setHardwareModel(true);
                    if (isHardwareBundle()) {
                    	//Store Bundle's hardware model (to get Warranty later)
                    	setBundleHardwareModel(_ei);
                    }
                }
                if (saCOFSUBCAT[1] != null && saCOFSUBCAT[1].equals("126")) { // System
                    setSystemModel(true);
                }
                if (saCOFCAT[1] != null && saCOFCAT[1].equals("102")) { // Service
                    setServiceModel(true);
                }
                if (saCOFGRP[1] != null && saCOFGRP[1].equals("150")) { // Base
                    setBaseModel(true);
                }
            }

        }
        //
        //

        //
        // These guys we need to store away for later, because we are mapping attribute toe
        //   columns which span entitytype - i.e. we cannot close the deal here...
        //
        if (strVE.equals("LCTO")) {


            //3 )OSLEVEL for MODEL added 01/04/06
            if (_ei.getEntityType().equals(MODEL_ENTITYTYPE)) {

                String[] saCOFCAT = getAttributeValue(_ei, "COFCAT");
                D.ebug(D.EBUG_SPEW, "sa[1b] for COFCAT:" + saCOFCAT[1]);
                String[] saCOFSUBCAT = getAttributeValue(_ei, "COFSUBCAT");
                D.ebug(D.EBUG_SPEW, "sa[1b] for COFSUBCAT:" + saCOFSUBCAT[1]);
                String[] saCOFGRP = getAttributeValue(_ei, "COFGRP");
                D.ebug(D.EBUG_SPEW, "sa[1b] for COFGRP:" + saCOFGRP[1]);
                if (saCOFCAT[1] != null && saCOFCAT[1].equals("101")) { // Software
                    setSoftwareModel(true);
                    String[] saAPPLICATIONTYPE = getAttributeValue(_ei, "APPLICATIONTYPE");
                    D.ebug(D.EBUG_SPEW, "sa[1]c for APPLICATIONTYPE:" + saAPPLICATIONTYPE[1]);
                    if (saAPPLICATIONTYPE[1] != null && saAPPLICATIONTYPE[1].equals("33")) { // now for OperatingSystem only!
                        setOperatingSystem(true);
                    }
                }
                if (saCOFCAT[1] != null && saCOFCAT[1].equals("100")) { // Hardware
                    setHardwareModel(true);
                }
                if (saCOFSUBCAT[1] != null && saCOFSUBCAT[1].equals("126")) { // System
                    setSystemModel(true);
                }
                if (saCOFCAT[1] != null && saCOFCAT[1].equals("102")) { // Service
                    setServiceModel(true);
                }
                if (saCOFGRP[1] != null && saCOFGRP[1].equals("150")) { // Base
                    setBaseModel(true);
                }
            }

        }
        else if (strVE.equals("LSEO")) {
        	// Checking for OSLEVEL MODEL type (Software?)
            if (_ei.getEntityType().equals(MODEL_ENTITYTYPE)) {
                String[] saCOFCAT = getAttributeValue(_ei, "COFCAT");
                D.ebug(D.EBUG_SPEW, "sa[1c] for COFCAT:" + saCOFCAT[1]);
                String[] saCOFSUBCAT = getAttributeValue(_ei, "COFSUBCAT");
                D.ebug(D.EBUG_SPEW, "sa[1c] for COFSUBCAT:" + saCOFSUBCAT[1]);
                String[] saCOFGRP = getAttributeValue(_ei, "COFGRP");
                D.ebug(D.EBUG_SPEW, "sa[1c] for COFGRP:" + saCOFGRP[1]);
                if (saCOFCAT[1] != null && saCOFCAT[1].equals("101")) { // for Software Model only!
                    setSoftwareModel(true);
                    String[] saAPPLICATIONTYPE = getAttributeValue(_ei, "APPLICATIONTYPE");
                    D.ebug(D.EBUG_SPEW, "sa[1]b for APPLICATIONTYPE:" + saAPPLICATIONTYPE[1]);
                    if (saAPPLICATIONTYPE[1] != null && saAPPLICATIONTYPE[1].equals("33")) { // now for OperatingSystem only!
                        setOperatingSystem(true);
                    }
                }
                if (saCOFCAT[1] != null && saCOFCAT[1].equals("100")) { // Hardware
                    setHardwareModel(true);
                }
                if (saCOFSUBCAT[1] != null && saCOFSUBCAT[1].equals("126")) { // System
                    setSystemModel(true);
                }
                if (saCOFCAT[1] != null && saCOFCAT[1].equals("102")) { // Service
                    setServiceModel(true);
                }
                if (saCOFGRP[1] != null && saCOFGRP[1].equals("150")) { // Base
                    setBaseModel(true);
                }
            }
            else if (_ei.getEntityType().equals(WWSEO_ENTITYTYPE)) {
            	String[] saSPECBID = getAttributeValue(_ei, "SPECBID");
                if(saSPECBID != null) {
                    m_strSPECBID_fc = saSPECBID[1];
    		    } else {
    				D.ebug(D.EBUG_WARN, "SPECBID is null for:" + _ei.getKey());
    			}

	            //Find if LSEO is an OPTION
                String[] saSEOORDERCODE = getAttributeValue(_ei,"SEOORDERCODE");
	            m_strSEOORDERCODE_fc = saSEOORDERCODE[1];
            }

        }
        //
        // End our stow away part...
        //


        if (_ei.getEntityType().equals(CATLGOR_ENTITYTYPE)) {

        	String strCountryList = gami.getCountryList();
        	String[] saOFFCOUNTRY = getAttributeValue(_ei, "OFFCOUNTRY");
        	if (strCountryList.equals(saOFFCOUNTRY[1])) {

//        		 Pubfrom date for Catalog Override
                String[] saPUBFROMDATE_CATLGOR = getAttributeValue(_ei, "PUBFROM");
                m_PUBFROMDATE_CATLGOR = saPUBFROMDATE_CATLGOR[0];
                D.ebug(D.EBUG_SPEW, "PUBFROM on CATLGOR is " + m_PUBFROMDATE_CATLGOR);
//       		 Pubto date for Catalog Override
                String[] saPUBTODATE_CATLGOR = getAttributeValue(_ei, "PUBTO");
                m_PUBTODATE_CATLGOR = saPUBTODATE_CATLGOR[0];
                D.ebug(D.EBUG_SPEW, "PUBTO on CATLGOR is " + m_PUBTODATE_CATLGOR);


            // FLFILSYSINDC for Catalog Override
            String[] saFLFILSYSINDC = getAttributeValue(_ei, "FLFILSYSINDC");
            D.ebug(D.EBUG_SPEW, "saFLFILSYSINDC[0]:" + saFLFILSYSINDC[0]);
            putStringVal(FLFILSYSINDC, saFLFILSYSINDC[0]);
            putStringVal(FLFILSYSINDC_FC, saFLFILSYSINDC[1]);

            String[] saCATAUDIENCE = getAttributeValue(_ei, "CATAUDIENCE");

            if(getAudien_fc() != null && saCATAUDIENCE[1] != null) {
            	StringTokenizer stAud1 = new StringTokenizer(getAudien_fc(),"|");

            	while(stAud1.hasMoreTokens()) {
    				String strAudTok1 = stAud1.nextToken();
    				String strAudien_fc_one1 = (String)c_hashAudMap.get(strAudTok1);
    				D.ebug(D.EBUG_SPEW, strAudTok1 + " " + strAudien_fc_one1);
    				StringTokenizer stAudo = new StringTokenizer(saCATAUDIENCE[1],"|");
    				while(stAudo.hasMoreTokens() && strAudien_fc_one1 != null) {
    					String strAudToko = stAudo.nextToken();
    					D.ebug(D.EBUG_SPEW, strAudToko);
    					if (strAudToko.equals(strAudien_fc_one1)) {
    						for (int i=0; i<flag_array.length; i++) {
    							if (flag_array[i][0].equals(strAudTok1)) {
    						String[] flagattr = getAttributeValue(_ei,flag_array[i][1]);
    						flag_array[i][2] = flagattr[1];
    						D.ebug(D.EBUG_SPEW, "found " + flag_array[i][1] + " " + flag_array[i][2] + " for audience " + strAudToko);
    							}
    						}
    					}
    				}
            	}
            }
        }
        	 else D.ebug(D.EBUG_SPEW, "skipping CATLGOR because gami is country:"  + strCountryList +
        			 "CATLGOR is country:" + saOFFCOUNTRY[1]);
        }
        else if (_ei.getEntityType().equals(AVAIL_ENTITYTYPE)) {
        	String[] saAVAILTYPE = getAttributeValue(_ei, "AVAILTYPE");
        	if (saAVAILTYPE != null) {
        		if (saAVAILTYPE[1].equals("143")) {
//          		 Pubfrom date for AVAIL 143
                    String[] saPUBFROMDATE_AVAIL = getAttributeValue(_ei, "EFFECTIVEDATE");
                    m_PUBFROMDATE_AVAIL143 = saPUBFROMDATE_AVAIL[0];
                    D.ebug(D.EBUG_SPEW, "PUBFROM on AVAIL 143 is " + m_PUBFROMDATE_AVAIL143);
         		}											// end if avail 143
        		else if (saAVAILTYPE[1].equals("146")) {
        			boolean findAnn = false;
                    for (int j = 0; j < getSmc().getCount(); j++) {
                        SyncMapItem smiann = getSmc().get(j);
                        if(smiann.getChildType().equals("AVAILANNA") && smiann.getEntity1ID() == _ei.getEntityID() && smiann.getChildTran().equals("ON")){
                        	findAnn = true;
                        	EntityItem eiAnn = Catalog.getEntityItem(_cat, smiann.getEntity2Type(), smiann.getEntity2ID());
                        	D.ebug(D.EBUG_SPEW, "PUBFROM: get it from ANNOUNCEMENT.ANNDATE: " + eiAnn.getKey());
                        	String[] saPUBFROMDATE_ANN = CatDBTableRecord.getAttributeValue(eiAnn, "ANNDATE");
                        	m_PUBFROMDATE_AVAIL146 = saPUBFROMDATE_ANN[0];
                        	D.ebug(D.EBUG_SPEW, "PUBFROM on AVAIL 146's ANNOUNCEMENT is " + m_PUBFROMDATE_AVAIL146);
                        }
                    }
                    if(!findAnn){            	//in case no ann that linked to avail
                    	D.ebug(D.EBUG_SPEW, "There is no ann that linked to this avail. will get it from avail.effectivedate for PUBFROM: " + _ei.getKey());
////            		 Pubfrom date for AVAIL 146
   	                    String[] saPUBFROMDATE_AVAIL = getAttributeValue(_ei, "EFFECTIVEDATE");
   	                    m_PUBFROMDATE_AVAIL146 = saPUBFROMDATE_AVAIL[0];
   	                    D.ebug(D.EBUG_SPEW, "PUBFROM on AVAIL 146 is " + m_PUBFROMDATE_AVAIL146);
                    }
					// end if avail 146
        		}
        		else if (saAVAILTYPE[1].equals("149")) {
//            		 Pubto date for AVAIL 149
                       String[] saPUBTODATE_AVAIL = getAttributeValue(_ei, "EFFECTIVEDATE");
                       m_PUBTODATE_AVAIL149 = saPUBTODATE_AVAIL[0];
                       D.ebug(D.EBUG_SPEW, "PUBTO on AVAIL 149 is " + m_PUBTODATE_AVAIL149);
           		}											// end if avail 149
        	}												// end if availtype not null
           }												// end if entitytype is avail
      }														// end setattributes

    /**
     * Let's just do/put derived fields
     */
    private void calcDerivedFields() {
        String strANNDATE = getStringVal(ANNDATE);
        if (strANNDATE != null) {
            if (strANNDATE.indexOf("/") > -1) {
                StringTokenizer st = new StringTokenizer(strANNDATE, "/");
                String strDD = st.nextToken();
                String strMM = st.nextToken();
                String strYYYY = st.nextToken();
                strANNDATE = strYYYY + "-" + strMM + "-" + strDD;
            }
            if(strANNDATE.indexOf("9999") > -1) {
		        putStringVal(FOTDATE, "9999-12-31");
			} else {
            	CalendarAdjust ca = new CalendarAdjust();
            	Date date = ca.getDate("yyyy-MM-dd", strANNDATE);
            	String strFOTDATE = ca.formatDate(ca.dateAdd(date, 6, -28), "yyyy-MM-dd");
            	putStringVal(FOTDATE, strFOTDATE);
			}
        }
    }

    /**
     * This gets a bit tricky, because MODEL can be a child in LSEO land, or a root in MODEL land.
     */
    private final boolean isRootEntity(EntityItem _ei) {
        ProductId pid = getProductId();
        return ( (_ei.getEntityType().equals(LSEO_ENTITYTYPE) && pid.isLSEO() && (_ei.getEntityID() == pid.getEntityId())) ||
                (_ei.getEntityType().equals(LSEOBUNDLE_ENTITYTYPE) && pid.isLSEOBUNDLE() && (_ei.getEntityID() == pid.getEntityId())) ||
                (_ei.getEntityType().equals(MODEL_ENTITYTYPE) && pid.isMODEL() && (_ei.getEntityID() == pid.getEntityId())));
    }


    /**
     * setProdStructCollection
     *
     * @param collection
     */
    public void setProdStructCollection(ProdStructCollection collection) {
        m_psc = collection;
    }

    /**
     * This generates an XMLFragment per the XML Interface
     *
     * Over the time, we can make new getXXXXX's and pass the
     * xml object to them and they will generate their own fragements
     * @param _xml
     */
    public void generateXMLFragment(XMLWriter _xml) {

        ProductId pid = this.getProductId();

        try {
            _xml.writeEntity(pid.getEntityType());
            this.writeXMLString(_xml, COUNTRYCODE);
            this.writeXMLString(_xml, LANGUAGECODE);
            this.writeXMLString(_xml, WWENTITYTYPE);
            this.writeXMLInt(_xml, WWENTITYID);
            this.writeXMLString(_xml, LOCENTITYTYPE);
            this.writeXMLInt(_xml, LOCENTITYID);
            this.writeXMLString(_xml, MODELNAME);
            this.writeXMLString(_xml, WWPARTNUMBER);
            this.writeXMLString(_xml, PARTNUMBER);
            this.writeXMLString(_xml, MARKETINGDESC);
            this.writeXMLString(_xml, ANNDATE);
            this.writeXMLString(_xml, WITHDRAWLDATE);
            this.writeXMLString(_xml, FOTDATE);
            this.writeXMLString(_xml, FLFILSYSINDC);
            this.writeXMLString(_xml, STATUS);
            this.writeXMLString(_xml, SPECMODDESGN);
            this.writeXMLString(_xml, INVNAME);
            this.writeXMLString(_xml, VALFROM);
            this.writeXMLString(_xml, VALTO);
            _xml.endEntity();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

///////
//
///////
    public void putStringVal(String _strColKey, String _strVal) {
        try {
            super.putStringVal(_strColKey, _strVal);
        }
        catch (NullPointerException exc) {
            D.ebug(D.EBUG_ERR, "NPE - figure this out later");
        }
    }

    public void putIntVal(String _strColKey, int _iVal) {
        super.putIntVal(_strColKey, _iVal);
    }

    public String getStringVal(String _strColKey) {
        String s = super.getStringVal(_strColKey);

        if (_strColKey.equals(ANNDATE) || _strColKey.equals(WITHDRAWLDATE) || _strColKey.equals(FOTDATE) ||
             _strColKey.equals(PLANAVAILDATE) || _strColKey.equals(PUBFROMDATE) || _strColKey.equals(PUBTODATE)) {
            if(s != null && s.equals("2005-10-28")) {
				s = "10/28/9999";
			}

        } else if (s == null) {
           s = "";
        }
        return s;
    }

    public int getIntVal(String _iColKey) {
        return super.getIntVal(_iColKey);
    }

//
//
//
    private void setSoftwareModel(boolean _b) {
        //D.ebug(D.EBUG_SPEW, "setSoftwareModel=" + _b);
        m_bSoftwareModel = _b;
    }

    private void setHardwareModel(boolean _b) {
        //D.ebug(D.EBUG_SPEW, "setHardwareModel=" + _b);
        m_bHardwareModel = _b;
    }

    private void setSystemModel(boolean _b) {
        //D.ebug(D.EBUG_SPEW, "setSystemModel=" + _b);
        m_bSystemModel = _b;
    }

    private void setServiceModel(boolean _b) {
        //D.ebug(D.EBUG_SPEW, "setServiceModel=" + _b);
        m_bServiceModel = _b;
    }

    private void setBaseModel(boolean _b) {
        //D.ebug(D.EBUG_SPEW, "setBaseModel=" + _b);
        m_bBaseModel = _b;
    }

    private void setOperatingSystem(boolean _b) {
        m_bOperatingSystem = _b;
    }

    private void setBundleHardwareModel(EntityItem ei) {
    	bundleHardwareModel = ei;
    }

    private boolean isSoftwareModel() {
        return m_bSoftwareModel;
    }

    private boolean isHardwareModel() {
        return m_bHardwareModel;
    }

    /* private boolean isSystemModel() {
        return m_bSystemModel;
    } */

    private boolean isServiceModel() {
        return m_bServiceModel;
    }

    private boolean isBaseModel() {
        return m_bBaseModel;
    }

    private boolean isOperatingSystem() {
        return m_bOperatingSystem;
    }

    private String getAudien_fc() {
		return m_strAudien_fc;
	}

    private boolean isHardwareBundle() {
    	if (m_strBUNDLETYPE_fc == null)
    		return false;
    	return m_strBUNDLETYPE_fc.indexOf("100") != -1;
    }

    private boolean isOption() {
    	//'20' is MES
    	return "20".equals(m_strSEOORDERCODE_fc);
    }

    private String getFlagAttr(String Audience, String Attr, ProductId pid) {
		String s = null;
    	for (int i=0; i<flag_array.length; i++) {
			if (flag_array[i][0].equals(Audience) && flag_array[i][1].equals(Attr)) {
				s = flag_array[i][2];
				break;
			}
		}

    	if (s == null) {
    		if (Attr.equals("CATNEWOFF")) {
        		s = "null";
        	}
    		else {
    		if (pid.isMODEL()) {
    			if (isHardwareModel() || isSoftwareModel()) {
    				if (Attr == "CATHIDE") s = "No";
    				if (Attr == "CATBUYABLE") s = "No";
    				if (Attr == "CATADDTOCART") s = "No";
    				if (Attr == "CATCUSTIMIZE") s = "Yes";
    			}
    			else if(isServiceModel()) {
    				if (Attr == "CATHIDE") s = "Yes";
    				if (Attr == "CATBUYABLE") s = "No";
    				if (Attr == "CATADDTOCART") s = "No";
    				if (Attr == "CATCUSTIMIZE") s = "No";
    			}
    		}
    		else if (pid.isLSEO()) {
    			if (isHardwareModel()) {
    					if (m_strPRCINDC_fc != null && m_strPRCINDC_fc.equals("yes")) {
    			//RCQ00337761-RQ if the PRCINDC is Yes and the SEOORDERCODE is MES, set the customizable flag to "No"
    						if (isOption()) {
    								if (Attr == "CATHIDE") s = "No";
        		    				if (Attr == "CATBUYABLE") s = "Yes";
        		    				if (Attr == "CATADDTOCART") s = "Yes";
        		    				if (Attr == "CATCUSTIMIZE") s = "No";
    						}
    						else {
    							if(m_strSPECBID_fc != null && m_strSPECBID_fc.equals("11458")) {
    								if (Attr == "CATHIDE") s = "No";
        		    				if (Attr == "CATBUYABLE") s = "Yes";
        		    				if (Attr == "CATADDTOCART") s = "Yes";
        		    				if (Attr == "CATCUSTIMIZE") s = "Yes";
    							}
    							else {
    								if (Attr == "CATHIDE") s = "No";
        		    				if (Attr == "CATBUYABLE") s = "Yes";
        		    				if (Attr == "CATADDTOCART") s = "Yes";
        		    				if (Attr == "CATCUSTIMIZE") s = "Yes";
    							}
    						}
    					}
    					else {
    						if (m_strSPECBID_fc != null && m_strSPECBID_fc.equals("11458")) {
    							if (Attr == "CATHIDE") s = "Yes";
    		    				if (Attr == "CATBUYABLE") s = "No";
    		    				if (Attr == "CATADDTOCART") s = "No";
    		    				if (Attr == "CATCUSTIMIZE") s = "No";
        					}
    						else {
    							if (Attr == "CATHIDE") s = "Yes";
    		    				if (Attr == "CATBUYABLE") s = "No";
    		    				if (Attr == "CATADDTOCART") s = "No";
    		    				if (Attr == "CATCUSTIMIZE") s = "No";
    						}
    					}
    			}
    			else if (isSoftwareModel()) {
    				if (m_strPRCINDC_fc != null && m_strPRCINDC_fc.equals("yes")) {
						if (m_strSPECBID_fc != null && m_strSPECBID_fc.equals("11458")) {
							if (Attr == "CATHIDE") s = "No";
		    				if (Attr == "CATBUYABLE") s = "Yes";
		    				if (Attr == "CATADDTOCART") s = "Yes";
		    				if (Attr == "CATCUSTIMIZE") s = "No";
    					}
						else {
							if (Attr == "CATHIDE") s = "No";
		    				if (Attr == "CATBUYABLE") s = "Yes";
		    				if (Attr == "CATADDTOCART") s = "Yes";
		    				if (Attr == "CATCUSTIMIZE") s = "No";
						}
					}
					else {
						if (m_strSPECBID_fc != null && m_strSPECBID_fc.equals("11458")) {
							if (Attr == "CATHIDE") s = "Yes";
		    				if (Attr == "CATBUYABLE") s = "No";
		    				if (Attr == "CATADDTOCART") s = "No";
		    				if (Attr == "CATCUSTIMIZE") s = "No";
    					}
						else {
							if (Attr == "CATHIDE") s = "Yes";
		    				if (Attr == "CATBUYABLE") s = "No";
		    				if (Attr == "CATADDTOCART") s = "No";
		    				if (Attr == "CATCUSTIMIZE") s = "No";
						}
					}
    			}
    			else if (isServiceModel()) {
    				if (m_strPRCINDC_fc != null && m_strPRCINDC_fc.equals("yes")) {
							if (Attr == "CATHIDE") s = "No";
		    				if (Attr == "CATBUYABLE") s = "Yes";
		    				if (Attr == "CATADDTOCART") s = "Yes";
		    				if (Attr == "CATCUSTIMIZE") s = "No";
    					}
						else {
							if (Attr == "CATHIDE") s = "Yes";
		    				if (Attr == "CATBUYABLE") s = "No";
		    				if (Attr == "CATADDTOCART") s = "No";
		    				if (Attr == "CATCUSTIMIZE") s = "No";
						}
					}
    		}                                   // end if LSEO
    		else if (pid.isLSEOBUNDLE()) {
    			if (m_strBUNDLETYPE_fc == null || m_strBUNDLETYPE_fc.indexOf("100") > -1) {
    				if (m_strSPECBID_fc != null && m_strSPECBID_fc.equals("11458")) {
						if (Attr == "CATHIDE") s = "No";
	    				if (Attr == "CATBUYABLE") s = "Yes";
	    				if (Attr == "CATADDTOCART") s = "Yes";
	    				if (Attr == "CATCUSTIMIZE") s = "Yes";
					}
    				else {
    				if (Attr == "CATHIDE") s = "No";
    				if (Attr == "CATBUYABLE") s = "Yes";
    				if (Attr == "CATADDTOCART") s = "Yes";
    				if (Attr == "CATCUSTIMIZE") s = "Yes";
    				}
    			}
    			else if (m_strBUNDLETYPE_fc.indexOf("101") > -1) {
    				if (m_strSPECBID_fc != null && m_strSPECBID_fc.equals("11458")) {
						if (Attr == "CATHIDE") s = "No";
	    				if (Attr == "CATBUYABLE") s = "Yes";
	    				if (Attr == "CATADDTOCART") s = "Yes";
	    				if (Attr == "CATCUSTIMIZE") s = "No";
					}
    				else {
    				if (Attr == "CATHIDE") s = "No";
    				if (Attr == "CATBUYABLE") s = "Yes";
    				if (Attr == "CATADDTOCART") s = "Yes";
    				if (Attr == "CATCUSTIMIZE") s = "No";
    				}
    			}
    			else {
    				if (Attr == "CATHIDE") s = "No";
    				if (Attr == "CATBUYABLE") s = "Yes";
    				if (Attr == "CATADDTOCART") s = "Yes";
    				if (Attr == "CATCUSTIMIZE") s = "No";
    				}
    		}									// end if pid.isLSEOBUNDLE
    	}										// end else attr not CATNEWOFF
    	}										// end if s == null
    	return s;
	}											// end getFlagAttr

///////////////////
// PRUNING CODE //
//////////////////

/**
 * Only way this is gonna pass is per CATLGPUB matching rules in spec.
 */
/*	public boolean matchesCATLGPUB(Catalog _cat) {
        ProductId pid = getProductId();
        GeneralAreaMapItem gami = pid.getGami();
        String strCountryList = gami.getCountryList();
        boolean bFound = false;
        SyncMapCollection smc = this.getSmc();
        for (int x = 0; x < smc.getCount(); x++) {
            SyncMapItem smi = smc.get(x);
            if(!smi.getChildTran().equals("ON")) {
				continue;
			}
            if(smi.getEntity2Type().equals("CATLGPUB")) {
				EntityItem eiCATLGPUB = Catalog.getEntityItem(_cat, smi.getEntity2Type(), smi.getEntity2ID());
				// We also need to check a PartNumber. Our assumption here is that if it's linked, then the PN's match.
				// Now let's assure country matches.
				String[] saCountry = getAttributeValue(eiCATLGPUB,"OFFCOUNTRY");
				String[] saCatActive = getAttributeValue(eiCATLGPUB,"CATACTIVE");
				if(saCountry != null && saCatActive != null) {
				    D.ebug(D.EBUG_SPEW,"matchesCATLGPUB found CATLGPUB COUNTRY for " + toString() + ", our country is:" + strCountryList);
				    if((saCountry[1] != null && saCountry[1].equals(strCountryList)) && (saCatActive[1] != null && saCatActive[1].equals("Active"))) {
						D.ebug(D.EBUG_SPEW,"matchesCATLGPUB found CATLGPUB CATACTIVE for:" + toString() + ", is:" + saCatActive[1]);
						D.ebug(D.EBUG_SPEW,"matchesCATLGPUB found CATLGPUB COUNTRY for " + toString() + "--and it matches!");
						bFound = true;
						break;
					} else if(saCatActive[1] == null || !saCatActive[1].equals("Active")) {
					    D.ebug(D.EBUG_SPEW,"matchesCATLGPUB skipping CATLGPUB due to CATACTIVE for:" + toString() + ", is null or incative");
					}
				} else {
				    D.ebug(D.EBUG_WARN,"matchesCATLGPUB CATLGPUB country for " + toString() + " is null!");
				}
			}
		}
        return bFound;
	} */

    /**
     * Here is where we remove feature/prodstruct smi's which don't fit are criteria (see 050506 spec,+flow criteria SS).
     *
     */
    protected void pruneSmc(Catalog _cat) {

        D.ebug(D.EBUG_SPEW,"BEGIN pruneSmc for prod:" + toString() + ". smc size is " + getSmc().getCount() + "[" + toString() + "]-->");

        ProductId pid = getProductId();

        if (/*pid.isLSEO() || */pid.isMODEL()) {

            for (int i = 0; i < getSmc().getCount(); i++) {
                SyncMapItem smi = getSmc().get(i);

                // ok, lets first build a list of prodstructs to remove.
                if(smi.getChildType().equals("SWPRODSTRUCT") ||
                   smi.getChildType().equals("PRODSTRUCT")) {
			        checkPruneProdstruct(_cat,smi);
				}
            }

            // now let's remove any SMI's referencing these (SW)PRODSTRUCTS...
            SyncMapCollection smcPruned = new SyncMapCollection();
            for (int i = 0; i < getSmc().getCount(); i++) {
                SyncMapItem smi = getSmc().get(i);
                // 1) childtype
                String strKey1 = smi.getChildType() + ":" + smi.getChildID();
                String strVal1 = (String)m_hashPrunedProdstructs.get(strKey1);
                // 2) e1type
                String strKey2 = smi.getEntity1Type() + ":" + smi.getEntity1ID();
                String strVal2 = (String)m_hashPrunedProdstructs.get(strKey2);
                // 3) e2type
                String strKey3 = smi.getEntity2Type() + ":" + smi.getEntity2ID();
                String strVal3 = (String)m_hashPrunedProdstructs.get(strKey3);
                if(strVal1 == null && strVal2 == null && strVal3 == null) { // i.e. NOT a remove
			        smcPruned.add(smi);
				} else {
					D.ebug(D.EBUG_SPEW,"pruneSmc:removing one SMI:"+smi.toString());
				}
            }
            this.setSmc(smcPruned);
        }
        D.ebug(D.EBUG_SPEW, "-->END pruneSmc smc size is now " + getSmc().getCount() + "[" + toString() + "]");
    }

/**
 * 1) SWPRODSTRUCT: we need to check AVAIL.AVAILTYPE == 156 thru SWPRODSTRUCTAVAIL.
 *                  AND Country is in AVAIL.COUNTRYLIST.
 * 2) PRODSTRUCT:
 *    a) If FEATURE.FCTYPE = 120 || 130 then flows if the target catalog country is in FEATURE.COUNTRYLIST
 *    b) else if its child MODEL is flowing and the PRODSTRUCT has a child AVAIL found via relator OOFAVAIL
 *       where AVAIL.AVAILTYPE = 146 and the target Catalog Country is in AVAIL.COUNTRYLIST.
 */
    private final void checkPruneProdstruct(Catalog _cat, SyncMapItem _smi) {

        ProductId pid = this.getProductId();
        GeneralAreaMapItem gami = pid.getGami();

        // careful, some unintuitive boolean setting below!
        boolean bConfigPrune = false;
		boolean bPrune = true;

		// 1)
		if(_smi.getChildType().equals("SWPRODSTRUCT")) {
			// there could be more than one avail hooked up...
            for (int i = 0; i < getSmc().getCount(); i++) {
                SyncMapItem smiInner = getSmc().get(i);

            	// First things first.. is this guy even valid??
            	if(!smiInner.getChildTran().equals("ON")) {
					continue;
				}

                // pull out child avails..
                if(smiInner.getChildType().equals("SWPRODSTRUCTAVAIL") && smiInner.getEntity1ID() == _smi.getChildID()) {
				    EntityItem eiAVAIL = Catalog.getEntityItem(_cat, smiInner.getEntity2Type(), smiInner.getEntity2ID());
        			String[] saAVAILTYPE = getAttributeValue(eiAVAIL,"AVAILTYPE");
        			String[] saORDERSYSNAME = getAttributeValue(eiAVAIL,"ORDERSYSNAME");
        			if (saAVAILTYPE == null || saAVAILTYPE[1] == null
        				|| saORDERSYSNAME == null || saORDERSYSNAME[1] == null ||
        				!saORDERSYSNAME[1].equals("4142")) {
        			    continue;
        			} else if(saAVAILTYPE[1].equals("146")) { // "Planned Availability"
						D.ebug(D.EBUG_SPEW,"Planned Availability found for:" + eiAVAIL.getKey());
						if(isCountryList(_cat,gami,eiAVAIL.getEntityType(),eiAVAIL.getEntityID())) {
							D.ebug(D.EBUG_SPEW,"Planned Availability found for:" + eiAVAIL.getKey());
							bPrune = false;
						    // we only need to verify one.
						    break;
						}
					}
				}
			}
        // 2)
		} else if(_smi.getChildType().equals("PRODSTRUCT")) {
			// let's first check out feature...
		    EntityItem eiFEATURE = Catalog.getEntityItem(_cat, _smi.getEntity1Type(), _smi.getEntity1ID());
        	String[] saFCTYPE = getAttributeValue(eiFEATURE,"FCTYPE");
        	/* EntityItem eiPRODSTRUCT = Catalog.getEntityItem(_cat, _smi.getChildType(), _smi.getChildID()); */

            D.ebug(D.EBUG_SPEW,"cehckPruneProdstruct, here we are in PRODSTRUCT part for:" +  _smi.getEntity1Type() + ":" + _smi.getEntity1ID() + "," + _smi.getChildType() + ":" + _smi.getChildID());

        	// GAB - 082906 - updated spec for FEATURE.CONFIGURATORFLAG==0040, do not pass prodstructs.
        	/* String[] saCONFIGURATORFLAG_FEATURE = getAttributeValue(eiFEATURE,"CONFIGURATORFLAG"); */
        	// GAB - 092106 - updated spec for PRODSTRUCT.CONFIGURATORFLAG==0040, do not pass prodstructs.
        	/* String[] saCONFIGURATORFLAG_PRODSTRUCT = getAttributeValue(eiPRODSTRUCT,"CONFIGURATORFLAG"); */

            // GAB - 092106 - updated spec.
        	// CR072607687 Removing old rule for CONFIGURATORFLAG skipping records.
        	//if(saCONFIGURATORFLAG_PRODSTRUCT != null && saCONFIGURATORFLAG_PRODSTRUCT[1] != null) {
				//if(saCONFIGURATORFLAG_PRODSTRUCT[1].equals("0040")) { // "Feature code/RPQ is not passed (N)"
                	//bConfigPrune = true;
                	//D.ebug(D.EBUG_SPEW,"checkPruneProdstruct PART Ia");
				//}
            //} else if(saCONFIGURATORFLAG_FEATURE != null && saCONFIGURATORFLAG_FEATURE[1] != null &&
        	  // (saCONFIGURATORFLAG_FEATURE[1].equals("0040"))) { // "Feature code/RPQ is not passed (N)"
                //bConfigPrune = true; // yep.
                                /*
                            kehrli@us.ibm.c...	you can say the following
							kehrli@us.ibm.c...	If FEATURE.CONFIGURATORFLAG = feature code/RPQ is not passed (N)?(N) then do not pass
							kehrli@us.ibm.c...	you can check that first
							kehrli@us.ibm.c...	if do not pass - then done
                            kehrli@us.ibm.c...	else continue with the rest
                                */

                //D.ebug(D.EBUG_SPEW,"checkPruneProdstruct PART Ib");

			//}
			// END CONFIGURATORFLAG

			if(saFCTYPE != null && saFCTYPE[1] != null &&
        	   (saFCTYPE[1].equals("120") || saFCTYPE[1].equals("130") ||
        	    saFCTYPE[1].equals("402"))) { // "RPQ-ILISTED" (120) OR "RPQ-PLISTED" (130)

        	    D.ebug(D.EBUG_SPEW,"checkPruneProdstruct PART II");

        	                             // GAB062106: OR "Placeholder" (402)
                String[] saSTATUS = getAttributeValue(eiFEATURE,"STATUS");
                if(saSTATUS != null && saSTATUS[1] != null &&
                   (saSTATUS[1].equals("0020") || saSTATUS[1].equals("0040"))) { // RFR or Final
			        if(isCountryList(_cat,gami,eiFEATURE.getEntityType(),eiFEATURE.getEntityID())) {
						bPrune = false;
					}
				}

			} else { // this is VERY similar to logic above for SWPRODSTRUCT!
                for (int i = 0; i < getSmc().getCount(); i++) {
           	        SyncMapItem smiInner = getSmc().get(i);

                    D.ebug(D.EBUG_SPEW,"checkPruneProdstruct PART II");

            		// First things first.. is this guy even valid??
            		if(!smiInner.getChildTran().equals("ON")) {
						continue;
					}

           	        // pull out child avails..
           	        if(smiInner.getChildType().equals("OOFAVAIL") && smiInner.getEntity1ID() == _smi.getChildID()) {
					    EntityItem eiAVAIL = Catalog.getEntityItem(_cat, smiInner.getEntity2Type(), smiInner.getEntity2ID());
        				String[] saAVAILTYPE = getAttributeValue(eiAVAIL,"AVAILTYPE");
        				String[] saORDERSYSNAME = getAttributeValue(eiAVAIL,"ORDERSYSNAME");
        				if (saAVAILTYPE == null || saAVAILTYPE[1] == null
        					|| saORDERSYSNAME == null || saORDERSYSNAME[1] == null ||
                			   !saORDERSYSNAME[1].equals("4142")) {
        				    continue;
        				} else if(saAVAILTYPE[1].equals("146")) { // "Planned Availability"
							D.ebug(D.EBUG_SPEW,"Planned Availability found for:" + eiAVAIL.getKey());
							if(isCountryList(_cat,gami,eiAVAIL.getEntityType(),eiAVAIL.getEntityID())) {
								D.ebug(D.EBUG_SPEW,"Planned Availability found for:" + eiAVAIL.getKey());
								bPrune = false;
							    // we only need to verify one.
							    break;
							}
						}
					}
				}
			}
		} else {
			bPrune = false;
		}

		// If configurator flag dictates a prune, we shalt prune.
		if(bConfigPrune) {
			bPrune = true;
		}

		if(bPrune) {
			D.ebug(D.EBUG_SPEW,"checkPrunProdstruct-->PRUNING:" + _smi.getChildType() + ":" + _smi.getChildID());
		    m_hashPrunedProdstructs.put(_smi.getChildType() + ":" + _smi.getChildID(),"PRUNE");
		} else {
			D.ebug(D.EBUG_SPEW,"checkPrunProdstruct-->KEEPING:" + _smi.getChildType() + ":" + _smi.getChildID());
		}
	}
// FOR CR072607687, This method which is only called by ProductStructCollection, was copied from  pruneSmc().
    /**
     * Here is where we remove feature/prodstruct smi's which don't fit are criteria (see 050506 spec,+flow criteria SS).
     * and the following check only for ProductstructCollection.
     */
    protected void pruneSmcForStruct(Catalog _cat) {

        D.ebug(D.EBUG_SPEW,"BEGIN pruneSmc for prod:" + toString() + ". smc size is " + getSmc().getCount() + "[" + toString() + "]-->");

        ProductId pid = getProductId();

        if (/*pid.isLSEO() || */pid.isMODEL()) {

            for (int i = 0; i < getSmc().getCount(); i++) {
                SyncMapItem smi = getSmc().get(i);

                // ok, lets first build a list of prodstructs to remove.
                if(smi.getChildType().equals("SWPRODSTRUCT") ||
                   smi.getChildType().equals("PRODSTRUCT")) {
			        checkPruneForStruct(_cat,smi);
				}
            }

            // now let's remove any SMI's referencing these (SW)PRODSTRUCTS...
            SyncMapCollection smcPruned = new SyncMapCollection();
            for (int i = 0; i < getSmc().getCount(); i++) {
                SyncMapItem smi = getSmc().get(i);
                // 1) childtype
                String strKey1 = smi.getChildType() + ":" + smi.getChildID();
                String strVal1 = (String)m_hashPrunedProdstructs.get(strKey1);
                // 2) e1type
                String strKey2 = smi.getEntity1Type() + ":" + smi.getEntity1ID();
                String strVal2 = (String)m_hashPrunedProdstructs.get(strKey2);
                // 3) e2type
                String strKey3 = smi.getEntity2Type() + ":" + smi.getEntity2ID();
                String strVal3 = (String)m_hashPrunedProdstructs.get(strKey3);
                if(strVal1 == null && strVal2 == null && strVal3 == null) { // i.e. NOT a remove
			        smcPruned.add(smi);
				} else {
					D.ebug(D.EBUG_SPEW,"pruneSmc:removing one SMI:"+smi.toString());
				}
            }
            this.setSmc(smcPruned);
        }
        D.ebug(D.EBUG_SPEW, "-->END pruneSmc smc size is now " + getSmc().getCount() + "[" + toString() + "]");
    }

/**
 * 1) SWPRODSTRUCT: we need to check AVAIL.AVAILTYPE == 156 thru SWPRODSTRUCTAVAIL.
 *                  AND Country is in AVAIL.COUNTRYLIST.
 * 2) PRODSTRUCT:
 *    a) If FEATURE.FCTYPE = 120 || 130 then flows if the target catalog country is in FEATURE.COUNTRYLIST
 *    b) else if its child MODEL is flowing and the PRODSTRUCT has a child AVAIL found via relator OOFAVAIL
 *       where AVAIL.AVAILTYPE = 146 and the target Catalog Country is in AVAIL.COUNTRYLIST.
 */
    private final void checkPruneForStruct(Catalog _cat, SyncMapItem _smi) {

        ProductId pid = this.getProductId();
        GeneralAreaMapItem gami = pid.getGami();

        // careful, some unintuitive boolean setting below!
        boolean bConfigPrune = false;
		boolean bPrune = true;

		// 1)
		if(_smi.getChildType().equals("SWPRODSTRUCT")) {
			// there could be more than one avail hooked up...
            for (int i = 0; i < getSmc().getCount(); i++) {
                SyncMapItem smiInner = getSmc().get(i);

            	// First things first.. is this guy even valid??
            	if(!smiInner.getChildTran().equals("ON")) {
					continue;
				}

                // pull out child avails..
                if(smiInner.getChildType().equals("SWPRODSTRUCTAVAIL") && smiInner.getEntity1ID() == _smi.getChildID()) {
				    EntityItem eiAVAIL = Catalog.getEntityItem(_cat, smiInner.getEntity2Type(), smiInner.getEntity2ID());
        			String[] saAVAILTYPE = getAttributeValue(eiAVAIL,"AVAILTYPE");
        			String[] saORDERSYSNAME = getAttributeValue(eiAVAIL,"ORDERSYSNAME");
        			if (saAVAILTYPE == null || saAVAILTYPE[1] == null
        				|| saORDERSYSNAME == null || saORDERSYSNAME[1] == null ||
            			   !saORDERSYSNAME[1].equals("4142")) {
        			    continue;
        			} else if(saAVAILTYPE[1].equals("146")) { // "Planned Availability"
						D.ebug(D.EBUG_SPEW,"Planned Availability found for:" + eiAVAIL.getKey());
						if(isCountryList(_cat,gami,eiAVAIL.getEntityType(),eiAVAIL.getEntityID())) {
							D.ebug(D.EBUG_SPEW,"Planned Availability found for:" + eiAVAIL.getKey());
							bPrune = false;
						    // we only need to verify one.
						    break;
						}
					}
				}
			}
        // 2)
		} else if(_smi.getChildType().equals("PRODSTRUCT")) {
			// let's first check out feature...
		    EntityItem eiFEATURE = Catalog.getEntityItem(_cat, _smi.getEntity1Type(), _smi.getEntity1ID());
        	String[] saFCTYPE = getAttributeValue(eiFEATURE,"FCTYPE");
        	/* EntityItem eiPRODSTRUCT = Catalog.getEntityItem(_cat, _smi.getChildType(), _smi.getChildID()); */

            D.ebug(D.EBUG_SPEW,"cehckPruneProdstruct, here we are in PRODSTRUCT part for:" +  _smi.getEntity1Type() + ":" + _smi.getEntity1ID() + "," + _smi.getChildType() + ":" + _smi.getChildID());

        	// GAB - 082906 - updated spec for FEATURE.CONFIGURATORFLAG==0040, do not pass prodstructs.
        	/* String[] saCONFIGURATORFLAG_FEATURE = getAttributeValue(eiFEATURE,"CONFIGURATORFLAG"); */
        	// GAB - 092106 - updated spec for PRODSTRUCT.CONFIGURATORFLAG==0040, do not pass prodstructs.
        	/* String[] saCONFIGURATORFLAG_PRODSTRUCT = getAttributeValue(eiPRODSTRUCT,"CONFIGURATORFLAG"); */

            // GAB - 092106 - updated spec.
        	// CR072607687 Removing old rule for CONFIGURATORFLAG skipping records.
        	//if(saCONFIGURATORFLAG_PRODSTRUCT != null && saCONFIGURATORFLAG_PRODSTRUCT[1] != null) {
				//if(saCONFIGURATORFLAG_PRODSTRUCT[1].equals("0040")) { // "Feature code/RPQ is not passed (N)"
			       // bConfigPrune = false;
                	//D.ebug(D.EBUG_SPEW,"checkPruneProdstruct PART Ia");
				//}
            //} else if(saCONFIGURATORFLAG_FEATURE != null && saCONFIGURATORFLAG_FEATURE[1] != null &&
        	  // (saCONFIGURATORFLAG_FEATURE[1].equals("0040"))) { // "Feature code/RPQ is not passed (N)"
                //bConfigPrune = false; // yep.
                                /*
                            kehrli@us.ibm.c...	you can say the following
							kehrli@us.ibm.c...	If FEATURE.CONFIGURATORFLAG =feature code/RPQ is not passed (N)?(N) then do not pass
							kehrli@us.ibm.c...	you can check that first
							kehrli@us.ibm.c...	if do not pass - then done
                            kehrli@us.ibm.c...	else continue with the rest
                                */

                //D.ebug(D.EBUG_SPEW,"checkPruneProdstruct PART Ib");

			//}
			// END CONFIGURATORFLAG

			if(saFCTYPE != null && saFCTYPE[1] != null &&
        	   (saFCTYPE[1].equals("120") || saFCTYPE[1].equals("130") ||
        	    saFCTYPE[1].equals("402"))) { // "RPQ-ILISTED" (120) OR "RPQ-PLISTED" (130)

        	    D.ebug(D.EBUG_SPEW,"checkPruneProdstruct PART II");

        	                             // GAB062106: OR "Placeholder" (402)
                String[] saSTATUS = getAttributeValue(eiFEATURE,"STATUS");
                if(saSTATUS != null && saSTATUS[1] != null &&
                   (saSTATUS[1].equals("0020") || saSTATUS[1].equals("0040"))) { // RFR or Final
			        if(isCountryList(_cat,gami,eiFEATURE.getEntityType(),eiFEATURE.getEntityID())) {
						bPrune = false;
					}
				}

			} else { // this is VERY similar to logic above for SWPRODSTRUCT!
                for (int i = 0; i < getSmc().getCount(); i++) {
           	        SyncMapItem smiInner = getSmc().get(i);

                    D.ebug(D.EBUG_SPEW,"checkPruneProdstruct PART II");

            		// First things first.. is this guy even valid??
            		if(!smiInner.getChildTran().equals("ON")) {
						continue;
					}

           	        // pull out child avails..
           	        if(smiInner.getChildType().equals("OOFAVAIL") && smiInner.getEntity1ID() == _smi.getChildID()) {
					    EntityItem eiAVAIL = Catalog.getEntityItem(_cat, smiInner.getEntity2Type(), smiInner.getEntity2ID());
        				String[] saAVAILTYPE = getAttributeValue(eiAVAIL,"AVAILTYPE");
        				String[] saORDERSYSNAME = getAttributeValue(eiAVAIL,"ORDERSYSNAME");
        				if (saAVAILTYPE == null || saAVAILTYPE[1] == null
        					|| saORDERSYSNAME == null || saORDERSYSNAME[1] == null ||
                 			   !saORDERSYSNAME[1].equals("4142")) {
        				    continue;
        				} else if(saAVAILTYPE[1].equals("146")) { // "Planned Availability"
							D.ebug(D.EBUG_SPEW,"Planned Availability found for:" + eiAVAIL.getKey());
							if(isCountryList(_cat,gami,eiAVAIL.getEntityType(),eiAVAIL.getEntityID())) {
								D.ebug(D.EBUG_SPEW,"Planned Availability found for:" + eiAVAIL.getKey());
								bPrune = false;
							    // we only need to verify one.
							    break;
							}
						}
					}
				}
			}
		} else {
			bPrune = false;
		}

		// If configurator flag dictates a prune, we shalt prune.
		if(bConfigPrune) {
			bPrune = true;
		}

		if(bPrune) {
			D.ebug(D.EBUG_SPEW,"checkPrunProdstruct-->PRUNING:" + _smi.getChildType() + ":" + _smi.getChildID());
		    m_hashPrunedProdstructs.put(_smi.getChildType() + ":" + _smi.getChildID(),"PRUNE");
		} else {
			D.ebug(D.EBUG_SPEW,"checkPrunProdstruct-->KEEPING:" + _smi.getChildType() + ":" + _smi.getChildID());
		}
	}

//end FOR CR072607687


    /**
     * Check if the COUNTRYLIST attribute from the specified etype/eid matches our gami...
     */
    protected static final boolean isCountryList(Catalog _cat, GeneralAreaMapItem _gami, String _strEntityType, int _iEntityID) {

        String strCountryListGami = _gami.getCountryList();

        EntityItem eiCheck = Catalog.getEntityItem(_cat, _strEntityType, _iEntityID);
        EANFlagAttribute faCOUNTRYLIST = (EANFlagAttribute) eiCheck.getAttribute("COUNTRYLIST");

        if (faCOUNTRYLIST == null || faCOUNTRYLIST.toString().equals("")) {
            return false;
        }

        MetaFlag[] amf = (MetaFlag[]) faCOUNTRYLIST.get();
        for (int i = 0; i < amf.length; i++) {
            String strFlagCode = amf[i].getFlagCode();
            if (amf[i].isSelected() && strFlagCode.equals(strCountryListGami)) {
                return true;
            }
        }
        return false;
    }

///////////////////////
// END PRUNING CODE //
//////////////////////

}
