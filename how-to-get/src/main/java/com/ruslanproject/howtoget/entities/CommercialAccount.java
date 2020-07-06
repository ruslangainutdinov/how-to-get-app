package com.ruslanproject.howtoget.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Entity class for CommercialAccount
 * 
 * @author Ruslan Gainutdinov
 *
 */

@Entity
@Table(name="commercial_accounts")
public class CommercialAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name ="company_name")
	private String companyName;
	
	@Column(name="telephone")
	private String telephone;
	
	@Column(name="address")
	private String address;
	
	@OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
			CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
	@JoinColumn(name = "user_profile_id ")
	private UserProfile userProfile;
	
	@Column(name="transport_types")
	private String transportTypes;
	
	public CommercialAccount() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "CommercialAccount [id=" + id + ", companyName=" + companyName + ", telephone=" + telephone
				+ ", address=" + address + ", userProfile=" + userProfile + "]";
	}

	public String getTransportTypes() {
		return transportTypes;
	}
	
	public String[] getFormattedTransportTypes() {
		return getTransportTypes().split(",");
	}

	public void setTransportTypes(String transportTypes) {
		this.transportTypes = transportTypes;
	}
	
	
}
