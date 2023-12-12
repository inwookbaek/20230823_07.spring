package com.lec.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import org.hibernate.sql.ast.spi.StandardSqlAstTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.lec.domain.Board;
import com.lec.domain.Member;
import com.lec.domain.PagingInfo;
import com.lec.service.BoardService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes({"member", "pagingInfo"})
public class BoardController {

	@Autowired
	BoardService boardService;
	
	public PagingInfo pagingInfo = new PagingInfo();	
	
	@Autowired
	Environment environment; 
	
	@Value("${path.upload}")
	public String uploadFolder;
	
	@Value("${path.download}")
	public String downloadFolder;
	
	@ModelAttribute("member")
	public Member getMember() {
		return new Member();
	}
	
	@RequestMapping("/getBoardList")
	public String getBoardList(Model model, 
		@RequestParam(defaultValue = "0") int curPage, 
		@RequestParam(defaultValue = "10") int rowSizePerPage, 
		@RequestParam(defaultValue = "title") String searchType, 
		@RequestParam(defaultValue = "") String searchWord) {
		
		Pageable pageable = PageRequest.of(curPage, rowSizePerPage, Sort.by(searchType).ascending());
		Page<Board> pagedResult = boardService.getBoardList(pageable, searchType, searchWord);
		
		int totalRowCount = pagedResult.getNumberOfElements(); 
		int totalPageCount = pagedResult.getTotalPages();
		int pageSize = pagingInfo.getPageSize();
		int startPage = curPage / pageSize * pageSize + 1;
		int endPage = startPage + pageSize - 1;
		endPage = endPage > totalPageCount ? (totalPageCount > 0 ? totalPageCount : 1) : endPage;
		
		pagingInfo.setCurPage(curPage);
		pagingInfo.setTotalRowCount(totalRowCount);
		pagingInfo.setTotalPageCount(totalPageCount);
		pagingInfo.setStartPage(startPage);
		pagingInfo.setEndPage(endPage);
		pagingInfo.setSearchType(searchType);
		pagingInfo.setSearchWord(searchWord);
		pagingInfo.setRowSizePerPage(rowSizePerPage);		
		
		model.addAttribute("pagingInfo", pagingInfo);
		model.addAttribute("pagedResult", pagedResult);
		model.addAttribute("pageable", pageable);
		
		model.addAttribute("cp", curPage);
		model.addAttribute("sp", startPage);
		model.addAttribute("ep", endPage);
		model.addAttribute("ps", pageSize);
		model.addAttribute("rp", rowSizePerPage);
		model.addAttribute("tp", totalPageCount);
		model.addAttribute("st", searchType);
		model.addAttribute("sw", searchWord);
		
		return "board/getBoardList";
	}
	
	@GetMapping("/insertBoard")
	public String insertBoardForm(@ModelAttribute("member") Member member) { //  @ModelAttribute("member") = session.getAttribute("member")
		if(member.getId() == null) {
			return "redirect:login";
		} else {
			return "board/insertBoard";
		}
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(@ModelAttribute("member") Member member, Board board) throws IOException { 
		if(member.getId() == null) {
			return "redirect:login";
		}
		
		// 파일업로드
		MultipartFile uploadFile = board.getUploadFile();
		if(!uploadFile.isEmpty()) {
			
			
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File(uploadFolder + fileName));
			board.setFileName(fileName);
		}
		
		boardService.insertBoard(board);
		return "redirect:getBoardList";
	}	
	
	@GetMapping("/getBoard")
	public String getBoard(@ModelAttribute("member") Member member, Board board, Model model) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		
		boardService.updateReadCount(board);
		model.addAttribute("board", boardService.getBoard(board));	
		
		return "board/getBoard";
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(@ModelAttribute("member") Member member, Board board) {
		if(member.getId() == null) {
			return "redirect:login";
		}
		
		boardService.updateBoard(board);
		return "forward:getBoardList";
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(@ModelAttribute("member") Member member, Board board) {
		
		if(member.getId() == null) return "redirect:login";
		boardService.deleteBoard(board);
		return "forward:getBoardList";
	}
	
	@RequestMapping("/download")
	public void download(HttpServletRequest req, HttpServletResponse res) throws Exception { 	
		req.setCharacterEncoding("utf-8");
		String fileName = req.getParameter("fn");
		
		String fromPath = uploadFolder + fileName;
		String toPath = uploadFolder + fileName;
	
		byte[] b = new byte[4096];
		File f = new File(toPath);
		FileInputStream fis = new FileInputStream(fromPath);
		
		String sMimeType = req.getSession().getServletContext().getMimeType(fromPath); // mimetype = file type : pdf, exe, txt.... 
		if(sMimeType == null) sMimeType = "application/octet-stream";
		
		String sEncoding = new String(fileName.getBytes("utf-8"), "8859_1");
		String sEncoding1 = URLEncoder.encode(fileName, "utf-8");
		
		res.setContentType(sMimeType);
		res.setHeader("Content-Transfer-Encoding", "binary");
		res.setHeader("Content-Disposition", "attachment; filename = " + sEncoding1);
			
		int numRead;
		ServletOutputStream os = res.getOutputStream();
	
		while((numRead=fis.read(b, 0, b.length)) != -1 ) {
			os.write(b, 0, numRead);
		}
		
		os.flush();
		os.close();
		fis.close();
		
	}
}








