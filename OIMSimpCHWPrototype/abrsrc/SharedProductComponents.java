package COM.ibm.eannounce.abr.sg.rfc;

import java.io.Serializable;

public class SharedProductComponents implements Serializable {

	private boolean SharedProduct = false;
	private String SharedProductMaterialType = null;
	private boolean SharedProductIn0147 = false;

	public boolean getSharedProduct() {
		return SharedProduct;
	}

	public boolean getSharedProductIn0147() {
		return SharedProductIn0147;
	}

	public java.lang.String getSharedProductMaterialType() {
		return SharedProductMaterialType;
	}

	public void setSharedProduct(boolean newSharedProduct) {
		SharedProduct = newSharedProduct;
	}

	public void setSharedProductMaterailType(
			java.lang.String newSharedProductMaterialType) {
		SharedProductMaterialType = newSharedProductMaterialType;
	}

	public void setSharedProductIn0147(boolean newSharedProductIn0147) {
		SharedProductIn0147 = newSharedProductIn0147;
	}

}
