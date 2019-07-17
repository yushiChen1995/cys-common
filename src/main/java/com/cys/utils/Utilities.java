package com.cys.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 此类用来做工具类集合
 *
 */

public class Utilities
{
	private static final String KEY_ALGORITHM = "RSA";
	private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
	private static final String SALT = "a20e462468a372M934a7B6ad6QRPMVvrr3aF5yY6qagRA38125[099698b395b5f85ce";
	private static final String AES_KEY="ef7f8ffceb162863ceba4f5a552e1788576819bf906df6dd1680315ab1cccb89";
	public static final String BAIDU_AES_KEY="b91e9398194e38d23086ae9ffd251b86";
	public static final String BAIDU_DES_KEY="ffd21b86";
	
	private static final char[] HEX_CHAR_TABLE = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
	private static final ThreadPoolExecutor executer = new ThreadPoolExecutor(50, 500, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100));
    private static  Logger log = LoggerFactory.getLogger(Utilities.class);

	public static ThreadPoolExecutor getExecutor() {
		return executer;
	}
	
	
	 /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password Password or other credentials to use in authenticating
     *        this username
     *
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password) {
    	StringBuilder sbPwd = new StringBuilder();
    	sbPwd.append(SALT);
    	sbPwd.append("_");
    	sbPwd.append(password);
    	
        byte[] unencodedPassword = sbPwd.toString().getBytes();
        
        MessageDigest md = null;
        
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            return password;
        }
        
        md.reset();
        
        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);
        
        // now calculate the hash
        byte[] encodedPassword = md.digest();
        
        StringBuilder buf = new StringBuilder();
        
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        
        return buf.toString();
    }
	
	
    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password Password or other credentials to use in authenticating
     *        this username
     * @param algorithm Algorithm used to do the digest
     *
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword;
		try {
			unencodedPassword = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return password;
		}
        
        MessageDigest md = null;
        
        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            return password;
        }
        
        md.reset();
        
        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);
        
        // now calculate the hash
        byte[] encodedPassword = md.digest();
        
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            
            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }
        
        return buf.toString();
    }

    
    /**
     * 排序字段的获取
     * @param orderBy 排序
     * @return 排序字段
     */
    public static String getOrderBy(Integer orderBy) {
		if(orderBy==null)
		{
			orderBy =0;
		}
		switch (orderBy) {
		case 0:
			return "volume:desc";
		case 1:
			return "price:desc";
		case 2:
			return "price:asc";
		case 3:
			return "seller_credit:desc";
		default:
			return "volume:desc";
		}
	}

	public static String encodeMD5(String content){
		return encodePassword(content + getRandomPwd(40), "md5");
	}

	/**
	 * 产生数字和字母混合的指定位数的随机密码
	 * @param count 位数
	 * @return 随机密码
	 */
	public static String getRandomPwd(int count)
	{
		return RandomStringUtils.random(count, true, true);
	}
    
    /**
     * 排序字段的获取
     * @param orderBy 排序
     * @return 排序字段
     */
    public static String getOrderKeBy(Integer orderBy) {
		if(orderBy==null)
		{
			orderBy =0;
		}
		switch (orderBy) {
		case 0:
			return "commissionNum_desc";
		case 1:
			return "price_desc";
		case 2:
			return "price_asc";
		case 3:
			return "credit_desc";
		default:
			return "commissionNum_desc";
		}
	} 
    

    public static String md5(String content){
    	return encodePassword(content, "md5");
    }
    

    public static String sign(byte[] data, String privateKey) throws Exception {  
        // 解密由base64编码的私钥  
        byte[] keyBytes = decryptBASE64(privateKey);  
  
        return sign(data, keyBytes);
    }
    
    public static String sign(byte[] data, byte[] privateKey) throws Exception {  
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
  
        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initSign(priKey);  
        signature.update(data);  
  
        return encryptBASE64(signature.sign());  
    }
    
	public static boolean verify(byte[] data, String publicKey, String sign)  
            throws Exception {  
  
        // 解密由base64编码的公钥  
        byte[] keyBytes = decryptBASE64(publicKey);  
  
        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
  
        // 取公钥匙对象  
        PublicKey pubKey = keyFactory.generatePublic(keySpec);  
  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initVerify(pubKey);  
        signature.update(data);  
  
        // 验证签名是否正常  
        return signature.verify(decryptBASE64(sign));  
    }
    
    public static byte[] decryptBASE64(String content) throws IOException{
		return (new BASE64Decoder()).decodeBuffer(content);
    }
    
    public static String encryptBASE64(byte[] content) {
		return (new BASE64Encoder()).encode(content);
	}
    
    public static String getParamName(String param){
    	if(StringUtils.isBlank(param)){
    		return "";
    	}
    	String paramName = "";
    	if(param.equalsIgnoreCase("code")){
    		paramName="验证码";
    	}else if(param.equalsIgnoreCase("email")){
    		paramName="电子邮件";
    	}else if(param.equalsIgnoreCase("mobile")){
    		paramName="手机号码";
    	}else if(param.equalsIgnoreCase("login_name")){
    		paramName="用户名";
    	}else if(param.equalsIgnoreCase("client_ip")){
    		paramName="客户端IP";
    	}else if(param.equalsIgnoreCase("password")){
    		paramName="密码";
    	}else if(param.equalsIgnoreCase("register_ip")){
    		paramName="注册IP";
    	}else if(param.equalsIgnoreCase("last_login_ip")){
    		paramName="最近登录IP";
    	}else if(param.equalsIgnoreCase("id")){
    		paramName="用户ID";
    	}else if(param.equalsIgnoreCase("newPassword")){
    		paramName="新密码";
    	}else if(param.equalsIgnoreCase("openid")){
    		paramName="第三方oauth用户ID";
    	}else if(param.equalsIgnoreCase("provider")){
    		paramName="oauth提供方";
    	}else{
    		paramName = param;
    	}
    	return paramName;
    }
    
    public static String encAESString(String source){
    	return encAESString(source, AES_KEY);
    }
    
    public static String encAESString(String source, String key){
    	byte[] byteMi = null;   
        byte[] byteMing = null;   
        String strMi ="";   
        BASE64Encoder base64en = new BASE64Encoder();   
        try {
          byteMing = source.getBytes("UTF-8");   
          Cipher cipher = Cipher.getInstance("AES");   
          Key genKey = getAESKey(key);
          cipher.init(Cipher.ENCRYPT_MODE, genKey);
          byteMi = cipher.doFinal(byteMing);   
          strMi = base64en.encode(byteMi);   
        } catch (Exception e) {
        	log.error("加密AES出错", e);
        } finally {   
          base64en = null;   
          byteMing = null;   
          byteMi = null;   
        }
        return strMi;  
    }
    
    public static String decAESString(String source, String key){
    	BASE64Decoder base64De = new BASE64Decoder();   
        byte[] byteMing = null;   
        byte[] byteMi = null;   
        String strMing = "";   
        try {   
          byteMi = base64De.decodeBuffer(source);   
          Cipher cipher = Cipher.getInstance("AES");
          Key genKey = getAESKey(key);
          cipher.init(Cipher.DECRYPT_MODE, genKey);   
          byteMing = cipher.doFinal(byteMi);   
          strMing = new String(byteMing, "UTF-8");   
        } catch (Exception e) {   
        	log.error("解密AES出错", e);
        } finally {   
          base64De = null;   
          byteMing = null;   
          byteMi = null;
        }
        return strMing;
    }
    
    public static String decAESString(String source){
    	return decAESString(source, AES_KEY);
    }
    
    public static Key getAESKey(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	KeyGenerator _generator = KeyGenerator.getInstance("AES");
    	SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
    	secureRandom.setSeed(key.getBytes("UTF-8")); 
        _generator.init(128, secureRandom);
        Key genKey = _generator.generateKey();
        _generator = null;
        return genKey;
    }
    
    public static Integer parseInt(String num){
    	Integer n = null;
    	try{
    		n = (num == null) ? null : Integer.parseInt(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }
    
    public static Byte parseByte(String num){
    	Byte n = null;
    	try{
    		n = (num == null) ? null : Byte.parseByte(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }
    
   
    public static Short parseShort(String num){
    	Short n = null;
    	try{
    		n = (num == null) ? null : Short.parseShort(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }     
    
    public static Boolean parseBoolean(String num){
    	Boolean n = null;
    	try{
    		n = (num == null) ? null : Boolean.parseBoolean(num);
    	}catch(Exception e){
    		
    	}
    	return n;
    }  
    
    public static Integer getMiddleNumber(String priceRule){
		//10-425,20-356
		Double retValue =null;;
		if(priceRule != null){
			String[] priceRules = priceRule.split(",");
			List<Integer> priceList = new ArrayList<Integer>();
			
			Double avu = 0d;
			Double avgPrice =0d;
			Double countPrice = 0d;
			for (int i = 0; i < priceRules.length; i++) {
				if(priceRules[i].isEmpty()){
					continue;
				}
				String [] singlePrice = priceRules[i].split("-");
				priceList.add(Integer.valueOf(singlePrice[1]));
				countPrice = countPrice + Integer.valueOf(singlePrice[1]).doubleValue();
			}
			//算出平均数
			avgPrice = countPrice/priceRules.length;
			
			Collections.sort(priceList,new Comparator<Integer>(){
				public int compare(Integer arg0,Integer arg1){
					if(arg0!= null && arg1 != null){
						return arg0.compareTo(arg1);
					}else{
						return 0;
					}
				}
			});
			
			if(priceList.size() ==1||priceList.size() == 2){
				retValue = priceList.get(0).doubleValue();
				return retValue.intValue();
			}
			//奇数
			if(priceList.size()%2==1){
				Integer index =priceList.size() /2;
				
				Double avgValue = priceList.get(index).doubleValue();
				if(avgValue.intValue()>avgPrice.intValue()){
					retValue = avgPrice;
				}else{
					retValue = avgValue;
				}

			}else{//偶数
				Integer mid = priceList.size()/2-1;
				Integer value1 = priceList.get(mid);
				Integer value2 = priceList.get(mid+1);
				Double avgValue = 0d;
				
				if(value1>value2){
					avgValue = value2.doubleValue();
				}else{
					avgValue = value1.doubleValue();
				}
				
				if(avgValue.intValue()>avgPrice.intValue()){
					retValue = avgPrice;
				}else{
					retValue = avgValue;
				}
			}
			
		}else{
			return null;
		}
		return retValue.intValue();
	}

    public static boolean isOdd(int a){
    	return ((a&1) == 1);
    }
    
	/**
	 * 去除角分
	 * @param price
	 * @return
	 */
	public static int keepYuan(int price){
		return ((price+50)/100)*100;
	}
	
	public static String convertSignString(JSONObject json){
		Set<Entry<String, Object>> entrySet = json.entrySet();
		ArrayList<Entry<String, Object>> list = new ArrayList<Entry<String, Object>>(entrySet);
		Collections.sort(list, new Comparator<Entry<String, Object>>(){
			@Override
			public int compare(Entry<String, Object> o1,
					Entry<String, Object> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		StringBuilder sb = new StringBuilder(1024);
		for(Entry<String, Object> entry : list){
			if(!"sign".equals(entry.getKey())
					&& !"signMethod".equals(entry.getKey())
					&& entry.getValue() != null){
				sb.append(entry.getKey());
				sb.append("=");
				if(entry.getValue() instanceof JSONObject){
					sb.append(convertSignString((JSONObject)entry.getValue()));
				}else{
					sb.append(entry.getValue());
				}
				sb.append("&");
			}
		}
		String signString = sb.toString();
		if(StringUtils.endsWith(signString, "&")){
			signString = StringUtils.removeEnd(signString, "&");
		}
		return signString;
	}
	
	public static String enc3DESString(String source, String key) throws Exception{
		return encodeDES(key, source.getBytes());
	}
	
	public static String dec3DESString(String source, String key){
		byte[] datas;
	    String value = null;
	        try {
	            datas = decodeDES(key, (new BASE64Decoder()).decodeBuffer(source));
	            value = new String(datas);
	        } catch (Exception e) {
	            value = "";
	        }
	    return value;
	}
	
	private static String encodeDES(String key, byte[] data) throws Exception{
	    try{
	       DESKeySpec dks = new DESKeySpec(key.getBytes());
	  
	       SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	      //key的长度不能够小于8位字节
	      Key secretKey = keyFactory.generateSecret(dks);
	      Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	      IvParameterSpec iv = new IvParameterSpec(key.getBytes());
	      AlgorithmParameterSpec paramSpec = iv;
	      cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
	  
	      byte[] bytes = cipher.doFinal(data);
	  
//	      return byte2hex(bytes);
	      return new String((new BASE64Encoder()).encode(bytes));
	    } catch (Exception e){
	    	throw new Exception(e);
	    }
    }
	
	private static byte[] decodeDES(String key,byte[] data) throws Exception{
	    try{
	      SecureRandom sr = new SecureRandom();
	      DESKeySpec dks = new DESKeySpec(key.getBytes());
	      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	      
	      //key的长度不能够小于8位字节
	      Key secretKey = keyFactory.generateSecret(dks);
	      Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	      IvParameterSpec iv = new IvParameterSpec(key.getBytes());
	      AlgorithmParameterSpec paramSpec = iv;
	      cipher.init(Cipher.DECRYPT_MODE, secretKey,paramSpec);
	      return cipher.doFinal(data);
	    } catch (Exception e){
	      throw new Exception(e);
	    }
	}
	
	public String encrypt(String keyStr, String str) throws NoSuchAlgorithmException, NoSuchPaddingException{
        byte[] ret = null;
        SecretKeySpec enc_key = new SecretKeySpec(keyStr.getBytes(), "AES");
        IvParameterSpec enc_iv = new IvParameterSpec(keyStr.getBytes());
        Cipher cipherEnc = Cipher.getInstance("AES/CBC/NoPadding");
        try {
            cipherEnc.init(Cipher.ENCRYPT_MODE, enc_key, enc_iv);
            ret = cipherEnc.doFinal(padRight(str,
                    ((int)Math.ceil(str.length() / 16.0))*16).getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return byteArray2HexString(ret);
    }
	
	public static String decrypt(String str, String keyStr){
        byte[] ret = null;

        try {
       	    SecretKeySpec dec_key = new SecretKeySpec(keyStr.getBytes(), "AES");
       	    byte[] iv = {0x2}; 
            IvParameterSpec dec_iv = new IvParameterSpec(iv);
            Cipher cipherDec = Cipher.getInstance("AES/CBC/NoPadding");
            
            cipherDec.init(Cipher.DECRYPT_MODE, dec_key, dec_iv);
            ret = cipherDec.doFinal(hexString2ByteArray(str));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        try {
			return new String(ret, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
    }

	public static String byteArray2HexString(byte[] b) {
        if (b == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * b.length);
        for (final byte by : b) {
            hex.append(HEX_CHAR_TABLE[(by & 0xF0) >> 4]).append(HEX_CHAR_TABLE[(by & 0x0F)]);
        }
        return hex.toString();
    }
	
	public static byte[] hexString2ByteArray(String s) {
        if (s == null) {
            return null;
        }
        byte high, low;
        int len = s.length() / 2;
        byte[] b = new byte[len];
        for(int i=0, k=0; i<len; i++, k+=2)
        {
            high = (byte) (Character.digit(s.charAt(k), 16) & 0x0F);
            low = (byte) (Character.digit(s.charAt(k+1), 16) & 0x0F);
            b[i] = (byte) ((high<<4) | low);
        }

        return b;
    }
	
	public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$#" + n + "s", s);
    }
    
    public static StringBuilder appendParam(StringBuilder returnStr,String paramId,String paramValue){
		if(returnStr.length() > 0){
			if(paramValue != null && !paramValue.equals("")){
				returnStr.append("&").append(paramId).append("=").append(paramValue);
			}
		}else{
			if(paramValue != null && !paramValue.equals("")){
				returnStr.append(paramId).append("=").append(paramValue);
			}
		}
		return returnStr;
	}

    public static boolean isUserAgentSupportWebp(String userAgent){
    	boolean isSupport = false;
    	String[] supportVersion = {"4", "3"}; 
    	String version =  getAndroidVersion(userAgent);
    	if(StringUtils.isNotBlank(version)){
    		String[] versions = version.split("\\.");
    		isSupport = (versions[0].compareTo(supportVersion[0]) > 0);
    		if(isSupport){
    			return isSupport;
    		}
    		if(versions.length > 1){
    			isSupport = (versions[1].compareTo(supportVersion[1]) >= 0);
    		}
    	}
    	return isSupport;
    }
    
    public static String getAndroidVersion(String userAgent){
    	Pattern pattern = Pattern.compile("Android\\s+([\\d\\.]+)?;");
    	Matcher matcher = pattern.matcher(userAgent);
    	String version = null;
    	if (matcher.find()){
    		version = matcher.group(1);
    	}
    	return version;
    }
    
    public static String getIOSVersion(String userAgent){
    	Pattern pattern = Pattern.compile("iPhone\\sOS\\s+([\\d\\_]+)?");
    	Matcher matcher = pattern.matcher(userAgent);
    	String version = null;
    	if (matcher.find()){
    		version = matcher.group(1);
    	}
    	return version == null ? "" : version.replace("_", ".");
    }
    
    public static String implode(String spacer, Object[] array){

        String res = "";

        for(int i = 0 ; i < array.length ; i++){
            if( !res.equals("") ){
                res += spacer;
            }
            res += array[i];
        }

        return res;
    }
    
	public static String getNativeAppVersion(String ua) {
		String versionPrefix = "lecar_mainapp_v";
		if (ua != null && !ua.contains(versionPrefix)) {
			versionPrefix = "lecar_proapp_v";
		}
		String version = StringUtils.substringAfter(ua, versionPrefix);
		if (StringUtils.indexOf(version, " ") > 0) {
			version = StringUtils.substringBefore(version, " ");
		}
		return version;
	}
    
    public static String genForbidKey(String path, String ip){
    	StringBuilder sb = new StringBuilder("soa_forbid:ip:");
		return sb.append(path).append(":").append(ip).toString();
    }
    
	/**
	 * 判断ip是否局域网IP地址
	 * @param ip
	 * @return true 局域网IP false 广域网IP
	 */
    public static boolean isLANIp(String ip){
		boolean result = false;
		if(StringUtils.isBlank(ip) || StringUtils.equals(ip, "127.0.0.1")){
			return true;
		}
		String[] ips = ip.split("\\.");
		if(ips.length != 4 || !StringUtils.isNumeric(ips[0]) || !StringUtils.isNumeric(ips[1])){
			return true;
		}
		try{
			Integer ipFirst = Integer.valueOf(ips[0]);
			Integer ipSecond = Integer.valueOf(ips[1]);
			if(ipFirst == 0){
				result = true;
			}else if(ipFirst == 10){
				result = true;
			}else if(ipFirst == 192 && ipSecond == 168){
				result = true;
			}else if(ipFirst == 172 && ipSecond >= 16 && ipSecond <= 31){
				result = true;
			}
		}catch(Exception e){
			result = true;
		}
		return result;
	}
}
