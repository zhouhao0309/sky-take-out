package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: sky-take-out
 * @description:
 * @author: 周浩
 * @create: 2025-11-16 14:19
 **/
@Component
@Slf4j
public class MyTask {
//    @Scheduled(cron = "0/5 * * * * ? ")
    public void myTask(){
        log.info("定时任务开始执行");
    }
}
