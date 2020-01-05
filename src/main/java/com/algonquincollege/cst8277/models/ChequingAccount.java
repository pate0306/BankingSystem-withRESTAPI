/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: ChequingAccount.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
/***********
 * File Name: ChequingAccount.java
 * Upadted By :Parth Patel
 * Updated Date: 2019-12-03
 * 
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The persistent class for the ACCOUNT database table.
 * 
 */
@Entity
@DiscriminatorValue(value = "C")
public class ChequingAccount extends AccountBase implements Serializable {
    private static final long serialVersionUID = 1L;

}