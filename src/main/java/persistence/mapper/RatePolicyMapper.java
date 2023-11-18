package persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import persistence.dto.RatePolicyDTO;


public interface RatePolicyMapper {
    String getRate = "SELECT * FROM rate_policy WHERE AccommodationID = #{accomID};";
    @Select(getRate)
    @Results(
            id = "RateResultSet",
            value = {
                    @Result(property = "accomID", column = "accomID"),
                    @Result(property = "weekday", column = "weekday"),
                    @Result(property = "weekend", column = "weekend")
            }
    )
    RatePolicyDTO getRate(@Param("accomID") int accomID);
}
