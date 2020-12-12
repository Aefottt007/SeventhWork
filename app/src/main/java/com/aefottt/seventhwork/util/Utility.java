package com.aefottt.seventhwork.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.aefottt.seventhwork.data.bean.MainArticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Utility {
    /**
     *检查是否登录成功
     */
    public static boolean handleLoginResponse(String response){
        boolean result = false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            int errorCode = jsonObject.getInt("errorCode");
            if (errorCode == 0) result = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 检查是否注册成功
     */
    public static boolean handleRegisterResponse(String response){
        boolean result = false;
        try {
            JSONObject jsonObject = new JSONObject(response);
            int errorCode = jsonObject.getInt("errorCode");
            if (errorCode == 0) result = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 处理得到的文章内容
     */
    public static ArrayList<MainArticle> handleGetArticle(String response){
        ArrayList<MainArticle> mList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray datas = data.getJSONArray("datas");
            for (int i = 0;i <datas.length();i++){
                JSONObject articleData = datas.getJSONObject(i);
                mList.add(new MainArticle(articleData.getString("shareUser"), articleData.getString("title"), "", articleData.getString("chapterName"), articleData.getString("niceDate"), articleData.getString("link")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }
}
