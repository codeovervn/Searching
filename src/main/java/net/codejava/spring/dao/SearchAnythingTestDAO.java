package net.codejava.spring.dao;

import java.util.List;

import net.codejava.spring.model.PersonInfoDetail;
import net.codejava.spring.model.SearchAnythingTest;

/**
 * Defines DAO operations for the contact model.
 * @author Sakekun
 *
 */
public interface SearchAnythingTestDAO {

	public void saveOrUpdate(SearchAnythingTest searchAnything);

	public void delete(int searchAnythingId);

	public void revert(int searchAnythingId);

	public SearchAnythingTest get(int searchAnythingId);

	public List<SearchAnythingTest> list();

	public List<SearchAnythingTest> listBk();

	public void copy(int searchAnythingId);

	public void skip(int searchAnythingId);

	public List<SearchAnythingTest> searchContensDisplayTest(SearchAnythingTest searchAnything);

	public List<SearchAnythingTest> getJsondata();

	public List<PersonInfoDetail> getJsonPersonInfo();
}
