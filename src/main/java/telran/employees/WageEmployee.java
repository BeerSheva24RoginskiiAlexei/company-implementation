package telran.employees;

import org.json.JSONObject;

public class WageEmployee extends Employee {
    private int wage;
    private int hours;

    public WageEmployee() {
    }

    public WageEmployee(long id, int salary, String department, int wage, int hours) {
        super(id, salary, department);
        this.wage = wage;
        this.hours = hours;
    }

    public int getWage() {
        return wage;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public int computeSalary() {
        return super.computeSalary() + wage * hours;
    }

    @Override
    protected void fillJSON(JSONObject json) {
        super.fillJSON(json);
        json.put("wage", wage);
        json.put("hours", hours);
    }

    @Override
    protected void setObject(JSONObject json) {
        super.setObject(json);
        wage = json.getInt("wage");
        hours = json.getInt("hours");
    }
}