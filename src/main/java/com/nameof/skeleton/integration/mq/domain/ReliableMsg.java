package com.nameof.skeleton.integration.mq.domain;

import com.nameof.skeleton.integration.mq.ReliableMsgStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "t_reliable_msg")
public class ReliableMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    private String message;
    @Enumerated(EnumType.STRING)
    private ReliableMsgStatus msgStatus;
    private int retryCount;
    private String lastError;
    private Date lastSendTime;
}
