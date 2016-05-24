package de.bhopp;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class BeanItem<T> implements Item {

    final T bean;
    private final Class<T> beanClass;
    private Map<String, ExpressionProperty> properties;

    BeanItem(T bean, Class<T> beanClass){
        this.bean = bean;
        this.beanClass = beanClass;
    }

    public Property getItemProperty(Object id) {

        String key = ExpressionContainer.asString(id);

        if(properties == null){
            properties = new ConcurrentHashMap<String, ExpressionProperty>(Accessors.getItemPropertyIds(beanClass).size());
        } else {
            ExpressionProperty cachedProperty = properties.get(key);

            if(cachedProperty != null){
                return cachedProperty;
            }
        }

        Accessor expression = Accessors.getAccessor(beanClass, key);

        ExpressionProperty expressionProperty = new ExpressionProperty(this, expression);

        properties.put(key, expressionProperty);

        return expressionProperty;
    }

    public Collection<?> getItemPropertyIds() {
        return Accessors.getItemPropertyIds(beanClass);
    }

    public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

}
