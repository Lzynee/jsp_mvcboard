/**
 * 글쓰기 기능 구현을 위한 서블릿
 * doGet() 메서드 사용 : 글쓰기 폼으로 진입
 * doPost() 메서드 사용 : 폼값을 받아 DB 처리
 * */

package mvcboard;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WriteController extends HttpServlet {
    /* 작성폼으로 진입하기 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/MVCBoard/Write.jsp").forward(req, resp);
    }
}
