package cn.lvyou.lwww_springboot.entiy;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HotSell {
    private Integer id;
    private String title;
    private Double price;
    private String img;
}
