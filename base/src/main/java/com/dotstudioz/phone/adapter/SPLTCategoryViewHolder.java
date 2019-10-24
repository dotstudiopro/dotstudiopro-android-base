package com.dotstudioz.phone.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.phone.R;

public class SPLTCategoryViewHolder extends SPLTContentViewHolder {

    private SPLTCategory mCategory = null;


    public void setSize(int width, int height){
        this.cardViewwidth = width;
        this.cardViewHeight = height;
    }


    public SPLTCategoryViewHolder(View itemView) {
        super(itemView);


    }
    public void setCategory(SPLTCategory category){
        this.mCategory = category;
        updateUI();


    }

    public void updateUI(){
        if(this.textViewPrimaryTitle != null){
            this.textViewPrimaryTitle.setVisibility(View.GONE);
            String categoryName = this.mCategory.getName();
            this.textViewPrimaryTitle.setText(categoryName!=null ? categoryName : "");
        }
        if(this.textViewSecondaryTitle != null){
            this.textViewSecondaryTitle.setVisibility(View.GONE);
            String categoryDesc = this.mCategory.getDescription();
            this.textViewSecondaryTitle.setText(categoryDesc!=null ? categoryDesc : "");
        }

        if(this.imageView != null){
            this.imageView.setVisibility(View.VISIBLE);
            this.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            this.imageView.setURL(this.imageView.getContext(),this.mCategory.getPoster(), this.cardViewwidth, this.cardViewHeight);

        }
        if(this.cardView != null){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cardViewwidth, cardViewHeight);
            this.cardView.setLayoutParams(params);
        }
    }

}
