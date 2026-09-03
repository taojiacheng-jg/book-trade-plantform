package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.Message;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {
    int insertMessage(Message message);
}
