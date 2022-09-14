package cn.harryai.tool.dbcompare.printer;

import cn.harryai.tool.dbcompare.module.Column;
import cn.harryai.tool.dbcompare.module.Comparable;
import cn.harryai.tool.dbcompare.module.ComparisonColumnResult;
import cn.harryai.tool.dbcompare.module.ComparisonResult;
import cn.harryai.tool.dbcompare.module.ComparisonTableResult;
import cn.harryai.tool.dbcompare.module.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/16 14:13
 **/
@Data
@AllArgsConstructor
public class DataWarp<T extends ComparisonTableResult,C extends ComparisonColumnResult> {
  private  Map<Class<? extends Comparable>,List<? extends ComparisonResult>>  compResult;
  private String leftDbAlias;
  private String rightDbAlias;
}
