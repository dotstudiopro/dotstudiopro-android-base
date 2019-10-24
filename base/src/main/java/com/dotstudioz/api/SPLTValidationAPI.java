package com.dotstudioz.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.dotstudioz.dotstudioPRO.models.dto.ValidateAppVersionDTO;
import com.dotstudioz.dotstudioPRO.services.appversionvalidation.ValidateAppVersionAPI;
import com.dotstudioz.dotstudioPRO.services.services.LatitudeAndLongitudeService;
import com.dotstudioz.phone.util.Utility;

public class SPLTValidationAPI {
    private String TAG="SPLTValidationAPI";
    //callback
    public Context mContext = null;
    public Callback mCallback = null;
    private int accessTokenExpirycounter = 0;

    public interface Callback {
        void onValidationSuccess();
        //void onAccessTokenExpired();
    }

    public SPLTValidationAPI(Context context){
        this.mContext = context;
        if (this.mContext instanceof Callback) {
            mCallback = (Callback) context;
        }
    }
    // Assign the listener implementing events interface that will receive the events
    public void setSPLTValidationAPICallback(Callback callback) {
        this.mCallback = callback;
    }
    public void getValidation(){
        if (mCallback == null) {
            if (mContext != null && mContext instanceof Callback) {
                mCallback = (Callback) mContext;
            }
            if (mCallback == null) {
                throw new RuntimeException(mContext.toString()+ " must implement SPLTValidationAPI.Callback or setSPLTValidationAPICallback");
            }
        }
        if(SPLTRouter.getInstance().APP_VERSION_API == null || SPLTRouter.getInstance().APP_VERSION_API.trim().length() == 0){
            Log.e(TAG,"APP_VALIDATION_URL NULL ");
            throw new NullPointerException("APP_VALIDATION_URL not defined");
        }
        if(SPLTRouter.getInstance().PACKAGE_NAME == null || SPLTRouter.getInstance().PACKAGE_NAME.trim().length() == 0){
            Log.e(TAG,"PACKAGE_NAME NULL ");
            throw new NullPointerException("PACKAGE_NAME not defined");
        }

        final ValidateAppVersionAPI validationService = new ValidateAppVersionAPI(mContext);
        validationService.setValidateAppVersionAPIListener(new ValidateAppVersionAPI.IValidateAppVersionAPI() {
            @Override
            public void getAppVersionResponse(boolean flag, ValidateAppVersionDTO validateAppVersionDTO) {
                //mCallback.onValidationResponse(flag,validateAppVersionDTO.getVersion(), validateAppVersionDTO.getSeverityLevel());
                if(flag){
                    handleVersionDialog(validateAppVersionDTO.getVersion(), validateAppVersionDTO.getSeverityLevel(), validationService);
                }else {
                    validationSuccess();
                }
            }

            @Override
            public void getAppVersionError(String ERROR) {
                //mCallback.onValidationError(ERROR);
                validationSuccess();
            }

            @Override
            public void accessTokenExpired() {
                //mCallback.onAccessTokenExpired();
                if(accessTokenExpirycounter < 5){
                    handleAccessTokenExpiry();
                }

            }
        });
        validationService.getAppVersion(SPLTRouter.getInstance().APP_VERSION_API, SPLTRouter.getInstance().PACKAGE_NAME);

    }
    private void handleAccessTokenExpiry(){
        accessTokenExpirycounter = accessTokenExpirycounter++;
        SPLTTokenAPI tokenAPI = new SPLTTokenAPI(mContext);
        tokenAPI.setSPLTTokenAPICallback(new SPLTTokenAPI.Callback() {
            @Override
            public void onTokenSuccess(String token) {
                Log.d(TAG, "onTokenSuccess: "+token);
                getValidation();
            }
            @Override
            public void onTokenError(String responseBody) {
                Log.d(TAG, "onTokenError: "+responseBody);
            }
        });
        tokenAPI.getToken();
    }
    private void validationSuccess(){
        mCallback.onValidationSuccess();
    }
    public boolean checkIfServerVersionIsGreater(String serverVersion, String appVersion) {
        return (new ValidateAppVersionAPI(mContext)).checkIfServerVersionIsGreater(serverVersion, appVersion);
    }

    private void handleVersionDialog(String serverVersion, int severityLevel, ValidateAppVersionAPI validationAPI) {
        String appVersion = "";
        try {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            appVersion = pInfo.versionName;
        } catch(Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "handleVersionDialog: version:"+appVersion +" SeverityLevel:"+severityLevel);
        if(severityLevel == 4) {
            String message = "Unfortunately, this app is no longer available!";
            String titleString = "App no longer available";
            boolean setNegativeButtonFlag = true;
            boolean cancellableFlag = true;
            String positiveButtonString = "OK";
            String negativeButtonString = "CANCEL";

            if (severityLevel == 1) {
                titleString = "Update Available";
                validationSuccess();
            } else if (severityLevel == 2) {
                titleString = "Update Recommended";
                validationSuccess();
            } else if (severityLevel == 3) {
                //forcing the user to update the app
                titleString = "Update Required";
                setNegativeButtonFlag = false;
                cancellableFlag = false;
            } else if (severityLevel == 4) {
                //forcing the user to update the app
                titleString = "App no longer available";
                setNegativeButtonFlag = false;
                cancellableFlag = false;
            }
            Utility.showCustomDialog(mContext, message,titleString,setNegativeButtonFlag,cancellableFlag,positiveButtonString,negativeButtonString,severityLevel);
        }else {
            if(!appVersion.equals(serverVersion)) {
                boolean checkIfServerVersionIsGreaterFlag = validationAPI.checkIfServerVersionIsGreater(serverVersion, appVersion);
                if(checkIfServerVersionIsGreaterFlag) {
                    String message = "For your app to function properly a required update is now available in the App Store.";
                    String titleString = "Update Available";
                    String positiveButtonString = "Update Now";
                    boolean setNegativeButtonFlag = true;
                    String negativeButtonString = "Later";
                    boolean cancellableFlag = true;
                    if (severityLevel == 1) {
                        titleString = "Update Available";
                        validationSuccess();
                    } else if (severityLevel == 2) {
                        titleString = "Update Recommended";
                        validationSuccess();
                    } else if (severityLevel == 3) {
                        //forcing the user to update the app
                        titleString = "Update Required";
                        setNegativeButtonFlag = false;
                        cancellableFlag = false;
                    }
                    Utility.showCustomDialog(mContext, message,titleString,setNegativeButtonFlag,cancellableFlag,positiveButtonString,negativeButtonString,severityLevel);
                }else {
                    validationSuccess();
                }
            }else {
                validationSuccess();
            }
        }
    }
}
