/**
 * mvcboard 테이블의 데이터에 관해 기능을 수행
 * 해당 파일 내에서 정의하는 메소드
 * 1. selectCount() : 목록 읽기 기능; 검색 조건에 맞는 게시물의 개수를 반환
 * 2. selectListPage() : 목록 읽기 기능; 페이징 기능
 * 3. insertWrite() : 글쓰기 기능
 * 4. selectView() : 상세 보기 기능 - 주어진 일련번호에 해당하는 게시물을 DTO로 반환
 * 5. updateVisitCount() : 상세 보기 기능 - 조회수를 증가시킨다.
 * 6. downCountPlus() : 다운로드 횟수를 증가시키는 메서드
 * 7. confirmPassword() : 삭제 시 비밀번호를 확인하는 메서드
 * 8. deletePost() : 지정한 일련번호의 게시물을 삭제하는 메서드
 * */

package mvcboard;

import common.DBConnPool;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MVCBoardDAO extends DBConnPool {  // 커넥션 풀 상속
    public MVCBoardDAO() {
        super();
    }

    /* R(Read) - 목록 읽기 기능 구현 */
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
        List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();  // 결과(게시물 목록)를 담을 변수

        // 쿼리문 준비
        String query = " "
                + "SELECT * FROM ( "
                + " SELECT @ROWNUM := @ROWNUM + 1 AS ROWNUM, b.* "
                + " FROM  mvcboard b,(SELECT @ROWNUM := 0 ) TMP ";

        // 검색 조건이 있다면 WHERE절로 추가
        if (map.get("searchWord") != null) {
            query += " WHERE " + map.get("searchField")
                    + " LIKE '%" + map.get("searchWord") + "%' ";
        }

        query += " ORDER BY idx DESC ) T " +
                " WHERE ROWNUM BETWEEN ? AND ? ;";  // 게시물 구간은 인파라미터로 설정

        try {
            // 쿼리문 완성
            psmt = con.prepareStatement(query);  // 동적 쿼리문 생성
            psmt.setString(1, map.get("start").toString());  // 인파라미터 설정
            psmt.setString(2, map.get("end").toString());
            rs = psmt.executeQuery();  // 쿼리문 실행

            // 반환된 게시물 목록을 List 컬렉션에 추가
            while (rs.next()) {
                // 한 행(게시물 하나)의 데이터를 DTO에 저장
                MVCBoardDTO dto = new MVCBoardDTO();

                dto.setIdx(rs.getString("idx"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getDate("postdate"));
                dto.setOfile(rs.getString("ofile"));
                dto.setSfile(rs.getString("sfile"));
                dto.setDowncount(rs.getInt("downcount"));
                dto.setPass(rs.getString("pass"));
                dto.setVisitcount(rs.getInt("visitcount"));

                // 반환할 결과 목록에 게시물 추가
                board.add(dto);
            }

        } catch (Exception e) {
            System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
        }

        return board;  // 목록 반환
    }

    /* W(Write) - 글쓰기 기능 구현; 글쓰기 처리 메서드 추가 */
    // 게시글 데이터를 받아 DB에 추가한다 (파일 업로드 기능 포함)
    public int insertWrite(MVCBoardDTO dto) {
        // Write.jsp 에서 전송한 폼값을 서블릿이 받아 DTO에 저장 후 DAO로 전달한다.
        int result = 0;

        try {
            // INSERT 쿼리문 작성
            String query = "INSERT INTO mvcboard ( "
                    + " name, title, content, ofile, sfile, pass) "
                    + " VALUES ( "
                    + " ?, ?, ?, ?, ?, ?)";

            // PreparedStatement 객체 생성 및 인파라미터 설정
            psmt = con.prepareStatement(query);  // 쿼리문을 인수로 한다.
            psmt.setString(1, dto.getName());
            psmt.setString(2, dto.getTitle());
            psmt.setString(3, dto.getContent());
            psmt.setString(4, dto.getOfile());
            psmt.setString(5, dto.getSfile());
            psmt.setString(6, dto.getPass());

            result = psmt.executeUpdate();  // 쿼리문 실행

        } catch (Exception e) {
            System.out.println("게시물 입력 중 예외 발생");
            e.printStackTrace();
        }

        // 입력된 결과를 서블릿으로 반환
        return result;
    }

    /* R(Read) : 주어진 일련번호에 해당하는 게시물을 DTO로 반환한다. */
    // 일련번호로 게시물 조회
    public MVCBoardDTO selectView(String idx) {
        MVCBoardDTO dto = new MVCBoardDTO();  // DTO 객체 생성
        String query = "SELECT * FROM mvcboard WHERE idx=?";  // 쿼리문 템플릿 준비

        try {
            psmt = con.prepareStatement(query);  // 쿼리문 준비
            psmt.setString(1, idx);  // 인파라미터 설정
            rs = psmt.executeQuery();  // 쿼리문 실행

            // 결과를 DTO 객체에 저장
            if (rs.next()) {
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
            }

        } catch (Exception e) {
            System.out.println("게시물 상세보기 중 예외 발생");
            e.printStackTrace();
        }

        return dto;  // 결과 반환
    }  // selectView()

    // 주어진 일련번호에 해당하는 게시물의 조회수를 1 증가시킨다.
    public void updateVisitCount(String idx) {
        String query = "UPDATE mvcboard SET "
                + " visitcount=visitcount+1 "
                + " WHERE idx=?";

        try {
            psmt = con.prepareStatement(query);
            psmt.setString(1, idx);
            psmt.executeQuery();

        } catch (Exception e) {
            System.out.println("게시물 조회수 증가 중 예외 발생");
            e.printStackTrace();
        }

    }  // updateVisitCount()

    /* Nov 19. 2023. 15:31 추가 : 다운로드 횟수를 증가시키는 메서드
    * [다운로드] 링크 클릭 시 전달되는 일련번호를 사용하여 업데이트
    *  */
    public void downCountPlus(String idx) {
        // 일련번호를 인수로 받아 downcount를 1 증가시킨다.
        String sql = "UPDATE mvcboard SET "
                + " downcount=downcount+1 "
                + " WHERE idx=? ";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, idx);
            psmt.executeUpdate();

        } catch (Exception e) {}
    }  // downCountPlus()
    /* Nov 19. 2023. 15:35 추가 완료 */
    /* Nov 19. 2023. 22:35 추가 : 비밀번호 확인 및 게시물 삭제 메서드 */
    // 입력한 비밀번호가 지정한 일련번호의 게시물의 비밀번호와 일치하는지 확인한다.
    public boolean confirmPassword(String pass, String idx) {
        boolean isCorr = true;

        try {
            // 비밀번호와 일련번호가 일치하는 게시물의 개수를 세어 비밀번호의 일치 여부를 확인한다.
            String sql = "SELECT COUNT(*) FROM mvcboard WHERE pass=? AND idx=?";
            psmt = con.prepareStatement(sql);
            psmt.setString(1, pass);
            psmt.setString(2, idx);

            rs = psmt.executeQuery();
            rs.next();

            // 일치하는 게시물이 없다면 false를 반환
            if (rs.getInt(1) == 0) {
                isCorr = false;
            }

        } catch (Exception e) {
            isCorr = false;  // 예외가 발생하면 false를 반환
            e.printStackTrace();
        }

        return isCorr;
    }  // confirmPassword()

    // 지정한 일련번호의 게시물을 삭제한다.
    public int deletePost(String idx) {
        int result = 0;

        try {
            String query = "DELETE FROM mvcboard WHERE idx=?";
            psmt = con.prepareStatement(query);
            psmt.setString(1, idx);
            result = psmt.executeUpdate();  // 게시물이 정상적으로 삭제되었다면 1을 반환

        } catch (Exception e) {
            System.out.println("게시물 삭제 중 예외 발생");
            e.printStackTrace();
        }

        return result;
    }
    /* Nov 19. 2023. 22:46 추가 완료 */


}  // class
