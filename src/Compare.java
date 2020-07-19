public class Compare {
    public String compare(String s1, String s2) {
        String[] ss1 = s1.split(".");
        String[] ss2 = s2.split(".");
        int i = 0;
        while (true) {
            if (ss1[i].compareTo(ss2[i]) < 0) {
                return s1;
            } else if (ss1[i].compareTo(ss2[i]) == 0){
                i++;
            } else {
                return s2;
            }
        }
    }
}
