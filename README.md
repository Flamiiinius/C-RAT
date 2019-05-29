# C-RAT
Master Thesis Project, Server and Desktop Application for Cyber-Risk Management

This Project was developed durign my master thesis in collaboration with my supervisor at SINTEF

Description:
-----------------

As I stated before this application was built to handle cyber-risk management. 
We started to divide the users of C-RAT into 2 groups: Managers and Risk Owners.


Managers are responsible for identifying risk, and perform their analise in the end;

Risk Owners are responsible to assign values to the risks. In this version we ask them to assign likelihood in the form average number of occurences per year; and consequence as the expected harm € of one occurence.

Risks must have a manager, a risk owner, consequence & likelihood, a description of the risks, and an asset who is harmed if the risk occurs.

The Users should be inserted in a tree-like form, we tried to mirror an organizational chart, were the upper you are in the tree, the more risks you have acess to. Risk Owners should be the leafs of the tree, and the other nodes are Managers.

There are 2 main features we implemented: Risk Seggregation and Risk Aggregation:
Risk Aggregation is used to aggregate 2 or more risks into one, while risk segregation is a feature only avaiable to the managers where they can chose to only see risks that belong to parts of their sub-tree.




Compile and run:
--------------------
With the arrival of Java11, the javaFX library was taken out of the core of java in versions 9 and 10.

The Client needs to run either in java 8 or java10+ with the javaFX module. (reccomended java8).
The Server should run in the same java version as the client.

The Server needs a MySql database as shown in our mockup databse. 

Installing:
---
Server:
- Create a Log files and update the path in the main file. (Server.java);
- Create a Datatabase that comply with ours or create your database and make the necessary changes in the code;
- Configure database name and password (DBConnection.java);

Client:
- Change Server IP and Port;

Other Comments
---
The tool can be reutilized to any kind of risk, not only cyber risks.
change the risk and newrisk classes, and replace by your risk of choice.


TODO:
---------------
- Create an admin page;

- Change configurations to an .txt, instead of hard coded;
