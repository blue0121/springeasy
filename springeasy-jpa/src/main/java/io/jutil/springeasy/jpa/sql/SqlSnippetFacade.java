package io.jutil.springeasy.jpa.sql;

import io.jutil.springeasy.core.util.AssertUtil;
import io.jutil.springeasy.jpa.sql.impl.H2SqlSnippet;
import io.jutil.springeasy.jpa.sql.impl.MySqlSnippet;
import io.jutil.springeasy.jpa.sql.impl.PostgreSqlSnippet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * @author Jin Zheng
 * @since 2023-10-27
 */
@Slf4j
public class SqlSnippetFacade {
    private static final String JDBC_SEP = ":";

    private static SqlSnippet snippet;

    @SuppressWarnings({"java:S1118", "java:S3010"})
    public SqlSnippetFacade(DataSourceProperties prop) {
        var dbType = detectDbType(prop);
        snippet = detect(dbType);
    }

    public static String getJsonString(String str) {
        return snippet.getJsonString(str);
    }

    public static SqlSnippet detect(String dbType) {
        return switch (dbType) {
            case "h2" -> new H2SqlSnippet();
            case "postgresql" -> new PostgreSqlSnippet();
            case "mysql" -> new MySqlSnippet();
            default -> throw new UnsupportedOperationException(
                    "Unknown Database type: " + dbType);
        };
    }

    private static String detectDbType(DataSourceProperties prop) {
        var url = prop.getUrl();
        AssertUtil.notEmpty(url, "Jdbc Url");

        var startPos = 5;
        var endPos = url.indexOf(JDBC_SEP, startPos);
        var type = url.substring(startPos, endPos);
        log.info("Database type: {}", type);
        return type;
    }
}
