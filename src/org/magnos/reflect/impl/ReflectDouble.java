package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;


public class ReflectDouble implements Reflect<Double>
{

    @Override
    public Double get( ByteBuffer in )
    {
        return in.getDouble();
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.setDouble( obj, in.getDouble() );
    }

    @Override
    public void put( ByteBuffer out, Double v )
    {
        out.putDouble( v.doubleValue() );
    }

    @Override
    public int sizeOf( Double v )
    {
        return 8;
    }
    
    @Override
    public int maxSize()
    {
        return 8;
    }

    @Override
    public Class<?> type()
    {
        return double.class;
    }

}
