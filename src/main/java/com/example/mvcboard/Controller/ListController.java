/**
 * 목록에 대한 서블릿 클래스
 * */

package com.example.mvcboard.Controller;

import com.example.mvcboard.MVCBoardDAO;
import com.example.mvcboard.MVCBoardDTO;
import com.example.mvcboard.paging.Criteria;
import com.example.mvcboard.paging.PageMaker;
import com.example.mvcboard.utils.BoardPage;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/mvcboard/list.do")
public class ListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // DAO 생성
        MVCBoardDAO dao = new MVCBoardDAO();

        // 뷰에 전달할 매개변수 저장용 맵 생성
        Map<String, Object> map = new HashMap<String, Object>();
        String searchField = req.getParameter("searchField");
        String searchWord = req.getParameter("searchWord");

        if (searchWord != null && !searchWord.trim().equals("")) {
            // 쿼리스트링으로 전달받은 매개변수 중 검색어가 있다면 map에 저장
            map.put("searchField", searchField);
            map.put("searchWord", searchWord);
        }

        int totalCount = dao.selectCount(map);  // 게시물 개수

        /* 페이지 처리문 수정 [직전 커밋 : 90a0f1e] */
//        String pageNum = req.getParameter("pageNum");
//        Criteria criteria = new Criteria();
//        int pageNumInt = 1;
//        if (pageNum != null && !pageNum.equals("")) {
//            try {
//                pageNumInt = Integer.parseInt(pageNum.trim());
//            } catch (Exception e) {
//                System.out.println("숫자로 변환할 수 없는 pageNum");
//                // default로 1을 준다.
//            }
//        }
//        criteria.setPageNum(pageNumInt);
//
//        map.put("pageNum", (criteria.getPageNum() - 1) * 10);
//        List<MVCBoardDTO> boardLists = dao.selectListPageWithPaging(map);
//
//        System.out.println("boardLists is null? = " + boardLists);
//        System.out.println("boardLists.size() = " + boardLists != null ? boardLists.size() : "null이기 때문에 size X");
//        /*
//         * boardLists is null? = []
//         * size => 0
//         * */
//
//        PageMaker pageMaker = new PageMaker(criteria, totalCount);
//        req.setAttribute("pageMaker", pageMaker);
//        req.setAttribute("boardLists", boardLists);
//        map.remove("pageNum");
//        req.setAttribute("map", map);
//        req.getRequestDispatcher("/MVCBoard/List.jsp").forward(req, resp);

        /* 페이지 처리문 (BoardPage 사용) */
        ServletContext application = getServletContext();
        int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
        int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));

        // 현재 페이지 확인
        int pageNum = 1;  // 기본값
        String pageTemp = req.getParameter("pageNum");
        if (pageTemp != null && !pageTemp.equals(""))
            pageNum = Integer.parseInt(pageTemp); // 요청받은 페이지로 수정

        // 목록에 출력할 게시물 범위 계산
        int start = (pageNum - 1) * pageSize + 1;  // 첫 게시물 번호
        int end = pageNum * pageSize; // 마지막 게시물 번호
        map.put("start", start);
        map.put("end", end);
        /* 페이지 처리 end */

        List<MVCBoardDTO> boardLists = dao.selectListPage(map);  // 게시물 목록 받기
//        dao.close(); // DB 연결 닫기

        // 뷰에 전달할 매개변수 추가
        String pagingImg = BoardPage.pagingStr(totalCount, pageSize,
                blockPage, pageNum, searchField, searchWord, "../mvcboard/list.do");  // 바로가기 영역 HTML 문자열
        map.put("pagingImg", pagingImg);
        map.put("totalCount", totalCount);
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);

        // 전달할 데이터를 request 영역에 저장 후 List.jsp로 포워드
        req.setAttribute("boardLists", boardLists);
        req.setAttribute("map", map);
        req.getRequestDispatcher("/MVCBoard/List.jsp").forward(req, resp);
    }  // doGet()
}  // class
