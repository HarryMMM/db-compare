package cn.harryai.tool.dbcompare.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/14 19:12
 **/
@AllArgsConstructor
@Data
public class ResolverConfig {
    private SchemaConfig[] schemas;
    private TableConfig[] tables;
}
