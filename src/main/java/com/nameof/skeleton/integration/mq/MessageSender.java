package com.nameof.skeleton.integration.mq;

import com.nameof.skeleton.integration.mq.domain.ReliableMsg;
import com.nameof.skeleton.integration.mq.repository.ReliableMsgRepository;
import com.nameof.skeleton.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SettableListenableFuture;

@Component
public class MessageSender {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private ReliableMsgRepository reliableMsgRepository;

    /**
     * 不保证消息可靠
     * 如开启了事务，则在事务提交后发送
     * @param topic
     * @param payload
     * @return
     */
    public ListenableFuture send(String topic, Object payload) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            return sendAfterTransaction(topic, payload);
        } else {
            return kafkaTemplate.send(topic, payload);
        }
    }

    private ListenableFuture sendAfterTransaction(String topic, Object payload) {
        SettableListenableFuture<SendResult<String, Object>> future = new SettableListenableFuture<>();
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                kafkaTemplate.send(topic, payload).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        future.setException(ex);
                    }

                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        future.set(result);
                    }
                });
            }
        });
        return future;
    }

    /**
     * 可靠消息依赖本地事务，没有事务则返回异常
     * 重试发送总是可能重发，需要下游实现幂等
     * @param topic
     * @param payload
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void sendReliable(String topic, Object payload) {
        ReliableMsg msg = new ReliableMsg();
        msg.setTopic(topic);
        msg.setMessage(JsonUtils.toJson(payload));
        msg.setRetryCount(0);
        msg.setMsgStatus(ReliableMsgStatus.NEW);
        reliableMsgRepository.save(msg);
    }

    @Override
    public int hashCode() {
        return 100;
    }
}