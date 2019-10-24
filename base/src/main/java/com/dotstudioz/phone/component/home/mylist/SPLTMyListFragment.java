package com.dotstudioz.phone.component.home.mylist;

import android.util.Log;

import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTChannel;
import com.dotstudioz.model.SPLTCompany;
import com.dotstudioz.model.SPLTMyListCategory;
import com.dotstudioz.phone.component.home.channels.SPLTChannelsFragment;
import com.dotstudioz.phone.util.Utility;

import java.util.List;


public class SPLTMyListFragment extends SPLTChannelsFragment {
    // TODO: Rename parameter arguments, choose names that match
    protected String TAG = "SPLTCategoryFragment";

    public SPLTMyListFragment() {
        // Required empty public constructor
    }


    public void reloadData(){
        //check data avail or not

        reloadCategory();
        //reloadUI();

    }

    public void reloadCategory(){
        //show my list
        showProgress();
        SPLTMyListCategory spltMyListCategory = SPLTMyListCategory.getInstance();
        spltMyListCategory.setCallback(new SPLTMyListCategory.MyListCallback() {
            @Override
            public void onSuccess() {
                hideProgress();
                mChannelList = SPLTCompany.getInstance().myListCategory.channels;
                reloadUI();
            }

            @Override
            public void onFailure(String error) {
                hideProgress();
                //Utility.showCustomDialog(getActivity(), "Token Error",error, false, false, "OK","Cancel",4);
            }
        });
        spltMyListCategory.getChannelToMyList(getActivity());
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
