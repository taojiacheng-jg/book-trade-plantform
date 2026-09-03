package cn.edu.hdu.service.impl;

import cn.edu.hdu.mapper.MessageMapper;
import cn.edu.hdu.pojo.Message;
import cn.edu.hdu.service.MessageService;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Result sendMessage(Message message) {
        message.setParentMsgId(null);
        message.setIsRead(false);
        int rows = messageMapper.insertMessage(message);
        return rows > 0 ? Result.success(message) : Result.error(cn.edu.hdu.utils.ResultCodeEnum.REGISTER_FAIL);
    }

    @Override
    public Result replyMessage(Integer parentId, String content, Integer fromUserId, Integer toUserId) {
        Message msg = new Message();
        msg.setParentMsgId(parentId);
        msg.setContent(content);
        msg.setFromUserId(fromUserId);
        msg.setToUserId(toUserId);
        msg.setIsRead(false);
        int rows = messageMapper.insertMessage(msg);
        return rows > 0 ? Result.success(msg) : Result.error(cn.edu.hdu.utils.ResultCodeEnum.REGISTER_FAIL);
    }

    @Override
    public Result getConversation(Integer userId, Integer otherUserId) {
        List<Message> list = messageMapper.getConversation(userId, otherUserId);
        return Result.success(list);
    }
}
