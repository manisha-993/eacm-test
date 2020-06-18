//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TestPDGII.java,v $
// Revision 1.18  2008/06/16 19:30:30  wendy
// MN35609290 fix revealed memory leaks, deref() needed
//
// Revision 1.17  2008/03/26 19:45:07  wendy
// Clean up RSA warnings
//
// Revision 1.16  2008/01/25 19:23:39  bala
// more debug
//
// Revision 1.15  2008/01/23 17:58:37  bala
// Add debug
//
// Revision 1.14  2007/12/05 17:46:40  bala
// Add comment lines and debug statements
//
// Revision 1.13  2006/03/03 00:17:44  joan
// fixes
//
// Revision 1.12  2006/02/28 00:47:20  joan
// work on pdg
//
// Revision 1.11  2006/02/26 01:49:58  joan
// fixes
//
// Revision 1.10  2006/02/20 22:35:34  joan
// clean up system.out
//
// Revision 1.9  2005/04/15 15:21:30  joan
// fixes
//
// Revision 1.8  2004/10/07 20:42:38  joan
// fixes
//
// Revision 1.7  2004/10/05 16:05:02  joan
// fixes
//
// Revision 1.6  2004/09/27 23:26:11  joan
// fixes
//
// Revision 1.5  2004/09/02 23:58:55  joan
// fixes
//
// Revision 1.4  2004/08/31 20:25:00  joan
// fixes
//
// Revision 1.3  2004/08/26 20:46:16  joan
// fixes
//
// Revision 1.2  2004/08/25 19:44:51  joan
// add new pdgs
//
// Revision 1.1  2004/08/20 17:24:59  joan
// add new pdg
//


package COM.ibm.eannounce.objects;

import java.io.*;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import java.util.*;
import java.sql.*;

public class TestPDGII  implements Serializable {

	private class TreeInfo {
		private int m_iLevel;
		private String m_strDirection;
		private String m_strEntityType;
		private Vector m_vecAttributes = new Vector();
		private String m_strAction;
		private String m_strRelatorInfo;
		private String m_strRelatorType;
		private Vector m_vecRelAttrs = new Vector();


		TreeInfo(String _s) {
			//D.ebug(D.EBUG_SPEW,"TestPDGII$TreeInfo _s: " + _s);
			StringTokenizer st1 = new StringTokenizer(_s,"|");
			if (st1.hasMoreTokens()) {
				m_iLevel = Integer.parseInt(st1.nextToken());
				m_strDirection = st1.nextToken();
                D.ebug(D.EBUG_SPEW,"TestPDGII$TreeInfo: Level is:"+m_iLevel+": Direction is:"+m_strDirection);
				// get stuff for Entity
				String strEntity = st1.nextToken();
				int i1 = strEntity.indexOf("[");
				if (i1 > -1 ){
					m_strEntityType = strEntity.substring(0, i1);
					String strAttributes = "";
					int i2 = strEntity.indexOf("]");
					if (i2 > -1) {
						strAttributes = strEntity.substring(i1+1, i2);
					} else {
						strAttributes = strEntity.substring(i1+1);
					}

					StringTokenizer st2 = new StringTokenizer(strAttributes,";");
					while (st2.hasMoreTokens()) {
						m_vecAttributes.addElement(st2.nextToken());
					}
				} else {
					m_strEntityType = strEntity;
				}
                D.ebug(D.EBUG_SPEW,"TestPDGII$TreeInfo: EntityType is:"+m_strEntityType);
				m_strAction = st1.nextToken();
			  	m_strRelatorInfo = st1.nextToken();
				int i3 = m_strRelatorInfo.indexOf("[");
				if (i3 > -1 ){
					m_strRelatorType = m_strRelatorInfo.substring(0, i3);
					String strAttributes = "";
					int i4 = m_strRelatorInfo.indexOf("]");
					if (i4 > -1) {
						strAttributes = m_strRelatorInfo.substring(i3+1, i4);
					} else {
						strAttributes = m_strRelatorInfo.substring(i3+1);
					}

					StringTokenizer st2 = new StringTokenizer(strAttributes,";");
					while (st2.hasMoreTokens()) {
						m_vecRelAttrs.addElement(st2.nextToken());
					}
				} else {
					m_strRelatorType = m_strRelatorInfo;
				}
			}
		}

		int getLevel() {
			return m_iLevel;
		}

		String getDirection() {
			return m_strDirection;
		}

		String getEntityType() {
			return m_strEntityType;
		}

		Vector getAttributes() {
			return m_vecAttributes;
		}

		void setAttributes(Vector _v) {
			m_vecAttributes = _v;
		}

		String getRelatorType() {
			return m_strRelatorType;
		}

		Vector getRelAttrs() {
			return m_vecRelAttrs;
		}

		void setRelAttrs(Vector _v) {
			m_vecRelAttrs = _v;
		}

		String getAction() {
			return m_strAction;
		}

