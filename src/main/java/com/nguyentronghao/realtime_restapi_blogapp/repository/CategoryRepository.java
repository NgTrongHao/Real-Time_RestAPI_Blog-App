package com.nguyentronghao.realtime_restapi_blogapp.repository;

import com.nguyentronghao.realtime_restapi_blogapp.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}