package com.javainuse.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseToken {

	private String token;
	private String name;
	private String surname;
	private String company_name;
	private List<ChildCompanies> childCompanies;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public List<ChildCompanies> getChildCompanies() {
		return childCompanies;
	}

	public void setChildCompanies(List<ChildCompanies> childCompanies) {
		this.childCompanies = childCompanies;
	}
}
