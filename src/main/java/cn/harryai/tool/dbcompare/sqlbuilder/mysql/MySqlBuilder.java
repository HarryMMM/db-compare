package cn.harryai.tool.dbcompare.sqlbuilder.mysql;

import cn.harryai.tool.dbcompare.config.SchemaConfig;
import cn.harryai.tool.dbcompare.config.TableConfig;
import cn.harryai.tool.dbcompare.sqlbuilder.AbsSqlBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.function.Function;
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
    String schemeCondition = " AND TABLE_SCHEMA in(%s)";
    String tableCondition = " AND TABLE_NAME in (%s)";
    String excludeTableCondition = " AND TABLE_NAME not in (%s)";

    @Override
    protected String buildColumn(SchemaConfig[] config) {
        String sql = "select * from COLUMNS where 1 = 1 %s %s";
        return String.format(sql, getSchemaCondition(config), getExcludeTableCondition(config));
    }


    @Override
    protected String buildColumn(TableConfig[] config) {
        String sql = "select * from COLUMNS where 1 = 1 %s %s";
        return String.format(sql, getSchemaCondition(config), getTableCondition(config));
    }


    @Override
    protected String buildTable(SchemaConfig[] config) {
        String sql = "select  tb.*,idx.`index` from (select * from tables  where 1 = 1 %s %s) tb\n" +
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
        return String.format(sql, getSchemaCondition(config), getExcludeTableCondition(config));
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
        String sql = "select  tb.*,idx.`index` from (select * from tables where 1 = 1 %s %s ) tb\n" +
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

        return String.format(sql, getSchemaCondition(config), getTableCondition(config));
    }

    private String getExcludeTableCondition(SchemaConfig[] config) {
        String excludeTables =
                join(config, SchemaConfig::getExcludeTableName);

        if (StringUtils.isNotEmpty(excludeTables)) {
            return String.format(excludeTableCondition, excludeTables);
        }
        return StringUtils.EMPTY;
    }


    private String getSchemaCondition(SchemaConfig[] config) {
        String schemas =
                join(config, SchemaConfig::getSchemaName);
        if (StringUtils.isNotEmpty(schemas)) {
            return String.format(schemeCondition, schemas);
        }
        return StringUtils.EMPTY;
    }

    private String getSchemaCondition(TableConfig[] config) {
        String schemas =
                join(config, TableConfig::getSchemaName);
        if (StringUtils.isNotEmpty(schemas)) {
            return String.format(schemeCondition, schemas);
        }
        return StringUtils.EMPTY;
    }

    private String getTableCondition(TableConfig[] config) {
        String tables = join(config, TableConfig::getTableName);
        if (StringUtils.isNotEmpty(tables)) {
            return String.format(tableCondition, tables);
        }
        return StringUtils.EMPTY;
    }

    private String join(TableConfig[] config, Function<TableConfig, String> getter) {
        return Arrays.stream(config).filter(e -> getter.apply(e) != null
        ).map(getter).distinct().map(e -> "'" + e + "'").collect(Collectors.joining(","));
    }

    private String join(SchemaConfig[] config, Function<SchemaConfig, String> getter) {
        return Arrays.stream(config).filter(e -> getter.apply(e) != null
        ).map(getter).distinct().map(e -> "'" + e + "'").collect(Collectors.joining(","));
    }
}
