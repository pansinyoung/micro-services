package com.gcit.hbm;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_author", catalog = "library")
public class TblAuthor implements java.io.Serializable {

	private static final long serialVersionUID = -5434477659395457820L;

	private Integer authorId;
	private String authorName;
	private Set<TblBook> books = new HashSet<TblBook>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authorId", unique = true, nullable = false)
	public Integer getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	
	@Column(name = "authorName")
	public String getAuthorName() {
		return authorName;
	}
	
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_book_authors", catalog = "library", joinColumns = { @JoinColumn(name = "authorId", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "bookId", nullable = false, updatable = true) })
    @JsonIgnoreProperties(value = {"authors", "hibernateLazyInitializer", "handler"}, allowSetters = true)
	public Set<TblBook> getBooks() {
		return books;
	}
	
	public void setBooks(Set<TblBook> books) {
		this.books = books;
	};
	
	
}
