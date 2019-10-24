package com.dotstudioz.api;

import android.content.Context;

import com.auth0.android.result.Credentials;
import com.dotstudioz.phone.component.login.SPLTLoginActivity;
import com.dotstudioz.phone.util.SharedPreferencesUtil;

import java.util.Observable;

public class SPLTRouter extends Observable {
    private static final SPLTRouter ourInstance = new SPLTRouter();

    public static SPLTRouter getInstance() {
        return ourInstance;
    }

    private SPLTRouter() {
    }

    private String strAccessToken = null;
    private String strClientToken = null;
    private String email = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStrAccessToken() {
        return strAccessToken;
    }

    public void setStrAccessToken(String strAccessToken) {
        this.strAccessToken = strAccessToken;
    }

    public String getStrClientToken() {
        return this.strClientToken;
    }

    public void setStrClientToken(String strClientToken) {
        this.strClientToken = strClientToken;
        setChanged();
        notifyObservers();
    }

    private Credentials mUserCredentials;
    private String auth0IdToken = "";
    private String auth0RefreshToken = "";

    public Credentials getUserCredentials() {
        return mUserCredentials;
    }

    public void setUserCredentials(Credentials userCredentials) {
        this.mUserCredentials = userCredentials;
    }

    public String getAuth0IdToken() {
        return auth0IdToken;
    }

    public void setAuth0IdToken(String auth0IdToken) {
        this.auth0IdToken = auth0IdToken;
    }

    public String getAuth0RefreshToken() {
        return auth0RefreshToken;
    }

    public void setAuth0RefreshToken(String auth0RefreshToken) {
        this.auth0RefreshToken = auth0RefreshToken;
    }


    public static String COUNTRY_CODE = "IN";

    //PRODUCTION_URL
    public static String API_DOMAIN; //PRODUCTION SERVER
    public static String API_DOMAIN_S = "https://api.myspotlight.tv"; //PRODUCTION SERVER
    public static String BLOG_LIST_ABS;
    public static String BLOG_LIST_BY_CATEGORY;
    public static String SPOTLIGHT_DOMAIN;
    public static String API_DSPRO_DOMAIN_S;
    public static String API_SPOTLIGHT_DOMAIN;
    public static String IMAGES_DSPRO_DOMAIN_S = "https://images.dotstudiopro.com/";
    public static String TEASER_DOMAIN;
    public static String ADSERVER_DOMAIN;
    public static String MYSPOTLIGHT_DOMAIN;
    public static String COLLECTOR_DEV_DOMAIN_S;
    public static String COLLECTOR_DSPRO_DOMAIN_S;




    public static String COMPANY_API_KEY;
    public static String AUTH0_CLIENT_ID;
    public static String AUTH0_CLIENT_DOMAIN;
    public static String PACKAGE_NAME;
    public static String PLATFORM;

    public boolean isUserLoggedIn() {
        if(strClientToken != null && strClientToken.trim().length()>0){
            return true;
        }
        return false;
    }

    public static enum PLATFORM_TYPE {ANDROID, ANDROID_TV, FIRE_TV};

    public void setPlatform(PLATFORM_TYPE platform){
        if(platform == PLATFORM_TYPE.ANDROID){
            PLATFORM = "android/";
        }else if(platform == PLATFORM_TYPE.ANDROID_TV){
            PLATFORM = "fire_tv/";
        }else if(platform == PLATFORM_TYPE.FIRE_TV){
            PLATFORM = "android_tv/";
        }
        APP_VERSION_API = API_DOMAIN_S + "/latestAppVersion/package/" + PLATFORM;
    }


    public static String ISO_CODE;
    public static String COUNTRY;
    public static long longitudeFloat;
    public static long latitudeFloat;


    public  static String  TOKEN_URL = API_DOMAIN_S + "/token";
    //public  static String TOKEN_URL = API_DOMAIN_S + "/token";
    public  static String DEVICE_CODE_URL = API_DOMAIN_S + "/device/codes/new";
    public  static String DEVICE_CODE_ACTIVATION_URL = API_DOMAIN_S + "/device/codes/customer";
    public  static String DEVICE_CODE_VERIFICATION_URL = API_DOMAIN_S + "/device/codes?code=";

