
package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Date;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectDate implements Reflect<Date>
{

    @Override
    public Date get( ByteBuffer in )
    {
        Long time = Compress.getLongNullable( in );

        return (time == null ? null : new Date( time ));
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, Date v )
    {
        if (v == null)
        {
            out.put( Compress.NULL );
        }
        else
        {
            Compress.putLongNullable( out, v.getTime() );
        }
    }

    @Override
    public int sizeOf( Date v )
    {
        return Compress.sizeOfNullable( v.getTime() );
    }

    @Override
    public int maxSize()
    {
        return 10;
    }

    @Override
    public Class<?> type()
    {
        return Date.class;
    }

}
