/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turbocrew;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author Tanvir Raiyan
 */
public class TimeSpinner extends Spinner<Integer>{

    public TimeSpinner() {
        this.setMaxSize(60, 12);
        
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
        
        valueFactory.setValue(1);
        
        this.setValueFactory(valueFactory);
    }
    
}
