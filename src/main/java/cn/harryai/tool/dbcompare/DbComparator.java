package cn.harryai.tool.dbcompare;

import cn.harryai.tool.dbcompare.config.DbCompareConfig;
import cn.harryai.tool.dbcompare.config.PrinterConfig;
import cn.harryai.tool.dbcompare.config.ResolverConfig;
import cn.harryai.tool.dbcompare.db.DatabaseManager;
import cn.harryai.tool.dbcompare.enums.PrintFormat;
import cn.harryai.tool.dbcompare.handler.comparator.IComparatorHandler;
import cn.harryai.tool.dbcompare.module.Column;
import cn.harryai.tool.dbcompare.module.Comparable;
import cn.harryai.tool.dbcompare.module.ComparisonResult;
import cn.harryai.tool.dbcompare.module.Table;
import cn.harryai.tool.dbcompare.printer.DataWarp;
import cn.harryai.tool.dbcompare.printer.IPinter;
import cn.harryai.tool.dbcompare.printer.PrinterManager;
import cn.harryai.tool.dbcompare.util.ServiceLoadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/13 17:40
 **/
@Slf4j
public class DbComparator {
    private final DatabaseManager databaseManager;
    private final DbCompareConfig dbCompareConfig;
    private final PrinterConfig printerConfig;
    private final Map<Class<? extends Comparable>, IComparatorHandler> comparatorHandler =
            ServiceLoadUtils.getInstance().load(IComparatorHandler.class, IComparatorHandler::key);

    private DbComparator(DbCompareConfig dbCompareConfig) {
        this.dbCompareConfig = dbCompareConfig;
        PrinterConfig printerConfig = dbCompareConfig.getPrinterConfig();
        this.printerConfig = printerConfig == null ? PrinterConfig.builder().printFormat(PrintFormat.EXCEL).build() :
                printerConfig;
        databaseManager = new DatabaseManager(dbCompareConfig.getLeftDb(), dbCompareConfig.getRightDb(),
                new ResolverConfig(dbCompareConfig.getSchemas(), dbCompareConfig.getTables()));
    }

    @SuppressWarnings("unchecked")
    public void compare() {
        // 查询列
        Map<Class<? extends Comparable>, Pair<List<? extends Comparable>, List<? extends Comparable>>> compMap =
                databaseManager.search();

        Map<Class<? extends Comparable>, List<? extends ComparisonResult>> compResult = new HashMap<>();

        for (Map.Entry<Class<? extends Comparable>, Pair<List<? extends Comparable>, List<? extends Comparable>>> classPairEntry : compMap.entrySet()) {
            Class<? extends Comparable> key = classPairEntry.getKey();
            Pair<List<? extends Comparable>, List<? extends Comparable>> value = classPairEntry.getValue();
            // 比较
            Map<Class<? extends Comparable>, List<? extends ComparisonResult>> sub =
                    comparatorHandler.get(key).compare(value, dbCompareConfig.getComparisonHandlerConfig());
            compResult.putAll(sub);
        }

        // 打印
        IPinter<PrinterConfig> printer = PrinterManager.getInstance().getPrinter(printerConfig);
        printer.print(new DataWarp<>(compResult, dbCompareConfig.getLeftDb().getAlias(),
                dbCompareConfig.getRightDb().getAlias()));

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

