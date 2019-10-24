package com.dotstudioz.model;


import com.dotstudioz.dotstudioPRO.models.dto.VideoInfoDTO;

import java.io.Serializable;

public class SPLTVideo implements Serializable {

    private String videoID;
    private String videoTitle;
    private String seriesTitle;
    private String description;
    private String thumb;
    private String slug;
    private String videoYear;
    private String videoLanguage;
    private String country;
    private int videoDuration;
    private String channelID;
    private String casting;
    private String writterDirector;


    public SPLTVideo(String videoID) {
        this.videoID = videoID;
    }

    public SPLTVideo(VideoInfoDTO videoInfoDTO) {
        this.mapFromSpotLightVideoDTO(videoInfoDTO);

    }



    private void mapFromSpotLightVideoDTO(VideoInfoDTO videoInfoDTO) {
        this.setVideoID(videoInfoDTO.getVideoID());
        this.setVideoTitle(videoInfoDTO.getVideoTitle());
        this.setSeriesTitle(videoInfoDTO.getSeriesTitle());
        this.setDescription(videoInfoDTO.getDescription());
        this.setThumb(videoInfoDTO.getThumb());
        this.setSlug(videoInfoDTO.getSlug());
        this.setVideoYear(videoInfoDTO.getVideoYear());
        this.setVideoLanguage(videoInfoDTO.getVideoLanguage());
        this.setCountry(videoInfoDTO.getCountry());
        this.setVideoDuration(videoInfoDTO.getVideoDuration());

        this.setChannelID(videoInfoDTO.getChannelID());
        this.setCasting(videoInfoDTO.getCasting());
        this.setWritterDirector(videoInfoDTO.getWritterDirector());
    }

    /*private void getVideoDetailAPI() {

        *//*videoInfoDTOArrayList// empltry arry list
        channelLink= spltchannal list; // or empltry list
        parentChannelID // emptry or
        SeriesVideoDetailsService_V1 seriesVideoDetailsService_v1 = new SeriesVideoDetailsService_V1(this, videoInfoDTOArrayList, channelLink, parentChannelID);*//*

        String channelLink = "";
        String parentChannelID = "";
        ApplicationConstants.xAccessToken = SPLTRouter.getInstance().getStrAccessToken();

        String videoURL = SPLTRouter.getInstance().VIDEO_PLAY2_API + "5cf5632697f8157f46cffceb" ;
        SeriesVideoDetailsService_V1 seriesVideoDetailsService_v1 = new SeriesVideoDetailsService_V1(this, new ArrayList<VideoInfoDTO>(), channelLink, parentChannelID);
        seriesVideoDetailsService_v1.setVideoDetailsService_V1Listener(new SeriesVideoDetailsService_V1.ISeriesVideoDetailsService_V1() {
            @Override
            public void callBackFromFetchSeriesVideoDetails(ArrayList<VideoInfoDTO> videoInfoDTOArrayList) {
                Log.d(TAG, "callBackFromFetchSeriesVideoDetails: "+videoInfoDTOArrayList.size());
                String mResJSON = new Gson().toJson(videoInfoDTOArrayList);
                Log.d(TAG, "callBackFromFetchSeriesVideoDetails: "+mResJSON);


                ArrayList<SPLTVideo> mVideoList = videoInfoDTOToSPLTVideo(videoInfoDTOArrayList);


            }

            @Override
            public void fetchVideoPlaybackDetailsForSeries(String videoID) {
                Log.d(TAG, "fetchVideoPlaybackDetailsForSeries: "+videoID);
            }

            @Override
            public void getSeriesVideoDetailsServiceError(String ERROR) {
                Log.d(TAG, "getSeriesVideoDetailsServiceError: "+ERROR);
            }

            @Override
            public void accessTokenExpired() {
                Log.d(TAG, "accessTokenExpired: ");
            }

            @Override
            public void clientTokenExpired() {
                Log.d(TAG, "clientTokenExpired: ");
            }
        });
        seriesVideoDetailsService_v1.fetchSeriesVideoDetails(videoURL);
    }*/

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getVideoYear() {
        return videoYear;
    }

    public void setVideoYear(String videoYear) {
        this.videoYear = videoYear;
    }

    public String getVideoLanguage() {
        return videoLanguage;
    }

    public void setVideoLanguage(String videoLanguage) {
        this.videoLanguage = videoLanguage;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getCasting() {
        return casting;
    }

    public void setCasting(String casting) {
        this.casting = casting;
    }

    public String getWritterDirector() {
        return writterDirector;
    }

    public void setWritterDirector(String writterDirector) {
        this.writterDirector = writterDirector;
    }
}
