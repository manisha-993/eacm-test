// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.*;

import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * used in veedit action - one per row
 *
 */
// $Log: VEEditItem.java,v $
// Revision 1.10  2012/09/05 14:32:50  wendy
// Added isAllNew() and rollback()
//
// Revision 1.9  2010/10/18 11:53:15  wendy
// make dereference public
//
// Revision 1.8  2010/01/05 19:21:42  wendy
// prevent null ptr if meta is bad
//
// Revision 1.7  2009/05/15 17:02:11  wendy
// Add toString() to be used in things like sort
//
// Revision 1.6  2009/05/11 15:32:58  wendy
// Support dereference for memory release
//
// Revision 1.5  2009/04/15 20:15:39  wendy
// Maintain a list for lookup to prevent returning the wrong EANObject
//
// Revision 1.4  2009/03/05 21:47:43  wendy
// Override getEntityItemTable() to prevent checks when root is a relator
//
// Revision 1.3  2008/12/08 22:53:04  wendy
// Find correct entity for getting lockgroup
//
// Revision 1.2  2008/09/08 17:46:01  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2008/04/29 19:27:56  wendy
// MN35270066 VEEdit rewrite
//
public class VEEditItem extends EntityItem {
    final static long serialVersionUID = 1L;

    private Vector entityVct = new Vector(1);  // all entities that will be in the row besides the root
    private String m_sKey = "";  // each item must have unique id in the entitygroup

    public void dereference(){
    	super.dereference();
    	m_sKey = null;
    	if (entityVct != null){
    		// items will be derefd by their group
    		entityVct.clear();
    		entityVct = null;
    	}
    }
    protected VEEditItem(VEEditItem _ei, int newid) throws MiddlewareRequestException {
		super(_ei);
		m_sKey = "VE"+newid;
    }

    /***************************************************
     *  Used when settings the parentEntityItems in the EntityList object
     *
     * @param  _f  EANFoundation
     * @param  _prof  Profile
     * @param  _ei  EntityItem - selected in the UI
     */
	protected VEEditItem(EANFoundation _f, Profile _prof, EntityItem _ei) throws MiddlewareRequestException
	{
        super(_f, _prof, _ei);
    }

    /***************************************************
     *  Unique key
     *
     * @return String
     */
	public String getKey() {
		return m_sKey + super.getKey();
	}

	/* (non-Javadoc)
	 * handle all entityitems in this VEEdit
	 * @see COM.ibm.eannounce.objects.EntityItem#rollback(java.lang.String)
	 */
	public void rollback(String str) {
        KeyPath kPath = new KeyPath();
        kPath.parse(str);
        String strEntityType = kPath.getEntityType();
        if (strEntityType.equals(getEntityType())) {
            super.rollback(str);
            return;
        }

		EntityItem[] items = getEditableItems();
		if (items != null) {
			for (int x=0;x<items.length;++x) {
				if(items[x].getEntityType().equals(strEntityType)){
					items[x].rollback(str);
		            return;
				}
			}
		}	
	}
    /***************************************************
     *  Add entity item to display in the row
     *
     * @param  item  EntityItem
     */
	public void addEntity(EntityItem item){
		entityVct.add(item);
	}

    /***************************************************
     * Find data for this key
     *
     * @param  _str  String key used to find data
     */
     public EANFoundation getEANObject(String _str) {
        KeyPath kPath = new KeyPath();
        kPath.parse(_str);
        String strEntityType = kPath.getEntityType();
        String strAttributeCode = kPath.getAttributeCode();

        if (strEntityType.equals(getEntityType())){
			return getThisEANObject(strAttributeCode);
		}else{
			// find it in our set of items
			for (int i=0; i<entityVct.size(); i++){
				EntityItem item = (EntityItem)entityVct.elementAt(i);
				if (strEntityType.equals(item.getEntityType())){
					return item.getThisEANObject(strAttributeCode);
				}
			}

			// if entitytype does not exist yet but is created or edited by this veedit action,
			// create it now
			EANFoundation obj = null;
			try{
				obj = createEntity(strEntityType, strAttributeCode);
				if (obj != null){
					return obj;
				}
			}catch(MiddlewareRequestException mre){
				mre.printStackTrace();
			}

			// should not get here!!!
			obj = super.getEANObject(_str);
			return obj;
		}
	}

