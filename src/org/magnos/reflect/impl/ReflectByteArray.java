package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectByteArray implements Reflect<byte[]>
{

    @Override
    public byte[] get( ByteBuffer in )
    {
        Integer length = Compress.getIntUnsignedNullable( in );
        
        if (length == null)
        {
            return null;
        }

        byte[] data = new byte[ length ];
        in.get( data );
        
        return data;
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, byte[] v )
    {
        if (v == null)
        {
            out.put( Compress.NULL );
        }
        else
        {
            Compress.putIntUnsignedNullable( out, v.length );
            out.put( v );
        }
    }

    @Override
    public int sizeOf( byte[] v )
    {
        return (v == null ? Compress.NULL_SIZE : Compress.sizeOfUnsignedNullable( v.length ) + v.length );
    }

    @Override
    public int maxSize()
    {
        return -1;
    }

    @Override
    public Class<?> type()
    {
        return byte[].class;
    }

}
