package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: sky-take-out
 * @description:
 * @author: 周浩
 * @create: 2025-11-20 19:41
 **/
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 统计指定时间区间内的营业额统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>(); //存放begin到end范围内每天的日期
        dateList.add(begin);
        while (!begin.equals(end)) {
            //计算指定日期的下一天
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        //存放dateList中的日期对应的营业额数据
        List<Double> turnoverList = new ArrayList<>();

        for (LocalDate date : dateList) {
            //查询date日期对应的营业额数据（查询订单状态为已完成的订单金额合计）
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);

            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnoverList.add(turnover == null ? 0.0 : turnover);

        }


        //封装返回结果
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 统计指定时间区间内的用户统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        //获取日期列表(begin到end每天对应的日期)
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        //存放dateList中的日期对应的用户数据
        List<Integer> newUserList = new ArrayList<>();
        //存放每天的总用户数量
        List<Integer> totalUserList = new ArrayList<>();
        List<Integer> newAndTotalUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap<>();

            map.put("end", endTime);
            //总用户数量
            Integer totalUser = userMapper.countByMap(map);
            map.put("begin", beginTime);
            //新增用户数量
            Integer newUser = userMapper.countByMap(map);

            totalUserList.add(totalUser);
            newUserList.add(newUser);
        }
        log.info("dateList={}", dateList);

        //封装结果数据
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }
}
