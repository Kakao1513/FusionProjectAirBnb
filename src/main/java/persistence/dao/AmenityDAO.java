package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AmenityDTO;
import persistence.mapper.AmenityMapper;
import java.util.List;


public class AmenityDAO extends DAO<AmenityDTO>{
    public AmenityDAO(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }
    public List<AmenityDTO> getAmenity(int accomID) {
        List<AmenityDTO> DTOS = null;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            AmenityMapper accomMapper = session.getMapper(AmenityMapper.class);
            DTOS = accomMapper.getAmenity(accomID);
        }
        return DTOS;
    }
}
