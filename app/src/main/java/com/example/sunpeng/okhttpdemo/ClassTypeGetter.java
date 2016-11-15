package com.example.sunpeng.okhttpdemo;

import java.lang.reflect.ParameterizedType;

/**
 * Created by sunpeng on 2016/11/9.
 */

public class ClassTypeGetter<T> {
    public Class<T> get(){
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) superClass.getActualTypeArguments()[0];
    }
}
