package com.dotstudioz.phone.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dotstudioz.phone.baseclasses.SPLTBaseImageView;

public class SPLTContentViewHolder extends RecyclerView.ViewHolder {

    protected SPLTBaseImageView imageView;
    protected TextView textViewPrimaryTitle;
    protected TextView textViewSecondaryTitle;
    protected CardView cardView;
    protected ToggleButton toggleButton;
    protected ProgressBar progressView;
    protected RelativeLayout relativeTopView;

    protected int cardViewwidth = 0;
    protected int cardViewHeight = 0;


    public SPLTContentViewHolder(@NonNull View itemView) {
        super(itemView);
    }


}
