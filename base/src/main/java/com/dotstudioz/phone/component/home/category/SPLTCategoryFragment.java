package com.dotstudioz.phone.component.home.category;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTChannel;
import com.dotstudioz.model.SPLTCompany;
import com.dotstudioz.model.SPLTMyListCategory;
import com.dotstudioz.phone.adapter.SPLTChannelAdapter;
import com.dotstudioz.phone.baseclasses.SPLTBaseFragment;
import com.dotstudioz.phone.component.home.channels.SPLTChannelsFragment;
import com.dotstudioz.phone.util.Utility;

import java.util.ArrayList;
import java.util.List;


public class SPLTCategoryFragment extends SPLTChannelsFragment {
    // TODO: Rename parameter arguments, choose names that match
    protected String TAG = "SPLTCategoryFragment";
    public SPLTCategory category = null;

    public SPLTCategory getCategory() {
        return category;
    }


    public void setCategory(SPLTCategory category) {
        this.category = category;
        if(this.category != null){
            reloadData();
        }
    }

    public SPLTCategoryFragment() {
        // Required empty public constructor
    }


    public void reloadData(){
        //check data avail or not

        reloadCategory();
        //reloadUI();

    }

    public void reloadCategory(){
        if(this.category != null){
            // show single category selected
            if(mChannelList == null || mChannelList.size()==0){
                showProgress();
            }
            this.category.loadCategoryChannels(getActivity(), new SPLTCategory.Callback() {
                @Override
                public void onSuccess(List<SPLTChannel> channelList) {
                    hideProgress();
                    mChannelList = channelList;
                    reloadUI();
                }

                @Override
                public void onFail(String error) {
                    hideProgress();
                    Utility.showCustomDialog(getActivity(), "Token Error",error, false, false, "OK","Cancel",4);
                }
            });
        }
    }


    public void reloadUI(){
        Log.d(TAG, "onViewCreated: "+this.mChannelList.size());
        if(this.channelAdapter != null ){
            this.channelAdapter.refreshChannelList(this.mChannelList);
            //categoryAdapter.notifyDataSetChanged();
            //recyclerView.invalidate();
        }
    }
    public void onSelectCategory(SPLTCategory category){


    }



}