		String getRelatorInfo() {
			StringBuffer sb = new StringBuffer();
			sb.append(m_strRelatorType);
			int size = m_vecRelAttrs.size();;
			for (int i=0; i < size; i++) {
				String strValue = (String)m_vecRelAttrs.elementAt(i);
				//if (strValue.indexOf('\n') > 0) {
					//strValue.replace("\n", "\\n");
				//}
				sb.append((i != 0?";":"[") + strValue);//(String)m_vecRelAttrs.elementAt(i));
				if (i == size-1) {
					sb.append("]");
				}
			}

			return sb.toString();
		}

		String dump(boolean _b) {
			StringBuffer sb = new StringBuffer();
			sb.append(m_iLevel + "|" + m_strDirection + "|" + m_strEntityType + ":" );
			for (int i=0; i < m_vecAttributes.size(); i++) {
				sb.append((i != 0?";":"") + (String)m_vecAttributes.elementAt(i));
			}
			sb.append("|" + m_strAction);
			sb.append("|" + getRelatorInfo());
			return sb.toString();
		}
		void dereference(){
			m_strDirection = null;
			m_strEntityType= null;
			if (m_vecAttributes!= null){
				m_vecAttributes.clear();
				m_vecAttributes = null;
			}

			m_strAction= null;
			m_strRelatorInfo= null;
			m_strRelatorType= null;

			if (m_vecRelAttrs!= null){
				m_vecRelAttrs.clear();
				m_vecRelAttrs = null;
			}
		}
	}

	private class VETreeInfo {
		private EntityItem m_ei = null;
		private OPICMList m_childList = new OPICMList();
		private OPICMList m_parentList = new OPICMList();

		VETreeInfo(EntityItem _ei) {
			m_ei = _ei;
		}

		EntityItem getEntityItem() {
			return m_ei;
		}

		void putChildList(OPICMList _list) {
			m_childList = _list;
		}

		OPICMList getChildList() {
			return m_childList;
		}

		void putParentList(OPICMList _list) {
			m_parentList = _list;
		}

		OPICMList getParentList() {
			return m_parentList;
		}

		String dump(boolean _bBrief) {
			StringBuffer sb = new StringBuffer();
			sb.append("\n" + m_ei.getKey());
			sb.append("\nParents:");
			for (int i=0; i < m_parentList.size(); i++) {
				EntityItem ei = (EntityItem)m_parentList.getAt(i);
				sb.append("\ni" + i + ":" + ei.getKey());
			}

			sb.append("\nChildren:");
			for (int i=0; i < m_childList.size(); i++) {
				EntityItem ei = (EntityItem)m_childList.getAt(i);
				sb.append("\ni" + i + ":" + ei.getKey());
			}
			return sb.toString();
		}
		void dereference(){
			m_ei = null;
			if (m_childList != null){
				m_childList.clear();
				m_childList = null;
			}
			if (m_parentList != null){
				m_parentList.clear();
				m_parentList = null;
			}
		}
	}

    static final long serialVersionUID = 20011106L;

	private EntityItem m_eiPDG = null;
	private OPICMList m_infoList = null;
	private EntityList m_el = null;
	//private Hashtable m_osLevelHt = new Hashtable();
	private StringBuffer m_sbFound = new StringBuffer();
	private StringBuffer m_sbNotFound = new StringBuffer();
	private String m_strGeoAttr = null;
	private OPICMList m_parentTypeList = new OPICMList();
	private OPICMList m_childrenTypeList = new OPICMList();

