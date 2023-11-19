<%--
  Created by IntelliJ IDEA.
  User: sec
  Date: 2023-11-19
  Time: 오후 2:16
  Title: 비밀번호 입력 화면
  Description: 비밀번호 검증 페이지
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
  <title>파일 첨부형 게시판</title>
  <script type="text/javascript">
    // 비밀번호를 입력했는지 확인한다.
    function validateForm(form) {
        if (form.pass.value == "") {
            alert("비밀번호를 입력하세요.");
            form.pass.focus();
            return false;
        }
    }
  </script>
  <style>
    td {
        border: 1px solid;
    }
  </style>
</head>
<body>
  <h2>파일 첨부형 게시판 - 비밀번호 검증(Pass)</h2>
  <form name="writeFrm" method="post" action="../mvcboard/pass.do"
        onsubmit="return validateForm(this);">
    <%-- 삭제 혹은 수정할 게시물의 일련번호와 모드를 hidden 타입 입력상자에 저장한다. --%>
    <input type="hidden" name="idx" value="${ param.idx }">
    <input type="hidden" name="mode" value="${ param.mode }">
    <table style="border: 1px solid; width: 90%;">
      <tr>
        <td>비밀번호</td>
        <td>
          <input type="password" name="pass" style="width: 100px;">
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align: center">
          <button type="submit">검증하기</button>
          <button type="reset">RESET</button>
          <button type="button" onclick="location.href='../mvcboard/list.do';">
            목록 바로가기
          </button>
        </td>
      </tr>
    </table>
  </form>
</body>
</html>
