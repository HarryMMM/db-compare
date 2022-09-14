package cn.harryai.tool.dbcompare.printer.excel;

import cn.harryai.tool.dbcompare.config.PrinterConfig;
import cn.harryai.tool.dbcompare.enums.PrintFormat;
import cn.harryai.tool.dbcompare.module.Column;
import cn.harryai.tool.dbcompare.module.Comparable;
import cn.harryai.tool.dbcompare.module.ComparisonResult;
import cn.harryai.tool.dbcompare.module.Table;
import cn.harryai.tool.dbcompare.printer.AbsPinter;
import cn.harryai.tool.dbcompare.printer.DataWarp;
import cn.harryai.tool.dbcompare.util.BeanUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.nio.file.Path;
import java.nio.file.Paths;
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

        Path fileName = Paths.get(basePath,  System.currentTimeMillis()+".xlsx" );
        try (ExcelWriter excelWriter = EasyExcel.write(fileName.toString()).withTemplate(templateFileName).build()) {
            WriteSheet tableSheet = EasyExcel.writerSheet("table").build();
            WriteSheet columnSheet = EasyExcel.writerSheet("column").build();

            Map<String, Object> map = MapUtils.newHashMap();
            map.put("leftDbAlias", dataWarp.getLeftDbAlias());
            map.put("rightDbAlias", dataWarp.getRightDbAlias());
            excelWriter.fill(map, tableSheet);
            excelWriter.fill(map, columnSheet);
            Map<Class<? extends Comparable>, List<? extends ComparisonResult>> compResult = dataWarp.getCompResult();

            excelWriter.fill(dillData(compResult.get(Table.class)), tableSheet);
            excelWriter.fill(dillData(compResult.get(Column.class)), columnSheet);
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
