import java.util.HashMap;

public class Task {
    static int identificator = 0;
    public int id;
    public String name;
    public String description;
    public  Status status;
    public Task(String name, String description){
        this.id = ++identificator;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }
}

