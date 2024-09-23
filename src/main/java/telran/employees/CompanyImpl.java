package telran.employees;

import java.util.*;

public class CompanyImpl implements Company {
    private final TreeMap<Long, Employee> employees = new TreeMap<>();
    private final HashMap<String, List<Employee>> employeesByDepartment = new HashMap<>();
    private final TreeMap<Float, List<Manager>> managersByFactor = new TreeMap<>();

    private class EmployeeIterator implements Iterator<Employee> {
        private final Iterator<Employee> iterator = employees.values().iterator();
        private Employee currentEmployee;

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Employee next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentEmployee = iterator.next();
            return currentEmployee;
        }

        @Override
        public void remove() {
            if (currentEmployee == null) {
                throw new IllegalStateException();
            }
            iterator.remove();
            removeFromDepartment(currentEmployee);
            currentEmployee = null;
        }
    }

    @Override
    public Iterator<Employee> iterator() {
        return new EmployeeIterator();
    }

    @Override
    public void addEmployee(Employee employee) {
        if (employees.containsKey(employee.getId())) {
            throw new IllegalStateException();
        }
        employees.put(employee.getId(), employee);
        employeesByDepartment.computeIfAbsent(employee.getDepartment(), k -> new ArrayList<>()).add(employee);

        if (employee instanceof Manager manager) {
            managersByFactor.computeIfAbsent(manager.getFactor(), k -> new LinkedList<>()).add(manager);
        }
    }

    @Override
    public Employee getEmployee(long id) {
        return employees.get(id);
    }

    @Override
    public Employee removeEmployee(long id) {
        Employee removedEmployee = employees.remove(id);
        if (removedEmployee == null) {
            throw new NoSuchElementException();
        }
        removeFromDepartment(removedEmployee);
        return removedEmployee;
    }

    private void removeFromDepartment(Employee employee) {
        List<Employee> departmentEmployees = employeesByDepartment.get(employee.getDepartment());
        if (departmentEmployees != null) {
            departmentEmployees.remove(employee);
            if (departmentEmployees.isEmpty()) {
                employeesByDepartment.remove(employee.getDepartment());
            }
        }
        if (employee instanceof Manager manager) {
            removeManager(manager);
        }
    }

    private void removeManager(Manager manager) {
        List<Manager> managers = managersByFactor.get(manager.getFactor());
        if (managers != null) {
            managers.remove(manager);
            if (managers.isEmpty()) {
                managersByFactor.remove(manager.getFactor());
            }
        }
    }

    @Override
    public int getDepartmentBudget(String department) {
        return employeesByDepartment.getOrDefault(department, Collections.emptyList())
            .stream()
            .mapToInt(Employee::computeSalary)
            .sum();
    }

    @Override
    public String[] getDepartments() {
        return employeesByDepartment.keySet().stream()
            .sorted()
            .toArray(String[]::new);
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        Map.Entry<Float, List<Manager>> entry = managersByFactor.lastEntry();
        return entry != null ? entry.getValue().toArray(new Manager[0]) : new Manager[0];
    }
}
