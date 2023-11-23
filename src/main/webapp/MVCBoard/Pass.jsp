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
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
        rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
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
</head>
<body>
  <h2>파일 첨부형 게시판 - 비밀번호 검증(Pass)</h2>
  <form name="writeFrm" method="post" action="../mvcboard/pass.do"
        onsubmit="return validateForm(this);">
    <%-- 삭제 혹은 수정할 게시물의 일련번호와 모드를 hidden 타입 입력상자에 저장한다. --%>
    <input type="hidden" name="idx" value="${ param.idx }">
    <input type="hidden" name="mode" value="${ param.mode }">
    <table class="table table-bordered" style="width: 90%;">
      <tr>
        <td class="table-light" style="width: 150px;">비밀번호</td>
        <td>
          <input type="password" name="pass" style="width: 400px;">
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align: center">
          <button class="btn btn-outline-secondary btn-sm" type="submit">검증하기</button>
          <button class="btn btn-outline-secondary btn-sm" type="reset">RESET</button>
          <button class="btn btn-outline-secondary btn-sm" type="button" onclick="location.href='../mvcboard/list.do';">
            목록 바로가기
          </button>
        </td>
      </tr>
    </table>
  </form>
</body>
</html>
