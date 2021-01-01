package com.nameof.skeleton.user.mq;

import com.nameof.skeleton.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageBody {
    private UserMessageType messageType;
    private User user;
}
