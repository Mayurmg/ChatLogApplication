package com.assessment.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.assessment.entity.ChatLog;
import com.assessment.entity.ChatLogRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChatLogService {

	@Autowired
	private ChatLogRepository chatLogRepository;

	public ChatLog createChatLogForUser(String user, ChatLog chat) {
		chat.setUser(user);
		chat = chatLogRepository.save(chat);
		return chat;
	}

	public List<ChatLog> getChatLogsForUser(String user, int limit, Long start) {
		Pageable pageable = PageRequest.of(0, limit, Sort.by("timestamp").descending());
		return Objects.nonNull(start)
				? chatLogRepository.findByUserAndMessageIdLessThanOrderByTimestampDesc(user, start, pageable)
				: chatLogRepository.findByUserOrderByTimestampDesc(user, pageable);
	}

	public void deleteByUser(String user) {
		List<ChatLog> chatLogsByUser = chatLogRepository.findAllByUser(user);
		if (chatLogsByUser.isEmpty()) {
			log.error("No messages found for user: {}", user);
			return;
		}
		for (ChatLog log : chatLogsByUser) {
			log.setIsDeleted(Boolean.TRUE);
		}
		chatLogRepository.saveAll(chatLogsByUser);
	}

	public Boolean deleteByUserAndMessageId(String user, Long messageId) {
		Optional<ChatLog> chatOptional = chatLogRepository.findById(messageId);
		if (chatOptional.isPresent()) {
			ChatLog chatLog = chatOptional.get();
			chatLog.setIsDeleted(Boolean.TRUE);
			chatLogRepository.save(chatLog);
			return Boolean.TRUE;
		}
		log.error("No message found with id: {}", messageId);
		return Boolean.FALSE;
	}

}