    public  static String USER_LOGIN_URL = API_DOMAIN_S + "/users/login";
    public  static String USER_DETAILS_URL = API_DOMAIN_S + "/users/details";
    public  static String USER_REGISTER_URL = API_DOMAIN_S + "/users/register";
    public  static String USER_TOKEN_REFRESH = API_DOMAIN_S + "/users/token/refresh";
    public  static String LON_LAT_COUNTRY = API_DOMAIN_S + "/country/analytics";
    public  static String CHANGE_PASSWORD_URL = API_DOMAIN_S + "/users/password";
    //public  static String CHANGE_PASSWORD_URL = API_DSPRO_DOMAIN_S +"/v2/universalapi/customer_details/password";
    //public  static String MY_PURCHASES_URL = API_DSPRO_DOMAIN_S +"/v2/universalapi/collect_video_information";
    public  static String MY_PURCHASES_URL = API_DOMAIN_S + "/users/orders/history";
    public  static String VIDEO_PURCHASE_STATUS = API_DSPRO_DOMAIN_S +"/v2/universalapi/paywall_status";
    public  static String CUSTOMER_REGISTER = API_DSPRO_DOMAIN_S +"/v2/universalapi/customer_register";

    public static String APP_VERSION_API = API_DOMAIN_S + "/latestAppVersion/package/";



    public static String APP_VERSION_ANDROID_API = API_DOMAIN_S + "/latestAppVersion/package/android/";
    public static String APP_VERSION_ANDROID_API_S = API_DOMAIN_S + "/latestAppVersion/package/android/";
    public static String APP_VERSION_FIRE_TV_API = API_DOMAIN_S + "/latestAppVersion/package/fire_tv/";
    public static String APP_VERSION_FIRE_TV_API_S = API_DOMAIN_S + "/latestAppVersion/package/fire_tv/";
    public static String APP_VERSION_ANDROID_TV_API = API_DOMAIN_S + "/latestAppVersion/package/android_tv/";
    public static String APP_VERSION_ANDROID_TV_API_s = API_DOMAIN_S + "/latestAppVersion/package/android_tv/";

    public static String BLOG_LIST = BLOG_LIST_ABS+"/wp-json/dsp/v1/featured/blog/carousel";

    //public  static String AVATAR = SPOTLIGHT_DOMAIN + "/user/avatar/";
    public  static String AVATAR = API_DOMAIN_S + "/users/avatar/";
    //public   static String CATEGORIES_LIST = SPOTLIGHT_DOMAIN + "/categories/list";
    public   static String HOMEPAGE_CATEGORIES_LIST = API_SPOTLIGHT_DOMAIN+"/wp-json/dsp/v1/homepage";

    public   static String SUBSCRIPTION_LIST = API_DOMAIN_S + "/subscriptions/summary";
    public   static String ACTIVE_SUBSCRIPTIONS_LIST = API_DOMAIN_S + "/subscriptions/users/active_subscriptions";
    public   static String CHECK_CHANNEL_SUBSCRIPTIONS_STATUS = API_DOMAIN_S + "/subscriptions/check/";
    public   static String CREATE_BRAINTREE_CUSTOMER_FROM_NONCE = API_DOMAIN_S + "/subscriptions/users/create_from_nonce";
    public   static String CREATE_CHARGIFY_CUSTOMER_USING_SUBSCRIPTION_ID = API_DOMAIN_S + "/subscriptions/users/import/subscribe_to/";

    public   static String CATEGORIES_LIST = API_DOMAIN_S + "/categories/"+COUNTRY_CODE+"/";
    //public   static String CATEGORIES_LIST ="/categories/"+COUNTRY_CODE+"/";
    //public  static String CHANNELS = SPOTLIGHT_DOMAIN + "json/channels";
    public  static String CHANNELS = API_DOMAIN_S + "/channels/"+COUNTRY_CODE+"/";
    public  static String CHANNEL = API_DOMAIN_S + "/channel/"+COUNTRY_CODE+"/";
    public  static String CHANNELS_DETAILS = API_DOMAIN_S + "/channels/"+COUNTRY_CODE+"/";
    public  static String HOMEPAGE = API_DOMAIN_S + "/homepage/"+COUNTRY_CODE+"/";