	public void dereference(){
		m_eiPDG = null;
		m_infoList = null;

		if (m_el != null){
			m_el.dereference();
			m_el = null;
		}

		m_sbFound = null;
		m_sbNotFound = null;
		m_strGeoAttr = null;
		if (m_parentTypeList != null){
			m_parentTypeList.clear();
			m_parentTypeList = null;
		}
		if (m_childrenTypeList != null){
			m_childrenTypeList.clear();
			m_childrenTypeList = null;
		}
	}
	public TestPDGII(Database _db, Profile _prof, EntityItem _eiRoot, OPICMList _infoList, ExtractActionItem _xai, String _strFileName ) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, SQLException {
		String strTraceBase = " TestPDGII with extractaction";
		_db.debug(D.EBUG_SPEW, strTraceBase);
		m_infoList = _infoList;
		m_eiPDG = (EntityItem)m_infoList.get("PDG");
		m_strGeoAttr = (String)m_infoList.get("GEOIND");

		StringBuffer sb = readFile(_strFileName);
		buildParentChildrenTypeList(sb);
		// get VE info

		OPICMList treeList = null;
		if (_eiRoot != null) {
       		EntityItem[] eiParm = {_eiRoot};
			EntityList el = EntityList.getEntityList(_db, _prof, _xai, eiParm);
			m_el = el;
			treeList = buildVETree(el);
		}else{
			treeList = new OPICMList();
		}

		if (m_strGeoAttr != null) {
			sb = checkForGEO(sb);
		}
//			D.ebug(D.EBUG_SPEW,sb.toString());

		StringTokenizer st1 = new StringTokenizer(sb.toString(),"\n");
		Hashtable ht = new Hashtable();
		//EANList eiList = new EANList();
		//boolean bNext = false;
		while (st1.hasMoreTokens()) {
			String s = st1.nextToken();
			_db.debug(D.EBUG_SPEW,strTraceBase+": Processing :"+s);
			TreeInfo ti = new TreeInfo(s);
			adjustTreeInfoAttributes(ti);

			if (_eiRoot != null){ 	// this is for restart logic, check what tree nodes are missing
				Vector eipVec = (Vector)ht.get("" + (ti.getLevel()-1));
				if (ti.getLevel() > 0 && (eipVec == null || eipVec.size() <= 0)) {
					// its parent doesn't exist, therefore it doesn't exist, no need to look for it
					m_sbNotFound.append("\n |" + ti.dump(false));
					ht.remove(ti.getLevel() + "");
					continue;
				}
				boolean bFound = false;
				EntityItem eiParent = null;
				if (eipVec != null) {
					for (int i=0; i < eipVec.size(); i++) {
						eiParent = (EntityItem)eipVec.elementAt(i);
						Vector eiVec = findEntityItem(ti, treeList, eiParent);
						if (eiVec.size() > 0) {
							ht.put(ti.getLevel() + "", eiVec);
							for (int j =0; j < eiVec.size(); j++) {
								//EntityItem ei = (EntityItem)eiVec.elementAt(j);
								m_sbFound.append("\n" + (eiParent.getEntityType() + "-" + eiParent.getEntityID()));
								m_sbFound.append("|" + ti.dump(false));
								bFound = true;
								break;
							}
						}
					}
				} else {				// eiParent is null
					Vector eiVec = findEntityItem(ti, treeList, eiParent);
					if (eiVec.size() > 0) {
						ht.put(ti.getLevel() + "", eiVec);
						for (int j =0; j < eiVec.size(); j++) {
							m_sbFound.append("\n" + (eiParent != null?eiParent.getEntityType() + "-" + eiParent.getEntityID():" "));
							m_sbFound.append("|" + ti.dump(false));
						}
						bFound = true;
					}
				}

				if (!bFound) {
					ht.remove(ti.getLevel() + "");
					m_sbNotFound.append("\n" + (eiParent != null?eiParent.getEntityType() + "-" + eiParent.getEntityID():" "));
					m_sbNotFound.append("|" + ti.dump(false));
				}
			} else {		// start to run the PDG, no entities created yet, need all tree nodes.
				m_sbNotFound.append("\n |" + ti.dump(false));
			}
			ti.dereference();
		}
		//release memory
		ht.clear();
		for (int i=0; i<treeList.size(); i++){
			VETreeInfo vti = (VETreeInfo)treeList.getAt(i);
			vti.dereference();
		}
		treeList.clear();
		treeList = null;
	}

	/*
	This constructor is not for testing the existing data
	*/
	public TestPDGII(Database _db, Profile _prof, EntityItem _eiRoot, OPICMList _infoList, String _strFileName ) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, SQLException {
		String strTraceBase = " TestPDGII with no extractaction";
		D.ebug(D.EBUG_SPEW, strTraceBase);

		m_infoList = _infoList;
		m_eiPDG = (EntityItem)m_infoList.get("PDG");
		m_strGeoAttr = (String)m_infoList.get("GEOIND");

		StringBuffer sb = readFile(_strFileName);
		buildParentChildrenTypeList(sb);

		if (m_strGeoAttr != null) {
			sb = checkForGEO(sb);
		}

		StringTokenizer st1 = new StringTokenizer(sb.toString(),"\n");
		//Hashtable ht = new Hashtable();
		//EANList eiList = new EANList();
		//boolean bNext = false;
		while (st1.hasMoreTokens()) {
			String s = st1.nextToken();
			//D.ebug(D.EBUG_SPEW,"TestPDGII " + s);
			TreeInfo ti = new TreeInfo(s);
			adjustTreeInfoAttributes(ti);

			if (_eiRoot != null){
				if (ti.getLevel() == 1) {
					// its parent doesn't exist, therefore it doesn't exist, no need to look for it
					EntityItem eiParent = _eiRoot;
					m_sbNotFound.append("\n" + (eiParent != null?eiParent.getEntityType() + "-" + eiParent.getEntityID():" "));
					m_sbNotFound.append("|" + ti.dump(false));
				} else if (ti.getLevel() > 1) {
					m_sbNotFound.append("\n |" + ti.dump(false));
				}
			} else {		// start to run the PDG, no entities created yet, need all tree nodes.
				m_sbNotFound.append("\n |" + ti.dump(false));
			}
			ti.dereference();
		}
	}

