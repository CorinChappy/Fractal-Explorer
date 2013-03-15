(Java 7)

The code is (hopefully) mostly self-explanatory.

I tried to go for a very modular approach. Theoretically you just need to write a class that extends Fractal: containing a call to a static method in ‘ControlPanel’ and defining an abstract method ‘calculateFractal’. Then then the program should incorporate it automatically (emphasis on should).

I had trouble with the zooming using the mousewheel. It works, however not very nicely and it’s the only way to zoom out.

If I had more time I would have allowed dragging and the arrow keys to move around. I would have also made it possible to actually save Juila’s when in dynamic mode!

Thanks!