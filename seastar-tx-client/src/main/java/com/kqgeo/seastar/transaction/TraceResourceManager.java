package com.kqgeo.seastar.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.ResourceHolder;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class TraceResourceManager {
    private static final Logger logger = LoggerFactory.getLogger(TraceResourceManager.class);

    private static final ConcurrentMap<String, Map<Object, Object>> resources = new ConcurrentHashMap<>();

    private static final ConcurrentMap<String, Map<Integer, TransactionStatusSnapshot>> snapshots = new ConcurrentHashMap<>();


    public static Object getResource(String traceId, Object key) {

        Object actualKey = TransactionResourceUtils.unwrapResourceIfNecessary(key);
        Object value = doGetResource(traceId, actualKey);
        if (value != null && logger.isTraceEnabled()) {
            logger.trace("Retrieved value [" + value + "] for key [" + actualKey + "] bound to trace [" + traceId + "]");
        }
        return value;
    }

    /**
     * Actually check the value of the resource that is bound for the given key.
     */
    private static Object doGetResource(String traceId, Object actualKey) {
        Map<Object, Object> map = resources.get(traceId);
        if (map == null) {
            return null;
        }
        Object value = map.get(actualKey);
        // Transparently remove ResourceHolder that was marked as void...
        if (value instanceof ResourceHolder && ((ResourceHolder) value).isVoid()) {
            map.remove(actualKey);
            // Remove entire if empty...
            if (map.isEmpty()) {
                resources.remove(traceId);
            }
            value = null;
        }
        return value;
    }

    public static void bindResource(String traceId, Object key, Object value) {

        Object actualKey = TransactionResourceUtils.unwrapResourceIfNecessary(key);
        Assert.notNull(value, "Value must not be null");
        Map<Object, Object> map = resources.get(traceId);
        // set Map if none found
        if (map == null) {
            map = new HashMap<>();
            resources.put(traceId, map);
        }
        Object oldValue = map.put(actualKey, value);
        // Transparently suppress a ResourceHolder that was marked as void...
        if (oldValue instanceof ResourceHolder && ((ResourceHolder) oldValue).isVoid()) {
            oldValue = null;
        }
        if (oldValue != null) {
            throw new IllegalStateException("Already value [" + oldValue + "] for key [" +
                    actualKey + "] bound to trace [" + traceId + "]");
        }

        if (logger.isTraceEnabled()) {
            logger.trace("Bound value [" + value + "] for key [" + actualKey + "] to trace [" + traceId + "]");
        }
    }

    public static Object unbindResource(String traceId, Object key) {

        Object actualKey = TransactionResourceUtils.unwrapResourceIfNecessary(key);
        Object value = doUnbindResource(traceId, actualKey);
        if (value == null) {
            throw new IllegalStateException(
                    "No value for key [" + actualKey + "] bound to trace [" + traceId + "]");
        }
        return value;
    }

    private static Object doUnbindResource(String traceId, Object actualKey) {
        Map<Object, Object> map = resources.get(traceId);
        if (map == null) {
            return null;
        }
        Object value = map.remove(actualKey);
        // Remove entire if empty...
        if (map.isEmpty()) {
            resources.remove(traceId);
        }
        // Transparently suppress a ResourceHolder that was marked as void...
        if (value instanceof ResourceHolder && ((ResourceHolder) value).isVoid()) {
            value = null;
        }
        if (value != null && logger.isTraceEnabled()) {
            logger.trace("Removed value [" + value + "] for key [" + actualKey + "] from trace [" + traceId + "]");
        }
        return value;
    }

    public static void addSnapshot(String traceId, TransactionStatusSnapshot statusSnapshot) {

        Map<Integer, TransactionStatusSnapshot> map = snapshots.get(traceId);
        // set Map if none found
        if (map == null) {
            map = new HashMap<>();
            snapshots.put(traceId, map);
        }

        Integer actualKey = statusSnapshot.getSpanId();

        Object oldValue = map.put(actualKey, statusSnapshot);


        if (oldValue != null) {
            throw new IllegalStateException("Already value [" + oldValue + "] for key [" +
                    actualKey + "] bound to trace [" + traceId + "]");
        }

        if (logger.isTraceEnabled()) {
            logger.trace("Bound value [" + statusSnapshot + "] for key [" + actualKey + "] to trace [" + traceId + "]");
        }
    }

    public static TransactionStatusSnapshot getSnapshot(String traceId, int spanId) {
        Map<Integer, TransactionStatusSnapshot> map = snapshots.get(traceId);
        if (map == null) {
            return null;
        }
        return map.get(spanId);
    }

    public static TransactionStatusSnapshot removeSnapshot(String traceId, int spanId) {

        Map<Integer, TransactionStatusSnapshot> map = snapshots.get(traceId);
        if (map == null) {
            return null;
        }

        TransactionStatusSnapshot value = map.remove(spanId);
        // Remove entire if empty...
        if (map.isEmpty()) {
            snapshots.remove(traceId);
        }

        if (value != null && logger.isTraceEnabled()) {
            logger.trace("Removed value [" + value + "] for key [" + spanId + "] from trace [" + traceId + "]");
        }
        return value;
    }

    public static ConcurrentMap<String, Map<Object, Object>> getResources() {
        return resources;
    }

    public static ConcurrentMap<String, Map<Integer, TransactionStatusSnapshot>> getSnapshots() {
        return snapshots;
    }
}