package cn.edu.hdu.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageContact {
    private Integer otherUserId;
    private String otherName;
    private String lastContent;
    private LocalDateTime lastTime;
}
