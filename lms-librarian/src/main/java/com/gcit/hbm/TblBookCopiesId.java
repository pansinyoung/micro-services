package com.gcit.hbm;

import javax.persistence.*;

@Embeddable
public class TblBookCopiesId implements java.io.Serializable {

	private static final long serialVersionUID = -2141405511760080529L;

	private Integer bookId;
	private Integer branchId;

	@Column(name = "bookId", nullable = false)
	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	@Column(name = "branchId", nullable = false)
	public int getBranchId() {
		return this.branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblBookCopiesId))
			return false;
		TblBookCopiesId castOther = (TblBookCopiesId) other;

		return (this.getBookId() == castOther.getBookId())
				&& (this.getBranchId() == castOther.getBranchId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getBookId();
		result = 37 * result + this.getBranchId();
		return result;
	}
}
