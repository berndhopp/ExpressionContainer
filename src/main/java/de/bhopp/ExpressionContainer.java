package de.bhopp;

import com.vaadin.data.Property;
import com.vaadin.data.util.AbstractInMemoryContainer;

import java.util.Collection;

public class ExpressionContainer<T> extends AbstractInMemoryContainer<T, String, BeanItem<T>> {

    private final Class<T> beanClass;

    public ExpressionContainer(Class<T> beanClass){
        this.beanClass = beanClass;
        Accessors.register(beanClass);
    }

    public Collection<?> getContainerPropertyIds() {
        return Accessors.getItemPropertyIds(beanClass);
    }

    protected BeanItem<T> getUnfilteredItem(Object itemId) {
        return new BeanItem<T>(asBean(itemId), beanClass);
    }

    public Property getContainerProperty(Object itemId, Object propertyId) {
            return getItem(itemId).getItemProperty(propertyId);
    }

    public Class<?> getType(Object propertyId) {
        return Accessors.getAccessor(beanClass, asString(propertyId)).getValueType();
    }

    static String asString(Object o){
        try {
            return (String) o;
        } catch (ClassCastException e){
            throw new RuntimeException("only String allowed");
        }
    }

    @SuppressWarnings("unchecked")
    private T asBean(Object o){
        try {
            return (T)o;
        } catch (ClassCastException e){
            throw new RuntimeException("only " + beanClass + " allowed");
        }
    }
}
