//JUnit 5.8
import static org.junit.jupiter.api.Assertions.*;
class MatrixCalculatorTest {
    private static MatrixCalculator app;
    private static float [][] MATRIX_1;
    private static float [][] MATRIX_2;
    private static float [][] addAnswer;
    private static float [][] subAnswer;
    private static float [][] mulAnswerAxB;
    private static float [][] mulAnswerBxA;
    private static String sizeMatrix1;
    private static String sizeMatrix2;

    @org.junit.jupiter.api.BeforeAll
    public static void setUp(){
        MATRIX_1= new float[][]{{3,2,5},{13,19,43},{20,16,76}};
        MATRIX_2 =new float[][]{{7,3,19},{111,29,13},{7,19,67}};
        addAnswer=new float[][]{{10,5,24},{124,48,56},{27,35,143}};
        subAnswer=new float[][]{{-4,-1,-14},{-98,-10,30},{13,-3,9}};
        mulAnswerAxB =new float[][]{{278,162,418},{2501,1407,3375},{2448,1968,5680}};
        mulAnswerBxA= new float[][] {{440,375,1608},{970,981,2790},{1608,1447,5944}};
        sizeMatrix1="["+MATRIX_1.length+"]"+"["+MATRIX_1[0].length+"]";
        sizeMatrix2="["+MATRIX_2.length+"]"+"["+MATRIX_2[0].length+"]";
        app = new MatrixCalculator();
    }

    @org.junit.jupiter.api.Test
    void fixedSize(){
        assertEquals("[3][3]",sizeMatrix1,"matrix size should be 3 by 3.");
        assertEquals("[3][3]",sizeMatrix2,"matrix size should be 3 by 3.");
    }
    @org.junit.jupiter.api.Test
    void addMatrix() {
        assertArrayEquals(addAnswer,app.AddMatrix(MATRIX_1, MATRIX_2),"The addition method should work correctly.");
    }

    @org.junit.jupiter.api.Test
    void subtractMatrix() {
        assertArrayEquals(subAnswer,app.subtractMatrix(MATRIX_1,MATRIX_2),"The subtraction method should work correctly.");
    }

    @org.junit.jupiter.api.Test
    void multiplyMatrixAxB() throws Exception {
        assertArrayEquals(mulAnswerAxB,app.MultiplyMatrix(MATRIX_1, MATRIX_2),"The multiplication method should work correctly(AxB).");
    }
    @org.junit.jupiter.api.Test
    void multiplyMatrixBxA() throws Exception {
        assertArrayEquals(mulAnswerBxA,app.MultiplyMatrix(MATRIX_2, MATRIX_1),"The multiplication method should work correctly(BxA).");
    }
}