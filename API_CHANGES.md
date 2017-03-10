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

1. It implements the ability to change the language. It does this 

###Minor Changes:

##Backend External API Changes

###Major Changes:

###Minor Changes:

##Backend Internal API Changes

###Major Changes:

###Minor Changes: