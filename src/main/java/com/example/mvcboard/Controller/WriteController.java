/**
 * 글쓰기 기능 구현을 위한 서블릿
 * doGet() 메서드 사용 : 글쓰기 폼으로 진입
 * doPost() 메서드 사용 : 폼값을 받아 DB 처리
 * */

package com.example.mvcboard.Controller;

import com.example.mvcboard.MVCBoardDAO;
import com.example.mvcboard.MVCBoardDTO;
import com.example.mvcboard.utils.FileUtil;
import com.example.mvcboard.utils.JSFunction;

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

    /* 폼값 처리하기 */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. 파일 업로드 처리 =======================================================
        // 업로드 디렉터리의 물리적 경로 확인
        String saveDirectory = req.getServletContext().getRealPath("/Uploads");

        // FileUtil.uploadFile() 메소드를 호출하여 파일 업로드
        String originalFileName = "";

        try {
            originalFileName = FileUtil.uploadFile(req, saveDirectory);

        } catch (Exception e) {
            // 파일 업로드에 실패할 경우 경고창을 띄우고 작성 페이지로 재이동한다.
            JSFunction.alertLocation(resp, "파일 업로드 오류입니다.",
                    "../mvcboard/write.do");

            return;
        }

        // 2. 파일 업로드 외 처리 =====================================================
        // 파일을 제외한 나머지 폼값을 DTO에 저장
        MVCBoardDTO dto = new MVCBoardDTO();

        dto.setName(req.getParameter("name"));
        dto.setTitle(req.getParameter("title"));
        dto.setContent(req.getParameter("content"));
        dto.setPass(req.getParameter("pass"));

        // 원본 파일명과 저장된 파일 이름 설정
        if (originalFileName != "") {  // 업로드 된 파일이 있는지 확인
            // 첨부 파일이 있을 경우 파일명 변경
            String savedFileName = FileUtil.renameFile(saveDirectory,
                    originalFileName);

            // 원본 파일명과 변경된 파일명을 따로 기록한다.
            dto.setOfile(originalFileName);  // 원래 파일 이름
            dto.setSfile(savedFileName);  // 서버에 저장된 파일 이름
        }

        // DAO를 통해 DB에 게시 내용 저장
        MVCBoardDAO dao = new MVCBoardDAO();
        int result = dao.insertWrite(dto);
        dao.close();

        // 성공, 실패 여부에 따라 처리
        if (result == 1) {  // 글쓰기 성공 => 목록으로 이동
            resp.sendRedirect("../mvcboard/list.do");

        } else {  // 글쓰기 실패 => 경고창을 띄우고 글쓰기 페이지로 재이동
            JSFunction.alertLocation(resp, "글쓰기에 실패했습니다.",
                    "../mvcboard/write.do");
        }

    }  // doPost()
}  // class
