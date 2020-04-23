package COM.ibm.eannounce.abr.util;

import java.sql.SQLException;
import java.util.Vector;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.SingleFlag;

public class XLABRQueueUtil {

	protected static final String ABR_QUEUED = "0020";

	protected static final String XL_POST_PROCESS_ATTRIBUTE = "XLPOSTPROCABR";

	public static void queueTranslationPostProcessABR(Database m_db,
			Profile m_prof, EntityItem ei, ControlBlock m_cbOn) throws MiddlewareBusinessRuleException, SQLException, MiddlewareException {
		//if (EnterpriseUtil.isLastEnterpriseVersion(m_prof)) {
			queueABR(m_db, m_prof, ei, m_cbOn, XL_POST_PROCESS_ATTRIBUTE);
		//}
	}

	public static void queueABR(Database db, Profile profile, EntityItem ei,
			ControlBlock controlBlock, String attributeCode) throws MiddlewareBusinessRuleException, SQLException, MiddlewareException {
		if (controlBlock == null) {
			throw new IllegalArgumentException(
					"Control Block was not initialized by ABR. Call setControlBlock();");
		}

		ReturnEntityKey rek = new ReturnEntityKey(ei.getEntityType(), ei
				.getEntityID(), true);

		SingleFlag sf = new SingleFlag(profile.getEnterprise(), rek
				.getEntityType(), rek.getEntityID(), attributeCode, ABR_QUEUED,
				1, controlBlock);

		Vector vctAtts = new Vector();
		Vector vctReturnsEntityKeys = new Vector();
		if (sf != null) {
			vctAtts.addElement(sf);
			rek.m_vctAttributes = vctAtts;
			vctReturnsEntityKeys.addElement(rek);

			db.update(profile, vctReturnsEntityKeys, false, false);
			db.commit();
		}
	}

}
