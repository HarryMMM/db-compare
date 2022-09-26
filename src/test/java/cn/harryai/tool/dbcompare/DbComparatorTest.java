package cn.harryai.tool.dbcompare;

import cn.harryai.tool.dbcompare.config.ComparisonHandlerConfig;
import cn.harryai.tool.dbcompare.config.DbCompareConfig;
import cn.harryai.tool.dbcompare.util.CommandLineUtils;
import cn.harryai.tool.dbcompare.util.DbCompareConfigUtils;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/21 09:35
 **/
class DbComparatorTest {

    @org.junit.jupiter.api.Test
    void compare() {
        DbCompareConfig build = DbCompareConfig.builder()
                .rightDb(DbCompareConfigUtils.dev())
                .leftDb(DbCompareConfigUtils.test())
                .schema(DbCompareConfigUtils.schemaConfig()
                )
                .comparisonHandlerConfig(ComparisonHandlerConfig.builder().fullMode(false).build())
                .build();
        DbComparator.builder().dbCompareConfig(build).build().compare();
    }

    @org.junit.jupiter.api.Test
    void argTest() {
        String[] args = {"-m", "1"};
        CommandLineUtils.exec(args);
//        String s = IPAddressUtil.checkHostString("11.2.2.2");
//        System.out.println(s);
    }
}