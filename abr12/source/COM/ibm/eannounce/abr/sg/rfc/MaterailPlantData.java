package COM.ibm.eannounce.abr.sg.rfc;

public class MaterailPlantData {
	String plant = "";
	String profitCenter="";
	
	public MaterailPlantData() {
		super();
	}
	
	public String getPlant() {
		return plant;
	}
	
	public String getProfitCenter() {
		return profitCenter;
	}
	
	public void setPlant(String newPlant) {
		plant = newPlant;
	}
	public void setProfitCenter(String newProfitCenter) {
		profitCenter = newProfitCenter;
	}
	
	public MaterailPlantData (String strPlant,String strProfitCenter){
		plant=strPlant;
		profitCenter = strProfitCenter;
	}	
}
