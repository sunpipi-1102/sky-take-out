package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;
    public static final String KEY = "SHOP_STATUS";

    @PutMapping("/{status}")
    @ApiOperation("获取店铺状态")
    public Result getStatus(@PathVariable Integer status) {
        log.info("店铺的营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(KEY,status);
        return Result.success();
    }

    /**
     * 获取店铺的营业状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取到店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
