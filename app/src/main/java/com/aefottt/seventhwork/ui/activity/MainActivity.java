package com.aefottt.seventhwork.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.aefottt.seventhwork.R;
import com.aefottt.seventhwork.ui.adapter.VpMainAdapter;
import com.aefottt.seventhwork.ui.fragment.MainFragment;
import com.aefottt.seventhwork.ui.fragment.SquareFragment;
import com.aefottt.seventhwork.util.RomUtils;
import com.aefottt.seventhwork.util.StatusBarColorUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView ivMainUser;
    private EditText etMainSearch;
    private ViewPager vpMain;
    private TabLayout tlMain;
    private ArrayList<Fragment> fList;

    private final String[] titles = { "广场", "首页", "导航", "问答", "体系", "项目", "公众号", "工具" };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarColorUtil.setStatusBarColor(this);
        setContentView(R.layout.activity_main);
        initView();
        setTabLayout();
        setVp();
        ivMainUser.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
    }

    /**
     * 设置ViewPager
     */
    private void setVp() {
        VpMainAdapter vpAdapter = new VpMainAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fList, titles);
        vpMain.setAdapter(vpAdapter);
        vpMain.setCurrentItem(1);
        tlMain.setupWithViewPager(vpMain);
    }

    /**
     * 设置TabLayout
     */
    private void setTabLayout() {
        for (String title : titles){
            tlMain.addTab(tlMain.newTab().setText(title));
        }
        //添加Fragment
        fList.add(new SquareFragment());
        fList.add(new MainFragment());
        //。。。。待添加
    }

    private void initView() {
        ivMainUser = findViewById(R.id.iv_main_user);
        etMainSearch = findViewById(R.id.et_main_search);
        tlMain = findViewById(R.id.tl_main);
        vpMain = findViewById(R.id.vp_main);
        fList = new ArrayList<>();
    }

    private static long firstTime = 0;
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000){
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        }else{
            finish();
        }
    }
}