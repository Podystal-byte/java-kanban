import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Task task = new Task("Готовка", "Выбор ингредиентов и тд");
        System.out.println("name = "+ task.name+" description =  "+ task.description +"    " + task.id + "   "+ task.status );
        Task task1 = new Task("Прогулка", "выйти на улицу");
        System.out.println("name = "+ task1.name+" description =  "+ task1.description +"    " + task1.id + "   "+ task1.status );
    }
}
