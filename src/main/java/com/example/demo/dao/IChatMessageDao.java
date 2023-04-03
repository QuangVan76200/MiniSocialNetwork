package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.request.MessageDTO;
import com.example.demo.entity.Message;

@Repository
public interface IChatMessageDao extends JpaRepository<Message, Long> {

    @Query(value =  "Select m from message as m "
            + "WHERE(m.from_user_id = :firstUser AND m.to_user_id = :secondUser)"
            + "OR (m.from_user_id = :secondUser AND m.to_user_id = :firstUser )"
            + "Order By m.time", nativeQuery = true)
    List<Message> findAllMessagesBetweenTwoUsers(@Param("firstUser") Long firstUserId,
            @Param("secondUser") Long secondUserId);

    @Modifying
    @Query(value = "Update message as m " + "SET m.message_status =1"
            + "WHERE m.from_user_id = :firstUser AND  m.to_user_id = :secondUser"
            + "AND m.message_status = 0", nativeQuery = true)
    void updateStatusFromMessages(@Param("firstUser") Long firstUser, @Param("secondUser") Long secondUser);

    @Query(value = "select m.from_user_id, count(*) as total" + "from message as m " + "where m.message_status=0"
            + "and m.to_user_id = :userId" + "group by m.to_user_id" + "order by m.time DESC", nativeQuery = true)
    List<Object[]> getCountOfUnreadMessagesByFromUser(@Param("userId") Long userId);

    @Query(value = "select * from Message as m " +" where "+ " m.to_user_id = :userId AND m.status=0 AND and m.time ="
            + "(select top(1) m1.time from Message as m1 where m1.from_user_id = m.from_user_id order by m1.time DESC)"
            + "Order By m.time DESC", nativeQuery = true)
    List<Message> getAllMessages(@Param("userId") Long userId);

    @Modifying
    @Query(value = "update Message as m" +
            "set m.status = 1" +
            "where status = 0 AND to_user_id = :loggedId  and from_user_id = :secondId", nativeQuery = true)
    Optional<Message> updateCompletionStatus(@Param("loggedId") Long loggedId, @Param("secondId") Long secondId);
    
    
    @Query(value = "select count(*) as Total"+
                   " from Message as m  "+
                   " where m.from_user_id = :firstUserId AND  m.to_user_id= :secondUser AND m.status=0", nativeQuery = true)
    int countUnreadMessage(@Param("firstUser") Long firstUserId,
            @Param("secondUser") Long secondUserId);

}
