package kr.co.makeit.hanium.domain;

/**
 * 댓글 도메인 클래스 int bSrl 게시글 번호 int memSrl 작성자 번호 String bName 게시글 제목 String bBody
 * 게시글 내용 int bAllow 공개범위 int bDel 삭제여부 int agree 가입 신청글 일경우 승인여부
 * 
 * @since 2014. 7. 24.
 * @version 1.0
 * @author 이병주
 * 
 *         <pre>
 * @  수정일         수정자              수정내용
 * @ ---------    ---------   -------------------------------
 * @ 2014. 7. 24.   이병주			 최초 생성
 * </pre>
 * 
 *         Copyright (C) by Make-IT All right reserved.
 */
public class Comment {
	private int cSrl; // 코멘트 번호
	private int bSrl; // 코멘트가 달린 게시글 번호
	private int memSrl; // 작성한 사람의 번호
	private String cBody; // 코멘트 내용
	private String cBirth; // 코멘트작성 일시
	private int cDel; // 코멘트 삭제 여부
	public int getcSrl() {
		return cSrl;
	}
	public void setcSrl(int cSrl) {
		this.cSrl = cSrl;
	}
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
	public String getcBody() {
		return cBody;
	}
	public void setcBody(String cBody) {
		this.cBody = cBody;
	}
	public String getcBirth() {
		return cBirth;
	}
	public void setcBirth(String cBirth) {
		this.cBirth = cBirth;
	}
	public int getcDel() {
		return cDel;
	}
	public void setcDel(int cDel) {
		this.cDel = cDel;
	}
}
