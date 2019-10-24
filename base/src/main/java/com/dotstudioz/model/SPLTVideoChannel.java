package com.dotstudioz.model;

import android.text.TextUtils;

import com.dotstudioz.dotstudioPRO.models.dto.SpotLightChannelDTO;

import java.util.ArrayList;

public class SPLTVideoChannel extends  SPLTChannel{

    SPLTVideo video = null;

    public ArrayList<SPLTVideo> getPlayListVideos() {
        if(video != null){
            ArrayList<SPLTVideo> videos = new ArrayList<>();
            videos.add(video);
            return videos;
        }
        return null;
    }

    public SPLTVideoChannel(SpotLightChannelDTO spotLightChannelDTO) {
        super(spotLightChannelDTO);

    }

    @Override
    public void mapFromSpotLightChannelDTO(SpotLightChannelDTO spotLightChannelDTO) {
        super.mapFromSpotLightChannelDTO(spotLightChannelDTO);

        String strVideoID=spotLightChannelDTO.getVideo();
        if(strVideoID != null && !TextUtils.isEmpty(strVideoID)){
            this.video = new SPLTVideo(strVideoID);
        }

    }
}
