package com.ruslanproject.howtoget.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.ruslanproject.howtoget.validators.PasswordMatch;

/**
 * Entity class for User
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Entity
@Table(name="users")
@PasswordMatch(first = "password", second = "tempPassword")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Size(min=2,message="Please enter correct first name")
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	@Size(min=2,message="Please enter correct last name")
	private String lastName;
	
	@Column(name="email")
	@Size(min=5,message="Please enter correct email")
	private String email;
	
	
	@Min(value=1,message="Please enter correct 'Age' value")
	@Max(value=99,message="Please enter correct 'Age' value")
	@Column(name="age")
	private Integer age;
	
	@Transient
	@Size(min=6,max=20,message="Length of password must be more than 6 and less than 20")
	private String password;
	
	@Column(name="pass")
	private String encryptedPassword;
	
	@Column(name="roles")
	private String roles= "ROLE_USER";
	
	@Transient
	private String tempPassword;
	
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	UserProfile userProfile;
	
	public User () {
		
	}
	
	public User(String password, String firstName, String lastName, String email, Integer age) {
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.age=age;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", age="+age+ "]";
	}
}
