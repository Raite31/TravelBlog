package cn.lvyou.lwww_springboot.mapper;

import cn.lvyou.lwww_springboot.entiy.HotSell;

import java.util.List;

public interface HotSellMapper {
    /**
     * 获取所有热卖信息
     * @return
     */
    List<HotSell> getHotSell();
}
