package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.vo.UserLoginVO;

/**
 * @program: sky-take-out
 * @description:
 * @author: 周浩
 * @create: 2025-10-30 19:08
 **/
public interface UserService {
    /**
     * 微信账号密码登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);
}
