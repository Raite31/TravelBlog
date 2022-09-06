package cn.lvyou.lwww_springboot.entiy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryObject {
    // 当前页，默认第一页
    private Integer currentPage = 1;
    // 容量 默认每页显示4条数据q
    private Integer pageSize = 4 ;
    // 接受最新或最热
    private String basic;
//    接收天数和金额
    private String money;
    private String day;
    private String classify;
}
