package com.GM.controller.user;

import com.GM.constant.StatusConstant;
import com.GM.entity.Category;
import com.GM.result.Result;
import com.GM.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 查询分类列表。
     *
     * @param type 分类类型（可选）：1=菜品分类，2=套餐分类，不传则查全部启用的分类
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        log.info("用户端查询分类列表：type={}", type);
        Category category = new Category();
        // type 为空时不设置类型，表示查询全部分类
        if (type != null) {
            category.setType(type);
        }
        category.setStatus(StatusConstant.ENABLE);

        List<Category> list = categoryService.list(category);
        return Result.success(list);
    }
}