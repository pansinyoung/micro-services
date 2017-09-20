package com.gcit.hbm;

import javax.persistence.*;

@Embeddable
public class TblLoanId implements java.io.Serializable {

	private static final long serialVersionUID = 3992577282775573053L;
	
	private Integer bookId;
	private Integer branchId;
	private Integer cardNo;
	private String dateOut;
	
	@Column(name = "bookId", nullable = false)
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	@Column(name = "branchId", nullable = false)
	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	
	@Column(name = "cardNo", nullable = false)
	public Integer getCardNo() {
		return cardNo;
	}
	
	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}
	
	@Column(name = "dateOut", nullable = false)
	public String getDateOut() {
		return dateOut;
	}
	
	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((cardNo == null) ? 0 : cardNo.hashCode());
		result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
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
		TblLoanId other = (TblLoanId) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (cardNo == null) {
			if (other.cardNo != null)
				return false;
		} else if (!cardNo.equals(other.cardNo))
			return false;
		if (dateOut == null) {
			if (other.dateOut != null)
				return false;
		} else if (!dateOut.equals(other.dateOut))
			return false;
		return true;
	}

	
}
