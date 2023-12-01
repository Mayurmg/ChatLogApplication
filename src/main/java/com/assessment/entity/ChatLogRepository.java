package com.assessment.entity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {

	List<ChatLog> findByUserAndMessageId(String user, Long messageId, Pageable pageable);

	void deleteByUser(String user);

	void deleteByUserAndMessageId(String user, Long messageId);

	List<ChatLog> findAllByUser(String user);

	List<ChatLog> findByUserAndMessageIdLessThanOrderByTimestampDesc(String user, Long start, Pageable pageable);

	List<ChatLog> findByUserOrderByTimestampDesc(String user, Pageable pageable);
}