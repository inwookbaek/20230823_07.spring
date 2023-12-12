package com.lec.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lec.domain.Board;


public interface BoardRepository extends CrudRepository<Board, Long> {

	// 클래스, 메서드 모두에 @Transactional 애너테이션을 선언하면 
	// 메서드레벨의 @Transactional선언이 우선 적용이 된다.
	// @Transactional으로 선언된 메서드는 메서드가 포함된 작업중에 하나라도 실패할 경우
	// 전체 작업이 취소(rollback)된다
	// @Modifying 애너테이션은 @Query 애너테이션에서 작성된 쿼리에서 조회를 제외한
	// 데이터의 변경(insert, delete, update)이 있는 쿼리를 사용할 경우에 선언해야 한다.
	@Modifying      // @Query의 sql이 insert/delete/update 
	@Transactional  // commit, rollback
	@Query("update Board b set b.cnt = b.cnt + 1 where b.seq = :seq")
	int updateReadCount(@Param("seq") Long seq);
	
	@Modifying     
	@Transactional 
	@Query("update Board b set b.board_ref = b.seq, b.board_lev=:lev, b.board_seq=:_seq where b.seq = :seq")
	void updateLastSeq(@Param("lev") Long i, @Param("_seq") Long j, @Param("seq") Long seq);
	
	Page<Board> findByTitleContaining(String title, Pageable pageable);
	Page<Board> findByWriterContaining(String writer, Pageable pageable);
	Page<Board> findByContentContaining(String content, Pageable pageable);
	
	
}
