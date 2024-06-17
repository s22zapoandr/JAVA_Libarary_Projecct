package lv.venta.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.venta.model.LibraryDepartment;

public interface ILibraryDepartmentRepo extends JpaRepository<LibraryDepartment, Long> {

}
