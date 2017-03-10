# SLogo Team 06

>CompSci 308 SLogo Project

**Name**: Dylan Peters, Andreas Santos, Tavo Loaiza, Yilin Gao

**Date started**: 16 February 2017

**Date finished**: 10 March 2017

**Hours worked**: 200 hours (Total hours of group)

**Resources used**: StackOverflow, The Java™ Tutorials, Piazza

**Main Class File**: `Main.java` in the default package

**Data/Resource files**: turtle pictures in the `images` folder, resource bundle files for different languages, css files for GUI layouts.

**Test files**: there are no additional testing files. The program can be tested by running it.

**Responsibilities**:

- Dylan Peters: Refactored structure of front end classes by creating interface `ObjectManager` and abstract class `SlogoBaseUIManager` in the `frontend` package. Created `SlogoController` and `Workspace` in the `controller` package. Implemented all classes in the `frontend.editor` package and helped refactor and add features to the `frontend.help` package.

- Andreas Santos

- Tavo Loaiza: implement `Interpreter`, `ProgramParser`, `Model`, `StatesList`, `State`, `ActorModel`, `ActorCompositeModel`, `TurtleModel`, part of `Command` sub classes, refactor code, and debug.

- Yilin Gao: implement `Interpreter`, `State`, `ColorList`, `ShapeList`, part of `Commands` sub classes, refactor code, and debug.

**Functionality Issues**: Currently we have implemented all requirements in Sprint 2, part of basic extension requirements in Sprint 3, and they can work without errors. There is no currently known bugs.

**Impressions of the Assignment**: Our team made a detailed and thorough plan at first, and was able to stick to it during the development process. In this way, the current project is not far away from initial design. As for design details, we are able to realize the "model-view-controller" ideology to strictly separate the front end and back end and achieve communication through JavaFX collections like observableList and observableMap. We leave a limited number of public methods and try to encapsulate implementation details as much as possible. The functions we haven't implemented are complex and require a lot of revisions from the current program. But we are willing to delve into them in the future. Overall, we regard our SLogo program has achieved its functions and designs as what we expect.