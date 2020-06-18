package COM.ibm.eannounce.objects;
import java.util.StringTokenizer;
import java.util.Vector;

public class VEPath {
	public static String DELIMIT2 = ".";						//VEEdit_Iteration3
	public static String DELIMIT = ",";
	public static String UP = "U";
	public static String DOWN = "D";

	private String m_sType = null;
	private String m_sDir = null;
	private Vector m_vAtt = null;								//VEEdit_Iteration3

	/**
	 * constructor
	 *
	 * @param string
	 * @author tony
	 */
	public VEPath(String _s) {
//		System.out.println("createVEPath(" + _s + ")");
		StringTokenizer st = null;

		if (_s.indexOf(DELIMIT2) >=0) {							//VEEdit_Iteration3
			st = new StringTokenizer(_s,DELIMIT2);				//VEEdit_Iteration3
			if (st.hasMoreTokens()) {							//VEEdit_Iteration3
				setType(st.nextToken());						//VEEdit_Iteration3
			}													//VEEdit_Iteration3
			while(st.hasMoreTokens()) {							//VEEdit_Iteration3
				setAttribute(st.nextToken());					//VEEdit_Iteration3
			}													//VEEdit_Iteration3
		} else if (_s.indexOf(DELIMIT) >=0) {					//VEEdit_Iteration3
			st = new StringTokenizer(_s,DELIMIT);
			if (st.hasMoreTokens()) {
				setType(st.nextToken());
			}
			if (st.hasMoreTokens()) {
				setDirection(st.nextToken());
			}
		} else {												//VEEdit_Iteration3
			setType(_s);										//VEEdit_Iteration3
		}														//VEEdit_Iteration3
		return;
	}

	/**
	 * constructor
	 *
	 * @param type
	 * @param direction
	 * @author tony
	 */
	public VEPath(String _eType, String _dir) {
		setType(_eType);
		setDirection(_dir);
		return;
	}

	/**
	 * set Type
	 *
	 * @param string
	 * @author tony
	 */
	public void setType(String _s) {
		if (_s != null) {
			m_sType = new String(_s);
		}
		return;
	}

	/**
	 * getType
	 *
	 * @return string
	 * @author tony
	 */
	public String getType() {
		return m_sType;
	}

	/**
	 * set Attribute
	 * VEEdit_Iteration3
	 *
	 * @param string
	 * @author tony
	 */
	public void setAttribute(String _s) {
		if (m_vAtt == null) {
			m_vAtt = new Vector();
		}
		if (_s != null) {
			m_vAtt.add(new String(_s));
		}
		return;
	}

	/**
	 * get Attribute
	 * VEEdit_Iteration3
	 *
	 * @return string
	 * @author tony
	 */
	public String[] getAttributes() {
		if (m_vAtt != null) {
			return (String[])m_vAtt.toArray(new String[m_vAtt.size()]);
		}
		return null;
	}

	public boolean hasAttributes() {
		if (m_vAtt != null) {
			return !m_vAtt.isEmpty();
		}
		return false;
	}
	/**
	 * set Direction
	 *
	 * @param direct
	 * @author tony
	 */
	public void setDirection(String _s) {
		if (_s != null) {
			m_sDir = new String(_s);
		}
		return;
	}

	/**
	 * get direction
	 *
	 * @return str
	 * @author tony
	 */
	public String getDirection() {
		return m_sDir;
	}

	/**
	 * to String
	 *
	 * @return string
	 * @author tony
	 */
	public String toString() {
		if (m_sDir == null) {
			if (!hasAttributes()) {
				return m_sType;
			} else {
				StringBuffer sb = new StringBuffer();
				sb.append(m_sType);
				String[] atts = getAttributes();
				if (atts != null) {
					int ii = atts.length;
					for (int i=0;i<ii;++i) {
						sb.append(".");
						sb.append(atts[i]);
					}
				}
				return sb.toString();
			}
		}
		return m_sType + ":" + m_sDir;
	}

	/**
	 * equals
	 *
	 * @param object
	 * @return boolean
	 * @author tony
	 */
	public boolean equals(Object _o) {
		if (_o instanceof VEPath) {
			VEPath p = (VEPath)_o;
			return toString().equals(p.toString());
		}
		return false;
	}
	/**
	 * is Up
	 *
	 * @return boolean
	 * @author tony
	 */
	public boolean isUp() {
		return m_sDir != null && m_sDir.equals(UP);
	}

