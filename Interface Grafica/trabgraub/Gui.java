package trabgraub;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Gui extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton regPC;
    private JButton lerArquivoButton;
    private JTextField textField1;
    private JButton rodarButton;
    private JButton salvarButton;
    private JLabel labelPC;
    private JButton regA;
    private JButton regB;
    private JButton regC;
    private JLabel labelC;
    private JLabel labelB;
    private JLabel labelA;
    private CPU cpu = new CPU();

    public static void main(String[] args){
        JFrame frame = new JFrame("Gui");
        frame.setBackground(Color.black);
        ImageIcon img = new ImageIcon("C:\\Users\\eduar\\Downloads\\aaa.jpg");
        frame.setIconImage(img.getImage());
        frame.setBounds(500, 200, 1000, 400);
        frame.setContentPane(new Gui().panel1);
        frame.setBackground(new Color(76, 76, 76));
        //frame.pack();
        frame.setVisible(true);
    }

    public Gui(){
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cpu.getUc().getMem().gravar(textField1.getText());
                atualizarMemoria();
            }
        });

        rodarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cpu.getUc().executarPrograma();
                atualizarMemoria();
            }
        });

        lerArquivoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cpu.getUc().getMem().carregar(textField1.getText());
                atualizarMemoria();
            }
        });
    }

    public void atualizarMemoria(){
        Memoria mem = cpu.getUc().getMem();

        String[][] values = new String[17][9];
        for (int j = 1; j < 9; j++) {
            values[0][j] = "0" + j;
        }

        for (int i = 0; i < 16; i++){
            values[i + 1][0] = String.format("0x%08X", (i + (7 * i))).substring(8);
            for (int j = 0; j < 8; j++){
                String valor =  String.format("0x%08X", mem.getMemoria((i + (7 * i)) + j)).substring(8);
                if (cpu.getUc().getPC().getValor() == i + (7 * i) + j){
                    values[i + 1][j + 1] = "<" + valor + ">";
                } else {
                    values[i + 1][j + 1] = valor;
                }
            }
        }

        regPC.setText(String.format("0x%08X", cpu.getUc().getPC().getValor()).substring(8));
        regA.setText(String.format("0x%08X", cpu.getUc().getUla().getA().getValor()).substring(8));
        regB.setText(String.format("0x%08X", cpu.getUc().getUla().getB().getValor()).substring(8));
        regC.setText(String.format("0x%08X", cpu.getUc().getUla().getC().getValor()).substring(8));

        table1.setModel(new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return true;
            }

            @Override
            public int getRowCount() {
                return 17;
            }

            @Override
            public int getColumnCount() {
                return 9;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return values[rowIndex][columnIndex];
            }
        });

        table1.setBackground(Color.DARK_GRAY);
        table1.setForeground(new Color(0, 163, 163));
        labelPC.setForeground(new Color(0, 163, 163));
        labelA.setForeground(new Color(0, 163, 163));
        labelB.setForeground(new Color(0, 163, 163));
        labelC.setForeground(new Color(0, 163, 163));
    }
}
