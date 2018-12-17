package com.lswd.youpin.common.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * @author lucas
 *
 * Dec 29, 2009
 */
public class DateUtils{

	/**
	 * 获取当前时间（年月日 时分秒）
	 * @return
	 */
	public static String getNow() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowc = new Date();
		String pid = formatter.format(nowc);
		return pid;
	}
	
	/**
	 * 得到当前小时，如：20
	 * @return
	 */
	public static int getHour() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH");
		Date nowDate = new Date();
		String hh = formatter.format(nowDate);
		return Integer.parseInt(hh);
	}
	/**
	 * 根据日期格式返回日期字符串
	 * @param pattern 不同的日期格式
	 * @return
	 */
	public static String getDateFormat(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}
	/**
	 * 返回时间戳（秒级）
	 * @param pattern
	 * @return
	 */
	public static Long getTimestampOfSS(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt = sdf.parse(pattern);
			Long time = dt.getTime();
			return time/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当前日期（年-月-日）
	 * @return
	 */
	public static String getYMD() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		String pid = formatter.format(nowDate);
		return pid;
	}
	
	/**
	 * 获取当前日期（年月日）
	 * @return
	 */
	public static String getNoBeepNow() {
		SimpleDateFormat formatter = 
		new SimpleDateFormat("yyyyMMdd");
		Date nowc = new Date();
		String pid = formatter.format(nowc);
		return pid;
	}
	
	
	 /**
	   * 秒转化为时间格式
	   * @param second   秒单位
	   * @return
	   */
	  public static String dateToString( int second ){
		  
		 return dateToString(new Date((long)second*1000),"yyyy-MM-dd HH:mm:ss");
	  }
	  
	  public static String dateToString( int second,String format ){
			 return dateToString(new Date((long)second*1000), format);
		  }
	  
	  
	  public static String dateToString( String second ){
		  
			 return dateToString(new Date(Long.valueOf(second)*1000),"yyyy-MM-dd HH:mm:ss");
	  }
	  
	  public static String dateToString( String second , String format ){
		  
			 return dateToString(new Date(Long.valueOf(second)*1000),format);
	  }

	/**
	 * 比较两个时间差是否在范围内
	 * @param date1		较晚时间
	 * @param date2		较早时间
	 * @param minute	差值范围
	 * @return	如在差值范围内返回true,否则返回False. 如date1<date2返回false.
	 */
	public static boolean compareDiff(Date date1, Date date2, int minute) {
		if (null ==date1 ||null==date2 )
			return false;
		if (!date1.before(date2)) {
			long diff = date1.getTime() - date2.getTime();
			if (diff / 60000 < minute) {
				return true;
			}
		}
		return false;
	}
	
	public static String getShortTime(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(getStringToDateTime(dateStr));
	}
	
	public static int compareDiff(Date date) {
		Date date1 = new Date();
		
			long diff = date1.getTime() - date.getTime();
			if (diff / 60000 < 60) {
				return 1;
			}
			if(diff/60000>60&&diff/60000<720) {
				return 2;
			}else {
				return 3 ;
			}
		
	}
	/**
	 * 
	 * @param str
	 * @return
	 * @throws ParseException @tale:
	 * @purpose：String TO date
	 * @author：Simon - 赵振明
	 * @CreationTime：2010-6-25下午01:55:56
	 */
	public static Date StringToDate(String str) throws ParseException{
		if(null==str) {
			return null;
		}
		SimpleDateFormat fordate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fordate.parse(str);
	}
	
	/**
	 * 获取指定日期的后一天
	 **/
	public static String getSpecifiedDayAfter(String specifiedDay){ 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(specifiedDay); 
		} catch (ParseException e) { 
			e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+1); 

		String dayAfter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()); 
		return dayAfter; 
	}
	
	/**
	 * 获取当前日期的前一天:yyyyMMdd
	 * @return
	 */
	public static String getNowBefor(){ 
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 

		String dayAfter=new SimpleDateFormat("yyyyMMdd").format(c.getTime()); 
		return dayAfter; 
	}
	
	/**
	 * 获取当前日期的前一天:yyyy-MM-dd
	 * @return
	 */
	public static String getNowBeforDay(){ 
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 

		String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayAfter; 
	}
	/**
	 * 获取当前日期的前一天:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Timestamp getNowBeforTimestamp(){ 
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date()); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day-1); 

		String dayAfter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()); 
		return Timestamp.valueOf(dayAfter); 
	}

	public static String getBeforeDay(String dateTime) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
		java.util.Date date=sdf.parse(dateTime);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE,day-1);
		String dayBefore=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		return dayBefore;
	}
	public static Date getNextDay(Date date) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE,day+1);
		return c.getTime();
	}

	public static String getNextDay(String dateTime) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
		java.util.Date date=sdf.parse(dateTime);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE,day+1);
		String dayAfter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		return dayAfter;
	}
	/**
	 * 返回当前时间的timestamp类型
	 * @return
	 */
	public static Timestamp getTimestamp(){
		
		return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	/***
	 * 	
	 * @param DATE1
	 * @param DATE2
	 * @return @tale:
	 * @purpose：比较两个日期
	 * @author：Simon - 赵振明
	 * @CreationTime：Jun 25, 20103:07:10 PM
	 */
	public static int compare_date(String DATE1, String DATE2) { 
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
        try { 
                Date dt1 = df.parse(DATE1); 
                Date dt2 = df.parse(DATE2); 
                if (dt1.getTime() > dt2.getTime()) { 
                        return 1; 
                } else if (dt1.getTime() < dt2.getTime()) { 
                        return -1; 
                } else if (dt1.getTime() == dt2.getTime()) {
                		return 2;
                }else { 
                        return 0; 
                } 
        } catch (Exception exception) { 
                exception.printStackTrace(); 
        } 
        return 0; 
} 
	/**
	 * 将日期字符串参数转换为YYYY-MM-DD格式的日期类型, 抛出格式化异常
	 * @param fromdate	日期格式字符串
	 * @return	YYYY-MM-DD格式的日期类型
	 * @throws ParseException
	 */
	public static Date getShortDate(String fromdate) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(fromdate);
	}


	/**
	 * @author sxd 字符串日期转化成date型日期
	 */

	public static Date getStringToDateTime(String date)  {
		System.err.println(date);
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		Date time = null;
		try {
			time = sdt.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("输入时间格式不对");
		}
		return time;
	}
	
	  /**
    Function: dateToString
    @param sFormat
    This is to convert a date into string with given format
    Format can be "yyyy-MM-dd HH:mm:ss"
  */
	 public static String dateToString( Date dDate, String sFormat )
	  {
	    if ( dDate == null )
	    {
	      return "";
	    }
	    else
	    {
	      SimpleDateFormat sdf = new SimpleDateFormat( sFormat );
	      return sdf.format( dDate );
	    }
	 }
  
  /**
   * 秒转化为时间格式
   * @param second   秒单位
   * @return
   */

  public static String datestrToString(String second , String format ){
	  
		 return dateToString(new Date(Long.valueOf(second)*1000),format);
  }
  
  
  public static String test(){
	 
	  return "test ok !";
  }
  
  /**
   * 秒转化为时间格式
   * @param second   秒单位
   * @return
   */
  public static String secondToString( int second,String fmt ){
	  
	 return dateToString(new Date((long)second*1000),fmt);
  }
  
	/**
	 * 将日期字符串参数转为long
	 * @param fromdate	日期格式字符串
	 * @return	YYYY-MM-DD格式的日期类型
	 * @throws ParseException
	 */
	public static long getLongDate(String fromdate) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fromdate).getTime();
	}
	
	/**
	 * 将日期字符串参数转为 int
	 * @param fromdate
	 * @return
	 * @throws ParseException
	 */
	public static int getIntDate(String fromdate) throws ParseException {
		return (int)(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fromdate).getTime()/1000);
	}
	
	public static int getIntDate() throws ParseException {
		return (int)(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(DateUtils.getNow()).getTime()/1000);
	}
	public static int getShortIntDate(String fromdate) throws ParseException {
		return (int)(new SimpleDateFormat("yyyy-MM-dd").parse(fromdate).getTime()/1000);
		
	}
	
	public static int getIntHour(String fromdate) throws ParseException {
		return (int)(new SimpleDateFormat("yyyy-MM-dd HH").parse(fromdate).getTime()/1000);
		
	}
	
	
	public static long getLongDate(String fromdate,String fmt) throws ParseException {
		return new SimpleDateFormat(fmt).parse(fromdate).getTime();
	}
	
  
	/**
	 * 取得与原日期相差一定天数的日期，返回Date型日期
	 * 
	 * @param date
	 *            原日期
	 * @param intBetween
	 *            相差的天数
	 * @return date加上intBetween天后的日期
	 */
	public static Date getDateBetween(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.DATE, intBetween);
		return calo.getTime();
	}
	
	
	/**
	 * 取得与原日期相差一定天数的日期，返回Date型日期
	 * 
	 * @param date
	 *            原日期
	 * @param intBetween
	 *            相差的天数
	 * @return date加上intBetween天后的日期
	 */
	public static String getDateBetween(String date, String ymd,int intBetween)throws Exception {
		if(date == null || "".equals(date.trim())) return "";
		Date dd = getDateBetween(strToDate(date,ymd),intBetween);
		return formatDateTime(ymd,dd);
	}
	
    public static String formatDateTime(String ymd, Date datetime){
        //格式化当前时间
        SimpleDateFormat isNow = new SimpleDateFormat(ymd,Locale.ENGLISH);
        String now = "";
        try{
            now = isNow.format(datetime);
        }catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }
    
    
    /**
     * 字符串格式的时间转化为long型的毫秒时间
     * @param str
     * @param dateformat
     * @return
     * @throws Exception
     */
    public static Date strToDate(String str,String dateformat)throws Exception{
    	SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
    	Date date = sdf.parse(str);
    	return date;
    }
    
    
    /**
     * 获得日志所用的开始时间    
     * @return     
     */
    public static String logStartTime(){
    	SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowc = new Date();
		String curTime = formater.format(nowc);
			
		return curTime;    	
    }
	
    /**
     * 获得日志所用的结束时间
     * @param logStartTime     
     * @return    
     */
    public static String logEndTime(String logStartTime){    	
		String logEndTime = "logEndTime";
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		long diff=-1;
		try {
			diff = new Date().getTime() - formater.parse(logStartTime).getTime();
			if(diff>0){				
				long minute = diff/(60*1000);
				long second = diff/1000;
				long millisecond = diff-(second*1000);
				
				logEndTime = minute +"分" + second +"秒"+millisecond+"毫秒- ";				
			}			
		} catch (ParseException e) {			
			e.printStackTrace();			
		} 				        
		return logEndTime;    	
    }
    
    /**
     * 获取int类型时间
     * @param field
     * @param amount
     * @return
     * @throws ParseException 
     */
    public static int getAddInDate(int field, int amount) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String strdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    	Calendar car = Calendar.getInstance();
		car.setTime(sdf.parse(strdate));
		car.add(field, amount);
		Date d = car.getTime();
		return (int)(d.getTime()/1000);
    }
    public static String getStrAddInDate(int field, int amount) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar car = Calendar.getInstance();
		car.setTime(new Date());
		car.add(field, amount);
		return sdf.format(car.getTime());
    }
    
    /**
     * 根据时间戳获取时间
     * @param secs
     * @return yyyyMMdd
     */
    public static String getDateBySecs(Long secs){
    	
    	SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	String dateBySecs = format.format(secs);
    	Calendar cd = Calendar.getInstance();
    	try {
			cd.setTime(format.parse(dateBySecs));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	int year = cd.get(Calendar.YEAR);     //获取年份
    	int month = cd.get(Calendar.MONTH);     //获取月份
    	int day = cd.get(Calendar.DATE);   //获取日
    	String valueOf = String.valueOf(year);
    	String valueOf3 = String.valueOf(month);
    	String valueOf2 = String.valueOf(day);
    	if(valueOf3.length()==1){
    		valueOf3 = "0"+valueOf3;
    	}
    	if(valueOf2.length()==1){
    		valueOf2 = "0"+valueOf2;
    	}
    	return valueOf+valueOf3+valueOf2;
    }
    
    
    public static void main(String[] args) throws ParseException {
		System.out.println(getBeforeDay("2018-01-01 00:00:00"));
	  /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   java.util.Date now = df.parse("2015-11-11 13:00:00");
	   java.util.Date date=df.parse("2016-05-19 13:00:00");
	   long l=now.getTime()-date.getTime();
	   long day=l/(24*60*60*1000);
	   long hour=(l/(60*60*1000)-day*24);
	   long min=((l/(60*1000))-day*24*60-hour*60);
	   long s=(l/1000-day*24*60*60-hour*60*60-min*60);
	   System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");*/
    	

//    	
//    	SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd ");
//    	Date date =sdf1.parse(dateBySecs);
//    	Calendar calendar = Calendar.getInstance();
//    	
//    	calendar.setTime(new Date(time));
//    	System.out.println(calendar.getTime());
    	
//    	Long time = 1270822997L *1000 ;
//    	String dateBySecs = getDateBySecs(time);
//    	System.out.println(dateBySecs);
//    	
//    	Calendar calendar = Calendar.getInstance();
//    	  SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//    	  String time1 = format.format(calendar.getTime());
//    	  System.out.println(getNowBefor());
    	 
	}
    
    public static int getDiscrepantDays(Date dateStart, Date dateEnd) {
        return (int) ((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60 / 24);
    } 
    
}
