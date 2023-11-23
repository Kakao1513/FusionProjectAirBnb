package Container;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.MyBatisConnectionFactory;
import persistence.dao.*;

public class IocContainer {
	public SqlSessionFactory sessionFactory(){
		return MyBatisConnectionFactory.getSqlSessionFactory();
	}
	public UserDAO userDAO(){
		return new UserDAO(sessionFactory());
	}

	public AccommodationDAO accommodationDAO(){
		return new AccommodationDAO(sessionFactory());
	}
	public ReviewDAO reviewDAO(){
		return new ReviewDAO(sessionFactory());
	}
	public ReservationDAO reservationDAO(){
		return new ReservationDAO(sessionFactory());
	}

	public DailyRateDAO dailyRateDAO(){
		return new DailyRateDAO(sessionFactory());
	}

	public DiscountPolicyDAO discountPolicyDAO(){
		return new DiscountPolicyDAO(sessionFactory());
	}

	public AmenityDAO amenityDAO(){
		return new AmenityDAO(sessionFactory());
	}

	public RatePolicyDAO ratePolicyDAO(){
		return new RatePolicyDAO(sessionFactory());
	}
}
