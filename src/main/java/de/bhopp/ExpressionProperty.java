package de.bhopp;

import com.vaadin.data.Property;

class ExpressionProperty implements Property {
    private Object bean;
    private final Accessor accessor;
    private boolean readOnly;

    ExpressionProperty(Object bean, Accessor accessor) {
        this.bean = bean;
        this.accessor = accessor;
        readOnly = false;
    }

    public Object getValue() {
        return accessor.get(bean);
    }

    public void setValue(Object newValue) throws ReadOnlyException {
        if(readOnly){
            throw new ReadOnlyException();
        }

        accessor.set(bean, newValue);
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
