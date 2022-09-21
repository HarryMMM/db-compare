package cn.harryai.tool.dbcompare.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Optional;

/**
 * <p>
 * 方言
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 18:10
 **/
@AllArgsConstructor
@Getter
public enum DialectEnum {
    MYSQL("mysql", "com.mysql.jdbc.Driver", "jdbc:mysql://%s:%s/information_schema?useSSL=false", 3306),
    MYSQL8("mysql8", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://%s:%s/information_schema?useUnicode=true" +
            "&characterEncoding" +
            "=UTF-8&useSSL=true", 3306),
    ORACLE("oracle", "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@%s:%s/sys", 1521),
    ;
    private String name;
    private String driver;
    private String url;
    private int defaultPort;

    public static Optional<DialectEnum> getInstance(String dialect) {
        for (DialectEnum value : DialectEnum.values()) {
            if (value.name().equals(StringUtils.stripToEmpty(dialect).toUpperCase(Locale.ROOT))) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
