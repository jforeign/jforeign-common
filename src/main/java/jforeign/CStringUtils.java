package jforeign;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;

public class CStringUtils {
    public static final Charset NATIVE_CHARSET = Charset.defaultCharset();

    public static ByteBuffer fromJString(String str) {
        return fromJString(str, NATIVE_CHARSET);
    }

    public static ByteBuffer fromJString(String str, Charset charset) {
        Objects.requireNonNull(str, "str");
        Objects.requireNonNull(charset, "charset");
        byte[] bytes = str.getBytes(charset);
        ByteBuffer cstr = ByteBuffer.allocateDirect(bytes.length + 1);
        cstr.put(bytes);
        cstr.flip();
        return cstr;
    }

    public static String toJString(ByteBuffer cstr) {
        return toJString(cstr, NATIVE_CHARSET, true);
    }

    public static String toJString(ByteBuffer cstr, Charset charset) {
        return toJString(cstr, charset, true);
    }

    public static String toJString(ByteBuffer cstr, Charset charset, boolean ignoreTerminator) {
        Objects.requireNonNull(cstr, "cstr");
        Objects.requireNonNull(charset, "charset");
        cstr = cstr.duplicate();
        int len = cstr.remaining();
        if (len == 0) {
            return "";
        }
        if (ignoreTerminator && cstr.get(len - 1) == 0) {
            cstr.limit(cstr.limit() - 1);
        }
        return charset.decode(cstr).toString();
    }

    /**
     * @see #fromByteBuffer(ByteBuffer, boolean)
     */
    public static ByteBuffer fromByteBuffer(ByteBuffer bf) {
        return fromByteBuffer(bf, true);
    }

    public static ByteBuffer fromByteBuffer(ByteBuffer bf, boolean ignoreTerminator) {
        Objects.requireNonNull(bf, "bf");
        int size = bf.remaining();
        if (ignoreTerminator && (bf.get(size - 1) == (byte) 0)) {
            size -= 1;
        }
        ByteBuffer cstr = ByteBuffer.allocateDirect(size + 1);
        for (int i = 0; i < size; i++) {
            cstr.put(i, bf.get(i));
        }
        return cstr;
    }

}
