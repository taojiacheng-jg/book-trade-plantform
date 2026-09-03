package cn.edu.hdu.controller;

import cn.edu.hdu.service.EvaluationService;
import cn.edu.hdu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/submit")
    public Result submit(@RequestParam Integer orderId,
                         @RequestParam Integer evaluatorId,
                         @RequestParam Integer score,
                         @RequestParam(value = "comment", required = false) String comment) {
        return evaluationService.submitEvaluation(orderId, evaluatorId, score, comment);
    }
}
