package telran.employees.db.jpa;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import org.json.JSONObject;
import telran.employees.Employee;

public class EmployeesMapper {
    private static final String PACKAGE = "telran.employees.";
    private static final String CLASS_NAME = "className";
    private static final String PACKAGE_JPA = PACKAGE + "db.jpa.";

    private static final Map<String, Class<?>> classCache = new ConcurrentHashMap<>();

    public static Employee toEmployeeDtoFromEntity(EmployeeEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("EmployeeEntity cannot be null");
        }

        String entityClassName = entity.getClass().getSimpleName();
        String dtoClassName = PACKAGE + entityClassName.replace("Entity", "");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CLASS_NAME, dtoClassName);
        entity.toJson(jsonObject);

        return Employee.getEmployeeFromJSON(jsonObject.toString());
    }

    @SuppressWarnings("unchecked")
    public static EmployeeEntity toEmployeeEntityFromDto(Employee dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Employee DTO cannot be null");
        }

        String dtoShortClassName = dto.getClass().getSimpleName();
        String dtoFullClassName = PACKAGE_JPA + dtoShortClassName + "Entity";

        try {
            Class<EmployeeEntity> entityClass = (Class<EmployeeEntity>) classCache.computeIfAbsent(
                dtoFullClassName, className -> {
                    try {
                        return Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException("Class not found: " + className, e);
                    }
                });

            EmployeeEntity entity = entityClass.getConstructor().newInstance();
            entity.fromDto(dto);
            return entity;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("Failed to instantiate EmployeeEntity: " + dtoFullClassName, e);
        }
    }
}
