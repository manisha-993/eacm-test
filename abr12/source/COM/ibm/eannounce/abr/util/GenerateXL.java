//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//$Log: GenerateXL.java,v $
//Revision 1.46  2007/02/09 21:25:15  yang
//more changes
//
//Revision 1.44  2006/11/30 18:39:59  yang
//minor changes
//
//Revision 1.21  2005/12/01 17:59:55  yang
//more logs
//
//Revision 1.20  2005/12/01 17:34:13  yang
//minor fixes
//
//Revision 1.19  2005/11/17 21:24:20  yang
//removed the fonts
//
//Revision 1.18  2005/10/27 20:54:34  yang
//changed attributes based on wayne
//
//Revision 1.17  2005/10/26 20:32:08  yang
//minor fixes
//
//Revision 1.15  2005/09/28 22:43:34  yang
//adding wayne's updated changes
//
//Revision 1.14  2005/09/12 18:51:35  yang
//fixes
//
//Revision 1.13  2005/09/12 15:38:43  yang
//adding color to the column
//
//Revision 1.12  2005/09/12 15:36:38  yang
//removing logs
//
//Revision 1.11  2005/09/12 15:34:53  yang
//minor changes
//
//Revision 1.10  2005/09/08 22:48:27  joan
//fixes
//
//Revision 1.9  2005/09/08 22:31:27  yang
//adding eiParent
//
//Revision 1.8  2005/09/07 22:10:36  joan
//fixes
//
//Revision 1.7  2005/09/07 17:51:25  yang
//removing logs
//
//Revision 1.6  2005/09/07 17:49:22  yang
//minor changes
//
//Revision 1.5  2005/09/07 16:35:59  yang
//syntax
//
//Revision 1.4  2005/09/06 17:23:45  yang
//Adding toString()
//
//Revision 1.3  2005/09/01 20:49:23  yang
//initial load
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.abr.util.PokBaseABR.*;
import java.util.Comparator;
import java.util.*;
import java.io.*;
import java.sql.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ddf.*;
import org.apache.poi.dev.*;
import org.apache.poi.hpsf.*;
import org.apache.poi.util.*;
import org.apache.poi.hssf.dev.*;
import org.apache.poi.hssf.eventmodel.*;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.poifs.common.*;
import org.apache.poi.poifs.dev.*;
import org.apache.poi.poifs.eventfilesystem.*;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.poifs.property.*;
import org.apache.poi.poifs.storage.*;

public class GenerateXL {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    String _strFlag = null;
    FileOutputStream out = null;
    EntityItem _ei = null;
    StringBuffer m_sb = new StringBuffer();
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFCellStyle wbcs = wb.createCellStyle();
    HSSFDataFormat wbdf = wb.createDataFormat();

    public GenerateXL(EntityList elist, EntityItem eiParent, String Filename, Database m_db, Profile m_prof, String SearchAction, String strRPTEXCELVE, boolean checkRPTEXCEL) throws SQLException, MiddlewareException, MiddlewareRequestException {

        if (checkRPTEXCEL) {
        System.out.println("-------------Starting generate XL----------------");
        SearchActionItem sai = new SearchActionItem(null, m_db, m_prof, SearchAction);
        EntityList list = new EntityList(m_db, m_prof, sai);
        EntityGroup eg = list.getEntityGroup("RPTEXCEL");

        if(eg != null) {
        for( int i = 0; i < eg.getEntityItemCount(); i++ ) {
            EntityItem ei = eg.getEntityItem(i);

                EANAttribute[] atts = new EANAttribute[4];
                atts[0] = ei.getAttribute("NAME");
                atts[1] = ei.getAttribute("RPTEXCELLAYOUT");
                atts[2] = ei.getAttribute("PDHDOMAIN");
                atts[3] = ei.getAttribute("RPTEXCELVE");


                String VeName = null;

                if (atts[3] == null) {
                    VeName = " ";
                } else {
                    VeName = atts[3].toString();
                    System.out.println("We using this VE to build the XL" + VeName + ": compared to MODEL's VE" + strRPTEXCELVE);
                }

                if (VeName.equals(strRPTEXCELVE)){


                System.out.println ("***RPTEXCEL: "
                + ei.getEntityType()
                + ei.getEntityID()
                + " atts[0]: "
                + atts[0]
                + " atts[1]: "
                + atts[1]);

                String strValue = null;
                if (atts[1] == null) {
                    strValue = " ";
                } else {
                    strValue = atts[1].toString();
                    System.out.println("**strValue from RPTEXCEL" + strValue);

                    StringTokenizer st = new StringTokenizer(strValue, "|");

                    int ss = st.countTokens();
                    int s = 0;

                    String[] strFlagCode = new String[ss];
                        System.out.println("ss: " + ss);

                    while (st.hasMoreTokens()) {
                        strFlagCode[s] = st.nextToken();
                        _strFlag = strFlagCode[s].toString();
                        System.out.println("**_strFlag: " + _strFlag);
                        ++s;
                    }

                System.out.println ("***we're using eiParent to build entitylist" + eiParent.getEntityType() + ": " + eiParent.getEntityID());
                EntityItem[] aei = new EntityItem[] { eiParent };

                ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, VeName);
                EntityList el = new EntityList(m_db, m_prof, eaItem, aei, eiParent.getEntityType());

                System.out.println ("********processEntity*************");
                processEntity(el, strFlagCode, eiParent, Filename, m_db, m_prof);
                System.out.println ("********End of processEntity*************");


                        }
            } else {
                System.out.println("do nothing with this RPTEXCEL" + ei.getEntityType() + ei.getEntityID());
            }
        }
    } else {
        System.out.println("eg for XL entityis empty");
    }
} else {

        //this is just for pulling all entitygroups from elist
        if (elist !=  null) {


        System.out.println("-------------Starting XL dump--------------");
            // create a new sheet
            HSSFSheet wbs = wb.createSheet(Filename);
               System.out.println("***Filename: " + Filename);
            // declare a row object reference
            HSSFRow wbRow = null;
            // declare a cell object reference
            HSSFCell wbCell = null;

            wbcs.setDataFormat(wbdf.getFormat("###,###,###,##0.0"));

            wb = new HSSFWorkbook();

            int count = 0;

            for (int ii = 0; ii < elist.getEntityGroupCount(); ii++) {
                EntityGroup _eg = elist.getEntityGroup(ii);



                //now lets do the rest of the entities within the VE
                if (_eg.isRelator() || _eg.isAssoc()) {
                } else {

                        String tabName = _eg.getLongDescription();
                        tabName = tabName.replace('/', ' ');
                        String ei_Parent = eiParent.getEntityType();
                        System.out.println("Working with _eg: " + ii + ": " + _eg.getLongDescription() + "count: " + count + ei_Parent + "tabName" + tabName); //This is the name of the tab
                        wbs = wb.createSheet(Filename);
                        wb.setSheetName(count, tabName);
                        count++;

                //need to check the root entity
                if (_eg.getEntityType().equals(ei_Parent)) {
                _eg = elist.getParentEntityGroup();
                }




                if (_eg != null) {
                    String strEntityType = _eg.getEntityType();
                    _eg = new EntityGroup(null, m_db, m_prof, strEntityType, "Edit", true);
                    MetaColumnOrderGroup mcog = _eg.getMetaColumnOrderGroup();
                    System.out.println("1strEntityType" + strEntityType + "_eg: " + _eg);

                for (short k = 0; k < mcog.getMetaColumnOrderItemCount(); k++) {
                    MetaColumnOrderItem mcoi = mcog.getMetaColumnOrderItem(k);
                    String strAttrCode = mcoi.getAttributeCode();
                    System.out.println("1strAttrCode" + strAttrCode);

                for( int i = 0; i < _eg.getEntityItemCount(); i++ ) {
                    _ei = _eg.getEntityItem(i);
                        System.out.println("1***_ei: " + _ei.getEntityType() + _ei.getEntityID() + ": " + _eg.getEntityItemCount() + "- " + i);

                         if(_ei != null) {

                            System.out.println("***mcog:***" + mcog + "*** " + _ei.getEntityType() + " entityid: " + _ei.getEntityID() + ": mcoi.getAttributeCode(): " + mcoi.getAttributeCode() + "k: " + k);
                           EANAttribute att = _ei.getAttribute(strAttrCode);

                           String strAttributeDesc = mcoi.getAttributeDescription();

                            if (strAttributeDesc.length() > 0 && strAttributeDesc.charAt(0) == '*') {
                                        strAttributeDesc = strAttributeDesc.substring(1);
                                        System.out.println("strAttributeDesc" + strAttributeDesc);
                            }

                            short iCellNum = 0;
                                System.out.println("iCellNum: " + iCellNum + "k" + k + _ei.getEntityType() + _ei.getEntityID());
                            wbRow = wbs.createRow(iCellNum);
                            wbs.setColumnWidth(k,(short) ((50 * 8) / ((double) 1 / 20)));
                            wbCell = wbRow.createCell(k);
                            wbCell.setCellValue(strAttributeDesc);
                            //wbCell.setCellStyle(style);
                            iCellNum++;


                            //Now write the summary rows
                            String strValue = null;
                            if (att == null) {
                                strValue = " ";
                            } else {
                                strValue = att.toString();

                            if (strValue.length() > 0 && strValue.charAt(0) == '*') {
                                        strValue = strValue.substring(1);
                               }
                            }

                            int iRow = (i + 1);


                                System.out.println("strValue: " + _ei.getEntityType() + _ei.getEntityID() + strValue + "iRow" + iRow);
                            wbRow = wbs.createRow(iRow);
                            wbs.setColumnWidth(k,(short) ((50 * 8) / ((double) 1 / 20)));
                            wbCell = wbRow.createCell(k);
                            wbCell.setCellValue(strValue);
                            iRow++;
                               }else {
                                System.out.println("EntityItem is empty");
                               }

                             }
                         }
                     }
                 }
             }
                 try {
                     String strFilename = Filename + ".xls";
                         File xlFile = new File(strFilename);
                         out = new FileOutputStream(xlFile);
                         wb.write(out);
                         out.close();
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
             }
         }
     }


