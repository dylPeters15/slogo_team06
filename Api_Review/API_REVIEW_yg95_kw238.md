## SLogo Team 6

### Part 1

1. The flexible part is a simple communication between frontend and backend, which is only calling one method and returning a list of global states. So that further extensions in the backend and frontend would be separate to each other.

2. The interpretation of the commands is hidden in only one method `interpret(String command)`. And all the global states are encapsulated inside the `State` object.

3. Exceptions are illegal user input commands. The exceptions are handled and caught within the `interpret()` method and this method will return the error to the frontend.

4. The interpretation of the languages are hidden from the frontend. It is good because it has a clear separation of backend and frontend.

### Part 2

1. The observer pattern is used. An observer is set in the frontend to observe the newly generated state objects in the backend. In the `interpret()` method, whenever a new state object is generated, it notifies the observer to take action. 

2. Probably we'll use generic types for interpreting the commands. But we are not sure yet.

3. The most exciting feature in backend can be the `interpret()` method. The method will require a lot of techniques on parsing inputs, dealing with nested and complex commands (like for loops).

4. The most worrying feature is the same as the most exciting feature.

5.  Five use cases:  
  (1). To show returned text from command: the backend team evaluates the commands, and at the end of the last command show its return value by calling `frontEnd.showText(String value)`.  
  (2). The pen shows or not: (team 6) in each state object there is a variable indicating whether the pen shows or not, and the frontend retrieves this information from the object and act accordingly. (team 8) The status of the pen is only remembered in the backend, and the backend calls different frontend methods `moveTo()` or `drawLine()` depends on the status.  
  (3). Execute a command from history by clicking: the history is stored only at the frontend, and the frontend runs the history by passing the command to backend for interpretation.  
  (4). foo...  
  (5). bar...  