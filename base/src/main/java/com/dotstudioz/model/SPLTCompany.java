package com.dotstudioz.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dotstudioz.SPLTConfig;
import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.api.SPLTValidationAPI;
import com.dotstudioz.dotstudioPRO.models.dto.CustomFieldDTO;
import com.dotstudioz.dotstudioPRO.models.dto.LastWatchedVideosPairDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SpotLightCategoriesDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SpotLightChannelDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SubscriptionDTO;
import com.dotstudioz.dotstudioPRO.models.dto.VideoInfoDTO;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstantURL;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstants;
import com.dotstudioz.dotstudioPRO.services.services.GetAllCategoriesServiceForHomepage_V1;
import com.dotstudioz.dotstudioPRO.services.services.GetAllCategoriesService_V1;
import com.dotstudioz.dotstudioPRO.services.services.LastWatchedVideosService_V1;
import com.dotstudioz.phone.component.home.SPLTHomeActivity;
import com.dotstudioz.phone.util.SharedPreferencesUtil;
import com.google.gson.Gson;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Predicate;

public class SPLTCompany {
    private String TAG = "SPLTCompany";
    private static final SPLTCompany ourInstance = new SPLTCompany();
    public Context mContext = null;
    public Callback mCallback = null;
    public SPLTMyListCategory myListCategory = SPLTMyListCategory.getInstance();;

    public static SPLTCompany getInstance() {
        return ourInstance;
    }

