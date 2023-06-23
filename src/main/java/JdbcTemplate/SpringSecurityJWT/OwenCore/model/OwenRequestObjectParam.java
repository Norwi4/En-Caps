package JdbcTemplate.SpringSecurityJWT.OwenCore.model;

import org.springframework.web.servlet.tags.Param;

import java.util.List;

public class OwenRequestObjectParam {
    private String token;
    private String device_id;
    private List<Param> params;
    private String value;
}
