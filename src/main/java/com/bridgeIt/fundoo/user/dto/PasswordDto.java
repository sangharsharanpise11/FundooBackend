package com.bridgeIt.fundoo.user.dto;

public class PasswordDto{

private String newPassword;
private String confirmPassword;

public String getNewPassword() {
	return newPassword;
}
public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
}
public String getConfirmPassword() {
	return confirmPassword;
}
public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}
@Override
public String toString() {
	return "Password [newPassword=" + newPassword + ", confirmPassword=" + confirmPassword + "]";
}
public PasswordDto(String newPassword, String confirmPassword) {
	super();
	this.newPassword = newPassword;
	this.confirmPassword = confirmPassword;
}

}
