/**
 * 삭제 기능 중 비밀번호와 관련된 영역을 매핑한다.
 * 비밀번호 입력 페이지로 이동하기 위한 서블릿
 * 전송된 비밀번호를 확인한 후 삭제 혹은 수정을 하기 위한 서블릿
 * */
package com.example.mvcboard.Controller;

import com.example.mvcboard.MVCBoardDAO;
import com.example.mvcboard.MVCBoardDTO;
import com.example.mvcboard.utils.FileUtil;
import com.example.mvcboard.utils.JSFunction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/mvcboard/pass.do")
public class PassController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("mode", req.getParameter("mode"));  // mode 매개변수의 값을 request 영역에 저장
        req.getRequestDispatcher("/MVCBoard/Pass.jsp").forward(req, resp);  // 저장한 매개변수 값을 Pass.jsp로 포워드
    }

    /* 파일 삭제/수정 서블릿 추가 [051279c] */
    @Override
    // 비밀번호 입력폼에서 전송한 값을 받아 처리한다.
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 매개변수 저장
        String idx = req.getParameter("idx");
        String mode = req.getParameter("mode");
        String pass = req.getParameter("pass");

        // DAO를 통해 비밀번호가 맞는지 확인한다.
        MVCBoardDAO dao = new MVCBoardDAO();
        boolean confirmed = dao.confirmPassword(pass, idx);
//        dao.close();

        if (confirmed) {  // 비밀번호 일치

            // 비밀번호가 일치하고 현재 요청이 수정인 경우
            if (mode.equals("edit")) {
                HttpSession session = req.getSession();
                session.setAttribute("pass", pass);  // session 영역에 비밀번호 저장
                resp.sendRedirect("../mvcboard/edit.do?idx=" + idx);  // 수정하기 페이지로 이동

            // 비밀번호가 일치하고 현재 요청이 삭제인 경우
            } else if (mode.equals("delete")) {
                dao = new MVCBoardDAO();
                MVCBoardDTO dto = dao.selectView(idx);  // 기존 정보를 보관

                int result = dao.deletePost(idx);  // 게시물 삭제
//                dao.close();

                // 게시물 삭제 성공 시 보관해 둔 정보에서 파일 이름을 찾아 첨부 파일도 삭제
                if (result == 1) {
                    String saveFileName = dto.getSfile();
                    FileUtil.deleteFile(req, "/Uploads", saveFileName);
                }

                JSFunction.alertLocation(resp, "삭제되었습니다.",
                        "../mvcboard/list.do");  // 삭제 처리 후 목록 페이지로 이동

            }  // if ~ else-if

        // 비밀번호 불일치하는 경우 경고창을 띄우고 이전 페이지로 이동한다.
        } else {
            JSFunction.alertBack(resp, "비밀번호 검증에 실패했습니다.");
        }  // if(confirmed) ~ else

    }  // doPost()
}  // class
