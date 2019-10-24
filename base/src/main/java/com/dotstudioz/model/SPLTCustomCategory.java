package com.dotstudioz.model;

import android.widget.Switch;

import com.dotstudioz.dotstudioPRO.models.dto.SpotLightCategoriesDTO;
import com.dotstudioz.dotstudioPRO.models.dto.VideoInfoDTO;

import java.util.ArrayList;

public class SPLTCustomCategory extends SPLTCategory {
    public enum Type{CONTINUE_WATCHING, WATCH_AGAIN}

    public SPLTCustomCategory(SpotLightCategoriesDTO categoriesDTO) {
        super(categoriesDTO);
    }
    public SPLTCustomCategory(ArrayList<VideoInfoDTO> videoList, Type type){
        this.initialize(type);
        SPLTCustomPlaylistChannel playlistChannel = new SPLTCustomPlaylistChannel(videoList);
        this.channels.add(playlistChannel);
    }

    public void initialize(Type type){
        switch (type){
            case WATCH_AGAIN:
                this.setName("Watch Again");
                this.setSlug("watch-again");
                this.setHomepage(true);
                break;
            case CONTINUE_WATCHING:
                this.setName("Continue watching");
                this.setSlug("continue-watching");
                this.setHomepage(true);
                break;
        }
    }
    //count waticng & watch again


    /*public SPLTCustomCategory() {
        super();
        *//*this.categoryId = "count_watching";*//*
        this.getId();
    }*/

}
