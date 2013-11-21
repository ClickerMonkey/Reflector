package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectInt implements Reflect<Integer>
{

    @Override
    public Integer get( ByteBuffer in )
    {
        return Compress.getInt( in );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.setInt( obj, Compress.getInt( in ) );
    }

    @Override
    public void put( ByteBuffer out, Integer v )
    {
        Compress.putInt( out, v.intValue() );
    }

    @Override
    public int sizeOf( Integer v )
    {
        return Compress.sizeOf( v.intValue() );
    }
    
    @Override
    public int maxSize()
    {
        return 5;
    }

    @Override
    public Class<?> type()
    {
        return int.class;
    }

}
