package com.GM.controller.admin;

import com.GM.result.Result;
import com.GM.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")  // 指定Bean名称，避免与user包的ShopController冲突
@RequestMapping("/admin/shop")  // 所有方法公用 URL 前缀
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;

    /**
     * 设置店铺状态
     */
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status) {
        shopService.setStatus(status);
        log.info("设置店铺营业状态：{}", status==1?"营业":"打烊");
        return Result.success();
    }

    /**
     * 获取店铺营业状态。
     * @return 店铺营业状态：1=营业，0=打烊
     */
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        Integer status = shopService.getStatus();
        log.info("获取店铺营业状态:{}", status==1?"营业":"打烊");
        return Result.success(status);
    }
}