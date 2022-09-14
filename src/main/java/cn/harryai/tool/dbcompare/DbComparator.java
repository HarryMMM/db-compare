package cn.harryai.tool.dbcompare;

import cn.harryai.tool.dbcompare.config.DbCompareConfig;
import cn.harryai.tool.dbcompare.config.DbConfig;
import cn.harryai.tool.dbcompare.config.ResolverConfig;
import cn.harryai.tool.dbcompare.config.SchemaConfig;
import cn.harryai.tool.dbcompare.db.DatabaseManager;
import cn.harryai.tool.dbcompare.enums.DialectEnum;
import cn.harryai.tool.dbcompare.module.Table;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 17:40
 **/
public class DbComparator {
    private final DatabaseManager databaseManager;

    private DbComparator(DbCompareConfig dbCompareConfig) {
        databaseManager = new DatabaseManager(dbCompareConfig.getLeftDb(), dbCompareConfig.getRightDb(),
                new ResolverConfig(dbCompareConfig.getSchemas(), dbCompareConfig.getTables()));
    }

    public void compare() {
        // 查询列
        Pair<List<Table>, List<Table>> listListPair = databaseManager.search();

        // 比较

    }

    public static DbComparator.DbComparatorBuilder builder() {
        return new DbComparator.DbComparatorBuilder();
    }

    public static class DbComparatorBuilder {
        private DbCompareConfig dbCompareConfig;

        DbComparatorBuilder() {
        }

        public DbComparator.DbComparatorBuilder dbCompareConfig(DbCompareConfig dbCompareConfig) {
            this.dbCompareConfig = dbCompareConfig;
            return this;
        }

        public DbComparator build() {
            return new DbComparator(this.dbCompareConfig);
        }

        @Override
        public String toString() {
            return "DbComparator.DbComparatorBuilder(dbCompareConfig=" + this.dbCompareConfig + ")";
        }
    }
}

