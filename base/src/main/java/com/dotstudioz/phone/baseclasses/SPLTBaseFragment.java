package com.dotstudioz.phone.baseclasses;

import android.support.v4.app.Fragment;

import com.dotstudioz.phone.component.shared.AppBusyDialog;

public class SPLTBaseFragment extends Fragment {


    private AppBusyDialog progressDialog = null;

    /**
     * Show ProgressDialog
     * */
    public void showProgress() {
        try{
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
            progressDialog = new AppBusyDialog(getActivity());
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
