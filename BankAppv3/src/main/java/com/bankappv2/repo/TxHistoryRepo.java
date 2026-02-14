package com.bankappv2.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bankappv2.entity.TxHistory;
import com.bankappv2.projection.TxView;

@Repository
public interface TxHistoryRepo extends JpaRepository<TxHistory, Long>{
	
	@Query("""
	        select t.type as type,
	               t.amount as amount,
	               t.balanceAfterTx as balanceAfterTx,
	               t.txTime as txTime,
	               t.status as status
	        from TxHistory t
	        where t.account.id = :accountId
	        order by t.txTime desc
	    """)
	 List<TxView> findTxByAccountId(@Param("accountId") int accountId);

    // ️ Transaction count
	
    @Query("""
        select count(t)
        from TxHistory t
        where t.account.id = :accountId
    """)
    long countTxForAccount(@Param("accountId") int accountId);
    
    List<TxView> findAllBy();

    Optional<TxView> findById(int id);
}
