/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: WizardPanel.java,v $
 * Revision 1.3  2009/05/26 12:29:25  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:25  wendy
 * Reorganized JUI module
 *
 * Revision 1.5  2006/05/15 20:41:43  tony
 * fixed error messaging.
 *
 * Revision 1.4  2006/05/15 18:38:42  tony
 * vs_20060515
 * missing metalink for linkaction error.
 *
 * Revision 1.3  2005/10/18 22:48:08  tony
 * MN25719436
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:45  tony
 * This is the initial load of OPICM
 *
 * Revision 1.30  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.29  2005/06/08 14:48:14  tony
 * improved logic to help find meta errors.
 *
 * Revision 1.28  2005/03/16 17:12:00  tony
 * 23199651 refresh the cart before querying it for data.
 *
 * Revision 1.27  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.26  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.25  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.24  2005/01/31 20:47:45  tony
 * JTest Second Pass
 *
 * Revision 1.23  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.22  2005/01/18 19:24:50  tony
 * updated logging on link action.
 *
 * Revision 1.21  2004/12/17 16:20:34  tony
 * NMHR-67QQZW logic enhancement
 *
 * Revision 1.20  2004/12/16 23:29:17  tony
 * NMHR-67QQZW
 *
 * Revision 1.19  2004/11/17 17:04:17  tony
 * USRO-R-DMKR-66STZL
 *
 * Revision 1.18  2004/11/03 23:48:32  tony
 * fixed getting opposing parent from navigate.
 *
 * Revision 1.17  2004/10/26 22:37:16  tony
 * TIR USRO-R-BTES-664TH5
 *
 * Revision 1.16  2004/10/11 20:59:53  tony
 * improved debug functionality.
 *
 * Revision 1.15  2004/10/09 17:41:18  tony
 * improved debugging functionality.
 *
 * Revision 1.14  2004/09/30 17:34:51  tony
 * improved application logging for autolinking functionality.
 *
 * Revision 1.13  2004/09/16 21:18:06  tony
 * MN20805300
 *
 * Revision 1.12  2004/09/03 23:00:31  tony
 * alleged null pointer fixed.
 *
 * Revision 1.11  2004/07/29 22:37:52  tony
 * added in approved autolink functionality.
 *
 * Revision 1.10  2004/06/30 19:55:04  tony
 * removed display statement.
 *
 * Revision 1.9  2004/06/30 17:03:22  tony
 * linking dual navigate fixed
 *
 * Revision 1.8  2004/06/25 16:28:43  tony
 * added auto_link preference.
 *
 * Revision 1.7  2004/06/24 15:30:51  tony
 * CR0618043756 -- repaired logic flaw, uncovered in testing.
 *
 * Revision 1.6  2004/06/22 23:27:56  tony
 * CR0618043756 (opposing selected link)
 *
 * Revision 1.5  2004/05/24 22:11:10  tony
 * cr_ActChain behavior mods
 *
 * Revision 1.4  2004/05/24 21:48:54  tony
 * cr_ActChain
 *
 * Revision 1.3  2004/05/20 14:47:54  tony
 * cr_ActChain
 *
 * Revision 1.2  2004/03/25 23:37:20  tony
 * cr_216041310
 *
 * Revision 1.1.1.1  2004/02/10 16:59:29  tony
 * This is the initial load of OPICM
 *
 * Revision 1.26  2004/01/06 19:50:14  tony
 * acl_20040106
 * updated logic to use the cart to link if the opposing navigate
 * is not in the correct location.
 *
 * Revision 1.25  2003/12/01 17:44:52  tony
 * accessibility
 *
 * Revision 1.24  2003/11/04 17:36:08  tony
 * 52871
 *
 * Revision 1.23  2003/10/29 00:21:16  tony
 * removed System.out. statements.
 *
 * Revision 1.22  2003/09/10 18:56:43  tony
 * 52143
 *
 * Revision 1.21  2003/08/15 15:36:54  tony
 * cr_0805036452
 *
 * Revision 1.20  2003/07/22 15:42:46  tony
 * 51492
 *
 * Revision 1.19  2003/07/03 16:38:04  tony
 * improved scripting logic.
 *
 * Revision 1.18  2003/06/25 16:18:29  tony
 * 1.2h modification
 *
 * Revision 1.17  2003/06/12 22:23:40  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.16  2003/06/04 20:47:59  tony
 * 50591
 *
 * Revision 1.15  2003/06/02 16:45:29  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.14  2003/05/30 21:09:20  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.13  2003/05/29 21:20:44  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.12  2003/05/28 16:27:39  tony
 * 50924
 *
 * Revision 1.11  2003/05/20 16:13:02  tony
 * 50817
 *
 * Revision 1.10  2003/05/09 17:30:50  tony
 * updated wizard logic
 *
 * Revision 1.9  2003/05/09 16:51:29  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.8  2003/05/08 19:18:51  tony
 * 50582
 *
 * Revision 1.7  2003/05/07 22:12:25  tony
 * made changes to show in frame dialog.  Modal dialog was
 * preventing table selection.
 *
 * Revision 1.6  2003/04/28 16:23:57  tony
 * 50444 -- added getString() around msg3011.0
 *
 * Revision 1.5  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.4  2003/03/25 23:29:05  tony
 * added eDisplayable to provide an
 * easier interface into components.  This will
 * assist in ease of programming in the future.
 *
 * Revision 1.3  2003/03/13 18:38:44  tony
 * accessibility and column Order.
 *
 * Revision 1.2  2003/03/04 16:54:04  tony
 * adjusted logic for Wizard and picklist update to allow
 * multiple link action selection. per cc
 *
 * Revision 1.1.1.1  2003/03/03 18:03:44  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.exception.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.eannounce.eforms.navigate.*;
