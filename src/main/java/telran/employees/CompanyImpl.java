package telran.employees;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import telran.io.Persistable;

public class CompanyImpl implements Company, Persistable {
    private TreeMap<Long, Employee> employees = new TreeMap<>();
    private HashMap<String, List<Employee>> departments = new HashMap<>();
    private TreeMap<Float, List<Manager>> managersFactor = new TreeMap<>();

    private class CompanyImplIterator implements Iterator<Employee> {
        private Iterator<Employee> iterator = employees.values().iterator();
        private Employee prev = null;

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Employee next() {
            return prev = iterator.next();
        }

        @Override
        public void remove() {
            iterator.remove();
            removeDepartment(prev);
            removeManager(prev);
        }
    }

    @Override
    public Iterator<Employee> iterator() {
        return new CompanyImplIterator();
    }

    /**
     * Time complexity: O(log n).
     */
    @Override
    public void addEmployee(Employee empl) {
        Employee oldEmployee = employees.putIfAbsent(empl.getId(), empl);
        if (oldEmployee != null) {
            throw new IllegalStateException();
        }

        addDepartment(empl);
        addManager(empl);
    }

    /**
     * Time complexity: O(log n)
     */
    private void addManager(Employee empl) {
        if (empl instanceof Manager manager) {
            managersFactor.computeIfAbsent(manager.getFactor(), i -> new ArrayList<>()).add(manager);
        }
    }

    /**
     * Time complexity: O(1)
     */
    private void addDepartment(Employee empl) {
        String department = empl.getDepartment();

        if (department != null) {
            departments.computeIfAbsent(department, i -> new ArrayList<>()).add(empl);
        }
    }

    /**
     * Time complexity: O(log n)
     */
    @Override
    public Employee getEmployee(long id) {
        return employees.get(id);
    }

    /**
     * Time complexity: O(log n)
     */
    @Override
    public Employee removeEmployee(long id) {
        Employee removedEmpl = employees.remove(id);

        if (removedEmpl == null) {
            throw new NoSuchElementException();
        }

        removeDepartment(removedEmpl);
        removeManager(removedEmpl);

        return removedEmpl;
    }

    /**
     * Time complexity: O(log n)
     */
    private void removeManager(Employee empl) {
        if (empl instanceof Manager manager) {
            Float factor = manager.getFactor();
            List<Manager> managers = managersFactor.get(factor);
            managers.remove(manager);

            if (managers.isEmpty()) {
                managersFactor.remove(factor);
            }
        }
    }

    /**
     * Time complexity: O(1)
     */
    private void removeDepartment(Employee empl) {
        String department = empl.getDepartment();
        if (department != null) {
            List<Employee> employees = departments.get(department);
            employees.remove(empl);

            if (employees.isEmpty()) {
                departments.remove(department);
            }
        }
    }

    @Override
    public int getDepartmentBudget(String department) {
        int sum = 0;
        List<Employee> employees = departments.get(department);

        if (employees != null) {
            sum = employees.stream().mapToInt(i -> i.computeSalary()).sum();
        }
        return sum;
    }

    @Override
    public String[] getDepartments() {
        return departments.keySet().stream().sorted().toArray(String[]::new);
    }

    /**
     * Time complexity: O(1)
     */
    @Override
    public Manager[] getManagersWithMostFactor() {
        Manager[] res = new Manager[0];
        if (!managersFactor.isEmpty()) {
            res = managersFactor.lastEntry().getValue().toArray(new Manager[0]);
        }
        return res;
    }

    @Override
    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName);) {
            forEach(writer::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void restoreFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.lines().forEach(i -> addEmployee(Employee.getEmployee(i)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}