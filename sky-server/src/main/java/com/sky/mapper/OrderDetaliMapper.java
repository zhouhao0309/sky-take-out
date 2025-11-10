package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: sky-take-out
 * @description:
 * @author: 周浩
 * @create: 2025-11-06 10:46
 **/
@Mapper
public interface OrderDetaliMapper {
    /**
     * 批量插入订单明细数据
     * @param orderDetailList
     */
    void insertBatch(List<OrderDetail> orderDetailList);
}
