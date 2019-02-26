package Core;

import Pages.Login;

public class Main {

    public static void main(String[] args){
        Client myclient = Client.getInstance();
        if(!Client.getValid()) return;
        Login loginPage = new Login();
        loginPage.main(args);
    }
}
