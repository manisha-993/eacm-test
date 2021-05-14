/*
 * Created on May 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.D;

/**
 * CatalogInterval
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CatalogInterval extends CatObject {

    private String StartDate = null;
    private String EndDate = null;

    private Class RequestingClass = null;

    /**
     * This guy will instantiate the class w/o looking in the database - it
     * assummes the caller understands the next sequence in their
     * workflow process
     *
     * @param _class
     * @param _strStart
     * @param _strEnd
     */
    public CatalogInterval(Class _class, String _strStart, String _strEnd) {
        this.setRequestingClass(_class);
        this.setStartDate(_strStart);
        this.setEndDate(_strEnd);
    }

    /**
     * This guy will look into the Catalog Database to see what the next interval of
     * Processing should be for the given class
     * In this case, the caller knows the end date  for the interval themselves
     * It is not assummed to be *now*
     * Right Now.. we have one CatalogInterval Represent all the NLS varying pulls
     * We do not have have repeat intervals for each NLS target
     *
     * @param _class
     * @param _strEnd
     * @param _cat
     */
    public CatalogInterval(Class _class, Catalog _cat, String _strEnd) {

        //
        // We know this much
        //

        Database db = _cat.getCatalogDatabase();

        //
        // The ObjectKey is the Class Name of the requestor + the GeneralArea Token
        //
        String strObjectKey = _class.getName();
        ResultSet rs = null;

		this.setRequestingClass(_class);
        try {

            DatePackage dp = db.getDates();
            setStartDate(dp.m_strEpoch);
            setEndDate(_strEnd);

			try {
	            rs = db.callGBL4014(new ReturnStatus(-1), _cat.getEnterprise(), strObjectKey);

    	        if (rs.next()) {
                	setStartDate(rs.getString(1));
        	    }
			} finally {
	            rs.close();
    	        db.commit();
        	    db.freeStatement();
            	db.isPending();
			}
        } catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * CatalogInterval
     *
     * @param _class
     * @param _cat
     */
    public CatalogInterval(Class _class, Catalog _cat) {

        Database db = _cat.getCatalogDatabase();
        String strEnterprise = _cat.getEnterprise();

        //
        // The ObjectKey is the Class Name of the requestor + the GeneralArea Token
        //
        String strObjectKey = _class.getName();
        ResultSet rs = null;
		this.setRequestingClass(_class);
        try {

            DatePackage dp = db.getDates();
            setStartDate(dp.m_strEpoch);
            if(CatalogRunner.getEndDate() != null) {
				setEndDate(CatalogRunner.getEndDate());
			} else {			
                String saveEndDate = getCATNETEndDate(_cat);  
                setEndDate(saveEndDate);
                CatalogRunner.setEndDate(saveEndDate);
			}

			try {
	            rs = db.callGBL4014(new ReturnStatus(-1), strEnterprise, strObjectKey);

	            //
	            // Lets set them both
	            //
	           if(CatalogRunner.getStartDate() != null) {
				    setStartDate(CatalogRunner.getStartDate());
				    D.ebug(D.EBUG_DETAIL,"CatalogInterval:using start date of:" + CatalogRunner.getStartDate());
			   } else if (rs.next()) {
	                setStartDate(rs.getString(1));
	            }
			} finally {
	            rs.close();
    	        db.commit();
        	    db.freeStatement();
            	db.isPending();
			}
        } catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        // TODO Auto-generated method stub
        return "CATI:" + this.getRequestingClass() + ":" + this.getStartDate() + ":" + this.getEndDate() + ":";
    }

    /**
     * dump
     *
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public String dump(boolean _breif) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * equals
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return 0;
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
    }

    /**
     * getEndDate
     *
     * @return
     */
    public String getEndDate() {
        return EndDate;
    }

    /**
     * getStartDate
     *
     * @return
     */
    public String getStartDate() {
        return StartDate;
    }

    /**
     * setEndDate
     *
     * @param string
     */
    public void setEndDate(String string) {
        EndDate = Catalog.zapDate(string);
    }

    /**
     * setStartDate
     *
     * @param string
     */
    public void setStartDate(String string) {
        StartDate = Catalog.zapDate(string);
    }

    /**
     * getRequestingClass
     *
     * @return
     */
    public Class getRequestingClass() {
        return RequestingClass;
    }

    /**
     * setRequestingClass
     *
     * @param class1
     */
    public void setRequestingClass(Class class1) {
        RequestingClass = class1;
    }

    public final void put(Catalog _cat) {

		if (Catalog.isDryRun()) {
			return;
		}

		Database db = _cat.getCatalogDatabase();
		String strEnterprise = _cat.getEnterprise();

		if (Catalog.isDryRun()) {
			return;
		}

		//
		// The ObjectKey is the Class Name of the requestor + the GeneralArea Token
		//
		String strObjectKey = this.getRequestingClass().getName();
		try {

			try {
				db.callGBL4009(new ReturnStatus(-1), strEnterprise, strObjectKey,this.getEndDate());
			} finally {
				db.commit();
				db.freeStatement();
				db.isPending();
			}
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    
    /**
     * get CATNET end date(DMNET last time).
     * @return
     * @author LiuBing
     */
    protected String getCATNETEndDate(Catalog cat) {  
    	
    	String runCATNETAlone = CatalogProperties.getRunCATNETAlone(); 
    	
    	String strAnswer = null; 	    
    	Statement stmt = null;
        ResultSet rs = null;
        Database db = cat.getCatalogDatabase();
        Connection conn = null; 
        
    	try {
			conn = db.getODSConnection();
			if(conn == null){
				db.connect();
	    	}
		} catch (MiddlewareException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
    	
    	if("Y".equalsIgnoreCase(runCATNETAlone)){
    		if(CatalogRunner.getCurrentTime() != null){
			    return  CatalogRunner.getCurrentTime();
    		}
    		else{
    			 try {
    			     DatePackage dp = db.getDates(); 			
    			     return dp.getNow();
    			 }catch (MiddlewareException e) {
    		            // TODO Auto-generated catch block
    		            e.printStackTrace();
    		        } 
    		}
    	}
    	else{
	        String strStatement = "SELECT RUNTIME FROM GBLI.TIMETABLE ORDER BY RUNTIME DESC";	        	        
	        try {
	            try {
	                stmt = conn.createStatement();
	                rs = stmt.executeQuery(strStatement);
	                if (rs.next()) {
	                    strAnswer = rs.getString(1).trim();
	                    D.ebug(D.EBUG_SPEW,"End time is : "+strAnswer);
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
	                if(conn!=null){
	                	conn.close();
	                }
	            }
	
	        }
	        catch (Exception se) {
	            D.ebug(D.EBUG_ERR, "getCATNETEndDate ERROR:" + se.getMessage());
	            System.exit( -1);
	        }
        }
        return strAnswer;
    }
}

/**
 * $Log: CatalogInterval.java,v $
 * Revision 1.6  2011/05/05 11:21:35  wendy
 * src from IBMCHINA
 *
 * Revision 1.7  2007/11/20 02:57:14  yang
 * *** empty log message ***
 *
 * Revision 1.6  2007/10/09 08:11:48  sulin
 * no message
 *
 * Revision 1.5  2007/06/18 01:36:48  liubdl
 * *** empty log message ***
 *
 * Revision 1.4  2007/06/08 05:45:40  liubdl
 * *** empty log message ***
 *
 * Revision 1.3  2007/06/07 02:06:56  liubdl
 * *** empty log message ***
 *
 * Revision 1.2  2007/06/07 02:06:22  liubdl
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2007/06/05 02:09:05  jingb
 * no message
 *
 * Revision 1.4  2007/05/15 15:09:07  rick
 * Set end date for first collection instead of getting
 * NOW for each collection.
 * Keeps end dates in synch for all collections.
 *
 * Revision 1.3  2006/09/06 20:53:52  gregg
 * allow definition of start date from runtime parm
 *
 * Revision 1.2  2006/07/20 22:03:17  gregg
 * CatalogRunner.getEndDate option
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.19  2005/06/13 05:21:56  dave
 * needed another put check for dryrun on catalog interval;
 *
 * Revision 1.18  2005/06/13 04:35:33  dave
 * ! needs to be not !
 *
 * Revision 1.17  2005/06/13 04:02:05  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.16  2005/06/07 13:21:24  dave
 * closing the loop and posting time back to the
 * timetable
 *
 * Revision 1.15  2005/06/01 03:32:14  dave
 * JTest clean up
 *
 * Revision 1.14  2005/05/29 00:25:29  dave
 * ok.. clean up and reorg to better support pulls from PDH
 *
 * Revision 1.13  2005/05/27 05:34:11  dave
 * doing some date manipulation
 *
 * Revision 1.12  2005/05/27 04:04:41  dave
 * more
 *
 *
 */
