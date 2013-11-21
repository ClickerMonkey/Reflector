package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;


public class ReflectFloat implements Reflect<Float>
{

    @Override
    public Float get( ByteBuffer in )
    {
        return in.getFloat();
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.setFloat( obj, in.getFloat() );
    }

    @Override
    public void put( ByteBuffer out, Float v )
    {
        out.putFloat( v.floatValue() );
    }

    @Override
    public int sizeOf( Float v )
    {
        return 4;
    }
    
    @Override
    public int maxSize()
    {
        return 4;
    }

    @Override
    public Class<?> type()
    {
        return float.class;
    }

}
