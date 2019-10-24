package com.dotstudioz.phone.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.dotstudioz.model.SPLTVideo;


public class SPLTVideoViewHolder extends SPLTContentViewHolder {
    private String TAG = "SPLTVideoViewHolder";

    //public CardView cardView;
    //public SPLTBaseImageView imageView;
    //public TextView textViewPrimaryTitle;
    //public ProgressBar progressView;
    protected SPLTVideo mVideo;

    private Callback callback;
    public interface Callback {
        void onVideoClick(SPLTVideo video);
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public SPLTVideoViewHolder(View itemView) {
        super(itemView);
    }

    public void setSize(int width, int height){
        this.cardViewwidth = width;
        this.cardViewHeight = height;
    }

    public void setVideo(SPLTVideo video){
        this.mVideo = video;
        updateUI();
    }

    private void updateUI() {
        if(this.textViewPrimaryTitle != null){
            this.textViewPrimaryTitle.setVisibility(View.VISIBLE);
            String categoryName = this.mVideo.getVideoTitle();
            this.textViewPrimaryTitle.setText(categoryName!=null ? categoryName : "");
            this.textViewPrimaryTitle.setMaxWidth(cardViewwidth);
        }

        if(this.imageView != null){
            this.imageView.setVisibility(View.VISIBLE);
            this.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            this.imageView.setURL(this.imageView.getContext(),this.mVideo.getThumb(), this.cardViewwidth, this.cardViewHeight);
            this.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null){
                        callback.onVideoClick(mVideo);
                    }
                }
            });

        }

        if(this.progressView != null){
            if(this.mVideo.getVideoDuration() != 0 && this.mVideo.getVideoPausedPoint() != 0){
                this.progressView.setVisibility(View.VISIBLE);
                this.progressView.setMax(this.mVideo.getVideoDuration());
                this.progressView.setProgress(mVideo.getVideoPausedPoint());
            }else {
                this.progressView.setVisibility(View.GONE);
            }




        }
        if(this.relativeTopView != null){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardViewwidth, cardViewHeight+35);
            relativeTopView.setLayoutParams(params);
        }
        /*if(this.cardView != null){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cardViewwidth, cardViewHeight);
            this.cardView.setLayoutParams(params);
        }*/

    }

}
