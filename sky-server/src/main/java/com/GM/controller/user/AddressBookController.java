package com.GM.controller.user;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GM.dto.AddressBookDTO;
import com.GM.entity.AddressBook;
import com.GM.result.Result;
import com.GM.service.AddressBookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户端 — 地址本控制器（Controller 层）。
 * <p>接收微信小程序地址本相关请求（添加、查询、删除），调用 Service 处理。</p>
 */
@RestController("userAddressBookController")  // 指定 Bean 名称，避免与其他端同名 Controller 冲突
@RequestMapping("/user/addressBook")          // 所有方法公用 URL 前缀
@RequiredArgsConstructor                       // 为 final 字段生成构造器注入
@Slf4j
public class AddressBookController {

    public final AddressBookService addressBookService;

    /**
     * 查看地址本（当前用户的地址本列表）。
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list() {
        log.info("查看地址本");
        List<AddressBook> list = addressBookService.list();
        return Result.success(list);
    }

    @PostMapping
    public Result add(@RequestBody AddressBookDTO addressBookDTO) {
        log.info("添加地址本");
        addressBookService.add(addressBookDTO);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody AddressBookDTO addressBookDTO) {
        log.info("修改地址本");
        addressBookService.update(addressBookDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id) {
        log.info("根据 ID 查询地址本");
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        log.info("根据 ID 删除地址本");
        addressBookService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/default")
    public Result<AddressBook> getDefault() {
        log.info("查询默认地址本");
        AddressBook addressBook = addressBookService.getDefault();
        return Result.success(addressBook);
    }

    @PutMapping("/default")
    public Result updateDefault(@RequestBody AddressBookDTO addressBookDTO) {
        log.info("将地址设为默认地址");
        addressBookService.updateDefault(addressBookDTO);
        return Result.success();
    }

}
