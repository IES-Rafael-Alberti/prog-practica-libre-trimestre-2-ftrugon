[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/NBVXLiSy)
# Actividad: Desarrollo de Proyecto Software en Kotlin

**ID actividad:** 2324_PRO_u4u5u6_libre

**Agrupamiento de la actividad**: Individual 

---

### Descripción:

La actividad consiste en el desarrollo de un proyecto software en Kotlin, permitiendo al estudiante elegir la temática. Este proyecto debe demostrar la comprensión y aplicación de los conceptos de programación orientada a objetos (POO), incluyendo la definición y uso de clases, herencia, interfaces, genericos, principios SOLID y el uso de librerías externas.

**Objetivo:**

- Demostrar comprensión de los fundamentos de POO mediante la instanciación y uso de objetos.
- Aplicar conceptos avanzados de POO como herencia, clases abstractas, e interfaces.
- Crear y usar clases que hagan uso de genéricos. 
- Aplicar principios SOLID.
- Integrar y utilizar librerías de clases externas para extender la funcionalidad del proyecto.
- Documentar y presentar el código de manera clara y comprensible.

**Trabajo a realizar:**

1. **Selección de la Temática:** Elige un tema de tu interés que pueda ser abordado mediante una aplicación software. Esto podría ser desde una aplicación de gestión para una pequeña empresa, una herramienta para ayudar en la educación, hasta un juego simple. Define claramente el problema que tu aplicación pretende resolver.

2. **Planificación:** Documenta brevemente cómo tu aplicación solucionará el problema seleccionado, incluyendo las funcionalidades principales que desarrollarás.

3. **Desarrollo:**
   - **Instancia de Objetos:** Tu aplicación debe crear y utilizar objetos, demostrando tu comprensión de la instanciación y el uso de constructores, métodos, y propiedades.
   - **Métodos Estáticos:** Define y utiliza al menos un método estático, explicando por qué es necesario en tu aplicación.
   - **Uso de IDE:** Desarrolla tu proyecto utilizando un IDE, aprovechando sus herramientas para escribir, compilar, y probar tu código.
   - **Definición de Clases:** Crea clases personalizadas con sus respectivas propiedades, métodos, y constructores.
   - **Clases con genéricos:** Define y utiliza al menos una clase que haga uso de genéricos en tu aplicación.
   - **Herencia y Polimorfismo:** Implementa herencia y/o interfaces en tu proyecto para demostrar la reutilización de código y la flexibilidad de tu diseño.  **Usa los principios SOLID:** Ten presente durante el desarrollo los principios SOLID y úsalos durante el diseño para mejorar tu aplicación.
   - **Librerías de Clases:** Integra y utiliza una o más librerías externas que enriquezcan la funcionalidad de tu aplicación.
   - **Documentación:** Comenta tu código de manera efectiva, facilitando su comprensión y mantenimiento.

4. **Prueba y Depuración:** Realiza pruebas para asegurarte de que tu aplicación funciona como se espera y depura cualquier error encontrado.
5. **Contesta a las preguntas** ver el punto **Preguntas para la Evaluación**

### Recursos

- Apuntes dados en clase sobre programación orientada a objetos, Kotlin, uso de IDEs, y manejo de librerías.
- Recursos vistos en clase, incluyendo ejemplos de código, documentación de Kotlin, y guías de uso de librerías.

### Evaluación y calificación

**RA y CE evaluados**: Resultados de Aprendizaje 2, 4, 6, 7 y Criterios de Evaluación asociados.

**Conlleva presentación**: SI

**Rubrica**: Mas adelante se mostrará la rubrica.

### Entrega

> **La entrega tiene que cumplir las condiciones de entrega para poder ser calificada. En caso de no cumplirlas podría calificarse como no entregada.**
>
- **Conlleva la entrega de URL a repositorio:** El contenido se entregará en un repositorio GitHub. 
- **Respuestas a las preguntas:** Deben contestarse en este fichero, README.md


# Preguntas para la Evaluación

Este conjunto de preguntas está diseñado para ayudarte a reflexionar sobre cómo has aplicado los criterios de evaluación en tu proyecto. Al responderlas, **asegúrate de hacer referencia y enlazar al código relevante** en tu `README.md`, facilitando así la evaluación de tu trabajo.

