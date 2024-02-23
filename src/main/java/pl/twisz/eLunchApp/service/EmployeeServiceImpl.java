package pl.twisz.eLunchApp.service;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.EmployeeDTO;
import pl.twisz.eLunchApp.model.DiscountCode;
import pl.twisz.eLunchApp.model.Employee;
import pl.twisz.eLunchApp.model.EmployeeBuilder;
import pl.twisz.eLunchApp.repo.DishRepo;
import pl.twisz.eLunchApp.repo.EmployeeRepo;
import pl.twisz.eLunchApp.repo.MenuItemRepo;
import pl.twisz.eLunchApp.repo.ProductRepo;
import pl.twisz.eLunchApp.utils.ConverterUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.twisz.eLunchApp.utils.ConverterUtils.convert;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<EmployeeDTO> getAll() {
        return employeeRepo.findAll().stream()
                .map(ConverterUtils::convert)
                .toList();
    }

    @Override
    public void put(UUID uuid, EmployeeDTO employeeDTO) {
        if (!Objects.equal(employeeDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Employee employee = employeeRepo.findByUuid(employeeDTO.getUuid())
                .orElseGet(() -> newEmployee(uuid));

        employee.setPersonalData(convert(employeeDTO.getPersonalData()));
        employee.setLoginData(convert(employeeDTO.getLoginData()));
        employee.setArchive(employeeDTO.getArchive());

        if (employee.getId() == null) {
            employeeRepo.save(employee);
        }

    }

    private Employee newEmployee(UUID uuid) {
        return new EmployeeBuilder()
                .withUuid(uuid)
                .build();
    }

    @Override
    public void delete(UUID uuid) {
        Employee employee = employeeRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        employeeRepo.delete(employee);
    }

    @Override
    public Optional<EmployeeDTO> getByUuid(UUID uuid) {
        return employeeRepo.findByUuid(uuid).map(ConverterUtils::convert);
    }
}
