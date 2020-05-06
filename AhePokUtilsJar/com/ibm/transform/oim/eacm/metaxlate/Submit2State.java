// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.metaxlate;

import java.io.Serializable;
import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.NLSItem;

/**
 * Implements the second step in the Flag Translation Submission Process. It
 * executes the GBL2921 middleware call using the earliest selected date from the
 * previous step (Submit1State). Any languages that do not have any changes are
 * not selected for the next step. This does not affect the selections in the
 * prior step. If there are no query results you will not be able to proceed to
 * Submit3State.
 * <pre>
 * Licensed Materials -- Property of IBM
 *
 * (c) Copyright International Business Machines Corporation, 2002, 2006
 * All Rights Reserved.
 *
 * CVS Log History
 * $Log: Submit2State.java,v $
 * Revision 1.5  2006/04/17 19:37:16  chris
 * JTest changes
 *
 * Revision 1.4  2006/03/10 21:11:31  chris
 * added serializeable and default constructors
 *
 * Revision 1.3  2006/03/10 20:00:08  chris
 * Make all versions final
 *
 * Revision 1.2  2006/01/26 20:52:51  sergio
 * AHE copyright
 *
 * Revision 1.1  2005/09/16 18:20:10  chris
 * Updates for Application Hosting Environment
 *
 * Revision 1.2  2005/02/16 17:50:20  chris
 * JTest cleanup
 *
 * Revision 1.1.1.1  2004/01/26 17:40:01  chris
 * Latest East Coast Source
 *
 * Revision 1.6  2003/09/26 19:00:59  cstolpe
 * Fix for FB 52098:694033
 *
 * Revision 1.5  2003/09/12 16:31:16  cstolpe
 * locking stubs and block submit
 *
 * Revision 1.4  2003/09/11 21:45:29  cstolpe
 * Latest Updates
 *
 * Revision 1.3  2003/09/04 20:27:08  cstolpe
 * latest changes for 1.2H
 *
 * Revision 1.2  2003/06/20 12:50:38  cstolpe
 * Initial 1.2H port
 *
 * Revision 1.1  2002/08/07 16:06:13  cstolpe
 * CR0718026417 (CR0625023357) Multiple urls for V1.0.1
 *
 * Revision 1.0.0.1  2002/06/28 11:33:22  cstolpe
 * Initialize
 *
 * </pre>
 * @author Chris Stolpe
 * @version $Revision: 1.5 $
 */
