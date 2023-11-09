import org.junit.jupiter.api.Test;
import persistence.MyBatisConnectionFactory;
import persistence.dao.UserDAO;
import persistence.dto.HostDto;
import persistence.dto.UserDTO;
import view.UserView;

import java.util.List;

public class UserDAOTest {
	UserDAO userDAO;

	@Test
	public void printAll() {
		userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		List<UserDTO> userDTOList = userDAO.selectAll();

		UserView userViewer = new UserView();
		userViewer.printAll(userDTOList);
	}

	@Test
	public void printHost(){
		userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		List<HostDto> hostDtoList = userDAO.selectHost();

		UserView userView = new UserView();
		userView.printAll(hostDtoList);
	}
}
