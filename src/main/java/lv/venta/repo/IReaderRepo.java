package lv.venta.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.venta.model.Reader;

public interface IReaderRepo extends JpaRepository<Reader, Long> {

}
