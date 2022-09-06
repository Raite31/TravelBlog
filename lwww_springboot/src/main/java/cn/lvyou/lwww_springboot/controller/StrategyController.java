package cn.lvyou.lwww_springboot.controller;

import cn.lvyou.lwww_springboot.entiy.HotSell;
import cn.lvyou.lwww_springboot.entiy.QueryObject;
import cn.lvyou.lwww_springboot.entiy.ResponseBean;
import cn.lvyou.lwww_springboot.entiy.Strategy;
import cn.lvyou.lwww_springboot.service.HotSellService;
import cn.lvyou.lwww_springboot.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StrategyController {
    @Autowired
    StrategyService strategyService;
    @Autowired
    HotSellService hotSellService;
    @RequestMapping("/selectStrategyAll")
    @ResponseBody
    public ResponseBean selectStrategyAll(QueryObject queryObject){
        // 1.调用Service层代码 获取所有攻略信息
        List<Strategy> strategies = strategyService.selectAll(queryObject);
        // 2.查询所有热卖信息
        List<HotSell> hotSellAll = hotSellService.getHotSellAll();
//        3. 响应数据
        ArrayList arrayList = new ArrayList<>();
        arrayList.add(strategies);
        arrayList.add(hotSellAll);

        // 返回响应信息
        if(strategies != null){
            return new ResponseBean(200,"查询成功",arrayList);
        }else{
            return new ResponseBean(500,"查询失败",null);
        }
    }
}