	/**
	 * is Down
	 *
	 * @return boolean
	 * @author tony
	 */
	public boolean isDown() {
		return m_sDir != null && m_sDir.equals(DOWN);
	}

	/**
	 * is target entity
	 *
	 * @return boolean
	 * @author Tony
	 */
	public boolean isTarget() {
		return m_sDir == null;
	}

	/**
	 * get path from
	 * VEEdit Iteration2
	 *
	 * @param _parent
	 * @param _child
	 * @param list
	 * @param action
	 * @return EntityGroup
	 * @author tony
	 */
	public static synchronized EntityGroup[] getEntityGroupArray(String _parent, String _child, EntityList _eList) {
		if (_parent != null && _child != null && _eList != null) {
			VEPath[] path = getPath(_child,_eList);
			if (path != null) {
				Vector v = new Vector();
				boolean b = false;
				int ii = path.length;
				for (int i=0;i<ii;++i) {
					String sType = path[i].getType();
					if (sType != null) {
						if (sType.equals(_child)) {
							break;
						} else if (b) {
							EntityGroup eg = _eList.getEntityGroup(sType);
							if (eg != null) {
								v.add(eg);
							}
						} else if (sType.equals(_parent)) {
							b = true;
						}
					}
				}
				if (!v.isEmpty()) {
					return (EntityGroup[])v.toArray(new EntityGroup[v.size()]);
				}
			}
		}
		return null;
	}

	private static synchronized VEPath[] getPath(String _type, EntityList _eList) {
		VEPath[] out = null;
		if (_type != null && _eList != null) {
			EANActionItem ean = _eList.getParentActionItem();
			if (ean instanceof ExtractActionItem) {
				ExtractActionItem eai = (ExtractActionItem)ean;
				out = eai.getPath(_type);
			}
		}
		return out;
	}

	public static synchronized EntityItem[] getEntityItems(EntityItem _start, EntityList _eList, boolean _forward) {
//		System.out.println("    VEPath.getEntityItems()");
		if (_start != null && _eList == null) {
			EntityGroup eg = _start.getEntityGroup();
			if (eg != null) {
				_eList = eg.getEntityList();
			}
		}
		if (_eList != null) {
			ExtractActionItem eai = (ExtractActionItem)_eList.getParentActionItem();
			String[] disp = eai.getDisplayable();
			EntityItem ei = _start;
			if (disp != null) {
				int ii = disp.length;
				Vector v = new Vector();
				for (int i=0;i<ii;++i) {
					VEPath[] path = null;
					String sType = disp[i];
					if (!_forward) {
						sType = ei.getEntityType();
					}
					path = getPath(sType,_eList);
					if (path != null) {
						int xx = path.length;
						if (_forward) {
							for (int x=1;x<xx;++x) {
								VEPath prev = path[x-1];
								VEPath cur = path[x];
								if (ei != null && !ei.getEntityType().equals(disp[i])) {
									ei = getEntityItem(ei,cur.getType(),prev.isUp());
									if (ei != null) {
										v.add(ei);
									}
								}
							}
						} else {
							for (int x=0;x<xx;++x) {
								VEPath cur = path[x];
								if (!cur.isTarget()) {
									if (ei != null) {
										if (!ei.getEntityType().equals(disp[i])) {
											ei = getEntityItem(ei,cur.getType(),!cur.isUp());
											if (ei != null) {
												v.add(ei);
											}
										}
									}
								}
							}
						}
					}
				}
				if (!v.isEmpty()) {
					return (EntityItem[])v.toArray(new EntityItem[v.size()]);
				}
			}
		}
		return null;
	}

	public static synchronized EANAttribute[] getAttributes(VEPath[] _path, EntityItem _start) {
		if (_path != null && _start != null) {
			EntityItem ei = _start;
			VEPath prev = null;
			VEPath cur = null;
			VEPath last = null;
			int ii = _path.length;
			for (int i=1;i<ii;++i) {
				prev = _path[i-1];
				cur = _path[i];
				if (ei != null) {
					ei = getEntityItem(ei,cur.getType(),prev.isUp());
					last = cur;
				}
			}
			if (ei != null && _start != ei && last != null) {
				Vector v = new Vector();
				String[] atts = last.getAttributes();
				if (atts != null) {
					int xx = atts.length;
					for (int x=0;x<xx;++x) {
						v.add(ei.getAttribute(atts[x]));
					}
				}
				if (!v.isEmpty()) {
					return (EANAttribute[])v.toArray(new EANAttribute[v.size()]);
				}
			}
		}
		return null;
	}

