

public abstract class HeapMember {

    private int value;
    private int index;
    private int id;

    // constructor

    public HeapMember(int name) {
        value = Integer.MAX_VALUE;
        index = -1;
        this.id = name;
    }

    public HeapMember(int x, int i) {
        value = x;
        index = i;
    }

    public HeapMember(int x, int i, int n) {
        value = x;
        index = i;
        id = n;
    }

    public int getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public void setValue(int x) {
        value = x;
    }

    public void setIndex(int i) {
        index = i;
    }

    public int getId() {
        return id;
    }




}
