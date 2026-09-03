package cn.edu.hdu.controller;

import cn.edu.hdu.pojo.Message;
import cn.edu.hdu.service.MessageService;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public Result send(@ModelAttribute Message message) {
        return messageService.sendMessage(message);
    }

    @PostMapping("/reply")
    public Result reply(@RequestParam Integer parentId,
                        @RequestParam String content,
                        @RequestParam Integer fromUserId,
                        @RequestParam Integer toUserId) {
        return messageService.replyMessage(parentId, content, fromUserId, toUserId);
    }

    @GetMapping("/conversation")
    public Result conversation(@RequestParam Integer userId, @RequestParam Integer otherId) {
        return messageService.getConversation(userId, otherId);
    }
}
