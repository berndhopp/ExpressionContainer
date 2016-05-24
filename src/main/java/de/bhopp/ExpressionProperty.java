package de.bhopp;

import com.vaadin.data.Property;

class ExpressionProperty implements Property {
    private BeanItem beanItem;
    private final Accessor accessor;
    private Object value;
    private boolean readOnly;

    ExpressionProperty(BeanItem beanItem, Accessor accessor) {
        this.beanItem = beanItem;
        this.accessor = accessor;
        readOnly = false;
    }

    public Object getValue() {
        if(value == null){
            value = accessor.get(beanItem.bean);
        }

        return value;
    }

    public void setValue(Object newValue) throws ReadOnlyException {
        if(readOnly){
            throw new ReadOnlyException();
        }

        accessor.set(beanItem.bean, newValue);
        this.value = newValue;
    }

    public Class getType() {
        return accessor.getValueType();
    }

    public boolean isReadOnly() {
        return false;
    }

    public void setReadOnly(boolean newStatus) {
        readOnly = newStatus;
    }
}
