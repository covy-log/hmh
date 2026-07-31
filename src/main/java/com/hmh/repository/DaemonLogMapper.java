package com.hmh.repository;

import com.hmh.domain.DaemonLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DaemonLogMapper {

    /**
     * 데몬 실행 시작 이력 저장 (status = RUNNING)
     * @param daemonLog
     */
    void save(DaemonLog daemonLog);

    /**
     * 데몬 실행 종료 이력 반영 (종료시각, 성공/실패 건수, 최종 상태, 에러메시지)
     * @param daemonLog
     */
    void update(DaemonLog daemonLog);
}
