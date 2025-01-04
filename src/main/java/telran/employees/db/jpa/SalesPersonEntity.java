package telran.employees.db.jpa;

import org.json.JSONObject;

import jakarta.persistence.Entity;
import telran.employees.Employee;
import telran.employees.SalesPerson;

@Entity
public class SalesPersonEntity extends WageEmployeeEntity {
    private float percent;
    private long sales;

    @Override
    public void fromDto(Employee dto) {
        super.fromDto(dto);
        if (dto instanceof SalesPerson) {
            this.percent = ((SalesPerson) dto).getPercent();
            this.sales = ((SalesPerson) dto).getSales();
        }
    }

    @Override
    public void toJson(JSONObject json) {
        super.toJson(json);
        json.put("percent", percent);
        json.put("sales", sales);
    }
}
