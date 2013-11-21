package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectLong implements Reflect<Long>
{

    @Override
    public Long get( ByteBuffer in )
    {
        return Compress.getLong( in );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.setLong( obj, Compress.getLong( in ) );
    }

    @Override
    public void put( ByteBuffer out, Long v )
    {
        Compress.putLong( out, v.longValue() );
    }

    @Override
    public int sizeOf( Long v )
    {
        return Compress.sizeOf( v.longValue() );
    }
    
    @Override
    public int maxSize()
    {
        return 10;
    }

    @Override
    public Class<?> type()
    {
        return long.class;
    }

}
