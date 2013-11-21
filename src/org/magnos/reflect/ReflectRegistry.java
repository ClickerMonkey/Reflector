
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


/**
 * A registry that stores all {@link Reflect} implementations based on the
 * {@link Class} of the object. If it doesn't exist in this Registry, you
 * can attempt to create one through {@link ReflectFactory#create(Object)}.
 * 
 * @author Philip Diffenderfer
 * 
 */
public class ReflectRegistry
{

    // The map of Class => Reflect implementation
    private static Map<Class<?>, Reflect<?>> reflectMap = new HashMap<Class<?>, Reflect<?>>();

    /**
     * Add all default Reflect implementations to the registry.
     */
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

    /**
     * Adds the given Reflect implementation to the registry based on it's
     * {@link Reflect#type()}.
     * 
     * @param reflect
     *        The reflect to add to the registry.
     */
    public static void add( Reflect<?> reflect )
    {
        reflectMap.put( reflect.type(), reflect );
    }

    /**
     * Aliases all specified classes as the given reflected class. This means
     * the {@link Reflect} implementation that exists in this registry that
     * exists for <code>reflected</code> will be pointed to by all
     * <code>aliases</code> specified so when {@link #get(Class)} is called with
     * one of the aliased classes, the Reflect associated with
     * <code>reflected</code> will be returned.
     * 
     * @param reflected
     *        The class to share the Reflect implementation of.
     * @param aliases
     *        The classes that are a superclass of reflected, that can be
     *        successfully reflected with <code>reflected</code>'s Reflect.
     */
    public static void alias( Class<?> reflected, Class<?>... aliases )
    {
        Reflect<?> r = reflectMap.get( reflected );

        for (Class<?> a : aliases)
        {
            reflectMap.put( a, r );
        }
    }

    /**
     * Aliases all specified classes as the given reflected class. This means
     * the {@link Reflect} implementation that exists in this registry that
     * exists for <code>reflected</code> will be pointed to by all
     * <code>aliases</code> specified so when {@link #get(Class)} is called with
     * one of the aliased classes, the Reflect associated with
     * <code>reflected</code> will be returned.
     * 
     * @param reflected
     *        The class to share the Reflect implementation of.
     * @param aliases
     *        The classes that are a superclass of reflected, that can be
     *        successfully reflected with <code>reflected</code>'s Reflect.
     */
    public static void alias( Class<?> reflected, Collection<Class<?>> aliases )
    {
        Reflect<?> r = reflectMap.get( reflected );

        for (Class<?> a : aliases)
        {
            reflectMap.put( a, r );
        }
    }

    /**
     * Returns the {@link Reflect} implementation associated with the given
     * class, if any.
     * 
     * @param type
     *        The class to get a Reflect implementation for.
     * @return The Reflect implementation or <code>null</code> if one doesn't
     *         exist in this registry.
     */
    @SuppressWarnings ("unchecked" )
    public static <T> Reflect<T> get( Class<?> type )
    {
        return (Reflect<T>)reflectMap.get( type );
    }

}
