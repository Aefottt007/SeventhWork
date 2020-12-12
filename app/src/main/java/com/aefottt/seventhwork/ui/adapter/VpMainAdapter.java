package com.aefottt.seventhwork.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VpMainAdapter extends FragmentPagerAdapter {

    private final ArrayList<Fragment> fList;
    private final String[] titles;

    public VpMainAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Fragment> fList, String[] titles) {
        super(fm, behavior);
        this.fList = fList;
        this.titles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fList.get(position);
    }

    @Override
    public int getCount() {
        return fList.size();
    }
}
