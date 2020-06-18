//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TestPDG.java,v $
// Revision 1.18  2008/09/08 17:32:46  wendy
// Cleanup RSA warnings
//
// Revision 1.17  2006/02/20 21:39:48  joan
// clean up System.out.println
//
// Revision 1.16  2005/06/15 21:33:17  joan
// add code
//
// Revision 1.15  2004/09/02 21:29:17  joan
// debug
//
// Revision 1.14  2004/08/13 21:32:23  joan
// fix error
//
// Revision 1.13  2004/08/12 18:03:03  joan
// work on PDG
//
// Revision 1.12  2003/11/26 17:56:14  joan
// adjust isMatch method
//
// Revision 1.11  2003/07/02 16:10:49  joan
// fix geo
//
// Revision 1.10  2003/05/08 17:38:29  joan
// fix fb
//
// Revision 1.9  2003/04/23 15:45:26  joan
// fix bugs
//
// Revision 1.8  2003/04/16 19:52:47  joan
// fix PDG creating entity
//
// Revision 1.7  2003/04/14 18:52:31  joan
// add HWUpgradeModelPDG
//
// Revision 1.6  2003/03/17 21:30:31  joan
// fix bugs
//
// Revision 1.5  2003/03/12 00:48:00  joan
// fix bugs
//
// Revision 1.4  2003/03/06 16:07:03  joan
// put more work
//
// Revision 1.3  2003/03/05 19:01:51  joan
// put more work
//
// Revision 1.2  2003/03/04 01:00:15  joan
// add pdg action item
//
// Revision 1.1  2003/03/03 18:38:08  joan
// initial load
//

// RMI and middleware

package COM.ibm.eannounce.objects;

import java.io.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;

// Normal Java stuff
import java.util.*;
import java.sql.*;

public class TestPDG  implements Serializable {

	private class TreeInfo {
		private int m_iLevel;
		private String m_strEntityType;
		private Vector m_vecAttributes = new Vector();
		private String m_strAction;
		private String m_strRelatorInfo;
		private String m_strRelatorType;
		private Vector m_vecRelAttrs = new Vector();


