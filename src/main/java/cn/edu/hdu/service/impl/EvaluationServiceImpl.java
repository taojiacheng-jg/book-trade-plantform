package cn.edu.hdu.service.impl;

import cn.edu.hdu.mapper.EvaluationMapper;
import cn.edu.hdu.mapper.OrderMapper;
import cn.edu.hdu.mapper.UserMapper;
import cn.edu.hdu.pojo.Evaluation;
import cn.edu.hdu.pojo.Order;
import cn.edu.hdu.service.EvaluationService;
import cn.edu.hdu.utils.Result;
import cn.edu.hdu.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result submitEvaluation(Integer orderId, Integer evaluatorId, Integer score, String comment) {
        if (score == null || score < 1 || score > 5) {
            return Result.error(ResultCodeEnum.ORDER_STATUS_ERROR);
        }
        Order order = orderMapper.findOrderById(orderId);
        if (order == null) {
            return Result.error(ResultCodeEnum.ORDER_NOT_FOUND);
        }
        if (!"已完成".equals(order.getStatus())) {
            return Result.error(ResultCodeEnum.ORDER_STATUS_ERROR);
        }
        if (!order.getBuyerId().equals(evaluatorId)) {
            return Result.error(ResultCodeEnum.ORDER_STATUS_ERROR);
        }

        // 该订单只能评价一次
        Evaluation existing = evaluationMapper.findByOrderId(orderId);
        if (existing != null && existing.getScore() != null) {
            return Result.error(ResultCodeEnum.ORDER_STATUS_ERROR);
        }
        if (existing != null) {
            // 第9轮预留的待评价记录 → 更新补全
            evaluationMapper.updateEvaluation(orderId, score, comment);
        } else {
            Evaluation eval = new Evaluation();
            eval.setOrderId(orderId);
            eval.setEvaluatorId(evaluatorId);
            eval.setTargetUserId(order.getSellerId());
            eval.setScore(score);
            eval.setComment(comment);
            evaluationMapper.insertEvaluation(eval);
        }

        // 更新该卖家的平均信用分（保留两位小数）
        double avg = evaluationMapper.calcAvgScore(order.getSellerId());
        BigDecimal credit = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);
        userMapper.updateCreditScore(order.getSellerId(), credit);

        return Result.success(credit);
    }
}
