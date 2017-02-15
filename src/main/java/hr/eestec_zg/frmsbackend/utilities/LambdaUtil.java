package hr.eestec_zg.frmsbackend.utilities;

import org.slf4j.Logger;

import java.util.concurrent.Callable;
import java.util.function.Function;

public class LambdaUtil {

    public static <T> T unchecked(Callable<T> callable) {
        try {
            return callable.call();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void unchecked(Runnable runnable) {
        try {
            runnable.run();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T uncheckedWithLog(Callable<T> callable, Logger log) {
        try {
            return callable.call();
        } catch (RuntimeException e) {
            log.error("Error while executing unchecked lambda", e);
            throw e;
        } catch (Exception e) {
            log.error("Error while executing unchecked lambda", e);
            throw new RuntimeException(e);
        }
    }

    public static void uncheckedWithLog(Runnable runnable, Logger log) {
        try {
            runnable.run();
        } catch (RuntimeException e) {
            log.error("Error while executing unchecked lambda", e);
            throw e;
        } catch (Exception e) {
            log.error("Error while executing unchecked lambda", e);
            throw new RuntimeException(e);
        }
    }


    public static <T> T fetchSafe(Callable<T> callable, T defaultValue) {
        try {
            T result = callable.call();
            return result != null ? result : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static <T> T fetchSafeWithHandler(Callable<T> callable, T defaultValue, Function<Exception, T> handler) {
        try {
            T result = callable.call();
            return result != null ? result : defaultValue;
        } catch (Exception e) {
            return handler.apply(e);
        }
    }

    public static <T> T fetchSafeWithLog(Callable<T> callable, T defaultValue, Logger log) {
        try {
            T result = callable.call();
            return result != null ? result : defaultValue;
        } catch (Exception e) {
            log.error("Error while executing fetchSafeWithLog", e);
            return defaultValue;
        }
    }

    public static void doSafeWithLog(Runnable job, Logger log) {
        try {
            job.run();
        } catch (Exception e) {
            log.error("Error while executing doSafeWithLog", e);
        }
    }

    public static <T> T uncheckCall(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            return sneakyThrow(e);
        }
    }

    public static void uncheckRun(RunnableExc r) {
        try {
            r.run();
        } catch (Exception e) {
            sneakyThrow(e);
        }
    }

    public static <T> T sneakyThrow(Throwable e) {
        return LambdaUtil.<RuntimeException, T>sneakyThrow0(e);
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable, T> T sneakyThrow0(Throwable t) throws E {
        throw (E) t;
    }

    /**
     * Search through object via mappers.
     * Example:
     * <pre>
     * 	public static void main(String[] args) {
     * 	UserCommon user = new UserCommon();
     * 	user.name = "name";
     * 	user.permissions = new HashMap<String, Entity>();
     * 	user.permissions.put("key", new Entity());
     *
     * 	System.out.println($(user, UserCommon::getName));
     * 	System.out.println(
     *  	$($($(user, UserCommon::getPermissions), u -> u.get("unknown")), Entity::toString)
     * 	);
     *    }
     * </pre>
     *
     * @param object
     * @param mapper
     * @return
     */
    public static <T, R> R $(T object, Function<T, R> mapper) {
        return object == null ? null : mapper.apply(object);
    }


    public interface RunnableExc {
        void run() throws Exception;
    }
}
