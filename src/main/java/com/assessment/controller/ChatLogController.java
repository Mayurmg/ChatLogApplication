package com.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.entity.ChatLog;
import com.assessment.service.ChatLogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/chatlogs/{user}")
public class ChatLogController {

	@Autowired
	private ChatLogService chatLogService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> createChatLog(@PathVariable("user") String user, @RequestBody ChatLog chatLog) {
		log.info("creating chatlog for user: {}", user);
		ChatLog savedmessage = chatLogService.createChatLogForUser(user, chatLog);
		return ResponseEntity.ok(savedmessage.getId());
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChatLog>> getChatLogs(@PathVariable("user") String user,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(required = false) Long start) {
		log.info("fetching last: {} chat logs for user: {}", limit, user);
		List<ChatLog> chatLogs = chatLogService.getChatLogsForUser(user, limit, start);
		return ResponseEntity.ok(chatLogs);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteChatLogs(@PathVariable String user) {
		log.info("deleting chat logs for user: {}", user);
		chatLogService.deleteByUser(user);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{messageId}")
	public ResponseEntity<Void> deleteChatLog(@PathVariable String user, @PathVariable Long messageId) {
		log.info("deleting message with id: {} for user: {}", messageId, user);
		Boolean isChatDeleted = chatLogService.deleteByUserAndMessageId(user, messageId);
		if (Boolean.TRUE.equals(isChatDeleted)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}

}
