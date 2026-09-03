package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.Message;
import cn.edu.hdu.pojo.MessageContact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    int insertMessage(Message message);

    List<Message> findMessagesByUser(@Param("userId") Integer userId, @Param("threadId") Integer parentId);

    Message findMessageById(Integer msgId);

    List<Message> getConversation(@Param("userId") Integer userId, @Param("otherUserId") Integer otherUserId);

    List<MessageContact> listContacts(@Param("userId") Integer userId);
}
