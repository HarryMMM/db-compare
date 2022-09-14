package cn.harryai.tool.dbcompare.sqlbuilder;

import cn.harryai.tool.dbcompare.config.ResolverConfig;
import cn.harryai.tool.dbcompare.config.SchemaConfig;
import cn.harryai.tool.dbcompare.config.TableConfig;
import org.apache.commons.lang3.ArrayUtils;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 19:01
 **/
public abstract class AbsSqlBuilder implements SqlBuilder {
    @Override
    public String buildColumn(ResolverConfig config){
        TableConfig[] tables = config.getTables();
        if (ArrayUtils.isNotEmpty(tables)){
            return buildColumn(tables);
        }else {
            return buildColumn(config.getSchemas());
        }
    }

    @Override
    public String buildTable(ResolverConfig config) {
        TableConfig[] tables = config.getTables();
        if (ArrayUtils.isNotEmpty(tables)){
            return buildTable(tables);
        }else {
            return buildTable(config.getSchemas());
        }
    }

    protected abstract String buildColumn(SchemaConfig [] config);

    protected abstract String buildColumn(TableConfig []config);


    protected abstract String buildTable(SchemaConfig [] config);

    protected abstract String buildTable(TableConfig []config);

}
