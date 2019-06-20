package com.bridgeIt.fundoo.notes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Component
@Table(name="LabelTable")
public class Label
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long labelId;
	
	@Column(name="labelName")
	@NotNull(message="label name should not be null")
	@NotEmpty(message="label name should not be empty")
	private String labelName;
 
	@Column(name="createdDate")
	private LocalDateTime createdDate;
	
	@Column(name="modifiedDate")
	private LocalDateTime modifiedDate;
	
	@Column(name="userId")
	private long userId;
	
	//@JsonIgnoreProperties(ignoreUnknown = false)
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Note> notes;

	public Label() 
	{

	}

	public long getLabelId() {
		return labelId;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Label [labelId=" + labelId + ", labelName=" + labelName + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", userId=" + userId + ", ]";
	}
	

}
