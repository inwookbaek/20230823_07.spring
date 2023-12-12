package com.lec.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingInfo {

	private int curPage = 1;           // 현재 페이지 번호
	private int rowSizePerPage = 10;   // 한페이지당 레코드수 기본  = 10
	private int pageSize = 10;         // 페이지리스트에 보여줄 페이지 갯수 보통은 10 or 5페이지
	private int totalRowCount;         // 총 레코드 건수
	private int firstRow;              // 시작 레코드 번호
	private int lastRow;               // 마지막 레코드 번호
	private int totalPageCount;        // 총 페이지 수
	private int startPage;             // 페이지리스트에서 시작 페이지 번호
	private int endPage;               // 페이지리스트에서 마지막 페이지 번호
	private String searchWord;         // 검색단어
	private String searchType;         // 검색타입(제목별, 작성자별...)
	
	// 페이징로직
	public void pageSetting() {
		totalPageCount = (totalRowCount-1) / rowSizePerPage + 1;
		firstRow = (curPage - 1) * rowSizePerPage;
		lastRow = firstRow + rowSizePerPage;
		if(lastRow > totalRowCount) lastRow = totalRowCount;
		startPage = (curPage -1) / pageSize * pageSize + 1;
		endPage = startPage + pageSize - 1;
		if(endPage > totalPageCount) endPage = totalPageCount;
	}
}
