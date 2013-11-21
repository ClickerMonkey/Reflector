package org.magnos.reflect.util;

import java.nio.ByteBuffer;


public class Compress
{
    
    public static final byte NULL = 0;
    public static final byte NOT_NULL = 1;
    public static final int NULL_SIZE = 1;
    
    public static final int BYTE_MORE = 0x80;
    public static final int BYTE_SIGN_NEG = 0x40;
    public static final int BYTE_SIGN_POS = 0x00;
    public static final int BYTE_NOT_NULL = 0x20;
    public static final int BYTE_NOT_NULL_UNSIGNED = 0x40;

    public static int sizeOf(int value) 
    {
        value = Math.abs( value );
        return (value < (1 <<  6) ? 1 :
               (value < (1 << 13) ? 2 : 
               (value < (1 << 20) ? 3 :
               (value < (1 << 27) ? 4 : 5))));
    }
    
    public static int sizeOfUnsigned(int value) 
    {
        return (value < (1 <<  7) ? 1 :
               (value < (1 << 14) ? 2 : 
               (value < (1 << 21) ? 3 :
               (value < (1 << 28) ? 4 : 5))));
    }
    
    public static int sizeOf(long value) 
    {
        value = Math.abs( value );
        return (value < (1L <<  6) ? 1 :    
               (value < (1L << 13) ? 2 :    
               (value < (1L << 20) ? 3 :    
               (value < (1L << 27) ? 4 :    
               (value < (1L << 34) ? 5 :    
               (value < (1L << 41) ? 6 :    
               (value < (1L << 48) ? 7 :    
               (value < (1L << 55) ? 8 :    
               (value < (1L << 62) ? 9 : 10)))))))));
    }
    
    public static int sizeOfNullable(Number n) 
    {
        if (n == null) return 1; 
        int value = Math.abs( n.intValue() );
        
        return (value < (1 <<  5) ? 1 :  
               (value < (1 << 12) ? 2 :  
               (value < (1 << 19) ? 3 :  
               (value < (1 << 26) ? 4 : 5))));
    }
    
    public static int sizeOfNullable(Character ch) 
    {
        if (ch == null) return 1; 
        
        return (ch < (1 <<  5) ? 1 :  
               (ch < (1 << 12) ? 2 :  
               (ch < (1 << 19) ? 3 :  
               (ch < (1 << 26) ? 4 : 5))));
    }
    
    public static int sizeOfUnsignedNullable(Number n) 
    {
        if (n == null) return 1; 
        int value = n.intValue();
        
        return (value < (1 <<  6) ? 1 :  
               (value < (1 << 13) ? 2 :  
               (value < (1 << 20) ? 3 :  
               (value < (1 << 27) ? 4 : 5))));
    }
    
    public static int sizeOfNullable(Long n) 
    {
        if (n == null) return 1; 
        long value = Math.abs( n.longValue() );
        
        return (value < (1L <<  5) ? 1 :                    
               (value < (1L << 12) ? 2 :                  
               (value < (1L << 19) ? 3 :                
               (value < (1L << 26) ? 4 :              
               (value < (1L << 33) ? 5 :            
               (value < (1L << 40) ? 6 : 
               (value < (1L << 47) ? 7 : 
               (value < (1L << 54) ? 8 : 
               (value < (1L << 61) ? 9 : 10)))))))));
    }
    
    public static int getInt(ByteBuffer in)
    {
        int a = in.get() & 0xFF;
        int sign = (a & BYTE_SIGN_NEG);
        int value = (a & 0x3f);
        int shift = -1;
        
        while ((a & BYTE_MORE) != 0)
        {
            a = in.get() & 0xFF;
            value |= (a & 0x7F) << (shift += 7);
        }
        
        if (sign == BYTE_SIGN_NEG)
        {
            value = -value;
        }
        
        return value;
    }
    
    public static int getIntUnsigned(ByteBuffer in)
    {
        int a = in.get() & 0xFF;
        int value = (a & 0x7f);
        int shift = 0;
        
        while ((a & BYTE_MORE) != 0)
        {
            a = in.get() & 0xFF;
            value |= (a & 0x7F) << (shift += 7);
        }
        
        return value;
    }
    
    public static Integer getIntNullable(ByteBuffer in)
    {
        int a = in.get() & 0xFF;

        if (a == NULL) 
        {
            return null;
        }
        
        int sign = (a & BYTE_SIGN_NEG);
        int value = (a & 0x1f);
        int shift = -2;
        
        while ((a & BYTE_MORE) != 0)
        {
            a = in.get() & 0xFF;
            value |= (a & 0x7F) << (shift += 7);
        }
        
        if (sign != 0)
        {
            value = -value;
        }
        
        return value;
    }
    
    public static Integer getIntUnsignedNullable(ByteBuffer in)
    {
        int a = in.get() & 0xFF;

        if (a == NULL) 
        {
            return null;
        }
        
        int value = (a & 0x3f);
        int shift = -1;
        
        while ((a & BYTE_MORE) != 0)
        {
            a = in.get() & 0xFF;
            value |= (a & 0x7F) << (shift += 7);
        }
        
        return value;
    }
    
    public static void putInt(ByteBuffer out, int x)
    {
        int value = (x < 0 ? -x : x);
        int sign = (x < 0 ? BYTE_SIGN_NEG : BYTE_SIGN_POS);
        int a = (value & 0x3F);
        int more = (value >>>= 6) != 0 ? BYTE_MORE : 0;
        
        out.put( (byte)(more | sign | a) );
        
        while (more != 0)
        {
            a = value & 0x7F;
            more = (value >>>= 7) != 0 ? BYTE_MORE : 0;
            out.put( (byte)(more | a) );
        }
    }
    
