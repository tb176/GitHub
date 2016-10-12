package com.mo9.webUtils.memcache;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;

public class CustomerSerializingTranscoder extends SerializingTranscoder {  
    public CustomerSerializingTranscoder() {  
    }  
  
    @Override  
    protected Object deserialize(byte[] in)  
    {  
        Object rv = null;  
        ByteArrayInputStream bis = null;  
        ObjectInputStream is = null;  
        try {  
            if (in != null) {  
                bis = new ByteArrayInputStream(in);  
                is = new ObjectInputStream(bis) {  
                            @Override  
                            protected Class<?> resolveClass(ObjectStreamClass desc)  
                                    throws IOException, ClassNotFoundException {  
                                try {  
                                    return Thread.currentThread().getContextClassLoader().loadClass(desc.getName());  
                                } catch (ClassNotFoundException e) {  
                                    return super.resolveClass(desc);  
                                }  
                            }  
                        };  
                rv = is.readObject();  
            }  
        } catch (IOException e) {  
            log.error("Caught IOException decoding " + in.length + " bytes of data", e);  
        } catch (ClassNotFoundException e) {  
            log.error("Caught CNFE decoding " + in.length + " bytes of data", e);  
        } finally {  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                }  
            }  
            if (bis != null) {  
                try {  
                    bis.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
        return rv;  
    }  
}  