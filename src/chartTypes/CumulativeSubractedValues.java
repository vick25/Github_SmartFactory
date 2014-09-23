/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chartTypes;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Victor Kadiata
 */
public interface CumulativeSubractedValues {

   public void getSubtractedValues(byte x, ArrayList<String> alValues) throws SQLException;
}
