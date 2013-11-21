
package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;


public class ReflectBooleanArray implements Reflect<boolean[]>
{

    public static final byte F_TRUE = 1;
    public static final byte F_FALSE = 0;

    @Override
    public boolean[] get( ByteBuffer in )
    {
        Integer length = Compress.getIntUnsignedNullable( in );

        if (length == null)
        {
            return null;
        }

        boolean[] array = new boolean[length];

        int byteCount = toBytes( length );
        byte[] bytes = new byte[byteCount];
        in.get( bytes );

        for (int i = 0; i < length; i++)
        {
            array[i] = (bytes[i >> 3] & 7) == 7;
        }

        return array;
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, boolean[] v )
    {
        if (v == null)
        {
            out.put( Compress.NULL );
        }
        else
        {
            Compress.putIntUnsignedNullable( out, v.length );
            byte[] bytes = new byte[toBytes( v.length )];

            for (int i = 0; i < v.length; i++)
            {
                if (v[i])
                {
                    bytes[i >> 3] |= (1 << (i & 7));
                }
            }
        }
    }

    @Override
    public int sizeOf( boolean[] v )
    {
        return (v == null ? Compress.NULL_SIZE : Compress.sizeOfUnsignedNullable( v.length ) + toBytes( v.length ));
    }

    @Override
    public Class<?> type()
    {
        return boolean[].class;
    }
    
    @Override
    public int maxSize()
    {
        return -1;
    }

    private int toBytes( int booleanCount )
    {
        return ((booleanCount | 7) >> 3);
    }

}
