package com.GM.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义 Jackson ObjectMapper，统一配置日期序列化/反序列化格式。
 * <p>Spring Boot 默认将 LocalDateTime 序列化为数组（如 [2026,9,16,21,30,0]），
 * 此处改为 yyyy-MM-dd HH:mm:ss 等可读字符串，方便前端直接展示。</p>
 *
 * <p>配置类 {@code WebMvcConfiguration} 中将其注册到 Spring MVC 消息转换器链首位，
 * 对所有 Controller 返回值生效。</p>
 */
public class JacksonObjectMapper extends ObjectMapper {

    /** LocalDateTime 序列化/反序列化格式 */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** LocalDate 序列化/反序列化格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /** LocalTime 序列化/反序列化格式 */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public JacksonObjectMapper() {
        super();

        // 反序列化时忽略 JSON 中 Java 对象不存在的字段，避免报错
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 不将日期写为时间戳数组（默认行为），改用自定义格式
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 注册 Java 8 时间类型（LocalDateTime / LocalDate / LocalTime）的序列化器
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));

        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));

        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        this.registerModule(javaTimeModule);
    }
}