package com.dotstudioz.api;

import android.content.Context;
import android.util.Log;

import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstantURL;
import com.dotstudioz.dotstudioPRO.services.services.LatitudeAndLongitudeService;

import org.json.JSONException;
import org.json.JSONObject;

public class SPLTLatLonAPI {
    public String TAG = "SPLTLatLonAPI";
    //callback
    public Context mContext = null;
    public Callback mCallback = null;


    public interface Callback {
        void onLatLonSuccess(String responseBody);
        void onLatLonError(String responseError);
    }

    public SPLTLatLonAPI(Context context){
        this.mContext = context;
        if (this.mContext instanceof Callback) {
            mCallback = (Callback) context;
        }
    }
    // Assign the listener implementing events interface that will receive the events
    public void setSPLTLatLonAPICallback(Callback callback) {
        this.mCallback = callback;
    }
    public void getLatLon(){
        if (mCallback == null) {
            if (mContext != null && mContext instanceof Callback) {
                mCallback = (Callback) mContext;
            }
            if (mCallback == null) {
                throw new RuntimeException(mContext.toString()+ " must implement SPLTLatLonAPI.Callback or setSPLTLatLonAPICallback");
            }
        }
        LatitudeAndLongitudeService latLonService = new LatitudeAndLongitudeService(this.mContext);
        latLonService.setLatitudeAndLongitudeServiceListener(new LatitudeAndLongitudeService.LatitudeAndLongitudeInterface() {
            @Override
            public void latitudeAndLongitudeResponse(String ACTUAL_RESPONSE) {
                try {
                    if(ACTUAL_RESPONSE == null){
                        mCallback.onLatLonError("Null Response");
                        return;
                    }
                    try {
                        JSONObject resultJSONObject = new JSONObject(ACTUAL_RESPONSE);
                        if (resultJSONObject.has("data")) {
                            if (resultJSONObject.getJSONObject("data").has("countryCode")) {
                                String ISO_CODE = resultJSONObject.getJSONObject("data").getString("countryCode");
                                SPLTRouter.getInstance().setCountryCode(ISO_CODE);
                                SPLTRouter.getInstance().ISO_CODE = resultJSONObject.getJSONObject("data").getString("countryCode");
                                SPLTRouter.getInstance().COUNTRY = resultJSONObject.getJSONObject("data").getString("countryName");
                                SPLTRouter.getInstance().longitudeFloat = resultJSONObject.getJSONObject("data").getLong("longitude");
                                SPLTRouter.getInstance().latitudeFloat = resultJSONObject.getJSONObject("data").getLong("latitude");
                                SPLTRouter.getInstance().setCountryCode(ISO_CODE);

                                ApplicationConstantURL.getInstance().setCountryCode(ISO_CODE);
                                ApplicationConstantURL.getInstance().setCountryCode(ISO_CODE);


                                Log.d("getLonLatCountry", ISO_CODE + "-" + ISO_CODE + "-" + SPLTRouter.getInstance().longitudeFloat + "-" + SPLTRouter.getInstance().latitudeFloat);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //SPLTRouter.getInstance().setCountryCode(ACTUAL_RESPONSE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                mCallback.onLatLonSuccess(ACTUAL_RESPONSE);
            }

            @Override
            public void latitudeAndLongitudeError(String ERROR) {
                mCallback.onLatLonError(ERROR);
            }
        });
        Log.d(TAG, "getLatLon: AccessToken :"+SPLTRouter.getInstance().getStrAccessToken() + " LON_LAT_COUNTRY:"+SPLTRouter.getInstance().LON_LAT_COUNTRY);
        latLonService.getLatitudeAndLongitude(SPLTRouter.getInstance().getStrAccessToken(), SPLTRouter.getInstance().LON_LAT_COUNTRY);
    }

}
