/**
 * 요청명/서블릿 매핑
 * 최초 생성 : [83c567d]
 * */
package mvcboard;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/mvcboard/edit.do")
@MultipartConfig(  // 파일 업로드를 위한 Multipart 설정
        maxFileSize = 1024 * 1024 * 1,
        maxRequestSize = 1024 * 1024 * 10
)
public class EditController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idx = req.getParameter("idx");  // 수정할 게시물의 일련번호

        MVCBoardDAO dao = new MVCBoardDAO();
        MVCBoardDTO dto = dao.selectView(idx);  // 기존 게시물 내용을 담은 DTO 객체
        req.setAttribute("dto", dto);  // 게시물 일련번호를 DTO 객체의 request 영역에 저장
        req.getRequestDispatcher("/MVCBoard/Edit.jsp").forward(req, resp);  // Edit.jsp로 포워드
    }
}
