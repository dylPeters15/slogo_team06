Tavo Loaiza, Dylan Peter's, Yilin Gao, Andreas Santos

## Internal Simulation ##
abstract class Cell:
>public State getNextState: necessary for internal calculations of simulations
>public void updateCell: allows the other parts of the back end to tell the cell to update
>public void updateBlock: allows the other parts of the back end to update the block's pointers to a new cell
>public void moveCell: allows the back end to move cells if the algorithm requires it
>public void cellEmptyRules: gets the rules for empty cells to help calculate the grid's next state
>public void rules: returns a collection of Blocks to calculate the next state of the grid
>The above methods can all be changed to protected or private because they do not need to be accessed by client code, but are rather only used internally in the algorithm to calculate or advance to the next state.

abstract class State:
>public String getName()
>public abstract boolean isEmpty()
>public boolean equals(State otherState)
>public String toString()

## External Simulation ##

abstract class Cell:
>public State getState: necessary for external API to allow
>public Block getBlock: allows view classes to get the Block it needs to display
>public void cycleState: allows an external client class to tell the simulation to advance to the next cycle
>public void getStates: tells the view what the possible states the cells can have, so it knows what it needs to be able to display


## Internal Configuration ##
>public FileData(Map<String, String> dataValues) 
>public String getSimulationType() 
>public int getParam1() 
>public int getParam2() 
>public String getTitle() 
>public String getAuthor() 
>public int getGridRows() 
>public int getGridColumns() 
>public String getGridShape() 
>public String getEdgeType() 
>public String getGridOutlined() 
>public String getCellColor() 
>public String getCellShape() 
>public int getSpeed() 
>public Collection<Integer> getInitialState() 
>public String toString()
> The rest of Configuration's public methods (above) can be considered internal because
> all the necessary info is passed through the readfile method.

## External Configuration ##
> public Control readFile(State primaryStage): passes necessary data to Visualization and Simulation

## Internal Visualization ##
>public int getRows() 
>public int getColumns() 
>public String getType() 
>public static void addButtonsTo(Collection<GUIButton> buttons, GridPane gridPane) 
>public Button getButton() 

## External Visualization ##
>public void startSimulation() 
>public void setStage(Stage stageIn) 
>public Animation(Control obj)
>public GridPane getVisualGrid() 
>public int getParam1() 
>public int getParam2() 
>public Color getColor()