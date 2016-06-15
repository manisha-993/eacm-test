package com.ibm.pprds.epimshw.revenue;

import com.ibm.pprds.epimshw.HWPIMSAbnormalException;

/**
 * @author laxmi
 *
 */
	public class TypeModelRev implements java.io.Serializable
	{
		public String revAnnDocNo;
		public String revType;
		public String revModel;
		public String allModels;
		public RevProfile revProfile;
		
	
		/*
		public TypeModelRev(String type, String model, String annDocNo)
			throws HWPIMSAbnormalException
		{
			retrieveData(type, model, annDocNo) ;
		}
		*/
		public TypeModelRev() {
			super();
		}
	/**
 	* Insert the method's description here.
 	* Creation date: (4/30/2001 4:22:50 PM)
 	* @param type java.lang.String
 	* @param model java.lang.String
 	*/
		public TypeModelRev(String typ, String modl, String AnnDocNo) 
			throws HWPIMSAbnormalException
		{	
				this.setRevType(typ);
				this.setRevModel(modl);
				this.setRevAnnDocNo(AnnDocNo);
		}

		public TypeModelRev(String typ, String modl, String AnnDocNo, String allModels) 
			throws HWPIMSAbnormalException
		{	
				this.setRevType(typ);
				this.setRevModel(modl);
				this.setRevAnnDocNo(AnnDocNo);
				this.setAllModels(allModels) ;
		}

		public void setRevProfile ( RevProfile profile )
		{
			this.revProfile = profile ;
		}

		public RevProfile getRevProfile()
		{
			return this.revProfile ;
		}
		public void setFromRevProfile ( RevProfile profile )
		{
			this.revProfile = profile ;
		}

		public RevProfile getFromRevProfile()
		{
			return this.revProfile ;
		}
		public void setToRevProfile ( RevProfile profile )
		{
			this.revProfile = profile ;
		}

		public RevProfile getToRevProfile()
		{
			return this.revProfile ;
		}
		public void setRevAnnDocNo ( String value )
		{
			this.revAnnDocNo = value ;
		}

		String getRevAnnDocNo()
		{
			return this.revAnnDocNo ;
		}
	
		public void setRevType ( String value )
		{
			this.revType = value ;
		}

		String getRevType()
		{
			return this.revType ;
		}

		public void setRevModel ( String value )
		{
			this.revModel = value ;
		}

		String getRevModel()
		{
			return this.revModel ;
		}

		public void setAllModels ( String value )
		{
			this.allModels = value ;
		}

		String getAllModels()
		{
			return this.allModels ;
		}

		public String toString()
		{
			StringBuffer sb = new StringBuffer();
			sb.append(super.toString());
			sb.append(" [");
			sb.append("revAnnDocNo: " + revAnnDocNo);
			sb.append(", revType; " + revType);
			sb.append(", revModel: " + revModel);
			sb.append(", RevProfile: " + revProfile);
			sb.append(", AllModels: " + allModels);
			sb.append("]");
			return sb.toString();
		}


}