	public static synchronized EntityItem getEntityItem(String _type, EntityItem _start, EntityList _eList, boolean _forward) {
		EntityItem out = null;
		if (_start != null && _eList == null) {
			EntityGroup eg = _start.getEntityGroup();
			if (eg != null) {
				_eList = eg.getEntityList();
			}
		}
		if (_type != null && _start != null && _eList != null) {
//			System.out.println("            VEPath.getEntityItem(" + _type + ", " + _start.getEntityType() + ", " + _eList.getKey() + ", " + _forward + ")");
			out = _start;
			VEPath[] path = null;
			if (_forward) {
				path = getPath(_type,_eList);
			} else {
				path = getPath(_start.getEntityType(),_eList);
			}
			if (path != null) {
				int ii = path.length;
				if (!_forward) {
					for (int i=ii-1;i>=0;--i) {
//						System.out.println("                pathRev " + i + " of " + ii);
						VEPath cur = path[i];
						if (!cur.isTarget()) {
//							System.out.println("                currentPathRev : " + cur.toString());
//							System.out.println("                positionRev    : " + out.getEntityType());
							if (out != null) {
								if (!out.getEntityType().equals(_type)) {
									out = getEntityItem(out,cur.getType(),!cur.isUp());
//								} else {
//									System.out.println("                out is " + _type);
								}
//							} else {
//								System.out.println("                out is null");
							}
//						} else {
//							System.out.println("                I am a target");
						}
					}
				} else {
					for (int i=1;i<ii;++i) {
//						System.out.println("                path " + i + " of " + ii);
						VEPath prev = path[i-1];
						VEPath cur = path[i];
//						System.out.println("                previousPath: " + prev.toString());
//						System.out.println("                currentPath : " + cur.toString());
//						System.out.println("                position    : " + out.getEntityType());
						if (out != null && !out.getEntityType().equals(_type)) {
							out = getEntityItem(out,cur.getType(),prev.isUp());
						}
					}
				}
			}
		}
		return out;
	}

	public static synchronized EntityItem getEntityItem(EntityItem _ei, String _type, boolean _up) {
//		System.out.println("    getEntityItem(" + _type + ", " + _up +")");
		EntityItem ei = null;
		if (_type != null && _ei != null) {
			if (_up) { // look at up links first
//				System.out.println("        checking up links");
				if (_ei.hasUpLinks()) {
					for (int i=0; i<_ei.getUpLinkCount(); i++) {
						EntityItem tmp = (EntityItem)_ei.getUpLink(i);
						if (_type.equals(tmp.getEntityType())) {
							ei = tmp;
							break;
						}
					}
				}
			}
			if (ei==null){ // not found yet
				if (_ei.hasDownLinks()) {
//					System.out.println("        checking down links");
					for (int i=0; i<_ei.getDownLinkCount(); i++) {
						EntityItem tmp = (EntityItem)_ei.getDownLink(i);
						if (_type.equals(tmp.getEntityType())) {
							ei = tmp;
							break;
						}
					}
				}
			}
		}
		return ei;
	}

