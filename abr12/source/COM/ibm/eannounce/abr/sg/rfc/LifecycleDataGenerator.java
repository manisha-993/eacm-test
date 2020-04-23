package COM.ibm.eannounce.abr.sg.rfc;

import com.ibm.rdh.chw.entity.TypeFeature;
import com.ibm.rdh.chw.entity.TypeFeatureUPGGeo;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelFeature;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;

public class LifecycleDataGenerator {
	protected String varCond;
	protected String material;
	protected String objectType;

	/**
	 * Constructor for LifecycleDataGenerator.
	 */
	public LifecycleDataGenerator(TypeModel tm) {
		super();
		this.setMaterial(tm);
		this.setVarCond(tm);

	}

//	public LifecycleDataGenerator(ServicePac svc) {
//		super();
//		this.setMaterial(svc);
//		this.setVarCond(svc);
//
//	}

	public LifecycleDataGenerator(TypeFeature tf) {
		super();
		this.setMaterial(tf);
		this.setVarCond(tf);
	}

	public LifecycleDataGenerator(TypeModelUPGGeo tmug) {
		super();
		this.setMaterial(tmug);
		this.setVarCond(tmug);
	}

	public LifecycleDataGenerator(TypeFeatureUPGGeo tfug) {
		super();
		this.setMaterial(tfug);
		this.setVarCond(tfug);
	}

	public LifecycleDataGenerator(TypeModelFeature tmf) {
		super();
		this.setMaterial(tmf);
		this.setVarCond(tmf);
	}
//
//	public LifecycleDataGenerator(TypeModelGeoWDFM tmgw) {
//		super();
//		this.setMaterial(tmgw);
//		this.setVarCond(tmgw);
//	}

	/*
	 * public LifecycleDataGenerator(ServicePacGeoWDFM svcw) { super();
	 * this.setMaterial(svcw); this.setVarCond(svcw); }
	 */
//	public LifecycleDataGenerator(ServicePacSalesOrgWDFM svcw) {
//		super();
//		this.setMaterial(svcw);
//		this.setVarCond(svcw);
//	}
//
//	public LifecycleDataGenerator(TypeFeatureGeoWDFM tfgw) {
//		super();
//		this.setMaterial(tfgw);
//		this.setVarCond(tfgw);
//	}
//
//	public LifecycleDataGenerator(TypeModelUPGGeoWDFM tmugw) {
//		super();
//		this.setMaterial(tmugw);
//		this.setVarCond(tmugw);
//	}
//
//	public LifecycleDataGenerator(TypeFeatureUPGGeoWDFM tfugw) {
//		super();
//		this.setMaterial(tfugw);
//		this.setVarCond(tfugw);
//	}
//
//	public LifecycleDataGenerator(TypeModelFeatureWDFM tmfw) {
//		super();
//		this.setMaterial(tmfw);
//		this.setVarCond(tmfw);
//	}

	/**
	 * Returns the material.
	 * 
	 * @return String
	 */
	public String getMaterial() {
		return material;
	}

	/**
	 * Returns the varCond.
	 * 
	 * @return String
	 */
	public String getVarCond() {
		return varCond;
	}

	/**
	 * Sets the material.
	 * 
	 * @param material
	 *            The material to set
	 */
	public void setMaterial(String material) {
		this.material = material;
	}

	public void setMaterial(TypeModel tm) {

		material = tm.getType() + "NEW";

	}

//	public void setMaterial(ServicePac svc) {
//
//		material = svc.getType() + svc.getModel();
//
//	}

	public void setMaterial(TypeFeature tf) {

		material = tf.getType() + "NEW";

	}

	public void setMaterial(TypeModelUPGGeo tmug) {

		if (tmug.isMTC()) {
			material = tmug.getType() + "MTC";
		} else {
			material = tmug.getType() + "UPG";
		}

	}

	public void setMaterial(TypeFeatureUPGGeo tfug) {

		if (tfug.isCrossType()) {
			material = tfug.getType() + "MTC";
		} else {
			material = tfug.getType() + "UPG";
		}

	}

	public void setMaterial(TypeModelFeature tmf) {

		material = tmf.getType() + "NEW";

	}
//
//	public void setMaterial(TypeModelGeoWDFM tmgw) {
//
//		material = tmgw.getType() + "NEW";
//
//	}
//
//	/*
//	 * public void setMaterial(ServicePacGeoWDFM svcw) {
//	 * 
//	 * material = svcw.getType()+svcw.getModel();
//	 * 
//	 * }
//	 */
//
//	public void setMaterial(ServicePacSalesOrgWDFM svcw) {
//
//		material = svcw.getType() + svcw.getModel();
//
//	}
//
//	public void setMaterial(TypeFeatureGeoWDFM tfgw) {
//
//		material = tfgw.getType() + "NEW";
//
//	}
//
//	public void setMaterial(TypeModelUPGGeoWDFM tmugw) {
//		if (tmugw.isMTC()) {
//			material = tmugw.getType() + "MTC";
//		} else {
//			material = tmugw.getType() + "UPG";
//		}
//
//	}
//
//	public void setMaterial(TypeFeatureUPGGeoWDFM tfugw) {
//
//		if (tfugw.isCrossType()) {
//			material = tfugw.getType() + "MTC";
//		} else {
//			material = tfugw.getType() + "UPG";
//		}
//
//	}
//
//	public void setMaterial(TypeModelFeatureWDFM tmfw) {
//
//		material = tmfw.getType() + "NEW";
//
//	}

