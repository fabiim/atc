package atc.messages;

import java.io.Serializable;

import atc.messages.MessageOpCodes;
public abstract class Message implements Serializable{
	protected  MessageOpCodes  opcode;

	public MessageOpCodes getOpcode() {
		return opcode;
	}

	public void setOpcode(MessageOpCodes opcode) {
		this.opcode = opcode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((opcode == null) ? 0 : opcode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (opcode != other.opcode)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [opcode=" + opcode
				+ "]";
	} 
	
}

	


