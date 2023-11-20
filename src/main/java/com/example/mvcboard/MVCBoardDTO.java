/**
 * mvcboard 테이블에 대응하는 클래스
 * */

package com.example.mvcboard;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MVCBoardDTO {
    // 멤버 변수 선언
    private String idx;
    private String name;
    private String title;
    private String content;
    private java.sql.Date postdate;
    private String ofile;
    private String sfile;
    private int downcount;
    private String pass;
    private int visitcount;
}
