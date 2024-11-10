package telran.employees;

import org.json.JSONObject;

public class Employee {
    private long id;
    private int salary;
    private String department;

    @SuppressWarnings("unchecked")
    static public Employee getEmployeeFromJSON(String jsonStr) {
        JSONObject json = new JSONObject(jsonStr);
        String className = json.getString("className");
        try {
            Class<Employee> clazz = (Class<Employee>) Class.forName(className);
            Employee empl = clazz.getConstructor().newInstance();
            empl.setObject(json);
            return empl;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Employee() {
    }

    public Employee(long id, int salary, String department) {
        this.id = id;
        this.salary = salary;
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public int getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    public int computeSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object obj) {
        boolean res = false;
        if (obj instanceof Employee empl) {
            res = id == empl.getId();
        }
        return res;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("className", getClass().getName());
        fillJSON(json);
        return json.toString();
    }

    protected void fillJSON(JSONObject json) {
        json.put("id", id);
        json.put("salary", salary);
        json.put("department", department);
    }

    protected void setObject(JSONObject json) {
        id = json.getLong("id");
        salary = json.getInt("salary");
        department = json.getString("department");
    }
}