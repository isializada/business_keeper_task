package com.alizada.business_keeper_task.repository;

import com.alizada.business_keeper_task.model.ExchangeRateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateModel, Long> {
    @Query(value = "SELECT e FROM ExchangeRateModel e WHERE e.createdDate = :date")
    List<ExchangeRateModel> getExchangeRateDataByDate(@Param("date") Date date);

    @Query(value = "SELECT e FROM ExchangeRateModel e WHERE e.createdDate >= :firstDayOfMonthDate AND e.createdDate < :firstDayOfNextMonthDate")
    List<ExchangeRateModel> getExchangeRateDataByMonth(@Param("firstDayOfMonthDate") Date firstDayOfMonthDate, @Param("firstDayOfNextMonthDate") Date firstDayOfNextMonthDate);
}
