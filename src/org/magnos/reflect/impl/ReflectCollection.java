
package org.magnos.reflect.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.ReflectFactory;
import org.magnos.reflect.ReflectRegistry;
import org.magnos.reflect.util.Compress;


public class ReflectCollection implements Reflect<Collection<?>>
{

    private static final Map<Class<?>, Integer> typeFlagMap = new HashMap<Class<?>, Integer>();
    private static final Map<Integer, Class<?>> flagTypeMap = new HashMap<Integer, Class<?>>();
    private static final Map<Integer, Constructor<?>> constructorMap = new HashMap<Integer, Constructor<?>>();

    public static void addCollection( Class<?> c )
    {
        Integer id = Integer.valueOf( typeFlagMap.size() );

        typeFlagMap.put( c, id );
        flagTypeMap.put( id, c );

        try
        {
            constructorMap.put( id, c.getConstructor() );
        }
        catch (Exception e)
        {
            throw new RuntimeException( e );
        }
    }
    
    public static Collection<Class<?>> getCollectionTypes()
    {
        return flagTypeMap.values();
    }

    static
    {
        // Sets
        addCollection( HashSet.class );
        addCollection( ConcurrentSkipListSet.class );
        addCollection( CopyOnWriteArraySet.class );
        addCollection( LinkedHashSet.class );
        addCollection( TreeSet.class );

        // Lists
        addCollection( ArrayList.class );
        addCollection( LinkedList.class );
        addCollection( CopyOnWriteArrayList.class );
        addCollection( Stack.class );
        addCollection( Vector.class );

        // Queue
        addCollection( ArrayDeque.class );
        addCollection( ConcurrentLinkedQueue.class );
        addCollection( DelayQueue.class );
        addCollection( LinkedBlockingDeque.class );
        addCollection( PriorityBlockingQueue.class );
        addCollection( SynchronousQueue.class );
    }

    @SuppressWarnings ("unchecked" )
    @Override
    public Collection<?> get( ByteBuffer in )
    {
        Integer size = Compress.getIntUnsignedNullable( in );

        if (size == null)
        {
            return null;
        }

        Integer flag = Integer.valueOf( in.get() & 0xFF );
        Constructor<?> constructor = constructorMap.get( flag );

        try
        {
            Collection<Object> collection = (Collection<Object>)constructor.newInstance();
            Reflect<Class<?>> componentClassReflect = ReflectRegistry.get( Class.class );
            Class<?> componentType = componentClassReflect.get( in );
            Reflect<Object> componentReflect = ReflectFactory.create( componentType );

            for (int i = 0; i < size; i++)
            {
                collection.add( componentReflect.get( in ) );
            }

            return collection;
        }
        catch (Exception e)
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
    public void put( ByteBuffer out, Collection<?> v )
    {
        if (v == null)
        {
            out.put( Compress.NULL );
        }
        else
        {
            Compress.putIntUnsignedNullable( out, v.size() );

            if (v.isEmpty())
            {
                return;
            }

            Class<?> componentType = null;

            for (Object o : v)
            {
                if (o != null)
                {
                    componentType = o.getClass();

                    break;
                }
            }

            if (componentType == null)
            {
                throw new RuntimeException( "No non-null elements exist in the collection, a component type could not be determined" );
            }

            Reflect<Class<?>> componentTypeReflect = ReflectRegistry.get( Class.class );
            componentTypeReflect.put( out, componentType );

            Reflect<Object> componentReflect = ReflectFactory.create( componentType );

            if (componentReflect == null)
            {
                throw new RuntimeException( "A Reflect implementation doesn't exist for the given type: " + componentType );
            }

            for (Object o : v)
            {
                if (o != null)
                {
                    componentReflect.put( out, o );
                }
                else
                {
                    out.put( Compress.NULL );
                }
            }
        }
    }

    @Override
    public int sizeOf( Collection<?> v )
    {
        if (v == null)
        {
            return 1;
        }

        int size = Compress.sizeOfUnsignedNullable( v.size() );

        for (Object o : v)
        {
            if (o != null)
            {
                Reflect<Object> r = ReflectFactory.create( o.getClass() );

                if (r == null)
                {
                    throw new RuntimeException( "A Reflect implementation doesn't exist for the given type: " + o.getClass() );
                }

                size += r.sizeOf( o );
            }
            else
            {
                size++;
            }
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
        return Collection.class;
    }

}
