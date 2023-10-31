package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.BoardDTO;
import persistence.dto.InsertBoardDTO;
import persistence.mapper.BoardMapper;

import java.util.List;
import java.util.Map;

public class MyBoardDAO {
	private final SqlSessionFactory sqlSessionFactory;

	public MyBoardDAO(SqlSessionFactory sqlSessionFactory) { //DI
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public List<BoardDTO> selectAll() {
		List<BoardDTO> list = null;
		SqlSession session = sqlSessionFactory.openSession(); //ctrl + alt + v
		try {
			list = session.selectList("mapper.BoardMapper.selectAll");
		} finally {
			session.close();
		}
		return list;
	}

	public int insertPost(InsertBoardDTO insertBoardDTO) {
		SqlSession session = sqlSessionFactory.openSession();
		int affectedRows = 0;
		try {
			affectedRows = session.insert("mapper.BoardMapper.insertBoard", insertBoardDTO);
			session.commit();
		} finally {
			session.close();
		}
		return affectedRows;
	}

	public List<BoardDTO> findPostWithTitleLike(Map<String, Object> params) {

		List<BoardDTO> list = null;
		SqlSession session = sqlSessionFactory.openSession(); //ctrl + alt + v
		try {
			list = session.selectList("mapper.BoardMapper.findPostWithTitleLike", params);
		} finally {
			session.close();
		}
		return list;
	}

	public List<BoardDTO> selectAllWithAnnotation() {
		SqlSession session = sqlSessionFactory.openSession(); //ctrl + alt + v
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		List<BoardDTO> all = mapper.getAll();
		return all;
	}
	public BoardDTO selectOneWithAnnotation(Long id){
		SqlSession session = sqlSessionFactory.openSession(); //ctrl + alt + v
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		BoardDTO boardDTO = mapper.selectById(id);
		return boardDTO;
	}

	public List<BoardDTO> selectRecentWithAnnotation(int day){
		SqlSession session = sqlSessionFactory.openSession(); //ctrl + alt + v
		BoardMapper mapper = session.getMapper(BoardMapper.class);
		List<BoardDTO> boardDTOS = mapper.selectRecentPost(day);
		return boardDTOS;
	}
}
