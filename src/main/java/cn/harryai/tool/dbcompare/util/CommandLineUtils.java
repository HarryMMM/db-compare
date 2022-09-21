package cn.harryai.tool.dbcompare.util;

import cn.harryai.tool.dbcompare.DbComparator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Optional;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/21 10:00
 **/
@Slf4j
public final class CommandLineUtils {
    private static Option MODE;
    private static Option COSTOM;

    private CommandLineUtils() {
    }

    static {
        MODE = Option.builder()
                .option("m")
                .longOpt("mode")
                .hasArg()
                .required(false)
                .desc("指定要比较的数据库，custom，优先使用此参数。内置以下几个:" + System.lineSeparator() + "1. dev vs test" + System.lineSeparator() +
                        "2. test vs pre" + System.lineSeparator() + "3. pre vs prod")
                .build();
        COSTOM = Option.builder()
                .option("c")
                .longOpt("custom")
                .hasArg()
                .required(false)
                .desc("自定义比较数据库,与mode参数互斥，优先使用mode.参数格式如下：dialect:ip:port:username:password:alias|dialect:ip:port" +
                        ":username" +
                        ":password:alias" + System.lineSeparator() +
                        "ip: 数据库方言：目前支持mysql|mysql8" +
                        "port: 可选，默认使用数据库的缺省端口" +
                        "username: 必填，数据库账户" +
                        "password: 必填，数据库密码" +
                        "alias: 可选，数据库别名，输出报告使用。没有的话使用IP当做别名" +
                        System.lineSeparator() +
                        "示例 ：-c mysql8:192.168.13.70:3306:user:pwd" +
                        ":test|mysql8" +
                        ":192.168.13.175:3306:root:pwd:dev")
                .build();

    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption(MODE).addOption(COSTOM);
        return options;
    }

    public static void help() {
        help(null);
    }

    public static void help(String msg) {
        Options options = getOptions();
        HelpFormatter formatter = new HelpFormatter();
        StringBuilder message = new StringBuilder();
        if (msg != null) {
            message.append(msg)
                    .append(System.lineSeparator());
        }
        message.append("db-compare")
                .append(System.lineSeparator())
                .append("\tdb-compare是用来比较数据库表的差异的工具");
        formatter.printHelp(message.toString(), options);
    }

    public static Optional<CommandLine> parse(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Optional<CommandLine> empty = Optional.empty();
        try {
            return Optional.of(parser.parse(getOptions(), args));
        } catch (ParseException e) {
            help();
        }
        return empty;
    }

    public static void exec(String[] args) {
        Optional<CommandLine> parse = parse(args);
        if (!parse.isPresent()) {
            return;
        }
        CommandLine commandLine = parse.get();
        if (!commandLine.hasOption(MODE) && commandLine.hasOption(COSTOM)) {
            help();
        } else if (commandLine.hasOption(MODE)) {
            compareWithMode(commandLine.getOptionValue(MODE));
        } else {
            // 解析参数
        }
    }

    private static void compareWithMode(String mode) {
        Optional<Mode> instance = Mode.getInstance(mode);
        if (!instance.isPresent()) {
            help();
            return;
        }
        Mode modeEnum = instance.get();
        switch (modeEnum) {
            case DEV_VS_TEST:
                DbComparator.builder()
                        .dbCompareConfig(DbCompareConfigUtils.devVsTest())
                        .build().compare();
                break;
            case TEST_VS_PRE:
                DbComparator.builder()
                        .dbCompareConfig(DbCompareConfigUtils.testVsPre())
                        .build().compare();
                break;
            case PRE_VS_PROD:
                DbComparator.builder()
                        .dbCompareConfig(DbCompareConfigUtils.preVsProd())
                        .build().compare();
                break;
            default:
                help("不支持的比较模式[" + mode + "]");
                break;
        }

    }

    @Getter
    @AllArgsConstructor
    static enum Mode {
        DEV_VS_TEST("1"),
        TEST_VS_PRE("2"),
        PRE_VS_PROD("3");
        private String code;

        public static Optional<Mode> getInstance(String mode) {
            for (Mode value : Mode.values()) {
                if (value.getCode().equals(StringUtils.stripToEmpty(mode).toUpperCase(Locale.ROOT))) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }

}
