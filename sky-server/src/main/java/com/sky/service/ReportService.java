package com.sky.service;

import com.sky.vo.TurnoverReportVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @program: sky-take-out
 * @description:
 * @author: 周浩
 * @create: 2025-11-20 19:38
 **/

public interface ReportService {


    /**
     * 统计指定时间区间内的营业额统计
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);
}
