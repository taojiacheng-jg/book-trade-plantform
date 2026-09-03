package cn.edu.hdu.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private BigDecimal creditScore;
    private String status;
    private String role;
    private LocalDateTime createdAt;
}
