# system-construct-project
To clone from git:
```
git clone https://github.com/nakarinh14/system-construct-project
```

For spring boot, in IntelliJ IDEA import/open the ```backend``` folder and reload the maven dependency

For Vue, install ```yarn``` first. Check

https://classic.yarnpkg.com/en/docs/install/

After ```yarn``` is installed, run ```yarn global add @vue/cli```

<b> To run: </b>

Both Vue and Springboot will occupy same port (localhost:8080) by default, I haven't configure it yet, so run ```SsoApplication.java``` first in IntelliJ IDEA to start backend server so it occupy 8080. And Vue might occupy 8081 (in Mac for me)

To deploy Vue, just cd in ```frontend``` and type ```yarn serve```, which would deploy in localhost and display a URL where the website is.


I only return a JSON in the backend REST controller ```CourseListController.java```for URL ```localhost:8080/api/courses```, which is displaying on the Home page for Vue. 



