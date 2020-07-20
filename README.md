# system-construct-project
## To clone from git:
```
git clone https://github.com/nakarinh14/system-construct-project
```

For spring boot, in IntelliJ IDEA import/open the ```backend``` folder and reload the maven dependency

For Window: install ```node.js``` before ```yarn```. Check  https://nodejs.org/en/

For Vue, install ```yarn``` first. Check   https://classic.yarnpkg.com/en/docs/install/

After ```yarn``` is installed, run ```yarn global add @vue/cli```


## To run:

To deploy Vue, just ```cd``` in ```frontend``` and type ```yarn serve```, which would deploy in localhost and display a URL where the website is.

For window before run ```yarn serve``` run ```npm i``` first.


I have asked Aj., he said we will need to configure and deploy Vue frontend in a nginx webserver later, so the ```yarn serve``` is just temporary for development I guess, and we can move on nginx after everything is done

## To work on project seperately:

<b> DON'T WORK ON ```master``` BRANCH ON YOUR PC. </b> To push change, don't ```git push``` on the ```master``` branch on your computer, as it will merge with master branch on Github and may cause file and error conflict for others when they ```git pull``` for latest update for their branch.

Work on a seperate branch on your local:
Create a new seperate branch with ```git checkout -b your-branch-name-here```. This would automatically switch from your branch ```master``` to ```your-branch-name``` as shown from your Terminal/Shell

Make sure your branch is up to date with ```master``` local branch:
1. Git commit all the local change for your local branch, or else changing branch will lose all your uncommited change
2. ```git checkout master```
3. ```git pull```
4. ```git checkout``` back to your local branch
5. ```git merge master``` to merge master into your local branch, which update changes, etc.
6. From there, edit any merge conflict (if any) and then you can push into Github

When you want to push change from ```your-branch-name-here``` to Github, do the usual ```git add . ``` , ```git commit -m "msg"``` in your branch, and when push run ```git push origin your-branch-name-here``` (```origin``` is this Github ```master``` branch)

Afterward, a pull request will appear on Github, so we can go and compare and check if there are conflicting files or not, then we can decide to merge with master branch here.

<a href="https://www.youtube.com/watch?v=MnUd31TvBoU">Source</a>


## Optional: 

On Chrome. I think these 2 extension are quite useful in debugging frontend and backend later on :

<a href="https://chrome.google.com/webstore/detail/vuejs-devtools/nhdogjmejiglipccpnnnanhbledajbpd?hl=en">Vue.js Chrome extension</a>


To develop ```frontend``` I recommend using <a href="https://www.jetbrains.com/webstorm/download/#section=mac"> WebStorm </a> (which is from IntelliJ) or VSCode is fine too

