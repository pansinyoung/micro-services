package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.Branch;

@Repository
public class BookCopiesDAO extends BaseDAO<BookCopies> implements ResultSetExtractor<List<BookCopies>>{

	public void addBookCopies(BookCopies bookcopies) throws SQLException{
		template.update("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (?,?,?)", new Object[]{bookcopies.getBook().getBookId(), bookcopies.getBranch().getBranchId(), bookcopies.getNoOfCopies()});
	}
	
	public void updateBookCopies(BookCopies bookcopies) throws SQLException{
		template.update("UPDATE tbl_book_copies SET noOfCopies=? WHERE bookId = ? AND branchId = ?", new Object[] {bookcopies.getNoOfCopies(),bookcopies.getBook().getBookId(), bookcopies.getBranch().getBranchId()});
	}
	
	public void deleteBookCopies(BookCopies bookcopies) throws SQLException{
		template.update("DELETE FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[]{bookcopies.getBook().getBookId(), bookcopies.getBranch().getBranchId()});
	}

	public List<BookCopies> readAllBookCopies() throws SQLException{
		return template.query("select * from tbl_book_copies where noOfCopies > 0", this);
	}
	
	@Override
	public List<BookCopies> extractData(ResultSet rs)	throws SQLException {
		List<BookCopies> bookcopiess = new ArrayList<BookCopies>();
		while(rs.next()) {
			BookCopies c = new BookCopies();
			Book book = new Book();
			Branch branch = new Branch();
			book.setBookId(rs.getInt("bookId"));
			branch.setBranchId(rs.getInt("branchId"));
			c.setBook(book);
			c.setBranch(branch);
			c.setNoOfCopies(rs.getInt("noOfCopies"));
			bookcopiess.add(c);
		}
		return bookcopiess;
	}

	public void bookCheckOut(int branchId, int bookId) throws SQLException {
		template.update("update tbl_book_copies Set noOfCopies = noOfCopies-1 WHERE bookId = ? and branchId = ?", new Object[] {bookId, branchId});
	}
	
	public void bookReturn(int branchId, int bookId) throws SQLException {
		template.update("update tbl_book_copies Set noOfCopies = noOfCopies+1 WHERE bookId = ? and branchId = ?", new Object[] {bookId, branchId});
	}

	public List<BookCopies> readCopiesByBranchId(int branchId) throws SQLException{
		return template.query("select * from tbl_book_copies where branchId = ? AND noOfCopies > 0", new Object[] {branchId}, this);
	}

	public List<BookCopies> readCopiesByBookId(int bookId) throws SQLException{
		return template.query("select * from tbl_book_copies where bookId = ?", new Object[] {bookId}, this);
	}
	
	public void addCopiesToBranch(int branchId, int bookId, int addedNumber) throws SQLException {
		if(!template.query("SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[] {bookId, branchId}, this) .isEmpty()) {
			template.update("UPDATE tbl_book_copies SET noOfCopies = noOfCopies + ? WHERE bookId = ? AND branchId = ?", new Object[] {addedNumber, bookId, branchId});
		}
		else {
			template.update("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (?,?,?)", new Object[]{bookId, branchId, addedNumber});
		}

	}

	public BookCopies getById(Integer branchId, Integer bookId) throws SQLException {
		List<BookCopies> copies = template.query("SELECT * FROM tbl_book_copies WHERE branchId = ? AND bookId = ?", new Object[] {branchId, bookId}, this);
		if(copies!=null && !copies.isEmpty()) {
			return copies.get(0);
		}
		return null;
	}
}
