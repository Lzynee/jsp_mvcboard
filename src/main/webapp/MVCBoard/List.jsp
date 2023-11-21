<%--
  Created by IntelliJ IDEA.
  User: sec
  Date: 2023-11-17
  Time: 오후 10:22
  Title: 뷰 코드
  Description:
    컨트롤러(서블릿)에서 처리한 내용을 출력할 뷰(JSP)
    검색 폼, 목록, 하단 메뉴로 구성된 화면
    하단 메뉴는 바로가기 링크와 글쓰기 버튼으로 구성됨
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>파일 첨부형 게시판</title>
    <style>
        a {
            text-display: none;
        }
        table {
            border: 1px solid;
            width: 90%;
        }
        th, td {
            border: 1px solid;
        }
    </style>
</head>
<body>
<h2>파일 첨부형 게시판 - 목록 보기(List)</h2>

<%-- 검색 폼 --%>
<%-- 입력된 검색어는 ListController 서블릿으로 전송된 후
MVCBoardDAO 클래스의 selectCount()와 selectListPage() 메서드의 인수로 전달된다. --%>
<form method="get">
    <table>
        <tr>
            <td style="text-align: center;">
                <select name="searchField">
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                </select>
                <input type="text" name="searchWord">
                <input type="submit" value="검색하기">
            </td>
        </tr>
    </table>
</form>

<%-- 목록 테이블 --%>
<table>
    <tr>
        <th style="width: 10%;">번호</th>
        <th style="width: auto;">제목</th>
        <th style="width: 15%;">작성자</th>
        <th style="width: 10%;">조회수</th>
        <th style="width: 15%;">작성일</th>
        <th style="width: 8%;">첨부</th>
    </tr>
    <c:choose>
      <%-- ListController에서 request 영역에 저장한 값을 받아 온다. --%>
        <c:when test="${ empty boardLists }">  <%-- 게시물이 없을 때 --%>
            <tr>
                <td colspan="6" style="text-align: center;">
                    등록된 게시물이 없습니다 ☺️
                </td>
            </tr>
        </c:when>
        <c:otherwise>  <%-- 게시물이 있을 때 --%>
          <%-- 게시물이 있으면 목록에 출력할 가상번호를 계산하고, 반복 출력한다. --%>
            <c:forEach items="${ boardLists }" var="row" varStatus="loop">
                <tr style="text-align: center;">
                    <td>  <%-- 번호 --%>
                            ${ map.totalCount - (((map.pageNum - 1) * map.pageSize) + loop.index) }
                    </td>
                    <td style="text-align: left;">  <%-- 제목(상세보기 페이지로 바로가기 링크) --%>
                      <%-- 게시물의 일련번호를 매개변수로 사용한다. --%>
                        <a href="../mvcboard/view.do?idx=${ row.idx }">${ row.title }</a>
                    </td>
                    <td>${ row.name }</td>  <%-- 작성자 --%>
                    <td>${ row.visitcount }</td>  <%-- 조회수 --%>
                    <td>${ row.postdate }</td>  <%-- 작성일 --%>
                    <td>
                        <c:if test="${ not empty row.ofile }">
                          <%-- 첨부된 파일을 다운로드하기 위한 링크 --%>
                          <%-- 원본 파일명, 저장된 파일명, 일련번호를 매개변수로 사용 --%>
                            <a href="../mvcboard/download.do?ofile=${ row.ofile }
                            &sfile=${ row.sfile }&idx=${ row.idx }">[Down]</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</table>

<%-- 하단 메뉴(바로가기, 글쓰기) --%>
<table>
  <tr style="text-align: center;">
    <td>  <%-- 페이지 바로가기 링크 --%>
      ${ map.pagingImg }
    </td>
    <td style="width: 100px;">  <%-- 글쓰기 버튼 --%>
      <button type="button" onclick="location.href='../mvcboard/write.do';">
        글쓰기
      </button>
    </td>
  </tr>
</table>
</body>
</html>