    public  static String SEARCH_API_URL = API_DOMAIN_S + "/search/";
    public  static String SEARCH_VIDEO_API_URL = API_DOMAIN_S + "/search/videos/";
    public  static String SEARCH_SUGGESTER_API_URL = API_DOMAIN_S + "/search/s/";
    public  static String SEARCH_BY_COMPANY_API_URL = SPOTLIGHT_DOMAIN + "/company/";
    public  static String SEARCH_BY_COMPANY_DATA_FORMAT = "/json";

    public  static String IMAGES = IMAGES_DSPRO_DOMAIN_S;
    //public  static String VIDEOS_API = API_DSPRO_DOMAIN_S +"/v2/cc89512b/json/videos/";
    //public  static String VIDEOS_API = API_DSPRO_DOMAIN_S +"/v2/cc89512b/json/videos/";
    public  static String VIDEOS_API = API_DOMAIN_S + "/channels/"+COUNTRY_CODE+"/";

    public static String VIDEO_PLAY2_API = API_DOMAIN_S + "/video/play2/";

    public  static String PLAYLIST_VIDEOS = API_DSPRO_DOMAIN_S +"/v2/cc89512b/json/playlists/";
    public  static String GET_FIRST_LAST_NAME_API = API_DSPRO_DOMAIN_S +"/v2/universalapi/customer_details";
    public  static String SAVE_FIRST_LAST_NAME_API = API_DSPRO_DOMAIN_S + "/v2/universalapi/customer_details/edit";
    public  static String FORGOT_PASSWORD_API = API_DSPRO_DOMAIN_S +"/v2/universalapi/forgotpassword";

    /*public  static String CREATE_ANALYTICS_USER_API = COLLECTOR_DEV_DOMAIN_S +"/users";
    public  static String SAVE_PLAYER_DATA_API = COLLECTOR_DEV_DOMAIN_S +"/plays";
    public  static String SAVE_APP_DATA_API = COLLECTOR_DEV_DOMAIN_S +"/players";*/
    public  static String CREATE_ANALYTICS_USER_API = COLLECTOR_DSPRO_DOMAIN_S +"/users";
    public  static String SAVE_PLAYER_DATA_API = COLLECTOR_DSPRO_DOMAIN_S +"/plays";
    public  static String SAVE_APP_DATA_API = COLLECTOR_DSPRO_DOMAIN_S +"/players";

    //public  static String CLIENT_TOKEN_API = MYSPOTLIGHT_DOMAIN +"/dotstudio_token";
    public  static String CLIENT_TOKEN_API = API_DOMAIN_S + "/users/payment/token";
    //public  static String RENT_API = MYSPOTLIGHT_DOMAIN +"/rent";
    public  static String RENT_API = API_DOMAIN_S + "/users/payment/purchase";
    public  static String RENT_API_ANDROID = API_DOMAIN_S + "/users/payment/android";

    public  static String RECEIPT_VERIFY_API_FIRE_TV = API_DOMAIN_S + "/users/payment/fire_tv";
    public static  String RENT_STATUS_API = API_DOMAIN_S + "/users/payment/status";
    public  static String SHARING_DOMAIN_NAME = MYSPOTLIGHT_DOMAIN +"/watch";

    public  static String ADSERVER_API = ADSERVER_DOMAIN +"/adserver/www/delivery/fc.php?script=apVideo:vast2&zoneid=";

    public  static String VIDEO_PLAYBACK_DETAILS_API = API_DOMAIN_S + "/users/videos/point/";
    public  static String MULTIPLE_VIDEO_PLAYBACK_DETAILS_API = API_DOMAIN_S + "/users/videos/points/";
    public  static String LAST_WATCHED_VIDEOS_API = API_DOMAIN_S + "/users/resumption/videos";

    public  static String ADD_TO_MY_LIST_API = API_DOMAIN_S + "/watchlist/channels/add";
    public  static String DELETE_FROM_MY_LIST_API = API_DOMAIN_S + "/watchlist/channels/delete";
    public  static String  GET_MY_LIST_API = API_DOMAIN_S + "/watchlist/channels";

