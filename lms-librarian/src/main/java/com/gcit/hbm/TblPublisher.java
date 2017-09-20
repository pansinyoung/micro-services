package com.gcit.hbm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_publisher", catalog = "library")
public class TblPublisher implements java.io.Serializable {

	private static final long serialVersionUID = 4049938131135430289L;
	
	private Integer publisherId;
	private String publisherName;
	private String publisherAddr;
	private String publisherPhone;
	private Set<TblBook> books = new HashSet<TblBook>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "publisherId", unique = true, nullable = false)
	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	
	
	@Column(name = "publisherName")
	public String getPublisherName() {
		return publisherName;
	}
	
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	@Column(name = "publisherAddress")
	public String getPublisherAddr() {
		return publisherAddr;
	}
	
	public void setPublisherAddr(String publisherAddr) {
		this.publisherAddr = publisherAddr;
	}
	
	@Column(name = "publisherPhone")
	public String getPublisherPhone() {
		return publisherPhone;
	}
	
	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "publisher")
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	public Set<TblBook> getBooks() {
		return books;
	}
	
	public void setBooks(Set<TblBook> books) {
		this.books = books;
	}
	
	
}
