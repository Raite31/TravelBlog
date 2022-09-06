package cn.lvyou.lwww_springboot.service.impl;

import cn.lvyou.lwww_springboot.entiy.QueryObject;
import cn.lvyou.lwww_springboot.entiy.Strategy;
import cn.lvyou.lwww_springboot.mapper.StrategyMapper;
import cn.lvyou.lwww_springboot.service.StrategyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class StrategyServiceImpl implements StrategyService {
    @Resource
    StrategyMapper strategyMapper;
    @Override
    public List<Strategy> selectAll(QueryObject queryObject) {
        // 调用mapper查询攻略信息
        return strategyMapper.selectAll(queryObject );
    }
}
