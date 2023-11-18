package persistence;

import lombok.Getter;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import persistence.mapper.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

public class MyBatisConnectionFactory { //싱글톤 패턴으로 만들어진 ConnectionFactory
	@Getter
	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String resource = "config/config.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,"development");
				Class[] Mappers = {
						UserMapper.class,
						AccommodationMapper.class,
						AmenityMapper.class,
						ReservationMapper.class,
						RatePolicyMapper.class,
						ReviewMapper.class
				};
				for (Class mapper : Mappers) {
					sqlSessionFactory.getConfiguration().addMapper(mapper);
				}
			}
		} catch (FileNotFoundException fileNotFoundException) {
			fileNotFoundException.printStackTrace();
		} catch (IOException iOException) {
			iOException.printStackTrace();
		}
	}

}
