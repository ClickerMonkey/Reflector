package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectCharObject implements Reflect<Character>
{

    @Override
    public Character get( ByteBuffer in )
    {
        return Compress.getCharNullable( in );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, Character v )
    {
        Compress.putCharNullable( out, v );
    }

    @Override
    public int sizeOf( Character v )
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
        return Character.class;
    }

}
