package com.eastnorth.service;

import com.eastnorth.pojo.Stu;

/**
 * @author zuojianhou
 * @date   2020/4/8
 * @Description:
 */
public interface StuService {

    Stu getStuInfo(int id);

    void saveStu();

    void updateStu(int id);

    void deleteStu(int id);
}
