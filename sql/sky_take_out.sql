-- ============================================================
-- 数据库初始化脚本：sky_take_out
-- 技术栈：MySQL 8.x
-- 字符集：utf8mb4 / utf8mb4_unicode_ci
-- 存储引擎：InnoDB
-- 说明：本脚本用于创建苍穹外卖系统数据库及11张核心业务表
-- ============================================================

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS sky_take_out
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE sky_take_out;

-- ============================================================
-- 1. employee —— 员工表（商家后台员工）
-- ============================================================
CREATE TABLE IF NOT EXISTS employee (
    id          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '主键',
    username    VARCHAR(32)  NOT NULL                 COMMENT '用户名',
    password    VARCHAR(64)  NOT NULL                 COMMENT '密码',
    name        VARCHAR(32)  NOT NULL                 COMMENT '员工姓名',
    phone       VARCHAR(11)                           COMMENT '手机号',
    sex         CHAR(1)                               COMMENT '性别',
    id_number   VARCHAR(18)                           COMMENT '身份证号',
    status      INT          NOT NULL DEFAULT 1       COMMENT '状态：1正常，0禁用',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user BIGINT                                COMMENT '创建人ID',
    update_user BIGINT                                COMMENT '修改人ID',
    PRIMARY KEY (id),
    UNIQUE KEY uk_employee_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工表';

-- ============================================================
-- 2. category —— 分类表（菜品分类/套餐分类）
-- ============================================================
CREATE TABLE IF NOT EXISTS category (
    id          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '主键',
    type        INT          NOT NULL                 COMMENT '类型：1=菜品分类，2=套餐分类',
    name        VARCHAR(32)  NOT NULL                 COMMENT '分类名称',
    sort        INT          NOT NULL DEFAULT 0       COMMENT '排序序号',
    status      INT          NOT NULL DEFAULT 1       COMMENT '状态：1=启用，0=禁用',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user BIGINT                                COMMENT '创建人ID',
    update_user BIGINT                                COMMENT '修改人ID',
    PRIMARY KEY (id),
    UNIQUE KEY uk_category_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分类表';

-- ============================================================
-- 3. dish —— 菜品表
-- ============================================================
CREATE TABLE IF NOT EXISTS dish (
    id          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '主键',
    name        VARCHAR(32)   NOT NULL                 COMMENT '菜品名称',
    category_id BIGINT        NOT NULL                 COMMENT '所属分类ID',
    price       DECIMAL(10,2) NOT NULL                 COMMENT '菜品价格',
    image       VARCHAR(255)                           COMMENT '菜品图片',
    description VARCHAR(255)                           COMMENT '菜品描述',
    status      INT           NOT NULL DEFAULT 1       COMMENT '状态：1=起售，0=停售',
    sort        INT           NOT NULL DEFAULT 0       COMMENT '排序序号',
    create_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user BIGINT                                 COMMENT '创建人ID',
    update_user BIGINT                                 COMMENT '修改人ID',
    PRIMARY KEY (id),
    INDEX idx_dish_category_id (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜品表';

-- ============================================================
-- 4. dish_flavor —— 菜品口味表
-- ============================================================
CREATE TABLE IF NOT EXISTS dish_flavor (
    id          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '主键',
    dish_id     BIGINT       NOT NULL                 COMMENT '所属菜品ID',
    name        VARCHAR(32)  NOT NULL                 COMMENT '口味名称（如：辣度、口味）',
    value       VARCHAR(255)                          COMMENT '口味值（如：微辣、中辣）',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user BIGINT                                COMMENT '创建人ID',
    update_user BIGINT                                COMMENT '修改人ID',
    PRIMARY KEY (id),
    INDEX idx_dish_flavor_dish_id (dish_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜品口味表';

-- ============================================================
-- 5. setmeal —— 套餐表
-- ============================================================
CREATE TABLE IF NOT EXISTS setmeal (
    id          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '主键',
    category_id BIGINT        NOT NULL                 COMMENT '所属分类ID',
    name        VARCHAR(32)   NOT NULL                 COMMENT '套餐名称',
    price       DECIMAL(10,2) NOT NULL                 COMMENT '套餐价格',
    status      INT           NOT NULL DEFAULT 1       COMMENT '状态：1=起售，0=停售',
    description VARCHAR(255)                           COMMENT '套餐描述',
    image       VARCHAR(255)                           COMMENT '套餐图片',
    create_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_user BIGINT                                 COMMENT '创建人ID',
    update_user BIGINT                                 COMMENT '修改人ID',
    PRIMARY KEY (id),
    INDEX idx_setmeal_category_id (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='套餐表';

-- ============================================================
-- 6. setmeal_dish —— 套餐菜品关系表（多对多中间表）
-- ============================================================
CREATE TABLE IF NOT EXISTS setmeal_dish (
    id          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '主键',
    setmeal_id  BIGINT        NOT NULL                 COMMENT '套餐ID',
    dish_id     BIGINT        NOT NULL                 COMMENT '菜品ID',
    name        VARCHAR(32)                            COMMENT '菜品名称（冗余）',
    price       DECIMAL(10,2)                          COMMENT '菜品单价（冗余）',
    copies      INT           NOT NULL DEFAULT 1       COMMENT '份数',
    PRIMARY KEY (id),
    UNIQUE KEY uk_setmeal_dish (setmeal_id, dish_id),
    INDEX idx_setmeal_dish_dish_id (dish_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='套餐菜品关系表';

-- ============================================================
-- 7. user —— 用户表（外卖顾客）
-- ============================================================
CREATE TABLE IF NOT EXISTS user (
    id          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '主键',
    openid      VARCHAR(45)  UNIQUE                   COMMENT '微信用户唯一标识',
    name        VARCHAR(32)                            COMMENT '用户姓名',
    phone       VARCHAR(11)                            COMMENT '手机号',
    sex         CHAR(1)                                COMMENT '性别',
    id_number   VARCHAR(18)                            COMMENT '身份证号',
    avatar      VARCHAR(500)                           COMMENT '头像URL',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表（C端顾客）';

-- ============================================================
-- 8. address_book —— 地址表
-- ============================================================
CREATE TABLE IF NOT EXISTS address_book (
    id              BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '主键',
    user_id         BIGINT       NOT NULL                 COMMENT '用户ID',
    consignee       VARCHAR(50)                            COMMENT '收货人',
    phone           VARCHAR(11)                            COMMENT '联系电话',
    sex             CHAR(1)                                COMMENT '性别',
    province_code   VARCHAR(12)                            COMMENT '省级行政区编码',
    province_name   VARCHAR(32)                            COMMENT '省份名称',
    city_code       VARCHAR(12)                            COMMENT '市级行政区编码',
    city_name       VARCHAR(32)                            COMMENT '城市名称',
    district_code   VARCHAR(12)                            COMMENT '区级行政区编码',
    district_name   VARCHAR(32)                            COMMENT '区县名称',
    detail          VARCHAR(200)                           COMMENT '详细地址',
    label           VARCHAR(100)                           COMMENT '地址标签（如：家、公司）',
    is_default      TINYINT      NOT NULL DEFAULT 0        COMMENT '是否默认地址：1=是，0=否',
    create_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_address_book_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='地址簿表';

-- ============================================================
-- 9. shopping_cart —— 购物车表
-- ============================================================
CREATE TABLE IF NOT EXISTS shopping_cart (
    id          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '主键',
    name        VARCHAR(32)   NOT NULL                 COMMENT '商品名称',
    image       VARCHAR(255)                           COMMENT '商品图片',
    user_id     BIGINT        NOT NULL                 COMMENT '用户ID',
    dish_id     BIGINT                                 COMMENT '菜品ID',
    setmeal_id  BIGINT                                 COMMENT '套餐ID',
    dish_flavor VARCHAR(255)                           COMMENT '菜品口味',
    number      INT           NOT NULL DEFAULT 1       COMMENT '数量',
    amount      DECIMAL(10,2) NOT NULL                 COMMENT '金额',
    create_time DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    INDEX idx_shopping_cart_user_id (user_id),
    INDEX idx_shopping_cart_dish_id (dish_id),
    INDEX idx_shopping_cart_setmeal_id (setmeal_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车表';

-- ============================================================
-- 10. orders —— 订单表（注意：表名使用 orders 而非 order）
-- ============================================================
CREATE TABLE IF NOT EXISTS orders (
    id                      BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '主键',
    number                  VARCHAR(50)   NOT NULL                 COMMENT '订单号',
    status                  INT           NOT NULL DEFAULT 1       COMMENT '订单状态',
    user_id                 BIGINT        NOT NULL                 COMMENT '用户ID',
    address_book_id         BIGINT                                 COMMENT '地址簿ID',
    order_time              DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    checkout_time           DATETIME                               COMMENT '支付/结账时间',
    pay_method              INT                                    COMMENT '支付方式：1=微信，2=支付宝',
    pay_status              INT           NOT NULL DEFAULT 0       COMMENT '支付状态：0=未支付，1=已支付',
    amount                  DECIMAL(10,2) NOT NULL                 COMMENT '订单金额',
    remark                  VARCHAR(255)                           COMMENT '订单备注',
    phone                   VARCHAR(11)                            COMMENT '收货手机号（快照）',
    address                 VARCHAR(255)                           COMMENT '收货地址（快照）',
    consignee               VARCHAR(50)                            COMMENT '收货人（快照）',
    cancel_reason           VARCHAR(255)                           COMMENT '取消原因',
    rejection_reason        VARCHAR(255)                           COMMENT '拒单原因',
    cancel_time             DATETIME                               COMMENT '取消时间',
    estimated_delivery_time DATETIME                               COMMENT '预计送达时间',
    delivery_status         INT                                    COMMENT '配送状态',
    delivery_time           DATETIME                               COMMENT '送达时间',
    pack_amount             INT                                    COMMENT '打包费',
    tableware_number        INT                                    COMMENT '餐具数量',
    tableware_status        INT                                    COMMENT '餐具状态',
    create_time             DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time             DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_orders_number (number),
    INDEX idx_orders_user_id (user_id),
    INDEX idx_orders_status (status),
    INDEX idx_orders_order_time (order_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ============================================================
-- 11. order_detail —— 订单明细表
-- ============================================================
CREATE TABLE IF NOT EXISTS order_detail (
    id          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '主键',
    name        VARCHAR(32)   NOT NULL                 COMMENT '商品名称',
    order_id    BIGINT        NOT NULL                 COMMENT '所属订单ID',
    dish_id     BIGINT                                 COMMENT '菜品ID',
    setmeal_id  BIGINT                                 COMMENT '套餐ID',
    dish_flavor VARCHAR(255)                           COMMENT '菜品口味',
    number      INT           NOT NULL DEFAULT 1       COMMENT '数量',
    amount      DECIMAL(10,2) NOT NULL                 COMMENT '金额',
    image       VARCHAR(255)                           COMMENT '商品图片',
    PRIMARY KEY (id),
    INDEX idx_order_detail_order_id (order_id),
    INDEX idx_order_detail_dish_id (dish_id),
    INDEX idx_order_detail_setmeal_id (setmeal_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';

-- ============================================================
-- 初始化数据：插入默认管理员账号
-- 密码为明文（初始阶段，后续会改为加密方式）
-- ============================================================
INSERT INTO employee (username, password, name, phone, sex, id_number, status, create_time, update_time, create_user, update_user)
VALUES ('root', '050826', '管理员', NULL, NULL, NULL, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1);