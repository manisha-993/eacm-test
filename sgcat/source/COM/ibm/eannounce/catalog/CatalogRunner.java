/*
 * Created on July 12, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: CatalogRunner.java,v $
 * Revision 1.14  2011/05/05 11:21:34  wendy
 * src from IBMCHINA
 *
 * Revision 1.4  2007/11/20 03:03:39  yang
 * *** empty log message ***
 *
 * Revision 1.3  2007/09/13 08:17:10  sulin
 * no message
 *
 * Revision 1.2  2007/09/13 05:54:52  sulin
 * no message
 *
 * Revision 1.1.1.1  2007/06/05 02:09:05  jingb
 * no message
 *
 * Revision 1.12  2007/05/15 15:17:57  rick
 * New set end date method to be called by CatalogInterval
 * to keep the end date in synch for all collections.
 *
 * Revision 1.11  2007/03/05 13:44:35  rick
 * 0308 deply to allow synch took message for log level 2.
 *
 * Revision 1.10  2006/09/21 20:19:55  gregg
 * isBasicRuleLoad
 *
 * Revision 1.9  2006/09/06 20:53:52  gregg
 * allow definition of start date from runtime parm
 *
 * Revision 1.8  2006/08/14 20:33:33  gregg
 * change to class variable names
 *
 * Revision 1.7  2006/07/20 22:24:32  gregg
 * more error msg
 *
 * Revision 1.6  2006/07/20 22:22:05  gregg
 * cleaner error msgs
 *
 * Revision 1.5  2006/07/20 22:15:17  gregg
 * some Exception handling mumbo jumbo
 *
 * Revision 1.4  2006/07/20 22:12:13  gregg
 * Ok folks, let's clear locks even (Especially!) after an exception is thrown.
 *
 * Revision 1.3  2006/07/20 22:10:12  gregg
 * more parms
 *
 * Revision 1.2  2006/07/20 22:01:31  gregg
 * Some fancy parm passing + end date option
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.14  2006/01/17 22:46:06  bala
 *
 * added pid 20 to canCatalogRun
 *
 * Revision 1.13  2006/01/17 18:08:43  gregg
 * fix for exception
 *
 * Revision 1.12  2006/01/17 18:03:15  gregg
 * ECCM->EACM
 *
 * Revision 1.11  2006/01/17 17:55:37  gregg
 * gb comment
 *
 * Revision 1.10  2006/01/17 16:47:43  dave
 * <No Comment Entered>
 *
 * Revision 1.9  2006/01/16 18:09:54  dave
 * New Imf Checker per IBM requirement
 *
 * Revision 1.8  2005/10/25 07:05:49  dave
 * memory cleanup
 *
 * Revision 1.7  2005/10/25 04:09:24  dave
 * curtailing smc's to spew
 *
 * Revision 1.6  2005/07/14 19:05:10  gregg
 * some Exception stack traces just in case
 *
 * Revision 1.5  2005/07/13 21:00:39  gregg
 * usr shortened class name for log names.
 *
 * Revision 1.4  2005/07/13 20:48:07  gregg
 * add pacakge path to class names
 *
 * Revision 1.3  2005/07/13 20:25:51  gregg
 * property-ize classes to run; add date to log file name
 *
 * Revision 1.2  2005/07/12 21:48:52  gregg
 * some logging per specific class
 *
 * Revision 1.1  2005/07/12 21:29:43  gregg
 * initial load
 *
 */

package COM.ibm.eannounce.catalog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import COM.ibm.eannounce.catalog.CatalogProperties;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Stopwatch;

/**
 * Use this to ultimately run all our Catalog processes.
 */
public final class CatalogRunner {

    static {
        D.isplay("CatalogRunner:" + getVersion());
    }

    private static final int SYNC = 0;
    private static final int IDL = 1;

