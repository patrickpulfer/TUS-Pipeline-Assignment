package edu.tus.car.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.tus.car.model.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	//List<Car> findbyMake(String make);
	List<Car> findByMakeContaining(String make); //Part of the name
	
	//@Query("SELECT t FROM Tutorial t WHERE t.level BETWEEN ?1 AND ?2")
	//List<Car> findByLevelBetween(int start, int end);
	//https://www.bezkoder.com/spring-jpa-query/
	
	@Query("SELECT c FROM Car c WHERE year >=?1 and year <=?2")
	List<Car> findByYearBetween(int start, int end);
	
	@Query("SELECT c FROM Car c WHERE price >=?1 and price <=?2")
	List<Car> findByPriceBetween(int start, int end);

}
