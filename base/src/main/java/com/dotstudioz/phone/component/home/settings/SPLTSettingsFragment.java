package com.dotstudioz.phone.component.home.settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.phone.R;
import com.dotstudioz.phone.baseclasses.SPLTBaseFragment;
import com.dotstudioz.phone.component.login.SPLTLoginActivity;
import com.dotstudioz.phone.util.SharedPreferencesUtil;


public class SPLTSettingsFragment extends SPLTBaseFragment {
    private String TAG = "SPLTSettingsFragment";
    public static int REQUEST_CODE_HANDLE_LOGIN_ACTIVITY_RESULT = 101;


    protected TextView textTitle, textAppName, textHelp, textHelpContactUs, textLegal, textLegalPrivacy, textLegalTerms, textLegalCopyRight, textAppVersion, textAppSubscription;
    protected TextView textAppSignIn,textAppVersionName ;
    protected Button btnSubscriptionSubmit;

    public SPLTSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View dsView = initDS(inflater,container,savedInstanceState);
        refreshSignInButton();

        if(this.textAppSignIn != null){
            this.textAppSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(SPLTRouter.getInstance().isUserLoggedIn(getActivity())){
                        SharedPreferencesUtil.getInstance(getActivity()).removeFromSharedPreference(SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE,SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE_KEY);
                        refreshSignInButton();
                    }else {
                        showLogin();
                    }

                }
            });
        }

        if(this.btnSubscriptionSubmit != null){
            this.btnSubscriptionSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSubscription();
                }
            });
        }
        if(this.textHelpContactUs != null){
            this.textHelpContactUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showContactUs();
                }
            });
        }
        if(this.textLegalPrivacy != null){
            this.textLegalPrivacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLegalPrivacyPolicy();
                }
            });
        }
        if(this.textLegalTerms != null){
            this.textLegalTerms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTermsOfService();
                }
            });
        }
        if(this.textLegalCopyRight != null){
            this.textLegalCopyRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCopyright();
                }
            });
        }


        return dsView;
    }

    public Intent loginIntent;
    public void showLogin(){
        if(this.loginIntent == null){
            loginIntent = new Intent(getActivity(), SPLTLoginActivity.class);
        }
        startActivityForResult(loginIntent, REQUEST_CODE_HANDLE_LOGIN_ACTIVITY_RESULT);
    }

    public void refreshSignInButton(){}
    public void showSubscription(){}
    public void showContactUs(){}
    public void showLegalPrivacyPolicy(){}
    public void showTermsOfService(){}
    public void showCopyright(){}


    protected View initDS(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: "+requestCode);
        if(requestCode == REQUEST_CODE_HANDLE_LOGIN_ACTIVITY_RESULT ){
            handleLoginActivityResult(requestCode,resultCode, data);
        }
    }
    protected void handleLoginActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == SPLTLoginActivity.RESULT_CODE_SUCCESS){
            refreshSignInButton();
        }else if(resultCode == SPLTLoginActivity.RESULT_CODE_CANCEL){

        }else if(resultCode == SPLTLoginActivity.RESULT_CODE_FAIL){

        }
    }
}
