package com.ibm.rdh.chw.entity;

/**
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class AUOMaterial implements java.io.Serializable {
	public String material;
	public String percentage;

	public AUOMaterial(String material, String percentage) {
		this.setMaterial(material);
		this.setPercentage(percentage);
	}

	public void setPercentage(String value) {
		this.percentage = value;
	}

	public String getPercentage() {
		return this.percentage;
	}

	public void setMaterial(String value) {
		this.material = value;
	}

	public String getMaterial() {
		return this.material;
	}

	public boolean equals(AUOMaterial aUOMaterialTmp) {
		boolean equals = false;
		if ((this.material == aUOMaterialTmp.material)
				&& (this.percentage == aUOMaterialTmp.percentage))
			equals = true;
		return equals;

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AUOMaterial [");
		sb.append("material: " + getMaterial());
		sb.append(", Percentage: " + getPercentage());
		sb.append("]");
		return sb.toString();
	}
}
