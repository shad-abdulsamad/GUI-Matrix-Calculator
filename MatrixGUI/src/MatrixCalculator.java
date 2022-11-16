import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.NumberFormat;
//  Group-B
//  Team:
//      1- Shad Abudlsamad
//      2- Rahen Muhammed
//      3- Zryan Muhammed

public class MatrixCalculator {
    private static final int max = 100;
    private static final int decimals = 3;
    private JLabel statusBar; //to see the status of the matrix
    private JTextArea matrixA, matrixB, result;
    private static NumberFormat numberFormat;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Matrix Calculator");
        frame.setSize(new Dimension(725, 200));
        MatrixCalculator app = new MatrixCalculator();
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
        numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(1);
        numberFormat.setMaximumFractionDigits(decimals);
    }
    public Component createComponents() {
        //MATRICES
        matrixA = new JTextArea();
        matrixB = new JTextArea();
        result = new JTextArea();
        result.setEnabled(false);
        result.setDisabledTextColor(new Color(0,0,0));
        JPanel matrixPanel = new JPanel();
        matrixPanel.add(MatrixPane("Matrix A", matrixA));
        matrixPanel.add(MatrixPane("Matrix B", matrixB));
        matrixPanel.add(MatrixPane("Result: ", result));

        // operations
        JPanel buttonPanel = new JPanel();
        JButton add = new JButton("A + B ");
        JButton subtract=new JButton("A-B");
        JButton firstMultiplySecond = new JButton("A * B ");
        JButton secondMultiplyFirst = new JButton("B * A ");
        JButton resetButton=new JButton("Reset");
        JButton quitButton=new JButton("Quit");
        buttonPanel.add(add);
        buttonPanel.add(subtract);
        buttonPanel.add(firstMultiplySecond);
        buttonPanel.add(secondMultiplyFirst);
        buttonPanel.add(resetButton);
        buttonPanel.add(quitButton);
        //Button Register
        add.addActionListener(event -> {
            try {
                DisplayMatrix(AddMatrix(readMatrix(matrixA),
                        readMatrix(matrixB)), result);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        });
        subtract.addActionListener(event -> {
            try {
                DisplayMatrix(subtractMatrix(readMatrix(matrixA),
                        readMatrix(matrixB)), result);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        });
        firstMultiplySecond.addActionListener(event -> {
            try {
                DisplayMatrix(MultiplyMatrix(
                        readMatrixNotSquare(matrixA),
                        readMatrixNotSquare(matrixB)), result);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        });
        secondMultiplyFirst.addActionListener(event -> {
            try {
                DisplayMatrix(MultiplyMatrix(readMatrixNotSquare(matrixB),
                        readMatrixNotSquare(matrixA)), result);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        });
        resetButton.addActionListener(event -> {
                if(event.getSource()==resetButton){
                    matrixA.setText(null);
                    matrixB.setText(null);
                    result.setText(null);
                }
        });
        quitButton.addActionListener(event -> {
            if(event.getSource()==quitButton){
                System.exit(0);
            }
        });
        //Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(matrixPanel);
        panel.add(buttonPanel);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BorderLayout());
        myPanel.add("Center", panel);
        JLabel matrixSize= new JLabel("Size 3x3");
        statusBar = new JLabel("Ready");
        myPanel.add("North",matrixSize);
        myPanel.add("South", statusBar);
        return myPanel;
    }
    // Setup Individual Matrix Panes
    private JPanel MatrixPane(String str, JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        int size = 200;
        scrollPane.setPreferredSize(new Dimension(size, size));
        JLabel label = new JLabel(str);
        label.setLabelFor(scrollPane);
        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(label);
        pane.add(scrollPane);
        return pane;
    }
    public float[][] readMatrix(JTextArea textarea) throws Exception {
        //Parse Text Area
        String rawtext = textarea.getText();
        String val;
        int i = 0;
        int j = 0;
        int[] resize = new int[max];
        // Determine Matrix Size/Valid
        StringTokenizer ts = new StringTokenizer(rawtext, "\n");
        while (ts.hasMoreTokens()) {
            StringTokenizer ts2 = new StringTokenizer(ts.nextToken());
            while (ts2.hasMoreTokens()) {
                ts2.nextToken();
                j++;
            }
            resize[i] = j;
            i++;
            j = 0;
        }
        statusBar.setText("Matrix Size: " + i);
        System.out.println("Matrix Size: " + i);
        for (int c = 0; c < i; c++) {
            System.out.println("i=" + i + "  j=" + resize[c] + "   Column: "
                    + c);
            if (resize[c] != i) {
                statusBar.setText("Invalid Matrix Entered. Size Mismatch.");
                throw new Exception("Invalid Matrix Entered. Size Mismatch.");
            }
        }
        //* setting matrix size.
        int n = i;
        float[][] matrix = new float[n][n];
        i = j = 0;
        // text parsing
        StringTokenizer st = new StringTokenizer(rawtext, "\n");
        while (st.hasMoreTokens()) {
            StringTokenizer st2 = new StringTokenizer(st.nextToken());
            while (st2.hasMoreTokens()) {
                val = st2.nextToken();
                try {
                    matrix[i][j] = Float.parseFloat(val);
                } catch (Exception exception) {
                    statusBar.setText("Invalid Number Format");
                }
                j++;
            }
            i++;
            j = 0;
        }
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                System.out.print("m[" + i + "][" + j + "] = "
                        + matrix[i][j] + "   ");
            }
            System.out.println();
        }
        return matrix;
    }
    public float[][] readMatrixNotSquare(JTextArea textarea)
            throws Exception {
        String rawtext = textarea.getText();
        StringTokenizer ts = new StringTokenizer(rawtext, "\n");
        float[][] matrix = new float[ts.countTokens()][];
        StringTokenizer st2;
        int row = 0;
        int col = 0;
        //making sure rows are same length
        int last;
        int curr = -5;
        while (ts.hasMoreTokens()) {
            st2 = new StringTokenizer(ts.nextToken(), " ");
            last = curr;
            curr = st2.countTokens();
            if(last != -5 && curr!= last)
                throw new Exception("Rows not of equal length");
            matrix[row] = new float[st2.countTokens()];
            while (st2.hasMoreElements()) {
                matrix[row][col++] = Float.parseFloat(st2.nextToken());
            }
            row++;
            col = 0;
        }
        System.out.println();
        return matrix;
    }
    public void DisplayMatrix(float[][] matrix, JTextArea textarea) {
        String str = "";
        String dv;
        for (float[] floats : matrix) {
            for (float aFloat : floats) {
                dv = numberFormat.format(aFloat);
                str = str.concat(dv + "  ");
            }
            str = str.concat("\n");
        }
        textarea.setText(str);
    }
    public float[][] AddMatrix(float[][] a, float[][] b) {
        int tms = a.length;
        int tmsB = b.length;
        if (tms != tmsB) {
            statusBar.setText("Matrix Size Mismatch");
        }
        float[][] matrix = new float[tms][tms];
        for (int i = 0; i < tms; i++)
            for (int j = 0; j < tms; j++) {
                matrix[i][j] = a[i][j] + b[i][j];
            }
        return matrix;
    }
    public float[][] subtractMatrix(float[][] a, float[][] b) {
        int tms = a.length;
        int tmsB = b.length;
        if (tms != tmsB) {
            statusBar.setText("Matrix Size Mismatch");
        }
        float[][] matrix = new float[tms][tms];
        for (int i = 0; i < tms; i++)
            for (int j = 0; j < tms; j++) {
                matrix[i][j] = a[i][j] - b[i][j];
            }
        return matrix;
    }
    public float[][] MultiplyMatrix(float[][] a, float[][] b) throws Exception {
        if(a[0].length != b.length)
            throw new Exception("Matrices incompatible for multiplication");
        float[][] matrix = new float[a.length][b[0].length];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < b[i].length; j++)
                matrix[i][j] = 0;
        //cycle through answer matrix
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                matrix[i][j] = calculateRowColumnProduct(a,i,b,j);
            }
        }
        return matrix;
    }
    public float calculateRowColumnProduct(float[][] A, int row, float[][] B, int col){
        float product = 0;
        for(int i = 0; i < A[row].length; i++)
            product +=A[row][i]*B[i][col];
        return product;
    }
}