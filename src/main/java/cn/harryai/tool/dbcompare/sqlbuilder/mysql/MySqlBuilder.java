package cn.harryai.tool.dbcompare.sqlbuilder.mysql;

import cn.harryai.tool.dbcompare.config.SchemaConfig;
import cn.harryai.tool.dbcompare.config.TableConfig;
import cn.harryai.tool.dbcompare.sqlbuilder.AbsSqlBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 18:49
 **/
public class MySqlBuilder extends AbsSqlBuilder {

    @Override
    protected String buildColumn(SchemaConfig[] config) {
        String sql = "select * from COLUMNS where TABLE_SCHEMA in(%s) and TABLE_NAME not in (%s)";
        String schemas =
                Arrays.stream(config).map(SchemaConfig::getSchemaName).distinct().map(e -> "'" + e + "'").collect(Collectors.joining(","));
        String excludeTables =
                Arrays.stream(config).map(SchemaConfig::getExcludeTableName).distinct().map(e -> "'" + e + "'").collect(Collectors.joining(","));
        return String.format(sql, schemas, excludeTables);
    }

    @Override
    protected String buildColumn(TableConfig[] config) {
        String schemas =
                Arrays.stream(config).map(TableConfig::getSchemaName).distinct().map(e -> "'" + e + "'").collect(Collectors.joining(
                        ","));
        String tables =
                Arrays.stream(config).map(TableConfig::getTableName).distinct().map(e -> "'" + e + "'").collect(Collectors.joining(","));
        String sql = "select * from COLUMNS where TABLE_SCHEMA in(%s) and TABLE_NAME in(%s)";
        return String.format(sql, schemas, tables);
    }

    @Override
    protected String buildTable(SchemaConfig[] config) {
        String sql = "select  tb.*,idx.`index` from (select * from tables where TABLE_SCHEMA in (%s) and " +
                "TABLE_NAME" +
                " not in" +
                "(%s)) tb\n" +
                "    left join\n" +
                "(select ba.TABLE_SCHEMA,ba.TABLE_NAME,group_concat(ba.`index`  SEPARATOR ';\\n') as `index` from" +
                "(select a.table_schema,\n" +
                "       a.table_name,\n" +
                "      concat(a.index_name,'(',group_concat(column_name order by seq_in_index),')',':',a.index_type) " +
                "`index`\n" +
                "\n" +
                "from information_schema.statistics a\n" +
                "group by a.table_schema, a.table_name,a.index_name,a.index_type) ba\n" +
                "group by ba.TABLE_SCHEMA, ba.TABLE_NAME) idx\n" +
                "on idx.table_schema=tb.table_schema and idx.table_name=tb.table_name;";
        String schemas =
                Arrays.stream(config).map(SchemaConfig::getSchemaName).distinct().map(e -> "'" + e + "'").collect(Collectors.joining(","));
        String excludeTables =
                Arrays.stream(config).map(SchemaConfig::getExcludeTableName).distinct().map(e -> "'" + e + "'").collect(Collectors.joining(","));
        return String.format(sql, schemas, excludeTables);
    }


    /**
     * select  tb.*,idx.`index` from (select * from tables where TABLE_SCHEMA in'scp_product' and TABLE_NAME in
     * ('product_base')) tb
     *     left join
     * (select ba.TABLE_SCHEMA,ba.TABLE_NAME,group_concat(ba.`index`  SEPARATOR ';\n') as `index` from(select a
     * .table_schema,
     *        a.table_name,
     *       concat(a.index_name,'(',group_concat(column_name order by seq_in_index),')',':',a.index_type) `index`
     *
     * from information_schema.statistics a
     * group by a.table_schema, a.table_name,a.index_name,a.index_type) ba
     * group by ba.TABLE_SCHEMA, ba.TABLE_NAME) idx
     *
     * on idx.table_schema=tb.table_schema and idx.table_name=tb.table_name;
     * @param config
     * @return
     */
    @Override
    protected String buildTable(TableConfig[] config) {
        String schemas =
                Arrays.stream(config).map(TableConfig::getSchemaName).distinct().map(e -> "'" + e + "'").collect(Collectors.joining(
                        ","));
        String tables =
                Arrays.stream(config).map(TableConfig::getTableName).distinct().map(e -> "'" + e + "'").collect(Collectors.joining(","));
        String sql = "select  tb.*,idx.`index` from (select * from tables where TABLE_SCHEMA in (%s) and TABLE_NAME " +
                "in" +
                "(%s)) tb\n" +
                "    left join\n" +
                "(select ba.TABLE_SCHEMA,ba.TABLE_NAME,group_concat(ba.`index`  SEPARATOR ';\\n') as `index` from" +
                "(select a.table_schema,\n" +
                "       a.table_name,\n" +
                "      concat(a.index_name,'(',group_concat(column_name order by seq_in_index),')',':',a.index_type) " +
                "`index`\n" +
                "\n" +
                "from information_schema.statistics a\n" +
                "group by a.table_schema, a.table_name,a.index_name,a.index_type) ba\n" +
                "group by ba.TABLE_SCHEMA, ba.TABLE_NAME) idx\n" +
                "on idx.table_schema=tb.table_schema and idx.table_name=tb.table_name;";
        return String.format(sql, schemas, tables);
    }
}
