
package com.jishang.bimeng.utils.Internet;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author 用post和get请求的工具类
 */
public class MyHttpRequest {
    public static String getHttpGet(String url, List<NameValuePair> params) {
        HttpClient client = new DefaultHttpClient();
        HttpGet post = new HttpGet(url);
        String result = null;
        HttpEntity entity = null;
        HttpResponse response = null;

        try {
            if (params != null) {
                entity = new UrlEncodedFormEntity(params, "utf-8");
                ((HttpResponse)post).setEntity(entity);
            }
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {

                result = EntityUtils.toString(response.getEntity());
            } else {
                result = "-1";
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "-1";
        } catch (IOException e) {
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * @param url
     * @param params
     * @return 带head请求头认证的post请求
     */
    public static String getHttpPostBasic(String url, List<BasicNameValuePair> params, String token) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String result = null;
        HttpEntity entity = null;
        HttpResponse response = null;
        try {
            entity = new UrlEncodedFormEntity(params, "utf-8");
            /* 添加请求头 */
            post.addHeader("Authorization", "Bearer" + " " + token);// 认证token
            // post.addHeader("Content-Type", "application/json");
            post.addHeader("User-Agent", "imgfornote");
            /* 添加请求头 */

            post.setEntity(entity);
            response = client.execute(post);
            String st = response.getStatusLine().getStatusCode() + "";
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
            } else {
                result = st;
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * @param url
     * @param params
     * @return 不带head请求头认证的post请求
     */
    public static String getHttpPost(String url, List<BasicNameValuePair> params) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        String result = null;
        HttpEntity entity = null;
        HttpResponse response = null;
        try {
            entity = new UrlEncodedFormEntity(params, "utf-8");

            post.setEntity(entity);
            response = client.execute(post);
            String st = response.getStatusLine().getStatusCode() + "";
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
            } else {
                result = st;
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
