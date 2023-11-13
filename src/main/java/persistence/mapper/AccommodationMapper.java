package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.AccommodationDTO;

import java.util.List;

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


	@SelectProvider(type = AccommodationSQL.class, method = "selectConfirmed")
	@ResultMap("AccommodationResultSet")
	List<AccommodationDTO> getConfirm();

	@InsertProvider(type = AccommodationSQL.class, method = "insertAccom")
	int insertAccom(@Param("accom") AccommodationDTO accom);




}
