# EACM Main development component

This is the main development component of EACM

# How to use this repository
- Clone this repo: 
  >git clone git@github.ibm.com:offering-information-management/EACM-main-dev.git

  Optional in case your network is slow
  >git clone --depth=1 git@github.ibm.com:offering-information-management/EACM-main-dev.git
 
  >git fetch --unshallow
 
  Note: Don't recommand to use RSA/eclipse to load the project from github directly since it is large.

- Load the folder that you clone to as workspace and open local projects in workspace

# Tips
- Don't update the local workspace/projects file to this repo. we need to do the below after you setup your local env.
  - use this command to avoid overwrite the same files in this repo
    >git update-index --assume-unchanged PATH (PATH is the file we need to ignore)
  - For untracked file, you should add to your local .gitignore file to avoid those files were uploaded.

# How to Commit your changes
Please add EACM-xxxx to mark the Jira story/defect id, for example:

>git commit -m "EACM-7238 add build.xml"
test!
