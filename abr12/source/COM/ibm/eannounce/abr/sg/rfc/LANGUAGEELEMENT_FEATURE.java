package COM.ibm.eannounce.abr.sg.rfc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
public class LANGUAGEELEMENT_FEATURE {
	@XmlElement(name = "NLSID")
	private String NLSID;
	@XmlElement(name = "MKTGDESC")
	private String MKTGDESC;
	@XmlElement(name = "MKTGNAME")
	private String MKTGNAME;
	@XmlElement(name = "INVNAME")
	private String INVNAME;
	@XmlElement(name = "BHINVNAME")
	private String BHINVNAME; 
	
	public String getNLSID() {
		return NLSID;
	}
	public String getMKTGDESC() {
		return MKTGDESC;
	}
	public String getMKTGNAME() {
		return MKTGNAME;
	}
	public String getINVNAME() {
		return INVNAME;
	}
	public String getBHINVNAME() {
		return BHINVNAME;
	}
}