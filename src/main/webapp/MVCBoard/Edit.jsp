<%--
  Created by IntelliJ IDEA.
  User: sec
  Date: 2023-11-19
  Time: 오후 11:00
  Title: 수정하기용 뷰
  작업 전 직전 커밋: [4d1af05]
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
    // 필수 항목을 모두 입력했는지 확인하는 함수
    function validateForm(form) {
        
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
    }
  </script>
  <style>
      table {
          width: 90%;
      }
  </style>
</head>
<body>
  <h2>파일 첨부형 게시판 - 수정하기(Edit)</h2>
  <form name="writeFrm" method="post" enctype="multipart/form-data"
        action="../mvcboard/edit.do" onsubmit="return validateForm(this);">
    
    <%-- hidden 타입 입력상자로 일련번호, 서버에 저장된 파일명, 원본 파일명을 전달 --%>
    <input type="hidden" name="idx" value="${ dto.idx }">
    <input type="hidden" name="prevOfile" value="${ dto.ofile }">
    <input type="hidden" name="prevSfile" value="${ dto.sfile }">
    
    <table class="table table-bordered">
      <colgroup>
        <col style="width: 10%; padding-left: 3em;" /> <col style="width: auto" />
      </colgroup>
      <tr>
        <th class="table-light">작성자</td>
        <td>
          <input type="text" name="name" style="width: 150px;"
                 value="${ dto.name }">  <%-- DTO에 담긴 기존 게시물의 내용으로 입력상자를 채운다. --%>
        </td>
      </tr>
      <tr>
        <th class="table-light">제목</td>
        <td>
          <input type="text" name="title" style="width: 90%;"
                 value="${ dto.title }">
        </td>
      </tr>
      <tr>
        <th class="table-light">내용</td>
        <td>
          <textarea name="content" style="width: 90%; height: 100px;">${ dto.content }</textarea>
        </td>
      </tr>
      <tr>
        <th class="table-light">첨부 파일</td>
        <td>
          <button class="btn btn-light btn-sm">
            <input type="file" name="ofile">
          </button>
        </td>
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