#### **Criterio global 1: Instancia objetos y hacer uso de ellos**
- **(2.a, 2.b, 2.c, 2.d, 2.f, 2.h, 4.f, 4.a)**: Describe cómo has instanciado y utilizado objetos en tu proyecto. ¿Cómo has aplicado los constructores y pasado parámetros a los métodos? Proporciona ejemplos específicos de tu código.

-- Instanciar y usar objetos
He instanciado y utilizado objetos de diversas clases para representar elementos del juego, como jugadores, armas, objetos y la propia partida, tambien he usado una clase para la gestion de la consola y todo lo que tenga que ver en mostrar cosas por pantalla, por ultimo el gestor de datos es para almacenar los datos en resultado.txt

Todos mis objetos estan instanciados en main, ya que para que la partida se inicie necesita de todos los objetos necesarios

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/58b5d338e9518caa9553eb8f10faf851c5d6bc6d/src/main/kotlin/Main.kt#L30-L48

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/58b5d338e9518caa9553eb8f10faf851c5d6bc6d/src/main/kotlin/Main.kt#L57

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/58b5d338e9518caa9553eb8f10faf851c5d6bc6d/src/main/kotlin/Main.kt#L66

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/58b5d338e9518caa9553eb8f10faf851c5d6bc6d/src/main/kotlin/Main.kt#L88

No he hecho

val partida = Partida(listaJugadores,armas,todosObjetos,GestionConsola(),GestionInfoPartida())

Porque tanto GestionConsola y GestionInfoPartida tienen metodos fuera de la partida

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/58b5d338e9518caa9553eb8f10faf851c5d6bc6d/src/main/kotlin/Main.kt#L53

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/58b5d338e9518caa9553eb8f10faf851c5d6bc6d/src/main/kotlin/Main.kt#L94

-- Constructores

Los constructores son para inicializar las instancias de las clases, proporcionan la informacion necesaria como para configurar bien al objeto

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/764668f50417205816dcf73ce241714fc4cd73bf/src/main/kotlin/Partida.kt#L14-L20

Ninguna propiedad de las clases excepto los objetos del usuario son iniciadas por defecto

val jugador1 = Jugador("Fran", vida, gestionConsola)

-- Pasar parametros a metodos

Realmente aqui reside todo, si no pasas bien los parametros a las funciones o no sabes a lo que te estas refiriendo en cada momento o su tipo,estas perdido

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/764668f50417205816dcf73ce241714fc4cd73bf/src/main/kotlin/Arma.kt#L39

por ejemplo aqui le paso num balas que es el tamaño maximo del cargador y crea un cargador aletorio

En la mayoria de las funciones tienes que pasar cosas por parametros

#### **Criterio global 2: Crear y llamar métodos estáticos**
- **(4.i)**: ¿Has definido algún método/propiedad estático en tu proyecto? ¿Cuál era el objetivo y por qué consideraste que debía ser estático en lugar de un método/propiedad de instancia?
- **(2.e)**: ¿En qué parte del código se llama a un método estático o se utiliza la propiedad estática?

En mi proyecto no he usado metodos estaticos, no he visto la necesidad
,Pero si en lugar de tratar al jugador por su nombre tubiera id y lo tratara por id, como la id es unica por jugador
podria hacer un companion object para hacer que la id sea unica por cada jugador, con un metodo de anadirId y cada vez que se inicialice un nuevo jugador llamarla

Tambien podria haberlo hecho en arma, pero como el jugador nunca va a instanciar un arma, nunca va a decidir el cargador maximo asi que prefiero que sea una propiedad y hacer una lista con las armas 

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/764668f50417205816dcf73ce241714fc4cd73bf/src/main/kotlin/Main.kt#L41-L45

creo que otro ejemplo es que las partidas tengan IDS y cuando se almacenan en resultado.txt, en lugar dee sobreescribir el archivo , que se le una con la id
https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/764668f50417205816dcf73ce241714fc4cd73bf/src/main/kotlin/GestorDatos.kt#L115

Seria cambiar esta linea, en lugar de writeText pues append

y para la id de la partida seria igual que el usuario

