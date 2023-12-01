package com.assessment.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NonNull;

@Entity
@Data
public class ChatLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String user;

	@NonNull
	private String message;

	@NonNull
	private Long timestamp;

	@NonNull
	private Boolean isSent;

	@NonNull
	private Boolean isDeleted = false;

	@CreationTimestamp
	protected Date createdAt;

	@UpdateTimestamp
	protected Date updatedAt;
}
