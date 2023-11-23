//import org.junit.jupiter.api.Test;
//import persistence.MyBatisConnectionFactory;
//import persistence.dao.AccommodationDAO;
//import persistence.dto.AccommodationDTO;
//import view.AccommodationView;
//
//import java.util.List;
//
//public class AccommodationDAOTest {
//
//	AccommodationDAO acDAO;
//	@Test
//	public void selectAll(){
//		acDAO = new AccommodationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
//		List<AccommodationDTO> acList = acDAO.selectAll();
//
//		AccommodationView acView = new AccommodationView();
//		acView.printAll(acList);
//	}
//
//	@Test
//	public void selectConfirmed(){
//		acDAO = new AccommodationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
//		List<AccommodationDTO> acList = acDAO.selectByStatus("승인됨");
//
//		AccommodationView acView = new AccommodationView();
//		acView.printAll(acList);
//	}
//}
