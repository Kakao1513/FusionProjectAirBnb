package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.DailyRateDTO;
import persistence.dto.RatePolicyDTO;


public interface DailyRateMapper {
    String getDaily = "SELECT * FROM daily_rate WHERE AccommodationID = #{accomID};";
    @Select(getDaily)
    @Results(
            id = "RateResultSet",
            value = {
                    @Result(property = "accomID", column = "accomID"),
                    @Result(property = "StartDate", column = "StartDate"),
                    @Result(property = "EndDate", column = "EndDate"),
                    @Result(property = "Value", column = "Value"),
            }
    )
    DailyRateDTO getDaily(@Param("accomID") int accomID);

    @InsertProvider(type = AccommodationSQL.class, method = "setAccomDaily")
    int setAccomDaily(@Param("daily") DailyRateDTO daily);

}
