package com.lec.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	
	private String title;
	
	@Column(updatable = false)
	private String writer;
	
	private String content;
	
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date createDate;
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")	
	private Long cnt;
	
	private String fileName;
	
	@Transient
	private MultipartFile uploadFile;
	
	// @Column(columnDefinition = "integer default 0", nullable = true)
	private Long board_ref;
	
	// @Column(columnDefinition = "integer default 0", nullable = true)
	private Long board_lev;
	
	// @Column(columnDefinition = "integer default 0", nullable = true)
	private Long board_seq;
}






















