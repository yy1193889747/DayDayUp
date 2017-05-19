package com.cy.test;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cy.tools.BaseConnection;

public class MapperDao {

    public static void main(String[] args) throws DocumentException, SQLException {
	MapperDao a = new MapperDao();
	// String s =
	// "{\"erptable\":\"book\",\"keyvalue\":{\"bookname\":\"'Who I am'\",\"zuozhe\":\"'cy'\",\"chubancode\":\"123\",\"chubanshe\":\"'电子出版社'\",\"ts\":\"2017-05-12\"}}";
	// System.out.println(a.insert(JSONObject.parseObject(s)));
	a.insert(a.handle());
	// exesql("select name a , id2 ss , id , date from factory where id =3");
	// querymap("book", "2");
    }

    public boolean insert(JSONArray j) throws SQLException {
	boolean b = false;
	Connection conn = BaseConnection.getConnection();
	PreparedStatement ps = null;
	try {
	    conn.setAutoCommit(false);
	    for (int i = 0; i < j.size(); i++) {
		JSONArray jo = j.getJSONArray(i);
		for (int k = 0; k < jo.size(); k++) {
		    JSONObject joo = jo.getJSONObject(k);
		    String table = joo.getString("erptable");
		    String keys = "";
		    String values = "";
		    JSONArray jooo = joo.getJSONArray("keyvalue");
		    for (int l = 0; l < jooo.size(); l++) {
			Set<Entry<String, Object>> set = jooo.getJSONObject(l).entrySet();
			Iterator<Entry<String, Object>> it = set.iterator();
			while (it.hasNext()) {
			    Entry<String, Object> entry = it.next();
			    keys += entry.getKey() + ",";
			    values += entry.getValue() + ",";
			}
		    }
		    keys = keys.substring(0, keys.lastIndexOf(","));
		    values = values.substring(0, values.lastIndexOf(","));
		    String sql = "insert into " + table + " (" + keys + ") values (" + values + ")";
		    System.out.println(sql);
		    ps = conn.prepareStatement(sql);
		    int a = ps.executeUpdate();
		    if (a == 0) {
			b = true;
			break;
		    }
		}
	    }
	    if (b = true) {
		conn.commit();
	    } else {
		conn.rollback();
	    }
	} catch (Exception e) {
	    conn.rollback();
	    e.printStackTrace();
	} finally {
	    BaseConnection.closeRes(ps, conn);
	}

	return b;
    }

