/**
 * 상세보기를 위한 서블릿
 * */
package mvcboard;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("mvcboard/view.do")  // 요청명과 서블릿을 매핑
public class ViewController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 게시물 불러오기
        MVCBoardDAO dao = new MVCBoardDAO();  // DAO 객체 생성
        // 게시물의 일련번호를 매개변수로 받아 조회수를 증가시킨다.
        String idx = req.getParameter("idx");
        dao.updateVisitCount(idx);

        // 게시물 내용 가져오기
        MVCBoardDTO dto = dao.selectView(idx);
        dao.close();

        // 줄바꿈 처리 : 일반 텍스트 문서의 줄바꿈 문자(\r\n) => html이 인식하는 줄바꿈 태그(<br>)
        dto.setContent(dto.getContent().replaceAll("\r\n", "<br>"));

        // 첨부 파일 확장자 추출 및 이미지 타입 확인
        // 첨부 파일이 이미지라면 <img> 태그로 상세보기 화면에 보여준다.
        String ext = null, fileName = dto.getSfile();

        if (fileName != null) {
            ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        }


        String[] mimeStr = {"png", "jpg", "gif"};  // String 타입 배열에 이미지 확장자들을 저장
        List<String> mimeList = Arrays.asList(mimeStr);  // 배열을 List 컬렉션으로 변환

        // 컬렉션에 포함된 확장자이면 isImage 변수의 값을 true로 변경한다.
        boolean isImage = false;
        if (mimeList.contains(ext)) {
            isImage = true;
        }

        // DTO 객체와 isImage를 request 영역에 저장하고 View.jsp 로 포워드한다.
        req.setAttribute("dto", dto);
        req.setAttribute("isImage", isImage);
        req.getRequestDispatcher("/MVCBoard/View.jsp").forward(req, resp);

    }  // service()
}  // class
