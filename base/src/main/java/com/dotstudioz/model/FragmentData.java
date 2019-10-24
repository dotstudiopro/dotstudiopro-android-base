package com.dotstudioz.model;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

public class FragmentData {

    public Fragment fragment;
    public Drawable drawable;
    public String title;

    public FragmentData(Fragment fragment, Drawable drawable, String title) {
        this.fragment = fragment;
        this.drawable = drawable;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
