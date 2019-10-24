package com.dotstudioz.phone.baseclasses;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dotstudioz.api.SPLTRouter;
import com.dotstudioz.phone.util.Utility;

public class SPLTBaseImageView extends android.support.v7.widget.AppCompatImageView {
    protected String TAG = "SPLTBaseImageView";

    protected Context context;
    public SPLTBaseImageView(Context context) {
        super(context);
        this.context = context;
    }

    public SPLTBaseImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SPLTBaseImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setURL(Context context, String strUrl){
        this.context = context;
        if(strUrl != null && strUrl.length()>0){
         RequestOptions options = new RequestOptions()
                    .centerInside()
                    /*.placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)*/
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    //.override(cardViewwidth, cardViewHeight)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
                Glide.with(context)
                        .load(strUrl)
                        .apply(options)
                        .thumbnail(1)
                        .into(this);

        }
    }
    public void setURL(Context context, String strURL, int width, int height){
        this.context = context;
        if(strURL != null && strURL.length()>0){
            strURL = getFullURL(strURL);
            String strFullURL = strURL +"/"+width+"/"+height;
            Log.d(TAG, "setURL: "+strFullURL);
            this.setURL(context, strFullURL);

        }

    }
    public  String getFullURL(String strURL){
        Context context;
        String fullURL = "";
        if(!strURL.contains("/")){
            fullURL = SPLTRouter.getInstance().IMAGES_DSPRO_DOMAIN_S + strURL;
            return fullURL;
        }else if(URLUtil.isNetworkUrl(strURL)){
            // means url start with http or https
            fullURL = strURL;
        }else {
            fullURL = "https://"+strURL;
        }

        return fullURL;
    }


}
