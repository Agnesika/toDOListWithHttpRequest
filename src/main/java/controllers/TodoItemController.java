package controllers;

import dto.Priority;
import dto.Todo;
import dto.TodoStatus;
import services.TodoService;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class TodoItemController {
    private final TodoService todoService = new TodoService();

    public void addTodo() {
        try {
            Todo todo = this.collectTodoInfo();
            // and then we will send it to some API  using http request
            this.todoService.createTodo(todo);
            this.displayMessage("Todo item created successfully");
        } catch (Exception exception) {
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

    private Object getUserInputFromDropDown(Object[] dropDownOptions, String title, String message) {
        return JOptionPane.showInputDialog(
                null,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                dropDownOptions,
                dropDownOptions[0] // first item in the array can be used as default
        );
    }

    public void viewAllTodo() {
        try {
            StringBuilder todoItemsAsString = new StringBuilder();

            for (Todo todo : this.todoService.getAllTodoItems()) {
                todoItemsAsString.append(todo.toString());
            }
            this.displayMessage(todoItemsAsString.toString());
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }

    }

    public void viewTodo() {
        try {
            List<Todo> existingTodoItem = this.todoService.getAllTodoItems();
            Todo selectedTodo = (Todo) this.getUserInputFromDropDown(
                    existingTodoItem.toArray(),
                    "View item",
                    "Choose the item to view");

            Todo todo = this.todoService.getTodoItem(selectedTodo.get_id());

            this.displayMessage(
                    new StringBuilder()
                            .append("Description: \t").append(todo.getDescription()).append("\n")
                            .append("Due date: \t").append(todo.getDueDate()).append("\n")
                            .append("Status: \t").append(todo.getStatus()).append("\n")
                            .append("Priority: \t").append(todo.getPriority()).append("\n")
                            .append("ID: \t").append(todo.get_id()).append("\n")
                            .toString()
            );

        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }


    }

    public void removeTodo() {
        try {
            List<Todo> existingTodoItem = this.todoService.getAllTodoItems();
            Todo selectedTodo = (Todo) this.getUserInputFromDropDown(
                    existingTodoItem.toArray(),
                    "View item",
                    "Choose the item to delete");

            this.todoService.deleteTodoItem(selectedTodo.get_id());

            this.displayMessage("Todo item was deleted successfully");

        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
    }

    public void updateTodo() {
        try {
            List<Todo> existingTodoItem = this.todoService.getAllTodoItems();
            Todo selectedTodo = (Todo) this.getUserInputFromDropDown(
                    existingTodoItem.toArray(),
                    "View item",
                    "Choose the item to update");

            Todo todo = this.collectTodoInfo();
            this.todoService.updateTodoItem(todo, selectedTodo.get_id());

            this.displayMessage("Todo item was updated successfully");

        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
    }

}
