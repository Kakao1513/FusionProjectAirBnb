package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.DailyRateDTO;


public interface DailyRateMapper {
    String getDaily = "SELECT * FROM daily_rate WHERE AccommodationID = #{accomID};";

    String setDaily = "INSERT INTO daily_rate " +
                      "VALUES (#accomID}, #{startDate}, #{endDate}, #{Charge}";
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

    @Insert(setDaily)
    int setAccomDaily(DailyRateDTO dailyDTO);

}
