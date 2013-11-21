package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectLongObject implements Reflect<Long>
{

    @Override
    public Long get( ByteBuffer in )
    {
        return Compress.getLongNullable( in );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, Long v )
    {
        Compress.putLongNullable( out, v );
    }

    @Override
    public int sizeOf( Long v )
    {
        return Compress.sizeOfNullable( v );
    }
    
    @Override
    public int maxSize()
    {
        return 10;
    }

    @Override
    public Class<?> type()
    {
        return Long.class;
    }

}
