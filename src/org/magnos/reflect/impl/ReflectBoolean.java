package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;


public class ReflectBoolean implements Reflect<Boolean>
{

    public static final byte F_TRUE = 1;
    public static final byte F_FALSE = 0;
    
    @Override
    public Boolean get( ByteBuffer in )
    {
        return in.get() == F_TRUE;
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.setBoolean( obj, in.get() == F_TRUE );
    }

    @Override
    public void put( ByteBuffer out, Boolean v )
    {
        out.put( v ? F_TRUE : F_FALSE );
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
        return boolean.class;
    }

}
