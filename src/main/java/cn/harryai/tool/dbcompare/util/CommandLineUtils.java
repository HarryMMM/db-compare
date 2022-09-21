package cn.harryai.tool.dbcompare.util;

import cn.harryai.tool.dbcompare.DbComparator;
import cn.harryai.tool.dbcompare.config.ComparisonHandlerConfig;
import cn.harryai.tool.dbcompare.config.DbCompareConfig;
import cn.harryai.tool.dbcompare.config.DbConfig;
import cn.harryai.tool.dbcompare.config.SchemaConfig;
import cn.harryai.tool.dbcompare.config.TableConfig;
import cn.harryai.tool.dbcompare.enums.DialectEnum;
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
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
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
    private static final Option MODE;

    private static final Option CUSTOM;

    private static final Option HELP;

    private static final Option SCHEMA;

    private static final Option TABLE;

    private static final Option EXCLUDE_TABLE;

    private static final Option FULL_MODE;

    private static final Option VERSION;


    private CommandLineUtils() {
    }

    static {
        HELP = Option.builder()
                .option("h")
                .longOpt("help")
                .hasArg()
                .required(false)
                .desc("帮助信息")
                .build();
        MODE = Option.builder()
                .option("m")
                .longOpt("mode")
                .hasArg()
                .required(false)
                .desc("指定要使用的模式，与-c互斥，同时使用优先使用此参数。内置以下几个:" + System.lineSeparator() + "1. dev vs test" + System.lineSeparator() +
                        "2. test vs pre" + System.lineSeparator() + "3. pre vs prod")
                .build();
        CUSTOM = Option.builder()
                .option("c")
                .longOpt("custom")
                .hasArg()
                .required(false)
                .desc("自定义比较数据库,与-c参数互斥，同时使用优先使用此参数.参数格式如下：" + System.lineSeparator() +
                        "dialect:ip:port:username:password:alias|dialect:ip:port:username:password:alias"
                        + System.lineSeparator() +
                        "dialect: 必填，数据库方言：目前支持mysql|mysql8" +System.lineSeparator()+
                        "host: 必填，数据库主机地址" +System.lineSeparator()+
                        "port: 可选，默认使用数据库的缺省端口" +System.lineSeparator()+
                        "username: 必填，数据库账户" +System.lineSeparator()+
                        "password: 必填，数据库密码" +System.lineSeparator()+
                        "alias: 可选，数据库别名，输出报告使用。没有的话使用IP当做别名"
                        + System.lineSeparator() +
                        "示例 ：-c mysql8:192.168.13.70:3306:user:pwd:test|mysql8"
                        + ":192.168.13.175:3306:root:pwd:dev")
                .build();

        SCHEMA = Option.builder()
                .option("s")
                .longOpt("schema")
                .hasArg()
                .required(false)
                .desc("比对的schema，可结合-e或-t使用。指定某些schema.默认比较scp全部的schema. eg. -s schema1,schema2")
                .build();

        TABLE = Option.builder()
                .option("t")
                .longOpt("table")
                .hasArg()
                .required(false)
                .desc("参与比对的表信息。与-e互斥,同时设置优先使用此参数，忽略-e。 eg. -t table1,table2")
                .build();

        EXCLUDE_TABLE = Option.builder()
                .option("e")
                .longOpt("exclude-table")
                .hasArg()
                .required(false)
                .desc("不参与对比的表信息,与-t互斥，同时设置优先使用-t参数，忽略此参数,默认排除undo_log表。eg. -e table1,table2")
                .build();
        FULL_MODE = Option.builder()
                .option("f")
                .longOpt("full-mode")
                .hasArg(false)
                .desc("比对报告输出所有表的比对信息。不加此参数默认只输出有差异的表信息")
                .build();
        VERSION = Option.builder()
                .option("v")
                .longOpt("version")
                .hasArg(false)
                .desc("版本信息")
                .build();


    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption(MODE)
                .addOption(CUSTOM)
                .addOption(HELP)
                .addOption(TABLE)
                .addOption(EXCLUDE_TABLE)
                .addOption(SCHEMA)
                .addOption(VERSION)
                .addOption(FULL_MODE);
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
        message.append("db-compare 用来比较数据库表的差异的工具");
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
        String[] postDillArgs = argsPreDillArgs(args);
        Optional<CommandLine> parse = parse(postDillArgs);
        if (!parse.isPresent()) {
            return;
        }
        CommandLine commandLine = parse.get();

        if (commandLine.hasOption(VERSION)){
            System.out.println("db-compare version 0.0.1");
        }else if (!commandLine.hasOption(MODE) && !commandLine.hasOption(CUSTOM)) {
            help();
        } else if (commandLine.hasOption(MODE)) {
            compareWithMode(commandLine, commandLine.getOptionValue(MODE));
        } else if (commandLine.hasOption(CUSTOM)) {
            customCompare(commandLine);
        }
    }

    private static String[] argsPreDillArgs(String[] args) {
        return Arrays.stream(args).map(StringUtils::trim).toArray(String[]::new);
    }

    private static void customCompare(CommandLine commandLine) {
        String customArgs = commandLine.getOptionValue(CUSTOM);
        Pair<DbConfig, DbConfig> dbConfigDbConfigPair = parseArgs(customArgs);
        DbCompareConfig config = DbCompareConfig.builder()
                .leftDb(dbConfigDbConfigPair.getLeft())
                .rightDb(dbConfigDbConfigPair.getRight())
                .comparisonHandlerConfig(ComparisonHandlerConfig.builder()
                        .fullMode(commandLine.hasOption(FULL_MODE))
                        .build())
                .build();

        DbCompareConfig dbCompareConfig = setSchemaOrTableFilter(commandLine, config);
        DbComparator.builder().dbCompareConfig(dbCompareConfig).build().compare();
    }

    private static DbCompareConfig setSchemaOrTableFilter(CommandLine commandLine, DbCompareConfig config) {
        config.setSchema(getSchemaConfig(commandLine));
        // 如果设置了table（-t）参数，则设置TableConfig,否则设置SchemaConfig
        if (commandLine.hasOption(TABLE)) {
            String optionValue = commandLine.getOptionValue(TABLE);
            String[] tableNames = optionValue.split(",");
            TableConfig tableConfig = DbCompareConfigUtils.tableConfig();
            tableConfig.setTableNames(tableNames);
            config.setTable(tableConfig);

        } else if (commandLine.hasOption(EXCLUDE_TABLE)) {
            String optionValue = commandLine.getOptionValue(EXCLUDE_TABLE);
            String[] excludeTables = optionValue.split(",");
            config.getSchema().setExcludeTableNames(excludeTables);
        }
        return config;
    }

    private static SchemaConfig getSchemaConfig(CommandLine commandLine) {
        SchemaConfig schemaConfig = DbCompareConfigUtils.schemaConfig();
        if (commandLine.hasOption(SCHEMA)) {
            String optionValue = commandLine.getOptionValue(SCHEMA);
            String[] schemaNames = optionValue.split(",");
            schemaConfig.setSchemaNames(schemaNames);
        }
        return schemaConfig;
    }

    private static Pair<DbConfig, DbConfig> parseArgs(String customArgs) {
        String[] split = customArgs.split("\\|");
        if (split.length < 2) {
            help("-c 参数不正确,请查看使用说明");
        }
        return Pair.of(convertToDbConfig(split[0]), convertToDbConfig(split[1]));
    }

    private static DbConfig convertToDbConfig(String arg) {
        String[] split = arg.split(":");
        String dialect = split[0];
        String host = split[1];
        String port = split[2];
        String username = split[3];
        String password = split[4];
        String alias = split[5];
        Optional<DialectEnum> instance = DialectEnum.getInstance(dialect);
        if (!instance.isPresent()) {
            help("数据库方言必填");
            System.exit(1);
        }
        if (StringUtils.isEmpty(host)) {
            help("数据库主机地址必填");
            System.exit(1);
        }
        if (StringUtils.isEmpty(username)) {
            help("数据库用户名必填");
            System.exit(1);
        }
        if (StringUtils.isEmpty(password)) {
            help("数据库密码必填");
            System.exit(1);
        }
        if (StringUtils.isEmpty(alias)) {
            alias = host;
        }

        return DbConfig.builder()
                .dialect(instance.get())
                .host(host)
                .port(Integer.valueOf(port))
                .userName(username)
                .password(password)
                .alias(alias)
                .build();
    }

    private static void compareWithMode(CommandLine commandLine, String mode) {
        Optional<Mode> instance = Mode.getInstance(mode);
        if (!instance.isPresent()) {
            help();
            return;
        }
        Mode modeEnum = instance.get();
        DbCompareConfig dbCompareConfig = null;
        switch (modeEnum) {
            case DEV_VS_TEST:
                dbCompareConfig = DbCompareConfigUtils.devVsTest();
                break;
            case TEST_VS_PRE:
                dbCompareConfig = DbCompareConfigUtils.testVsPre();
                break;
            case PRE_VS_PROD:
                dbCompareConfig = DbCompareConfigUtils.preVsProd();
                break;
            default:
                help("不支持的比较模式[" + mode + "]");
                System.exit(1);
                break;
        }
        dbCompareConfig.getComparisonHandlerConfig().setFullMode(commandLine.hasOption(FULL_MODE));
        DbComparator.builder()
                .dbCompareConfig(setSchemaOrTableFilter(commandLine, dbCompareConfig))
                .build().compare();

    }

    @Getter
    @AllArgsConstructor
    static enum Mode {
        /**
         *
         */
        DEV_VS_TEST("1"),

        /**
         *
         */
        TEST_VS_PRE("2"),

        /**
         *
         */
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
