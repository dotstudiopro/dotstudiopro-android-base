package com.dotstudioz.phone.baseclasses;

import android.support.v7.app.AppCompatActivity;

import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.phone.component.shared.AppBusyDialog;
import com.dotstudioz.phone.util.SharedPreferencesUtil;

public class SPLTBaseActivity extends AppCompatActivity {

    private String TAG = "SPLTBaseActivity";
    private AppBusyDialog progressDialog = null;

    public SPLTBaseActivity(){
        try {
            String accessToken = SharedPreferencesUtil.getInstance(this).getSharedPreference(
                    SPLTRouter.TOKEN_RESPONSE_SHARED_PREFERENCE,
                    SPLTRouter.TOKEN_RESPONSE_SHARED_PREFERENCE_KEY);
            if(accessToken != null && accessToken.length() > 0)
                SPLTRouter.getInstance().setStrAccessToken(accessToken);

        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            String clientToken = SharedPreferencesUtil.getInstance(this).getSharedPreference(
                    SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE,
                    SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE_KEY);

            if(clientToken != null && clientToken.length() > 0)
                SPLTRouter.getInstance().setStrClientToken(clientToken);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Show ProgressDialog
     * */
    public void showProgress() {
        try{
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
            progressDialog = new AppBusyDialog(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Dismiss custom dialog box
     */
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
