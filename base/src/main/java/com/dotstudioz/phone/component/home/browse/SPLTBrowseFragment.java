package com.dotstudioz.phone.component.home.browse;

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
import com.dotstudioz.model.SPLTVideo;
import com.dotstudioz.phone.adapter.SPLTHomeAdapter;
import com.dotstudioz.phone.baseclasses.SPLTBaseFragment;

import java.util.ArrayList;
import java.util.List;


public class SPLTBrowseFragment extends SPLTBaseFragment {
    private String TAG = "SPLTHomeFragment";
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    protected SPLTHomeAdapter homeCategoryAdapter;
    protected RecyclerView recyclerView;
    public List<SPLTCategory> mHomeCategoryList = new ArrayList<>();
    protected int cardViewHeight = 120;
    protected int cardViewwidth = 120;
    protected int cellMargin = 5;
    protected int numberOfColumns = 3;

    protected Callback callback;
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

    public SPLTBrowseFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static SPLTBrowseFragment newInstance(String param1, String param2) {
        SPLTBrowseFragment fragment = new SPLTBrowseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View dsView = initDS(inflater,container,savedInstanceState);
        return dsView;
    }


    protected View initDS(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void loadList(){ }

    public void reloadUI(){
        Log.d(TAG, "onViewCreated: "+this.mHomeCategoryList.size());
        if(this.homeCategoryAdapter != null ){
            this.homeCategoryAdapter.refreshCategoryList(this.mHomeCategoryList);
            //categoryAdapter.notifyDataSetChanged();
            //recyclerView.invalidate();
        }
        //loadList();
    }

    protected void openPlayerActivity(){}
}
