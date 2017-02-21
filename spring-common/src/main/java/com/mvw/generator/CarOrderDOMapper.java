package com.mvw.generator;

import com.mvw.generator.CarOrderDO;

public interface CarOrderDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CarOrderDO record);

    int insertSelective(CarOrderDO record);

    CarOrderDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CarOrderDO record);

    int updateByPrimaryKey(CarOrderDO record);
}