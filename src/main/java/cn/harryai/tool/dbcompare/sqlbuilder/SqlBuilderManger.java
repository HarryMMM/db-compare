package cn.harryai.tool.dbcompare.sqlbuilder;

import cn.harryai.tool.dbcompare.enums.DialectEnum;
import cn.harryai.tool.dbcompare.sqlbuilder.mysql.MySqlBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 18:48
 **/
public class SqlBuilderManger {
    private static final Map<DialectEnum,SqlBuilder> SQL_BUILDERS =new HashMap<>();
    public static SqlBuilder getSqlBuilder (DialectEnum dialect){
        return SQL_BUILDERS.get(dialect);
    }
    static {
        SQL_BUILDERS.put(DialectEnum.MYSQL, new MySqlBuilder());
        SQL_BUILDERS.put(DialectEnum.MYSQL8, new MySqlBuilder());
    }
}