public class Submit2State extends PageableState implements Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = 1289656716295572182L;
	/** Automatically generated javadoc for: MAXHANDLEDCHANGES */
    private static final int MAXHANDLEDCHANGES = 1000;
    /**
	 * CVS Version 
	 */
	public static final String VERSION = "$Revision: 1.5 $";  //$NON-NLS-1$
	/**
	 * Query Date
	 */
	private String entityType = null;
	/**
	 * Query Date
	 */
	private int entityID = -1;
	/**
	 * Profile
	 */
	private Profile profile = null;
	/**
	 * Query Date
	 */
	private String queryDate = null;
	/**
	 * Keep track of the states 
	 */
	private Stack stack  = new Stack();
	/**
	 * Cache the index 
	 */
	private IndexState index = null;
	/**
	 * Keep track of how many changes there are per language
	 * Map of NLSItem -> Integer
	 */
	private Map languageChanges = new HashMap();
	/**
	 * The Selected dates for Submit1State
	 * List of NLSItem
	 */
	private ArrayList queryLanguages = new ArrayList();
	
	/**
	 * Map NLSItem to Search Action
	 */
	private static Map mapSearch = new HashMap();
	
	static {
		mapSearch.put(Profile.GERMAN_LANGUAGE, "SRDMETAXLATESTATUS2");  //$NON-NLS-1$
		mapSearch.put(Profile.ITALIAN_LANGUAGE, "SRDMETAXLATESTATUS3");  //$NON-NLS-1$
		mapSearch.put(Profile.JAPANESE_LANGUAGE, "SRDMETAXLATESTATUS4");  //$NON-NLS-1$
		mapSearch.put(Profile.FRENCH_LANGUAGE, "SRDMETAXLATESTATUS5");  //$NON-NLS-1$
		mapSearch.put(Profile.SPANISH_LANGUAGE, "SRDMETAXLATESTATUS6");  //$NON-NLS-1$
		mapSearch.put(Profile.UK_ENGLISH_LANGUAGE, "SRDMETAXLATESTATUS7");  //$NON-NLS-1$
		mapSearch.put(Profile.KOREAN_LANGUAGE, "SRDMETAXLATESTATUS8");  //$NON-NLS-1$
		mapSearch.put(Profile.CHINESE_LANGUAGE, "SRDMETAXLATESTATUS9");  //$NON-NLS-1$
		mapSearch.put(Profile.FRENCH_CANADIAN_LANGUAGE, "SRDMETAXLATESTATUS10");  //$NON-NLS-1$
		mapSearch.put(Profile.CHINESE_SIMPLIFIED_LANGUAGE, "SRDMETAXLATESTATUS11");  //$NON-NLS-1$
		mapSearch.put(Profile.SPANISH_LATINAMERICAN_LANGUAGE, "SRDMETAXLATESTATUS12");  //$NON-NLS-1$
		mapSearch.put(Profile.PORTUGUESE_BRAZILIAN_LANGUAGE, "SRDMETAXLATESTATUS13");  //$NON-NLS-1$

		mapSearch.put("2", Profile.GERMAN_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("3", Profile.ITALIAN_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("4", Profile.JAPANESE_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("5", Profile.FRENCH_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("6", Profile.SPANISH_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("7", Profile.UK_ENGLISH_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("8", Profile.KOREAN_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("9", Profile.CHINESE_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("10", Profile.FRENCH_CANADIAN_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("11", Profile.CHINESE_SIMPLIFIED_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("12", Profile.SPANISH_LATINAMERICAN_LANGUAGE);  //$NON-NLS-1$
		mapSearch.put("13", Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);  //$NON-NLS-1$
	}
	/**
     * Constructor
     *
     */
    public Submit2State() {
	}
	/**
     * Constructor initializes the ending date from the GML2028 middleware
     * call. Finds the earlies start date of the selected languages.
     * Retrieves the GBL2921 ReturnDataRows for these dates and deselects
     * any languages that don't have any changes
     *
     * @param db
     * @param aProfile
     * @param aId
     * @param aType 
     * @throws java.rmi.RemoteException
     * @throws java.sql.SQLException
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     */
	public void init(Database db, Profile aProfile, String aType, int aId) 
		throws 
	java.rmi.RemoteException, 
	java.sql.SQLException,
	COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException 
	{
		EntityGroup eg;
		EntityItem ei;
		EANAttribute xllanguages;
		profile = aProfile;
		entityType = aType;
		entityID = aId;
		// Get the selected languages from the XLLANGUAGES attribute on the METAXLATEGRP instance
		eg = new EntityGroup(null, db, aProfile, entityType, "Edit");  //$NON-NLS-1$
		ei = new EntityItem(eg, aProfile, db, entityType, entityID);
		eg.put(ei.getKey(), ei);
		xllanguages = ei.getAttribute("XLLANGUAGES");  //$NON-NLS-1$
		if (xllanguages != null && xllanguages instanceof EANFlagAttribute) {
			EANFlagAttribute fa = (EANFlagAttribute) xllanguages;
			MetaFlag[] mfa = (MetaFlag[]) fa.get();
			Vector pLang = new Vector();
			for (int i = 0; i < mfa.length; i++) {
				if (mfa[i].isSelected()) {
					NLSItem nls = (NLSItem) mapSearch.get(mfa[i].getFlagCode());
					String strSA = (String) mapSearch.get(nls);
					if (strSA != null) {
						SearchActionItem sai = new SearchActionItem(null, db, aProfile, strSA);
						EntityList list = db.executeAction(aProfile, sai);
						if (list != null && list.getEntityGroup(entityType) != null && list.getEntityGroup(entityType).getEntityItemCount() > 0) {
							mfa[i].setSelected(false); // Deselect it since it is in process
						}
						else {
							// Search did not find any in process so it is ok for query
							pLang.add(nls); 
						}
					}
					else {
						// Don't have a search mapping for this
						mfa[i].setSelected(false); // Deselect it
					}
				}
			}
			fa.put(mfa);
			// commit changes here if any 
			if (ei.hasChanges()) {
                ei.commit(db, null);
			}
			if (pLang.size() > 0) {
				NLSItem[] anls;
				MetaTranslationGroup mlg;
				int[] nChanges = new int[MAXHANDLEDCHANGES];
				Iterator i;
				// Run the query using the selected
				queryLanguages.addAll(pLang);
				anls = new NLSItem[pLang.size()];
				pLang.copyInto(anls);
				queryDate = aProfile.getNow();
				mlg = new MetaTranslationGroup(db, aProfile, anls);
				i = mlg.getMetaTranslationItem().values().iterator();
				while (i.hasNext()) {
					MetaTranslationItem mti = (MetaTranslationItem) i.next();
					Iterator iNLS = mti.getTargetNLSItems().values().iterator();
					while (iNLS.hasNext()) {
						NLSItem nls = (NLSItem) iNLS.next();
						nChanges[nls.getNLSID()]++;
					}
				}
				i = queryLanguages.iterator();
				while (i.hasNext()) {
					NLSItem nls = (NLSItem) i.next();
					languageChanges.put(nls, new Integer(nChanges[nls.getNLSID()]));
				}
				//System.out.println(mlg.dump(false));
				setList(mlg.getMetaTranslationItem().values());
				// Sort the rows by attribute code, flag type, flag value
				Collections.sort(getList(), new MetaTranslationItemComparator());
			}
		}

		// Count how many changes for each language
		stack.push(this);
	}	
	/**
	 * Implements the back, continue, index, and gotoPage actions.
	 * <pre>
	 * back     - go back to the Submit1State
	 * continue - if one or more languages have qurey results go to Submit3State  
	 * index    - go to the IndexState (cached)
	 * gotoPage - go to the page specified by <b>parameter</b>.
	 *            if not a valid number (e.g. letter) or page do nothing.
	 * </pre>
	 * @see State#doAction(Map)
	 */
	public void doAction(Map params) 
	throws
		COM.ibm.eannounce.objects.WorkflowException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		java.sql.SQLException
	{
		Object action = params.get("action");  //$NON-NLS-1$
		if ("submit".equals(action)) {  //$NON-NLS-1$
			Database dbCurrent = (Database) params.get("dbCurrent");  //$NON-NLS-1$
			EntityGroup eg = new EntityGroup(null, dbCurrent, profile, entityType, "Edit");  //$NON-NLS-1$
			EntityItem[] aItems = new EntityItem[1];
			WorkflowActionItem wfai;
			aItems[0] = new EntityItem(eg, profile, dbCurrent, entityType, entityID);
			wfai = new WorkflowActionItem(null, dbCurrent, profile, "WFMETAXLATEGRP");  //$NON-NLS-1$ 
			wfai.setEntityItems(aItems);
			dbCurrent.executeAction(profile, wfai);
			stack.clear();
		}
		else if ("index".equals(action)) {  //$NON-NLS-1$
			if (index == null) {
                index = new IndexState();
                index.init(this);
			}
			stack.push(index);
		}
		else if ("gotoPage".equals(params.get("action"))) {  //$NON-NLS-1$  //$NON-NLS-2$
			gotoPage(params);
		}
	}
	/**
	 * @see State#getStack()
	 */
	public Stack getStack() { return stack; }
	/**
	 * @see State#getStateID()
	 */
	public Integer getStateID() { return STATE_SUBMIT2; } 
	/**
	 * Map accessor of NLSItem -> Integer
	 * @return Set of languages (NLSItems) that have changes.
	 */
	public Map getLanguageChanges() {
		return Collections.unmodifiableMap(languageChanges);
	}
	/**
	 * ISO Timestamp of when query was done
	 * @return String Timestamp.
	 */
	public String getQueryDate() {
		return queryDate;
	}
	/**
	 * List of NLSItems used in the query
	 * @return List 
	 */
	public List getQueryLanguages() { return Collections.unmodifiableList(queryLanguages); }
	/**
     * Accessor for Profile
     *
     * @return Profile
     */
	public Profile getProfile() {
		return profile;
	}
	/**
     * Accessor for Profile
     *
     * @param aProfile 
     */
	protected void setProfile(Profile aProfile) {
		profile = aProfile;
	}
}

