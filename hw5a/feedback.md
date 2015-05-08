# Team 28 Feedback
You have created a pretty generic framework but it seems not to be structured like a framework. the goal of a framework is for the framework to call into the different plugins to achieve extensibility--not the other way around. Here you provide the Framework (as a Data processor) and as your comments describe, the functions call the data plugin's methods. Additionally, you may want to think about how the rate limit will affect your multiple queries because in some apis, it will be difficult to provide on demand data as a library.

In an unrelated note, it is probably a good indication of ease-of-use and good naming conventions if your object model doesn't require so many annotations and explainations. It should be obvious how your framework works without the comments.


-Terence Sun (ttsun@andrew.cmu.edu)
