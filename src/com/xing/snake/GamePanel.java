package com.xing.snake;

import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener,ActionListener {
    int lenth;//蛇的长度
    int[] snakeX = new int[600];
    int[] snakeY = new int[500];
    String fx = null;
    boolean isStart = false;//游戏是否开始

    Timer timer = new Timer(200,this);
    //1.定义一个食物
    int foodX;
    int foodY;
    Random random = new Random();

    //死亡判断
    boolean isFail = false;

    //积分系统
    int score;

    public GamePanel() {
        init();
        //获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    //初始化
    public void init(){
        lenth = 3;
        snakeX[0] = 100;snakeY[0] = 100;//头部坐标
        snakeX[1] = 75;snakeY[1] = 100;//身体第一节坐标
        snakeX[2] = 50;snakeY[2] = 100;//身体第二节坐标
        fx = "R";    //R右L左U上D下
        timer.start();  //时间启动
        foodX = 25 + 25*random.nextInt(34);
        foodY = 75 + 25*random.nextInt(24);
        score = 0;
    }

    //面板:画界面,画蛇
    //Graphics:画笔
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);    //清屏
        this.setBackground(Color.WHITE);        //设置背景颜色
        //绘制头部的广告
        Data.header.paintIcon(this,g,25,11);
        //绘制游戏区域
        g.fillRect(25,75,850,600);

        //画静态小蛇
        if(fx.equals("R")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }

//        Data.body.paintIcon(this,g,snakeX[1],snakeY[1]);
//        Data.body.paintIcon(this,g,snakeX[2],snakeY[2]);
        for (int i = 1; i < lenth ; i++) {
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
        //游戏提示是否开始
        if(isStart == false){
            //画一个文字.String
            g.setColor(Color.WHITE);
            g.setFont(new Font("隶书",Font.BOLD,40));
            g.drawString("按下空格开始游戏",300,300);
        }
        //画食物
        Data.food.paintIcon(this,g,foodX,foodY);
        //画积分
        g.setColor(Color.black);
        g.setFont(new Font("隶书",Font.BOLD,18));
        g.drawString("长度:"+lenth,750,35);
        g.drawString("分数:"+score,750,50);


        //失败提醒
        if(isFail){
            //画一个文字
            g.setColor(Color.RED);
            g.setFont(new Font("隶书",Font.BOLD,40));
            g.drawString("游戏失败,按下空格重新开始",200,300);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //按下
        //获取按下的键
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_SPACE){
            if(isFail){
                isFail = false;
                init();     //重新初始化游戏
            }else{
                //暂停游戏
                isStart = !isStart;
            }

            repaint();  //刷新界面
        }
        //键盘控制走向
        if(keyCode == KeyEvent.VK_RIGHT){
            if(fx != "L"){
                fx = "R";
            }
        }else if(keyCode == KeyEvent.VK_LEFT){
            if(fx != "R"){
                fx = "L";
            }
        }else if(keyCode == KeyEvent.VK_UP){
            if(fx != "D"){
                fx = "U";
            }
        }else if(keyCode == KeyEvent.VK_DOWN){
            if(fx != "U"){
                fx = "D";
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }



    //接收键盘的输入:监听
    @Override
    public void actionPerformed(ActionEvent e) {
        //如果游戏处于开始状态
        if(isStart && isFail==false){
            //右移
            for (int i = lenth-1; i > 0 ; i--) {    //除了脑袋,身体向前移动
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
            }
            //通过控制方向让头部移动
            if(fx.equals("R")){
                snakeX[0] = snakeX[0]+25;
                if(snakeX[0] > 850) {
                    isFail = true;
                }
            }else if(fx.equals("L")){
                snakeX[0] = snakeX[0]-25;
                if(snakeX[0] < 25) {
                    isFail = true;
                }
            }else if(fx.equals("U")){
                snakeY[0] = snakeY[0]-25;
                if(snakeY[0] < 75) {
                    isFail = true;
                }
            }else if(fx.equals("D")){
                snakeY[0] = snakeY[0]+25;
                if(snakeY[0] > 650) {
                    isFail = true;
                }
            }

            //如果蛇头和食物重合
            if(snakeX[0] == foodX && snakeY[0] == foodY){
                //长度+1
                lenth++;
                score += 10;
                foodX = 25 + 25*random.nextInt(34);
                foodY = 75 + 25*random.nextInt(24);
            }
            //结束判断
            for (int i = 1; i < lenth ; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]){
                    isFail = true;
                }
            }
            //刷新界面
            repaint();
        }
        timer.start();  //时间启动
    }



}