    // 处理报文对应值及固定值 拼成一下结构
    // [[{"erptable":"bookss","keyvalue":[{"zhangjie":"'第一章'"}]},{"erptable":"books","keyvalue":[{"booknames":"'Who I ama'"},{"gaiyao":"'第一章的简介'"},{"zhangjie":"'第一章'"},{"zuozhe":"'cy'"}]}],[{"erptable":"bookss","keyvalue":[{"zhangjie":"'第二章'"}]},{"erptable":"books","keyvalue":[{"booknames":"'Who I ama'"},{"gaiyao":"'第二章的简介'"},{"zhangjie":"'第二章'"},{"zuozhe":"'cy'"}]}],[{"erptable":"book","keyvalue":[{"bookname":"'Who I ama'"},{"zuozhe":"'cy'"},{"chubancode":"3"},{"ts":"'2017-05-17 15:12:31'"},{"chubanshe":"'电子科技出版社'"}]}],[{"erptable":"bom","keyvalue":[{"bookname":"'Who I ama'"},{"zuozhe":"'cy'"}]}]]
    @SuppressWarnings("unchecked")
    public JSONArray handle() throws DocumentException {
	// reqMsg.getReqValue("FANAData");

	// Document document =
	// DocumentHelper.parseText(reqMsg.getReqValue("FANAData"));
	JSONArray j = new JSONArray();
	SAXReader reader = new SAXReader();
	File file = new File("books.xml");
	Document document = reader.read(file);
	Element root = document.getRootElement();
	List<Element> childElements = root.elements();
	// 遍历报文根节点
	for (Element child : childElements) {
	    List<Element> elementList = child.elements();
	    String type = child.elementText("Type");
	    if (child.getName().equals("summary")) {
		type = child.getName();
	    }
	    // 产生一条头表json mapper
	    JSONArray jj = MapperDao.querymap(type, "1");
	    System.out.println(child.elementText("Type") + "的mapper----" + jj.toString());
	    for (Element ele : elementList) {
		Element ele1 = child.element(ele.getName());
		// 判断是否有子节点
		if (ele1.nodeCount() != 1) {
		    for (Iterator<?> it1 = ele1.elementIterator(); it1.hasNext();) {
			Element ele2 = (Element) it1.next();
			JSONArray jjaaa = MapperDao.querymap(type, "2");
			System.out.println("产生的json" + jjaaa.toString());
			for (Iterator<?> it2 = ele2.elementIterator(); it2.hasNext();) {
			    Element ele3 = (Element) it2.next();
			    System.out.println(ele3.getName() + ": " + ele3.getText());
			    for (int i = 0; i < jjaaa.size(); i++) {
				JSONObject jjo = jjaaa.getJSONObject(i);
				JSONArray jo = JSONArray.parseArray(jjo.getString("keyvalue"));
				int aaa = 0;
				for (int k = 0; k < jo.size(); k++) {
				    if (aaa == 1) {
					break;
				    }
				    JSONObject joo = jo.getJSONObject(k);
				    Set<Entry<String, Object>> set = joo.entrySet();
				    Iterator<Entry<String, Object>> it = set.iterator();
				    while (it.hasNext()) {
					Entry<String, Object> entry = it.next();
					if (entry.getKey().equals("cappname")) {
					    if (entry.getValue().equals(ele3.getName())) {
						aaa = 1;
						if (joo.getString("typename").equals("String")) {
						    joo.put(joo.getString("erpname"), "'" + ele3.getText() + "'");
						}
						if (joo.getString("typename").equals("int")) {
						    joo.put(joo.getString("erpname"), ele3.getText());
						}
						if (joo.getString("typename").equals("date")) {
						    joo.put(joo.getString("erpname"), ele3.getText());
						}
						joo.remove("typename");
						joo.remove("erpname");
						joo.remove("cappname");
						break;
					    }
					}
				    }
				}
				jjo.remove("keyvalue");
				jjo.put("keyvalue", jo);
				jjaaa.remove(i);
				jjaaa.add(i, jjo);
			    }
			}
			System.out.println();
			// 处理行表 固定值及sql
			for (int i = 0; i < jjaaa.size(); i++) {
			    JSONObject jjo = jjaaa.getJSONObject(i);
			    JSONArray jo = JSONArray.parseArray(jjo.getString("keyvalue"));
			    for (int k = 0; k < jo.size(); k++) {
				JSONObject joo = jo.getJSONObject(k);
				Set<Entry<String, Object>> set = joo.entrySet();
				Iterator<Entry<String, Object>> it = set.iterator();
				while (it.hasNext()) {
				    Entry<String, Object> entry = it.next();
				    if (entry.getKey().equals("sql")) {
					Pattern p = Pattern.compile("\\[(.*?)\\]");
					Matcher m = p.matcher(entry.getValue().toString());
					ArrayList<String> strs = new ArrayList<String>();
					while (m.find()) {
					    strs.add(m.group(1));
					}
					for (String s : strs) {
					    System.out.println(s);
					}
					String sql = entry.getValue().toString();
					for (int l = 0; l < jo.size(); l++) {
					    JSONObject jooo = jo.getJSONObject(l);
					    Set<Entry<String, Object>> sets = jooo.entrySet();
					    Iterator<Entry<String, Object>> its = sets.iterator();
					    while (its.hasNext()) {
						Entry<String, Object> entrys = its.next();
						for (String s : strs) {
						    if (entrys.getKey().equals(s)) {
							sql = sql.replace("[" + s + "]", entrys.getValue().toString());
						    }
						}
						if (sql.indexOf("[") == -1) {
						    break;
						}
					    }
					    if (sql.indexOf("[") == -1) {
						break;
					    }
					}
					JSONObject ss = MapperDao.exesql(sql);
					Set<Entry<String, Object>> setss = ss.entrySet();
					Iterator<Entry<String, Object>> itss = setss.iterator();
					while (itss.hasNext()) {
					    JSONObject jjooo = new JSONObject();
					    Entry<String, Object> entryss = itss.next();
					    jjooo.put(entryss.getKey(), entryss.getValue());
					    jo.add(jjooo);
					}
					jo.remove(k);
					k = -1;
				    }
				    if (entry.getKey().equals("fiexd")) {
					if (entry.getValue().equals("[nowtime]")) {
					    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    if (joo.getString("typename").equals("String")) {
						joo.put(joo.getString("erpname"), "'" + sdf.format(new Date()) + "'");
					    }
					    if (joo.getString("typename").equals("int")) {
						joo.put(joo.getString("erpname"), sdf.format(new Date()));
					    }
					    if (joo.getString("typename").equals("date")) {
						joo.put(joo.getString("erpname"), sdf.format(new Date()));
					    }
					} else {
					    if (joo.getString("typename").equals("String")) {
						joo.put(joo.getString("erpname"), "'" + entry.getValue() + "'");
					    }
					    if (joo.getString("typename").equals("int")) {
						joo.put(joo.getString("erpname"), entry.getValue());
					    }
					    if (joo.getString("typename").equals("date")) {
						joo.put(joo.getString("erpname"), entry.getValue());
					    }
					}
					joo.remove("fiexd");
					joo.remove("typename");
					joo.remove("erpname");
					break;
				    }
				}
			    }
			    jjo.remove("keyvalue");
			    jjo.put("keyvalue", jo);
			    jjaaa.remove(i);
			    jjaaa.add(i, jjo);
			}
			j.add(jjaaa);
		    }
		} else {
		    System.out.println(ele.getName() + ": " + ele.getText());
		    if (!ele.getName().equals("Type")) {
			for (int i = 0; i < jj.size(); i++) {
			    JSONObject jjo = jj.getJSONObject(i);
			    JSONArray jo = JSONArray.parseArray(jjo.getString("keyvalue"));
			    int aaa = 0;
			    for (int k = 0; k < jo.size(); k++) {
				if (aaa == 1) {
				    break;
				}
				JSONObject joo = jo.getJSONObject(k);
				Set<Entry<String, Object>> set = joo.entrySet();
				Iterator<Entry<String, Object>> it = set.iterator();
				while (it.hasNext()) {
				    Entry<String, Object> entry = it.next();
				    if (entry.getKey().equals("cappname")) {
					if (entry.getValue().equals(ele.getName())) {
					    aaa = 1;
					    if (joo.getString("typename").equals("String")) {
						joo.put(joo.getString("erpname"), "'" + ele.getText() + "'");
					    }
					    if (joo.getString("typename").equals("int")) {
						joo.put(joo.getString("erpname"), ele.getText());
					    }
					    if (joo.getString("typename").equals("date")) {
						joo.put(joo.getString("erpname"), ele.getText());
					    }
					    joo.remove("typename");
					    joo.remove("erpname");
					    joo.remove("cappname");
					    break;
					}
				    }
				}
			    }
			    jjo.remove("keyvalue");
			    jjo.put("keyvalue", jo);
			    jj.remove(i);
			    jj.add(jjo);
			}
		    }
		}

	    }
	    // 节点遍历完 处理sql及固定值
	    for (int i = 0; i < jj.size(); i++) {
		JSONObject jjo = jj.getJSONObject(i);
		JSONArray jo = JSONArray.parseArray(jjo.getString("keyvalue"));
		for (int k = 0; k < jo.size(); k++) {
		    JSONObject joo = jo.getJSONObject(k);
		    Set<Entry<String, Object>> set = joo.entrySet();
		    Iterator<Entry<String, Object>> it = set.iterator();
		    while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			if (entry.getKey().equals("sql")) {
			    Pattern p = Pattern.compile("\\[(.*?)\\]");
			    Matcher m = p.matcher(entry.getValue().toString());
			    ArrayList<String> strs = new ArrayList<String>();
			    while (m.find()) {
				strs.add(m.group(1));
			    }
			    String sql = entry.getValue().toString();
			    for (int l = 0; l < jo.size(); l++) {
				JSONObject jooo = jo.getJSONObject(l);
				Set<Entry<String, Object>> sets = jooo.entrySet();
				Iterator<Entry<String, Object>> its = sets.iterator();
				while (its.hasNext()) {
				    Entry<String, Object> entrys = its.next();
				    for (String s : strs) {
					if (entrys.getKey().equals(s)) {
					    sql = sql.replace("[" + s + "]", entrys.getValue().toString());
					}
				    }
				    if (sql.indexOf("[") == -1) {
					break;
				    }
				}
				if (sql.indexOf("[") == -1) {
				    break;
				}

			    }
			    JSONObject ss = MapperDao.exesql(sql);
			    Set<Entry<String, Object>> setss = ss.entrySet();
			    Iterator<Entry<String, Object>> itss = setss.iterator();
			    while (itss.hasNext()) {
				JSONObject jjooo = new JSONObject();
				Entry<String, Object> entryss = itss.next();
				jjooo.put(entryss.getKey(), entryss.getValue());
				jo.add(jjooo);
			    }
			    jo.remove(k);
			    k = -1;
			}
			if (entry.getKey().equals("fiexd")) {
			    if (entry.getValue().equals("[nowtime]")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (joo.getString("typename").equals("String")) {
				    joo.put(joo.getString("erpname"), "'" + sdf.format(new Date()) + "'");
				}
				if (joo.getString("typename").equals("int")) {
				    joo.put(joo.getString("erpname"), sdf.format(new Date()));
				}
				if (joo.getString("typename").equals("date")) {
				    joo.put(joo.getString("erpname"), sdf.format(new Date()));
				}
			    } else {
				if (joo.getString("typename").equals("String")) {
				    joo.put(joo.getString("erpname"), "'" + entry.getValue() + "'");
				}
				if (joo.getString("typename").equals("int")) {
				    joo.put(joo.getString("erpname"), entry.getValue());
				}
				if (joo.getString("typename").equals("date")) {
				    joo.put(joo.getString("erpname"), entry.getValue());
				}
			    }
			    joo.remove("fiexd");
			    joo.remove("typename");
			    joo.remove("erpname");
			    break;
			}
		    }
		}
		jjo.remove("keyvalue");
		jjo.put("keyvalue", jo);
		jj.remove(i);
		jj.add(jjo);
		j.add(jj);
	    }
	}
	System.out.println(j.toString());
	return j;
    }

    // 处理sql值
    public static JSONObject exesql(String sql) {
	Connection conn = BaseConnection.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	    ps = conn.prepareStatement(sql);
	    rs = ps.executeQuery();
	    // 获取列数
	    ResultSetMetaData metaData = rs.getMetaData();
	    int columnCount = metaData.getColumnCount();
	    // 遍历ResultSet中的每条数据
	    while (rs.next()) {
		JSONObject jsonObj = new JSONObject();
		// 遍历每一列
		for (int i = 1; i <= columnCount; i++) {
		    String columnName = metaData.getColumnLabel(i);
		    String value = rs.getString(columnName);
		    System.out.println(metaData.getColumnClassName(i));
		    if (metaData.getColumnClassName(i).endsWith("String")) {
			value = "'" + rs.getString(columnName) + "'";
		    }
		    if (metaData.getColumnClassName(i).endsWith("Integer")) {
			value = rs.getString(columnName);
		    }
		    if (metaData.getColumnClassName(i).endsWith("Date")) {
			value = rs.getString(columnName);
		    }
		    jsonObj.put(columnName, value);
		}
		return jsonObj;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    BaseConnection.closeRes(rs, ps, conn);
	}
	return null;
    }

    // 通过 Type 返回 JSONArray
    public static JSONArray querymap(String type, String level) {
	JSONArray j = new JSONArray();
	Connection conn = BaseConnection.getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
	try {
	    String ss1 = "select * from mapper where type = '" + type + "' and tablelevel = " + level + " order by erptable desc";
	    ps = conn.prepareStatement(ss1);
	    rs = ps.executeQuery();

	    JSONObject jj = new JSONObject(); // {"erptable":"book","keyvalue":[{"erpname":"book","typename":"String",},{},{},{},{}]}
	    JSONArray jjj = new JSONArray();
	    for (int i = 0; rs.next(); i++) {
		JSONObject jjjj = new JSONObject();
		if (i == 0) {
		    jj.put("erptable", rs.getString("erptable"));

		} else {
		    if (!jj.getString("erptable").equals(rs.getString("erptable"))) {
			jj.put("keyvalue", jjj);
			j.add(jj);
			jj = new JSONObject();
			jjj = new JSONArray();
			jj.put("erptable", rs.getString("erptable"));
		    }
		}
		jjjj.put("erpname", rs.getString("erpname"));
		jjjj.put("typename", rs.getString("typename"));
		if (!"".equals(rs.getString("cappname")) && rs.getString("cappname") != null) {
		    jjjj.put("cappname", rs.getString("cappname"));
		}
		if (!"".equals(rs.getString("fiexd")) && rs.getString("fiexd") != null) {
		    jjjj.put("fiexd", rs.getString("fiexd"));
		}
		if (!"".equals(rs.getString("sql")) && rs.getString("sql") != null) {
		    jjjj.put("sql", rs.getString("sql"));
		}
		jjj.add(jjjj);
	    }
	    jj.put("keyvalue", jjj);
	    j.add(jj);
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    BaseConnection.closeRes(rs, ps, conn);
	}
	return j;
    }
}
