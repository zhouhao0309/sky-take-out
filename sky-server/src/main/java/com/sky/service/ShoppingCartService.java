package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @program: sky-take-out
 * @description:
 * @author: 周浩
 * @create: 2025-11-05 14:49
 **/
public interface ShoppingCartService {
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);


    List<ShoppingCart> showShoppingCart();

    void cleanShoppingCart();
}
