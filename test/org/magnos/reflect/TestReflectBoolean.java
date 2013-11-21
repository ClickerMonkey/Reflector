package org.magnos.reflect;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;


public class TestReflectBoolean
{

    @Test
    public void test()
    {
        Reflect<Boolean> r = ReflectRegistry.get( boolean.class );
        
        assertNotNull( r );
        assertSame( boolean.class, r.type() );
        
        ByteBuffer bb = ByteBuffer.allocate( r.maxSize() * 3 );
        
        r.put( bb, true );
        
        System.out.println( "Boolean [actualSize=" + bb.position() + ", maxSize=" + r.maxSize() + "]" );
        
        bb.flip();
        
        assertEquals( true, r.get( bb ) );
        
        bb.clear();
        
        r.put( bb, false );
        
        bb.flip();
        
        assertEquals( false, r.get( bb ) );
        
        bb.clear();
        
        r.put( bb, true );
        r.put( bb, false );
        r.put( bb, true );
        
        bb.flip();
        
        assertEquals( true, r.get( bb ) );
        assertEquals( false, r.get( bb ) );
        assertEquals( true, r.get( bb ) );
    }
    
}
