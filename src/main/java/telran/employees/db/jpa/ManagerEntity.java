package telran.employees.db.jpa;

import org.json.JSONObject;

import jakarta.persistence.Entity;
import telran.employees.Employee;
import telran.employees.Manager;

@Entity
public class ManagerEntity extends EmployeeEntity {
    private float factor;

    @Override
    public void fromDto(Employee dto) {
        super.fromDto(dto);
        if (dto instanceof Manager) {
            this.factor = ((Manager) dto).getFactor();
        }
    }

    @Override
    public void toJson(JSONObject json) {
        super.toJson(json);
        json.put("factor", factor);
    }
}
