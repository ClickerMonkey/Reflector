package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectString implements Reflect<String>
{

    @Override
    public String get( ByteBuffer in )
    {
        Integer length = Compress.getIntNullable( in );
        
        if (length == null) 
        {
            return null;
        }
        
        byte[] bytes = new byte[ length ];
        in.get( bytes );
        
        return new String( bytes );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, String v )
    {
        if (v == null)
        {
            out.put( Compress.NULL );
        }
        else
        {
            Compress.putIntNullable( out, v.length() );
            out.put( v.getBytes()  );
        }
    }

    @Override
    public int sizeOf( String v )
    {
        return (v == null ?  Compress.NULL_SIZE : Compress.sizeOfNullable( v.length() ) + v.length() );
    }
    
    @Override
    public int maxSize()
    {
        return -1;
    }

    @Override
    public Class<?> type()
    {
        return String.class;
    }

}
