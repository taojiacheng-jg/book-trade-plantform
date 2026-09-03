package cn.edu.hdu.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Evaluation {
    private Integer evalId;
    private Integer orderId;
    private Integer evaluatorId;
    private Integer targetUserId;
    private Integer score;
    private String comment;
    private LocalDateTime createTime;
}
