package cn.harryai.tool.dbcompare;

import cn.harryai.tool.dbcompare.util.CommandLineUtils;
import cn.harryai.tool.dbcompare.util.DbCompareContent;

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
        DbCompareContent.markCli();
        CommandLineUtils.exec(args);
        DbCompareContent.unMarkCli();
    }
}
