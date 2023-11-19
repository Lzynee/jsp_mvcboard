/**
 * 비밀번호 입력 페이지로 이동하기 위한 서블릿
 * */
package mvcboard;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/mvcboard/pass.do")
public class PassController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("mode", req.getParameter("mode"));  // mode 매개변수의 값을 request 영역에 저장
        req.getRequestDispatcher("/MVCBoard/Pass.jsp").forward(req, resp);  // 저장한 매개변수 값을 Pass.jsp로 포워드
    }
}
