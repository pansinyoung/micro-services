package com.gcit.hbm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_book", catalog = "library")
public class TblBook implements java.io.Serializable {

	private static final long serialVersionUID = 4150877780362560736L;

	private Integer bookId;
	private String title;
	private TblPublisher publisher;
	private Set<TblLoan> loans = new HashSet<TblLoan>(0);
	private Set<TblBookCopies> bookCopies = new HashSet<TblBookCopies>(0);
	private Set<TblAuthor> authors = new HashSet<TblAuthor>(0);
	private Set<TblGenre> genres = new HashSet<TblGenre>(0);


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookId", unique = true, nullable = false)
	public Integer getBookId() {
		return bookId;
	}
	
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}


	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pubId", referencedColumnName = "publisherId")
    @JsonIgnoreProperties(value = {"books", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public TblPublisher getPublisher() {
		return publisher;
	}
	
	public void setPublisher(TblPublisher publisher) {
		this.publisher = publisher;
	}
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @JsonIgnoreProperties(value = {"book", "branch", "borrower", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public Set<TblLoan> getLoans() {
		return loans;
	}
	
	public void setLoans(Set<TblLoan> loans) {
		this.loans = loans;
	}
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @JsonIgnoreProperties(value = {"book", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public Set<TblBookCopies> getBookCopies() {
		return bookCopies;
	}
	
	public void setBookCopies(Set<TblBookCopies> bookCopies) {
		this.bookCopies = bookCopies;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_book_authors", catalog = "library", joinColumns = { @JoinColumn(name = "bookId", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "authorId", nullable = false, updatable = true) })
    @JsonIgnoreProperties(value = {"books", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public Set<TblAuthor> getAuthors() {
		return authors;
	}
	
	public void setAuthors(Set<TblAuthor> authors) {
		this.authors = authors;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_book_genres", catalog = "library", joinColumns = { @JoinColumn(name = "bookId", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "genre_id", nullable = false, updatable = true) })
    @JsonIgnoreProperties(value = {"books", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public Set<TblGenre> getGenres() {
		return genres;
	}
	
	public void setGenres(Set<TblGenre> genres) {
		this.genres = genres;
	}
	
	
}