    private static final String LOG_PATH = "./rpt/";
    private static final String SYNC_METHOD_NAME = "syncToCatDb";
    private static final String IDL_METHOD_NAME = "idlToCatDb";
    private static final String PACKAGE_PATH = "COM.ibm.eannounce.catalog";

    private static final SimpleDateFormat SDF_LOG_TIMESTAMP = new SimpleDateFormat("yyyyMMddHHmm");

    /**
     * These are the classes for which we run Sync/IDLs.
     */
    private static final String[] c_astrClasses = CatalogProperties.getCatRunnerClasses();

    /**
     * Provide a command line option for defining endpoint.
     */
    private static String c_strEndDate = null;
    /**
     * Ditto for start
     */
    private static String c_strStartDate = null;
    
    private static int c_iType = -1;

    /**
     * Let's allow an IDL to reload ONLY specific basicrules.
     */
    private static boolean c_bBasicRuleLoad = false;
	/**
	 * Added by Yujiang He. 2007-08-02 It's value is set according to a command
	 * line switch, tell CatalogRunner branch into collection splitting process
	 */
	private static boolean c_bStartSplitProc = false;

	private final static String PARA_SYNC_METHOD_NAME = "parallelSyncToCatDb";
	
	private static String c_strCRNo = "";
	
	private static String c_currentTime = null;

	private static List Col_List = new ArrayList();

