package com.dotstudioz.phone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTChannel;
import com.dotstudioz.model.SPLTVideo;

import java.util.List;

public class SPLTHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public int TYPE_CHANNEL = 1;
    public int TYPE_VIDEO = 2;

    private String TAG ="SPLTHomeAdapter";
    public List<SPLTCategory> mCategoryList;
    public Context context;
    public int cardViewHeight = 120;
    public int cardViewwidth = 120;
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

    // for category list
    public SPLTHomeAdapter(Context context, List<SPLTCategory> categoryList, int cardViewHeight, int cardViewwidth){
        //for category adapter
        this.context = context;
        this.mCategoryList = categoryList;
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

        int type = getItemViewType(position);
        Log.d(TAG, "onBindViewHolder: "+type);

        SPLTHomeViewHolder spltHomeViewHolder = (SPLTHomeViewHolder)viewHolder;
        spltHomeViewHolder.setCategory(this.mCategoryList.get(position));

        spltHomeViewHolder.setCallback(new SPLTHomeViewHolder.Callback() {
            @Override
            public void onHomeChannelToggleButtonClick(SPLTChannel channel, boolean checked) {
                onHomeChannelToggleButtonClicked(channel,checked);
            }

            @Override
            public void onHomeChannelClick(SPLTChannel channel) {
                onHomeChannelClicked(channel);
            }

            @Override
            public void onHomeViewAllCategoryClick(SPLTCategory category) {
                onHomeViewAllCategoryClicked(category);
            }

            @Override
            public void onHomeVideoClick(SPLTVideo video) {
                onHomeVideoClicked(video);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if(mCategoryList.get(position).channels != null){
            if(mCategoryList.get(position).channels.size() == 1){
                return TYPE_VIDEO;
            }else {
                return TYPE_CHANNEL;
            }
        }
        return TYPE_CHANNEL;
    }

    @Override
    public int getItemCount() {
        return this.mCategoryList.size();
    }
    public void refreshCategoryList(List<SPLTCategory> adapterList){
        this.mCategoryList.clear();
        this.mCategoryList.addAll(adapterList);
        this.notifyDataSetChanged();
    }
    private void onHomeChannelToggleButtonClicked(SPLTChannel channel, boolean checked){
        if(this.callback != null){
            this.callback.onHomeChannelToggleButtonClick(channel,checked);
        }
    }
    private void onHomeChannelClicked(SPLTChannel channel){
        if(this.callback != null){
            this.callback.onHomeChannelClick(channel);
        }
    }
    private void onHomeViewAllCategoryClicked(SPLTCategory category){
        if(this.callback != null){
            this.callback.onHomeViewAllCategoryClick(category);
        }
    }
    private void onHomeVideoClicked(SPLTVideo video){
        if(this.callback != null){
            this.callback.onHomeVideoClick(video);
        }
    }
}
