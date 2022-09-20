package cn.harryai.tool.dbcompare.config;

import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    private String [] schemaNames;
    private String [] excludeTableNames;
}
