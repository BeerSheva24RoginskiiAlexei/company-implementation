package telran.employees.db.jpa;

import org.json.JSONObject;

public interface Convertible<T> {
    void fromDto(T dto);
    void toJson(JSONObject json);
}
