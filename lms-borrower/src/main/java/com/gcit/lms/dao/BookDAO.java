package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gcit.lms.entity.*;

@Repository
public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>>{

	public Integer addBook(Book book) throws SQLException{
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "insert into tbl_book (title, pubId) values (?,?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, book.getTitle());
				ps.setInt(2, book.getPublisher().getPublisherId());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
	
	public void updateBook(Book book) throws SQLException{
		template.update("update tbl_book set title = ?, pubId = ? where bookId = ?", new Object[] {book.getTitle(), book.getPublisher().getPublisherId(), book.getBookId()});
	}
	
	public void deleteBook(Integer book) throws SQLException{
		template.update("delete from tbl_book where bookId = ?", new Object[]{book});
		template.update("delete from tbl_book_authors where bookId = ?", new Object[]{book});
		template.update("delete from tbl_book_genres where bookId = ?", new Object[]{book});
	}

	public List<Book> readAllBooks(String searchString) throws SQLException{
		String sql = "SELECT * FROM `library`.`tbl_book`";
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			sql += "WHERE title LIKE ?";
			return template.query(sql, new Object[] {searchString}, this);
		}
		return template.query(sql, this);
	}
	
	public List<Book> readAllBooks() throws SQLException{
		return template.query("select * from tbl_book", this);
	}
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while(rs.next()) {
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("pubId"));
			b.setPublisher(p);
			books.add(b);
		}
		return books;
	}

	public Integer getAllCount(String searchString) throws SQLException{
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			return template.queryForObject("SELECT COUNT(*) AS a FROM tbl_book WHERE title LIKE ?", new Object[] {searchString}, Integer.class);
		}
		return template.queryForObject("SELECT COUNT(*) AS a FROM tbl_book", Integer.class);
	}
	
	public void addUpdateBookAuthor(int bookId, List<Integer> author) throws SQLException {
		template.update("DELETE FROM `library`.`tbl_book_authors` WHERE bookId= ?", new Object[] {bookId});
		if (author!=null && !author.isEmpty()) {
			for (Integer s : author) {
				template.update("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] {bookId, s});
			} 
		}
	}
	
	public void addUpdateBookGenre(int bookId, List<Integer> genre) throws SQLException {
		template.update("DELETE FROM `library`.`tbl_book_genres` WHERE bookId= ?", new Object[] {bookId});
		if (genre!=null&&!genre.isEmpty()) {
			for (Integer s : genre) {
				template.update("INSERT INTO tbl_book_genres VALUES (?, ?)", new Object[] {s, bookId});
			} 
		}
	}

	public Book getById(Integer id) throws SQLException {
		List<Book> books = template.query("SELECT * FROM tbl_book WHERE bookId = ?", new Object[] {id}, this);
		if(books!=null && !books.isEmpty())
			return books.get(0);
		return null;
	}
	
	public List<Book> searchByAuthorId(int authorId) throws SQLException{
		return template.query("SELECT * FROM tbl_book b JOIN tbl_book_authors ba ON ba.bookId = b.bookId WHERE ba.authorId = ?", new Object[] {authorId}, this);
	}
	
	public List<Book> searchByPublisherId(int publisherId) throws SQLException{
		return template.query("SELECT * FROM tbl_book WHERE pubId = ?", new Object[] {publisherId}, this);

	}

	public List<Book> searchByGenreId(int genreId) throws SQLException{
		return template.query("SELECT * FROM tbl_book b JOIN tbl_book_genres ga ON ga.bookId = b.bookId WHERE ga.genre_id = ?", new Object[] {genreId}, this);
	}

	public List<Book> searchAvailableByBranch(int branchId) {
		return template.query("SELECT * FROM tbl_book b WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE noOfCopies > 0 AND branchId = ?)", new Object[] {branchId}, this);
	}
}
