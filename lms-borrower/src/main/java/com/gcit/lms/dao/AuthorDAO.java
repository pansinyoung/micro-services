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
public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>>{

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while(rs.next()) {
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}

	public Integer addAuthor(Author author) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO `library`.`tbl_author` (authorName) VALUES (?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, author.getAuthorName());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
	
	public void updateAuthor(Author author) throws SQLException {
		template.update("UPDATE `library`.`tbl_author` SET authorName = ? WHERE authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
	}
	
	public void deleteAuthor(Integer author) throws SQLException {
		template.update("DELETE FROM `library`.`tbl_author` WHERE authorId = ?", new Object[] {author});
	}
	
	public List<Author> readAllAuthor(String searchString) throws SQLException{
		String sql = "SELECT * FROM `library`.`tbl_author`";
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			sql += "WHERE authorName LIKE ?";
			return template.query(sql, new Object[] {searchString}, this);
		}
		return template.query(sql, this);
	}

	public List<Author> readAllAuthor() throws SQLException{
		return template.query("SELECT * FROM `library`.`tbl_author`", this);
	}
	
	public List<Author> searchByBookId(int bookId) throws SQLException{
		return template.query("SELECT * FROM tbl_author a JOIN tbl_book_authors ba ON ba.authorId = a.authorId WHERE ba.bookId = ?", new Object[] {bookId}, this);
	}
	
	public Integer getAllCount(String searchString) throws SQLException {
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			return template.queryForObject("SELECT COUNT(authorId) AS a FROM tbl_author WHERE authorName LIKE ?", new Object[] {searchString}, Integer.class);
		}
		return template.queryForObject("SELECT COUNT(authorId) AS a FROM tbl_author", Integer.class);
	}

	public Author getById(Integer id) throws SQLException {
		List<Author> authors = template.query("SELECT * FROM tbl_author WHERE authorId = ?", new Object[] {id}, this);
		if(authors!=null&&!authors.isEmpty()) {
			return authors.get(0);
		}
		return null;
	}

	public void addUpdateAuthorBook(int authorId, List<Integer> book) throws SQLException {
		template.update("DELETE FROM `library`.`tbl_book_authors` WHERE authorId= ?", new Object[] {authorId});
		if(book==null)
			return;
		for(Integer s: book) {
			template.update("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] {s, authorId});
		}
	}
}