	/**
	 * Sets the varCond.
	 * 
	 * @param varCond
	 *            The varCond to set
	 */
	public void setVarCond(String varCond) {
		this.varCond = varCond;
	}

	public void setVarCond(TypeModel tm) {

		varCond = "*MODEL: " + tm.getModel();

	}

//	public void setVarCond(ServicePac svc) {
//
//		varCond = "*MODEL: " + svc.getModel();
//
//	}

	public void setVarCond(TypeFeature tf) {

		if (tf.isRPQ()) {
			varCond = "RPQ: " + tf.getFeature();
		} else {
			varCond = "FEATURE: " + tf.getFeature();
		}

	}

	public void setVarCond(TypeModelUPGGeo tmug) {

		if (tmug.isMTC()) {
			varCond = "MTC: " + tmug.getFromType() + tmug.getFromModel() + "_"
					+ tmug.getType() + tmug.getModel();
		} else {

			varCond = "MOD CONV: " + tmug.getFromModel() + "_"
					+ tmug.getModel();

		}

	}

	public void setVarCond(TypeFeatureUPGGeo tfug) {

		if (tfug.isCrossType()) {
			varCond = "MTFC: " + tfug.getFromType() + tfug.getFromFeature()
					+ "_" + tfug.getType() + tfug.getFeature();
		} else {

			varCond = "FEAT CONV: " + tfug.getFromFeature() + "_"
					+ tfug.getFeature();

		}

	}

	public void setVarCond(TypeModelFeature tmf) {

		if (tmf.isRPQ()) {
			varCond = "MODEL RPQ: " + tmf.getModel() + "_" + tmf.getFeature();
		} else {
			varCond = "MODEL FEAT: " + tmf.getModel() + "_" + tmf.getFeature();
		}

	}
//
//	public void setVarCond(TypeModelGeoWDFM tmgw) {
//
//		varCond = "*MODEL: " + tmgw.getModel();
//	}
//
//	/*
//	 * public void setVarCond(ServicePacGeoWDFM svcw) {
//	 * 
//	 * varCond = "*MODEL: "+svcw.getModel();
//	 * 
//	 * }
//	 */
//
//	public void setVarCond(ServicePacSalesOrgWDFM svcw) { // GX1
//
//		varCond = "*MODEL: " + svcw.getModel();
//
//	}
//
//	public void setVarCond(TypeFeatureGeoWDFM tfgw) {
//
//		if (tfgw.isRpq()) {
//			varCond = "RPQ: " + tfgw.getFeature();
//		} else {
//			varCond = "FEATURE: " + tfgw.getFeature();
//		}
//
//	}
//
//	public void setVarCond(TypeModelUPGGeoWDFM tmugw) {
//
//		if (tmugw.isMTC()) {
//			varCond = "MTC: " + tmugw.getFromType() + tmugw.getFromModel()
//					+ "_" + tmugw.getType() + tmugw.getModel();
//		} else {
//
//			varCond = "MOD CONV: " + tmugw.getFromModel() + "_"
//					+ tmugw.getModel();
//		}
//
//	}
//
//	public void setVarCond(TypeFeatureUPGGeoWDFM tfugw) {
//
//		if (tfugw.isCrossType()) {
//			varCond = "MTFC: " + tfugw.getFromType() + tfugw.getFromFeature()
//					+ "_" + tfugw.getType() + tfugw.getFeature();
//		} else {
//			varCond = "FEAT CONV: " + tfugw.getFromFeature() + "_"
//					+ tfugw.getFeature();
//		}
//
//	}
//
//	public void setVarCond(TypeModelFeatureWDFM tmfw) {
//		/*
//		 * varCond = "MODEL FEAT: "+ tmfw.getModel() + "_" + tmfw.getFeature();
//		 */
//		if (tmfw.isRPQ()) {
//			varCond = "MODEL RPQ: " + tmfw.getModel() + "_" + tmfw.getFeature();
//		} else {
//			varCond = "MODEL FEAT: " + tmfw.getModel() + "_"
//					+ tmfw.getFeature();
//		}
//	}
}