    public static final void main(String[] args) throws Exception {

    	Catalog cat = null;
    	try {
    	    cat = new Catalog();
		} catch(Exception exc) {
			System.err.println("error Creating Catalog object.");
		    throw exc;
		}

        try {

            setupParms(args);

            if(getEndDate() != null && c_iType == IDL) {
				String strMsg = "You cannot specify an end date for an IDL! Duh.";
				//System.err.println(strMsg);
				throw new CatalogRunnerParmException(strMsg);
			}

			if (c_bStartSplitProc) {
				try {
					// here should be some option check. added future
					startSplitProc(cat);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}

            if (CatalogRunner.canCatalogRun(cat)) {
                CatalogRunner.setIFMLock(cat);
                String strLogDate = SDF_LOG_TIMESTAMP.format(new Date());

                if (c_iType == SYNC) {
                    CatalogRunner.moveToCatDb(SYNC, strLogDate);
                }
                else if (c_iType == IDL) {
                    CatalogRunner.moveToCatDb(IDL, strLogDate);
                } else {
					String strMsg = "Please specify an argument for \"-r\": IDL or SYNC";
					//System.err.println(strMsg);
					throw new CatalogRunnerParmException(strMsg);
				}
                CatalogRunner.clearIFMLock(cat);
            } else {
                System.err.println("system is busy processing data.. cannot run at this time");
            }

            //
            // make sure we close this
            //
            cat.close();

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            CatalogRunner.clearIFMLock(cat);
        } catch (MiddlewareException ex) {
            ex.printStackTrace();
            CatalogRunner.clearIFMLock(cat);
        } catch(CatalogRunnerParmException ex) {
			if(args == null || args.length == 0) {
				System.err.println("No Parms passed!!");
			} else {
				System.err.println("PARMS:");
				for(int i = 0; i < args.length; i++) {
					System.err.println("  args[" + i + "]:\"" + args[i] + "\"");
				}
			}
			System.err.println(ex.getMessage());
			//ex.printStackTrace();
			CatalogRunner.clearIFMLock(cat);
		}

    }

    private static final void setupParms(String[] _args) throws CatalogRunnerParmException {
        if(_args == null || _args.length == 0) {
			String strMsg = "No Parms Passed!!";
			//System.err.println(strMsg);
			throw new CatalogRunnerParmException(strMsg);
		}
		for(int i = 0; i < _args.length; i++) {
			if(_args[i].startsWith("-")) {
				switch(_args[i].charAt(1)) {
					case 'r' :
					    if(i == _args.length-1) {
							String strMsg = "Please specify an argument for \"-r\": IDL or SYNC";
							//System.err.println(strMsg);
							throw new CatalogRunnerParmException(strMsg);
						}
						if(_args[i+1].equalsIgnoreCase("IDL")) {
							c_iType = IDL;
						} else if(_args[i+1].equalsIgnoreCase("SYNC")) {
							c_iType = SYNC;
						} else {
							String strMsg = "Only valid Arguments for \"-r\" are: IDL or SYNC";
							//System.err.println(strMsg);
							throw new CatalogRunnerParmException(strMsg);
						}
					    break;
					case 'e':
					    if(i == _args.length-1) {
							String strMsg = "Please specify an end date in the form:yyyy-MM-dd-hh-mm.ss.zzzzzz";
							//System.err.println(strMsg);
							throw new CatalogRunnerParmException(strMsg);
						}
                        c_strEndDate = _args[i+1];
					    break;
					case 's':
					    if(i == _args.length-1) {
							String strMsg = "Please specify an end date in the form:yyyy-MM-dd-hh-mm.ss.zzzzzz";
							//System.err.println(strMsg);
							throw new CatalogRunnerParmException(strMsg);
						}
                        c_strStartDate = _args[i+1];
					    break;
					case 'b' : // BasicRuleLoad

						c_bBasicRuleLoad = true;

					    break;

				// Added by Yujiang He. 2007-08-02
				// This switch will instruct CatalogRunner branch into
				// collection splitting process
				case 'p':
					c_bStartSplitProc = true;
					break;
				case 't':
				    if(i == _args.length-1) {
						String strMsg = "Can't get Current time,please check shell script runallcollection_para.sh";
						//System.err.println(strMsg);
						throw new CatalogRunnerParmException(strMsg);
					}
                    c_currentTime = _args[i+1];
				    break;
				case 'n':
					if (i == _args.length - 1) {
						String strMsg = "Please specify an argument for \"-r\": IDL or SYNC";
						//System.err.println(strMsg);
						throw new CatalogRunnerParmException(strMsg);
					}
					if (_args[i + 1].equalsIgnoreCase("")) {
						String strMsg = "please specify process num for \"-n\" .";
						//System.err.println(strMsg);
						throw new CatalogRunnerParmException(strMsg);
					} else {
						c_strCRNo = _args[i + 1];
					} 
					break;

					default:
						String strMsg = "Huh?? I don't understand the argument, \"" + _args[i].charAt(1) + "\"";
						//System.err.println(strMsg);
						throw new CatalogRunnerParmException(strMsg);
						//break;
		        }
			}
		}
	}

    /**
     * Sync/IDL All to Cataolog Database.
     */
    private static final void moveToCatDb(int _iType, String _strLogDate) {

        for (int i = 0; i < c_astrClasses.length; i++) {
            Stopwatch sw = new Stopwatch();
            sw.start();
            String strShortClassName = c_astrClasses[i];
            Class c = null;
            try {
                c = Class.forName(PACKAGE_PATH + "." + strShortClassName);
            }
            catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace(System.err);
                continue;
            }

            String strLogName = LOG_PATH + strShortClassName + "." + _strLogDate;
            D.ebugSetOut(strLogName);

            log("Starting " + (_iType == SYNC ? "SYNC" : "IDL") + " for " + c.getName());
            try {
                Class cls[] = new Class[] {};
                Object obj[] = new Object[] {};
                Method m = c.getDeclaredMethod(_iType == SYNC ? SYNC_METHOD_NAME : IDL_METHOD_NAME, cls);
                m.invoke(c, obj);
                //
                // Cleanup
                //
                for (int x = 0; i < cls.length; x++) {
                    cls[x] = null;
                }
                for (int x = 0; i < obj.length; x++) {
                    obj[x] = null;
                }

            }
            catch (NoSuchMethodException nsme) {
                log(nsme.toString());
                nsme.printStackTrace(System.err);
            }
            catch (SecurityException se) {
                log(se.toString());
                se.printStackTrace(System.err);
            }
            catch (IllegalAccessException iae) {
                log(iae.toString());
                iae.printStackTrace(System.err);
            }
            catch (InvocationTargetException ite) {
                log(ite.toString());
                ite.printStackTrace(System.err);
            }

            log( (_iType == SYNC ? "SYNC" : "IDL") + " took " + sw.finish());
        }

    }

