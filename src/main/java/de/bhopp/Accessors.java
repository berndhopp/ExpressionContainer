package de.bhopp;

import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.beans.Introspector.getBeanInfo;

class Accessors {

    private static final SpelExpressionParser parser = new SpelExpressionParser();
    private static final Map<Class<?>, Map<String, Accessor>> accessorCache = new ConcurrentHashMap<Class<?>, Map<String, Accessor>>();
    private static final Map<Class<?>, Set<String>> propertyIdCache = new ConcurrentHashMap<Class<?>, Set<String>>();

    static Accessor getAccessor(Class<?> beanClass, String key){
        Map<String, Accessor> accessorMap = getAccessorMap(beanClass);

        Accessor expression = accessorMap.get(key);

        if(expression == null){
            expression = new ExpressionAccessor(parser.parseExpression(key));
            accessorMap.put(key, expression);
        }

        return expression;
    }

    static Collection<?> getItemPropertyIds(Class<?> beanClass) {
        return propertyIdCache.get(beanClass);
    }

    private static Map<String, Accessor> getAccessorMap(Class<?> beanClass){
        return accessorCache.get(beanClass);
    }

    static void register(Class<?> beanClass) {
        Map<String, Accessor> expressionMap = accessorCache.get(beanClass);

        if(expressionMap == null){
            expressionMap = new ConcurrentHashMap<String, Accessor>();

            try {
                Set<String> propertyIds = new HashSet<String>();

                for (PropertyDescriptor propertyDescriptor : getBeanInfo(beanClass).getPropertyDescriptors()) {
                    if(propertyDescriptor.isHidden()){
                        continue;
                    }

                    if("class".equals(propertyDescriptor.getName())){
                        continue;
                    }

                    String property = propertyDescriptor.getName();

                    propertyIds.add(property);

                    expressionMap.put(property, new MethodAccessor(propertyDescriptor.getReadMethod(), propertyDescriptor.getWriteMethod()));
                }

                propertyIdCache.put(beanClass, propertyIds);

            } catch (IntrospectionException e) {
                throw new RuntimeException(e);
            }

            accessorCache.put(beanClass, expressionMap);
        }
    }
}
