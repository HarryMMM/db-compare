package cn.harryai.tool.dbcompare.module;

import cn.harryai.tool.dbcompare.annotion.TableField;
import cn.harryai.tool.dbcompare.annotion.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author haorui.hao
 * @since 2022-09-13
 */
@Data
@TableName("TABLES")
@NoArgsConstructor
public class Table implements Comparable {

    private static final long serialVersionUID = 1L;

    @TableField("TABLE_CATALOG")
    private String tableCatalog;

    @TableField("TABLE_SCHEMA")
    private String tableSchema;

    @TableField("TABLE_NAME")
    private String tableName;

    @TableField("TABLE_TYPE")
    private String tableType;

    @TableField("ENGINE")
    private String engine;

    @TableField(value = "VERSION")
    private Integer version;

    @TableField("ROW_FORMAT")
    private String rowFormat;

    @TableField("TABLE_ROWS")
    private Long tableRows;

    @TableField("AVG_ROW_LENGTH")
    private Long avgRowLength;

    @TableField("DATA_LENGTH")
    private Long dataLength;

    @TableField("MAX_DATA_LENGTH")
    private Long maxDataLength;

    @TableField("INDEX_LENGTH")
    private Long indexLength;

    @TableField("DATA_FREE")
    private Long dataFree;

    @TableField("AUTO_INCREMENT")
    private Long autoIncrement;

    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    @TableField("CHECK_TIME")
    private LocalDateTime checkTime;

    @TableField("TABLE_COLLATION")
    private String tableCollation;

    @TableField("CHECKSUM")
    private Long checksum;

    @TableField("CREATE_OPTIONS")
    private String createOptions;

    @TableField("TABLE_COMMENT")
    private String tableComment;

    @TableField("INDEX")
    private String index;

    List<Column> columns;


    public static final String TABLE_CATALOG = "TABLE_CATALOG";

    public static final String TABLE_SCHEMA = "TABLE_SCHEMA";

    public static final String TABLE_NAME = "TABLE_NAME";

    public static final String TABLE_TYPE = "TABLE_TYPE";

    public static final String ENGINE = "ENGINE";

    public static final String VERSION = "VERSION";

    public static final String ROW_FORMAT = "ROW_FORMAT";

    public static final String TABLE_ROWS = "TABLE_ROWS";

    public static final String AVG_ROW_LENGTH = "AVG_ROW_LENGTH";

    public static final String DATA_LENGTH = "DATA_LENGTH";

    public static final String MAX_DATA_LENGTH = "MAX_DATA_LENGTH";

    public static final String INDEX_LENGTH = "INDEX_LENGTH";

    public static final String DATA_FREE = "DATA_FREE";

    public static final String AUTO_INCREMENT = "AUTO_INCREMENT";

    public static final String CREATE_TIME = "CREATE_TIME";

    public static final String UPDATE_TIME = "UPDATE_TIME";

    public static final String CHECK_TIME = "CHECK_TIME";

    public static final String TABLE_COLLATION = "TABLE_COLLATION";

    public static final String CHECKSUM = "CHECKSUM";

    public static final String CREATE_OPTIONS = "CREATE_OPTIONS";

    public static final String TABLE_COMMENT = "TABLE_COMMENT";

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Table table = (Table) o;

        return new EqualsBuilder().append(tableSchema, table.tableSchema).append(tableName, table.tableName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tableSchema).append(tableName).toHashCode();
    }
}
