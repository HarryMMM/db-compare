package cn.harryai.tool.dbcompare.resolver.mysql;

import cn.harryai.tool.dbcompare.module.Column;
import cn.harryai.tool.dbcompare.module.Table;
import cn.harryai.tool.dbcompare.resolver.ResolverHelper;
import cn.harryai.tool.dbcompare.resolver.ResultResolver;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 19:57
 **/
public class MysqlResolver implements ResultResolver {
    @Override
    public List<Table> resolving(ResultSet tableResultSet, ResultSet columnResultSet) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        // 获取表数据
        List<Table> tables = getData(tableResultSet,Table.class);
        if (CollectionUtils.isEmpty(tables)) {
            return Collections.emptyList();
        }

        // 获取列数据
        List<Column> columns = getData(columnResultSet,Column.class);
        if (CollectionUtils.isNotEmpty(columns)) {
            Map<String, List<Column>> columnGroupByTableSchemaAndTableNameMap =
                    columns.stream().collect(Collectors.groupingBy(e -> getKey(e.getTableSchema(), e.getTableName())));

            for (Table table : tables) {
                String key = getKey(table.getTableSchema(), table.getTableName());
                table.setColumns(columnGroupByTableSchemaAndTableNameMap.get(key));
            }
        }
        return tables;
    }

    private String getKey(String tableSchema, String tableName) {
        return tableSchema +
                "-" + tableName;
    }

    private <T> List<T> getData(ResultSet columnResultSet, Class<T> clz) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        List<Field> allFieldsList = FieldUtils.getAllFieldsList(clz);
        List<T> columns = new ArrayList<>();
        while (columnResultSet.next()) {
            T column = ConstructorUtils.invokeConstructor(clz);
            for (Field field : allFieldsList) {
                ResolverHelper.setValue(columnResultSet, column, field);
            }
            columns.add(column);
        }
        return columns;
    }
}
