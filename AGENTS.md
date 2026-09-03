# AGENTS.md

## Project

Java 17 / Spring Boot 3.4.4 single-module web app — a university book trading platform.
Frontend: static HTML + Bootstrap 5 + jQuery, served from `src/main/resources/static/`.
Backend: MyBatis XML mappers, MySQL 8, Alibaba Druid connection pool.

## Build & Run

```bash
# Build (requires Java 17 + Maven)
mvn clean package

# Run
mvn spring-boot:run

# Run built JAR
java -jar target/book-trading-platform-0.0.1-SNAPSHOT.jar
```

App starts on **http://localhost:8080**. No test files exist yet; `spring-boot-starter-test` is on classpath but unused.

## Database

MySQL database `book_trading` on localhost:3306. Schema is in `schema.sql` at project root.
Credentials are hardcoded in `src/main/resources/application.yaml` (username: root, password: tyh2968934950).

## Architecture

3-tier layout under `cn.edu.hdu`:

- `controller/` — REST endpoints (no session/auth middleware; userId passed as request param)
- `service/` + `service/impl/` — Business logic, `@Transactional` on order/ban/evaluation flows
- `mapper/` — MyBatis interfaces; XML in `src/main/resources/mappers/*.xml`
- `pojo/` — Data classes with Lombok `@Data`; `*VO` suffix = view objects
- `utils/` — `Result` (uniform API response wrapper), `ResultCodeEnum`, `PageResult`
- `config/` — `WebMvcConfig` serves uploaded book covers from filesystem

No Spring Security framework — only `spring-security-crypto` for BCrypt password hashing.
No token/session auth — all endpoints rely on client-side `sessionStorage` userId.

## Conventions

- API responses always use `Result.success(data)` or `Result.error(ResultCodeEnum.XXX)`
- Chinese string statuses in DB: book (`在售/已预定/已售出/已下架`), order (`待付款/已付款/已完成/已取消`)
- Book cover uploads go to `static/upload/books/` with UUID filenames; `.gitkeep` preserves the directory
- Pagination is manual offset/limit + count query (no pagination library)
- Order creation uses optimistic locking (`WHERE status = '在售'`) to prevent double-booking

## Key Files

- `schema.sql` — full DB schema (9 tables)
- `pom.xml` — all dependencies and build config
- `src/main/resources/application.yaml` — server, datasource, mybatis, upload path config
- `BookTradingPlatformApplication.java` — entry point, `@MapperScan("cn.edu.hdu.mapper")`
