-- 구글 소셜 로그인 지원을 위한 member 테이블 변경
-- 실제 Aiven PostgreSQL DB에 수동으로 실행 필요 (db_script.sql은 신규 생성 기준 스크립트)

ALTER TABLE member ADD COLUMN provider VARCHAR(20) NOT NULL DEFAULT 'LOCAL';
ALTER TABLE member ALTER COLUMN password DROP NOT NULL;

COMMENT ON COLUMN member.provider IS '가입 경로 (LOCAL, GOOGLE, KAKAO, NAVER 등)';
COMMENT ON COLUMN member.password IS '비밀번호 (암호화 됨, 소셜 로그인 회원은 NULL)';
