package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectUUID implements Reflect<UUID>
{

    @Override
    public UUID get( ByteBuffer in )
    {
        return in.get() == Compress.NULL ? null : new UUID( in.getLong(), in.getLong() );
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, UUID v )
    {
        if (v == null)
        {
            out.put( Compress.NULL );
        }
        else
        {
            out.put( Compress.NOT_NULL );
            out.putLong( v.getMostSignificantBits() );
            out.putLong( v.getLeastSignificantBits() );
        }
    }

    @Override
    public int sizeOf( UUID v )
    {
        return (v == null ? 1 : 17);
    }
    
    @Override
    public int maxSize()
    {
        return 17;
    }

    @Override
    public Class<?> type()
    {
        return UUID.class;
    }

}
