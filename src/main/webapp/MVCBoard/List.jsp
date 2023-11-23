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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>파일 첨부형 게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        a {
            text-display: none;
        }
        table {
            width: 90%;
        }
    </style>
</head>
<body>
<h2>파일 첨부형 게시판 - 목록 보기(List)</h2>

<%-- 검색 폼 --%>
<%-- 입력된 검색어는 ListController 서블릿으로 전송된 후
MVCBoardDAO 클래스의 selectCount()와 selectListPage() 메서드의 인수로 전달된다. --%>
<form method="get" action="/mvcboard/list.do">
    <table class="table table-borderless">
        <tr>
            <td style="text-align: center;">
              <div class="searchbar"
                   style="display: flex; justify-content: center;
                   align-content: center">
                <select class="form-select form-select-sm"
                        aria-label="Small select example" name="searchField"
                        style="display: inline-block; width: 100px;
                        margin-right: 0.4em;">
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                </select>
                <input type="text" name="searchWord"
                       style="width: 40%; margin-right: 0.4em;">
                <input class="btn btn-outline-secondary btn-sm" type="submit" value="검색하기">
              </div>
            </td>
        </tr>
    </table>
</form>

<%-- 목록 테이블 --%>
<table class="table table-hover">
  <thead class="table-light">
    <tr style="text-align: center;">
        <th scope="col" style="width: 10%;">번호</th>
        <th scope="col" style="width: auto;">제목</th>
        <th scope="col" style="width: 15%;">작성자</th>
        <th scope="col" style="width: 10%;">조회수</th>
        <th scope="col" style="width: 15%;">작성일</th>
        <th scope="col" style="width: 8%;">첨부</th>
    </tr>
  </thead>
  <tbody class="table-group-divider">
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
                    <th scope="row">  <%-- 번호 --%>
                            ${ map.totalCount - (((map.pageNum - 1) * map.pageSize) + loop.index) }
                    </th>
                    <td style="text-align: left;">  <%-- 제목(상세보기 페이지로 바로가기 링크) --%>
                      <%-- 게시물의 일련번호를 매개변수로 사용한다. --%>
                        <a href="../mvcboard/view.do?idx=${ row.idx }"
                           style="text-decoration: none; color: black;">
                            ${ row.title }
                        </a>
                    </td>
                    <td>${ row.name }</td>  <%-- 작성자 --%>
                    <td>${ row.visitcount }</td>  <%-- 조회수 --%>
                    <td>${ row.postdate }</td>  <%-- 작성일 --%>
                    <td>
                        <c:if test="${ not empty row.ofile }">
                          <%-- 첨부된 파일을 다운로드하기 위한 링크 --%>
                          <%-- 원본 파일명, 저장된 파일명, 일련번호를 매개변수로 사용 --%>
                          <button class="btn btn-light btn-sm" type="button"
                                  onclick="../mvcboard/download.do?ofile=${ row.ofile }
                                          &sfile=${ row.sfile }&idx=${ row.idx }">
                            Down
                          </button>
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
      <button class="btn btn-dark btn-sm" type="button"
              onclick="location.href='../mvcboard/write.do';">
        글쓰기
      </button>
    </td>
  </tr>
</table>
</body>
</html>
