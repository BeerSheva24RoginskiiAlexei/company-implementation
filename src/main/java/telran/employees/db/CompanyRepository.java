package telran.employees.db;

import java.util.List;

import telran.employees.*;

public interface CompanyRepository {
    List<Employee> getEmployees();
}