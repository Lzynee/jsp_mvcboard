/**
 * 파일 다운로드 서블릿
 * 다운로드 링크 클릭 시 전달하는 매개변수를 받아와서
 * 파일을 다운로드한 후
 * 다운로드 횟수를 증가시킨다.
 * */

package com.example.mvcboard.Controller;

import com.example.mvcboard.MVCBoardDAO;
import com.example.mvcboard.utils.FileUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/mvcboard/download.do")
public class DownloadController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 매개변수 받기
        String ofile = req.getParameter("ofile");  // 원본 파일명
        String sfile = req.getParameter("sfile");  // 저장된 파일명
        String idx = req.getParameter("idx");  // 게시물 일련번호

        // 파일 다운로드
        FileUtil.download(req, resp, "/Uploads", sfile, ofile);

        // 해당 게시물의 다운로드 수 1 증가
        MVCBoardDAO dao = new MVCBoardDAO();
        dao.downCountPlus(idx);
//        dao.close();
    }
}
