package org.magnos.reflect;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;


public class TestReflectInt
{
    
    public static final Integer INT0 = 0;
    public static final Integer INT1 = 1;
    public static final Integer INT2 = 2;

    @Test
    public void test()
    {
        Reflect<Integer> r = ReflectRegistry.get( int.class );
        
        assertNotNull( r );
        assertSame( int.class, r.type() );
        
        ByteBuffer bb = ByteBuffer.allocate( r.maxSize() * 3 );
        
        r.put( bb, INT0 );
        
        System.out.println( "Integer [actualSize=" + bb.position() + ", maxSize=" + r.maxSize() + "]" );
        
        bb.flip();
        
        assertEquals( INT0, r.get( bb ) );
        
        bb.clear();
        
        r.put( bb, INT2 );
        
        bb.flip();
        
        assertEquals( INT2, r.get( bb ) );
        
        bb.clear();
        
        r.put( bb, INT0 );
        r.put( bb, INT2 );
        r.put( bb, INT1 );
        
        bb.flip();
        
        assertEquals( INT0, r.get( bb ) );
        assertEquals( INT2, r.get( bb ) );
        assertEquals( INT1, r.get( bb ) );
    }
    
}
