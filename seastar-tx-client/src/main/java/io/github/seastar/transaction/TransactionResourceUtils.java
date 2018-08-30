package io.github.seastar.transaction;

import org.springframework.aop.scope.ScopedObject;
import org.springframework.core.InfrastructureProxy;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

abstract class TransactionResourceUtils {

    private static final boolean aopAvailable = ClassUtils.isPresent(
            "org.springframework.aop.scope.ScopedObject", TransactionResourceUtils.class.getClassLoader());


    static Object unwrapResourceIfNecessary(Object resource) {
        Assert.notNull(resource, "Resource must not be null");
        Object resourceRef = resource;
        // unwrap infrastructure proxy
        if (resourceRef instanceof InfrastructureProxy) {
            resourceRef = ((InfrastructureProxy) resourceRef).getWrappedObject();
        }
        if (aopAvailable) {
            // now unwrap scoped proxy
            resourceRef = ScopedProxyUnwrapper.unwrapIfNecessary(resourceRef);
        }
        return resourceRef;
    }


    /**
     * Inner class to avoid hard-coded dependency on AOP module.
     */
    private static class ScopedProxyUnwrapper {

        public static Object unwrapIfNecessary(Object resource) {
            if (resource instanceof ScopedObject) {
                return ((ScopedObject) resource).getTargetObject();
            } else {
                return resource;
            }
        }
    }

}
