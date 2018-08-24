package net.codejava.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import net.codejava.spring.model.PersonInfoDetail;
import net.codejava.spring.model.SearchAnything;
import net.codejava.spring.model.SearchAnythingTest;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 * An implementation of the ContactDAO interface.
 * @author Sakekun
 *
 */
public class SearchAnythingTestDAOImpl implements SearchAnythingTestDAO {

	private JdbcTemplate jdbcTemplate;

	public SearchAnythingTestDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void saveOrUpdate(SearchAnythingTest searchAnythingTest) {

		String searchKey = searchAnythingTest.getSearchContents().toUpperCase();
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

		if (searchAnythingTest.getId() > 0) {
			// update
			String sql = "UPDATE tb_search_anything SET SEARCH_KEY = ?, SEARCH_CONTENTS = ?, DATE_CREATE_SEARCH = ?, DATE_UPDATE_SEARCH = ? WHERE ID = ? AND DEL_FLG = 0";
			jdbcTemplate.update(sql, searchKey, searchAnythingTest.getSearchContents(), resultFmDt, resultFmDt, searchAnythingTest.getId());
		} else {
			// insert
			String sql = "INSERT INTO tb_search_anything (ID, SEARCH_KEY, SEARCH_CONTENTS, SEARCH_NUM, DATE_CREATE_SEARCH, DATE_UPDATE_SEARCH, DEL_FLG)"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

			//String sqlSq = "SELECT Auto_increment FROM information_schema.tables WHERE table_name='tb_search_anything'";

			jdbcTemplate.update(sql, null, searchKey, searchAnythingTest.getSearchContents(), 1, resultFmDt, resultFmDt, 0);
		}
	}

