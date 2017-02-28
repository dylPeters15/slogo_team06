Andreas Santos- ajs118

Jack Bloomfeld- jfb25

Andreas Santos SLOGO Team 6:

Part 1:

* Flexibility:

    * One part of our front end design that promotes flexibility is that our front-end view is split up into multiple windows, which helps divide functionality and responsibility between the parts of the view. For example, the ‘terminal’ window, the simulation view, and the help pane are all separate classes/windows so that if any of them needs to be changed/altered, it can easily be done without affecting the other classes.

        * Having multiple windows also helps out with other functionality in our design, such as being able to control multiple simulation windows with only one terminal window

* Encapsulation:

    * Once again, the decision to separate out our front end view windows makes it easier to have separate classes/interfaces for each view, and by nature, encourages encapsulation, since each class can implement its own functionality (e.g. the private methods) however it chooses without affecting other view windows and classes.

    * Another way we’ve encouraged encapsulation, is that we’ve made effort to make all methods, classes, and interfaces as closed as possible. This means that while public interfaces have public methods, the ‘internal’ front-end API contains classes/interfaces and methods with no modifiers (so that they are package level viewable only). In this way, different classes should only be able to implement/see methods that they are supposed to use

* Exceptions/Handling:

    * In theory, since my job is to run the view of the simulation, by the time information gets to me, since it is coming from the model, there shouldn’t be any errors in the data I’m accessing.

    * That being said, there could potentially be bad information passed to me from the turtle model, in which case I’d need to throw an exception either notifying the user of the model’s error, or trying to interpret the error and fix it on the fly.

* I think my API is good because it addresses many of the points and issues raised in the topics above, it is flexible and fairly well encapsulated, (and it shouldn’t really have to handle any errors). Overall, my API doesn’t give anyone unnecessary access to code, while still being readable/usable.

Part 2:

* Design Patterns:

    * Some patterns that our design implements (or could benefit from implementing) are the iterator and mediator patterns. These will be helpful to implement when having our frontend and backend interact. Especially since our current idea is to have the backend push turtle model states onto a queue, and for the front end to pop those states off and use them to construct the view.

* "Advanced" java features:

    * I think our design might benefit from the use of generalization to help connect elements of the frontend and the backend instead of having a large mediator. I’m not 100% sure what the best answer is.

* Excited to work on:

    * I’m most excited to work on the set-up/view of the simulation

* Worries:

    * I’m worried about how I will best interact with the backend/and how to make sure I have all the necessary methods in my API.

Jack Bloomfeld SLOGO Team 1:

Part 1:

* My front-end external API currently contains a lot of methods for the Turtle class that currently exists in the front end. The methods are flexible because they can apply to any Turtle object that extends the Turtle class and allow them to move, rotate, or any other valid command. A list of every Turtle in the simulation exists and can be modified by the backend when necessary, and the Turtle methods will function identically on the Display regardless of the parameters of the list of Turtles. This allows a lot of flexibility with Turtle types and numbers. 

* The current design which contains the Turtle class is the front end with a lot of public methods that allow the backend to change the location and other parameters is not well encapsulated. We will discuss moving Turtles to the backend so that the methods can be more protected and can be in the backend internal api, and that will improve the encapsulation. 

* My main task is the console which is the main source of errors in the project. If an incorrect command is inputted, we will have a very extensive and specific Parser to interpret the command and then a detailed error popup will be thrown by the front-end.

* In my opinion a good API is as small, flexible, and encapsulated as possible. Our frontend API allows for a lot of customization of Turtles while still functioning properly, and it keeps all concrete graphical components secure and un-editable. Our API could be improved by moving the Turtle class to the backend to both decrease the size of the external API, and increase the encapsulation because the Turtle methods can be more protected in that case. 

Part 2:

* Our design definitely incorporates the "Command" behavioral design pattern by transferring an input from the console into a Command object before passing it to the backend. We implement the “Factory Method” creational design pattern as well when creating our animation in the Frontend that runs the TimeLine. In order to improve our design we could implement a “Mediator” design pattern that could make it a lot easier and more encapsulated for the interaction between Turtles and the backend. 

* The "advanced" feature of generality would improve my implementation by eliminating the risk of error by adding an incorrect kind of object to my turtle queue, or adding other incorrect data to any data structures.

* I am excited to work on the implementation of the console graphically. It will be a rewarding challenge to code a complete text input box that supports the enter key, and the arrow keys to navigate the text.

* I am scared to face the implementation of the Turtles compatibility with the backend commands. I think it will be difficult to have our Turtle objects behave properly while maintaining an encapsulated design.

Use cases for both projects:

* A command is typed into the console and the enter key is pressed

    * The onKeyPressed method checks that the keyCode is enter, and calls the dropTextInputLine method so that the user can continue typing in the command on the next line in the console

* A command is typed into the console and the Execute button is pressed

    * The command in the console is stored as a String, and then a new Command object is created with the String as a parameter in the constructor. The Command object is then sent to the Parser in the backend.

* A new turtle model state is pushed onto the Queue (or depending on implementation, sent back to the front end)

    * The frontend simulation can pop the queue (or otherwise access the information). Based on this info, the frontend can construct the necessary graphics of the simulation.

* The user clicks on a command in the command history box that stores commands that have been previously executed.

    * A new command is created with the parameter called from the CommandHistory class getPreviousCommand method. 

