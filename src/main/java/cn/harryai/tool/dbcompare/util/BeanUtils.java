package cn.harryai.tool.dbcompare.util;

import com.alibaba.excel.support.cglib.beans.BeanMap;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/20 12:36
 **/
public class BeanUtils {

    private BeanUtils() {
    }

    /**
     * 将对象装换为 map,对象转成 map，key肯定是字符串
     *
     * @param bean 转换对象
     * @return 返回转换后的 map 对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> beanToMap(Object bean) {
        return beanToMap(bean, null);
    }

    /**
     * 将对象装换为 map,对象转成 map，key肯定是字符串
     *
     * @param bean 转换对象
     * @return 返回转换后的 map 对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> beanToMap(Object bean, Object nullReplaceVal) {
        if (null == bean) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(BeanMap.create(bean));
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            if (stringObjectEntry.getValue() == null && nullReplaceVal != null) {
                stringObjectEntry.setValue(nullReplaceVal);
            }
        }
        return map;
    }

    /**
     * List&lt;T&gt; 转换为 List&lt;Map&lt;String, Object&gt;&gt;
     *
     * @param beans 转换对象集合
     * @return 返回转换后的 bean 列表
     */
    public static <T> List<Map<String, Object>> beansToMaps(List<T> beans) {
        if (CollectionUtils.isEmpty(beans)) {
            return Collections.emptyList();
        }
        return beans.stream().map(BeanUtils::beanToMap).collect(toList());
    }

    /**
     * List&lt;T&gt; 转换为 List&lt;Map&lt;String, Object&gt;&gt;
     *
     * @param beans 转换对象集合
     * @return 返回转换后的 bean 列表
     */
    public static <T> List<Map<String, Object>> beansToMaps(List<T> beans, Object nullReplaceVal) {
        if (CollectionUtils.isEmpty(beans)) {
            return Collections.emptyList();
        }
        return beans.stream().map(bean -> beanToMap(bean, nullReplaceVal)).collect(toList());
    }
}
