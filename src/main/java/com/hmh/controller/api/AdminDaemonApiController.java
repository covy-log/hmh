package com.hmh.controller.api;

import com.hmh.daemon.service.DaemonDailyLogService;
import com.hmh.daemon.service.DaemonRoutineCycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 어드민 전용 데몬 수동 실행 API.
 * "/admin/**" 경로라 AdminCheckInterceptor 가 먼저 ADMIN 권한을 검증한다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/daemon")
public class AdminDaemonApiController {

    private final DaemonRoutineCycleService daemonRoutineCycleService;

    private final DaemonDailyLogService daemonDailyLogService;

    @PostMapping("/routine-cycle")
    public ResponseEntity<String> runRoutineCycleDaemon() {
        daemonRoutineCycleService.createWeeklyCycle();
        return ResponseEntity.ok("RoutineCycleDaemon 실행 완료");
    }

    @PostMapping("/daily-log")
    public ResponseEntity<String> runDailyLogDaemon() {
        daemonDailyLogService.createDailyLog();
        return ResponseEntity.ok("DailyLogDaemon 실행 완료");
    }
}