    /***************************************************
     * get items that can be edited
     *
     * @return  EntityItem[]
     */
	public EntityItem[] getEditableItems(){
		EntityItem[] eai = null;
		EntityList thelist = getEntityGroup().getEntityList();
		ExtractActionItem ean = (ExtractActionItem)thelist.getParentActionItem();
		Vector tmp = new Vector(1);
		for (int i=0; i<entityVct.size(); i++){
			EntityItem item = (EntityItem)entityVct.elementAt(i);
			if (ean.isEditable(item.getEntityType()) || ean.isCreatable(item.getEntityType())){
				tmp.add(item);
			}
		}
		if (tmp.size()>0){
			eai = new EntityItem[tmp.size()];
			tmp.copyInto(eai);
		}
		return eai;
	}

    /***************************************************
     * Indicate that something in this item will be editable
     *
     *@return  boolean
     */
    public boolean canEdit() {
		EntityList thelist = getEntityGroup().getEntityList();
		ExtractActionItem ean = (ExtractActionItem)thelist.getParentActionItem();
		EntityItem eai[] = getEditableItems();
		if (eai != null){
			for (int i=0; i<eai.length; i++){
				EntityItem item = eai[i];
				if (item.getEntityID()<0 && !ean.isCreatable(item.getEntityType())){
					//System.err.println("VEEditItem.canEdit "+item.getKey()+" is not creatable!");
					return false;
				}
			}
		}
		return true;
    }

    /***************************************************
     * get items in the VEpath
     *
     * @return  EntityItem[]
     */
	public EntityItem[] getVEPathItems(){
		EntityItem[] eai = null;
		EntityList thelist = getEntityGroup().getEntityList();
		ExtractActionItem ean = (ExtractActionItem)thelist.getParentActionItem();
		StringTokenizer st = new StringTokenizer(ean.getVEPath(),":"); // D:MODELBOM:BOM
		//String pathdir =
			st.nextToken();
		String pathreltype = st.nextToken();
		String pathtype = st.nextToken();

		Vector tmp = new Vector(1);
		for (int i=0; i<entityVct.size(); i++){
			EntityItem item = (EntityItem)entityVct.elementAt(i);
			if (pathreltype.equals(item.getEntityType())||pathtype.equals(item.getEntityType())){
				tmp.add(item);
			}
		}
		if (tmp.size()>0){
			eai = new EntityItem[tmp.size()];
			tmp.copyInto(eai);
		}
		return eai;
	}

    /***************************************************
     * get items used for display/edit
     * it is the meta defined with DISPLAY
     * SG  Action/Attribute    EDITPRODBOMEXT  DISPLAY  PRODSTRUCTBOM   Full|PRODSTRUCT,D:PRODSTRUCTBOM
	 * SG  Action/Attribute    EDITPRODBOMEXT  DISPLAY  BOM         	NavOnly|BOM
     *
     * @return  EntityItem[]
     */
	public EntityItem[] getDisplayableItems(){
		EntityItem[] eai = null;
		EntityList thelist = getEntityGroup().getEntityList();
		ExtractActionItem ean = (ExtractActionItem)thelist.getParentActionItem();
		String[] disp = ean.getDisplayable();
		if (disp != null){
			Vector tmp = new Vector(1);
			for (int d=0; d<disp.length; d++){
				for (int i=0; i<entityVct.size(); i++){
					EntityItem item = (EntityItem)entityVct.elementAt(i);
					if (disp[d].equals(item.getEntityType())){
						tmp.add(item);
						break;
					}
				}
			}
			if (tmp.size()>0){
				eai = new EntityItem[tmp.size()];
				tmp.copyInto(eai);
			}
		}
		return eai;
	}

