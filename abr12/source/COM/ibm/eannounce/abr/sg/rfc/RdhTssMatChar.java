/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhTssMatCharEntity;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;

import com.google.gson.annotations.SerializedName;


public class RdhTssMatChar extends RdhBase {
	@SerializedName("Z_GEO")
	private String z_geo;
	@SerializedName("I_MATCHAR")
	private List<RdhTssMatCharEntity> entities;

	public RdhTssMatChar(SVCMOD svcmod) {	
		super(svcmod.getMACHTYPE() + svcmod.getMODEL(), "RDH_YMDM_MATCHAR"
				.toLowerCase(), null);
		
		List<PRODSTRUCT> prodStructs = svcmod.getPRODSTRUCTLIST();
		if (prodStructs!=null && !prodStructs.isEmpty())
		{
			for (PRODSTRUCT prodStruct : prodStructs)
			{
				RdhTssMatCharEntity entity = new RdhTssMatCharEntity();
				entity.setMaterial_number(svcmod.getMACHTYPE() + svcmod.getMODEL());
				entity.setFeature_id(prodStruct.getFEATURECODE());
				entity.setFeature_name(prodStruct.getMKTGNAME());
				entity.setManopt_flag(prodStruct.getMNATORYOPT());
				entity.setBukrs("");
				entity.setWithdrawdate(prodStruct.getWITHDRAWDATE().replaceAll("-", ""));
				entity.setWthdrweffctvdate(prodStruct.getWTHDRWEFFCTVDATE().replaceAll("-", ""));
				entity.setEffective_date(prodStruct.getEFFECTIVEDATE().replaceAll("-", ""));
				entity.setEnd_date(prodStruct.getENDDATE().replaceAll("-", ""));
				
				entities.add(entity);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.sdpi.cmd.interfaces.esw.rdh.RdhBase#setDefaultValues()
	 */
	@Override
	protected void setDefaultValues() {
		this.entities = new ArrayList<RdhTssMatCharEntity>();
		this.z_geo = "WW";
	}

	@Override
	protected boolean isReadyToExecute() {
		return true;
	}

}
