# INF1009 Group 9 Team Project

## Part 1: Game Engine

### Requirements

1. Application should open, initialize, load content, draw content and update (Main class)
2. Input Management - **Farhan**
3. Entity (Player and NPC)/Collision and Detection Managers (Movement, creation, drawing, rendering, destruction of entities. . For example, think of the methods/functions that allow for the entity to be created, rendered, moved, or deleted) - **Afiq*
4. Screen/Scene Manager. (Transitions between scenes, scenes include start screen, instruction screen, leaderboard, game play screen and overlays such as menus, scoreboards, etc) - **Esther**
6. Lifecycle Managers (Process the game, from the START to the END) - **Jacob**
    - Entities should be created, initialised, loaded, drawn, deleted (if required) and updated

### Input Management [[Link to Article]](https://www.gamedev.net/blogs/entry/2250186-designing-a-robust-input-handling-system-for-games/)

We can divide the input system into three layers:

1. Raw input gathering from the OS/etc. **(Not really important)**
2. Input mapping and dispatch to the correct high-level handlers
3. High level handler code 

Input mapping and dispatch to the correct context/high-level handlers:

1. What is context?

- The context defines the inputs that are available for the player at a given time. For instance, a player could be in a menu, in game as a soldier on foot, or in game driving a vehicle. The former examples illustrate the differences of context.

2. Types of inputs for contexts:

- Contexts contain three different types of input:

    1. Actions
    2. States
    3. Ranges
    
- An action is a single-time thing, like casting a spell or opening a door; generally if the player just holds the button down, the action should only happen once, generally when the button is first pressed, or when it is finally released. "Key repeat" should not affect actions.

- States are similar, but designed for continuous activities, like running or shooting. A state is a simple binary flag: either the state is on, or it's off. When the state is active, the corresponding game action is performed; when it is not active, the action is not performed. Simple as that. Other good examples of states include things like scrolling through menus.

- Finally, a range is an input that can have a number value associated with it. For simplicity, we will assume that ranges can have any value; however, it is common to define them in normalized spans, e.g. 0 to 1, or -1 to 1. We'll see more about the specifics of range values later. Ranges are most useful for dealing with analog input, such as joysticks, analog controller thumbsticks, and mice.

3. Input mapping

- Each context has an input map
- Take a type of hardware input (e.g., keyboard, gamepad, touch, etc.) and convert it to a type of input (action/state/range). 

4. Dispatch methods

Two types:

- Callback method (like cpu interrupt)
- Polling method
    
The callback method will call special functions which would handle the input when an input occurs. For the polling method, the code will prompt the input management system each frame for whatever input that is occurring, and handles those inputs accordingly.

#### Input mapping and dispatch (Summary):
The basic design looks like this:

- Every frame, raw input is obtained from the OS/hardware
- The currently active contexts are evaluated, and input mapping is performed
- Once a list of actions, states, and ranges is obtained, we package this up into a special data structure and invoke the appropriate callbacks

    
### What needs to be showcased

- Need to showcase a prototype to demonstrate the game engine technology and
game mechanics
- Proper usage of OOP concepts
- Classes
- Objects 
- Inheritance
- Polymorphism
- Error Handling
- Re-usablilty of code
- Modularity
- Functionalities
- Various features of the game
- UML diagram
  - Identify the main entities to create classes
  - Think how you want to structure and connect these classes
- Code
- Store code in a repository (Git/Github)
- Collaborate with your team-mates
- Use it for the entire project
- Code Structure
- File, class and function naming convention should be followed (they should be self explanatory)

### Things to submit:
- Report
- Code
- Weekly updates on progress???
- Presentation Slides
- Video Demo (Not more than 8 mins)
  - Show innovation and functionalities
    
## Part 2: Actual Game

### Project Theme: Space Exploration

Current Ideas: 
- Fun Fact Game?  Pop up for funfact?
- Trivia? (e.g. how to be a millionaire)
- Badges (Achievements ?)
- Boxhead:	
  - Answer trivia qns for blocks/health etc
  - Top down shooter, but exploratory in nature (e.g., many levels, or “environment/planets” to explore”. 
  - Player can decide on which path/environment to take/visit
  - Player can then defeat the “boss” in each room to get more information on each planet
