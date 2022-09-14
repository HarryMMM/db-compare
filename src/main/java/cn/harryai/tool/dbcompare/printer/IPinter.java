package cn.harryai.tool.dbcompare.printer;

import cn.harryai.tool.dbcompare.config.PrinterConfig;
import cn.harryai.tool.dbcompare.enums.PrintFormat;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/16 14:12
 **/
public interface IPinter<C extends PrinterConfig> {
    /**
     * 打印
     * @param warp 数据包装器
     */
    void print(DataWarp<?,?> warp);

    /**
     * 打印器的名字
     * @return
     */
    PrintFormat name();

    /**
     * 配置打印器
     * @param config
     */
    void configure(C config);
}
