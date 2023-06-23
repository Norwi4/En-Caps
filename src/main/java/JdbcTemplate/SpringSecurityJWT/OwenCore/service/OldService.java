package JdbcTemplate.SpringSecurityJWT.OwenCore.service;

import JdbcTemplate.SpringSecurityJWT.OwenCore.repository.OldRepository;
import JdbcTemplate.SpringSecurityJWT.OwenCore.vo.CompaniesModel;
import JdbcTemplate.SpringSecurityJWT.OwenCore.vo.Device;
import JdbcTemplate.SpringSecurityJWT.OwenCore.vo.PlcCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OldService {

    @Autowired
    OldRepository oldRepository;

    public void saveCompanies(List<PlcCompany> companies, String companyName) {
        oldRepository.saveCompanies(companies, oldRepository.getCompanyByName(companyName));
    }

    public List<CompaniesModel> getCompanies(String companyName) {
        return oldRepository.getCompanies(oldRepository.getCompanyByName(companyName));
    }

    public void saveSensor(Device devices) {
        oldRepository.saveSensor(devices);
    }
}
