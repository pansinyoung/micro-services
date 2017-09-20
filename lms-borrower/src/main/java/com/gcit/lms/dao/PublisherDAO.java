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

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Publisher;

@Repository
public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>>{

	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddr(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(p);
		}
		return publishers;
	}

	public Integer addPublisher(Publisher publisher) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO `library`.`tbl_publisher` (publisherName, publisherAddress, publisherPhone) VALUES (?,?,?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, publisher.getPublisherName());
				ps.setString(2, publisher.getPublisherAddr());
				ps.setString(3, publisher.getPublisherPhone());
				return ps;
			}
		}, holder);
		if (!publisher.getBooks().isEmpty()) {
			for (Book b : publisher.getBooks()) {
				template.update("UPDATE tbl_book SET pubId = ? WHERE bookId = ?",
						new Object[] { holder.getKey().intValue(), b.getBookId() });
			} 
		}
		return holder.getKey().intValue();
	}
	
	public void updatePublisher(Publisher publisher) throws SQLException {
		template.update("UPDATE `library`.`tbl_publisher` SET publisherName = ?, publisherAddress = ?, publisherPhone = ? WHERE publisherId = ?", new Object[] {publisher.getPublisherName(), publisher.getPublisherAddr(), publisher.getPublisherPhone(), publisher.getPublisherId()});
		if (!publisher.getBooks().isEmpty()) {
			for (Book b : publisher.getBooks()) {
				template.update("UPDATE tbl_book SET pubId = ? WHERE bookId = ?",
						new Object[] { publisher.getPublisherId(), b.getBookId() });
			} 
		}
	}
	
	public void deletePublisher(Integer publisher) throws SQLException {
		template.update("UPDATE `library`.`tbl_book` SET pubId = NULL WHERE pubId = ?", new Object[] {publisher} );
		template.update("DELETE FROM `library`.`tbl_publisher` WHERE publisherId = ?", new Object[] {publisher});
	}
	
	public List<Publisher> readAllPublisher(String searchString) throws SQLException{
		String sql = "SELECT * FROM `library`.`tbl_publisher`";
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			sql += "WHERE publisherName LIKE ?";
			return template.query(sql, new Object[] {searchString}, this);
		}
		return template.query(sql, this);
	}

	public List<Publisher> readAllPublisher() throws SQLException{
		return template.query("SELECT * FROM `library`.`tbl_publisher`", this);
	}

	public Publisher getById(Integer id) throws SQLException {
		List<Publisher> pubs = template.query("SELECT * FROM tbl_publisher WHERE publisherId = ?", new Object[] {id}, this);
		if(pubs!=null && !pubs.isEmpty())
			return pubs.get(0);
		return null;
	}

	public Integer getAllCount(String searchString) throws SQLException {
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			return template.queryForObject("SELECT COUNT(*) AS a FROM tbl_publisher WHERE publisherName LIKE ?", new Object[] {searchString}, Integer.class);
		}
		return template.queryForObject("SELECT COUNT(*) AS a FROM tbl_publisher", Integer.class);
	}
	
}