	private StringBuffer checkForGEO(StringBuffer _sb) {
		StringTokenizer st1 = new StringTokenizer(_sb.toString(),"\n");

		StringBuffer sbReturn = new StringBuffer();
		while (st1.hasMoreTokens()) {
			String s = st1.nextToken();
			int i = s.indexOf(m_strGeoAttr);
			if (i > -1) {
				String strGEO = s.substring(i);
				StringTokenizer st2 = new StringTokenizer(strGEO,";");
				if	(st2.hasMoreTokens()) {
					strGEO = st2.nextToken();
				}

				EANFlagAttribute att = (EANFlagAttribute)m_eiPDG.getAttribute(m_strGeoAttr);

				if (att != null) {
					MetaFlag[] mfa = (MetaFlag[])att.get();
					boolean bWW = false;
					// check for WW for SG GENAREASELECTION
					if (att.getKey().equals("GENAREASELECTION") && att.isSelected("1999")) {
						bWW = true;
					}

					if (!bWW) {
						for (int f=0; f < mfa.length; f++) {
							MetaFlag mf = mfa[f];
							String flagCode = mf.getFlagCode();
							if (mf.isSelected()) {
								if (strGEO.indexOf(flagCode) > -1) {
									sbReturn.append("\n" + s);
									break;
								}
							}
						}
					} else {
						// if WW, add everything
						sbReturn.append("\n" + s);
					}
				} else {
					// if there's no GEO attribute on the PDG, add to the string buffer
					sbReturn.append("\n" + s);
				}
			} else {
				sbReturn.append("\n" + s);
			}
		}
		return sbReturn;
	}

	private Vector findEntityItem(TreeInfo _ti, OPICMList _list, EntityItem _eiParent) {
		String strTraceBase =  "TestPDGII findEntityItem ";
		D.ebug(D.EBUG_SPEW,strTraceBase + " 00 ");
		Vector vReturn = new Vector();
		if (_eiParent != null) {
			VETreeInfo vti = (VETreeInfo)_list.get(_eiParent.getKey());
			String strDirection = _ti.getDirection();
			OPICMList eiList = vti.getChildList();

			if (strDirection.equals("U")) {
				eiList = vti.getParentList();
			}

			for (int i=0; i < eiList.size(); i++) {
				EntityItem ei = (EntityItem)eiList.getAt(i);
				if (isMatch(_eiParent, ei, _ti)) {
					vReturn.addElement(ei);
				}
			}
		} else {
			String strEntityType = _ti.getEntityType();
			for (int i=0; i < _list.size(); i++) {
				VETreeInfo vti = (VETreeInfo)_list.getAt(i);
				EntityItem ei = vti.getEntityItem();
				if (ei.getEntityType().equals(strEntityType)) {
					if (isMatch(_eiParent, ei, _ti)) {
						vReturn.addElement(ei);
					}
				}
			}
		}

		return vReturn;
	}

	private Vector adjustAttributes(Vector vctAttributes) {
		//String strTraceBase = "TestPDGII adjustAttributes method";
		Vector tempVec = new Vector();

		for (int j=0; j < vctAttributes.size(); j++) {
			String s = (String)vctAttributes.elementAt(j);

			int i = s.indexOf("=");
			if (i > -1) {
				String strOriginalValue = s.substring(i+1);
				String strProcessedValue = "";
				StringTokenizer stAttValue = new StringTokenizer(strOriginalValue, ",");
				while (stAttValue.hasMoreTokens()) {
					String strAttrValue = stAttValue.nextToken();
					if (strAttrValue.indexOf("@") > -1) {
						StringBuffer sbReturn = new StringBuffer();
						StringTokenizer st = new StringTokenizer(strAttrValue,"@");

						while (st.hasMoreTokens()) {
							String s1 = st.nextToken();

							String strComment = "";
							int iSemi = s1.indexOf(":");
							if (iSemi < 0) {		// to pick up the comment part in front of @
								strComment = s1;
							} else {
								String code = s1.substring(iSemi+1);
								s1 = s1.substring(0,iSemi);

								//filter the comments
								int iComment = code.indexOf('\"');

								if (iComment > -1) {
									strComment = code.substring(iComment+1, code.length()-1);
									code = code.substring(0,iComment);
								}


								Object obj = m_infoList.get(s1);
								if (obj instanceof EntityItem) {

									EntityItem ei = (EntityItem)obj;

									int iStar = code.indexOf("*");
									if (iStar > -1) {
										code = code.substring(0,iStar);
									}

									if (code.equals("ENTITYKEY")) {
										sbReturn.append(ei.getKey());
									} else if (code.equals("ALL")) {
										for (int a = 0; a < ei.getAttributeCount(); a++) {
											EANAttribute att = ei.getAttribute(a);
											EANMetaAttribute ma = att.getMetaAttribute();
											if (ma != null && (!ma.isExcludeFromCopy())) {
												sbReturn.append(att.getAttributeCode() + "=" + att.toString());
											}
										}
									} else {
										EANAttribute att = ei.getAttribute(code);
										if (att != null) {
											if (att instanceof EANTextAttribute) {
												sbReturn.append(((String)att.get()).trim());
											} else if (att instanceof EANFlagAttribute) {
												MetaFlag[] mfa = (MetaFlag[])att.get();
												boolean bFirst = true;
												for (int f=0; f < mfa.length; f++) {
													MetaFlag mf = mfa[f];
													if (mf.isSelected()) {
														sbReturn.append(bFirst ? "" : ",");
														if (iStar > -1) {
															sbReturn.append(mf.getLongDescription());
														} else {
															sbReturn.append(mf.getFlagCode());
														}
														bFirst = false;
													}
												}
											}
										}
									}
								} else if (obj instanceof String) {
									String sObj = (String)obj;
									sbReturn.append(sObj);
								}
							}

							sbReturn.append(strComment);
						}
						strAttrValue = sbReturn.toString();
					}

					strProcessedValue = strProcessedValue + (strProcessedValue.length() > 0? ",": "") + strAttrValue;
				}
				// to prevent adding empty attribute value
				if (strProcessedValue.length() > 0) {
					s = s.substring(0, i) + "=" + strProcessedValue;
					tempVec.addElement(s);
				}
			}
		}
		return tempVec;
	}

