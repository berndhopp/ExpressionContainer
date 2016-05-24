package de.bhopp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MethodAccessor implements Accessor {

    private Method getMethod;
    private Method setMethod;

    MethodAccessor(Method getMethod, Method setMethod) {
        this.getMethod = getMethod;
        this.setMethod = setMethod;
    }

    public Object get(Object bean) {
        try {
            return getMethod.invoke(bean);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(Object bean, Object object) {
        try {
            setMethod.invoke(bean, object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Class getValueType() {
        return getMethod.getReturnType();
    }
}
