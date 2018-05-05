package com.example.chenchongkang.memeapplication.api;

import com.example.chenchongkang.memeapplication.util.StreamTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by chenchongkang on 2018/4/14.
 */

public class HttpHandler {
    /**
     *
     * 通过Get方式获取HTTP服务器数据
     *
     **/

    public static String executeHttpGet(String path) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(10000); // 设置超时时间
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET"); // 设置获取信息方式
            conn.setRequestProperty("Charset", "UTF-8"); // 设置接收数据编码格式

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return convertStreamToString(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     *
     * 通过Post方式获取HTTP服务器数据
     *
     **/
    public static String executeHttpPost(String path,String content) {

        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(10000); // 设置超时时间
            conn.setReadTimeout(10000);
            conn.setRequestMethod("POST"); // 设置获取信息方式
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
//            conn.setRequestProperty("Content-Length","");
            if (content != null) {
                conn.getOutputStream().write(content.getBytes());
            }
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                return StreamTools.readStream(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }



}
