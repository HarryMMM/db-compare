package cn.harryai.tool.dbcompare.config;

import cn.harryai.tool.dbcompare.enums.PrintFormat;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/16 16:58
 **/
@Data
@Builder
public class PrinterConfig {
    private PrintFormat printFormat;
    private String basePath;

}
