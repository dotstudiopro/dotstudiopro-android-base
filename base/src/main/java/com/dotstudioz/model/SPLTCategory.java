package com.dotstudioz.model;


import android.content.Context;
import android.util.Log;

import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.dotstudioPRO.models.dto.ChannelMyListDTO;
import com.dotstudioz.dotstudioPRO.models.dto.CustomFieldDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SearchResultDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SpotLightCategoriesDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SpotLightChannelDTO;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstantURL;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstants;
import com.dotstudioz.dotstudioPRO.services.services.GetAllChannelsFromAllCategoriesService_V1;
import com.dotstudioz.dotstudioPRO.services.services.SearchService_V1;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SPLTCategory implements Serializable {
    private String TAG ="SPLTCategory";

    private String id;
    private String companyId;
    private String name;
    private String description;
    private String displayName;
    private String slug;
    private String wallpaper;
    private String poster;
    private int imageHeight;
    private int imageWidth;
    private String imageSlug;
    private int weight;
    private boolean homepage;
    private boolean menu;
    private boolean enabled;
    private ArrayList<SPLTCustomField> customFields;
    private boolean platform;
    private String path;
    private String channelsURL;
    private boolean hasChildCategory;
    private int parentId;
    private boolean isChannelPosterAssignedToCategoriesPoster;
    private boolean isParentChannel;

    public ArrayList<SPLTChannel> channels = new ArrayList<>();

    /*public CallbackSlugChannel callbackSlugChannel;*/
    public interface Callback {
        void onSuccess(List<SPLTChannel> channelList);
        void onFail(String error);
    }


    public SPLTCategory(){}
    public SPLTCategory(SpotLightCategoriesDTO categoriesDTO) {
        this.mapFromSpotLightCategoryDTO(categoriesDTO);
    }




    public void mapFromSpotLightCategoryDTO(SpotLightCategoriesDTO data){
        
        this.setId(data.getCategoryId());
        this.setName(data.getCategoryName());
        this.setSlug(data.getCategorySlug());
        this.setWeight(data.getCategoryWeight());
        this.setChannelsURL(data.getChannels());
        this.setCompanyId(data.getCompanyId());
        this.setEnabled(data.isEnabled());
        this.setHasChildCategory(data.isHasChildCategory());
        this.setHomepage(data.isHomepage());
        this.setImageWidth(data.getImageWidth());
        this.setImageHeight(data.getImageHeight());
        this.setChannelPosterAssignedToCategoriesPoster(data.isChannelPosterAssignedToCategoriesPoster());
        this.setParentChannel(data.isParentChannel());
        this.setMenu(data.isMenu());
        this.setParentId(data.getParentId());
        this.setPath(data.getPath());
        this.setPlatform(data.isPlatform());
        this.setPoster(data.getPoster());
       /*
        this.setCustomFields(setCustomFieldList(data.getCustomFieldDTOArrayList()));*/
        this.setWallpaper(data.getWallpaper());
        this.setPlatform(data.isPlatform());

        /*this.setDescription();
        this.setDisplayName();
        this.setImageSlug();
        this.setChildSPLTCategories(Arrya);*/
        this.channels = new ArrayList<>();
        List<SpotLightChannelDTO> channelDTOList = data.getSpotLightChannelDTOList();
        for (SpotLightChannelDTO channelDTO : channelDTOList) {
            SPLTChannel spltChannel = SPLTChannel.getChannelFromSpotLightChannelDTO(channelDTO);
            this.channels.add(spltChannel);
        }
        this.customFields = new ArrayList<>();
        List<CustomFieldDTO> mCustomFieldDTOList = data.getCustomFieldDTOArrayList();
        for (CustomFieldDTO customFieldDTO : mCustomFieldDTOList) {
            SPLTCustomField spltCustomField = new SPLTCustomField(customFieldDTO);
            this.customFields.add(spltCustomField);
        }
    }




    public void loadCategoryChannels(Context context, final Callback callbackSlugChannel){
        ApplicationConstants.xAccessToken = SPLTRouter.getInstance().getStrAccessToken();
        HashSet channelDTOList = new HashSet<SpotLightChannelDTO>();
        List<SpotLightCategoriesDTO> spotLightCategoriesDTOList = new ArrayList<>();
        String categoriesSlug = this.getSlug();
        GetAllChannelsFromAllCategoriesService_V1 getAllChannelsFromAllCategoriesService_v1 = new GetAllChannelsFromAllCategoriesService_V1(context);
        getAllChannelsFromAllCategoriesService_v1.setGetAllChannelsFromAllCategoriesService_V1Listener(new GetAllChannelsFromAllCategoriesService_V1.IGetAllChannelsFromAllCategoriesService_V1() {
            @Override
            public void getAllChannelsFromAllCategoriesServiceResponse(Set<SpotLightChannelDTO> channelDTOList, List<SpotLightCategoriesDTO> spotLightCategoriesDTOList) {
                Log.d(TAG, "getAllChannelsFromAllCategoriesServiceResponse: channelDTOList: "+channelDTOList.size());
                List<SpotLightChannelDTO> mSlugChannelList = new ArrayList<SpotLightChannelDTO>(channelDTOList);
                List<SPLTChannel> channelList = new ArrayList<>();
                /*List<SpotLightChannelDTO> slugChannelDTOList = mSlugChannelList;*/
                for (SpotLightChannelDTO channelDTO : mSlugChannelList) {
                    SPLTChannel spltChannel = SPLTChannel.getChannelFromSpotLightChannelDTO(channelDTO);
                    channelList.add(spltChannel);
                }
                callbackSlugChannel.onSuccess(channelList);
            }

            @Override
            public void getAllChannelsFromAllCategoriesServiceError(String ERROR) {
                Log.d(TAG, "getAllChannelsFromAllCategoriesServiceError: "+ERROR);
                callbackSlugChannel.onFail(ERROR);
            }

            @Override
            public void accessTokenExpired() {
                Log.d(TAG, "accessTokenExpired: ");
                callbackSlugChannel.onFail("accessTokenExpired");
            }

            @Override
            public void clientTokenExpired() {
                Log.d(TAG, "clientTokenExpired: ");
                callbackSlugChannel.onFail("clientTokenExpired");
            }
        });
        getAllChannelsFromAllCategoriesService_v1.getAllChannelsFromAllCategoriesService(ApplicationConstants.xAccessToken, ApplicationConstantURL.getInstance().CHANNELS, categoriesSlug, channelDTOList, spotLightCategoriesDTOList);

    }



   /* private void getAllChannelsFromAllCategory() {
        //String categoriesSlug = "category_slug1;categoryslug2;category_slug3;category_slug4";
        String categoriesSlug = categorySlugDetail();
        Log.d(TAG, "getAllChannelsFromAllCategory: SLUG LIST :"+categoriesSlug);

        HashSet channelDTOList = new HashSet<SpotLightChannelDTO>();
        List<SpotLightCategoriesDTO> spotLightCategoriesDTOList = getFilterCategoryFromHomeSPLTCategory();

        GetAllChannelsFromAllCategoriesService_V1 getAllChannelsFromAllCategoriesService_v1 = new GetAllChannelsFromAllCategoriesService_V1(this);
        getAllChannelsFromAllCategoriesService_v1.setGetAllChannelsFromAllCategoriesService_V1Listener(new GetAllChannelsFromAllCategoriesService_V1.IGetAllChannelsFromAllCategoriesService_V1() {
            @Override
            public void getAllChannelsFromAllCategoriesServiceResponse(Set<SpotLightChannelDTO> channelDTOList, List<SpotLightCategoriesDTO> spotLightCategoriesDTOList) {
                Log.d(TAG, "getAllChannelsFromAllCategoriesServiceResponse: "+channelDTOList.size());
                String mResJSON = new Gson().toJson(channelDTOList);
                Log.d(TAG, "getAllChannelsFromAllCategoriesServiceResponse: "+mResJSON);
                Log.d(TAG, "getAllChannelsFromAllCategoriesServiceResponse: "+spotLightCategoriesDTOList.size());

                ArrayList<SPLTChannel> mSPLTChannelList =  null; //setSpotLightList(convertSetToList(channelDTOList));
                String mResJSONmSPLTChannelList = new Gson().toJson(mSPLTChannelList);
                Log.d(TAG, "getAllChannelsFromAllCategoriesServiceResponse: "+mResJSONmSPLTChannelList);

                for(int i=0; i < mSPLTChannelList.size();i++){
                    if(mSPLTChannelList.get(i).getCategories() != null && mSPLTChannelList.get(i).getCategories().size()>0){
                        for(int j=0;j<mSPLTChannelList.get(i).getCategories().size();j++){
                            for(int k=0; k<SPLTCompany.getInstance().getCategories().size();k++){
                                if(mSPLTChannelList.get(i).getCategories().get(j).equalsIgnoreCase(SPLTCompany.getInstance().getCategories().get(k).getId())){
                                    if(SPLTCompany.getInstance().getCategories().get(k).channels == null || SPLTCompany.getInstance().getCategories().get(k).channels.size()==0){
                                        ArrayList<SPLTChannel> mChannels = new ArrayList<SPLTChannel>();
                                        mChannels.add(mSPLTChannelList.get(i));
                                        *//*SPLTCompany.getInstance().getCategories().get(k).setChannels(mChannels);*//*
                                        SPLTCompany.getInstance().getCategories().get(k).channels = mChannels;
                                    }

                                }
                            }
                        }
                    }
                }

                //loop
                //ek chanal how may category
                *//*SPLTCompany.getInstance().setChannels(mSPLTChannelList);*//*
            }

            @Override
            public void getAllChannelsFromAllCategoriesServiceError(String ERROR) {
                Log.d(TAG, "getAllChannelsFromAllCategoriesServiceError: "+ERROR);
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
        getAllChannelsFromAllCategoriesService_v1.getAllChannelsFromAllCategoriesService(SPLTRouter.getInstance().getStrAccessToken(), SPLTRouter.getInstance().CHANNELS, categoriesSlug, channelDTOList, spotLightCategoriesDTOList);

    }*/

    public void onLogout(){
        for( SPLTChannel channel : this.channels){
            channel.onLogout();
        }
    }

    public boolean isChannelPosterAssignedToCategoriesPoster() {
        return isChannelPosterAssignedToCategoriesPoster;
    }

    public void setChannelPosterAssignedToCategoriesPoster(boolean channelPosterAssignedToCategoriesPoster) {
        isChannelPosterAssignedToCategoriesPoster = channelPosterAssignedToCategoriesPoster;
    }

    public boolean isParentChannel() {
        return isParentChannel;
    }

    public void setParentChannel(boolean parentChannel) {
        isParentChannel = parentChannel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageSlug() {
        return imageSlug;
    }

    public void setImageSlug(String imageSlug) {
        this.imageSlug = imageSlug;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isHomepage() {
        return homepage;
    }

    public void setHomepage(boolean homepage) {
        this.homepage = homepage;
    }

    public boolean isMenu() {
        return menu;
    }

    public void setMenu(boolean menu) {
        this.menu = menu;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isPlatform() {
        return platform;
    }

    public void setPlatform(boolean platform) {
        this.platform = platform;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getChannelsURL() {
        return channelsURL;
    }

    public void setChannelsURL(String channelsURL) {
        this.channelsURL = channelsURL;
    }


    public boolean isHasChildCategory() {
        return hasChildCategory;
    }

    public void setHasChildCategory(boolean hasChildCategory) {
        this.hasChildCategory = hasChildCategory;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}
