package com.gcit.hbm;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_book_loans", catalog = "library")
public class TblLoan implements java.io.Serializable {

	private static final long serialVersionUID = -3248501327100510800L;

	private TblLoanId id;
	private TblBook book;
	private TblBranch branch;
	private TblBorrower borrower;
	private String dateIn;
	private String dateOut;
	private String dueDate;

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "bookId", column = @Column(name = "bookId", nullable = false)),
		@AttributeOverride(name = "branchId", column = @Column(name = "branchId", nullable = false)),
		@AttributeOverride(name = "cardNo", column = @Column(name = "cardNo", nullable = false)),
		@AttributeOverride(name = "dateOut", column = @Column(name = "dateOut", nullable = false)) })
	public TblLoanId getId() {
		return id;
	}
	
	public void setId(TblLoanId id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bookId", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties(value = {"loans", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public TblBook getBook() {
		return book;
	}
	
	public void setBook(TblBook tblBook) {
		this.book = tblBook;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branchId", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties(value = {"loans", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public TblBranch getBranch() {
		return branch;
	}
	
	public void setBranch(TblBranch tblLibraryBranch) {
		this.branch = tblLibraryBranch;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cardNo", nullable = false, insertable = false, updatable = false)
    @JsonIgnoreProperties(value = {"loans", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public TblBorrower getBorrower() {
		return borrower;
	}
	
	public void setBorrower(TblBorrower tblBorrower) {
		this.borrower = tblBorrower;
	}
	
	@Column(name = "dateOut", nullable = false, insertable = false, updatable = false)
	public String getDateOut() {
		return dateOut;
	}
	
	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}
	
	@Column(name = "dueDate")
	public String getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	@Column(name = "dateIn")
	public String getDateIn() {
		return dateIn;
	}
	
	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}
	
	
}
