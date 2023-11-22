DROP TABLE IF EXISTS mvcboard;
CREATE TABLE mvcboard(
	idx int NOT NULL auto_increment PRIMARY KEY comment "일련번호",
	name varchar(50) NOT NULL comment "작성자 이름",
	title varchar(200) NOT NULL comment "제목",
	content varchar(2000) NOT NULL comment "내용",
	postdate date NOT NULL DEFAULT sysdate() comment "작성일",
	ofile varchar(200) comment "원본 파일명",
	sfile varchar(30) comment "저장된 파일명",
	downcount decimal NOT NULL DEFAULT 0 comment "다운로드 횟수",
	pass varchar(200) NOT NULL comment "비밀번호",
	visitcount decimal NOT NULL DEFAULT 0 comment "조회수"
);