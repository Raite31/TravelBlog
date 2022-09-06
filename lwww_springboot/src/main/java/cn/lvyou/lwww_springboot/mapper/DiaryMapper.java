package cn.lvyou.lwww_springboot.mapper;

import cn.lvyou.lwww_springboot.entiy.Diary;
import cn.lvyou.lwww_springboot.entiy.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DiaryMapper {
    /**
     * 获取基本的所有日记
     * @return
     */
    List<Diary> getDiaryAllByBasic(@Param("queryObject") QueryObject queryObject);

    /**
     * 根据ID查询游记
     * @param id 文章id
     * @return 文章信息
     */
    Diary queryById(Integer id);

    /**
     * 根据文章id增加对应阅读量
     * @param id 文章
     * @return 受影响函数
     */
    int updateWatch(Integer id);

    /**
     * 新增游记
     * @param diary
     * @return
     */
    int insert(Diary diary);

    /**
     * 通过文章id删除文章信息
     * @param id 文章id
     * @return 受影响行数
     */
    int deleteById(Integer id);

    /**
     * 更新游记信息
     * @param diary 游记信息
     * @return
     */
    int updateDiary(Diary diary);

    /**
     * 获取热门城市前5名
     * @return
     */
    List<Diary> hotCity();

    /**
     * 花费统计
     * @param num1
     * @param num2
     * @return
     */
    int countMoney(@Param("num1") Integer num1,@Param("num2") Integer num2);
}


