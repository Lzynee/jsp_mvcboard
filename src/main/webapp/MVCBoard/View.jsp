<%--
  Created by IntelliJ IDEA.
  User: sec
  Date: 2023-11-17
  Time: 오후 11:52
  Title: 게시물 내용보기 뷰(JSP)
  Description: 게시물 내용을 출력해줄 뷰
  Functions: 게시물 관련 정보를 보여준다.
  첨부 파일을 다운로드할 수 있는 링크와 현 게시물을 수정/삭제할 수 있는 버튼을 제공한다.
  첨부 파일이 이미지라면 이미지도 함께 보여준다.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <title>파일 첨부형 게시판</title>
    
</head>
<body>
<h2>파일 첨부형 게시판 - 상세 보기(View)</h2>

<table class="table" style="width: 90%">
    <colgroup>
        <col style="width: 15%" /> <col style="width: 35%" />
        <col style="width: 15%" /> <col style="width: auto" />
    </colgroup>
    
    <%-- 게시글 정보 --%>
    <%-- 서블릿에서 request 영역에 저장한 DTO 객체의 내용을 EL로 출력한다. --%>
    <tr>
        <td>번호</td> <td>${ dto.idx }</td> <%-- ${ 속성명.멤버변수 }--%>
        <td>작성자</td> <td>${ dto.name }</td>
    </tr>
    <tr>
        <td>작성일</td> <td>${ dto.postdate }</td>
        <td>조회수</td> <td>${ dto.visitcount }</td>
    </tr>
    <tr>
        <td>제목</td> <td colspan="3">${ dto.title }</td>
    </tr>
    <tr>
        <td>내용</td>
        <td colspan="3" height="100px">${ dto.content }
            <%-- 이미지 첨부 파일이 있다면 <img> 태그를 이용해 이미지를 출력한다. --%>
            <c:if test="${ not empty dto.ofile and isImage eq true }">
                <br><img src="../Uploads/${ dto.sfile }"
                         <%-- 이미지가 출력될 영역보다 작으면 원본 크기로, 크다면 해당 영역만큼만 출력한다. --%>
                         style="max-width: 100%;">
            </c:if>
        </td>
    </tr>
    
    <%-- 첨부 파일 --%>
    <tr>
        <td>첨부 파일</td>
        <td>
            <c:if test="${ not empty dto.ofile }">
                ${ dto.ofile }
                <a href="../mvcboard/download.do?ofile=${ dto.ofile }
                    &sfile=${ dto.sfile }&idx=${ dto.idx }">
                    [다운로드]
                </a>
            </c:if>
        </td>
        <td>다운로드수</td>
        <td>${ dto.downcount}</td>
    </tr>
    
    <%-- 하단 메뉴(버튼) - 수정, 삭제, 목록 바로가기 버튼 --%>
    <tr>
        <td colspan="4" style="text-align: center">
            <%-- 수정하기 및 삭제하기 버튼은 비밀번호 검증 페이지로 이동 --%>
            <button class="btn btn-outline-secondary btn-sm" type="button"
                    onclick="location.href='../mvcboard/pass.do?' +
                            'mode=edit&idx=${ param.idx }';">
                수정하기
            </button>
            <button class="btn btn-outline-secondary btn-sm" type="button"
                    onclick="location.href='../mvcboard/pass.do?' +
                            'mode=delete&idx=${ param.idx }';">
                삭제하기
            </button>
            <button class="btn btn-outline-secondary btn-sm" type="button"
                    onclick="location.href='../mvcboard/list.do?';">
                목록 바로가기
            </button>
        </td>
    </tr>
</table>
</body>
</html>
