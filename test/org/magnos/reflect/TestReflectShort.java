package org.magnos.reflect;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;


public class TestReflectShort
{
    
    public static final Short SHORT0 = 0;
    public static final Short SHORT1 = 1;
    public static final Short SHORT2 = 2;

    @Test
    public void test()
    {
        Reflect<Short> r = ReflectRegistry.get( short.class );
        
        assertNotNull( r );
        assertSame( short.class, r.type() );
        
        ByteBuffer bb = ByteBuffer.allocate( r.maxSize() * 3 );
        
        r.put( bb, SHORT0 );
        
        System.out.println( "Short [actualSize=" + bb.position() + ", maxSize=" + r.maxSize() + "]" );
        
        bb.flip();
        
        assertEquals( SHORT0, r.get( bb ) );
        
        bb.clear();
        
        r.put( bb, SHORT2 );
        
        bb.flip();
        
        assertEquals( SHORT2, r.get( bb ) );
        
        bb.clear();
        
        r.put( bb, SHORT0 );
        r.put( bb, SHORT2 );
        r.put( bb, SHORT1 );
        
        bb.flip();
        
        assertEquals( SHORT0, r.get( bb ) );
        assertEquals( SHORT2, r.get( bb ) );
        assertEquals( SHORT1, r.get( bb ) );
    }
    
}
