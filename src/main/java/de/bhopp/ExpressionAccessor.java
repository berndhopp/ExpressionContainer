package de.bhopp;

import org.springframework.expression.Expression;

class ExpressionAccessor implements Accessor {
    private Expression expression;

    ExpressionAccessor(Expression expression) {
        this.expression = expression;
    }

    public Object get(Object bean) {
        return expression.getValue(bean);
    }

    public void set(Object bean, Object object) {
        expression.setValue(bean, object);
    }

    public Class getValueType() {
        return Object.class;
    }
}
