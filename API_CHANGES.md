#API Changes

>CompSci 308 SLogo Project

**Names**: Dylan Peters, Andreas Santos, Tavo Loaiza, Yilin Gao

##Frontend External API Changes

###Major Changes:

###Minor Changes:

##Frontend Internal API Changes

###Major Changes:

- Deprecated all delegation interfaces. These interfaces are: `EditorPaneMenuBarDelegate`, `TerminalDisplayDelegate`, `VariableDisplayDelegate`, and `SimulationMenuBarDelegate`. These interfaces were originally made in order to allow UI classes to communicate with each other. However, upon inspection, these interfaces essentially created a pattern similar to Java's Observable interface. Therefore, we chose to abstract this behavior that was common to all the interfaces and instead use ObjectProperties for the purpose of UI communication. Using the original pattern of delegation, a standard use case would look like this:

1. The `EditorPaneManager` contains an `EditorMenuBarManager` to set up and run the menu bar. When the user changes the value of the ComboBox controlling language, the `EditorMenuBarManager` is notified of the change by the ComboBox.
2. The `EditorMenuBarManager` needs to notify the `EditorPaneManager` so that the `EditorPaneManager` can change the language of the other parts of the UI (for example the variable table).
3. The `EditorMenuBarManager` contains a field called delegate that is of type EditorMenuBarDelegate. Within the `EditorMenuBarDelegate` interface, there is a method called didChangeLanguage(ResourceBundle newLanguage). The `EditorMenuBarManager` calls delegate.didChangeLanguage(ResourceBundle newLanguage).
4. The `EditorPaneManager` implements the `EditorMenuBarDelegate` interface, and is set as the EditorMenuBarManager's delegate. Therefore, when the menu bar calls the delegate method, the pane manager executes the code necessary to change language.

This design worked at the beginning, but as the code got more complex, the number of delegates increased, and the code became harder to read. Furthermore, we noticed that this design limits each class to having just a single delegate that it can notify when something changes. This limitation is arbitrary and unnecessary. To fix this limitation, we deprecated all of the delegate interfaces (in our repository we have deleted all of these files for the sake of making the repository organized and easy for the graders to inspect, but if this were a production API, we would simply mark those interfaces as depreicated) and created the next major change:

- Created abstract class `SlogoBaseUIManager`. This class was created to unify the front end classes and improve code consistency. It is designed to implement the behavior that is common to all of the classes in the front end of the Slogo API. Specifically, it implements 3 important behaviors:

1. behavior 1
2. behavior 2
3. behavior 3

###Minor Changes:

##Backend External API Changes

###Major Changes:
- Restructuring the API of `StatesList` . We orginally had the methods for states list, such as `peek` and `poll`, accessable directly in the model class. However, it made more sense to instead include a getter for `StatesList` itself, named `getStatesList()`. The front-end could then call the appriopate methods from the observable list itself. This improved the overall orginization to make the API more intuitive.
###Minor Changes:
- We added a few extra public methods in our model to handle additional dependencies between the front-end and the backend. `setResourceBundle()` allowed the front end to change which property file the back end utilized, to support interpreting commands in different languages. `getVariables` passed an observable Map of `<String,String>`, containing the slogo defined varaibles. The list could be modified by both the front and backend, allowing manipulation vis the GUI or Slogo commands. The final two methods added were `getPastCommands()` and `setPastCommands()`. The two methods were intended to retrive and set a list of the past commands runs, as stored by the model. This simplifies how we could save workstation text to a file.
##Backend Internal API Changes

###Major Changes:
- We orginally did not define an internal API, but we later did as we found it was extremely helpful for organizing the structure and implementing new features. The main classes of the backend consist of `Interpreter`, which handles all the parsing of text to slogo commands, the abstract `Command` class, which holds the basic structure and common methods of all the implemented slogo commands, the `ActorModel`, which contains all information specific to a Turtle such as it's position, heading, and visiblity state, the `State` class, which holds the `ActorModel`s in map and information relevent to the current state but not a turtle (such the variable map), and the `StateList` class, which implements an ObservableList via a queue to make it easy to add and retrive states from opposite ends of a list.

###Minor Changes:
- To handle multiple turtles without signficanly changing the commands already implemented, we created `ActorCompositeModel`. This class mirrored the methods of `ActorModel`, but was not actually an instance. Instead, any change applied to the `ActorCompositeModel` was applied to all the active turtles. For retriving information specific to an actor, the composite retrived the info from the last active turtle. The class meant we only had to make minimal modifications to our existing command classes to ensure they were functional with multiple turtles, and without having to iterate throw all the active turtles in the command classes themselves. 

- A custom exception class, `SlogoException`. This class held an editable string containing the error message, which could be modified according to the conditions that led to the error and then translated into the appriopate language using the resource bundles.