package com.dotstudioz.model;

import android.content.Context;
import android.util.Log;

import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.dotstudioPRO.models.dto.ChannelMyListDTO;
import com.dotstudioz.dotstudioPRO.models.dto.ChannelsMyListDTOForMyList;
import com.dotstudioz.dotstudioPRO.models.dto.SpotLightCategoriesDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SpotLightChannelDTO;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstants;
import com.dotstudioz.dotstudioPRO.services.services.ChannelsMyListService_V1;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SPLTMyListCategory extends SPLTCategory implements ChannelsMyListService_V1.IChannelsMyListService{
    String TAG = "SPLTMyListCategory";
    public MyListCallback callback;
    private SPLTMyListCategory instance = null;
    private static final SPLTMyListCategory ourInstance = new SPLTMyListCategory();

    private void SPLTMyListCategory(){
        //super();
        this.setName("My List");
        this.setSlug("my-list");

    }
    public static SPLTMyListCategory getInstance() {
        return ourInstance;
    }

    public interface MyListCallback {
         public void onSuccess();
         public void onFailure(String error);
    }

    public MyListCallback getCallback() {
        return callback;
    }

    public void setCallback(MyListCallback callback) {
        this.callback = callback;
    }

    @Override
    public void loadCategoryChannels(Context context, Callback callbackSlugChannel) {
        //api call after login


        //get list of channel
        // ArrayList<SPLTChannel> channels fill


    }
    public boolean isInMyList(SPLTChannel channel){
        if(channel == null || channel.getSlug()==null|| channel.getSlug().equalsIgnoreCase("")){
            return false;
        }
        if(this.channels != null){
            for(SPLTChannel currentChannel : this.channels){
                if(currentChannel.getSlug().equalsIgnoreCase(channel.getSlug())){
                    return true;
                }
            }
        }
        return false;
    }

    //addChannelToMyList(String channelID, String parentChannelID, String xAccessToken, String xClientToken, String API_URL)
    //addChannelToMyList(String channelID, String xAccessToken, String xClientToken, String API_URL)
    //deleteChannelFromMyList(String channelID, String xAccessToken, String xClientToken, String API_URL)
    //getChannelFromMyList(String xAccessToken, String xClientToken, String API_URL)


    public void addChannelToMyList(Context context, final SPLTChannel channel){
        if(callback == null){
            throw new RuntimeException("Must implement SPLTMyListCategory.MyListCallback");
        }
        if(channel == null || channel.getId() ==null || channel.getId().trim().length() == 0){
            callback.onFailure("Channel is Null");
            return;
        }
        ApplicationConstants.xAccessToken = SPLTRouter.getInstance().getStrAccessToken();
        ApplicationConstants.CLIENT_TOKEN = SPLTRouter.getInstance().getStrClientToken();
        Log.d(TAG, "addChannelToMyList: LIKE ID "+ channel.getId());
        ChannelsMyListService_V1 channelsMyListService_v1 = new ChannelsMyListService_V1(context);
        channelsMyListService_v1.setChannelsMyListServiceListener(new ChannelsMyListService_V1.IChannelsMyListService() {
            @Override
            public void addChannelToMyListResponse(JSONObject response) {
                Log.d("TAG", "ADD addChannelToMyListResponse: "+response);
                channels.add(channel);
                channel.setInMyList(true);
            }

            @Override
            public void deleteChannelFromMyListResponse(JSONObject response) {
                Log.d("TAG", "ADD deleteChannelFromMyListResponse: "+response);
            }

            @Override
            public void getMyListResponse(ArrayList<ChannelsMyListDTOForMyList> channelsMyListDTOForMyListArrayList, ArrayList<ChannelMyListDTO> channelMyListDTOArrayList) {
                Log.d("TAG", "ADD channelsMyListDTOForMyListArrayList: "+channelsMyListDTOForMyListArrayList.size() + " channelMyListDTOArrayList:"+channelMyListDTOArrayList.size());
            }

            @Override
            public void myListError(String ERROR) {
                Log.d("TAG", "ADD myListError: "+ERROR);
            }

            @Override
            public void accessTokenExpired() {
                Log.d("TAG", "ADD accessTokenExpired: ");
            }

            @Override
            public void clientTokenExpired() {
                Log.d("TAG", "ADD clientTokenExpired: ");
            }
        });
        /*channelsMyListService_v1.addChannelToMyList(channel.getId(), SPLTRouter.getInstance().getStrAccessToken(), SPLTRouter.getInstance().getStrClientToken(), SPLTRouter.getInstance().ADD_TO_MY_LIST_API);*/
        channelsMyListService_v1.addChannelToMyList(channel.getId(), SPLTRouter.getInstance().getStrAccessToken(), SPLTRouter.getInstance().getStrClientToken(), SPLTRouter.getInstance().ADD_TO_MY_LIST_API);
    }
    public void deleteChannelToMyList(Context context, SPLTChannel channel){
        if(callback == null){
            throw new RuntimeException("Must implement SPLTMyListCategory.MyListCallback");
        }
        if(channel == null || channel.getId() ==null || channel.getId().trim().length() == 0){
            callback.onFailure("Channel is Null");
            return;
        }
        ApplicationConstants.xAccessToken = SPLTRouter.getInstance().getStrAccessToken();
        ApplicationConstants.CLIENT_TOKEN = SPLTRouter.getInstance().getStrClientToken();

        ChannelsMyListService_V1 channelsMyListService_v1 = new ChannelsMyListService_V1(context);
        channelsMyListService_v1.setChannelsMyListServiceListener(new ChannelsMyListService_V1.IChannelsMyListService() {
            @Override
            public void addChannelToMyListResponse(JSONObject response) {
                Log.d(TAG, "addChannelToMyListResponse: "+response.toString());
            }

            @Override
            public void deleteChannelFromMyListResponse(JSONObject response) {
                Log.d(TAG, "deleteChannelFromMyListResponse: "+response.toString());
            }

            @Override
            public void getMyListResponse(ArrayList<ChannelsMyListDTOForMyList> channelsMyListDTOForMyListArrayList, ArrayList<ChannelMyListDTO> channelMyListDTOArrayList) {
                Log.d(TAG, "getMyListResponse: "+channelsMyListDTOForMyListArrayList.size() + " "+ channelMyListDTOArrayList.size());
            }

            @Override
            public void myListError(String ERROR) {
                Log.d(TAG, "myListError: "+ERROR);
            }

            @Override
            public void accessTokenExpired() {
                Log.d(TAG, "accessTokenExpired: ");
            }

            @Override
            public void clientTokenExpired() {
                Log.d(TAG, "clientTokenExpired: ");
            }
        });
        
        
        
        channelsMyListService_v1.deleteChannelFromMyList(channel.getId(), SPLTRouter.getInstance().getStrAccessToken(), SPLTRouter.getInstance().getStrClientToken(), SPLTRouter.getInstance().DELETE_FROM_MY_LIST_API);
    }
    public void getChannelToMyList(Context context){
        if(callback == null){
            throw new RuntimeException("Must implement SPLTMyListCategory.MyListCallback");
        }
        ApplicationConstants.xAccessToken = SPLTRouter.getInstance().getStrAccessToken();
        ApplicationConstants.CLIENT_TOKEN = SPLTRouter.getInstance().getStrClientToken();

        ChannelsMyListService_V1 channelsMyListService_v1 = new ChannelsMyListService_V1(context);
        channelsMyListService_v1.setChannelsMyListServiceListener(new ChannelsMyListService_V1.IChannelsMyListService() {
            @Override
            public void addChannelToMyListResponse(JSONObject response) {
                Log.d("TAG", "addChannelToMyListResponse: "+response);
            }

            @Override
            public void deleteChannelFromMyListResponse(JSONObject response) {
                Log.d("TAG", "deleteChannelFromMyListResponse: "+response);
            }

            @Override
            public void getMyListResponse(ArrayList<ChannelsMyListDTOForMyList> channelsMyListDTOForMyListArrayList, ArrayList<ChannelMyListDTO> channelMyListDTOArrayList) {
                Log.d("TAG", "getMyListResponse: "+ channelsMyListDTOForMyListArrayList.size() + " "+ channelMyListDTOArrayList.size());
                // Arraylist SPLTChannel ke list me mapp
                //
                // set to SPLTMyListCategory
                SPLTCompany.getInstance().myListCategory.channels = mapFromSpotLightChannelMyListDTO(channelMyListDTOArrayList);
                callback.onSuccess();

            }

            @Override
            public void myListError(String ERROR) {
                Log.d("TAG", "myListError: "+ERROR);
                callback.onFailure(ERROR);
            }

            @Override
            public void accessTokenExpired() {
                Log.d("TAG", "accessTokenExpired: ");
                callback.onFailure("accessTokenExpired");
            }

            @Override
            public void clientTokenExpired() {
                Log.d("TAG", "clientTokenExpired: ");
                callback.onFailure("clientTokenExpired");
            }
        });
        channelsMyListService_v1.getChannelFromMyList(SPLTRouter.getInstance().getStrAccessToken(), SPLTRouter.getInstance().getStrClientToken(), SPLTRouter.getInstance().GET_MY_LIST_API);
    }

    @Override
    public void addChannelToMyListResponse(JSONObject response) {

    }

    @Override
    public void deleteChannelFromMyListResponse(JSONObject response) {

    }

    @Override
    public void getMyListResponse(ArrayList<ChannelsMyListDTOForMyList> channelsMyListDTOForMyListArrayList, ArrayList<ChannelMyListDTO> channelMyListDTOArrayList) {

    }

    @Override
    public void myListError(String ERROR) {

    }

    @Override
    public void accessTokenExpired() {

    }

    @Override
    public void clientTokenExpired() {

    }

    public ArrayList<SPLTChannel> mapFromSpotLightChannelMyListDTO(ArrayList<ChannelMyListDTO> channelMyListDTOArrayList){
        ArrayList<SPLTChannel> channels = new ArrayList<>();
        for (ChannelMyListDTO channelMyListDTO : channelMyListDTOArrayList) {
            SPLTChannel spltChannel = new SPLTChannel(channelMyListDTO);
            channels.add(spltChannel);
        }
        return channels;
    }

}
