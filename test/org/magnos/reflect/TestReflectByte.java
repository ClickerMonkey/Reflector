package org.magnos.reflect;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;


public class TestReflectByte
{
    
    public static final Byte BYTE0 = 0;
    public static final Byte BYTE1 = 1;
    public static final Byte BYTE2 = 2;

    @Test
    public void test()
    {
        Reflect<Byte> r = ReflectRegistry.get( byte.class );
        
        assertNotNull( r );
        assertSame( byte.class, r.type() );
        
        ByteBuffer bb = ByteBuffer.allocate( r.maxSize() * 3 );
        
        r.put( bb, BYTE0 );
        
        System.out.println( "Byte [actualSize=" + bb.position() + ", maxSize=" + r.maxSize() + "]" );
        
        bb.flip();
        
        assertEquals( BYTE0, r.get( bb ) );
        
        bb.clear();
        
        r.put( bb, BYTE2 );
        
        bb.flip();
        
        assertEquals( BYTE2, r.get( bb ) );
        
        bb.clear();
        
        r.put( bb, BYTE0 );
        r.put( bb, BYTE2 );
        r.put( bb, BYTE1 );
        
        bb.flip();
        
        assertEquals( BYTE0, r.get( bb ) );
        assertEquals( BYTE2, r.get( bb ) );
        assertEquals( BYTE1, r.get( bb ) );
    }
    
}
