package com.itbank.model;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

public class ReviewDTO {

	private int idx;
	private int member_idx;
	private int board_idx;
	private String re_content;
	private Date re_wdate;
	// 추후에 사용
	private String re_likeConunt;
	private String re_fileName;
	private MultipartFile re_upload;
	
	private String member_userid;
	private String member_fileName;
	

	public final int getIdx() {
		return idx;
	}
	public final void setIdx(int idx) {
		this.idx = idx;
	}
	public final int getMember_idx() {
		return member_idx;
	}
	public final void setMember_idx(int member_idx) {
		this.member_idx = member_idx;
	}
	public final int getBoard_idx() {
		return board_idx;
	}
	public final void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	public final String getRe_content() {
		return re_content;
	}
	public final void setRe_content(String re_content) {
		this.re_content = re_content;
	}
	public final Date getRe_wdate() {
		return re_wdate;
	}
	public final void setRe_wdate(Date re_wdate) {
		this.re_wdate = re_wdate;
	}
	public final String getRe_likeConunt() {
		return re_likeConunt;
	}
	public final void setRe_likeConunt(String re_likeConunt) {
		this.re_likeConunt = re_likeConunt;
	}
	public final String getRe_fileName() {
		return re_fileName;
	}
	public final void setRe_fileName(String re_fileName) {
		this.re_fileName = re_fileName;
	}
	public final MultipartFile getRe_upload() {
		return re_upload;
	}
	public final void setRe_upload(MultipartFile re_upload) {
		this.re_upload = re_upload;
	}
	public final String getMember_userid() {
		return member_userid;
	}
	public final void setMember_userid(String member_userid) {
		this.member_userid = member_userid;
	}
	public final String getMember_fileName() {
		return member_fileName;
	}
	public final void setMember_fileName(String member_fileName) {
		this.member_fileName = member_fileName;
	}
	
}
