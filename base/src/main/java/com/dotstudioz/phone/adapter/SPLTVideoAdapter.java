package com.dotstudioz.phone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTVideo;

import java.util.ArrayList;
import java.util.List;

public class SPLTVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public String TAG ="SPLTVideoAdapter";
    public List<SPLTVideo> mVideoList = new ArrayList<>();
    public Context context;
    public int cardViewHeight = 120;
    public int cardViewwidth = 120;

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

    // for video list
    public SPLTVideoAdapter(Context context, List<SPLTVideo> videoList,  int cardViewwidth, int cardViewHeight){
        //for category adapter
        this.context = context;
        this.mVideoList = videoList;
        this.cardViewHeight = cardViewHeight;
        this.cardViewwidth = cardViewwidth;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        SPLTVideoViewHolder videoViewHolder = (SPLTVideoViewHolder)viewHolder;
        videoViewHolder.setVideo(mVideoList.get(position));
        videoViewHolder.setCallback(new SPLTVideoViewHolder.Callback() {
            @Override
            public void onVideoClick(SPLTVideo video) {
                onVideoClicked(video);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public void refreshVideoList(List<SPLTVideo> adapterList){
        this.mVideoList.clear();
        this.mVideoList.addAll(adapterList);
        this.notifyDataSetChanged();
    }
    private void onVideoClicked(SPLTVideo video){
        if(this.callback != null){
            this.callback.onVideoClick(video);
        }
    }
}
