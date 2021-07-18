package com.jasper.scaffold.common.util;

import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuyuanju1
 * @date 2020/7/3
 * @description: bean深度复制工具类
 */
public class BeanMapperUtil {

    private static ModelMapper modelMapper = new ModelMapper();

    static {
        // 忽略模棱两可的属性
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    private BeanMapperUtil () {}

    /**
     * 按照名称和对象的层级结构进行复制
     *
     * @param source
     * @param destinationType
     * @param <D>
     * @return
     */
    public static <D> D copyProperties(Object source, Class<D> destinationType) {
        if (source == null) {
            return null;
        }
        return modelMapper.map(source, destinationType);
    }

    /**
     * 按照列表中的每个对象的名称和对象的层级结构进行复制
     *
     * @param sourceList
     * @param destinationType
     * @param <D>
     * @return
     */
    public static <D> List<D> copyListProperties(List<? extends Object> sourceList, Class<D> destinationType) {
        if (sourceList == null) {
            return Collections.emptyList();
        }
        return sourceList.stream().map(e -> modelMapper.map(e, destinationType)).collect(Collectors.toList());
    }
}
