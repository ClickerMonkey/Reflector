package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectShort implements Reflect<Short>
{

    @Override
    public Short get( ByteBuffer in )
    {
        return Compress.getShort( in );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.setShort( obj, Compress.getShort( in ) );
    }

    @Override
    public void put( ByteBuffer out, Short v )
    {
        Compress.putShort( out, v.shortValue() );
    }

    @Override
    public int sizeOf( Short v )
    {
        return Compress.sizeOf( v.intValue() );
    }
    
    @Override
    public int maxSize()
    {
        return 3;
    }

    @Override
    public Class<?> type()
    {
        return short.class;
    }

}
