package com.bs.dao;

import com.bs.entity.Album;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AlbumDAO extends Mapper<Album> {
}