    public  static String RECOMMENDATION_API = API_DOMAIN_S + "/search/recommendation";
    public  static String RECOMMENDATION_CHANNEL_API = API_DOMAIN_S + "/search/recommendation/channel";

    public  static String ANALYTICS_TESTING_URL = API_DOMAIN_S + "/testing/analytics";
    public  static String CLIENT_REFRESH_TOKEN = API_DOMAIN_S + "/users/token/refresh";

    public  static String SUBSCRIPTION_API = API_DOMAIN_S + "/subscriptions/google/parse";

    public  static String RECOMMENDATION_CHANNEL_FIRE_TV_API = "/channel/"+COUNTRY_CODE+"/recommendationsamazonfire";
    public  static String HOMEPAGE_API_FIRE_TV = "/homepage/"+COUNTRY_CODE+"/firetv";
    public  static String HOMEPAGE_API_ANDROID_TV = "/homepage/"+COUNTRY_CODE+"/androidtv";
    public  static String HOMEPAGE_API_ANDROID = "/homepage/"+COUNTRY_CODE+"/android";
    
    public void setAPIDomain()
    {
        TOKEN_URL = API_DOMAIN_S + "/token";
        //public  String TOKEN_URL = API_DOMAIN_S + "/token";
        DEVICE_CODE_URL = API_DOMAIN_S + "/device/codes/new";
        DEVICE_CODE_ACTIVATION_URL = API_DOMAIN_S + "/device/codes/customer";
        DEVICE_CODE_VERIFICATION_URL = API_DOMAIN_S + "/device/codes?code=";

        USER_LOGIN_URL = API_DOMAIN_S + "/users/login";
        USER_DETAILS_URL = API_DOMAIN_S + "/users/details";
        USER_REGISTER_URL = API_DOMAIN_S + "/users/register";
        USER_TOKEN_REFRESH = API_DOMAIN_S + "/users/token/refresh";
        LON_LAT_COUNTRY = API_DOMAIN_S + "/country/analytics";
        CHANGE_PASSWORD_URL = API_DOMAIN_S + "/users/password";
        //this.CHANGE_PASSWORD_URL = API_DSPRO_DOMAIN_S +"/v2/universalapi/customer_details/password";
        //this.MY_PURCHASES_URL = API_DSPRO_DOMAIN_S +"/v2/universalapi/collect_video_information";
        MY_PURCHASES_URL = API_DOMAIN_S + "/users/orders/history";
        VIDEO_PURCHASE_STATUS = API_DSPRO_DOMAIN_S +"/v2/universalapi/paywall_status";
        CUSTOMER_REGISTER = API_DSPRO_DOMAIN_S +"/v2/universalapi/customer_register";

        APP_VERSION_ANDROID_API = API_DOMAIN_S + "/latestAppVersion/package/android/";
        APP_VERSION_ANDROID_API_S = API_DOMAIN_S + "/latestAppVersion/package/android/";
        APP_VERSION_FIRE_TV_API = API_DOMAIN_S + "/latestAppVersion/package/fire_tv/";
        APP_VERSION_FIRE_TV_API_S = API_DOMAIN_S + "/latestAppVersion/package/fire_tv/";
        APP_VERSION_ANDROID_TV_API = API_DOMAIN_S + "/latestAppVersion/package/android_tv/";
        APP_VERSION_ANDROID_TV_API_s = API_DOMAIN_S + "/latestAppVersion/package/android_tv/";

        BLOG_LIST = BLOG_LIST_ABS + "/wp-json/dsp/v1/featured/blog/carousel";
        BLOG_LIST_BY_CATEGORY = BLOG_LIST_BY_CATEGORY + "/wp-json/abs/v1/posts/bycategory/";

        //this.AVATAR = SPOTLIGHT_DOMAIN + "/user/avatar/";
        AVATAR = API_DOMAIN_S + "/users/avatar/";
        //this.CATEGORIES_LIST = SPOTLIGHT_DOMAIN + "/categories/list";
        HOMEPAGE_CATEGORIES_LIST = API_SPOTLIGHT_DOMAIN+"/wp-json/dsp/v1/homepage";

        SUBSCRIPTION_LIST = API_DOMAIN_S + "/subscriptions/summary";
        ACTIVE_SUBSCRIPTIONS_LIST = API_DOMAIN_S + "/subscriptions/users/active_subscriptions";
        CHECK_CHANNEL_SUBSCRIPTIONS_STATUS = API_DOMAIN_S + "/subscriptions/check/";
        CREATE_BRAINTREE_CUSTOMER_FROM_NONCE = API_DOMAIN_S + "/subscriptions/users/create_from_nonce";
        CREATE_CHARGIFY_CUSTOMER_USING_SUBSCRIPTION_ID = API_DOMAIN_S + "/subscriptions/users/import/subscribe_to/";

        CATEGORIES_LIST = API_DOMAIN_S + "/categories/"+COUNTRY_CODE+"/";
        //this.CATEGORIES_LIST ="/categories/"+COUNTRY_CODE+"/";
        //this.CHANNELS = SPOTLIGHT_DOMAIN + "json/channels";
        CHANNELS = API_DOMAIN_S + "/channels/"+COUNTRY_CODE+"/";
        CHANNEL = API_DOMAIN_S + "/channel/"+COUNTRY_CODE+"/";
        CHANNELS_DETAILS = API_DOMAIN_S + "/channels/"+COUNTRY_CODE+"/";
        HOMEPAGE = API_DOMAIN_S + "/homepage/"+COUNTRY_CODE+"/";


        SEARCH_API_URL = API_DOMAIN_S + "/search/";
        SEARCH_VIDEO_API_URL = API_DOMAIN_S + "/search/videos/";
        SEARCH_SUGGESTER_API_URL = API_DOMAIN_S + "/search/s/";
        SEARCH_BY_COMPANY_API_URL = SPOTLIGHT_DOMAIN + "/company/";
        SEARCH_BY_COMPANY_DATA_FORMAT = "/json";

        IMAGES = IMAGES_DSPRO_DOMAIN_S;
        //this.VIDEOS_API = API_DSPRO_DOMAIN_S +"/v2/cc89512b/json/videos/";
        //this.VIDEOS_API = API_DSPRO_DOMAIN_S +"/v2/cc89512b/json/videos/";
        VIDEOS_API = API_DOMAIN_S + "/channels/"+COUNTRY_CODE+"/";

        VIDEO_PLAY2_API = API_DOMAIN_S + "/video/play2/";

        PLAYLIST_VIDEOS = API_DSPRO_DOMAIN_S +"/v2/cc89512b/json/playlists/";
        GET_FIRST_LAST_NAME_API = API_DSPRO_DOMAIN_S +"/v2/universalapi/customer_details";
        SAVE_FIRST_LAST_NAME_API = API_DSPRO_DOMAIN_S + "/v2/universalapi/customer_details/edit";
        FORGOT_PASSWORD_API = API_DSPRO_DOMAIN_S +"/v2/universalapi/forgotpassword";

        /*this.CREATE_ANALYTICS_USER_API = COLLECTOR_DEV_DOMAIN_S +"/users";
        SAVE_PLAYER_DATA_API = COLLECTOR_DEV_DOMAIN_S +"/plays";
        SAVE_APP_DATA_API = COLLECTOR_DEV_DOMAIN_S +"/players";*/
        CREATE_ANALYTICS_USER_API = COLLECTOR_DSPRO_DOMAIN_S +"/users";
        SAVE_PLAYER_DATA_API = COLLECTOR_DSPRO_DOMAIN_S +"/plays";
        SAVE_APP_DATA_API = COLLECTOR_DSPRO_DOMAIN_S +"/players";

        //this.CLIENT_TOKEN_API = MYSPOTLIGHT_DOMAIN +"/dotstudio_token";
        CLIENT_TOKEN_API = API_DOMAIN_S + "/users/payment/token";
        //this.RENT_API = MYSPOTLIGHT_DOMAIN +"/rent";
        RENT_API = API_DOMAIN_S + "/users/payment/purchase";
        RENT_API_ANDROID = API_DOMAIN_S + "/users/payment/android";

        RECEIPT_VERIFY_API_FIRE_TV = API_DOMAIN_S + "/users/payment/fire_tv";
        RENT_STATUS_API = API_DOMAIN_S + "/users/payment/status";
        SHARING_DOMAIN_NAME = MYSPOTLIGHT_DOMAIN +"/watch";

        ADSERVER_API = ADSERVER_DOMAIN +"/adserver/www/delivery/fc.php?script=apVideo:vast2&zoneid=";

        VIDEO_PLAYBACK_DETAILS_API = API_DOMAIN_S + "/users/videos/point/";
        MULTIPLE_VIDEO_PLAYBACK_DETAILS_API = API_DOMAIN_S + "/users/videos/points/";
        LAST_WATCHED_VIDEOS_API = API_DOMAIN_S + "/users/resumption/videos";

        ADD_TO_MY_LIST_API = API_DOMAIN_S + "/watchlist/channels/add";
        DELETE_FROM_MY_LIST_API = API_DOMAIN_S + "/watchlist/channels/delete";
        GET_MY_LIST_API = API_DOMAIN_S + "/watchlist/channels";

        RECOMMENDATION_API = API_DOMAIN_S + "/search/recommendation";
        RECOMMENDATION_CHANNEL_API = API_DOMAIN_S + "/search/recommendation/channel";

        ANALYTICS_TESTING_URL = API_DOMAIN_S + "/testing/analytics";
        CLIENT_REFRESH_TOKEN = API_DOMAIN_S + "/users/token/refresh";

        SUBSCRIPTION_API = API_DOMAIN_S + "/subscriptions/google/parse";

        RECOMMENDATION_CHANNEL_FIRE_TV_API = "/channel/"+COUNTRY_CODE+"/recommendationsamazonfire";

        HOMEPAGE_API_FIRE_TV = "/homepage/"+COUNTRY_CODE+"/firetv";
        HOMEPAGE_API_ANDROID_TV = "/homepage/"+COUNTRY_CODE+"/androidtv";
        HOMEPAGE_API_ANDROID = "/homepage/"+COUNTRY_CODE+"/android";
    }