	private void adjustTreeInfoAttributes(TreeInfo _ti) {
       // D.ebug(D.EBUG_SPEW,"adjustTreeInforAttributes before: " + _ti.dump(false));
		Vector vctAttributes = _ti.getAttributes();
		_ti.setAttributes(adjustAttributes(vctAttributes));
		Vector vctRelAttrs = _ti.getRelAttrs();
		_ti.setRelAttrs(adjustAttributes(vctRelAttrs));
       // D.ebug(D.EBUG_SPEW,"adjustTreeInforAttributes after: " + _ti.dump(false));
	}

	private boolean isMatch(EntityItem _eip, EntityItem _ei, TreeInfo _ti) {
		String strTraceBase = "TestPDGII isMatch method ";

		Vector vctAttributes = _ti.getAttributes();
		boolean bMatch = true;
		boolean bMapping = false;
		D.ebug(D.EBUG_SPEW,strTraceBase + "00 ");
		for (int j=0; j < vctAttributes.size(); j++) {
			String s = (String)vctAttributes.elementAt(j);
			D.ebug(D.EBUG_SPEW,strTraceBase + " s: " + s);
			if (s.substring(0,3).equals("map")) {
				D.ebug(D.EBUG_SPEW,strTraceBase + " in mapping ");
				bMapping = true;
				s = s.substring(4);
				//StringTokenizer st = new StringTokenizer(s.toString(),"=");
				//String strAttrCode = st.nextToken().trim();
				//String strAttrValue = st.nextToken().trim();
				int iEqual = s.indexOf("=");
				String strAttrCode = s.substring(0, iEqual);
				String strAttrValue = s.substring(iEqual + 1);

				String[] valArray = getValueArray(strAttrValue);

				if (strAttrCode.equals("ALL")) {
					StringBuffer sb = new StringBuffer();
					for (int a = 0; a < _ei.getAttributeCount(); a++) {
						EANAttribute att = _ei.getAttribute(a);
						EANMetaAttribute ma = att.getMetaAttribute();
						if (ma != null && (!ma.isExcludeFromCopy())) {
							sb.append(att.getAttributeCode() + "=" + att.toString());
						}
					}
					D.ebug(D.EBUG_SPEW, strTraceBase + " in ALL sb: " + sb.toString());
					D.ebug(D.EBUG_SPEW, strTraceBase + " in ALL strAttrValue: " + strAttrValue);
					if (sb.toString().trim().equals(strAttrValue)) {
						D.ebug(D.EBUG_SPEW, strTraceBase + " found matching for ALL, break out ");
						bMatch = true;
						break;
					} else {
						bMatch = false;
					}
				} else if (strAttrCode.equals("ENTITYKEY")) {
					D.ebug(D.EBUG_SPEW, strTraceBase + " in ENTITYKEY: " + _ei.getKey() + ":" + strAttrValue);
					if (_ei.getKey().equals(strAttrValue)) {
						D.ebug(D.EBUG_SPEW, strTraceBase + " found matching ENTITYKEY, break out ");
						bMatch = true;
						break;
					} else {
						bMatch = false;
					}
				} else {

					EANAttribute att = _ei.getAttribute(strAttrCode);
					if (att != null) {
						D.ebug(D.EBUG_SPEW,"isMatch att: " + att.getKey() + ":" + att.toString());
						if (att instanceof EANTextAttribute) {
							String attValue = ((String)att.get()).trim();
							if (! attValue.equals(strAttrValue)) {
								bMatch = false;
							}
						} else if (att instanceof EANFlagAttribute) {
							MetaFlag[] mfa = (MetaFlag[])att.get();
							boolean bFlag = true;
							for (int v=0; v < valArray.length; v++) {
								String val = valArray[v];
								for (int f=0; f < mfa.length; f++) {
									MetaFlag mf = mfa[f];
									String flagCode = mf.getFlagCode();
									String desc = mf.getLongDescription();
									if (mf.isSelected()) {
										if (!flagCode.equals(val) && !desc.equals(val)) {
											bFlag = false;
										} else {
											bFlag = true;
										}
									}
								}
								if(bFlag && !(att instanceof MultiFlagAttribute)) {
									break;
								}
							}

							if (!bFlag) {
								bMatch = false;
							}
						}
					} else {
						bMatch = false;
					}
				}
			}
		}

		//check relator attributes if needed and the child entity already matches
		String strRelatorInfo = _ti.getRelatorInfo();
		if (bMatch && strRelatorInfo.indexOf("map") > -1) {
			bMapping = true;
			String strRelatorType = "";
			String strRelatorAttr = "";
			int iAttrO = strRelatorInfo.indexOf("[");
			int iAttrC = strRelatorInfo.indexOf("]");
			if (iAttrO > -1) {
				strRelatorType = strRelatorInfo.substring(0,iAttrO);
				strRelatorAttr = strRelatorInfo.substring(iAttrO+1, (iAttrC > -1 ? iAttrC: strRelatorInfo.length()));
			} else {
				strRelatorType = strRelatorInfo;
			}

			// prepare the relator attribute vector.
			Vector vctRelAttr = new Vector();
			if (strRelatorAttr.length() > 0) {
				StringTokenizer stTemp = new StringTokenizer(strRelatorAttr,";");
				while (stTemp.hasMoreTokens()) {
					String str = stTemp.nextToken();
					//D.ebug(D.EBUG_SPEW,"prepare rel attr: " + str);
					vctRelAttr.addElement(str);
				}
			}

			// look for the matching relator
			EntityGroup eg = m_el.getEntityGroup(strRelatorType);
			if (eg != null && eg.isRelator()) {
				for (int i = 0; i < eg.getEntityItemCount(); i++) {
					EntityItem ei = eg.getEntityItem(i);
					EntityItem eid = (EntityItem)ei.getDownLink(0);
					EntityItem eiu = (EntityItem)ei.getUpLink(0);

					// check the relator that links the entities only
					if (eid == null || eiu == null) {
						bMatch = false;
						continue;
					}

					if (eid.getKey().equals(_ei.getKey()) && eiu.getKey().equals(_eip.getKey())) {
						bMatch = true;

						// check relator's attributes
						for (int j =0; j < vctRelAttr.size(); j++) {
							String s = (String)vctRelAttr.elementAt(j);
							if (s.substring(0,3).equals("map")) {
								s = s.substring(4);
								StringTokenizer st = new StringTokenizer(s.toString(),"=");
								String strAttrCode = st.nextToken().trim();
								String strAttrValue = st.nextToken().trim();

								EANAttribute att = ei.getAttribute(strAttrCode);
								if (att != null) {
									if (att instanceof EANTextAttribute) {
										String attValue = ((String)att.get()).trim();
										if (! attValue.equals(strAttrValue)) {
											bMatch = false;
										}
									} else if (att instanceof EANFlagAttribute) {
										MetaFlag[] mfa = (MetaFlag[])att.get();
										for (int f=0; f < mfa.length; f++) {
											MetaFlag mf = mfa[f];
											String flagCode = mf.getFlagCode();
											String desc = mf.getLongDescription();
											if (mf.isSelected()) {
												if (!flagCode.equals(strAttrValue) && !desc.equals(strAttrValue)) {
													bMatch = false;
												}
											}
										}
									}
								} else {
									D.ebug(D.EBUG_SPEW,"rel attr is null: " + strAttrCode);
									bMatch = false;
								}
							}
						}
					} else {
						bMatch = false;
					}

					if (bMatch) {
						break;
					}
				}
			} else {
				bMatch = false;
			}
		}

		// if no need to do mapping, set match to false
		if (!bMapping) {
			bMatch = false;
		}

		D.ebug(D.EBUG_SPEW,strTraceBase + " bMatch " + bMatch);
		return bMatch;
	}

