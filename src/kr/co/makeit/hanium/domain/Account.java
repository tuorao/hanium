package kr.co.makeit.hanium.domain;

import java.io.Serializable;


public class Account implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	int memSrl;
	int memPush;
	int memDel;

	String memId;
	String pass;
	String memName;
	String memNickName;
	String gender;
	String memPic;
	String memProfile;
	String memBirth;
	double memLon;
	double memLat;

	public int getMemPush() {
		return memPush;
	}

	public void setMemPush(int memPush) {
		this.memPush = memPush;
	}

	public int getMemDel() {
		return memDel;
	}

	public void setMemDel(int memDel) {
		this.memDel = memDel;
	}

	public int getMemSrl() {
		return memSrl;
	}

	public void setMemSrl(int memSrl) {
		this.memSrl = memSrl;
	}

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public String getMemNickName() {
		return memNickName;
	}

	public void setMemNickName(String memNickName) {
		this.memNickName = memNickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMemPic() {
		return memPic;
	}

	public void setMemPic(String memPic) {
		this.memPic = memPic;
	}

	public String getMemProfile() {
		return memProfile;
	}

	public void setMemProfile(String memProfile) {
		this.memProfile = memProfile;
	}

	public String getMemBirth() {
		return memBirth;
	}

	public void setMemBirth(String memBirth) {
		this.memBirth = memBirth;
	}

	public Double getMemLon() {
		return memLon;
	}

	public void setMemLon(Double memLon) {
		this.memLon = memLon;
	}

	public Double getMemLat() {
		return memLat;
	}

	public void setMemLat(Double memLat) {
		this.memLat = memLat;
	}

}
