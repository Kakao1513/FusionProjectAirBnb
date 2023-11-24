package Container;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.MyBatisConnectionFactory;
import persistence.dao.*;
import service.AccommodationService;
import service.UserService;

public class IocContainer {
	private static final IocContainer instance = new IocContainer();
	private static final UserDAO userDAO = new UserDAO(sessionFactory());
	private static final AccommodationDAO acDAO = new AccommodationDAO(sessionFactory());

	private static final ReservationDAO reservationDAO = new ReservationDAO(sessionFactory());
	private static final ReviewDAO reviewDAO = new ReviewDAO(sessionFactory());

	private static final DailyRateDAO dailyRateDAO = new DailyRateDAO(sessionFactory());

	private static final DiscountPolicyDAO discountPolicyDAO = new DiscountPolicyDAO(sessionFactory());

	private static final AmenityDAO amenityDAO = new AmenityDAO(sessionFactory());

	private static final RatePolicyDAO ratePolicyDAO = new RatePolicyDAO(sessionFactory());

	private static final UserService userService = new UserService(instance);

	private static final AccommodationService acService = new AccommodationService(instance);

	private IocContainer(){}

	private static SqlSessionFactory sessionFactory() {
		return MyBatisConnectionFactory.getSqlSessionFactory();
	}

	public static IocContainer iocContainer() { // getInstance;
		return instance;
	}

	public UserDAO userDAO() {
		return userDAO;
	}

	public AccommodationDAO accommodationDAO() {
		return acDAO;
	}

	public ReviewDAO reviewDAO() {
		return reviewDAO;
	}

	public ReservationDAO reservationDAO() {
		return reservationDAO;
	}

	public DailyRateDAO dailyRateDAO() {
		return dailyRateDAO;
	}

	public DiscountPolicyDAO discountPolicyDAO() {
		return discountPolicyDAO;
	}

	public AmenityDAO amenityDAO() {
		return amenityDAO;
	}

	public RatePolicyDAO ratePolicyDAO() {
		return ratePolicyDAO;
	}

	public UserService userService() {
		return userService;
	}

	public AccommodationService accommodationService() {
		return acService;
	}
}
