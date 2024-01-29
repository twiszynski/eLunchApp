package pl.twisz.eLunchApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.twisz.eLunchApp.DTO.EmployeeDTO;
import pl.twisz.eLunchApp.repo.DishRepo;
import pl.twisz.eLunchApp.repo.EmployeeRepo;
import pl.twisz.eLunchApp.repo.MenuItemRepo;
import pl.twisz.eLunchApp.repo.ProductRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<EmployeeDTO> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, EmployeeDTO employeeDTO) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<EmployeeDTO> getByUuid(UUID uuid) {
        return Optional.empty();
    }
}
