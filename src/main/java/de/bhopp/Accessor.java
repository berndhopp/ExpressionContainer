package de.bhopp;

interface Accessor {
    Object get(Object bean);
    void set(Object bean, Object object);
    Class getValueType();
}
