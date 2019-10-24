package com.dotstudioz.phone.component.splash;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.dotstudioz.api.SPLTLatLonAPI;
import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.api.SPLTTokenAPI;
import com.dotstudioz.api.SPLTValidationAPI;
import com.dotstudioz.dotstudioPRO.models.dto.ValidateAppVersionDTO;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstantURL;
import com.dotstudioz.phone.R;
import com.dotstudioz.phone.baseclasses.SPLTBaseActivity;
import com.dotstudioz.phone.component.home.SPLTHomeActivity;
import com.dotstudioz.phone.component.intro.SPLTIntroActivity;
import com.dotstudioz.phone.component.login.SPLTLoginActivity;
import com.dotstudioz.phone.component.subscription.SPLTSelectSubscriptionPlanActivity;
import com.dotstudioz.phone.util.SharedPreferencesUtil;
import com.dotstudioz.phone.util.Utility;

import org.json.JSONObject;

public class SPLTSplashActivity extends SPLTBaseActivity {

    public static int REQUEST_CODE_HANDLE_INTRO_ACTIVITY_RESULT = 100;
    public static int REQUEST_CODE_HANDLE_LOGIN_ACTIVITY_RESULT = 101;
    public static int REQUEST_CODE_HANDLE_SUBSCRIPTION_ACTIVITY_RESULT = 102;

    private static final String TAG = "SPLTSplashActivity";
    public static final int REQUEST_CODE = 1;

    protected boolean isVideo;
    protected String videoSourceString;
    public VideoView videoView;

    protected static int SPLASH_TIME_OUT = 2000;
    private int idleWidth = 0;
    private int idleHeight = 0;

