package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.AccommodationDTO;
import persistence.dto.AmenityDTO;

import java.util.List;

public interface AmenityMapper {
    String getAmenityByAccomID = """
            SELECT amenity.amenityID, name, category 
            FROM accommodation_amenity 
            JOIN Amenity 
            ON accommodation_amenity.amenityID = Amenity.amenityID 
            WHERE accommodationID = #{accomID};""";

    String selectAll = "SELECT * FROM Amenity";

    String selectByCategory = "SELECT * FROM Amenity WHERE category=#{category}";

    String insertAccomAmenity = "INSERT INTO Accommodation_amenity (accommodationID, amenityID)" +
            "VALUES (#{accomID}, #{amenityID})";

    @Select(getAmenityByAccomID)
    @Results(
            id = "AmenityResultSet",
            value = {
                    @Result(property = "amenityID", column = "AmenityID"),
                    @Result(property = "name", column = "Name"),
                    @Result(property = "category", column = "Category")
            }
    )
    List<AmenityDTO> selectAmenityByAccomID(int accomID);

    @Select(selectByCategory)
    List<AmenityDTO> selectByCategory(String category);

    @Insert(insertAccomAmenity)
    int insertAccomAmenity(int accomID, int amenityID);


}