package cn.harryai.tool.dbcompare.db;

import cn.harryai.tool.dbcompare.config.DbConfig;
import cn.harryai.tool.dbcompare.config.ResolverConfig;
import cn.harryai.tool.dbcompare.enums.DialectEnum;
import cn.harryai.tool.dbcompare.module.Table;
import cn.harryai.tool.dbcompare.resolver.ResultResolver;
import cn.harryai.tool.dbcompare.resolver.ResultResolverManger;
import cn.harryai.tool.dbcompare.sqlbuilder.SqlBuilder;
import cn.harryai.tool.dbcompare.sqlbuilder.SqlBuilderManger;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 17:51
 **/
@AllArgsConstructor
public class DatabaseManager {
    private DbConfig left;
    private DbConfig right;
    private ResolverConfig resolverConfig;

    private Connection connect(DbConfig dbConfig) {
        try {
            DialectEnum dialect = dbConfig.getDialect();

            //1.加载驱动程序
            Class.forName(dialect.getDriver());

            //2. 获得数据库连接
            int port = dbConfig.getPort() == null ? dialect.getDefaultPort() : dbConfig.getPort();
            String url = String.format(dialect.getUrl(), dbConfig.getHost(), port);
            return DriverManager.getConnection(url, dbConfig.getUserName(), dbConfig.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Pair<List<Table>, List<Table>> search() {
        return Pair.of(doSearch(left), doSearch(right));
    }

    private List<Table> doSearch(DbConfig dbConfig) {
        Connection connect = connect(dbConfig);
        SqlBuilder absSqlBuilder = SqlBuilderManger.getSqlBuilder(dbConfig.getDialect());
        try {
            PreparedStatement tablePreparedStatement =
                    connect.prepareStatement(absSqlBuilder.buildTable(resolverConfig));
            ResultSet tableResultSet = tablePreparedStatement.executeQuery();
            PreparedStatement columnPreparedStatement =
                    connect.prepareStatement(absSqlBuilder.buildColumn(resolverConfig));
            ResultSet columnResultSet = columnPreparedStatement.executeQuery();
            ResultResolver sqlResolver = ResultResolverManger.getSqlResolver(dbConfig.getDialect());
            return sqlResolver.resolving(tableResultSet, columnResultSet);
        } catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
