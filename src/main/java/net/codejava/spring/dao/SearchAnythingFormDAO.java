package net.codejava.spring.dao;

import java.util.List;

import net.codejava.spring.model.SearchAnything;
import net.codejava.spring.model.SearchAnythingForm;

/**
 * Defines DAO operations for the contact model.
 * @author DungLT
 *
 */
public interface SearchAnythingFormDAO {

	public void saveOrUpdate(SearchAnythingForm searchAnythingForm);

	public SearchAnythingForm get(int searchAnythingId);

	/*public void delete(int searchAnythingId);

	public void revert(int searchAnythingId);

	public List<SearchAnythingForm> list();

	public List<SearchAnythingForm> listBk();

	public void copy(int searchAnythingId);

	public void skip(int searchAnythingId);*/
}
