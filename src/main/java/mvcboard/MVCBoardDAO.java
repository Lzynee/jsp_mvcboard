package mvcboard;

import common.DBConnPool;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MVCBoardDAO extends DBConnPool {  // 커넥션 풀 상속
    public MVCBoardDAO() {
        super();
    }

    // 검색 조건에 맞는 게시물의 개수를 반환한다 (페이징 x)
    public int selectCount(Map<String, Object> map) {
        int totalCount = 0;

        // 쿼리문 준비
        String query = "SELECT COUNT(*) FROM mvcboard";

        // 검색 조건이 있다면 WHERE절로 추가
        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField") + " "
                    + " LIKE '%" + map.get("searchWord") + "%'";
        }

        try {
            stmt = con.createStatement();  // 쿼리문 생성
            rs = stmt.executeQuery(query);  // 쿼리문 실행
            rs.next();
            totalCount = rs.getInt(1);  // 검색된 게시물 개수 저장

        } catch (Exception e) {
            System.out.println("게시물 카운트 중 예외 발생");
            e.printStackTrace();
        }

        return totalCount;  // 게시물 개수를 서블릿으로 반환
    }


    // 검색 조건에 맞는 게시물 목록을 반환한다 (페이징)
    public List<MVCBoardDTO> selectListPage(Map<String, Object> map) {
        List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();

        // 쿼리문 준비
        String query = " "
                + "SELECT * FROM ( "
                + " SELECT @ROWNUM := @ROWNUM + 1 AS ROWNUM, b.* "
                + " FROM  board b,(SELECT @ROWNUM := 0 ) TMP ";

        // 검색 조건이 있다면 WHERE절로 추가
        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField")
                    + " LIKE '%" + map.get("searchWord") + "%' ";
        }

        query += " ORDER BY  num DESC ) T " +
                " WHERE ROWNUM BETWEEN ? AND ? ;";  // 게시물 구간은 인파라미터로 설정

        try {
            psmt = con.prepareStatement(query);  // 동적 쿼리문 생성
            psmt.setString(1, map.get("start").toString());  // 인파라미터 설정
            psmt.setString(2, map.get("end").toString());
            rs = psmt.executeQuery();  // 쿼리문 실행

            // 반환된 게시물 목록을 List 컬렉션에 추가
            while (rs.next()) {
                MVCBoardDTO dto = new MVCBoardDTO();

                dto.setIdx(rs.getString(1));
                dto.setName(rs.getString(2));
                dto.setTitle(rs.getString(3));
                dto.setContent(rs.getString(4));
                dto.setPostdate(rs.getDate(5));
                dto.setOfile(rs.getString(6));
                dto.setSfile(rs.getString(7));
                dto.setDowncount(rs.getInt(8));
                dto.setPass(rs.getString(9));
                dto.setVisitcount(rs.getInt(10));

                board.add(dto);
            }

        } catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }

        return board;  // 목록 반환
    }

}  // class
