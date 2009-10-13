package gamba.container.test.entities;

public class MessageProvider {

	private IMessage message;

	public MessageProvider() {
		super();
	}

	public MessageProvider(final IMessage message) {
		super();
		this.message = message;
	}

	public IMessage getMessage() {
		return message;
	}

	public void setMessage(final IMessage message) {
		this.message = message;
	}

}
