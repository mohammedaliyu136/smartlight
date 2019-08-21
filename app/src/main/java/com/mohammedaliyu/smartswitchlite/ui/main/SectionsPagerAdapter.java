package com.mohammedaliyu.smartswitchlite.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mohammedaliyu.smartswitchlite.Light;
import com.mohammedaliyu.smartswitchlite.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private List<Light> mLight = new ArrayList<>();

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //Light("Living room Light", "1", true, "12345678") id#status#light_id#name
        String str = String.valueOf(mLight.get(position).getId())+"#"+
                mLight.get(position).getStatus().toString()+"#"+
                mLight.get(position).getLight_id()+"#"+
                mLight.get(position).getName();
        return PlaceholderFragment.newInstance(str);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //return mContext.getResources().getString(TAB_TITLES[position]);
        return mLight.get(position).getName();
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return mLight.size();
    }

    public void setLights(List<Light> lights) {
        this.mLight = lights;
        notifyDataSetChanged();
    }
}