package springBootFirstTry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springBootFirstTry.dto.HomeDto;
/**
 * get homeDto form SQL
 * @author 123456
 *
 */
public interface PreferencesRepository extends JpaRepository<HomeDto, Long> {
	// how to create constom queries like below:
	//@Query("select dto from HomeDto where userId = : userId")
	// built in "findBy... was used below.
	HomeDto findByUserId(Long userId);// SQL statement find/select was created by spring here.

}
