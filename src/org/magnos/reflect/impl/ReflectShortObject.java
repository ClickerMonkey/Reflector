package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectShortObject implements Reflect<Short>
{

    @Override
    public Short get( ByteBuffer in )
    {
        return Compress.getShortNullable( in );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, Short v )
    {
        Compress.putShortNullable( out, v );
    }

    @Override
    public int sizeOf( Short v )
    {
        return Compress.sizeOfNullable( v );
    }
    
    @Override
    public int maxSize()
    {
        return 3;
    }

    @Override
    public Class<?> type()
    {
        return Short.class;
    }

}
