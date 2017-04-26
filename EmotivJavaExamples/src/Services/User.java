/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

import org.neuroph.core.NeuralNetwork;

/**
 * Singleton class User holds the information that was obtained from the data 
 * base that relates to a particular user. This information is available within
 * the context of the program
 * 
 * @author Marc S
 */
public class User {
    private String userName;
    private NeuralNetwork userNeuralNet;
    private static User instance;
    
    protected User () {userName = "Marc S";};
    
    public static User getInstance(){
        if(instance == null){
            User.instance = new User();
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public NeuralNetwork getUserNeuralNet() {
        return userNeuralNet;
    }

    public void setUserNeuralNet(NeuralNetwork userNeuralNet) {
        this.userNeuralNet = userNeuralNet;
    }


}
