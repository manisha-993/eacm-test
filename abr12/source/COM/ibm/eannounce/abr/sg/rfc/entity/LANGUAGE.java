package COM.ibm.eannounce.abr.sg.rfc.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
public class LANGUAGE {
	@XmlElement(name = "NLSID")
	private String NLSID;
	@XmlElement(name = "INVNAME")
	private String INVNAME;
	@XmlElement(name = "MKTGNAME")
	private String MKTGNAME;
	public String getNLSID() {
		return NLSID;
	}
	public String getINVNAME() {
		return INVNAME;
	}
	public String getMKTGNAME() {
		return MKTGNAME;
	}

}