package studyscheduler.repository;

import studyscheduler.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private static final String FILE_NAME = "tasks.dat";

    public void save(List<Task> tasks) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(new ArrayList<>(tasks));

        } catch (IOException ignored) {}
    }

    @SuppressWarnings("unchecked")
    public List<Task> load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            return (List<Task>) ois.readObject();

        } catch (IOException | ClassNotFoundException ignored) {
            return new ArrayList<>();
        }
    }
}