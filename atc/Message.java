/**
 * 
 */
package atc;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * @author sa
 *
 */
public class Message {
	
	private byte opCode;
	
	/**
	 * Converts an object into a <code>byte[]</code>.
	 * @param obj The object that shall be converted into <code>byte[]</code>.
	 * @return the <code>byte[]</code> that represents the received object.
	 * @throws IOException //@Sa: dunno
	 * @see byteToObject(byte[])
	 */
	private static byte[] objectToByte(Object obj) throws IOException{
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
	 * Reads a <code>byte[]</code>Êand returns the associated object.
	 * @param bytes A <code>byte[]</code>Êthat constitutes an object.
	 * @return the corresponding object represented by the <code>byte[]</code>Êreceived.
	 * @throws IOException //@Sa: dunno
	 * @throws ClassNotFoundException when the <code>byte[]</code> given as argument does not represent any known object.
	 * @see objectToByte(Object)
	 */
	private static Object byteToObject(byte[] bytes) throws IOException,ClassNotFoundException{
	      ByteArrayInputStream bis = new ByteArrayInputStream(bytes); 
	      ObjectInputStream ois = new ObjectInputStream(bis); 
	      Object o;
		
	      o = ois.readObject();

	      ois.close();
	      bis.close();
	      return o;
	}
	
	public Message(byte opcode){
		opCode = opcode;
	}
	
	

}
