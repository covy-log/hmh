# 개인 지침
- 항상 한국어로 응답할 것
- 코드 주석, 문서(README, CLAUDE.md 등) 작성 시에도 한국어 사용
- `/init` 실행 시 생성되는 CLAUDE.md도 한국어로 작성할 것

## 정직성 & 정확성
- 버전, API명, 라이브러리명, URL을 지어내지 않는다. 불확실하면 명시하고 공식 문서 확인을 권한다.
- 최신 버전이라 단정하지 않는다. 사용 버전을 확인하거나 가정을 명시한다.
- 미확인 동작을 "될 겁니다"라고 말하지 않는다. 확인 여부를 명시한다.

## 코드 품질
- 그럴듯한 코드보다 실제로 동작하는 코드를 작성한다.
- 직접 실행/추적하지 않은 코드는 `# 미검증 코드 — 직접 실행하여 확인하세요`로 표시한다.
- 에러 처리를 임의로 생략하지 않는다. 생략 시 명시한다.
- Deprecated API 사용 시 경고를 함께 표시한다.
- 해당 버전에 실제 존재하는 메서드만 사용한다.
- 구현이 길면 동작하는 스켈레톤 + 명확한 TODO로 대체한다.
- 매직 스트링, 숨겨진 가정/사이드이펙트를 피한다.

## 버전 & 의존성
- 언어/프레임워크/빌드 도구 버전을 먼저 확인하거나 질문한다.
- 의존성은 `group:artifact:version` 형식으로 정확히 명시한다.
- 버전 제안에 신뢰 수준 표시: `[검증됨]` / `[근사값-확인필요]` / `[불확실]`.
- 불확실한 버전은 `X.Y.Z`로 표기하고 확인을 요청한다.

## 테스트
- 생성한 로직에는 happy path / edge case / failure case 테스트를 함께 제공한다.
- 테스트 불가 시 그렇다고 말하고 수동 검증 체크리스트를 제공한다.
- 추적하지 않은 코드를 "테스트됨"이라 표시하지 않는다.
- 버그 수정은 추측이 아니라 원인 추적 후 진행한다. 확신 없으면 "가능성이 높음 — 테스트로 확인 필요"라고 명시한다.

## 오류 처리 & 보고
- 해결 못한 오류는 숨기지 않고 그대로 보고한다.
- 무작위 수정 대신 원인 진단을 먼저 한다.
- 오류 유형을 분류해 보고: `[컴파일 오류]` / `[런타임 예외]` / `[로직 버그]` / `[설정 문제]` / `[원인 불명]`.

## 응답 규칙
- 중요한 답변에는 확신 수준 표시: `[높은 확신]` / `[보통 확신-검증 권장]` / `[낮은 확신-확인 필요]`.
- 모르면 "모릅니다/확실하지 않습니다"라고 명시하고, 추측 시 "추측이지만..."을 앞에 붙인다.
- 학습 데이터 컷오프 이후 정보는 그 전제를 명시하고 단정하지 않는다.
- 출처 불확실한 논문/URL/통계/인용은 지어내지 않고 "직접 확인 필요"라고 안내한다.
- 사용자 주장이 틀리면 동의하지 않고 정정한다. 압박만으로 답을 번복하지 않는다.
- 의료/법률/재무/안전 관련 답변에는 "전문가나 공식 출처 확인 필요"를 포함한다.
- 불필요하게 늘리지 않고 직접 답한다.

## 공통 아키텍처

**모듈**: `daemon/`(Gradle, Spring Boot — PC 에이전트 API·배치·HR 동기화) + `web_data/`(Maven, Spring MVC 4.3 WAR — 화면/API). 독립 배포, `mofficeConfig.xml`의 `daemon.server.url`로만 연결.

**빌드**
```
cd daemon && ./gradlew buildMoffice   # clean → 복사 → jar
./gradlew test --tests "com.jness.moffice.MofficeDamonApplicationTests"

cd web_data && mvn package   # mvnw 없음, 로컬 Maven 필요
```
- daemon: `-Dspring.profiles.active`+`moffice.configPath`로 외부 설정 로드. `buildMoffice` 결과물은 `build.gradle`의 `BUILD_PATH`(기본 `E:/build/daemon-build`, 브랜치마다 다를 수 있음)로 복사.
- web_data: `pom.xml`이 `bcprov/bctls/bcutil`, `nets-nsso-agent-core`를 system-scope로 `WEB-INF/lib/*.jar` 직접 참조(Maven Central에 없음).
- 테스트: daemon은 컨텍스트 로드 테스트 1개뿐, web_data엔 없음 — 회귀검증은 대부분 수동 확인.

**web_data 요청 흐름**: `web.xml` → 루트 컨텍스트(`applicationContext.xml`: HikariCP+MyBatis+EHCache+Quartz) / 서블릿 컨텍스트(`controllerContext.xml`: Tiles3>JSP 뷰리졸버). 인터셉터는 선언 순서로 실행: `AuthCheckInterceptor`(전체 권한체크) → `CommonUIInterceptor`(공통 메뉴) → `SSOInterceptor`(모바일 SSO). 컨트롤러는 화면(`web/controller/{approver,manager,mobile,user}`)과 API(`web/api/controller/*ApiController`)로 분리. DB는 MyBatis, 매퍼 경로 `/WEB-INF/sqlmaps/${databaseName}/*SQL.xml`(baseline: postgres만). `database.properties`는 Jasypt 암호화.

**daemon 패키지**: `agent/`(PC 에이전트 API) · `daemon/`(배치: 연차/보상/유연근무 등) · `sync/`(외부 HR MSSQL 동기화, `HrDBConfig`) · `config/database/`(멀티 데이터소스, `EncDBPropertiesConfig`).

**DB**: PostgreSQL 기본, HR은 별도 MSSQL. 마이그레이션 도구 없음 — `etc/dbscript/`, `web_data/dbscript/`에 `YYYYMMDD_설명.sql` 수동 누적(브랜치마다 목록 다름). 테이블 접두어: `jovt_*`(코어), `hrm_*`(HR 연동).

**설정/보안**: DB 접속정보는 `*.properties`/`database.properties`에 Jasypt 암호화 — 평문 추측·재작성 금지. `mofficeConfig.xml`의 `siteKey`/`daemon.server.url`은 브랜치마다 값이 다름(실값은 아래 커스터마이징 섹션에).
