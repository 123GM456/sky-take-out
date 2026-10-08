package com.GM.service.impl;

import com.GM.dto.AddressBookDTO;    
import com.GM.context.BaseContext;
import com.GM.entity.AddressBook;
import com.GM.mapper.AddressBookMapper;
import com.GM.service.AddressBookService;

import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 地址簿业务逻辑实现（Service 层）。
 */
@Service // 标记为 Service 层 Bean，Spring 自动注册
@RequiredArgsConstructor // 为 final 字段生成构造器注入
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {

    private final AddressBookMapper addressBookMapper;

    /**
     * 查询当前登录用户的地址簿列表。
     */
    @Override
    public List<AddressBook> list() {
        AddressBook addressBook = new AddressBook();
        // 用户 ID 由登录拦截器解析 token 后存入线程上下文
        addressBook.setUserId(BaseContext.getCurrentId());
        return addressBookMapper.list(addressBook);
    }

    /**
     * 添加地址本。
     * @param addressBook 待添加的地址本
     */
    @Override
    public void add(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = new AddressBook();
        // 转换为实体类
        BeanUtils.copyProperties(addressBookDTO, addressBook);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setCreateTime(LocalDateTime.now());
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBookMapper.insert(addressBook);
    }

    /**
     * 更新地址本。
     * @param addressBookDTO 待更新的地址本
     */
    @Override
    public void update(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = new AddressBook();
        // 转换为实体类
        BeanUtils.copyProperties(addressBookDTO, addressBook);
        // 用户 ID 由登录拦截器解析 token 后存入线程上下文，同时作为 SQL 的归属校验条件
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBookMapper.update(addressBook);
    }

    /**
     * 按 ID 查询地址本。
     * @param id 地址本 ID
     * @return 地址本实体
     */
    @Override
    public AddressBook getById(Long id) {
        // 用户 ID 由登录拦截器解析 token 后存入线程上下文，作为归属校验条件
        return addressBookMapper.getById(id, BaseContext.getCurrentId());
    }

    /**
     * 按 ID 删除地址本。
     * @param id 地址本 ID
     */
    @Override
    public void deleteById(Long id) {
        // 用户 ID 由登录拦截器解析 token 后存入线程上下文，作为归属校验条件
        addressBookMapper.deleteById(id, BaseContext.getCurrentId());
    }

    /**
     * 查询默认地址本。
     * @return 默认地址本实体
     */
    @Override
    public AddressBook getDefault() {
        // 用户 ID 由登录拦截器解析 token 后存入线程上下文
        return addressBookMapper.getDefault(BaseContext.getCurrentId());
    }

    /**
     * 将地址设为默认地址。
     * @param addressBookDTO 地址本 ID
     */
    @Override
    public void updateDefault(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = new AddressBook();
        // 转换为实体类
        BeanUtils.copyProperties(addressBookDTO, addressBook);
        // 用户 ID 由登录拦截器解析 token 后存入线程上下文，同时作为 SQL 的归属校验条件
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }
    
}