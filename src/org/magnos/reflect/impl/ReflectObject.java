
package org.magnos.reflect.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.magnos.reflect.Reflect;
import org.magnos.reflect.ReflectFactory;
import org.magnos.reflect.util.Compress;


public class ReflectObject implements Reflect<Object>
{

    private final Class<?> type;
    private final Field[] fields;
    private final Reflect<Object>[] reflects;
    private final Constructor<?> constructor;
    private final int maxSize;

    @SuppressWarnings ("unchecked" )
    public ReflectObject( Class<?> type )
    {
        Constructor<?> calculatedConstructor = null;
        Field[] calculateFields = null;
        Reflect<Object>[] calculatedReflects = null;
        int calculatedMaxSize = 0;
        
        try
        {
            calculatedConstructor = type.getConstructor();
            calculateFields = getFields( type );
            calculatedReflects = new Reflect[calculateFields.length];   
            
            for (int i = 0; i < calculateFields.length; i++)
            {
                Reflect<Object> r = ReflectFactory.create( calculateFields[i].getType() );
                
                if (r == null)
                {
                    throw new Exception( "Reflect missing for field " + calculateFields[i].getName() + " with type " + calculateFields[i].getType() );
                }
                
                calculatedReflects[i] = r;
                
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
        }
        catch (Exception e)
        {
            throw new RuntimeException( e );
        }
        
        this.type = type;
        this.constructor = calculatedConstructor;
        this.fields = calculateFields;
        this.reflects = calculatedReflects;
        this.maxSize = calculatedMaxSize;
    }

    @Override
    public Object get( ByteBuffer in )
    {
        byte nil = in.get();

        if (nil == Compress.NULL)
        {
            return null;
        }

        try
        {
            Object o = constructor.newInstance();

            for (int i = 0; i < fields.length; i++)
            {
                reflects[i].getAndSet( in, o, fields[i] );
            }

            return o;
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
    public void put( ByteBuffer out, Object v )
    {
        if (v == null)
        {
            out.put( Compress.NULL );
        }
        else
        {
            out.put( Compress.NOT_NULL );
            
            try
            {
                for (int i = 0; i < fields.length; i++)
                {
                    reflects[i].put( out, fields[i].get( v ) );
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException( e );
            }
        }
    }

    @Override
    public int sizeOf( Object v )
    {
        if (v == null)
        {
            return Compress.NULL_SIZE;
        }

        int size = Compress.NULL_SIZE;

        try
        {
            for (int i = 0; i < fields.length; i++)
            {
                size += reflects[i].sizeOf( fields[i].get( v ) );
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException( e );
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
        return type;
    }
    
    public Field[] getFields()
    {
        return fields;
    }
    
    public Reflect<Object>[] getReflects()
    {
        return reflects;
    }
    
    public Constructor<?> getConstructor()
    {
        return constructor;
    }

    private static Field[] getFields(Class<?> c)
    {
        List<Field> fieldList = new ArrayList<Field>();
        
        traverseFields( c, fieldList );
        
        return fieldList.toArray( new Field[ fieldList.size() ] );
    }
    
    private static void traverseFields(Class<?> c, List<Field> out)
    {
        List<Field> fieldList = new ArrayList<Field>();
        
        for (Field f : c.getFields())
        {
            if ((f.getModifiers() & (Modifier.STATIC | Modifier.TRANSIENT)) == 0)
            {
                f.setAccessible( true );
                
                fieldList.add( f );
            }
        }
        
        Collections.sort( fieldList, new Comparator<Field>() 
        {
            public int compare( Field a, Field b ) 
            {
                return a.getName().compareTo( b.getName() );
            }
        } );
        
        out.addAll( fieldList );
        
        if (c.getSuperclass() != null && c.getSuperclass() != Object.class) 
        {
            traverseFields( c.getSuperclass(), out );
        }
    }

}
