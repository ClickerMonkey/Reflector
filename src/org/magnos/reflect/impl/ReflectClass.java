package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectClass implements Reflect<Class<?>>
{

    @Override
    public Class<?> get( ByteBuffer in )
    {
        Integer length = Compress.getIntUnsignedNullable( in );
        
        if (length == null)
        {
            return null;
        }
        
        byte[] bytes = new byte[ length ];
        in.get( bytes );
        
        try
        {
            return Class.forName( new String( bytes ) );
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException( e );
        }
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, Class<?> v )
    {
        if ( v == null )
        {
            out.put( Compress.NULL );
        }
        else
        {
            String name = v.getCanonicalName();
            Compress.putIntUnsignedNullable( out, name.length() );
            out.put( name.getBytes() );
        }
    }

    @Override
    public int sizeOf( Class<?> v )
    {
        return (v == null ? Compress.NULL_SIZE : Compress.sizeOfUnsignedNullable( v.getCanonicalName().length() ) + v.getCanonicalName().length() );
    }

    @Override
    public int maxSize()
    {
        return -1;
    }

    @Override
    public Class<?> type()
    {
        return Class.class;
    }

}
