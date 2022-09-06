package cn.lvyou.lwww_springboot.controller;

import cn.lvyou.lwww_springboot.entiy.Diary;
import cn.lvyou.lwww_springboot.entiy.EchartsObject;
import cn.lvyou.lwww_springboot.entiy.QueryObject;
import cn.lvyou.lwww_springboot.entiy.ResponseBean;
import cn.lvyou.lwww_springboot.service.DiaryService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DiaryController {
    @Value("${upload.imgurl}")
    private String imgUrl;

    @Autowired
    DiaryService diaryService; // 注入service

    @RequestMapping("/getDiaryByBasic")
    @ResponseBody
    public ResponseBean getDiaryByBasic(QueryObject queryObject){
//        根据参数改变SQL语句
        System.out.println(queryObject);
        // 调用service方法
        PageInfo<Diary> diaryAll = diaryService.getDiaryAllByBasic(queryObject);
        // 返回响应数据
        return new ResponseBean(200,"查询旅游日记成功",diaryAll);
    }


    @RequestMapping("/queryDiaryById")
    @ResponseBody
    public ResponseBean queryDiaryById(Integer id){
        // 调用service
        Diary diary = diaryService.queryById(id);

        // 返回数据
        return new ResponseBean(200,"查询成功",diary);
    }

    @RequestMapping("/insertDiary")
    @ResponseBody
    public ResponseBean insertDiary(@RequestBody Diary diary){
        int row = diaryService.insert(diary);
        if(row>0){
            return new ResponseBean(200,"新增游记成功",null);
        }else{
            return new ResponseBean(500,"新增游记失败",null);
        }
    }

    /**
     * 通过主键id删除对应的文件夹以及数据库数据
     * @param id 游记的主键id
     * @return ResponseBean
     */
    @RequestMapping("/deleteDiary")
    @ResponseBody
    public ResponseBean deleteDiary(Integer id){
//        1. 删除文件夹
//          获取文件夹 diary/1/ 需要加上D:/iamge/
        Diary diary = diaryService.queryById(id);
        String folder = imgUrl+diary.getFolder();
//            删除
        File file = new File(folder);
//        将里面的文件都删除
        File[] files = file.listFiles();
        for(File file1 : files){
            file1.delete();
        }
        file.delete(); // 只能删除空文件夹
//        2. 删除数据库数据

        diaryService.deleteById(id);
        return new ResponseBean(200,"删除游记成功",null);
    }

    @RequestMapping("/updateDiary")
    @ResponseBody
    public ResponseBean updateDiary(@RequestBody Diary diary){
//        System.out.println(diary);
        int row = diaryService.updateDiary(diary);
        if(row>0){
            return new ResponseBean(200,"修改游记信息成功",null) ;
        }else {
            return new ResponseBean(500,"修改游记信息失败",null);
        }
    }

    /**
     * 计算热门城市图表
     * @return
     */
    @RequestMapping("/hotCity")
    @ResponseBody
    public ResponseBean hotCity(){
        List<Diary> diaries = diaryService.hotCity();
        return new ResponseBean(200,"响应成功",diaries);
    }

    @RequestMapping("/countMoney")
    @ResponseBody
    public ResponseBean countMoney(){
        EchartsObject echartsObject1 = new EchartsObject(50000,null);
        EchartsObject echartsObject2 = new EchartsObject(40000,50000);
        EchartsObject echartsObject3 = new EchartsObject(30000,40000);
        EchartsObject echartsObject4 = new EchartsObject(10000,30000);
        EchartsObject echartsObject5 = new EchartsObject(1,10000);
//        集合存5个实体类
        ArrayList<EchartsObject> arrayList = new ArrayList<EchartsObject>();
        arrayList.add(echartsObject1);
        arrayList.add(echartsObject2);
        arrayList.add(echartsObject3);
        arrayList.add(echartsObject4);
        arrayList.add(echartsObject5);
//        创建一个存花费数据的集合
        ArrayList result = new ArrayList();
//        遍历获取每个EchartsObject实体类
        for(EchartsObject obj : arrayList){
//            调用service查询符合范围的数据
            int count = diaryService.countMoney(obj.getNum1(), obj.getNum2());
//            将每条数据设置在结果集中
            result.add(count);
        }
        return new ResponseBean(200,"花费统计查询成功",result);
    }
}
