package com.javainuse.systemClient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Сущность для работы с ПЛК
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PLC {
    @JsonProperty("id")
    private Long device_id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("type")
    private String type;

    @JsonProperty("status")
    private String status;

    public Long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Long device_id) {
        this.device_id = device_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
