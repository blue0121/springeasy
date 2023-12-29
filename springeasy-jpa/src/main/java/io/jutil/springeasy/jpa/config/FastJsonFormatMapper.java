package io.jutil.springeasy.jpa.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.core.util.JsonUtil;
import io.jutil.springeasy.jpa.sql.SqlSnippetFacade;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.format.FormatMapper;

/**
 * @author Jin Zheng
 * @since 2023-10-27
 */
@Slf4j
@NoArgsConstructor
public class FastJsonFormatMapper implements FormatMapper {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T fromString(CharSequence str, JavaType<T> type, WrapperOptions options) {
        var json = SqlSnippetFacade.getJsonString(str.toString());
        var clazz = type.getJavaTypeClass();
        if (clazz == JSONObject.class) {
            return (T) JSON.parseObject(json);
        } else if (clazz == JSONArray.class) {
            return (T) JSON.parseArray(json);
        }
        return JsonUtil.fromString(json, clazz);
    }

    @Override
    public <T> String toString(T value, JavaType<T> type, WrapperOptions options) {
        return JsonUtil.toString(value);
    }

}
