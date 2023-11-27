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
 * 9. updatePost() : 게시글을 수정하는 메서드
 * */

package com.example.mvcboard;

import com.example.mvcboard.mybatis.factory.MyBatisSessionFactory;
import com.example.mvcboard.mybatis.mapper.MVCBoardMapper;
//import common.DBConnPool;
import com.example.mvcboard.utils.Encrypt;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MVCBoardDAO/* extends DBConnPool*/ {  // 커넥션 풀 상속
    public MVCBoardDAO() {
        super();
    }

    /* R(Read) - 목록 읽기 기능 구현 */
    // 검색 조건에 맞는 게시물의 개수를 반환한다
    public int selectCount(Map<String, Object> map) {

        SqlSession session = MyBatisSessionFactory.getSqlSession();
        MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);

        int result = mapper.selectCount(map);

        System.out.println("selectCount - 행 개수 = " + result);
        session.close();

        return result;

    }

    // 검색 조건에 맞는 게시물 목록을 반환한다 (페이징)
    public List<MVCBoardDTO> selectListPage(Map<String, Object> map) {

        SqlSession session = MyBatisSessionFactory.getSqlSession();
        MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);

        List<MVCBoardDTO> result = mapper.selectListPage(map);

        session.close();
        return result;

    }

    /* W(Write) - 글쓰기 기능 구현; 글쓰기 처리 메서드 추가 */
    // 게시글 데이터를 받아 DB에 추가한다 (파일 업로드 기능 포함)
    public int insertWrite(MVCBoardDTO dto) {
        /* mybatis로 구현 [직전 커밋: 43c3681]*/
        SqlSession sqlSession = MyBatisSessionFactory.getSqlSession();
        MVCBoardMapper mapper = sqlSession.getMapper(MVCBoardMapper.class);
        int result = mapper.insertWrite(dto);

        if (result == 1) {
            sqlSession.commit();
            System.out.println("새 게시물 저장 성공");

        } else {
            System.out.println("새 게시물 저장 실패");
        }

        sqlSession.close();
        return result;

    }

    /* R(Read) : 주어진 일련번호에 해당하는 게시물을 DTO로 반환한다. */
    // 일련번호로 게시물 조회
    public MVCBoardDTO selectView(String idx) {

        SqlSession session = MyBatisSessionFactory.getSqlSession();
        MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);

        MVCBoardDTO result = mapper.selectView(idx);

        session.close();
        return result;

    }  // selectView()

    // 주어진 일련번호에 해당하는 게시물의 조회수를 1 증가시킨다.
    public void updateVisitCount(String idx) {

        SqlSession session = MyBatisSessionFactory.getSqlSession();
        MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);

        int result = mapper.updateVisitCount(idx);

        if (result == 1) {
            session.commit();

        } else {
            System.out.println("조회수 증가 중 오류 발생");
        }

        session.close();

    }  // updateVisitCount()

    /* Nov 19. 2023. 15:31 추가 : 다운로드 횟수를 증가시키는 메서드
    * [다운로드] 링크 클릭 시 전달되는 일련번호를 사용하여 업데이트
    *  */
    public void downCountPlus(String idx) {

        SqlSession session = MyBatisSessionFactory.getSqlSession();
        MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);

        int result = mapper.downCountPlus(idx);

        if (result == 1) {
            session.commit();

        } else {
            System.out.println("다운로드 횟수 증가 중 오류 발생");
        }

        session.close();

    }  // downCountPlus()
    /* Nov 19. 2023. 15:35 추가 완료 */

    /* Nov 19. 2023. 22:35 추가 : 비밀번호 확인 및 게시물 삭제 메서드
    * salt 방식의 암호화 기법을 추가 [직전 커밋 : 146c0f3] */
    // 입력한 비밀번호가 지정한 일련번호의 게시물의 비밀번호와 일치하는지 확인한다.
    public boolean confirmPassword(String pass, String idx) {

        Map<String, String> map = new HashMap<>();
        map.put("pass", Encrypt.getEncrypt(pass));
        map.put("idx", idx);

        SqlSession session = MyBatisSessionFactory.getSqlSession();
        MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);

        int result = mapper.confirmPassword(map);

        if (result == 1) {
            return true;

        } else {
            return false;
        }

    }  // confirmPassword()

    // 지정한 일련번호의 게시물을 삭제한다.
    public int deletePost(String idx) {

        SqlSession sqlSession = MyBatisSessionFactory.getSqlSession();
        MVCBoardMapper mapper = sqlSession.getMapper(MVCBoardMapper.class);
        int result = mapper.deletePost(idx);

        if (result == 1) {
            sqlSession.commit();
            System.out.println("게시물 삭제 성공");

        } else {
            System.out.println("게시물 삭제 실패");
        }

        sqlSession.close();
        return result;

    }  // deletePost()
    /* Nov 19. 2023. 22:46 추가 완료 */

    /* 수정하기 메서드 추가 [직전 커밋: 1bdc05a] */
    // 게시글 데이터를 받아 DB에 저장되어 있던 내용을 갱신한다 (파일 업로드 o)
    public int updatePost(MVCBoardDTO dto) {  // 수정된 내용을 담은 DTO 객체를 매개변수로 받는다.

        SqlSession session = MyBatisSessionFactory.getSqlSession();
        MVCBoardMapper mapper = session.getMapper(MVCBoardMapper.class);

        int result = mapper.updatePost(dto);

        if (result == 1) {
            session.commit();

        } else {
            System.out.println("게시물 수정 증가 중 오류 발생");
        }

        session.commit();
        return result;

    }  // updatePost()
}  // class
