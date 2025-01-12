import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<SubTask> subtasks;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.subtasks = new ArrayList<SubTask>();
    }
    public void addSubTask(SubTask subTask){
        if (subtasks.contains(subTask)){
            System.out.println("Такая подзадача уже есть");
        } else {
            subtasks.add(subTask);
        }
    }
    public void updateStatus(){
        if (subtasks.isEmpty()){
            status = Status.NEW;
        }
//        else if () { не понимаю как статус поменять корректно
//
//        }
    }
    public void printAllSubTasks(){
        System.out.println("Эпик " + name);
        for (SubTask subTask: subtasks){
            System.out.println(subTask.name);
        }
    }
}
