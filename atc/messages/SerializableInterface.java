/**
 * 
 */
package atc.messages;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author sa
 *
 */
public class SerializableInterface {
			
	/**
	 * Converts an object into a <code>byte[]</code>.
	 * @param obj The object that shall be converted into <code>byte[]</code>.
	 * @return the <code>byte[]</code> that represents the received object.
	 * @throws IOException //@Sa: dunno
	 * @see byteToObject(byte[])
	 */
	 public static <T extends Serializable> byte[] objectToByte(T obj) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		ObjectOutputStream oos = new ObjectOutputStream(bos); 
		oos.writeObject(obj);
		oos.flush(); 
		oos.close(); 
		bos.close();
		byte[] data = bos.toByteArray();
		return data;
	}
	
	/**
	 * Reads a <code>byte[]</code>�and returns the associated object.
	 * @param bytes A <code>byte[]</code>�that constitutes an object.
	 * @return the corresponding object represented by the <code>byte[]</code>�received.
	 * @throws IOException //@Sa: dunno
	 * @throws ClassNotFoundException when the <code>byte[]</code> given as argument does not represent any known object.
	 * @see objectToByte(Object)
	 */
	public static  Object byteToObject(final byte[] bytes) throws IOException,ClassNotFoundException{
	      ByteArrayInputStream bis = new ByteArrayInputStream(bytes); 
	      ObjectInputStream ois = new ObjectInputStream(bis); 
	      Object o;
	      o = ois.readObject();
	      ois.close();
	      bis.close();
	      return o;
	}
	
	
	

}
