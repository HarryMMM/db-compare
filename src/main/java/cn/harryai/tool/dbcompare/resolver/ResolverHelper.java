package cn.harryai.tool.dbcompare.resolver;

import cn.harryai.tool.dbcompare.annotion.TableField;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/14 18:21
 **/
public final class ResolverHelper {
    private ResolverHelper() {
    }

    private static final Map<Class<?>, BiFunction<ResultSet,String,Object>> map=new HashMap<>();
    static {
        map.put(Integer.class,(res,key)-> {
            try {
                return res.getInt(key);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        map.put(String.class,(res,key)-> {
            try {
                return res.getString(key);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        map.put(Boolean.class,(res,key)-> {
            try {
                return res.getBoolean(key);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        map.put(Long.class,(res,key)-> {
            try {
                return res.getLong(key);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        map.put(Double.class,(res,key)-> {
            try {
                return res.getDouble(key);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        map.put(Float.class,(res,key)-> {
            try {
                return res.getFloat(key);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        map.put(float.class,(res,key)-> {
            try {
                return res.getString(key);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0f;
        });
        map.put(Object.class, (res, key) -> {
            try {
                return res.getObject(key);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        map.put(LocalDateTime.class, (res, key) -> {
            try {
                Timestamp timestamp = res.getTimestamp(key);
                if (timestamp != null) {
                    return timestamp.toLocalDateTime();
                }
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static Object getValue(Class<?> obj, ResultSet resultSet, String fieldName){
        BiFunction<ResultSet, String, Object> function = map.get(obj);
        if (function == null) {
            return null;
        }
        return function.apply(resultSet, fieldName);
    }

    public static <T> void setValue(ResultSet resultSet, T t, Field field) throws IllegalAccessException {
        TableField annotation = field.getAnnotation(TableField.class);
        if (annotation == null) {
            return;
        }
        String fieldName = annotation.value();
        if (fieldName == null) {
            return;
        }
        FieldUtils.writeField(field, t,getValue(field.getType(),resultSet,fieldName),true);
    }
}
