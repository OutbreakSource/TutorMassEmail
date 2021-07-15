package gui;

public class MyMan {

    public boolean myMan(String[] student, String checker){

        int flag = 0;

        String[] line1 = student[1].split(" ");


        for (String s : line1) {
            if (checker.contains(s)) {
                flag++;
            }
        }

        return flag >= 2;
    }

}
