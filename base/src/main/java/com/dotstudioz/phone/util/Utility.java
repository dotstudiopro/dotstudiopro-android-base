package com.dotstudioz.phone.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;

import com.dotstudioz.phone.R;
import com.dotstudioz.phone.component.splash.SPLTSplashActivity;

public class Utility {

    public static void showCustomDialog(final Context context, String message, String titleString, boolean setNegativeButtonFlag, boolean cancellableFlag, String positiveButtonString, String negativeButtonString, final int severityLevel){
        //Log.d(TAG, "showCustomDialog: msg"+message + " title:"+titleString +" setNegativeButtonFlag:"+setNegativeButtonFlag + " cancellableFlag:"+cancellableFlag+" positiveButtonString:"+positiveButtonString+" negativeButtonString:"+negativeButtonString);
        final Activity activity = (Activity) context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);

        builder.setTitle(titleString);
        builder.setPositiveButton(positiveButtonString, null);

        if (setNegativeButtonFlag)
            builder.setNegativeButton(negativeButtonString, null);
        builder.setCancelable(cancellableFlag);

        AlertDialog alert = builder.create();
//        alert.setOnShowListener(updateAppVersionOnShowListener);
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(severityLevel == 4) {
                            activity.finish();
                        } else {
                            final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    }
                });
            }
        });
        alert.show();
    }



}
