package com.aefottt.seventhwork.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {

    /**
     * 发送服务器GET请求
     */
    public static void sendHttpGetRequest(String address, HttpCallbackListener listener) {
        new Thread(
                () -> {
                    HttpURLConnection connection = null;
                    try {
                        URL url = new URL(address);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        connection.connect();
                        //把获取到的输入流转换为String
                        String response = StreamToString(connection.getInputStream());
                        if (listener != null) {
                            listener.onResponse(response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (listener != null) {
                            listener.onFailure(e);
                        }
                    }
                    finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                }
        ).start();
    }

    /**
     * 发送服务器POST请求
     */
    public static void sendHttpPostRequest(String address, Map<String, String> params, HttpCallbackListener listener) {
        new Thread(
                () -> {
                    HttpURLConnection connection = null;
                    try {
                        URL url = new URL(address);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.connect();
                        //拼接字符串
                        StringBuilder data = new StringBuilder();
                        for (String key : params.keySet()){
                            data.append(key).append("=").append(params.get(key)).append("&");
                        }
                        //开启输入流
                        OutputStream outputStream = connection.getOutputStream();
                        outputStream.write(data.substring(0, data.length() - 1).getBytes());    //去除最后一个&
                        String response = StreamToString(connection.getInputStream());
                        if (listener != null){
                            listener.onResponse(response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (listener != null){
                            listener.onFailure(e);
                        }
                    } finally {
                        if (connection != null){
                            connection.disconnect();
                        }
                    }
                }
        ).start();
    }

    /**
     *将输入流转换为String字符串
     */
    public static String StreamToString(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String lines;
        while ((lines = reader.readLine()) != null){
            builder.append(lines);
        }
        return builder.toString();
    }
}
