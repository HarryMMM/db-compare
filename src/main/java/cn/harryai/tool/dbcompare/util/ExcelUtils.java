package cn.harryai.tool.dbcompare.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.text.SimpleDateFormat;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/20 23:03
 **/
public final class ExcelUtils {
    private ExcelUtils() {
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        // 以下是判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue =
                            sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                } else {
                    DataFormatter dataFormatter = new DataFormatter();
                    cellValue = dataFormatter.formatCellValue(cell);
                }
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case FORMULA:
                cellValue = cell.getCellFormula() + "";
                break;
            default:
                cellValue = "";
                break;
        }
        return cellValue;
    }

    public static boolean cellValueEqual(Cell c1, Cell c2) {
        return getCellValue(c1).equals(getCellValue(c2));
    }
}
