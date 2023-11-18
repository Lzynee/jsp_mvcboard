/**
 * 파일 업로드를 위한 유틸리티 클래스
 * */

package utils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {
    // 파일 업로드
    public static String uploadFile(HttpServletRequest req, String sDirectory)
        throws ServletException, IOException {

        Part part = req.getPart("ofile");  // request 내장 객체의 getPart() 메서드로 file 타입으로 전송된 폼값을 받아 Part 객체에 저장한다.
        String partHeader = part.getHeader("content-disposition");  // Part 객체에서 헤더값을 읽어온다.

        // 헤더의 내용에서 파일명을 추출한다.
        String[] phArr = partHeader.split("filename=");
        String originalFileName = phArr[1].trim().replace("\"", "");  // 더블쿼테이션을 제거

        // 파일명이 빈 값이 아니라면 디렉터리에 파일을 저장한다.
        if (!originalFileName.isEmpty()) {
            part.write(sDirectory + File.separator +originalFileName);
        }

        // 저장된 원본파일명을 반환한다.
        return originalFileName;
    }  // uploadFile()

    // 파일명 변경
    public static String renameFile(String sDirectory, String fileName) {
        // 새로운 파일명을 생성한다.
        String ext = fileName.substring(fileName.lastIndexOf("."));  // 원본파일명에서 확장자를 잘라낸다.
        String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());  // 생성할 문자열의 형식 : "현재날짜_시간"
        String newFileName = now + ext;  // 확장자와 파일명을 연결하여 새로운 파일명을 생성

        // 원본파일과 새로운파일에 대한 File객체를 생성한 후 파일명을 변경한다.
        File oldFile = new File(sDirectory + File.separator + fileName);
        File newFile = new File(sDirectory + File.separator + newFileName);
        oldFile.renameTo(newFile);

        // 변경된 파일명을 반환한다.
        return newFileName;
    }  // renameFile()
}  // class
