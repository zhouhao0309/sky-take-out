package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: sky-take-out
 * @description:口味mapper接口
 * @author: 周浩
 * @create: 2025-10-16 14:53
 **/
@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入菜品口味数据
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);
}
