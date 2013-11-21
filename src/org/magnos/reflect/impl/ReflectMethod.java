
package org.magnos.reflect.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Comparator;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.ReflectFactory;


public class ReflectMethod implements Reflect<Object[]>
{

    private final Reflect<Object>[] reflects;
    private final int maxSize;

    @SuppressWarnings ("unchecked" )
    public ReflectMethod( Method method )
    {
        Class<?>[] parameters = method.getParameterTypes();

        int calculatedMaxSize = 0;
        reflects = new Reflect[parameters.length];

        for (int i = 0; i < parameters.length; i++)
        {
            Reflect<Object> r = ReflectFactory.create( parameters[i] );

            if (r == null)
            {
                throw new RuntimeException( "A Reflect implementation was not found for parameter: " + parameters[i] );
            }

            reflects[i] = r;

            if (calculatedMaxSize != -1)
            {
                if (r.maxSize() == -1)
                {
                    calculatedMaxSize = -1;
                }
                else
                {
                    calculatedMaxSize += r.maxSize();
                }
            }
        }

        maxSize = calculatedMaxSize;
    }

    @Override
    public Object[] get( ByteBuffer in )
    {
        Object[] values = new Object[reflects.length];

        for (int i = 0; i < reflects.length; i++)
        {
            values[i] = reflects[i].get( in );
        }

        return values;
    }

    @Override
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException
    {
        // not applicable
    }

    @Override
    public void put( ByteBuffer out, Object[] v )
    {
        for (int i = 0; i < reflects.length; i++)
        {
            reflects[i].put( out, v[i] );
        }
    }

    @Override
    public int sizeOf( Object[] v )
    {
        int size = 0;

        for (int i = 0; i < reflects.length; i++)
        {
            size += reflects[i].sizeOf( v[i] );
        }

        return size;
    }

    @Override
    public int maxSize()
    {
        return maxSize;
    }

    @Override
    public Class<?> type()
    {
        return null;
    }

    public Reflect<Object>[] getReflects()
    {
        return reflects;
    }
    
    public static class MethodComparator implements Comparator<Method>
    {
        @Override
        public int compare( Method a, Method b )
        {
            int c0 = a.getName().compareTo( b.getName() );
            
            if (c0 != 0)
            {
                return c0;
            }
            
            Class<?>[] pa = a.getParameterTypes();
            Class<?>[] pb = b.getParameterTypes();
            
            int c1 = pa.length - pb.length;
            
            if (c1 != 0)
            {
                return c1;
            }
            
            for (int i = 0; i < pa.length; i++)
            {
                String pac = pa[i].getCanonicalName();
                String pbc = pb[i].getCanonicalName();
                
                int c2 = pac.compareTo( pbc );
                
                if (c2 != 0)
                {
                    return c2;
                }
            }
            
            return 0;
        }
        
    }

}
