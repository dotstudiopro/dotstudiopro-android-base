package com.dotstudioz.phone.component.home.channels;

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
import com.dotstudioz.phone.util.Utility;

import java.util.ArrayList;
import java.util.List;


public class SPLTChannelsFragment extends SPLTBaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    protected String TAG = "SPLTChannelsFragment";

    protected RecyclerView recyclerView;
    protected SPLTChannelAdapter channelAdapter;
    public List<SPLTChannel> mChannelList = new ArrayList<>();
    protected int cardViewHeight = 120;
    protected int cardViewwidth = 120;
    protected int cellMargin = 5;
    protected int numberOfColumns = 3;

    protected Callback callback;
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



    public SPLTChannelsFragment() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showMenu();
        setUserVisibleHint(true);
        View mView = init(inflater,container,savedInstanceState);
        return mView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        initialize();
        loadList();

    }
    protected void showMenu(){
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void loadList(){}
    public void initialize(){};

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            Log.e("App","@@@ CategoriesFragment setUserVisibleHint");
            //getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //super.onHiddenChanged(hidden);
        if (hidden) {
            //do when hidden
            Log.e("App","@@@ ChannelFragment onHiddenChanged true");
        } else {
            //do when show
            Log.e("App","@@@ ChannelFragment onHiddenChanged false");
            reloadData();
        }

    }

    public void reloadData(){
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
