package com.gcit.hbm;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_library_branch", catalog = "library")
public class TblBranch implements java.io.Serializable {

	private static final long serialVersionUID = -1102418252003678494L;

	private Integer branchId;
	private String branchName;
	private String branchAddr;
	private List<TblLoan> loans;
	private List<TblBookCopies> copies;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branchId", unique = true, nullable = false)
	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	@Column(name = "branchName")
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Column(name = "branchAddress")
	public String getBranchAddr() {
		return branchAddr;
	}

	public void setBranchAddr(String branchAddr) {
		this.branchAddr = branchAddr;
	}

	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinColumn(name = "branchId")
    @JsonIgnoreProperties(value = {"branch", "book", "borrower", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public List<TblLoan> getLoans() {
		return loans;
	}

	public void setLoans(List<TblLoan> loans) {
		this.loans = loans;
	}

	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany
	@JoinColumn(name = "branchId")
    @JsonIgnoreProperties(value = {"branch", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public List<TblBookCopies> getCopies() {
		return copies;
	}

	public void setCopies(List<TblBookCopies> copies) {
		this.copies = copies;
	}
	
	
}
