package com.GM.mapper;

import com.GM.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地址簿数据访问接口（Mapper / DAO 层）。
 * <p>
 * 复杂 SQL（含动态条件）写在对应的 XML 中。
 * </p>
 */
@Mapper // MyBatis 标记为 Mapper 接口，Spring 自动扫描注册
public interface AddressBookMapper {

    /**
     * 条件查询地址簿。
     * <p>
     * 按非空字段动态拼接条件（userId、isDefault），
     * 用于查询某用户的地址列表，或查询该用户的默认地址。
     * </p>
     *
     * @param addressBook 查询条件
     * @return 符合条件的地址列表
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 新增地址。
     * <p>
     * 新增地址时，默认设为非默认地址。
     * </p>
     *
     * @param addressBook 待插入的地址记录
     */
    void insert(AddressBook addressBook);

    /**
     * 修改地址（XML 中使用 <set> 动态 SQL，只更新非空字段）。
     *
     * @param addressBook 含 id、userId 与待修改字段的地址记录
     */
    void update(AddressBook addressBook);

    /**
     * 按 ID 查询地址（带归属校验）。
     *
     * @param id     地址 ID
     * @param userId 当前登录用户 ID，确保只能查到自己的地址
     * @return 地址详情，不存在或不属于该用户时返回 null
     */
    AddressBook getById(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 按 ID 删除地址（带归属校验）。
     *
     * @param id     地址 ID
     * @param userId 当前登录用户 ID，确保只能删除自己的地址
     */
    void deleteById(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 将指定用户的所有地址设为非默认。
     * <p>
     * 设置新默认地址前先调用，保证一个用户最多只有一个默认地址。
     * </p>
     *
     * @param addressBook 含 userId 与目标 isDefault 的记录
     */
    void updateIsDefaultByUserId(AddressBook addressBook);

    /**
     * 查询指定用户的默认地址。
     *
     * @param userId 当前登录用户 ID，确保只能查到自己的默认地址
     * @return 默认地址，不存在时返回 null
     */
    AddressBook getDefault(Long userId);
}