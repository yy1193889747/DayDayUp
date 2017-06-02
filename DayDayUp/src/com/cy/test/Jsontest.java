package com.cy.test;

import com.alibaba.fastjson.JSONObject;

public class Jsontest {

    public static void main(String[] args) {
	String ss = "{\"pk_corp\":\"1039\",\"unitname\":\"财产综合保险\"}";
	JSONObject sss = JSONObject.parseObject(ss);
	System.out.println(sss.size());

	String aa = sss.getString("unitnamse");
	System.out.println("aa---" + aa);

    }
}
