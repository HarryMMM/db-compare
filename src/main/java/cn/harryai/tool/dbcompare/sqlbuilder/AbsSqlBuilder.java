package cn.harryai.tool.dbcompare.sqlbuilder;

import cn.harryai.tool.dbcompare.config.ResolverConfig;
import cn.harryai.tool.dbcompare.config.SchemaConfig;
import cn.harryai.tool.dbcompare.config.TableConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 19:01
 **/
@Slf4j
public abstract class AbsSqlBuilder implements SqlBuilder {
    @Override
    public String buildColumn(ResolverConfig config){
        TableConfig table = config.getTable();
        String s;
        if (table != null) {
            s = buildColumn(table);
        } else {
            s = buildColumn(config.getSchema());
        }
        log.debug("The sql is: {}", s);
        return s;
    }

    @Override
    public String buildTable(ResolverConfig config) {
        TableConfig tables = config.getTable();
        String s;
        if (tables != null) {
            s = buildTable(tables);
        } else {
            s = buildTable(config.getSchema());
        }
        log.debug("The sql is: {}",s);
        return s;
    }

    protected abstract String buildColumn(SchemaConfig  config);

    protected abstract String buildColumn(TableConfig config);


    protected abstract String buildTable(SchemaConfig  config);

    protected abstract String buildTable(TableConfig config);

}
