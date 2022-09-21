package cn.harryai.tool.dbcompare.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/21 20:09
 **/
public final class DbCompareContent {
    private static final ThreadLocal<Map<String, Object>> CONTENT = new ThreadLocal<>();
    private static final String CLI_KEY = "cli_key";
    static {
        CONTENT.set(new HashMap<>());
    }

    public static Boolean isCli() {
        return Optional.ofNullable(CONTENT.get())
                        .map(e -> e.get(CLI_KEY))
                        .map(e -> (Boolean) e).orElse(false);
    }
    public static void markCli() {
        put(CLI_KEY,true);
    }
    public static void unMarkCli() {
        remove(CLI_KEY);
    }

    public static void put(String key, Object value) {
       CONTENT.get().put(key, value);
    }
    public static void remove(String key) {
        CONTENT.get().remove(key);
    }
}
