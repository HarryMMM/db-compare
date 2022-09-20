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
import cn.harryai.tool.dbcompare.util.ExcelUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;

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

    private static final String TABLE = "table";
    private static final String COLUMN = "column";

    public ExcelPrinter() {
    }

    @Override
    protected String doPrint(DataWarp<?, ?> dataWarp) {
        String templateFileName = getClass().getClassLoader().getResource("compResult.xlsx").getFile();
        String leftDbAlias = dataWarp.getLeftDbAlias();
        String rightDbAlias = dataWarp.getRightDbAlias();
        Path fileName = Paths.get(basePath,
                "[" + leftDbAlias + "]VS[" + rightDbAlias + "]_" + DateTimeFormatter.ofPattern(
                        "yyyyMMddHHmmss").format(LocalDateTime.now()) + ".xlsx");
        try (ExcelWriter excelWriter = EasyExcel.write(fileName.toString()).withTemplate(templateFileName)
                .registerWriteHandler(rowWriteHandler())
                .registerWriteHandler(cellBorder())
                .excelType(ExcelTypeEnum.XLSX)
                .build()) {
            WriteSheet tableSheet = EasyExcel.writerSheet(TABLE).build();
            WriteSheet columnSheet = EasyExcel.writerSheet(COLUMN).build();

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

    private static RowWriteHandler rowWriteHandler() {
        return new RowWriteHandler() {
            @Override
            public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row
                    , Integer relativeRowIndex, Boolean isHead) {
                if (row.getRowNum() < 2) {
                    CellStyle rowStyle = row.getRowStyle();
                    return;
                }
                int startRowNun;
                if (TABLE.equals(writeSheetHolder.getSheetName())) {
                    startRowNun = 2;
                } else if (COLUMN.equals(writeSheetHolder.getSheetName())) {
                    startRowNun = 3;
                } else {
                    return;
                }
                for (int i = startRowNun; i < row.getLastCellNum(); i++) {
                    Cell cell1 = row.getCell(i);
                    Cell cell2 = row.getCell(++i);
                    if (!ExcelUtils.cellValueEqual(cell1,cell2)) {
                        Sheet sheet = writeSheetHolder.getSheet();
                        Workbook workbook = sheet.getWorkbook();
                        Font font = workbook.createFont();
                        font.setFontHeightInPoints((short) 12);
                        font.setColor(IndexedColors.RED.getIndex());
                        font.setBold(true);
                        CellStyle cellStyle = workbook.createCellStyle();
                        cellStyle.cloneStyleFrom(cell1.getCellStyle());
                        cellStyle.setFont(font);
                        cell1.setCellStyle(cellStyle);
                        cell2.setCellStyle(cellStyle);
                    }
                }
            }
        };
    }

    private List<Object> dillData(List<? extends ComparisonResult> compResult) {
        List<Object> collect = compResult.stream()
                .map(e -> BeanUtils.beanToMap(e, "-"))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public PrintFormat name() {
        return PrintFormat.EXCEL;
    }

    public static HorizontalCellStyleStrategy cellBorder() {
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        return new HorizontalCellStyleStrategy(contentWriteCellStyle, contentWriteCellStyle);
    }
}
