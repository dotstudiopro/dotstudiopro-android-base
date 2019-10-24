package com.dotstudioz.phone.component.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.ParameterBuilder;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;
import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.phone.R;
import com.dotstudioz.phone.baseclasses.SPLTBaseActivity;
import com.dotstudioz.phone.util.SharedPreferencesUtil;

import java.util.Map;

public class SPLTLoginActivity extends SPLTBaseActivity {

    private String TAG = "SPLTLoginActivity";
    public static String AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE;
    public static String AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE_KEY = "auth0refreshtoken";
    public static String AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE_KEY = "auth0idtoken";
    public static String AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE;

    protected int initialScreenToUse = 0;

    public static int RESULT_CODE_SUCCESS = 1;
    public static int RESULT_CODE_CANCEL = 1;
    public static int RESULT_CODE_FAIL = 1;
    //handleLoginActivityResult
    protected Button btnSubmit;
    private Lock mLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splt_login);

        init();

        openLogin();

        /*if(btnSubmit != null){
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    //intent.putExtra(getString(R.string.response_request),getString(R.string.response_success));
                    setResult(RESULT_CODE_SUCCESS,intent);
                    finish();


                }
            });
        }*/


    }
    protected void init(){}
    protected void openLogin(){
        if(SPLTRouter.getInstance().COMPANY_API_KEY == null || SPLTRouter.getInstance().COMPANY_API_KEY.trim().length() == 0){
            Log.e(TAG,"Invalid compnay key");
            throw new NullPointerException("Invalid compnay key");
        }
        if(SPLTRouter.getInstance().AUTH0_CLIENT_ID == null || SPLTRouter.getInstance().AUTH0_CLIENT_ID.trim().length() == 0){
            Log.e(TAG,"Invalid client id");
            throw new NullPointerException("Invalid client id");
        }
        if(SPLTRouter.getInstance().AUTH0_CLIENT_DOMAIN == null || SPLTRouter.getInstance().AUTH0_CLIENT_DOMAIN.trim().length() == 0){
            Log.e(TAG,"Invalid client domain");
            throw new NullPointerException("Invalid client domain");
        }

        Map<String, Object> parametersMap = ParameterBuilder
                .newAuthenticationBuilder()
                .setScope(ParameterBuilder.SCOPE_OFFLINE_ACCESS)
                /*.set("c", SPLTRouter.getInstance().COMPANY_API_KEY)*/
                .set("c", "5ced8f7398f8159a5f201576")
                .asDictionary();

        /*Theme customizedLockTheme = Theme.newBuilder()
                //.withHeaderLogo(R.drawable.famil_league_logo)
                .withHeaderLogo(R.drawable.fami_league_transparent_bg_new_logo3)
                .withHeaderTitle(R.string.auth0_empty_title)
                .withHeaderTitleColor(R.color.bt_light_gray)
                .withPrimaryColor(R.color.bt_black)
                .withDarkPrimaryColor(R.color.bt_black)
                .withHeaderColor(R.color.white)
                .build();*/


        Auth0 auth0 = new Auth0(SPLTRouter.getInstance().AUTH0_CLIENT_ID, SPLTRouter.getInstance().AUTH0_CLIENT_DOMAIN);
        mLock = Lock.newBuilder(auth0, mCallback).withAuthenticationParameters(parametersMap)//.withTheme(customizedLockTheme)
                //Add parameters to the build
                .withScheme("demo")
                .initialScreen(initialScreenToUse)
                .closable(true)
                //.build();
                .build(this);
        /*mLock.onCreate(this);
        auth0.getAuthorizeUrl();*/
        startActivity(mLock.newIntent(this));

    }


    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLock.onDestroy(this);
        mLock = null;
    }
    @Override
    public void onBackPressed() {
       // super.onBackPressed();

    }


    private final LockCallback mCallback = new AuthenticationCallback() {
        @Override
        public void onAuthentication(Credentials credentials) {
            Log.d(TAG, "onAuthentication: Success Login");

            SPLTRouter.getInstance().setUserCredentials(credentials);
            SPLTRouter.getInstance().setAuth0IdToken(credentials.getIdToken());
            SPLTRouter.getInstance().setAuth0RefreshToken(credentials.getRefreshToken());

            if(credentials.getRefreshToken() != null && credentials.getRefreshToken().length() > 0) {
                SharedPreferencesUtil.getInstance(SPLTLoginActivity.this).addToSharedPreference(AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE,
                        SPLTRouter.getInstance().getUserCredentials().getRefreshToken(),
                        AUTH0_REFRESH_TOKEN_RESPONSE_SHARED_PREFERENCE_KEY
                );
            }

            SharedPreferencesUtil.getInstance(SPLTLoginActivity.this).addToSharedPreference(
                    AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE,
                    SPLTRouter.getInstance().getUserCredentials().getIdToken(),
                    AUTH0_ID_TOKEN_RESPONSE_SHARED_PREFERENCE_KEY
            );


            getTheUserProfileFromAuth0();
            /*Intent intent=new Intent();
            //intent.putExtra(getString(R.string.response_request),getString(R.string.response_success));
            setResult(RESULT_CODE_SUCCESS,intent);
            finish();*/

        }

        @Override
        public void onCanceled() {
            Intent intent=new Intent();
            setResult(RESULT_CODE_CANCEL,intent);
            finish();
        }

        @Override
        public void onError(LockException error){
            Log.d(TAG, "onError: "+error.getMessage());
            //Toast.makeText(getApplicationContext(), "Log In - Error Occurred "+error.getMessage(), Toast.LENGTH_SHORT).show();
            /*Intent intent=new Intent();
            setResult(RESULT_CODE_FAIL,intent);
            finish();*/
        }
    };

    public void getTheUserProfileFromAuth0() {
        Auth0 mAuth0 = new Auth0(SPLTRouter.getInstance().AUTH0_CLIENT_ID, SPLTRouter.getInstance().AUTH0_CLIENT_DOMAIN);
        // The process to reclaim an UserProfile is preceded by an Authentication call.
        AuthenticationAPIClient aClient = new AuthenticationAPIClient(mAuth0);

        if(SPLTRouter.getInstance().getStrClientToken() == null || SPLTRouter.getInstance().getStrClientToken().length() == 0) {
            if (SPLTRouter.getInstance().getAuth0IdToken() != null && SPLTRouter.getInstance().getAuth0IdToken().length() > 0) {
                aClient.tokenInfo(SPLTRouter.getInstance().getAuth0IdToken())
                        .start(new BaseCallback<UserProfile, AuthenticationException>() {
                            @Override
                            public void onSuccess(final UserProfile payload) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        UserProfile mUserProfile = payload;
                                        try {
                                            //Log.d("MainActivity", "PROFILE REQUEST:-" + mUserProfile.toString());
                                            //Log.d("MainActivity", "mUserProfile.getExtraInfo().get('spotlight')" + mUserProfile.getExtraInfo().get("spotlight"));
                                        } catch(Exception e) {
                                            e.printStackTrace();
                                        }
                                        if (mUserProfile != null && mUserProfile.getExtraInfo() != null && mUserProfile.getExtraInfo().size() > 0) {

                                            if (mUserProfile.getEmail() != null && mUserProfile.getEmail().length() > 0) {
                                                SPLTRouter.getInstance().setEmail(mUserProfile.getEmail());


                                                SharedPreferencesUtil.getInstance(SPLTLoginActivity.this).addToSharedPreference(
                                                        SPLTRouter.USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE,
                                                        SPLTRouter.getInstance().getEmail(),
                                                        SPLTRouter.USER_EMAIL_DETAILS_RESPONSE_SHARED_PREFERENCE_KEY);
                                            }

                                            if (mUserProfile.getExtraInfo().containsKey("spotlight")) {
                                                scrapeOutInformationFromClientToken(mUserProfile.getExtraInfo().get("spotlight").toString());
                                            }

                                        } else {
                                            Toast.makeText(SPLTLoginActivity.this, "User details not available!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            }

                            @Override
                            public void onFailure(AuthenticationException error) {
                                SPLTLoginActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(SPLTLoginActivity.this, "Profile Request Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        });
            }
        }
    }

    public void scrapeOutInformationFromClientToken(String clientToken) {
        SPLTRouter.getInstance().setStrClientToken(clientToken);
        SharedPreferencesUtil.getInstance(SPLTLoginActivity.this).addToSharedPreference(
                SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE,
                SPLTRouter.getInstance().getStrClientToken(),
                SPLTRouter.USER_DETAILS_RESPONSE_SHARED_PREFERENCE_KEY);

            Intent intent=new Intent();
            setResult(RESULT_CODE_SUCCESS,intent);
            finish();

        /*Base64 decoder = new Base64(true);
        byte[] secret = decoder.decodeBase64(ApplicationConstants.CLIENT_TOKEN.split("\\.")[1]);
        String s = new String(secret);
        //Log.d("MainActivity", "String of secret:String of secret:" + s);
        try {
            JSONObject spotlightJSONObject = new JSONObject(s);

            if (spotlightJSONObject.has("context")) {
                if (spotlightJSONObject.getJSONObject("context").has("id")) {
                    userIdString = spotlightJSONObject.getJSONObject("context").getString("id");
                    ApplicationConstants.userIdString = userIdString;
                    if(AppController.isUserLoggedInJustNow) {
                        //AppController.getInstance().sendAppsFlyerEvent(this, AnalyticsConstants.AF_LOGIN, AnalyticsConstants.AF_USER_ID, userIdString, "", "");
                        String userIDToSend = "";
                        if(ApplicationConstants.CLIENT_TOKEN != null && ApplicationConstants.CLIENT_TOKEN.length() > 0) {
                            if(userIdString != null && userIdString.length() > 0)
                                userIDToSend = userIdString;
                        }
                        if(userIDToSend != null && userIDToSend.length() > 0)
                            AppController.getInstance().sendAppsFlyerEvent(this, AnalyticsConstants.AF_LOGIN, AnalyticsConstants.AF_USER_ID, userIdString, "", "", AFInAppEventParameterName.CUSTOMER_USER_ID, userIDToSend);
                        else
                            AppController.getInstance().sendAppsFlyerEvent(this, AnalyticsConstants.AF_LOGIN, AnalyticsConstants.AF_USER_ID, userIdString, "", "", "", "");

                        FacebookAnalyticsEventsService.getInstance().saveAnalyticsEvent(AppController.getInstance().logger, AppEventsConstants.EVENT_NAME_UNLOCKED_ACHIEVEMENT, "");
                        TuneAnalyticsEventsService.getInstance().savePreDefinedAnalyticsEvent(TuneEvent.LOGIN);
                        Branch.getInstance().setIdentity(userIDToSend);

                        AppController.isUserLoggedInJustNow = false;
                    }
                }

                if (spotlightJSONObject.getJSONObject("context").has("first_name")) {
                    userFirstName = spotlightJSONObject.getJSONObject("context").getString("first_name");
                    ApplicationConstants.userFirstName = userFirstName;
                }

                if (spotlightJSONObject.getJSONObject("context").has("last_name")) {
                    userLastName = spotlightJSONObject.getJSONObject("context").getString("last_name");
                    ApplicationConstants.userLastName = userLastName;
                }

                if (spotlightJSONObject.getJSONObject("context").has("avatar")) {
                    userAvatarPath = spotlightJSONObject.getJSONObject("context").getString("avatar");
                    ApplicationConstants.userAvatarPath = userAvatarPath;
                }

                try { ((TextView)findViewById(R.id.loginTextView)).setText("Logout"); } catch(Exception e) {}
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/




    }

}