	public static synchronized String getVirtualKey(EntityGroup _par, ExtractActionItem _xtract) {
		return _par.getEntityType() + "_VIRTUALENTITY" + _xtract.getID();
	}
	/**
	 * process path to EntityItems from EntityItem
	 * based on the defined path
	 *
	 * @param _path
	 * @param _ei
	 * @return EntityItems
	 * @author tony
	 */
	public EntityItem[] process(EntityItem _ei, VEPath _path) {
		if (_ei != null) {
//			System.out.println("VEPath.process(" + _ei.getClass().getName() + ", " + _path.toString() + ")");
			Vector v = new Vector();
			String type = getType();
			if (type != null) {
//				System.out.println("    type: " + type);
				if (_path.isUp()) {
//					System.out.println("    path is up");
					if (_ei.hasUpLinks()) {
						int ii = _ei.getUpLinkCount();
						for (int i=0;i<ii;++i) {
//							System.out.println("    processing UP " + i + " of " + ii);
							EntityItem out = (EntityItem)_ei.getUpLink(i);
							String strEnt = out.getEntityType();
//							System.out.println("    entity UP: " + strEnt);
							if (strEnt != null && strEnt.equals(type)) {
//								System.out.println("    added UP " + out.getKey());
								v.add(out);
							}
						}
					}
				} else if (_path.isDown()) {
//					System.out.println("    path is down");
					if (_ei.hasDownLinks()) {
						int ii = _ei.getDownLinkCount();
						for (int i=0;i<ii;++i) {
//							System.out.println("    processing DOWN " + i + " of " + ii);
							EntityItem out = (EntityItem)_ei.getDownLink(i);
							String strEnt = out.getEntityType();
//							System.out.println("        entity DOWN: " + strEnt);
							if (strEnt != null && strEnt.equals(type)) {
//								System.out.println("    added DOWN " + out.getKey());
								v.add(out);
							}
						}
					}
//				} else {
//					System.out.println("flat Lined");
				}
			}
			if (!v.isEmpty()) {
				return (EntityItem[]) v.toArray(new EntityItem[v.size()]);
			}
		}
		return null;
	}

	/**
	 * is the EntityItem new
	 *
	 * @param EntityItem
	 * @return boolean
	 * @author tony
	 */
	public static boolean isNew(EntityItem _ei) {
		if (_ei.isVEEdit()) {
//			System.out.println("VEPath.isnew entered with ei " + _ei.getKey());
			EntityGroup eg = _ei.getEntityGroup();
			if (eg != null) {
				EntityList eList = eg.getEntityList();
				if (eList != null) {
					// TIR6YZSJJ this is incorrectly flagging a parent entity item as new
					// it was finding the first displayable entity and checking that
					if (_ei.getEntityType().equals(eList.getParentEntityGroup().getEntityType())){
						return _ei.amNew();
					}
					EANActionItem ean = eList.getParentActionItem();
					if (ean != null) {
						ExtractActionItem eai = (ExtractActionItem)ean;
						String[] sDisp = eai.getDisplayable();
						if (sDisp != null) {
							int ii = sDisp.length;
							for (int i=0;i<ii;++i) {
//								System.out.println("checking " + i + " of " + ii+"    " + sDisp[i]);
								EntityItem ei = getEntityItem(sDisp[i], _ei, eList, true);
								if (ei != null) {
//									System.out.println("VEPath.isnew found ei " + ei.getKey() + " was found");
									if (ei.amNew()) {
//										System.out.println("    I am New");
										return true;
//									} else {
//										System.out.println("    NOT New");
									}
//								} else {
//									System.out.println("ei is null");
								}
							}
						}
					}
				}
			}
		} else {
			return _ei.amNew();
		}
		return false;
	}

}

/**
 * Copyright (c) 2005, International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * VEEdit Iteration 2 the revenge of create
 *
 * @version 3.0b  2005/10/13
 * @author Anthony C. Liberto
 *
 * $Log: VEPath.java,v $
 * Revision 1.9  2009/02/18 17:16:50  wendy
 * Returning wrong entity for getEntityItem if no match was found
 *
 * Revision 1.8  2007/04/12 19:17:31  wendy
 * TIR6YZSJJ returning parent as new
 *
 * Revision 1.7  2005/11/14 15:16:46  tony
 * VEEdit_Iteration3
 * PRODSTRUCT Functionality.
 *
 * Revision 1.6  2005/11/10 21:25:17  tony
 * VEEdit_Iteration3
 *
 * Revision 1.5  2005/11/07 21:40:12  tony
 * improved logic for VEEdit Iteration2
 *
 * Revision 1.4  2005/11/04 23:15:21  tony
 * adjusted
 *
 * Revision 1.3  2005/11/04 23:02:17  tony
 * VEEdit_Iteration2 tuning
 *
 * Revision 1.2  2005/11/04 16:50:13  tony
 * VEEdit_Iteration2
 *
 * Revision 1.1  2005/11/04 14:58:07  tony
 * VEEdit_Iteration2
 *
 */
