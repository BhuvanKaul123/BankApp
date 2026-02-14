package com.bankapp.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bankapp.entities.Clerk;

public interface ClerkRepo extends JpaRepository<Clerk, Integer> {
	@Query("""
			    select c from Clerk c
			    left join fetch c.manager
			""")
	List<Clerk> findAllWithManager();

	@Query("""
			    select c from Clerk c
			    left join fetch c.manager
			    where c.empId = :id
			""")
	Optional<Clerk> findByIdWithManager(Integer id);

}