	private void getChildren(EntityItem _ei, String _strEntityType, EntityList _el, boolean _bRoot, OPICMList _list) {
//		D.ebug(D.EBUG_SPEW,"TestPDGII getChildren: " + _ei.getKey() + ":" + _strEntityType);
		String strChildrenTypes = (String)m_childrenTypeList.get(_strEntityType);
		if (strChildrenTypes == null) {
			//D.ebug(D.EBUG_SPEW,"TestPDGII getChildren no children type" );
			return;
		}
		String strParentTypes = (String)m_parentTypeList.get(_strEntityType);

		for (int i=0; i < _ei.getDownLinkCount(); i++) {
			EntityItem ei = (EntityItem)_ei.getDownLink(i);
			//int iP = strParentTypes.indexOf(ei.getEntityType());
			//int iC = strChildrenTypes.indexOf(ei.getEntityType());
//			D.ebug(D.EBUG_SPEW,"TestPDGII getChildren: iP " + iP + ", iC: " + iC);

			if (strChildrenTypes.indexOf(ei.getEntityType()) >= 0) {
//				D.ebug(D.EBUG_SPEW,"TestPDGII getChildren put in child list : " + ei.getKey());
				_list.put(ei.getKey(), ei);
			} else {
				if (strParentTypes.indexOf(ei.getEntityType()) < 0) {
					// to prevent looping
					getChildren(ei, _strEntityType, _el, false, _list);
				}
			}
		}
	}

