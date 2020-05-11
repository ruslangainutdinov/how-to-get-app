package com.ruslanproject.howtoget.enities;

public class UniqueId {
	private long uniqueId;
	private String email;
	private int count;
	public UniqueId() {
		
	}
	public UniqueId(long uniqueId, String email) {
		this.uniqueId = uniqueId;
		this.email = email;
	}

	public long getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(long uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	public void incrementCount() {
		count++;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (uniqueId ^ (uniqueId >>> 32));
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
		UniqueId other = (UniqueId) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (uniqueId != other.uniqueId)
			return false;
		return true;
	}
	
}
