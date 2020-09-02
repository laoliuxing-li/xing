package com.xing.snake;

import javax.swing.*;

public class StateGames {
    public static void main(String[] args) {
        //1.绘制一个静态窗口 JFrame
        JFrame frame = new JFrame("贪吃蛇小游戏");
        //设置界面大小
        frame.setBounds(10,10,900,700);
        //窗口大小不可改变
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //设置关闭事件

        //2.面板 JPanel   可以加入到JFrame
        frame.add(new GamePanel());


        frame.setVisible(true); //让窗口能够展现出来
    }
}
