/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigInteger;

/**
 *
 * @author javiyesares
 */
public class Pareja {

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }
    
    int indice;
    BigInteger valor;
    
    public Pareja (int indice, BigInteger valor){
        this.indice = indice;
        this.valor = new BigInteger(valor.toString());
    }
    
}
