package com.lswd.youpin.commons;

/**
 * Created by liruilong on 17/04/11
 */
public class ConstantsCode {

    public static final int _200 = 200;

    public static final int _201 = 201;

    public static final int _400 = 400;

    public static final int _404 = 404;

    public static final int _409 = 409;

    public static final int SESSION_EXPIRE = 7200;

    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String USER = "user";
    public static final String ASSOCIATOR = "associator";
    public static final String TENANT_ASSOCIATOR = "tenantAssociator";
    public static final String LSWD_DB = "lswd";
    public static final String TOKEN = "token";
    public static final String TYPE = "type";
    public static final String CANTEEN_ID = "canteenId";

    public static final short TMP = 1;

    public static final short PAID = 2;

    public static final short OVER = 3;

    public static final short REFUND = 4;

    public static final short CANCEL = 5;

    public static final short WX = 1;

    public static final short ALI = 2;

    public static final short CARD = 3;

    public static final short CARD_WX = 4;

    public static final short CARD_ALI = 5;

    public static final short CLOSE = 0;

    public static final short OPEN = 1;

    public static final String GOOD_PAY = "0";

    public static final String RECHARGE = "1";

    public static final short WX_ACCOUNT=0;

    public static final short ALI_ACCOUNT=1;

    public static final short CARD_ACCOUNT=2;

    public static final String GENERAL="general";

    public static final String  LSCT="lsct";

    public static final String LOCAL_UPDATE_URL="http://192.168.1.211:8082/upload/";

    public static final String YUN_UPDATE_URL="https://web.lsypct.com/upload/";


    public static final String getLiveshowList="{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/mclz.png\",\"list\":[\n" +
            "                {\"id\":1, \"name\":\"餐厅\", \"img\":\"https://web.lsypct.com/upload/nodata/mingchu_list/1.jpg\"},\n" +
            "                {\"id\":2, \"name\":\"餐具洗刷间\", \"img\":\"https://web.lsypct.com/upload/nodata/mingchu_list/2.jpg\"},\n" +
            "                {\"id\":3, \"name\":\"水产品加工间\", \"img\":\"https://web.lsypct.com/upload/nodata/mingchu_list/3.jpg\"},\n" +
            "                {\"id\":4, \"name\":\"副食加工间\", \"img\":\"https://web.lsypct.com/upload/nodata/mingchu_list/4.jpg\"},\n" +
            "                {\"id\":5, \"name\":\"肉产品加工间\", \"img\":\"https://web.lsypct.com/upload/nodata/mingchu_list/5.jpg\"},\n" +
            "                {\"id\":6, \"name\":\"面点品加工间\", \"img\":\"https://web.lsypct.com/upload/nodata/mingchu_list/6.jpg\"}\n" +
            "            ]}";

    public static final String getLiveshowInfo="{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/mclz.png\",\"video\":\"http://capcobroadcaststream.in:1935/capco/ad24/playlist.m3u8\"}";



    //nutrition
    public static final String getNutritionMainInfo = "{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/cpzs.png\"}";
    public static final String getRecipeList = "{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/cpzs.png\",\"recipelist\":[\n" +
            "                    {\"id\": 0, \"title\": \"周一\", \"data\": [\n" +
            "                        {\"name\": \"葱油拌面\", \"id\": \"0\"},\n" +
        "                        {\"name\": \"芹菜炒香干\", \"id\": \"1\"},\n" +
        "                        {\"name\": \"菠菜蛋汤\", \"id\": \"2\"},\n" +
        "                        {\"name\": \"米饭\", \"id\": \"3\"},\n" +
            "                        {\"name\": \"中等大小橘子\", \"id\": \"4\"},\n" +
            "                    ]},\n" +
            "                    {\"id\": 1, \"title\": \"周二\", \"data\": [\n" +
            "                        {\"name\": \"香菇狮子头\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"大白菜炒双菇\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"清炒西蓝花\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"西红柿鸡蛋汤\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"二米饭\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"一杯酸奶\",  \"id\": \"1\"}\n" +
            "                    ]},\n" +
            "                    {\"id\": 2, \"title\": \"周三\", \"data\": [\n" +
            "                        {\"name\": \"虾仁豆腐\",  \"id\": \"0\"},\n" +
            "                        {\"name\": \"山药炒肉\",  \"id\": \"0\"},\n" +
            "                        {\"name\": \"卷心菜奶酪凉拌\",  \"id\": \"0\"},\n" +
            "                        {\"name\": \"菠菜猪肝汤\",  \"id\": \"0\"},\n" +
            "                        {\"name\": \"蛋炒饭\",  \"id\": \"0\"},\n" +
            "                        {\"name\": \"半个 苹果\",  \"id\": \"1\"}\n" +
            "                    ]},\n" +
            "                    {\"id\": 3, \"title\": \"周四\", \"data\": [\n" +
            "                        {\"name\": \"红烧带鱼\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"家常豆腐\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"素炒三丝\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"丝瓜蛋汤\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"馒头\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"牛奶100ml\", \"id\": \"1\"}\n" +
            "                    ]},\n" +
            "                    {\"id\": 4, \"title\": \"周五\", \"data\": [\n" +
            "                        {\"name\": \"土豆烧牛肉\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"西红柿炒鸡蛋\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"炒油菜\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"海带豆腐汤\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"米饭\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"香蕉\",  \"id\": \"1\"}\n" +
            "                    ]},\n" +
            "                    {\"id\": 5, \"title\": \"周六\", \"data\": [\n" +
            "                        {\"name\": \"猪肉白菜水饺\", \"id\": \"1\"}\n" +
            "                    ]}\n" +
        "                ]\n" +
        "                }";