#### **Criterio global 3: Uso de entornos**
- **(2.i)**: ¿Cómo utilizaste el IDE para el desarrollo de tu proyecto? Describe el proceso de creación, compilación, y prueba de tu programa.

Haber el IDE tampoco ayuda mucho a no ser que sea depurando y cuando tienes errores en algo, que te dice como solucionarlo o como optimizar algo de codigo
La compilacion suele ser lenta ya que el proyecto esta en gradle por tanto probar el programa, tambien suele ser algo lento

la depuracion definitivamente ha sido mi mejor amiga, sobretodo para hacer la IA
ya que no sabia como calcular el chance por ronda, ya que usar algun item requiere que el chance sea 100 o 0 directamente
Asi que cada vez que la IA se ponia a meterse tiros ronda por ronda asta la muerte me ponia a depurar y ver que pasaba
Asta que llegue a esto

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/764668f50417205816dcf73ce241714fc4cd73bf/src/main/kotlin/Jugador.kt#L190-L198

#### **Criterio global 4: Definir clases y su contenido**
- **(4.b, 4.c, 4.d, 4.g)**: Explica sobre un ejemplo de tu código, cómo definiste las clases en tu proyecto, es decir como identificaste las de propiedades, métodos y constructores y modificadores del control de acceso a métodos y propiedades, para representar al objeto del mundo real. ¿Cómo contribuyen estas clases a la solución del problema que tu aplicación aborda?

Empece con la clase arma, ya que para jugar se necesita un arma, como hay mas de un arma sera una clase abstracta

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/764668f50417205816dcf73ce241714fc4cd73bf/src/main/kotlin/Arma.kt#L10

al pasarle los parametros al arma me di cuenta que necesitaba un jugador el cual reciba el daño, En ese momento solo guardaba datos asi que era una data class

al seguir desarrollando arma me di cuenta que necesitaba las balas, y si estaban cargadas o no

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/764668f50417205816dcf73ce241714fc4cd73bf/src/main/kotlin/Cartucho.kt#L5

y como no queria hacerlo todo dentro del main hice la clase partida, de primeras solo se le pasaba la lista de jugadores y una escopeta, ya que en la primera version solo habia 1 escopeta

https://github.com/IES-Rafael-Alberti/prog-practica-libre-trimestre-2-ftrugon/blob/764668f50417205816dcf73ce241714fc4cd73bf/src/main/kotlin/Cartucho.kt#L5

com

#### **Criterio global 5: Herencia y uso de clases abstractas e interfaces**
- **(4.h, 4.j, 7.a, 7.b, 7.c)**: Describe sobre tu código cómo has implementado la herencia o utilizado interfaces en tu proyecto. ¿Por qué elegiste este enfoque y cómo beneficia a la estructura de tu aplicación? ¿De qué manera has utilizado los principios SOLID para mejorar el diseño de tu proyecto? ¿Mostrando tu código, contesta a qué principios has utilizado y qué beneficio has obtenido?

#### **Criterio global 6: Diseño de jerarquía de clases**
- **(7.d, 7.e, 7.f, 7.g)**: Presenta la jerarquía de clases que diseñaste. ¿Cómo probaste y depuraste esta jerarquía para asegurar su correcto funcionamiento? ¿Qué tipo de herencia has utilizado: Especificación, Especialización, Extensión, Construcción?

#### **Criterio global 7: Librerías de clases**
- **(2.g, 4.k)**: Describe cualquier librería externa que hayas incorporado en tu proyecto. Explica cómo y por qué las elegiste, y cómo las incorporaste en tu proyecto. ¿Cómo extendió la funcionalidad de tu aplicación? Proporciona ejemplos específicos de su uso en tu proyecto.

#### **Criterio global 8: Documentado**
- **(7.h)**: Muestra ejemplos de cómo has documentado y comentado tu código. ¿Que herramientas has utilizado? ¿Cómo aseguras que tu documentación aporte valor para la comprensión, mantenimiento y depuración del código?

#### **Criterio global 9: Genéricos**
- **(6.f)**: Muestra ejemplos de tu código sobre cómo has implementado una clase con genéricos. ¿Qué beneficio has obtenido?

