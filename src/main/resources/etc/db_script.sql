CREATE TABLE member (
                        seq_no BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        login_id VARCHAR(50) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        name VARCHAR(50) NOT NULL,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        week_start_day VARCHAR(10) NOT NULL,
                        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                        role VARCHAR(20) NOT NULL DEFAULT 'BASIC_USER',
                        last_login_at TIMESTAMP,
                        create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        change_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE member IS '회원 정보 테이블';
COMMENT ON COLUMN member.seq_no IS '회원 고유번호 (PK)';
COMMENT ON COLUMN member.login_id IS '로그인 아이디';
COMMENT ON COLUMN member.password IS '비밀번호 (암호화 됨)';
COMMENT ON COLUMN member.name IS '사용자 닉네임 또는 이름';
COMMENT ON COLUMN member.email IS '이메일 주소';
COMMENT ON COLUMN member.week_start_day IS '주 시작 요일';
COMMENT ON COLUMN member.status IS '회원 상태 (ACTIVE, DORMANCY, BANNED, DELETED 등)';
COMMENT ON COLUMN member.role IS '회원 권한 (BASIC_USER, ADMIN 등)';
COMMENT ON COLUMN member.last_login_at IS '마지막 로그인 일시';
COMMENT ON COLUMN member.create_at IS '계정 생성일시';
COMMENT ON COLUMN member.change_at IS '계정 변경일시';

CREATE TABLE routine (
                         seq_no BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         member_seq_no BIGINT NOT NULL,
                         title VARCHAR(100) NOT NULL,
                         target_value NUMERIC(10, 2) NOT NULL,
                         daily_limit NUMERIC(10, 2) NOT NULL,
                         routine_type VARCHAR(20) NOT NULL,
                         interval_weeks INTEGER NOT NULL,
                         days_of_week VARCHAR(30),
                         start_ymd DATE NOT NULL,
                         create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         change_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         status VARCHAR(20) NOT NULL DEFAULT 'IN_PROGRESS'
);

COMMENT ON TABLE routine IS '루틴 정보 테이블';
COMMENT ON COLUMN routine.seq_no IS '루틴 고유번호 (PK)';
COMMENT ON COLUMN routine.member_seq_no IS '회원 고유번호';
COMMENT ON COLUMN routine.title IS '루틴명';
COMMENT ON COLUMN routine.target_value IS '루틴 목표 수치 (예: 20시간, 4회)';
COMMENT ON COLUMN routine.daily_limit IS '1일 최대 한도 수치';
COMMENT ON COLUMN routine.routine_type IS '루틴 목표 타입 (CHECK, COUNT, TIME 등)';
COMMENT ON COLUMN routine.interval_weeks IS '반복 주기 (단위: 주, 예: 1=매주, 2=격주)';
COMMENT ON COLUMN routine.days_of_week IS '수행 요일 (예: 1,3,5 / 1:월 ~ 7:일)';
COMMENT ON COLUMN routine.start_ymd IS '루틴 시작(기준) 일자';
COMMENT ON COLUMN routine.create_at IS '생성일시';
COMMENT ON COLUMN routine.change_at IS '변경일시';
COMMENT ON COLUMN routine.status IS '루틴 상태 (IN_PROGRESS 등)';


CREATE TABLE routine_cycle (
                               seq_no BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                               routine_seq_no BIGINT NOT NULL,
                               member_seq_no BIGINT NOT NULL,
                               cycle_number INTEGER NOT NULL,
                               start_ymd DATE NOT NULL,
                               end_ymd DATE NOT NULL,
                               target_value NUMERIC(10, 2) NOT NULL,
                               daily_limit NUMERIC(10, 2) NOT NULL,
                               current_achieved_value NUMERIC(10, 2) NOT NULL DEFAULT 0,
                               status varchar(20) DEFAULT 'IN_PROGRESS'::character varying NOT NULL,
                               create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               change_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE routine_cycle IS '루틴 회차/주기 정보 테이블';
COMMENT ON COLUMN routine_cycle.seq_no IS '루틴 주기 고유번호 (PK)';
COMMENT ON COLUMN routine_cycle.routine_seq_no IS '부모 루틴 고유번호 (FK)';
COMMENT ON COLUMN routine_cycle.member_seq_no IS '회원 고유번호';
COMMENT ON COLUMN routine_cycle.cycle_number IS '회차 번호 (예: 1회차, 2회차...)';
COMMENT ON COLUMN routine_cycle.start_ymd IS '주기 시작일';
COMMENT ON COLUMN routine_cycle.end_ymd IS '주기 종료일';
COMMENT ON COLUMN routine_cycle.target_value IS '주기 생성 당시의 목표 수치 스냅샷';
COMMENT ON COLUMN routine_cycle.daily_limit IS '1일 최대 한도 수치';
COMMENT ON COLUMN routine_cycle.current_achieved_value IS '현재까지 달성한 누적 수치';
COMMENT ON COLUMN routine_cycle.status IS '주기 상태 (진행중, 성공, 실패 등)';
COMMENT ON COLUMN routine_cycle.create_at IS '생성일시';
COMMENT ON COLUMN routine_cycle.change_at IS '변경일시';


CREATE TABLE daily_log (
                           seq_no BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           cycle_seq_no BIGINT NOT NULL,
                           member_seq_no BIGINT NOT NULL,
                           todo_ymd DATE NOT NULL,
                           status VARCHAR(20) NOT NULL DEFAULT 'TODO',
                           achieved_value NUMERIC(10, 2) NOT NULL DEFAULT 0,
                           completed_at TIMESTAMP,
                           create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           change_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 테이블 및 컬럼 코멘트 (DB 툴에서 설명 보기용)
COMMENT ON TABLE daily_log IS '일일 루틴 수행 기록 테이블';
COMMENT ON COLUMN daily_log.seq_no IS '일일 기록 고유번호 (PK)';
COMMENT ON COLUMN daily_log.cycle_seq_no IS '부모 사이클 고유번호 (FK)';
COMMENT ON COLUMN daily_log.member_seq_no IS '회원 고유번호';
COMMENT ON COLUMN daily_log.todo_ymd IS '루틴을 수행해야 하는 지정 날짜';
COMMENT ON COLUMN daily_log.status IS '일일 수행 상태 (TODO, DONE, SKIP 등)';
COMMENT ON COLUMN daily_log.achieved_value IS '오늘 하루 동안 달성한 수치';
COMMENT ON COLUMN daily_log.completed_at IS '사용자가 실제로 완료 처리를 한 시간';
COMMENT ON COLUMN daily_log.create_at IS '생성일시';
COMMENT ON COLUMN daily_log.change_at IS '변경일시';

