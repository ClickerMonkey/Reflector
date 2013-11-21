package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;


public class ReflectNull implements Reflect<Object>
{

    public static final ReflectNull INSTANCE = new ReflectNull();
    
    @Override
    public Object get( ByteBuffer in )
    {
        return null;
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, null );
    }

    @Override
    public void put( ByteBuffer out, Object v )
    {
        
    }

    @Override
    public int sizeOf( Object v )
    {
        return 0;
    }

    @Override
    public int maxSize()
    {
        return 0;
    }

    @Override
    public Class<?> type()
    {
        return null;
    }

}
