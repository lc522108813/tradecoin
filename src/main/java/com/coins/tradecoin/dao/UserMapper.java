package com.coins.tradecoin.dao;

import com.coins.tradecoin.entity.UserPO;
import com.coins.tradecoin.entity.UserPOExample;
import java.util.List;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Mar 15 15:30:16 CST 2019
     */
    long countByExample(UserPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Mar 15 15:30:16 CST 2019
     */
    int insert(UserPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Mar 15 15:30:16 CST 2019
     */
    int insertSelective(UserPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Mar 15 15:30:16 CST 2019
     */
    List<UserPO> selectByExample(UserPOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Mar 15 15:30:16 CST 2019
     */
    UserPO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Mar 15 15:30:16 CST 2019
     */
    int updateByPrimaryKeySelective(UserPO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Mar 15 15:30:16 CST 2019
     */
    int updateByPrimaryKey(UserPO record);
}