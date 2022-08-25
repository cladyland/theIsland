# theIsland
My second project on JavaRush

## Notice for my mentors:
Свой проект я начала с реализации движка, как такового. Классы животных и растений пока не прописаны полностью, так как пишу постепенно и не все сразу (чтобы не приходилось каждый раз редактировать все классы), начиная с тех моментов, которые мне кажутся более сложными и с которыми нужно разбираться.

###На что обратить внимание:
_1) класс FindAppProperties, который отвечает за поиск настроек приложения_

_2) класс GameField, который содержит в себе static inner class_

_3) в классе IslandRandom есть метод createInitialObjects, который используется для заполнения острова при создании. Сейчас метод может создать по одному животному классов Wolf и Fox (тестирую пока только Wolf), позже я добавлю зависимость от максимального количества объектов и создание остальных, а пока для тестов достаточно и по одному :)_

_4) класс IslandGame (основная бизнесс-логика)_

P.S. Джарку пока не создавала, но вот пример вывода программы при заданной ширине и высоте 4:

- для пользователя:
```
🌊 🌊 🌊 🌊 🌊 🌊 
🌊 🌿 🌿 🌿 🌿 🌊 
🌊 🌿 🌿 🌿 🌿 🌊 
🌊 🌿 🌿 🌿 🌿 🌊 
🌊 🌿 🌿 🌿 🌿 🌊 
🌊 🌊 🌊 🌊 🌊 🌊 
```

- для разработчика:
1) до перемещения:
```
true true true true true true 
true {WOLF=[model.predator.Wolf@4f3f5b24]} {WOLF=[model.predator.Wolf@15aeb7ab]} {WOLF=[model.predator.Wolf@7b23ec81]} {WOLF=[model.predator.Wolf@6acbcfc0]} true 
true {WOLF=[model.predator.Wolf@5f184fc6]} {WOLF=[model.predator.Wolf@3feba861]} {WOLF=[model.predator.Wolf@5b480cf9]} {WOLF=[model.predator.Wolf@6f496d9f]} true 
true {WOLF=[model.predator.Wolf@723279cf]} {WOLF=[model.predator.Wolf@10f87f48]} {WOLF=[model.predator.Wolf@b4c966a]} {WOLF=[model.predator.Wolf@2f4d3709]} true 
true {WOLF=[model.predator.Wolf@4e50df2e]} {WOLF=[model.predator.Wolf@1d81eb93]} {WOLF=[model.predator.Wolf@7291c18f]} {WOLF=[model.predator.Wolf@34a245ab]} true 
true true true true true true 
```

2) после:
```
true true true true true true 
true {WOLF=[model.predator.Wolf@4f3f5b24, model.predator.Wolf@15aeb7ab]} {WOLF=[model.predator.Wolf@3feba861]} {WOLF=[model.predator.Wolf@7b23ec81]} {WOLF=[model.predator.Wolf@6acbcfc0]} true 
true {WOLF=[model.predator.Wolf@5f184fc6]} {WOLF=[]} {WOLF=[model.predator.Wolf@5b480cf9]} {WOLF=[model.predator.Wolf@6f496d9f]} true 
true {WOLF=[model.predator.Wolf@723279cf]} {WOLF=[model.predator.Wolf@10f87f48]} {WOLF=[model.predator.Wolf@b4c966a]} {WOLF=[model.predator.Wolf@2f4d3709]} true 
true {WOLF=[model.predator.Wolf@4e50df2e]} {WOLF=[model.predator.Wolf@1d81eb93]} {WOLF=[model.predator.Wolf@7291c18f]} {WOLF=[model.predator.Wolf@34a245ab]} true 
true true true true true true
```
  
P.P.S. Хорошего и спокойного вам дня :)