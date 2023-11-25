package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.DiscountPolicyDTO;
import persistence.dto.RatePolicyDTO;

import java.util.List;


public interface DiscountPolicyMapper {
    String getDiscount = "SELECT * FROM discountPolicy WHERE AccommodationID = #{accomID};";
    String insertDiscount = "INSERT INTO DiscountPolicy " +
                            "VALUES (#{accomID}, #{discountType}, #{value}, #{dateStart}, #{dateEnd})";
    @Select(getDiscount)
    @Results(
            id = "RateResultSet",
            value = {
                    @Result(property = "accomID", column = "accommodationID"),
                    @Result(property = "discountType", column = "DiscountType"),
                    @Result(property = "value", column = "Value"),
                    @Result(property = "startDate", column = "DateStart"),
                    @Result(property = "endDate", column = "DateEnd")
            }
    )
    List<DiscountPolicyDTO> getDiscount(int accomID);

    @Insert(insertDiscount)
    int insertDiscount(DiscountPolicyDTO discountDTO);

}
