package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;


public class ReflectByte implements Reflect<Byte>
{

    @Override
    public Byte get( ByteBuffer in )
    {
        return in.get();
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.setByte( obj, in.get() );
    }

    @Override
    public void put( ByteBuffer out, Byte v )
    {
        out.put( v.byteValue() );
    }

    @Override
    public int sizeOf( Byte v )
    {
        return 1;
    }
    
    @Override
    public int maxSize()
    {
        return 1;
    }

    @Override
    public Class<?> type()
    {
        return byte.class;
    }

}
