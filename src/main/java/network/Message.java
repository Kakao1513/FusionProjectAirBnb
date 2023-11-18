package network;

import java.io.Serializable;

public class Message implements Serializable {
	private String order;
	private Object object;

	public Message(String order, Object object){
		this.order = order;
		this.object = object;
	}

	public String getOrder() {
		return order;
	}
	public Object getObject(){
		return object;
	}


}
