package com.example.dell.chihuobao.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    // 创建HttpClient对象

    public static HttpClient httpClient = new DefaultHttpClient();
    public static final String BASE_URL = "http://10.6.12.82:8080/chb/user/";
 /* http://10.6.12.60:8080/chb/user/login.do?usernamexia&password=dsf
  http://10.6.12.60:8080/chb/user/login.do?username=xia&password=dsf*/



    /**
     * 获取指定URL的响应字符串
     * @param urlString
     * @return
     */
    public static String getURLResponse(final String urlString) {

        HttpURLConnection conn = null; //连接对象
        InputStream is = null;
        StringBuilder resultData = new StringBuilder();
        try {
            URL url = new URL(urlString); //URL对象
            conn = (HttpURLConnection) url.openConnection(); //使用URL打开一个链接
            conn.setDoInput(true); //允许输入流，即允许下载
            conn.setDoOutput(true); //允许输出流，即允许上传
            conn.setUseCaches(false); //不使用缓冲
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(8000);
            conn.setRequestMethod("GET"); //使用get请求
            is = conn.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData.append(inputLine);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return resultData.toString();
    }

    /**
     *
     * @param url
     *            发送请求的URL
     * @param params
     *            请求参数
     * @return 服务器响应字符串
     * @throws Exception
     */
    public static String postRequest(String url, Map<String, String> rawParams)
            throws Exception {
        // 创建HttpPost对象。
        HttpPost post = new HttpPost(url);
        // 如果传递参数个数比较多的话可以对传递的参数进行封装
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (String key : rawParams.keySet()) {
            // 封装请求参数
            params.add(new BasicNameValuePair(key, rawParams.get(key)));
        }
        // 设置请求参数
        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        // 发送POST请求
        HttpResponse httpResponse = httpClient.execute(post);
        // 如果服务器成功地返回响应
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            // 获取服务器响应字符串
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }
        return null;
    }
}