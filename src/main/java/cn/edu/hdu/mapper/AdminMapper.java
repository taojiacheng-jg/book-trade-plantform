package cn.edu.hdu.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {

    BigDecimal sumTodayCompletedAmount();

    int countAllOrders();

    List<Map<String, Object>> hotBooks();

    List<Map<String, Object>> monthlyTrend();
}
