package telran.employees.db.jpa;

import org.json.JSONObject;

import jakarta.persistence.*;
import telran.employees.Employee;

@Table(name = "employees")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class EmployeeEntity implements Convertible<Employee> {
    @Id
    private long id;

    @Column(name = "basic_salary")
    private int basicSalary;

    private String department;

    @Override
    public void fromDto(Employee dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Employee DTO cannot be null");
        }
        this.id = dto.getId();
        this.basicSalary = dto.getBasicSalary();
        this.department = dto.getDepartment();
    }

    @Override
    public void toJson(JSONObject json) {
        json.put("id", id);
        json.put("basicSalary", basicSalary);
        json.put("department", department);
    }
}
