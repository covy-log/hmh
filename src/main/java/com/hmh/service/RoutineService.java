package com.hmh.service;

import com.hmh.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final MemberService memberService;

    private final RoutineRepository routineRepository;


}
