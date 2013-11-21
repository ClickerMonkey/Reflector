package org.magnos.reflect.impl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.ReflectFactory;
import org.magnos.reflect.util.Compress;


public class ReflectArray<T> implements Reflect<T[]>
{

    public Class<?> componentType;
    public Class<?> arrayType;
    public Reflect<T> reflect;
    
    public ReflectArray(Class<?> arrayType)
    {
        this.arrayType = arrayType;
        this.componentType = arrayType.getComponentType();
        this.reflect = ReflectFactory.create( componentType );
    }
    
    @SuppressWarnings ("unchecked" )
    @Override
    public T[] get( ByteBuffer in )
    {
        Integer length = Compress.getIntUnsignedNullable( in );
        
        if (length == null)
        {
            return null;
        }

        T[] array = (T[])Array.newInstance( componentType, length );
        
        for (int i = 0; i < length; i++)
        {
            array[i] = reflect.get( in );
        }
        
        return array;
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        field.set( obj, get( in ) );
    }

    @Override
    public void put( ByteBuffer out, T[] v )
    {
        if (v == null)
        {
            out.put( Compress.NULL );
        }
        else
        {
            Compress.putIntUnsignedNullable( out, v.length );
            
            for (int i = 0; i < v.length; i++)
            {
                reflect.put( out, v[i] );
            }
        }
    }

    @Override
    public int sizeOf( T[] v )
    {
        if (v == null)
        {
            return Compress.NULL_SIZE;
        }
        
        int size = Compress.NULL_SIZE;
        
        for (int i = 0; i < v.length; i++)
        {
            size += reflect.sizeOf( v[i] );
        }
        
        return size;
    }
    
    @Override
    public int maxSize()
    {
        return -1;
    }

    @Override
    public Class<?> type()
    {
        return arrayType;
    }

}
