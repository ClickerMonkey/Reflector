package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.util.Compress;

public class ReflectEnum<T extends Enum<T>> implements Reflect<T>
{

	public final Class<T> enumClass;
	public final T[] enumConstants;
	
	public ReflectEnum(Class<T> enumClass)
	{
		this.enumClass = enumClass;
		this.enumConstants = enumClass.getEnumConstants();
	}
	
	@Override
	public T get( ByteBuffer in )
	{
		int ordinal = Compress.getIntUnsignedNullable( in );
		
		return (ordinal == 0 ? null : enumConstants[ordinal - 1]);
	}

	@Override
	public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
	{
		field.set( obj, get( in ) );
	}

	@Override
	public void put( ByteBuffer out, T v )
	{
		if (v == null)
		{
			out.put( Compress.NULL );
		}
		else
		{
			Compress.putIntUnsignedNullable( out, v.ordinal() + 1 );
		}
	}

	@Override
	public int sizeOf( T v )
	{
		return (v == null ? Compress.NULL_SIZE : Compress.sizeOfUnsignedNullable( v.ordinal() ) );
	}

	@Override
	public int maxSize()
	{
		return 5;
	}

	@Override
	public Class<?> type()
	{
		return enumClass;
	}

}
