package com.aefottt.seventhwork.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aefottt.seventhwork.R;
import com.aefottt.seventhwork.data.bean.MainArticle;
import com.aefottt.seventhwork.ui.activity.ContentActivity;
import com.aefottt.seventhwork.ui.adapter.RvMainAdapter;
import com.aefottt.seventhwork.util.HttpCallbackListener;
import com.aefottt.seventhwork.util.HttpUtil;
import com.aefottt.seventhwork.util.Utility;

import java.util.ArrayList;
import java.util.Objects;

public class MainFragment extends Fragment {

    private ProgressDialog progressDialog;

    private RvMainAdapter rvMainAdapter;

    private final ArrayList<MainArticle> mList = new ArrayList<>();

    private static final int GET_ARTICLE_HANDLE = 3;

    private final Handler getArticleHandler = new Handler(msg -> {
        if (msg.what == GET_ARTICLE_HANDLE){
            Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                mList.clear();
                ArrayList<MainArticle> getList = Utility.handleGetArticle(msg.obj.toString());
                mList.addAll(getList);
                rvMainAdapter.notifyDataSetChanged();
                closeProgressDialog();
            });
        }
        return true;
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        showProgressDialog();
        RecyclerView rvMain = view.findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMainAdapter = new RvMainAdapter(mList);
        getArticleList();
        rvMain.setAdapter(rvMainAdapter);
        rvMainAdapter.setOnItemClickListener((view1, position) -> {
            Intent intent = new Intent(getActivity(), ContentActivity.class);
            intent.putExtra("url", mList.get(position).getUrl());
            startActivity(intent);
        });
        return view;
    }

    private void getArticleList() {
        HttpUtil.sendHttpGetRequest("https://www.wanandroid.com/article/list/0/json", new HttpCallbackListener() {
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.what = GET_ARTICLE_HANDLE;
                message.obj = response;
                getArticleHandler.sendMessage(message);
            }
            @Override
            public void onFailure(Exception e) {
                closeProgressDialog();
                Toast.makeText(getActivity(), "加载失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}