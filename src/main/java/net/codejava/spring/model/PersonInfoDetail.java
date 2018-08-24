package net.codejava.spring.model;

/**
 * @Author Sakekun
 * @CreateDate 2018/08/18 10:50:23
 * */
public class PersonInfoDetail {

	private int ID;
	private String NAME;
	private String EMAIL;
	private String BIRTHDAY;
	private int YEAR_OLD;
	private String JOB;
	private String COUNTRY;
	private String REMARKS;

	public PersonInfoDetail() {
	}

	public PersonInfoDetail(int id, String name, String email, String birthday, int yearOld, String job, String country, String remarks) {
		this.ID = id;
		this.NAME = name;
		this.EMAIL = email;
		this.BIRTHDAY = birthday;
		this.YEAR_OLD = yearOld;
		this.JOB = job;
		this.COUNTRY = country;
		this.REMARKS = remarks;
	}

	/** Auto gen SETTER/GETTER method */
	public int getID() {
		return ID;
	}

	public String getNAME() {
		return NAME;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public String getBIRTHDAY() {
		return BIRTHDAY;
	}

	public int getYEAR_OLD() {
		return YEAR_OLD;
	}

	public String getJOB() {
		return JOB;
	}

	public String getCOUNTRY() {
		return COUNTRY;
	}

	public String getREMARKS() {
		return REMARKS;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public void setBIRTHDAY(String bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}

	public void setYEAR_OLD(int yEAR_OLD) {
		YEAR_OLD = yEAR_OLD;
	}

	public void setJOB(String jOB) {
		JOB = jOB;
	}

	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}

	public void setREMARKS(String rEMARKS) {
		REMARKS = rEMARKS;
	}
}
