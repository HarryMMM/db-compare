package cn.harryai.tool.dbcompare.util;

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
                .alias("s$UM@vjPM3Cu2UIVGP4Xj6WCEAli%!yZ")
                .build();
    }
    public static DbConfig prod(){
       return DbConfig.builder()
                .host("")
                .dialect(DialectEnum.MYSQL8)
                .password("")
                .userName("")
                .alias("MMFIXMdT$GJ3ETvGTXBl0bwzGgk21fd9")
                .build();
    }

    public static SchemaConfig schemaConfig(){
        return SchemaConfig.builder().schemaNames(new String[]{"scp_brand", "scp_cost", "scp_demand",
                "scp_file", "scp_inventory", "scp_manufacture", "scp_message", "scp_product",
                "scp_purchase", "scp_quality", "scp_resource", "scp_settlement", "scp_sys"
                , "scp_integration"}).excludeTableNames(
                new String[]{"undo_log"}).build();
    }
    public static TableConfig tableConfig(){
        return TableConfig.builder().schemaNames(new String[]{"scp_product"}).build();
    }
}
