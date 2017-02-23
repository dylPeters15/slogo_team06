API Review

eol, ncp14

## Part 1 ##

What about your API/design is intended to be flexible?
 -  The interaction between the front end and the backend is inteneded to be very flexible, via the model that connects to the two components together. I hold the necessary methods and data structures required by the front end in back end.
How is your API/design encapsulating your implementation decisions?
 - By having a well definied model class to interface between the front end and the backend, we can encapsulate how we implement our front-end and backend components, keeping the two independent of each other.
What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
 - The most notable exceptions which may occur are bad Slogo commands that can't be parsed. I will handle them by throwing beautiful exceptions to the front-end, so they can be diplayed to the user. 
Why do you think your API/design is good (also define what your measure of good is)?
 - My measure of a good API is a design that is thorough, flexible, and easy to understand. We kept these these guidelines in mind when designing our API, which is why we are using clear and well definied methods, a concrete point of interface, and heavy use of encapsualtion.
.

## Part 2 ##

How do you think Design Patterns are currently represented in the design or could be used to help improve the design?
- Our design represents a Model/Controler design pattern, as well as a hierarchy.
How do you think the "advanced" Java features will help you implement your design?
- Given that we will be implementing many classes for our interpreter, reflection would be a very useful advanced java feature. We will also be utilizing observable lists to notify the frontend about new states.
What feature/design problem are you most excited to work on?
- Parsing, becausing it sounda like a new, fun and thrilling challange to work on.
What feature/design problem are you most worried about working on-
- Parsing, because it even though it may be new and fun, it also seems difficult to implement with the all the commands and looping/nested structers. It is also a critical component of our SLogo project.
Come up with at least five use cases for your part (it is absolutely fine if they are useful for both teams).
- FD 50: Front-end calls Interpreter("FD 50") in model. The Interper than translates and parses the command. Upon completion, it addes a new state with an updated turtle Model position. The Front-end has a listener on the observable list "StatesList", and then automatically displays the new FD;
- User enter blank input: Parser detects blank input, does nothing
- FD FD 50 Same as forward 50, except the parse has to parse the inner command first, return 50, then excute the first "FD". Will be tricky
- User inputs a bad command: Parser can't find the correct parameters. Drops and exception to the GUI, who notifies the user
- PENUP: User inputs a penup command. Interpreter parses the command. Updates the turtle model property to be pen-up. GUI listens and reacts to change in states lists. updates the view.