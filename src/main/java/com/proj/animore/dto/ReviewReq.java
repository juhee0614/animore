package com.proj.animore.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReviewReq {

	private Integer rnum;
	private Integer bnum;
	private Integer rscore;
	private String rcontent;
	private String id;
	private String nickname;
	private LocalDate rvcdate;
	private LocalDate rvudate;
}
