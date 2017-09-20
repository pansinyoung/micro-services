package com.gcit.hbm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_genre", catalog = "library")
public class TblGenre implements java.io.Serializable {

	private static final long serialVersionUID = -8348584464104781671L;

	private Integer genre_id;
	private String genre_name;
	private Set<TblBook> books = new HashSet<TblBook>(0);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "genre_id", unique = true, nullable = false)
	public Integer getGenre_id() {
		return this.genre_id;
	}

	public void setGenre_id(Integer genre_id) {
		this.genre_id = genre_id;
	}

	@Column(name = "genre_name")
	public String getGenre_name() {
		return genre_name;
	}

	public void setGenre_name(String genre_name) {
		this.genre_name = genre_name;
	}

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_book_genres", catalog = "library", joinColumns = { @JoinColumn(name = "genre_id", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "bookId", nullable = false, updatable = true) })
    @JsonIgnoreProperties(value = {"genres", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public Set<TblBook> getBooks() {
		return books;
	}

	public void setBooks(Set<TblBook> books) {
		this.books = books;
	}
	
}
