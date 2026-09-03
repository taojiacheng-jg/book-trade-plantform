package cn.edu.hdu.service;

import cn.edu.hdu.pojo.Message;
import cn.edu.hdu.utils.Result;

public interface MessageService {
    Result sendMessage(Message message);

    Result replyMessage(Integer parentId, String content, Integer fromUserId, Integer toUserId);

    Result getConversation(Integer userId, Integer otherUserId);
}
