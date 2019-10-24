package com.dotstudioz.model;

import com.dotstudioz.dotstudioPRO.models.dto.SpotLightChannelDTO;
import com.dotstudioz.dotstudioPRO.models.dto.VideoInfoDTO;

import java.util.ArrayList;
import java.util.List;

public class SPLTParentChannel extends SPLTPlaylistChannel {

    ArrayList<SPLTChannel> channels = null;

    @Override
    public ArrayList<SPLTVideo> getPlayListVideos() {
        if(this.channels != null && this.channels.size()>0){
            SPLTChannel spltChannel = this.channels.get(0);
            if(spltChannel instanceof SPLTPlaylistChannel){
                return ((SPLTPlaylistChannel)spltChannel).getPlayListVideos();
            }else if(spltChannel instanceof SPLTVideoChannel){
                ArrayList<SPLTVideo> videos = new ArrayList<>();
                videos.add(((SPLTVideoChannel)spltChannel).video);
                return videos;
            }
        }
        return null;
        //return super.getPlayListVideos();
    }

    public SPLTParentChannel(SpotLightChannelDTO spotLightChannelDTO) {
        super(spotLightChannelDTO);
    }

    @Override
    public void mapFromSpotLightChannelDTO(SpotLightChannelDTO spotLightChannelDTO) {
        super.mapFromSpotLightChannelDTO(spotLightChannelDTO);
        List<SpotLightChannelDTO> channelDTOList = spotLightChannelDTO.getSeasonsList();
        if(channelDTOList != null && channelDTOList.size()>0){
            for(SpotLightChannelDTO spotLightChannelDTOChild:  channelDTOList){
                SPLTChannel spltChildChannel = SPLTChannel.getChannelFromSpotLightChannelDTO(spotLightChannelDTOChild);
                this.channels.add(spltChildChannel);
            }
        }
    }
}
