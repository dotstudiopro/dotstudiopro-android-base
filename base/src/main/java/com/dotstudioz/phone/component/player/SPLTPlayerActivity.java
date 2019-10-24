package com.dotstudioz.phone.component.player;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dotstudioz.phone.R;
import com.dotstudioz.phone.baseclasses.SPLTBaseActivity;

public class SPLTPlayerActivity extends SPLTBaseActivity {


    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splt_player);

        init();
        if(this.toolbar != null){
            setSupportActionBar(this.toolbar);
        }
        showMenu();



    }
    protected  void init(){}

    protected void showMenu(){
        if( getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