        private void processEntity(EntityList el, String[] _s, EntityItem eiParent, String Filename, Database m_db, Profile m_prof) {

            if (_s != null) {


            // create a new sheet
            HSSFSheet wbs = wb.createSheet(Filename);
               System.out.println("***Filename: " + Filename);
            // declare a row object reference
            HSSFRow wbRow = null;
            // declare a cell object reference
            HSSFCell wbCell = null;
            wbcs.setDataFormat(wbdf.getFormat("###,###,###,##0.0"));

            wb = new HSSFWorkbook();

            //Count is for the location of the tab
            EntityGroup eg = null;
            EntityItem _releitem = null;
            EntityItem _eitem = null;
            EntityItem _ei = null;
            MetaColumnOrderGroup mcog = null;
            String sRel = null;
            String sEg1 = null;
            String _sfindRel = null;
            String sAtt1 = null;
            String sEg = null;
            String sAtt = null;
            int Count = 0;
            short pp = 0;

                int ii = _s.length;
                System.out.println("ii: " + ii);
                for (int i=0; i<ii; i++) {
                    int indx = _s[i].indexOf(".");
                    sEg = _s[i].substring(0,indx);
                    boolean _ifRelator = stringCheck(sEg);
                        System.out.println("***sEg: " + sEg + "sEG's _ifRelator: " + _ifRelator);
                    if (!_ifRelator) {
                    eg = el.getEntityGroup(sEg);
                        System.out.println("***eg: " + eg.getEntityType() + ": " + eg);
                    } else {

                    int ee = sEg.length();
                    for (int e=0; e<ee; e++) {
                        int index = sEg.indexOf(":");
                        String _Egrp = sEg.substring(0,index);
                                eg = el.getEntityGroup(_Egrp);
                                System.out.println("***eg: " + eg.getEntityType() + ": " + eg + "_Egrp" + _Egrp);
                                _sfindRel = sEg.substring(index+1);
                                System.out.println("***_sfindRel: " + _sfindRel);
                        }
                    for( int w = 0; w < eg.getEntityItemCount(); w++ ) {
                        _releitem = eg.getEntityItem(w);
                            System.out.println("***_releitem: " + _releitem.getEntityType() + _releitem.getEntityID());


                    if (_releitem !=null) {
                    StringTokenizer _st = new StringTokenizer(_sfindRel,":");

                    int uu = _st.countTokens();
                    int u = 0;

                    String[] _sEg = new String[uu];
                            System.out.println("uu: " + uu + " _sfindRel: " + _sfindRel);


                            if (uu < 1) {
                                        System.out.println("*** No relators: ");
                            }
                            if (uu == 1) {
                                        System.out.println("*** with uu less than 2: "
                                                            + eg
                                                            + "_releitem"
                                                            + _releitem.getEntityType()
                                                            + _releitem.getEntityID()
                                                            + "***_sfindRel"
                                                            + _sfindRel);
                                   _eitem = findLink(_releitem, _sfindRel);
                                   if (_eitem != null) {
                                   eg = el.getEntityGroup(_eitem.getEntityType());
                               }
                            }

                            if (uu > 1 && (_sEg !=null)) {
                            while (_st.hasMoreTokens()) {
                                _sEg[u] = _st.nextToken();
                                System.out.println("***_sEg: " + _sEg[u] + "_sEg[0]: " + _sEg[0] + "_sEg[1]" + _sEg[1] + "_sEg[uu-1]" + _sEg[(uu-1)]);
                                ++u;
                            }

                            if ((uu == 2) && (_sEg[(uu-1)] != null)) {
                                        //FEATUREHDD_sEg[1]HDD_sEg[uu-1]HDD
                                        System.out.println("*** with uu == 2: "
                                                            + eg
                                                            + "_releitem"
                                                            + _releitem.getEntityType()
                                                            + _releitem.getEntityID()
                                                            + "_sEg[0]"
                                                            + _sEg[0]
                                                            + "_sEg[1]"
                                                            + _sEg[1]);
                                   EntityItem _eitem1 = findLink3(_releitem, _sEg[0]);
                                   if (_eitem1 !=null) {
                                   _eitem = findLink(_eitem1, _sEg[1]);
                                   if (_eitem !=null) {
                                   eg = el.getEntityGroup(_eitem.getEntityType());
                                   }
                               }
                            } else if (uu == 3 && (_sEg[(uu-1)] != null)) {
                                        System.out.println("*** with uu == 3: "
                                                            + eg
                                                            + "_releitem"
                                                            + _releitem.getEntityType()
                                                            + _releitem.getEntityID()
                                                            + "***_sfindRel"
                                                            + _sfindRel);
                                   EntityItem _eitem1 = findLink3(_releitem, _sEg[0]);
                                   if (_eitem1 !=null) {
                                   EntityItem _eitem2 = findLink3(_eitem1, _sEg[1]);
                                   if (_eitem2 !=null) {
                                   _eitem = findLink(_eitem2, _sEg[(uu-1)]);
                                   if (_eitem !=null) {
                                   eg = el.getEntityGroup(_eitem.getEntityType());
                                       }
                                   }
                               }
                            } else if (uu == 4 && (_sEg[(uu-1)] != null)) {
                                        System.out.println("*** with uu == 4: "
                                                            + eg
                                                            + "_releitem"
                                                            + _releitem.getEntityType()
                                                            + _releitem.getEntityID()
                                                            + "***_sfindRel"
                                                            + _sfindRel);
                                   EntityItem _eitem1 = findLink3(_releitem, _sEg[0]);
                                   if (_eitem1 !=null) {
                                   EntityItem _eitem2 = findLink3(_eitem1, _sEg[1]);
                                   if (_eitem2 !=null) {
                                   EntityItem _eitem3 = findLink3(_eitem2, _sEg[2]);
                                   if (_eitem3 !=null) {
                                   _eitem = findLink(_eitem3, _sEg[(uu-1)]);
                                   if (_eitem !=null) {
                                   eg = el.getEntityGroup(_eitem.getEntityType());
                                           }
                                       }
                                   }
                               }
                            } else if (uu == 5 && (_sEg[(uu-1)] != null)) {
                                        System.out.println("*** with uu == 5: "
                                                            + eg
                                                            + "_releitem"
                                                            + _releitem.getEntityType()
                                                            + _releitem.getEntityID()
                                                            + "***_sfindRel"
                                                            + _sfindRel);
                                   EntityItem _eitem1 = findLink3(_releitem, _sEg[0]);
                                   if (_eitem1 !=null) {
                                   EntityItem _eitem2 = findLink3(_eitem1, _sEg[1]);
                                   if (_eitem2 !=null) {
                                   EntityItem _eitem3 = findLink3(_eitem2, _sEg[2]);
                                   if (_eitem3 !=null) {
                                   EntityItem _eitem4 = findLink3(_eitem3, _sEg[3]);
                                   if (_eitem4 !=null) {
                                   _eitem = findLink(_eitem4, _sEg[(uu-1)]);
                                   if (_eitem !=null) {
                                   eg = el.getEntityGroup(_eitem.getEntityType());
                                               }
                                           }
                                       }
                                   }
                               }
                            } else if (uu == 6 && (_sEg[(uu-1)] != null)) {
                                        System.out.println("*** with uu == 6: "
                                                            + eg
                                                            + "_releitem"
                                                            + _releitem.getEntityType()
                                                            + _releitem.getEntityID()
                                                            + "***_sfindRel"
                                                            + _sfindRel);
                                   EntityItem _eitem1 = findLink3(_releitem, _sEg[0]);
                                   if (_eitem1 !=null) {
                                   EntityItem _eitem2 = findLink3(_eitem1, _sEg[1]);
                                   if (_eitem2 !=null) {
                                   EntityItem _eitem3 = findLink3(_eitem2, _sEg[2]);
                                   if (_eitem3 !=null) {
                                   EntityItem _eitem4 = findLink3(_eitem3, _sEg[3]);
                                   if (_eitem4 !=null) {
                                   EntityItem _eitem5 = findLink3(_eitem4, _sEg[4]);
                                   if (_eitem5 !=null) {
                                   _eitem = findLink(_eitem5, _sEg[(uu-1)]);
                                   if (_eitem !=null) {
                                   eg = el.getEntityGroup(_eitem.getEntityType());
                                                   }
                                               }
                                           }
                                       }
                                   }
                               }
                            } else if (uu == 7 && (_sEg[(uu-1)] != null)) {
                                        System.out.println("*** with uu == 7: "
                                                            + eg
                                                            + "_releitem"
                                                            + _releitem.getEntityType()
                                                            + _releitem.getEntityID()
                                                            + "***_sfindRel"
                                                            + _sfindRel);
                                   EntityItem _eitem1 = findLink3(_releitem, _sEg[0]);
                                   if (_eitem1 !=null) {
                                   EntityItem _eitem2 = findLink3(_eitem1, _sEg[1]);
                                   if (_eitem2 !=null) {
                                   EntityItem _eitem3 = findLink3(_eitem2, _sEg[2]);
                                   if (_eitem3 !=null) {
                                   EntityItem _eitem4 = findLink3(_eitem3, _sEg[3]);
                                   if (_eitem4 !=null) {
                                   EntityItem _eitem5 = findLink3(_eitem4, _sEg[4]);
                                   if (_eitem5 !=null) {
                                   EntityItem _eitem6 = findLink3(_eitem5, _sEg[5]);
                                   if (_eitem6 !=null) {
                                   _eitem = findLink(_eitem6, _sEg[(uu-1)]);
                                   if (_eitem !=null) {
                                   eg = el.getEntityGroup(_eitem.getEntityType());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            sAtt = _s[i].substring(indx+1);
            System.out.println("***sAtt: " + sAtt + "Count: " + Count);

            if (eg.isRelator() || eg.isAssoc()) {
            } else {
                try {
                String tabName = eg.getLongDescription();
                tabName = tabName.replace('/', ' ');
                String ei_Parent = eiParent.getEntityType();
                System.out.println("Working with eg: " + eg.getLongDescription() + "Count: " + Count + ei_Parent + "tabName" + tabName);
                wbs = wb.createSheet(Filename);
                //This is the name of the tab
                wb.setSheetName(Count, tabName);
                Count++;

                if (sAtt.equals("*")) {
                    System.out.println("sAtt equals to *: " + sAtt);

                //need to check the root entity
                if (eg.getEntityType().equals(ei_Parent)) {
                eg = el.getParentEntityGroup();
                }


                for (int x = 0; x < eg.getEntityItemCount(); x++ ) {
                   _ei = eg.getEntityItem(x);
                       System.out.println("***_ei: " + _ei.getEntityType() + _ei.getEntityID() + ": " + eg.getEntityItemCount() + "- " + x);

                    String strEntityType = eg.getEntityType();
                    EntityGroup eg1 = new EntityGroup(null, m_db, m_prof, strEntityType, "Edit", true);
                    mcog = eg1.getMetaColumnOrderGroup();
                    System.out.println("strEntityType" + strEntityType + "eg1: " + eg1);

                for (short k = 0; k < mcog.getMetaColumnOrderItemCount(); k++) {
                    MetaColumnOrderItem mcoi = mcog.getMetaColumnOrderItem(k);
                    String strAttrCode = mcoi.getAttributeCode();
                    System.out.println("strAttrCode" + strAttrCode);


                        if(_ei != null) {

                           System.out.println("***mcog:***" + _ei.getEntityType() + " entityid: " + _ei.getEntityID() + ": mcoi.getAttributeCode(): " + mcoi.getAttributeCode() + "k: " + k);
                           EANAttribute att = _ei.getAttribute(strAttrCode);

                           String strAttributeDesc = mcoi.getAttributeDescription();

                           if (strAttributeDesc.length() > 0 && strAttributeDesc.charAt(0) == '*') {
                                       strAttributeDesc = strAttributeDesc.substring(1);
                                       System.out.println("strAttributeDesc" + strAttributeDesc);
                           }

                           short iCellNum = 0;
                           wbRow = wbs.createRow(iCellNum);
                           wbs.setColumnWidth(k,(short) ((50 * 8) / ((double) 1 / 20)));
                           wbCell = wbRow.createCell(k);
                           wbCell.setCellValue(strAttributeDesc);
                           iCellNum++;

                           //Now write the summary rows
                           String strValue1 = null;
                           if (att == null) {
                               strValue1 = " ";
                           } else {
                               strValue1 = att.toString();

                           if (strValue1.length() > 0 && strValue1.charAt(0) == '*') {
                                       strValue1 = strValue1.substring(1);
                              }
                           }

                           int iRow = (x + 1);


                               System.out.println("strValue1: " + _ei.getEntityType() + _ei.getEntityID() + strValue1 + "iRow" + iRow);
                           wbRow = wbs.createRow(iRow);
                           wbs.setColumnWidth(k,(short) ((50 * 8) / ((double) 1 / 20)));
                           wbCell = wbRow.createCell(k);
                           wbCell.setCellValue(strValue1);
                           iRow++;
                           }
                        }
                    }
                } else {
                System.out.println("sAtt NOT equals to *: " + sAtt);

                StringTokenizer st = new StringTokenizer(sAtt,";");

                int yy = st.countTokens();
                int y = 0;

                String[] atts = new String[yy];
                        System.out.println("yy: " + yy + " atts: " + atts);

                while (st.hasMoreTokens()) {
                    atts[y] = st.nextToken();
                    System.out.println("***atts: " + atts[y]);
                    ++y;
                }

                if (eg != null && atts != null) {
                short _z = -1;
                int jj = atts.length;
                for (int j=jj-1;j>=0;--j) {
                    System.out.print("atts[j]: (" + atts[j] + ")" + "jj: " + jj);

                if (!atts[j].equals("*")) {

                    if (atts !=null) {
                    System.out.println("we're here without a *: " + "atts: " + atts + "atts.length: " + atts.length);

                        StringTokenizer rlst = new StringTokenizer(atts[j],":");

                        int oo = rlst.countTokens();
                        int o = 0;

                        String[] _relatts = new String[oo];
                                System.out.println("oo: " + oo + " _relatts: " + _relatts);

                        while (rlst.hasMoreTokens()) {
                            _relatts[o] = rlst.nextToken();
                            String relatts = (_relatts[o]);
                            o++;


                        boolean ifRelator = stringCheck(relatts);
                        System.out.println("***relatts: " + relatts + " ifRelator: " + ifRelator);
                        if (!ifRelator) {
                        sRel = findRelator(relatts);
                            System.out.println("<>sRel: " + sRel);
                        } else {

                        sEg1 = findEntity(relatts);
                            System.out.println("<>sEg1: " + sEg1);
                        sAtt1 = findAtt(relatts);
                            System.out.println("<>sAtt1: " + sAtt1);


                        StringTokenizer st1 = new StringTokenizer(sAtt1,",");

                        int zz = st1.countTokens();
                        int z = 0;

                        String[] flagatts = new String[zz];
                                System.out.println("zz: " + zz + " flagatts: " + flagatts);

                        while (st1.hasMoreTokens()) {
                            flagatts[z] = st1.nextToken();
                            String _flagatts = (flagatts[z]);
                            z++;
                            System.out.println("***_flagatts: " + _flagatts);
                                EntityGroup egp_sEg1= el.getEntityGroup(sEg1);
                                System.out.println("***sEg1: " + sEg1 + "egp_sEg1: " + egp_sEg1 + "_z" + _z);
                                EANMetaAttribute ma1 = egp_sEg1.getMetaAttribute(_flagatts);
                                _z++;

                        for( short x = 0; x < eg.getEntityItemCount(); x++ ) {
                        EntityItem ei = eg.getEntityItem(x);

                            if (oo == 1) {
                                        System.out.println("*** with oo == 1: "
                                                            + ei.getEntityType()
                                                            + ei.getEntityID()
                                                            + "***sEg1: "
                                                            + sEg1
                                                            + "***sRel"
                                                            + sRel);
                                   _eitem = ei;
                            } else if (oo == 2) {
                                        System.out.println("*** with oo == 2: "
                                                            + ei.getEntityType()
                                                            + ei.getEntityID()
                                                            + "***sEg1: "
                                                            + sEg1
                                                            + "***sRel"
                                                            + sRel);
                                   EntityItem[] aei = findLink2(ei, sEg1, sRel);
                                   int iRow = (x + 1);
                                   for (int kk = 0; kk < aei.length; kk++) {
                                   _eitem = aei[kk];
                                   //System.out.println("**aei-_eitem" + _eitem.getEntityType() + " ID: " + _eitem.getEntityID() + " aei.length: " + aei.length + " kk: " + kk);
                                   ProcessXL(wbs, wbRow, wbCell, _eitem, eg, ma1, _flagatts, x, _z, iRow);
                                   iRow++;
                                   }
                            } else if (oo == 3) {
                                        System.out.println("*** with oo == 3: "
                                                            + ei.getEntityType()
                                                            + ei.getEntityID()
                                                            + "***sEg1: "
                                                            + sEg1
                                                            + "***sRel"
                                                            + sRel);
                                   EntityItem _eitem1 = findLink3(ei,sRel);
                                   if (_eitem1 == null) {
                                       continue;
                                   }
                                   _eitem = findLink(_eitem1, sEg1);
                                   int iRow = (x + 1);
                                   ProcessXL(wbs, wbRow, wbCell, _eitem, eg, ma1, _flagatts, x, _z, iRow);
                                   iRow++;
                                   //EntityItem[] aei = findLink2(_eitem1, sEg1, sRel);
                                   //int iRow = (x + 1);
                                   //for (int kk = 0; kk < aei.length; kk++) {
                                   //_eitem = aei[kk];
                                   //ProcessXL(wbs, wbRow, wbCell, _eitem, eg, ma1, _flagatts, x, _z, iRow);
                                   //iRow++;
                                   //}
                            } else if (oo == 4) {
                                        System.out.println("*** with oo == 4: "
                                                            + ei.getEntityType()
                                                            + ei.getEntityID()
                                                            + "***sEg1: "
                                                            + sEg1
                                                            + "***sRel"
                                                            + sRel);
                                   EntityItem _eitem1 = findLink3(ei,sRel);
                                   if (_eitem1 == null) {
                                       continue;
                                   }
                                   _eitem = findLink(_eitem1, sEg1);
                                   int iRow = (x + 1);
                                   ProcessXL(wbs, wbRow, wbCell, _eitem, eg, ma1, _flagatts, x, _z, iRow);
                                   iRow++;
                                       //EntityItem[] aei = findLink2(_eitem1, sEg1, sRel);
                                       //for (int kk = 0; kk < aei.length; kk++) {
                                       //    int iRow = (x + 1);
                                       //_eitem = aei[kk];
                                       //ProcessXL(wbs, wbRow, wbCell, _eitem, eg, ma1, _flagatts, x, _z, iRow);
                                       //iRow++;
                                       // }

                            }  else if (oo == 5) {
                                        System.out.println("*** with oo == 5: "
                                                            + ei.getEntityType()
                                                            + ei.getEntityID()
                                                            + "***sEg1: "
                                                            + sEg1
                                                            + "***sRel"
                                                            + sRel);
                                   EntityItem _eitem1 = findLink3(ei,sRel);
                                   if (_eitem1 == null) {
                                       continue;
                                   }
                                   _eitem = findLink(_eitem1, sEg1);
                                   int iRow = (x + 1);
                                   ProcessXL(wbs, wbRow, wbCell, _eitem, eg, ma1, _flagatts, x, _z, iRow);
                                   iRow++;
                            }  else if (oo == 6) {
                                        System.out.println("*** with oo == 6: "
                                                            + ei.getEntityType()
                                                            + ei.getEntityID()
                                                            + "***sEg1: "
                                                            + sEg1
                                                            + "***sRel"
                                                            + sRel);
                                   EntityItem _eitem1 = _findLink(ei);
                                   EntityItem _eitem2 = _findLink(_eitem1);
                                   EntityItem _eitem3 = _findLink(_eitem2);
                                   EntityItem _eitem4 = _findLink(_eitem3);
                                   EntityItem[] aei = findLink2(_eitem4, sEg1, sRel);
                                   int iRow = (x + 1);
                                   for (int kk = 0; kk < aei.length; kk++) {
                                   _eitem = aei[kk];
                                   ProcessXL(wbs, wbRow, wbCell, _eitem, eg, ma1, _flagatts, x, _z, iRow);
                                   iRow++;
                                   }
                            }  else if (oo == 7) {
                                        System.out.println("*** with oo == 7: "
                                                            + ei.getEntityType()
                                                            + ei.getEntityID()
                                                            + "***sEg1: "
                                                            + sEg1
                                                            + "***sRel"
                                                            + sRel);
                                   EntityItem _eitem1 = _findLink(ei);
                                   EntityItem _eitem2 = _findLink(_eitem1);
                                   EntityItem _eitem3 = _findLink(_eitem2);
                                   EntityItem _eitem4 = _findLink(_eitem3);
                                   EntityItem _eitem5 = _findLink(_eitem4);
                                   EntityItem[] aei = findLink2(_eitem5, sEg1, sRel);
                                   int iRow = (x + 1);
                                   for (int kk = 0; kk < aei.length; kk++) {
                                   _eitem = aei[kk];
                                   ProcessXL(wbs, wbRow, wbCell, _eitem, eg, ma1, _flagatts, x, _z, iRow);
                                   iRow++;
                                   }
                            }  else if (oo == 8) {
                                        System.out.println("*** with oo == 8: "
                                                            + ei.getEntityType()
                                                            + ei.getEntityID()
                                                            + "***sEg1: "
                                                            + sEg1
                                                            + "***sRel"
                                                            + sRel);
                                   EntityItem _eitem1 = _findLink(ei);
                                   EntityItem _eitem2 = _findLink(_eitem1);
                                   EntityItem _eitem3 = _findLink(_eitem2);
                                   EntityItem _eitem4 = _findLink(_eitem3);
                                   EntityItem _eitem5 = _findLink(_eitem4);
                                   EntityItem _eitem6 = _findLink(_eitem5);
                                   EntityItem[] aei = findLink2(_eitem6, sEg1, sRel);
                                   int iRow = (x + 1);
                                   for (int kk = 0; kk < aei.length; kk++) {
                                   _eitem = aei[kk];
                                   ProcessXL(wbs, wbRow, wbCell, _eitem, eg, ma1, _flagatts, x, _z, iRow);
                                   iRow++;
                                   }
                            }  else if (oo == 9) {
                                        System.out.println("*** with oo == 9: "
                                                            + ei.getEntityType()
                                                            + ei.getEntityID()
                                                            + "***sEg1: "
                                                            + sEg1
                                                            + "***sRel"
                                                            + sRel);
                                   EntityItem _eitem1 = _findLink(ei);
                                   EntityItem _eitem2 = _findLink(_eitem1);
                                   EntityItem _eitem3 = _findLink(_eitem2);
                                   EntityItem _eitem4 = _findLink(_eitem3);
                                   EntityItem _eitem5 = _findLink(_eitem4);
                                   EntityItem _eitem6 = _findLink(_eitem5);
                                   EntityItem _eitem7 = _findLink(_eitem6);
                                   EntityItem[] aei = findLink2(_eitem7, sEg1, sRel);
                                   int iRow = (x + 1);
                                   for (int kk = 0; kk < aei.length; kk++) {
                                   _eitem = aei[kk];
                                   ProcessXL(wbs, wbRow, wbCell, _eitem, eg, ma1, _flagatts, x, _z, iRow);
                                   iRow++;
                                   }
                            } else {
                                System.out.println("too many relators");
                            }
                                   }
                               }
                           }
                        }
                    }
                }  else {
                System.out.println("---------------------we're here *: -------------------------" + atts);


                if (eg != null) {
                    String strEntityType = eg.getEntityType();
                    EntityGroup eg1 = new EntityGroup(null, m_db, m_prof, strEntityType, "Edit", true);
                    mcog = eg1.getMetaColumnOrderGroup();
                       System.out.println("strEntityType" + strEntityType + "eg1: " + eg1);


                for (int k = 0; k < mcog.getMetaColumnOrderItemCount(); k++) {
                    MetaColumnOrderItem mcoi = mcog.getMetaColumnOrderItem(k);
                    _z++;
                    String strAttrCode = mcoi.getAttributeCode();
                    System.out.println("strAttrCode" + strAttrCode);


                for( short x = 0; x < eg.getEntityItemCount(); x++ ) {
                   _eitem = eg.getEntityItem(x);
                    pp = x;
                       System.out.println("***_eitem: " + _eitem.getEntityType() + _eitem.getEntityID() + ": " + eg.getEntityItemCount() + "- " + x);

                         String _strAttributeDesc = mcoi.getAttributeDescription();

                         if (_strAttributeDesc.length() > 0 && _strAttributeDesc.charAt(0) == '*') {
                                     _strAttributeDesc = _strAttributeDesc.substring(1);
                                     System.out.println("_strAttributeDesc" + _strAttributeDesc);
                         }

                         System.out.println(": strAttrCode: " + strAttrCode);
                         EANAttribute att = _eitem.getAttribute(strAttrCode);

                         short _iCellNum = 0;
                         System.out.println("_iCellNum: " + _iCellNum + "_z" + _z);
                         wbRow = wbs.createRow(_iCellNum);
                         wbs.setColumnWidth(_z,(short) ((50 * 8) / ((double) 1 / 20)));
                         wbCell = wbRow.createCell(_z);
                         wbCell.setCellValue(_strAttributeDesc);
                         //wbCell.setCellStyle(style);
                         _iCellNum++;



                        //Now write the summary rows
                        String _strValue = null;
                        if (att == null) {
                            System.out.println("att is null");
                            _strValue = " ";
                        } else {
                            _strValue = att.toString();

                        if (_strValue.length() > 0 && _strValue.charAt(0) == '*') {
                                    _strValue = _strValue.substring(1);
                                    System.out.println("_strValue" + _strValue);
                            }
                           System.out.println("****_strValue: ****" + _eitem.getEntityType() + _eitem.getEntityID() + _strValue);
                        }


                       int _iRow = (pp + 1);

                           System.out.println("strValue1: " + _eitem.getEntityType() + _eitem.getEntityID() + _strValue + "_iRow: " + _iRow + "_z: " + _z);
                       wbRow = wbs.createRow(_iRow);
                       wbs.setColumnWidth(pp,(short) ((50 * 8) / ((double) 1 / 20)));
                       wbCell = wbRow.createCell(_z);
                       wbCell.setCellValue(_strValue);
                       _iRow++;

                        System.out.println("---------------------done with *: -------------------------");
                                    }
                                }
                            }
                        }
                    }
                }
            }
                   try {
                   String strFilename = Filename + ".xls";
                   File xlFile = new File(strFilename);
                   out = new FileOutputStream(xlFile);
                   wb.write(out);
                   out.close();
                   System.out.println("-------------done generate XL----------------");
                   } catch (IOException ex) {
                   ex.printStackTrace();
                    }
                } catch (Exception x) {
                    System.out.println("Badness in mcog" + x);
                    }
                }
            }
            return;
        }
    }

        public boolean stringCheck(String str){
                int t = str.length();
                char[] ch = {'.', ':'};


                for(int c = 0; c < t; c++){
                    for (int x = 0; ch.length > x; x++){

                               if (str.charAt(c)==ch[x]){
                                   return str.charAt(c)==ch[x];
                               }
                            }
                        }
                        return false;
                    }


        public byte[] getWBBytes() {
            byte[] tmp = null;

            tmp = getBytes();
            return tmp;
        }

        public String toString() {
            return out.toString();
        }
        public static void main(String[] args) {
        }


        public byte[] getBytes() {
            byte[] result = null;
            try {
            wb.write(baos);
            if (baos.size() > 0) {
                result = baos.toByteArray();
                System.out.println("baos's size is greater than 0");
            } else {
                System.out.println("badness in baos");
            }
        } catch (Exception x) {
            System.out.println("Badness" + x);
            }
            return result;
        }



    public String findRelator(String relatts) {
        String sRel = null;
            System.out.println("findRelator: "  + relatts);

                        int _indx1 = relatts.indexOf(".");

                        if (_indx1 <0) {
                        sRel = relatts;
                            System.out.println("***_sRel: " + sRel);
                        return sRel;
                        }
                    return sRel;
                    }


    public String findEntity(String relatts) {
        String sEg1 = null;
            System.out.println("findEntity: "  + relatts);

                        int _indx1 = relatts.indexOf(".");

                        if (_indx1 >=0) {
                        sEg1 = relatts.substring(0,_indx1);
                            System.out.println("***sEg1: " + sEg1);
                        return sEg1;
                        }
                    return sEg1;
                    }

    public String findAtt(String relatts) {
        String sAtt1 = null;
            System.out.println("findAtt: "  + relatts);

                        int _indx1 = relatts.indexOf(".");

                        if (_indx1 >=0) {
                        sAtt1 = relatts.substring(_indx1+1);
                            System.out.println("***sAtt1: " + sAtt1);
                        return sAtt1;
                        }
                    return sAtt1;
                    }


    public HSSFCell ProcessXL(HSSFSheet wbs, HSSFRow wbRow, HSSFCell wbCell, EntityItem _eitem, EntityGroup eg, EANMetaAttribute ma1, String _flagatts, short x, short _z, int iRow) {

        EANAttribute att = _eitem.getAttribute(_flagatts);
        System.out.println("***ProcessXL_eitem: "
                            + _eitem.getEntityType()
                            + _eitem.getEntityID()
                            + "x: "
                            + x
                            + "iRow: "
                            + iRow
                            + eg.getEntityType());
        String _strAttributeDesc = ma1.toString();

        if (_strAttributeDesc.length() > 0 && _strAttributeDesc.charAt(0) == '*') {
                    _strAttributeDesc = (_strAttributeDesc.substring(1));
                    System.out.println("_strAttributeDesc" + _strAttributeDesc);
        }


        short iCellNum = 0;
        wbRow = wbs.createRow(iCellNum);
        wbs.setColumnWidth(_z,(short) ((50 * 8) / ((double) 1 / 20)));
        wbCell = wbRow.createCell(_z);
        wbCell.setCellValue(("*" + _strAttributeDesc));
        iCellNum++;


         //Now write the summary rows
         String _strValue = null;
         if (att == null) {
             System.out.println("att is null");
             _strValue = " ";
         } else {
             _strValue = att.toString();

        if (_strValue.length() > 0 && _strValue.charAt(0) == '*') {
                    _strValue = _strValue.substring(1);
                    System.out.println("_strValue" + _strValue);
        }

        //System.out.println("****_strValue: ****" + _eitem.getEntityType() + _eitem.getEntityID() + _strValue);
        }

        wbRow = wbs.createRow(iRow);
        wbs.setColumnWidth(_z,(short) ((50 * 8) / ((double) 1 / 20)));
        wbCell = wbRow.createCell(_z);
        wbCell.setCellValue(_strValue);
        return wbCell;
    }

    public EntityItem[] findLink2(EntityItem ei, String sEg1, String _sRel) {
        Vector eiVector = new Vector();
        EntityItem eiReturn = null;
            System.out.println("Starting findLink2: "  + ei.getEntityType() + ei.getEntityID() + "sEg1:" + sEg1 + "_sRel" + _sRel);

        if (ei.hasUpLinks()) {
        for (int p = 0; p < ei.getUpLinkCount(); p++) {
               //System.out.println("ei.getUpLinkCount" +ei.getUpLinkCount() + " p: " + p);
            EntityItem _ei = (EntityItem) ei.getUpLink(p);
                if ((_ei !=null) && (_ei.getEntityType().equals(_sRel))) {
                for (int c=0; c < _ei.getUpLinkCount(); c++) {
                        //System.out.println("_ei.getUpLinkCount" +_ei.getUpLinkCount() + " c: " + c);
                    eiReturn = (EntityItem) _ei.getUpLink(c);
                        //System.out.println("findLink2 eiReturn: " + eiReturn.getEntityType() + eiReturn.getEntityID() + "sEg1:" + sEg1);
                    if ((eiReturn !=null) && (eiReturn.getEntityType().equals(sEg1))) {
                        eiVector.addElement(eiReturn);
                       }
                    }
                } else {
                    //System.out.println("***findLink2: relator for uplink did not match");
                }
            }
        }
        if (ei.hasDownLinks()) {

           for (int q = 0; q < ei.getDownLinkCount(); q++) {
                //System.out.println("ei.getDownLinkCount" +ei.getDownLinkCount() + " q: " + q);
               EntityItem eidownlink = (EntityItem) ei.getDownLink(q);
               if ((eidownlink !=null) && (eidownlink.getEntityType().equals(_sRel))) {
                   for (int s=0; s < eidownlink.getDownLinkCount(); s++) {
                        //System.out.println("eidownlink.getDownLinkCount" +eidownlink.getDownLinkCount() + " s: " + s);
                       eiReturn = (EntityItem) eidownlink.getDownLink(s);
                           //System.out.println("findLink2 eiReturn: "  + eiReturn.getEntityType() + eiReturn.getEntityID() + "sEg1:" + sEg1);
                       if ((eiReturn !=null) && (eiReturn.getEntityType().equals(sEg1))) {
                           eiVector.addElement(eiReturn);
                                    }
                                }
                            } else {
                            //System.out.println("***findLink2: relator for downlink did not match");
                            }
                        }
                    }
        EntityItem[] aeiReturn = new EntityItem[eiVector.size()];
        eiVector.toArray(aeiReturn);
        return aeiReturn;
    }


    public EntityItem findLink3(EntityItem ei, String sEg1) {
        EntityItem eiLevel2 = null;
        EntityItem eiLevel3 = null;
        EntityItem eiLevel4 = null;
        EntityItem eiLevel5 = null;
            System.out.println("findLink3: "  + ei.getEntityType() + ei.getEntityID() + "sEg1:" + sEg1);

        if (ei.hasUpLinks()) {
        for (int p = 0; p < ei.getUpLinkCount(); p++) {
            EntityItem _ei = (EntityItem) ei.getUpLink(p);
                        System.out.println("_ei - level 1" + _ei.getEntityType() + _ei.getEntityID());
                if ((_ei !=null) && (_ei.getEntityType().equals(sEg1))) {
                return _ei;
                }
                if (_ei.hasUpLinks()) {
                for (int c=0; c < _ei.getUpLinkCount(); c++) {
                     eiLevel2 = (EntityItem) _ei.getUpLink(c);
                        System.out.println("findLink3 level 2 - 2: " + eiLevel2.getEntityType() + eiLevel2.getEntityID() + "sEg1:" + sEg1);
                    if ((eiLevel2 !=null) && (eiLevel2.getEntityType().equals(sEg1))) {
                    return eiLevel2;
                       }
                       if (eiLevel2.hasUpLinks()) {
                          for (int y=0; y < eiLevel2.getUpLinkCount(); y++) {
                               eiLevel3 = (EntityItem) eiLevel2.getUpLink(y);
                                  System.out.println("findLink3 level -3 3: "  + eiLevel3.getEntityType() + eiLevel3.getEntityID() + "sEg1:" + sEg1);
                              if ((eiLevel3 !=null) && (eiLevel3.getEntityType().equals(sEg1))) {
                              return eiLevel3;
                              }
                                  if (eiLevel3.hasUpLinks()) {
                                     for (int z=0; z < eiLevel3.getUpLinkCount(); z++) {
                                          eiLevel4 = (EntityItem) eiLevel3.getUpLink(z);
                                             System.out.println("findLink3 level 4 - 4: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                         if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                         return eiLevel4;
                                         }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level 5 - 20: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level 5 - 21: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                           }
                                         }
                                      }
                                  if (eiLevel3.hasDownLinks()) {
                                     for (int z=0; z < eiLevel3.getDownLinkCount(); z++) {
                                         eiLevel4 = (EntityItem) eiLevel3.getDownLink(z);
                                             System.out.println("findLink3 level 4 - 5: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                         if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                         return eiLevel4;
                                         }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level 5 - 20: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level 5 - 21: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                           }
                                         }
                                     }
                                  }
                               }

                       if (eiLevel2.hasDownLinks()) {
                          for (int y=0; y < eiLevel2.getDownLinkCount(); y++) {
                              eiLevel3 = (EntityItem) eiLevel2.getDownLink(y);
                                  System.out.println("findLink3 level 3 - 6: "  + eiLevel3.getEntityType() + eiLevel3.getEntityID() + "sEg1:" + sEg1);
                              if ((eiLevel3 !=null) && (eiLevel3.getEntityType().equals(sEg1))) {
                              return eiLevel3;
                              }
                                  if (eiLevel3.hasUpLinks()) {
                                     for (int z=0; z < eiLevel3.getUpLinkCount(); z++) {
                                         eiLevel4 = (EntityItem) eiLevel3.getUpLink(z);
                                             System.out.println("findLink3 level 4 - 7: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                         if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                         return eiLevel4;
                                         }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level 5 - 20: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level 5 - 21: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                           }
                                        }
                                     }
                                  if (eiLevel3.hasDownLinks()) {
                                     for (int z=0; z < eiLevel3.getDownLinkCount(); z++) {
                                         eiLevel4 = (EntityItem) eiLevel3.getDownLink(z);
                                             System.out.println("findLink3 level 4 - 8: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                         if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                         return eiLevel4;
                                         }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level 5 - 20: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level 5 - 21: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                          }
                                     }
                                 }
                              }
                           }
                        }
                    }
                if (_ei.hasDownLinks()) {
                for (int d=0; d < _ei.getDownLinkCount(); d++) {
                    eiLevel2 = (EntityItem) _ei.getDownLink(d);
                        System.out.println("findLink3 level 2 - 9: " + eiLevel2.getEntityType() + eiLevel2.getEntityID() + "sEg1:" + sEg1);
                    if ((eiLevel2 !=null) && (eiLevel2.getEntityType().equals(sEg1))) {
                    return eiLevel2;
                       }
                       if (eiLevel2.hasUpLinks()) {
                          for (int y=0; y < eiLevel2.getUpLinkCount(); y++) {
                              eiLevel3 = (EntityItem) eiLevel2.getUpLink(y);
                                  System.out.println("findLink3 level 3 - 10: "  + eiLevel3.getEntityType() + eiLevel3.getEntityID() + "sEg1:" + sEg1);
                              if ((eiLevel3 !=null) && (eiLevel3.getEntityType().equals(sEg1))) {
                              return eiLevel3;
                              }
                                  if (eiLevel3.hasUpLinks()) {
                                     for (int z=0; z < eiLevel3.getUpLinkCount(); z++) {
                                         eiLevel4 = (EntityItem) eiLevel3.getUpLink(z);
                                             System.out.println("findLink3 level 4 - 11: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                         if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                         return eiLevel4;
                                         }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level 5 - 20: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level 5 - 21: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                           }
                                         }
                                      }
                                  if (eiLevel3.hasDownLinks()) {
                                     for (int z=0; z < eiLevel3.getDownLinkCount(); z++) {
                                         eiLevel4 = (EntityItem) eiLevel3.getDownLink(z);
                                             System.out.println("findLink3 level 4 - 12: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                         if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                         return eiLevel4;
                                         }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level 5 - 20: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level 5 - 21: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                           }
                                         }
                                     }
                                  }
                               }

                       if (eiLevel2.hasDownLinks()) {
                          for (int y=0; y < eiLevel2.getDownLinkCount(); y++) {
                              eiLevel3 = (EntityItem) eiLevel2.getDownLink(y);
                                  System.out.println("findLink3 level3 - 13: "  + eiLevel3.getEntityType() + eiLevel3.getEntityID() + "sEg1:" + sEg1);
                              if ((eiLevel3 !=null) && (eiLevel3.getEntityType().equals(sEg1))) {
                              return eiLevel3;
                              }
                                  if (eiLevel3.hasUpLinks()) {
                                     for (int z=0; z < eiLevel3.getUpLinkCount(); z++) {
                                         eiLevel4 = (EntityItem) eiLevel3.getUpLink(z);
                                             System.out.println("findLink3 level 4 - 14: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                         if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                         return eiLevel4;
                                         }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level 5 - 20: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level 5 - 21: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                           }
                                         }
                                      }
                                  if (eiLevel3.hasDownLinks()) {
                                     for (int z=0; z < eiLevel3.getDownLinkCount(); z++) {
                                         eiLevel4 = (EntityItem) eiLevel3.getDownLink(z);
                                             System.out.println("findLink3 level 4 - 15: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                         if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                         return eiLevel4;
                                         }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level 5 - 20: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level 5 - 21: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                           }
                                     }
                                 }
                              }
                           }
                        }
                    }
                }
            }
        if (ei.hasDownLinks()) {
        for (int q = 0; q < ei.getDownLinkCount(); q++) {
            EntityItem eidownlink = (EntityItem) ei.getDownLink(q);
                     System.out.println("eidownlink level 1" + eidownlink.getEntityType() + eidownlink.getEntityID());
                 if ((eidownlink !=null) && (eidownlink.getEntityType().equals(sEg1))) {
                         System.out.println("findLink3 16: "  + eidownlink.getEntityType() + eidownlink.getEntityID() + "sEg1:" + sEg1);
                 return eidownlink;
                 }
                 if (eidownlink.hasUpLinks()) {
                 for (int s=0; s < eidownlink.getUpLinkCount(); s++) {
                     eiLevel2 = (EntityItem) eidownlink.getUpLink(s);
                         System.out.println("findLink3 level 2 - 17: "  + eiLevel2.getEntityType() + eiLevel2.getEntityID() + "sEg1:" + sEg1);
                     if ((eiLevel2 !=null) && (eiLevel2.getEntityType().equals(sEg1))) {
                     return eiLevel2;
                        }
                     if (eiLevel2.hasUpLinks()) {
                        for (int y=0; y < eiLevel2.getUpLinkCount(); y++) {
                            eiLevel3 = (EntityItem) eiLevel2.getUpLink(y);
                                System.out.println("findLink3 level 3 - 18: "  + eiLevel3.getEntityType() + eiLevel3.getEntityID() + "sEg1:" + sEg1);
                            if ((eiLevel3 !=null) && (eiLevel3.getEntityType().equals(sEg1))) {
                            return eiLevel3;
                            }
                                 if (eiLevel3.hasUpLinks()) {
                                    for (int z=0; z < eiLevel3.getUpLinkCount(); z++) {
                                        eiLevel4 = (EntityItem) eiLevel3.getUpLink(z);
                                            System.out.println("findLink3 level 4 - 19: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                        if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                        return eiLevel4;
                                        }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level 5 - 20: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level 5 - 21: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                           }
                                        }
                                     }
                                 if (eiLevel3.hasDownLinks()) {
                                    for (int z=0; z < eiLevel3.getDownLinkCount(); z++) {
                                        eiLevel4 = (EntityItem) eiLevel3.getDownLink(z);
                                            System.out.println("findLink3 level 4 - 22: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                        if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                        return eiLevel4;
                                        }
                                    }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level 5 - 23: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level 5 - 24: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                           }
                                        }
                                     }
                                  }

                     if (eiLevel2.hasDownLinks()) {
                        for (int y=0; y < eiLevel2.getDownLinkCount(); y++) {
                            eiLevel3 = (EntityItem) eiLevel2.getDownLink(y);
                                System.out.println("findLink3 level 3 - 25: "  + eiLevel3.getEntityType() + eiLevel3.getEntityID() + "sEg1:" + sEg1);
                            if ((eiLevel3 !=null) && (eiLevel3.getEntityType().equals(sEg1))) {
                            return eiLevel3;
                            }
                               if (eiLevel3.hasUpLinks()) {
                               for (int z=0; z < eiLevel3.getUpLinkCount(); z++) {
                                   eiLevel4 = (EntityItem) eiLevel3.getUpLink(z);
                                       System.out.println("findLink3 level 4 - 26: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                   if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                   return eiLevel4;
                                   }
                                        if (eiLevel4.hasUpLinks()) {
                                           for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                               eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                   System.out.println("findLink3 level 5 - 27: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                               if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                               return eiLevel5;
                                               }
                                           }
                                        }
                                        if (eiLevel4.hasDownLinks()) {
                                           for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                               eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                   System.out.println("findLink3 level 5 - 28: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                               if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                               return eiLevel5;
                                               }
                                           }
                                       }
                                   }
                               }
                               if (eiLevel3.hasDownLinks()) {
                               for (int z=0; z < eiLevel3.getDownLinkCount(); z++) {
                                   eiLevel4 = (EntityItem) eiLevel3.getDownLink(z);
                                       System.out.println("findLink3 level - 4 29: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                   if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                   return eiLevel4;
                                   }
                                        if (eiLevel4.hasUpLinks()) {
                                           for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                               eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                   System.out.println("findLink3 level - 5 30: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                               if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                               return eiLevel5;
                                               }
                                            }
                                         }
                                        if (eiLevel4.hasDownLinks()) {
                                           for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                               eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                   System.out.println("findLink3 level - 5 31: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                               if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                               return eiLevel5;
                                               }
                                           }
                                        }
                                     }
                                   }
                                }
                             }
                          }
                       }
                 if (eidownlink.hasDownLinks()) {
                 for (int r=0; r < eidownlink.getDownLinkCount(); r++) {
                     eiLevel2 = (EntityItem) eidownlink.getDownLink(r);
                         System.out.println("findLink3 level 2 - 32: " + eiLevel2.getEntityType() + eiLevel2.getEntityID() + "sEg1:" + sEg1);
                     if ((eiLevel2 !=null) && (eiLevel2.getEntityType().equals(sEg1))) {
                     return eiLevel2;
                     }
                          if (eiLevel2.hasUpLinks()) {
                             for (int y=0; y < eiLevel2.getUpLinkCount(); y++) {
                                 eiLevel3 = (EntityItem) eiLevel2.getUpLink(y);
                                     System.out.println("findLink3 level 3 - 33: "  + eiLevel3.getEntityType() + eiLevel3.getEntityID() + "sEg1:" + sEg1);
                                 if ((eiLevel3 !=null) && (eiLevel3.getEntityType().equals(sEg1))) {
                                 return eiLevel3;
                                 }
                                    if (eiLevel3.hasUpLinks()) {
                                       for (int z=0; z < eiLevel3.getUpLinkCount(); z++) {
                                            eiLevel4 = (EntityItem) eiLevel3.getUpLink(z);
                                               System.out.println("findLink3 level 4 - 34: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                            if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                            return eiLevel4;
                                            }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level - 5 35: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                                }
                                             }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level - 5 36: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                        }
                                    }
                                    if (eiLevel3.hasDownLinks()) {
                                       for (int z=0; z < eiLevel3.getDownLinkCount(); z++) {
                                            eiLevel4 = (EntityItem) eiLevel3.getDownLink(z);
                                               System.out.println("findLink3 level -4 37: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                            if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                            return eiLevel4;
                                            }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level - 5 38: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                                }
                                             }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level - 5 39: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                        }
                                    }
                                }
                            }

                          if (eiLevel2.hasDownLinks()) {
                             for (int y=0; y < eiLevel2.getDownLinkCount(); y++) {
                                 eiLevel3 = (EntityItem) eiLevel2.getDownLink(y);
                                     System.out.println("findLink3 level - 3 40: "  + eiLevel3.getEntityType() + eiLevel3.getEntityID() + "sEg1:" + sEg1);
                                 if ((eiLevel3 !=null) && (eiLevel3.getEntityType().equals(sEg1))) {
                                 return eiLevel3;
                                 }
                                    if (eiLevel3.hasUpLinks()) {
                                       for (int z=0; z < eiLevel3.getUpLinkCount(); z++) {
                                           eiLevel4 = (EntityItem) eiLevel3.getUpLink(z);
                                               System.out.println("findLink3 level 4 - 41: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                            if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                            return eiLevel4;
                                            }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level - 5 42: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                                }
                                             }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level - 5 43: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                        }
                                    }
                                    if (eiLevel3.hasDownLinks()) {
                                       for (int z=0; z < eiLevel3.getDownLinkCount(); z++) {
                                           eiLevel4 = (EntityItem) eiLevel3.getDownLink(z);
                                               System.out.println("findLink3 level 4 - 44: "  + eiLevel4.getEntityType() + eiLevel4.getEntityID() + "sEg1:" + sEg1);
                                            if ((eiLevel4 !=null) && (eiLevel4.getEntityType().equals(sEg1))) {
                                            return eiLevel4;
                                            }
                                            if (eiLevel4.hasUpLinks()) {
                                               for (int m=0; m < eiLevel4.getUpLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getUpLink(m);
                                                       System.out.println("findLink3 level - 5 45: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                                }
                                             }
                                            if (eiLevel4.hasDownLinks()) {
                                               for (int m=0; m < eiLevel4.getDownLinkCount(); m++) {
                                                   eiLevel5 = (EntityItem) eiLevel4.getDownLink(m);
                                                       System.out.println("findLink3 level - 5 46: "  + eiLevel5.getEntityType() + eiLevel5.getEntityID() + "sEg1:" + sEg1);
                                                   if ((eiLevel5 !=null) && (eiLevel5.getEntityType().equals(sEg1))) {
                                                   return eiLevel5;
                                                   }
                                               }
                                            }
                                        }
                                     }
                                  }
                               }
                            }
                         }
                     }
                 }
                 return null;
                 }



    public EntityItem _findLink(EntityItem ei) {
        EntityItem eiReturn = null;
            System.out.println("_findLink: "  + ei.getEntityType() + ei.getEntityID());

        if (ei.hasUpLinks()) {
        for (int p = 0; p < ei.getUpLinkCount(); p++) {
            EntityItem _ei = (EntityItem) ei.getUpLink(p);
                for (int c=0; c < _ei.getUpLinkCount(); c++) {
                    eiReturn = (EntityItem) _ei.getUpLink(c);
                        System.out.println("_findLink eiReturn: " + eiReturn.getEntityType() + eiReturn.getEntityID());
                    if (eiReturn !=null)  {
                    return eiReturn;
                   }
                }
            }
        }
        if (ei.hasDownLinks()) {

           for (int q = 0; q < ei.getDownLinkCount(); q++) {
               EntityItem eidownlink = (EntityItem) ei.getDownLink(q);
           for (int s=0; s < eidownlink.getDownLinkCount(); s++) {
               eiReturn = (EntityItem) eidownlink.getDownLink(s);
                   System.out.println("_findLink eiReturn: "  + eiReturn.getEntityType() + eiReturn.getEntityID());
               if (eiReturn !=null) {
               return eiReturn;
                            }
                        }
                    }
                }

        return eiReturn;
    }


    public EntityItem findLink(EntityItem ei, String sEg1) {
        EntityItem eiReturn = null;
            System.out.println("findLink: "  + ei.getEntityType() + ei.getEntityID() + "sEg1:" + sEg1);

        if (ei.hasUpLinks()) {
                for (int c=0; c < ei.getUpLinkCount(); c++) {
                    eiReturn = (EntityItem) ei.getUpLink(c);
                        System.out.println("findLink eiReturn: " + eiReturn.getEntityType() + eiReturn.getEntityID() + "sEg1:" + sEg1);
                    if ((eiReturn !=null) && (eiReturn.getEntityType().equals(sEg1))) {
                    return eiReturn;
               }
            }
        }
        if (ei.hasDownLinks()) {

                   for (int s=0; s < ei.getDownLinkCount(); s++) {
                       eiReturn = (EntityItem) ei.getDownLink(s);
                           System.out.println("findLink eiReturn: "  + eiReturn.getEntityType() + eiReturn.getEntityID() + "sEg1:" + sEg1);
                       if ((eiReturn !=null) && (eiReturn.getEntityType().equals(sEg1))) {
                       return eiReturn;
                    }
                }
            }
        return null;
    }
}

