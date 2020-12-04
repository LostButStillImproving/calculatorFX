import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.mariuszgromada.math.mxparser.Expression;
import org.xml.sax.SAXException;
import persistence.Connect;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Stack;


public class Controller implements Initializable {
    private String lastResult = "";
    private Boolean resetField = false;
    Stack<String> previousResults = new Stack<>();
    Stack<String> forwardResults = new Stack<>();
    String currentResult = "";

    @FXML Button sin;
    @FXML Button cos;
    @FXML Button tan;
    @FXML Button ln;
    @FXML Button log;
    @FXML Button sqrt;
    @FXML Button equals;
    @FXML private TextArea inputArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> resultsFromDB = Connect.selectAll();
        resultsFromDB.forEach(i -> previousResults.push(i));
    }

    @FXML private void back() {
        if (previousResults.empty()) return;

        this.forwardResults.push(this.currentResult);
        this.currentResult = this.previousResults.pop();
        inputArea.setText(this.currentResult);
    }
    @FXML private void forward() {
        if (forwardResults.empty()) return;

        this.previousResults.push(this.currentResult);
        this.currentResult = forwardResults.pop();
        inputArea.setText(this.currentResult);
    }
    private void addNewResult(String result) {
        if (!this.currentResult.isEmpty()) {
            this.previousResults.push(this.currentResult);
        }
        this.currentResult = result;
        /// send til sqlite her
        Connect.insert(result);

    }

    @FXML private void askWolfram() throws IOException, SAXException, ParserConfigurationException {
        WolframAPI wolframAPI = new WolframAPI();
        if (inputArea.getText().isEmpty()) return;
        String result = wolframAPI.ask(inputArea.getText());
        inputArea.setText(result);
        lastResult = inputArea.getText();
        addNewResult(result);
        resetField = true;
    }

    @FXML private void evaluateString() {
        Expression e = new Expression(inputArea.getText());

        double result = e.calculate();
        int flooredResult = (int) Math.floor(result);

        if (result > flooredResult) {
            inputArea.setText(String.valueOf(result));
            addNewResult(String.valueOf(result));
            lastResult = String.valueOf(result);
        } else {
            inputArea.setText(String.valueOf(flooredResult));
            addNewResult(String.valueOf(flooredResult));
            lastResult = String.valueOf(flooredResult);
        }
        System.out.println(e.checkSyntax());
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
