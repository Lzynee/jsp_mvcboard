/**
 * 메시지 알림창을 띄운 후
 * 특정 페이지로 이동하는 자바스크립트 코드
 * 기능 성공/실패 여부에 따른 다음 동작을 처리한다.
 * */

package com.example.mvcboard.utils;

import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JSFunction {
    // 메시지 알림창을 띄운 후 명시한 URL로 이동한다.
    /* 매개변수
    * msg : 알림창에 띄울 메시지
    * url : 알림창을 닫은 후 이동할 페이지의 URL
    * out : 자바스크립트 코드를 삽입할 출력 스트림
    * */
    public static void alertLocation(String msg, String url, JspWriter out) {
        try {
            String script = ""  // 삽입할 자바스크립트 코드를 문자열 형태로 선언
                    + "<script>"
                    + "    alert('" + msg + "');"
                    + "    location.href='" + url + "';"
                    + "</script>";
            out.println(script);  // 자바스크립트 코드를 out 내장 객체로 출력(삽입)
        }
        catch (Exception e) {}
    }

    // 메시지 알림창을 띄운 후 이전 페이지로 돌아간다.
    // 호출자가 URL을 지정할 수 없고, 무조건 이전 페이지로 이동한다.
    public static void alertBack(String msg, JspWriter out) {
        try {
            String script = ""
                    + "<script>"
                    + "    alert('" + msg + "');"
                    + "    history.back();"
                    + "</script>";
            out.println(script);
        }
        catch (Exception e) {}
    }

    // 메시지 알림창을 띄운 후 명시한 URL로 이동한다.
    public static void alertLocation(HttpServletResponse resp, String msg, String url) {
        try {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            String script = ""
                    + "<script>"
                    + "    alert('" + msg + "');"
                    + "    location.href='" + url + "';"
                    + "</script>";
            writer.print(script);
        }
        catch (Exception e) {}
    }

    // 메시지 알림창을 띄운 후 이전 페이지로 돌아간다.
    public static void alertBack(HttpServletResponse resp, String msg) {
        try {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            String script = ""
                    + "<script>"
                    + "    alert('" + msg + "');"
                    + "    history.back();"
                    + "</script>";
            writer.print(script);
        }
        catch (Exception e) {}
    }

}
