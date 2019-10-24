package com.dotstudioz.phone.component.home.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.dotstudioPRO.models.dto.SearchResultDTO;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstants;
import com.dotstudioz.dotstudioPRO.services.services.SearchService_V1;
import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTChannel;
import com.dotstudioz.phone.R;
import com.dotstudioz.phone.baseclasses.SPLTBaseFragment;
import com.dotstudioz.phone.component.home.channels.SPLTChannelsFragment;
import com.dotstudioz.phone.util.Utility;

import java.util.ArrayList;
import java.util.List;


public class SPLTChannelSearchFragment extends SPLTChannelsFragment {

    protected EditText editSearch;
    protected TextView emptryView;

    public Handler mHandler = new Handler();;
    public MyRunnable runnable;

    public class MyRunnable implements Runnable {
        private String queryString;
        public MyRunnable(String queryString) {
            this.queryString = queryString;
        }

        @Override
        public void run() {
            searchText(queryString);
        }
    }

    public SPLTChannelSearchFragment() {
        // Required empty public constructor
    }
    public void initialize(){

        if(this.mChannelList != null && this.mChannelList.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            if(emptryView != null){
                emptryView.setVisibility(View.GONE);
            }
        }else {
            recyclerView.setVisibility(View.GONE);
            if(emptryView != null){
                emptryView.setVisibility(View.VISIBLE);
            }
        }


        if(this.editSearch != null){
            editSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence query, int start, int before, int count) {
                    if(query != null && query.toString().trim().length()>2){
                        makeHandlerAndSerchText(query.toString());
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

    }

    // TODO: make handler with 2 second timer
    public void makeHandlerAndSerchText(String queryString){
        if(runnable != null){
            mHandler.removeCallbacks(runnable);
        }
        runnable = new MyRunnable(queryString);
        mHandler.postDelayed(runnable,3000);

        // timer
        //searchText(queryString);
    }


    public void searchText(String queryString){

        if(this.mChannelList != null && this.mChannelList.size()>0){

        }else {
            showProgress();
        }

        ApplicationConstants.xAccessToken = SPLTRouter.getInstance().getStrAccessToken();
        ApplicationConstants.CLIENT_TOKEN = SPLTRouter.getInstance().getStrClientToken();

        SearchService_V1 searchService = new SearchService_V1(getActivity());
        searchService.setSearchServiceListener(new SearchService_V1.ISearchService() {
            @Override
            public void showProgress(String message) {
                Log.d(TAG, "showProgress: "+message);
            }

            @Override
            public void searchServiceResponse(ArrayList<SearchResultDTO> searchResultDTOArrayList) {
                hideProgress();
                if(searchResultDTOArrayList != null && searchResultDTOArrayList.size()>0){
                    recyclerView.setVisibility(View.VISIBLE);
                    emptryView.setVisibility(View.GONE);
                    Log.d(TAG, "searchServiceResponse: "+searchResultDTOArrayList.size());
                    processSearchResult(searchResultDTOArrayList);
                }else {
                    recyclerView.setVisibility(View.GONE);
                    if(emptryView != null){
                        emptryView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void searchError(String ERROR) {
                hideProgress();
                Log.d(TAG, "searchError: "+ERROR);
            }

            @Override
            public void accessTokenExpired() {
                hideProgress();
                Log.d(TAG, "accessTokenExpired: ");
            }

            @Override
            public void clientTokenExpired() {
                hideProgress();
                Log.d(TAG, "clientTokenExpired: ");
            }
        });
        searchService.search(ApplicationConstants.xAccessToken, ApplicationConstants.CLIENT_TOKEN, queryString, SPLTRouter.getInstance().SEARCH_API_URL);
    }

    public void processSearchResult(ArrayList<SearchResultDTO> searchResultDTOArrayList){ //array
        List<SearchResultDTO> mSearchResultList = new ArrayList<>(searchResultDTOArrayList);
        List<SPLTChannel> channelList = new ArrayList<>();
        for (SearchResultDTO channelDTO : mSearchResultList) {
            SPLTChannel spltChannel = new SPLTChannel(channelDTO);
            channelList.add(spltChannel);
        }
        showSearchChannelsResult(channelList);
    }

    public void showSearchChannelsResult(List<SPLTChannel> channelList){
        this.mChannelList = channelList;
        reloadUI();
    }

    public void reloadData(){

        reloadUI();

    }

    public void reloadUI(){
        super.reloadUI();
    }

}
