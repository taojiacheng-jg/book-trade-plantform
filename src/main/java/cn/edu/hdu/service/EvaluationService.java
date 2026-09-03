package cn.edu.hdu.service;

import cn.edu.hdu.utils.Result;

public interface EvaluationService {
    Result submitEvaluation(Integer orderId, Integer evaluatorId, Integer score, String comment);
}
