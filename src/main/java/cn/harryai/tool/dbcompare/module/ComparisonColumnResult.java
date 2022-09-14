package cn.harryai.tool.dbcompare.module;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * <p>
 *
 * </p>
 *
 * @author haorui.hao
 * @since 9/19/2022 7:32 PM
 **/
@Data
@Builder
public class ComparisonColumnResult implements ComparisonResult<ComparisonColumnResult> {
    private String tableSchema;

    private String tableName;

    private String columnName;

    private String columnDefaultL;

    private String columnDefaultR;

    private String columnCommentL;

    private String columnCommentR;

    private String isNullableL;

    private String isNullableR;

    private String columnTypeL;

    private String columnTypeR;

    private String missingL;

    private String missingR;

    @Override
    public int compareTo(ComparisonColumnResult o) {
        return (tableSchema + tableName + columnName).compareTo((o.tableSchema + o.tableName + o.columnName));
    }

    @Override
    public boolean isSame() {
        return new EqualsBuilder()
                .append(columnDefaultL, columnDefaultR)
                .append(columnCommentL, columnCommentR)
                .append(isNullableL, isNullableR)
                .append(columnTypeL, columnTypeR)
                .isEquals();
    }
}
