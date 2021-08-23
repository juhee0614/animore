package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.BoardDTO;
import com.proj.animore.dto.BoardReqDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BoardDAOImpl implements BoardDAO {
	
	private final JdbcTemplate jt;
	
	//게시글등록
	@Override
	public void addBoard(String id,BoardDTO boardDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into board(bnum,bcategory,id,btitle,bcontent) ");
		sql.append("   values(board_bnum_seq.nextval,?,?,?,?) ");
		jt.update(sql.toString(),
					boardDTO.getBcategory(),
					id,
					boardDTO.getBtitle(),
					boardDTO.getBcontent());
		
		log.info("boardDTO:{}",boardDTO.toString());
		
	}
	//게시글조회
	@Override
	public BoardReqDTO findBoardByBnum(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.bcategory,b.btitle,b.id,m.nickname,b.bcdate,b.bhit,b.bgood,b.breply,b.bcontent,b.bnum ");
		sql.append("      from board b, member m ");
		sql.append("     where b.id =m.id ");
		sql.append("         and b.bnum=? ");
		
		BoardReqDTO boardReqDTO = 
				jt.queryForObject(sql.toString(), 
				new BeanPropertyRowMapper<>(BoardReqDTO.class),
											bnum);
		return boardReqDTO;
	}
	//게시글검색(by id)
	@Override
	public List<BoardReqDTO> findBoardById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select bcategory,btitle,id,bcdate,bhit,bgood,breply,bcontent ");
		sql.append("from board ");
		sql.append("where id=? ");
		
		List<BoardReqDTO> list = jt.query(sql.toString(), 
												new BeanPropertyRowMapper<>(BoardReqDTO.class),
												id);
		return list;
		
	}
	//게시글검색(by btitle)
	@Override
	public List<BoardReqDTO> findBoardByBtitle(String btitle) {
		StringBuffer sql = new StringBuffer();
		sql.append("select bcategory,btitle,id,bcdate,bhit,bgood,breply,bcontent ");
		sql.append("from board ");
		sql.append("where btitle like '%?%'; ");
		
		List<BoardReqDTO> list = jt.query(sql.toString(), 
				new BeanPropertyRowMapper<>(BoardReqDTO.class),
				btitle);
		return list;

	}
	//게시글검색(by bcontent)
	@Override
	public List<BoardReqDTO> findBoardByBcontent(String bcontent) {

		StringBuffer sql = new StringBuffer();
		sql.append("select bcategory,btitle,id,bcdate,bhit,bgood,breply,bcontent ");
		sql.append("from board ");
		sql.append("where bcontent	 like '%?%'; ");
		
		List<BoardReqDTO> list = jt.query(sql.toString(), 
				new BeanPropertyRowMapper<>(BoardReqDTO.class),
				bcontent);
		return list;

	}
	//게시글수정
	@Override
	public BoardReqDTO modifyBoard(int bnum, BoardDTO boardDTO) {
		StringBuffer sql = new StringBuffer();
		sql.append("update board ");
		sql.append("set bcategory =?, ");
		sql.append("    btitle=?, ");
		sql.append("    budate= current_timestamp, ");
		sql.append("    bcontent =? ");
		sql.append("where bnum=? ");
		
		jt.update(sql.toString(),
								boardDTO.getBcategory(),
								boardDTO.getBtitle(),
								boardDTO.getBcontent(),
								bnum);
		return findBoardByBnum(bnum);
	}
	//게시글삭제
	@Override
	public void deleteBoard(int bnum) {
		String sql ="delete board where bnum=?";
		jt.update(sql.toString(),bnum);
		
	}
	//게시글전체목록(by bcategory)
	@Override
	public List<BoardReqDTO> list(String bcategory) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.bnum,b.bhit,b.bgood,b.btitle,b.id,m.nickname,b.bcdate,b.bcategory,b.breply,b.bcontent ");
		sql.append("  from board b, member m ");
		sql.append("  where b.id = m.id ");
		sql.append("   and bcategory=? ");
		List<BoardReqDTO> list = jt.query(sql.toString(),
										new BeanPropertyRowMapper<>(BoardReqDTO.class),
										bcategory);
		return list;
	}

}
