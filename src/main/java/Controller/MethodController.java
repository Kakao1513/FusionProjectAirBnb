package Controller;

import network.Protocol.Request;
import network.Protocol.Response;

public interface MethodController extends Controller {

	Response getHandle(Request req);
	Response postHandle(Request req);

	Response putHandle(Request req);

	Response deleteHandle(Request req);
}
