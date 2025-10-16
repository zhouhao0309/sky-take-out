package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * @program: sky-take-out
 * @description:
 * @author: 周浩
 * @create: 2025-10-16 11:42
 **/
public interface DishService {
    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);
}
