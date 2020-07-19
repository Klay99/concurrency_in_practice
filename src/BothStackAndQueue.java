import java.util.ArrayList;

public class BothStackAndQueue {
    private ArrayList<Integer> data = new ArrayList<>();
    public void addElement(Integer value) {
        data.add(0, value);
    }
    public Integer getElement(boolean isStack) {
        if (data == null || data.size() == 0) return null;
        return isStack ? data.get(0) : data.get(data.size() - 1);
    }
    public Integer getAndRemove(boolean isStack) {
        if (data == null || data.size() == 0) return null;
        Integer res;
        if (isStack) {
            res = data.get(0);
            data.remove(0);
        } else {
            res = data.get(data.size() - 1);
            data.remove(data.size() - 1);
        }
        return res;
    }
}
