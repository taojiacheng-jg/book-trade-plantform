package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.Evaluation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EvaluationMapper {
    int insertEvaluation(Evaluation evaluation);

    int updateEvaluation(@Param("orderId") Integer orderId,
                         @Param("score") Integer score,
                         @Param("comment") String comment);

    Evaluation findByOrderId(Integer orderId);

    List<Evaluation> findEvaluationsByTargetUser(Integer targetUserId);

    double calcAvgScore(Integer targetUserId);
}
