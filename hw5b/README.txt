Directions for data plugin implementation:
1. write the getName method, return the name of the data source 
2. write extractPostsByName(). know how to get posts information for a specific user ID. Read the Post class to know details of formatting posts informations.
3. write extractFriendsByName(). know how to get connectors’ names in the API.
4. if some information is not available in the source API, set it as null. refer to
data plugin interface for more information.

Directions for analysis plugin implementation:
1. write the getName method, return the name of the analysis method.
2. write analyze(UserInfo userInfo) method. framework will call this method and parse in an instance of UserInfo. UserInfo provide a bunch of preprocessed data for you. you can read the documents of UserInfo interface for more details. You should check, some of the basic information including time, location may be null. in this case, you should give up the analysis and show a message in your result panel indicating that a certain kind of data is not available in the source API.

How to use the framework:
1. type in username and max posts number.
2. choose a data plugin and click the relevant button
3. wait for data fetching
4. click an analysis plugin to see the result
5. if the error message panel says invalid output or rate limit. You can differentiate between these two situations by confirming if the user is valid. if the user is valid, then it must be rate limit issues.
6. our Facebook API token is easy to outdated. Please use your own access token if necessary.
