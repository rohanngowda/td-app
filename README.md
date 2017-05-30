<h1>Treasure Data Command Line Interface</h1>
Command line tool to issue a query on Treasure Data and query a database and table to retrieve the values of a specified set of columns in a specified date/time range.

<h2>Instruction to Run the Application</h2>

<h3>Maven plugin</h3>

mvn spring-boot:run -Drun.arguments="--td=-t www_access --database sample_datasets -c host -c user -c path -l 10"
mvn spring-boot:run -Drun.arguments="--td=-t www_access --database sample_datasets -c host -c user -c path -m 1412355591 -M 1412355600 -l 10"

<ul>
<li>mvn – Building and running the application
<li>spring-boot – Web application with embedded Tomcat.
<li>-Drun.arguments – sending the command line arguments through CLI.
</ul>

<h3>Running the application as a JAR</h3>
java -jar target/td-app-0.0.1-SNAPSHOT.jar --td="-t www_access -d sample_datasets -c host -c user -c path -c code"
