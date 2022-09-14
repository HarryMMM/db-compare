package cn.harryai.tool.dbcompare.resolver;

import cn.harryai.tool.dbcompare.enums.DialectEnum;
import cn.harryai.tool.dbcompare.resolver.mysql.MysqlResolver;

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
public class ResultResolverManger {
    private static final Map<DialectEnum, ResultResolver> RESULT_RESOLVER =new HashMap<>();
    public static ResultResolver getSqlResolver(DialectEnum dialect){
        return RESULT_RESOLVER.get(dialect);
    }
    static {
        RESULT_RESOLVER.put(DialectEnum.MYSQL, new MysqlResolver());
        RESULT_RESOLVER.put(DialectEnum.MYSQL8, new MysqlResolver());
    }
}