    public static void putIntUnsigned(ByteBuffer out, int x)
    {
        int more, a;
        
        do 
        {
            a = (x & 0x7F);
            more = (x >>= 7) != 0 ? BYTE_MORE : 0;
            out.put( (byte)(more | a) );    
        }
        while (more != 0);
    }
    
    public static void putIntNullable(ByteBuffer out, Integer x)
    {
        if (x == null) 
        {
            out.put( NULL );
        }
        else
        {
            int value = (x < 0 ? -x : x);
            int sign = (x < 0 ? BYTE_SIGN_NEG : BYTE_SIGN_POS);
            int a = (value & 0x1F);
            int more = (value >>= 5) != 0 ? BYTE_MORE : 0;
            
            out.put( (byte)(more | sign | BYTE_NOT_NULL | a ) );
            
            while (more != 0)
            {
                a = value & 0x7F;
                more = (value >>= 7) != 0 ? BYTE_MORE : 0;
                out.put( (byte)(more | a) );
            }
        }
    }
    
    public static void putIntUnsignedNullable(ByteBuffer out, Integer x)
    {
        if (x == null) 
        {
            out.put( NULL );
        }
        else
        {
            int value = x.intValue();
            int a = (value & 0x3F);
            int more = (value >>= 6) != 0 ? BYTE_MORE : 0;
            
            out.put( (byte)(more | BYTE_NOT_NULL_UNSIGNED | a) );
            
            while (more != 0)
            {
                a = value & 0x7F;
                more = (value >>= 7) != 0 ? BYTE_MORE : 0;
                out.put( (byte)(more | a) );
            }
        }
    }

    public static long getLong(ByteBuffer in)
    {
        int a = in.get() & 0xFF;
        int sign = (a & BYTE_SIGN_NEG);
        long value = (a & 0x3fL);
        int shift = -1;
        
        while ((a & BYTE_MORE) != 0)
        {
            a = in.get() & 0xFF;
            value |= (a & 0x7FL) << (shift += 7);
        }
        
        if (sign != 0)
        {
            value = -value;
        }
        
        return value;
    }
    
    public static Long getLongNullable(ByteBuffer in)
    {
        int a = in.get() & 0xFF;

        if (a == NULL) 
        {
            return null;
        }
        
        int sign = (a & BYTE_SIGN_NEG);
        long value = (a & 0x1FL);
        int shift = -2;
        
        while ((a & BYTE_MORE) != 0)
        {
            a = in.get() & 0xFF;
            value |= (a & 0x7FL) << (shift += 7);
        }
        
        if (sign != 0)
        {
            value = -value;
        }
        
        return value;
    }
    
    public static void putLong(ByteBuffer out, long x)
    {
        long value = (x < 0 ? -x : x);
        int sign = (x < 0 ? BYTE_SIGN_NEG : BYTE_SIGN_POS);
        long a = (value & 0x3F);
        int more = (value >>= 6) != 0 ? BYTE_MORE : 0;
        
        out.put( (byte)(more | sign | a) );
        
        while (more != 0)
        {
            a = value & 0x7F;
            more = (value >>= 7) != 0 ? BYTE_MORE : 0;
            out.put( (byte)(more | a) );
        }
    }
    
    public static void putLongNullable(ByteBuffer out, Long x)
    {
        if (x == null) 
        {
            out.put( NULL );
        }
        else
        {
            long value = (x < 0 ? -x : x);
            int sign = (x < 0 ? BYTE_SIGN_NEG : BYTE_SIGN_POS);
            long a = (value & 0x1F);
            int more = (value >>= 5) != 0 ? BYTE_MORE : 0;
            
            out.put( (byte)(more | sign | BYTE_NOT_NULL | a) );
            
            while (more != 0)
            {
                a = value & 0x7F;
                more = (value >>= 7) != 0 ? BYTE_MORE : 0;
                out.put( (byte)(more | a) );
            }
        }
    }
    
    public static void putShort(ByteBuffer out, short x) 
    {
        putInt( out, x );
    }
    
    public static short getShort(ByteBuffer in) 
    {
        return (short)getInt( in );
    }
    
    public static void putShortNullable(ByteBuffer out, Short x) 
    {
        putIntNullable( out, x == null ? null : x.intValue() );
    }
    
    public static Short getShortNullable(ByteBuffer in) 
    {
        Integer x = getIntNullable( in );
        
        return (x == null ? null : x.shortValue());
    }
    
    public static void putChar(ByteBuffer out, char c) 
    {
        putIntUnsigned( out, c );
    }
    
    public static char getChar(ByteBuffer in) 
    {
        return (char)getIntUnsigned( in );
    }
    
    public static void putCharNullable(ByteBuffer out, Character x) 
    {
        putIntUnsignedNullable( out, x == null ? null : (int)x );
    }
    
    public static Character getCharNullable(ByteBuffer in) 
    {
        Integer x = getIntUnsignedNullable( in );
        
        return (x == null ? null : (char)x.intValue() );
    }
    
    public static void putByteNullable(ByteBuffer out, Byte x) 
    {
        putIntNullable( out, x == null ? null : x.intValue() );
    }
    
    public static Byte getByteNullable(ByteBuffer in) 
    {
        Integer x = getIntNullable( in );
        
        return (x == null ? null : x.byteValue() );
    }

}
