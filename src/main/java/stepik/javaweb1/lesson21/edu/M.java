package stepik.javaweb1.lesson21.edu;

public class M {

    public static void main(String[] args) {
        A aB = new B();

        A aC = new C();

        B bB= new B();

        B bC= new C();

        C cC = new C();

        System.out.println(aC.sum(1,1)==2);
        ;
        //aB.diff(1,1) == 0; // +
//        bB.diff(1,2)==1;
        System.out.println(bB.diff(1,2)==1);
//        bC.diff(1,2) ==1;
        System.out.println(bC.diff(1,2) ==1);
        //bC.mult(2,2)==4;

    }
}
