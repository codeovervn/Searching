package net.codejava.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import net.codejava.spring.model.SearchAnything;
import net.codejava.spring.model.SearchAnythingForm;

/**
 * An implementation of the ContactDAO interface.
 * @author DungLT
 *
 */
public class SearchAnythingFormDAOImpl implements SearchAnythingFormDAO {

	private JdbcTemplate jdbcTemplate;

	public SearchAnythingFormDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void saveOrUpdate(SearchAnythingForm searchAnythingForm) {

		String searchKey = searchAnythingForm.getSearchContents().toUpperCase();
		//searchKey = searchAnything.getSearchContents();

		String temp = Normalizer.normalize(searchKey, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		searchKey = pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll(" ", "_");
		//searchKey = test;
		//String result = test.replaceAll(" ", "_");
		//searchKey = test.replaceAll(" ", "_");
		// [ \ ^ $ . | ? * + ( )

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(dateFormat.format(date));
		String resultFmDt = dateFormat.format(date);

		if (searchAnythingForm.getId() > 0) {
			// update
			String sql = "UPDATE tb_search_anything SET SEARCH_KEY = ?, SEARCH_CONTENTS = ?, DATE_CREATE_SEARCH = ?, DATE_UPDATE_SEARCH = ? WHERE ID = ? AND DEL_FLG = 0";
			jdbcTemplate.update(sql, searchKey, searchAnythingForm.getSearchContents(), resultFmDt, resultFmDt, searchAnythingForm.getId());
		} else {
			// insert
			String sql = "INSERT INTO tb_search_anything (ID, SEARCH_KEY, SEARCH_CONTENTS, SEARCH_NUM, DATE_CREATE_SEARCH, DATE_UPDATE_SEARCH, DEL_FLG)"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

			//String sqlSq = "SELECT Auto_increment FROM information_schema.tables WHERE table_name='tb_search_anything'";

			jdbcTemplate.update(sql, null, searchKey, searchAnythingForm.getSearchContents(), 1, resultFmDt, resultFmDt, 0);
		}
	}

	/*@Override
	public void delete(int searchAnythingId) {
		String sqlDel = "DELETE FROM tb_search_anything WHERE ID = ? AND DEL_FLG = 1";
		//String sqlDel = "UPDATE tb_search_anything SET DEL_FLG = 1 WHERE ID = ? AND DEL_FLG = 0";
		jdbcTemplate.update(sqlDel, searchAnythingId);
	}

	@Override
	public void skip(int searchAnythingId) {
		//String sqlDel = "DELETE FROM tb_search_anything WHERE ID = ? AND DEL_FLG = 0";
		String sqlSkip = "UPDATE tb_search_anything SET DEL_FLG = 1 WHERE ID = ? AND DEL_FLG = 0";
		jdbcTemplate.update(sqlSkip, searchAnythingId);
	}

	@Override
	public void revert(int searchAnythingId) {
		//String sqlDel = "DELETE FROM tb_search_anything WHERE ID = ? AND DEL_FLG = 0";
		String sqlDel = "UPDATE tb_search_anything SET DEL_FLG = 0 WHERE ID = ? AND DEL_FLG = 1";
		jdbcTemplate.update(sqlDel, searchAnythingId);
	}*/

	//@SuppressWarnings("unchecked")
	/*@Override
	public void copy(int searchAnythingId) {

		SearchAnything searchAnything = new SearchAnything();
		int searchNum = 0;

		 Get data to copy from reuest by ID 
		String getData2Copy = "SELECT * FROM tb_search_anything WHERE ID = " + searchAnythingId + " AND DEL_FLG = 0";
		//searchAnything = jdbcTemplate.queryForObject(sql1, (RowMapper<SearchAnything>) searchAnything);
		//searchAnything = jdbcTemplate.queryForObject(sql1, SearchAnything.class);
		searchAnything = jdbcTemplate.queryForObject(getData2Copy, new BeanPropertyRowMapper<>(SearchAnything.class));

		 Copy a new records 
		// insert
		String saveDataCopy = "INSERT INTO tb_search_anything (ID, SEARCH_KEY, SEARCH_CONTENTS, SEARCH_NUM, DATE_CREATE_SEARCH, DATE_UPDATE_SEARCH, DEL_FLG)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		searchNum = searchAnything.getSearchNum() + 1;
		jdbcTemplate.update(saveDataCopy, null, searchAnything.getSearchKey(), searchAnything.getSearchContents(), searchNum, 
				searchAnything.getDateCreateSearch(), searchAnything.getDateUpdateSearch(), 0);

		 Update records after Copy finished 
		String updateDataCopy = "UPDATE tb_search_anything SET SEARCH_NUM = ? WHERE ID = ? AND DEL_FLG = 0";
		jdbcTemplate.update(updateDataCopy, searchNum, searchAnythingId);
	
	}*/

	/*@Override
	public List<SearchAnything> list() {
		String sql = "SELECT * FROM tb_search_anything WHERE DEL_FLG = 0 ORDER BY SEARCH_KEY, SEARCH_NUM ASC";
		List<SearchAnything> listSearchAnything = jdbcTemplate.query(sql, new RowMapper<SearchAnything>() {

			@Override
			public SearchAnything mapRow(ResultSet rs, int rowNum) throws SQLException {
				SearchAnything searchAnything = new SearchAnything();

				searchAnything.setId(rs.getInt("ID"));
				searchAnything.setSearchKey(rs.getString("SEARCH_KEY"));
				searchAnything.setSearchContents(rs.getString("SEARCH_CONTENTS"));
				searchAnything.setSearchNum(rs.getInt("SEARCH_NUM"));
				searchAnything.setDateCreateSearch(rs.getString("DATE_CREATE_SEARCH"));
				searchAnything.setDateUpdateSearch(rs.getString("DATE_UPDATE_SEARCH"));
				searchAnything.setDelFlg(rs.getInt("DEL_FLG"));
				return searchAnything;
			}
		});

		return listSearchAnything;
	}*/

	/*@Override
	public List<SearchAnything> listBk() {
		String sql = "SELECT * FROM tb_search_anything WHERE DEL_FLG = 1";
		List<SearchAnything> listSearchAnythingBk = jdbcTemplate.query(sql, new RowMapper<SearchAnything>() {

			@Override
			public SearchAnything mapRow(ResultSet rs, int rowNum) throws SQLException {
				SearchAnything searchAnythingBk = new SearchAnything();

				searchAnythingBk.setId(rs.getInt("ID"));
				searchAnythingBk.setSearchKey(rs.getString("SEARCH_KEY"));
				searchAnythingBk.setSearchContents(rs.getString("SEARCH_CONTENTS"));
				searchAnythingBk.setSearchNum(rs.getInt("SEARCH_NUM"));
				searchAnythingBk.setDateCreateSearch(rs.getString("DATE_CREATE_SEARCH"));
				searchAnythingBk.setDateUpdateSearch(rs.getString("DATE_UPDATE_SEARCH"));
				searchAnythingBk.setDelFlg(rs.getInt("DEL_FLG"));
				return searchAnythingBk;
			}
		});

		return listSearchAnythingBk;
	}*/

	@Override
	public SearchAnythingForm get(int searchAnythingId) {
		String sql = "SELECT * FROM  tb_search_anything WHERE ID = " + searchAnythingId + " AND DEL_FLG = 0";
		return jdbcTemplate.query(sql, new ResultSetExtractor<SearchAnythingForm>() {

			@Override
			public SearchAnythingForm extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					SearchAnythingForm searchAnythingForm = new SearchAnythingForm();
					searchAnythingForm.setId(rs.getInt("ID"));
					searchAnythingForm.setSearchKey(rs.getString("SEARCH_KEY"));
					searchAnythingForm.setSearchContents(rs.getString("SEARCH_CONTENTS"));
					searchAnythingForm.setSearchNum(rs.getInt("SEARCH_NUM"));
					searchAnythingForm.setDateCreateSearch(rs.getString("DATE_CREATE_SEARCH"));
					searchAnythingForm.setDateUpdateSearch(rs.getString("DATE_UPDATE_SEARCH"));
					searchAnythingForm.setDelFlg(rs.getInt("DEL_FLG"));
					return searchAnythingForm;
				}

				return null;
			}
		});
	}
}
