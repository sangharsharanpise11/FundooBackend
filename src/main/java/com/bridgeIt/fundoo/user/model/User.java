package com.bridgeIt.fundoo.user.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;
import com.bridgeIt.fundoo.notes.model.Label;
import com.bridgeIt.fundoo.notes.model.Note;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
@Entity
@Table(name = "userDetails123")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;

	@NotEmpty(message = "please provide your name")
	@NotNull(message = "please provide your name")
	private String firstName;

	@NotEmpty(message = "please provide your name")
	@NotNull(message = "please provide your name")
	private String lastName;

	@Email(regexp =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$")
	@NotEmpty(message = "Please provide valid email")
    @NotNull(message = "Please provide valid email")
	private String emailId;

	@Pattern(regexp = "[0-9]{10}" , message = "provide valid mobile number")
	@NotEmpty(message = "please provide your mobile number")
    @NotNull(message = "please provide your mobile number")
	private String mobNo;


	@NotEmpty(message = "Please provide password")
    @NotNull(message = "Please provide password")
	private String password;
	private boolean isVerified = false;
	private LocalDate registeredDate;
	private LocalDate modifiedDate;
	private String image;
	
	@JsonIgnore
	@ManyToMany(mappedBy="collaboratedUsers")
	private List<Note>collaboratedNote;
	
	//@JsonIgnoreProperties
	@OneToMany(cascade = CascadeType.ALL)
	private List<Note> notes;
	
	//@JsonIgnoreProperties
	@OneToMany(cascade = CascadeType.ALL)
    private List<Label> label;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobNo() {
		return mobNo;
	}
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isVerified() {
		return isVerified;
	}
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	public LocalDate getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(LocalDate registeredDate) {
		this.registeredDate = registeredDate;
	}
	public LocalDate getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public List<Label> getLabel() {
		return label;
	}
	public void setLabel(List<Label> label) {
		this.label = label;
	}
	
	public List<Note> getCollaboratedNote() {
		return collaboratedNote;
	}
	public void setCollaboratedNote(List<Note> collaboratedNote) {
		this.collaboratedNote = collaboratedNote;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", mobNo=" + mobNo + ", password=" + password + ", isVerified=" + isVerified + ", registeredDate="
				+ registeredDate + ", modifiedDate=" + modifiedDate + ", notes=" + notes + "]";
	}
	
}