package com.itbank.model;

/*
	create table board_like(
	    idx             number              default board_like_seq.nextval primary key,
	    member_userid   varchar2(100)       not null,
	    board_idx       number              not null,
	    blike_status    varchar2(10)        default 'false',
	    
	    constraint member_board_like_fk
	    foreign key (member_userid)
	    references member(userid),
	    
	    constraint board_board_like_fk
	    foreign key (board_idx)
	    references board(idx)
	);
 */

public class BoardLikeDTO {
	private int idx;
	private String member_userid;
	private int board_idx;
	private String blike_status;

	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getMember_userid() {
		return member_userid;
	}
	public void setMember_userid(String member_userid) {
		this.member_userid = member_userid;
	}
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	public String getBlike_status() {
		return blike_status;
	}
	public void setBlike_status(String blike_status) {
		this.blike_status = blike_status;
	}



}