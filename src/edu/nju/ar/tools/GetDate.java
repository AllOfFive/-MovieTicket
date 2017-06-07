package edu.nju.ar.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetDate {
	public static String getDateToday() {

		 Date d = new Date(); 
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	     String dateNowStr = sdf.format(d);  
//	     System.out.println("格式化后的日期：" + dateNowStr);  
	     return dateNowStr;
	          
	}
	
	public static void main(String[] args) {
//		getDateToday();
    	System.out.println(FormatPrice.formatPrice("￥ 32.5"));
	}
}
