package org.magnos.reflect;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;
import org.magnos.reflect.util.Compress;


public class TestCompress
{

    @Test
    public void testIntUnsigned()
    {
        ByteBuffer bb = ByteBuffer.allocate( 10 );
        
        for (int i = 0; i < 346636; i++)
        {
            testIntUnsigned(bb, i);
        }
        
        testIntUnsigned(bb, Integer.MAX_VALUE);
    }
    
    private void testIntUnsigned(ByteBuffer bb, int intToTest)
    {
        bb.clear();
        Compress.putIntUnsigned( bb, intToTest );
        bb.flip();
        
        int uncompressedInt = Compress.getIntUnsigned( bb );
        int bytesUsed = bb.position();
        int bytesCalculated = Compress.sizeOfUnsigned( intToTest );

        assertEquals( "output and input are not equal", uncompressedInt, intToTest );
        assertEquals( "size of " + intToTest + " is off", bytesUsed, bytesCalculated );
    }

    @Test
    public void testInt()
    {
        ByteBuffer bb = ByteBuffer.allocate( 10 );

        testInt(bb, Integer.MIN_VALUE + 1);
        testInt(bb, 0);
        
        for (int i = -34663655; i < 346633456; i += 2384 )
        {
            testInt(bb, i);
        }
        
        testInt(bb, Integer.MAX_VALUE);
    }
    
    private void testInt(ByteBuffer bb, int intToTest)
    {
        bb.clear();
        Compress.putInt( bb, intToTest );
        bb.flip();
        
        int uncompressedInt = Compress.getInt( bb );
        int bytesUsed = bb.position();
        int bytesCalculated = Compress.sizeOf( intToTest );
        
        assertEquals( "output and input are not equal", uncompressedInt, intToTest );
        assertEquals( "size of " + intToTest + " is off", bytesUsed, bytesCalculated );
    }

    @Test
    public void testIntNullable()
    {
        ByteBuffer bb = ByteBuffer.allocate( 10 );

        testIntNullable(bb, null);
        testIntNullable(bb, Integer.MIN_VALUE + 1);
        testIntNullable(bb, 0);
        
        for (int i = -34663655; i < 346633456; i += 2384 )
        {
            testIntNullable(bb, i);
        }
        
        testIntNullable(bb, Integer.MAX_VALUE);
    }
    
    private void testIntNullable(ByteBuffer bb, Integer intToTest)
    {
        bb.clear();
        Compress.putIntNullable( bb, intToTest );
        bb.flip();
        
        Integer uncompressedInt = Compress.getIntNullable( bb );
        int bytesUsed = bb.position();
        int bytesCalculated = Compress.sizeOfNullable( intToTest );
        
        assertEquals( "output and input are not equal", uncompressedInt, intToTest );
        assertEquals( "size of " + intToTest + " is off", bytesUsed, bytesCalculated );
    }

    @Test
    public void testLong()
    {
        ByteBuffer bb = ByteBuffer.allocate( 10 );

        testLong(bb, Long.MIN_VALUE + 1);
        testLong(bb, 0);
        
        for (long i = -34663655859L; i < 346633456123L; i += 2384345 )
        {
            testLong(bb, i);
        }
        
        testLong(bb, Long.MAX_VALUE);
    }
    
    private void testLong(ByteBuffer bb, long longToTest)
    {
        bb.clear();
        Compress.putLong( bb, longToTest );
        bb.flip();
        
        long uncompressedLong = Compress.getLong( bb );
        int bytesUsed = bb.position();
        int bytesCalculated = Compress.sizeOf( longToTest );
        
        assertEquals( "output and input are not equal", uncompressedLong, longToTest );
        assertEquals( "size of " + longToTest + " is off", bytesUsed, bytesCalculated );
    }

    @Test
    public void testLongNullable()
    {
        ByteBuffer bb = ByteBuffer.allocate( 10 );

        testLongNullable(bb, null);
        testLongNullable(bb, Long.MIN_VALUE + 1);
        testLongNullable(bb, 0L);
        
        for (long i = -34663655859L; i < 346633456123L; i += 2384345 )
        {
            testLongNullable(bb, i);
        }
        
        testLongNullable(bb, Long.MAX_VALUE);
    }
    
    private void testLongNullable(ByteBuffer bb, Long longToTest)
    {
        bb.clear();
        Compress.putLongNullable( bb, longToTest );
        bb.flip();
        
        Long uncompressedLong = Compress.getLongNullable( bb );
        int bytesUsed = bb.position();
        int bytesCalculated = Compress.sizeOfNullable( longToTest );
        
        assertEquals( "output and input are not equal", uncompressedLong, longToTest );
        assertEquals( "size of " + longToTest + " is off", bytesUsed, bytesCalculated );
    }
    
}
