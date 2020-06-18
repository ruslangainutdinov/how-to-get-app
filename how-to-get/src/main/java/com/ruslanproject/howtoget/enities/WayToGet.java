package com.ruslanproject.howtoget.enities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ruslanproject.howtoget.validators.FieldsDoNotMatch;

/**
 * Entity class for WayToGet
 * 
 * @author Ruslan Gainutdinov
 *
 */

@MappedSuperclass
@FieldsDoNotMatch(first = "locationFrom", second = "locationTo",message="Departure location and arrival location should not match")
//@FutureDateTimeString(first = "departureDate", message = "Arrival date must be after departure date", second = "arrivalDate")
public class WayToGet {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Pattern(regexp = "[a-zA-Z0-9]{3,6}", message="UFN must be 3 or 4 letters or numbers")
	@Column(name="ufn")
	private String ufn;
	
	@Column(name="company")
	private String companyProvider;
	
	@Column(name="price")
	@Min(value = 5, message="Minimum price is 5$")
	private Double price;
	
	@Column(name="departure_date")
	private String departureDate;
	
	
	@Column(name="arrival_date")
	private String arrivalDate;
	
	
	@NotNull
	@Size(min=4, message="Length of the location cannot be less than 5")
	@Pattern(regexp = "^[A-Z].*[^0-9]",message="Location must starts with capital letter and should not contain number")
	@Column(name="departure_from")
	private String locationFrom;
	
	@NotNull
	@Size(min=4, message="Length of the location cannot be less than 5")
	@Pattern(regexp = "^[A-Z].*[^0-9]",message="Location must starts with capital letter and should not contain number")	
	@Column(name="arrival_to")
	private String locationTo;
	
	@Column(name="tickets_available")
	private int ticketsAvailable;
	
	public WayToGet() {
		
	}
	
	public WayToGet(String id, String companyProvider, Double price, String departureDate, String arrivalDate,
			String locationFrom, String locationTo) {
		this.ufn = id;
		this.companyProvider = companyProvider;
		this.price = price;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
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
	public String  getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
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


	public int getTicketsAvailable() {
		return ticketsAvailable;
	}

	public void setTicketsAvailable(int ticketsAvailable) {
		this.ticketsAvailable = ticketsAvailable;
	}
	
	
	@Override
	public String toString() {
		return "[id=" + id + ", ufn=" + ufn + ", companyProvider=" + companyProvider + ", price=" + price
				+ ", departureDate=" + departureDate + ", arrivalDate=" + arrivalDate + ", locationFrom=" + locationFrom
				+ ", locationTo=" + locationTo + " ticketsAvailable " +ticketsAvailable+ "]";
	}
	
	public enum Type{
		BUS,
		FLIGHT
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyProvider == null) ? 0 : companyProvider.hashCode());
		result = prime * result + ((departureDate == null) ? 0 : departureDate.hashCode());
		result = prime * result + ((arrivalDate == null) ? 0 : arrivalDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((locationFrom == null) ? 0 : locationFrom.hashCode());
		result = prime * result + ((locationTo == null) ? 0 : locationTo.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((ufn == null) ? 0 : ufn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WayToGet other = (WayToGet) obj;
		if (companyProvider == null) {
			if (other.companyProvider != null)
				return false;
		} else if (!companyProvider.equals(other.companyProvider))
			return false;
		if (departureDate == null) {
			if (other.departureDate != null)
				return false;
		} else if (!departureDate.equals(other.departureDate))
			return false;
		if (arrivalDate == null) {
			if (other.arrivalDate != null)
				return false;
		} else if (!arrivalDate.equals(other.arrivalDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (locationFrom == null) {
			if (other.locationFrom != null)
				return false;
		} else if (!locationFrom.equals(other.locationFrom))
			return false;
		if (locationTo == null) {
			if (other.locationTo != null)
				return false;
		} else if (!locationTo.equals(other.locationTo))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (ufn == null) {
			if (other.ufn != null)
				return false;
		} else if (!ufn.equals(other.ufn))
			return false;
		return true;
	}


}
