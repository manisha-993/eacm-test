/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Joan Tran
 *
 * $Log: EditPDGPanel.java,v $
 * Revision 1.6  2009/08/25 12:24:33  wendy
 * avoid NPE
 *
 * Revision 1.5  2008/02/18 16:20:22  wendy
 * Use lockitem from eaccess when possible
 *
 * Revision 1.4  2008/02/13 15:55:47  wendy
 * Get lockowner from eaccess to use entityitem from hashtable
 *
 * Revision 1.3  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/08/01 18:44:58  wendy
 * prevent hang
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.3  2006/11/09 00:31:43  tony
 * added monitor functionality
 *
 * Revision 1.2  2005/10/13 20:11:22  joan
 * adjust for PDG
 *
 * Revision 1.1.1.1  2005/09/09 20:37:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.15  2005/09/08 17:58:59  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.14  2005/03/07 17:46:24  joan
 * add delete key in pdg dialog
 *
 * Revision 1.13  2005/02/28 22:25:34  joan
 * add catch exception
 *
 * Revision 1.12  2005/02/24 18:51:30  joan
 * work on edit panel
 *
 * Revision 1.11  2005/02/15 17:17:30  joan
 * add buttons
 *
 * Revision 1.10  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.9  2005/02/02 17:44:57  joan
 * fix TIR
 *
 * Revision 1.8  2005/02/02 17:30:20  tony
 * JTest Second Pass
 *
 * Revision 1.7  2005/01/27 23:18:50  tony
 * JTest Formatting
 *
 * Revision 1.6  2005/01/21 19:20:35  joan
 * change to refresh with no seperate thread
 *
 * Revision 1.5  2005/01/07 17:02:05  tony
 * USRO-R-RLON-68EBE2
 *
 * Revision 1.4  2004/11/08 21:41:05  joan
 * work on PDG
 *
 * Revision 1.3  2004/11/02 00:31:53  joan
 * fixes for PDG dialog
 *
 * Revision 1.2  2004/08/09 20:32:05  joan
 * adjust for PDG
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.38  2003/10/29 00:21:16  tony
 * removed System.out. statements.
 *
 * Revision 1.37  2003/10/02 15:19:40  joan
 * fb fix
 *
 * Revision 1.36  2003/09/23 23:20:07  joan
 * fixes for upgrade
 *
 * Revision 1.35  2003/07/18 22:14:43  joan
 * 51336
 *
 * Revision 1.34  2003/07/18 00:11:16  joan
 * fix horz. bar
 *
 * Revision 1.33  2003/07/10 20:17:25  joan
 * 51317
 *
 * Revision 1.32  2003/07/10 18:09:36  tony
 * updated logic to comply with 1.2h
 *
 * Revision 1.31  2003/07/10 15:20:54  joan
 * 51317
 *
 * Revision 1.30  2003/07/03 23:27:16  joan
 * call reset list
 *
 * Revision 1.29  2003/07/03 16:38:50  tony
 * adjusted logic to pass entity item to dsatabase for
 * scripting purposes.
 *
 * Revision 1.28  2003/06/19 23:52:02  joan
 * fix bug
 *
 * Revision 1.27  2003/06/19 21:03:01  joan
 * fix bug
 *
 * Revision 1.26  2003/06/19 20:00:07  joan
 * work on PDGInfo panel
 *
 * Revision 1.25  2003/06/17 19:47:43  joan
 * work on SPDGActionItem
 *
 * Revision 1.24  2003/06/02 21:12:24  tony
 * updated messaging logic to improve performance.
 *
 * Revision 1.23  2003/06/02 18:29:33  joan
 * 51068
 *
 * Revision 1.22  2003/05/30 21:09:18  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.21  2003/05/28 16:27:40  tony
 * 50924
 *
 * Revision 1.20  2003/05/15 17:33:35  tony
 * 50656
 *
 * Revision 1.19  2003/05/15 16:26:33  joan
 * work on pop up dialog when editing PDG
 *
 * Revision 1.18  2003/05/14 21:34:10  tony
 * cleaned-up logic
 *
 * Revision 1.17  2003/05/13 16:06:54  tony
 * 50621
 *
 * Revision 1.16  2003/05/06 20:40:17  joan
 * 50530
 *
 * Revision 1.15  2003/05/06 18:59:22  joan
 * 50530
 *
 * Revision 1.14  2003/04/23 23:25:17  joan
 * fix bug
 *
 * Revision 1.13  2003/04/15 23:30:26  joan
 * fix feedback
 *
 * Revision 1.12  2003/04/14 21:55:32  joan
 * 50362
 *
 * Revision 1.11  2003/04/14 16:57:03  joan
 * fix null pointer
 *
 * Revision 1.10  2003/04/10 19:50:32  joan
 * fix feedback
 *
 * Revision 1.9  2003/04/08 23:20:47  joan
 * 50352
 *
 * Revision 1.8  2003/04/08 17:53:38  joan
 * 50354
 *
 * Revision 1.7  2003/04/03 22:44:18  joan
 * fixes
 *
 * Revision 1.6  2003/03/29 00:06:27  tony
 * added remove Menu Logic
 *
 * Revision 1.5  2003/03/27 21:29:05  joan
 * fix bug
 *
 * Revision 1.4  2003/03/27 16:24:13  tony
 * adjusted logic for reuse of the editController component.
 *
 * Revision 1.3  2003/03/26 17:06:48  tony
 * per Joan adjusted logic on PDG so
 * that table is properly displayed.  Adjusted logic
 * by trapping possible null pointers.
 *
 * Revision 1.2  2003/03/25 18:28:38  joan
 * fix null pointer
 *
 * Revision 1.1  2003/03/20 01:05:06  joan
 * initial load
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eforms.edit.*;
import COM.ibm.eannounce.objects.*;
//import COM.ibm.opicmpdh.middleware.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EditPDGPanel extends AccessibleDialogPanel implements ActionListener, PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	/*
     USRO-R-RLON-68EBE2
    	private eButton btnInfo = new eButton("View Missing Data");
    	private eButton	btnOK = new eButton("Generate Data");
    */
    private EButton btnStep = new EButton("Step");
    private EButton btnInfo = new EButton(getString("viewMD")); //USRO-R-RLON-68EBE2
    private EButton btnOK = new EButton(getString("genDat")); //USRO-R-RLON-68EBE2
    private EButton btnSave = new EButton(getString("save")); //USRO-R-RLON-68EBE2

    private EButton btnCancel = new EButton(getString("cncl"));

