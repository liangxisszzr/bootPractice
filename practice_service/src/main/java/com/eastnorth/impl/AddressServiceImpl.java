package com.eastnorth.impl;

import com.eastnorth.enums.YesOrNo;
import com.eastnorth.mapper.UserAddressMapper;
import com.eastnorth.pojo.UserAddress;
import com.eastnorth.pojo.bo.AddressBO;
import com.eastnorth.service.AddressService;
import com.eastnorth.utils.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {

        UserAddress address = new UserAddress();
        address.setUserId(userId);

        return userAddressMapper.select(address);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {

        // 1.判断当前用户是否存在地址，如果没有，则新增为 '默认地址'
        Integer isDefault = 0;
        List<UserAddress> list = this.queryAll(addressBO.getUserId());
        if (list == null || list.isEmpty() || list.size() == 0) {
            isDefault = 1;
        }

        String addressId = sid.nextShort();

        // 2.保存地址到数据库
        UserAddress newAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, newAddress);

        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(newAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {

        String addressId = addressBO.getAddressId();

        UserAddress address = new UserAddress();
        BeanUtils.copyProperties(addressBO, address);
        address.setId(addressId);
        address.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(address);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {

        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);

        userAddressMapper.delete(address);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {

        // 1.查找默认地址，设置为不默认
        UserAddress address = new UserAddress();
        address.setUserId(userId);
        address.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> list = userAddressMapper.select(address);
        for (UserAddress addressInfo : list) {
            addressInfo.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(addressInfo);
        }

        // 2.根据地址id修改为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {

        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);

        return userAddressMapper.selectOne(address);
    }
}
