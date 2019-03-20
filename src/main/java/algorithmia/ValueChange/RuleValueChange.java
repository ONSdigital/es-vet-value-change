package algorithmia.ValueChange;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RuleValueChange {

    private BigDecimal value1;
    private BigDecimal value2;
    private BigDecimal percentIncreaseThreshold;
    private BigDecimal absoluteIncreaseThreshold;
    private BigDecimal percentDecreaseThreshold;
    private BigDecimal absoluteDecreaseThreshold;

    public RuleValueChange(InputData sourceInputData) {
        value1 = safeDefineBigDecimal(sourceInputData.value1);
        value2 = safeDefineBigDecimal(sourceInputData.value2);
        percentIncreaseThreshold = safeDefineBigDecimal(sourceInputData.percentIncreaseThreshold);
        absoluteIncreaseThreshold = safeDefineBigDecimal(sourceInputData.absoluteIncreaseThreshold);
        percentDecreaseThreshold = safeDefineBigDecimal(sourceInputData.percentDecreaseThreshold);
        absoluteDecreaseThreshold = safeDefineBigDecimal(sourceInputData.absoluteDecreaseThreshold);
    }

    private BigDecimal safeDefineBigDecimal(String string){
        BigDecimal safeDecimal;
        try {
            safeDecimal = new BigDecimal(string);
        } catch (NumberFormatException e) {
            safeDecimal = new BigDecimal(0);
        } catch (NullPointerException e) {
            safeDecimal = new BigDecimal(0);
        }
        return safeDecimal;
    }

    public String getValueFormula() {
        return "(abs((" + value1 + "-" + value2 + ")/" + value1 + ")*100 > " + percentIncreaseThreshold
                + " AND abs(" + value1 + "-" + value2 + ") > " + absoluteIncreaseThreshold
                + ") OR (abs((" + value1 + "-" + value2 + ")/" + value1 + ")*100 > " + percentDecreaseThreshold
                + " AND abs(" + value1 + "-" + value2 + ") > " + absoluteDecreaseThreshold + ")";
        // return getFormula(inputData.getValue());
    }

    public boolean run() {
        if (value1.compareTo(BigDecimal.ZERO) == 0) return false;

        if (value1.compareTo(value2) == -1) {
            // Values have increased so only do the increase comparison
            return (percentDifference(value1,value2,percentIncreaseThreshold) && valueDifference(value1,value2,absoluteIncreaseThreshold));
        }
        else if (value1.compareTo(value2) == 1) {
            // Values have decreased so only do the decrease comparison
            return (percentDifference(value1,value2,percentDecreaseThreshold) && valueDifference(value1,value2,absoluteDecreaseThreshold));
        }
        return false;
    }

    private boolean percentDifference(BigDecimal value1,BigDecimal value2,BigDecimal percent){
        return (value1.subtract(value2).divide(value1, 8, RoundingMode.HALF_EVEN).abs().multiply(new BigDecimal(100)).compareTo(percent) == 1);
    }

    private boolean valueDifference(BigDecimal value1, BigDecimal value2, BigDecimal threshold){
        return (value1.subtract(value2).abs().compareTo(threshold) == 1);
    }

}