          /*  "                    {\"id\": 0, \"title\": \"周一\", \"data\": [\n" +
            "                        {\"name\": \"宫保鸡丁\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"小米粥\", \"id\": \"1\"},\n" +
            "                        {\"name\": \"地三鲜\", \"id\": \"2\"},\n" +
            "                        {\"name\": \"大盘鸡\", \"id\": \"3\"},\n" +
            "                        {\"name\": \"九转大肠\", \"id\": \"4\"},\n" +
            "                    ]},\n" +
            "                    {\"id\": 1, \"title\": \"周二\", \"data\": [\n" +
            "                        {\"name\": \"辣椒炒鸡\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"清炒西兰花\",  \"id\": \"1\"}\n" +
            "                    ]},\n" +
            "                    {\"id\": 2, \"title\": \"周三\", \"data\": [\n" +
            "                        {\"name\": \"蘑菇炒肉\",  \"id\": \"0\"},\n" +
            "                        {\"name\": \"红烧鲤鱼\",  \"id\": \"1\"}\n" +
            "                    ]},\n" +
            "                    {\"id\": 3, \"title\": \"周四\", \"data\": [\n" +
            "                        {\"name\": \"炒土豆丝\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"辣子鸡\", \"id\": \"1\"}\n" +
            "                    ]},\n" +
            "                    {\"id\": 4, \"title\": \"周五\", \"data\": [\n" +
            "                        {\"name\": \"猪肉炖粉条\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"西红柿炒鸡蛋\",  \"id\": \"1\"}\n" +
            "                    ]},\n" +
            "                    {\"id\": 5, \"title\": \"周六\", \"data\": [\n" +
            "                        {\"name\": \"黄瓜炒蛋\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"角瓜鸡蛋\",  \"id\": \"1\"}\n" +
            "                    ]},\n" +
            "                    {\"id\": 6, \"title\": \"周日\", \"data\": [\n" +
            "                        {\"name\": \"鱼香肉丝\", \"id\": \"0\"},\n" +
            "                        {\"name\": \"青椒牛柳\", \"id\": \"1\"}\n" +
            "                    ]}\n" +
            "                ]\n" +
            "                }";*/
    public static final String getRecipeInfo = "{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/cpzs.png\",\"webview\":\"http://www.meishij.net/zuofa/congyoubanmian_20.html\"}";
    public static final String getNutritionInfo = "{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/yyzs.png\",\"webview\":\"http://www.boohee.com/\"}";
    public static final String getNutritionRecordInfo = "{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/yyda.png\",\"pdf\":\"https://web.lsypct.com/upload/nodata/ssbt.pdf\"}";
//canteenQulitifacation

