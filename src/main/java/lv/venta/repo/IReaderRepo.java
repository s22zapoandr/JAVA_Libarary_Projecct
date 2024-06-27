package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.Reader;

public interface IReaderRepo extends CrudRepository<Reader, Long> {

}
