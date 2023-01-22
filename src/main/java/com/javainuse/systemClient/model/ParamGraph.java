package com.javainuse.systemClient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Сущность для построения графиков
 */
public class ParamGraph {

    @JsonProperty("id")
    private String id;
    @JsonProperty("values")
    private List<Values> values;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }
}
