package JdbcTemplate.SpringSecurityJWT.MainApp.service;

import JdbcTemplate.SpringSecurityJWT.MainApp.model.Company;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.DashboardView.Dashboard;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.DashboardView.Views;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.ObjectsListResponse;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.RenameParamRequest;
import JdbcTemplate.SpringSecurityJWT.MainApp.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    /**
     * Создание компании
     * @param company
     * @return
     */
    public String createCompany(Company company) {
        if (companyRepository.existCompany(company.getName()))
        {
            return "Такая компания уже существует";
        } else {
            companyRepository.createCompany(company);
            return "Компания зарегестрирована успешно";
        }
    }

    public List<Dashboard> dashboardList(String userName) {
        return companyRepository.dashboardList(userName);
    }

    /**
     * Получение списка объектов пользователя
     * @param userName
     * @return
     */
    public List<ObjectsListResponse> getObjectsUsers(String userName) {
        return companyRepository.getObjectsUsers(userName);
    }

    /**
     * Получение параметров и аварий объекта
     * @param userName пользоователь, которому он принадлежит
     * @param objectId Id объекта
     * @return список параметров
     */
    public List<Views> getViewsObject(String userName, String objectId) {
        return companyRepository.getViewsObject(userName, objectId);
    }

    @Transactional
    public void renameParam(List<RenameParamRequest> paramRequests) {
        for (RenameParamRequest renameParamRequest : paramRequests) {
            companyRepository.renameParam(
                    renameParamRequest.getParamID(),
                    renameParamRequest.getParamLongName(),
                    renameParamRequest.getParamShortName());
        }
    }
}
