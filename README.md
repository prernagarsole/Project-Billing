# individual-project-prernagarsole
individual-project-prernagarsole created by GitHub Classroom

Inventory Application:
  This is an inventory management application which takes an inventory/billing input in csv format and formats the data to check whether each stock item verifies if the total item exceeds on a maximum capacity per category. After successful validation, in case of failure it prints the output of check failed items to output file. It also prints the total amount in the output csv and in console. It checks for card and missing card adds to database. 
Database: HashMap, Project IDE: Eclipse, Project Language: Java


**Design Patterns :**
 
 Following design patterns are used to model the application.
 
 1. Singleton
 2. Chain of Responsibility
 3. Factory
 4. Adapter
 5. Builder

**Singleton Design Pattern**

Rationale for the Singleton Design Pattern:

•	Singleton design pattern is a creational design pattern that is used to restrict the instantiation of a class which ensures that the class should have only one instance, providing global access point to the instance.

•	Implemented this pattern to ensure that only one instance of database is available across the whole application. As the database instance is needed globally across the application such as quantity validation, category wise validation, adding item to the cart, singleton object must be used in order to access the object from anywhere in the application.

•	The design pattern also ensures only single instance of card holder or card collection is available throughout the application.


**Factory Design Pattern**

Rationale for the Factory Design Pattern:

•	Factory design pattern is a creational design pattern that provides an interface for creating objects in a superclass, but it does allow subclasses to alter the type of objects that will be created.

•	The pattern used to handle different types of output file. The program can generate two types of output file 
    a) When order is processed successfully
    b) When there’s an error

We have used this pattern to output when an order is processed. The following files are the part of this design implementation :
    Interface OutputFile
    Class CheckoutFile
    Class ErrorFile

•	In the implementation the OutputWriterFactory shows the invalid orders and also the correct billing amount.

![image](https://user-images.githubusercontent.com/99928364/164774903-28218482-e9e7-4630-b389-d8ae304bcfae.png)



**Chain of Responsibility Design Pattern**

Rationale for Chain of Responsibility Design Pattern

•	It is a behavioral design pattern that lets you pass requests along a chain of handlers. Once receiving a request ,each handler decides either it will process the request or pass the request to the other handler in the chain.

•	This design pattern identifies the category of item. As any item in the input list can belong to any categories such as "Essential", "Luxury", "Misc" then the item can be passed to each category handler sequentially to identify whether the item belongs to that category or not. If the item does not belong to that category, it would be passed to next category handler and so on.

•	Used the pattern to prevent code bloating. As the number of item categories grow in future, changing one check for a category can sometimes affect the others. Such a system will become more and more hard to understand and to debug, as the item categories grow.



**Adapter Design Pattern**

Rationale for Adapter Design Pattern:

•	Adapter is a structural design pattern that allows objects with incompatible interfaces to collaborate.

•	This design pattern used to match between two incompatible objects.

•	In the implementation this design pattern is used here to convert List<List> to List<String[]> for printing the output to the txt file.

  •	Classes used for Adapter design pattern implementation
  o	InvoiceAdapter	
  o	CardRepositoryAdapter
  o	InventoryAdapter
  o	InvoiceAdapterInitial

  
  
**Builder Design Pattern**  

  Rationale for Builder Design Pattern:
 
Builder is a creational design pattern which allows you to construct complex objects step by step. Using the same code this pattern allows to produce different representation and types of object.

In the implementation the builder pattern is used 
  
•	To build the item inventory:
  
      Building an item inventory can be a complex task which can be broken down into CardRepositoryBuilder:
      Reading the data from the input data file(getOrCreateInstance, loadDataFromCSV)
      Creating objects of the Item class for each item.( addItem,getItem)
      The finally building the item inventory.( outputWriter)
•	To build the card Inventory ; 
      o	In the implementation CardRepositoryBuilder
•	CSV File reader : 	
      o	CSVFileReaderBuilder used in the implementation


  
**Class Diagram**
  
  ![image](https://user-images.githubusercontent.com/99928364/164775915-99af569e-5423-41c2-8861-2ced56700924.png)
  
 
  
  **Run from Eclipse:**

  1.	Git clone the repository by going to Window-> Show view-> Other -> Git -> Git Repositories -> Clone a Git Repository -> choose the working directory in local.

  2.	Place all the dataset, credit card and inventory input csv files in the project root folder.
![image](https://user-images.githubusercontent.com/99928364/164775992-415f3059-3024-4169-b783-e7c0215d4fda.png)
  
  3.	To run the project ->Run new configuration (green icon on top) shown below.
  ![image](https://user-images.githubusercontent.com/99928364/164776043-ad13811c-7898-4712-9960-c7bdd3f98aae.png)
  
  4.	Path to output files is in root directory
  ![image](https://user-images.githubusercontent.com/99928364/164776078-b03f04a4-3eb3-4377-9df7-4b9b167c1ac9.png)
  
  
  
  
  **Run from Powershell**
  
  
  1. Open powershell and navigate to the path
  2. Javac *.java
  3. Java Billing
  
  (Make sure to keep all input files in same folder)
  
  
  
  **Screenshots**
  
  
  
  Output through Powershell
  
  
  ![image](https://user-images.githubusercontent.com/99928364/166557818-167b8ca6-653c-45b8-97d0-5d0a0596f7fe.png)
![image](https://user-images.githubusercontent.com/99928364/166557844-29421cd1-744f-459f-8aa5-3a9d58ee4734.png)
![image](https://user-images.githubusercontent.com/99928364/166557870-b4d0fb71-c181-450a-b319-fdccd818c51d.png)
![image](https://user-images.githubusercontent.com/99928364/166557886-96e16dd2-71da-482b-9018-0344e615539e.png)


  
  **Error file**
  
  ![image](https://user-images.githubusercontent.com/99928364/166573681-c228a047-1b3f-4ccd-b300-56325264e01d.png)



**Screenshots**
  
  
•Input1.csv : Calculated prices for the cart

  
** Output Through Eclipse:**
  
  ![image](https://user-images.githubusercontent.com/99928364/164776217-d4e99dbe-8050-40ad-a977-804c77d11d28.png)

  ![image](https://user-images.githubusercontent.com/99928364/164776256-35d57e92-2ad3-4a7f-a1b4-c79fa11d2dec.png)

  Input2.csv : Quantity capacity(shows quantity error)
  
  ![image](https://user-images.githubusercontent.com/99928364/164776301-003ae737-b36d-42e2-9494-455f010e0108.png)

  ![image](https://user-images.githubusercontent.com/99928364/164776318-d69b34e6-9564-4ae4-8e3b-af9a90ec3424.png)

  Input3.csv -Item not found
  ![image](https://user-images.githubusercontent.com/99928364/164776348-004a1b00-73ed-4a2a-a7d4-903f5d49935c.png)

  
  
**Excel Output-csv**
  
![image](https://user-images.githubusercontent.com/99928364/164776377-e0d88e58-977b-4627-b5d8-b7d585e3ac35.png)

  ![image](https://user-images.githubusercontent.com/99928364/164776393-7e7908e7-f457-4815-84f4-0720d009df55.png)
  
![image](https://user-images.githubusercontent.com/99928364/164776394-703528b6-c4e5-483c-a946-176c67802bd6.png)

  ![image](https://user-images.githubusercontent.com/99928364/164776438-62d8ed37-1c61-41a8-91ce-604f163d93fe.png)

  ![image](https://user-images.githubusercontent.com/99928364/164776411-77a1761d-4a88-43ed-8282-a77ec3eab471.png)

