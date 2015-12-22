package chat.view;

import chat.protocol.Message;

public interface Observer
{
	public void update(String msg);
}
