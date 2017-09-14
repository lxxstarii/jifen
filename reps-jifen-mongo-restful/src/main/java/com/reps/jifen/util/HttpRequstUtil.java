package com.reps.jifen.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	 * post请求
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getPostUrlResponse(String path) throws Exception {
		String result = ""; 
		PrintWriter out = null;
	    BufferedReader in = null;
		URL url = new URL(path);
		URLConnection conn = url.openConnection();
		 // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(url);
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
		System.out.println(getPostUrlResponse("http://localhost:8888/uapi/teacherpointsassign/save?access_token=6630a75cd299cbf071ad84db783ed3fe"));
	}

}
