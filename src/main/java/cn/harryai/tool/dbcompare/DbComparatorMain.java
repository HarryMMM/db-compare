package cn.harryai.tool.dbcompare;

import cn.harryai.tool.dbcompare.config.ComparisonHandlerConfig;
import cn.harryai.tool.dbcompare.config.DbCompareConfig;
import cn.harryai.tool.dbcompare.config.DbConfig;
import cn.harryai.tool.dbcompare.config.SchemaConfig;
import cn.harryai.tool.dbcompare.config.TableConfig;
import cn.harryai.tool.dbcompare.enums.DialectEnum;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/14 19:21
 **/
public class DbComparatorMain {
    public static void main(String[] args) {
        DbCompareConfig build = DbCompareConfig.builder()
                .rightDb(DbConfig.builder()
                        .host("")
                        .dialect(DialectEnum.MYSQL8)
                        .password("")
                        .userName("")
                        .build())
                .leftDb(DbConfig.builder()
                        .host("")
                        .dialect(DialectEnum.MYSQL8)
                        .password("")
                        .userName("")
                        .build())
                .schemas(new SchemaConfig[]{SchemaConfig.builder().schemaName("scp_purchase").excludeTableName(
                        "undo_log").build()})
                .tables(new TableConfig[]{TableConfig.builder().schemaName("scp_product").build()})
                .comparisonHandlerConfig(ComparisonHandlerConfig.builder().fullMode(false).build())
                .build();
        DbComparator.builder().dbCompareConfig(build).build().compare();
    }
}
