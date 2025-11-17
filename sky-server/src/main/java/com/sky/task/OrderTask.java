package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: sky-take-out
 * @description:定时任务类
 * @author: 周浩
 * @create: 2025-11-16 14:53
 **/
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单
     */
    @Scheduled(cron = "0 * * * * ?")  //每分钟执行一次
//    @Scheduled(cron = "0/5 * * * * ? ")

    public void processTimeoutOrder() {
        log.info("开始处理超时订单：{}", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> orderTime = orderMapper.getBystatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);
        if (orderTime != null && orderTime.size() > 0) {
            for (Orders order : orderTime) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }


    /**
     * 处理一直处于派送中的订单
     */
    @Scheduled(cron = "0 0 1 * * ?")  //每天凌晨1点执行一次
//    @Scheduled(cron = "0/5 * * * * ? ")

    public void processDeliverOrder() {
        log.info("处理支付超时订单：{}", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> orderTime = orderMapper.getBystatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);
        if (orderTime != null && orderTime.size() > 0) {
            for (Orders order : orderTime) {
                order.setStatus(Orders.COMPLETED);

                orderMapper.update(order);
            }
        }
    }

}
