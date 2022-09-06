package cn.lvyou.lwww_springboot.service;

import cn.lvyou.lwww_springboot.entiy.QueryObject;
import cn.lvyou.lwww_springboot.entiy.Strategy;

import java.util.List;

public interface StrategyService {
    /**
     * 查询所有旅游攻略
     * @return
     */
    List<Strategy> selectAll(QueryObject queryObject);
}