    private SPLTCompany() {
        // TODO strClientToken only Observer insted of full object
        // currenlty set in clientToken Remove at that time called
        SPLTRouter.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                onLoginLogout();
            }
        });
    }
    /*public SPLTCompany(Context context) {
        this.mContext = context;
        if (this.mContext instanceof Callback) {
            this.mCallback = (Callback) context;
        }
    }*/
    public interface Callback {
        void onSuccess();
        void onError(String responseBody);
        void onAccessTokenExpired();
        void onClientTokenExpired();
    }

    //this will contain all dotstudio categories
    private ArrayList<SPLTCategory> categories = new ArrayList<>();

    //this will contain continuewatching and watchagain category
    private ArrayList<SPLTCustomCategory> customCategories = new ArrayList<>();

    // this will contain all home page categories
    private ArrayList<SPLTCategory> homepageAllCategories = new ArrayList<>();

    // this will contain homepage categories + custom categories
    private ArrayList<SPLTCategory> homepageAndCustomCategories = new ArrayList<>();


    public void startLoadingData(Callback callback){
        //homepage
        //callback
        this.mCallback = callback;
        loadHomePageData();
    }
    public void startCountinuewatchAndWatchAgain(Callback callback){
        //homepage
        this.mCallback = callback;
        loadWatchedVideoList();
    }

    private void onLogin(){
        this.loadCustomCategories();
	// this.loadMyList();
    }
    private void onLogout(){
        this.customCategories.clear();
        this.mergeCategories();
	    this.myListCategory.clear();
        for( SPLTCategory category : this.categories){
            category.onLogout();
        }
    }
    private void onLoginLogout(){
        if(SPLTRouter.getInstance().isUserLoggedIn()){
            this.onLogin();
        }else {
            this.onLogout();
        }

    }


    // todo check callback
    private void loadHomePageData(){
        if (mCallback == null) {
            /*if (mContext != null && mContext instanceof SPLTValidationAPI.Callback) {*/
            if (mContext != null && mContext instanceof Callback) {
                mCallback = (Callback) mContext;
            }
            if (mCallback == null) {
                /*throw new RuntimeException(mContext.toString()+ " must implement SPLTValidationAPI.Callback or setSPLTValidationAPICallback");*/
                throw new RuntimeException(mContext.toString()+ " must implement Callback or setSPLTCompanyCallback");
            }
        }
        homePageCategoryAPICall();
        //loadCategoryAPICall();
        //loadWatchedVideoList();
    }
    private void loadCustomCategories(){
        // if client token avail
        loadWatchedVideoList();
    }

    public ArrayList<SPLTCategory> getHomepageAndCustomCategories() {
        return homepageAndCustomCategories;
    }

    public void setHomepageAndCustomCategories(ArrayList<SPLTCategory> homepageAndCustomCategories) {
        this.homepageAndCustomCategories = homepageAndCustomCategories;
    }

    public ArrayList<SPLTCategory> getHomepageAllCategories() {
        return homepageAllCategories;
    }

    public void setHomepageAllCategories(ArrayList<SPLTCategory> homepageAllCategories) {
        this.homepageAllCategories = homepageAllCategories;
    }

    public ArrayList<SPLTCategory> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<SPLTCategory> categories) {
        this.categories = categories;
    }

    private void homePageCategoryAPICall(){
        GetAllCategoriesServiceForHomepage_V1 getAllCategoriesServiceForHomepageV1 = new GetAllCategoriesServiceForHomepage_V1(mContext);
        getAllCategoriesServiceForHomepageV1.PLATFORM = SPLTRouter.getInstance().APP_VERSION_API;
        getAllCategoriesServiceForHomepageV1.setGetAllCategoriesServiceForHomepageListener(new GetAllCategoriesServiceForHomepage_V1.IGetAllCategoriesServiceForHomepage_V1() {
            @Override
            public void getAllCategoriesForHomepageError(String ERROR) {
                if(ERROR != null) {
                    Toast.makeText(mContext, "" + ERROR, Toast.LENGTH_SHORT).show();
                }
                //getContinueWatchingAndWatchedVideos();
                if (mCallback != null) {
                    mCallback.onError(ERROR);
                }
            }
            @Override
            public void getAllCategoriesServiceForHomepageResponse( ArrayList<SpotLightCategoriesDTO> CategoriesListAll, ArrayList<SpotLightCategoriesDTO> CategoriesList, ArrayList<SpotLightCategoriesDTO> SliderShowcaseList, ArrayList<SpotLightCategoriesDTO> RosterList,
                    ArrayList<SpotLightCategoriesDTO> GenreList) {
                Log.d(TAG, "getAllCategoriesServiceForHomepageResponse: CategoriesList: "+CategoriesList.size());
                processGetAllCategoriesServiceForHomepageResponse(CategoriesListAll, CategoriesList, SliderShowcaseList, RosterList, GenreList);

            }

            @Override
            public void accessTokenExpired() {
                //getToken();
                Log.d(TAG, "accessTokenExpired: ");
                if (mCallback != null) {
                    mCallback.onAccessTokenExpired();
                }
            }

            @Override
            public void clientTokenExpired() {
                //refreshTheClientToken();
                Log.d(TAG, "clientTokenExpired: ");
                if (mCallback != null) {
                    mCallback.onClientTokenExpired();
                }
            }
        });
        getAllCategoriesServiceForHomepageV1.getAllCategoriesServiceForHomePage(SPLTRouter.getInstance().getStrAccessToken(), SPLTRouter.getInstance().HOMEPAGE_API_FIRE_TV);

    }
    private void processGetAllCategoriesServiceForHomepageResponse(ArrayList<SpotLightCategoriesDTO> CategoriesListAll, ArrayList<SpotLightCategoriesDTO> CategoriesList, ArrayList<SpotLightCategoriesDTO> SliderShowcaseList, ArrayList<SpotLightCategoriesDTO> RosterList, ArrayList<SpotLightCategoriesDTO> GenreList){
        this.homepageAllCategories = new ArrayList<>();
        for (SpotLightCategoriesDTO categoriesDTO : CategoriesList) {
            SPLTCategory spltCategory = new SPLTCategory(categoriesDTO);
            this.homepageAllCategories.add(spltCategory);
        }
        this.loadCategories();
    }

    private void loadCategories(){
        GetAllCategoriesService_V1 getAllCategoriesService_V1 = new GetAllCategoriesService_V1(mContext);
        /*getAllCategoriesService_V1.PLATFORM = SPLTRouter.getInstance().APP_VERSION_API;*/
        getAllCategoriesService_V1.PLATFORM =  GetAllCategoriesService_V1.PLATFORM_TYPE_ANDROID;
        GetAllCategoriesService_V1.IGetAllCategoriesService_V1 mListener = new GetAllCategoriesService_V1.IGetAllCategoriesService_V1() {
            @Override
            public void getAllCategoriesServiceResponse(ArrayList<SpotLightCategoriesDTO> spotLightCategoriesDTOListALL, ArrayList<SpotLightCategoriesDTO> spotLightCategoriesDTOList, ArrayList<SpotLightCategoriesDTO> spotLightCategoriesDTOListForSliderShowcase, ArrayList<SpotLightCategoriesDTO> spotLightCategoriesDTOListForRoster, ArrayList<SpotLightCategoriesDTO> spotLightCategoriesDTOListForGenre) {
                Log.d(TAG, "getAllCategoriesServiceResponse: spotLightCategoriesDTOList:"+spotLightCategoriesDTOList.size());
                processGetAllCategoriesServiceResponse(spotLightCategoriesDTOListALL, spotLightCategoriesDTOList, spotLightCategoriesDTOListForSliderShowcase, spotLightCategoriesDTOListForRoster, spotLightCategoriesDTOListForGenre);
            }

            @Override
            public void getAllCategoriesError(String ERROR) {
                Log.d(TAG, "getAllCategoriesError: "+ERROR);
                if (mCallback != null) {
                    mCallback.onError(ERROR);
                }
            }

            @Override
            public void accessTokenExpired() {
                Log.d(TAG, "accessTokenExpired: ");
                if (mCallback != null) {
                    mCallback.onAccessTokenExpired();
                }
            }

            @Override
            public void clientTokenExpired() {
                Log.d(TAG, "clientTokenExpired: ");
                if (mCallback != null) {
                    mCallback.onClientTokenExpired();
                }
            }
        };
        getAllCategoriesService_V1.setGetAllCategoriesService_V1Listener(mListener);
        getAllCategoriesService_V1.getAllCategoriesService(SPLTRouter.getInstance().getStrAccessToken(), SPLTRouter.getInstance().CATEGORIES_LIST); //https://api.myspotlight.tv/categories/IN/
    }
    private void processGetAllCategoriesServiceResponse(ArrayList<SpotLightCategoriesDTO> spotLightCategoriesDTOListALL, ArrayList<SpotLightCategoriesDTO> spotLightCategoriesDTOList, ArrayList<SpotLightCategoriesDTO> spotLightCategoriesDTOListForSliderShowcase, ArrayList<SpotLightCategoriesDTO> spotLightCategoriesDTOListForRoster, ArrayList<SpotLightCategoriesDTO> spotLightCategoriesDTOListForGenre){
        for (SpotLightCategoriesDTO categoriesDTO : spotLightCategoriesDTOList) {
            SPLTCategory spltCategory = new SPLTCategory(categoriesDTO);
            this.categories.add(spltCategory);
        }
        this.setCategories(this.categories);
        addingHomeCategoryToSPLTCategory();
        if (mCallback != null) {
            mCallback.onSuccess();
        }
        loadWatchedVideoList();

    }
    private void loadMyList(){
        this.myListCategory.loadCategoryChannels(mContext, new SPLTCategory.Callback() {
            @Override
            public void onSuccess(List<SPLTChannel> channelList) {

            }

            @Override
            public void onFail(String error) {

            }
        });

    }



    private void loadWatchedVideoList(){

        ApplicationConstants.CLIENT_TOKEN = SPLTRouter.getInstance().getStrClientToken();

        Log.d(TAG, "===loadWatchedVideoList: WS CALL");
        LastWatchedVideosService_V1 lastWatchedVideosService_V1 = new LastWatchedVideosService_V1(mContext);
        lastWatchedVideosService_V1.setLastWatchedVideosService_V1Listener(new LastWatchedVideosService_V1.ILastWatchedVideosService_V1() {
            @Override
            public void callBackFromLastWatchedVideosService(LastWatchedVideosPairDTO lastWatchedVideosPairDTO) {
                Log.d(TAG, "===callBackFromLastWatchedVideosService: "+lastWatchedVideosPairDTO.getContinueWatchingDTOList().size() +" - "+lastWatchedVideosPairDTO.getWatchAgainDTOList().size());
                processingCallBackFromLastWatchedVideosService(lastWatchedVideosPairDTO);

            }

            @Override
            public void getVideoPausedPointServiceError(String ERROR) {
                Log.d(TAG, "===getVideoPausedPointServiceError: "+ERROR);
                mergeCategories();
            }

            @Override
            public void accessTokenExpired() {
                Log.d(TAG, "===accessTokenExpired: ");
                mergeCategories();
            }

            @Override
            public void clientTokenExpired() {
                Log.d(TAG, "===clientTokenExpired: ");
                mergeCategories();
            }
        });
        lastWatchedVideosService_V1.getLastWatchedVideos(ApplicationConstantURL.getInstance().LAST_WATCHED_VIDEOS_API, 0);
    }
    private void processingCallBackFromLastWatchedVideosService(LastWatchedVideosPairDTO lastWatchedVideosPairDTO){
        this.customCategories.clear();

        ArrayList<VideoInfoDTO> continueWatchList = lastWatchedVideosPairDTO.getContinueWatchingDTOList();
        ArrayList<VideoInfoDTO> watchAgainWatchList = lastWatchedVideosPairDTO.getWatchAgainDTOList();

        if(continueWatchList != null && continueWatchList.size()>0){
            SPLTCustomCategory spltContinueWatchingCategory = new SPLTCustomCategory(continueWatchList, SPLTCustomCategory.Type.CONTINUE_WATCHING);
            this.customCategories.add(spltContinueWatchingCategory);
        }

        if(watchAgainWatchList != null && watchAgainWatchList.size()>0){
            SPLTCustomCategory spltWatchAgainCategory = new SPLTCustomCategory(watchAgainWatchList, SPLTCustomCategory.Type.WATCH_AGAIN);
            this.customCategories.add(spltWatchAgainCategory);
        }
        mergeCategories();
    }

    private void mergeCategories() {
        this.homepageAndCustomCategories.clear();
        ArrayList<SPLTCategory> allCategories = new ArrayList<SPLTCategory>(this.categories);
        CollectionUtils.filter(allCategories, new org.apache.commons.collections4.Predicate<SPLTCategory>() {

            @Override
            public boolean evaluate(SPLTCategory object) {
                if(object.isHomepage()){
                    return true;
                }
                return false;
            }
        });
        this.homepageAndCustomCategories.addAll(allCategories);
        if(this.customCategories != null && this.customCategories.size()>0){
            for(SPLTCustomCategory spltCustomCategory: customCategories){
                if(spltCustomCategory.getType() == SPLTCustomCategory.Type.CONTINUE_WATCHING){
                    if(SPLTConfig.iContinueWatchIndex == -1){
                        this.homepageAndCustomCategories.add(spltCustomCategory);
                    }else {
                        if(this.homepageAndCustomCategories.size()>SPLTConfig.iContinueWatchIndex){
                            this.homepageAndCustomCategories.add(SPLTConfig.iContinueWatchIndex, spltCustomCategory);
                        }else {
                            this.homepageAndCustomCategories.add(spltCustomCategory);
                        }
                    }
                }
                if(spltCustomCategory.getType() == SPLTCustomCategory.Type.WATCH_AGAIN){
                    if(SPLTConfig.iWatchAgainIndex == -1){
                        this.homepageAndCustomCategories.add(spltCustomCategory);
                    }else {
                        if(this.homepageAndCustomCategories.size()>SPLTConfig.iWatchAgainIndex){
                            this.homepageAndCustomCategories.add(SPLTConfig.iWatchAgainIndex, spltCustomCategory);
                        }else {
                            this.homepageAndCustomCategories.add(spltCustomCategory);
                        }
                    }
                }
            }
        }
        if (mCallback != null) {
            mCallback.onSuccess();
        }
    }



    private void addingHomeCategoryToSPLTCategory() {
        ArrayList<SPLTCategory> homepageAllCategoriesList = new ArrayList<>();
        homepageAllCategoriesList.addAll(this.getHomepageAllCategories());

        ArrayList<SPLTCategory> categoriesList = new ArrayList<>();
        categoriesList.addAll(this.getCategories());

        for(int i=0;i<homepageAllCategoriesList.size();i++) {
            for(int j=0;j<categoriesList.size();j++) {
                if(categoriesList.get(j).getId().equalsIgnoreCase(homepageAllCategoriesList.get(i).getId())) {
                    if(homepageAllCategoriesList.get(i).channels != null && homepageAllCategoriesList.get(i).channels.size()>0){
                        categoriesList.get(j).channels = homepageAllCategoriesList.get(i).channels;
                    }
                }
            }
        }
        this.setCategories(categoriesList);

    }





}

