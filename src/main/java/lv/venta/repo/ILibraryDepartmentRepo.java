package lv.venta.repo;

import org.springframework.data.repository.CrudRepository;

import lv.venta.model.LibraryDepartment;

public interface ILibraryDepartmentRepo extends CrudRepository<LibraryDepartment, Long> {

}
