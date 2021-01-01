package com.nameof.skeleton.integration.mq.repository;

import com.nameof.skeleton.integration.mq.ReliableMsgStatus;
import com.nameof.skeleton.integration.mq.domain.ReliableMsg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReliableMsgRepository extends JpaRepository<ReliableMsg, Long> {
    List<ReliableMsg> findByMsgStatus(ReliableMsgStatus msgStatus);
}