    /***************************************************
     * were any changes made in this set of items
     *
     * @return boolean
     */
    public boolean hasChanges() {
        if (pendingChanges()) {
            return true;
        }

		EntityItem[] related = getEditableItems();
		if (related != null) {
			for (int i = 0; i < related.length; ++i) {
				if (related[i].pendingChanges()) {
					return true;
				}
			}
		}

        return false;
    }

    /**
     *
     */
    public boolean put(String _str, Object _o) throws EANBusinessRuleException {
        KeyPath kPath = new KeyPath();
        kPath.parse(_str);
        String strEntityType = kPath.getEntityType();
        if (strEntityType.equals(getEntityType())) {
            return super.put(_str, _o);
        }

		EntityItem[] items = getEditableItems();
		if (items != null) {
			for (int x=0;x<items.length;++x) {
				if(items[x].getEntityType().equals(strEntityType)){
					return items[x].put(_str, _o);
				}
			}
		}

		return false;
    }

    /***************************************************
     * is anything editable in this row - used in horizontal edit
     *
     * @return boolean
     */
    public boolean isEntityGroupEditable(String _s) {
        if (_s == null) {
            return isEntityGroupEditable();
        }

        KeyPath kPath = new KeyPath();
        kPath.parse(_s);
        String strEntityType = kPath.getEntityType();
       // String strAttributeCode = kPath.getAttributeCode();

		EntityList thelist = getEntityGroup().getEntityList();
		ExtractActionItem ean = (ExtractActionItem)thelist.getParentActionItem();

		EntityItem eai[] = getEditableItems();
		if (eai != null){
			for (int i=0; i<eai.length; i++){
				EntityItem item = eai[i];
				if (item.getEntityID()<0){
					return ean.isCreatable(item.getEntityType());
				}else if (ean.isEditable(strEntityType)){
					return true;
				}
			}
		}

        return false;
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
        }

		// get editable and look for the entity there
		EntityItem[] related = getEditableItems();
		if (related != null) {
			for (int i = 0; i < related.length; ++i) {
				if (related[i].getEntityType().equals(strEntityType)) {
					 return related[i].isLocked(_rdi, _db, _ll, _prof, _lockOwnerEI, _iLockType, _strTime, _bCreateLock);
				}
			}
		}
		return super.isLocked(_str, _rdi, _db, _ll,_prof,_lockOwnerEI, _iLockType, _strTime, _bCreateLock);
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
		boolean isdelete = false;

