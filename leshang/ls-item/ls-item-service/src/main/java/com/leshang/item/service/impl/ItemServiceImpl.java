package com.leshang.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leshang.item.mapper.ZkItemCatMapper;
import com.leshang.item.mapper.ZkItemMapper;
import com.leshang.item.pojo.ZkItem;
import com.leshang.item.pojo.ZkItemCat;
import com.leshang.item.service.ItemService;
import com.leshang.common.enums.ExceptionEnum;
import com.leshang.common.exception.LyException;
import com.leshang.common.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 描述:
 *
 * @author 愿你活的通透拎得清轻重辩得明是非
 * @create 2019-09-09 12:08
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ZkItemMapper zkItemMapper;

    @Autowired
    private ZkItemCatMapper zkItemCatMapper;

    @Override
    public List<ZkItem> queryItemAll() {
        PageHelper.startPage(2,14);
        return zkItemMapper.selectAll();
    }

    @Override
    public PageResult<ZkItem> queryItemsPage(Integer page, Integer rows, Integer status, String key, Integer cid) {
        //分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(ZkItem.class);
        //搜索字段过滤
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNoneBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        //上下架过滤
        if (status != null) {
            criteria.andEqualTo("status", 1);
        }
        //分类过滤
        if (cid != null) {
            criteria.andEqualTo("cid", cid);
        }
        //默认排序
        example.setOrderByClause("created DESC");

        List<ZkItem> zkItems = zkItemMapper.selectByExample(example);
        //判断
        if (CollectionUtils.isEmpty(zkItems)) {
            throw new LyException(ExceptionEnum.ITEM_NOT_FOND);
        }
        //解析分页结果
        PageInfo<ZkItem> info = new PageInfo<>(zkItems);
        return new PageResult<>(info.getTotal(),(long)info.getPages(), zkItems);
    }

    /**
     * 根据id获取商品信息
     * @param id 商品id
     * @return
     */
    @Override
    public ZkItem finditemById(Long id) {
        ZkItem zkItem=new ZkItem();
        zkItem.setId(id);
        return zkItemMapper.selectOne(zkItem);
    }

    @Override
    public List<ZkItemCat> queryAllCat() {
        List<ZkItemCat> zkItemCats = zkItemCatMapper.selectAll();//获取商品所有类别
        return zkItemCats;
    }

    /**
     * 获取当前商品类别下的商品数量
     * @param cid 类别id
     * @return
     */
    @Override
    public Integer queryItemCountByCatId(Long cid) {
        Example example = new Example(ZkItem.class);
        //搜索字段过滤
        if (cid != null){
            example.createCriteria().andEqualTo("cid",cid);
        }
        return zkItemMapper.selectCountByExample(example);
    }

    /**
     * 查找该商品类别下的所有商品
     * @param id
     * @return
     */
    @Override
    public List<ZkItem> queryItemsByCatId(Long id) {
        ZkItem item=new ZkItem();
        item.setCid(id);
        List<ZkItem> itemList = zkItemMapper.select(item);
        return itemList;
    }

    @Override
    public List<ZkItem> queryItemsByPriceASC() {
        List<ZkItem> zkItems = zkItemMapper.queryItemsByPriceASC();
        return zkItems;
    }

    @Override
    public List<ZkItem> queryItemsByPriceDESC() {
        List<ZkItem> zkItems = zkItemMapper.queryItemsByPriceDESC();
        return zkItems;
    }
}