    public void setCountryCode(String countryCode) {
        this.COUNTRY_CODE                       = countryCode;

        this.CATEGORIES_LIST                    = API_DOMAIN_S + "/categories/"+countryCode+"/";
        this.CHANNEL                            = API_DOMAIN_S + "/channel/"+countryCode+"/";
        this.CHANNELS                           = API_DOMAIN_S + "/channels/"+countryCode+"/";
        this.CHANNELS_DETAILS                   = API_DOMAIN_S + "/channels/"+countryCode+"/";
        this.HOMEPAGE                           = API_DOMAIN_S + "/homepage/"+countryCode+"/";
        this.VIDEOS_API                         = API_DOMAIN_S + "/channels/"+countryCode+"/";
        this.RECOMMENDATION_CHANNEL_FIRE_TV_API = API_DOMAIN_S + "/channel/"+countryCode+"/recommendationsamazonfire";

        this.HOMEPAGE_API_FIRE_TV               = API_DOMAIN_S + "/homepage/"+countryCode+"/firetv";
        this.HOMEPAGE_API_ANDROID_TV            = API_DOMAIN_S + "/homepage/"+countryCode+"/androidtv";
        this.HOMEPAGE_API_ANDROID               = API_DOMAIN_S + "/homepage/"+countryCode+"/android";

    }

    
    public static String TOKEN_RESPONSE_SHARED_PREFERENCE;
    public static String IS_FB_USER_RESPONSE_SHARED_PREFERENCE;
    public static String FACEBOOK_RESPONSE_SHARED_PREFERENCE;
    public static String USER_DETAILS_RESPONSE_SHARED_PREFERENCE;
    public static String USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE;
    public static String AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE;
    public static String AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE;
    public static String LIVE_TICKER_PREFERENCE_RESPONSE_SHARED_PREFERENCE;
    public static String USER_ID_FOR_REWARDS_POINTS_SHARED_PREFERENCE;
    public static String USER_NAME_FOR_REWARDS_POINTS_SHARED_PREFERENCE;

