package com.bridgeIt.fundoo.notes.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

import com.bridgeIt.fundoo.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@Table(name = "NoteTable")
public class Note implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long noteId;

	private long userId;

	private String color;

	private String title;

	private String description;

	private boolean isPin;

	private boolean isArchive;

	private boolean isTrash;

	private LocalDateTime created;

	private LocalDateTime modified;

	private String remainder;

//	public String getCollaboratorEmailId() {
//		return collaboratorEmailId;
//	}
//
//	public void setCollaboratorEmailId(String collaboratorEmailId) {
//		this.collaboratorEmailId = collaboratorEmailId;
//	}

	//private String collaboratorEmailId;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
    //@JoinTable(name = "note_table_collaborated_users",JoinColumn=(@JoinColumn(name=""))
	private List<User> collaboratedUsers;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Label> listLabel;

	public Note() {

	}

	public List<User> getCollaboratedUsers() {
		return collaboratedUsers;
	}

	public void setCollaboratedUsers(List<User> collaboratedUsers) {
		this.collaboratedUsers = collaboratedUsers;
	}

	public long getNoteId() {
		return noteId;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPin() {
		return isPin;
	}

	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}

	public boolean isArchive() {
		return isArchive;
	}

	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	public boolean isTrash() {
		return isTrash;
	}

	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Label> getListLabel() {
		return listLabel;
	}

	public void setListLabel(List<Label> listLabel) {
		this.listLabel = listLabel;
	}

	public String getRemainder() {
		return remainder;
	}

	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}

	
	public Note(long noteId, long userId, String color, String title, String description, boolean isPin,
			boolean isArchive, boolean isTrash, LocalDateTime created, LocalDateTime modified, String remainder,
			String collaboratorEmailId, List<User> collaboratedUsers, List<Label> listLabel) {
		super();
		this.noteId = noteId;
		this.userId = userId;
		this.color = color;
		this.title = title;
		this.description = description;
		this.isPin = isPin;
		this.isArchive = isArchive;
		this.isTrash = isTrash;
		this.created = created;
		this.modified = modified;
		this.remainder = remainder;
		this.collaboratedUsers = collaboratedUsers;
		this.listLabel = listLabel;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", userId=" + userId + ", color=" + color + ", title=" + title
				+ ", description=" + description + ", isPin=" + isPin + ", isArchive=" + isArchive + ", isTrash="
				+ isTrash + ", created=" + created + ", modified=" + modified + ", remainder=" + remainder
				+ ", collaboratedUsers=" + collaboratedUsers
				+ ", listLabel=" + listLabel + "]";
	}

}
