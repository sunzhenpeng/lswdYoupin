package RFID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class RfidUtil {
    private static final long serialVersionUID = 1L;
    private static long m_hr = 0;// 读卡器操作句柄

    /**
     * 加载驱动
     */
    private static void LoadDriver() {
        String driPath = System.getProperty("user.dir") + "\\Drivers";
        rfidlib_reader.RDR_LoadReaderDrivers(driPath);
        int m_cnt = rfidlib_reader.RDR_GetLoadedReaderDriverCount();
        int nret;
        for (int i = 0; i < m_cnt; i++) {
            char[] valueBuffer = new char[256];
            Integer nSize = new Integer(0);
            String sDes;
            nret = rfidlib_reader.RDR_GetLoadedReaderDriverOpt(i,
                    rfid_def.LOADED_RDRDVR_OPT_CATALOG, valueBuffer, nSize);
            if (nret == 0) {
                sDes = String.copyValueOf(valueBuffer, 0, nSize);
                if (sDes.equals(rfid_def.RDRDVR_TYPE_READER)) {
                    rfidlib_reader
                            .RDR_GetLoadedReaderDriverOpt(i,
                                    rfid_def.LOADED_RDRDVR_OPT_NAME,
                                    valueBuffer, nSize);
                }
            }
        }
    }

    /**
     * 打开串口
     */
    private static void OpenDev() {
        String connStr;
        String sDevName = "RD242";
        String comNameString = "COM2";
        connStr = rfid_def.CONNSTR_NAME_RDTYPE + "=" + sDevName + ";"
                + rfid_def.CONNSTR_NAME_COMMTYPE + "="
                + rfid_def.CONNSTR_NAME_COMMTYPE_COM + ";"
                + rfid_def.CONNSTR_NAME_COMNAME + "=" + comNameString + ";"
                + rfid_def.CONNSTR_NAME_COMBARUD + "=" + "38400" + ";"
                + rfid_def.CONNSTR_NAME_COMFRAME + "=" + "8E1" + ";"
                + rfid_def.CONNSTR_NAME_BUSADDR + "=" + "255";
        Long hrOut = new Long(0);
        int nRet = rfidlib_reader.RDR_Open(connStr, hrOut);
        if (nRet != 0) {
            String sRet = "Open device failed!err=" + nRet;
            return;
        }
        m_hr = hrOut;
    }


    public static ArrayList<String> getLabelInfo() {
        LoadDriver();
        OpenDev();
        return getLabel();
    }

    public static ArrayList<String> getLabel() {
        ArrayList<String> result=new ArrayList<>();
        long InventoryParamSpecList = rfidlib_reader.RDR_CreateInvenParamSpecList();
        boolean b_iso15693 = true;
        if (InventoryParamSpecList != 0) {
            byte AntennaID = 0;
            if (b_iso15693) {
                byte en_afi = 0;
                byte afi = (byte) Integer.parseInt("00");
                byte slot_type = 0;
                rfidlib_AIP_ISO15693.ISO15693_CreateInvenParam(
                        InventoryParamSpecList, AntennaID, en_afi, afi, slot_type);
            }
        }
        byte newAI = rfid_def.AI_TYPE_NEW;
        int nret;
        long dnhReport;
        byte[] AntennaIDs = new byte[64];
        AntennaIDs[0]=1;
        AntennaIDs[1]=2;
        rfidlib_reader.RDR_TagInventory(m_hr, newAI, (byte) 2,
                AntennaIDs, InventoryParamSpecList);
        dnhReport = rfidlib_reader.RDR_GetTagDataReport(m_hr,
                rfid_def.RFID_SEEK_FIRST);
        while (dnhReport != 0) {
            if (b_iso15693) {
                Integer aip_id = new Integer(0);
                Integer tag_id = new Integer(0);
                Integer ant_id = new Integer(0);
                Byte dsfid = new Byte((byte) 0);
                byte uid[] = new byte[8];
                nret = rfidlib_AIP_ISO15693.ISO15693_ParseTagDataReport(
                        dnhReport, aip_id, tag_id, ant_id, dsfid, uid);
                if (nret == 0) {
                    String sUid = RfidFunction.encodeHexStr(uid);
                    result.add(sUid);
                }
            }
            dnhReport = rfidlib_reader.RDR_GetTagDataReport(m_hr,
                    rfid_def.RFID_SEEK_NEXT);
        }
        if (InventoryParamSpecList != 0) {
            rfidlib_reader.DNODE_Destroy(InventoryParamSpecList);
        }
        rfidlib_reader.RDR_ResetCommuImmeTimeout(m_hr);
        rfidlib_reader.RDR_Close(m_hr);
        m_hr = 0;
        return result;
    }
}
