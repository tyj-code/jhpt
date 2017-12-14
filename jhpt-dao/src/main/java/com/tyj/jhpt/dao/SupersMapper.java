/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.tyj.jhpt.dao;

import com.github.fartherp.framework.core.dao.DaoMapper;
import com.github.fartherp.framework.core.orm.mybatis.annotation.SqlMapper;
import com.tyj.jhpt.bo.Supers;

/**
 * This class corresponds to the database table `tb_supers`
 */
@SqlMapper
public interface SupersMapper extends DaoMapper<Supers, Long> {
}