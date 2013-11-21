package org.magnos.reflect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.magnos.reflect.impl.ReflectArray;
import org.magnos.reflect.impl.ReflectBoolean;
import org.magnos.reflect.impl.ReflectBooleanArray;
import org.magnos.reflect.impl.ReflectBooleanObject;
import org.magnos.reflect.impl.ReflectByte;
import org.magnos.reflect.impl.ReflectByteObject;
import org.magnos.reflect.impl.ReflectChar;
import org.magnos.reflect.impl.ReflectCharObject;
import org.magnos.reflect.impl.ReflectClass;
import org.magnos.reflect.impl.ReflectCollection;
import org.magnos.reflect.impl.ReflectDate;
import org.magnos.reflect.impl.ReflectDouble;
import org.magnos.reflect.impl.ReflectDoubleObject;
import org.magnos.reflect.impl.ReflectFloat;
import org.magnos.reflect.impl.ReflectFloatObject;
import org.magnos.reflect.impl.ReflectInt;
import org.magnos.reflect.impl.ReflectIntObject;
import org.magnos.reflect.impl.ReflectLong;
import org.magnos.reflect.impl.ReflectLongObject;
import org.magnos.reflect.impl.ReflectShort;
import org.magnos.reflect.impl.ReflectShortObject;
import org.magnos.reflect.impl.ReflectString;
import org.magnos.reflect.impl.ReflectUUID;


public class ReflectRegistry
{

    private static Map<Class<?>, Reflect<?>> reflectMap = new HashMap<Class<?>, Reflect<?>>();

    static
    {
        add( new ReflectBoolean() );
        add( new ReflectBooleanObject() );
        add( new ReflectBooleanArray() );
        add( new ReflectChar() );
        add( new ReflectCharObject() );
        add( new ReflectArray<Character>( char[].class ) );
        add( new ReflectByte() );
        add( new ReflectByteObject() );
        add( new ReflectArray<Byte>( byte[].class ) );
        add( new ReflectShort() );
        add( new ReflectShortObject() );
        add( new ReflectArray<Short>( short[].class ) );
        add( new ReflectInt() );
        add( new ReflectIntObject() );
        add( new ReflectArray<Integer>( int[].class ) );
        add( new ReflectLong() );
        add( new ReflectLongObject() );
        add( new ReflectArray<Long>( long[].class ) );
        add( new ReflectFloat() );
        add( new ReflectFloatObject() );
        add( new ReflectArray<Float>( float[].class ) );
        add( new ReflectDouble() );
        add( new ReflectDoubleObject() );
        add( new ReflectArray<Double>( double[].class ) );
        add( new ReflectString() );
        add( new ReflectDate() );
        add( new ReflectUUID() );
        add( new ReflectClass() );
        add( new ReflectCollection() );
        
        alias( Collection.class, ReflectCollection.getCollectionTypes() );
    }
    
    public static void add(Reflect<?> reflect)
    {
        reflectMap.put( reflect.type(), reflect );
    }
    
    public static void alias(Class<?> reflected, Class<?> ... aliases)
    {
        Reflect<?> r = reflectMap.get( reflected );
        
        for (Class<?> a : aliases)
        {
            reflectMap.put( a, r );
        }
    }
    
    public static void alias(Class<?> reflected, Collection<Class<?>> aliases)
    {
        Reflect<?> r = reflectMap.get( reflected );
        
        for (Class<?> a : aliases)
        {
            reflectMap.put( a, r );
        }
    }
    
    @SuppressWarnings ("unchecked" )
    public static <T> Reflect<T> get(Class<?> type)
    {
        return (Reflect<T>) reflectMap.get( type );
    }
    
}
