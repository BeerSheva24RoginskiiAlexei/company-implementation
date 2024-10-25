package telran.employees;

import org.json.JSONObject;

public class SalesPerson extends WageEmployee {
    float percent;
    long sales;

    public SalesPerson() {
    }

    public SalesPerson(long id, int salary, String department, int wage, int hours, float percent, long sales) {
        super(id, salary, department, wage, hours);
        this.percent = percent;
        this.sales = sales;
    }

    public float getPercent() {
        return percent;
    }

    public long getSales() {
        return sales;
    }

    @Override
    public int computeSalary() {
        return (int) (super.computeSalary() + percent * sales / 100);
    }

    @Override
    protected void fillJSON(JSONObject json) {
        super.fillJSON(json);
        json.put("percent", percent);
        json.put("sales", sales);
    }

    @Override
    protected void setObject(JSONObject json) {
        super.setObject(json);
        percent = json.getFloat("percent");
        sales = json.getLong("sales");
    }
}