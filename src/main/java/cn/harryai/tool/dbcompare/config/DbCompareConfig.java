package cn.harryai.tool.dbcompare.config;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 17:21
 **/
@Data
@Builder
public class DbCompareConfig {
    private DbConfig leftDb;
    private DbConfig rightDb;
    private SchemaConfig[] schemas;
    private TableConfig[] tables;
    private boolean writeExcel;
}