	private void getParents(EntityItem _ei, String _strEntityType, EntityList _el, boolean _bRoot, OPICMList _list) {
//		D.ebug(D.EBUG_SPEW,"TestPDGII getParents: " + _ei.getKey() + ":" + _strEntityType);
		String strParentTypes = (String)m_parentTypeList.get(_strEntityType);
		if (strParentTypes == null) {
			//D.ebug(D.EBUG_SPEW,"TestPDGII getParents no parent type" );
			return;
		}
		String strChildrenTypes = (String)m_childrenTypeList.get(_strEntityType);

		for (int i=0; i < _ei.getUpLinkCount(); i++) {
			EntityItem ei = (EntityItem)_ei.getUpLink(i);
			//int iP = strParentTypes.indexOf(ei.getEntityType());
			//int iC = strChildrenTypes.indexOf(ei.getEntityType());
			if (strParentTypes.indexOf(ei.getEntityType()) >= 0) {
				_list.put(ei.getKey(), ei);
			} else {
				if (strChildrenTypes.indexOf(ei.getEntityType()) < 0) {
					// to prevent looping
					getParents(ei, _strEntityType, _el, false, _list);
				}
			}
		}
	}

	private OPICMList buildVETree(EntityList _el) {
		D.ebug(D.EBUG_SPEW,"TestPDGII buildVETree: ");
		OPICMList veList = new OPICMList();

		EntityGroup eg = _el.getParentEntityGroup();
		for (int i=0; i< eg.getEntityItemCount(); i++) {
			EntityItem ei = eg.getEntityItem(i);
			OPICMList childList = new OPICMList();
			getChildren(ei, ei.getEntityType(), _el, true, childList);
			VETreeInfo vti = new VETreeInfo(ei);
			vti.putChildList(childList);
			OPICMList parentList = new OPICMList();
			getParents(ei, ei.getEntityType(), _el, true, parentList);
			vti.putParentList(parentList);
			veList.put(ei.getKey(), vti);
		}

		for (int i=0; i < _el.getEntityGroupCount(); i++) {
			EntityGroup eg1 = _el.getEntityGroup(i);
			D.ebug(D.EBUG_SPEW,"TestPDGII buildVETree eg1: " + eg1.getKey());
			for (int j=0; j < eg1.getEntityItemCount(); j++) {
				EntityItem ei = eg1.getEntityItem(j);
				OPICMList childList = new OPICMList();
				getChildren(ei, ei.getEntityType(), _el, true, childList);
				VETreeInfo vti = new VETreeInfo(ei);
				vti.putChildList(childList);
				OPICMList parentList = new OPICMList();
				getParents(ei, ei.getEntityType(), _el, true, parentList);
				vti.putParentList(parentList);
				veList.put(ei.getKey(), vti);
			}
		}

		for (int i =0; i < veList.size(); i++) {
			VETreeInfo ti = (VETreeInfo) veList.getAt(i);
			D.ebug(D.EBUG_SPEW,ti.dump(false));
		}

		return veList;
	}

	/*public StringBuffer genVETree(EntityItem _ei, int _iLevel, Hashtable _hshHistory, OPICMList _treeList) {
		D.ebug(this,D.EBUG_SPEW,"TestPDGII genVETree 00" + _ei.getKey() + _iLevel);
		StringBuffer strbResult = new StringBuffer();

		String strParentTypes = (String)m_parentTypeList.get(_ei.getEntityType());
		String strChildrenTypes = (String)m_childrenTypeList.get(_ei.getEntityType());
		D.ebug(this,D.EBUG_SPEW,"TestPDGII genVETree 02" + strParentTypes + ":" + strChildrenTypes);

		strbResult.append("\n" + _iLevel + ":" + _ei.getKey());

		if (! _hshHistory.containsKey(_ei.getKey() + _iLevel)) {
			_hshHistory.put(_ei.getKey() + _iLevel, "hi");
			VETreeInfo vti = (VETreeInfo)_treeList.get(_ei.getKey());
			if (vti != null) {
				OPICMList list= vti.getChildList();
				for (int i=0; i < list.size(); i++) {
					EntityItem ei = (EntityItem)list.getAt(i);
					strbResult.append(genVETree(ei, _iLevel +1, _hshHistory, _treeList));
				}

				OPICMList parentList= vti.getParentList();
				for (int i=0; i < parentList.size(); i++) {
					EntityItem ei = (EntityItem)parentList.getAt(i);
					strbResult.append(genVETree(ei, _iLevel +1, _hshHistory, _treeList));
				}

			}
		}

		return strbResult;
	}*/

