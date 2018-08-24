package net.codejava.spring.dao;

import java.util.List;

import net.codejava.spring.model.SearchAnything;

/**
 * Defines DAO operations for the contact model.
 * @author Sakekun
 *
 */
public interface SearchAnythingDAO {

	public void saveOrUpdate(SearchAnything searchAnything);

	public void delete(int searchAnythingId);

	public void revert(int searchAnythingId);

	public SearchAnything get(int searchAnythingId);

	public List<SearchAnything> list();

	public List<SearchAnything> listBk();

	public void copy(int searchAnythingId);

	public void skip(int searchAnythingId);
}
