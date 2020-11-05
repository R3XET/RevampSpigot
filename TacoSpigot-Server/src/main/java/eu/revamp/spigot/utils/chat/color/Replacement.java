package eu.revamp.spigot.utils.chat.color;


import java.util.HashMap;
import java.util.Map;

public class Replacement {
    private Map<Object, Object> replacements;
    private String message;

    public Replacement(String message) {
        setReplacements(new HashMap<>());
        setMessage(message);
    }

    public Replacement add(Object current, Object replacement) {
        getReplacements().put(current, replacement);
        return this;
    }

    @Override
    public String toString() {
        getReplacements().keySet().forEach(current -> setMessage(getMessage().replace(String.valueOf(current), String.valueOf(getReplacements().get(current)))));
        return CC.translate(getMessage());
    }

    public String toString(boolean ignored) {
        getReplacements().keySet().forEach(current -> setMessage(getMessage().replace(String.valueOf(current), String.valueOf(getReplacements().get(current)))));
        return getMessage();
    }

    public void setReplacements(Map<Object, Object> replacements) {
        this.replacements = replacements;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<Object, Object> getReplacements() {
        return this.replacements;
    }

    public String getMessage() {
        return this.message;
    }
}
