package com.javainuse.systemClient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Сущность для построения графиков
 */
public class ParamGraph {

    @JsonProperty("id")
    private String device_id;
    @JsonProperty("values")
    private List<Values> values;

    public String getId() {
        return device_id;
    }

    public void setId(String device_id) {
        this.device_id = device_id;
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }
}
