package lv.venta.repo;

import lv.venta.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorRepo extends JpaRepository<Author, Long> {
	
}
