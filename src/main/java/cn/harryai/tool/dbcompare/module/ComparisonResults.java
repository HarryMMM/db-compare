package cn.harryai.tool.dbcompare.module;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanghanchun
 * @date 2022/3/16 18:21
 * @Description:
 */
@Data
public class ComparisonResults {
    private String tableName;
    // 表在test是否存在
    private Boolean testIsNotExsit = false;
    // test有，dev没有的字段
    private String testNewFields;
    // test没有，dev有的字典
    private String testLackFields;
    // test和dev字段类型对不上的字段
    private String testTypeFields;
    // test和dev字段是否为null对不上的字段
    private String testIsNullFlagFields;

    public ComparisonResults(String tableName) {
        this.tableName = tableName;
    }

    public String getPrintStr(String separator){
        List<String> list = new ArrayList<>();
        String[] split = this.tableName.split("\\.");
        list.add(split[0]);
        list.add(split[1]);
        list.add(this.testIsNotExsit ? "YES" : "NO");
        list.add(this.testNewFields);
        list.add(this.testLackFields);
        list.add(this.testTypeFields);
        list.add(this.testIsNullFlagFields);
        list = list.stream().map(item -> item == null ? "" : item).collect(Collectors.toList());
        return String.join(separator, list);
    }
}
