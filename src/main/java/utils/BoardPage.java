/**
 * 페이지 바로가기 영역 HTML 문자열 출력
 * */

package utils;

public class BoardPage {
    public static String pagingStr(int totalCount, int pageSize, int blockPage,
                                   int pageNum, String reqUrl) {
        String pagingStr = "";

        // 전체 페이지 수 계산
        // 계산한 결과는 무조건 올림 처리 => 마지막 페이지의 게시물까지 조회
        int totalPages = (int) (Math.ceil(((double) totalCount / pageSize)));

        // '이전 페이지 블록 바로가기' 출력
        int pageTemp = (((pageNum - 1) / blockPage) * blockPage) + 1;

        // pageTemp가 1이 아닐 때만 [첫 페이지]와 [이전 블록] 링크를 출력한다.
        // pageTemp가 1인 경우는 첫 번째 페이지 블록인 경우
        if (pageTemp != 1) {
            pagingStr += "<a href='" + reqUrl + "?pageNum=1'>[첫 페이지]</a>";  // 첫 페이지로의 바로가기 링크
            pagingStr += "&nbsp;";
            pagingStr += "<a href='" + reqUrl + "?pageNum=" + (pageTemp - 1) + "'>[이전 블록]</a>";
        }

        // 각 페이지 번호 출력
        // pageTemp를 BLOCK_PAGE만큼 반복하면서 +1 연산 후 출력한다.
        int blockCount = 1;

        while (blockCount <= blockPage && pageTemp <= totalPages) {
            if (pageTemp == pageNum) {
                // 현재 페이지는 링크를 걸지 않음
                pagingStr += "&nbsp;" + pageTemp + "&nbsp;";

            } else {
                pagingStr += "&nbsp;<a href='" + reqUrl + "?pageNum=" + pageTemp
                        + "'>" + pageTemp + "</a>&nbsp;";
            }

            pageTemp++;
            blockCount++;
        }

        // '다음 페이지 블록 바로가기' 출력
        // pageTemp가 전체 페이지 수 이하일 때 [다음 블록]과 [마지막 페이지] 링크를 출력한다.
        // 각 페이지 번호를 출력한 후 다음 페이지 블록 바로가기를 설정
        if (pageTemp <= totalPages) {
            pagingStr += "&nbsp;<a href='" + reqUrl + "?pageNum=" + pageTemp
                    + "'>[다음 블록]</a>";
            pagingStr += "&nbsp;";
            pagingStr += "<a href='" + reqUrl + "?pageNum=" + totalPages
                    + "'>[마지막 페이지]</a>";
        }

        return pagingStr;

    }  // pagingStr()

}  // class
