package com.dotstudioz.phone.baseclasses;

import android.support.v7.app.AppCompatActivity;

import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.phone.component.shared.AppBusyDialog;
import com.dotstudioz.phone.util.SharedPreferencesUtil;

public class SPLTBaseActivity extends AppCompatActivity {

    private String TAG = "SPLTBaseActivity";
    private AppBusyDialog progressDialog = null;

    public SPLTBaseActivity(){

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