    private boolean isSplashOver = false;
    private boolean isAPICallOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splt_splash);

        // initialization
        init();

        validateAppVersion();
        //checkTokenAPI();
        //checkLatLonAPI();

        if(this.isVideo){
            this.SPLASH_TIME_OUT = 0;
        }
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        try {
            idleWidth = displaymetrics.widthPixels;
            idleHeight = ((displaymetrics.widthPixels * 9) /16);

            if(idleHeight > displaymetrics.heightPixels) {
                idleHeight = displaymetrics.heightPixels;
                idleWidth = ((displaymetrics.heightPixels * 16) / 9);
            }
            if(isVideo){
                setVideo();
            }else {
                setImage();
            }

        } catch(Exception e) {
            splashOver();
            finish();
        }
    }
    protected  void init(){}

    /**
     * Initializ
     */
    private void validateAppVersion(){
        ApplicationConstantURL.getInstance().API_DOMAIN_S = SPLTRouter.getInstance().API_DOMAIN_S;
        ApplicationConstantURL.getInstance().IMAGES_DSPRO_DOMAIN_S = SPLTRouter.getInstance().IMAGES_DSPRO_DOMAIN_S;

        //Reset all URL's
        ApplicationConstantURL.getInstance().setAPIDomain();

        final SPLTValidationAPI validationAPI = new SPLTValidationAPI(this);
        validationAPI.setSPLTValidationAPICallback(new SPLTValidationAPI.Callback() {
            @Override
            public void onValidationSuccess() {
                getToken();
            }
        });
        validationAPI.getValidation();
    }

    private void getToken(){
        String clientToken = SharedPreferencesUtil.getInstance(this).getSharedPreference(
                SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE,
                SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE_KEY);
        if(clientToken != null && clientToken.trim().length()>0){
            SPLTRouter.getInstance().setStrClientToken(clientToken);
        }

        String accessToken = SharedPreferencesUtil.getInstance(this).getSharedPreference(
                SPLTRouter.TOKEN_RESPONSE_SHARED_PREFERENCE,
                SPLTRouter.TOKEN_RESPONSE_SHARED_PREFERENCE_KEY);
        if(accessToken != null && accessToken.trim().length()>0){

            Log.d(TAG, "onTokenSuccess: token"+accessToken);
            SPLTRouter.getInstance().setStrAccessToken(accessToken);
            getLatLonCountry();
        }else {
            SPLTTokenAPI tokenAPI = new SPLTTokenAPI(this);
            tokenAPI.setSPLTTokenAPICallback(new SPLTTokenAPI.Callback() {
                @Override
                public void onTokenSuccess(String token) {
                    Log.d(TAG, "onTokenSuccess: "+token);
                    getLatLonCountry();
                }
                @Override
                public void onTokenError(String responseBody) {
                    Log.d(TAG, "onTokenError: "+responseBody);
                    Utility.showCustomDialog(SPLTSplashActivity.this, "Token Error",responseBody, false, false, "OK","Cancel",4);
                }
            });
            tokenAPI.getToken();
        }
    }

    private void getLatLonCountry(){
        SPLTLatLonAPI latLonAPI = new SPLTLatLonAPI(this);
        latLonAPI.setSPLTLatLonAPICallback(new SPLTLatLonAPI.Callback() {
            @Override
            public void onLatLonSuccess(String responseBody) {
                Log.d(TAG, "onLatLonSuccess: "+responseBody);
                if(isSplashOver){
                    splashOver();
                }else {
                    isAPICallOver = true;
                }
            }
            @Override
            public void onLatLonError(String responseError) {
                Log.d(TAG, "onLatLonError: "+responseError);
                Utility.showCustomDialog(SPLTSplashActivity.this, "Country Error",responseError, false, false, "OK","Cancel",4);
            }
        });
        latLonAPI.getLatLon();
    }

    protected void setImage(){
        setHandler();
    }
    protected void setVideo(){
        if(videoSourceString == null || videoSourceString.trim().length() == 0){
            Log.e(TAG,"videoSourceString NULL ");
            throw new NullPointerException("Video path not defined");
        }
        ((RelativeLayout.LayoutParams)videoView.getLayoutParams()).width = idleWidth;
        ((RelativeLayout.LayoutParams)videoView.getLayoutParams()).height = idleHeight;

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                int videoWidth = mp.getVideoWidth();
                int videoHeight = mp.getVideoHeight();

                DisplayMetrics displaymetrics1 = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics1);

                float ratio = (((float)videoWidth) / ((float)videoHeight));

                int widthToUse = displaymetrics1.widthPixels;
                int heightToUse = (int) (displaymetrics1.heightPixels * ratio);

                Log.d(TAG, "onPrepared: displaymetrics1.wid==>"+displaymetrics1.widthPixels);
                Log.d(TAG, "onPrepared: displaymetrics1.hei==>"+displaymetrics1.heightPixels);

                ((RelativeLayout.LayoutParams)videoView.getLayoutParams()).width = widthToUse;
                ((RelativeLayout.LayoutParams)videoView.getLayoutParams()).height = heightToUse;

                Log.d(TAG, "onPrepared: widthToUse==>"+widthToUse);
                Log.d(TAG, "onPrepared: heightToUse==>"+heightToUse);

            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                setHandler();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                setHandler();
                return false;
            }
        });
        try {
            videoView.setVideoURI(Uri.parse(videoSourceString));
            videoView.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void setHandler(){
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(isAPICallOver){
                    splashOver();
                }else {
                    isSplashOver = true;
                    showProgress();
                }
            }
        }, SPLASH_TIME_OUT);
    }
    public void splashOver() {
        hideProgress();
        //showHome(); if user logged in

        if(SPLTRouter.getInstance().isUserLoggedIn()){
            showHome();
        }else {
            showIntro();
        }
    }

    public Intent intorIntent;
    public void showIntro(){
        if(this.intorIntent == null){
            intorIntent = new Intent(this, SPLTIntroActivity.class);
        }
        startActivityForResult(intorIntent, REQUEST_CODE_HANDLE_INTRO_ACTIVITY_RESULT);
    }

    public Intent loginIntent;
    public void showLogin() {
        if(this.loginIntent == null){
            loginIntent = new Intent(this, SPLTLoginActivity.class);
        }
        startActivityForResult(loginIntent, REQUEST_CODE_HANDLE_LOGIN_ACTIVITY_RESULT);
    }

    public Intent subscriptionIntent;
    public void showSubscription() {
        if(this.subscriptionIntent == null){
            subscriptionIntent = new Intent(this, SPLTSelectSubscriptionPlanActivity.class);
        }
        startActivityForResult(subscriptionIntent, REQUEST_CODE_HANDLE_SUBSCRIPTION_ACTIVITY_RESULT);
    }

    public Intent homeIntent;
    public void showHome() {
        if(this.homeIntent == null){
            homeIntent = new Intent(this, SPLTHomeActivity.class);
        }
        startActivity(homeIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_HANDLE_INTRO_ACTIVITY_RESULT ){
            handleIntroActivityResult(requestCode,resultCode, data);
        }else if(requestCode == REQUEST_CODE_HANDLE_LOGIN_ACTIVITY_RESULT ){
            handleLoginActivityResult(requestCode,resultCode, data);
        }else if(requestCode == REQUEST_CODE_HANDLE_SUBSCRIPTION_ACTIVITY_RESULT ){
            handleSubscriptionActivityResult(requestCode,resultCode, data);
        }
    }

    protected void handleSubscriptionActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == SPLTSelectSubscriptionPlanActivity.RESULT_CODE_SUBSCIRPTION_SUCCESS){
            showHome();
            finish();
        }else if(resultCode == SPLTSelectSubscriptionPlanActivity.RESULT_CODE_SUBSCIRPTION_CANCEL){
            showHome();
            finish();
        }else {
            finish();
        }
    }

    protected void handleLoginActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == SPLTLoginActivity.RESULT_CODE_SUCCESS){
            //check subscirpiton or go to home
            showSubscription();
        }else if(resultCode == SPLTLoginActivity.RESULT_CODE_CANCEL){
            showHome();
            finish();
        }else if(resultCode == SPLTLoginActivity.RESULT_CODE_FAIL){
            showHome();
            finish();
        }else {
            finish();
        }
    }

    protected void handleIntroActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == SPLTIntroActivity.RESULT_CODE_SUCCESS){
            showLogin();
        }else if(resultCode == SPLTIntroActivity.RESULT_CODE_SKIP){
            showHome();
            finish();
        }else {
            finish();
        }

    }

}
