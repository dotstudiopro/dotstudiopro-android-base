package com.dotstudioz.phone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTChannel;

import java.util.List;

public class SPLTChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    public String TAG ="SPLTChannelAdapter";
    public List<SPLTChannel> mChannelList;
    public Context context;
    public int cardViewHeight = 120;
    public int cardViewwidth = 120;

    private Callback callback;
    public interface Callback {
        void onChannelToggleButtonClick(SPLTChannel channel, boolean checked);
        void onChannelClick(SPLTChannel channel);
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    // for channel list
    public SPLTChannelAdapter(Context context, List<SPLTChannel> channelList, int cardViewwidth, int cardViewHeight){
        // for channel adapter callback
        this.context = context;
        this.mChannelList = channelList;
        this.cardViewwidth = cardViewwidth;
        this.cardViewHeight = cardViewHeight;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        SPLTChannelViewHolder spltChannelViewHolder = (SPLTChannelViewHolder)viewHolder;
        spltChannelViewHolder.setCallback(new SPLTChannelViewHolder.Callback() {
            @Override
            public void onToggleButtonClick(SPLTChannel channel, boolean checked) {
                onToggleButtonClicked(channel, checked);
            }

            @Override
            public void onChannelClick(SPLTChannel channel) {
                onChannelClicked(channel);
            }
        });

        spltChannelViewHolder.setChannel(this.mChannelList.get(position));
    }
    public void onToggleButtonClicked(SPLTChannel channel, boolean checked){
        if(this.callback != null){
            this.callback.onChannelToggleButtonClick(channel,checked);
        }
    }
    public void onChannelClicked(SPLTChannel channel){
        if(this.callback != null){
            this.callback.onChannelClick(channel);
        }
    }

    @Override
    public int getItemCount() {
        if(this.mChannelList != null && this.mChannelList.size()>0){
            return this.mChannelList.size();
        }else return 0;
    }

    /*@Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }*/

    public void refreshChannelList(List<SPLTChannel> adapterList){
        this.mChannelList.clear();
        this.mChannelList.addAll(adapterList);
        this.notifyDataSetChanged();
    }
}
