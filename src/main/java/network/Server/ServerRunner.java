package network.Server;

import Container.IocContainer;
import persistence.MyBatisConnectionFactory;
import persistence.dao.*;

public class ServerRunner {
	public static void main(String[] args) {
		Server server = new Server(IocContainer.iocContainer());
		server.run();
	}
}
