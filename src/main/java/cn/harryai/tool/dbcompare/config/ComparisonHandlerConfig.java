package cn.harryai.tool.dbcompare.config;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 17:21
 **/
@Data
@Builder
public class ComparisonHandlerConfig {
    /**
     * 只显示差异还是所有。
     * true,显示所有。
     * false,显示差异。
     */
    private boolean fullMode;
}
