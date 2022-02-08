Description :

	A Java Desktop Application in which we can create projects, add members to the project, create phases, create tasks, assign tasks to people and track their progress.

Background Image : 

	Background Image can be changed by specifying the absolute path of the image ( without quotes ) in 'path.txt' file in '03 - Background Image' directory.
	
	Use double slash in the path if you find difficulties in displaying the image E.g : E:\\SoftwareProjectManagementSystem\\03 - Background Image\\bg.png

External Jars : 
	
	Copy all the files in the '02 - External Jars directory' and place them in 'C:\Program Files\Java\jre\lib\ext' 
	
	This location may vary based on the location of Java in your machine but regardless the location place them inside Java\jre\lib\ext
	
Database : 
	
	MySQL, hibernate is used.
		
	In hibernate.cfg, give your respective username and its password in the following places : 
			
		<property name="connection.username">YOUR MYSQL USERNAME</property>
        	<property name="connection.password">YOUR MYSQL PASSWORD</property>

