package com.dotstudioz.phone.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTChannel;
import com.dotstudioz.model.SPLTParentChannel;
import com.dotstudioz.model.SPLTPlaylistChannel;
import com.dotstudioz.model.SPLTVideo;
import com.dotstudioz.model.SPLTVideoChannel;

import java.util.ArrayList;
import java.util.List;

public class SPLTHomeViewHolder  extends RecyclerView.ViewHolder {
    private String TAG = "SPLTHomeViewHolder";

    public TextView textViewPrimaryTitle;
    public TextView textViewSecondaryTitle;
    public RecyclerView recyclerView;

    /*Category List*/
    public List<SPLTChannel> mChannelList = new ArrayList<>();
    public SPLTChannelAdapter homeChannelAdapter;

    /*Video List*/
    public List<SPLTVideo> mVideoList = new ArrayList<>();
    public SPLTVideoAdapter homeVideoAdapter;


    public SPLTCategory category;
    protected int cardViewwidth = 0;
    protected int cardViewHeight = 0;

    private Callback callback;
    public interface Callback {
        void onHomeChannelToggleButtonClick(SPLTChannel channel, boolean checked);
        void onHomeChannelClick(SPLTChannel channel);
        void onHomeViewAllCategoryClick(SPLTCategory category);
        void onHomeVideoClick(SPLTVideo video);
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

    public SPLTHomeViewHolder(@NonNull View itemView) {
        super(itemView);
        initialize();

    }
    public void initialize(){
        if(this.homeChannelAdapter != null){
            this.homeChannelAdapter.setCallback(new SPLTChannelAdapter.Callback() {
                @Override
                public void onChannelToggleButtonClick(SPLTChannel channel, boolean checked) {
                    if(callback != null){
                        callback.onHomeChannelToggleButtonClick(channel, checked);
                    }
                }

                @Override
                public void onChannelClick(SPLTChannel channel) {
                    if(callback != null){
                        callback.onHomeChannelClick(channel);
                    }
                }
            });
        }
        if(this.homeVideoAdapter != null){
            this.homeVideoAdapter.setCallback(new SPLTVideoAdapter.Callback() {
                @Override
                public void onVideoClick(SPLTVideo video) {
                    if(callback != null){
                        callback.onHomeVideoClick(video);
                    }
                }
            });


        }

        if(this.textViewSecondaryTitle != null){
            this.textViewSecondaryTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback != null){
                        callback.onHomeViewAllCategoryClick(category);
                    }
                }
            });


        }
    }
    public void setSecondaryTitle(TextView textViewSecondaryTitle){}
    public void setCategory(SPLTCategory categoryData){
        this.category = categoryData;
        if(this.textViewPrimaryTitle != null){
            this.textViewPrimaryTitle.setVisibility(View.VISIBLE);
            String categoryName = this.category.getName();
            this.textViewPrimaryTitle.setText(categoryName!=null ? categoryName : "");
        }
        if(this.textViewSecondaryTitle != null){
            this.textViewSecondaryTitle.setVisibility(View.VISIBLE);
            setSecondaryTitle(this.textViewSecondaryTitle);
            //this.textViewSecondaryTitle.setText("View All");
        }
        if(this.category.channels != null){
            if(this.category.channels.size()>1){
                this.setHomeChannelAdapter(this.category.channels);
            }else {
                if(this.category.channels.size()>0){
                    SPLTChannel channel = this.category.channels.get(0);
                    if(channel instanceof SPLTPlaylistChannel){
                        SPLTPlaylistChannel playlistChannel = (SPLTPlaylistChannel)channel;
                        this.setHomeVideoViewAdapter(playlistChannel.getPlayListVideos());
                    }else if(channel instanceof SPLTParentChannel){ //list of video
                        SPLTParentChannel parentChannel = (SPLTParentChannel)channel;
                        this.setHomeVideoViewAdapter(parentChannel.getPlayListVideos());
                    }else if(channel instanceof SPLTVideoChannel){
                        SPLTVideoChannel videoChannel = (SPLTVideoChannel)channel;
                        this.setHomeVideoViewAdapter(videoChannel.getPlayListVideos());
                    }
                }
            }


        }
        initialize();
    }

    private void setHomeChannelAdapter(List<SPLTChannel> channelList){
        if(this.homeChannelAdapter != null){
            this.mChannelList = channelList;
            this.homeChannelAdapter.refreshChannelList(this.mChannelList);
            initialize();
        }
    }

    private void setHomeVideoViewAdapter(ArrayList<SPLTVideo> videos){
        if(this.homeVideoAdapter != null){
            this.mVideoList = videos;
            this.homeVideoAdapter.refreshVideoList(this.mVideoList);
        }
    }



}
