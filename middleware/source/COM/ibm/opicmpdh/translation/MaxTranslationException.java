package COM.ibm.opicmpdh.translation;

import java.util.ArrayList;
import java.util.List;

public class MaxTranslationException extends Throwable {

	private static final long serialVersionUID = 1L;
	
	private List errorList = new ArrayList();
	
	public void addError(int entityId, String entityType,
			String entityDisplayName, String attributeCode, int nlsid,
			String originalValue, String truncatedValue) {
		ReportData reportData = new ReportData(entityId, entityType, entityDisplayName,
				attributeCode, nlsid, originalValue, truncatedValue);
		errorList.add(reportData);
	}
	
	public boolean hasErrors() {
		return !errorList.isEmpty();
	}
	
	public String toHtml() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < errorList.size(); i++) {
			ReportData reportData = (ReportData) errorList.get(i);
			sb.append("<h3><font color=red>Max Length Error(s):</font></h3>");
			sb.append(reportData.toHtml());
			sb.append("<br><br>");
		}
		return sb.toString();
	}
	

	private class ReportData {

		private int entityId;

		private String entityType;

		private String entityDisplayName;

		private String attributeCode;

		private int nlsid;

		private String originalValue;

		private String truncatedValue;

		public ReportData(int entityId, String entityType,
				String entityDisplayName, String attributeCode, int nlsid,
				String originalValue, String truncatedValue) {
			this.entityId = entityId;
			this.entityType = entityType;
			this.entityDisplayName = entityDisplayName;
			this.attributeCode = attributeCode;
			this.nlsid = nlsid;
			this.originalValue = originalValue;
			this.truncatedValue = truncatedValue;
		}

		public String getAttributeCode() {
			return attributeCode;
		}

		public String getEntityDisplayName() {
			return entityDisplayName;
		}

		public int getEntityId() {
			return entityId;
		}

		public String getEntityType() {
			return entityType;
		}

		public int getNlsid() {
			return nlsid;
		}

		public String getOriginalValue() {
			return originalValue;
		}

		public String getTruncatedValue() {
			return truncatedValue;
		}

		public String toHtml() {
			StringBuffer sb = new StringBuffer();
			sb.append("Entity Type: " + entityType + "<br>");
			sb.append("Entity ID: " + entityId + "<br>");
			sb.append("Entity Description: " + entityDisplayName + "<br>");
			sb.append("Attribute Code: " + attributeCode + "<br>");
			sb.append("NLSID: " + nlsid + "<br>");
			sb.append("Original Value: " + originalValue + "<br>");
			sb.append("Truncated Value: " + truncatedValue + "<br>");
			return sb.toString();
		}

	}
}
