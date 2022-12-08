package com.javainuse.systemClient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChildCompanies {

    @JsonProperty("id")
    private String company_id;
    private String name;

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
