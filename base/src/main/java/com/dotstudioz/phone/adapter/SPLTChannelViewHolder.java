package com.dotstudioz.phone.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTChannel;
import com.dotstudioz.phone.component.home.channels.SPLTChannelsFragment;

import java.util.Observable;
import java.util.Observer;

public class SPLTChannelViewHolder extends SPLTContentViewHolder {

    private SPLTChannel mChannel = null;
    private Callback callback;
    public interface Callback {
        void onToggleButtonClick(SPLTChannel channel, boolean checked);
        void onChannelClick(SPLTChannel channel);
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setSize(int width, int height){
        this.cardViewwidth = width;
        this.cardViewHeight = height;
    }

    public SPLTChannelViewHolder(View itemView) {
        super(itemView);
        initialize();
    }
    public void toggleButtonSetImage(View v){

    }
    private void initialize(){
        if(toggleButton != null){
            this.toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleButtonSetImage(v);
                    if(callback != null){
                        callback.onToggleButtonClick(mChannel, ((ToggleButton)v).isChecked());
                    }
                }
            });
        }
        if(this.cardView != null){
            this.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null){
                        callback.onChannelClick(mChannel);
                    }
                }
            });
        }
    }

    public void setChannel(final SPLTChannel channel){
        //remove kvo on mChannel
        if(this.mChannel != null && this.mToggleObservel != null){
            this.mChannel.deleteObserver(mToggleObservel);
        }
        this.mChannel = channel;
        //add kvo on mChannel
        this.mChannel.addObserver(mToggleObservel);

        updateUI();

        if(toggleButton != null){
            this.toggleButton.setVisibility(View.VISIBLE);
            initialize();

        }
    }

    Observer mToggleObservel = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            updateToggleButtonUI();
        }
    };

    private void updateUI(){
        if(this.textViewPrimaryTitle != null){
            this.textViewPrimaryTitle.setVisibility(View.GONE);
            String categoryName = this.mChannel.getCompany();
            categoryName = "SPLTChannelViewHolder";
            this.textViewPrimaryTitle.setText(categoryName!=null ? categoryName : "");
        }
        if(this.textViewSecondaryTitle != null){
            this.textViewSecondaryTitle.setVisibility(View.GONE);
            String categoryDesc = this.mChannel.getTitle();
            this.textViewSecondaryTitle.setText(categoryDesc!=null ? categoryDesc : "");
        }

        if(this.imageView != null){
            this.imageView.setVisibility(View.VISIBLE);
            this.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            this.imageView.setURL(this.imageView.getContext(),this.mChannel.getPoster(),cardViewwidth, cardViewHeight);

        }

        if(this.cardView != null){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cardViewwidth, cardViewHeight);
            this.cardView.setLayoutParams(params);
        }

        this.updateToggleButtonUI();

    }
    private void updateToggleButtonUI(){
        if(this.toggleButton != null){
            if(this.mChannel.isInMyList()){
                this.toggleButton.setChecked(true);
            }else {
                this.toggleButton.setChecked(false);
            }
        }
    }
}
