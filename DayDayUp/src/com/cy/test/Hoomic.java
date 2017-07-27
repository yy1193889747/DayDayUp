package com.cy.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Hoomic {
    public static Map<String, String> SignParams(Map<String, String> paramMap) {
	// sign=94DF49A4F8026A41662124A38ACCF67B&device_os=android&sessionToken=ab146b9d6b8c0ab4c6c9aa300f033da5&umeng_channel=xxone&q_version=2.4.0601&device_osversion=4.4.2&device_type=min4&userId=2566231&req_timestamp=1465201790433&device_id=014336261988662&app_name=forums
	paramMap.put("userId", "2566231");
	paramMap.put("sessionToken", "ab146b9d6b8c0ab4c6c9aa300f033da5");
	paramMap.put("q_version", "2.4.0601");
	paramMap.put("device_id", "014336261988662");
	paramMap.put("device_os", "android");
	paramMap.put("device_type", "min4");
	paramMap.put("device_osversion", "4.4.2");
	paramMap.put("req_timestamp", "1465201790433");
	paramMap.put("app_name", "forums");
	paramMap.put("umeng_channel", "xxone");
	paramMap.put("sign", makeSign(paramMap));
	return paramMap;
    }

    public static String makeSign(Map arg8) {
	TreeMap v4 = new TreeMap();
	Iterator v5 = arg8.entrySet().iterator();
	while (v5.hasNext()) {
	    Object v1 = v5.next();
	    v4.put(((Map.Entry) v1).getKey(), ((Map.Entry) v1).getValue());
	}
	StringBuilder v2 = new StringBuilder("fe#%d8ec93a1159a2a3");
	v5 = v4.keySet().iterator();
	while (v5.hasNext()) {
	    Object v0 = v5.next();
	    if ("sign".equals(v0)) {
		continue;
	    }

	    if (((String) v0).startsWith("file_")) {
		continue;
	    }

	    v2.append(((String) v0)).append(v4.get(v0));
	}

	v2.append("fe#%d8ec93a1159a2a3");
	System.out.println(v2.toString());
	return md5(v2.toString()).toUpperCase();
    }

    public static void main(String[] arg2) {
	Map<String, String> myMap = new HashMap<String, String>();
	SignParams(myMap);
    }

    public static String md5(String arg8) {
	byte[] v2;
	try {
	    v2 = MessageDigest.getInstance("MD5").digest(arg8.getBytes("UTF-8"));
	} catch (UnsupportedEncodingException v1) {
	    throw new RuntimeException("Huh, UTF-8 should be supported?", ((Throwable) v1));
	} catch (NoSuchAlgorithmException v1_1) {
	    throw new RuntimeException("Huh, MD5 should be supported?", ((Throwable) v1_1));
	}

	StringBuilder v3 = new StringBuilder(v2.length * 2);
	int v5 = v2.length;
	int v4;
	for (v4 = 0; v4 < v5; ++v4) {
	    int v0 = v2[v4];
	    if ((v0 & 255) < 16) {
		v3.append("0");
	    }

	    v3.append(Integer.toHexString(v0 & 255));
	}
	System.out.println(v3.toString().toUpperCase());
	return v3.toString();
    }
}
