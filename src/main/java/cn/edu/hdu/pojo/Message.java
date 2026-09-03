package cn.edu.hdu.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Message {
    private Integer msgId;
    private Integer fromUserId;
    private Integer toUserId;
    private Integer bookId;
    private Integer parentMsgId;
    private String content;
    private LocalDateTime sendTime;
    private Boolean isRead;
}
