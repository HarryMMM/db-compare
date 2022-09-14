package cn.harryai.tool.dbcompare.sqlbuilder;

import cn.harryai.tool.dbcompare.config.DbCompareConfig;
import cn.harryai.tool.dbcompare.config.ResolverConfig;
import cn.harryai.tool.dbcompare.config.SchemaConfig;
import cn.harryai.tool.dbcompare.config.TableConfig;

import javax.xml.validation.Schema;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 18:50
 **/
public interface SqlBuilder {
    String buildColumn(ResolverConfig config);
    String buildTable(ResolverConfig config);
}
