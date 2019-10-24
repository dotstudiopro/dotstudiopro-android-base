package com.dotstudioz.model;

import com.dotstudioz.dotstudioPRO.models.dto.CustomFieldDTO;
import com.dotstudioz.dotstudioPRO.models.dto.SpotLightCategoriesDTO;

import java.io.Serializable;

public class SPLTCustomField implements Serializable {

    private String customFieldName;
    private String customFieldValue;


    public SPLTCustomField(CustomFieldDTO customFieldDTO) {
        this.mapFromSpotLightCustomFieldDTO(customFieldDTO);
    }

    public void mapFromSpotLightCustomFieldDTO(CustomFieldDTO customFieldDTO){
        this.setCustomFieldName(customFieldDTO.getCustomFieldName());
        this.setCustomFieldValue(customFieldDTO.getCustomFieldValue());
    }

    public String getCustomFieldName() {
        return customFieldName;
    }

    public void setCustomFieldName(String customFieldName) {
        this.customFieldName = customFieldName;
    }

    public String getCustomFieldValue() {
        return customFieldValue;
    }

    public void setCustomFieldValue(String customFieldValue) {
        this.customFieldValue = customFieldValue;
    }
}
