package cn.harryai.tool.dbcompare.resolver;

import cn.harryai.tool.dbcompare.module.Table;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 19:56
 **/
public interface ResultResolver {
    public List<Table> resolving(ResultSet tableResultSet, ResultSet columnResultSet) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException;
}
