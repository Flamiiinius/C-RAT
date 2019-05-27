# C-RAT
Master Thesis Project, Server and Desktop Application for Cyber-Risk Management

This Project was developed durign my master thesis in collaboration with my supervisor at SINTEF


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
