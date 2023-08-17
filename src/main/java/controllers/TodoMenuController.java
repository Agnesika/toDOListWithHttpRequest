package controllers;

import javax.swing.*;

public class TodoMenuController {

    private final TodoItemController todoItemController = new TodoItemController();

    public void start() {
        String userChoice = JOptionPane.showInputDialog(this.getMenuItems());
        this.handleUserChoice(userChoice);
        this.start();
    }

    private void handleUserChoice(String userChoice) {
        switch (userChoice) {
            case "1":
                // add Todo
                this.todoItemController.addTodo();
                break;
            case "2":
                // view all Todo
                this.todoItemController.viewAllTodo();
                break;
            case "3":
                // view single Todo
                this.todoItemController.viewTodo();
                break;
            case "4":
                // remove Todo
                this.todoItemController.removeTodo();
                break;
            case "5":
                // update Todo
                this.todoItemController.updateTodo();
                break;
            case "6":
                System.exit(0);
                break;
            default:
                JOptionPane.showInputDialog(null, "Please choose an option from the list");
        }
    }

    private String getMenuItems() {
        return """
                Welcome to Todo Application
                1. Add Todo item
                2. Display Todo list
                3. View Single item
                4. Remove Todo item
                5. Update Todo item
                6. Exit
                """;
    }
}
