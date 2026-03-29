package com.exp.backend.repo;

import com.exp.backend.model.ExpenseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends
        JpaRepository<ExpenseModel,Long> {
    @Query(
            value = "select * from (select * from expenses where user_id = :user and is_active=true) as ex " +
                    "where ex.expense_created_time >= :startDate and ex.expense_created_time < :endDate ",
            nativeQuery = true
    )
    List<ExpenseModel> findAllByDate(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate,@Param("user") long user);

    Optional<ExpenseModel> findById(long id);
}
