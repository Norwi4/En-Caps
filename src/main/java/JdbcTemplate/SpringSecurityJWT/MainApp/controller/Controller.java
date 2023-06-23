package JdbcTemplate.SpringSecurityJWT.MainApp.controller;

import JdbcTemplate.SpringSecurityJWT.MainApp.model.Company;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.DashboardView.Dashboard;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.DashboardView.Views;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.ObjectsListResponse;
import JdbcTemplate.SpringSecurityJWT.MainApp.model.RenameParamRequest;
import JdbcTemplate.SpringSecurityJWT.MainApp.service.CompanyService;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    CompanyService companyService;

    /**
     * Создание компании
     * @param company
     * @return
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/create-company")
    public ResponseEntity<String> createCompany(@RequestBody @JsonInclude(JsonInclude.Include.NON_NULL) Company company) {
        try{
            return new ResponseEntity<>(companyService.createCompany(company), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //посмотреть обьекты пользователя
    //посмотреть параметры обьекта на плашке
    //посмотреть полные параметры обьекта
    //посмотреть полный списоок аварий обьекта
    //посмотреть полный списоок событий обьекта (из овена)

    /**
     * Получение объектов (котельной) компании поользоователя
     * @return список ообъектов
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/view-objects")
    public ResponseEntity addObject() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            List<Dashboard> result = companyService.dashboardList(auth.getName());
            if (result.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("У вас нет обьектов");
            }
            else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получение списка объектов поользователя
     * @return
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/get-object_list")
    public ResponseEntity getObjectList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            List<ObjectsListResponse> responseList = companyService.getObjectsUsers(auth.getName());
            if (responseList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("У вас нет обьектов");
            } else {
                return new ResponseEntity<>(responseList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Системная ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Получение параметров объекта
     * @param objectId номер объекта
     * @return список параметров
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/get-object-param/{objectId}")
    public ResponseEntity getObjectParam(@PathVariable("objectId") String objectId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            List<Views> responseList = companyService.getViewsObject(auth.getName(), objectId);
            if (responseList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Объект не найден");
            } else {
                return new ResponseEntity<>(responseList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Системная ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/rename-params")
    public ResponseEntity renameParams(@RequestBody @JsonInclude(JsonInclude.Include.NON_NULL) List<RenameParamRequest> paramRequest) {
        try{
            companyService.renameParam(paramRequest);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Сохранены успешно");
        } catch (Exception e) {
            return new ResponseEntity<>("Системная ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**@PostMapping("/add-object")
    public ResponseEntity<String> addObject(@RequestBody @JsonInclude(JsonInclude.Include.NON_NULL) OwenUser user) {
        try{
            return new ResponseEntity<>(companyService.createCompany(company), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Системная ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }**/
}
