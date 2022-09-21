package cn
        .harryai
        .tool
        .dbcompare
        .module;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.time.LocalDateTime;


/**
 * <p>
 *
 * </p>
 *
 * @author haorui.hao
 * @since 9/19/2022 7:31 PM
 **/
@Data
@Builder
public class ComparisonTableResult implements ComparisonResult<ComparisonTableResult> {
    private String tableSchema;

    private String tableName;

    private String missingL;

    private String missingR;

    private String tableTypeL;

    private String tableTypeR;

    private String engineL;

    private String engineR;

    private String rowFormatL;

    private String rowFormatR;

    private Long tableRowsL;

    private Long tableRowsR;

    private Long dataLengthL;

    private Long dataLengthR;

    private Long indexLengthL;

    private Long indexLengthR;

    private LocalDateTime createTimeL;

    private LocalDateTime createTimeR;

    private String tableCommentL;

    private String tableCommentR;

    private String indexL;

    private String indexR;

    public boolean isSame() {
        return new EqualsBuilder()
                .append(tableTypeL, tableTypeR)
                .append(engineL, engineR)
                .append(rowFormatL, rowFormatR)
                .append(indexLengthL, indexLengthR)
                .append(tableCommentL, tableCommentR)
                .append(indexL, indexR)
                .isEquals();
    }

    @Override
    public int compareTo(ComparisonTableResult o) {
        return (tableSchema + tableName).compareTo((o.tableSchema + o.tableName));
    }
}
