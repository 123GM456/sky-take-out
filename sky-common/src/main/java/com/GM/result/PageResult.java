package com.GM.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果封装。
 * <p>Service 层通过 PageHelper 完成分页查询后，将 total + records 封装为此类型返回给 Controller。</p>
 *
 * @param <T> 记录类型
 */
@Data
@AllArgsConstructor      // 生成全参构造器（new PageResult<>(total, records)）
@NoArgsConstructor       // 无参构造器，供 Jackson 反序列化时使用
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 总记录数（满足查询条件的全部条数，非当前页条数） */
    private long total;

    /** 当前页数据列表 */
    private List<T> records;
}