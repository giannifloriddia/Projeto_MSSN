# Projeto MSSN

## Overview

This project is an agent-based simulation of the classic Rock-Paper-Scissors game, developed in Java with the Processing library for visualization. The simulation runs on a procedurally generated map where three populations—Rocks, Papers, and Scissors—compete for survival. Agents move, interact, reproduce, and are affected by the terrain until a single victorious population remains.

The simulation starts with a GUI that allows for the configuration of initial parameters, such as the number of agents and the effect of the terrain on agent survival.

## Core Concepts

### Agents: Rock, Paper & Scissors
The simulation features three types of agents, each with unique properties and interactions with the environment:
*   **Rocks**: Strong agents that can thrive in rocky biomes but are slowed by grass. They are invulnerable to obstacles and water.
*   **Papers**: Agile agents that can move over water (with an energy cost) but are instantly eliminated by obstacles. They gain energy from grass.
*   **Scissors**: Balanced agents that gain energy from grass but lose energy in rocky biomes. They are eliminated by obstacles and water.

Each agent is represented as a "Boid" with physics-based movement and behaviors.

### Terrain: Cellular Automata
The game world is a grid-based terrain generated using a Cellular Automaton with a majority rule algorithm (`MajorityCA`). This creates distinct biomes with different properties:
*   **Water**: Slows or damages certain agents.
*   **Obstacles**: Impassable or lethal for some agents.
*   **Grass**: A source of energy for all agents. When consumed, it becomes temporarily fertile and regenerates over time.
*   **Rocks**: A beneficial biome for Rock agents.
*   **Unfertile/Fertile**: Transitional terrain types.

### Agent Behavior: Boids & DNA
Agent movement and decision-making are governed by a Boid physics model.
*   **Behaviors**: Agents exhibit behaviors like `Wander` (random movement) and `AvoidObstacle` to navigate the environment.
*   **DNA**: Each agent has a `DNA` that defines its physical attributes, such as `maxSpeed`, `maxForce`, and vision range. When an agent reproduces, its DNA is passed to its offspring.

### Game Dynamics
*   **Interaction**: When two agents of different types are in close proximity, they interact based on the classic rules: Rock beats Scissors, Scissors beats Paper, and Paper beats Rock. The losing agent is eliminated.
*   **Reproduction**: The winning agent immediately reproduces, creating a new agent of its own type.
*   **Energy**: Agents have an energy level that depletes over time. They can replenish it by consuming `GRASS` patches. If "Terrain Damage" is enabled, certain biomes (like `WATER` or `OBSTACLES`) can drain energy or cause instant death. An agent dies if its energy drops to zero.
*   **Winning Condition**: The simulation ends when only one type of agent remains, or if all agents are eliminated. The winner is then declared.
*   **Logging**: The population count for each agent type is logged over time and exported to `population_log.csv` at the end of the simulation.

## Features
*   Agent-based Rock-Paper-Scissors simulation.
*   Procedurally generated terrain using Cellular Automata.
*   Swing-based GUI for customizing initial simulation parameters.
*   Boid physics model for agent movement and steering behaviors.
*   Energy and survival mechanics influenced by terrain interaction.
*   Dynamic population logging to a CSV file.
*   Real-time visualization powered by the Processing library.

## How to Run

1.  Ensure you have a Java JDK (version 18 or newer) installed.
2.  The project relies on the Processing `core.jar` library, which is included in the repository.
3.  Compile and run the `setup.ProcessingSetup` class. This will launch the settings GUI.
4.  In the GUI, configure the simulation:
    *   **Player1 / Player2**: Choose an agent type (this is cosmetic for the simulation).
    *   **NºObjetos**: Set the initial number of agents for each of the three populations.
    *   **Dano do Terreno**: Check this box to enable energy loss and death from hostile terrain types (like water and obstacles).
5.  Click the **Play** button to close the GUI and start the simulation in a new Processing window.
6.  The simulation runs until one population remains. Population data is then saved to `population_log.csv`.

## Project Structure
```
.
├── java/
│   ├── src/
│   │   ├── ca/          # Cellular Automata classes for terrain generation
│   │   ├── jogo/        # Core game logic, agent classes (Rocks, Papers, Scissors), and population management
│   │   ├── physics/     # Boid physics, behaviors (Wander, Avoid), and DNA
│   │   ├── setup/       # Main application entry point and Processing setup
│   │   └── tools/       # Utility classes (SubPlot, PopulationLogger, etc.)
│   ├── imagens/         # PNG images for agents
│   └── core.jar         # Processing library dependency
├── population_log.csv   # Output file for simulation population data
└── LICENCE              # Project license
```

## License
Copyright (c) 2025 Gianni Floriddia, Pedro Marques. All rights reserved.

This code is proprietary and confidential. Unauthorized copying, modification, distribution, or use of this software, in whole or in part, is strictly prohibited without explicit written permission from the authors. This project was created for academic purposes. Unauthorized submission as coursework by third parties is strictly prohibited.
