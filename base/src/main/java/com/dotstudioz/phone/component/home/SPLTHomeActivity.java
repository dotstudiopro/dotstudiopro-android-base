package com.dotstudioz.phone.component.home;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.dotstudioPRO.models.dto.ChannelsMyListDTOForMyList;
import com.dotstudioz.dotstudioPRO.models.dto.SpotLightCategoriesDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SpotLightChannelDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SubscriptionDTO;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstantURL;
import com.dotstudioz.dotstudioPRO.services.constants.ApplicationConstants;
import com.dotstudioz.dotstudioPRO.services.services.GetAllCategoriesService_V1;
import com.dotstudioz.dotstudioPRO.services.services.GetAllChannelsFromAllCategoriesService_V1;
import com.dotstudioz.dotstudioPRO.services.services.GetCategoriesFullDataService_V1;
import com.dotstudioz.dotstudioPRO.services.services.GetUserSubscriptionsService_V1;
import com.dotstudioz.model.FragmentData;
import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTChannel;
import com.dotstudioz.model.SPLTCompany;

import com.dotstudioz.model.SPLTMyListCategory;
import com.dotstudioz.model.SPLTVideo;
import com.dotstudioz.phone.R;
import com.dotstudioz.phone.baseclasses.SPLTBaseActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SPLTHomeActivity extends SPLTBaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private String TAG = "SPLTHomeActivity";

    private final FragmentManager fm = getSupportFragmentManager();
    protected Fragment active = null;
    protected LinkedHashMap<Integer, FragmentData> mHashMap = new LinkedHashMap<>();
    protected BottomNavigationView navigation;
    protected Toolbar toolbar;


    private GetAllCategoriesService_V1 getAllCategoriesService_V1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splt_home);
        init();




        if(this.navigation != null){
            navigation.setOnNavigationItemSelectedListener(this);
            for (Map.Entry<Integer, FragmentData> entry : mHashMap.entrySet()) {
                int key = entry.getKey();
                FragmentData value = entry.getValue();
                navigation.getMenu().add(Menu.NONE, key, Menu.NONE, value.getTitle()).setIcon(value.getDrawable());
            }

            for (Map.Entry<Integer, FragmentData> entry : mHashMap.entrySet()) {
                int key = entry.getKey();
                FragmentData value = entry.getValue();
                // now work with key and value...
                fm.beginTransaction().add(getFragmentContainer(), value.getFragment(), value.getTitle()).hide(value.getFragment()).commit();
            }
            selectedMenu(0);
        }


        //load home page data
        showProgress();

        SPLTCompany.getInstance().startLoadingData(new SPLTCompany.Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "startLoadingData onSuccess: "+SPLTCompany.getInstance().getHomepageAllCategories().size());
                Log.d(TAG, "startLoadingData onSuccess: "+SPLTCompany.getInstance().getCategories().size());
                hideProgress();
                reloadUI();

            }

            @Override
            public void onError(String responseBody) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onAccessTokenExpired() {
                Log.d(TAG, "onAccessTokenExpired: ");
            }

            @Override
            public void onClientTokenExpired() {
                Log.d(TAG, "onClientTokenExpired: ");
            }
        });


    }

    public void init(){}
    protected int getFragmentContainer(){
        return 0;
    }

    public void selectedMenu(int selectedMenu){
        navigation.getMenu().getItem(selectedMenu).setChecked(true);
        onNavigationItemSelected(navigation.getMenu().getItem(selectedMenu));

        //for badge count
     /* BottomNavigationMenuView mbottomNavigationMenuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        View view = mbottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) view;
        View cart_badge = LayoutInflater.from(this).inflate(R.layout.custom_cart_item_layout,mbottomNavigationMenuView, false);
        TextView count= cart_badge.findViewById(R.id.cart_badge);
        count.setText("10");
        itemView.addView(cart_badge);*/
    }

    protected boolean onCreateOptionsMenuSPLT(Menu menu){
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        onCreateOptionsMenuSPLT(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();
        if(this.mHashMap != null){
            if(this.mHashMap.get(itemId) != null){

                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                fm.beginTransaction().hide(active).show(mHashMap.get(itemId).getFragment()).commit();
                active = this.mHashMap.get(itemId).getFragment();

                //loadFragment(mHashMap.get(itemId).getFragment());

                return true;
            }
        }

        return false;
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    /*.replace(R.id.fragment_container, fragment)*/
                    .replace(getFragmentContainer(), fragment)
                    .commit();
            return true;
        }
        return false;
    }
    public void reloadUI(){}

    protected void addHomeTabbarFragment(int i) {}

    protected void addCategoriesTabbarFragment(int i) {}

    protected void addMyListTabbarFragment(int i) {}


    protected void navigatePlayerActivity(SPLTChannel channel){
        navigatePlayerActivity(channel,-1,-1,null);
    }
    protected void navigatePlayerActivity(SPLTVideo spltVideo){
        navigatePlayerActivity(null,-1,-1,spltVideo);
    }
    protected void navigatePlayerActivity(SPLTChannel channel, int iCurrentChildChannelIndex, int autoPlayIndex, SPLTVideo spltVideo){

    }

    protected void navigateChannelList(SPLTCategory category ){

    }

    // like unlike click
    protected void onChannelLikeUnlickClicked(SPLTChannel channel, boolean checked){



        SPLTMyListCategory spltMyListCategory = SPLTCompany.getInstance().myListCategory;
        spltMyListCategory.setCallback(new SPLTMyListCategory.MyListCallback() {
            @Override
            public void onSuccess() {

            }
            @Override
            public void onFailure(String error) {

            }
        });
        if(checked){
            spltMyListCategory.addChannelToMyList(SPLTHomeActivity.this, channel);
        }else {
            spltMyListCategory.deleteChannelToMyList(SPLTHomeActivity.this, channel);
        }

    }

}

