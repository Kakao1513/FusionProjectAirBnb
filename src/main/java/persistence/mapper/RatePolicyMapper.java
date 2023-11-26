package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.RatePolicyDTO;


public interface RatePolicyMapper {
    String getRate = "SELECT * FROM rate_policy WHERE AccommodationID = #{accomID};";
    String updateRatePolicy = "Update rate_policy SET weekday=#{weekday}, weekend=#{weekend} WHERE accommodationID=#{accomID}";
    @Select(getRate)
    @Results(
            id = "RateResultSet",
            value = {
                    @Result(property = "accomID", column = "accommodationID"),
                    @Result(property = "weekday", column = "weekday"),
                    @Result(property = "weekend", column = "weekend")
            }
    )
    RatePolicyDTO getRate(@Param("accomID") int accomID);
    @InsertProvider(type = AccommodationSQL.class, method = "setAccomPolicy")
    int setAccomPolicy(@Param("rate") RatePolicyDTO rate);

    @Update(updateRatePolicy)
    int updateRatePolicy(RatePolicyDTO ratePolicyDTO);
}
