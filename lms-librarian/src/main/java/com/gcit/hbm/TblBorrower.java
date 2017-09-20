package com.gcit.hbm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_borrower", catalog = "library")
public class TblBorrower implements java.io.Serializable{

	private static final long serialVersionUID = 4286797009641518009L;
	
	private Integer cardNo;
	private String name;
	private String address;
	private String phone;
	private Set<TblLoan> loans = new HashSet<TblLoan>(0);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cardNo", unique = true, nullable = false)
	public Integer getCardNo() {
		return cardNo;
	}

	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "cardNo")
    @JsonIgnoreProperties(value = {"borrower", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public Set<TblLoan> getLoans() {
		return loans;
	}

	public void setLoans(Set<TblLoan> loans) {
		this.loans = loans;
	}

	
}
