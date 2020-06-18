//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LocalRuleGroup.java,v $
// Revision 1.7  2005/03/04 18:26:23  dave
// Jtest actions for the day
//
// Revision 1.6  2005/02/22 19:18:32  gregg
// messageString on Local Rule
//
// Revision 1.5  2005/02/18 23:07:42  gregg
// somemore fixin
//
// Revision 1.4  2005/02/18 23:00:40  gregg
// more fixesa
//
// Revision 1.3  2005/02/18 22:15:11  gregg
// fix
//
// Revision 1.2  2005/02/18 22:09:56  gregg
// fixes
//
// Revision 1.1  2005/02/18 22:03:20  gregg
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * LocalRuleGroup
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LocalRuleGroup extends EANMetaEntity {

    final static long serialVersionUID = 1L;

    /**
     * LocalRuleGroup
     *
     * @param _emf
     * @param _prof
     * @param _strGroupKey
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public LocalRuleGroup(EANMetaFoundation _emf, Profile _prof, String _strGroupKey) throws MiddlewareRequestException {
        super(_emf, _prof, _strGroupKey);
    }

    /**
     * putItem
     *
     * @param _lri
     *  @author David Bigelow
     */
    protected void putItem(LocalRuleItem _lri) {
        putMeta(_lri);
    }

    /**
     * getItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    protected LocalRuleItem getItem(int _i) {
        return (LocalRuleItem) getMeta(_i);
    }

    /**
     * getItem
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    protected LocalRuleItem getItem(String _s) {
        return (LocalRuleItem) getMeta(_s);
    }

    /**
     * getItemCount
     *
     * @return
     *  @author David Bigelow
     */
    protected int getItemCount() {
        return getMetaCount();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _b) {
        return getKey();
    }

    /**
     * getEntityGroup
     *
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup() {
        return (EntityGroup) getParent();
    }

}

/*
meta looks like:

insert into opicm.metalinkattr values('SG13',  'Entity/Group',     'MODEL',      'MODELLOCALRULEGRP1',   'LocalRule',   'Y',                                                                                                                                                                                                                                                              '2005-02-18-00.00.00.000000', '9999-12-31-00.00.00.000000', '2005-02-18-00.00.00.000000', '9999-12-31-00.00.00.000000',        9998,          -9);
insert into opicm.metalinkattr values('SG13',  'Group/Attribute',  'MODELLOCALRULEGRP1', 'Evaluation',   'Item1',       ':ANNDATE < :WITHDRAWDATE',                                                                                                                                                                                                                                        '2005-02-18-00.00.00.000000', '9999-12-31-00.00.00.000000', '2005-02-18-00.00.00.000000', '9999-12-31-00.00.00.000000',        9998,           -9);
insert into opicm.metalinkattr values('SG13',  'Group/Attribute',  'MODELLOCALRULEGRP1', 'Message',      'Item1',       ':ANNDATE must be less than :WITHDRAWDATE',                                                                                                                                                                                                                                        '2005-02-18-00.00.00.000000', '9999-12-31-00.00.00.000000', '2005-02-18-00.00.00.000000', '9999-12-31-00.00.00.000000',        9998,           -9);
insert into opicm.metalinkattr values('SG13',  'Group/Attribute',  'MODELLOCALRULEGRP1', 'Type',         'Item1',       'Full',                                                                                                                                                                                                                                        '2005-02-18-00.00.00.000000', '9999-12-31-00.00.00.000000', '2005-02-18-00.00.00.000000', '9999-12-31-00.00.00.000000',        9998,           -9);

*/
