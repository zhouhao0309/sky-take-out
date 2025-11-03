package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @program: sky-take-out
 * @description:菜品管理
 * @author: 周浩
 * @create: 2025-10-16 11:18
 **/
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

//    @PostMapping("/save")
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        //清理已经缓存在redis的菜品数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);
        return Result.success();

    }

    /**
     * 分页查询代码
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除菜品，ids：{}",ids);
        dishService.deleteBatch(ids);

        //将所有菜品缓存数据清理掉，所有以dish_ 开头的key
        cleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品和对应的口味")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据id查询菜品信息：{}",id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);

        //清理所有以dish_ 开头的缓存数据
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 菜品启售/停售
     */

    @PostMapping("/status/{status}")
    @ApiOperation("菜品启售/停售")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("菜品起售或停售：{},{}",status,id);
        dishService.startOrStop(status,id);
        //清理所有以dish_ 开头的缓存数据
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 根据分类id查询菜品选项
     */

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品选项")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("根据分类id查询菜品选项：{}", categoryId);
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 清理缓存数据
     * @param key
     */
    private void cleanCache(String key) {
        Set keys = redisTemplate.keys(key);
        redisTemplate.delete(keys);
    }

}
