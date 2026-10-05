package com.GM.controller.admin;

import com.GM.dto.SetmealPageQueryDTO;
import com.GM.entity.Setmeal;
import com.GM.result.PageResult;
import com.GM.result.Result;
import com.GM.service.SetmealService;
import com.GM.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端 — 套餐管理控制器（Controller 层）。
 * <p>接收前端套餐管理请求（新增、更新、删除、起停、分页、详情），调用 Service 处理。</p>
 */
@RestController                     // 将返回值自动序列化为 JSON 写入 HTTP 响应体
@RequestMapping("/admin/setmeal")  // 所有方法公用 URL 前缀
@RequiredArgsConstructor
@Slf4j
public class SetmealController {

    private final SetmealService setmealService;

    /**
     * 新增套餐。
     */
    @PostMapping
    public Result<Setmeal> save(@RequestBody Setmeal setmeal) {
        setmealService.save(setmeal);
        return Result.success(setmeal);
    }

    /**
     * 修改套餐。
     */
    @PutMapping
    public Result<Setmeal> update(@RequestBody Setmeal setmeal) {
        setmealService.update(setmeal);
        return Result.success(setmeal);
    }

    /**
     * 起售/停售套餐。
     *
     * @param status 目标状态：1=起售，0=停售
     * @param id     套餐 ID
     */
    @PostMapping("/status/{status}")
    public Result setStatus(@PathVariable Integer status, Long id) {
        log.info("起售停售套餐：status={}, id={}", status, id);
        setmealService.setStatus(status, id);
        return Result.success();
    }

    /**
     * 批量删除套餐。
     *
     * @param ids 待删除的套餐 ID 列表（查询参数，如 ?ids=1&ids=2）
     */
    // @RequestParam：将查询参数 ids 绑定为 List<Long>
    @DeleteMapping
    public Result deleteByIds(@RequestParam List<Long> ids) {
        log.info("批量删除套餐：{}", ids);
        setmealService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 套餐分页查询。
     *
     * @param setmealPageQueryDTO 分页参数（page、pageSize、name、categoryId、status）
     */
    @GetMapping("/page")
    public Result<PageResult<SetmealVO>> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询：{}", setmealPageQueryDTO);
        PageResult<SetmealVO> pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 查询套餐详情。
     *
     * @param id 套餐 ID（路径变量）
     */
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }
}