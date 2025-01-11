import java.util.HashMap;

public class Task {
    public String name;
    public String details;
    public String description;
    public Status status;
    public Task(String name, String details, String description, Status status){
        this.name = name;
        this.details = details;
        this.description = description;
        this.status = status;
    }
}

