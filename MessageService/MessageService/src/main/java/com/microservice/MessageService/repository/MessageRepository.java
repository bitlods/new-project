package com.microservice.MessageService.repository;

import com.microservice.MessageService.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query("FROM Message m WHERE m.sendUser = :sendUserId AND m.receiveUser = :receiveUserId")
    List<Message> findAllMessagesBySendUserAndReceiveUser(@Param("sendUserId") Long sendUserId, @Param("receiveUserId") Long receiveUserId, Pageable pageable);
    List<Message> findAllMessagesByReceiveUserAndSendUser(Long receiverId, Long id, Pageable pageable);
//    @Query("SELECT COUNT(*) FROM Message m WHERE m.sendUser.id = :sendUserId AND m.receiveUser.id = :receiveUserId")
//    Long countByReceiveUserIdAndSendUserId(@Param("receiveUserId") Integer sendUserId, @Param("sendUserId") Integer receiveUserId);
}
