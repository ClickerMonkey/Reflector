package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectByteObject implements Reflect<Byte>
{

    @Override
    public Byte get( ByteBuffer in )
    {
        return Compress.getByteNullable( in );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, Byte v )
    {
        Compress.putByteNullable( out, v );
    }

    @Override
    public int sizeOf( Byte v )
    {
        return Compress.sizeOfNullable( v );
    }
    
    @Override
    public int maxSize()
    {
        return 2;
    }

    @Override
    public Class<?> type()
    {
        return Byte.class;
    }

}
