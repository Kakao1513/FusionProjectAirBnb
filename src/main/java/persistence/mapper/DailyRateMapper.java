package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.DailyRateDTO;


public interface DailyRateMapper {
    String getRate = "SELECT * FROM daily_rate WHERE AccommodationID = #{accomID};";
    @Select(getRate)
    @Results(
            id = "RateResultSet",
            value = {
                    @Result(property = "accomID", column = "accomID"),
                    @Result(property = "StartDate", column = "StartDate"),
                    @Result(property = "EndDate", column = "EndDate"),
                    @Result(property = "Value", column = "Value"),
            }
    )
    DailyRateDTO getRate(@Param("accomID") int accomID);
}
