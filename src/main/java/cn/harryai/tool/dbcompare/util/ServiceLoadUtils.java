package cn.harryai.tool.dbcompare.util;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <p>
 *
 * <p>
 *
 * @author haorui.hao
 * @since 2022/09/16 17:41
 **/
public final class ServiceLoadUtils {
    private static final Supplier<ServiceLoadUtils> SERVICE_LOAD_UTILS_SUPPLIER = ServiceLoadUtils::new;

    private ServiceLoadUtils() {
    }
    public  <K,T> Map<K,T> load(Class<T> clz, Function<T,K> keyGenerator){
        Map<K,T> map=new HashMap<>();
        ServiceLoader<T> services = ServiceLoader.load(clz);
        for (T service : services) {
            K key = keyGenerator.apply(service);
            map.put(key,service);
        }
        return map;
    }
    public static ServiceLoadUtils getInstance(){
        return SERVICE_LOAD_UTILS_SUPPLIER.get();
    }
}
