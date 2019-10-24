package com.dotstudioz.phone.component.shared;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.dotstudioz.phone.R;

public class AppBusyDialog extends Dialog {

    public AppBusyDialog(Context context) {
        super(context, R.style.styleCustomDialog);
        init("");
    }

    public AppBusyDialog(Context context, String message) {
        super(context, R.style.styleCustomDialog);
        init(message);
    }

    // initialize progress dialog with msg
    private void init(String message) {

        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.progress_dialog);
        super.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION);
        super.setCancelable(false);
        super.show();
    }

    // dismiss progress dialog
    public void dismiss() {

        if (this != null && this.isShowing()) {
            super.dismiss();
        }
    }
}
