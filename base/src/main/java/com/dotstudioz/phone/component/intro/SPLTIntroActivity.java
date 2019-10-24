package com.dotstudioz.phone.component.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dotstudioz.phone.R;
import com.dotstudioz.phone.baseclasses.SPLTBaseActivity;
import com.dotstudioz.phone.component.splash.SPLTSplashActivity;
import com.dotstudioz.phone.util.Utility;

public class SPLTIntroActivity extends SPLTBaseActivity {

    public static int RESULT_CODE_SUCCESS = 1;
    public static int RESULT_CODE_SKIP = 2;
    protected Button btnSubmit;
    protected TextView txtSkip, txtOR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splt_intro);

        init();
        if(btnSubmit != null){
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    //intent.putExtra(getString(R.string.response_request),getString(R.string.response_success));
                    setResult(RESULT_CODE_SUCCESS,intent);
                    finish();
                }
            });
        }
        if(txtSkip != null){
            txtSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    //intent.putExtra(getString(R.string.response_request),getString(R.string.response_success));
                    setResult(RESULT_CODE_SKIP,intent);
                    finish();
                }
            });


        }

    }
    protected  void init(){}
}
