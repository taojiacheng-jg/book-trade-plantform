package cn.edu.hdu.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Admin {
    private Integer adminId;
    private Integer userId;
    private String permissionLevel;
    private String lastLoginIp;
    private LocalDateTime lastLoginTime;
}
