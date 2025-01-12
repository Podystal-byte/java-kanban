import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<SubTask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        this.subtasks = new ArrayList<SubTask>();
    }
    public void addSubTask(SubTask subTask){
        if (subtasks.contains(subTask)){
            System.out.println("Такая подзадача уже есть");
        } else {
            subtasks.add(subTask);
        }
    }
    public void updateStatus(){ // Тут запутався, как добавить статус DONE, понятно что NEW автоматически ставится
        if(!subtasks.isEmpty()) { // а IN_PROGRESS меняется если добавляется хоть одна подзадача
            status = Status.IN_PROGRESS;
        }
    }
}