	private StringBuffer readFile(String _fileName) throws SBRException {
        D.ebug(this,D.EBUG_INFO,"Reading PDG Template file :"+_fileName);
		File file =  new File(_fileName);
		SBRException sbr = new SBRException();
		StringBuffer sbReturn = new StringBuffer();
		if ( !file.exists() || !file.canRead(  ) ) {
			sbr.add("Can't read " + file.getAbsolutePath() );
		    throw sbr;
		}

		if ( file.isDirectory(  ) ) {
		    String [] files = file.list(  );
		    for (int i=0; i< files.length; i++)
		    	D.ebug(this,D.EBUG_SPEW,files[i] );
		} else {
		    try {
		        FileReader fr = new FileReader ( file );
		        BufferedReader in = new BufferedReader( fr );
		        String line;

		        while ((line = in.readLine(  )) != null) {
					String rest = line.trim();
					sbReturn.append("\n" + rest);
				}
		    } catch ( FileNotFoundException e ) {
		    	D.ebug(this,D.EBUG_SPEW,"File Disappeared" );
		    } catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sbReturn;
	}

	public StringBuffer getMissingEntities() {
		return m_sbNotFound;
	}

	public EntityList getEntityList() {
		return m_el;
	}

	private String[] getValueArray(String _s) {
		Vector v = new Vector();
		if (_s.indexOf(',') > -1) {
			StringTokenizer st = new StringTokenizer(_s, ",");
			while (st.hasMoreTokens()) {
				v.addElement(st.nextToken().trim());
			}
		} else {
			v.addElement(_s);
		}

		String[] aReturn = new String[v.size()];
		v.copyInto(aReturn);
		return aReturn;
	}

	private void buildParentChildrenTypeList(StringBuffer _sb) {
		StringTokenizer st1 = new StringTokenizer(_sb.toString(),"\n");

		while (st1.hasMoreTokens()) {
			String s = st1.nextToken();
                        D.ebug(D.EBUG_SPEW,"buildParentChildrenTypeList: Parsing template file string:"+s);
			TreeInfo ti = new TreeInfo(s);
			D.ebug(D.EBUG_SPEW,"buildParentChildrenTypeList strEntityType: " + ti.getEntityType());

			String str1 = (String) m_parentTypeList.get(ti.getEntityType());
			StringBuffer sbP = new StringBuffer();
			if (str1 != null) {
				m_parentTypeList.remove(ti.getEntityType());
				sbP.append(str1);
			}

			String strP = getEntityTypeList(_sb, ti.getLevel(), ti.getDirection(), true, sbP);

			D.ebug(D.EBUG_SPEW,"parentTypeList: " + strP);
			m_parentTypeList.put(ti.getEntityType(), strP);

			String str2 = (String) m_childrenTypeList.get(ti.getEntityType());
			StringBuffer sbC = new StringBuffer();
			if (str2 != null) {
				m_childrenTypeList.remove(ti.getEntityType());
				sbC.append(str2);
			}

			String strC = getEntityTypeList(_sb, ti.getLevel(), ti.getDirection(), false, sbC);

			D.ebug(D.EBUG_SPEW,"childrenTypeList: " + strC);
			m_childrenTypeList.put(ti.getEntityType(), strC);
		}
	}

	private String getEntityTypeList(StringBuffer _sb, int _iLevel, String _strD, boolean _bParent, StringBuffer _sbTemp) {
		StringTokenizer st1 = new StringTokenizer(_sb.toString(),"\n");

		while (st1.hasMoreTokens()) {
			String s = st1.nextToken();
			TreeInfo ti = new TreeInfo(s);

			int l = ti.getLevel();
			String strDirection = ti.getDirection();

			String strTemp = _sbTemp.toString();
			// if find parent
			if (_bParent) {
				if (_strD.equals("U")) {
					if ((l == _iLevel - 1) && strDirection.equals("U")) {
						if (strTemp.indexOf(ti.getEntityType()) < 0) {
							_sbTemp.append(ti.getEntityType() + ":");
						}
					}
				} else {
					if ((l == _iLevel + 1) && strDirection.equals("U")) {
						if (strTemp.indexOf(ti.getEntityType()) < 0) {
							_sbTemp.append(ti.getEntityType() + ":");
						}
					} else if ((l == _iLevel - 1) && strDirection.equals("D")) {
						if (strTemp.indexOf(ti.getEntityType()) < 0) {
							_sbTemp.append(ti.getEntityType() + ":");
						}
					}
				}
			} else {
				if (_strD.equals("U")) {
					if ((l == _iLevel - 1 ) && strDirection.equals("D")) {
						if (strTemp.indexOf(ti.getEntityType()) < 0) {
							_sbTemp.append(ti.getEntityType() + ":");
						}
					} else if ((l == _iLevel + 1 ) && strDirection.equals("D")) {
						if (strTemp.indexOf(ti.getEntityType()) < 0) {
							_sbTemp.append(ti.getEntityType() + ":");
						}
					}
				} else {
					if ((l == _iLevel + 1 ) && strDirection.equals("D")) {
						if (strTemp.indexOf(ti.getEntityType()) < 0) {
							_sbTemp.append(ti.getEntityType() + ":");
						}
					}
				}
			}
		}

		return _sbTemp.toString();
	}
}
