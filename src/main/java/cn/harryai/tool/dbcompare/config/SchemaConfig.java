package cn.harryai.tool.dbcompare.config;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 18:40
 **/
@Data
@Builder
public class SchemaConfig {
    private String schemaName;
    private String excludeTableName;
}
