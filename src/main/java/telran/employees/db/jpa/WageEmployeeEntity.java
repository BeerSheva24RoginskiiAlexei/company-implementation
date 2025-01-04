package telran.employees.db.jpa;

import org.json.JSONObject;

import jakarta.persistence.Entity;
import telran.employees.Employee;
import telran.employees.WageEmployee;

@Entity
public class WageEmployeeEntity extends EmployeeEntity {
    private int wage;
    private int hours;

    @Override
    public void fromDto(Employee dto) {
        super.fromDto(dto);
        if (dto instanceof WageEmployee) {
            this.wage = ((WageEmployee) dto).getWage();
            this.hours = ((WageEmployee) dto).getHours();
        }
    }

    @Override
    public void toJson(JSONObject json) {
        super.toJson(json);
        json.put("wage", wage);
        json.put("hours", hours);
    }
}