//    private eLabel lblSearch = new eLabel(getString("fnd-l"));

    private PDGActionItem m_pdgai = null;
    private SPDGActionItem m_spdgai = null;
    private EntityList m_el = null;
    private EntityItem m_ei = null;
    private RowSelectableTable m_rst = null;

    private EditController m_ec = new EditController();

    private EPanel pSouth = new EPanel(new GridLayout(1, 3));

//    private int navType = 0;

//    private boolean bProcess = false;
    private boolean m_bSPDG = false;

    /**
     * EditPDGPanel
     * @author Anthony C. Liberto
     */
    public EditPDGPanel() {
        super(new BorderLayout(5, 5));
    }

    //	public JMenuBar getMenuBar() {
    //		return null;
    //	}

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
        menubar.setModalCursor(true); //51336
        menubar.removeAll();
        menubar.addMenu("Help", "attHelp", this, KeyEvent.VK_F1, 0, true);
        menubar.setMenuMnemonic("Help", 'H');
        menubar.addMenu("Edit", "rfrsh", this, KeyEvent.VK_F5, 0, true);
        menubar.setMenuMnemonic("Edit", 'E');
        menubar.setMenuMnemonic("Refresh", 'R');
        menubar.addSeparator("Edit");
        menubar.addMenu("Edit", "dActAtt", this, KeyEvent.VK_DELETE, 0, true);
    }

    private void attributeHelp() {
        String str = null;
        if (m_ec != null) {
            str = m_ec.getHelpText();
        }
        setMessage((str != null && str.length() > 0) ? str : getString("nia"));
        eaccess().showFYI(this);
        setModalBusy(false);
    }

    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        pSouth.add(btnSave);
        pSouth.add(btnInfo);
        pSouth.add(btnOK);
        pSouth.add(btnCancel);

        //USRO-R-RLON-68EBE2		btnOK.setMnemonic(getChar("fnd-s"));		//22554
        btnCancel.setMnemonic(getChar("cncl-s")); //22554
        btnOK.setMnemonic(getChar("genDat-s")); //USRO-R-RLON-68EBE2
        btnInfo.setMnemonic(getChar("viewMD-s")); //USRO-R-RLON-68EBE2
        btnSave.setMnemonic('a'); //USRO-R-RLON-68EBE2

        btnOK.addActionListener(this);
        btnOK.setActionCommand("generate");
        btnStep.addActionListener(this);
        btnStep.setActionCommand("step");
        btnInfo.addActionListener(this);
        btnInfo.setActionCommand("view");
        btnCancel.addActionListener(this);
        btnCancel.setActionCommand("cancel");
        btnSave.addActionListener(this);
        btnSave.setActionCommand("save");

        add("South", pSouth);
        setModal(true);
    }

    /**
     * setFindEnabled
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setFindEnabled(boolean _b) { //17010
        btnOK.setEnabled(_b); //17010
    } //17010

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * @author Anthony C. Liberto
     */
    public void propertyChange(PropertyChangeEvent _pce) { //17010
        String property = _pce.getPropertyName(); //17010
        if (property.equalsIgnoreCase("Activate")) { //17010
            setFindEnabled(true); //17010
        } else if (property.equalsIgnoreCase("Deactivate")) { //17010
            setFindEnabled(false); //17010
        }
    } //17010

    /**
     * disposeDialog
     * @author Anthony C. Liberto
     */
    public void disposeDialog() {
        try {
            if (m_rst.hasChanges()) {
                m_rst.rollback();
            }
            closeEdit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        	try{
            super.disposeDialog();
        	}catch(Exception x){}
            eaccess().setPDGOn(false); //50530
            setBusy(false);
        }
    }

    /**
     * setSPDG
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSPDG(boolean _b) {
        m_bSPDG = _b;
    }

    /**
     * isSPDG
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSPDG() {
        return m_bSPDG;
    }

    /**
     * processRequest
     * @author Anthony C. Liberto
     */
    public void processRequest() {
        final ESwingWorker myWorker = new ESwingWorker() {
            public Object construct() {
				try{
					Exception e = null;
					EntityGroup eg = m_el.getParentEntityGroup();
					m_ec.stopEditing(); //50394
					if (m_rst.hasChanges()) {
						if (m_ei.getEntityID() < 0) {
							m_ei.setParentEntityItem(eg.getEntityItem(0));
						}
						e = dBase().commit(m_rst, m_ec);
					}

					if (e == null) {
						System.out.println("processRequest m_ei: " + m_ei.getKey());
						if (m_bSPDG) {

							dBase().genPDGData(m_spdgai, m_ei, (Window) getParentDialog());
						} else {
							dBase().genPDGData(m_pdgai, m_ei, (Window) getParentDialog());
						}
						return "pass";
					} else {
						if (e instanceof EANBusinessRuleException) {
							EANBusinessRuleException bre = (EANBusinessRuleException) e;
							m_ec.moveToErrorPDG(bre); //51068
						}
						setMessage(e.toString());
						showError(getParentDialog());
						return null;
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in EditPDGPanel.processRequest.ESwingWorker.construct() "+ex);
					ex.printStackTrace();
					setBusy(false);
				}
                return null;
            }

            public void finished() {
                Object o = getValue();
                if (o instanceof String) {
                    refresh();
                }

                setWorker(null);
                setBusy(false);
            }
        };
        setWorker(myWorker);
    }

    /**
     * processRequest
     * @author Anthony C. Liberto
     */
    public void processSave() {
        final ESwingWorker myWorker = new ESwingWorker() {
            public Object construct() {
				try{
					Exception e = null;
					EntityGroup eg = m_el.getParentEntityGroup();
					m_ec.stopEditing(); //50394
					if (m_rst.hasChanges()) {
						if (m_ei.getEntityID() < 0) {
							m_ei.setParentEntityItem(eg.getEntityItem(0));
						}
						e = dBase().commit(m_rst, m_ec);
					}

					if (e != null) {
						if (e instanceof EANBusinessRuleException) {
							EANBusinessRuleException bre = (EANBusinessRuleException) e;
							m_ec.moveToErrorPDG(bre); //51068
						}
						setMessage(e.toString());
						showError(getParentDialog());
						return null;
					} else {
						return "pass";
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in EditPDGPanel.processSave.ESwingWorker.construct() "+ex);
					ex.printStackTrace();
					setBusy(false);
				}
				return null;
            }

            public void finished() {
                Object o = getValue();
                if (o instanceof String) {
                    refresh();
                }

                setWorker(null);
                setBusy(false);
            }
        };
        setWorker(myWorker);
    }

    /**
     * processView
     * @author Anthony C. Liberto
     */
    public void processView() {
        final ESwingWorker myWorker = new ESwingWorker() {
            public Object construct() {
				try{
					Exception e = null;
					//boolean bNewEntity = false;
					EntityGroup eg = m_el.getParentEntityGroup();
					byte[] ba = null;
					m_ec.stopEditing(); //50394
					if (m_rst.hasChanges()) {
						if (m_ei.getEntityID() < 0) {
						//	bNewEntity = true;
							m_ei.setParentEntityItem(eg.getEntityItem(0));
						}
						e = dBase().commit(m_rst, m_ec);
					}

					if (e == null) {
						System.out.println("processView m_ei: " + m_ei.getKey());
						if (m_bSPDG) {
							ba = dBase().viewMissingPDGData(m_spdgai, m_ei, (Window) getParentDialog());
						} else {
							ba = dBase().viewMissingPDGData(m_pdgai, m_ei, (Window) getParentDialog());
						}

						if (ba != null) {
							String s = new String(ba);
							return s;
						} else {
							return "";
						}
					} else {
						if (e instanceof EANBusinessRuleException) {
							EANBusinessRuleException bre = (EANBusinessRuleException) e;
							m_ec.moveToErrorPDG(bre); //51068
						}
						setMessage(e.toString());
						//50621					showError();
						showError(getParentDialog()); //50621
						return null;
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in EditPDGPanel.processView.ESwingWorker.construct() "+ex);
					ex.printStackTrace();
	                setBusy(false);
	                setModalBusy(false);
				}
				return null;
            }

            public void finished() {
                Object o = getValue();
                if (o instanceof String) {
                    String s = (String) o;
                    refreshNoSepThread();
                    //refresh();
                    if (s.length() > 0) {
                        setWorker(null);
                        setModalBusy(false);
                        setBusy(false);
                        eaccess().showModalScrollDialog((Window) getParentDialog(), s);
                    }
                }

                setWorker(null);
                setBusy(true);
                setModalBusy(false);
            }
        };
        setWorker(myWorker);
    }

    /**
     * processStep
     * @author Anthony C. Liberto
     */

    public void processStep() {
        final ESwingWorker myWorker = new ESwingWorker() {
            public Object construct() {
				try{
					Exception e = null;
					//boolean bNewEntity = false;
					EntityGroup eg = m_el.getParentEntityGroup();

					PDGCollectInfoList cl = null;
					m_ec.stopEditing(); //50394
					if (m_rst.hasChanges()) {
						if (m_ei.getEntityID() < 0) {
						//	bNewEntity = true;
							m_ei.setParentEntityItem(eg.getEntityItem(0));
						}
						e = dBase().commit(m_rst, m_ec);
					}

					if (e == null) {
						if (m_pdgai.isCollectParentNavInfo()) {
							try {
								Object[] ao = eaccess().getParentInformationAtLevel(m_pdgai.getParentNavInfoLevel());
								if (ao.length == 2) {
									String strEntityType = (String)ao[0];
									int[] aiEntityID = (int[])ao[1];
									EntityItem[] aei = new EntityItem[aiEntityID.length];
									for (int i=0; i < aiEntityID.length; i++) {
										int iEntityID = aiEntityID[i];
										EntityItem ei = new EntityItem(null, getActiveProfile(), strEntityType, iEntityID);
										aei[0] = ei;
									}
									m_pdgai.setParentNavInfo(aei);
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}

						m_pdgai.setEntityItem(m_ei);
						cl = m_pdgai.collectInfo(m_pdgai.getCollectStep());
						if (cl == null) {
							try {
								cl = m_pdgai.collectInfo(null, getRemoteDatabaseInterface(), getActiveProfile(), m_pdgai.getCollectStep());
							} catch (Exception ex) {
								ex.printStackTrace();
								setModalBusy(false);
								setBusy(false);
								setMessage(ex.toString());
								showError(getParentDialog());
								return null;
							}
						}

						return cl;

					} else {
						if (e instanceof EANBusinessRuleException) {
							EANBusinessRuleException bre = (EANBusinessRuleException) e;
							m_ec.moveToErrorPDG(bre); //51068
						}

						setModalBusy(false);
						setBusy(false);
						setMessage(e.toString());
						showError(getParentDialog()); //50621
						return null;
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in EditPDGPanel.processStep.ESwingWorker.construct() "+ex);
					ex.printStackTrace();
	                setBusy(false);
	                setModalBusy(false);
				}
				return null;
            }

            public void finished() {
                Object o = getValue();
                if (o instanceof PDGCollectInfoList) {
					PDGCollectInfoList cl = (PDGCollectInfoList) o;
					PDGInfoPanel panel = EAccess.getPDGInfoPnl();
					panel.setModalCursor(true); //51317

                    setWorker(null);
                    setModalBusy(false);
                    setBusy(false);


					panel.setTitle(m_pdgai.getStepDescription(m_pdgai.getCollectStep()));

					panel.setPDGActionItem(m_pdgai);
					panel.setRequestTable(m_rst);
					cl = panel.showPDGInfo((Window) getParentDialog(), cl);
					if (cl != null) {
						//m_pdgai.setPDGCollectInfo(cl, m_pdgai.getCollectStep(), m_rst);
						m_ec.refresh();
					}
                }

                setWorker(null);
                setBusy(true);
                setModalBusy(false);
            }
        };
        setWorker(myWorker);
    }

    /**
     * processStep
     * @author Anthony C. Liberto
     */
/*
    public void getPDGInfo(EANMetaAttribute _meta) {
		System.out.println("EditPDGPanel getPDGInfo");
		setModalBusy(true);

		PDGCollectInfoList cl = null;
		if (m_pdgai.isCollectParentNavInfo()) {
			try {
				Object[] ao = eaccess().getParentInformationAtLevel(m_pdgai.getParentNavInfoLevel());
				if (ao.length == 2) {
					String strEntityType = (String)ao[0];
					int[] aiEntityID = (int[])ao[1];
					EntityItem[] aei = new EntityItem[aiEntityID.length];
					for (int i=0; i < aiEntityID.length; i++) {
						int iEntityID = aiEntityID[i];
						EntityItem ei = new EntityItem(null, getActiveProfile(), strEntityType, iEntityID);
						aei[0] = ei;
					}
					m_pdgai.setParentNavInfo(aei);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}


		m_pdgai.setEntityItem(m_ei);
		try {
			cl = m_pdgai.collectInfo(null, getRemoteDatabaseInterface(), getActiveProfile(), _meta);
		} catch (Exception ex) {
			ex.printStackTrace();
			setModalBusy(false);
			setBusy(false);
			setMessage(ex.toString());
			showError(getParentDialog());
			return;
		}


		if (cl != null) {
			PDGInfoPanel panel = EAccess.getPDGInfoPnl();
			panel.setModalCursor(true); //51317

            setModalBusy(false);
            setBusy(false);


			panel.setTitle(_meta.getLongDescription());
			panel.setRequestMeta(_meta);

			panel.setPDGActionItem(m_pdgai);
			panel.setRequestTable(m_rst);
			cl = panel.showPDGInfo((Window) getParentDialog(), cl);
			if (cl != null) {
				m_ec.refresh();
			}
		}

    }
*/
    /**
     * processStep
     * @author Anthony C. Liberto
     */

    public void getPDGInfo(final EANMetaAttribute _meta) {
		System.out.println("EditPDGPanel getPDGInfo");
		setModalBusy(true);
        final ESwingWorker myWorker = new ESwingWorker() {
            public Object construct() {
				PDGCollectInfoList cl = null;
				try{
					if (m_pdgai.isCollectParentNavInfo()) {
						try {
							Object[] ao = eaccess().getParentInformationAtLevel(m_pdgai.getParentNavInfoLevel());
							if (ao.length == 2) {
								String strEntityType = (String)ao[0];
								int[] aiEntityID = (int[])ao[1];
								EntityItem[] aei = new EntityItem[aiEntityID.length];
								for (int i=0; i < aiEntityID.length; i++) {
									int iEntityID = aiEntityID[i];
									EntityItem ei = new EntityItem(null, getActiveProfile(), strEntityType, iEntityID);
									aei[0] = ei;
								}
								m_pdgai.setParentNavInfo(aei);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

					m_pdgai.setEntityItem(m_ei);
					try {
						cl = m_pdgai.collectInfo(null, getRemoteDatabaseInterface(), getActiveProfile(), _meta);
					} catch (Exception ex) {
						ex.printStackTrace();
						setModalBusy(false);
						setBusy(false);
						setMessage(ex.toString());
						showError(getParentDialog());
						return null;
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in EditPDGPanel.getPDGInfo.ESwingWorker.construct() "+ex);
					ex.printStackTrace();
	                setBusy(false);
	                setModalBusy(false);
				}

				return cl;
            }

            public void finished() {
                Object o = getValue();
                if (o instanceof PDGCollectInfoList) {
					PDGCollectInfoList cl = (PDGCollectInfoList) o;
					PDGInfoPanel panel = EAccess.getPDGInfoPnl();
					panel.setModalCursor(true); //51317

                    setWorker(null);
                    setModalBusy(false);
                    setBusy(false);


					panel.setTitle(_meta.getLongDescription());
					panel.setRequestMeta(_meta);

					panel.setPDGActionItem(m_pdgai);
					panel.setRequestTable(m_rst);
					cl = panel.showPDGInfo((Window) getParentDialog(), cl);
					if (cl != null) {
						m_ec.refresh();
					}
                }

                setWorker(null);
                setBusy(true);
                setModalBusy(false);
            }
        };
        setWorker(myWorker);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _e) {
        String action = _e.getActionCommand();
        if (isModalBusy()) {
            return;
        }
        //51317		setBusy(true);
        setModalBusy(true);
        if (action.equals("cancel")) {
            disposeDialog();
        } else if (action.equals("generate")) {
            processRequest();
            return;
        } else if (action.equals("view")) {
            processView();
            return;
        } else if (action.equals("step")) {
            processStep();
            return;
        } else if (action.equals("attHelp")) {
            attributeHelp();
        } else if (action.equals("rfrsh")) {
            refresh();
        } else if (action.equals("save")) {
            processSave();
            return;
        } else if (action.equals("dActAtt")) {
			EannounceEditor ed = m_ec.getCurrentEditor();
			if (ed != null) {
				ed.deactivateAttribute();
			}
        }

        //51317		setBusy(false);
        setModalBusy(false);
    }

    /**
     * showEditPDG
     * @param _pdgai
     * @param _el
     * @author Anthony C. Liberto
     */
    public void showEditPDG(PDGActionItem _pdgai, EntityList _el) {
        setPDGActionItem(_pdgai, _el);
        m_pdgai.resetPDGCollectInfo();
        //51317		setBusy(false);
        //51317		showDialog(this,this);
        eaccess().show(this, this, false); //51317
        setBusy(true); //51317
        setModalCursor(true); //51317
    }

    /**
     * setPDGActionItem
     * @param _pdgai
     * @param _el
     * @author Anthony C. Liberto
     */
    public void setPDGActionItem(PDGActionItem _pdgai, EntityList _el) {
        m_pdgai = _pdgai;
        m_el = _el;

        pSouth.add(btnOK);
        pSouth.add(btnInfo);

        if (m_pdgai.isCollectInfo()) {
            //USRO-R-RLON-68EBE2			btnStep.setText(m_pdgai.getStepDescription(m_pdgai.getCollectStep()));
            String txt = m_pdgai.getStepDescription(m_pdgai.getCollectStep()); //USRO-R-RLON-68EBE2
            if (txt != null) { //USRO-R-RLON-68EBE2
                btnStep.setText(txt); //USRO-R-RLON-68EBE2
                setMnemonic(btnStep, txt.toCharArray()); //USRO-R-RLON-68EBE2
            } //USRO-R-RLON-68EBE2
            pSouth.add(btnStep);
        } else {
            pSouth.remove(btnStep);
        }

        pSouth.add(btnCancel);
        setTitle(_pdgai.toString());
        constructTable(m_el);
        if (id instanceof Window) { //50656
            ((Window) id).toFront(); //50656
        } //50656
        packDialog();
    }

    /**
     * showEditSPDG
     * @param _spdgai
     * @param _el
     * @author Anthony C. Liberto
     */
    public void showEditSPDG(SPDGActionItem _spdgai, EntityList _el) { //SPDG
        setSPDGActionItem(_spdgai, _el);
        //51317		setBusy(false);
        //51317		showDialog(this,this);
        eaccess().show(this, this, false); //51317
        setBusy(true); //51317
        setModalCursor(true); //51317
    }

    /**
     * setSPDGActionItem
     * @param _spdgai
     * @param _el
     * @author Anthony C. Liberto
     */
    public void setSPDGActionItem(SPDGActionItem _spdgai, EntityList _el) {
        m_spdgai = _spdgai;
        m_el = _el;
        setTitle(_spdgai.toString());
        constructTable(m_el);
        if (id instanceof Window) {
            ((Window) id).toFront();
        }
        packDialog();
    }

    /**
     * refresh
     * @author Anthony C. Liberto
     */
    public void refresh() {
        final ESwingWorker myWorker = new ESwingWorker() {
            public Object construct() {
                try {
                    EntityGroup eg = m_el.getEntityGroup(m_ei.getEntityType());
                    if (eg != null) {
                        if (eg.isRelator() || eg.isAssoc()) {
                            EntityItem eiD = (EntityItem) m_ei.getDownLink(0);
                            eg = m_el.getEntityGroup(eiD.getEntityType());
                            m_ei.removeDownLink(eiD);
                            eiD = getRemoteDatabaseInterface().refreshEntityItem(getActiveProfile(), eg, eiD);
                            m_ei.putDownLink(eiD);
                        } else {
                            m_ei = getRemoteDatabaseInterface().refreshEntityItem(getActiveProfile(), eg, m_ei);
                        }
                    }
                    return "pass";
                } catch (Exception ex) {
                    ex.printStackTrace();
	                setBusy(false);
                    return null;
                }
            }

            public void finished() {
                refreshTable(m_ei.getEntityItemTable());
                setWorker(null);
                setBusy(false);
                return;
            }
        };
        setWorker(myWorker);
        setBusy(false);
    }

    /**
     * refreshNoSepThread
     * @author Anthony C. Liberto
     */
    public void refreshNoSepThread() {
        EntityGroup eg = m_el.getEntityGroup(m_ei.getEntityType());
        try {
            if (eg != null) {

                if (eg.isRelator() || eg.isAssoc()) {
                    EntityItem eiD = (EntityItem) m_ei.getDownLink(0);
                    eg = m_el.getEntityGroup(eiD.getEntityType());
                    m_ei.removeDownLink(eiD);
                    eiD = getRemoteDatabaseInterface().refreshEntityItem(getActiveProfile(), eg, eiD);
                    m_ei.putDownLink(eiD);
                } else {
                    m_ei = getRemoteDatabaseInterface().refreshEntityItem(getActiveProfile(), eg, m_ei);
                }
            }

            if (m_ei != null) {
                refreshTable(m_ei.getEntityItemTable());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void closeEdit() {
        LockList ll = null;
        EntityItem lockOwnerEI = null;
        try {
            //m_ec.gc();moved below remove() to prevent null ptr
            remove(m_ec);          
            m_ec.gc();
                       
            pSouth.remove(btnStep);
            m_ec = null;
            ll = getLockList();
            if (ll == null) {
                return;
            }
            lockOwnerEI = eaccess().getLockOwner();
            	//new EntityItem(null, getActiveProfile(), Profile.OPWG_TYPE, getOPWGID());
            if (lockOwnerEI!=null){
            	m_rst.unlock(getRemoteDatabaseInterface(), null, ll, getActiveProfile(), lockOwnerEI, LockGroup.LOCK_NORMAL);
            	if (eaccess().isMonitor()) {
            		eaccess().monitor("unlock", ll);
            	}
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * constructTable
     * @param _eList
     * @author Anthony C. Liberto
     */
    public void constructTable(EntityList _eList) {
        int ii = _eList.getEntityGroupCount();
        for (int i = 0; i < ii; ++i) {
            EntityGroup eg = _eList.getEntityGroup(i);
            if (eg.isDisplayable()) {
                if (eg.getEntityItemCount() > 0) {
                    EntityItem ei = eg.getEntityItem(0);
                    m_ei = ei;
                    m_rst = m_ei.getEntityItemTable();
                }

                break;
            }
        }

        if (m_rst != null) {
            m_ec = new EditController();
            m_ec.buildPDGEdit(m_rst, m_pdgai, this);
            m_ec.refresh();

            //			pNorth.add("North", (JComponent)ec);
            add("Center", (JComponent) m_ec);
        }
    }

    /**
     * removeTable
     * @author Anthony C. Liberto
     */
    public void removeTable() {
        remove(m_ec);
    }

    /**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() { //50530
        m_ec.requestFocus();
    }
    /**
     * getPDGActionItem
     * @return
     * @author Anthony C. Liberto
     */
    public PDGActionItem getPDGActionItem() {
        return m_pdgai;
    }

    /*
     50621
    */
    /**
     * setParentDialog
     *
     * @author Anthony C. Liberto
     * @param _id
     */
    public void setParentDialog(InterfaceDialog _id) {
        id = _id;
        //		if (m_ec != null) {
        //			metaValidator valid = m_ec.getValidator();
        //			if (valid != null) {
        //				valid.setInterfaceDialog(id);
        //			}
        //		}
    }

    /*
     50924
     */
    /**
     * getSearchableObject
     *
     * @author Anthony C. Liberto
     * @return Object
     */
    public Object getSearchableObject() {
        return m_ec.getSearchableObject();
    }

    /**
     * refreshTable
     * @param _rst
     * @author Anthony C. Liberto
     */
    public void refreshTable(RowSelectableTable _rst) {
        if (_rst != null) {
            m_rst = _rst;
            if (m_ec != null) {
				m_ec.refreshTable(m_rst);
				m_ec.repaintImmediately();
			}
        }
    }

    /*
     USRO-R-RLON-68EBE2
     */
    private void setMnemonic(JButton _btn, char[] _c) {
        if (_btn != null && _c != null) {
            int ii = _c.length;
            char char0 = getChar("cncl-s");
            char char1 = getChar("genDat-s");
            char char2 = getChar("viewMD-s");
            for (int i = 0; i < ii; ++i) {
                if (_c[i] != char0) {
                    if (_c[i] != char1) {
                        if (_c[i] != char2) {
                            _btn.setMnemonic(_c[i]);
                            return;
                        }
                    }
                }
            }
        }
    }
}
