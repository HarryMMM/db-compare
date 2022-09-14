package cn.harryai.tool.dbcompare.module;

import cn.harryai.tool.dbcompare.annotion.TableField;
import cn.harryai.tool.dbcompare.annotion.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author haorui.hao
 * @since 2022-09-13
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("COLUMNS")
public class Column implements Comparable {
    private static final long serialVersionUID = 1L;

    @TableField("TABLE_CATALOG")
    private String tableCatalog;

    @TableField("TABLE_SCHEMA")
    private String tableSchema;

    @TableField("TABLE_NAME")
    private String tableName;

    @TableField("COLUMN_NAME")
    private String columnName;

    @TableField("ORDINAL_POSITION")
    private Integer ordinalPosition;

    @TableField("COLUMN_DEFAULT")
    private String columnDefault;

    @TableField("IS_NULLABLE")
    private String isNullable;

    @TableField("DATA_TYPE")
    private String dataType;

    @TableField("CHARACTER_MAXIMUM_LENGTH")
    private Long characterMaximumLength;

    @TableField("CHARACTER_OCTET_LENGTH")
    private Long characterOctetLength;

    @TableField("NUMERIC_PRECISION")
    private Long numericPrecision;

    @TableField("NUMERIC_SCALE")
    private Long numericScale;

    @TableField("DATETIME_PRECISION")
    private Integer datetimePrecision;

    @TableField("CHARACTER_SET_NAME")
    private String characterSetName;

    @TableField("COLLATION_NAME")
    private String collationName;

    @TableField("COLUMN_TYPE")
    private String columnType;

    @TableField("COLUMN_KEY")
    private String columnKey;

    @TableField("EXTRA")
    private String extra;

    @TableField("PRIVILEGES")
    private String privileges;

    @TableField("COLUMN_COMMENT")
    private String columnComment;

    @TableField("GENERATION_EXPRESSION")
    private String generationExpression;

    @TableField("SRS_ID")
    private Integer srsId;


    public static final String TABLE_CATALOG = "TABLE_CATALOG";

    public static final String TABLE_SCHEMA = "TABLE_SCHEMA";

    public static final String TABLE_NAME = "TABLE_NAME";

    public static final String COLUMN_NAME = "COLUMN_NAME";

    public static final String ORDINAL_POSITION = "ORDINAL_POSITION";

    public static final String COLUMN_DEFAULT = "COLUMN_DEFAULT";

    public static final String IS_NULLABLE = "IS_NULLABLE";

    public static final String DATA_TYPE = "DATA_TYPE";

    public static final String CHARACTER_MAXIMUM_LENGTH = "CHARACTER_MAXIMUM_LENGTH";

    public static final String CHARACTER_OCTET_LENGTH = "CHARACTER_OCTET_LENGTH";

    public static final String NUMERIC_PRECISION = "NUMERIC_PRECISION";

    public static final String NUMERIC_SCALE = "NUMERIC_SCALE";

    public static final String DATETIME_PRECISION = "DATETIME_PRECISION";

    public static final String CHARACTER_SET_NAME = "CHARACTER_SET_NAME";

    public static final String COLLATION_NAME = "COLLATION_NAME";

    public static final String COLUMN_TYPE = "COLUMN_TYPE";

    public static final String COLUMN_KEY = "COLUMN_KEY";

    public static final String EXTRA = "EXTRA";

    public static final String PRIVILEGES = "PRIVILEGES";

    public static final String COLUMN_COMMENT = "COLUMN_COMMENT";

    public static final String GENERATION_EXPRESSION = "GENERATION_EXPRESSION";

    public static final String SRS_ID = "SRS_ID";

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Column column = (Column) o;

        return new EqualsBuilder().append(tableSchema, column.tableSchema).append(tableName, column.tableName).append(columnName, column.columnName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tableSchema).append(tableName).append(columnName).toHashCode();
    }
}
