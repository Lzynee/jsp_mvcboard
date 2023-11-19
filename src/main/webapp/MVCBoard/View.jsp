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
    <title>파일 첨부형 게시판</title>
</head>
<body>
    <h2>파일 첨부형 게시판 - 상세 보기(View)</h2>

    <table style="border: 1px solid; width: 90%">
        <colgroup>
            <col style="width: 15%" /> <col style="width: 35%" />
            <col style="width: 15%" /> <col style="width: auto" />
        </colgroup>
        
        <%-- 게시글 정보 --%>
        <tr>
            <td>번호</td> <td>${ dto.idx }</td>
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
                <c:if test="${ not empty dto.ofile and isImage eq true }">
                    <br><img src="../Uploads/${ dto.sfile }"
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
    </table>
</body>
</html>
