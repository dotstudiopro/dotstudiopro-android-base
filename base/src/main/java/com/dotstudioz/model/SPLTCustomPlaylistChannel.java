package com.dotstudioz.model;

import com.dotstudioz.dotstudioPRO.models.dto.SpotLightChannelDTO;
import com.dotstudioz.dotstudioPRO.models.dto.VideoInfoDTO;

import java.util.ArrayList;

public class SPLTCustomPlaylistChannel extends SPLTPlaylistChannel {

    public SPLTCustomPlaylistChannel(){
        super();
    }

    public SPLTCustomPlaylistChannel(SpotLightChannelDTO spotLightChannelDTO) {
        super(spotLightChannelDTO);
    }

    public SPLTCustomPlaylistChannel(ArrayList<VideoInfoDTO> videoList){
        this.mapFromVideoInfoDTOList(videoList);
    }



}
