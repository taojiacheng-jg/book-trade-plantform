package cn.edu.hdu.mapper;

import cn.edu.hdu.pojo.Evaluation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EvaluationMapper {
    int insertEvaluation(Evaluation evaluation);
}
