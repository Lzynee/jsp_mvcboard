/**
 * 수정하기 기능 구현을 위한 서블릿
 * */
package mvcboard;

import utils.FileUtil;
import utils.JSFunction;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/mvcboard/edit.do")
@MultipartConfig(  // 파일 업로드를 위한 Multipart 설정
        maxFileSize = 1024 * 1024 * 1,
        maxRequestSize = 1024 * 1024 * 10
)
public class EditController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /* 요청명/서블릿 매핑 [직전 커밋: 83c567d] */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idx = req.getParameter("idx");  // 수정할 게시물의 일련번호

        MVCBoardDAO dao = new MVCBoardDAO();
        MVCBoardDTO dto = dao.selectView(idx);  // 기존 게시물 내용을 담은 DTO 객체
        req.setAttribute("dto", dto);  // 게시물 일련번호를 DTO 객체의 request 영역에 저장
        req.getRequestDispatcher("/MVCBoard/Edit.jsp").forward(req, resp);  // Edit.jsp로 포워드
    }

    /* 수정하기 서블릿 추가 [직전 커밋: 3169568] */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1. 파일 업로드 처리
        // 업로드 디렉터리의 물리적 경로 확인
        String saveDirectory = req.getServletContext().getRealPath("/Uploads");

        // 파일 업로드
        String originalFileName = "";

        try {
            originalFileName = FileUtil.uploadFile(req, saveDirectory);

        // 업로드 중 예외가 발생하면 경고창을 띄우고 글쓰기 페이지로 이동한다.
        } catch (Exception e) {
            JSFunction.alertBack(resp, "파일 업로드 오류입니다.");
            return;
        }

        // 2. 파일 업로드 외 처리 =====================================================
        // 매개변수로부터 수정 내용을 얻어 온다.
        String idx = req.getParameter("idx");
        String prevOfile = req.getParameter("prevOfile");
        String prevSfile = req.getParameter("prevSfile");

        String name = req.getParameter("name");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        // 비밀번호는 session에서 가져온다.
        HttpSession session = req.getSession();
        String pass = (String) session.getAttribute("pass");

        // DTO에 저장
        MVCBoardDTO dto = new MVCBoardDTO();
        dto.setIdx(idx);
        dto.setName(name);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setPass(pass);

        // 첨부 파일이 있는 경우 원본 파일명과 저장된 파일 이름 설정
        if (originalFileName != "") {
            String savedFileName = FileUtil.renameFile(saveDirectory,
                    originalFileName);

            dto.setOfile(originalFileName);  // 원래 파일 이름
            dto.setSfile(savedFileName);  // 서버에 저장된 파일 이름

            // 기존 파일 삭제
            FileUtil.deleteFile(req, "/Uploads", prevSfile);

        } else {
            // 첨부 파일이 없으면 기존 이름 유지
            dto.setOfile(prevOfile);
            dto.setSfile(prevSfile);
        }  // if ~ else

        // DB에 수정 내용 반영
        MVCBoardDAO dao = new MVCBoardDAO();
        int result = dao.updatePost(dto);
        dao.close();

        // 성공/실패 여부에 따라 진행
        if (result == 1) {  // 수정 성공
            session.removeAttribute("pass");  // 세션 영역에 저장된 비밀번호 삭제
            resp.sendRedirect("../mvcboard/view.do?idx=" + idx);  // 상세 보기 뷰로 이동해 수정된 내용을 확인시킨다.

        } else {  // 수정 실패
            JSFunction.alertLocation(resp, "비밀번호 검증을 다시 진행해주세요.",
                    "../mvcboard/view.do?idx=" + idx);  // 상세 보기 페이지에서 다시 비밀번호 검증을 하도록 유도한다.

        }  // if ~ else
    }  // doPost()
}  // class
