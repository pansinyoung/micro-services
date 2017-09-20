package com.gcit.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Genre implements Serializable{

	private static final long serialVersionUID = 1066847902991243572L;
	
	private Integer genre_id;
	private String genre_name;
	private List<Book> books;
	
	//Getters and Setters
	/**
	 * @return the genreId
	 */
	public Integer getGenre_id() {
		return genre_id;
	}
	/**
	 * @param genreId the genreId to set
	 */
	public void setGenre_id(Integer genreId) {
		this.genre_id = genreId;
	}
	/**
	 * @return the genrename
	 */
	public String getGenre_name() {
		return genre_name;
	}
	/**
	 * @param genreName the genreName to set
	 */
	public void setGenreName(String genreName) {
		this.genre_name = genreName;
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
	
	//hashcode and equals
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genre_id == null) ? 0 : genre_id.hashCode());
		result = prime * result + ((genre_name == null) ? 0 : genre_name.hashCode());
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
		Genre other = (Genre) obj;
		if (genre_id == null) {
			if (other.genre_id != null)
				return false;
		} else if (!genre_id.equals(other.genre_id))
			return false;
		if (genre_name == null) {
			if (other.genre_name != null)
				return false;
		} else if (!genre_name.equals(other.genre_name))
			return false;
		return true;
	}
	
	
}
