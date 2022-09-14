package cn.harryai.tool.dbcompare.handler.comparator;

import cn.harryai.tool.dbcompare.config.ComparisonHandlerConfig;
import cn.harryai.tool.dbcompare.module.Comparable;
import cn.harryai.tool.dbcompare.module.ComparisonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/19 20:05
 **/
@Slf4j
public abstract class AbsComparatorHandler<T extends Comparable, R extends ComparisonResult<R>> implements IComparatorHandler<T, R> {
    protected static final String YES = "YES";
    protected static final String NO = "NO";

    @Override
    public Map<Class<T>, List<R>> compare(Pair<List<T>, List<T>> compileMap, ComparisonHandlerConfig config) {
        List<R> value = doCompare(compileMap.getLeft(), compileMap.getRight(), config);
        if (CollectionUtils.isEmpty(value)) {
            return Collections.emptyMap();
        }
        Map<Class<T>, List<R>> map = new HashMap<>(1);
        if (!config.isFullMode()) {
            value = value.stream().filter(e -> !e.isSame()).collect(Collectors.toList());
        }
        Collections.sort(value);
        map.put(key(), value);
        return map;
    }

    protected abstract List<R> doCompare(List<T> l, List<T> r, ComparisonHandlerConfig config);

    protected void fillData(T leftTable, T rightTable, R tableResult) {
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(tableResult.getClass());
        for (Field field : allFieldsList) {
            String name = field.getName();
            String nameSub;
            T target;
            if (name.endsWith("L") && leftTable != null) {
                target = leftTable;
                nameSub = StringUtils.substringBeforeLast(name, "L");
            } else if (name.endsWith("R") && rightTable != null) {
                target = rightTable;
                nameSub = StringUtils.substringBeforeLast(name, "R");
            } else {
                continue;
            }
            try {
                Object value = FieldUtils.readField(target, nameSub, true);
                FieldUtils.writeField(field, tableResult, value, true);
            } catch (IllegalAccessException e) {
                log.warn("The field [{}] of [{}] Assignment failed.", name, tableResult);
            } catch (IllegalArgumentException e) {
                // do nothing
            }
        }
    }

}
