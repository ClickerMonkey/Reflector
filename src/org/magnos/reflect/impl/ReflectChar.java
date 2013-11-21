package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectChar implements Reflect<Character>
{

    @Override
    public Character get( ByteBuffer in )
    {
        return Compress.getChar( in );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.setChar( obj, Compress.getChar( in ) );
    }

    @Override
    public void put( ByteBuffer out, Character v )
    {
        Compress.putChar( out, v.charValue() );
    }

    @Override
    public int sizeOf( Character v )
    {
        return Compress.sizeOf( v.charValue() );
    }
    
    @Override
    public int maxSize()
    {
        return 3;
    }

    @Override
    public Class<?> type()
    {
        return char.class;
    }

}
