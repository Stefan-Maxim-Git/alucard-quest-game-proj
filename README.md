# alucard-quest-game-proj
<h2> Description: </h2>
Game project written in Java for the purpose of exercising OOP skills.

The game features 3 level in which you have to defeat a certain number of enemies in order to progress from one stage to another. You will have to face more and stronger enemies as you complete each level.

<h2> Controlls: </h2>
<ul>
  <li>Menu navigation: <br/>
      - W and S keys for navigating up and down respectively; <br/>
      - Enter to select the highlighted option.
  </li>
  <li>
    In-game controlls: <br/>
        - W, A, S, D to move the character up, left, down and right respectively; <br/>
        - K to attack; <br/>
        - P to pause.
  </li>
</ul>

<h2> Implemented features: </h2>
<ul>
  <li> 2 types of enemies: Zombie (has 1 hit-point) and Vampire (has 2 hit-points) </li>
  <li> Hit-point system: Enemies are able to deal damage to the player by collision. The player can hurt enemies by attacking (see Controlls) </li>
  <li> Collision handler for both the player and enemies</li>
  <li> Pause menu using the state design pattern</li>
  <li> Enemy spawning system using the factory design pattern </li>
  <li> 3 levels with different win conditions each </li>
</ul>

<h2> Unfinished/scrapped features: </h2>
Due to lack of sprites and time, the following features were planned but never finished:
<ul>
  <li> Transformation mechanic: <br/>
        - At certain periods of time, a "Moonglow" item will spawn on the map. Collecting 3 Moonglow items would allow the player to transform into a Werewolf state; <br/>
        - The Werewolf state would make the player invincible and allow for damage-dealing through collisions; <br/>
        - The Werewolf state would expire after 10 seconds, emptying the Moon bar (see Gameplay) and reverting the player to their base state.<br/>
  </li>
  <li> Secondary attack: Garlick Bomb: <br/>
        - The ranged, area of effect attack alternative for the player; <br/>
        - A "Garlic" item would have a chance to be dropped everytime the player defeats an enemy; <br/>
        - The amount of Garlic items acquired after finishing a level would translate to the next level. <br/>
  </li>
  <li> Database for managinf save files; </li>
  <li> Fourth level which was intended to be a boss fight; </li>
  <li> Soundtrack and other SFX. </li>
</ul>

<h2>Gameplay: </h2>


https://github.com/Stefan-Maxim-Git/alucard-quest-game-proj/assets/164219916/e57a100d-668e-4811-a35b-106aa96a834f


