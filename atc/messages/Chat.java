package atc.messages;

public class Chat extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5359592763493015746L;
	private String text;
	
	public Chat() {
		setOpcode(MessageOpCodes.CHAT);
	}
	
	private Chat(Chat c){
		setOpcode(MessageOpCodes.CHAT);
		text=c.getText();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Chat [text=" + text + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chat other = (Chat) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	public Chat clone(){
		return (new Chat(this));
	}
	
}
