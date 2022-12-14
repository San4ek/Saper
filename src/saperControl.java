import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class saperControl implements Initializable {
    @FXML
    private Button restartButton;

    @FXML
    private Label timeLabel;

    @FXML
    private Label gameOverLabel;

    @FXML
    private Pane pane;

    private int numbOfFire=0;

    Scene scene=new Scene();

   private ArrayList<String> addBombNumb() {
       ArrayList<String> bombList = new ArrayList<>(Const.NUMB_OF_BOMBS);
       int i = 0;


       while (i < Const.NUMB_OF_BOMBS) {
           int bombNumb = Random.getRandNumber();
           while (bombList.contains("button"+ bombNumb)) {
               bombNumb = Random.getRandNumber();
           }
           bombList.add("button"+ bombNumb);
           ++i;
       }

       return bombList;
   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> bombList = addBombNumb();
        Map<String, String> buttonList=setNumber(bombList);

        timer.start();
        restartButton.setOnAction(event-> scene.showScene(Const.SAPER_SCENE, restartButton));

        setOnActionButtons(buttonList);

        pane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getTarget() instanceof Button) {
                if (event.getButton().name().equals(MouseButton.PRIMARY.name()) &&
                        !((Button) event.getTarget()).getText().equals(Const.BOMB_IS_HERE)) {
                    if (bombList.contains(((Button) event.getTarget()).getId())) {
                        timer.stop();
                        ((Button) event.getTarget()).setStyle(Const.BACKGROUND_COLOR_RED + ";" +
                                Const.FONT_WEIGHT_BOLD + ";" +
                                Const.BORDER_COLOR_BLACK);
                        setVisibleGameOverLabel();
                        showAllBombs(bombList);
                        openField(buttonList);
                    }
                    if (numbOfFire == Const.NUMB_OF_POINTS - Const.NUMB_OF_BOMBS) {
                        timer.stop();
                        setDisableAllButtons();
                        gameOverLabel.setVisible(true);
                        gameOverLabel.setStyle(Const.TEXT_FILL_GREEN + "; " +
                                Const.FONT_WEIGHT_BOLD);
                        timeLabel.setStyle(Const.TEXT_FILL_GREEN + "; " +
                                Const.BORDER_RADIUS_10 + "; " +
                                Const.BORDER_COLOR_GREEN + "; " +
                                Const.BACKGROUND_COLOR_GREY);
                    }
                }
                if (event.getButton().name().equals(MouseButton.SECONDARY.name())) {
                    if (((Button) event.getTarget()).getText().isEmpty()) {
                        ((Button) event.getTarget()).setText(Const.BOMB_IS_HERE);
                    } else ((Button) event.getTarget()).setText("");
                }
            }
        });
    }


    private void setVisibleGameOverLabel() {
       timer.stop();
       gameOverLabel.setVisible(true);
       gameOverLabel.setStyle(Const.TEXT_FILL_RED+"; "+
               Const.FONT_WEIGHT_BOLD);
       timeLabel.setStyle(Const.BACKGROUND_COLOR_GREY+"; "+
               Const.BORDER_RADIUS_10+"; "+
               Const.BORDER_COLOR_RED+"; "+
               Const.TEXT_FILL_RED);
   }

   private void showAllBombs(ArrayList<String> bombList) {
       for (Object object : pane.getChildren().toArray()) {
           if (object instanceof Button) {
               if (bombList.contains(((Button) object).getId())) {
                   if (((Button) object).getText()!=null && ((Button) object).getText().equals(Const.BOMB_IS_HERE)) {
                       ((Button) object).setStyle(Const.BACKGROUND_COLOR_GREEN+";"+
                               Const.FONT_WEIGHT_BOLD+";"+
                               Const.BORDER_COLOR_BLACK);
                   }
               }
               if (((Button) object).getText()!=null && ((Button) object).getText().equals(Const.BOMB_IS_HERE)) {
                   if (!bombList.contains(((Button) object).getId())) {
                       ((Button) object).setStyle(Const.BACKGROUND_COLOR_YELLOW+";"+
                               Const.FONT_WEIGHT_BOLD+";"+
                               Const.BORDER_COLOR_BLACK);
                   }
               }
           }
       }
   }

   private void openField(Map<String, String> buttonList) {
       for (Object object : pane.getChildren().toArray()) {
           if (object instanceof Button) {
               if (!((Button) object).getId().equals(Const.RESTART_BUTTON)) {
                   ((Button) object).setText(buttonList.get(((Button) object).getId()));
                   ((Button) object).setDisable(true);
               }
           }
       }
   }

   private void setOnActionButtons(Map<String, String> buttonList) {
       for (Object object : pane.getChildren().toArray()) {
           if (object instanceof Button) {
               if (!((Button) object).getId().equals(Const.RESTART_BUTTON)) {
                   if (buttonList.get(((Button) object).getId()).equals("")) {
                       int i=Integer.parseInt(((Button) object).getId().replaceAll("button", ""));
                       switch (i) {
                           case 2, 3, 4, 5, 6, 7, 8, 9 -> ((Button) object).setOnAction(event -> {
                               ++numbOfFire;
                               ((Button) object).setText(buttonList.get(((Button) object).getId()));
                               ((Button) object).setDisable(true);
                               for (Object newObject : pane.getChildren().toArray()) {
                                   if (newObject instanceof Button) {
                                       if (((Button) newObject).getId().equals("button" + (i + 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 9)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 11))) {
                                           ((Button) newObject).fire();
                                       }
                                   }
                               }
                           });

                           case 92, 93, 94, 95, 96, 97, 98, 99 -> ((Button) object).setOnAction(event -> {
                               ++numbOfFire;
                               ((Button) object).setText(buttonList.get(((Button) object).getId()));
                               ((Button) object).setDisable(true);
                               for (Object newObject : pane.getChildren().toArray()) {
                                   if (newObject instanceof Button) {
                                       if (((Button) newObject).getId().equals("button" + (i + 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 9)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 11)))
                                           ((Button) newObject).fire();
                                   }
                               }
                           });

                           case 11, 21, 31, 41, 51, 61, 71, 81 -> ((Button) object).setOnAction(event -> {
                               ++numbOfFire;
                               ((Button) object).setText(buttonList.get(((Button) object).getId()));
                               ((Button) object).setDisable(true);
                               for (Object newObject : pane.getChildren().toArray()) {
                                   if (newObject instanceof Button) {
                                       if (((Button) newObject).getId().equals("button" + (i + 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 11)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 9)))
                                           ((Button) newObject).fire();
                                   }
                               }
                           });

                           case 20, 30, 40, 50, 60, 70, 80 -> ((Button) object).setOnAction(event -> {
                               ++numbOfFire;
                               ((Button) object).setText(buttonList.get(((Button) object).getId()));
                               ((Button) object).setDisable(true);
                               for (Object newObject : pane.getChildren().toArray()) {
                                   if (newObject instanceof Button) {
                                       if (((Button) newObject).getId().equals("button" + (i + 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 9)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 11)))
                                           ((Button) newObject).fire();
                                   }
                               }
                           });

                           case 1 -> ((Button) object).setOnAction(event -> {
                               ++numbOfFire;
                               ((Button) object).setText(buttonList.get(((Button) object).getId()));
                               ((Button) object).setDisable(true);
                               for (Object newObject : pane.getChildren().toArray()) {
                                   if (newObject instanceof Button) {
                                       if (((Button) newObject).getId().equals("button" + (i + 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 11)))
                                           ((Button) newObject).fire();
                                   }
                               }
                           });

                           case 10 -> ((Button) object).setOnAction(event -> {
                               ++numbOfFire;
                               ((Button) object).setText(buttonList.get(((Button) object).getId()));
                               ((Button) object).setDisable(true);
                               for (Object newObject : pane.getChildren().toArray()) {
                                   if (newObject instanceof Button) {
                                       if (((Button) newObject).getId().equals("button" + (i + 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 9)))
                                           ((Button) newObject).fire();
                                   }
                               }
                           });

                           case 91 -> ((Button) object).setOnAction(event -> {
                               ++numbOfFire;
                               ((Button) object).setText(buttonList.get(((Button) object).getId()));
                               ((Button) object).setDisable(true);
                               for (Object newObject : pane.getChildren().toArray()) {
                                   if (newObject instanceof Button) {
                                       if (((Button) newObject).getId().equals("button" + (i - 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 9)))
                                           ((Button) newObject).fire();
                                   }
                               }
                           });
                           case 100 -> ((Button) object).setOnAction(event -> {
                               ++numbOfFire;
                               ((Button) object).setText(buttonList.get(((Button) object).getId()));
                               ((Button) object).setDisable(true);
                               System.out.println(100);
                               for (Object newObject : pane.getChildren().toArray()) {
                                   if (newObject instanceof Button) {
                                       if (((Button) newObject).getId().equals("button" + (i - 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 11)))
                                           ((Button) newObject).fire();
                                   }
                               }
                           });
                           default -> ((Button) object).setOnAction(event -> {
                               ++numbOfFire;
                               ((Button) object).setText(buttonList.get(((Button) object).getId()));
                               ((Button) object).setDisable(true);
                               for (Object newObject : pane.getChildren().toArray()) {
                                   if (newObject instanceof Button) {
                                       if (((Button) newObject).getId().equals("button" + (i - 9)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 1)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 10)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 11)) ||
                                               ((Button) newObject).getId().equals("button" + (i - 11)) ||
                                               ((Button) newObject).getId().equals("button" + (i + 9)))
                                           ((Button) newObject).fire();
                                   }
                               }
                           });
                       }
                   } else {
                       ((Button) object).setOnAction( event -> {
                           ++numbOfFire;
                           if (!((Button) object).getText().equals(Const.BOMB_IS_HERE)) {
                               ((Button) object).setText(buttonList.get(((Button) object).getId()));
                               ((Button) object).setDisable(true);
                           }
                       });
                   }
               }
           }
       }
   }

   Map<String, String> setNumber(ArrayList<String> bombList) {
       Map<String, String> list= new HashMap<>();
       for (int i=1; i<=100; ++i) {
           if (bombList.contains("button"+i)) {
               list.put("button"+i, Const.BOMB);
           } else {
               int numbOfBombs=0;
               if (i>=2 && i<=9) {
                   if (bombList.contains("button"+(i+9))) ++numbOfBombs;
                   if (bombList.contains("button"+(i+10))) ++numbOfBombs;
                   if (bombList.contains("button"+(i+11))) ++numbOfBombs;
                   if (bombList.contains("button"+(i-1))) ++numbOfBombs;
                   if (bombList.contains("button"+(i+1))) ++numbOfBombs;
               } else {
                   if (i>=92 && i<=99) {
                       if (bombList.contains("button"+(i-9))) ++numbOfBombs;
                       if (bombList.contains("button"+(i-10))) ++numbOfBombs;
                       if (bombList.contains("button"+(i-11))) ++numbOfBombs;
                       if (bombList.contains("button"+(i-1))) ++numbOfBombs;
                       if (bombList.contains("button"+(i+1))) ++numbOfBombs;
                   } else {
                       if (i==11 || i==21 || i==31 || i==41 || i==51 || i==61 || i==71 || i==81) {
                           if (bombList.contains("button"+(i-9))) ++numbOfBombs;
                           if (bombList.contains("button"+(i+1))) ++numbOfBombs;
                           if (bombList.contains("button"+(i+11))) ++numbOfBombs;
                           if (bombList.contains("button"+(i-10))) ++numbOfBombs;
                           if (bombList.contains("button"+(i+10))) ++numbOfBombs;
                       } else {
                           if (i==20 || i==30 || i==40 || i==50 || i==60 || i==70 || i==80 || i==90) {
                               if (bombList.contains("button"+(i+9))) ++numbOfBombs;
                               if (bombList.contains("button"+(i-1))) ++numbOfBombs;
                               if (bombList.contains("button"+(i-11))) ++numbOfBombs;
                               if (bombList.contains("button"+(i-10))) ++numbOfBombs;
                               if (bombList.contains("button"+(i+10))) ++numbOfBombs;
                           } else {
                               if (i==1) {
                                   if (bombList.contains("button"+(i+1))) ++numbOfBombs;
                                   if (bombList.contains("button"+(i+10))) ++numbOfBombs;
                                   if (bombList.contains("button"+(i+11))) ++numbOfBombs;
                               } else {
                                   if (i==10) {
                                       if (bombList.contains("button"+(i-1))) ++numbOfBombs;
                                       if (bombList.contains("button"+(i+10))) ++numbOfBombs;
                                       if (bombList.contains("button"+(i+9))) ++numbOfBombs;
                                   } else {
                                       if (i==91) {
                                           if (bombList.contains("button"+(i+1))) ++numbOfBombs;
                                           if (bombList.contains("button"+(i-10))) ++numbOfBombs;
                                           if (bombList.contains("button"+(i-9))) ++numbOfBombs;
                                       } else {
                                           if (i==100) {
                                               if (bombList.contains("button" + (i - 1))) ++numbOfBombs;
                                               if (bombList.contains("button" + (i - 10))) ++numbOfBombs;
                                               if (bombList.contains("button" + (i - 11))) ++numbOfBombs;
                                           } else {
                                               if (bombList.contains("button" + (i - 1))) ++numbOfBombs;
                                               if (bombList.contains("button" + (i - 10))) ++numbOfBombs;
                                               if (bombList.contains("button" + (i - 11))) ++numbOfBombs;
                                               if (bombList.contains("button" + (i - 9))) ++numbOfBombs;
                                               if (bombList.contains("button" + (i + 9))) ++numbOfBombs;
                                               if (bombList.contains("button" + (i + 10))) ++numbOfBombs;
                                               if (bombList.contains("button" + (i + 11))) ++numbOfBombs;
                                               if (bombList.contains("button" + (i + 1))) ++numbOfBombs;
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
               }
               if (numbOfBombs==0) list.put("button"+i, "");
               else list.put("button"+i, String.valueOf(numbOfBombs));
           }
       }

       System.out.println(list);

       return list;
   }

   private void setDisableAllButtons() {
       for (Object object : pane.getChildren().toArray()) {
           if (object instanceof Button) {
               if (!((Button) object).getId().equals(Const.RESTART_BUTTON)) {
                   ((Button) object).setDisable(true);
               }
           }
       }
   }

   AnimationTimer timer = new AnimationTimer() {
       private long timestamp;
       private long time = 0;
       private long fraction = 0;

       @Override
       public void start() {
           timestamp = System.currentTimeMillis() - fraction;
           super.start();
       }

       @Override
       public void stop() {
           super.stop();
           fraction = System.currentTimeMillis() - timestamp;
       }

       @Override
       public void handle(long now) {
           long newTime = System.currentTimeMillis();
           if (timestamp + 1000 <= newTime) {
               long deltaT = (newTime - timestamp) / 1000;
               time += deltaT;
               timestamp += 1000 * deltaT;
               timeLabel.setText(time / 60 + ":" + time % 60);
           }
       }
   };
}