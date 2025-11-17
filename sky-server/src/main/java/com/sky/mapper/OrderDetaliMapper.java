package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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


    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> getByOrderId(Long id);
}
