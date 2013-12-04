
package org.magnos.reflect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.magnos.reflect.impl.ReflectArray;
import org.magnos.reflect.impl.ReflectEnum;
import org.magnos.reflect.impl.ReflectMethod;
import org.magnos.reflect.impl.ReflectNull;
import org.magnos.reflect.impl.ReflectObject;


public class ReflectFactory
{

    public static enum MethodDepth
    {
        DEFINED, PUBLIC_INHERITED, ALL_INHERITED
    }

    private static Map<Method, ReflectMethod> methodMap = new HashMap<Method, ReflectMethod>();
    private static Map<Class<?>, ReflectObject> objectMap = new HashMap<Class<?>, ReflectObject>();

    private static ReflectFactoryListener listener;

    @SuppressWarnings ({ "unchecked", "rawtypes" } )
    public static <T> Reflect<T> create( Object o )
    {
        if (o == null)
        {
            return (Reflect<T>)ReflectNull.INSTANCE;
        }
        else if (o instanceof Method)
        {
            return (Reflect<T>)addMethod( (Method)o );
        }
        
        Class<?> oclass = (o instanceof Class ? (Class<?>)o : o.getClass());
        
        Reflect<T> reflect = ReflectRegistry.get( oclass );
        
        if (reflect != null)
        {
        	return reflect;
        }
        
        if (oclass.isArray())
        {
       		reflect = new ReflectArray( oclass );
        		
       		ReflectRegistry.add( reflect );
        }
        else if (oclass.isEnum())
        {
       		reflect = new ReflectEnum( oclass );
        		
       		ReflectRegistry.add( reflect );
        }
        else
        {
        	reflect = (Reflect<T>)addObject( oclass );
        }

        return reflect;
    }

    public static ReflectObject addObject( Class<?> c )
    {
        ReflectObject ro = objectMap.get( c );

        if (ro == null)
        {
            ro = new ReflectObject( c );

            if (listener != null)
            {
                listener.onReflectObjectCreate( ro );
            }

            ReflectRegistry.add( ro );

            objectMap.put( c, ro );
        }

        return ro;
    }

    public static ReflectMethod addMethod( Method m )
    {
        ReflectMethod rm = methodMap.get( m );

        if (rm == null)
        {
            rm = new ReflectMethod( m );
            methodMap.put( m, rm );
        }

        return rm;
    }

    public static void addMethods( Class<?> c, MethodDepth depth )
    {
        switch (depth)
        {
        case DEFINED:
            for (Method m : c.getDeclaredMethods())
            {
                addMethod( m );
            }
            break;
        case PUBLIC_INHERITED:
            for (Method m : c.getMethods())
            {
                addMethod( m );
            }
            break;
        case ALL_INHERITED:
            for (Method m : c.getDeclaredMethods())
            {
                addMethod( m );
            }
            if (c.getSuperclass() != Object.class)
            {
                addMethods( c.getSuperclass(), depth );
            }
            break;
        }
    }

    public static Map<Method, ReflectMethod> getMethodMap()
    {
        return methodMap;
    }

    public static Map<Class<?>, ReflectObject> getObjectMap()
    {
        return objectMap;
    }

    public static ReflectFactoryListener getListener()
    {
        return listener;
    }

    public static void setListener( ReflectFactoryListener listener )
    {
        ReflectFactory.listener = listener;
    }

}
