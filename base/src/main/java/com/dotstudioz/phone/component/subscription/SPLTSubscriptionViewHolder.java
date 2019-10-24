package com.dotstudioz.phone.component.subscription;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dotstudioz.dotstudioPRO.models.dto.SubscriptionDTO;
import com.dotstudioz.model.SPLTCategory;
import com.dotstudioz.model.SPLTChannel;
import com.dotstudioz.phone.adapter.SPLTChannelAdapter;

import java.util.ArrayList;
import java.util.List;

public class SPLTSubscriptionViewHolder {

    protected TextView textUnitName;
    protected TextView textAmount;
    protected TextView textDescription;
    protected Button btnStartTrial;

    private Callback callback;
    public interface Callback {
        void onStartTrialClick(SubscriptionDTO channel);
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void updateUI(final SubscriptionDTO model){

        textUnitName.setText( model.getName()+ "Subscription");
        textAmount.setText("$"+model.getPrice() +"/"+model.getName());
        textDescription.setText("$"+model.getPrice() + " billed "+model.getName()+ " after trial period");
        btnStartTrial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTrialClick(model);
            }
        });



    }

    private void startTrialClick(SubscriptionDTO model) {
        if(callback != null){
            callback.onStartTrialClick(model);
        }
    }

}
