package cn.harryai.tool.dbcompare.printer.excel;

import cn.harryai.tool.dbcompare.config.PrinterConfig;
import cn.harryai.tool.dbcompare.enums.PrintFormat;
import cn.harryai.tool.dbcompare.module.Column;
import cn.harryai.tool.dbcompare.module.Comparable;
import cn.harryai.tool.dbcompare.module.ComparisonColumnResult;
import cn.harryai.tool.dbcompare.module.ComparisonResult;
import cn.harryai.tool.dbcompare.module.ComparisonTableResult;
import cn.harryai.tool.dbcompare.module.Table;
import cn.harryai.tool.dbcompare.printer.AbsPinter;
import cn.harryai.tool.dbcompare.printer.DataWarp;
import cn.harryai.tool.dbcompare.util.BeanUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/16 14:20
 **/
public final class ExcelPrinter extends AbsPinter<PrinterConfig> {
    public ExcelPrinter() {
    }

    @Override
    protected String doPrint(DataWarp<?, ?> dataWarp) {
        String templateFileName = getClass().getClassLoader().getResource("compResult.xlsx").getFile();
        String leftDbAlias = dataWarp.getLeftDbAlias();
        String rightDbAlias = dataWarp.getRightDbAlias();
        Path fileName = Paths.get(basePath,
                "["+leftDbAlias + "]VS[" + rightDbAlias + "]_" + DateTimeFormatter.ofPattern(
                "yyyyMMddHHmmSS").format(LocalDateTime.now()) + ".xlsx");
        try (ExcelWriter excelWriter = EasyExcel.write(fileName.toString()).withTemplate(templateFileName).build()) {
            WriteSheet tableSheet = EasyExcel.writerSheet("table").build();
            WriteSheet columnSheet = EasyExcel.writerSheet("column").build();

            Map<String, Object> map = MapUtils.newHashMap();
            map.put("leftDbAlias", leftDbAlias);
            map.put("rightDbAlias", rightDbAlias);
            excelWriter.fill(map, tableSheet);
            excelWriter.fill(map, columnSheet);
            Map<Class<? extends Comparable>, List<? extends ComparisonResult>> compResult = dataWarp.getCompResult();

            List<ComparisonTableResult> compResult1 = (List<ComparisonTableResult>) compResult.get(Table.class);
            excelWriter.fill(dillData(compResult1), tableSheet);

            // 删除表不存在的列。没有比较的意义。
            Map<Pair<String, String>, Pair<String, String>> collect =
                    compResult1.stream().collect(Collectors.toMap(e -> Pair.of(e.getTableSchema(), e.getTableName()),
                            e -> Pair.of(e.getMissingL(), e.getMissingR())));
            List<ComparisonColumnResult> compResult2 = (List<ComparisonColumnResult>) compResult.get(Column.class);
            compResult2.removeIf(e -> {
                Pair<String, String> stringStringPair = collect.get(Pair.of(e.getTableSchema(), e.getTableName()));
                return stringStringPair != null && StringUtils.equalsAny("YES", stringStringPair.getLeft(),
                        stringStringPair.getRight());
            });
            excelWriter.fill(dillData(compResult2), columnSheet);
        }
        return fileName.toString();
    }

    private List<Object> dillData(List<? extends ComparisonResult> compResult) {
        List<Object> collect = compResult.stream()
                .map(e -> BeanUtils.beanToMap(e,"-" ))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public PrintFormat name() {
        return PrintFormat.EXCEL;
    }
}
