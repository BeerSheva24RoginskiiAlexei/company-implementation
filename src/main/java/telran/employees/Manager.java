package telran.employees;

import org.json.JSONObject;

public class Manager extends Employee {
    private float factor;

    public Manager() {
    }

    public Manager(long id, int salary, String department, float factor) {
        super(id, salary, department);
        this.factor = factor;
    }

    public float getFactor() {
        return factor;
    }

    @Override
    public int computeSalary() {
        return (int) Math.round(super.computeSalary() * factor);
    }

    @Override
    protected void fillJSON(JSONObject json) {
        super.fillJSON(json);
        json.put("factor", factor);
    }

    @Override
    protected void setObject(JSONObject json) {
        super.setObject(json);
        factor = json.getFloat("factor");
    }
}