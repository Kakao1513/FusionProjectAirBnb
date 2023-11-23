package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.DiscountPolicyDTO;
import persistence.dto.RatePolicyDTO;


public interface DiscountPolicyMapper {
    String getDiscount = "SELECT * FROM discountPolicy WHERE AccommodationID = #{accomID};";
    @Select(getDiscount)
    @Results(
            id = "RateResultSet",
            value = {
                    @Result(property = "accomID", column = "accomID"),
                    @Result(property = "DiscountType", column = "DiscountType"),
                    @Result(property = "Value", column = "Value"),
                    @Result(property = "DateStart", column = "DateStart"),
                    @Result(property = "DateEnd", column = "DateEnd")
            }
    )
    DiscountPolicyDTO getDiscount(@Param("accomID") int accomID);
}
