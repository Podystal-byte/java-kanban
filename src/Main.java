import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        ArrayList<String> subTaskList = new ArrayList<>();
        SubTask subTask = new SubTask(subTaskList);
        HashMap<String, SubTask> epicMap = new HashMap<>();
        epicMap.put("Уехать за границу", subTask);

        Epic epic = new Epic(epicMap);
        subTaskList.add("Побриться");
        subTaskList.add("Помыться");
        subTaskList.add("Убраться");
        subTaskList.add("Подстричься");
        epic.printAllSubTaskOnEpic("Уехать за границу");
    }
}
