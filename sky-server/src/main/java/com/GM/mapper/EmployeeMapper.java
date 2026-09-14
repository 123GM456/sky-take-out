package com.GM.mapper;

import com.GM.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("SELECT * FROM employee WHERE username = #{username}")
    Employee getByUsername(String username);

    @Select("SELECT * FROM employee WHERE (name LIKE CONCAT('%', #{name}, '%') OR #{name} IS NULL) ORDER BY create_time DESC LIMIT #{offset}, #{pageSize}")
    List<Employee> pageQuery(String name, Integer offset, Integer pageSize);

    @Select("SELECT COUNT(*) FROM employee WHERE (name LIKE CONCAT('%', #{name}, '%') OR #{name} IS NULL)")
    Long countQuery(String name);

    @Insert("INSERT INTO employee (name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) VALUES (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

}
