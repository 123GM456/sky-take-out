package com.GM.mapper;


import com.GM.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * 用户数据访问接口（Mapper / DAO 层）。
 * <p>定义 user 表的 CRUD 方法，SQL 写在 {@code resources/mapper/UserMapper.xml} 中。
 * 简单查询用 {@code @Select} 注解直接写 SQL，复杂查询（动态 SQL）用 XML 配置。</p>
 */
@Mapper
public interface UserMapper {

    /**
     * 按 openid 查询用户（登录时检查用户是否存在）。
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 新增用户。
     */
    void insert(User user);
}
