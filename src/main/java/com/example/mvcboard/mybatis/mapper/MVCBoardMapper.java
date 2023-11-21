package com.example.mvcboard.mybatis.mapper;

import com.example.mvcboard.MVCBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MVCBoardMapper {
    int insertWrite(MVCBoardDTO dto);
    int selectCount(Map<String, Object> map);
    List<MVCBoardDTO> selectListPage(Map<String, Object> map);
    MVCBoardDTO selectView(String idx);
    int updateVisitCount(String idx);
    int downCountPlus(String idx);
    int confirmPassword(Map<String, String> map);
    int deletePost(String idx);
    int updatePost(MVCBoardDTO dto);

}
