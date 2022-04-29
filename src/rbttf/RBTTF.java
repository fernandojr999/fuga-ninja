package rbttf;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class RBTTF extends JFrame {

    //importa as imagens dos objetos
    ImageIcon iconEscorrega = new ImageIcon(getClass().getResource("escorrega.gif"));
    ImageIcon iconMorreu = new ImageIcon(getClass().getResource("Morreu.png"));
    ImageIcon iconCano = new ImageIcon(getClass().getResource("obst.png"));
    ImageIcon iconFundo = new ImageIcon(getClass().getResource("fundo2.png"));
    ImageIcon iconFundoAux = new ImageIcon(getClass().getResource("fundo2.png"));
    ImageIcon iconPulando = new ImageIcon(getClass().getResource("pulo.png"));
    ImageIcon iconAndando = new ImageIcon(getClass().getResource("anda.gif"));
    ImageIcon iconCano2 = new ImageIcon(getClass().getResource("obst.png"));
    ImageIcon iconScore2 = new ImageIcon(getClass().getResource("partescore.png"));

    //cria uma label com cada imagem
    JLabel morreu = new JLabel(iconMorreu);
    JLabel cano2 = new JLabel(iconCano2);
    JLabel cano = new JLabel(iconCano);
    JLabel anda = new JLabel(iconAndando);
    JLabel pulando = new JLabel(iconPulando);
    JLabel fundo = new JLabel(iconFundo);
    JLabel fundoAux = new JLabel(iconFundoAux);
    JLabel personagem = new JLabel(iconAndando);
    //JLabel personagem2 = new JLabel(iconEscorrega);

    JLabel score2 = new JLabel(iconScore2);
    JButton botao = new JButton("ok");

    //variaveis de auxilio
    int libera;
    int posicaoX = 300;
    int posicaoY = 250;
    int fundoX = 0;
    int fundoY = 0;
    int bateu = 0;
    int score = 0;
    int gravidade = 150;
    int inverte = 0;
    int mais = 1;
    int menos = -1;
    double velocityY = 0;
    double gravity = 20.0;
    double deltaTime;
    JLabel score1 = new JLabel("Score: 0");
    int ypersonagem = 249;

    //jogo
    public RBTTF() {
        tela();
        editarComponentes();
        addMovimento();
        new MovimentoFundo().start();

    }

    //captura das tecla pressionadas no teclado
    public void addMovimento() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                
                
                if (e.getKeyCode() == 38) {
                    personagem.setBounds(posicaoX, 249, 180, 180);
                    ypersonagem = 249;

                }
                
                if (e.getKeyCode() == 40) {
                    personagem.setIcon(iconEscorrega);
                    personagem.setBounds(posicaoX, 270, 180, 180);

                }
            }

            @Override
            public void keyReleased(KeyEvent e
            ) {
                personagem.setIcon(iconAndando);

            }

        }
        );
    }

    //Método para editar os componentes do jogo
    public void editarComponentes() {
        botao.setBounds(300, 180, 200, 200);
        cano2.setBounds(2500, 7, 100, 150);
        anda.setBounds(0, 0, 200, 200);
        personagem.setBounds(posicaoX, 250, 200, 200);
        //personagem2.setBounds(posicaoX, 250, 200, 200);
        fundoAux.setBounds(800, fundoY, 800, 600);
        cano.setBounds(1500, 250, 100, 150);
        fundo.setBounds(fundoX, fundoY, 800, 600);
        score1.setFont(new Font("Comics Sans MS", Font.PLAIN, 20));
        score1.setForeground(Color.red);
        score1.setBounds(50, 50, 115, 40);
        score2.setBounds(50, 50, 115, 40);
    }

    //Método para contruir a tela
    public void tela() {
        setTitle("Fuga Ninja");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLayout(null);
        setVisible(true);

        //Adiciona os componentes na tela
        add(score1);
        add(score2);
        add(morreu);
        add(cano2);
        add(cano);
        add(personagem);
        //add(personagem2);
        add(fundo);
        add(fundoAux);
    }

    //método para identificar colisão entre os componentes
    public boolean bateu(Component a, Component b) {
        int aX = a.getX();
        int aY = a.getY();
        int ladoDireitoA = aX + a.getWidth();
        int ladoEsquerdoA = aX;
        int ladoBaixoA = aY + a.getHeight();
        int ladoCimaA = aY;

        int bX = b.getX();
        int bY = b.getY();
        int ladoDireitoB = bX + b.getWidth();
        int ladoEsquerdoB = bX;
        int ladoBaixoB = bY + b.getHeight();
        int ladoCimaB = bY;

        boolean bateu = false;

        boolean cDireita = false;
        boolean cCima = false;
        boolean cBaixo = false;
        boolean cEsquerda = false;

        if (ladoDireitoA >= ladoEsquerdoB) {
            cDireita = true;
        }
        if (ladoCimaA <= ladoBaixoB) {
            cCima = true;
        }
        if (ladoBaixoA >= ladoCimaB) {
            cBaixo = true;
        }
        if (ladoEsquerdoA <= ladoDireitoB) {
            cEsquerda = true;
        }

        if (cDireita && cEsquerda && cBaixo && cCima) {
            bateu = true;
        }

        return bateu;
    }

    //Classe para movimentar o cenário
    public class MovimentoFundo extends Thread {

        public void run() {
            while (true) {

                try {
                    sleep(8);
                } catch (Exception erro) {
                }

                //pulo do personagem
                personagem.setBounds(posicaoX, personagem.getY() - inverte, 200, 200);
                System.out.println(gravidade);

                //gravidade
                //Quando precionar up o personagem vai pra posicao 249 e a variavel inverte recebe o valor 1
                if (personagem.getY() == ypersonagem) {
                    inverte = mais;

                }

                if (personagem.getY() == 5) {
                    inverte = menos;
                }

                if (personagem.getY() == 249) {
                    personagem.setBounds(posicaoX, 250, 180, 180);
                    ypersonagem = 0;

                }
                /*if (personagem.getY() == 270) {
                    
                    personagem = personagem2;
                    personagem.setBounds(posicaoX, 250, 180, 180);
                }*/
                //*************************************************
                cano2.setBounds(cano2.getX() - 1, -150, 100, 400);
                cano.setBounds(cano.getX() - 1, 400, 20, 50);
                fundo.setBounds(fundo.getX() - 1, fundoY, 800, 600);

                if ((cano.getX() < -800) || (cano2.getX() < -800)) {
                    cano.setBounds(800, 350, 20, 50);
                    cano2.setBounds(1600, 7, 100, 150);
                }

                if (fundo.getX() < -800) {
                    fundo.setBounds(0, fundoY, 800, 600);
                    fundoAux.setBounds(800, fundoY, 800, 600);

                }
                if (fundo.getX() < 0) {
                    fundoAux.setBounds(fundoAux.getX() - 1, fundoY, 800, 600);

                }
                
               

                //Score
                // Se o obstáculo passar pelo meio da tela o score ganha um ponto
                if ((cano.getX() == 280) || (cano2.getX() == 280)) {
                    score++;
                    score1.setForeground(Color.red);
                    score1.setFont(new Font("Comics Sans MS", Font.PLAIN, 20));
                    score1.setText("Score: " + score);
                    System.out.println(score);
                }

                //contador sleep
           

                //Identifica a colisao
                if (bateu(cano, personagem)) {
                    JOptionPane.showMessageDialog(null, "Perdeu");
                    System.exit(0);

                }

                if (bateu(cano2, personagem)) {
                    JOptionPane.showMessageDialog(null, "Perdeu");
                    System.exit(0);

                }

            }

        }

    }

    //Método principal
    public static void main(String[] args) {
        new RBTTF();

    }
}
