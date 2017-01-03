package hr.eestec_zg.frmsbackend.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;

public class Util {

    public static <K> List<K> l(K... data) {
        final List<K> retval = new ArrayList<K>();
        retval.addAll(Arrays.asList(data));
        return retval;
    }

    public static <K> Set<K> s(K... data) {
        final Set<K> retval = new HashSet<K>();
        retval.addAll(Arrays.asList(data));
        return retval;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> m(K key, V val, Object... kvals) {
        final HashMap<K, V> m = new HashMap<K, V>();
        m.put(key, val);
        for (int i = 0; i < kvals.length; ) m.put((K) kvals[i++], (V) kvals[i++]);
        return m;
    }

    public static Properties props(String... data) {
        final Properties props = new Properties();
        for (int i = 0; i < data.length; ) props.put(data[i++], data[i++]);
        return props;
    }

    public static <T> T unchecked(Callable<T> callable) {
        try {
            return callable.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

}