		EntityItem[] related = getVEPathItems();
        if (related != null) {
			isdelete = isVEEditDelete(related);
			if (!isdelete){// veedit was not a delete, so find changes
				int ii = related.length;
				for (int i = 0; i < ii; ++i) {
					ReturnEntityKey rekVe = related[i].generateUpdateEntity(true);
					if (rekVe != null) {
						eanlst.put(related[i]);
						vctReturnEntityKeys.addElement(rekVe);
						if (related[i].isNew()) {
							EntityItem eiRelator = (EntityItem) related[i].getUpLink(0);
							if (eiRelator!= null && eiRelator.isNew()) {
								EntityGroup egRelator = eiRelator.getEntityGroup();
								if (egRelator.isRelator()) {
									EntityItem eiParent = (EntityItem) eiRelator.getUpLink(0);
									ReturnRelatorKey rKey = new ReturnRelatorKey(eiRelator.getEntityType(), eiRelator.getEntityID(),
										eiParent.getEntityType(), eiParent.getEntityID(), related[i].getEntityType(),
										related[i].getEntityID(), true);
									vctReturnEntityKeys.addElement(rKey);
								}
							}
						}
					}
				}
			}else{ // hang onto this one and do all deletes together
				vctDeleteParentItems.addElement(this);
			}
		}
		return isdelete;
	}


    /**
    * VEEdit_delete MN30841458
    * check to see if this is a delete for veedit action
    *@param related EntityItem[] relator and item that need to be deleted or committed
    *@return boolean true if this is a delete
    */
	private boolean isVEEditDelete(EntityItem[] related) throws MiddlewareException
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
                    D.ebug(D.EBUG_ERR,"VEEditItem.isVEEditDelete delete action was not in meta for "+ean+", can't delete items related to "+getKey());
                    throw new MiddlewareException("Delete action was not in meta for "+ean+", can't delete items related to "+super.getKey());
				}
			}
		}

		return isdelete;
	}

    /***************************************************
     * look for lock in this item or its set of items
     *
     *@param  _str          String
     *@param  _lockOwnerEI  EntityItem
     *@param  _prof         Profile
     *@return               boolean
     */
    public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
        KeyPath kPath = new KeyPath();
        kPath.parse(_str);
        String strEntityType = kPath.getEntityType();

		if (strEntityType.equals(getEntityType())) {
            return hasLock(_lockOwnerEI, _prof);
        }

		for (int i=0; i<entityVct.size(); i++){
			EntityItem item = (EntityItem)entityVct.elementAt(i);
			if (strEntityType.equals(item.getEntityType())){
				return item.hasLock(_lockOwnerEI, _prof);
			}
		}

        return super.hasLock(_str,_lockOwnerEI, _prof);
    }

    /***************************************************
     *  checks to see if any of these editable items are new
     *
     *@return  boolean
     */
    public boolean isNew() {
		EntityItem[] itemArray = getEditableItems();
		if (itemArray!=null){
			for (int i=0;i<itemArray.length;++i) {
				EntityItem ei = itemArray[i];
				if (ei.isNew()) {
					return true;
				}
			}
		}
		return false;
    }

    /***************************************************
     *  checks to see if all items are new
     *
     *@return  boolean
     */
    public boolean isAllNew() {
    	if(getEntityID()>0){
    		return false;
    	}
    	for (int i=0; i<entityVct.size(); i++){
    		EntityItem item = (EntityItem)entityVct.elementAt(i);
    		if (item.getEntityID()>0) {
    			return false;
    		}
    	}
		return true;
    }
    
    /***************************************************
     * create the missing item and relator for this veedit
     *
     * @return EANFoundation
     */
	private EANFoundation createEntity(String strEntityType, String strAttributeCode)
	throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
        EANAttribute att = null;
		EntityList thelist = getEntityGroup().getEntityList();
		ExtractActionItem ean = (ExtractActionItem)thelist.getParentActionItem();
		StringTokenizer st = new StringTokenizer(ean.getVEPath(),":"); // D:MODELBOM:BOM
		String pathdir = st.nextToken();
		String pathreltype = st.nextToken();
		String pathtype = st.nextToken();

		if (!strEntityType.equals(pathreltype) && !strEntityType.equals(pathtype))	{
			return att;
		}
		EntityGroup relGrp = thelist.getEntityGroup(pathreltype);
		EntityGroup childGrp = thelist.getEntityGroup(pathtype);

		//need to generate the entity and the path to it.
		EntityItem eiRelator = new EntityItem(relGrp, null, pathreltype, ean.getID());
		relGrp.putEntityItem(eiRelator);

		EntityItem eiChild = new EntityItem(childGrp, null, pathtype, ean.getID());
		childGrp.putEntityItem(eiChild);
		if (pathdir.equalsIgnoreCase("D")){
			this.putDownLink(eiRelator);
			eiRelator.putDownLink(eiChild);
		}else{
			this.putUpLink(eiRelator);
			eiRelator.putUpLink(eiChild);
		}

		addEntity(eiRelator);
		addEntity(eiChild);

		EntityItem item = null;
		if (strEntityType.equals(pathtype)){
			item = eiChild;
		}else{
			item = eiRelator;
		}
		att = item.getAttribute(strAttributeCode);
		if (att == null) {
			try {
				att =  (EANAttribute)item.genAttribute(strAttributeCode);
			}
			catch (Exception x) {
				x.printStackTrace();
			}
		}
		return att;
    }

    /***************************************************
     * look at the set of items in this veedit for the value
     *
     * @return Object
     */
    public Object get(String _str, boolean _b) {
        KeyPath kPath = new KeyPath();
        kPath.parse(_str);
        String strEntityType = kPath.getEntityType();
        String strAttributeCode = kPath.getAttributeCode();
		if (!strEntityType.equals(getEntityType())) {
			//look here first
			for (int i=0; i<entityVct.size(); i++){
				EntityItem item = (EntityItem)entityVct.elementAt(i);
				if (strEntityType.equals(item.getEntityType())){
					//EANAttribute att = getAttributeFromEntityItem(item, strAttributeCode);
					EANAttribute att = item.getAttribute(strAttributeCode);
					if (att == null) {
						return "";
					} else {
						return att.get(EANAttribute.VALUE, _b);
					}
				}
			}
		}else{
			EANAttribute att = getAttribute(strAttributeCode);
            if (att == null) {
                return "";
            } else {
                return att.get(EANAttribute.VALUE, _b);
            }
		}
		
		return super.get(_str,_b);
	}

    /***************************************************
     * look at the set of items in this veedit for the value
     *
     * @return Object
     */
    protected void copyVE() throws MiddlewareRequestException
    {
		EntityList thelist = getEntityGroup().getEntityList();
		ExtractActionItem ean = (ExtractActionItem)thelist.getParentActionItem();
		// copy the root entity
		VEEditItem vei2 = new VEEditItem(this, ean.getID());
		getEntityGroup().putEntityItem(vei2);
		vei2.setAttribute(this); // group isnt set until now

		// add displayables but not the items that can be edited
		StringTokenizer st = new StringTokenizer(ean.getVEPath(),":"); // D:MODELBOM:BOM
		//String pathdir =
			st.nextToken();
		String pathreltype = st.nextToken();
		String pathtype = st.nextToken();

		for (int i=0; i<entityVct.size(); i++){
			EntityItem item = (EntityItem)entityVct.elementAt(i);
			if (pathreltype.equals(item.getEntityType())||pathtype.equals(item.getEntityType())){
				continue;
			}
			vei2.addEntity(item);
		}
    }

    /***************************************************
     *  Gets the lockGroup for the EntityItem object
     *
     *@param  _str  String with entitytype:attributecode CATLGOR:CATAUDIENCE
     *@return       The lockGroup value
     */
    public LockGroup getLockGroup(String _str) {
        // Lets get the Meta Attribute based upon the string
        StringTokenizer st = new StringTokenizer(_str, ":");
        String strEntityType = st.nextToken();
        st.nextToken(); // this is the attributecode

        if (!strEntityType.equals(getEntityType())) {
			//look here first
			for (int i=0; i<entityVct.size(); i++){
				EntityItem item = (EntityItem)entityVct.elementAt(i);
				if (strEntityType.equals(item.getEntityType())){
					return item.getLockGroup(_str);
				}
			}
		}

		return super.getLockGroup(_str);
    }
    /**
     *  Gets the entityItemTable attribute of the EntityItem object
     *
     *@return    The entityItemTable value
     */
    public RowSelectableTable getEntityItemTable() {
        // We are in normal edit mode here
        return new RowSelectableTable(this, getLongDescription());
    }
    
    /****
     * only look at displayable items for things like sort
     * @see COM.ibm.eannounce.objects.EntityItem#toString()
     */
    public String toString() {
    	EntityItem[] dispEiArray = getDisplayableItems();
        StringBuffer strbResult = new StringBuffer();

        if (dispEiArray!=null){
        	for (int d=0; d<dispEiArray.length; d++){
        		EntityItem ei = dispEiArray[d];
        		if (strbResult.length()>0){
        			strbResult.append(", ");
        		}
        		strbResult.append(ei.toString());
        	}
        }else{
        	System.err.println("WARNING: VEEditItem "+getKey()+" does not have any displayable items!!");
        }
        return strbResult.toString();
    }

}

