# alienvault
Interview Test

This was a fun little exercise. 

So basically the model represents the 3 things I'm working with - Repositories, Issues and the Top Day. I modeled it so the
Repo has a bunch of issues and a top day.

The SimpleIssue implements Comparable so I can sort the Issues based on date in reverse order. Probably the only thing of note in that
class.

The SimpleRepo class does all the figuring out for the Top Day. There I use a LinkedHashMap to store a sorted Map. The map is
sorted based on the count of issues for that day. The highest number of issues reported for any given date is the first
entry in the map.

Top Day is just basically a structure to hold the 3 pieces of information I need - Date, count and repo name.

I broke out the Report into a couple of classes to demonstrate how I would decouple the work and modularize the code. Tried
to show some simple dependency injection without going to the next level to use Google Guice or Spring or something else.

I did some basic Unit testing. Threw in a Mock so you all would know I know how to do that kind of thing. (Even though it is a
pretty basic example).

The only other thing of note is that I threaded the calls out to GitHub to make things a little faster. Each repo argument
creates a separate thread so the calls can be made simultaneously and then collected back before processing the data.

That's the summary. Thank you for the opportunity.

Mark
