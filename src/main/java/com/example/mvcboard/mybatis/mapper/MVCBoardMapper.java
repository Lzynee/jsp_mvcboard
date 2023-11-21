package com.example.mvcboard.mybatis.mapper;

import com.example.mvcboard.MVCBoardDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MVCBoardMapper {
    int insertWrite(MVCBoardDTO dto);

}
