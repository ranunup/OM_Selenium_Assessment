# OM_Selenium_Assessment

To execute the project kindly follow below steps:

Download and install <a href="https://www.docker.com/products/docker-desktop">Docker</a>.

Verify your docker version by issuing command : docker --version, mine is : Docker version 19.03.5, build 633a0ea

Pull the following docker selenium images:
		selenium/hub
		selenium/node-chrome-debug (selenium/node-chrome works too, '-debug' version allows for viewing with vnc server)
		selenium/node-firefox-debug
To achieve this execute : docker pull <image_name> from your command line.

Once these have been successfully pulled navigate to the root of this project and run the docker-compose.yml file
in detached mode using command: docker-compose up -d, this starts up selenium/hub node and attaches both the chrome
and firefox nodes to the hub node.

To execute test scripts, kindly import this project to eclipse,
I'm using Eclipse IDE for Java Developers version: 2019-12 (4.14.0) Build id: 20191212-1212.

Ensure that you have the TestNG plugin installed, if not then:
  Navigate to : Help > Install New Software and click on Add
              Insert: TesNG in the name field
              Insert http://beust.com/eclipse/ in the Location field and click Add
              Select the checkbox next to the the TestNG library and click Next,
              accept the license agreement cick and on Finish.
              Wait until the plugin has finished installing and accept if the IDE requires a restart.
              
Add libraries from the jars.zip folder by right clicking on the Project > Properties > Java Build Path > Libraries
Also note that I'm using JRE 1.8 for this project.

Once all these steps have been successfully executed, run the project as TestNG Test or directly run the script located in
src > test.scripts > PersonalLoanCalculator.java as TestNG Test.

The console window should indicate the current test step,
to view test evidence refresh the project by right clicking on it and selecting Refresh then view the reports folder,
this should consist of screenshots from the previous execution as well as an html test report.

To change the browser you're executing against navigate to : src > resources > config.properties
and change the webDriver variable, the only options catered for are chrome and firefox.

All the best :-).


              

