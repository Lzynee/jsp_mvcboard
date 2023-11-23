<%--
  Created by IntelliJ IDEA.
  User: sec
  Date: 2023-11-18
  Time: 오전 12:04
  Title: 글쓰기 폼
  Description: 뷰 - MVCBoardWrite 서블릿에서 포워드할 JSP
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
        rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" />
  <title>파일 첨부형 게시판</title>
  <script type="text/javascript">
    function validateForm(form) {  // 필수 항목 입력 확인
        // 폼값을 서버로 전송하기 전에 필수 항목 중 빈 값이 있는지를 확인한다.
        if (form.name.value == "") {
            alert("작성자를 입력하세요.");
            form.name.focus();
            return false;
        }

        if (form.title.value == "") {
            alert("제목을 입력하세요.");
            form.title.focus();
            return false;
        }

        if (form.content.value == "") {
            alert("내용을 입력하세요.");
            form.content.focus();
            return false;
        }

        if (form.pass.value == "") {
            alert("비밀번호를 입력하세요.");
            form.pass.focus();
            return false;
        }
        
    }
  </script>
  <style>
    table {
           width: 90%;
    }
  </style>
</head>
<body>
  <h2>파일 첨부형 게시판 - 글쓰기(Write)</h2>
  <%-- 파일을 첨부할 수 있도록 method와 enctype 속성을 지정 --%>
  <form name="writeFrm" method="post" enctype="multipart/form-data"
        action="../mvcboard/write.do" onsubmit="return validateForm(this);">
    <table class="table table-bordered">
      <colgroup>
        <col style="width: 10%; padding-left: 3em;" /> <col style="width: auto" />
      </colgroup>
      <tr>
        <th class="table-light">작성자</th>
        <td><input type="text" name="name" style="width: 150px;"></td>
      </tr>
      <tr>
        <th class="table-light">제목</td>
        <td><input type="text" name="title" style="width: 90%;"></td>
      </tr>
      <tr>
        <th class="table-light">내용</td>
        <td><textarea name="content" style="width: 90%; height: 100px;"></textarea></td>
      </tr>
      <tr>
        <th class="table-light">첨부 파일</td>
        <td>
          <button class="btn btn-light btn-sm">
            <input type="file" name="ofile">
          </button>
       </td>  <%-- 파일 선택을 위한 입력 상자 --%>
      </tr>
      <tr>
        <th class="table-light">비밀번호</td>
        <td><input type="password" name="pass" style="width: 100px;"></td>
      </tr>
      <tr>
        <td colspan="2" style="text-align: center">
          <button class="btn btn-outline-secondary btn-sm" type="submit">작성 완료</button>
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
