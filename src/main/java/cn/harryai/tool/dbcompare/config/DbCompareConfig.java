package cn.harryai.tool.dbcompare.config;

import cn.harryai.tool.dbcompare.enums.PrintFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;

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

    private PrinterConfig printerConfig;

    /**
     * 只显示差异还是所有。
     * true,显示所有。
     * false,显示差异。
     */
    private ComparisonHandlerConfig comparisonHandlerConfig;
}
