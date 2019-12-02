package nbp.processor;

public enum Parameters {

    INSTANCE;

    private String currency;
    private String start;
    private String end;

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
