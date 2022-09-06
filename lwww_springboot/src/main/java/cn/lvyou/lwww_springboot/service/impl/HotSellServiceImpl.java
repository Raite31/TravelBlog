package cn.lvyou.lwww_springboot.service.impl;

import cn.lvyou.lwww_springboot.entiy.HotSell;
import cn.lvyou.lwww_springboot.mapper.HotSellMapper;
import cn.lvyou.lwww_springboot.service.HotSellService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class HotSellServiceImpl implements HotSellService {
    @Resource
    HotSellMapper hotSellMapper;
    @Override
    public List<HotSell> getHotSellAll() {
        return hotSellMapper.getHotSell();
    }
}
