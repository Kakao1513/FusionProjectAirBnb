package Controller;

import network.Protocol.Request;
import network.Protocol.Response;

public interface Controller {
	Response handle(Request req);
}
