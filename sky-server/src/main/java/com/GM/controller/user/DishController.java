package com.GM.controller.user;

import com.GM.result.Result;
import com.GM.service.DishService;
import com.GM.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")  // 指定 Bean 名称，避免与管理端同名 Controller 冲突
@RequestMapping("/user/dish")
@RequiredArgsConstructor
@Slf4j
public class DishController {

    private final DishService dishService;

    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        List<DishVO> list = dishService.listWithFlavor(categoryId);
        return Result.success(list);
    }
}