package com.dotstudioz.phone.component.home.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.phone.baseclasses.SPLTBaseFragment;

public class SPLTWebviewFragment extends SPLTBaseFragment {

    private String strURL = "";
    protected WebView webview;

    public SPLTWebviewFragment() {
    }

    public void setUrl(String strURL) {
        this.strURL = strURL;
        loadWebView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = init(inflater,container,savedInstanceState);
        return mView;
    }

    protected View init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);

        initialize();
        loadWebView();

    }
    public void initialize(){
        if(this.webview != null ){

            WebSettings settings = webview.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setBuiltInZoomControls(true);
            webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

            webview.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                public void onPageFinished(WebView view, String url) {

                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    if(alertDialog != null){
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage(description);
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                        alertDialog.show();
                    }
                }
            });
        }
    }
    public void loadWebView(){

        if(this.webview != null && strURL != null && strURL.length()>0){
            webview.loadUrl(strURL);
        }

    }
}
