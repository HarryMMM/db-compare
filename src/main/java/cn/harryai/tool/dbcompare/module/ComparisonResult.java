package cn.harryai.tool.dbcompare.module;

import java.io.Serializable;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/19 20:01
 **/
public interface ComparisonResult<T> extends Serializable, java.lang.Comparable<T> {
    boolean isSame();
}
