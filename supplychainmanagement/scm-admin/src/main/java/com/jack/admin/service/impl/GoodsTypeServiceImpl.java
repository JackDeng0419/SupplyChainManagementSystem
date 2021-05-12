package com.jack.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jack.admin.dto.TreeDto;
import com.jack.admin.pojo.GoodsType;
import com.jack.admin.mapper.GoodsTypeMapper;
import com.jack.admin.service.IGoodsTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品类别表 服务实现类
 * </p>
 *
 * @author Jack
 * @since 2021-05-12
 */
@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements IGoodsTypeService {

    /**
     * 获取自身和子页面
     * @param typeId
     * @return 包括自身id和子页面id的列表
     */
    @Override
    public List<Integer> queryAllSubTypeIdsByTypeId(Integer typeId) {
        GoodsType goodsType = this.getById(typeId);
        if(goodsType.getPId() == -1) {
            // 返回所有GoodType的id
            return this.list().stream().map(GoodsType::getId).collect(Collectors.toList());
        }
        List<Integer> result =new ArrayList<Integer>();
        result.add(typeId);
        return getSubTypeIds(typeId,result);
    }

    @Override
    public List<TreeDto> queryAllGoodsTypes(Integer typeId) {
        List<TreeDto> treeDtos = this.baseMapper.queryAllGoodsTypes();
        if(null !=typeId){
            for (TreeDto treeDto : treeDtos) {
                if(treeDto.getId().equals(typeId)){
                    // 设置节点选中
                    treeDto.setChecked(true);
                    break;
                }
            }
        }
        return treeDtos;
    }

    private List<Integer> getSubTypeIds(Integer typeId, List<Integer> result) {
        List<GoodsType> goodsTypes = this.baseMapper.selectList(new QueryWrapper<GoodsType>().eq("p_id", typeId));
        if(CollectionUtils.isNotEmpty(goodsTypes)){
            //对每一个type再递归添加其子type
            goodsTypes.forEach(goodsType -> {
                result.add(goodsType.getId());
                getSubTypeIds(goodsType.getId(), result);
            });

        }
        return result;
    }
}
