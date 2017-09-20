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

import com.gcit.lms.entity.Genre;

@Repository
public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<>();
		while(rs.next()) {
			Genre g = new Genre();
			g.setGenre_id(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			genres.add(g);
		}
		return genres;
	}

	public int addGenre(Genre genre) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO `library`.`tbl_genre` (genre_name) VALUES (?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, genre.getGenre_name());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
	
	public void updateGenre(Genre genre) throws SQLException {
		template.update("UPDATE `library`.`tbl_genre` SET genre_name = ? WHERE genre_id = ?", new Object[] {genre.getGenre_name(), genre.getGenre_id()});
	}
	
	public void deleteGenre(Integer genre) throws SQLException {
		template.update("DELETE FROM `library`.`tbl_book_genres` WHERE genre_id = ?", new Object[] {genre});
		template.update("DELETE FROM `library`.`tbl_genre` WHERE genre_id = ?", new Object[] {genre});
	}
	
	public List<Genre> readAllGenre(String searchString) throws SQLException{
		String sql = "SELECT * FROM `library`.`tbl_genre`";
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			sql += "WHERE genre_name LIKE ?";
			return template.query(sql, new Object[] {searchString}, this);
		}
		return template.query(sql, this);
	}
	
	public List<Genre> readAllGenre() throws SQLException{
		return template.query("SELECT * FROM `library`.`tbl_genre`", this);
	}

	public List<Genre> searchByBookId(int bookId) throws SQLException{
		return template.query("SELECT * FROM tbl_genre g JOIN tbl_book_genres ga ON ga.genre_Id = g.genre_Id WHERE ga.bookId = ?", new Object[] {bookId}, this);
	}

	public Integer getAllCount(String searchString) throws SQLException{
		if(searchString!=null&&!searchString.isEmpty()) {
			searchString =  "%" + searchString + "%";
			return template.queryForObject("SELECT COUNT(*) AS a FROM tbl_genre WHERE genre_name LIKE ?", new Object[] {searchString}, Integer.class);
		}
		return template.queryForObject("SELECT COUNT(*) AS a FROM tbl_genre", Integer.class);	}

	public Genre getById(Integer id) throws SQLException {
		List<Genre> genres = template.query("SELECT * FROM tbl_genre WHERE genre_id = ?", new Object[] {id}, this);
		if(genres!=null && !genres.isEmpty()) {
			return genres.get(0);
		}
		return null;
	}

	public void addUpdateGenreBook(int genreId, List<Integer> books) throws NumberFormatException, SQLException {
		template.update("DELETE FROM `library`.`tbl_book_genres` WHERE genre_id = ?", new Object[] {genreId});
		if (books!=null&&!books.isEmpty()) {
			for (Integer s : books) {
				template.update("INSERT INTO tbl_book_genres VALUES (?, ?)", new Object[] {genreId, s});			} 
		}
	}
}
