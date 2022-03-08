package service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import dto.BoardDTO;
import dto.ReplyDTO;
import sqlmap.SqlSessionManager;

//db 기능을 실제로 구현하는 클래스
public class BoardServiceImpl implements BoardService{
	//쿼리 실행하는 객체
	SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSession();
	SqlSession sqlSession = sqlSessionFactory.openSession();
	
	
	@Override
	public List<BoardDTO> selectBoardList(BoardDTO boardDTO) {
		List<BoardDTO> list = sqlSession.selectList("boardMapper.selectBoardList", boardDTO);
		sqlSession.commit();
		return list;
	}


	@Override
	public void insertBoard(BoardDTO boardDTO) {
		sqlSession.insert("boardMapper.insertBoard", boardDTO);
		sqlSession.commit();
		
	}


	@Override
	public BoardDTO selectBoardDetail(int boardNum) {
		BoardDTO result = sqlSession.selectOne("boardMapper.selectBoardDetail", boardNum);
		sqlSession.commit();
		return result;
	}


	@Override
	public void updateReadCnt(int boardNum) {
		sqlSession.update("boardMapper.updateReadCnt", boardNum);
		sqlSession.commit(); 
	}


	@Override
	public void insertReply(ReplyDTO replyDTO) {
		sqlSession.insert("boardMapper.insertReply", replyDTO);
		sqlSession.commit();
	}


	@Override
	public List<ReplyDTO> selectReplyList(int boardNum) {
		List<ReplyDTO> list = sqlSession.selectList("boardMapper.selectReplyList", boardNum);
		sqlSession.commit();
		return list;
	}


	@Override
	public void deleteReply(int replyNum) {
		sqlSession.delete("boardMapper.deleteReply", replyNum);
		sqlSession.commit();
	}

	//댓글 삭제 + 게시글 삭제
	@Override
	public void deleteBoard(int boardNum) {
		
		try {
			sqlSession.delete("boardMapper.deleteReplyForDeleteBoard", boardNum);
			sqlSession.delete("boardMapper.deleteBoard", boardNum);
			sqlSession.commit();
		}catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		}
		
	}


	@Override
	public int selectBoardListCnt(BoardDTO boardDTO) {
		int cnt = sqlSession.selectOne("boardMapper.selectBoardListCnt", boardDTO);
		sqlSession.commit();
		return cnt;
	}


	
}








