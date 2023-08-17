package controllers;

import dto.Priority;
import dto.Todo;
import dto.TodoStatus;
import services.TodoService;

import javax.swing.*;
import java.util.Arrays;

public class TodoItemController {
    private final TodoService todoService = new TodoService();
    public void addTodo(){
        try {
            Todo todo = this.collectTodoInfo();
            // and then we will send it to some API  using http request
            this.todoService.createTodo(todo);
            this.displayMessage("Todo item created successfully");
        } catch (Exception exception){
            this.displayMessage(exception.getMessage());
        }
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);

    }

    private Todo collectTodoInfo() {
        Todo todo = new Todo();
        todo.setDescription(this.getUserInput("What would you like to do?"));
        String selectedStatus = (String) this.getUserInputFromDropDown(
                Arrays.stream(TodoStatus.values()).map(TodoStatus::name).toArray(),
                "Choose Status",
                "What is the current state of this task?"
        );
        todo.setStatus(TodoStatus.valueOf(selectedStatus));
//        String[] priorityListAsString  = (String[]) Arrays.stream(Priority.values()).map(Priority::name);
//        String[] priorityListAsString = EnumUtils.getEnumList(Priority.class).toArray();

        String selectedPriority = (String) this.getUserInputFromDropDown( // use single method to ask user drop down selection multiple places
                Arrays.stream(Priority.values()).map(Priority::name).toArray(), // convert enum to list then array of string
                "Choose Priority",
                "How important is this task??"
        );
        todo.setPriority(Priority.valueOf(selectedPriority)); // convert string to enum
        todo.setDueDate(this.getUserInput("When do you want to complete this task?"));
        return todo;
    }

    private String getUserInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

    private Object getUserInputFromDropDown(Object[] dropDownOptions, String title, String message){
        return JOptionPane.showInputDialog(
                null,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                dropDownOptions,
                dropDownOptions[0]
        );
    }

    public void viewAllTodo(){}
    public void viewTodo(){}
    public void removeTodo(){}
    public void updateTodo(){}


}
