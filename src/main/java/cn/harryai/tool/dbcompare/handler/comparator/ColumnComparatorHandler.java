package cn.harryai.tool.dbcompare.handler.comparator;

import cn.harryai.tool.dbcompare.config.ComparisonHandlerConfig;
import cn.harryai.tool.dbcompare.module.Column;
import cn.harryai.tool.dbcompare.module.Comparable;
import cn.harryai.tool.dbcompare.module.ComparisonColumnResult;
import cn.harryai.tool.dbcompare.module.ComparisonResult;
import cn.harryai.tool.dbcompare.module.ComparisonTableResult;
import cn.harryai.tool.dbcompare.module.Table;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

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
public class ColumnComparatorHandler extends AbsComparatorHandler<Column, ComparisonColumnResult> {

    @Override
    protected List<ComparisonColumnResult> doCompare(List<Column> left, List<Column> right,
                                                     ComparisonHandlerConfig config) {
        List<ComparisonColumnResult> columnResults = new ArrayList<>();

        Map<Triple<String, String, String>, Column> leftTableMap =
                left.stream().collect(Collectors.toMap(e -> Triple.of(e.getTableSchema(), e.getTableName(),e.getColumnName()),
                        e -> e));

        Map<Triple<String, String, String>, Column> rightTableMap =
                right.stream().collect(Collectors.toMap(e -> Triple.of(e.getTableSchema(), e.getTableName(),e.getColumnName()), e -> e));

        for (Map.Entry<Triple<String, String, String>, Column> leftEntity : leftTableMap.entrySet()) {
            Triple<String, String, String> tableSchemaAndTableNameAndColumnName = leftEntity.getKey();
            Column leftColumn = leftEntity.getValue();
            Column rightColumn = rightTableMap.remove(tableSchemaAndTableNameAndColumnName);
            columnResults.add(generatorColumnResult(tableSchemaAndTableNameAndColumnName, leftColumn, rightColumn));
        }
        for (Map.Entry<Triple<String, String, String>, Column> rightEntity : rightTableMap.entrySet()) {
            Triple<String, String, String> tableSchemaAndTableNameAndColumnName = rightEntity.getKey();
            Column rightColumn = rightEntity.getValue();
            columnResults.add(generatorColumnResult(tableSchemaAndTableNameAndColumnName, null, rightColumn));
        }
        return columnResults;
    }

    private ComparisonColumnResult generatorColumnResult(Triple<String, String, String> tSchemaAndTNameAndCName, Column leftColumn, Column rightColumn) {
        ComparisonColumnResult comparisonColumnResult = ComparisonColumnResult.builder()
                .tableSchema(tSchemaAndTNameAndCName.getLeft())
                .tableName(tSchemaAndTNameAndCName.getMiddle())
                .columnName(tSchemaAndTNameAndCName.getRight())
                .missingL(leftColumn == null ? YES : NO)
                .missingR(rightColumn == null ? YES : NO)
                .build();
        fillData(leftColumn,rightColumn,comparisonColumnResult);
        return comparisonColumnResult;
    }


    @Override
    public Class<Column> key() {
        return Column.class;
    }
}
