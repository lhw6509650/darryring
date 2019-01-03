package com.darryring.util;


import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Component
public class ImageCreate {

    public void createImage(HttpServletRequest request, HttpServletResponse response)throws IOException {
        BufferedImage bfi = new BufferedImage(105, 40,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bfi.getGraphics();
        graphics.fillRect(0, 0, 110, 40);
        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
                .toCharArray();
        Random random = new Random();
        int index;
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            index = random.nextInt(ch.length);
            graphics.setColor(new Color(random.nextInt(255), random
                    .nextInt(255), random.nextInt(255)));
            Font font = new Font("微软雅黑", 30, 26);
            graphics.setFont(font);
            graphics.drawString(ch[index] + "", (i * 20) + 5, 30);
            str.append(ch[index]);
        }
        int area = (int) (0.02 * 105 * 40);
        for (int i = 0; i < area; i++) {
            int x = (int) (Math.random() * 80);
            int y = (int) (Math.random() * 25);
            bfi.setRGB(x, y, (int) (Math.random() * 255));
        }
        for (int i = 0; i < 6; i++) {
            int xstart = (int) (Math.random() * 105);
            int ystart = (int) (Math.random() * 40);
            int xend = (int) (Math.random() * 105);
            int yend = (int) (Math.random() * 40);
            graphics.setColor(interLine(1, 255));
            graphics.drawLine(xstart, ystart, xend, yend);
        }
        HttpSession session = request.getSession();
        // 保存了验证码 服务器内部产生
        session.setAttribute("verificationCode", str.toString());
        System.out.println("验证码：" + str.toString());
        ImageIO.write(bfi, "JPG", response.getOutputStream());
    }

    private static Color interLine(int low, int high) {
        if (low > 255) {
            low = 255;
        }
        if (high > 255) {
            high = 255;
        }
        if (low < 0) {
            low = 0;
        }
        if (high < 0) {
            high = 0;
        }
        int interval = high - low;
        int r = low + (int) (Math.random() * interval);
        int g = low + (int) (Math.random() * interval);
        int b = low + (int) (Math.random() * interval);
        return new Color(r, g, b);
    }


}