	@Override
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
	}

	//@SuppressWarnings("unchecked")
	@Override
	public void copy(int searchAnythingId) {

		SearchAnything searchAnything = new SearchAnything();
		int searchNum = 0;

		/* Get data to copy from reuest by ID */
		String getData2Copy = "SELECT * FROM tb_search_anything WHERE ID = " + searchAnythingId + " AND DEL_FLG = 0";
		//searchAnything = jdbcTemplate.queryForObject(sql1, (RowMapper<SearchAnything>) searchAnything);
		//searchAnything = jdbcTemplate.queryForObject(sql1, SearchAnything.class);
		searchAnything = jdbcTemplate.queryForObject(getData2Copy, new BeanPropertyRowMapper<>(SearchAnything.class));

		/* Copy a new records */
		// insert
		String saveDataCopy = "INSERT INTO tb_search_anything (ID, SEARCH_KEY, SEARCH_CONTENTS, SEARCH_NUM, DATE_CREATE_SEARCH, DATE_UPDATE_SEARCH, DEL_FLG)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		searchNum = searchAnything.getSearchNum() + 1;
		jdbcTemplate.update(saveDataCopy, null, searchAnything.getSearchKey(), searchAnything.getSearchContents(), searchNum, 
				searchAnything.getDateCreateSearch(), searchAnything.getDateUpdateSearch(), 0);

		/* Update records after Copy finished */
		String updateDataCopy = "UPDATE tb_search_anything SET SEARCH_NUM = ? WHERE ID = ? AND DEL_FLG = 0";
		jdbcTemplate.update(updateDataCopy, searchNum, searchAnythingId);
	
	}

	@Override
	public List<SearchAnythingTest> list() {
		String sql = "SELECT * FROM tb_search_anything WHERE DEL_FLG = 0 ORDER BY SEARCH_KEY, SEARCH_NUM ASC";
		List<SearchAnythingTest> listSearchAnything = jdbcTemplate.query(sql, new RowMapper<SearchAnythingTest>() {

			@Override
			public SearchAnythingTest mapRow(ResultSet rs, int rowNum) throws SQLException {
				SearchAnythingTest searchAnything = new SearchAnythingTest();

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
	}

	@Override
	public List<SearchAnythingTest> listBk() {
		String sql = "SELECT * FROM tb_search_anything WHERE DEL_FLG = 1";
		List<SearchAnythingTest> listSearchAnythingBk = jdbcTemplate.query(sql, new RowMapper<SearchAnythingTest>() {

			@Override
			public SearchAnythingTest mapRow(ResultSet rs, int rowNum) throws SQLException {
				SearchAnythingTest searchAnythingBk = new SearchAnythingTest();

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
	}

	@Override
	public SearchAnythingTest get(int searchAnythingId) {
		String sql = "SELECT * FROM  tb_search_anything WHERE ID = " + searchAnythingId + " AND DEL_FLG = 0";
		return jdbcTemplate.query(sql, new ResultSetExtractor<SearchAnythingTest>() {

			@Override
			public SearchAnythingTest extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					SearchAnythingTest searchAnything = new SearchAnythingTest();
					searchAnything.setId(rs.getInt("ID"));
					searchAnything.setSearchKey(rs.getString("SEARCH_KEY"));
					searchAnything.setSearchContents(rs.getString("SEARCH_CONTENTS"));
					searchAnything.setSearchNum(rs.getInt("SEARCH_NUM"));
					searchAnything.setDateCreateSearch(rs.getString("DATE_CREATE_SEARCH"));
					searchAnything.setDateUpdateSearch(rs.getString("DATE_UPDATE_SEARCH"));
					searchAnything.setDelFlg(rs.getInt("DEL_FLG"));
					return searchAnything;
				}

				return null;
			}
		});
	}

	@Override
	public List<SearchAnythingTest> searchContensDisplayTest(SearchAnythingTest searchAnythingTest) {
		SearchAnythingTest searchAnything = new SearchAnythingTest();

		//insert vào 2 table trước.
		//String sqlInsSearchAny = "INSERT INTO tb_search_anything (ID, SEARCH_KEY, SEARCH_CONTENTS, SEARCH_NUM, DATE_CREATE_SEARCH, DATE_UPDATE_SEARCH, DEL_FLG)"
		//		+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		//jdbcTemplate.update(sqlInsSearchAny, null, searchAnything.getSearchKey(), searchAnything.getSearchContents(), searchAnything.getSearchNum() + 1, searchAnything.getDateCreateSearch(), searchAnything.getDateUpdateSearch(), 0);

		//String sqlInsSearchPer = "INSERT INTO tb_search_person (ID, SEARCH_KEY, SEARCH_PERSON_CD, SEARCH_PERSON_NAME, DATE_CREATE_SEARCH, DEL_FLG)"
		//		+ " VALUES (?, ?, ?, ?, ?, ?)";
		//jdbcTemplate.update(sqlInsSearchPer, null, searchAnything.getSearchKey(), searchAnything.getSearchContents(), searchAnything.getDateCreateSearch(), 0);

		//joint 2 table rồi lấy câu truy vấn cuối cùng ra.
		String sqlGetSearchContents = "SELECT * FROM `tb_search_person` AS TSP INNER JOIN `tb_search_anything` AS TSA ON TSA.SEARCH_KEY = TSP.SEARCH_KEY AND TSA.DEL_FLG = 0 WHERE TSP.DEL_FLG = 0";

		List<SearchAnythingTest> listSearchAnythingTest = jdbcTemplate.query(sqlGetSearchContents, new RowMapper<SearchAnythingTest>() {

			@Override
			public SearchAnythingTest mapRow(ResultSet rs, int rowNum) throws SQLException {
				SearchAnythingTest searchAnything = new SearchAnythingTest();

				searchAnything.setId(rs.getInt("ID"));
				searchAnything.setSearchKey(rs.getString("SEARCH_KEY"));
				searchAnything.setSearchContents(rs.getString("SEARCH_CONTENTS"));
				searchAnything.setSearchNum(rs.getInt("SEARCH_NUM"));
				searchAnything.setDateCreateSearch(rs.getString("DATE_CREATE_SEARCH"));
				searchAnything.setDateUpdateSearch(rs.getString("DATE_UPDATE_SEARCH"));
				searchAnything.setDelFlg(rs.getInt("DEL_FLG"));
				searchAnything.setSearchPersonCd(rs.getString("SEARCH_PERSON_CD"));
				searchAnything.setSearchPersonName(rs.getString("SEARCH_PERSON_NAME"));
				return searchAnything;
			}
		});

		return listSearchAnythingTest;
	}

	@Override
	public List<SearchAnythingTest> getJsondata() {
		List<SearchAnythingTest> listSearchAnythingTest = new ArrayList<SearchAnythingTest>();
		SearchAnythingTest searchAnythingTest;
		//ObjectMapper mapperObj = new ObjectMapper();

		String jsonString = "[{\"id\": \"1\", \"name\": \"Dung Le\", \"email\": \"dungle.iq@gmail.com\", \"birthday\": \"1987-09-20 19:30:55\", \"yearOld\": \"32\", \"job\": \"Programming\", \"country\": \"Ha Tinh, Viet Nam\", \"remarks\": \"Cool Papa\"},"
				+ "{\"id\": \"2\", \"name\": \"Thinh Ngo\", \"email\": \"thinhngo.ds@gmail.com\", \"birthday\": \"1987-11-09 19:30:55\", \"yearOld\": \"32\", \"job\": \"Programming\", \"country\": \"Ha Nam, Viet Nam\", \"remarks\": \"Cool Mama\"},"
				+ "{\"id\": \"2\", \"name\": \"Huy Hoang\", \"email\": \"huyhoang.cd@gmail.com\", \"birthday\": \"1987-20-02 20:25:00\", \"yearOld\": \"1\", \"job\": \"Eat & Sleep\", \"country\": \"Ha Tinh, Viet Nam\", \"remarks\": \"Cool Boy\"}]";

		try {
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonString);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				searchAnythingTest = new SearchAnythingTest();
				searchAnythingTest.setId(Integer.parseInt((String) jsonObject.get("id")));
				searchAnythingTest.setSearchPersonName((String) jsonObject.get("name"));
				searchAnythingTest.setSearchPersonCd((String) jsonObject.get("email"));
				listSearchAnythingTest.add(searchAnythingTest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listSearchAnythingTest;
	}

	@Override
	public List<PersonInfoDetail> getJsonPersonInfo() {
		PersonInfoDetail personInfoDetail;
		List<PersonInfoDetail> personInfoDetailList = new ArrayList<PersonInfoDetail>();
		//ObjectMapper mapperObj = new ObjectMapper();

		//http://www.java2novice.com/java-json/jackson/java-object-to-json/
		String jsonString = "[{\"id\": \"1\", \"name\": \"Dung Le\", \"email\": \"dungle.iq@gmail.com\", \"birthday\": \"1987-09-20 19:30:55\", \"yearOld\": \"32\", \"job\": \"Programming\", \"country\": \"Ha Tinh - Viet Nam\", \"remarks\": \"Cool Papa\"},"
				+ "{\"id\": \"2\", \"name\": \"Thinh Ngo\", \"email\": \"thinhngo.ds@gmail.com\", \"birthday\": \"1987-11-09 19:30:55\", \"yearOld\": \"32\", \"job\": \"Teacher\", \"country\": \"Ha Nam - Viet Nam\", \"remarks\": \"Cool Mama\"},"
				+ "{\"id\": \"3\", \"name\": \"Huy Hoang\", \"email\": \"huyhoang.cd@gmail.com\", \"birthday\": \"2002-20-02 20:25:00\", \"yearOld\": \"1\", \"job\": \"Eat & Sleep\", \"country\": \"Ha Tinh - Viet Nam\", \"remarks\": \"Cool Boy\"}]";

		try {
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonString);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				personInfoDetail = new PersonInfoDetail();
				personInfoDetail.setID(Integer.parseInt((String) jsonObject.get("id")));
				personInfoDetail.setNAME((String) jsonObject.get("name"));
				personInfoDetail.setEMAIL((String) jsonObject.get("email"));
				personInfoDetail.setBIRTHDAY((String) jsonObject.get("birthday"));
				personInfoDetail.setYEAR_OLD(Integer.parseInt((String) jsonObject.get("yearOld")));
				personInfoDetail.setJOB((String) jsonObject.get("job"));
				personInfoDetail.setCOUNTRY((String) jsonObject.get("country"));
				personInfoDetail.setREMARKS((String) jsonObject.get("remarks"));
				personInfoDetailList.add(personInfoDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personInfoDetailList;
	}
}
