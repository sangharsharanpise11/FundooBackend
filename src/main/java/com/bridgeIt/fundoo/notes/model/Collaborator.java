package com.bridgeIt.fundoo.notes.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name="CollaboratorTable")
public class Collaborator
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long collaboratorId;

	private String emailId;
	
	private long userId;
    private long noteId;
  
	@Column(name="createdDate")
	private LocalDateTime createdDate;
	
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getNoteId() {
		return noteId;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "Collaborator [userId=" + userId + ", noteId=" + noteId + ", createdDate="
				+ createdDate + "]";
	}

	
}
