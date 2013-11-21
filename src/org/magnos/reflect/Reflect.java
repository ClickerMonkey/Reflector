package org.magnos.reflect;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public interface Reflect<T>
{
    public T get(ByteBuffer in);
    
    public void getAndSet(ByteBuffer in, Object obj, Field field) throws IllegalAccessException;
    
    public void put(ByteBuffer out, T v);
    
    public int sizeOf(T v);
    
    public int maxSize();
    
    public Class<?> type();
}