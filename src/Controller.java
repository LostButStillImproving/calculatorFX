import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.mariuszgromada.math.mxparser.*;



public class Controller {
    private String lastResult = "";
    private Boolean resetField = false;

    @FXML Button sin;
    @FXML Button cos;
    @FXML Button tan;
    @FXML Button ln;
    @FXML Button log;
    @FXML Button sqrt;
    @FXML private TextArea inputArea;

    @FXML
    private void evaluateString() {
        Expression e = new Expression(inputArea.getText());

        double result = e.calculate();
        int flooredResult = (int) Math.floor(result);

        if (result > flooredResult) {
            inputArea.setText(String.valueOf(result));
            lastResult = String.valueOf(result);
        } else {
            inputArea.setText(String.valueOf(flooredResult));
            lastResult = String.valueOf(flooredResult);
        }
        resetField = true;
    }

    @FXML
    private void appendString(ActionEvent event) {

        if (resetField) inputArea.setText("");
        resetField = false;

        Button sourceButton = (Button) event.getSource();
        if (sourceButton.getText().equals("EXP")) {
            inputArea.appendText("E");
            return;
        }
        if (sourceButton.getText().equals("x!")) {
            inputArea.appendText("!");
            return;
        }
        if (sourceButton.getText().equals("x^2")) {
            inputArea.appendText("^2");
            return;
        }
        inputArea.appendText(sourceButton.getText());
    }

    @FXML
    private void delete() {
        String inputAreaText = inputArea.getText();

        if (inputArea.getText().isEmpty()) return;
        inputArea.setText(inputAreaText.substring(0, inputAreaText.length() - 1));
    }

    @FXML
    private void getLastResult() {
        if (lastResult.isEmpty()) return;
        inputArea.appendText(lastResult);
    }

    @FXML
    private void inverse() {
        if (sin.getText().equals("sin")){

            sin.setText("arcsin");
            cos.setText("arccos");
            tan.setText("arctan");
            ln.setText("e^x");
            log.setText("10^");
            sqrt.setText("x^2");

        } else {
            sin.setText("sin");
            cos.setText("cos");
            tan.setText("tan");
            ln.setText("ln");
            log.setText("log");
            sqrt.setText("sqrt");
        }
    }
}
