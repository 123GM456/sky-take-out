package com.GM.service;

import java.util.List;

import com.GM.dto.AddressBookDTO;
import com.GM.entity.AddressBook;

/**
 * 地址本业务逻辑接口（Service 层）。
 */
public interface AddressBookService {

    /**
     * 查看地址本（当前用户的地址本列表）。
     * @return 地址本列表
     */
    List<AddressBook> list();

    /**
     * 添加地址本。
     * @param addressBookDTO 待添加的地址本
     */
	void add(AddressBookDTO addressBookDTO);

    /**
     * 更新地址本。
     * @param addressBookDTO 待更新的地址本
     */
	void update(AddressBookDTO addressBookDTO);

    /**
     * 按 ID 查询地址本。
     * @param id 地址本 ID
     * @return 地址本实体
     */
	AddressBook getById(Long id);

    /**
     * 按 ID 删除地址本。
     * @param id 地址本 ID
     */
	void deleteById(Long id);

    /**
     * 查询默认地址本。
     * @return 默认地址本实体
     */
	AddressBook getDefault();

    /**
     * 将地址设为默认地址。
     * @param addressBookDTO 地址本 ID
     */
	void updateDefault(AddressBookDTO addressBookDTO);

}
