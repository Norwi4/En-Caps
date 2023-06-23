package JdbcTemplate.SpringSecurityJWT.OwenCore.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {
    private String token;
    @JsonProperty("childCompanies")
    private List<ChildCompanies> childCompanies;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ChildCompanies> getCompanies() {
        return childCompanies;
    }

    public void setCompanies(List<ChildCompanies> childCompanies) {
        this.childCompanies = childCompanies;
    }
}