package algorithmia.ValueChange;

public class InputData {

    public String value1 = "";
    public String value2 = "";
    public String absoluteIncreaseThreshold = "";
    public String absoluteDecreaseThreshold = "";
    public String percentIncreaseThreshold = "";
    public String percentDecreaseThreshold = "";
    public Object metaData = "{}";

    public InputData() {}

    public InputData(String value1, String value2,
                     String absoluteIncreaseThreshold, String absoluteDecreaseThreshold,
                     String percentIncreaseThreshold, String percentDecreaseThreshold) {
        this.value1 = value1;
        this.value2 = value2;
        this.absoluteIncreaseThreshold = absoluteIncreaseThreshold;
        this.absoluteDecreaseThreshold = absoluteDecreaseThreshold;
        this.percentIncreaseThreshold = percentIncreaseThreshold;
        this.percentDecreaseThreshold = percentDecreaseThreshold;
    }

}
