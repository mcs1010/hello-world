/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Services;

/**
 * An exception class for the BCI software that will allow for the developers to
 * write their own exceptions to handle errors
 * 
 * @author Marc S
 */
public class BciException extends Exception{
    public BciException() {};
    
    public BciException(String message) {
        super(message);
    };
    
    public BciException(Throwable cause) {
        super(cause);
    };
    
    public BciException(String message, Throwable cause) {
        super(message, cause);
    }
};