package cn.lvyou.lwww_springboot.service;

import cn.lvyou.lwww_springboot.entiy.HotSell;

import java.util.List;

public interface HotSellService {
    /**
     * 获取所有热卖信息
     * @return
     */
    List<HotSell> getHotSellAll();
}
