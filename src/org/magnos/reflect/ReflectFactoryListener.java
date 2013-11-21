package org.magnos.reflect;

import org.magnos.reflect.impl.ReflectObject;

/**
 * A listener that is notified
 * 
 * @author Philip Diffenderfer
 *
 */
public interface ReflectFactoryListener
{
    public void onReflectObjectCreate(ReflectObject reflect);
}
