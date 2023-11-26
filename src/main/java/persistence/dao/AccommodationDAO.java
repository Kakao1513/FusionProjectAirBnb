package persistence.dao;

import Enums.AccommodationStatus;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AccommodationDTO;
import persistence.mapper.AccommodationMapper;

import java.util.List;
import java.util.Map;

public class AccommodationDAO extends DAO<AccommodationDTO> {
	public AccommodationDAO(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}
	public List<AccommodationDTO> selectAccom(Map<String, Object> filters) {
		List<AccommodationDTO> DTOs = null;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			DTOs = accomMapper.selectAccom(filters);
		}catch (Exception e){
			e.printStackTrace();
		}
		return DTOs;
	}

	public int insertAccom(AccommodationDTO dto){
		int num = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			num = accomMapper.insertAccom(dto);
			session.commit();
		}catch (Exception e){
			e.printStackTrace();
		}
		return num;
	}

	public AccommodationDTO getAccom(int accomID){
		AccommodationDTO DTO = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			DTO = accomMapper.getAccom(accomID);
		}catch (Exception e){
			e.printStackTrace();
		}
		return DTO;
	}
	
	public void updateAccomStatus(int id, String status)
	{
		try (SqlSession session = sqlSessionFactory.openSession())
		{
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			accomMapper.updateAccomStatus(id, status);
			session.commit();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public int insertRooms(AccommodationDTO dto){
		int num = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			num = accomMapper.insertRooms(dto.getAccomID(), dto.getCapacity());
			session.commit();
		}catch (Exception e){
			e.printStackTrace();
		}
		return num;
	}

}
