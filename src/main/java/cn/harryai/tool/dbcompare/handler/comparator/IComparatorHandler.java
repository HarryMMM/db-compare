package cn.harryai.tool.dbcompare.handler.comparator;

import cn.harryai.tool.dbcompare.config.ComparisonHandlerConfig;
import cn.harryai.tool.dbcompare.enums.PrintFormat;
import cn.harryai.tool.dbcompare.module.Column;
import cn.harryai.tool.dbcompare.module.Comparable;
import cn.harryai.tool.dbcompare.module.ComparisonColumnResult;
import cn.harryai.tool.dbcompare.module.ComparisonResult;
import cn.harryai.tool.dbcompare.module.ComparisonTableResult;
import cn.harryai.tool.dbcompare.module.Table;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/16 15:25
 **/
public interface IComparatorHandler<T extends Comparable, R extends ComparisonResult> {
    /**
     * 打印器的名字
     * @return
     * @param compileMap
     */
    Map<Class<T>,List<R>> compare(Pair<List<T>, List<T>> compileMap, ComparisonHandlerConfig config);

    Class<T> key();
}
