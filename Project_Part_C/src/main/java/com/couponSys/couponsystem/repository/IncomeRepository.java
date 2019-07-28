package com.couponSys.couponsystem.repository;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.couponSys.couponsystem.model.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

}
