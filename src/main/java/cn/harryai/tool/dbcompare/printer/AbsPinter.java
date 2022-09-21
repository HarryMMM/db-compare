package cn.harryai.tool.dbcompare.printer;

import cn.harryai.tool.dbcompare.config.PrinterConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/16 15:13
 **/
@Slf4j
@Data
public abstract class AbsPinter<C extends PrinterConfig> implements IPinter<C> {
    protected String basePath = System.getProperty("java.io.tmpdir");

    @Override
    public String print(DataWarp<?, ?> warp) {
        String filePath = doPrint(warp);
        log.info("Your report is here: {}", filePath);
        return filePath;
    }

    @Override
    public void configure(C config) {
        if (config.getBasePath() != null) {
            basePath = config.getBasePath();
        }
        doConfigure(config);
    }

    /**
     * 如果是文件要求返回文件全路径
     *
     * @param dataWarp 要打印的数据
     * @return 文件全路径
     */
    protected abstract String doPrint(DataWarp<?, ?> dataWarp);

    /**
     * 配置
     *
     * @param config
     */
    protected void doConfigure(C config){}
}
