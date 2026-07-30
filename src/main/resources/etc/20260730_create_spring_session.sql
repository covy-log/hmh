-- ============================================================
--  Spring Session JDBC 세션 저장용 테이블 (PostgreSQL)
--
--  목적 : 세션을 서버 메모리가 아닌 DB 에 저장한다.
--         Render 무료 티어는 15분 무활동 시 컨테이너를 내리는데,
--         인메모리 세션이면 그때마다 전체 사용자가 로그아웃됐다.
--         재배포 시에도 마찬가지였다.
--
--  전제 : build.gradle 에 org.springframework.session:spring-session-jdbc 추가
--         application.properties 에 spring.session.jdbc.initialize-schema=never
--         (테이블을 이 스크립트로 직접 만들기 때문에 자동 생성을 끈다)
--
--  ⚠️ 이 DDL 은 Spring Session 이 배포하는 공식 스키마를 옮긴 것이다.
--     버전에 따라 컬럼이 달라질 수 있으므로 실행 전 아래로 실제 파일과 비교할 것.
--       ./gradlew dependencies --configuration runtimeClasspath | grep spring-session
--     그리고 내려받은 jar 안의 정본을 확인한다.
--       org/springframework/session/jdbc/schema-postgresql.sql
-- ============================================================

CREATE TABLE SPRING_SESSION (
    PRIMARY_ID            CHAR(36)     NOT NULL,
    SESSION_ID            CHAR(36)     NOT NULL,
    CREATION_TIME         BIGINT       NOT NULL,
    LAST_ACCESS_TIME      BIGINT       NOT NULL,
    MAX_INACTIVE_INTERVAL INT          NOT NULL,
    EXPIRY_TIME           BIGINT       NOT NULL,
    PRINCIPAL_NAME        VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);


CREATE TABLE SPRING_SESSION_ATTRIBUTES (
    SESSION_PRIMARY_ID CHAR(36)     NOT NULL,
    ATTRIBUTE_NAME     VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES    BYTEA        NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID)
        REFERENCES SPRING_SESSION (PRIMARY_ID) ON DELETE CASCADE
);


-- ── 확인 ────────────────────────────────────────────────
-- 두 테이블이 만들어졌는지 확인한다.
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_name IN ('spring_session', 'spring_session_attributes')
ORDER BY table_name;
