/*
 * This program creates an instance of the TDApp classd which
 * displays the GUI for the tower defense application
 */
package tdgame;


public class TDApp {
    private TDFrame gameFrame;
    
    public TDApp() {
        gameFrame = new TDFrame();
    }
    
    
    public static void main(String[] args) {
        TDApp gameApp = new TDApp();
    }
    
}
