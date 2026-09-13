package com.GM.result;

// Lombok：生成全参构造器，方便 new PageResult<>(total, records) 一行构造
import lombok.AllArgsConstructor;
// Lombok：自动生成 getter/setter、toString、equals、hashCode
import lombok.Data;
// Lombok：生成无参构造器，供框架（Jackson、MyBatis 等）反射创建对象时使用
import lombok.NoArgsConstructor;
// JDK：实现序列化接口，支持 Redis 缓存和跨进程传输
import java.io.Serializable;
// JDK：使用 List 类型承载分页数据行
import java.util.List;

/**
 * 分页查询结果封装。
 *
 * @param <T> 记录类型
 */
// Lombok：自动生成 getter/setter、toString、equals、hashCode、canEqual
@Data
// Lombok：生成全参构造器（用于 new PageResult<>(total, records)）
@AllArgsConstructor
// Lombok：生成无参构造器（用于框架反射创建对象）
@NoArgsConstructor
// 分页结果封装：携带总记录数 total 和当前页数据 records，用于分页查询接口
public class PageResult<T> implements Serializable {

    /** 总记录数 */
    private long total;

    /** 当前页数据 */
    private List<T> records;

}