    /**
     * setIFMLock
     *
     *  @author David Bigelow
     */
    protected static void setIFMLock(Catalog _cat) throws MiddlewareException {
//
        Database db = null;
        Connection con = null;

        String strSelect = "SELECT * FROM EACM.IFMLOCK WHERE PROCESS_ID = 1";
        String strUpdate = "UPDATE EACM.IFMLOCK SET STATUS = 1, START_TIME = CURRENT TIMESTAMP WHERE PROCESS_ID = 1";
        String strInsert = "INSERT INTO EACM.IFMLOCK (PROCESS_ID,PROCESS_NAME,STATUS,START_TIME, END_TIME) VALUES " +
            "(1,'CATPOP',0,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";

        Statement stmt = null;
        ResultSet rs = null;
        Statement stmtInsertTable = null;
        Statement stmtUpdateTable = null;

        boolean bExists = false;

        try {

            db = _cat.getCatalogDatabase();
            db.connect();
            con = db.getPDHConnection();

            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(strSelect);
                bExists = rs.next();
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                con.commit();
            }

            if (!bExists) {
                log("setIFMLock:Inserting PROCESS_ID = 2 INTO EACM.IFMLOCK");
                try {
                    stmtInsertTable = con.createStatement();
                    stmtInsertTable.execute(strInsert);
                }
                finally {
                    if (stmtInsertTable != null) {
                        stmtInsertTable.close();
                        stmtInsertTable = null;
                    }
                    con.commit();
                }
            }

            log("setIFMLock:Update PROCESS_ID = 2 INTO EACM.IFMLOCK");
            try {
                stmtUpdateTable = con.createStatement();
                stmtUpdateTable.execute(strUpdate);
            }
            finally {
                if (stmtUpdateTable != null) {
                    stmtUpdateTable.close();
                    stmtUpdateTable = null;
                }
                con.commit();
            }

            if (con != null) {
                con.commit();
                con.close();
            }
            if (db != null) {
                db.close();
            }

        }
        catch (SQLException e) {
            log("setIFMLock:ERROR: on EACM.IFMLOCK:" + e.getMessage());
            System.exit( -1);
        }

    }

    /**
     * clearIFMLock
     *
     *  @author David Bigelow
     */
    protected static void clearIFMLock(Catalog _cat) throws MiddlewareException {

        String strUpdate = "UPDATE EACM.IFMLOCK SET STATUS = 0, END_TIME = CURRENT TIMESTAMP WHERE PROCESS_ID = 1";
        Statement stmtUpdateTable = null;

        Database db = null;
        Connection con = null;

        try {

            db = _cat.getCatalogDatabase();
            db.connect();
            con = db.getPDHConnection();

            log("setIFMLock:Update PROCESS_ID = 2 INTO EACM.IFMLOCK");

            // force a change
            try {
                stmtUpdateTable = con.createStatement();
                stmtUpdateTable.execute(strUpdate);
            }
            finally {
                if (stmtUpdateTable != null) {
                    stmtUpdateTable.close();
                    stmtUpdateTable = null;
                }
                if (con != null) {
                    con.commit();
                    con.close();
                }
                if (db != null) {
                    db.close();
                }
            }

        }
        catch (SQLException e) {
            log("setIFMLock:ERROR: on EACM.IFMLOCK:" + e.getMessage());
            System.exit( -1);
        }

    }

    /**
     * We can only run if process 8,9, and 11 are not
     * @return
     * @author Dave
     */
    protected static final boolean canCatalogRun(Catalog _cat) throws MiddlewareException {

        Statement stmt = null;
        ResultSet rs = null;
        Database db = null;
        Connection con = null;

        //
        // 1 = myself
        // 4 = EACM PRice
        // 10 = Cat Generator
        // 20 = Catalog ODS
        String strStatement = "SELECT COUNT(*) from eacm.IFMLOCK where STATUS = 1 AND PROCESS_ID IN (1,4,10,20)";
        int iAnswer = 0;

        try {

            try {
                db = _cat.getCatalogDatabase();
				db.connect();
                con = db.getPDHConnection();
                stmt = con.createStatement();
                rs = stmt.executeQuery(strStatement);
                if (rs.next()) {
                    iAnswer = rs.getInt(1);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (con != null) {
                    con.close();
                }
                if (db != null) {
                    db.close();
                }
            }
        }
        catch (SQLException se) {
            log("Cannot run had an SQL error... ");
            se.printStackTrace();
            System.exit( -1);
        }
        return iAnswer == 0;

    }

    private static final void log(String _strMsg) {
        D.ebug(D.EBUG_INFO, _strMsg);
    }

    protected static final String getEndDate() {
		return c_strEndDate;
	}

    public static final void setEndDate(String _s) {
		c_strEndDate = _s;
	}

    protected static final String getStartDate() {
		return c_strStartDate;
	}
    
    public static final String getCurrentTime() {
		return c_currentTime;
	}

	protected static final boolean isBasicRuleLoad() {
		return c_bBasicRuleLoad;
	}

    /**
     *  Return the version of this class
     *
     *@return    The version value
     */
	public final static String getVersion() {
		return new String(
				"$Id: CatalogRunner.java,v 1.14 2011/05/05 11:21:34 wendy Exp $");
	}

	/***************************************************************************
	 * 
	 * 
	 * BELOW METHODS ARE DEFINED FOR COLLECTION SPLITTING TEST
	 * 
	 * 
	 **************************************************************************/
	/**
	 * Start collection splitting test process Add by Yujiang He 2007-08-02
	 * 
	 * @return null
	 */
	private static void startSplitProc(Catalog cat) throws Exception {
		String nextCollection = null;

		// --------------------
		try {
			// first check if i can run
			if (CatalogRunner.canParaCatalogRun(cat)) { // 1. can run?
				// Although it should be set just once, but here, it may be set
				// multiple times,
				// not a problem, as long its logic is right
				CatalogRunner.setParaIFMLock(cat); // 2. set lock

				String strLogDate = SDF_LOG_TIMESTAMP.format(new Date());

				getCollections(cat);// store Collections into Col_list ;
				
				while (true) {
					nextCollection = getNextCollection(cat);
					if (nextCollection == null) {
						log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: There is no collection any more!");

						// break out
						break;
					}
					log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: Now start the next collection :"
							+ nextCollection);

					CatalogRunner.paraMoveToCatDb(SYNC, strLogDate,
							nextCollection);
					log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: After process :" + nextCollection);

					updateCollectionStatus(nextCollection);

					if (isFinalCompleted()) {
						log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: " + nextCollection
								+ " is the last collection ");
						log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: No more to do. We'll clear ifmlock and exit");
						log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: delete all temp file now!");
						delFiles();
						clearParaIFMLock(cat);
						break;
					} else {
						log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: We've just finished"
								+ nextCollection + ". Now let's find another.");
						continue;
					}
				}
				log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: Current process finished.");

			} else {
				System.err
						.println("system is busy processing data.. cannot run at this time");
			}
			cat.close();
			Col_List = null;
		} catch (SQLException ex) {
			ex.printStackTrace();
			CatalogRunner.clearParaIFMLock(cat);
		} catch (MiddlewareException ex) {
			ex.printStackTrace();
			CatalogRunner.clearParaIFMLock(cat);
		}

		System.exit(0);

	}

	/*
	 * Add by Hou Jie && Yujiang He 2007-08-03 Before parallel CatalogRunners
	 * run, they should check if sequential CatalogRunner or any other
	 * confilicting program in processing.
	 * 
	 * But parallel CatalogRunners should be permitted to run together @ return
	 * True if it can run, otherwise false
	 */
	private static boolean canParaCatalogRun(Catalog _cat)
			throws MiddlewareException {
		Statement stmt = null;
		ResultSet rs = null;
		Database db = null;
		Connection con = null;

		// 1 = myself
		// 4 = EACM PRice
		// 10 = Cat Generator
		// 20 = Catalog ODS
		// 50 = CatalogRunner in parallel
		String strStatement = "SELECT COUNT(*) FROM EACM.IFMLOCK WHERE PROCESS_ID = 1 and STATUS=1 and (not exists (select * from EACM.IFMLOCK where PROCESS_ID=50) or exists(select * from EACM.IFMLOCK where PROCESS_ID = 50 and STATUS=0)) or exists(select * from EACM.IFMLOCK where PROCESS_ID IN (4,10,20) and STATUS=1)";

		int iAnswer = 0;

		try {

			try {
				db = _cat.getCatalogDatabase();
				db.connect();
				con = db.getPDHConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(strStatement);
				if (rs.next()) {
					iAnswer = rs.getInt(1);
				}
			} finally {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (con != null) {
					con.close();
				}
				if (db != null) {
					db.close();
				}
			}
		} catch (SQLException se) {
			log("Cannot run had an SQL error... ");
			se.printStackTrace();
			System.exit(-1);
		}
		return iAnswer == 0;
	}

	/**
	 * Get next runnable collection from table EACM.CollectionStatus Add by
	 * Guo Bin 2007-09-02 @ return The collection class name
	 */
	private static String getNextCollection(Catalog _cat)
			throws MiddlewareException {
		String nextCollection = "";
		try {
			for (int i = 0; i < Col_List.size(); i++) {
				nextCollection = Col_List.toArray()[i].toString();
				String filePath1 = "./stop" + nextCollection;
				boolean isStop = (new File(filePath1)).exists();
				if (!isStop){
					String filePath2 = "./run" + nextCollection;
					boolean result = makeFile(filePath2);
					if (result == true) {
						log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: Now start create filepath: "
								+ nextCollection);
						return nextCollection;
					
				    }else{
						log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: " + nextCollection + " is runing!");				
				         }	
				}else{
					log("CatalogRunner "+ c_strCRNo + " startSplitProc:INFO: " + nextCollection + " is Stoped!");					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Add by Hoe Jie && Yujiang He 2007-08-03 Before parallel CatalogRunners
	 * start data processing, they should set IFMLock to tell other processes.
	 * 
	 * On the other hand, the parallel CatalogRunner which set IFMLock also need
	 * to tell other parallel CatalogRunners that we are parallel processing so,
	 * they can run together @ return null
	 */
	private static void setParaIFMLock(Catalog cat) throws MiddlewareException {
		String sqlContidion1 = "SELECT * FROM EACM.IFMLOCK WHERE PROCESS_ID = 1";
		String sqlContidion2 = "SELECT * FROM EACM.IFMLOCK WHERE PROCESS_ID = 50";
		String sqlAction1_01 = "UPDATE EACM.IFMLOCK set STATUS=1 where PROCESS_ID = 1";
		String sqlAction1_02 = "INSERT INTO EACM.IFMLOCK (PROCESS_ID,PROCESS_NAME,STATUS,START_TIME, END_TIME) VALUES (1,'CATPOP',1,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";
		String sqlAction2_01 = "UPDATE EACM.IFMLOCK set STATUS=1 where PROCESS_ID =50";
		String sqlAction2_02 = "INSERT INTO EACM.IFMLOCK (PROCESS_ID,PROCESS_NAME,STATUS,START_TIME, END_TIME) VALUES (50,'ParaCatalogRunner',1,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";
		String sqlContidions[] = { sqlContidion1, sqlContidion2 };
		String sqlActions[][] = { { sqlAction1_01, sqlAction1_02 },
				{ sqlAction2_01, sqlAction2_02 } };
		String sqlResetTable = "Update EACM.CollectionStatus set STATUS=0";
		Database db = null;
		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement actionStmt = null;
		ResultSet rs = null;
		try {
			db = cat.getCatalogDatabase();
			db.connect();
			con = db.getPDHConnection();
			// step1.set IMFLOCK for prosessid=1,50, set status=1
			for (int i = 0; i < sqlContidions.length; i++) {
				try {
					stmt = con.prepareStatement(sqlContidions[i]);
					rs = stmt.executeQuery();
					if (rs.next()) {
						actionStmt = con.prepareStatement(sqlActions[i][0]);// update
					} else {
						actionStmt = con.prepareStatement(sqlActions[i][1]);// insert
					}
					actionStmt.executeUpdate();
				} finally {
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
					if (actionStmt != null) {
						actionStmt.close();
					}
				}
			}
			// step2.reset EACM.CollectionStatus
			try {
				stmt = con.prepareStatement(sqlResetTable);
				stmt.executeUpdate();
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
			if (con != null) {
				con.commit();
				con.close();
			}
			if (db != null) {
				db.close();
			}
		} catch (SQLException e) {
			log("setParaIFMLock:ERROR: on EACM.IFMLOCK:" + e.getMessage());
			System.exit(-1);
		}
	}

	/*
	 * Add by Yujiang He 2007-08-03 Before parallel CatalogRunners start data
	 * processing, they should set IFMLock to tell other processes.
	 * 
	 * On the other hand, the parallel CatalogRunner which set IFMLock also need
	 * to tell other parallel CatalogRunners that we are parallel processing so,
	 * they can run together @ return null
	 */
	private static final void paraMoveToCatDb(int _iType, String _strLogDate,
			String _colName) {

		Stopwatch sw = new Stopwatch();
		sw.start();
		Class c = null;
		try {
			log("paraMoveToCatDb: INFO: Now loading the collection class "
					+ PACKAGE_PATH + "." + _colName);
			c = Class.forName(PACKAGE_PATH + "." + _colName);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace(System.err);
			System.exit(0);
		}

		String strLogName = LOG_PATH + _colName + "." + _strLogDate;
		D.ebugSetOut(strLogName);

		log("Starting " + (_iType == SYNC ? "SYNC" : "IDL") + " for "
				+ c.getName());
		try {
			Class cls[] = new Class[] {};
			Object obj[] = new Object[] {};
			// Method m = c.getDeclaredMethod(_iType == SYNC ?
			// PARA_SYNC_METHOD_NAME : IDL_METHOD_NAME, cls);
			Method m = c.getDeclaredMethod(PARA_SYNC_METHOD_NAME, cls);
			m.invoke(c, obj);
			//
			// Cleanup
			//
			for (int x = 0; x < cls.length; x++) {
				cls[x] = null;
			}
			for (int x = 0; x < obj.length; x++) {
				obj[x] = null;
			}

		} catch (NoSuchMethodException nsme) {
			log(nsme.toString());
			nsme.printStackTrace(System.err);
		} catch (SecurityException se) {
			log(se.toString());
			se.printStackTrace(System.err);
		} catch (IllegalAccessException iae) {
			log(iae.toString());
			iae.printStackTrace(System.err);
		} catch (InvocationTargetException ite) {
			log(ite.toString());
			ite.printStackTrace(System.err);
		}

		log((_iType == SYNC ? "SYNC" : "IDL") + " took " + sw.finish());

	}

	/*
	 * Add by GuoBin  2007-09-03 After running a collection, it's status
	 * should be changed,file name be changed from "run%%" to "stop%%" @ return null
	 */

	private static void updateCollectionStatus(String colName
			) {
		   File oldFile = new File("./run" + colName);
		   File newFile = new File("./stop" +colName);
		   boolean success = oldFile.renameTo(newFile);
		   if(!success){
			   log("File run" + colName + " is not rename successfully !");
		   }
		   
	}

	/*
	 * Add by Guo Bin2007-09-03 Use this method to check if it's
	 * the last one completed @ return True if it is the last one completed,
	 * otherwise false
	 */
	private static boolean isFinalCompleted()
      	 {
		String nextCollection = "";
		boolean result = true;
			for(int i=0;i < Col_List.size();i++){
		    nextCollection = Col_List.toArray()[i].toString();
		    String filePath = "./stop" + nextCollection;
			boolean exists = (new File(filePath)).exists();
			    if(!(exists))
			    {
				    return false;
			    }
			}
			return result;
	}

	/*
	 * Add by Guo Bin 2007-8-23 Create a file,the name represents current
	 * running Collection. @ return Ture or False
	 */
	private static boolean makeFile(String filepath) throws IOException {
		boolean result = false;
		File file = new File(filepath);
		result = file.createNewFile();
		file = null;
		return result;

	}

	/*
	 * Add by Guo Bin 2007-8-23 Create a file,the name represents current
	 * running Collection. @ return Ture or False
	 */
	public static void delFiles() {

		String nextCollection = "";
		try{
	            for (int i=0; i<Col_List.size(); i++)
	            {
	            	nextCollection = Col_List.toArray()[i].toString();
	            	String filepath1 = "./stop" + nextCollection;
	            	String filepath2 = "./run" + nextCollection;
					File file1 = new File(filepath1);
					boolean result1 = file1.delete();
					if (result1) {
						log(filepath1 + "delete successfully !");
					} else {
						log(filepath1 + "delete falsely !");
					}
					File file2 = new File(filepath2);
					if(file2.exists()){
						boolean result2 = file2.delete();
					}
					file1 = null;
					file2 = null;
		        }
		}catch (SecurityException e){		
			e.printStackTrace();
			log( "delete falsely and throw SecurityException !");
		}
	}

	/*
	 * Add by guo bin 2007-08-27 before parallel CatalogRunners get all
	 * Collections from EACM.Collectionstatus and put them into Col_List.
	 */
	private static void getCollections(Catalog cat) throws MiddlewareException {
		Database db = null;
		ResultSet rs = null;
		Connection con = null;
		Statement stmtQuery = null;
		// Statement stmtUpdate = null;
		boolean origConAuotCommit = false;
		try {
			String strSelect = "select collectionname from EACM.COLLECTIONSTATUS";
			db = cat.getCatalogDatabase();
			db.connect();
			con = db.getPDHConnection();

			origConAuotCommit = con.getAutoCommit();
			con.setAutoCommit(false);
			stmtQuery = con.createStatement();
			rs = stmtQuery.executeQuery(strSelect);
			if (!(rs == null)) {
				while (rs.next()) {
					String Collection = rs.getString(1);
					Col_List.add(Collection);
				}
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (MiddlewareException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} finally {

			if (con != null) {
				try {
					con.setAutoCommit(origConAuotCommit);
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (db != null) {
				db.close();
			}

		}
	}

	/*
	 * Add by Hou Jie && Yujiang He 2007-08-03 After parallel CatalogRunners all
	 * completes, the last one should clear IFMLock @ return null
	 */
	private static void clearParaIFMLock(Catalog cat)
			throws MiddlewareException {
		String sqlUnlock = "UPDATE EACM.IFMLOCK set STATUS=0 WHERE PROCESS_ID in(1,50)";
		Database db = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			db = cat.getCatalogDatabase();
			db.connect();
			con = db.getPDHConnection();
			// check sql contidions
			try {
				stmt = con.prepareStatement(sqlUnlock);
				stmt.executeUpdate();
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
			if (con != null) {
				con.commit();
				con.close();
			}
			if (db != null) {
				db.close();
			}
		} catch (SQLException e) {
			log("clearParaIFMLock:ERROR: on EACM.IFMLOCK:" + e.getMessage());
			System.exit(-1);

		}

	}
}
