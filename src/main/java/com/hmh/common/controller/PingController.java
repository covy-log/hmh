package com.hmh.common.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    private final JdbcTemplate jdbcTemplate;

    public PingController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping(value = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> ping() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(503).body("db-down");
        }
    }
}