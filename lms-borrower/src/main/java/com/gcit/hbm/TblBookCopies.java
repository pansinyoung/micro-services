package com.gcit.hbm;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_book_copies", catalog = "library")
public class TblBookCopies implements java.io.Serializable {

	private static final long serialVersionUID = -6669717277729491617L;
	
	private TblBookCopiesId id;
	private TblBook book;
	private TblBranch branch;
	private Integer noOfCopies;

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "bookId", column = @Column(name = "bookId", nullable = false)), @AttributeOverride(name = "branchId", column = @Column(name = "branchId", nullable = false)) })
	public TblBookCopiesId getId() {
		return id;
	}
	
	public void setId(TblBookCopiesId id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId", nullable = true, insertable = false, updatable = false)
    @JsonIgnoreProperties(value = {"bookCopies", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public TblBook getBook() {
		return book;
	}
	
	public void setBook(TblBook book) {
		this.book = book;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branchId", nullable = true, insertable = false, updatable = false)
    @JsonIgnoreProperties(value = {"bookCopies", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public TblBranch getBranch() {
		return branch;
	}
	
	public void setBranch(TblBranch branch) {
		this.branch = branch;
	}
	
	@Column(name = "noOfCopies")
	public Integer getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Integer noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	
	
}
