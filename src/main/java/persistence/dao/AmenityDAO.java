package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AccommodationDTO;
import persistence.dto.AmenityDTO;
import persistence.mapper.AccommodationMapper;
import persistence.mapper.AmenityMapper;
import java.util.List;


public class AmenityDAO extends DAO<AmenityDTO> {
    public AmenityDAO(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    public List<AmenityDTO> selectAmenityByCategory(String category) {
        List<AmenityDTO> DTOS = null;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            AmenityMapper amenityMapper = session.getMapper(AmenityMapper.class);
            DTOS = amenityMapper.selectByCategory(category);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DTOS;
    }

    public List<AmenityDTO> selectAmenityByAccomID(int accomID) {
        List<AmenityDTO> DTOS = null;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            AmenityMapper amenityMapper = session.getMapper(AmenityMapper.class);
            DTOS = amenityMapper.selectAmenityByAccomID(accomID);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DTOS;
    }

    public int insertAccomAmenity(int accomID, int amenityID){
        int num = 0;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            AmenityMapper amenityMapper = session.getMapper(AmenityMapper.class);
            num = amenityMapper.insertAccomAmenity(accomID, amenityID);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return num;
    }
}

