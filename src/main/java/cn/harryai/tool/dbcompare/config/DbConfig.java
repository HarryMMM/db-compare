package cn.harryai.tool.dbcompare.config;

import cn.harryai.tool.dbcompare.enums.DialectEnum;
import lombok.Builder;
import lombok.Data;

/**
 *
 *
 *
 * @author haorui.hao
 * @since 2022/09/13 17:21
 **/
@Data
@Builder
public class DbConfig {
    private String host;
    private Integer port;
    private String userName;
    private String password;
    private DialectEnum dialect;
    private String alias;

    public String getAlias() {
        if (alias == null) {
            alias = host;
        }
        return alias;
    }
}
