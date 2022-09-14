package cn.harryai.tool.dbcompare;

import cn.harryai.tool.dbcompare.config.DbCompareConfig;
import cn.harryai.tool.dbcompare.config.DbConfig;
import cn.harryai.tool.dbcompare.config.SchemaConfig;
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
                .leftDb(DbConfig.builder()
                        .host("")
                        .dialect(DialectEnum.MYSQL8)
                        .password("")
                        .userName("")
                        .build())
                .rightDb(DbConfig.builder()
                        .host("")
                        .dialect(DialectEnum.MYSQL8)
                        .password("")
                        .userName("")
                        .build())
                .schemas(new SchemaConfig[]{SchemaConfig.builder().schemaName("scp_purchase").excludeTableName(
                        "undo_log").build()})
                .build();
        DbComparator.builder().dbCompareConfig(build).build().compare();
    }
}
