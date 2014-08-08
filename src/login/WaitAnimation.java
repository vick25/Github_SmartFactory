package login;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;
import user.ChangePasswordNewUser;

public class WaitAnimation extends JComponent implements ActionListener {

    private Image[] animation;
    private int index;
    private int direction;
    private int end;
    private JFrame parent;
    private int idUser;
    String status, userName, OldPassword;

    public WaitAnimation(JFrame parent, int idUser, String status, String userName, String OldPassword) {
        this.parent = parent;
        this.idUser = idUser;
        this.status = status;
        this.userName = userName;
        this.OldPassword = OldPassword;
        setOpaque(false);
        index = 0;
        direction = 1;
        MediaTracker tracker = new MediaTracker(this);
        animation = new Image[6];
        for (int i = 0; i < 6; i++) {
            animation[i] = UIHelper.readImage("auth_" + String.valueOf(i) + ".png");
            tracker.addImage(animation[i], i);
        }
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
        }
        Timer animationTimer = new Timer(150, this);
        animationTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        int x = (int) ((getWidth() - animation[index].getWidth(this)) / 2.0);
        int y = (int) ((getHeight() - animation[index].getHeight(this)) / 2.0);
        g.drawImage(animation[index], x, y, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        end++;
        index += direction;
        if (index > 5) {
            index = 5;
            direction = -1;
        } else if (index < 0) {
            index = 0;
            direction = 1;
        }
        if (end == 40) {
            parent.dispose();
            if (status.equalsIgnoreCase("NewUser")) {
                new ChangePasswordNewUser(parent, true, userName, OldPassword).setVisible(true);
            }
//            new MainFrame(idUser).showFrame();
        }
    }
}
