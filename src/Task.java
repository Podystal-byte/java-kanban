public class Task {
    static int identificator = 0;
    public int id;
    public String name;
    public String description;
    public  Status status;
    public Task(String name, String description, Status status){
        this.id = ++identificator;
        this.name = name;
        this.description = description;
        this.status = status;
    }
    public void removeTask(){
        id = identificator; // добавил этот метод для обновления id в классе TaskManager при отработке метода removeAllTasks
    }// но он не работает энивей, не совсем понимаю почему
    // Хотел спросить по поводу статуса, у Task он вообще не должен меняться?
}

