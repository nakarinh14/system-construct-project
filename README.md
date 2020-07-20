# system-construct-project
## To clone from git:
```
git clone https://github.com/nakarinh14/system-construct-project
```

For spring boot, in IntelliJ IDEA import/open the ```backend``` folder and reload the maven dependency

For Vue, install ```yarn``` first. Check   https://classic.yarnpkg.com/en/docs/install/

After ```yarn``` is installed, run ```yarn global add @vue/cli```

## To run:

To deploy Vue, just ```cd``` in ```frontend``` and type ```yarn serve```, which would deploy in localhost and display a URL where the website is.


I have asked Aj., he said we will need to configure and deploy Vue frontend in a nginx webserver later, so the ```yarn serve``` is just temporary for development I guess, and we can move on nginx after everything is done

## To work on project seperately:

<b> DON'T WORK ON ```master``` BRANCH ON YOUR PC. </b> To push change, don't ```git push``` on the ```master``` branch on your computer (indicate from your Terminal/Shell when ```cd``` in folder), as when you ```git push``` from there, it will merge with master branch on Github and may cause file and error conflict for others when they also ```git pull``` for latest update.

Work on a seperate branch on your local:
Create a new seperate branch with ```git checkout -b your-branch-name-here```. This would automatically switch from your branch ```master``` to ```your-branch-name``` as shown from your Terminal/Shell

When you want to push change from ```your-branch-name-here``` to github, do the usual ```git add . ``` , ```git commit -m "msg"``` in your branch, and when push run ```git push origin your-branch-name-here``` (```origin``` is this Github ```master``` branch)

Afterward, a pull request will appear on Github, so we can go and compare and check if there are conflicting files or not, then we can decide to merge with master branch here.

<a href="https://www.youtube.com/watch?v=MnUd31TvBoU">Source</a>

## To update changes and files from other people push:

```git pull origin your-branch-name-here```

<a href="https://www.youtube.com/watch?v=MnUd31TvBoU">Source</a>




## Optional: 

On Chrome. I think these 2 extension are quite useful in debugging frontend and backend later on :

<a href="https://chrome.google.com/webstore/detail/vuejs-devtools/nhdogjmejiglipccpnnnanhbledajbpd?hl=en">Vue.js Chrome extension</a>,  <a href="https://chrome.google.com/webstore/detail/json-formatter/bcjindcccaagfpapjjmafapmmgkkhgoa?hl=en">JSON formatter (to read JSON easily on web)</a>


To develop ```frontend``` I recommend using <a href="https://www.jetbrains.com/webstorm/download/#section=mac"> WebStorm </a> (which is from IntelliJ) or VSCode is fine too

