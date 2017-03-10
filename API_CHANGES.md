#API Changes

>CompSci 308 SLogo Project

**Names**: Dylan Peters, Andreas Santos, Tavo Loaiza, Yilin Gao

##Frontend External API Changes

###Major Changes:

- Created abstract class `SlogoBaseUIManager`. This class was created to unify the front end classes and improve code consistency. It is designed to implement the behavior that is common to all of the classes in the front end of the Slogo API. Specifically, it implements 4 important behaviors:

1. It implements the ObjectManager interface, with the object it manages being a subclass of Parent. There is more information about the ObjectManager interface in the internal API changes section, but essentially implementing this interface enforces consistency in the way that the UI classes set up and return UI objects.
2. Every UI class in the Slogo application is styleable with CSS. Therefore, we abstracted this functionality into the SlogoBaseUIManager class. Importantly, the SlogoBaseUIManager class has a `private static final` field variable that specifies the default stylesheet. This ensures that every class in the frontend hierarchy is instantiated with the correct stylesheet, without them all having duplicated code to set up. Furthermore, it means that we only need to make a change in one place in order to change the default stylesheet.
3. Every UI class in the Slogo application is also able to use ResourceBundles to dispay text to the user in multiple languages. We abstracted this functionality into the SlogoBaseUIManager as well, resulting in reduced duplicated code, better code consistency, and easier changes to code, similar to the benefits described above for the stylesheets.
4. The stylesheet and language variables were put into ObjectProperties (StringProperty in the case of the stylesheet). A getter was provided for the properties so that other classes can get the stylesheet and language and can make changes or listen for changes. This allows classes to bind stylesheets and ensure that they all display in the same style, and similarly for languages. Furthermore, classes can extend the getStylesheet or getLanguage methods and make them return a readonly property to allow other classes to view the variables but not change them.

We believe that the SlogoBaseUIManager offers many benefits including increased code consistency, decreased duplicated code, and an inheritence hierarchy to unify the front end. Furthermore, it solves the problem we had with the delegate interfaces; for example, for a menu bar item to communicate with another class that the user has changed the language, it need only change its own language variable, and any class that is interested in that change can listen for changes to it. This has the added benefit of being able to have an arbitrary number of listeners, rather than the limit of one that was enforced using the delegate interfaces. This is an external change because the SlogoBaseUIManager is a public class in order to allow other code to interact with any public Slogo UI classes through the methods provided in the abstract class.

- Created `public class TabbedSlogoView`. This class creates a way for other classes to easily run the Slogo IDE. This way, client code does not have to set up or understand how the Workspace class or the EditorPaneManager or the SimulationPaneManager works. Instead, the TabbedSlogoView is a single class that creates multiple workspaces and allows the user to interact with them. It uses the `show()`, `hide()`, and `close()` methods that are used with the Stage class, so it is straightforward for client classes to use. The only public methods are the constructors, and the `show()`, `hide()`, and `close()` methods. Therefore, it does not allow significant customization of the IDE, but instead is designed to create a very simple way to instantiate the IDE. If more flexibility is desired, client code can use the Workspace class to set up more customized views.

###Minor Changes:

##Frontend Internal API Changes

###Major Changes:

- Deprecated all delegation interfaces. These interfaces are: `EditorPaneMenuBarDelegate`, `TerminalDisplayDelegate`, `VariableDisplayDelegate`, and `SimulationMenuBarDelegate`. These interfaces were originally made in order to allow UI classes to communicate with each other. However, upon inspection, these interfaces essentially created a pattern similar to Java's Observable interface. Therefore, we chose to abstract this behavior that was common to all the interfaces and instead use ObjectProperties for the purpose of UI communication. Using the original pattern of delegation, a standard use case would look like this:

1. The `EditorPaneManager` contains an `EditorMenuBarManager` to set up and run the menu bar. When the user changes the value of the ComboBox controlling language, the `EditorMenuBarManager` is notified of the change by the ComboBox.
2. The `EditorMenuBarManager` needs to notify the `EditorPaneManager` so that the `EditorPaneManager` can change the language of the other parts of the UI (for example the variable table).
3. The `EditorMenuBarManager` contains a field called delegate that is of type EditorMenuBarDelegate. Within the `EditorMenuBarDelegate` interface, there is a method called didChangeLanguage(ResourceBundle newLanguage). The `EditorMenuBarManager` calls delegate.didChangeLanguage(ResourceBundle newLanguage).
4. The `EditorPaneManager` implements the `EditorMenuBarDelegate` interface, and is set as the EditorMenuBarManager's delegate. Therefore, when the menu bar calls the delegate method, the pane manager executes the code necessary to change language.

This design worked at the beginning, but as the code got more complex, the number of delegates increased, and the code became harder to read. Furthermore, we noticed that this design limits each class to having just a single delegate that it can notify when something changes. This limitation is arbitrary and unnecessary. To fix this limitation, we deprecated all of the delegate interfaces (in our repository we have deleted all of these files for the sake of making the repository organized and easy for the graders to inspect, but if this were a production API, we would simply mark those interfaces as depreicated), and we created the `SlogoBaseUIManager` abstract class, which uses properties that other classes can view in order to listen for changes.



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