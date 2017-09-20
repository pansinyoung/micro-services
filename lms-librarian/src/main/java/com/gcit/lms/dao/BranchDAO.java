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
public class BranchDAO extends BaseDAO<Branch> implements ResultSetExtractor<List<Branch>>{

	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException {
		List<Branch> branchs = new ArrayList<>();
		while(rs.next()) {
			Branch b = new Branch();
			b.setBranchId(rs.getInt("branchId"));
			b.setBranchName(rs.getString("branchName"));
			b.setBranchAddr(rs.getString("branchAddress"));
			branchs.add(b);
		}
		return branchs;
	}
	
	public Integer addBranch(Branch branch) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO `library`.`tbl_library_branch` (branchName, branchAddress) VALUES (?,?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, branch.getBranchName());
				ps.setString(2, branch.getBranchAddr());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
	
	public void updateBranch(Branch branch) throws SQLException {
		template.update("UPDATE `library`.`tbl_library_branch` SET branchName = ?, branchAddress = ? WHERE branchId = ?", new Object[] {branch.getBranchName(), branch.getBranchAddr(), branch.getBranchId()});
	}
	
	public void deleteBranch(Integer branch) throws SQLException {
		template.update("DELETE FROM `library`.`tbl_library_branch` WHERE branchId = ?", new Object[] {branch});
	}
	
	public Integer getAllCount(String searchString) throws SQLException{
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			return template.queryForObject("SELECT COUNT(*) FROM tbl_library_branch WHERE branchName LIKE ?", new Object[] {searchString}, Integer.class);
		}
		return template.queryForObject("SELECT COUNT(*) FROM tbl_library_branch", Integer.class);
	}
	
	public List<Branch> readAllBranch(String searchString) throws SQLException{
		String sql = "SELECT * FROM `library`.`tbl_library_branch`";
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			sql += "WHERE branchName LIKE ?";
			return template.query(sql, new Object[] {searchString}, this);
		}
		return template.query(sql, this);
	}
	
	public List<Branch> readAllBranch() throws SQLException{
		return template.query("select * from tbl_library_branch", this);
	}
	
	public Branch getById(Integer id) throws SQLException {
		List<Branch> branches = template.query("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[] {id}, this);
		if(branches!=null && !branches.isEmpty())
			return branches.get(0);
		return null;
	}
	
}
