package com.gcit.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Publisher implements Serializable{

	private static final long serialVersionUID = 3148598448836792516L;

	private Integer publisherId;
	private String publisherName;
	private String publisherAddr;
	private String publisherPhone;
	private List<Book>	books;
	/**
	 * @return the publisherId
	 */
	public Integer getPublisherId() {
		return publisherId;
	}
	/**
	 * @param publisherId the publisherId to set
	 */
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	/**
	 * @return the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}
	/**
	 * @param publisherName the publisherName to set
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	/**
	 * @return the publisherAddr
	 */
	public String getPublisherAddr() {
		return publisherAddr;
	}
	/**
	 * @param publisherAddr the publisherAddr to set
	 */
	public void setPublisherAddr(String publisherAddr) {
		this.publisherAddr = publisherAddr;
	}
	/**
	 * @return the publisherPhone
	 */
	public String getPublisherPhone() {
		return publisherPhone;
	}
	/**
	 * @param publisherPhone the publisherPhone to set
	 */
	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}
	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((publisherAddr == null) ? 0 : publisherAddr.hashCode());
		result = prime * result + ((publisherId == null) ? 0 : publisherId.hashCode());
		result = prime * result + ((publisherName == null) ? 0 : publisherName.hashCode());
		result = prime * result + ((publisherPhone == null) ? 0 : publisherPhone.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publisher other = (Publisher) obj;
		if (publisherAddr == null) {
			if (other.publisherAddr != null)
				return false;
		} else if (!publisherAddr.equals(other.publisherAddr))
			return false;
		if (publisherId == null) {
			if (other.publisherId != null)
				return false;
		} else if (!publisherId.equals(other.publisherId))
			return false;
		if (publisherName == null) {
			if (other.publisherName != null)
				return false;
		} else if (!publisherName.equals(other.publisherName))
			return false;
		if (publisherPhone == null) {
			if (other.publisherPhone != null)
				return false;
		} else if (!publisherPhone.equals(other.publisherPhone))
			return false;
		return true;
	}
	
	
	
}

