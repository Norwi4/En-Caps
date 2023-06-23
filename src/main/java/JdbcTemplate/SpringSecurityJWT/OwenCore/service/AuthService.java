package JdbcTemplate.SpringSecurityJWT.OwenCore.service;

import JdbcTemplate.SpringSecurityJWT.OwenCore.repository.AuthRepository;
import JdbcTemplate.SpringSecurityJWT.OwenCore.vo.PlcCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    AuthRepository authRepository;

    public void saveToken(List<PlcCompany> companyList, String token_id) {
        authRepository.saveToken(companyList, token_id);
    }
}


