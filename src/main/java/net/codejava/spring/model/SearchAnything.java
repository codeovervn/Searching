package net.codejava.spring.model;

/**
 * @Author Sakekun
 * @CreateDate 2018/08/18 10:50:23
 * */
public class SearchAnything {

	private int ID;
	private String SEARCH_KEY;
	private String SEARCH_CONTENTS;
	private int SEARCH_NUM;
	private String DATE_CREATE_SEARCH;
	private String DATE_UPDATE_SEARCH;
	private int DEL_FLG;

	public SearchAnything() {
	}

	public SearchAnything(int id, String searchKey, String searchContents, int searchNum, String dateCreateSearch, String dateUpdateSearch, int delFlg) {
		this.ID = id;
		this.SEARCH_KEY = searchKey;
		this.SEARCH_CONTENTS = searchContents;
		this.SEARCH_NUM = searchNum;
		this.DATE_CREATE_SEARCH = dateCreateSearch;
		this.DATE_UPDATE_SEARCH = dateUpdateSearch;
		this.DEL_FLG = delFlg;
	}

	/** Auto gen SETTER/GETTER method */
	public void setId(int id) {
		this.ID = id;
	}

	public void setSearchKey(String searchKey) {
		this.SEARCH_KEY = searchKey;
	}

	public void setSearchContents(String searchContents) {
		this.SEARCH_CONTENTS = searchContents;
	}

	public void setSearchNum(int searchNum) {
		this.SEARCH_NUM = searchNum;
	}

	public void setDateCreateSearch(String dateCreateSearch) {
		this.DATE_CREATE_SEARCH = dateCreateSearch;
	}

	public void setDateUpdateSearch(String dateUpdateSearch) {
		this.DATE_UPDATE_SEARCH = dateUpdateSearch;
	}

	public void setDelFlg(int delFlg) {
		this.DEL_FLG = delFlg;
	}

	public int getId() {
		return ID;
	}

	public String getSearchKey() {
		return SEARCH_KEY;
	}

	public String getSearchContents() {
		return SEARCH_CONTENTS;
	}

	public int getSearchNum() {
		return SEARCH_NUM;
	}

	public String getDateCreateSearch() {
		return DATE_CREATE_SEARCH;
	}

	public String getDateUpdateSearch() {
		return DATE_UPDATE_SEARCH;
	}

	public int getDelFlg() {
		return DEL_FLG;
	}
}
