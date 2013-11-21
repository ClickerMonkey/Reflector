package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectIntObject implements Reflect<Integer>
{

    @Override
    public Integer get( ByteBuffer in )
    {
        return Compress.getIntNullable( in );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, Integer v )
    {
        Compress.putIntNullable( out, v );
    }

    @Override
    public int sizeOf( Integer v )
    {
        return Compress.sizeOfNullable( v );
    }
    
    @Override
    public int maxSize()
    {
        return 5;
    }

    @Override
    public Class<?> type()
    {
        return Integer.class;
    }

}
