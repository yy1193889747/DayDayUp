package com.cy.test;

import com.alibaba.fastjson.JSONObject;

public class Jsontest {

    public static void main(String[] args) {
	String ss = "{\"pk_corp\":\"1039\",\"unitname\":\"财产综合保险\"}";
	JSONObject sss = JSONObject.parseObject(ss);
	System.out.println(sss.size());
	String aa = sss.getString("unitnamse");
	System.out.println("aa---" + aa);

	JSONObject s = JSONObject.parseObject("{}");
	String ssss = s.getString("aa");
	System.out.println(ssss);
	System.out.println(changenull(ssss));

    }

    public static String changenull(String str) {
	if ("null".equals(str) || str == null) {
	    return "";
	}
	return str;
    }

}
