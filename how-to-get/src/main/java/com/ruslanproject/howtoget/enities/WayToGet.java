package com.ruslanproject.howtoget.enities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class WayToGet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="ufn")
	private String ufn;
	
	@Column(name="company")
	private String companyProvider;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="departure_date")
	private String departureDate;
	
	@Column(name="arrival_date")
	private String duration;
	
	@Column(name="departure_from")
	private String locationFrom;
	
	@Column(name="arrival_to")
	private String locationTo;
	
	public WayToGet() {
		
	}
	
	public WayToGet(String id, String companyProvider, Double price, String departureDate, String duration,
			String locationFrom, String locationTo) {
		this.ufn = id;
		this.companyProvider = companyProvider;
		this.price = price;
		this.departureDate = departureDate;
		this.duration = duration;
		this.locationFrom = locationFrom;
		this.locationTo = locationTo;
	}
	public String getCompanyProvider() {
		return companyProvider;
	}
	public void setCompanyProvider(String companyProvider) {
		this.companyProvider = companyProvider;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getLocationFrom() {
		return locationFrom;
	}
	public void setLocationFrom(String locationFrom) {
		this.locationFrom = locationFrom;
	}
	public String getLocationTo() {
		return locationTo;
	}
	public void setLocationTo(String locationTo) {
		this.locationTo = locationTo;
	}
	public String getUfn() {
		return ufn;
	}
	public void setUfn(String ufn) {
		this.ufn = ufn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer ids) {
		this.id = ids;
	}

	@Override
	public String toString() {
		return "WayToGet [id=" + id + ", ufn=" + ufn + ", companyProvider=" + companyProvider + ", price=" + price
				+ ", departureDate=" + departureDate + ", duration=" + duration + ", locationFrom=" + locationFrom
				+ ", locationTo=" + locationTo + "]";
	}
	
	
}