    public static String TOKEN_RESPONSE_SHARED_PREFERENCE_KEY = "xAccessToken";
    public static String IS_FB_USER_RESPONSE_SHARED_PREFERENCE_KEY = "isFBUser";
    public static String FACEBOOK_RESPONSE_SHARED_PREFERENCE_KEY = "authenticDetails";
    public static String USER_DETAILS_RESPONSE_SHARED_PREFERENCE_KEY = "authenticDetails";
    public static String USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE_KEY = "userEmailId";
    public static String AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE_KEY = "auth0idtoken";
    public static String AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE_KEY = "auth0refreshtoken";
    public static String LIVE_TICKER_PREFERENCE_RESPONSE_SHARED_PREFERENCE_KEY = "liveTickerVisibilityPreference";
    public static String USER_ID_FOR_REWARDS_POINTS_SHARED_PREFERENCE_KEY = "userID";
    public static String USER_NAME_FOR_REWARDS_POINTS_SHARED_PREFERENCE_KEY = "userName";
    
    
    public static void setSharedPreferenceNames(
            String TOKEN_RESPONSE_SHARED_PREFERENCE,
            String IS_FB_USER_RESPONSE_SHARED_PREFERENCE,
            String FACEBOOK_RESPONSE_SHARED_PREFERENCE,
            String USER_DETAILS_RESPONSE_SHARED_PREFERENCE,
            String USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE,
            String AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE,
            String AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE,
            String LIVE_TICKER_PREFERENCE_RESPONSE_SHARED_PREFERENCE
    ) {

        SPLTRouter.TOKEN_RESPONSE_SHARED_PREFERENCE = TOKEN_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.IS_FB_USER_RESPONSE_SHARED_PREFERENCE = IS_FB_USER_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.FACEBOOK_RESPONSE_SHARED_PREFERENCE = FACEBOOK_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE = USER_DETAILS_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE = USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE = AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE = AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.LIVE_TICKER_PREFERENCE_RESPONSE_SHARED_PREFERENCE = LIVE_TICKER_PREFERENCE_RESPONSE_SHARED_PREFERENCE;

    }
    public static void setSharedPreferenceNames(
            String TOKEN_RESPONSE_SHARED_PREFERENCE,
            String IS_FB_USER_RESPONSE_SHARED_PREFERENCE,
            String FACEBOOK_RESPONSE_SHARED_PREFERENCE,
            String USER_DETAILS_RESPONSE_SHARED_PREFERENCE,
            String USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE,
            String AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE,
            String AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE
    ) {

        SPLTRouter.TOKEN_RESPONSE_SHARED_PREFERENCE = TOKEN_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.IS_FB_USER_RESPONSE_SHARED_PREFERENCE = IS_FB_USER_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.FACEBOOK_RESPONSE_SHARED_PREFERENCE = FACEBOOK_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE = USER_DETAILS_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE = USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE = AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE = AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE;

    }

