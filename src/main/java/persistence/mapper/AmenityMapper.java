package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.AmenityDTO;

import java.util.List;

public interface AmenityMapper {
    String getAmenity = """
            SELECT amenity.amenityID, name, category FROM accommodation_amenity
            JOIN Amenity ON accommodation_amenity.amenityID = Amenity.amenityID
            WHERE accommodationID=#{accomID};""";
    @Select(getAmenity)
    @Results(
            id = "AmenityResultSet",
            value = {
                    @Result(property = "amenityID", column = "AmenityID"),
                    @Result(property = "name", column = "Name"),
                    @Result(property = "category", column = "Category")
            }
    )
    List<AmenityDTO> getAmenity(int accomID);
}