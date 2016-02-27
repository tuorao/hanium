package kr.co.makeit.hanium.domain;

import java.io.Serializable;

/**
 * 게시글 도메인 클래스
 * int bSrl		게시글 번호
 * int memSrl	작성자 번호
 * String bName 게시글 제목
 * String bBody 게시글 내용
 * int bAllow	공개범위
 * int bDel		삭제여부
 * int agree	가입 신청글 일경우 승인여부
 * 전현우는장애인
 * @since 2014. 7. 24.
 * @version 1.0
 * @author 이병주
 * <pre>
 * @  수정일         수정자              수정내용
 * @ ---------    ---------   -------------------------------
 * @ 2014. 7. 24.   이병주			 최초 생성
 * </pre>
 * Copyright (C) by Make-IT All right reserved.
 */
public class Board_domain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int bSrl;	//게시글 번호
	private int memSrl;	//작성자 번호
	private String bName;// 게시글 제목
	private String bBody;// 게시글 내용
	private int bAllow; // 공개범위
	private int bDel;	// 삭제여부
	private int agree;	// 가입 신청글 일경우 승인여부
	private int groupSrl;
	private String boardPic;
	
	public int getbSrl() {
		return bSrl;
	}
	public void setbSrl(int bSrl) {
		this.bSrl = bSrl;
	}
	public int getMemSrl() {
		return memSrl;
	}
	public void setMemSrl(int memSrl) {
		this.memSrl = memSrl;
	}
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getbBody() {
		return bBody;
	}
	public void setbBody(String bBody) {
		this.bBody = bBody;
	}
	public int getbAllow() {
		return bAllow;
	}
	public void setbAllow(int bAllow) {
		this.bAllow = bAllow;
	}
	public int getbDel() {
		return bDel;
	}
	public void setbDel(int bDel) {
		this.bDel = bDel;
	}
	public int getAgree() {
		return agree;
	}
	public void setAgree(int agree) {
		this.agree = agree;
	}
	public int getGroupSrl() {
		return groupSrl;
	}
	public void setGroupSrl(int groupSrl) {
		this.groupSrl = groupSrl;
	}
	public String getBoardPic() {
		return boardPic;
	}
	public void setBoardPic(String boardPic) {
		this.boardPic = boardPic;
	}
}
