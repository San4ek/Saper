public class Random {

    public static int getRandNumber(){
       java.util.Random rnd = new java.util.Random(System.currentTimeMillis());

        return Const.MIN_VALUE + rnd.nextInt(Const.MAX_VALUE - Const.MIN_VALUE + 1);
    }
}