package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectBooleanObject implements Reflect<Boolean>
{

    public static final byte F_FALSE = 1;
    public static final byte F_TRUE = 2;
    
    @Override
    public Boolean get( ByteBuffer in )
    {
        byte b = in.get();
        
        return (b == Compress.NULL ? null : b == F_TRUE);
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get(in) );
    }

    @Override
    public void put( ByteBuffer out, Boolean v )
    {
        out.put( v == null ? Compress.NULL : (v ? F_TRUE : F_FALSE) );
    }

    @Override
    public int sizeOf( Boolean v )
    {
        return 1;
    }
    
    @Override
    public int maxSize()
    {
        return 1;
    }

    @Override
    public Class<?> type()
    {
        return Boolean.class;
    }

}
