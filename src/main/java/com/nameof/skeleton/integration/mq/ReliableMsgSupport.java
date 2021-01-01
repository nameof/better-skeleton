package com.nameof.skeleton.integration.mq;

import com.nameof.skeleton.integration.mq.domain.ReliableMsg;
import com.nameof.skeleton.integration.mq.repository.ReliableMsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Date;
import java.util.List;

@Component
public class ReliableMsgSupport {
    private static final int MAX_RETRY = 3;
    @Autowired
    private ReliableMsgRepository msgRepository;
    @Autowired
    private MessageSender messageSender;

    @Scheduled(fixedDelay = 5000)
    public void sendMsg() {
        List<ReliableMsg> newMsgList = msgRepository.findByMsgStatus(ReliableMsgStatus.NEW);
        for (ReliableMsg msg : newMsgList) {
            send(msg);
        }

        List<ReliableMsg> errorMsgList = msgRepository.findByMsgStatus(ReliableMsgStatus.ERROR);
        for (ReliableMsg msg : errorMsgList) {
            if (msg.getRetryCount() == MAX_RETRY) {
                // 死信队列、人工处理、反向通知发送方
            } else {
                send(msg);
            }
        }
    }

    private void send(ReliableMsg msg) {
        messageSender.send(msg.getTopic(), msg.getMessage()).addCallback(new ListenableFutureCallback<Object>() {
            @Override
            public void onFailure(Throwable ex) {
                msg.setRetryCount(msg.getRetryCount() + 1);
                msg.setLastSendTime(new Date());
                msg.setLastError(ex.getMessage());
                msg.setMsgStatus(ReliableMsgStatus.ERROR);
                msgRepository.save(msg);
            }

            @Override
            public void onSuccess(Object result) {
                msgRepository.delete(msg);
            }
        });
    }
}
