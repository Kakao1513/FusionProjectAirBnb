package persistence.dao;

import persistence.dto.BoardDTO;
import persistence.PooledDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private final DataSource ds = PooledDataSource.getDataSource();
	public List<BoardDTO> findAll() {
		String sql = "select * from Board";

		List<BoardDTO> boardDTOs = new ArrayList<>();
		try {
			String url = "jdbc:mysql://localhost/myDB";
				//conn = DriverManager.getConnection(url, "root", "mjmj0221");
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				Long id = rs.getLong("id");
				String title = rs.getNString("title");
				String writer = rs.getNString("writer");
				String contents = rs.getString("contents");
				LocalDateTime regdate = rs.getTimestamp("regdate").toLocalDateTime();
				int hit = rs.getInt(6);

				boardDTO.setId(id);
				boardDTO.setTitle(title);
				boardDTO.setWriter(writer);
				boardDTO.setRegDate(regdate);
				boardDTO.setContents(contents);
				boardDTO.setHit(hit);
				boardDTOs.add(boardDTO);
			}

		} catch (SQLException se) {
			do {
				System.out.println("SQL_STATE : " + se.getSQLState());
				System.out.println("ERROR CODE : " + se.getErrorCode());
				System.out.println("MESSAGE : " + se.getMessage());
				System.out.println();
				se = se.getNextException();
			} while (se != null);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return boardDTOs;
	}


}