		public TreeInfo(String _s) {
			D.ebug(D.EBUG_SPEW,"TestPDG$TreeInfo _s: " + _s);
			StringTokenizer st1 = new StringTokenizer(_s,"|");
			if (st1.hasMoreTokens()) {
				m_iLevel = Integer.parseInt(st1.nextToken());

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

		public int getLevel() {
			return m_iLevel;
		}

		public String getEntityType() {
			return m_strEntityType;
		}

		public Vector getAttributes() {
			return m_vecAttributes;
		}

		public void setAttributes(Vector _v) {
			m_vecAttributes = _v;
		}

		public String getRelatorType() {
			return m_strRelatorType;
		}

		public Vector getRelAttrs() {
			return m_vecRelAttrs;
		}

		public void setRelAttrs(Vector _v) {
			m_vecRelAttrs = _v;
		}

		public String getAction() {
			return m_strAction;
		}

		public String getRelatorInfo() {
			StringBuffer sb = new StringBuffer();
			sb.append(m_strRelatorType);
			int size = m_vecRelAttrs.size();;
			for (int i=0; i < size; i++) {
				String strValue = (String)m_vecRelAttrs.elementAt(i);
				if (strValue.indexOf('\n') > 0) {
					//strValue.replace("\n", "\\n");
				}
				sb.append((i != 0?";":"[") + (String)m_vecRelAttrs.elementAt(i));
				if (i == size-1) {
					sb.append("]");
				}
			}

			return sb.toString();
		}

		public String dump(boolean _b) {
			StringBuffer sb = new StringBuffer();
			sb.append(m_iLevel + "|" + m_strEntityType + ":" );
			for (int i=0; i < m_vecAttributes.size(); i++) {
				sb.append((i != 0?";":"") + (String)m_vecAttributes.elementAt(i));
			}
			sb.append("|" + m_strAction);
			sb.append("|" + getRelatorInfo());
			return sb.toString();
		}
	}

	private class VETreeInfo {
		private EntityItem m_ei = null;
		private OPICMList m_childList = new OPICMList();

		public VETreeInfo(EntityItem _ei) {
			m_ei = _ei;
		}

		public EntityItem getEntityItem() {
			return m_ei;
		}
		public void putChildList(OPICMList _list) {
			m_childList = _list;
		}

		public OPICMList getChildList() {
			return m_childList;
		}

		public String dump(boolean _bBrief) {
			StringBuffer sb = new StringBuffer();
			sb.append("\n" + m_ei.getKey());
			sb.append("\nChildren:");
			for (int i=0; i < m_childList.size(); i++) {
				EntityItem ei = (EntityItem)m_childList.getAt(i);
				sb.append("\ni" + i + ":" + ei.getKey());
			}
			return sb.toString();
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

	public TestPDG(Database _db, Profile _prof, EntityItem _eiCOF, OPICMList _infoList, ExtractActionItem _xai, String _strFileName ) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, SQLException {
		m_infoList = _infoList;
		m_eiPDG = (EntityItem)m_infoList.get("PDG");
		m_strGeoAttr = (String)m_infoList.get("GEOIND");
		// get VE info
		OPICMList treeList = new OPICMList();
		if (_eiCOF != null) {
       		EntityItem[] eiParm = {_eiCOF};
			EntityList el = EntityList.getEntityList(_db, _prof, _xai, eiParm);
			m_el = el;
			treeList = buildVETree(el);
/*
	 		EntityGroup eg = el.getParentEntityGroup();
			_eiCOF = eg.getEntityItem(_eiCOF.getKey());
	 		StringBuffer sbVE = genVETree(_eiCOF, 0, new Hashtable(), treeList);
			System.out.println(sbVE.toString());
*/
		}

		StringBuffer sb = readFile(_strFileName);
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
			TreeInfo ti = new TreeInfo(s);
			adjustTreeInfoAttributes(ti);

			if (_eiCOF != null){ 	// this is for restart logic, check what tree nodes are missing
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
		}
	}

	/*
	This constructor is not for testing the existing data
	*/
	public TestPDG(Database _db, Profile _prof, EntityItem _eiRoot, OPICMList _infoList, String _strFileName ) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException, SQLException {
		m_infoList = _infoList;
		m_eiPDG = (EntityItem)m_infoList.get("PDG");
		m_strGeoAttr = (String)m_infoList.get("GEOIND");

		StringBuffer sb = readFile(_strFileName);

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
		Vector vReturn = new Vector();
		if (_eiParent != null) {
			VETreeInfo vti = (VETreeInfo)_list.get(_eiParent.getKey());
			OPICMList childList = vti.getChildList();
			for (int i=0; i < childList.size(); i++) {
			//	boolean bFound = true;
				EntityItem ei = (EntityItem)childList.getAt(i);
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

  	/*private boolean isDigit(String _s) {
 		for (int i =0; i < _s.length(); i++) {
		  	char c = _s.charAt(i);
		  	if (!Character.isDigit(c))
		  		return false;
	  	}
	  	return true;
  	}*/

	private Vector adjustAttributes(Vector vctAttributes) {
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

									EANAttribute att = ei.getAttribute(code);
									if (att != null) {
										if (att instanceof EANTextAttribute) {
											sbReturn.append(((String)att.get()).trim().replace('\n', ' '));
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
		Vector vctAttributes = _ti.getAttributes();
		_ti.setAttributes(adjustAttributes(vctAttributes));
		Vector vctRelAttrs = _ti.getRelAttrs();
		_ti.setRelAttrs(adjustAttributes(vctRelAttrs));
	}

	private boolean isMatch(EntityItem _eip, EntityItem _ei, TreeInfo _ti) {
		Vector vctAttributes = _ti.getAttributes();
		boolean bMatch = true;
		boolean bMapping = false;
		for (int j=0; j < vctAttributes.size(); j++) {
			String s = (String)vctAttributes.elementAt(j);
			if (s.substring(0,3).equals("map")) {
				bMapping = true;
				s = s.substring(4);
				StringTokenizer st = new StringTokenizer(s.toString(),"=");
				String strAttrCode = st.nextToken().trim();
				String strAttrValue = st.nextToken().trim();
				String[] valArray = getValueArray(strAttrValue);

				EANAttribute att = _ei.getAttribute(strAttrCode);
				if (att != null) {
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

		return bMatch;
	}

	private OPICMList getChildren(EntityItem _ei, EntityList _el, boolean _bRoot, OPICMList _list) {
		EntityGroup eg = null;
		if (_bRoot) {
			eg = _el.getParentEntityGroup();
		} else {
			eg = _el.getEntityGroup(_ei.getEntityType());
		}

		if (eg != null) {
			if (eg.isRelator()) {
				for (int i=0; i < _ei.getDownLinkCount(); i++) {
					EntityItem ei = (EntityItem)_ei.getDownLink(i);
					_list.put(ei.getKey(), ei);
				}
				return _list;
			} else {
				for (int i=0; i < _ei.getDownLinkCount(); i++) {
					EntityItem ei = (EntityItem)_ei.getDownLink(i);
					getChildren(ei, _el, false, _list);
				}
			}
		}

		return _list;
	}

	private OPICMList buildVETree(EntityList _el) {
		OPICMList veList = new OPICMList();

		EntityGroup eg = _el.getParentEntityGroup();
		for (int i=0; i< eg.getEntityItemCount(); i++) {
			EntityItem ei = eg.getEntityItem(i);
			OPICMList childList = getChildren(ei, _el, true, new OPICMList());
			VETreeInfo vti = new VETreeInfo(ei);
			vti.putChildList(childList);
			veList.put(ei.getKey(), vti);
		}


		for (int i=0; i < _el.getEntityGroupCount(); i++) {
			EntityGroup eg1 = _el.getEntityGroup(i);
			if (!eg1.isRelator()) {
				for (int j=0; j < eg1.getEntityItemCount(); j++) {
					EntityItem ei = eg1.getEntityItem(j);
					OPICMList childList = getChildren(ei, _el, true, new OPICMList());
					VETreeInfo vti = new VETreeInfo(ei);
					vti.putChildList(childList);
					veList.put(ei.getKey(), vti);
				}
			}
		}
/*
		for (int i =0; i < veList.size(); i++) {
			VETreeInfo ti = (VETreeInfo) veList.getAt(i);
			D.ebug(D.EBUG_SPEW,ti.dump(false));
		}
*/
		return veList;
	}

	public StringBuffer genVETree(EntityItem _ei, int _iLevel, Hashtable _hshHistory, OPICMList _treeList) {
		StringBuffer strbResult = new StringBuffer();

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
			}
		}

		return strbResult;
	}

	private StringBuffer readFile(String _fileName) throws SBRException {
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
				System.out.println( files[i] );
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
		        System.out.println( "File Disappeared" );
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
}
