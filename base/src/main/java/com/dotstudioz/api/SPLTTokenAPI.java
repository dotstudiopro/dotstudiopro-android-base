package com.dotstudioz.api;

import android.content.Context;
import android.util.Log;

import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstantURL;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstants;
import com.dotstudioz.dotstudioPRO.services.services.CompanyTokenService;
import com.dotstudioz.phone.util.SharedPreferencesUtil;

import org.json.JSONObject;

public class SPLTTokenAPI {
    private String TAG ="SPLTTokenAPI";
    //callback
    public Context context = null;
    public Callback mCallback = null;


    public interface Callback {
        void onTokenSuccess(String token);
        void onTokenError(String responseBody);
    }

    public SPLTTokenAPI(Context context){
        this.context = context;
        if (this.context instanceof Callback) {
            mCallback = (Callback) context;
        }
    }
    // Assign the listener implementing events interface that will receive the events
    public void setSPLTTokenAPICallback(Callback callback) {
        this.mCallback = callback;
    }
    public void getToken(){
        if (mCallback == null) {
            if (context != null && context instanceof Callback) {
                mCallback = (Callback) context;
            }
            if (mCallback == null) {
                throw new RuntimeException(context.toString()+ " must implement SPLTTokenAPI.Callback or setSPLTTokenAPICallback");
            }
        }
        CompanyTokenService companyTokenService = new CompanyTokenService(this.context);
        companyTokenService.setCompanyTokenServiceListener(new CompanyTokenService.ICompanyTokenService() {
            @Override
            public void companyTokenServiceResponse(JSONObject responseBody) {
                String token = "";
                try {

                    if(responseBody == null){
                        mCallback.onTokenError("Null Response");
                        return;
                    }
                    if(responseBody != null && responseBody.has("token")){
                        token = responseBody.getString("token");
                        setToken(token);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    mCallback.onTokenError(e.getMessage());
                }
                mCallback.onTokenSuccess(token);
            }

            @Override
            public void companyTokenServiceError(String responseError) {
                mCallback.onTokenError(responseError);
            }
        });
        Log.d(TAG, "getToken: COMPANY_KEY"+SPLTRouter.getInstance().COMPANY_API_KEY + " TOKEN_URL:"+SPLTRouter.TOKEN_URL);
        companyTokenService.requestForToken(SPLTRouter.getInstance().COMPANY_API_KEY, SPLTRouter.getInstance().TOKEN_URL);
    }
    public void setToken(String token){
        SPLTRouter.getInstance().setStrAccessToken(token);
        SharedPreferencesUtil.getInstance(context).addToSharedPreference(
                ApplicationConstants.TOKEN_RESPONSE_SHARED_PREFERENCE,
                token,
                ApplicationConstants.TOKEN_RESPONSE_SHARED_PREFERENCE_KEY);
    }

}