import com.ibm.eannounce.eforms.table.NavTable;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JViewport;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class WizardPanel extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	private EPanel pBtn = new EPanel(new GridLayout(1, 4, 2, 2));
    private EPanel pNorth = new EPanel(new BorderLayout());

    private Dimension scrollSize = new Dimension(300, 400);
    private EScrollPane scroll = new EScrollPane();
    private ELabel lblDirection = new ELabel();
    private ELabel lblFilter = new ELabel(eaccess().getImageIcon("fltr.gif"));

    private EButton btnNext = new EButton(getString("next"));
    private EButton btnPrev = new EButton(getString("prev"));
    private EButton btnFinish = new EButton(getString("fin"));
    private EButton btnCancel = new EButton(getString("cncl"));

    private EANActionItem ai = null;
    private Navigate nav = null;
    private Component[] view = null;
    private int curIndex = -1;
    private String msgPrefix = null;

    /**
     * wizardPanel
     * @author Anthony C. Liberto
     */
    public WizardPanel() {
        super(new BorderLayout());
        init();
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        setModal(true);
        btnPrev.setActionCommand("prev");
        btnNext.setActionCommand("next");
        btnFinish.setActionCommand("finish");
        btnCancel.setActionCommand("exit");

        btnNext.setMnemonic(getChar("next-s"));
        btnPrev.setMnemonic(getChar("prev-s"));
        btnFinish.setMnemonic(getChar("fin-s"));
        btnCancel.setMnemonic(getChar("cncl-s"));

        btnPrev.addActionListener(this);
        btnNext.addActionListener(this);
        btnFinish.addActionListener(this);
        btnCancel.addActionListener(this);

        setModalCursor(true);

        pBtn.add(btnPrev);
        pBtn.add(btnNext);
        pBtn.add(btnCancel);
        pBtn.add(btnFinish);

        scroll.setSize(scrollSize);
        scroll.setPreferredSize(scrollSize);

        pNorth.add("North", lblFilter);
        pNorth.add("South", lblDirection);

        add("North", pNorth);
        add("Center", scroll);
        add("South", pBtn);
        lblFilter.setEnabled(false);
    }

    private void setActionItem(EANActionItem _ai) {
        if (_ai instanceof LinkActionItem) {
            LinkActionItem link = (LinkActionItem) _ai;
            MetaLink mLink = null;
            if (link != null) {
                appendLog("parent link: " + link.useParents());
                appendLog("oppSel link: " + link.isOppSelect());
                appendLog("cart link  : " + link.useCart());
                appendLog("link       : " + link.canLink());
                appendLog("copyLink   : " + link.canLinkCopy());

                mLink = link.getMetaLink();
                if (mLink != null) {
                    appendLog("link Parent is: " + mLink.getEntity1Type());
                    appendLog("link child is: " + mLink.getEntity2Type());
                } else {
                    appendLog("link undefined:");
                }
            }
        }
        appendLog("linkType: " + eaccess().getLinkType());
        ai = _ai;
    }

    private void setNavigate(Navigate _nav) {
        nav = _nav;
    }

    private void setMessagePrefix(String _s) {
        msgPrefix = _s;
    }

    private void setView(Component _c) {
        setNavTableMenuEnabled(_c instanceof NavTable); //50582
        scroll.setViewportView(_c);
        scroll.revalidate();
        if (_c instanceof NavTable) { //USRO-R-DMKR-66STZL
            ((NavTable) _c).scrollToFirstSelectedRow(); //USRO-R-DMKR-66STZL
        } //USRO-R-DMKR-66STZL
        lblFilter.setEnabled(isFiltered());
        lblDirection.setLabelFor(_c);
        if (eaccess().isDebug()) {
            if (_c instanceof NavTable) {
                NavTable nt = (NavTable) _c;
                EntityItem ei = nt.getEntityItem(0, false);
                if (ei != null) {
                    appendLog("table entityType is: " + ei.getEntityType());
                    if (ei.hasUpLinks()) {
                        int xx = ei.getUpLinkCount();
                        for (int x = 0; x < xx; ++x) {
                            appendLog("    uplink(" + x + " of " + xx + "): " + ei.getUpLink(x).getEntityType());
                        }
                    }
                    if (ei.hasDownLinks()) {
                        int xx = ei.getDownLinkCount();
                        for (int x = 0; x < xx; ++x) {
                            appendLog("    downlink(" + x + " of " + xx + "): " + ei.getDownLink(x).getEntityType());
                        }
                    }
                }
            }
        }
    }

    private void setView(int _i) {
        if (isValid(_i)) {
            setView(view[_i]);
            curIndex = _i;
        }
    }

    private boolean isValid(int _i) {
        if (view == null) {
            return false;
        } else if (_i < 0) {
            return false;
        } else if (_i >= view.length) {
            return false;
        }
        return true;
    }

    private boolean isLast(int _i) {
        if (view == null) {
            return false;
        } else if ((view.length - 1) == _i) {
            return true;
        }
        return false;
    }

    private boolean isFirst(int _i) {
        if (_i == 0) {
            return true;
        }
        return false;
    }

    private void adjustButtons(int _i) {
        boolean bLast = isLast(_i);
        boolean bFirst = isFirst(_i);
        btnNext.setEnabled(!bLast);
        btnFinish.setEnabled(bLast);
        btnPrev.setEnabled(!bFirst);
        setCode(msgPrefix + _i); //acl_20021024
        setParm(getParm(ai)); //acl_20021024
        lblDirection.setText(getMessage()); //acl_20021024
        eaccess().clearParms(); //acl_20021024
    }

    private String getParm(EANActionItem _ai) { //acl_20021024
        if (ai != null) { //acl_20021024
            if (ai instanceof LinkActionItem) { //acl_20021024
                LinkActionItem link = (LinkActionItem) _ai; //acl_20021024
                if (link.canLink()) { //acl_20021024
                    return getString("lnk"); //acl_20021024
                } else if (link.canLinkCopy()) { //acl_20021024
                    return getString("copylink"); //acl_20021024
                } //acl_20021024
            } //acl_20021024
        } //acl_20021024
        return ""; //acl_20021024
    } //acl_20021024

    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        int iCopy = 0;
        if (isModalBusy()) {
            return;
        }
        if (_action.equals("finish")) {
            if (isLinkCopy()) {
                iCopy = getCopies();
            }
        }
        setModalBusy(true);
        if (_action.equals("prev")) {
            prev();
        } else if (_action.equals("next")) {
            next();
        } else if (_action.equals("finish")) {
            finish(iCopy);
        } else if (_action.equals("exit")) {
            cancel();
        } else if (_action.equals("selA")) { //50582
            NavTable tbl = getNavTable(); //50582
            if (tbl != null) { //50582
                tbl.selectAll(); //50582
            } //50582
        } else if (_action.equals("iSel")) { //50582
            NavTable tbl = getNavTable(); //50582
            if (tbl != null) { //50582
                tbl.invertSelection(); //50582
            } //50582
        } else if (_action.equals("f/r")) { //50582
            NavTable tbl = getNavTable(); //50582
            if (tbl != null) { //50582
                tbl.showFind(id); //50582
            } //50582
        } else if (_action.equals("srt")) { //50582
            NavTable tbl = getNavTable(); //50582
            if (tbl != null) { //50582
                tbl.showSort(id); //50582
            } //50582
        } else if (_action.equals("fltr")) { //50582
            NavTable tbl = getNavTable(); //50582
            if (tbl != null) { //50582
                tbl.showFilter(id); //50582
            } //50582
        } //50582
        setModalBusy(false);
    }

    private void prev() {
        setView(--curIndex);
        adjustButtons(curIndex);
    }

    private void next() {
        setView(++curIndex);
        adjustButtons(curIndex);
        return;
    }

    private void finish(int _copies) {
        if (ai instanceof LinkActionItem) {
            LinkActionItem link = (LinkActionItem) ai;
            Object[] o1 = null;
            Object[] o2 = null;
            int iLinkType = -1;
            try {
                o1 = ((NavTable) view[0]).getSelectedObjects(false, true);
                o2 = ((NavTable) view[1]).getSelectedObjects(false, true);
            } catch (OutOfRangeException _range) {
                _range.printStackTrace();
                setMessage(_range.toString());
                showError();
                return;
            }
            iLinkType = getLinkType();
            link.setLinkType(iLinkType);
            if (iLinkType == LinkActionItem.TYPE_COPY) {
                if (_copies <= 0) {
                    return;
                }
                link.setCopyCount(_copies);
            }
            if (o1 instanceof EntityItem[] && o2 instanceof EntityItem[]) {
                EntityItem[] eiParent = (EntityItem[]) o1; //22695
                EntityItem[] eiChild = (EntityItem[]) o2; //22695
                MetaLink mLink = null; //51492
                Object o = null; //CR0618043756
                String out = null; //CR0618043756
                setOptions(link, nav.getCart().getLinkType()); //acl_20021024
                mLink = link.getMetaLink(); //51492
                if (mLink == null) { //51492
                    return; //51492
                } //51492
                //CR0618043756				String out = reportLink(eiParent,eiChild,mLink);						//51492

                if (link.isOppSelect()) { //CR0618043756
                    out = reportLink(eiChild, eiParent, mLink); //CR0618043756
                } else { //CR0618043756
                    out = reportLink(eiParent, eiChild, mLink); //CR0618043756
                } //CR0618043756
                if (link.isOppSelect()) { //CR0618043756
                    o = dBase().rexec(link, eiChild, eiParent, this); //CR0618043756
                } else { //CR0618043756
                    o = dBase().rexec(link, eiParent, eiChild, this); //CR0618043756
                } //CR0618043756
                if (o != null) { //cr_ActChain
                    if (o instanceof Boolean) { //cr_ActChain
                        if (!((Boolean) o).booleanValue()) { //cr_ActChain
                            return; //cr_ActChain
                        } //cr_ActChain
                    } //cr_ActChain
                } else { //cr_ActChain
                    return; //cr_ActChain
                } //cr_ActChain

                //50445				showLinkDialog(getString("msg3011.0"),out);					//22695
                setModalBusy(false); //50817
                if (isShowing()) { //auto_link
                	eaccess().showLinkDialog((Window) id, getString("msg3011.0"), out); //50445
                } else { //auto_link
                	eaccess().showLinkDialog(null, getString("msg3011.0"), out); //auto_link
                } //auto_link
                eaccess().shouldRefresh(nav.getENavForm()); //22695

                if (link.hasChainAction()) { //cr_ActChain
                    setModalBusy(true);
                    if (!link.requireInput()) { //cr_ActChain
                        if (link.isChainEditAction()) { //cr_ActChain
                            eaccess().load(nav.getENavForm(), o, "edit.gif"); //cr_ActChain
                        } else if (link.isChainWhereUsedAction()) { //cr_ActChain
                            eaccess().load(nav.getENavForm(), o, "used.gif"); //cr_ActChain
                        } else if (link.isChainMatrixAction()) { //cr_ActChain
                            eaccess().load(nav.getENavForm(), o, "mtrx.gif"); //cr_ActChain
                        } //cr_ActChain
                    } //cr_ActChain
                    setModalBusy(false); //cr_ActChain
                } //cr_ActChain
                if (isShowing()) { //auto_link
                    cancel();
                } //auto_link
            } else { //52143
                showError("msg12010.0"); //52143
            }
        }
    }

    private int getCopies() {
        return eaccess().getNumber((Window) id, "msg3013");
    }

    private void setOptions(LinkActionItem _lai, String _s) { //acl_20021024
        if (_lai != null) { //acl_20021024
            if (_s.equals("NODUPES")) { //acl_20021024
                _lai.setOption(LinkActionItem.OPT_NODUPES); //acl_20021024
            } else if (_s.equals("REPLACEALL")) { //acl_20021024
                _lai.setOption(LinkActionItem.OPT_REPLACEALL); //acl_20021024
            } else { //acl_20021024
                _lai.setOption(LinkActionItem.OPT_DEFAULT); //acl_20021024
            } //acl_20021024
        } //acl_20021024
    } //acl_20021024

    private void cancel() {
        disposeDialog();
        view = null;
        ai = null;
        nav = null;
        msgPrefix = null;
    }

    /**
     * process
     * @param _ai
     * @param _ei
     * @param _nav
     * @author Anthony C. Liberto
     */
    public void process(EANActionItem _ai, EntityItem[] _ei, Navigate _nav) {
        EntityGroup eg1 = null;
        EntityGroup eg2 = null;
        EntityItem[] ei2 = _ei;
        String strEntity1 = null; //NMHR-67QQZW
        String strEntity2 = null; //NMHR-67QQZW
        setActionItem(_ai);
        setNavigate(_nav);
        if (_ai instanceof LinkActionItem) {
            LinkActionItem link = (LinkActionItem) _ai;
            MetaLink mLink = link.getMetaLink();

			if (mLink == null) {										//vs_20060515
			    setCode("msg11007.1");									//vs_20060515
				setParm(link.getKey());									//vs_20060515
				showError();											//vs_20060515
				return;													//vs_20060515
			}															//vs_20060515

            eg1 = getParentEntityGroup(link, mLink, _nav);

            if (eg1 == null) { //52871
                showError("msg11007.6"); //52871
                return; //52871
            } //52871

            eg2 = getChildEntityGroup(link, mLink, _nav);
            if (eg2 == null) {
                return;
            }
            if (link.useCart() && _nav.isMultipleNavigate()) {
                ei2 = getOpposingEntityItems(_nav);
            }
            link.setCheckExistence(true); //50591
            if (link.isOppSelect()) { //CR0618043756
                setMessagePrefix("msg11007wp."); //CR0618043756
                strEntity1 = mLink.getEntity2Type(); //NMHR-67QQZW
                strEntity2 = mLink.getEntity1Type(); //NMHR-67QQZW
            } else { //CR0618043756
                setMessagePrefix("msg11007w.");
                strEntity1 = mLink.getEntity1Type(); //NMHR-67QQZW
                strEntity2 = mLink.getEntity2Type(); //NMHR-67QQZW
            } //CR0618043756

            if (link.canLink() && link.canLinkCopy()) { //cc_20030303
                view = new Component[3]; //cc_20030303
            } else { //cc_20030303
                view = new Component[2];
            } //cc_20030303
            view[0] = new NavTable(this, eg1.getEntityGroupTable()) {
            	private static final long serialVersionUID = 1L;
            	public void setFilter(boolean _b) {
                    lblFilter.setEnabled(_b);
                }
            };
            System.out.println("highlighting view0");
            //NMHR-67QQZW			((NavTable)view[0]).childHighlight(_ei);
            ((NavTable) view[0]).highlight(_ei, strEntity1); //NMHR-67QQZW
            //			((NavTable)view[0]).setModalCursor(true);
            view[1] = new NavTable(this, eg2.getEntityGroupTable()) {
            	private static final long serialVersionUID = 1L;
            	public void setFilter(boolean _b) {
                    lblFilter.setEnabled(_b);
                }
            };
            if (ei2 != null) {
                System.out.println("highlighting view1");
                //NMHR-67QQZW				((NavTable)view[1]).childHighlight(ei2);			//22810
                ((NavTable) view[1]).highlight(ei2, strEntity2); //NMHR-67QQZW
            }
            //			((NavTable)view[1]).setModalCursor(true);
            if (view.length == 3) { //cc_20030303
                view[2] = new LChooser(); //cc_20030303
            } //cc_20030303
            //22703 -- GOES HERE
            if (link.useParents()) { //22703
                setView(0);
                adjustButtons(0);
            } else { //22703
                setView(1); //22703
                adjustButtons(1); //22703
            } //22703
            setBusy(false);
            setWorker(null);

            if (isAutoLink()) { //auto_link
                if (link.isOppSelect()) { //auto_link
                    if (shouldAutoLink(ei2, _ei)) { //auto_link
                        if (link.useCart()) { //TIR USRO-R-BTES-664TH5
                            ((NavTable) view[0]).selectAll(); 		//MN20805300
						} else if (link.useParents()) {				//MN25719436
							((NavTable) view[1]).selectAll();		//MN25719436
                        } //TIR USRO-R-BTES-664TH5
                        if (isLinkCopy()) { //auto_link
                            finish(getCopies()); //auto_link
                        } else { //auto_link
                            finish(0); //auto_link
                        } //auto_link
                        return; //auto_link
                    } //auto_link
                } else { //auto_link
                    if (shouldAutoLink(_ei, ei2)) { //auto_link
                        if (link.useCart()) { //TIR USRO-R-BTES-664TH5
                            ((NavTable) view[1]).selectAll(); //MN20805300
						} else if (link.useParents()) {				//MN25719436
							((NavTable) view[0]).selectAll();		//MN25719436
                        } //TIR USRO-R-BTES-664TH5
                        if (isLinkCopy()) { //auto_link
                            finish(getCopies()); //auto_link
                        } else { //auto_link
                            finish(0); //auto_link
                        } //auto_link
                        return; //auto_link
                    } //auto_link
                } //auto_link
            } //auto_link
            show(this, this, false);
        }
    }

    /*
     for log parsing.

     */
    /**
     * process
     * @param _ai
     * @param _parent
     * @param _children
     * @param _nav
     * @author Anthony C. Liberto
     */
    public void process(EANActionItem _ai, String[] _parent, String[] _children, Navigate _nav) {
        EntityGroup eg1 = null;
        EntityGroup eg2 = null;
        setActionItem(_ai);
        setNavigate(_nav);
        if (_ai instanceof LinkActionItem) {
            LinkActionItem link = (LinkActionItem) _ai;
            MetaLink mLink = link.getMetaLink();
            eg1 = getParentEntityGroup(link, mLink, _nav);
            if (mLink == null) {
                showError("msg11007.1");
                return;
            }
            eg2 = getChildEntityGroup(link, mLink, _nav);
            if (eg2 == null) {
                return;
            }
            link.setCheckExistence(true); //50591
            setMessagePrefix("msg11007w.");
            if (link.canLink() && link.canLinkCopy()) { //cc_20030303
                view = new Component[3]; //cc_20030303
            } else { //cc_20030303
                view = new Component[2];
            } //cc_20030303
            view[0] = new NavTable(this, eg1.getEntityGroupTable()) {
            	private static final long serialVersionUID = 1L;
            	public void setFilter(boolean _b) {
                    lblFilter.setEnabled(_b);
                }
            };
            ((NavTable) view[0]).childHighlight(_parent);
            view[1] = new NavTable(this, eg2.getEntityGroupTable()) {
            	private static final long serialVersionUID = 1L;
            	public void setFilter(boolean _b) {
                    lblFilter.setEnabled(_b);
                }
            };
            ((NavTable) view[1]).childHighlight(_children); //22810
            if (view.length == 3) { //cc_20030303
                view[2] = new LChooser(); //cc_20030303
            } //cc_20030303
            if (link.useParents()) { //22703
                setView(0);
                adjustButtons(0);
            } else { //22703
                setView(1); //22703
                adjustButtons(1); //22703
            } //22703
            setBusy(false);
            setWorker(null);
            show(this, this, false);
        }
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        btnPrev.removeActionListener(this);
        btnNext.removeActionListener(this);
        btnFinish.removeActionListener(this);
        btnCancel.removeActionListener(this);

        if (scroll != null) {
            scroll.dereference();
            scroll = null;
        }

        pNorth.removeAll();
        pBtn.removeAll();

        view = null;
        ai = null;
        nav = null;
        msgPrefix = null;

        removeAll();
        removeNotify();
    }

    private String reportLink(EntityItem[] _eiParent, EntityItem[] _eiChild, MetaLink _rel) { //22695
        StringBuffer sb = null;
        String msg = null;
        int pp = -1;
        int cc = -1;
        if (_eiParent == null || _eiChild == null) { //22695
            return null;
        } //22695
        sb = new StringBuffer(); //22695
        msg = " " + getString("l2") + " "; //22695
        pp = _eiParent.length; //22695
        cc = _eiChild.length; //22695
        for (int p = 0; p < pp; ++p) { //22695
            for (int c = 0; c < cc; ++c) { //22695
                sb.append(getParentString(_eiParent[p], _rel) + msg + _eiChild[c].toString() + RETURN); //22695
                appendLog("       to "+_eiChild[c].getKey());
            } //22695
        } //22695
        return sb.toString(); //22695
    } //22695

    private String getParentString(EntityItem _ei, MetaLink _rel) { //22695
        String e1Type = null;
        EANEntity ent = null;
        if (_rel == null) { //22695
        	appendLog("    Linking "+_ei.getKey());
            return _ei.toString();
        } //22695
        e1Type = _rel.getEntity1Type(); //22695
        if (e1Type.equals(_ei.getEntityType())) { //22695
        	appendLog("    Linking "+_ei.getKey());
            return _ei.toString();
        } //22695
        ent = _ei.getDownLink(0); //22695
        if (ent != null && e1Type.equals(ent.getEntityType())) { //22695
        	appendLog("    Linking "+ent.getKey());
            return ent.toString();
        } //22695
        appendLog("    Linking "+_ei.getKey());
        return _ei.toString(); //22695
    } //22695

    /**
     * setTitle
     *
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {
    }
    /**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
        return getString("wizard.panel");
    }

    private class LChooser extends EPanel { //cc_20030303
    	private static final long serialVersionUID = 1L;
    	private EComboBox eBox = new EComboBox(); //cc_20030303
        private ELabel eLbl = new ELabel(getString("chooseLink")); //cc_20030303
        private int[] out = new int[2]; //cc_20030303
        private String[] str = { "link", "copylink" }; //cc_20030303
        //cc_20030303
        /**
         * lChooser
         * @author Anthony C. Liberto
         */
        private LChooser() { //cc_20030303
            super(); //cc_20030303
            init(); //cc_20030303
        } //cc_20030303
        //cc_20030303
        private void init() { //cc_20030303
            setModalCursor(true); //cc_20030303
            loadChooser(); //cc_20030303
            add(eLbl); //cc_20030303
            add(eBox); //cc_20030303
        } //cc_20030303
        //cc_20030303
        private void loadChooser() { //cc_20030303
            out[0] = LinkActionItem.TYPE_DEFAULT; //cc_20030303
            out[1] = LinkActionItem.TYPE_COPY; //cc_20030303
            for (int i = 0; i < out.length; ++i) { //cc_20030303
                eBox.addItem(getString(str[i])); //cc_20030303
            } //cc_20030303
        } //cc_20030303
        //cc_20030303
        /**
         * getLinkType
         * @return
         * @author Anthony C. Liberto
         */
        private int getLinkType() { //cc_20030303
            int indx = eBox.getSelectedIndex(); //cc_20030303
            if (indx >= 0 && indx < out.length) { //cc_20030303
                return out[indx]; //cc_20030303
            } //cc_20030303
            return indx; //cc_20030303
        } //cc_20030303
    } //cc_20030303

    /*
     50582
     */

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
        super.createMenu();
        menubar.addMenu("Edit", "selA", this, KeyEvent.VK_A, Event.CTRL_MASK, true);
        menubar.addMenu("Edit", "iSel", this, KeyEvent.VK_I, Event.CTRL_MASK, true);
        menubar.addSeparator("Edit");
        menubar.addMenu("Edit", "f/r", this, KeyEvent.VK_F, Event.CTRL_MASK, true);
        menubar.setMenuMnemonic("Edit", 'E');

        menubar.addMenu("Table", "srt", this, 0, 0, true);
        //cr_0805036452		menubar.addMenu("Table","fltr",this, KeyEvent.VK_F8, Event.CTRL_MASK, true);
        menubar.addMenu("Table", "fltr", this, KeyEvent.VK_F8, 0, true); //cr_0805036452
        menubar.setMenuMnemonic("Table", 'T');
    }

    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
        menubar.removeMenuItem("selA", this);
        menubar.removeMenuItem("iSel", this);
        menubar.removeMenuItem("f/r", this);

        menubar.removeMenuItem("srt", this);
        menubar.removeMenuItem("fltr", this);
        super.removeMenu();
    }

    private void setNavTableMenuEnabled(boolean _b) {
        menubar.setEnabled("selA", _b);
        menubar.setEnabled("iSel", _b);
        menubar.setEnabled("f/r", _b);
        menubar.setEnabled("srt", _b);
        menubar.setEnabled("fltr", _b);
    }

    /**
     * getComponent
     * @return
     * @author Anthony C. Liberto
     */
    private Component getComponent() {
        JViewport viewport = scroll.getViewport();
        if (viewport != null) {
            return viewport.getView();
        }
        return null;
    }

    private NavTable getNavTable() {
        Component c = getComponent();
        if (c != null && c instanceof NavTable) {
            return (NavTable) c;
        }
        return null;
    }

    /**
     * isFiltered
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isFiltered() {
        NavTable nt = getNavTable();
        if (nt != null) {
            return nt.isFiltered();
        }
        return false;
    }

    /**
     * getLinkType
     * @return
     * @author Anthony C. Liberto
     */
    private int getLinkType() {
        if (ai instanceof LinkActionItem) {
            LinkActionItem link = (LinkActionItem) ai;
            if (view.length == 3) {
                return ((LChooser) view[2]).getLinkType();
            } else {
                if (link.canLink()) {
                    return LinkActionItem.TYPE_DEFAULT;
                } else {
                    return LinkActionItem.TYPE_COPY;
                }
            }
        }
        return -1;
    }

    /**
     * isLinkCopy
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isLinkCopy() {
        int iLinkType = getLinkType();
        return iLinkType == LinkActionItem.TYPE_COPY;
    }

    /*
     50924
     */
    /**
     * getSearchableObject
     * @author Anthony C. Liberto
     * @return Object
     */
    public Object getSearchableObject() {
        return getNavTable();
    }

    /*
     1.2h
    */
    private EntityGroup getParentEntityGroup(LinkActionItem _link, MetaLink _mLink, Navigate _nav) {
        if (_link.useParents()) {
            return _nav.getParentEntityGroup();
        } else if (_link.isOppSelect()) { //CR0618043756
            return _nav.getEntityGroup(_mLink.getEntity2Type()); //CR0618043756
        } else {
            return _nav.getEntityGroup(_mLink.getEntity1Type());
        }
    }

    private EntityGroup getChildEntityGroup(LinkActionItem _link, MetaLink _mLink, Navigate _nav) {
        if (_link.useCart()) {
            if (_nav.isMultipleNavigate()) {
                if (_link.isOppSelect()) { //CR0618043756
                    return getOpposingParentEntityGroup(_mLink, _nav); //CR0618043756
                } else { //CR0618043756
                    return getOpposingChildEntityGroup(_mLink, _nav);
                } //CR0618043756
            } else {
                if (_link.isOppSelect()) { //CR0618043756
                    return getParentEntityGroupFromCart(_mLink, _nav, "msg11007.3"); //CR0618043756
                } else { //CR0618043756
                    return getChildEntityGroupFromCart(_mLink, _nav, "msg11007.3");
                } //CR0618043756
            }
        } else {
            if (_link.isOppSelect()) { //CR0618043756
                return getParentEntityGroupFromNavigate(_mLink, _nav); //CR0618043756
            } else { //CR0618043756
                return getChildEntityGroupFromNavigate(_mLink, _nav);
            } //CR0618043756
        }
    }

    private EntityItem[] getOpposingEntityItems(Navigate _nav) {
        EntityItem[] ei = null;
        Navigate nav2 = _nav.getOpposingNavigate();
        if (nav2 != null) {
            try {
                ei = nav2.getSelectedEntityItems(false, false);
            } catch (OutOfRangeException _range) {
                EAccess.report(_range,false);
            }
        }
        return ei;
    }

    private EntityGroup getOpposingChildEntityGroup(MetaLink _mLink, Navigate _nav) {
        Navigate nav2 = _nav.getOpposingNavigate();
        EntityGroup eg = null;
        if (nav2 == null) {
            return getChildEntityGroupFromCart(_mLink, _nav, "msg11007.3");
        }
        eg = nav2.getEntityGroup(_mLink.getEntity2Type());
        //acl_20040106		if (eg == null) {
        //acl_20040106			setCode("msg11007.5");
        //acl_20040106			setParm(_mLink.getEntity2Description());
        //acl_20040106			showError();
        //acl_20040106		} else if (eg.getEntityItemCount() == 0) {
        if (eg == null || eg.getEntityItemCount() == 0) { //acl_20040106
            return getChildEntityGroupFromCart(_mLink, _nav, "msg11007.5");
        }
        return eg;
    }

    private EntityGroup getChildEntityGroupFromNavigate(MetaLink _mLink, Navigate _nav) {
        EntityGroup eg = _nav.getEntityGroup(_mLink.getEntity2Type());
        if (eg == null) {
            String[] tmp = new String[2];
            setCode("msg11007.4c");
            tmp[0] = _mLink.getEntity2Type();
            tmp[1] = _mLink.getEntity2Description();
            eaccess().setParms(tmp);
            showError();
        }
        return eg;
    }

    private EntityGroup getChildEntityGroupFromCart(MetaLink _mLink, Navigate _nav, String _msgCode) {
    	NavCartDialog cart = _nav.getCart();
        EntityGroup eg = null;
        if (cart == null) {
            return null;
        }
        cart.refresh();					//23199651
        eg = cart.getEntityGroup(_mLink.getEntity2Type());
        if (eg == null) {
            setCode(_msgCode);
            setParm(_mLink.getEntity2Description());
            showError();
        }
        return eg;
    }
    /*
     CR0618043756
     */
    private EntityGroup getParentEntityGroupFromNavigate(MetaLink _mLink, Navigate _nav) {
        EntityGroup eg = _nav.getEntityGroup(_mLink.getEntity1Type());
        if (eg == null) {
            String[] tmp = new String[2];
            setCode("msg11007.4p");
			tmp[0] = _mLink.getEntity1Type();
			tmp[1] = _mLink.getEntity1Description();
			eaccess().setParms(tmp);
            showError();
        }
        return eg;
    }

    private EntityGroup getParentEntityGroupFromCart(MetaLink _mLink, Navigate _nav, String _msgCode) {
    	NavCartDialog cart = _nav.getCart();
        String e1 = null;
        EntityGroup eg = null;
        String desc = null;
        if (cart == null) {
            return null;
        }
        cart.refresh();					//23199651
        e1 = _mLink.getEntity1Type();
        eg = cart.getEntityGroup(e1);
        desc = _mLink.getEntity1Description();
        if (eg == null) {
            setCode(_msgCode);
            setParm((desc == null) ? e1 : desc);
            showError();
        }
        return eg;
    }

    private EntityGroup getOpposingParentEntityGroup(MetaLink _mLink, Navigate _nav) {
        Navigate nav2 = _nav.getOpposingNavigate();
        EntityGroup eg = null;
        if (nav2 == null) {
            return getParentEntityGroupFromCart(_mLink, _nav, "msg11007.3");
        }
        eg = nav2.getEntityGroup(_mLink.getEntity1Type());
        if (eg == null || eg.getEntityItemCount() == 0) {
            return getParentEntityGroupFromCart(_mLink, _nav, "msg11007.5");
        }
        return eg;
    }

    /*
     auto_link
     */
    /**
     * isAutoLink
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isAutoLink() {
        boolean b = EAccess.isArmed(AUTOLINK_ARM_FILE); //MN20805300
        appendLog("autoLinks: " + b);
        return b;
    }

    /**
     * shouldAutoLink
     * @param _parent
     * @param _child
     * @return
     * @author Anthony C. Liberto
     */
    private boolean shouldAutoLink(EntityItem[] _parent, EntityItem[] _child) {
        if (_parent != null && _child != null) {
            return (_parent.length == 1);
        }
        return false;
    }
}
