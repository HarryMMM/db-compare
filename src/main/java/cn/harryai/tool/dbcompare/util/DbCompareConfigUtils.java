package cn.harryai.tool.dbcompare.util;

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
 * @since 2022/09/21 10:14
 **/
public final class DbCompareConfigUtils {

    private static final String[] SCHEMA_NAMES = {"scp_brand", "scp_cost", "scp_demand",
            "scp_file", "scp_inventory", "scp_manufacture", "scp_message", "scp_product",
            "scp_purchase", "scp_quality", "scp_resource", "scp_settlement", "scp_sys"
            , "scp_integration"};
    private static final String[] EXCLUDE_TABLE_NAMES = {"undo_log"};

    private DbCompareConfigUtils() {
    }
    public static DbConfig dev(){
       return DbConfig.builder()
                .host("")
                .dialect(DialectEnum.MYSQL8)
                .password("")
                .userName("")
                .alias("dev")
                .build();
    }
    public static DbConfig test(){
       return DbConfig.builder()
               .host("")
               .dialect(DialectEnum.MYSQL8)
               .password("")
               .userName("")
               .alias("test")
               .build();
    }
    public static DbConfig pre(){
       return DbConfig.builder()
                .host("")
                .dialect(DialectEnum.MYSQL8)
                .password("")
                .userName("")
                .alias("pre")
                .build();
    }
    public static DbConfig prod(){
       return DbConfig.builder()
                .host("")
                .dialect(DialectEnum.MYSQL8)
                .password("")
                .userName("")
                .alias("prod")
                .build();
    }

    public static SchemaConfig schemaConfig() {
        return SchemaConfig.builder().schemaNames(SCHEMA_NAMES).excludeTableNames(
                 EXCLUDE_TABLE_NAMES).build();
    }

    public static TableConfig tableConfig() {
        return TableConfig.builder().schemaNames(new String[]{"scp_product"}).build();
    }

    public static DbCompareConfig devVsTest() {
        return DbCompareConfig.builder()
                .leftDb(DbCompareConfigUtils.dev())
                .rightDb(DbCompareConfigUtils.test())
                .schema(DbCompareConfigUtils.schemaConfig()
                )
                .comparisonHandlerConfig(ComparisonHandlerConfig.builder().fullMode(false).build())
                .build();
    }

    public static DbCompareConfig testVsPre() {
        return DbCompareConfig.builder()
                .leftDb(DbCompareConfigUtils.test())
                .rightDb(DbCompareConfigUtils.pre())
                .schema(DbCompareConfigUtils.schemaConfig()
                )
                .comparisonHandlerConfig(ComparisonHandlerConfig.builder().fullMode(false).build())
                .build();
    }

    public static DbCompareConfig preVsProd() {
        return DbCompareConfig.builder()
                .leftDb(DbCompareConfigUtils.pre())
                .rightDb(DbCompareConfigUtils.prod())
                .schema(DbCompareConfigUtils.schemaConfig()
                )
                .comparisonHandlerConfig(ComparisonHandlerConfig.builder().fullMode(false).build())
                .build();
    }
}
