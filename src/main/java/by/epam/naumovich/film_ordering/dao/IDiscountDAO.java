package by.epam.naumovich.film_ordering.dao;

import by.epam.naumovich.film_ordering.bean.Discount;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IDiscountDAO extends CrudRepository<Discount, Integer> {

    /**
     * Searches for user current discount by its ID
     *
     * @param id user ID
     * @return found discount or null if it was not found
     */
    @Query(value = "SELECT d.d_id, d.d_user, d.d_amount, d.d_stdate, d.d_sttime, d.d_endate, d.d_entime FROM discounts d " +
            "WHERE d.d_user = :id " +
            "AND ((CURDATE() = d.d_stdate AND CURTIME() > d.d_sttime) " +
            "OR (CURDATE() = d.d_endate AND CURTIME() < d.d_entime) OR (CURDATE() > d.d_stdate AND CURDATE() < d.d_endate))",
            nativeQuery = true)
    List<Discount> findDiscountByUserId(@Param("id") int id);
}
