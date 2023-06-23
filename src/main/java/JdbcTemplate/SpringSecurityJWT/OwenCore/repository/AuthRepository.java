package JdbcTemplate.SpringSecurityJWT.OwenCore.repository;

import JdbcTemplate.SpringSecurityJWT.OwenCore.vo.PlcCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveToken(List<PlcCompany> companyList, String token_id) {
        for (PlcCompany company : companyList) {
            jdbcTemplate.update("insert into response ( device_id, token) value (?,?)", company.getId(), token_id);
        }
    }
}
