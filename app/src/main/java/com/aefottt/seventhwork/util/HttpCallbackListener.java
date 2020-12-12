package com.aefottt.seventhwork.util;

public interface HttpCallbackListener {
    void onResponse(String response);
    void onFailure(Exception e);
}
