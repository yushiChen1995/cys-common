/**
 * 
 */
package com.cys.utils;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Pattern;





/**
 * @author chenyushi
 */
public class StringUtil {
	
	private static final Pattern PATTERN_MOBILE	= Pattern.compile("^(1(([3-9][0-9])))\\d{8}$");
	private static final Pattern PATTERN_EMAIL	= Pattern.compile("^[\\w-]{1,40}(\\.[\\w-]{1,20}){0,6}@[\\w-]{1,40}(\\.[\\w-]{1,20}){1,6}$");

	public static int parse(String input) {
		if(isEmail(input))
			return 1;
		else if(isMobile(input))
			return 2;
		else 
			return 3;
	}
	

	
	public static Map<String,Object> replaceDate(Map<String,Object> map) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(map != null && !map.isEmpty()) 
			for(String key : map.keySet()) 
				if(map.get(key) != null && !"".equals(map.get(key)))
					if(key.endsWith("time")) 
						map.put(key, sdf.format(map.get(key)));
		return map;
	}


	public static boolean isMobile(String mobile){
		return mobile != null && PATTERN_MOBILE.matcher(mobile).matches();
		//String pMobile = "^(1(([3-9][0-9])))\\d{8}$";
		//return Pattern.matches(pMobile, mobile);
	}
	
	public static boolean isEmail(String email){
//		String pEmail = "^\\S+([-+.]\\S+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		if(email == null || (!email.contains("@"))){
			return false;
		}
		return PATTERN_EMAIL.matcher(email).matches();
//		boolean result = true;
//		result = StringUtils.isAsciiPrintable(email);
//		if(!result) return result;
//		result = (email.indexOf(".") != -1);
//		if(!result) return result;
//		result = (email.indexOf("@") != -1);
//		if(!result) return result;
//		return result;
	}
}
