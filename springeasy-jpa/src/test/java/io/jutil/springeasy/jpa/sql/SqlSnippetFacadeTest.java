package io.jutil.springeasy.jpa.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * @author Jin Zheng
 * @since 2023-10-27
 */
@ExtendWith(MockitoExtension.class)
class SqlSnippetFacadeTest {
    @Mock
    DataSourceProperties prop;

    @CsvSource({"jdbc:postgresql://localhost:5432/mgmt,\"str\",\"str\"",
            "jdbc:mysql://localhost:3306/mgmt,\"str\",\"str\"",
            "jdbc:h2:mem:testdb,\"str\",str"})
    @ParameterizedTest
    void testGetJsonString(String url, String str, String json) {
        Mockito.when(prop.getUrl()).thenReturn(url);
        var snippet = new SqlSnippetFacade(prop);
        Assertions.assertEquals(json, SqlSnippetFacade.getJsonString(str));
    }

}
