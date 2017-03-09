We choose to refactor the command classes because they had the most instances of 
duplicated code. We addressed this my making methods and throwing them in the
abstract command class to that all the child classes that implement this can
access these methods.