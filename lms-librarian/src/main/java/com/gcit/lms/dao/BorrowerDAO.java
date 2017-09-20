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
public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>>{
	
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while(rs.next()) {
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setName(rs.getString("name"));
			b.setAddress(rs.getString("address"));
			b.setPhone(rs.getString("phone"));
			borrowers.add(b);
		}
		return borrowers;
	}
	
	public Integer addBorrower(Borrower borrower) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO `library`.`tbl_borrower` (name, address, phone) VALUES (?,?,?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, borrower.getName());
				ps.setString(2, borrower.getAddress());
				ps.setString(3, borrower.getPhone());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
	
	public void updateBorrower(Borrower borrower) throws SQLException {
		template.update("UPDATE `library`.`tbl_borrower` SET name = ?, address = ?, phone = ? WHERE cardNo = ?", new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo()});
	}
	
	public void deleteBorrower(Integer borrower) throws SQLException {
		template.update("DELETE FROM `library`.`tbl_borrower` WHERE cardNo = ?", new Object[] {borrower});
	}
	
	public List<Borrower> readAllBorrower(String searchString) throws SQLException{
		String sql = "SELECT * FROM `library`.`tbl_borrower`";
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			sql += "WHERE name LIKE ?";
			return template.query(sql, new Object[] {searchString}, this);
		}
		return template.query(sql, this);
	}

	public List<Borrower> readAllBorrowers() throws SQLException{
		return template.query("select * from tbl_borrower", this);
	}
	
	public Integer getAllCount(String searchString) throws SQLException{
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			return template.queryForObject("SELECT COUNT(*) AS a FROM tbl_borrower WHERE name LIKE ?", new Object[] {searchString}, Integer.class);
		}
		return template.queryForObject("SELECT COUNT(*) AS a FROM tbl_borrower", Integer.class);	
	}
	
	public Borrower getById(Integer id) throws SQLException {
		List<Borrower> rs = template.query("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[] {id}, this);
		if(rs!=null&&!rs.isEmpty()) {
			return rs.get(0);
		}
		return null;
	}
	
	public Boolean borrowerLogin(int cardNo) throws SQLException {
		List<Borrower> li = template.query("select * from tbl_borrower where cardNo = ?", new Object[] {cardNo}, this);
		if(li!=null&&!li.isEmpty()) 
			return true;
		return false;
	}
}
