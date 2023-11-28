package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.DiscountPolicyDTO;
import persistence.dto.RatePolicyDTO;

import java.util.List;


public interface DiscountPolicyMapper {
    String getDiscount = "SELECT * FROM discountPolicy WHERE AccommodationID = #{accomID};";
    String insertDiscount = "INSERT INTO DiscountPolicy " +
                            "VALUES (#{accomID}, #{discountType}, #{value}, #{startDate}, #{endDate})";
    @Select(getDiscount)
    @Results(
            id = "RateResultSet",
            value = {
                    @Result(property = "accomID", column = "accommodationID"),
                    @Result(property = "discountType", column = "DiscountType"),
                    @Result(property = "value", column = "Value"),
                    @Result(property = "startDate", column = "startDate"),
                    @Result(property = "endDate", column = "endDate")
            }
    )
    List<DiscountPolicyDTO> getDiscount(@Param("accomID") int accomID);

    @Insert(insertDiscount)
    int insertDiscount(DiscountPolicyDTO discountDTO);

}
