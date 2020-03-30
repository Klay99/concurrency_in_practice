package threadcoreknowledge.multithreadserror;

import java.util.HashMap;
import java.util.Map;

/**
 * 多线程带来的问题：第三种，发布溢出
 * 解决方案：返回副本
 */
public class MultiThreadsError3 {
    private Map<String, String> states;
    public MultiThreadsError3() {
        states = new HashMap<>();
        states.put("1", "周一");
        states.put("2", "周二");
        states.put("3", "周三");
        states.put("4", "周四");
    }

    public Map<String, String> getStates() {
        return states;
    }

    // 返回副本，外界的操作不会影响到当前类的states
    public Map<String, String> getStatesImprove() {
        return new HashMap<>(states);
    }

    public static void main(String[] args) {
        MultiThreadsError3 multiThreadsError3 = new MultiThreadsError3();
        Map<String, String> states = multiThreadsError3.getStates();
//        System.out.println(states.get("1"));
//        states.remove("1");
//        System.out.println(states.get("1"));
        System.out.println(multiThreadsError3.getStatesImprove().get("1"));
        multiThreadsError3.getStatesImprove().remove("1");
        System.out.println(multiThreadsError3.getStatesImprove().get("1"));
    }
}
