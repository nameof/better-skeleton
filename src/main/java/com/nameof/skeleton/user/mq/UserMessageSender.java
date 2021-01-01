package com.nameof.skeleton.user.mq;

import com.nameof.skeleton.integration.mq.MessageSender;
import com.nameof.skeleton.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMessageSender {
    @Autowired
    private MessageSender messageSender;

    public void sendUserSignup(User user) {
        UserMessageBody msg = new UserMessageBody(UserMessageType.SIGNUP, user);
        messageSender.sendReliable(UserMessageTopic.USER, msg);
    }
}
