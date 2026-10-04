package com.GM.controller.user;

import com.GM.entity.Category;
import com.GM.result.Result;
import com.GM.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")  // 指定 Bean 名称，避免与管理端同名 Controller 冲突
@RequestMapping("/user/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<Category>> list() {
        List<Category> list = categoryService.list(1L);
        return Result.success(list);
    }
}