#### theIsland
My second project on JavaRush

### Notice:
_This project is still under development, but you can already see the results of the program._

Available functionality:
- animals can move;
- animals can eat (hunt, if it's a predator or herbivore that eats other herbivores);
- animals can reproduce.

After running the code, the array of the island will be displayed on the screen, as well as general statistics of changes in the number of animals and plants. For example:
```
🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 
🌊 🐺 🐛 🐁 🐻 🦆 🐐 🌿 🌿 🐗 🐑 🌿 🌿 🦊 🦅 🐑 🐗 🦊 🐺 🐻 🦆 🐻 🐑 🐗 🐍 🦊 🐑 🦌 🌿 🌿 🌿 🌊 
🌊 🐻 🐐 🐛 🐺 🦊 🦅 🦆 🐎 🐻 🐐 🦆 🐗 🐎 🐍 🐎 🐺 🐃 🐃 🦊 🐃 🐃 🐍 🌿 🦌 🐎 🦌 🐗 🐗 🦌 🐛 🌊 
🌊 🐍 🐻 🦆 🐁 🐗 🐍 🐇 🌿 🐇 🐃 🐐 🐑 🌿 🦊 🦆 🐍 🐇 🦅 🌿 🐇 🦆 🐑 🐁 🐃 🐇 🐃 🐃 🐺 🐁 🐑 🌊 
🌊 🦌 🐐 🐑 🐐 🐛 🐍 🐁 🦌 🐎 🐃 🐺 🦌 🐃 🐛 🌿 🦊 🐎 🐎 🐺 🐑 🐇 🐁 🦊 🦅 🐻 🐇 🐁 🦆 🐃 🐃 🌊 
🌊 🦌 🐺 🐁 🐎 🐎 🐻 🦌 🦅 🦅 🐗 🦌 🐺 🦆 🐐 🐗 🦊 🐐 🦊 🦌 🐇 🦊 🐎 🐛 🐐 🦅 🐐 🦊 🐻 🦊 🦆 🌊 
🌊 🐑 🦆 🐎 🦆 🐛 🌿 🐛 🐛 🦆 🐐 🦅 🌿 🐁 🐗 🐎 🐎 🦅 🐁 🐻 🐁 🐗 🐁 🐃 🐺 🐛 🐃 🐻 🐺 🐻 🐍 🌊 
🌊 🦌 🦅 🐑 🐗 🐻 🦆 🦊 🦆 🦌 🐃 🦆 🌿 🐛 🐑 🐁 🐁 🐛 🐛 🐛 🐃 🐁 🐺 🦌 🦅 🦌 🦅 🐇 🌿 🐺 🐎 🌊 
🌊 🐛 🐻 🦆 🐇 🦌 🐁 🐃 🦌 🌿 🦆 🦆 🦌 🌿 🐛 🐑 🐗 🦌 🦌 🦆 🌿 🐍 🐐 🦅 🦅 🐻 🦌 🐻 🐺 🐑 🐁 🌊 
🌊 🦌 🦆 🐇 🦌 🐃 🐑 🐻 🐐 🐗 🐁 🐎 🦊 🐗 🐺 🐇 🐎 🐁 🦊 🐗 🦊 🐎 🐻 🐺 🐇 🐛 🐃 🐎 🐁 🦌 🐃 🌊 
🌊 🐑 🐐 🌿 🦅 🐐 🐻 🦊 🦌 🐛 🐇 🐁 🐑 🐗 🦊 🐃 🌿 🐺 🦆 🐺 🐎 🌿 🐻 🐍 🦆 🦌 🐛 🐻 🐑 🐛 🦆 🌊 
🌊 🐑 🐻 🐃 🐗 🐗 🦌 🦊 🦆 🦅 🐻 🦌 🐃 🐺 🐻 🐗 🐍 🐍 🐛 🐃 🐗 🦅 🐑 🌿 🐻 🐍 🐇 🐑 🐛 🦊 🐇 🌊 
🌊 🦌 🐍 🐃 🐛 🦅 🐻 🐍 🐍 🐃 🐎 🐐 🐁 🐇 🐁 🦌 🐺 🐎 🐍 🌿 🐛 🦅 🦆 🐐 🐻 🐗 🦆 🦌 🐇 🦆 🐍 🌊 
🌊 🐇 🐇 🦆 🦌 🐃 🐇 🦊 🐺 🐐 🦅 🦅 🦌 🐎 🐇 🐐 🐺 🐁 🌿 🐛 🐁 🦅 🐛 🐎 🐁 🐇 🌿 🦊 🐗 🐗 🐗 🌊 
🌊 🦌 🐻 🐇 🐑 🦅 🌿 🐻 🌿 🦅 🐛 🐗 🌿 🌿 🐁 🐑 🦅 🌿 🐁 🐁 🐐 🦊 🐺 🐗 🦆 🐍 🐎 🌿 🦌 🐑 🦌 🌊 
🌊 🐗 🦆 🐐 🐐 🐐 🐁 🐇 🐁 🌿 🐻 🦆 🦌 🦅 🐛 🐗 🐛 🐍 🐛 🐑 🐃 🐎 🐎 🌿 🐑 🐗 🦆 🐇 🌿 🐇 🐃 🌊 
🌊 🐐 🐑 🐺 🦆 🦅 🐗 🐎 🦅 🐁 🐃 🐺 🐑 🐎 🦊 🦌 🐑 🐎 🐑 🐃 🌿 🐗 🐗 🐍 🐐 🐑 🐍 🐇 🦌 🦆 🐐 🌊 
🌊 🐃 🐑 🐻 🐺 🦊 🐻 🐗 🐇 🦆 🐇 🐎 🐑 🦌 🦌 🐎 🐗 🐺 🐑 🐛 🦊 🌿 🦅 🦌 🐍 🐺 🐍 🐃 🐇 🐇 🐃 🌊 
🌊 🐑 🌿 🐇 🐛 🐑 🐃 🐎 🐇 🐻 🦅 🐗 🐑 🦆 🐑 🐐 🐇 🦊 🐺 🐎 🐺 🦊 🐍 🐐 🐗 🐁 🦆 🐎 🐻 🦊 🐇 🌊 
🌊 🐍 🐃 🐁 🐐 🌿 🐐 🐑 🐑 🐇 🐇 🐗 🐇 🐁 🐻 🦌 🦌 🐻 🐻 🐑 🦌 🐇 🌿 🐃 🐁 🐻 🦆 🐍 🐃 🐃 🐍 🌊 
🌊 🐑 🦌 🐑 🐇 🐑 🦌 🐗 🐗 🦆 🐇 🐐 🐍 🐗 🦊 🦆 🦆 🦆 🦅 🐻 🐍 🦆 🐃 🐻 🐐 🐛 🐎 🐺 🐻 🐃 🦅 🌊 
🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 🌊 
```

    Day starts:
    animals count = 345013
    plants count = 120000
    ===============
    The animals ate:
    animals count = 221515
    plants count = 39195
    ===============
    The animals reproduced:
    animals count = 696924
    plants count = 39195
    ===============

_P.S. The reason the jar file is missing is because the correct path to the property files cannot be obtained..._ 
