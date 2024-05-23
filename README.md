# Payment-System
Cash Register Management System
The Cash Register Management System is an application developed in Java using the Swing GUI framework. This application allows multiple users to access and manage cash registers simultaneously from different devices.

# # Features
**Order Management:**

Adding new orders.
Displaying orders in a table.
Cancelling and voiding orders.
**Calculations:**

Automatic calculation of commission based on the entered amount.
Calculation of the total invoice amount.
**User Sessions:**

Managing sessions for multiple users.
Each user can manage their orders independently of others.
# # Technologies
**Java SE**
**Swing**
**MVC Architecture**
**MySql (DAO pattern)**
# # Project Structure
//Still incomplete.
**common: Contains static methods, interfaces, and similar.**
**model: Contains Java classes representing data (e.g., OrdersModel, UserModel).**
**view: Contains Java Swing GUI panels and components (e.g., CashRegPanel).**
**controller: Contains controllers managing interaction between the model and view (e.g., CashRegPanelController).**
**dao: Contains classes communicating with the database (e.g., DBOrderManipulationDao).**
# # Usage
1.Log in or register as a new user or as an admin.
2.If you choose the admin option, you can view the entered accounts per employee for the day. Have an overview of the list of employees, make changes to it, as well as add new ones or delete existing employees.
3.If you log in as an employee, after logging in, access the Cash Register panel.
4.Enter order details and perform necessary operations such as commission calculation, invoicing, cancelling, or reversing orders.
5.The display of orders in the table allows for the review and management of existing orders.
