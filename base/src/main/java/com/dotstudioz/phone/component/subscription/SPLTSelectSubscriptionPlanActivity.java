package com.dotstudioz.phone.component.subscription;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.dotstudioPRO.models.dto.SubscriptionDTO;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstantURL;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstants;
import com.dotstudioz.dotstudioPRO.services.services.GetAllSubscriptionsService_V1;
import com.dotstudioz.phone.R;
import com.dotstudioz.phone.baseclasses.SPLTBaseActivity;
import com.dotstudioz.phone.component.login.SPLTLoginActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SPLTSelectSubscriptionPlanActivity extends SPLTBaseActivity {
    protected String TAG ="SPLTSelectSubscriptionPlanActivity";
    public static int REQUEST_CODE_HANDLE_LOGIN_ACTIVITY_RESULT = 101;

    public static int RESULT_CODE_SUBSCIRPTION_SUCCESS = 1;
    public static int RESULT_CODE_SUBSCIRPTION_FAILURE = 2;
    public static int RESULT_CODE_SUBSCIRPTION_CANCEL = 3;

    protected Button  btnSkip;//btnSubmit,
    protected TextView textSubsribtionTitle, textSubsribtionTitleText;
    protected LinearLayout lnrSubscriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splt_subscription_plan);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        init();
        initListener();
        //check logigged in or not
        checkSubscription();

    }
    protected  void init(){}

    public void initListener(){


        if(btnSkip != null){
            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    setResult(RESULT_CODE_SUBSCIRPTION_CANCEL,intent);
                    finish();

                    /*Intent intent = new Intent();
                    setResult(RESULT_CODE_SUBSCIRPTION_SUCCESS,intent);
                    finish();*/
                }
            });
        }
    }

    private void checkSubscription(){

        ApplicationConstants.xAccessToken = SPLTRouter.getInstance().getStrAccessToken();
        ApplicationConstants.CLIENT_TOKEN = SPLTRouter.getInstance().getStrClientToken();

        GetAllSubscriptionsService_V1 getAllSubscriptionsService_v1 = new GetAllSubscriptionsService_V1(this);
        getAllSubscriptionsService_v1.setGetAllSubscriptionsServiceListener(new GetAllSubscriptionsService_V1.IGetAllSubscriptionsService() {
            @Override
            public void getAllSubscriptionsServiceResponse(ArrayList<SubscriptionDTO> subscriptionDTOArrayList) {
                Log.d(TAG, "getAllSubscriptionsServiceResponse: "+subscriptionDTOArrayList.size());
                createRow(subscriptionDTOArrayList);
            }

            @Override
            public void getAllSubscriptionsError(String ERROR) {
                Log.d(TAG, "getAllSubscriptionsError: "+ERROR);
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
        getAllSubscriptionsService_v1.getAllSubscriptionsService(ApplicationConstants.xAccessToken, ApplicationConstantURL.getInstance().SUBSCRIPTION_LIST);

    }

    public void createRow(ArrayList<SubscriptionDTO> subscriptionDTOArrayList) {}



    public Intent loginIntent;
    public void showLogin() {
        if(this.loginIntent == null){
            loginIntent = new Intent(SPLTSelectSubscriptionPlanActivity.this, SPLTLoginActivity.class);
        }
        startActivityForResult(loginIntent, REQUEST_CODE_HANDLE_LOGIN_ACTIVITY_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_HANDLE_LOGIN_ACTIVITY_RESULT){
            handleLoginActivityResult(requestCode,resultCode, data);
        }

    }
    private void handleLoginActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == SPLTLoginActivity.RESULT_CODE_SUCCESS){
            //after login success
            //check subscription
        }else {
            finish();
        }
    }
}
