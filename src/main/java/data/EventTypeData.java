package data;

public enum EventTypeData {
    OPEN("Открытый вебинар");

    private String name;

    EventTypeData(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
