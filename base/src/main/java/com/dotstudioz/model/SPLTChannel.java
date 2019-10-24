package com.dotstudioz.model;

import android.text.TextUtils;
import android.util.Log;

import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.dotstudioPRO.models.dto.ChannelMyListDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SearchResultDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SpotLightChannelDTO;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstants;
import com.dotstudioz.dotstudioPRO.services.services.ChannelDetailsService;
import com.dotstudioz.phone.component.home.SPLTHomeActivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SPLTChannel extends Observable implements Serializable {
    private List<String> categories;
    private Integer categoryWeight;
    private String channelDescription;
    private String id;
    private String dsproId;
    private String image;
    private boolean isProduct;
    private boolean isSeasonsPresent;
    private int numberOfSeasons;
    /*public ArrayList playlist;*/
    private String poster;
    /*public ArrayList seasonsList;*/
    private String slug;
    private String spotlightImage;
    private String title;
    private boolean unlocked;
    /*private ArrayList<SPLTVideo> videoList;*/
    private Integer weight;


    private String channelRating;
    private String company;
    private String country;
    private String language;
    private String link;
    private String year;

    private boolean isInMyList = false;

    public SPLTChannel(){}

    public void onLogout(){
       this.setInMyList(false);

    }
    public SPLTChannel(SpotLightChannelDTO spotLightChannelDTO) {
        this.mapFromSpotLightChannelDTO(spotLightChannelDTO);
    }

    public SPLTChannel(ChannelMyListDTO channelMyListDTO){
        this.mapFromChannelMyListDTO(channelMyListDTO);
    }
    public SPLTChannel(SearchResultDTO searchResultDTO){
        this.mapFromSearchListDTO(searchResultDTO);
    }

    public static SPLTChannel getChannelFromSpotLightChannelDTO(SpotLightChannelDTO spotLightChannelDTO){
        SPLTChannel spltChannel = null;
        if(spotLightChannelDTO.getVideo() != null && !TextUtils.isEmpty(spotLightChannelDTO.getVideo())){
            spltChannel = new SPLTVideoChannel(spotLightChannelDTO);
        }else if(spotLightChannelDTO.getVideoInfoDTOList() != null && spotLightChannelDTO.getVideoInfoDTOList().size()>0){
            spltChannel = new SPLTPlaylistChannel(spotLightChannelDTO);
        }else if(spotLightChannelDTO.getSeasonsList() != null && spotLightChannelDTO.getSeasonsList().size()>0){
            spltChannel = new SPLTParentChannel(spotLightChannelDTO);
        }else {
            spltChannel = new SPLTChannel(spotLightChannelDTO);
        }
        return spltChannel;
    }

    public void mapFromSpotLightChannelDTO(SpotLightChannelDTO data){
        this.setCategoryWeight(data.getCategoryWeight());
        this.setChannelDescription(data.getChannelDescription());
        this.setId(data.getId());
        this.setDsproId(data.getDspro_id());
        this.setImage(data.getImage());
        this.setProduct(data.isProduct());
        this.setSeasonsPresent(data.isSeasonsPresent());
        this.setNumberOfSeasons(data.getNumberOfSeasons());
        this.setPoster(data.getPoster());
        this.setSlug(data.getSlug());
        this.setSpotlightImage(data.getSpotlightImage());
        this.setTitle(data.getTitle());
        this.setUnlocked(data.isUnlocked());
        this.setWeight(data.getWeight());

       /* this.setVideoList(setVideoList(data.getVideoInfoDTOList()));*/

        this.setChannelRating(data.getChannelRating()); // for getting new ws
        this.setCompany(data.getCompany());
        this.setCountry(data.getCountry());
        this.setLanguage(data.getLanguage());
        this.setLink(data.getLink());
        this.setYear(data.getYear());
        this.setCategories(data.getCategories());
    }

    private void mapFromChannelMyListDTO(ChannelMyListDTO channelMyListDTO) {
        this.setId(channelMyListDTO.getId());
        this.setTitle(channelMyListDTO.getTitle());
        this.setCompany(channelMyListDTO.getCompanyId());
        this.setPoster(channelMyListDTO.getPoster());
        this.setSpotlightImage(channelMyListDTO.getSpotlightPoster());
        this.setSlug(channelMyListDTO.getSlug());
        //this.categoryName
        this.setProduct(channelMyListDTO.isProduct());
        this.setCategories(channelMyListDTO.getCategoriesArrayList());
        //this.parentChannelMyListDTO
        //this.parentCategorySlug
        //this.parentChannelSlug
    }

    private void mapFromSearchListDTO(SearchResultDTO channelMyListDTO) {
        this.setId(channelMyListDTO.getId());
        this.setTitle(channelMyListDTO.getTitle());
        this.setCompany(channelMyListDTO.getId());
        this.setPoster(channelMyListDTO.getPoster());
        this.setSpotlightImage(channelMyListDTO.getSpotlightPoster());
        this.setSlug(channelMyListDTO.getSlug());
        //this.categoryName
        this.setProduct(channelMyListDTO.isProduct());
        //this.setCategories(channelMyListDTO.getCategoriesArrayList());
        //this.parentChannelMyListDTO
        //this.parentCategorySlug
        //this.parentChannelSlug
    }


    /*private void getChannelDetailSlug() {
        ApplicationConstants.xAccessToken = SPLTRouter.getInstance().getStrAccessToken();
        String categorySlug = "documentaries";

        FetchChannelUsingSlugService_V1 channelDetailsService = new FetchChannelUsingSlugService_V1(SPLTHomeActivity.this);
        channelDetailsService.setFetchChannelUsingSlugService_V1Listener(new FetchChannelUsingSlugService_V1.IFetchChannelUsingSlugService_V1() {
            @Override
            public void showProgress(String message) {
                Log.d(TAG, "showProgress: "+message);
            }

            @Override
            public void hidePDialog() {
                Log.d(TAG, "hidePDialog: ");
            }

            @Override
            public void postProcessingChannelDataServiceResponse(SpotLightChannelDTO spotLightChannelDTO) {
                String mResJSON = new Gson().toJson(spotLightChannelDTO);
                Log.d(TAG, "postProcessingChannelDataServiceResponse: "+mResJSON);
            }

            @Override
            public void processMissingChannelDataServiceError(String ERROR) {
                Log.d(TAG, "processMissingChannelDataServiceError: "+ERROR);
            }

            @Override
            public void accessTokenExpired() {
                Log.d(TAG, "accessTokenExpired: ");
            }
        });
        channelDetailsService.fetchChannelData(categorySlug, SPLTRouter.getInstance().getStrAccessToken());
    }
    */
    /*private void getChannelDetail() {
        ApplicationConstants.xAccessToken = SPLTRouter.getInstance().getStrAccessToken();
        String categorySlug = "slightly-dramatic";
        String channelSlug = "arrested-development-single-no-parent";
        *//*String categorySlug = categorySlugDetail();
        String channelSlug = channelSlugDetail();*//*
        Log.d(TAG, "getChannelDetail: categorySlug : "+categorySlug);
        Log.d(TAG, "getChannelDetail: channelSlug : "+channelSlug);
        ChannelDetailsService channelDetailsService = new ChannelDetailsService(SPLTHomeActivity.this);
        channelDetailsService.setChannelDetailsServiceListener(new ChannelDetailsService.IChannelDetailsService() {
            @Override
            public void channelDetailsServiceResponse(JSONObject jsonObject) {
                Log.d(TAG, "channelDetailsServiceResponse: JSON : "+jsonObject);
                *//*SPLTChannel*//*
            }

            @Override
            public void channelDetailsServiceError(String error) {
                Log.d(TAG, "channelDetailsServiceError: "+error);
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
        channelDetailsService.getChannelDetails(SPLTRouter.getInstance().getStrAccessToken(), SPLTRouter.getInstance().CHANNELS_DETAILS, categorySlug, channelSlug);
    }*/

    public boolean isInMyList() {
        boolean flag = SPLTMyListCategory.getInstance().isInMyList(this);
        return flag;
    }

    public void setInMyList(boolean inMyList) {
        this.isInMyList = inMyList;
        setChanged();
        notifyObservers();
    }

    public String getDsproId() {
        return dsproId;
    }

    public void setDsproId(String dsproId) {
        this.dsproId = dsproId;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getChannelRating() {
        return channelRating;
    }

    public void setChannelRating(String channelRating) {
        this.channelRating = channelRating;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    /*public ArrayList<SPLTVideo> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<SPLTVideo> videoList) {
        this.videoList = videoList;
    }*/

    public Integer getCategoryWeight() {
        return categoryWeight;
    }

    public void setCategoryWeight(Integer categoryWeight) {
        this.categoryWeight = categoryWeight;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public void setProduct(boolean product) {
        isProduct = product;
    }

    public boolean isSeasonsPresent() {
        return isSeasonsPresent;
    }

    public void setSeasonsPresent(boolean seasonsPresent) {
        isSeasonsPresent = seasonsPresent;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSpotlightImage() {
        return spotlightImage;
    }

    public void setSpotlightImage(String spotlightImage) {
        this.spotlightImage = spotlightImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }


    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
