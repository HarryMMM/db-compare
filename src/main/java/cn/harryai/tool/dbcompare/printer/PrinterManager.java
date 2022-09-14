package cn.harryai.tool.dbcompare.printer;

import cn.harryai.tool.dbcompare.config.PrinterConfig;
import cn.harryai.tool.dbcompare.enums.PrintFormat;
import cn.harryai.tool.dbcompare.util.ServiceLoadUtils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/16 14:34
 **/
public final class PrinterManager {
    private static final Supplier<PrinterManager> PRINTER_MANAGER_SUPPLIER = PrinterManager::new;

    @SuppressWarnings("rawtypes")
    private final Map<PrintFormat, IPinter> PRINTER_MAP =
            ServiceLoadUtils.getInstance().load(IPinter.class, IPinter::name);


    @SuppressWarnings("unchecked")
    public IPinter<PrinterConfig> getPrinter(PrinterConfig printerConfig) {
        AbsPinter<PrinterConfig> printer =
                (AbsPinter<PrinterConfig>) PRINTER_MAP.get(printerConfig.getPrintFormat());
        printer.configure(printerConfig);
        return printer;
    }

    public static PrinterManager getInstance() {
        return PRINTER_MANAGER_SUPPLIER.get();
    }
}
