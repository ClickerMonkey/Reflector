package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectFloatObject implements Reflect<Float>
{

    @Override
    public Float get( ByteBuffer in )
    {
        return in.get() == Compress.NOT_NULL ? in.getFloat() : null;
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, Float v )
    {
        if (v == null)
        {
            out.put( Compress.NULL );
        }
        else
        {
            out.put( Compress.NOT_NULL );
            out.putFloat( v.floatValue() );    
        }
    }

    @Override
    public int sizeOf( Float v )
    {
        return (v == null ? Compress.NULL_SIZE : 5);
    }
    
    @Override
    public int maxSize()
    {
        return 5;
    }

    @Override
    public Class<?> type()
    {
        return Float.class;
    }

}
