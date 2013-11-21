
package org.magnos.reflect;

import java.lang.reflect.Field;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;


/**
 * An interface responsible for "reflecting" (process of converting a data type
 * to bytes, and from bytes back to the data type) a given data {@link #type()}
 * to and from a {@link ByteBuffer}. {@link #sizeOf(Object)} is provided to
 * ensure a {@link BufferOverflowException} doesn't occur, and
 * {@link #maxSize()} is available to estimate the largest possible size this
 * type may be in bytes (if that's not determinable without the object through
 * {@link #sizeOf(Object)}, -1 will be returned for {@link #maxSize()}).
 * 
 * @author Philip Diffenderfer
 * 
 * @param <T>
 *        The type to reflect.
 */
public interface Reflect<T>
{

    /**
     * Returns the value stored in the ByteBuffer in the exact state it was in
     * when it was passed to {@link #put(ByteBuffer, Object)}.
     * 
     * @param in
     *        The ByteBuffer to get the value from.
     * @return The value from the ByteBuffer.
     */
    public T get( ByteBuffer in );

    /**
     * Sets the {@link Field} on the {@link Object} with the value stored in the
     * {@link ByteBuffer}.
     * 
     * @param in
     *        The ByteBuffer to get the value from.
     * @param obj
     *        The object with the field to set.
     * @param field
     *        The field to set.
     * @throws IllegalAccessException
     * @see {@link #get(ByteBuffer)}
     */
    public void getAndSet( ByteBuffer in, Object obj, Field field ) throws IllegalAccessException;

    /**
     * Stores the given value into the {@link ByteBuffer}. This should put no
     * more than {@link #maxSize()} (if it's not equal to -1) bytes in the
     * ByteBuffer, it should put exactly {@link #sizeOf(Object)}.
     * 
     * @param out
     *        The ByteBuffer to put the value in.
     * @param v
     *        The value to put in the ByteBuffer.
     */
    public void put( ByteBuffer out, T v );

    /**
     * Returns the size of the value in bytes if it were to be
     * {@link #put(ByteBuffer, Object)} into a ByteBuffer.
     * 
     * @param v
     *        The value to measure.
     * @return The size of the value in bytes.
     */
    public int sizeOf( T v );

    /**
     * Returns the maximum possible size of a value of this data type. If this
     * data type has a variable size then -1 is returned.
     * 
     * @return The maximum possible size of a value of this data type in bytes.
     */
    public int maxSize();

    /**
     * The type of data reflected by the implementation.
     * 
     * @return The reference to the class that's reflected, or null if it's not
     *         a class.
     */
    public Class<?> type();
    
}
