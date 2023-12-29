package io.jutil.springeasy.jpa.config;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.jutil.springeasy.jpa.sql.SqlSnippetFacade;
import org.hibernate.type.descriptor.java.JavaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * @author Jin Zheng
 * @since 2023-10-27
 */
class FastJsonFormatMapperTest {
    FastJsonFormatMapper mapper;
    SqlSnippetFacade snippet;

    FastJsonFormatMapperTest() {
        var prop = Mockito.mock(DataSourceProperties.class);
        Mockito.when(prop.getUrl()).thenReturn("jdbc:postgresql://localhost:5432/mgmt");
        snippet = new SqlSnippetFacade(prop);
        mapper = new FastJsonFormatMapper();
    }

    @Test
    void testToString() {
        var json = new JSONObject();
        Assertions.assertNotNull(mapper.toString(json, null, null));
    }

    @Test
    void testFromString1() {
        var javaType = Mockito.mock(JavaType.class);
        Mockito.when(javaType.getJavaTypeClass()).thenReturn(JSONObject.class);
        var json = new JSONObject();
        var view = mapper.fromString(json.toString(), javaType, null);
        Assertions.assertEquals(json, view);
    }

    @Test
    void testFromString2() {
        var javaType = Mockito.mock(JavaType.class);
        Mockito.when(javaType.getJavaTypeClass()).thenReturn(JSONArray.class);
        var array = new JSONArray();
        var view = mapper.fromString(array.toString(), javaType, null);
        Assertions.assertEquals(array, view);
    }

    @Test
    void testFromString3() {
        var javaType = Mockito.mock(JavaType.class);
        Mockito.when(javaType.getJavaTypeClass()).thenReturn(String.class);
        var json = "str";
        var view = mapper.fromString(json, javaType, null);
        Assertions.assertEquals(json, view);
    }

}
