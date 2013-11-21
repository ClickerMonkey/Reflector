package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectDoubleObject implements Reflect<Double>
{

    @Override
    public Double get( ByteBuffer in )
    {
        return in.get() == Compress.NOT_NULL ? in.getDouble() : null;
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, Double v )
    {
        if (v == null)
        {
            out.put( Compress.NULL );
        }
        else
        {
            out.put( Compress.NOT_NULL );
            out.putDouble( v.doubleValue() );
        }
    }

    @Override
    public int sizeOf( Double v )
    {
        return (v == null ? Compress.NULL_SIZE : 9);
    }
    
    @Override
    public int maxSize()
    {
        return 9;
    }

    @Override
    public Class<?> type()
    {
        return Double.class;
    }

}
