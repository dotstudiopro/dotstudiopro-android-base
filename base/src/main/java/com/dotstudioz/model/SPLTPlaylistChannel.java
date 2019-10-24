package com.dotstudioz.model;

import com.dotstudioz.dotstudioPRO.models.dto.SpotLightChannelDTO;
import com.dotstudioz.dotstudioPRO.models.dto.VideoInfoDTO;

import java.util.ArrayList;
import java.util.List;

public class SPLTPlaylistChannel extends SPLTChannel {


    ArrayList<SPLTVideo> playListVideos = null;

    public SPLTPlaylistChannel(){
        super();
    }

    public ArrayList<SPLTVideo> getPlayListVideos() {
        return playListVideos;
    }

    public void setPlayListVideos(ArrayList<SPLTVideo> playListVideos) {
        this.playListVideos = playListVideos;
    }

    public SPLTPlaylistChannel(SpotLightChannelDTO spotLightChannelDTO) {
        super(spotLightChannelDTO);
        this.mapFromSpotLightChannelDTO(spotLightChannelDTO);
    }

    @Override
    public void mapFromSpotLightChannelDTO(SpotLightChannelDTO spotLightChannelDTO) {
        super.mapFromSpotLightChannelDTO(spotLightChannelDTO);

        List<VideoInfoDTO> videoInfoDTOList = spotLightChannelDTO.getVideoInfoDTOList();
        this.mapFromVideoInfoDTOList(videoInfoDTOList);

    }

    public void mapFromVideoInfoDTOList(List<VideoInfoDTO> videoInfoDTOList){
        if(videoInfoDTOList != null && videoInfoDTOList.size()>0){
            this.playListVideos = new ArrayList<>();
            for(VideoInfoDTO videoInfoDTO:  videoInfoDTOList){
                SPLTVideo spltVideo = new SPLTVideo(videoInfoDTO);
                this.playListVideos.add(spltVideo);
            }
        }
    }
}
