package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button task1Button;
    @FXML
    private Button task2Button;
    @FXML 
    private Button task3Button;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        if(task1Button.getText().equals("Start Task 1")){
            //task 1 not running
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            startTask(task1, task1Button, 1);
        }
        else if(task1Button.getText().equals("End Task 1")){
            //task 1 running
            endTask(task1, task1Button, 1);
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            endTask(task1, task1Button, 1);
        }               
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        if(task2Button.getText().equals("Start Task 2")){
            //task 2 not running
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {                
                textArea.appendText(message + "\n");
                if (message.equals("Task2 done.")) {
                    endTask(task2, task2Button, 2);
                }
            });
            startTask(task2, task2Button, 2);
        }
        else if(task2Button.getText().equals("End Task 2")){
            
            endTask(task2, task2Button, 2);
        }        
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
          if(task3Button.getText().equals("Start Task 3")){
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
                if (evt.getNewValue().equals("Task3 done.")) {
                    endTask(task3, task3Button, 3);
                }
            });
            startTask(task3, task3Button, 3);
        }
        else if(task3Button.getText().equals("End Task 3")){
            endTask(task3, task3Button, 3);
        }     
    } 
    
    public void startTask(Thread task, Button taskButton, int taskNum){
        task.start();
        taskButton.setText("End Task "+taskNum);
    }
    
    public void endTask(Task task, Button taskButton, int taskNum){
        task.end();
        task = null;
        taskButton.setText("Start Task "+taskNum);
    }
}