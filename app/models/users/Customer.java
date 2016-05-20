package models.users;

import java.util.*;
import javax.persistence.*;

import models.shopping.*;

@Entity

// This is a Customer of type admin
@DiscriminatorValue("customer")

// Customer inherits from the User class
public class Customer extends User{
	
	public String street1;
	public String street2;
    public String town;
    public String postCode;
    public String creditCard;
    
    // Customer has one basket.
    // Customer is the owner (foreign key will be added to Basket table)
    // All changes to Customer are cascaded.
    @OneToOne(mappedBy="customer", cascade = CascadeType.ALL)
    public Basket basket;

    // Customer can habe many ShopOrders.
    // Customer is the owner (forieng key will be added to Basket table)
    // All changes to Customer are cascaded
    @OneToMany(mappedBy="customer", cascade = CascadeType.ALL)
    public List<ShopOrder> orders;
	
	public Customer(String email, String role, String name, String password, String street1, String street2, String town, String postCode, String creditCard)
	{
		super(email, role, name, password);
        this.street1 = street1;
        this.street2 = street2;
        this.town = town;
        this.postCode = postCode;
		this.creditCard = creditCard;
	}
} 