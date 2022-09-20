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
                .schema(
                        SchemaConfig.builder().schemaNames(new String[]{"scp_brand", "scp_cost", "scp_demand",
                                "scp_file", "scp_inventory", "scp_manufacture", "scp_message", "scp_product",
                                "scp_purchase", "scp_quality", "scp_resource", "scp_settlement", "scp_sys"
                                , "scp_integration"}).excludeTableNames(
                                new String[]{"undo_log"}).build())
//                .table(TableConfig.builder().schemaNames(new String[]{"scp_product"}).build())
                .comparisonHandlerConfig(ComparisonHandlerConfig.builder().fullMode(false).build())
                .build();
        DbComparator.builder().dbCompareConfig(build).build().compare();
    }
}
