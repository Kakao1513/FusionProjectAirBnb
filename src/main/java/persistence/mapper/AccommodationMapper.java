package persistence.mapper;

import Enums.AccommodationStatus;
import org.apache.ibatis.annotations.*;
import persistence.dto.AccommodationDTO;

import java.util.List;
import java.util.Map;

public interface AccommodationMapper {
	@SelectProvider(type = AccommodationSQL.class, method = "selectAll")
	@Results(
			id = "AccommodationResultSet",
			value = {
					@Result(property = "accomId", column = "accommodationId"),
					@Result(property = "userID", column = "UserID"),
					@Result(property = "accomName", column = "HouseName"),
					@Result(property = "address", column = "Address"),
					@Result(property = "type", column = "AccommodationType"),
					@Result(property = "capacity", column = "Capacity"),
					@Result(property = "comment", column = "AccommodationComment"),
					@Result(property = "status", column = "status"),
			}
	)
	List<AccommodationDTO> getAll();


	@SelectProvider(type = AccommodationSQL.class, method = "getAccom")
	@ResultMap("AccommodationResultSet")
	AccommodationDTO getAccom(@Param("accomID") int accomID);

	@InsertProvider(type = AccommodationSQL.class, method = "insertAccom")
	int insertAccom(@Param("accom") AccommodationDTO accom);

	@SelectProvider(type = AccommodationSQL.class, method = "selectAccom")
	@ResultMap("AccommodationResultSet")
	List<AccommodationDTO> selectAccom(Map<String, Object> filters);
	
	@UpdateProvider(type = AccommodationSQL.class, method = "updateStatusById")
	int updateAccomStatus(@Param("id") int id, @Param("status") AccommodationStatus status);


}
