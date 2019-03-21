package algorithmia.ValueChange;

import junit.framework.TestCase;

public class RuleValueChangeTest extends TestCase {

    public void test_GivenEmptySourceData_WhenGettingFormula_ReturnZeroValuesInFormula() {
        InputData inputData = new InputData();
        RuleValueChange rule = new RuleValueChange(inputData);
        String output = rule.getValueFormula();
        String expectedFormula = "(abs((0-0)/0)*100 > 0 AND abs(0-0) > 0) OR (abs((0-0)/0)*100 > 0 AND abs(0-0) > 0)";
        assertEquals(expectedFormula,output);
    }

    public void test_GivenCompleteSourceData_WhenGettingFormula_ReturnCorrectFormula() {
        InputData inputData = new InputData("201.0","201.51",
                "25","20",
                "10","5");

        RuleValueChange rule = new RuleValueChange(inputData);
        String output = rule.getValueFormula();
        String expectedFormula = "(abs((201.0-201.51)/201.0)*100 > 10 AND abs(201.0-201.51) > 25) OR " +
                "(abs((201.0-201.51)/201.0)*100 > 5 AND abs(201.0-201.51) > 20)";
        assertEquals(expectedFormula,output);
    }

    public void test_GivenTextSourceData_WhenGettingFormula_ReturnZeroValuesInFormula() {
        InputData inputData = new InputData("Bananas","201.51",
                "25","Toast",
                "10","5");

        RuleValueChange rule = new RuleValueChange(inputData);
        String output = rule.getValueFormula();
        String expectedFormula = "(abs((0-201.51)/0)*100 > 10 AND abs(0-201.51) > 25) OR " +
                "(abs((0-201.51)/0)*100 > 5 AND abs(0-201.51) > 0)";
        assertEquals(expectedFormula,output);
    }

    public void test_GivenNullSourceData_WhenGettingFormula_ReturnZeroValuesInFormula() {
        InputData inputData = new InputData(null,null,null,null,null,null);

        RuleValueChange rule = new RuleValueChange(inputData);
        String output = rule.getValueFormula();
        String expectedFormula = "(abs((0-0)/0)*100 > 0 AND abs(0-0) > 0) OR (abs((0-0)/0)*100 > 0 AND abs(0-0) > 0)";
        assertEquals(expectedFormula,output);
    }

    public void test_GivenAllZeroInputs_RunRule_DoNotTrigger(){
        InputData inputData = new InputData();
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(false,output);
    }

    public void test_GivenPositivePercentIncrease_RunRule_Trigger(){
        InputData inputData = new InputData("20","50","29.99","0","149","0");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(true,output);
    }

    public void test_GivenNegativeToPositiveIncrease_RunRule_Trigger(){
        InputData inputData = new InputData("-10","50","59.99","0","599","0");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(true,output);
    }

    public void test_GivenNegativeToNegativeIncrease_RunRule_Trigger(){
        InputData inputData = new InputData("-15","-10","4.99","0","32","0");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(true,output);
    }

    public void test_GivenPositivePercentIncreaseAndNotExceedAbsoluteThreshold_RunRule_Trigger(){
        InputData inputData = new InputData("20","50","30","0","149","");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(false,output);
    }


    public void test_GivenNegativeToPositiveIncreaseAndNotExceedAbsoluteThreshold_RunRule_Trigger(){
        InputData inputData = new InputData("-10","50","60","0","599","");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(false,output);
    }

    public void test_GivenNegativeToNegativeIncreaseAndNotExceedAbsoluteThreshold_RunRule_Trigger(){
        InputData inputData = new InputData("-15","-10","5","0","32","");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(false,output);
    }

    public void test_GivenPositivePercentDecrease_RunRule_Trigger(){
        InputData inputData = new InputData("50","20","0","29.99","0","59.99");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(true,output);
    }

    public void test_GivenPositiveToNegativeDecrease_RunRule_Trigger(){
        InputData inputData = new InputData("50","-10","0","59.99","0","119.99");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(true,output);
    }

    public void test_GivenNegativeToNegativeDecrease_RunRule_Trigger(){
        InputData inputData = new InputData("-10","-15","0","4.99","0","49.99");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(true,output);
    }

    public void test_GivenZeroToNegativeDecrease_RunRule_Trigger(){
        InputData inputData = new InputData("0","-15","0","0","0","0");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(false,output);
    }

    public void test_GivenPositiveToZeroDecrease_RunRule_Trigger(){
        InputData inputData = new InputData("15","0","0","14.99","0","99.99");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(true,output);
    }

    public void test_GivenPositivePercentDecreaseAndNotExceedAbsoluteThreshold_RunRule_NotTrigger(){
        InputData inputData = new InputData("50","20","","30","","59.99");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(false,output);
    }


    public void test_GivenPositiveToNegativeDecreaseAndNotExceedAbsoluteThreshold_RunRule_NotTrigger(){
        InputData inputData = new InputData("50","-10","","60","599","119.99");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(false,output);
    }

    public void test_GivenNegativeToNegativeDecreaseAndNotExceedAbsoluteThreshold_RunRule_NotTrigger(){
        InputData inputData = new InputData("-10","-15","","5","","49.99");
        RuleValueChange rule = new RuleValueChange(inputData);
        boolean output = rule.run();
        assertEquals(false,output);
    }

}

/*
if (val1 > val2) { Decrease logic }
if (val1 < val2) { Increase Logic }
else do not trigger



Increase logic:

20 -> 50           150% | 30


(20 - 50) / 20

        abs((val1 - val2) / val1) * 100


-10 -> 50       600% | 60

        (-10 - 50) / -10   =>


-15 -> -10

    10 / 15    33%


Decrease Logic
val1 > val2

50 -> 20      60% | 30

    abs((val1 - val2) / val1) * 100
          50 - 20 / 50  60%

50 -> -10   -120% | 60
    abs((val1 - val2) / val1) * 100
        50 + 10 / 50 120%

-10 -> -15    50% | 5

*/