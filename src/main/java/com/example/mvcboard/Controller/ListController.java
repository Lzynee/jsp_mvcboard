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
        /* 페이지 처리*/
        String pageNum = req.getParameter("pageNum");
        Criteria criteria = new Criteria();
        int pageNumInt = 1;
        if (pageNum != null && !pageNum.equals("")) {
            try {
                pageNumInt = Integer.parseInt(pageNum.trim());
            } catch (Exception e) {
                System.out.println("숫자로 변환할 수 없는 pageNum");
                // default로 1을 준다.
            }
        }
        criteria.setPageNum(pageNumInt);

        map.put("pageNum", (criteria.getPageNum() - 1) * 10);
        List<MVCBoardDTO> boardLists = dao.selectListPageWithPaging(map);

        System.out.println("boardLists is null? = " + boardLists);
        System.out.println("boardLists.size() = " + boardLists != null ? boardLists.size() : "null이기 때문에 size X");
        /*
         * boardLists is null? = []
         * size => 0
         * */

        PageMaker pageMaker = new PageMaker(criteria, totalCount);
        req.setAttribute("pageMaker", pageMaker);
        req.setAttribute("boardLists", boardLists);
        map.remove("pageNum");
        req.setAttribute("map", map);
        req.getRequestDispatcher("/MVCBoard/List.jsp").forward(req, resp);
    }  // doGet()
}  // class