    public static void setSharedPreferenceNames(
            String TOKEN_RESPONSE_SHARED_PREFERENCE,
            String IS_FB_USER_RESPONSE_SHARED_PREFERENCE,
            String FACEBOOK_RESPONSE_SHARED_PREFERENCE,
            String USER_DETAILS_RESPONSE_SHARED_PREFERENCE,
            String USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE,
            String AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE,
            String AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE,
            String USER_ID_FOR_REWARDS_POINTS_SHARED_PREFERENCE,
            String USER_NAME_FOR_REWARDS_POINTS_SHARED_PREFERENCE
    ) {

        SPLTRouter.TOKEN_RESPONSE_SHARED_PREFERENCE = TOKEN_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.IS_FB_USER_RESPONSE_SHARED_PREFERENCE = IS_FB_USER_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.FACEBOOK_RESPONSE_SHARED_PREFERENCE = FACEBOOK_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE = USER_DETAILS_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE = USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE = AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE = AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE;
        SPLTRouter.USER_ID_FOR_REWARDS_POINTS_SHARED_PREFERENCE = USER_ID_FOR_REWARDS_POINTS_SHARED_PREFERENCE;
        SPLTRouter.USER_NAME_FOR_REWARDS_POINTS_SHARED_PREFERENCE = USER_NAME_FOR_REWARDS_POINTS_SHARED_PREFERENCE;

    }

}
