package cn.edu.hdu.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Favorite {
    private Integer favId;
    private Integer userId;
    private Integer bookId;
    private LocalDateTime createTime;
}