    public static final String getRestaurantManagementMainInfo = "{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/ctgl.jpg\"}";
    public static final String getQualificationList = "{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/ctzz.jpg\",\"list\":[" +
            "{\"id\":1,\"img\":\"https://web.lsypct.com/upload/nodata/zizhi/1.jpg\"}," +
            "{\"id\":2,\"img\":\"https://web.lsypct.com/upload/nodata/zizhi/2.jpg\"}," +
            "{\"id\":3,\"img\":\"https://web.lsypct.com/upload/nodata/zizhi/3.jpg\"}]}";
    public static final String getForbiddenList = "{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/jymd.jpg\",\"list\":[" +
            "{\"id\":1,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/1.png\"}," +
            "{\"id\":2,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/2.png\"}," +
            "{\"id\":3,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/3.png\"}," +
            "{\"id\":4,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/4.png\"}," +
            "{\"id\":5,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/5.png\"}," +
            "{\"id\":6,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/6.png\"}," +
            "{\"id\":7,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/7.png\"}," +
            "{\"id\":8,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/8.png\"}," +
            "{\"id\":9,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/9.png\"}," +
            "{\"id\":10,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/10.png\"}," +
            "{\"id\":11,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/11.png\"}," +
            "{\"id\":12,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/12.png\"}," +
            "{\"id\":13,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/13.png\"}," +
            "{\"id\":14,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/14.png\"}," +
            "{\"id\":15,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/15.png\"}," +
            "{\"id\":16,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/16.png\"}," +
            "{\"id\":17,\"img\":\"https://web.lsypct.com/upload/nodata/jinyong/17.png\"}" +
            "]}";
    public static final String getStaffList = "{\n"+
            "\t\t\"banner\": \"https://web.lsypct.com/upload/nodata/banner/ctgl.jpg\",\n"+
            "\t\t\"stafflist\": [{\n"+
            "\t\t\t\t\"key\": \"餐厅主管\",\n"+
            "\t\t\t\t\"image\": \"icon_boss\",\n"+
            "\t\t\t\t\"data\": [{\n"+
            "\t\t\t\t\t\"title\": \"张小敏\",\n"+
            "\t\t\t\t\t\"text\": \"Section s1\",\n"+
            "\t\t\t\t\t\"key\": \"0\"\n"+
            "\t\t\t\t}]\n"+
            "\t\t\t},\n"+
            "\t\t\t{\n"+
            "\t\t\t\t\"key\": \"餐厅经理\",\n"+
            "\t\t\t\t\"image\": \"icon_manager\",\n"+
            "\t\t\t\t\"data\": [{\n"+
            "\t\t\t\t\t\t\"title\": \"李冰冰\",\n"+
            "\t\t\t\t\t\t\"text\": \"Section s2\",\n"+
            "\t\t\t\t\t\t\"key\": \"0\"\n"+
            "\t\t\t\t\t},\n"+
            "\t\t\t\t\t{\n"+
            "\t\t\t\t\t\t\"title\": \"王斌\",\n"+
            "\t\t\t\t\t\t\"text\": \"Section s2\",\n"+
            "\t\t\t\t\t\t\"key\": \"1\"\n"+
            "\t\t\t\t\t}\n"+
            "\t\t\t\t]\n"+
            "\t\t\t},\n"+
            "\t\t\t{\n"+
            "\t\t\t\t\"key\": \"餐厅员工\",\n"+
            "\t\t\t\t\"image\": \"icon_staff\",\n"+
            "\t\t\t\t\"data\": [{\n"+
            "\t\t\t\t\t\t\"title\": \"李国强\",\n"+
            "\t\t\t\t\t\t\"text\": \"Section s2\",\n"+
            "\t\t\t\t\t\t\"key\": \"0\"\n"+
            "\t\t\t\t\t},\n"+
            "\t\t\t\t\t{\n"+
            "\t\t\t\t\t\t\"title\": \"张广茂\",\n"+
            "\t\t\t\t\t\t\"text\": \"Section s2\",\n"+
            "\t\t\t\t\t\t\"key\": \"1\"\n"+
            "\t\t\t\t\t}\n"+
            "\t\t\t\t]\n"+
            "\t\t\t}\n"+
            "\t\t]\n"+
            "\t}";

    public static final String getStaffInfo = "{\n" +
            "\t\"code\": 1,\n" +
            "\t\"data\": {\n" +
            "\t\t\"banner\": \"https://web.lsypct.com/upload/nodata/banner/ctgl.png\",\n" +
            "\t\t\"info\": {\n" +
            "\t\t   \"name\":\"张小敏\",\n" +
            "\t\t   \"sex\":\"女\",\n" +
            "\t\t   \"role\":\"餐厅主管\",\n" +
            "\t\t   \"idcardImg\":\"http://h.hiphotos.baidu.com/image/pic/item/2cf5e0fe9925bc31b3e6ab0a52df8db1ca1370ed.jpg\",\n" +
            "\t\t   \"healthcardImg\":\"http://h.hiphotos.baidu.com/image/pic/item/2cf5e0fe9925bc31b3e6ab0a52df8db1ca1370ed.jpg\"\n" +
            "\t\t\t}\n";
    public static final String getTrainList = "{\"list\":[{\"id\":1,\"name\":\"厨师餐饮培训\",\"img\":\"https://web.lsypct.com/upload/nodata/peixun/peixun01.jpg\"},{\"id\":2,\"name\":\"厨师餐饮秋季培训\",\"img\":\"https://web.lsypct.com/upload/nodata/peixun/peixun02.jpg\"},{\"id\":3,\"name\":\"厨师餐饮冬季培训\",\"img\":\"https://web.lsypct.com/upload/nodata/peixun/peixun03.jpg\"}]}";
    public static final String getTrainInfo = "{\"banner\":\"https://web.lsypct.com/upload/nodata/banner/ygpx.png\",\"imagelist\":[\"http://h.hiphotos.baidu.com/image/pic/item/2cf5e0fe9925bc31b3e6ab0a52df8db1ca1370ed.jpg\",\"http://h.hiphotos.baidu.com/image/pic/item/2cf5e0fe9925bc31b3e6ab0a52df8db1ca1370ed.jpg\"]}";





}
