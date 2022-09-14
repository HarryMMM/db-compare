package cn.harryai.tool.dbcompare.handler.comparator;

import cn.harryai.tool.dbcompare.config.ComparisonHandlerConfig;
import cn.harryai.tool.dbcompare.module.ComparisonTableResult;
import cn.harryai.tool.dbcompare.module.Table;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/16 15:28
 **/
@Slf4j
public class TableComparatorHandler extends AbsComparatorHandler<Table, ComparisonTableResult> {
    @Override
    protected List<ComparisonTableResult> doCompare(List<Table> left, List<Table> right, ComparisonHandlerConfig config) {
        List<ComparisonTableResult> tableResults = new ArrayList<>();
        Map<Pair<String, String>, Table> leftTableMap =
                left.stream().collect(Collectors.toMap(e -> Pair.of(e.getTableSchema(), e.getTableName()), e -> e));

        Map<Pair<String, String>, Table> rightTableMap =
                right.stream().collect(Collectors.toMap(e -> Pair.of(e.getTableSchema(), e.getTableName()), e -> e));

        for (Map.Entry<Pair<String, String>, Table> leftEntity : leftTableMap.entrySet()) {
            Pair<String, String> tableSchemaAndName = leftEntity.getKey();
            Table leftTable = leftEntity.getValue();
            Table rightTable = rightTableMap.remove(tableSchemaAndName);
            tableResults.add(generatorTableResult(tableSchemaAndName, leftTable, rightTable));
        }

        for (Map.Entry<Pair<String, String>, Table> rightEntity : rightTableMap.entrySet()) {
            Pair<String, String> tableSchemaAndName = rightEntity.getKey();
            Table rightTable = rightEntity.getValue();
            tableResults.add(generatorTableResult(tableSchemaAndName, null, rightTable));
        }
        return tableResults;
    }

    @Override
    public Class<Table> key() {
        return Table.class;
    }

    private ComparisonTableResult generatorTableResult(Pair<String, String> tableSchemaAndName, Table leftTable,
                                                       Table rightTable) {
        ComparisonTableResult tableResult = ComparisonTableResult.builder()
                .tableSchema(tableSchemaAndName.getLeft())
                .tableName(tableSchemaAndName.getRight())
                .missingL(leftTable == null ? YES : NO)
                .missingR(rightTable == null ? YES : NO)
                .build();
        fillData(leftTable, rightTable, tableResult);
        return tableResult;
    }
}
