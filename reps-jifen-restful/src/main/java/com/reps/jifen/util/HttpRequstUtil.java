package com.reps.jifen.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

public class HttpRequstUtil {
	
	/**
	 * get请求
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getGetUrlResponse(String path) throws Exception {
		String result = ""; 
		BufferedReader in = null;
		URL realUrl = new URL(path);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        connection.setRequestProperty("Charset", "UTF-8");
         // 建立实际的连接
        connection.connect();
        // 定义 BufferedReader输入流来读取URL的响应
        in = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        in.close();
        JSONObject jsonObject = (JSONObject) JSONObject.fromObject(result);
        return jsonObject;
	}
	
	/**
	 * post提交
	 * @param path 接口对应地址
	 * @param param 参数
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getPostUrlResponse(String path, String param) throws Exception {
		String result = ""; 
		DataOutputStream  out = null;
	    BufferedReader in = null;
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.addRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		// 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        out = new DataOutputStream(conn.getOutputStream());
        // 发送请求参数
        out.write(param.getBytes());
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        out.close();
        in.close();
        JSONObject jsonObject = (JSONObject) JSONObject.fromObject(result);
        return jsonObject;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getPostUrlResponse("http://localhost:8888/uapi/pointsexchange/save", "access_token=14096c1993ac5bfd90ef3779b16a6e78&personId=1"));
	}

}
